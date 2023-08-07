
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Rol;

public class RolJpaController implements Serializable {

    public RolJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf;

    public RolJpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaHospitalarioPU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Rol rol) {
        if (rol.getListaPersona() == null) {
            rol.setListaPersona(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Persona> attachedListaPersona = new ArrayList<Persona>();
            for (Persona listaPersonaPersonaToAttach : rol.getListaPersona()) {
                listaPersonaPersonaToAttach = em.getReference(listaPersonaPersonaToAttach.getClass(), listaPersonaPersonaToAttach.getId_persona());
                attachedListaPersona.add(listaPersonaPersonaToAttach);
            }
            rol.setListaPersona(attachedListaPersona);
            em.persist(rol);
            for (Persona listaPersonaPersona : rol.getListaPersona()) {
                Rol oldRolOfListaPersonaPersona = listaPersonaPersona.getRol();
                listaPersonaPersona.setRol(rol);
                listaPersonaPersona = em.merge(listaPersonaPersona);
                if (oldRolOfListaPersonaPersona != null) {
                    oldRolOfListaPersonaPersona.getListaPersona().remove(listaPersonaPersona);
                    oldRolOfListaPersonaPersona = em.merge(oldRolOfListaPersonaPersona);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Rol rol) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol persistentRol = em.find(Rol.class, rol.getId_rol());
            List<Persona> listaPersonaOld = persistentRol.getListaPersona();
            List<Persona> listaPersonaNew = rol.getListaPersona();
            List<String> illegalOrphanMessages = null;
            for (Persona listaPersonaOldPersona : listaPersonaOld) {
                if (!listaPersonaNew.contains(listaPersonaOldPersona)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Persona " + listaPersonaOldPersona + " since its rol field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Persona> attachedListaPersonaNew = new ArrayList<Persona>();
            for (Persona listaPersonaNewPersonaToAttach : listaPersonaNew) {
                listaPersonaNewPersonaToAttach = em.getReference(listaPersonaNewPersonaToAttach.getClass(), listaPersonaNewPersonaToAttach.getId_persona());
                attachedListaPersonaNew.add(listaPersonaNewPersonaToAttach);
            }
            listaPersonaNew = attachedListaPersonaNew;
            rol.setListaPersona(listaPersonaNew);
            rol = em.merge(rol);
            for (Persona listaPersonaNewPersona : listaPersonaNew) {
                if (!listaPersonaOld.contains(listaPersonaNewPersona)) {
                    Rol oldRolOfListaPersonaNewPersona = listaPersonaNewPersona.getRol();
                    listaPersonaNewPersona.setRol(rol);
                    listaPersonaNewPersona = em.merge(listaPersonaNewPersona);
                    if (oldRolOfListaPersonaNewPersona != null && !oldRolOfListaPersonaNewPersona.equals(rol)) {
                        oldRolOfListaPersonaNewPersona.getListaPersona().remove(listaPersonaNewPersona);
                        oldRolOfListaPersonaNewPersona = em.merge(oldRolOfListaPersonaNewPersona);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = rol.getId_rol();
                if (findRol(id) == null) {
                    throw new NonexistentEntityException("The rol with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Rol rol;
            try {
                rol = em.getReference(Rol.class, id);
                rol.getId_rol();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The rol with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Persona> listaPersonaOrphanCheck = rol.getListaPersona();
            for (Persona listaPersonaOrphanCheckPersona : listaPersonaOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Rol (" + rol + ") cannot be destroyed since the Persona " + listaPersonaOrphanCheckPersona + " in its listaPersona field has a non-nullable rol field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(rol);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Rol> findRolEntities() {
        return findRolEntities(true, -1, -1);
    }

    public List<Rol> findRolEntities(int maxResults, int firstResult) {
        return findRolEntities(false, maxResults, firstResult);
    }

    private List<Rol> findRolEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Rol.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Rol findRol(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Rol.class, id);
        } finally {
            em.close();
        }
    }

    public int getRolCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Rol> rt = cq.from(Rol.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

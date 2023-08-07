
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Cuenta;
import modelo.Rol;
import modelo.HistorialClinico;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Persona;

public class PersonaJpaController implements Serializable {

    public PersonaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf;
    
    public PersonaJpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaHospitalarioPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta cuenta = persona.getCuenta();
            if (cuenta != null) {
                cuenta = em.getReference(cuenta.getClass(), cuenta.getId_cuenta());
                persona.setCuenta(cuenta);
            }
            Rol rol = persona.getRol();
            if (rol != null) {
                rol = em.getReference(rol.getClass(), rol.getId_rol());
                persona.setRol(rol);
            }
            HistorialClinico historial_clinico = persona.getHistorial_clinico();
            if (historial_clinico != null) {
                historial_clinico = em.getReference(historial_clinico.getClass(), historial_clinico.getId_historial_clinico());
                persona.setHistorial_clinico(historial_clinico);
            }
            em.persist(persona);
            if (cuenta != null) {
                Persona oldPersonaOfCuenta = cuenta.getPersona();
                if (oldPersonaOfCuenta != null) {
                    oldPersonaOfCuenta.setCuenta(null);
                    oldPersonaOfCuenta = em.merge(oldPersonaOfCuenta);
                }
                cuenta.setPersona(persona);
                cuenta = em.merge(cuenta);
            }
            if (rol != null) {
                rol.getListaPersona().add(persona);
                rol = em.merge(rol);
            }
            if (historial_clinico != null) {
                Persona oldPersonaOfHistorial_clinico = historial_clinico.getPersona();
                if (oldPersonaOfHistorial_clinico != null) {
                    oldPersonaOfHistorial_clinico.setHistorial_clinico(null);
                    oldPersonaOfHistorial_clinico = em.merge(oldPersonaOfHistorial_clinico);
                }
                historial_clinico.setPersona(persona);
                historial_clinico = em.merge(historial_clinico);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persistentPersona = em.find(Persona.class, persona.getId_persona());
            Cuenta cuentaOld = persistentPersona.getCuenta();
            Cuenta cuentaNew = persona.getCuenta();
            Rol rolOld = persistentPersona.getRol();
            Rol rolNew = persona.getRol();
            HistorialClinico historial_clinicoOld = persistentPersona.getHistorial_clinico();
            HistorialClinico historial_clinicoNew = persona.getHistorial_clinico();
            List<String> illegalOrphanMessages = null;
            if (cuentaOld != null && !cuentaOld.equals(cuentaNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Cuenta " + cuentaOld + " since its persona field is not nullable.");
            }
            if (historial_clinicoOld != null && !historial_clinicoOld.equals(historial_clinicoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain HistorialClinico " + historial_clinicoOld + " since its persona field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (cuentaNew != null) {
                cuentaNew = em.getReference(cuentaNew.getClass(), cuentaNew.getId_cuenta());
                persona.setCuenta(cuentaNew);
            }
            if (rolNew != null) {
                rolNew = em.getReference(rolNew.getClass(), rolNew.getId_rol());
                persona.setRol(rolNew);
            }
            if (historial_clinicoNew != null) {
                historial_clinicoNew = em.getReference(historial_clinicoNew.getClass(), historial_clinicoNew.getId_historial_clinico());
                persona.setHistorial_clinico(historial_clinicoNew);
            }
            persona = em.merge(persona);
            if (cuentaNew != null && !cuentaNew.equals(cuentaOld)) {
                Persona oldPersonaOfCuenta = cuentaNew.getPersona();
                if (oldPersonaOfCuenta != null) {
                    oldPersonaOfCuenta.setCuenta(null);
                    oldPersonaOfCuenta = em.merge(oldPersonaOfCuenta);
                }
                cuentaNew.setPersona(persona);
                cuentaNew = em.merge(cuentaNew);
            }
            if (rolOld != null && !rolOld.equals(rolNew)) {
                rolOld.getListaPersona().remove(persona);
                rolOld = em.merge(rolOld);
            }
            if (rolNew != null && !rolNew.equals(rolOld)) {
                rolNew.getListaPersona().add(persona);
                rolNew = em.merge(rolNew);
            }
            if (historial_clinicoNew != null && !historial_clinicoNew.equals(historial_clinicoOld)) {
                Persona oldPersonaOfHistorial_clinico = historial_clinicoNew.getPersona();
                if (oldPersonaOfHistorial_clinico != null) {
                    oldPersonaOfHistorial_clinico.setHistorial_clinico(null);
                    oldPersonaOfHistorial_clinico = em.merge(oldPersonaOfHistorial_clinico);
                }
                historial_clinicoNew.setPersona(persona);
                historial_clinicoNew = em.merge(historial_clinicoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = persona.getId_persona();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
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
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getId_persona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Cuenta cuentaOrphanCheck = persona.getCuenta();
            if (cuentaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the Cuenta " + cuentaOrphanCheck + " in its cuenta field has a non-nullable persona field.");
            }
            HistorialClinico historial_clinicoOrphanCheck = persona.getHistorial_clinico();
            if (historial_clinicoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Persona (" + persona + ") cannot be destroyed since the HistorialClinico " + historial_clinicoOrphanCheck + " in its historial_clinico field has a non-nullable persona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Rol rol = persona.getRol();
            if (rol != null) {
                rol.getListaPersona().remove(persona);
                rol = em.merge(rol);
            }
            em.remove(persona);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
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

    public Persona findPersona(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Persona> getPersonasPorEstado(String estado) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT e "
                    + "FROM Persona e " 
                    + "WHERE e.estado = ?3")
                    .setParameter(3, estado);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}

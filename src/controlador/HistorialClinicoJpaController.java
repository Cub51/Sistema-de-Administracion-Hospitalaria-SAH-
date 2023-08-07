
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
import modelo.HistorialClinico;

public class HistorialClinicoJpaController implements Serializable {

    public HistorialClinicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf;

    public HistorialClinicoJpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaHospitalarioPU");
    }

    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HistorialClinico historialClinico) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Persona personaOrphanCheck = historialClinico.getPersona();
        if (personaOrphanCheck != null) {
            HistorialClinico oldHistorial_clinicoOfPersona = personaOrphanCheck.getHistorial_clinico();
            if (oldHistorial_clinicoOfPersona != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Persona " + personaOrphanCheck + " already has an item of type HistorialClinico whose persona column cannot be null. Please make another selection for the persona field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Persona persona = historialClinico.getPersona();
            if (persona != null) {
                persona = em.getReference(persona.getClass(), persona.getId_persona());
                historialClinico.setPersona(persona);
            }
            em.persist(historialClinico);
            if (persona != null) {
                persona.setHistorial_clinico(historialClinico);
                persona = em.merge(persona);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HistorialClinico historialClinico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistorialClinico persistentHistorialClinico = em.find(HistorialClinico.class, historialClinico.getId_historial_clinico());
            Persona personaOld = persistentHistorialClinico.getPersona();
            Persona personaNew = historialClinico.getPersona();
            List<String> illegalOrphanMessages = null;
            if (personaNew != null && !personaNew.equals(personaOld)) {
                HistorialClinico oldHistorial_clinicoOfPersona = personaNew.getHistorial_clinico();
                if (oldHistorial_clinicoOfPersona != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Persona " + personaNew + " already has an item of type HistorialClinico whose persona column cannot be null. Please make another selection for the persona field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (personaNew != null) {
                personaNew = em.getReference(personaNew.getClass(), personaNew.getId_persona());
                historialClinico.setPersona(personaNew);
            }
            historialClinico = em.merge(historialClinico);
            if (personaOld != null && !personaOld.equals(personaNew)) {
                personaOld.setHistorial_clinico(null);
                personaOld = em.merge(personaOld);
            }
            if (personaNew != null && !personaNew.equals(personaOld)) {
                personaNew.setHistorial_clinico(historialClinico);
                personaNew = em.merge(personaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = historialClinico.getId_historial_clinico();
                if (findHistorialClinico(id) == null) {
                    throw new NonexistentEntityException("The historialClinico with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HistorialClinico historialClinico;
            try {
                historialClinico = em.getReference(HistorialClinico.class, id);
                historialClinico.getId_historial_clinico();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historialClinico with id " + id + " no longer exists.", enfe);
            }
            Persona persona = historialClinico.getPersona();
            if (persona != null) {
                persona.setHistorial_clinico(null);
                persona = em.merge(persona);
            }
            em.remove(historialClinico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HistorialClinico> findHistorialClinicoEntities() {
        return findHistorialClinicoEntities(true, -1, -1);
    }

    public List<HistorialClinico> findHistorialClinicoEntities(int maxResults, int firstResult) {
        return findHistorialClinicoEntities(false, maxResults, firstResult);
    }

    private List<HistorialClinico> findHistorialClinicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HistorialClinico.class));
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

    public HistorialClinico findHistorialClinico(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HistorialClinico.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistorialClinicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HistorialClinico> rt = cq.from(HistorialClinico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

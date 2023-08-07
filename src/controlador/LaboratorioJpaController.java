
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Examen;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Laboratorio;

public class LaboratorioJpaController implements Serializable {

    public LaboratorioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf;
    
    public LaboratorioJpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaHospitalarioPU");
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Laboratorio laboratorio) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Examen examen = laboratorio.getExamen();
            if (examen != null) {
                examen = em.getReference(examen.getClass(), examen.getId_examen());
                laboratorio.setExamen(examen);
            }
            em.persist(laboratorio);
            if (examen != null) {
                Laboratorio oldLaboratorioOfExamen = examen.getLaboratorio();
                if (oldLaboratorioOfExamen != null) {
                    oldLaboratorioOfExamen.setExamen(null);
                    oldLaboratorioOfExamen = em.merge(oldLaboratorioOfExamen);
                }
                examen.setLaboratorio(laboratorio);
                examen = em.merge(examen);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Laboratorio laboratorio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Laboratorio persistentLaboratorio = em.find(Laboratorio.class, laboratorio.getId_laboratorio());
            Examen examenOld = persistentLaboratorio.getExamen();
            Examen examenNew = laboratorio.getExamen();
            List<String> illegalOrphanMessages = null;
            if (examenOld != null && !examenOld.equals(examenNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Examen " + examenOld + " since its laboratorio field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (examenNew != null) {
                examenNew = em.getReference(examenNew.getClass(), examenNew.getId_examen());
                laboratorio.setExamen(examenNew);
            }
            laboratorio = em.merge(laboratorio);
            if (examenNew != null && !examenNew.equals(examenOld)) {
                Laboratorio oldLaboratorioOfExamen = examenNew.getLaboratorio();
                if (oldLaboratorioOfExamen != null) {
                    oldLaboratorioOfExamen.setExamen(null);
                    oldLaboratorioOfExamen = em.merge(oldLaboratorioOfExamen);
                }
                examenNew.setLaboratorio(laboratorio);
                examenNew = em.merge(examenNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = laboratorio.getId_laboratorio();
                if (findLaboratorio(id) == null) {
                    throw new NonexistentEntityException("The laboratorio with id " + id + " no longer exists.");
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
            Laboratorio laboratorio;
            try {
                laboratorio = em.getReference(Laboratorio.class, id);
                laboratorio.getId_laboratorio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The laboratorio with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Examen examenOrphanCheck = laboratorio.getExamen();
            if (examenOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Laboratorio (" + laboratorio + ") cannot be destroyed since the Examen " + examenOrphanCheck + " in its examen field has a non-nullable laboratorio field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(laboratorio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Laboratorio> findLaboratorioEntities() {
        return findLaboratorioEntities(true, -1, -1);
    }

    public List<Laboratorio> findLaboratorioEntities(int maxResults, int firstResult) {
        return findLaboratorioEntities(false, maxResults, firstResult);
    }

    private List<Laboratorio> findLaboratorioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Laboratorio.class));
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

    public Laboratorio findLaboratorio(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Laboratorio.class, id);
        } finally {
            em.close();
        }
    }

    public int getLaboratorioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Laboratorio> rt = cq.from(Laboratorio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Laboratorio> getLaboratoriosPorEstado(String estado) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT e "
                    + "FROM Laboratorio e " 
                    + "WHERE e.estado = ?3")
                    .setParameter(3, estado);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}


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
import modelo.Categoria;

public class CategoriaJpaController implements Serializable {

    public CategoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf;

    public CategoriaJpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaHospitalarioPU");
    }    
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Categoria categoria) {
        if (categoria.getListaExamen() == null) {
            categoria.setListaExamen(new ArrayList<Examen>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Examen> attachedListaExamen = new ArrayList<Examen>();
            for (Examen listaExamenExamenToAttach : categoria.getListaExamen()) {
                listaExamenExamenToAttach = em.getReference(listaExamenExamenToAttach.getClass(), listaExamenExamenToAttach.getId_examen());
                attachedListaExamen.add(listaExamenExamenToAttach);
            }
            categoria.setListaExamen(attachedListaExamen);
            em.persist(categoria);
            for (Examen listaExamenExamen : categoria.getListaExamen()) {
                Categoria oldCategoriaOfListaExamenExamen = listaExamenExamen.getCategoria();
                listaExamenExamen.setCategoria(categoria);
                listaExamenExamen = em.merge(listaExamenExamen);
                if (oldCategoriaOfListaExamenExamen != null) {
                    oldCategoriaOfListaExamenExamen.getListaExamen().remove(listaExamenExamen);
                    oldCategoriaOfListaExamenExamen = em.merge(oldCategoriaOfListaExamenExamen);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Categoria categoria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categoria persistentCategoria = em.find(Categoria.class, categoria.getId_categoria());
            List<Examen> listaExamenOld = persistentCategoria.getListaExamen();
            List<Examen> listaExamenNew = categoria.getListaExamen();
            List<String> illegalOrphanMessages = null;
            for (Examen listaExamenOldExamen : listaExamenOld) {
                if (!listaExamenNew.contains(listaExamenOldExamen)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Examen " + listaExamenOldExamen + " since its categoria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Examen> attachedListaExamenNew = new ArrayList<Examen>();
            for (Examen listaExamenNewExamenToAttach : listaExamenNew) {
                listaExamenNewExamenToAttach = em.getReference(listaExamenNewExamenToAttach.getClass(), listaExamenNewExamenToAttach.getId_examen());
                attachedListaExamenNew.add(listaExamenNewExamenToAttach);
            }
            listaExamenNew = attachedListaExamenNew;
            categoria.setListaExamen(listaExamenNew);
            categoria = em.merge(categoria);
            for (Examen listaExamenNewExamen : listaExamenNew) {
                if (!listaExamenOld.contains(listaExamenNewExamen)) {
                    Categoria oldCategoriaOfListaExamenNewExamen = listaExamenNewExamen.getCategoria();
                    listaExamenNewExamen.setCategoria(categoria);
                    listaExamenNewExamen = em.merge(listaExamenNewExamen);
                    if (oldCategoriaOfListaExamenNewExamen != null && !oldCategoriaOfListaExamenNewExamen.equals(categoria)) {
                        oldCategoriaOfListaExamenNewExamen.getListaExamen().remove(listaExamenNewExamen);
                        oldCategoriaOfListaExamenNewExamen = em.merge(oldCategoriaOfListaExamenNewExamen);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = categoria.getId_categoria();
                if (findCategoria(id) == null) {
                    throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.");
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
            Categoria categoria;
            try {
                categoria = em.getReference(Categoria.class, id);
                categoria.getId_categoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The categoria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Examen> listaExamenOrphanCheck = categoria.getListaExamen();
            for (Examen listaExamenOrphanCheckExamen : listaExamenOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Categoria (" + categoria + ") cannot be destroyed since the Examen " + listaExamenOrphanCheckExamen + " in its listaExamen field has a non-nullable categoria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(categoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Categoria> findCategoriaEntities() {
        return findCategoriaEntities(true, -1, -1);
    }

    public List<Categoria> findCategoriaEntities(int maxResults, int firstResult) {
        return findCategoriaEntities(false, maxResults, firstResult);
    }

    private List<Categoria> findCategoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Categoria.class));
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

    public Categoria findCategoria(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Categoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCategoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Categoria> rt = cq.from(Categoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

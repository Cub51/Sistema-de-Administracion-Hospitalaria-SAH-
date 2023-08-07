
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Pedido;
import modelo.Laboratorio;
import modelo.Categoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Examen;

public class ExamenJpaController implements Serializable {

    public ExamenJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf;

    public ExamenJpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaHospitalarioPU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Examen examen) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Laboratorio laboratorioOrphanCheck = examen.getLaboratorio();
        if (laboratorioOrphanCheck != null) {
            Examen oldExamenOfLaboratorio = laboratorioOrphanCheck.getExamen();
            if (oldExamenOfLaboratorio != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Laboratorio " + laboratorioOrphanCheck + " already has an item of type Examen whose laboratorio column cannot be null. Please make another selection for the laboratorio field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pedido pedido = examen.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId_pedido());
                examen.setPedido(pedido);
            }
            Laboratorio laboratorio = examen.getLaboratorio();
            if (laboratorio != null) {
                laboratorio = em.getReference(laboratorio.getClass(), laboratorio.getId_laboratorio());
                examen.setLaboratorio(laboratorio);
            }
            Categoria categoria = examen.getCategoria();
            if (categoria != null) {
                categoria = em.getReference(categoria.getClass(), categoria.getId_categoria());
                examen.setCategoria(categoria);
            }
            em.persist(examen);
            if (pedido != null) {
                pedido.getListaExamen().add(examen);
                pedido = em.merge(pedido);
            }
            if (laboratorio != null) {
                laboratorio.setExamen(examen);
                laboratorio = em.merge(laboratorio);
            }
            if (categoria != null) {
                categoria.getListaExamen().add(examen);
                categoria = em.merge(categoria);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Examen examen) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Examen persistentExamen = em.find(Examen.class, examen.getId_examen());
            Pedido pedidoOld = persistentExamen.getPedido();
            Pedido pedidoNew = examen.getPedido();
            Laboratorio laboratorioOld = persistentExamen.getLaboratorio();
            Laboratorio laboratorioNew = examen.getLaboratorio();
            Categoria categoriaOld = persistentExamen.getCategoria();
            Categoria categoriaNew = examen.getCategoria();
            List<String> illegalOrphanMessages = null;
            if (laboratorioNew != null && !laboratorioNew.equals(laboratorioOld)) {
                Examen oldExamenOfLaboratorio = laboratorioNew.getExamen();
                if (oldExamenOfLaboratorio != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Laboratorio " + laboratorioNew + " already has an item of type Examen whose laboratorio column cannot be null. Please make another selection for the laboratorio field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId_pedido());
                examen.setPedido(pedidoNew);
            }
            if (laboratorioNew != null) {
                laboratorioNew = em.getReference(laboratorioNew.getClass(), laboratorioNew.getId_laboratorio());
                examen.setLaboratorio(laboratorioNew);
            }
            if (categoriaNew != null) {
                categoriaNew = em.getReference(categoriaNew.getClass(), categoriaNew.getId_categoria());
                examen.setCategoria(categoriaNew);
            }
            examen = em.merge(examen);
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                pedidoOld.getListaExamen().remove(examen);
                pedidoOld = em.merge(pedidoOld);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                pedidoNew.getListaExamen().add(examen);
                pedidoNew = em.merge(pedidoNew);
            }
            if (laboratorioOld != null && !laboratorioOld.equals(laboratorioNew)) {
                laboratorioOld.setExamen(null);
                laboratorioOld = em.merge(laboratorioOld);
            }
            if (laboratorioNew != null && !laboratorioNew.equals(laboratorioOld)) {
                laboratorioNew.setExamen(examen);
                laboratorioNew = em.merge(laboratorioNew);
            }
            if (categoriaOld != null && !categoriaOld.equals(categoriaNew)) {
                categoriaOld.getListaExamen().remove(examen);
                categoriaOld = em.merge(categoriaOld);
            }
            if (categoriaNew != null && !categoriaNew.equals(categoriaOld)) {
                categoriaNew.getListaExamen().add(examen);
                categoriaNew = em.merge(categoriaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = examen.getId_examen();
                if (findExamen(id) == null) {
                    throw new NonexistentEntityException("The examen with id " + id + " no longer exists.");
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
            Examen examen;
            try {
                examen = em.getReference(Examen.class, id);
                examen.getId_examen();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The examen with id " + id + " no longer exists.", enfe);
            }
            Pedido pedido = examen.getPedido();
            if (pedido != null) {
                pedido.getListaExamen().remove(examen);
                pedido = em.merge(pedido);
            }
            Laboratorio laboratorio = examen.getLaboratorio();
            if (laboratorio != null) {
                laboratorio.setExamen(null);
                laboratorio = em.merge(laboratorio);
            }
            Categoria categoria = examen.getCategoria();
            if (categoria != null) {
                categoria.getListaExamen().remove(examen);
                categoria = em.merge(categoria);
            }
            em.remove(examen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Examen> findExamenEntities() {
        return findExamenEntities(true, -1, -1);
    }

    public List<Examen> findExamenEntities(int maxResults, int firstResult) {
        return findExamenEntities(false, maxResults, firstResult);
    }

    private List<Examen> findExamenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Examen.class));
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

    public Examen findExamen(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Examen.class, id);
        } finally {
            em.close();
        }
    }

    public int getExamenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Examen> rt = cq.from(Examen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

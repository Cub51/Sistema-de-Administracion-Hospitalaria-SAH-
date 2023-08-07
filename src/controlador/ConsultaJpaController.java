
package controlador;

import controlador.exceptions.IllegalOrphanException;
import controlador.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Receta;
import modelo.HistorialClinico;
import modelo.Diagnostico;
import modelo.Pedido;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import modelo.Consulta;

public class ConsultaJpaController implements Serializable {

    public ConsultaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf;

    public ConsultaJpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaHospitalarioPU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Consulta consulta) throws IllegalOrphanException {
        List<String> illegalOrphanMessages = null;
        Receta recetaOrphanCheck = consulta.getReceta();
        if (recetaOrphanCheck != null) {
            Consulta oldConsultaOfReceta = recetaOrphanCheck.getConsulta();
            if (oldConsultaOfReceta != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Receta " + recetaOrphanCheck + " already has an item of type Consulta whose receta column cannot be null. Please make another selection for the receta field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Receta receta = consulta.getReceta();
            if (receta != null) {
                receta = em.getReference(receta.getClass(), receta.getId_receta());
                consulta.setReceta(receta);
            }
            HistorialClinico historial_clinico = consulta.getHistorial_clinico();
            if (historial_clinico != null) {
                historial_clinico = em.getReference(historial_clinico.getClass(), historial_clinico.getId_historial_clinico());
                consulta.setHistorial_clinico(historial_clinico);
            }
            Diagnostico diagnostico = consulta.getDiagnostico();
            if (diagnostico != null) {
                diagnostico = em.getReference(diagnostico.getClass(), diagnostico.getId_diagnostico());
                consulta.setDiagnostico(diagnostico);
            }
            Pedido pedido = consulta.getPedido();
            if (pedido != null) {
                pedido = em.getReference(pedido.getClass(), pedido.getId_pedido());
                consulta.setPedido(pedido);
            }
            em.persist(consulta);
            if (receta != null) {
                receta.setConsulta(consulta);
                receta = em.merge(receta);
            }
            if (historial_clinico != null) {
                historial_clinico.getListarConsulta().add(consulta);
                historial_clinico = em.merge(historial_clinico);
            }
            if (diagnostico != null) {
                Consulta oldConsultaOfDiagnostico = diagnostico.getConsulta();
                if (oldConsultaOfDiagnostico != null) {
                    oldConsultaOfDiagnostico.setDiagnostico(null);
                    oldConsultaOfDiagnostico = em.merge(oldConsultaOfDiagnostico);
                }
                diagnostico.setConsulta(consulta);
                diagnostico = em.merge(diagnostico);
            }
            if (pedido != null) {
                Consulta oldConsultaOfPedido = pedido.getConsulta();
                if (oldConsultaOfPedido != null) {
                    oldConsultaOfPedido.setPedido(null);
                    oldConsultaOfPedido = em.merge(oldConsultaOfPedido);
                }
                pedido.setConsulta(consulta);
                pedido = em.merge(pedido);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Consulta consulta) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Consulta persistentConsulta = em.find(Consulta.class, consulta.getId_consulta());
            Receta recetaOld = persistentConsulta.getReceta();
            Receta recetaNew = consulta.getReceta();
            HistorialClinico historial_clinicoOld = persistentConsulta.getHistorial_clinico();
            HistorialClinico historial_clinicoNew = consulta.getHistorial_clinico();
            Diagnostico diagnosticoOld = persistentConsulta.getDiagnostico();
            Diagnostico diagnosticoNew = consulta.getDiagnostico();
            Pedido pedidoOld = persistentConsulta.getPedido();
            Pedido pedidoNew = consulta.getPedido();
            List<String> illegalOrphanMessages = null;
            if (recetaNew != null && !recetaNew.equals(recetaOld)) {
                Consulta oldConsultaOfReceta = recetaNew.getConsulta();
                if (oldConsultaOfReceta != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Receta " + recetaNew + " already has an item of type Consulta whose receta column cannot be null. Please make another selection for the receta field.");
                }
            }
            if (diagnosticoOld != null && !diagnosticoOld.equals(diagnosticoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Diagnostico " + diagnosticoOld + " since its consulta field is not nullable.");
            }
            if (pedidoOld != null && !pedidoOld.equals(pedidoNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Pedido " + pedidoOld + " since its consulta field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (recetaNew != null) {
                recetaNew = em.getReference(recetaNew.getClass(), recetaNew.getId_receta());
                consulta.setReceta(recetaNew);
            }
            if (historial_clinicoNew != null) {
                historial_clinicoNew = em.getReference(historial_clinicoNew.getClass(), historial_clinicoNew.getId_historial_clinico());
                consulta.setHistorial_clinico(historial_clinicoNew);
            }
            if (diagnosticoNew != null) {
                diagnosticoNew = em.getReference(diagnosticoNew.getClass(), diagnosticoNew.getId_diagnostico());
                consulta.setDiagnostico(diagnosticoNew);
            }
            if (pedidoNew != null) {
                pedidoNew = em.getReference(pedidoNew.getClass(), pedidoNew.getId_pedido());
                consulta.setPedido(pedidoNew);
            }
            consulta = em.merge(consulta);
            if (recetaOld != null && !recetaOld.equals(recetaNew)) {
                recetaOld.setConsulta(null);
                recetaOld = em.merge(recetaOld);
            }
            if (recetaNew != null && !recetaNew.equals(recetaOld)) {
                recetaNew.setConsulta(consulta);
                recetaNew = em.merge(recetaNew);
            }
            if (historial_clinicoOld != null && !historial_clinicoOld.equals(historial_clinicoNew)) {
                historial_clinicoOld.getListarConsulta().remove(consulta);
                historial_clinicoOld = em.merge(historial_clinicoOld);
            }
            if (historial_clinicoNew != null && !historial_clinicoNew.equals(historial_clinicoOld)) {
                historial_clinicoNew.getListarConsulta().add(consulta);
                historial_clinicoNew = em.merge(historial_clinicoNew);
            }
            if (diagnosticoNew != null && !diagnosticoNew.equals(diagnosticoOld)) {
                Consulta oldConsultaOfDiagnostico = diagnosticoNew.getConsulta();
                if (oldConsultaOfDiagnostico != null) {
                    oldConsultaOfDiagnostico.setDiagnostico(null);
                    oldConsultaOfDiagnostico = em.merge(oldConsultaOfDiagnostico);
                }
                diagnosticoNew.setConsulta(consulta);
                diagnosticoNew = em.merge(diagnosticoNew);
            }
            if (pedidoNew != null && !pedidoNew.equals(pedidoOld)) {
                Consulta oldConsultaOfPedido = pedidoNew.getConsulta();
                if (oldConsultaOfPedido != null) {
                    oldConsultaOfPedido.setPedido(null);
                    oldConsultaOfPedido = em.merge(oldConsultaOfPedido);
                }
                pedidoNew.setConsulta(consulta);
                pedidoNew = em.merge(pedidoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = consulta.getId_consulta();
                if (findConsulta(id) == null) {
                    throw new NonexistentEntityException("The consulta with id " + id + " no longer exists.");
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
            Consulta consulta;
            try {
                consulta = em.getReference(Consulta.class, id);
                consulta.getId_consulta();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The consulta with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Diagnostico diagnosticoOrphanCheck = consulta.getDiagnostico();
            if (diagnosticoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Consulta (" + consulta + ") cannot be destroyed since the Diagnostico " + diagnosticoOrphanCheck + " in its diagnostico field has a non-nullable consulta field.");
            }
            Pedido pedidoOrphanCheck = consulta.getPedido();
            if (pedidoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Consulta (" + consulta + ") cannot be destroyed since the Pedido " + pedidoOrphanCheck + " in its pedido field has a non-nullable consulta field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Receta receta = consulta.getReceta();
            if (receta != null) {
                receta.setConsulta(null);
                receta = em.merge(receta);
            }
            HistorialClinico historial_clinico = consulta.getHistorial_clinico();
            if (historial_clinico != null) {
                historial_clinico.getListarConsulta().remove(consulta);
                historial_clinico = em.merge(historial_clinico);
            }
            em.remove(consulta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Consulta> findConsultaEntities() {
        return findConsultaEntities(true, -1, -1);
    }

    public List<Consulta> findConsultaEntities(int maxResults, int firstResult) {
        return findConsultaEntities(false, maxResults, firstResult);
    }

    private List<Consulta> findConsultaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Consulta.class));
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

    public Consulta findConsulta(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Consulta.class, id);
        } finally {
            em.close();
        }
    }

    public int getConsultaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Consulta> rt = cq.from(Consulta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    public List<Consulta> getConsultasPorEstado(String estado) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT e "
                    + "FROM Consulta e "
                    + "WHERE e.estado_consulta = ?1")
                    .setParameter(1, estado);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public List<Consulta> getPedidosPorTodosMenosUnEstado(String estado) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("SELECT e "
                    + "FROM Consulta e "
                    + "WHERE NOT e.estado_consulta = ?1")
                    .setParameter(1, estado);
            return q.getResultList();
        } finally {
            em.close();
        }
    }
    
}

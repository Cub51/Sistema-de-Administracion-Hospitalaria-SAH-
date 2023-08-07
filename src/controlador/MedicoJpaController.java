
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
import modelo.Medico;

public class MedicoJpaController implements Serializable {

    public MedicoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf;

    public MedicoJpaController() {
        emf = Persistence.createEntityManagerFactory("SistemaHospitalarioPU");
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Medico medico) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cuenta cuenta = medico.getCuenta();
            if (cuenta != null) {
                cuenta = em.getReference(cuenta.getClass(), cuenta.getId_cuenta());
                medico.setCuenta(cuenta);
            }
            Rol rol = medico.getRol();
            if (rol != null) {
                rol = em.getReference(rol.getClass(), rol.getId_rol());
                medico.setRol(rol);
            }
            HistorialClinico historial_clinico = medico.getHistorial_clinico();
            if (historial_clinico != null) {
                historial_clinico = em.getReference(historial_clinico.getClass(), historial_clinico.getId_historial_clinico());
                medico.setHistorial_clinico(historial_clinico);
            }
            em.persist(medico);
            if (cuenta != null) {
                modelo.Persona oldPersonaOfCuenta = cuenta.getPersona();
                if (oldPersonaOfCuenta != null) {
                    oldPersonaOfCuenta.setCuenta(null);
                    oldPersonaOfCuenta = em.merge(oldPersonaOfCuenta);
                }
                cuenta.setPersona(medico);
                cuenta = em.merge(cuenta);
            }
            if (rol != null) {
                rol.getListaPersona().add(medico);
                rol = em.merge(rol);
            }
            if (historial_clinico != null) {
                modelo.Persona oldPersonaOfHistorial_clinico = historial_clinico.getPersona();
                if (oldPersonaOfHistorial_clinico != null) {
                    oldPersonaOfHistorial_clinico.setHistorial_clinico(null);
                    oldPersonaOfHistorial_clinico = em.merge(oldPersonaOfHistorial_clinico);
                }
                historial_clinico.setPersona(medico);
                historial_clinico = em.merge(historial_clinico);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Medico medico) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Medico persistentMedico = em.find(Medico.class, medico.getId_persona());
            Cuenta cuentaOld = persistentMedico.getCuenta();
            Cuenta cuentaNew = medico.getCuenta();
            Rol rolOld = persistentMedico.getRol();
            Rol rolNew = medico.getRol();
            HistorialClinico historial_clinicoOld = persistentMedico.getHistorial_clinico();
            HistorialClinico historial_clinicoNew = medico.getHistorial_clinico();
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
                medico.setCuenta(cuentaNew);
            }
            if (rolNew != null) {
                rolNew = em.getReference(rolNew.getClass(), rolNew.getId_rol());
                medico.setRol(rolNew);
            }
            if (historial_clinicoNew != null) {
                historial_clinicoNew = em.getReference(historial_clinicoNew.getClass(), historial_clinicoNew.getId_historial_clinico());
                medico.setHistorial_clinico(historial_clinicoNew);
            }
            medico = em.merge(medico);
            if (cuentaNew != null && !cuentaNew.equals(cuentaOld)) {
                modelo.Persona oldPersonaOfCuenta = cuentaNew.getPersona();
                if (oldPersonaOfCuenta != null) {
                    oldPersonaOfCuenta.setCuenta(null);
                    oldPersonaOfCuenta = em.merge(oldPersonaOfCuenta);
                }
                cuentaNew.setPersona(medico);
                cuentaNew = em.merge(cuentaNew);
            }
            if (rolOld != null && !rolOld.equals(rolNew)) {
                rolOld.getListaPersona().remove(medico);
                rolOld = em.merge(rolOld);
            }
            if (rolNew != null && !rolNew.equals(rolOld)) {
                rolNew.getListaPersona().add(medico);
                rolNew = em.merge(rolNew);
            }
            if (historial_clinicoNew != null && !historial_clinicoNew.equals(historial_clinicoOld)) {
                modelo.Persona oldPersonaOfHistorial_clinico = historial_clinicoNew.getPersona();
                if (oldPersonaOfHistorial_clinico != null) {
                    oldPersonaOfHistorial_clinico.setHistorial_clinico(null);
                    oldPersonaOfHistorial_clinico = em.merge(oldPersonaOfHistorial_clinico);
                }
                historial_clinicoNew.setPersona(medico);
                historial_clinicoNew = em.merge(historial_clinicoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = medico.getId_persona();
                if (findMedico(id) == null) {
                    throw new NonexistentEntityException("The medico with id " + id + " no longer exists.");
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
            Medico medico;
            try {
                medico = em.getReference(Medico.class, id);
                medico.getId_persona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The medico with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Cuenta cuentaOrphanCheck = medico.getCuenta();
            if (cuentaOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Medico (" + medico + ") cannot be destroyed since the Cuenta " + cuentaOrphanCheck + " in its cuenta field has a non-nullable persona field.");
            }
            HistorialClinico historial_clinicoOrphanCheck = medico.getHistorial_clinico();
            if (historial_clinicoOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Medico (" + medico + ") cannot be destroyed since the HistorialClinico " + historial_clinicoOrphanCheck + " in its historial_clinico field has a non-nullable persona field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Rol rol = medico.getRol();
            if (rol != null) {
                rol.getListaPersona().remove(medico);
                rol = em.merge(rol);
            }
            em.remove(medico);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Medico> findMedicoEntities() {
        return findMedicoEntities(true, -1, -1);
    }

    public List<Medico> findMedicoEntities(int maxResults, int firstResult) {
        return findMedicoEntities(false, maxResults, firstResult);
    }

    private List<Medico> findMedicoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Medico.class));
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

    public Medico findMedico(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Medico.class, id);
        } finally {
            em.close();
        }
    }

    public int getMedicoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Medico> rt = cq.from(Medico.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

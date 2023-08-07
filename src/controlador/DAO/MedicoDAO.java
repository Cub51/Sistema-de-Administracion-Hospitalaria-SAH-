package controlador.DAO;

import controlador.MedicoJpaController;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import modelo.Medico;

public class MedicoDAO {

    private MedicoJpaController medicoJpa = new MedicoJpaController();
    private Medico medico;

    public Medico getMedico() {
        if (this.medico == null) {
            this.medico = new Medico();
        }
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    /**
     * Método que busca el médico solicitado.
     *
     * @param medico Objeto que va a ser buscado en el sistema, es de tipo Medico.
     * @return Retorna Medico si se logró encontrar el médico, caso contrario,
     * devuelve vacio.
     */
    public Medico buscarMedico(Medico medico) {
        Medico aux = new Medico();
        try {
            aux = medicoJpa.findMedico(medico.getId_persona());
            return aux;
        } catch (Exception e) {
            return aux;
        }
    }

    /**
     * Método que busca el médico solicitado por su identificador.
     *
     * @param id identificador que va a ser buscado en el sistema, es de tipo long.
     * @return Retorna Medico si se logró encontrar el médico, caso contrario,
     * devuelve vacio.
     */
    public Medico buscarMedicoid(long id) {
        Medico aux = new Medico();
        try {
            aux = medicoJpa.findMedico(id);
            return aux;
        } catch (Exception e) {
            return aux;
        }
    }

    /**
     * Método que permite agregar un médico.
     *
     * @param medico Objeto que va a ser agregado al sistema, es de tipo Medico.
     * @return Retorna true si se logró agregar el medico, caso contrario,
     * devuelve false.
     */
    public boolean agregarMedico(Medico medico) {
        try {
            medicoJpa.create(medico);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    /**
     * Método que devuelve una lista con todos los medicos ingresados en el sistema.
     *
     * @return Retorna una lista de tipo List.
     */
    public List listarMedicos() {
        List<Medico> listaMedico = new ArrayList<Medico>();
        try {
            listaMedico = medicoJpa.findMedicoEntities();
            return listaMedico;
        } catch (Exception e) {
            return listaMedico;
        }
    }

    /**
     * Método que permite filtrar los médicos según su identificador y estado de Disponibilidad.
     *
     * @param idRol Identificador de los médicos registrados que se filtrarán, es de tipo Long.
     * @param estadoDisponibilidad Estado de los médicos registrados que se filtrarán, es de tipo String.
     * @return Retorna una lista de tipo List.
     */
    public List<Medico> filtrarPor_EstadoDiponibilidad_IdRol(Long idRol, String estadoDisponibilidad) {
        List<Medico> listaMedicos = getMedicosPorEstado("activo");
        List<Medico> listaMedicosPorEstadoDisponibilidad = new ArrayList<Medico>();

        for (Medico medico : listaMedicos) {
            //if (medico.getEstado_disponibilidad().equalsIgnoreCase(estadoDisponibilidad) && medico.getRol().getId_rol() == idRol) {
            //    listaMedicosPorEstadoDisponibilidad.add(medico);
            if (medico.getEstado_disponibilidad().equalsIgnoreCase(estadoDisponibilidad) && Objects.equals(medico.getRol().getId_rol(), idRol)) {
                listaMedicosPorEstadoDisponibilidad.add(medico);
            }
        }
        return listaMedicosPorEstadoDisponibilidad;
    }

    /**
     * Método que devuelve una lista con todos los medicos que cumplan con el
     * estado indicado.
     *
     * @param estado Estado por el cual se van a filtar los medicos, es de tipo
     * String.
     * @return Retorna una lista de tipo List.
     */
    public List<Medico> getMedicosPorEstado(String estado) {
        List<Medico> listaMedicos = listarMedicos();
        List<Medico> listaMedicosPorEstado = new ArrayList<Medico>();
        for (Medico medico : listaMedicos) {
            if (medico.getEstado().equalsIgnoreCase(estado)) {
                listaMedicosPorEstado.add(medico);
            }
        }
        return listaMedicosPorEstado;
    }

    /**
     * Método que devuelve una lista con todos los medicos ingresados en el sistema que tiene un estado "activo" y un estado de disponibilidad "Disponible".
     *
     * @return Retorna una lista de tipo List.
     */
    public List FiltrarMedicos() {
        List<Medico> medicos = new ArrayList();
        for (Object p : getMedicosPorEstado("activo")) {
            if (buscarMedico((Medico) p).getEstado_disponibilidad().toString().equals("Disponible")) {
                medicos.add((Medico) p);
            }
        }
        return medicos;
    }

    /**
     * Método que permite editar un medico ingresado en el sistema.
     * 
     * @param medico Objeto que contiene los datos actualizados del medico, es de tipo Medico.
     * @return Retorna true si se logró actualizar el médico, caso contrario, devuelve false.
     */
    public boolean editarMedico(Medico medico) {
        try {
            medicoJpa.edit(medico);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    /**
     * Método que busca el médico solicitado por su cédula.
     *
     * @param cedula cédula que va a ser buscada en el sistema, es de tipo String.
     * @return Retorna Medico si se logró encontrar el médico, caso contrario, devuelve vacio.
     */
    public Medico buscarMedico(String cedula) {
        for (Object p : listarMedicos()) {
            if (buscarMedico((Medico) p).getCedula().equals(cedula)) {
                System.out.println("SI ENCONTRO MEDICO");
                return buscarMedico((Medico) p);
            }
        }
        return null;
    }
}

package controlador.DAO;

import controlador.HistorialClinicoJpaController;
import java.util.ArrayList;
import java.util.List;
import modelo.HistorialClinico;

public class HistorialClinicoDAO {

    private HistorialClinicoJpaController hcJPAC = new HistorialClinicoJpaController();
    private PersonaDAO personaDAO = new PersonaDAO();
    private HistorialClinico hc = new HistorialClinico();

    public HistorialClinico getHc() {
        if (hc == null) {
            hc = new HistorialClinico();
        }
        return hc;
    }

    public void setHc(HistorialClinico exam) {
        this.hc = exam;
    }

    /**
     * Metodo para crear un nuevo historial clínico en la base de datos
     *
     * @param hc Objeto que va a ser agregado al sistema
     * @return Retorna true si se logró agregar el Historial Clinico, caso contrario, devuelve false.
     */
    public boolean agregarHC(HistorialClinico hc) {

        try {
            hcJPAC.create(hc);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo para editar un historial clínico en la base de datos
     *
     * @param hc Objeto que contiene los datos actualizados del Historial Clinico.
     * @return Retorna true si se logró actualizar el Historial Clinico, caso contrario, devuelve false.
     */
    public boolean editarHC(HistorialClinico hc) {

        try {
            hcJPAC.edit(hc);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo para encontrar un historial clínico en la base de datos mediante
     * su id
     *
     * @param hc Objeto que contiene el id del Historial Clinico a buscar
     * @return Retorna el Historial Clinico en caso de encontrarlo.
     */
    public HistorialClinico encontrarHC(HistorialClinico hc) {
        HistorialClinico aux = new HistorialClinico();

        try {
            aux = hcJPAC.findHistorialClinico(hc.getId_historial_clinico());
            return aux;
        } catch (Exception e) {
            return aux;
        }
    }

    /**
     * Metodo para devolver una lista de historiales clínicos de la base de
     * datos
     *
     * @return Retorna una lista de tipo List. que contiene todos los Historiales Clinicos
     */
    public List TodosHistorialClinico() {
        List<HistorialClinico> Lhc = new ArrayList<HistorialClinico>();
        try {
            Lhc = hcJPAC.findHistorialClinicoEntities();
            return Lhc;
        } catch (Exception e) {
            return Lhc;
        }
    }

    /**
     * Metodo para devolver una lista de historiales clínicos de la base de
     * datos , mediante un intervalo
     *
     * @param j Ultima posición del objeto
     * @param i Primera posición del objeto
     * @return Devuelve una lista de historiales clínicos dependiendo del intervalo ingresado
     */
    public List IntervaloHC(int j, int i) {
         List<HistorialClinico> Lhc = new ArrayList<HistorialClinico>();
        try {
            Lhc =hcJPAC.findHistorialClinicoEntities(j, i);
            return Lhc;
        } catch (Exception e) {
            return Lhc;
        }
    }

    /**
     * Metodo que cuenta los historiales clínicos existentes en la Base de Datos
     *
     * @return Devuelve la cantidad de historiales clínicos que se encuentran en el sistema
     */
    public int contadorHC() {
        int total = 0;
        try {
            total = hcJPAC.getHistorialClinicoCount();
            return total;
        } catch (Exception e) {
            return total;
        }
    }
    /**
     * 
     * @param cedula String con la que se va a comparar la cedula de una 
     * persona ingresada en el sistema, para encontrar su historial clínico
     * @return Retorna el historial clínico encontrado, caso controario retorna null
     */
    public HistorialClinico buscarHistorial(String cedula) {
        for (Object p : TodosHistorialClinico()) {
            if (personaDAO.buscarPersona(((HistorialClinico) p).getPersona()).getCedula().equals(cedula)) {
                return (HistorialClinico) p;
            }
        }
        return null;
    }

}

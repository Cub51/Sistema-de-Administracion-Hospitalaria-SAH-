package controlador.DAO;

import controlador.LaboratorioJpaController;
import java.util.List;
import modelo.Laboratorio;

public class LaboratorioDAO {

    private LaboratorioJpaController jpaControlador = new LaboratorioJpaController();
    private Laboratorio laboratorio;
    
    public Laboratorio getLaboratorio() {
        if (laboratorio == null) {
            laboratorio = new Laboratorio();
        }
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    /**
     * Método que permite agregar un laboratorio.
     * @param laboratorio Objeto que va a ser agregado al sistema.
     * @return Retorna true si se logró agregar el laboratorio, caso contrario, devuelve false.
     */
    public boolean agregar(Laboratorio laboratorio) {
        try {
            jpaControlador.create(laboratorio);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método que permite editar un laboratorio ingresado en el sistema.
     * @param laboratorio Objeto que contiene los datos actualizados del laboratorio.
     * @return Retorna true si se logró actualizar el laboratorio, caso contrario, devuelve false.
     */
    public boolean editar(Laboratorio laboratorio) {
        try {
            jpaControlador.edit(laboratorio);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método que devuelve una lista con todos los laboratorios ingresados en el sistema.
     * @return Retorna una lista de tipo List.
     */
    public List<Laboratorio> listar() {
        return jpaControlador.findLaboratorioEntities();
    }
    
    /**
     * Método que devuelve una lista con todos los laboratorios que cumplan con el estado indicado.
     * @param estado Estado por el cual se van a filtar los laboratorios, es de tipo String.
     * @return Retorna una lista de tipo List.
     */
    public List<Laboratorio> laboratoriosPorEstado(String estado) {
        return jpaControlador.getLaboratoriosPorEstado(estado);
    }
    
    /**
     * Método que devulve el total de laboratorios ingresados en el sistema.
     * @return Retorna el total de labopratorios, es de tipo int.
     */
     public int contadorLab(){
     int total = 0;
        try {
           total = jpaControlador.getLaboratorioCount();
            return total;
        } catch (Exception e) {
            return  0;
        }
    }
}

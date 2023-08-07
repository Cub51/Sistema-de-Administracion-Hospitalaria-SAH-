package controlador.DAO;

import controlador.ExamenJpaController;
import java.util.ArrayList;
import java.util.List;
import modelo.Examen;

public class ExamenDAO {

    private ExamenJpaController examJPAC = new ExamenJpaController();
    private Examen exam = new Examen();

    public Examen getExam() {
        if (exam == null) {
            exam = new Examen();
        }
        return exam;
    }

    public void setExam(Examen exam) {
        this.exam = exam;
    }

    /**
     * Metodo que crea un nuevo examen en la base de datos
     *
     * @param exam Objeto que va a ser agregado al sistema
     * @return Retorna true si se logró agregar el examen, caso contrario, devuelve false.
     */
    public boolean agregarExam(Examen exam) {
        try {
            examJPAC.create(exam);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo que edita un examen en la base de datos
     *
     * @param exam Objeto que contiene los datos actualizados del examen.
     * @return Retorna true si se logró atualizar el examen, caso contrario, devuelve false.
     */
    public boolean editarExam(Examen exam) {

        try {
            examJPAC.edit(exam);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo que elimina un examen en la base de datos
     * @param exam Objeto a eliminar 
     * @return Retorna true si se logró eliminar el examen, caso contrario, devuelve false.
     */
    public boolean eliminarExam(Examen exam) {

        try {
            examJPAC.destroy(exam.getId_examen());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo que encuentra un examen existente en la base de datos
     * @param exam  Objeto que contiene el id del examen a buscar
     * @return Retorna el examen en caso de encontrarlo.
     */
    public Examen encontrarExam(Examen exam) {
        Examen aux = new Examen();
        try {
            aux = examJPAC.findExamen(exam.getId_examen());
            return aux;
        } catch (Exception e) {
            return aux;
        }
    }

    /**
     * Metodo que lista todos los examenes de la base de datos
     * @return Retorna una lista de todos los examenes existentes en el sistema
     */
    public List TodosExam() {
        List<Examen> Ecat = new ArrayList<>();
        try {
            Ecat = examJPAC.findExamenEntities();
            return Ecat;
        } catch (Exception e) {
            return Ecat;
        }
    }

    /**
     * Metodo que lista todos los examenes de la base de datos, mediante un
     * intervalo.
     * @param j  Ultima posición del objeto
     * @param i  Primera posición del  objeto
     * @return Devuelve una lista de examenes dependiendo del intervalo ingresado
     */
    public List IntervaloExam(int j, int i) {
        List<Examen> Ecat = new ArrayList<>();
        try {
            Ecat = examJPAC.findExamenEntities(j, i);
            return Ecat;
        } catch (Exception e) {
            return Ecat;
        }
    }

    /**
     * Metodo que cuenta los examenes existentes en la base de datos
     * @return Retorna la cantidad de examenes que se encuentran en el sistema 
     */
    public int contadorExam() {
        int total = 0;
        try {
            total = examJPAC.getExamenCount();
            return total;
        } catch (Exception e) {
            return 0;
        }
    }
}

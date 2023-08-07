package controlador.DAO;

import controlador.ConsultaJpaController;
import controlador.RecetaJpaController;
import modelo.Consulta;
import modelo.Receta;

public class RecetaDAO {

    private long idConsulta;
    private RecetaJpaController recetajpa = new RecetaJpaController();
    private ConsultaJpaController jpaconsulta = new ConsultaJpaController();
    private Consulta consulta = new Consulta();
    private Receta receta = new Receta();

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public RecetaJpaController getRecetajpa() {
        return recetajpa;
    }

    public void setRecetajpa(RecetaJpaController recetajpa) {
        this.recetajpa = recetajpa;
    }

    public long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public ConsultaJpaController getJpaconsulta() {
        return jpaconsulta;
    }

    public void setJpaconsulta(ConsultaJpaController jpaconsulta) {
        this.jpaconsulta = jpaconsulta;
    }

    public Receta getReceta() {
        if (receta == null) {
            receta = new Receta();
        }
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    /**
     * Metodo para agregar una receta en la base de datos
     * @param rec objeto de receta que va a ser agregado al que ya estaba en la BD.
     * @return retorna un true si la receta fue guardado con éxito y un false si existe un error.
     */
    public boolean agregarReceta(Receta rec) {

        try {
            recetajpa.create(rec);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Permite editar una receta de la BD controlando las posibles excepciones.
     *
     * @param receta objeto de receta que va a remplazar al que ya estaba en la BD.
     * @return un true si se edito exitosamente en la BD.
     */
    public boolean editarReceta(Receta receta) {
        try {
            recetajpa.edit(receta);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

    /**
     * ¨Permite encontrar una consulta en la BD, mediante un ID.
     *
     * @param id identificador de la consulta.
     * @return Retorna Consulta si se logró encontrar la consulta, caso contrario, devuelve vacio.
     */
    public Consulta encontrarConsulta(Long id) {
        try {
            return jpaconsulta.findConsulta(idConsulta);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * ¨Permite encontrar una Receta en la BD, mediante un ID.
     *
     * @param id identificador de la receta.
     * @return retorna una receta si se logro realizar la consulta exitosamente.
     */
    public Receta buscarReceta(long id) {
        Receta aux = new Receta();
        try {
            aux = recetajpa.findReceta(id);
            return aux;
        } catch (Exception e) {
            return aux;
        }
    }

    /**
     * Permite cargar una consulta dentro del objeto de RecetaDAO
     */
    public void CargarConsulta() {
        this.setConsulta(jpaconsulta.findConsulta(idConsulta));
    }

}

package controlador.DAO;

import controlador.ConsultaJpaController;
import controlador.exceptions.IllegalOrphanException;
import java.util.ArrayList;
import java.util.List;
import modelo.Consulta;

public class ConsultaDAO {

    private ConsultaJpaController ConsultaJpa = new ConsultaJpaController();
    private Consulta consulta;

    public Consulta getConsulta() {
        if (consulta == null) {
            consulta = new Consulta();
        }
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    /**
     * Permite agregar nuevas consultas a la BD sin preocuparnos por errores en
     * el sistema.
     *
     * @param consulta el objeto Consulta ha ser guardado en la BD
     * @return retorna un true si la Consulta fué guardado con exito y un false
     * si existe un error.
     */
    public boolean agregarConsulta(Consulta consulta) {
        try {
            ConsultaJpa.create(consulta);
            return true;
        } catch (IllegalOrphanException e) {
            return false;
        }
    }

    /**
     * Devuelveen una lista todos las consultas guardadas en la BD.
     *
     * @return una lista con todas las consultas en la BD.
     */
    public List listarConsultas() {
        List<Consulta> listaPersona = new ArrayList<Consulta>();
        try {
            listaPersona = ConsultaJpa.findConsultaEntities();
            return listaPersona;
        } catch (Exception e) {
            return listaPersona;
        }
    }

    /**
     * ¨Permite encontrar una consulta en la BD, mediante un ID.
     *
     * @param id identificador de la consulta.
     * @return Retorna Consulta si se logró encontrar la consulta, caso
     * contrario, devuelve vacio.
     */
    public Consulta encontrarConsulta(Long id) {
        try {
            return ConsultaJpa.findConsulta(id);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Devuelve una lista de consultas filtrando por el estado deseado.
     *
     * @param estado Estado de la consulta a buscar.
     * @return una lista con todos los campos que tengan el estado seleccionado.
     */
    public List<Consulta> getConsultasPorEstado(String estado) {
        return ConsultaJpa.getConsultasPorEstado(estado);
    }

    /**
     * Devuelve una lista de consultas filtrando por el estado que no se desea
     * mostrar.
     *
     * @param estado Estado que no se quiere consultar en la BD.
     * @return una lista de consutlas excepto las que tengas el estado
     * seleccionado.
     */
    public List<Consulta> getPedidosPorTodosMenosUnEstado(String estado) {
        return ConsultaJpa.getPedidosPorTodosMenosUnEstado(estado);
    }

    /**
     * Permite editar una consulta en la BD.
     *
     * @param consulta Objeto de Consulta que remplazara al que esta guardado en
     * la BD.
     * @return un true si el objeto se edito correctamente y un false si se
     * produjo un error.
     */
    public boolean editarConsulta(Consulta consulta) {
        try {
            ConsultaJpa.edit(consulta);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e);
            return false;
        }
    }

}

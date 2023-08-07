package controlador.DAO;

import controlador.ConsultaJpaController;
import controlador.PedidoJpaController;
import java.util.ArrayList;
import java.util.List;
import modelo.Consulta;
import modelo.Examen;
import modelo.Pedido;

public class PedidoDAO {

    private long idConsulta;
    private PedidoJpaController jpaControlador = new PedidoJpaController();
    private Pedido pedido = new Pedido();
    private ConsultaJpaController jpaconsulta = new ConsultaJpaController();
    private Consulta consulta = new Consulta();
    private ExamenDAO examendao = new ExamenDAO();
    private Examen examen = new Examen();
    private List<Examen> listaExamen = new ArrayList<Examen>();

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public PedidoJpaController getJpaControlador() {
        return jpaControlador;
    }

    public void setJpaControlador(PedidoJpaController jpaControlador) {
        this.jpaControlador = jpaControlador;
    }

    public ConsultaJpaController getJpaconsulta() {
        return jpaconsulta;
    }

    public List<Examen> getListaExamen() {
        return listaExamen;
    }

    public void setListaExamen(List<Examen> listaExamen) {
        this.listaExamen = listaExamen;
    }

    public void setJpaconsulta(ConsultaJpaController jpaconsulta) {
        this.jpaconsulta = jpaconsulta;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public ExamenDAO getExamendao() {
        return examendao;
    }

    public void setExamendao(ExamenDAO examendao) {
        this.examendao = examendao;
    }

    public Examen getExamen() {
        return examen;
    }

    public long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public void setExamen(Examen examen) {
        this.examen = examen;
    }

    /**
     * Método que permite agregar un pedido.
     * @param pedido Objeto que va a ser agregado al sistema.
     * @return Retorna true si se logró agregar el pedido, caso contrario, devuelve false.
     */
    public boolean agregar(Pedido pedido) {
        try {
            jpaControlador.create(pedido);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método que permite editar un pedido ingresado en el sistema.
     * @param pedido Objeto que contiene los datos actualizados del pedido.
     * @return Retorna true si se logró actualizar el pedido, caso contrario, devuelve false.
     */
    public boolean editar(Pedido pedido) {
        try {
            jpaControlador.edit(pedido);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Método que devuelve una lista con todos los pedidos ingresados en el sistema.
     * @return Retorna una lista de tipo List.
     */
    public List<Pedido> listar() {
        return jpaControlador.findPedidoEntities();
    }

    /**
     * Método que devuelve una lista con todos los pedidos que cumplan con el estado indicado.
     * @param estado Estado por el cual se van a filtar los pedidos, es de tipo String.
     * @return Retorna una lista de tipo List.
     */
    public List<Pedido> getPedidosPorEstado(String estado) {
        return jpaControlador.getPedidosPorEstado(estado);
    }

    /**
     * Método que devulve un objeto de tipo Consulta según el id indicado. 
     * @param id Id por el cual se va a buscar el objeto de tipo Consulta.
     * @return Retorna un objeto de tipo Consulta.
     */
    public Consulta encontrarConsulta(Long id) {
        try {
            return jpaconsulta.findConsulta(idConsulta);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Método que devuelve una lista con todos los pedidos que no cumplan con el estado indicado.
     * @param estado Estado por el cual se van a filtar los pedidos, es de tipo String.
     * @return Retorna una lista de tipo List.
     */
    public List<Pedido> getPedidosPorTodosMenosUnEstado(String estado) {
        return jpaControlador.getPedidosPorTodosMenosUnEstado(estado);
    }

}

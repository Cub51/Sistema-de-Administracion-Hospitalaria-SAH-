package modelo.tabla;

import controlador.DAO.MedicoDAO;
import controlador.DAO.PersonaDAO;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Medico;
import modelo.Pedido;
import modelo.Persona;

public class PedidosTabla extends AbstractTableModel {

    private List<Pedido> listaPedidos;

    public List<Pedido> getListaPedidos() {
        return listaPedidos;
    }

    public void setListaPedidos(List<Pedido> listaPedidos) {
        this.listaPedidos = listaPedidos;
    }

    @Override
    public int getRowCount() {
        return listaPedidos.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columna) {
        switch (columna) {
            case 0:
                return "Nro Pedido";
            case 1:
                return "Médico Solicitante";
            case 2:
                return "Paciente";
            case 3:
                return "Estado";
            default:
                return "Sin Asignar";
        }
    }

    @Override
    public Object getValueAt(int fila, int columna) {

        Pedido pedido = listaPedidos.get(fila);

        switch (columna) {
            case 0:
                if (pedido.getNro_pedido() == null) {
                    return "Sin Asignar";
                } else {
                    return pedido.getNro_pedido();
                }
            case 1:
                
                if (pedido.getConsulta().getId_medico() == null) {
                    return "Sin Asignar";
                } else {
                    Medico medico = new MedicoDAO().buscarMedicoid(pedido.getConsulta().getId_medico());
                    return medico.toString();
                }
            case 2:
                if (pedido.getConsulta().getId_paciente() == null) {
                    return "Sin Asignar";
                } else {
                    Persona persona = new PersonaDAO().buscarPersonaPorId(pedido.getConsulta().getId_paciente());
                    return persona.toString();
                }
            case 3:
                if (pedido.getEstado_pedido()== null) {
                    return "Sin Asignar";
                } else {
                    return pedido.getEstado_pedido();
                }
            default:
                return "Sin asignar";
        }
    }

}

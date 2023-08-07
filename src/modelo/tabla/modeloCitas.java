/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.tabla;

import controlador.DAO.PersonaDAO;
import controlador.PersonaJpaController;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Consulta;
import modelo.Medico;
import modelo.Persona;

/**
 *
 * @author CNH
 */
public class modeloCitas extends AbstractTableModel {

    private List<Consulta> listaCitas;
    private PersonaDAO personadao = new PersonaDAO();

    public List<Consulta> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<Consulta> listaCitas) {
        this.listaCitas = listaCitas;
    }

    @Override
    public int getRowCount() {
        return listaCitas.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "id";
            case 1:
                return "Fecha";
            case 2:
                return "Estado";
            case 3:
                return "Hora";
            case 4:
                return "Paciente";
            default:
                return "--";
        }
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        Consulta consulta = listaCitas.get(fila);
        switch (columna) {
            case 0:
                return consulta.getId_consulta().toString();
            case 1:
                return consulta.getFecha_cita();
            case 2:
                return consulta.getEstado_consulta();
            case 3:
                return consulta.getHora_cita();
            case 4:
                Persona per = personadao.buscarPersonaPorId(consulta.getId_paciente());
                return per.getNombre() + " " + per.getApellido();
            default:
                return "--";
        }
    }
}

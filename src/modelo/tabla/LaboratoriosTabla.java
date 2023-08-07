package modelo.tabla;

import controlador.DAO.PersonaDAO;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Laboratorio;

public class LaboratoriosTabla extends AbstractTableModel {

    private PersonaDAO personaDAO = new PersonaDAO();
    private List<Laboratorio> listaLaboratorios;

    public List<Laboratorio> getListaLaboratorios() {
        return listaLaboratorios;
    }

    public void setListaLaboratorios(List<Laboratorio> listaLaboratorios) {
        this.listaLaboratorios = listaLaboratorios;
    }

    @Override
    public int getRowCount() {
        return listaLaboratorios.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columna) {
        switch (columna) {
            case 0:
                return "Encargado";
            case 1:
                return "Nombre";
            case 2:
                return "Descripción";
            default:
                return "Sin Asignar";
        }
    }

    @Override
    public Object getValueAt(int fila, int columna) {

        Laboratorio laboratorio = listaLaboratorios.get(fila);

        switch (columna) {
            case 0:
                if (laboratorio.getId_encargado() == null) {
                    return "Sin Asignar";
                } else {
                    personaDAO.setPersona(personaDAO.buscarPersonaPorId(laboratorio.getId_encargado()));
                    String nombreCompleto = personaDAO.getPersona().getNombre();
                    nombreCompleto += personaDAO.getPersona().getApellido();
                    return nombreCompleto;
                }
            case 1:
                if (laboratorio.getNombre_lab() == null) {
                    return "Sin Asignar";
                } else {
                    return laboratorio.getNombre_lab();
                }
            case 2:
                if (laboratorio.getDescripcion_lab() == null) {
                    return "Sin Asignar";
                } else {
                    return laboratorio.getDescripcion_lab();
                }
            default:
                return "Sin asignar";
        }
    }

}

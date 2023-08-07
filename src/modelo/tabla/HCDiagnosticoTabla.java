/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.tabla;

import controlador.DAO.PersonaDAO;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Diagnostico;


/**
 *
 * @author Jean Agreda
 */
public class HCDiagnosticoTabla extends AbstractTableModel{
PersonaDAO personaDAO = new PersonaDAO();
    private List<Diagnostico> listaDiagnosticos;

    public List<Diagnostico> getListaDiagnosticos() {
        return listaDiagnosticos;
    }

    public void setListaDiagnosticos(List<Diagnostico> listaDiagnosticos) {
        this.listaDiagnosticos = listaDiagnosticos;
    }
    
    
    @Override
    public int getRowCount() {
      return listaDiagnosticos.size();
    }

    @Override
    public int getColumnCount() {
      return 2; 
    }

    @Override
    public String getColumnName(int columna) {
        switch (columna) {
            case 0:
                return "Medico";
            case 1:
                return "Enfermedad";
            default:
                return "Sin Asignar";
        }
    }

    
    @Override
    public Object getValueAt(int fila, int columna) {
        Diagnostico diagnostico = listaDiagnosticos.get(fila);

       switch (columna) {
            case 0:
                if (diagnostico.getConsulta().getId_medico()== null) {
                    return "Sin Asignar";
                } else {
                    
                    personaDAO.setPersona(personaDAO.buscarPersonaPorId( diagnostico.getConsulta().getId_medico()  ));
                    String nombreCompleto = personaDAO.getPersona().getNombre();
                    nombreCompleto += " "+personaDAO.getPersona().getApellido();
                    return nombreCompleto;
                }
            case 1:
                if (diagnostico.getEnfermedad()== null) {
                    return "Sin Asignar";
                } else {
                    return diagnostico.getEnfermedad();
                }
            default:
                return "Sin asignar";
        }
    }
    
}

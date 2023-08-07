/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.tabla;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Cuenta;
import modelo.Medico;
import modelo.Persona;

/**
 *
 * @author RICARDO
 */
public class ModeloTablaPersonalMedico extends AbstractTableModel{
     private List<Medico> listaMedico;
     //private List<Cuenta> listaCuenta;

    public List<Medico> getListaMedico() {
        return listaMedico;
    }

    public void setListaMedico(List<Medico> listaMedico) {
        this.listaMedico = listaMedico;
    }
  

    @Override
    public int getRowCount() {
        return listaMedico.size();
    }

    @Override
    public int getColumnCount() {
        return 12;
    }

    @Override
    public String getColumnName(int columna) {
        switch (columna) {
            case 0:
                return "Cédula";
            case 1:
                return "Nombre";
            case 2:
                return "Apellido";
            case 3:
                return "Correo";
            case 4:
                return "Teléfono";
            case 5:
                return "Tlf. Auxiliar";
            case 6:
                return "Dirección";
            case 7:
                return "Género";
            case 8:
                return "Estado Civil";
            case 9:
                return "Fecha Nacimiento";
            case 10:
                return "Rol";
            case 11:
                return "Especialidad";
            case 12:
                return "Estado";
            default:
                return "";
        }
    }

    @Override
    public Object getValueAt(int fila, int columna) {

        Medico medico = listaMedico.get(fila);

        switch (columna) {
            case 0:
                if (medico.getCedula() == null) {
                    return "Sin Asignar";
                } else {
                    return medico.getCedula();
                }
            case 1:
                if (medico.getNombre() == null) {
                    return "Sin Asignar";
                } else {
                    return medico.getNombre();
                }
            case 2:
                if (medico.getApellido() == null) {
                    return "Sin Asignar";
                } else {
                    return medico.getApellido();
                }
            case 3:
                if (medico.getCorreo() == null) {
                    return "Sin Asignar";
                } else {
                    return medico.getCorreo();
                }
            case 4:
                if (medico.getCelular() == null) {
                    return "Sin Asignar";
                } else {
                    return medico.getCelular();
                }
            case 5:
                if (medico.getContacto_auxiliar() == null) {
                    return "Sin Asignar";
                } else {
                    return medico.getContacto_auxiliar();
                }
            case 6:
                if (medico.getDireccion() == null) {
                    return "Sin Asignar";
                } else {
                    return medico.getDireccion();
                }
            case 7:
                if (medico.getGenero() == null) {
                    return "Sin Asignar";
                } else {
                    return medico.getGenero();
                }
            case 8:
                if (medico.getEstado_civil() == null) {
                    return "Sin Asignar";
                } else {
                    return medico.getEstado_civil();
                }
            case 9:
                if (medico.getFecha_nacimiento() == null) {
                    return "Sin Asignar";
                } else {
                    return medico.getFecha_nacimiento();
                }
            case 10:
                if (medico.getRol().getNombre_rol() == null) {
                    return "Sin Asignar";
                } else {
                    return medico.getRol().getNombre_rol();
                }
            case 11:
                if (medico.getEspecialidad() == null) {
                    return "Sin Asignar";
                } else {
                    return medico.getEspecialidad();
                }
            case 12:
                if (medico.getEstado() == null) {
                    return "Sin Asignar";
                } else {
                    return medico.getEstado();
                }
            default:
                return "Sin asignar";
        }
    }
}

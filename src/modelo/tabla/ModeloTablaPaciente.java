/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.tabla;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Persona;

/**
 *
 * @author RICARDO
 */
public class ModeloTablaPaciente extends AbstractTableModel{
     private List<Persona> listaPersona;

    public List<Persona> getListaPersona() {
        return listaPersona;
    }

    public void setListaPersona(List<Persona> listaPersona) {
        this.listaPersona = listaPersona;
    }

    @Override
    public int getRowCount() {
        return listaPersona.size();
    }

    @Override
    public int getColumnCount() {
        return 11;
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
                return "Estado";
            default:
                return "";
        }
    }

    @Override
    public Object getValueAt(int fila, int columna) {

        Persona persona = listaPersona.get(fila);

        switch (columna) {
            case 0:
                if (persona.getCedula() == null) {
                    return "Sin Asignar";
                } else {
                    return persona.getCedula();
                }
            case 1:
                if (persona.getNombre() == null) {
                    return "Sin Asignar";
                } else {
                    return persona.getNombre();
                }
            case 2:
                if (persona.getApellido() == null) {
                    return "Sin Asignar";
                } else {
                    return persona.getApellido();
                }
            case 3:
                if (persona.getCorreo() == null) {
                    return "Sin Asignar";
                } else {
                    return persona.getCorreo();
                }
            case 4:
                if (persona.getCelular() == null) {
                    return "Sin Asignar";
                } else {
                    return persona.getCelular();
                }
            case 5:
                if (persona.getContacto_auxiliar() == null) {
                    return "Sin Asignar";
                } else {
                    return persona.getContacto_auxiliar();
                }
            case 6:
                if (persona.getDireccion() == null) {
                    return "Sin Asignar";
                } else {
                    return persona.getDireccion();
                }
            case 7:
                if (persona.getGenero() == null) {
                    return "Sin Asignar";
                } else {
                    return persona.getGenero();
                }
            case 8:
                if (persona.getEstado_civil() == null) {
                    return "Sin Asignar";
                } else {
                    return persona.getEstado_civil();
                }
            case 9:
                if (persona.getFecha_nacimiento() == null) {
                    return "Sin Asignar";
                } else {
                    return persona.getFecha_nacimiento();
                }
            case 10:
                if (persona.getRol().getNombre_rol() == null) {
                    return "Sin Asignar";
                } else {
                    return persona.getRol().getNombre_rol();
                }
            case 11:
                if (persona.getEstado() == null) {
                    return "Sin Asignar";
                } else {
                    return persona.getEstado();
                }
            default:
                return "Sin asignar";
        }
    }
}

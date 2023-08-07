package vista.utilidades;

import java.util.List;
import javax.swing.JComboBox;
import modelo.Medico;

public class UtilidadesVista {

    /**
     * Método que permite cargar objetos de tipo Medico en un combobox.
     * @param cbx Combobox en el que se va a cargar los objetos de tipo Medico.
     * @param medicos Lista de objetos de tipo Medico que se van a cargar.
     */
    public static void cargarCbxMedicos(JComboBox cbx, List<Medico> medicos) {
        cbx.removeAllItems();
        for (Medico medico : medicos) {
            cbx.addItem(medico);
        }
    }

    /**
     * Método que permite cargar objetos en un combobox.
     * @param cbx Combobox en el que se va a cargar los objetos de la lista.
     * @param objetos Lista de objetos que se van a cargar .
     */
    public static void cargarCbx(JComboBox cbx, List<Object> objetos) {
        cbx.removeAllItems();
        for (Object objeto : objetos) {
            cbx.addItem(objeto);
        }
    }
}

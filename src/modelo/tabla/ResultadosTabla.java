package modelo.tabla;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Examen;

public class ResultadosTabla extends AbstractTableModel {

    private List<Examen> listaExamenes;

    public List<Examen> getListaExamenes() {
        return listaExamenes;
    }

    public void setListaExamenes(List<Examen> listaExamenes) {
        this.listaExamenes = listaExamenes;
    }

    @Override
    public int getRowCount() {
        return listaExamenes.size();
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public String getColumnName(int columna) {
        switch (columna) {
            case 0:
                return "Exámen";
            case 1:
                return "Resultado";
            case 2:
                return "Unidad de Medida";
            default:
                return "Sin Asignar";
        }
    }

    @Override
    public Object getValueAt(int fila, int columna) {

        Examen examen = listaExamenes.get(fila);

        switch (columna) {
            case 0:
                if (examen.getNombre() == null) {
                    return "Sin Asignar";
                } else {
                    return examen.getNombre();
                }
            case 1:
                return "";
            case 2:
                if (examen.getUnidad_medida() == null) {
                    return "Sin Asignar";
                } else {
                    return examen.getUnidad_medida();
                }
            default:
                return "Sin asignar";
        }
    }

}


package modelo.tabla;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.HistorialClinico;


public class HistorialClinicoTabla extends AbstractTableModel{
    
    private List<HistorialClinico> listaHC;

    public List<HistorialClinico> getListaHC() {
        return listaHC;
    }

    public void setListaHC(List<HistorialClinico> listaHC) {
        this.listaHC = listaHC;
    }
    
    @Override
    public int getRowCount() {
       return listaHC.size();
    }

    @Override
    public int getColumnCount() {
       return  4;
    }

    @Override
    public String getColumnName(int column) {
         switch (column) {
            case 0:
                return "Tipo de Sangre";
            case 1:
                return "Parentezco";
             case 2:
                return "Enfermedad Hereditaria";
             case 3:
                return "Habito";
            default:
                return "Sin Asignar";
           
        }
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        HistorialClinico hc = listaHC.get(fila);
        
         switch (columna) {
            case 0:
                if (hc.getTipo_sangre() == null) {
                    return "Sin Asignar";
                }else{ 
                    return hc.getTipo_sangre();
                }
            case 1:
                if (hc.getEnfermedad_hereditaria()== null) {
                    return "Sin Asignar";
                }else{ 
                    //madre
                    //padre
                    return hc.getEnfermedad_hereditaria().substring(0, 5);
                }
             case 2:
                if (hc.getEnfermedad_hereditaria()== null || hc.getEnfermedad_hereditaria().length() < 6) {
                    return "Sin Asignar";
                }else{ 
                    return hc.getEnfermedad_hereditaria().substring(6);
                }
             case 3:
                if (hc.getHabitos()== null) {
                    return "Sin Asignar";
                }else{ 
                    return hc.getHabitos();
                }
            default:
                return "Sin Asignar";
        }
    }
    
}

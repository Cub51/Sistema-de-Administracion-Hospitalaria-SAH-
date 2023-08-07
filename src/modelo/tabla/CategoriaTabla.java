/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.tabla;

import controlador.DAO.CategoriaDAO;
import javax.swing.table.AbstractTableModel;
import modelo.Categoria;

/**
 *
 * @author Jean Agreda
 */
public class CategoriaTabla extends AbstractTableModel{
    CategoriaDAO catDAO = new CategoriaDAO();

    public CategoriaDAO getCatDAO() {
        return catDAO;
    }

    public void setCatDAO(CategoriaDAO catDAO) {
        this.catDAO = catDAO;
    }
    
    @Override
    public int getRowCount() {
       return catDAO.contadorCat();
    }

    @Override
    public int getColumnCount() {
       return 2;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Nombre";
            case 1:
                return "Descripción";
            default:
                return "--";
           
        }
        
    }
    
    @Override
    public Object getValueAt(int fila, int columna) {
   
        Categoria cat = (Categoria)catDAO.TodasCat().get(fila);
       
        switch (columna) {
            case 0:
                return cat.getNombre_cat();
            case 1:
              return cat.getDescripcion_cat();
              
            default:
                return "--";
        }
    }
    
}

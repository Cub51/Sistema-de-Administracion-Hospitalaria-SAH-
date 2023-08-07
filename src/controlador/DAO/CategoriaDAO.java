package controlador.DAO;

import controlador.CategoriaJpaController;
import java.util.ArrayList;
import java.util.List;
import modelo.Categoria;

public class CategoriaDAO {

    private CategoriaJpaController CatJPAC = new CategoriaJpaController();
    private Categoria cat = new Categoria();

    public Categoria getCat() {
        if (cat == null) {
            cat = new Categoria();
        }
        return cat;
    }

    public void setCat(Categoria cat) {
        this.cat = cat;
    }

    /**
     * Metodo para agregar una categoria en la base de datos
     * @param cat Objeto que va a ser agregado al sistema
     * @return Retorna true si se logró agregar la categoria, caso contrario, devuelve false.
     */
    public boolean agregarCat(Categoria cat) {

        try {
            CatJPAC.create(cat);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo para editar una categoria en la base de datos
     * @param cat Objeto que contiene los datos actualizados de la categoria.
     * @return Retorna true si se logró actualizar la categoria, caso contrario, devuelve false.
     */
    public boolean editarCat(Categoria cat) {

        try {
            CatJPAC.edit(cat);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * Metodo para eliminar una categoria en la base de datos
     * @param cat Objeto a eliminar 
     * @return Retorna true si se logró eliminar la categoria, caso contrario, devuelve false.
     */
    public boolean eliminarCat(Categoria cat) {

        try {
            CatJPAC.destroy(cat.getId_categoria());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo para encontrar una categoria existente en la base de datos
     * @param cat Objeto que contiene el id de la categoria a buscar
     * @return Retorna la categoria en caso de encontrarlo.
     */
    public Categoria encontrarCat(Categoria cat) {
        Categoria aux = new Categoria();
        try {
            aux = CatJPAC.findCategoria(cat.getId_categoria());
            return aux;
        } catch (Exception e) {
            return aux;
        }
    }

    /**
     * Metodo que lista todas las categorias en la base de datos
     * @return  Retorna una lista de todas las categorias existentes en el sistema
     */
    public List TodasCat() {
        List<Categoria> Lcat = new ArrayList<Categoria>();
        try {
            Lcat = CatJPAC.findCategoriaEntities();
            return Lcat;
        } catch (Exception e) {
            return Lcat;
        }
    }

    /**
     * Metodo que lista todas las categorias en la base de datos, mediante
     * un intervalo
     * @param j Ultima posición del objeto
     * @param i Primera posición del objeto
     * @return Devuelve una lista de categorias dependiendo del intervalo ingresado
     */
    public boolean IntervaloCat(int j, int i) {

        try {
            CatJPAC.findCategoriaEntities(j, i);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Metodo que cuenta todas las categorias existentes en la base de datos
     * @return Retorna la cantidad de categorias que se encuentran en el sistema 
     */
    public int contadorCat() {
        int total = 0;
        try {
            total = CatJPAC.getCategoriaCount();
            return total;
        } catch (Exception e) {
            return 0;
        }
    }
}

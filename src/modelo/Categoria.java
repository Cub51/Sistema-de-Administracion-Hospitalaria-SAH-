
package modelo;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "categoria")
public class Categoria implements Serializable {

    private static final long  serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_categoria;
    private String nombre_cat;
    private String descripcion_cat;
    
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Examen>listaExamen = new ArrayList<Examen>();

    public String getNombre_cat() {
        return nombre_cat;
    }

    public void setNombre_cat(String nombre_cat) {
        this.nombre_cat = nombre_cat;
    }

    public String getDescripcion_cat() {
        return descripcion_cat;
    }

    public void setDescripcion_cat(String descripcion_cat) {
        this.descripcion_cat = descripcion_cat;
    }

    public List<Examen> getListaExamen() {
        return listaExamen;
    }

    public void setListaExamen(List<Examen> listaExamen) {
        this.listaExamen = listaExamen;
    }
    
    
    public Long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Long id_categoria) {
        this.id_categoria = id_categoria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_categoria != null ? id_categoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id_categoria fields are not set
        if (!(object instanceof Categoria)) {
            return false;
        }
        Categoria other = (Categoria) object;
        if ((this.id_categoria == null && other.id_categoria != null) || (this.id_categoria != null && !this.id_categoria.equals(other.id_categoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre_cat;
    }
    
}

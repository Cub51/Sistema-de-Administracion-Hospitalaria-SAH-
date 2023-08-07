
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
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "historial_clinico")
public class HistorialClinico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_historial_clinico;
    private String enfermedad_hereditaria;
    private String tipo_sangre;
    private String habitos;
    
    @OneToMany(mappedBy="historial_clinico",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Consulta>ListarConsulta=new ArrayList<Consulta>();
    
    @OneToOne(cascade = CascadeType.REFRESH,fetch = FetchType.LAZY)
    @JoinColumn(name="id_persona", nullable = false,referencedColumnName = "id_persona")
    private Persona persona;

    public List<Consulta> getListarConsulta() {
        return ListarConsulta;
    }

    public void setListarConsulta(List<Consulta> ListarConsulta) {
        this.ListarConsulta = ListarConsulta;
    }
    
    public String getEnfermedad_hereditaria() {
        return enfermedad_hereditaria;
    }

    public void setEnfermedad_hereditaria(String enfermedad_hereditaria) {
        this.enfermedad_hereditaria = enfermedad_hereditaria;
    }

    public String getTipo_sangre() {
        return tipo_sangre;
    }

    public void setTipo_sangre(String tipo_sangre) {
        this.tipo_sangre = tipo_sangre;
    }

    public String getHabitos() {
        return habitos;
    }

    public void setHabitos(String habitos) {
        this.habitos = habitos;
    }

//    public String getObservacion() {
//        return observacion;
//    }
//
//    public void setObservacion(String observacion) {
//        this.observacion = observacion;
//    }

    public Long getId_historial_clinico() {
        return id_historial_clinico;
    }

    public void setId_historial_clinico(Long id_historial_clinico) {
        this.id_historial_clinico = id_historial_clinico;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_historial_clinico != null ? id_historial_clinico.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id_historial_clinico fields are not set
        if (!(object instanceof HistorialClinico)) {
            return false;
        }
        HistorialClinico other = (HistorialClinico) object;
        if ((this.id_historial_clinico == null && other.id_historial_clinico != null) || (this.id_historial_clinico != null && !this.id_historial_clinico.equals(other.id_historial_clinico))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.HistorialClinico[ id=" + id_historial_clinico + " ]";
    }

}

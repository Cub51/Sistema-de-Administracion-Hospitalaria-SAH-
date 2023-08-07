package controlador.DAO;

import controlador.ConsultaJpaController;
import controlador.DiagnosticoJpaController;
import java.util.List;
import modelo.Consulta;
import modelo.Diagnostico;
import modelo.Medico;

public class DiagnosticoDAO {

    private long idConsulta;
    private ConsultaJpaController jpaconsulta = new ConsultaJpaController();
    private DiagnosticoJpaController jpadiagnostico = new DiagnosticoJpaController();
    private Consulta consulta = new Consulta();
    private Diagnostico diagnostico = new Diagnostico();
    private PersonaDAO pesonadao = new PersonaDAO();
    private MedicoDAO medicodao = new MedicoDAO();
    private Medico medico = new Medico();

    public DiagnosticoJpaController getJpadiagnostico() {
        return jpadiagnostico;
    }

    public void setJpadiagnostico(DiagnosticoJpaController jpadiagnostico) {
        this.jpadiagnostico = jpadiagnostico;
    }

    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public ConsultaJpaController getJpaconsulta() {
        return jpaconsulta;
    }

    public void setJpaconsulta(ConsultaJpaController jpaconsulta) {
        this.jpaconsulta = jpaconsulta;
    }

    public Diagnostico getDiagnostico() {
        if (diagnostico == null) {
            diagnostico = new Diagnostico();
        }
        return diagnostico;
    }

    public void setDiagnostico(Diagnostico diagnostico) {
        this.diagnostico = diagnostico;
    }

    public PersonaDAO getPesonadao() {
        return pesonadao;
    }

    public void setPesonadao(PersonaDAO pesonadao) {
        this.pesonadao = pesonadao;
    }

    public MedicoDAO getMedicodao() {
        return medicodao;
    }

    public void setMedicodao(MedicoDAO medicodao) {
        this.medicodao = medicodao;
    }

    public Medico getMedico() {
        if (medico == null) {
            medico = new Medico();
        }
        return medico;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }

    /**
     * Permite agregar nuevos diagnosticos a la BD, sin preocuparnos por errores en
     * el sistema.     
     *
     * @param diag el objeto diagnostico ha ser guardado en la BD 
     * @return retorna un true si el Diagnostico fué guardado con exito y un false si existe un error.
     */
    public boolean agregarDiagnostico(Diagnostico diag) {
        try {
            this.jpadiagnostico.create(diag);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Permite cargar una consulta dentro del objeto de el DiagnosticoDAO
     */
    public void CargarConsulta() {
        this.setConsulta(jpaconsulta.findConsulta(idConsulta));
    }

    /**
     * ¨Permite encontrar una consulta en la BD, mediante un ID.
     *
     * @param id identificador de la consulta.
     * @return Retorna Consulta si se logró encontrar la consulta, caso contrario, devuelve vacio.
     */
    public Consulta encontrarConsulta(Long id) {
        try {
            return jpaconsulta.findConsulta(idConsulta);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Devuelveen una lista todos los Diagnosticos guardadas en la BD.
     *
     * @param id_persona identificador de la persona.
     * @return retorna una lista de de diagnosticos.
     */
    public List<Diagnostico> diagnosticoPorPersona(Long id_persona) {
        return jpadiagnostico.getDiagnosticoPorPersona(id_persona);
    }

}

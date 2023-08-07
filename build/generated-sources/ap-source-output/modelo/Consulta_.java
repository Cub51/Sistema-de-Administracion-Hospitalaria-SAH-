package modelo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Diagnostico;
import modelo.HistorialClinico;
import modelo.Pedido;
import modelo.Receta;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-22T13:58:46", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Consulta.class)
public class Consulta_ { 

    public static volatile SingularAttribute<Consulta, Long> id_consulta;
    public static volatile SingularAttribute<Consulta, String> estado_consulta;
    public static volatile SingularAttribute<Consulta, Receta> receta;
    public static volatile SingularAttribute<Consulta, Diagnostico> diagnostico;
    public static volatile SingularAttribute<Consulta, HistorialClinico> historial_clinico;
    public static volatile SingularAttribute<Consulta, Long> id_medico;
    public static volatile SingularAttribute<Consulta, Pedido> pedido;
    public static volatile SingularAttribute<Consulta, String> hora_cita;
    public static volatile SingularAttribute<Consulta, String> fecha_cita;
    public static volatile SingularAttribute<Consulta, Long> id_paciente;

}
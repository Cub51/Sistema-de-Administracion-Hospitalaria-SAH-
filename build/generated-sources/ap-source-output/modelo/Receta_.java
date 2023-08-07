package modelo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Consulta;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-22T13:58:46", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Receta.class)
public class Receta_ { 

    public static volatile SingularAttribute<Receta, String> fecha;
    public static volatile SingularAttribute<Receta, String> indicaciones;
    public static volatile SingularAttribute<Receta, Long> id_receta;
    public static volatile SingularAttribute<Receta, Consulta> consulta;

}
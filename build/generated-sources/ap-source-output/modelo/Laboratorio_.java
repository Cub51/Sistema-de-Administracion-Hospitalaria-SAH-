package modelo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Examen;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-22T13:58:46", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Laboratorio.class)
public class Laboratorio_ { 

    public static volatile SingularAttribute<Laboratorio, Long> id_laboratorio;
    public static volatile SingularAttribute<Laboratorio, String> estado;
    public static volatile SingularAttribute<Laboratorio, String> nombre_lab;
    public static volatile SingularAttribute<Laboratorio, Long> id_encargado;
    public static volatile SingularAttribute<Laboratorio, String> descripcion_lab;
    public static volatile SingularAttribute<Laboratorio, Examen> examen;

}
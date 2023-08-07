package modelo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Categoria;
import modelo.Laboratorio;
import modelo.Pedido;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-22T13:58:46", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Examen.class)
public class Examen_ { 

    public static volatile SingularAttribute<Examen, String> unidad_medida;
    public static volatile SingularAttribute<Examen, Long> id_examen;
    public static volatile SingularAttribute<Examen, Categoria> categoria;
    public static volatile SingularAttribute<Examen, Pedido> pedido;
    public static volatile SingularAttribute<Examen, Laboratorio> laboratorio;
    public static volatile SingularAttribute<Examen, String> nombre;

}
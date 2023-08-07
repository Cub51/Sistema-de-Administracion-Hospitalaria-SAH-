package modelo;

import javax.annotation.processing.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Consulta;
import modelo.Examen;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2023-07-22T13:58:46", comments="EclipseLink-2.7.10.v20211216-rNA")
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile SingularAttribute<Pedido, String> estado_pedido;
    public static volatile SingularAttribute<Pedido, String> fecha_pedido;
    public static volatile SingularAttribute<Pedido, Long> id_pedido;
    public static volatile SingularAttribute<Pedido, Consulta> consulta;
    public static volatile SingularAttribute<Pedido, String> nro_pedido;
    public static volatile ListAttribute<Pedido, Examen> listaExamen;

}
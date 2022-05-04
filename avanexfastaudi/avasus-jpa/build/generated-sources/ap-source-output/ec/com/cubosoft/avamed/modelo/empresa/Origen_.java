package ec.com.cubosoft.avamed.modelo.empresa;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Origen.class)
public class Origen_ { 

    public static volatile SingularAttribute<Origen, String> descripcion;
    public static volatile SingularAttribute<Origen, String> lastUser;
    public static volatile SingularAttribute<Origen, String> firstUser;
    public static volatile SingularAttribute<Origen, Short> lockReg;
    public static volatile SingularAttribute<Origen, Integer> id;
    public static volatile SingularAttribute<Origen, Date> fecIni;
    public static volatile ListAttribute<Origen, Orden> orden;
    public static volatile SingularAttribute<Origen, Date> fecUpd;

}
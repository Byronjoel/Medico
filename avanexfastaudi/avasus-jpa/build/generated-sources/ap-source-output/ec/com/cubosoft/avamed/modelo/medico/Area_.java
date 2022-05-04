package ec.com.cubosoft.avamed.modelo.medico;

import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import ec.com.cubosoft.avamed.modelo.practica.NombreP;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Area.class)
public class Area_ { 

    public static volatile SingularAttribute<Area, String> descripcion;
    public static volatile SingularAttribute<Area, String> lastUser;
    public static volatile SingularAttribute<Area, String> perArea;
    public static volatile SingularAttribute<Area, String> firstUser;
    public static volatile SingularAttribute<Area, Short> lockReg;
    public static volatile SingularAttribute<Area, Integer> id;
    public static volatile SingularAttribute<Area, Date> fecIni;
    public static volatile SingularAttribute<Area, Date> fecUpd;
    public static volatile ListAttribute<Area, Nombre> nombreList;
    public static volatile ListAttribute<Area, NombreP> practica;

}
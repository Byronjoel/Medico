package ec.com.cubosoft.avamed.modelo.practica;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(PracticaAsociada.class)
public class PracticaAsociada_ { 

    public static volatile SingularAttribute<PracticaAsociada, String> descripcion;
    public static volatile SingularAttribute<PracticaAsociada, String> lastUser;
    public static volatile SingularAttribute<PracticaAsociada, BigInteger> firstOid;
    public static volatile SingularAttribute<PracticaAsociada, String> firstUser;
    public static volatile SingularAttribute<PracticaAsociada, BigInteger> lastOid;
    public static volatile SingularAttribute<PracticaAsociada, Short> lockReg;
    public static volatile SingularAttribute<PracticaAsociada, BigInteger> idNombre;
    public static volatile SingularAttribute<PracticaAsociada, Integer> id;
    public static volatile SingularAttribute<PracticaAsociada, Date> fecIni;
    public static volatile SingularAttribute<PracticaAsociada, Date> fecUpd;

}
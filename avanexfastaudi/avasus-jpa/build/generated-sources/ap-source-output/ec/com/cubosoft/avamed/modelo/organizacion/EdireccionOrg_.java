package ec.com.cubosoft.avamed.modelo.organizacion;

import ec.com.cubosoft.avamed.modelo.organizacion.Organizacion;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(EdireccionOrg.class)
public class EdireccionOrg_ { 

    public static volatile SingularAttribute<EdireccionOrg, String> tipo;
    public static volatile SingularAttribute<EdireccionOrg, String> lastUser;
    public static volatile SingularAttribute<EdireccionOrg, BigInteger> firstOid;
    public static volatile SingularAttribute<EdireccionOrg, BigInteger> lastOid;
    public static volatile SingularAttribute<EdireccionOrg, Short> lockReg;
    public static volatile SingularAttribute<EdireccionOrg, Date> fecIni;
    public static volatile SingularAttribute<EdireccionOrg, Date> fecUpd;
    public static volatile SingularAttribute<EdireccionOrg, String> detalle;
    public static volatile SingularAttribute<EdireccionOrg, String> uso;
    public static volatile SingularAttribute<EdireccionOrg, String> firstUser;
    public static volatile SingularAttribute<EdireccionOrg, String> preferencia;
    public static volatile SingularAttribute<EdireccionOrg, Organizacion> nombreOrg;
    public static volatile SingularAttribute<EdireccionOrg, Integer> id;

}
package ec.com.cubosoft.avamed.modelo.organizacion;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Organizacion.class)
public class Organizacion_ { 

    public static volatile SingularAttribute<Organizacion, String> ruc;
    public static volatile SingularAttribute<Organizacion, Integer> codRef;
    public static volatile SingularAttribute<Organizacion, String> lastUser;
    public static volatile SingularAttribute<Organizacion, String> contacto;
    public static volatile SingularAttribute<Organizacion, BigInteger> firstOid;
    public static volatile SingularAttribute<Organizacion, String> abreviatura;
    public static volatile SingularAttribute<Organizacion, BigInteger> lastOid;
    public static volatile SingularAttribute<Organizacion, Short> lockReg;
    public static volatile SingularAttribute<Organizacion, String> direccion;
    public static volatile SingularAttribute<Organizacion, Date> fecIni;
    public static volatile SingularAttribute<Organizacion, Date> fecUpd;
    public static volatile SingularAttribute<Organizacion, String> razonSocial;
    public static volatile SingularAttribute<Organizacion, String> firstUser;
    public static volatile SingularAttribute<Organizacion, Long> id;
    public static volatile ListAttribute<Organizacion, Orden> orden;
    public static volatile SingularAttribute<Organizacion, String> telefono;

}
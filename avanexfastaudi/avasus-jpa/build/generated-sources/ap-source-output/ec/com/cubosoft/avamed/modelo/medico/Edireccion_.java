package ec.com.cubosoft.avamed.modelo.medico;

import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Edireccion.class)
public class Edireccion_ { 

    public static volatile SingularAttribute<Edireccion, String> tipo;
    public static volatile SingularAttribute<Edireccion, String> lastUser;
    public static volatile SingularAttribute<Edireccion, BigInteger> firstOid;
    public static volatile SingularAttribute<Edireccion, BigInteger> lastOid;
    public static volatile SingularAttribute<Edireccion, Short> lockReg;
    public static volatile SingularAttribute<Edireccion, Date> fecIni;
    public static volatile SingularAttribute<Edireccion, Date> fecUpd;
    public static volatile SingularAttribute<Edireccion, String> detalle;
    public static volatile SingularAttribute<Edireccion, String> uso;
    public static volatile SingularAttribute<Edireccion, String> firstUser;
    public static volatile SingularAttribute<Edireccion, Nombre> idNombre;
    public static volatile SingularAttribute<Edireccion, String> preferencia;
    public static volatile SingularAttribute<Edireccion, Integer> id;

}
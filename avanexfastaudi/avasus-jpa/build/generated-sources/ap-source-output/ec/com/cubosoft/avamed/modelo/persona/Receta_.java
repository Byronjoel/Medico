package ec.com.cubosoft.avamed.modelo.persona;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Receta.class)
public class Receta_ { 

    public static volatile SingularAttribute<Receta, String> estado;
    public static volatile SingularAttribute<Receta, String> lastUser;
    public static volatile SingularAttribute<Receta, Short> lockReg;
    public static volatile SingularAttribute<Receta, BigInteger> idHistoria;
    public static volatile SingularAttribute<Receta, Date> fecIni;
    public static volatile SingularAttribute<Receta, BigInteger> idOrden;
    public static volatile SingularAttribute<Receta, Date> fecUpd;
    public static volatile SingularAttribute<Receta, String> texto;
    public static volatile SingularAttribute<Receta, Date> fecha;
    public static volatile SingularAttribute<Receta, String> firstUser;
    public static volatile SingularAttribute<Receta, String> medico;
    public static volatile SingularAttribute<Receta, String> usuario;
    public static volatile SingularAttribute<Receta, Long> id;
    public static volatile SingularAttribute<Receta, Integer> idPractica;

}
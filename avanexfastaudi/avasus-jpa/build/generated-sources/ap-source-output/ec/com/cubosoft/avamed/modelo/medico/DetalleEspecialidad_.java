package ec.com.cubosoft.avamed.modelo.medico;

import ec.com.cubosoft.avamed.modelo.medico.Especialidad;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(DetalleEspecialidad.class)
public class DetalleEspecialidad_ { 

    public static volatile SingularAttribute<DetalleEspecialidad, String> lastUser;
    public static volatile SingularAttribute<DetalleEspecialidad, BigInteger> firstOid;
    public static volatile SingularAttribute<DetalleEspecialidad, String> firstUser;
    public static volatile SingularAttribute<DetalleEspecialidad, BigInteger> lastOid;
    public static volatile SingularAttribute<DetalleEspecialidad, Short> lockReg;
    public static volatile SingularAttribute<DetalleEspecialidad, Integer> id;
    public static volatile SingularAttribute<DetalleEspecialidad, Date> fecIni;
    public static volatile SingularAttribute<DetalleEspecialidad, Especialidad> idEspecialidad;
    public static volatile SingularAttribute<DetalleEspecialidad, Date> fecUpd;

}
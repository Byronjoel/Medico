package ec.com.cubosoft.avamed.modelo.medico;

import ec.com.cubosoft.avamed.modelo.medico.DetalleEspecialidad;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Especialidad.class)
public class Especialidad_ { 

    public static volatile SingularAttribute<Especialidad, String> descripcion;
    public static volatile SingularAttribute<Especialidad, String> lastUser;
    public static volatile SingularAttribute<Especialidad, BigInteger> firstOid;
    public static volatile SingularAttribute<Especialidad, String> firstUser;
    public static volatile SingularAttribute<Especialidad, BigInteger> lastOid;
    public static volatile SingularAttribute<Especialidad, Short> lockReg;
    public static volatile SingularAttribute<Especialidad, Integer> id;
    public static volatile SingularAttribute<Especialidad, Date> fecIni;
    public static volatile SingularAttribute<Especialidad, Date> fecUpd;
    public static volatile ListAttribute<Especialidad, DetalleEspecialidad> detalleEspecialidadList;

}
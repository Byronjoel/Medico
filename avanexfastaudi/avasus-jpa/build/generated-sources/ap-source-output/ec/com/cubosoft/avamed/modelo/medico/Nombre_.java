package ec.com.cubosoft.avamed.modelo.medico;

import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
import ec.com.cubosoft.avamed.modelo.medico.Area;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Nombre.class)
public class Nombre_ { 

    public static volatile SingularAttribute<Nombre, Area> area;
    public static volatile SingularAttribute<Nombre, String> tipoId;
    public static volatile SingularAttribute<Nombre, String> lastUser;
    public static volatile SingularAttribute<Nombre, BigInteger> firstOid;
    public static volatile SingularAttribute<Nombre, Date> fechaNace;
    public static volatile SingularAttribute<Nombre, String> numId;
    public static volatile SingularAttribute<Nombre, BigInteger> lastOid;
    public static volatile SingularAttribute<Nombre, Short> lockReg;
    public static volatile SingularAttribute<Nombre, String> titulo;
    public static volatile SingularAttribute<Nombre, Date> fecIni;
    public static volatile SingularAttribute<Nombre, CsUsuarios> usuarios;
    public static volatile SingularAttribute<Nombre, String> nombre;
    public static volatile SingularAttribute<Nombre, String> codMedico;
    public static volatile SingularAttribute<Nombre, String> especialidad;
    public static volatile SingularAttribute<Nombre, Date> fecUpd;
    public static volatile ListAttribute<Nombre, XmlResultado> xmlResultados;
    public static volatile SingularAttribute<Nombre, String> paisId;
    public static volatile SingularAttribute<Nombre, String> firstUser;
    public static volatile SingularAttribute<Nombre, String> apellido;
    public static volatile SingularAttribute<Nombre, String> usuario;
    public static volatile SingularAttribute<Nombre, Integer> id;
    public static volatile SingularAttribute<Nombre, String> sexo;

}
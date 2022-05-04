package ec.com.cubosoft.avamed.modelo.organizacion;

import ec.com.cubosoft.avamed.modelo.organizacion.Organizacion;
import ec.com.cubosoft.avamed.modelo.organizacion.UsuarioPK;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Usuario.class)
public class Usuario_ { 

    public static volatile SingularAttribute<Usuario, String> mailUser;
    public static volatile SingularAttribute<Usuario, String> lastUser;
    public static volatile SingularAttribute<Usuario, BigInteger> firstOid;
    public static volatile SingularAttribute<Usuario, BigInteger> lastOid;
    public static volatile SingularAttribute<Usuario, String> pwdUsuario;
    public static volatile SingularAttribute<Usuario, String> firstUser;
    public static volatile SingularAttribute<Usuario, Short> lockReg;
    public static volatile SingularAttribute<Usuario, Organizacion> organizacion;
    public static volatile SingularAttribute<Usuario, String> nombreUsuario;
    public static volatile SingularAttribute<Usuario, Date> fecIni;
    public static volatile SingularAttribute<Usuario, UsuarioPK> usuarioPK;
    public static volatile SingularAttribute<Usuario, Date> fecUpd;

}
package ec.com.cubosoft.avamed.modelo.organizacion;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:18")
@StaticMetamodel(ImgLogo.class)
public class ImgLogo_ { 

    public static volatile SingularAttribute<ImgLogo, String> descripcion;
    public static volatile SingularAttribute<ImgLogo, Short> perLogo;
    public static volatile SingularAttribute<ImgLogo, Short> lockReg;
    public static volatile SingularAttribute<ImgLogo, byte[]> logo;
    public static volatile SingularAttribute<ImgLogo, BigInteger> idEmpresa;
    public static volatile SingularAttribute<ImgLogo, Integer> id;
    public static volatile SingularAttribute<ImgLogo, Date> fecIni;
    public static volatile SingularAttribute<ImgLogo, Date> fecUpd;

}
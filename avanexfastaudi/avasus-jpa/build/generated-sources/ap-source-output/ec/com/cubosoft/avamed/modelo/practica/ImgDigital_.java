package ec.com.cubosoft.avamed.modelo.practica;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(ImgDigital.class)
public class ImgDigital_ { 

    public static volatile SingularAttribute<ImgDigital, String> descripcion;
    public static volatile SingularAttribute<ImgDigital, Short> lockReg;
    public static volatile SingularAttribute<ImgDigital, String> usuario;
    public static volatile SingularAttribute<ImgDigital, Short> perFirma;
    public static volatile SingularAttribute<ImgDigital, Integer> id;
    public static volatile SingularAttribute<ImgDigital, Date> fecIni;
    public static volatile SingularAttribute<ImgDigital, byte[]> firma;
    public static volatile SingularAttribute<ImgDigital, Date> fecUpd;

}
package ec.com.cubosoft.avamed.modelo.practica;

import ec.com.cubosoft.avamed.modelo.practica.TextoXAbreviatura;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Abreviatura.class)
public class Abreviatura_ { 

    public static volatile SingularAttribute<Abreviatura, String> descripcion;
    public static volatile SingularAttribute<Abreviatura, String> lastUser;
    public static volatile SingularAttribute<Abreviatura, BigInteger> firstOid;
    public static volatile SingularAttribute<Abreviatura, String> firstUser;
    public static volatile SingularAttribute<Abreviatura, BigInteger> lastOid;
    public static volatile SingularAttribute<Abreviatura, Short> lockReg;
    public static volatile ListAttribute<Abreviatura, TextoXAbreviatura> textoXAbreviaturaList;
    public static volatile SingularAttribute<Abreviatura, Integer> id;
    public static volatile SingularAttribute<Abreviatura, Date> fecIni;
    public static volatile SingularAttribute<Abreviatura, Date> fecUpd;

}
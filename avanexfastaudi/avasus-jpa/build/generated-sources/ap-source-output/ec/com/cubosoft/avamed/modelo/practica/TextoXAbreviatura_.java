package ec.com.cubosoft.avamed.modelo.practica;

import ec.com.cubosoft.avamed.modelo.practica.Abreviatura;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(TextoXAbreviatura.class)
public class TextoXAbreviatura_ { 

    public static volatile SingularAttribute<TextoXAbreviatura, String> texto;
    public static volatile SingularAttribute<TextoXAbreviatura, String> codigo;
    public static volatile SingularAttribute<TextoXAbreviatura, String> lastUser;
    public static volatile SingularAttribute<TextoXAbreviatura, BigInteger> firstOid;
    public static volatile SingularAttribute<TextoXAbreviatura, Abreviatura> abreviatura;
    public static volatile SingularAttribute<TextoXAbreviatura, String> firstUser;
    public static volatile SingularAttribute<TextoXAbreviatura, BigInteger> lastOid;
    public static volatile SingularAttribute<TextoXAbreviatura, Short> lockReg;
    public static volatile SingularAttribute<TextoXAbreviatura, Integer> id;
    public static volatile SingularAttribute<TextoXAbreviatura, Date> fecIni;
    public static volatile SingularAttribute<TextoXAbreviatura, Date> fecUpd;

}
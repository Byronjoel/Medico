package ec.com.cubosoft.avamed.modelo.practica;

import ec.com.cubosoft.avamed.modelo.practica.NombreP;
import ec.com.cubosoft.avamed.modelo.practica.Reporte;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(PracticaXReporte.class)
public class PracticaXReporte_ { 

    public static volatile SingularAttribute<PracticaXReporte, String> lastUser;
    public static volatile SingularAttribute<PracticaXReporte, NombreP> nombreP;
    public static volatile SingularAttribute<PracticaXReporte, BigInteger> firstOid;
    public static volatile SingularAttribute<PracticaXReporte, String> firstUser;
    public static volatile SingularAttribute<PracticaXReporte, BigInteger> lastOid;
    public static volatile SingularAttribute<PracticaXReporte, Short> lockReg;
    public static volatile SingularAttribute<PracticaXReporte, Short> ordenPractica;
    public static volatile SingularAttribute<PracticaXReporte, Integer> id;
    public static volatile SingularAttribute<PracticaXReporte, Date> fecIni;
    public static volatile SingularAttribute<PracticaXReporte, Date> fecUpd;
    public static volatile SingularAttribute<PracticaXReporte, Reporte> reporte;

}
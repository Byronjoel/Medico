package ec.com.cubosoft.avamed.modelo.practica;

import ec.com.cubosoft.avamed.modelo.practica.PracticaXReporte;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Reporte.class)
public class Reporte_ { 

    public static volatile SingularAttribute<Reporte, String> descripcion;
    public static volatile SingularAttribute<Reporte, String> lastUser;
    public static volatile SingularAttribute<Reporte, BigInteger> firstOid;
    public static volatile SingularAttribute<Reporte, byte[]> syntaxis;
    public static volatile SingularAttribute<Reporte, String> firstUser;
    public static volatile SingularAttribute<Reporte, BigInteger> lastOid;
    public static volatile SingularAttribute<Reporte, Short> lockReg;
    public static volatile SingularAttribute<Reporte, Integer> id;
    public static volatile SingularAttribute<Reporte, Date> fecIni;
    public static volatile SingularAttribute<Reporte, Date> fecUpd;
    public static volatile ListAttribute<Reporte, PracticaXReporte> practicaXReporteList;

}
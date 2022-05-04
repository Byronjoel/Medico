package ec.com.cubosoft.avamed.modelo.practica;

import ec.com.cubosoft.avamed.modelo.ingreso.PracticaXOrden;
import ec.com.cubosoft.avamed.modelo.medico.Area;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import ec.com.cubosoft.avamed.modelo.practica.PracticaXReporte;
import ec.com.cubosoft.avamed.modelo.practica.Termino;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(NombreP.class)
public class NombreP_ { 

    public static volatile SingularAttribute<NombreP, String> descripcion;
    public static volatile SingularAttribute<NombreP, Area> area;
    public static volatile SingularAttribute<NombreP, Integer> codRef;
    public static volatile SingularAttribute<NombreP, String> lastUser;
    public static volatile SingularAttribute<NombreP, BigInteger> firstOid;
    public static volatile SingularAttribute<NombreP, String> abreviatura;
    public static volatile SingularAttribute<NombreP, String> infAd2;
    public static volatile SingularAttribute<NombreP, BigInteger> lastOid;
    public static volatile SingularAttribute<NombreP, String> infAd3;
    public static volatile SingularAttribute<NombreP, Short> lockReg;
    public static volatile SingularAttribute<NombreP, Short> perAdd;
    public static volatile SingularAttribute<NombreP, String> infAd1;
    public static volatile SingularAttribute<NombreP, Date> fecIni;
    public static volatile ListAttribute<NombreP, Termino> terminoList;
    public static volatile SingularAttribute<NombreP, Integer> ordenImp;
    public static volatile SingularAttribute<NombreP, Date> fecUpd;
    public static volatile SingularAttribute<NombreP, Short> perImpa;
    public static volatile ListAttribute<NombreP, PracticaXOrden> practicaXorden;
    public static volatile ListAttribute<NombreP, XmlResultado> xmlResultados;
    public static volatile SingularAttribute<NombreP, String> firstUser;
    public static volatile SingularAttribute<NombreP, Short> srvStnd;
    public static volatile SingularAttribute<NombreP, Integer> id;
    public static volatile ListAttribute<NombreP, PracticaXReporte> practicaXReporteList;

}
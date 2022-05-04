package ec.com.cubosoft.avamed.modelo.ingreso;

import ec.com.cubosoft.avamed.modelo.empresa.Origen;
import ec.com.cubosoft.avamed.modelo.ingreso.PracticaXOrden;
import ec.com.cubosoft.avamed.modelo.organizacion.Organizacion;
import ec.com.cubosoft.avamed.modelo.persona.Episodio;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import ec.com.cubosoft.avamed.modelo.persona.XmlAntecedentes;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Orden.class)
public class Orden_ { 

    public static volatile SingularAttribute<Orden, String> lastUser;
    public static volatile SingularAttribute<Orden, BigInteger> firstOid;
    public static volatile SingularAttribute<Orden, Short> lockReg;
    public static volatile SingularAttribute<Orden, String> stsAdmin;
    public static volatile SingularAttribute<Orden, Date> fecIni;
    public static volatile SingularAttribute<Orden, Origen> origen;
    public static volatile SingularAttribute<Orden, Date> fecUpd;
    public static volatile ListAttribute<Orden, XmlResultado> xmlResultados;
    public static volatile SingularAttribute<Orden, String> stsTecnico;
    public static volatile SingularAttribute<Orden, Date> fecIngreso;
    public static volatile SingularAttribute<Orden, Organizacion> organizacion;
    public static volatile ListAttribute<Orden, Episodio> episodios;
    public static volatile SingularAttribute<Orden, Long> id;
    public static volatile SingularAttribute<Orden, String> desOri;
    public static volatile SingularAttribute<Orden, Historia> historia;
    public static volatile SingularAttribute<Orden, BigInteger> lastOid;
    public static volatile SingularAttribute<Orden, Date> fecEntrega;
    public static volatile SingularAttribute<Orden, String> mSolicitante;
    public static volatile ListAttribute<Orden, XmlAntecedentes> xmlAntecedentes;
    public static volatile ListAttribute<Orden, PracticaXOrden> practicaXorden;
    public static volatile SingularAttribute<Orden, Long> idPlan;
    public static volatile SingularAttribute<Orden, Long> codOrd;
    public static volatile SingularAttribute<Orden, String> codOri;
    public static volatile SingularAttribute<Orden, Integer> idOrigen;
    public static volatile SingularAttribute<Orden, String> nroExterno;
    public static volatile SingularAttribute<Orden, String> firstUser;
    public static volatile SingularAttribute<Orden, String> observaciones;
    public static volatile SingularAttribute<Orden, Integer> idReferencia;

}
package ec.com.cubosoft.avamed.modelo.persona;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import ec.com.cubosoft.avamed.modelo.practica.NombreP;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:18")
@StaticMetamodel(XmlResultado.class)
public class XmlResultado_ { 

    public static volatile SingularAttribute<XmlResultado, Long> codPac;
    public static volatile SingularAttribute<XmlResultado, String> estado;
    public static volatile SingularAttribute<XmlResultado, String> lastUser;
    public static volatile SingularAttribute<XmlResultado, BigInteger> firstOid;
    public static volatile SingularAttribute<XmlResultado, String> codAna;
    public static volatile SingularAttribute<XmlResultado, Date> hora;
    public static volatile SingularAttribute<XmlResultado, Short> lockReg;
    public static volatile SingularAttribute<XmlResultado, Date> fecIni;
    public static volatile SingularAttribute<XmlResultado, BigInteger> idOrden;
    public static volatile SingularAttribute<XmlResultado, Date> fecUpd;
    public static volatile SingularAttribute<XmlResultado, NombreP> practica;
    public static volatile SingularAttribute<XmlResultado, String> dx;
    public static volatile SingularAttribute<XmlResultado, Long> idOrdenNextlab;
    public static volatile SingularAttribute<XmlResultado, Long> nroOrd;
    public static volatile SingularAttribute<XmlResultado, Nombre> medicos;
    public static volatile SingularAttribute<XmlResultado, String> linck;
    public static volatile SingularAttribute<XmlResultado, Long> id;
    public static volatile SingularAttribute<XmlResultado, Orden> orden;
    public static volatile SingularAttribute<XmlResultado, Integer> idPractica;
    public static volatile SingularAttribute<XmlResultado, String> desOri;
    public static volatile SingularAttribute<XmlResultado, Date> fecArch;
    public static volatile SingularAttribute<XmlResultado, Historia> historia;
    public static volatile SingularAttribute<XmlResultado, String> resultado;
    public static volatile SingularAttribute<XmlResultado, BigInteger> lastOid;
    public static volatile SingularAttribute<XmlResultado, String> lkImg;
    public static volatile SingularAttribute<XmlResultado, BigInteger> idHistoria;
    public static volatile SingularAttribute<XmlResultado, String> archUser;
    public static volatile SingularAttribute<XmlResultado, Date> fechaAudit;
    public static volatile SingularAttribute<XmlResultado, Date> fecha;
    public static volatile SingularAttribute<XmlResultado, String> codOri;
    public static volatile SingularAttribute<XmlResultado, String> firstUser;
    public static volatile SingularAttribute<XmlResultado, String> medico;
    public static volatile SingularAttribute<XmlResultado, String> auditUser;
    public static volatile SingularAttribute<XmlResultado, String> empresa;

}
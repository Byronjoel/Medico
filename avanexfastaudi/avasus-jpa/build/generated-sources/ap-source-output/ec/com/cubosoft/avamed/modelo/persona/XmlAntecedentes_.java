package ec.com.cubosoft.avamed.modelo.persona;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(XmlAntecedentes.class)
public class XmlAntecedentes_ { 

    public static volatile SingularAttribute<XmlAntecedentes, Long> codPac;
    public static volatile SingularAttribute<XmlAntecedentes, Integer> codRef;
    public static volatile SingularAttribute<XmlAntecedentes, String> estado;
    public static volatile SingularAttribute<XmlAntecedentes, String> lastUser;
    public static volatile SingularAttribute<XmlAntecedentes, BigInteger> firstOid;
    public static volatile SingularAttribute<XmlAntecedentes, BigInteger> lastOid;
    public static volatile SingularAttribute<XmlAntecedentes, Long> codAna;
    public static volatile SingularAttribute<XmlAntecedentes, Date> hora;
    public static volatile SingularAttribute<XmlAntecedentes, Short> lockReg;
    public static volatile SingularAttribute<XmlAntecedentes, Historia> idHistoria;
    public static volatile SingularAttribute<XmlAntecedentes, Integer> idMedico;
    public static volatile SingularAttribute<XmlAntecedentes, Date> fecIni;
    public static volatile SingularAttribute<XmlAntecedentes, Date> fecUpd;
    public static volatile SingularAttribute<XmlAntecedentes, Date> fecha;
    public static volatile SingularAttribute<XmlAntecedentes, String> codOri;
    public static volatile SingularAttribute<XmlAntecedentes, String> firstUser;
    public static volatile SingularAttribute<XmlAntecedentes, String> medico;
    public static volatile SingularAttribute<XmlAntecedentes, Long> nroOrd;
    public static volatile SingularAttribute<XmlAntecedentes, String> antecedentes;
    public static volatile SingularAttribute<XmlAntecedentes, Integer> idEmpresa;
    public static volatile SingularAttribute<XmlAntecedentes, Long> id;
    public static volatile SingularAttribute<XmlAntecedentes, Orden> orden;
    public static volatile SingularAttribute<XmlAntecedentes, Integer> idPractica;
    public static volatile SingularAttribute<XmlAntecedentes, String> empresa;

}
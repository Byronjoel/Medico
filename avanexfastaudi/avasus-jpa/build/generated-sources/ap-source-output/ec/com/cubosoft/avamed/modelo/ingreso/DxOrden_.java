package ec.com.cubosoft.avamed.modelo.ingreso;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(DxOrden.class)
public class DxOrden_ { 

    public static volatile SingularAttribute<DxOrden, Long> codPac;
    public static volatile SingularAttribute<DxOrden, String> descripcion;
    public static volatile SingularAttribute<DxOrden, String> codRef;
    public static volatile SingularAttribute<DxOrden, String> lastUser;
    public static volatile SingularAttribute<DxOrden, BigInteger> firstOid;
    public static volatile SingularAttribute<DxOrden, BigInteger> lastOid;
    public static volatile SingularAttribute<DxOrden, Short> lockReg;
    public static volatile SingularAttribute<DxOrden, Long> idHistoria;
    public static volatile SingularAttribute<DxOrden, BigInteger> idXml;
    public static volatile SingularAttribute<DxOrden, String> ocupacion;
    public static volatile SingularAttribute<DxOrden, Date> fecIni;
    public static volatile SingularAttribute<DxOrden, String> origen;
    public static volatile SingularAttribute<DxOrden, String> nombre;
    public static volatile SingularAttribute<DxOrden, String> edad;
    public static volatile SingularAttribute<DxOrden, Integer> idOrden;
    public static volatile SingularAttribute<DxOrden, Date> fecUpd;
    public static volatile SingularAttribute<DxOrden, String> firstUser;
    public static volatile SingularAttribute<DxOrden, String> grupoRiesgos;
    public static volatile SingularAttribute<DxOrden, Long> nroOrd;
    public static volatile SingularAttribute<DxOrden, String> dxMedico;
    public static volatile SingularAttribute<DxOrden, String> departamento;
    public static volatile SingularAttribute<DxOrden, Long> id;
    public static volatile SingularAttribute<DxOrden, Integer> idPractica;
    public static volatile SingularAttribute<DxOrden, String> codCie;

}
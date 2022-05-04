package ec.com.cubosoft.avamed.modelo.ingreso;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.practica.NombreP;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(PracticaXOrden.class)
public class PracticaXOrden_ { 

    public static volatile SingularAttribute<PracticaXOrden, Date> fecAtencion;
    public static volatile SingularAttribute<PracticaXOrden, String> lastUser;
    public static volatile SingularAttribute<PracticaXOrden, Integer> idArea;
    public static volatile SingularAttribute<PracticaXOrden, Short> lockReg;
    public static volatile SingularAttribute<PracticaXOrden, String> stsAdmin;
    public static volatile SingularAttribute<PracticaXOrden, BigDecimal> valorSeguro;
    public static volatile SingularAttribute<PracticaXOrden, Long> idNombrep;
    public static volatile SingularAttribute<PracticaXOrden, String> archUser;
    public static volatile SingularAttribute<PracticaXOrden, Date> fecIni;
    public static volatile SingularAttribute<PracticaXOrden, Date> fechaAudit;
    public static volatile SingularAttribute<PracticaXOrden, Date> fecUpd;
    public static volatile SingularAttribute<PracticaXOrden, NombreP> practica;
    public static volatile SingularAttribute<PracticaXOrden, Long> idPlan;
    public static volatile SingularAttribute<PracticaXOrden, String> stsTecnico;
    public static volatile SingularAttribute<PracticaXOrden, String> firstUser;
    public static volatile SingularAttribute<PracticaXOrden, BigDecimal> valorPaciente;
    public static volatile SingularAttribute<PracticaXOrden, String> auditUser;
    public static volatile SingularAttribute<PracticaXOrden, Long> id;
    public static volatile SingularAttribute<PracticaXOrden, Short> cantidad;
    public static volatile SingularAttribute<PracticaXOrden, Orden> orden;
    public static volatile SingularAttribute<PracticaXOrden, Date> fecArch;

}
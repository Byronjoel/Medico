package ec.com.cubosoft.avamed.modelo.core;

import ec.com.cubosoft.avamed.modelo.core.CsGrupos;
import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(CsUsuarios.class)
public class CsUsuarios_ { 

    public static volatile SingularAttribute<CsUsuarios, String> pwdUsu;
    public static volatile SingularAttribute<CsUsuarios, Short> perAbto;
    public static volatile SingularAttribute<CsUsuarios, String> lastUser;
    public static volatile SingularAttribute<CsUsuarios, Short> lockUsu;
    public static volatile SingularAttribute<CsUsuarios, Short> lockReg;
    public static volatile SingularAttribute<CsUsuarios, Short> perUpload;
    public static volatile SingularAttribute<CsUsuarios, Date> fecIni;
    public static volatile SingularAttribute<CsUsuarios, Date> fecUpd;
    public static volatile SingularAttribute<CsUsuarios, Short> priVis;
    public static volatile SingularAttribute<CsUsuarios, String> nomUsu;
    public static volatile SingularAttribute<CsUsuarios, String> docUsu;
    public static volatile SingularAttribute<CsUsuarios, Short> perDelete;
    public static volatile SingularAttribute<CsUsuarios, String> firstUser;
    public static volatile ListAttribute<CsUsuarios, Nombre> medicos;
    public static volatile SingularAttribute<CsUsuarios, String> usuario;
    public static volatile SingularAttribute<CsUsuarios, CsGrupos> csGrupos;
    public static volatile SingularAttribute<CsUsuarios, Short> perCyp;

}
package ec.com.cubosoft.avamed.modelo.core;

import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(CsGrupos.class)
public class CsGrupos_ { 

    public static volatile SingularAttribute<CsGrupos, Short> lockGru;
    public static volatile SingularAttribute<CsGrupos, String> desGru;
    public static volatile SingularAttribute<CsGrupos, String> lastUser;
    public static volatile SingularAttribute<CsGrupos, String> firstUser;
    public static volatile SingularAttribute<CsGrupos, Short> lockReg;
    public static volatile SingularAttribute<CsGrupos, String> codGru;
    public static volatile SingularAttribute<CsGrupos, Date> fecIni;
    public static volatile ListAttribute<CsGrupos, CsUsuarios> csUsuariosList;
    public static volatile SingularAttribute<CsGrupos, Date> fecUpd;

}
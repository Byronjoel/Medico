package ec.com.cubosoft.avamed.modelo.medico;

import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import ec.com.cubosoft.avamed.modelo.practica.Abreviatura;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(AbreviaturaXMedico.class)
public class AbreviaturaXMedico_ { 

    public static volatile SingularAttribute<AbreviaturaXMedico, String> lastUser;
    public static volatile SingularAttribute<AbreviaturaXMedico, Abreviatura> abreviatura;
    public static volatile SingularAttribute<AbreviaturaXMedico, String> firstUser;
    public static volatile SingularAttribute<AbreviaturaXMedico, Short> lockReg;
    public static volatile SingularAttribute<AbreviaturaXMedico, Nombre> Medico;
    public static volatile SingularAttribute<AbreviaturaXMedico, Integer> id;
    public static volatile SingularAttribute<AbreviaturaXMedico, Date> fecIni;
    public static volatile SingularAttribute<AbreviaturaXMedico, Date> fecUpd;

}
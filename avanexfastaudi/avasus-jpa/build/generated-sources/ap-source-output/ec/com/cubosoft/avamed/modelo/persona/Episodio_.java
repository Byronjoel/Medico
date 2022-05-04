package ec.com.cubosoft.avamed.modelo.persona;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import ec.com.cubosoft.avamed.modelo.practica.Termino;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Episodio.class)
public class Episodio_ { 

    public static volatile SingularAttribute<Episodio, Historia> historia;
    public static volatile SingularAttribute<Episodio, String> lastUser;
    public static volatile SingularAttribute<Episodio, String> estado;
    public static volatile SingularAttribute<Episodio, Termino> terminos;
    public static volatile SingularAttribute<Episodio, BigInteger> firstOid;
    public static volatile SingularAttribute<Episodio, String> resultado;
    public static volatile SingularAttribute<Episodio, BigInteger> lastOid;
    public static volatile SingularAttribute<Episodio, Short> lockReg;
    public static volatile SingularAttribute<Episodio, String> tipoOrden;
    public static volatile SingularAttribute<Episodio, String> grupo;
    public static volatile SingularAttribute<Episodio, Date> fecIni;
    public static volatile SingularAttribute<Episodio, String> practica;
    public static volatile SingularAttribute<Episodio, Date> fecUpd;
    public static volatile SingularAttribute<Episodio, Nombre> nombres;
    public static volatile SingularAttribute<Episodio, String> unidad;
    public static volatile SingularAttribute<Episodio, String> firstUser;
    public static volatile SingularAttribute<Episodio, Orden> ordens;
    public static volatile SingularAttribute<Episodio, Short> ordenPractica;
    public static volatile SingularAttribute<Episodio, String> medico;
    public static volatile SingularAttribute<Episodio, Long> id;
    public static volatile SingularAttribute<Episodio, String> termino;
    public static volatile SingularAttribute<Episodio, Short> orden;
    public static volatile SingularAttribute<Episodio, Integer> idPractica;
    public static volatile SingularAttribute<Episodio, Character> tipoDato;

}
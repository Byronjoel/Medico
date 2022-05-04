package ec.com.cubosoft.avamed.modelo.practica;

import ec.com.cubosoft.avamed.modelo.persona.Episodio;
import ec.com.cubosoft.avamed.modelo.practica.NombreP;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Termino.class)
public class Termino_ { 

    public static volatile SingularAttribute<Termino, String> descripcion;
    public static volatile SingularAttribute<Termino, String> lastUser;
    public static volatile SingularAttribute<Termino, NombreP> nombreP;
    public static volatile SingularAttribute<Termino, BigInteger> firstOid;
    public static volatile SingularAttribute<Termino, String> xdefecto;
    public static volatile SingularAttribute<Termino, BigInteger> lastOid;
    public static volatile SingularAttribute<Termino, Short> lockReg;
    public static volatile SingularAttribute<Termino, String> grupo;
    public static volatile SingularAttribute<Termino, Date> fecIni;
    public static volatile SingularAttribute<Termino, Date> fecUpd;
    public static volatile SingularAttribute<Termino, String> unidad;
    public static volatile SingularAttribute<Termino, String> firstUser;
    public static volatile SingularAttribute<Termino, String> formula;
    public static volatile ListAttribute<Termino, Episodio> episodios;
    public static volatile SingularAttribute<Termino, Integer> id;
    public static volatile SingularAttribute<Termino, Short> orden;
    public static volatile SingularAttribute<Termino, Character> tipoDato;

}
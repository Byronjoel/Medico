package ec.com.cubosoft.avamed.modelo.persona;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.persona.Episodio;
import ec.com.cubosoft.avamed.modelo.persona.XmlAntecedentes;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Historia.class)
public class Historia_ { 

    public static volatile SingularAttribute<Historia, String> tipoId;
    public static volatile SingularAttribute<Historia, String> lastUser;
    public static volatile SingularAttribute<Historia, BigInteger> firstOid;
    public static volatile SingularAttribute<Historia, String> tipoEdad;
    public static volatile SingularAttribute<Historia, String> numId;
    public static volatile SingularAttribute<Historia, Short> lockReg;
    public static volatile SingularAttribute<Historia, Long> idNextlab;
    public static volatile SingularAttribute<Historia, String> titulo;
    public static volatile SingularAttribute<Historia, String> ocupacion;
    public static volatile SingularAttribute<Historia, Date> fecIni;
    public static volatile SingularAttribute<Historia, String> instruccion;
    public static volatile SingularAttribute<Historia, Date> fecUpd;
    public static volatile SingularAttribute<Historia, String> nombres;
    public static volatile ListAttribute<Historia, XmlResultado> xmlResultados;
    public static volatile SingularAttribute<Historia, String> tipoSangre;
    public static volatile SingularAttribute<Historia, String> paciente;
    public static volatile SingularAttribute<Historia, Boolean> pluralNac;
    public static volatile ListAttribute<Historia, Episodio> episodios;
    public static volatile SingularAttribute<Historia, Long> id;
    public static volatile ListAttribute<Historia, Orden> orden;
    public static volatile CollectionAttribute<Historia, XmlAntecedentes> xmlAntecedentesCollection;
    public static volatile SingularAttribute<Historia, String> telefono;
    public static volatile SingularAttribute<Historia, String> email;
    public static volatile SingularAttribute<Historia, String> apellidos;
    public static volatile SingularAttribute<Historia, Date> fechaNace;
    public static volatile SingularAttribute<Historia, BigInteger> lastOid;
    public static volatile SingularAttribute<Historia, Date> fechaMuerte;
    public static volatile SingularAttribute<Historia, String> direccion;
    public static volatile SingularAttribute<Historia, Short> ordenNac;
    public static volatile SingularAttribute<Historia, String> estadoCivil;
    public static volatile SingularAttribute<Historia, String> ciudadNace;
    public static volatile SingularAttribute<Historia, String> paisId;
    public static volatile SingularAttribute<Historia, String> etnia;
    public static volatile SingularAttribute<Historia, String> sufijo;
    public static volatile SingularAttribute<Historia, String> firstUser;
    public static volatile SingularAttribute<Historia, String> paisNace;
    public static volatile SingularAttribute<Historia, String> profesion;
    public static volatile SingularAttribute<Historia, String> sexo;
    public static volatile SingularAttribute<Historia, String> pwd;
    public static volatile SingularAttribute<Historia, String> pariente;

}
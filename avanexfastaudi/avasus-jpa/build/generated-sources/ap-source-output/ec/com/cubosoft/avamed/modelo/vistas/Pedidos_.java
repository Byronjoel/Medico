package ec.com.cubosoft.avamed.modelo.vistas;

import java.math.BigInteger;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:18")
@StaticMetamodel(Pedidos.class)
public class Pedidos_ { 

    public static volatile SingularAttribute<Pedidos, Date> fecAtencion;
    public static volatile SingularAttribute<Pedidos, String> descripcion;
    public static volatile SingularAttribute<Pedidos, Integer> codRef;
    public static volatile SingularAttribute<Pedidos, String> abreviatura;
    public static volatile SingularAttribute<Pedidos, Integer> idArea;
    public static volatile SingularAttribute<Pedidos, Short> lockReg;
    public static volatile SingularAttribute<Pedidos, String> stsAdmin;
    public static volatile SingularAttribute<Pedidos, String> mSolicitante;
    public static volatile SingularAttribute<Pedidos, BigInteger> idNombrep;
    public static volatile SingularAttribute<Pedidos, BigInteger> idHistoria;
    public static volatile SingularAttribute<Pedidos, Short> perImpa;
    public static volatile SingularAttribute<Pedidos, BigInteger> idOrden;
    public static volatile SingularAttribute<Pedidos, Long> codOrd;
    public static volatile SingularAttribute<Pedidos, String> codOri;
    public static volatile SingularAttribute<Pedidos, String> stsTecnico;
    public static volatile SingularAttribute<Pedidos, BigInteger> idEmpresa;
    public static volatile SingularAttribute<Pedidos, BigInteger> id;
    public static volatile SingularAttribute<Pedidos, Integer> idPractica;

}
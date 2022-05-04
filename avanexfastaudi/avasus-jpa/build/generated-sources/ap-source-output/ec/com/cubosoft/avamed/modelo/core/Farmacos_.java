package ec.com.cubosoft.avamed.modelo.core;

import ec.com.cubosoft.avamed.modelo.core.Presentacion;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Farmacos.class)
public class Farmacos_ { 

    public static volatile SingularAttribute<Farmacos, String> descripcion;
    public static volatile SingularAttribute<Farmacos, String> concentracion;
    public static volatile SingularAttribute<Farmacos, String> tipo;
    public static volatile SingularAttribute<Farmacos, String> codigo;
    public static volatile SingularAttribute<Farmacos, Short> lockReg;
    public static volatile SingularAttribute<Farmacos, String> nomGenerico;
    public static volatile SingularAttribute<Farmacos, String> contraindicaciones;
    public static volatile SingularAttribute<Farmacos, String> presentacion;
    public static volatile SingularAttribute<Farmacos, String> grupo;
    public static volatile SingularAttribute<Farmacos, String> laboratorio;
    public static volatile CollectionAttribute<Farmacos, Presentacion> presentacionCollection;
    public static volatile SingularAttribute<Farmacos, String> nomComercial;
    public static volatile SingularAttribute<Farmacos, Integer> id;

}
package ec.com.cubosoft.avamed.modelo.core;

import ec.com.cubosoft.avamed.modelo.core.Farmacos;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:18")
@StaticMetamodel(Presentacion.class)
public class Presentacion_ { 

    public static volatile SingularAttribute<Presentacion, String> descripcion;
    public static volatile SingularAttribute<Presentacion, String> concentracion;
    public static volatile SingularAttribute<Presentacion, Farmacos> idFarmaco;
    public static volatile SingularAttribute<Presentacion, Short> lockReg;
    public static volatile SingularAttribute<Presentacion, Integer> id;

}
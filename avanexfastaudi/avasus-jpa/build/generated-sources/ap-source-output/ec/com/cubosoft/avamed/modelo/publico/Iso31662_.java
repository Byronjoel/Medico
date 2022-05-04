package ec.com.cubosoft.avamed.modelo.publico;

import ec.com.cubosoft.avamed.modelo.publico.Iso31661;
import ec.com.cubosoft.avamed.modelo.publico.Iso3166R2;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-03-09T17:13:17")
@StaticMetamodel(Iso31662.class)
public class Iso31662_ { 

    public static volatile SingularAttribute<Iso31662, String> region1;
    public static volatile SingularAttribute<Iso31662, Iso31661> iso31661;
    public static volatile SingularAttribute<Iso31662, Float> id;
    public static volatile SingularAttribute<Iso31662, String> iso2;
    public static volatile CollectionAttribute<Iso31662, Iso3166R2> iso3166R2;

}
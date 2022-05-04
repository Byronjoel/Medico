package ec.com.cubosoft.avasus.controller.renderder;

import ec.com.cubosoft.avamed.modelo.publico.Iso3166R2;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.ItemRenderer;

/**
 *
 * @author JP
 */
public class CiudadRendererSb implements ItemRenderer {

    @Override
    public String render(Component owner, Object data, int index) throws Exception {
        Iso3166R2 ciudad = (Iso3166R2) data;
        owner.setAttribute(new Integer(index).toString(), ciudad);
        
        return ciudad.getRegion2();
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avasus.controller.renderder;


import ec.com.cubosoft.avamed.modelo.medico.Area;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.ItemRenderer;

/**
 *
 * @author JP
 */
public class AreaRedererSb implements ItemRenderer {

    @Override
    public String render(Component owner, Object data, int index) throws Exception {
        Area area = (Area) data;
        owner.setAttribute(new Integer(index).toString(),area);
        return area.getDescripcion();
    }
    
}

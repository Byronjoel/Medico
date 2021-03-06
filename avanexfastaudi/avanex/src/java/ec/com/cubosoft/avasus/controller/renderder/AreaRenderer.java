/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avasus.controller.renderder;


import ec.com.cubosoft.avamed.modelo.medico.Area;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author JP
 */
public class AreaRenderer implements ListitemRenderer{
    @Override
     public void render(Listitem item, Object data, int index) throws Exception {
		Area area = (Area) data;
		item.setValue(area);
		new Listcell(String.valueOf(area.getId())).setParent(item);
		new Listcell(area.getDescripcion()).setParent(item);
	}

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avasus.controller.renderder;

import ec.com.cubosoft.avamed.modelo.organizacion.Organizacion;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author JP
 */
public class OrganizacionRenderer implements ListitemRenderer{
    @Override
     public void render(Listitem item, Object data, int index) throws Exception {
		Organizacion empresa = (Organizacion) data;
		item.setValue(empresa);
		new Listcell(String.valueOf(empresa.getId())).setParent(item);
		new Listcell(empresa.getAbreviatura()).setParent(item);
	}
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avasus.controller.renderder;

import ec.com.cubosoft.avamed.modelo.organizacion.Organizacion;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
//import org.zkoss.zul.Listcell;
//import org.zkoss.zul.Listitem;
//import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author JP
 */

public class OrganizacionRendererCombo implements ComboitemRenderer {
    @Override
    public void render(Comboitem cmbtm, Object t, int i) throws Exception {
        Organizacion empresa = (Organizacion) t;
        cmbtm.setValue(empresa);
        cmbtm.setLabel(empresa.getAbreviatura());

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avasus.controller.renderder;

import ec.com.cubosoft.avamed.modelo.core.Farmacos;
import org.zkoss.zul.Button;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author JP
 */
public class farmacoRenderer implements ComboitemRenderer<Object> {

   
    @Override
    public void render(Comboitem cmbtm, Object t, int i) throws Exception {
        Farmacos farmaco = (Farmacos) t;
        cmbtm.setValue(farmaco);
//          cmbtm.setLabel(farmaco.getNomGenerico());
         cmbtm.setLabel(farmaco.getDescripcion() );
//        new Listcell(String.valueOf(farmaco.getId())).setParent(cmbtm);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.modelo.core.Farmacos;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

/**
 *
 * @author JP
 */

public class FarmacoRenderCombo implements ComboitemRenderer {

    @Override
    public void render(Comboitem cmbtm, Object t, int i) throws Exception {
        Farmacos farmaco = (Farmacos) t;
        cmbtm.setValue(farmaco);
        cmbtm.setLabel(farmaco.getNomGenerico());

    }
}

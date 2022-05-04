/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.modelo.core.Farmacos;
import ec.com.cubosoft.avamed.modelo.core.Presentacion;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;

/**
 *
 * @author JP
 */
public class PresentacionRenderCombo implements ComboitemRenderer {

  @Override
    public void render(Comboitem cmbtm, Object t, int i) throws Exception {
       Presentacion presentacion = (Presentacion) t;
        cmbtm.setValue(presentacion);
        cmbtm.setLabel(presentacion.getDescripcion()+" / "+presentacion.getConcentracion());

    }
}

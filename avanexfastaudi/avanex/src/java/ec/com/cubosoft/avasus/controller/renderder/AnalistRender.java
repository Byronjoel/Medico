
package ec.com.cubosoft.avasus.controller.renderder;


//import ec.com.cubosoft.avamed.modelo.core.Ciediez;
import ec.com.cubosoft.avamed.modelo.core.Ciediez;
import ec.com.cubosoft.avamed.modelo.nextla.LisanaPrac;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;


/**
 *
 * @author JP
 */
public class AnalistRender implements ComboitemRenderer<Object> {

//    @Override
//    public void render(Listitem item, Object data, int index) throws Exception {
//     
//    }
    @Override
    public void render(Comboitem cmbtm, Object data, int i) throws Exception {
        LisanaPrac list = (LisanaPrac) data;
        cmbtm.setValue(list);
//        cmbtm.setId(cie.getCodCie());
//        new Listcell(cie.getCodCie()).setParent(cmbtm);
//        new Listcell(cie.getDescripcion()).setParent(cmbtm);
        cmbtm.setLabel(list.getDesAna());
//        cmbtm.setDisabled(true);
      }

}

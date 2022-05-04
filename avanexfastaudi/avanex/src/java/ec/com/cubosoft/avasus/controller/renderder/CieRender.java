
package ec.com.cubosoft.avasus.controller.renderder;


//import ec.com.cubosoft.avamed.modelo.core.Ciediez;
import ec.com.cubosoft.avamed.modelo.core.Ciediez;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;


/**
 *
 * @author JP
 */
public class CieRender implements ComboitemRenderer<Object> {

//    @Override
//    public void render(Listitem item, Object data, int index) throws Exception {
//     
//    }
    @Override
    public void render(Comboitem cmbtm, Object data, int i) throws Exception {
        Ciediez cie = (Ciediez) data;
        cmbtm.setValue(cie);
//        cmbtm.setId(cie.getCodCie());
//        new Listcell(cie.getCodCie()).setParent(cmbtm);
//        new Listcell(cie.getDescripcion()).setParent(cmbtm);
        cmbtm.setLabel(cie.getDescripcion());
//        cmbtm.setDisabled(true);
      }

}

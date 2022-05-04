/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avasus.controller.renderder;

import ec.com.cubosoft.avamed.modelo.core.Farmacos;
import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.ingreso.PracticaXOrden;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import java.util.List;
import org.zkoss.zul.Button;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

/**
 *
 * @author JP
 */
public class presentacionRenderer implements RowRenderer {

//   
//    @Override
//    public void render(Comboitem cmbtm, Object t, int i) throws Exception {
//        Farmacos farmaco = (Farmacos) t;
//        cmbtm.setValue(farmaco);
////          cmbtm.setLabel(farmaco.getNomGenerico());
//         cmbtm.setLabel(farmaco.getNomGenerico());
////        new Listcell(String.valueOf(farmaco.getId())).setParent(cmbtm);
//    }
    @Override
    public void render(Row row, Object t, int i) throws Exception {
        final Farmacos farm = (Farmacos) t;
        row.setValue(farm);
        new Label(farm.getNomGenerico()).setParent(row);
        new Label(farm.getPresentacion()).setParent(row);
        new Label(farm.getConcentracion()).setParent(row);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avasus.controller.renderder;

import ec.com.cubosoft.avamed.modelo.nextla.LisanaPrac;
import ec.com.cubosoft.avamed.modelo.practica.NombreP;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author JP
 */
public class PracticaRenderer implements ListitemRenderer{
//public class PracticaRenderer implements RowRenderer{
//    @Override
//    public void render(Row row, Object t, int i) throws Exception {
//        LisanaPrac practica = (LisanaPrac) t;
//		row.setValue(practica);
//		new Label(String.valueOf(practica.getDesAna())).setParent(row);
//		new Label(practica.getCodPrac().toString()).setParent(row);
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    @Override
     public void render(Listitem item, Object data, int index) throws Exception {
		NombreP practica = (NombreP) data;
		item.setValue(practica);
		new Listcell(String.valueOf(practica.getId())).setParent(item);
		new Listcell(practica.getAbreviatura()).setParent(item);
	}
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avasus.controller.renderder;

import ec.com.cubosoft.avamed.modelo.persona.ResultadoGrafico;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 *
 * @author DESARROLLO
 */
public class ResultadoGraficoRenderer implements ListitemRenderer{

    @Override
    public void render(Listitem lstm, Object t, int i) throws Exception {
        ResultadoGrafico resultadoGrafico = (ResultadoGrafico) t;
        lstm.setValue(resultadoGrafico);
        new Listcell(String.valueOf(resultadoGrafico.getId())).setParent(lstm);
        new Listcell(resultadoGrafico.getDescripcion()).setParent(lstm);
//throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
//    @Override
//     public void render(Listitem item, Object data, int index) throws Exception {
//		NombreP practica = (NombreP) data;
//		item.setValue(practica);
//		new Listcell(String.valueOf(practica.getId())).setParent(item);
//		new Listcell(practica.getAbreviatura()).setParent(item);
//	}
    
}

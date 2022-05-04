package ec.com.cubosoft.avamed.coneccion;

import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.*;
import org.zkoss.zul.Html;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Juan Pablo
 */
public class controladora extends GenericForwardComposer {

    Button applet;
    Button click;
    Button btnAplet;
    Window winAudio;

    public void onClick$click() throws InterruptedException {
        Window winExamen;
        winExamen = new Window();
        winExamen.setStyle("border: 2px black solid;border-color: #8FBC8F;");
        winExamen.setPosition("center");
        winExamen.setWidth("800px");
        winExamen.setHflex("700px");
        winExamen.setBorder("none");
        winExamen.setParent(winAudio);
        winExamen.doModal();
        String appletDef = "<applet id=\"idApplet\"  width=\"100%\" height=\"100%\" code=\"examen.ImagenExamen.class\" MAYSCRIPT ></applet>";
        final Html htmlComponent = new Html();
        htmlComponent.setContent(appletDef);
        htmlComponent.setParent(winExamen);
        final Button salir = new Button();
        salir.setHeight("25px");
        salir.setWidth("25px");
        salir.setImage("/images/salir.jpg");
        salir.setParent(winExamen);
     }

//                                             btnAplet.addEventListener(Events.ON_CLICK, new EventListener() {
//
//                                                    @Override
//                                                    public void onEvent(Event event) throws Exception {
//                                                        Window winh = (Window) Executions.createComponents("examenAudiometria.zul", null, null);
//                                                        winh.setHeight("60%");
//                                                        winh.setWidth("70%");
//                                                        winh.setMaximizable(true);
//                                                       winh.doModal();
//                                                   }
//                                                });
    public void onClick$btnAplet() {
//        alert("click btnAplet");
    }
//    public void onCreate$WinExamenAudiometria()
//    {
////        String appletDef= "<applet id=\"idApplet\"  width=\"100%\" height=\"100%\" code=\"examen.ImagenExamen.class\" MAYSCRIPT ></applet>";
////       this.htmlComponent.setContent(appletDef);
//    }
//        JSObject obj = null;
//        String c=obj.eval("derecho").toString();
//       String appletDef= "<applet   width=\"100%\" height=\"100%\" code=\"ImagenExamen\"> ";
//        <param name=\"examen\" value=\"parametro\"</param></applet>
}
//}


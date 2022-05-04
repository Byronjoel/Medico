/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.procesos;

import ec.com.cubosoft.avamed.mensajes.AdmMensajes;
import ec.com.cubosoft.avamed.mensajes.TipoMensaje;
import java.io.File;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

/**
 *
 * @author Administrador
 */
public class CreacionXml {

    public Document crearDocumento() throws Exception {
        Document newDoc = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            DOMImplementation implementation = builder.getDOMImplementation();
            newDoc = implementation.createDocument(null, "resultados", null);
            newDoc.setXmlVersion("1.0"); // asignamos la version de nuestro XML
            newDoc.setXmlStandalone(true);
        } catch (Exception e) {
            AdmMensajes admMensajes = new AdmMensajes(TipoMensaje.ERROR_GRABAR, e);
            e.printStackTrace(System.out);
        }
        return newDoc;
    }

    public void GuardarDocumento(Document doc, String nombre) {
        try {
            String path = "";
            Source source = new DOMSource(doc);
            String osName = System.getProperty("os.name");
            if (osName.toUpperCase().indexOf("WINDOWS") == 0) {
                path = "C:/glassfishv3/XMLs/Reportes/" ;
            } else if (osName.toUpperCase().indexOf("LINUX") == 0) {
                path = "/usr/share/glassfishv3/XMLs/Reportes/" ;
            }
            File archivo = new File(path + nombre);
            Result result = new StreamResult(archivo); //nombre del archivo
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(source, result);

        } catch (TransformerException e) {
            e.printStackTrace(System.out);
        }
    }
}

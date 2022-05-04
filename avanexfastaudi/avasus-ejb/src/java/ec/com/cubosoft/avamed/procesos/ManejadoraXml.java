/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.procesos;

//import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import ec.com.cubosoft.avamed.jpa.AdministradorGenericoBean;
import ec.com.cubosoft.avamed.jpa.AdministradorGlobalBean;
import ec.com.cubosoft.avamed.modelo.practica.FormatoXPractica;
import ec.com.cubosoft.avamed.negocio.ConvertirDocumento;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.xml.parsers.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author Patty Amoroso
 */
public class ManejadoraXml {

    CreacionXml NXML = new CreacionXml();
    //<editor-fold defaultstate="collapsed" desc="MANEJO DE PLANTILLA XML">

    public Document getDocumento(String doc_nombre, Integer tipo) {
        String path = "";
        String osName = System.getProperty("os.name");
        if (tipo == 0) {
            if (osName.toUpperCase().indexOf("WINDOWS") == 0) {
                path = "C:/glassfishv3/XMLs/" + doc_nombre;
            } else if (osName.toUpperCase().indexOf("LINUX") == 0) {
                path = "/usr/share/glassfishv3/XMLs/" + doc_nombre;
            }

        } else {
            if (osName.toUpperCase().indexOf("WINDOWS") == 0) {
                path = "C:/glassfishv3/XMLs/Reportes/" + doc_nombre;
            } else if (osName.toUpperCase().indexOf("LINUX") == 0) {
                path = "/usr/share/glassfishv3/XMLs/Reportes/" + doc_nombre;
            }
        }

        Document doc = null;
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            doc = docBuilder.parse(path);
            doc.getDocumentElement().normalize();
        } catch (SAXException e) {
            e.printStackTrace(System.out);
        } catch (IOException e) {
            e.printStackTrace(System.out);
        } catch (ParserConfigurationException e) {
            e.printStackTrace(System.out);
        }

        return doc;
    }

    public NodeList getlistanodos(String tagref, Document docref) {
        NodeList lista = docref.getElementsByTagName(tagref);
        return lista;
    }

    /**
     * metodo para obtener el valor de un atributo de acuerdo al xml
     *
     * @param tag nombre del nodo
     * @param pos la posiscion del atributo
     * @param doc
     * @return retorna el valor del atributo
     */
    public String valorAtributo(String tag, Integer pos, Document doc) {
        String ref = null;
        Element elemref;
        NamedNodeMap nodeatributos;
        NodeList listRef = doc.getElementsByTagName(tag);
        Node nodoRef = listRef.item(0);
        if (nodoRef.getNodeType() == Node.ELEMENT_NODE) {
            elemref = (Element) nodoRef;
            nodeatributos = elemref.getAttributes();
            Node val = nodeatributos.item(pos);
            ref = val.getNodeValue();
        }
        return ref;
    }
// </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="MANEJO DE REPORTES">
    private Context contexto;
    private AdministradorGenericoBean admJPAG;

    public List<FormatoXPractica> abrirReportes(String idPractica) throws ParserConfigurationException, NamingException {
        //Document doc1 = null;
        List<FormatoXPractica> Formatos = null;
          AdministradorGlobalBean admJPAG;
        contexto = new InitialContext();
        admJPAG = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        Formatos = admJPAG.getTodosXParametro("FormatoXPractica", "idPractica", idPractica, Integer.class, "idHoja", true);
        return Formatos;
    }

    public List<String> obtenerIdFormato(String XMLS) {
        Node NPagina;
        Element elemento;
        Document doc1 = null;
        List<String> idsFormatos = new ArrayList<String>();
        doc1 = ConvertirDocumento.getConvertirDocumentoDocument(XMLS);
        NodeList listaHijos = doc1.getChildNodes();
        try {
            listaHijos = listaHijos.item(0).getChildNodes();
            for (int t = 1; t < listaHijos.getLength(); t++) {
                NPagina = listaHijos.item(t);
                if (NPagina.getNodeType() == Node.ELEMENT_NODE) {
                    elemento = (Element) NPagina;
                    if (elemento.getNodeName() != null) {
                        String NamNText = elemento.getTagName();
                        if (!(NamNText.equals("datos_generales_standar"))) {
                            String c = elemento.getAttribute("Id");
                            String idforma = elemento.getAttribute("IdFormato");
                            if (!(idforma.isEmpty())) {
                                idsFormatos.add(idforma);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Clase ManejadoraXml Metodo obtenerIdFormato" + e.getMessage());
        }
        return idsFormatos;
    }

    public String valorTag(String XMLS, String tag) {
        
        Node NPagina;
        Element elemento;
        Document doc1 = null;
        String valor = null;
        doc1 = ConvertirDocumento.getConvertirDocumentoDocument(XMLS);
        NodeList listaHijos = doc1.getChildNodes();
        try {
            int j = listaHijos.getLength();
            listaHijos = listaHijos.item(0).getChildNodes();
            j=listaHijos.getLength();
            for (int t = 1; t < listaHijos.getLength(); t++) {
                NPagina = listaHijos.item(t);
                if (NPagina.getNodeType() == Node.ELEMENT_NODE) {
                    elemento = (Element) NPagina;
                    if (elemento.getNodeName() != null) {
                        String NamNText = elemento.getTagName();
                        if ((NamNText.equals(tag))) {
                            valor = elemento.getNodeValue();
                             valor = elemento.getTextContent();
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Clase ManejadoraXml Metodo obtenerIdFormato" + e.getMessage());
        }
        return valor;
    }

}

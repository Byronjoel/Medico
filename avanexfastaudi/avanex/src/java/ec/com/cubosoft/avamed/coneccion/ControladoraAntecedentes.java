package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
import ec.com.cubosoft.avamed.modelo.core.Perxuser;
import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import ec.com.cubosoft.avamed.modelo.nextla.SUsuar;
import ec.com.cubosoft.avamed.modelo.nextla.sessionOk;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import ec.com.cubosoft.avamed.modelo.practica.FormatoXAntecedentes;
import ec.com.cubosoft.avamed.modelo.persona.XmlAntecedentes;
import ec.com.cubosoft.avamed.negocio.ConvertirDocumento;
import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import ec.com.cubosoft.avamed.procesos.ManejadoraXml;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zul.*;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 * @author Juan Pablo Chavez
 * @version 1.0.1
 * @version 1.0.2
 *
 * @author Patricia Amoroso
 * @version 1.0
 */
public class ControladoraAntecedentes extends GenericForwardComposer {

    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Datos fijos Sesion">
    private Nombre medico;
    private CsUsuarios objUsuarioP;
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Datos Informe">
    private Historia historia;
    private Orden orden;
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="InicioSesinon">
    AdmiNegocio admNegocio;
    Window WinHistoria;
    SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
    Rows rowsAnt;
    Grid gridAnt;
    boolean stado;
    boolean modifica;
    private Document docAnteHis;
    List listaper = null;
    SUsuar usuarioN = null;
    private boolean abierto;
    private boolean impr;
    private boolean carga;
    private boolean edit;
    private boolean copiar;
    String usuario = "";
    Integer IdPractica;

    private void modificarSession() {
        ProcesosSession admiSessionUsuario = new ProcesosSession();
        sessionOk objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
        historia = objsessiActica.getObjHistoria();
        IdPractica = objsessiActica.getIdPractica();
        orden = objsessiActica.getObjOrden();
        admNegocio = new AdmiNegocio();
        stado = objsessiActica.getEstado();
        docAnteHis = (Document) objsessiActica.getDocAntecedentes();
//        docAnteHis = (Document) admiSessionUsuario.ObtenerAtributoSession(11, desktop.getSession());
        if (objsessiActica.getTipo() == 1) {//nextlab
            listaper = objsessiActica.getPerUsuNext();
            usuarioN = objsessiActica.getUsuarioN();
            usuario = usuarioN.getUsuario();
            Perxuser objpe = objsessiActica.getPerUsuNex();
            abierto = false;
            impr = false;
            carga = false;
            edit = false;
            copiar = false;
            if (objpe.getPerUpload() == 1) {
                carga = false;
            } else {
                carga = true;
            }
            if (objpe.getPerAbto() == 1) {
                abierto = false;
            } else {
                abierto = true;
            }
            if (objpe.getPriVis() == 0) {
                impr = false;
            } else {
                impr = true;
            }
            if (objpe.getPerCyp() == 1) {
                copiar = false;
            } else {
                copiar = true;
            }
        } else {
            listaper = objsessiActica.getPerUsuAva();
            objUsuarioP = objsessiActica.getUsuarioP();
            usuario = objUsuarioP.getUsuario();

            if (objUsuarioP.getPerUpload() == 1) {
                carga = false;
            } else {
                carga = true;
            }
            if (objUsuarioP.getPerAbto() == 1) {
                abierto = false;
            } else {
                abierto = true;
            }
            if (objUsuarioP.getPriVis() == 0) {
                impr = false;
            } else {
                impr = true;
            }
            if (objUsuarioP.getPerCyp() == 1) {
                copiar = false;
            } else {
                copiar = true;
            }
        }
        if (objUsuarioP != null) {
            if (objUsuarioP.getMedicos().size() > 0) {
                medico = objUsuarioP.getMedicos().get(0);

            } else {
                Map<String, Object> wSQL = new HashMap<>();
                List oSQL = new ArrayList();
                wSQL.put("id ?=", 0);
                List data = null;
                try {
                    data = admNegocio.getData(new Nombre(), wSQL, null, oSQL);
                } catch (Exception e) {
                }

                if (data.size() > 0) {
                    medico = (Nombre) data.get(0);
                }
            }
            admNegocio = new AdmiNegocio();
        } else {
            Map<String, Object> wSQL = new HashMap<>();
            List oSQL = new ArrayList();
            wSQL.put("id ?=", 0);
            List data = null;
            try {
                data = admNegocio.getData(new Nombre(), wSQL, null, oSQL);
            } catch (Exception e) {
            }

            if (data.size() > 0) {
                medico = (Nombre) data.get(0);
            }
        }
        admNegocio = new AdmiNegocio();
        try {
            findAntecedenteLoad();
        } catch (NamingException ex) {
            Logger.getLogger(ControladoraAntecedentes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ControladoraAntecedentes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int numFormatosAnteced;
    Textbox numFormato;
    Textbox modificar;
    private Document docAntecedentes;
    private boolean visibleFrameCarga;
    Document docrecet;
    DateFormat formatoHora = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    XmlAntecedentes objAntecedenes;

    public XmlAntecedentes getObjAntecedenes() {
        return objAntecedenes;
    }

    public void setObjAntecedenes(XmlAntecedentes objAntecedenes) {
        this.objAntecedenes = objAntecedenes;
    }

    private void findAntecedenteLoad() throws InterruptedException, NamingException {
        boolean nuevo = false;
        Map<String, Object> wSQL = new HashMap<>();
        wSQL.put("idPractica ?=", IdPractica);
        wSQL.put("idEmpresa ?=", orden.getOrganizacion().getId());
        wSQL.put("idHistoria.id ?=", historia.getId());
        wSQL.put("orden.id ?=", orden.getId());
        wSQL.put("lockReg ?=", 0);
        List XMLAntecentes = admNegocio.getData(new XmlAntecedentes(), wSQL, null, null);
        if (XMLAntecentes.size() == 1) {
            objAntecedenes = (XmlAntecedentes) XMLAntecentes.get(0);
            setObjAntecedenes(objAntecedenes);
            ProcesosSession admiSessionUsuario = new ProcesosSession();
            sessionOk objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
            objsessiActica.setObjXmlAntecedentes(objAntecedenes);
            admiSessionUsuario.AgregarAtributoSession(2, objsessiActica, desktop.getSession());
            if (stado) {
                modificar.setValue("T");
                loadGridResultados(stado);
            } else {
                modificar.setValue("F");
                loadGridResultados(stado);
            }
        } else {
            if (XMLAntecentes.size() > 1) {
                Messagebox.show("Antecedentes duplicado ext 720", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
            } else {
                if (docAnteHis == null) {
                    //nuevo

                    loadEntradasAntecedentes();

                } else {//existe historial y nuevo antecedentes
                    recuparardatos();
                }
            }
        }
        gridAnt.renderAll();
        gridAnt.invalidate();
        gridAnt.setVisible(true);
    }

    private void recuparardatos() {
        NodeList listTexFrames = null;
        Node nodoText, nodoFrame;
        Element elemento;
        List dataGrid = new ArrayList();
        rowsAnt.getChildren().clear();
        try {
            Map<String, Object> wSQL = new HashMap<String, Object>();
            List oSQL = new ArrayList();
            Integer idPrac;
            wSQL.put("idPractica ?=", IdPractica);
            if (orden.getOrganizacion().getCodRef() > 0) {
                wSQL.put("idEmpresa ?=", orden.getOrganizacion().getCodRef());
            } else {
                wSQL.put("idEmpresa ?=", orden.getOrganizacion().getId());
            }
     //       wSQL.put("idEmpresa ?=", orden.getOrganizacion().getId());
            wSQL.put("lockReg ?=", 0);
            oSQL.add("idHoja");
            List<Object> Formatos = null;
            try {
                Formatos = admNegocio.getDataAsc(new FormatoXAntecedentes(), wSQL, null, oSQL);

            } catch (NamingException ex) {
                System.out.println(objUsuarioP.getUsuario() + "  Formatos = admNegocio.getDataAsc(new FormatoXPractica(), wSQL, null, oSQL); ");
                Logger
                        .getLogger(ControladoraHistoria.class
                                .getName()).log(Level.SEVERE, null, ex);
            }
            if (!(Formatos.isEmpty())) {
                numFormatosAnteced = Formatos.size() + 1;
                numFormato.setValue(String.valueOf(numFormatosAnteced));
                for (int t = 0; t < Formatos.size(); t++) {
                    FormatoXAntecedentes formatPage = (FormatoXAntecedentes) Formatos.get(t);
                    String xml = null;
                    try {
                        xml = new String(formatPage.getXml(), "UTF8");
                    } catch (Exception e) {
                        Messagebox.show("Formato en pruebas");
                        reset();
                    }
                    DocumentBuilder docBuilder = null;
                    try {
                        docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                        docAntecedentes = docBuilder.parse(new InputSource(new StringReader(xml)));
                    } catch (ParserConfigurationException ex) {
                        System.out.println(objUsuarioP.getUsuario() + "   docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); ");
                        Logger
                                .getLogger(ControladoraHistoria.class
                                        .getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        System.out.println(objUsuarioP.getUsuario() + "  docAntecedentes = docBuilder.parse(new InputSource(new StringReader(xml))); ");
                        Logger
                                .getLogger(ControladoraHistoria.class
                                        .getName()).log(Level.SEVERE, null, ex);
                    }

                    ManejadoraXml AdmXml = new ManejadoraXml();
                    NodeList lista = AdmXml.getlistanodos("frame", docAntecedentes);
                    Group fila = new Group();
                    fila.setId(formatPage.getId().toString());
                    fila.setLabel("PAGINA" + t + "  ID Formato: " + formatPage.getIdHoja());
                    fila.setVisible(false);
                    fila.setParent(rowsAnt);
                    for (int k = 0; k < lista.getLength(); k++) {
                        listTexFrames = lista.item(k).getChildNodes();
                        nodoFrame = lista.item(k);
                        visibleFrameCarga = true; //lista de frame o grupos
                        listTexFrames = loadFrame(nodoFrame);
                        if (listTexFrames.getLength() > 0) {
                            dataGrid.add(nodoFrame);
                        }
                        if (listTexFrames != null) {
                            for (int i = 0; i < listTexFrames.getLength(); i++) {
                                nodoText = listTexFrames.item(i);
                                if (nodoText.getNodeType() == Node.ELEMENT_NODE) {
                                    dataGrid.add(nodoText);
                                    elemento = (Element) nodoText;
                                    if (elemento.getNodeName() != null) {
                                        try {
                                            //String valor = elemento.getTextContent();
                                            loadElement(elemento, nodoText, true);
                                        } catch (InterruptedException ex) {
                                            System.out.println(objUsuarioP.getUsuario() + " Error al crear elemento " + elemento.getTagName());
                                            alert("Error al crear elemento " + ex.getMessage());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    rowsAnt.setVisible(true);
                    modificar.setValue("N");
                }
            } else {
                Messagebox.show("No se encontraron formatos cargados " + orden.getOrganizacion().getAbreviatura());
                reset();
            }
        } catch (IOException e) {
            System.out.println(objUsuarioP.getUsuario() + "  loadGridEntradas " + orden.getId());
            throw new RuntimeException(e);
        }

    }

    private String buscarValor(String datoB) {
        Document doc1 = null;
        Node NPagina;
        Node Nhijo;
        Node NNieto;
        String valRecu = null;
        Element elemento;
        //   String tagBusca = elemento.getTagName();
        doc1 = docAnteHis;
        NodeList listaPadres = doc1.getChildNodes();
        NPagina = listaPadres.item(0);
        listaPadres = NPagina.getChildNodes();
        NodeList listaHijos;
        NodeList listaNietos;
        for (int t = 0; t < listaPadres.getLength(); t++) {
            NPagina = listaPadres.item(t);
            if (NPagina.getNodeType() == Node.ELEMENT_NODE) {
                elemento = (Element) NPagina;
                if (elemento.getNodeName() != null) {
                    listaHijos = NPagina.getChildNodes();
                    for (int j = 0; j < listaHijos.getLength(); j++) {
                        Nhijo = listaHijos.item(j);
                        if (Nhijo.getNodeType() == Node.ELEMENT_NODE) {
                            elemento = (Element) Nhijo;
                            if (elemento.getNodeName() != null) {
                                listaNietos = Nhijo.getChildNodes();
                                for (int x = 0; x < listaNietos.getLength(); x++) {
                                    NNieto = listaNietos.item(x);
                                    if (NNieto.getNodeType() == Node.ELEMENT_NODE) {
                                        elemento = (Element) NNieto;
                                        if (elemento.getNodeName() != null) {
                                            String NamNText = elemento.getTagName();
                                            //   System.out.println("D bus " + datoB + " D Rec " + NamNText);
                                            if (NamNText.equals(datoB)) {
                                                valRecu = elemento.getTextContent();
                                                x = listaNietos.getLength();
                                                j = listaHijos.getLength();
                                                t = listaPadres.getLength();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return valRecu;
    }

    private void reset() {
        numFormatosAnteced = 0;
        numFormato.setValue("0");
    }

    public void onCreate$antecedentes() {
        try {
            modificarSession();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CARGA DE FORMATO">
    private void loadEntradasAntecedentes() {
        NodeList listTexFrames = null;
        Node nodoText, nodoFrame;
        Element elemento;
        List dataGrid = new ArrayList();
        rowsAnt.getChildren().clear();
        try {
            Map<String, Object> wSQL = new HashMap<String, Object>();
            List oSQL = new ArrayList();
            Integer idPrac;
            wSQL.put("idPractica ?=", IdPractica);
            if (orden.getOrganizacion().getCodRef() > 0) {
                wSQL.put("idEmpresa ?=", orden.getOrganizacion().getCodRef());
            } else {
                wSQL.put("idEmpresa ?=", orden.getOrganizacion().getId());
            }
            wSQL.put("lockReg ?=", 0);
            oSQL.add("idHoja");
            List<Object> Formatos = null;
            try {
                Formatos = admNegocio.getDataAsc(new FormatoXAntecedentes(), wSQL, null, oSQL);

            } catch (NamingException ex) {
                System.out.println(objUsuarioP.getUsuario() + "  Formatos = admNegocio.getDataAsc(new FormatoXPractica(), wSQL, null, oSQL); ");
                Logger
                        .getLogger(ControladoraHistoria.class
                                .getName()).log(Level.SEVERE, null, ex);
            }
            if (!(Formatos.isEmpty())) {
                numFormatosAnteced = Formatos.size() + 1;
                numFormato.setValue(String.valueOf(numFormatosAnteced));
                for (int t = 0; t < Formatos.size(); t++) {
                    FormatoXAntecedentes formatPage = (FormatoXAntecedentes) Formatos.get(t);
                    String xml = null;
                    try {
                        xml = new String(formatPage.getXml(), "UTF8");
                    } catch (Exception e) {
                        Messagebox.show("Formato en pruebas");
                        reset();
                    }
                    DocumentBuilder docBuilder = null;
                    try {
                        docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                        docAntecedentes = docBuilder.parse(new InputSource(new StringReader(xml)));
                    } catch (ParserConfigurationException ex) {
                        System.out.println(objUsuarioP.getUsuario() + "   docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); ");
                        Logger
                                .getLogger(ControladoraHistoria.class
                                        .getName()).log(Level.SEVERE, null, ex);
                    } catch (SAXException ex) {
                        System.out.println(objUsuarioP.getUsuario() + "  docAntecedentes = docBuilder.parse(new InputSource(new StringReader(xml))); ");
                        Logger
                                .getLogger(ControladoraHistoria.class
                                        .getName()).log(Level.SEVERE, null, ex);
                    }

                    ManejadoraXml AdmXml = new ManejadoraXml();
                    NodeList lista = AdmXml.getlistanodos("frame", docAntecedentes);
                    Group fila = new Group();
                    fila.setId(formatPage.getId().toString());
                    fila.setLabel("PAGINA" + t + "  ID Formato: " + formatPage.getIdHoja());
                    fila.setVisible(false);
                    fila.setParent(rowsAnt);
                    for (int k = 0; k < lista.getLength(); k++) {
                        listTexFrames = lista.item(k).getChildNodes();
                        nodoFrame = lista.item(k);
                        visibleFrameCarga = true; //lista de frame o grupos
                        listTexFrames = loadFrame(nodoFrame);
                        if (listTexFrames.getLength() > 0) {
                            dataGrid.add(nodoFrame);
                        }
                        if (listTexFrames != null) {
                            for (int i = 0; i < listTexFrames.getLength(); i++) {
                                nodoText = listTexFrames.item(i);
                                if (nodoText.getNodeType() == Node.ELEMENT_NODE) {
                                    dataGrid.add(nodoText);
                                    elemento = (Element) nodoText;
                                    if (elemento.getNodeName() != null) {
                                        try {
                                            loadElement(elemento, nodoText, false);
                                        } catch (InterruptedException ex) {
                                            System.out.println(objUsuarioP.getUsuario() + " Error al crear elemento " + elemento.getTagName());
                                            alert("Error al crear elemento " + ex.getMessage());
                                        }
                                    }
                                }
                            }
                        }
                    }
                    rowsAnt.setVisible(true);
                    modificar.setValue("N");
                }
            } else {
                Messagebox.show("No se encontraron formatos cargados " + orden.getOrganizacion().getAbreviatura());
                reset();
            }
        } catch (IOException e) {
            System.out.println(objUsuarioP.getUsuario() + "  loadGridEntradas " + orden.getId());
            throw new RuntimeException(e);
        }
    }

    private NodeList loadFrame(Node NFrame) {
        NodeList listTextFrames = null;
        NodeList listanodoproperty = null;
        Group grupo;
        try {
            if (NFrame.getNodeType() == Node.ELEMENT_NODE) {
                Element elemento = (Element) NFrame;
                if (elemento.getNodeName() != null) {
                    String NomNodo = elemento.getTagName();
                    if (NomNodo.equals("frame")) {
                        //es un grupo y tomo los textField del grupo
                        listTextFrames = NFrame.getChildNodes();
                        if (listTextFrames.getLength() > 0) {
                            //tomo las propiedades del frame
                            listanodoproperty = listTextFrames.item(1).getChildNodes();
                        }
                        String nomGrupo = "S/N";
                        String n = "S/D";
                        String sexoVisible = "";
                        String areaa = "0";
                        boolean frameVisible = true;
                        for (int j = 0; j < listanodoproperty.getLength(); j++) {
                            Node tagitem = listanodoproperty.item(j);
                            if (tagitem.getNodeType() == Node.ELEMENT_NODE) {
                                elemento = (Element) tagitem;
                                NamedNodeMap item_atributos = elemento.getAttributes();
                                Node valname = item_atributos.item(0);
                                Node valvalor = item_atributos.item(1);
                                if ((valname != null) && (valvalor != null)) {
                                    String va = valname.getNodeValue();
                                    if (va.equals("nombre")) {
                                        nomGrupo = valvalor.getNodeValue();
                                    }
                                    if (va.equals("descripcion")) {
                                        n = valvalor.getNodeValue();
                                    }
                                    if (va.equals("sexo")) {
                                        sexoVisible = valvalor.getNodeValue();
                                    }
                                    if (va.equals("area")) {
                                        areaa = valvalor.getNodeValue();
                                        if (areaa.contains("1")) {
                                            frameVisible = false;
                                        }
                                    }
                                }
                            }
                        }
                        if (!(sexoVisible.isEmpty())) {
                            if (!(sexoVisible.equalsIgnoreCase(historia.getSexo()))) {
                                n = "NO APLICA PARA:" + sexoVisible;
                                visibleFrameCarga = false;
                            }
                        }
                        grupo = new Group();
                        grupo.setLabel(n);
                        grupo.setStyle("border: 1px outset; background:#b5dbe5; color: black;text-align: center;");
                        grupo.setVisible(frameVisible);
                        if (nomGrupo.equals("S/D")) {
                            nomGrupo = " ";
                        }
                        try {
                            grupo.setId(nomGrupo);
                            grupo.setParent(rowsAnt);
                        } catch (Exception e) {
                            alert("No agregado " + nomGrupo + " " + e.getMessage());
                        }
                    }
                }
            }
            return listTextFrames;
        } catch (Exception e) {
            return null;
        }

    }

    private void loadElement(Element elemento, Node NText, boolean si) throws InterruptedException {
        try {
            NodeList listaNodoTextFile, listaNodoProperty = null;
            Label lblOrden, lblNombre, lblDescripcion;
            Node tagItem;
            NamedNodeMap item_atributos;
            Row row;
            String NamNText = elemento.getTagName();
            if (!visibleFrameCarga) {
                return;
            }
            if (NamNText.equals("textField")) {
                listaNodoTextFile = NText.getChildNodes();
                lblOrden = new Label();
                lblNombre = new Label("S/N");
                lblDescripcion = new Label("S/D");
                String tipoDato = "T", itemsLista = "", obligatorio = "", valorDef = "";
                int lineas = 1;
                String orden = "";
                if (listaNodoTextFile.getLength() > 0) {
                    listaNodoProperty = listaNodoTextFile.item(1).getChildNodes();
                }
                if (listaNodoProperty.getLength() == 1) {
                    orden = "1";
                    lblOrden.setValue(orden);
                    tipoDato = "sinPropiedades";
                }
                String valHistoria = "";
                //RECORRO LAS PROPIEDADES DE LAS TEXTFIELD
                for (int j = 0; j < listaNodoProperty.getLength(); j++) {
                    tagItem = listaNodoProperty.item(j);
                    if (tagItem.getNodeType() == Node.ELEMENT_NODE) {
                        elemento = (Element) tagItem;
                        item_atributos = elemento.getAttributes();
                        //obtener del xml el atributos show
                        Node valname = item_atributos.item(0);
                        Node valvalor = item_atributos.item(1);
                        if ((valname != null) && (valvalor != null)) {
                            String va = valname.getNodeValue();
                            switch (va) {
                                case "orden":
                                    orden = valvalor.getNodeValue();
                                    lblOrden.setValue(valvalor.getNodeValue());
                                    break;
                                case "nombre":
                                    valHistoria = "";
                                    lblNombre.setValue(valvalor.getNodeValue());
                                    if (si) {
                                        valHistoria = buscarValor(valvalor.getNodeValue());
                                    }
                                    lblNombre.setSclass("nombre-resultado");
                                    break;
                                case "descripcion":
                                    lblDescripcion.setValue(valvalor.getNodeValue());
                                    lblDescripcion.setSclass("descripcion-resultado");
                                    break;
                                case "lineas":
                                    lineas = Integer.parseInt(valvalor.getNodeValue());
                                    break;
                                case "item_lista":
                                    itemsLista = valvalor.getNodeValue();
                                    break;
                                case "tipo_dato":
                                    if (lblNombre.getValue().contains("dx")) {
                                        tipoDato = "G";
                                    } else {
                                        tipoDato = valvalor.getNodeValue();
                                    }
                                    break;
                                case "obligatorio":
                                    obligatorio = valvalor.getNodeValue();
                                    break;
                                case "valor_defecto":
                                    valorDef = valvalor.getNodeValue();

                                    break;
                                default:
                                    Messagebox.show("PROPIEDAD DESCONOCIDA: '" + va + "'", "Información", Messagebox.OK, Messagebox.INFORMATION);
                                    break;
                            }
                        }
                        if (valHistoria != null) {
                            if (!valHistoria.isEmpty()) {
                                valorDef = valHistoria;
                            }
                        }
                    }
                }

                //agregar las columnas
                if ((!(orden.equals("0"))) && (!(orden.isEmpty()))) {//Si es 0 no se hace nada se salta
                    row = new Row();
                    row.setSclass("row-resultado");
                    row.appendChild(lblDescripcion);
                    switch (tipoDato) {
                        case "T":
                            final Textbox nText = new Textbox();
                            if (obligatorio.equals("0")) {
                                nText.setConstraint("no empty, no future: Ingrese Dato");
                            }
                            switch (lineas) {
                                case 0: {
                                    nText.setMultiline(true);
                                    nText.addEventListener("onDoubleClick", new org.zkoss.zk.ui.event.EventListener() {
                                        @Override
                                        public void onEvent(Event event) throws Exception {
                                            Window winEdicion = new Window();
                                            String windowMessage;
                                            windowMessage = "edicion-aux.zul";
                                            winEdicion.setWidth("550px");
                                            Executions.createComponents(windowMessage, winEdicion, null);
                                            final Textbox edicion = (Textbox) winEdicion.getFellow("edit-aux", true);
                                            edicion.setValue(nText.getValue());
                                            edicion.setSclass("edit-resultado");
                                            if (objUsuarioP.getPerCyp() == 1) {
                                                edicion.setCtrlKeys("^c^v");
                                                edicion.addEventListener(Events.ON_CTRL_KEY, new org.zkoss.zk.ui.event.EventListener() {
                                                    @Override
                                                    public void onEvent(Event event) throws Exception {
                                                        int keyCode = ((KeyEvent) event).getKeyCode();
                                                        if (keyCode == 67 || keyCode == 86) {
                                                        }
                                                    }
                                                });
                                                edicion.addEventListener(Events.ON_RIGHT_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                                                    @Override
                                                    public void onEvent(Event event) throws Exception {
                                                    }
                                                });
                                            }
                                            winEdicion.addEventListener(Events.ON_CLOSE, new org.zkoss.zk.ui.event.EventListener() {
                                                @Override
                                                public void onEvent(Event event) throws Exception {
                                                    nText.setValue(edicion.getValue());
                                                }
                                            });
                                            winEdicion.setTitle("Edición de resultado");
                                            winEdicion.setClosable(true);
                                            winEdicion.setSizable(true);
                                            winEdicion.setId("winEditAuxiliar");
                                            winEdicion.setParent(WinHistoria);
                                            winEdicion.doModal();
                                        }
                                    });
                                    if (objUsuarioP.getPerCyp() == 1) {
                                        nText.setCtrlKeys("^c^v");
                                        nText.addEventListener(Events.ON_CTRL_KEY, new org.zkoss.zk.ui.event.EventListener() {

                                            @Override
                                            public void onEvent(Event event) throws Exception {
                                                int keyCode = ((KeyEvent) event).getKeyCode();
                                                if (keyCode == 67 || keyCode == 86) {
                                                }
                                            }
                                        });
                                        nText.addEventListener(Events.ON_RIGHT_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                                            @Override
                                            public void onEvent(Event event) throws Exception {
                                            }
                                        });
                                    }
                                    nText.setRows(5);
                                    break;
                                }
                                case 1: {
                                    nText.setMaxlength(128);
                                    break;
                                }
                                case 2: {
                                    nText.setMaxlength(256);
                                    break;
                                }
                                default: {
                                    nText.setMaxlength(128);
                                    break;
                                }
                            }
//                            nText.setSclass("resultado-completo");
                            if (!(valorDef.isEmpty())) {
                                nText.setValue(valorDef);
                            }
                            nText.setId("T" + lblNombre.getValue() + new Date().toString());
                            nText.setWidth("98%");
                            row.appendChild(nText);
                            nText.setSclass("resultado");
                            break;
                        case "N":
                            final Decimalbox nDecimal = new Decimalbox();
                            nDecimal.setCols(20);
                            nDecimal.setWidth("50%");
                            if (!(valorDef.isEmpty())) {
                                nDecimal.setValue(new BigDecimal(valorDef));
                                // nDecimal.setDisabled(true);
                            }
//                            nDecimal.setSclass("resultado");
                            row.appendChild(nDecimal);
                            break;
                        case "D":
                            final Datebox fechita = new Datebox();
                            if (obligatorio.equals("0")) {
                                fechita.setConstraint("no empty, no future: Ingrese Dato");
                            }
                            fechita.setCols(16);
                            fechita.setFormat("dd-MMM-yyyy");
                            fechita.setWidth("30%");
                            if (!(valorDef.isEmpty())) {
                                fechita.setText(valorDef);
                                //fechita.setDisabled(true);
                            }
                            fechita.setMold("rounded");
//                           fechita.setSclass("resultado");
                            row.appendChild(fechita);
                            break;
                        case "S":
                            Radiogroup grpRadio = new Radiogroup();
                            Radio lblSi = new Radio("SI");
                            Radio lblNo = new Radio("NO");
                            lblSi.setParent(grpRadio);
                            lblNo.setParent(grpRadio);
                            if (obligatorio.equals("0")) {
                            }
                            if (!(valorDef.isEmpty())) {
                                if (valorDef.equals("SI")) {
                                    lblSi.setChecked(true);
                                    lblNo.setChecked(false);
                                    // lblSi.setDisabled(true);
                                    // lblNo.setDisabled(true);
                                }
                                if (valorDef.equals("NO")) {
                                    lblSi.setChecked(false);
                                    lblNo.setChecked(true);
                                    //  lblSi.setDisabled(true);
                                    //  lblNo.setDisabled(true);
                                }
                            }
                            lblSi.setStyle("font-size: 16px;");
//                                lblSi.setSclass("resultado");
                            lblNo.setStyle("font-size: 16px;");
//                                lblNo.setSclass("resultado");
                            grpRadio.setWidth("98%");
                            row.appendChild(grpRadio);
                            break;
                        case "L": {
                            Combobox grpLista = new Combobox();
                            String subString = itemsLista;
                            while (!(subString.isEmpty())) {
                                int pos = subString.indexOf("/");
                                if (pos > 0) {
                                    String sub = subString.substring(0, pos);
                                    grpLista.appendItem(sub);
                                    subString = subString.substring(pos + 1);
                                } else {
                                    subString = "";
                                }
                            }
                            grpLista.setValue("(Seleccione)");
                            if (!(valorDef.isEmpty())) {
                                grpLista.setText(valorDef);
                                // grpLista.setDisabled(true);
                            }
                            if (obligatorio.equals("0")) {
                                grpLista.setConstraint("no empty, no future: Ingrese Dato");
                            }
                            grpLista.setStyle("font-size: 16px;");
                            grpLista.setWidth("98%");
//                            grpLista.setSclass("resultado");
                            row.appendChild(grpLista);
                            break;
                        }
                        case "LD": {
                            Hbox grpListaDetalle = new Hbox();
                            grpListaDetalle.setWidth("98%");
                            Combobox grpLista = new Combobox();
                            Space espacio = new Space();
                            String subString = itemsLista;
                            while (!(subString.isEmpty())) {
                                int pos = subString.indexOf("/");
                                if (pos > 0) {
                                    String sub = subString.substring(0, pos);
                                    grpLista.appendItem(sub);
                                    subString = subString.substring(pos + 1);
                                } else {
                                    subString = "";
                                }
                            }
                            grpLista.setValue("(Seleccione)");
                            if (!(valorDef.isEmpty())) {
                                grpLista.setText(valorDef);
                                //     grpLista.setDisabled(true);
                            }
                            if (obligatorio.equals("0")) {
                                grpLista.setConstraint("no empty, no future: Ingrese Dato");
                            }
                            Textbox porq = new Textbox();
                            porq.setWidth("190%");
//                            porq.setSclass("resultado");
                            porq.setMaxlength(256);
                            grpLista.setStyle("font-size: 16px;");
                            grpLista.setParent(grpListaDetalle);
                            espacio.setParent(grpListaDetalle);
                            porq.setParent(grpListaDetalle);
                            row.appendChild(grpListaDetalle);
                            break;
                        }
                        case "B":                        //Audiometria
                            Button btnAplet = new Button(lblDescripcion.getValue());
                            if (lblDescripcion.getValue().equals("AUDIOMETRIA")) {
                                btnAplet.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {

                                    @Override
                                    public void onEvent(Event event) throws Exception {
//                                        loadAudioApplet();
                                    }
                                });
                                if (!(valorDef.isEmpty())) {
                                    btnAplet.setLabel(valorDef);
                                }
                                row.appendChild(btnAplet);
                            }
                            break;
                        case "G": {
                            break;
                        }
                        default: {
                            final Textbox nText1 = new Textbox();
                            nText1.setMaxlength(128);
                            nText1.setCols(50);
                            nText1.setWidth("98%");
                            nText1.setSclass("resultado");
                            if (obligatorio.equals("0")) {
                                nText1.setConstraint("no empty, no future: Ingrese Dato");
                            }
                            if (!(valorDef.isEmpty())) {
                                nText1.setValue(valorDef);
                            }
                            row.appendChild(nText1);
                            break;
                        }
                    }
                    if (row.getChildren().size() < 2) {
                        Messagebox.show("Error al cargar el formato Nodo <2,", "Información de usuario", Messagebox.OK, Messagebox.ERROR);
                        final Textbox nText = new Textbox();
                        nText.setMaxlength(128);
                        nText.setWidth("98%");
                        nText.setSclass("resultado");
                        row.appendChild(nText);
                        row.appendChild(lblNombre);
                    } else {
                        row.appendChild(lblNombre);
                    }
                    row.setParent(rowsAnt);
                }
            }
        } catch (Exception e) {
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CARGA DE XML">
    private void loadGridResultados(boolean estado) {
        //desde XmlResultados

        numFormatosAnteced = 1;
        numFormato.setValue(Integer.toString(numFormatosAnteced));

        try {
            int x = rowsAnt.getChildren().size();
            if (x >= 1) {//si hay datos toca eliminar
                while (rowsAnt.getChildren().size() > 0) {
                    for (int i = 0; i < rowsAnt.getChildren().size(); i++) {
                        rowsAnt.getChildren().remove(i);
                    }
                }
            }
            String xml = objAntecedenes.getAntecedentes();
            Document doc1 = ConvertirDocumento.getConvertirDocumentoDocument(xml);
            NodeList listaHijos = doc1.getChildNodes();
            listaHijos = listaHijos.item(0).getChildNodes();
            GridResultados(listaHijos, estado);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private void GridResultados(NodeList listaHijos, boolean estado) {
        Element elemento;
        NodeList listGrupos, listHijosGrupo = null;
        Label LblNombre, LblDescripcion, LblOrden;
        Node NItem, NGurpo, NPagina;
        Group grupo;
        Row NewFila;
        try {
            for (int t = 1; t < listaHijos.getLength(); t++) {
                NPagina = listaHijos.item(t);
                if (NPagina.getNodeType() == Node.ELEMENT_NODE) {
                    elemento = (Element) NPagina;
                    if (elemento.getNodeName() != null) {
                        String NamNText = elemento.getTagName();
                        if (!(NamNText.equals("datos_generales_standar"))) {
                            String idforma = elemento.getAttribute("IdFormato");
                            numFormatosAnteced = numFormatosAnteced + 1;
                            numFormato.setValue(Integer.toString(numFormatosAnteced));
                            NamNText = NamNText + " ID Formato: " + elemento.getAttribute("Id");
                            final Group fila = new Group();
                            fila.setLabel(NamNText);
                            fila.setId(idforma);
                            fila.setOpen(false);
                            fila.setVisible(false);
                            fila.setParent(rowsAnt);
                            listGrupos = NPagina.getChildNodes();
                            for (int k = 0; k < listGrupos.getLength(); k++) {
                                String descripcionXmlRes;
                                NGurpo = listGrupos.item(k);
                                if (NGurpo.getNodeType() == Node.ELEMENT_NODE) {
                                    elemento = (Element) NGurpo;
                                    if (elemento.getNodeName() != null) {
                                        NamNText = elemento.getTagName();
                                        grupo = new Group();
                                        descripcionXmlRes = elemento.getAttribute("descripcion");
                                        String sexVisible = elemento.getAttribute("sexo");
                                        boolean visibleEditFrameTExt = true;
                                        if (!(sexVisible.isEmpty())) {
                                            if (!(sexVisible.equalsIgnoreCase(historia.getSexo()))) {
                                                visibleEditFrameTExt = false;
                                            }
                                        }
                                        grupo.setVisible(visibleEditFrameTExt);
                                        grupo.setLabel(descripcionXmlRes);
                                        grupo.setStyle(" border: 1px outset; background: #b5dbe5; color: black;text-align: center; ");
                                        grupo.setId(NamNText);
                                        grupo.setParent(rowsAnt);
                                        listHijosGrupo = NGurpo.getChildNodes();
                                        if (listHijosGrupo.getLength() > 0) {
                                            for (int y = 0; y < listHijosGrupo.getLength(); y++) {
                                                NItem = listHijosGrupo.item(y);
                                                if (NItem.getNodeType() == Node.ELEMENT_NODE) {
                                                    String nombreXmlRes;
                                                    String ordenXmlRes;
                                                    String tipoXmlRes;
                                                    String ItemsListaXmlRes = null;
                                                    int lineas = 1;
                                                    elemento = (Element) NItem;
                                                    if (elemento.getNodeName() != null) {
                                                        NamNText = elemento.getTagName();
                                                        nombreXmlRes = elemento.getTagName();
                                                        descripcionXmlRes = elemento.getAttribute("descripcion");
                                                        ordenXmlRes = elemento.getAttribute("orden");
                                                        tipoXmlRes = elemento.getAttribute("tipo_dato");
                                                        if ((tipoXmlRes.equals("L")) || (tipoXmlRes.equals("LD"))) {
                                                            ItemsListaXmlRes = elemento.getAttribute("item_lista");
                                                        }
                                                        if (tipoXmlRes.equals("T")) {
                                                            String xv = elemento.getAttribute("lineas");
                                                            if (xv.isEmpty()) {
                                                                lineas = 1;
                                                            } else {
                                                                lineas = Integer.parseInt(xv);
                                                            }
                                                        }
                                                        if (tipoXmlRes.isEmpty() && nombreXmlRes.equalsIgnoreCase("dx")) {
                                                            tipoXmlRes = "G";
                                                        }
                                                        String valor = elemento.getTextContent();
                                                        try {
                                                            if (valor == null || valor.trim().equalsIgnoreCase("")) {
                                                                valor = "";
                                                            }
                                                        } catch (Exception e) {
                                                            valor = "";
                                                        }
                                                        LblDescripcion = new Label(descripcionXmlRes);
                                                        if (!(descripcionXmlRes.equals("S/D"))) {
                                                            LblDescripcion.setSclass("descripcion-resultado");
                                                        }
                                                        LblOrden = new Label(ordenXmlRes);
                                                        LblNombre = new Label(nombreXmlRes);

                                                        NewFila = new Row();
                                                        NewFila.setSclass("row-resultado");
                                                        NewFila.appendChild(LblDescripcion);
                                                        //verifico tipos de datos
                                                        switch (tipoXmlRes) {
                                                            case "T": {
                                                                final Textbox nText = new Textbox(valor);
                                                                nText.setDisabled(!estado);
                                                                nText.setSclass("resultado-completo");
                                                                nText.setId("T" + LblNombre.getValue());
                                                                if (valor.isEmpty()) {
                                                                    nText.setSclass("resultado-incompleto");
                                                                    nText.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                        @Override
                                                                        public void onEvent(Event event) {
                                                                            nText.setSclass("resultado-completo");
                                                                        }
                                                                    });
                                                                }
                                                                switch (lineas) {
                                                                    case 0: {
                                                                        nText.setMultiline(true);
                                                                        if (!copiar) {
                                                                            nText.setCtrlKeys("^c^v");
                                                                            nText.addEventListener(Events.ON_CTRL_KEY, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                                @Override
                                                                                public void onEvent(Event event) {
                                                                                    int keyCode = ((KeyEvent) event).getKeyCode();
                                                                                    if (keyCode == 67 || keyCode == 86) {
                                                                                    }
                                                                                }
                                                                            });
                                                                            nText.addEventListener(Events.ON_RIGHT_CLICK, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                                @Override
                                                                                public void onEvent(Event event) {
                                                                                }
                                                                            });
                                                                        }
                                                                        nText.addEventListener(Events.ON_DOUBLE_CLICK, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                            @Override
                                                                            public void onEvent(Event event) {
                                                                                Window winEdicion = new Window();
                                                                                String windowMessage;
                                                                                windowMessage = "edicion-aux.zul";
                                                                                winEdicion.setWidth("550px");
                                                                                Executions.createComponents(windowMessage, winEdicion, null);
                                                                                final Textbox edicion = (Textbox) winEdicion.getFellow("edit-aux", true);
                                                                                edicion.setSclass("edit-resultado");
                                                                                if (!copiar) {
                                                                                    edicion.setCtrlKeys("^c^v");
                                                                                    edicion.addEventListener(Events.ON_CTRL_KEY, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                                        @Override
                                                                                        public void onEvent(Event event) {
                                                                                            int keyCode = ((KeyEvent) event).getKeyCode();
                                                                                            if (keyCode == 67 || keyCode == 86) {
                                                                                            }
                                                                                        }
                                                                                    });
                                                                                    edicion.addEventListener(Events.ON_RIGHT_CLICK, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                                        @Override
                                                                                        public void onEvent(Event event) {
                                                                                        }
                                                                                    });
                                                                                }
                                                                                winEdicion.addEventListener(Events.ON_CLOSE, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                                    @Override
                                                                                    public void onEvent(Event event) {
                                                                                        nText.setValue(edicion.getValue());
                                                                                    }
                                                                                });
                                                                                edicion.setValue(nText.getValue());
                                                                                winEdicion.setTitle("Edición de resultado");
                                                                                winEdicion.setClosable(true);
                                                                                winEdicion.setSizable(true);
                                                                                winEdicion.setId("winEditAuxiliar");
                                                                                winEdicion.setParent(WinHistoria);
                                                                                winEdicion.doModal();
                                                                            }
                                                                        });
                                                                        nText.setRows(5);
                                                                        break;
                                                                    }
                                                                    case 1: {
                                                                        nText.setMaxlength(128);
                                                                        break;
                                                                    }
                                                                    case 2: {
                                                                        nText.setMaxlength(256);
                                                                        break;
                                                                    }
                                                                    default: {
                                                                        nText.setMaxlength(128);
                                                                        break;
                                                                    }
                                                                }
                                                                nText.setWidth("98%");
                                                                NewFila.appendChild(nText);
                                                                break;
                                                            }
                                                            case "N": {
                                                                final Decimalbox nDecimal;
                                                                if (valor.isEmpty()) {
                                                                    nDecimal = new Decimalbox();
                                                                    nDecimal.setSclass("resultado-incompleto");
                                                                    nDecimal.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                        @Override
                                                                        public void onEvent(Event event) {
                                                                            nDecimal.setSclass("resultado-completo");
                                                                        }
                                                                    });
                                                                } else {
                                                                    nDecimal = new Decimalbox(new BigDecimal(valor));
                                                                    nDecimal.setSclass("resultado-completo");
                                                                }
                                                                nDecimal.setCols(20);
                                                                nDecimal.setDisabled(!estado);
                                                                NewFila.appendChild(nDecimal);
                                                                break;
                                                            }
                                                            case "D": {
                                                                if (!(estado)) {
                                                                    final Label nFecha = new Label(valor);
                                                                    nFecha.setStyle("color:black;");
                                                                    NewFila.appendChild(nFecha);
                                                                } else {
                                                                    final Datebox nFecha = new Datebox();
                                                                    nFecha.setSclass("resultado");
                                                                    if (!(valor.trim().isEmpty())) {
                                                                        nFecha.setFormat("dd-MMM-yyyy");
                                                                        DateFormat formatter;
                                                                        formatter = new SimpleDateFormat("dd-MMM-yyyy", new Locale("es", "ES"));
                                                                        Date fec = null;
                                                                        try {
                                                                            fec = (Date) formatter.parse(valor);
                                                                        } catch (ParseException e) {
                                                                            StringTokenizer stk = new StringTokenizer(valor, "/");
                                                                            String d = stk.nextToken();
                                                                            String m = stk.nextToken();
                                                                            String a = stk.nextToken();
                                                                            valor = a + "/" + m + "/" + d;
                                                                            Date nuv = new Date(valor);
                                                                            valor = formatter.format(nuv);
                                                                            fec = (Date) formatter.parse(valor);
                                                                            System.out.println("ERROR DANDO FORMATO FECHA DEL XML " + valor + "rESxML " + objAntecedenes.getId() + " ID orden " + objAntecedenes.getOrden().getId());
                                                                            throw new RuntimeException(e);
                                                                        }
                                                                        nFecha.setValue(fec);
                                                                    } else {
                                                                        nFecha.setSclass("resultado-incompleto");
                                                                        nFecha.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                            @Override
                                                                            public void onEvent(Event event) {
                                                                                nFecha.setSclass("resultado-completo");
                                                                            }
                                                                        });
                                                                    }
                                                                    nFecha.setCols(16);
                                                                    nFecha.setMold("rounded");// nFecha.setFormat("yyyy/MM/dd"); //nFecha.setFormat("MM-dd-yy");
                                                                    NewFila.appendChild(nFecha);
                                                                }
                                                                break;
                                                            }
                                                            case "S": {
                                                                if (!(estado)) {
                                                                    final Label nValor = new Label(valor);
                                                                    nValor.setStyle("color:black;");
                                                                    NewFila.appendChild(nValor);
                                                                } else {
                                                                    Radiogroup grpRadio = new Radiogroup();
                                                                    final Radio lblSi = new Radio("SI");
                                                                    final Radio lblNo = new Radio("NO");
                                                                    if (valor.equals("SI")) {
                                                                        lblSi.setChecked(true);
                                                                        lblSi.setSclass("resultado");
                                                                    } else {
                                                                        if (valor.equals("NO")) {
                                                                            lblNo.setChecked(true);
                                                                            lblNo.setSclass("resultado");
                                                                        }
                                                                    }
                                                                    if (valor.trim().isEmpty()) {
                                                                        lblSi.setSclass("sino-incompleto");
                                                                        lblNo.setSclass("sino-incompleto");
                                                                        lblSi.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                            @Override
                                                                            public void onEvent(Event event) {
                                                                                lblSi.setSclass("resultado");
                                                                                lblNo.setSclass("resultado");
                                                                            }
                                                                        });

                                                                        lblNo.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                            @Override
                                                                            public void onEvent(Event event) {
                                                                                lblNo.setSclass("resultado");
                                                                                lblSi.setSclass("resultado");
                                                                            }
                                                                        });
                                                                    }
                                                                    lblSi.setStyle("font-size: 16px;");
                                                                    lblNo.setStyle("font-size: 16px;");
                                                                    lblSi.setParent(grpRadio);
                                                                    lblNo.setParent(grpRadio);
                                                                    NewFila.appendChild(grpRadio);
                                                                }
                                                                break;
                                                            }
                                                            case "L": {
                                                                if (valor.isEmpty()) {
                                                                    NewFila.setStyle("color:red;");
                                                                }
                                                                final Combobox grpLista = new Combobox();
                                                                grpLista.setSclass("resultado");
                                                                String subString = ItemsListaXmlRes;
                                                                while (!(subString.isEmpty())) {
                                                                    int pos = subString.indexOf("/");
                                                                    if (pos > 0) {
                                                                        String sub = subString.substring(0, pos);
                                                                        grpLista.appendItem(sub);
                                                                        subString = subString.substring(pos + 1);
                                                                    } else {
                                                                    }
                                                                }
                                                                if (valor.trim().isEmpty()) {
                                                                    grpLista.setValue("(Seleccione)");
                                                                    grpLista.setSclass("resultado-incompleto");
                                                                    grpLista.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                        @Override
                                                                        public void onEvent(Event event) {
                                                                            grpLista.setSclass("resultado-completo");
                                                                        }
                                                                    });
                                                                } else {
                                                                    grpLista.setValue(valor);
                                                                    grpLista.setSclass("resultado");
                                                                }
                                                                grpLista.setStyle("font-size: 16px;");
                                                                grpLista.setWidth("95%");
                                                                grpLista.setDisabled(!estado);
                                                                NewFila.appendChild(grpLista);
                                                                break;
                                                            }
                                                            case "LD": {
                                                                if (!(estado)) {
                                                                    String va = null;
                                                                    if (!(valor.isEmpty())) {

                                                                        String g = valor.substring(0, valor.indexOf("|"));
                                                                        va = g;
                                                                        g = valor.substring(valor.indexOf("|") + 1);
                                                                        g = g.substring(0, g.indexOf("|"));
                                                                        if (!(g.isEmpty())) {
                                                                            va = va + " , " + g;
                                                                        }
                                                                    } else {
                                                                        va = "";
                                                                    }
                                                                    final Label nValor = new Label(va);
                                                                    nValor.setWidth("95%");
                                                                    nValor.setStyle("color:black;");
                                                                    NewFila.appendChild(nValor);

                                                                } else {
                                                                    Hbox grpListaDetalle = new Hbox();
                                                                    final Combobox grpLista = new Combobox();
                                                                    grpLista.setParent(grpListaDetalle);
                                                                    Space espacio = new Space();
                                                                    espacio.setParent(grpListaDetalle);
                                                                    String subString = ItemsListaXmlRes;
                                                                    final Textbox porq = new Textbox();
                                                                    porq.setWidth("190%");
                                                                    porq.setMaxlength(256);
                                                                    porq.setParent(grpListaDetalle);
                                                                    while (!(subString.isEmpty())) {
                                                                        int pos = subString.indexOf("/");
                                                                        if (pos > -1) {
                                                                            String sub = subString.substring(0, pos);
                                                                            grpLista.appendItem(sub);
                                                                            subString = subString.substring(pos + 1);
                                                                        } else {
                                                                        }
                                                                    }
                                                                    if (valor.isEmpty()) {
                                                                        grpLista.setValue("(Seleccione)");
                                                                        grpLista.setSclass("resuldo-incompleto");
                                                                        porq.setSclass("resultado-incompleto");
                                                                        grpLista.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                            @Override
                                                                            public void onEvent(Event event) {
                                                                                grpLista.setClass("resultado-completo");
                                                                                porq.setSclass("resultado-completo");
                                                                            }
                                                                        });
                                                                        porq.setSclass("resultado-incompleto");
                                                                    } else {
                                                                        //Si el resultado NO esta vacio
                                                                        String g = valor.substring(0, valor.indexOf("|"));
                                                                        grpLista.setValue(g);
                                                                        g = valor.substring(valor.indexOf("|") + 1);
                                                                        g = g.substring(0, g.indexOf("|"));
                                                                        porq.setValue(g);
                                                                        porq.setSclass("resultado");
                                                                    }
                                                                    grpListaDetalle.setStyle("font-size: 16px;");
                                                                    NewFila.appendChild(grpListaDetalle);
                                                                }
                                                                break;
                                                            }
                                                            case "B": {
                                                                alert("No tiene definido tipo de dato");
                                                                break;
                                                            }

                                                            case "G": {
                                                                alert("No tiene definido tipo de dato");
                                                                break;
                                                            }
                                                            default:
                                                                final Textbox nText1 = new Textbox();
                                                                nText1.setDisabled(!estado);
                                                                nText1.setSclass("resultado");
                                                                if (valor.isEmpty()) {
                                                                    nText1.setSclass("resultado-incompleto");
                                                                    nText1.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                        @Override
                                                                        public void onEvent(Event event) {
                                                                            nText1.setSclass("resultado-completo");
                                                                        }
                                                                    });
                                                                }
                                                                nText1.setValue(valor);
                                                                nText1.setWidth("90%");
                                                                NewFila.appendChild(nText1);
                                                                break;
                                                        }
                                                        if (NewFila.getChildren().size() < 2) {
                                                            final Textbox nText1 = new Textbox();
                                                            nText1.setDisabled(!estado);
                                                            nText1.setSclass("resultado");
                                                            if (valor.isEmpty()) {
                                                                nText1.setSclass("resultado-incompleto");
                                                                nText1.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {

                                                                    @Override
                                                                    public void onEvent(Event event) {
                                                                        nText1.setSclass("resultado-completo");
                                                                    }
                                                                });
                                                            }
                                                            nText1.setValue(valor);
                                                            nText1.setWidth("90%");
                                                            NewFila.appendChild(nText1);
                                                            Messagebox.show("Existe un error en el resultado: " + LblDescripcion.getValue(),
                                                                    "Información de usuario", Messagebox.OK, Messagebox.ERROR);
                                                        } else {
                                                            NewFila.appendChild(LblNombre);
                                                            NewFila.appendChild(LblOrden);
                                                        }
                                                        try {
                                                            //        NewFila.setId(LblNombre.getValue());
                                                            //       System.out.println(LblNombre.getValue());
                                                            NewFila.setVisible(visibleEditFrameTExt);
                                                            NewFila.setParent(rowsAnt);
                                                        } catch (Exception e) {
                                                            alert("no puede cargar Antecedente" + LblNombre.getValue());
                                                        }

                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            rowsAnt.setVisible(true);
            if (estado) {
                modificar.setValue("T");
            } else {
                modificar.setValue("F");
            }
        } catch (ParseException | RuntimeException e) {
        }
    }
    // </editor-fold>
}

//
//    public XmlAntecedentes getObjAntecedenes() {
//        return objAntecedenes;
//    }
//
//    public void setObjAntecedenes(XmlAntecedentes objAntecedenes) {
//        this.objAntecedenes = objAntecedenes;
//    }
//
//    private void findAntecedenteLoad() throws InterruptedException, NamingException {
//        Map<String, Object> wSQL = new HashMap<>();
////        wSQL.put("idPractica ?=", IdPractica);
//        wSQL.put("idEmpresa ?=", orden.getOrganizacion().getId());
//        wSQL.put("idHistoria.id ?=", historia.getId());
//        wSQL.put("idOrden ?=", orden.getId());
//        wSQL.put("lockReg", 0);
//        List XMLAntecentes = admNegocio.getData(new XmlAntecedentes(), wSQL, null, null);
//        if (XMLAntecentes.size() == 1) {
//            objAntecedenes = (XmlAntecedentes) XMLAntecentes.get(0);
//            setObjAntecedenes(objAntecedenes);
//            ProcesosSession admiSessionUsuario = new ProcesosSession();
//            sessionOk objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
//            objsessiActica.setObjXmlAntecedentes(getObjAntecedenes());
//            admiSessionUsuario.AgregarAtributoSession(2, objsessiActica, desktop.getSession());
//            if (stado) {
//                modificar.setValue("T");
//                loadGridResultados(stado);
//            } else {
//                modificar.setValue("F");
//                loadGridResultados(stado);
//            }
//        } else {
//            if (XMLAntecentes.size() > 1) {
//                Messagebox.show("Antecedentes duplicado ext 720", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//            } else {
//                if (docAnteHis == null) {
//                    loadEntradasAntecedentes();
//                } else {//existe historial y nuevo antecedentes
//                    recuparardatos();
//                }
//            }
//        }
//        gridAnt.renderAll();
//        gridAnt.invalidate();
//        gridAnt.setVisible(true);
//    }
//
//    private void recuparardatos() {
//        NodeList listTexFrames = null;
//        Node nodoText, nodoFrame;
//        Element elemento;
//        List dataGrid = new ArrayList();
//        rowsAnt.getChildren().clear();
//        try {
//            Map<String, Object> wSQL = new HashMap<String, Object>();
//            List oSQL = new ArrayList();
//            Integer idPrac;
//            wSQL.put("idPractica ?=", IdPractica);
//            wSQL.put("idEmpresa ?=", orden.getOrganizacion().getId());
//            wSQL.put("lockReg ?=", 0);
//            oSQL.add("idHoja");
//            List<Object> Formatos = null;
//            try {
//                Formatos = admNegocio.getDataAsc(new FormatoXAntecedentes(), wSQL, null, oSQL);
//
//            } catch (NamingException ex) {
//                System.out.println(usuario + "  Formatos = admNegocio.getDataAsc(new FormatoXPractica(), wSQL, null, oSQL); ");
//                Logger
//                        .getLogger(ControladoraHistoria.class
//                                .getName()).log(Level.SEVERE, null, ex);
//            }
//            if (!(Formatos.isEmpty())) {
//                numFormatosAnteced = Formatos.size() + 1;
//                numFormato.setValue(String.valueOf(numFormatosAnteced));
//                for (int t = 0; t < Formatos.size(); t++) {
//                    FormatoXAntecedentes formatPage = (FormatoXAntecedentes) Formatos.get(t);
//                    String xml = null;
//                    try {
//                        xml = new String(formatPage.getXml(), "UTF8");
//                    } catch (Exception e) {
//                        Messagebox.show("Formato en pruebas");
//                        reset();
//                    }
//                    DocumentBuilder docBuilder = null;
//                    try {
//                        docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//                        docAntecedentes = docBuilder.parse(new InputSource(new StringReader(xml)));
//                    } catch (ParserConfigurationException ex) {
//                        System.out.println(usuario + "   docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); ");
//                        Logger.getLogger(ControladoraHistoria.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (SAXException ex) {
//                        System.out.println(usuario + "  docAntecedentes = docBuilder.parse(new InputSource(new StringReader(xml))); ");
//                        Logger
//                                .getLogger(ControladoraHistoria.class
//                                        .getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                    ManejadoraXml AdmXml = new ManejadoraXml();
//                    NodeList lista = AdmXml.getlistanodos("frame", docAntecedentes);
//                    Group fila = new Group();
//                    fila.setId(formatPage.getId().toString());
//                    fila.setLabel("PAGINA" + t + "  ID Formato: " + formatPage.getIdHoja());
//                    fila.setVisible(false);
//                    fila.setParent(rowsAnt);
//                    for (int k = 0; k < lista.getLength(); k++) {
//                        listTexFrames = lista.item(k).getChildNodes();
//                        nodoFrame = lista.item(k);
//                        visibleFrameCarga = true; //lista de frame o grupos
//                        listTexFrames = loadFrame(nodoFrame);
//                        if (listTexFrames.getLength() > 0) {
//                            dataGrid.add(nodoFrame);
//                        }
//                        if (listTexFrames != null) {
//                            for (int i = 0; i < listTexFrames.getLength(); i++) {
//                                nodoText = listTexFrames.item(i);
//                                if (nodoText.getNodeType() == Node.ELEMENT_NODE) {
//                                    dataGrid.add(nodoText);
//                                    elemento = (Element) nodoText;
//                                    if (elemento.getNodeName() != null) {
//                                        try {
//                                            //String valor = elemento.getTextContent();
//                                            loadElement(elemento, nodoText, true);
//                                        } catch (InterruptedException ex) {
//                                            System.out.println(usuario + " Error al crear elemento " + elemento.getTagName());
//                                            alert("Error al crear elemento " + ex.getMessage());
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    rowsAnt.setVisible(true);
//                    modificar.setValue("N");
//                }
//            } else {
//                Messagebox.show("No se encontraron formatos cargados " + orden.getOrganizacion().getAbreviatura());
//                reset();
//            }
//        } catch (IOException e) {
//            System.out.println(usuario + "  loadGridEntradas " + orden.getId());
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    private String buscarValor(String datoB) {
//        Document doc1 = null;
//        Node NPagina;
//        Node Nhijo;
//        Node NNieto;
//        String valRecu = null;
//        Element elemento;
//        //   String tagBusca = elemento.getTagName();
//        doc1 = docAnteHis;
//        NodeList listaPadres = doc1.getChildNodes();
//        NPagina = listaPadres.item(0);
//        listaPadres = NPagina.getChildNodes();
//        NodeList listaHijos;
//        NodeList listaNietos;
//        for (int t = 0; t < listaPadres.getLength(); t++) {
//            NPagina = listaPadres.item(t);
//            if (NPagina.getNodeType() == Node.ELEMENT_NODE) {
//                elemento = (Element) NPagina;
//                if (elemento.getNodeName() != null) {
//                    listaHijos = NPagina.getChildNodes();
//                    for (int j = 0; j < listaHijos.getLength(); j++) {
//                        Nhijo = listaHijos.item(j);
//                        if (Nhijo.getNodeType() == Node.ELEMENT_NODE) {
//                            elemento = (Element) Nhijo;
//                            if (elemento.getNodeName() != null) {
//                                listaNietos = Nhijo.getChildNodes();
//                                for (int x = 0; x < listaNietos.getLength(); x++) {
//                                    NNieto = listaNietos.item(x);
//                                    if (NNieto.getNodeType() == Node.ELEMENT_NODE) {
//                                        elemento = (Element) NNieto;
//                                        if (elemento.getNodeName() != null) {
//                                            String NamNText = elemento.getTagName();
//                                            //   System.out.println("D bus " + datoB + " D Rec " + NamNText);
//                                            if (NamNText.equals(datoB)) {
//                                                valRecu = elemento.getTextContent();
//                                                x = listaNietos.getLength();
//                                                j = listaHijos.getLength();
//                                                t = listaPadres.getLength();
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return valRecu;
//    }
//
//    private void reset() {
//        numFormatosAnteced = 0;
//        numFormato.setValue("0");
//    }
//
//    public void onCreate$antecedentes() {
//        try {
//            modificarSession();
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    // </editor-fold>
//    //<editor-fold defaultstate="collapsed" desc="CARGA DE FORMATO">
//    private void loadEntradasAntecedentes() {
//        NodeList listTexFrames = null;
//        Node nodoText, nodoFrame;
//        Element elemento;
//        List dataGrid = new ArrayList();
//        rowsAnt.getChildren().clear();
//        try {
//            Map<String, Object> wSQL = new HashMap<String, Object>();
//            List oSQL = new ArrayList();
//            Integer idPrac;
////            wSQL.put("idPractica ?=", IdPractica);
//            wSQL.put("idEmpresa ?=", orden.getOrganizacion().getId());
//            wSQL.put("lockReg ?=", 0);
//            oSQL.add("idHoja");
//            List<Object> Formatos = null;
//            try {
//                Formatos = admNegocio.getDataAsc(new FormatoXAntecedentes(), wSQL, null, oSQL);
//
//            } catch (NamingException ex) {
//                System.out.println(usuario + "  Formatos = admNegocio.getDataAsc(new FormatoXPractica(), wSQL, null, oSQL); ");
//                Logger
//                        .getLogger(ControladoraHistoria.class
//                                .getName()).log(Level.SEVERE, null, ex);
//            }
//            if (!(Formatos.isEmpty())) {
//                numFormatosAnteced = Formatos.size() + 1;
//                numFormato.setValue(String.valueOf(numFormatosAnteced));
//                for (int t = 0; t < Formatos.size(); t++) {
//                    FormatoXAntecedentes formatPage = (FormatoXAntecedentes) Formatos.get(t);
//                    String xml = null;
//                    try {
//                        xml = new String(formatPage.getXml(), "UTF8");
//                    } catch (Exception e) {
//                        Messagebox.show("Formato en pruebas");
//                        reset();
//                    }
//                    DocumentBuilder docBuilder = null;
//                    try {
//                        docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//                        docAntecedentes = docBuilder.parse(new InputSource(new StringReader(xml)));
//                    } catch (ParserConfigurationException ex) {
//                        System.out.println(usuario + "   docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); ");
//                        Logger
//                                .getLogger(ControladoraHistoria.class
//                                        .getName()).log(Level.SEVERE, null, ex);
//                    } catch (SAXException ex) {
//                        System.out.println(usuario + "  docAntecedentes = docBuilder.parse(new InputSource(new StringReader(xml))); ");
//                        Logger
//                                .getLogger(ControladoraHistoria.class
//                                        .getName()).log(Level.SEVERE, null, ex);
//                    }
//
//                    ManejadoraXml AdmXml = new ManejadoraXml();
//                    NodeList lista = AdmXml.getlistanodos("frame", docAntecedentes);
//                    Group fila = new Group();
//                    fila.setId(formatPage.getId().toString());
//                    fila.setLabel("PAGINA" + t + "  ID Formato: " + formatPage.getIdHoja());
//                    fila.setVisible(false);
//                    fila.setParent(rowsAnt);
//                    for (int k = 0; k < lista.getLength(); k++) {
//                        listTexFrames = lista.item(k).getChildNodes();
//                        nodoFrame = lista.item(k);
//                        visibleFrameCarga = true; //lista de frame o grupos
//                        listTexFrames = loadFrame(nodoFrame);
//                        if (listTexFrames.getLength() > 0) {
//                            dataGrid.add(nodoFrame);
//                        }
//                        if (listTexFrames != null) {
//                            for (int i = 0; i < listTexFrames.getLength(); i++) {
//                                nodoText = listTexFrames.item(i);
//                                if (nodoText.getNodeType() == Node.ELEMENT_NODE) {
//                                    dataGrid.add(nodoText);
//                                    elemento = (Element) nodoText;
//                                    if (elemento.getNodeName() != null) {
//                                        try {
//                                            loadElement(elemento, nodoText, false);
//                                        } catch (InterruptedException ex) {
//                                            System.out.println(usuario + " Error al crear elemento " + elemento.getTagName());
//                                            alert("Error al crear elemento " + ex.getMessage());
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    rowsAnt.setVisible(true);
//                    modificar.setValue("N");
//                }
//            } else {
//                Messagebox.show("No se encontraron formatos cargados " + orden.getOrganizacion().getAbreviatura());
//                reset();
//            }
//        } catch (IOException e) {
//            System.out.println(usuario + "  loadGridEntradas " + orden.getId());
//            throw new RuntimeException(e);
//        }
//    }
//
//    private NodeList loadFrame(Node NFrame) {
//        NodeList listTextFrames = null;
//        NodeList listanodoproperty = null;
//        Group grupo;
//        try {
//            if (NFrame.getNodeType() == Node.ELEMENT_NODE) {
//                Element elemento = (Element) NFrame;
//                if (elemento.getNodeName() != null) {
//                    String NomNodo = elemento.getTagName();
//                    if (NomNodo.equals("frame")) {
//                        //es un grupo y tomo los textField del grupo
//                        listTextFrames = NFrame.getChildNodes();
//                        if (listTextFrames.getLength() > 0) {
//                            //tomo las propiedades del frame
//                            listanodoproperty = listTextFrames.item(1).getChildNodes();
//                        }
//                        String nomGrupo = "S/N";
//                        String n = "S/D";
//                        String sexoVisible = "";
//                        String areaa = "0";
//                        boolean frameVisible = true;
//                        for (int j = 0; j < listanodoproperty.getLength(); j++) {
//                            Node tagitem = listanodoproperty.item(j);
//                            if (tagitem.getNodeType() == Node.ELEMENT_NODE) {
//                                elemento = (Element) tagitem;
//                                NamedNodeMap item_atributos = elemento.getAttributes();
//                                Node valname = item_atributos.item(0);
//                                Node valvalor = item_atributos.item(1);
//                                if ((valname != null) && (valvalor != null)) {
//                                    String va = valname.getNodeValue();
//                                    if (va.equals("nombre")) {
//                                        nomGrupo = valvalor.getNodeValue();
//                                    }
//                                    if (va.equals("descripcion")) {
//                                        n = valvalor.getNodeValue();
//                                    }
//                                    if (va.equals("sexo")) {
//                                        sexoVisible = valvalor.getNodeValue();
//                                    }
//                                    if (va.equals("area")) {
//                                        areaa = valvalor.getNodeValue();
//                                        if (areaa.contains("1")) {
//                                            frameVisible = false;
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        if (!(sexoVisible.isEmpty())) {
//                            if (!(sexoVisible.equalsIgnoreCase(historia.getSexo()))) {
//                                n = "NO APLICA PARA:" + sexoVisible;
//                                visibleFrameCarga = false;
//                            }
//                        }
//                        grupo = new Group();
//                        grupo.setLabel(n);
//                        grupo.setStyle("border: 1px outset; background:#b5dbe5; color: black;text-align: center;");
//                        grupo.setVisible(frameVisible);
//                        if (nomGrupo.equals("S/D")) {
//                            nomGrupo = " ";
//                        }
//                        try {
//                            grupo.setId(nomGrupo);
//                            grupo.setParent(rowsAnt);
//                        } catch (Exception e) {
//                            alert("No agregado " + nomGrupo + " " + e.getMessage());
//                        }
//                    }
//                }
//            }
//            return listTextFrames;
//        } catch (Exception e) {
//            return null;
//        }
//
//    }
//
//    private void loadElement(Element elemento, Node NText, boolean si) throws InterruptedException {
//        try {
//            NodeList listaNodoTextFile, listaNodoProperty = null;
//            Label lblOrden, lblNombre, lblDescripcion;
//            Node tagItem;
//            NamedNodeMap item_atributos;
//            Row row;
//            String NamNText = elemento.getTagName();
//            if (!visibleFrameCarga) {
//                return;
//            }
//            if (NamNText.equals("textField")) {
//                listaNodoTextFile = NText.getChildNodes();
//                lblOrden = new Label();
//                lblNombre = new Label("S/N");
//                lblDescripcion = new Label("S/D");
//                String tipoDato = "T", itemsLista = "", obligatorio = "", valorDef = "";
//                int lineas = 1;
//                String orden = "";
//                if (listaNodoTextFile.getLength() > 0) {
//                    listaNodoProperty = listaNodoTextFile.item(1).getChildNodes();
//                }
//                if (listaNodoProperty.getLength() == 1) {
//                    orden = "1";
//                    lblOrden.setValue(orden);
//                    tipoDato = "sinPropiedades";
//                }
//                String valHistoria = "";
//                //RECORRO LAS PROPIEDADES DE LAS TEXTFIELD
//                for (int j = 0; j < listaNodoProperty.getLength(); j++) {
//                    tagItem = listaNodoProperty.item(j);
//                    if (tagItem.getNodeType() == Node.ELEMENT_NODE) {
//                        elemento = (Element) tagItem;
//                        item_atributos = elemento.getAttributes();
//                        //obtener del xml el atributos show
//                        Node valname = item_atributos.item(0);
//                        Node valvalor = item_atributos.item(1);
//                        if ((valname != null) && (valvalor != null)) {
//                            String va = valname.getNodeValue();
//                            switch (va) {
//                                case "orden":
//                                    orden = valvalor.getNodeValue();
//                                    lblOrden.setValue(valvalor.getNodeValue());
//                                    break;
//                                case "nombre":
//                                    valHistoria = "";
//                                    lblNombre.setValue(valvalor.getNodeValue());
//                                    if (si) {
//                                        valHistoria = buscarValor(valvalor.getNodeValue());
//                                    }
//                                    lblNombre.setSclass("nombre-resultado");
//                                    break;
//                                case "descripcion":
//                                    lblDescripcion.setValue(valvalor.getNodeValue());
//                                    lblDescripcion.setSclass("descripcion-resultado");
//                                    break;
//                                case "lineas":
//                                    lineas = Integer.parseInt(valvalor.getNodeValue());
//                                    break;
//                                case "item_lista":
//                                    itemsLista = valvalor.getNodeValue();
//                                    break;
//                                case "tipo_dato":
//                                    if (lblNombre.getValue().contains("dx")) {
//                                        tipoDato = "G";
//                                    } else {
//                                        tipoDato = valvalor.getNodeValue();
//                                    }
//                                    break;
//                                case "obligatorio":
//                                    obligatorio = valvalor.getNodeValue();
//                                    break;
//                                case "valor_defecto":
//                                    valorDef = valvalor.getNodeValue();
//
//                                    break;
//                                default:
//                                    Messagebox.show("PROPIEDAD DESCONOCIDA: '" + va + "'", "Información", Messagebox.OK, Messagebox.INFORMATION);
//                                    break;
//                            }
//                        }
//                        if (valHistoria != null) {
//                            if (!valHistoria.isEmpty()) {
//                                valorDef = valHistoria;
//                            }
//                        }
//                    }
//                }
//
//                //agregar las columnas
//                if ((!(orden.equals("0"))) && (!(orden.isEmpty()))) {//Si es 0 no se hace nada se salta
//                    row = new Row();
//                    row.setSclass("row-resultado");
//                    row.appendChild(lblDescripcion);
//                    switch (tipoDato) {
//                        case "T":
//                            final Textbox nText = new Textbox();
//                            if (obligatorio.equals("0")) {
//                                nText.setConstraint("no empty, no future: Ingrese Dato");
//                            }
//                            switch (lineas) {
//                                case 0: {
//                                    nText.setMultiline(true);
//                                    nText.addEventListener("onDoubleClick", new org.zkoss.zk.ui.event.EventListener() {
//                                        @Override
//                                        public void onEvent(Event event) throws Exception {
//                                            Window winEdicion = new Window();
//                                            String windowMessage;
//                                            windowMessage = "edicion-aux.zul";
//                                            winEdicion.setWidth("550px");
//                                            Executions.createComponents(windowMessage, winEdicion, null);
//                                            final Textbox edicion = (Textbox) winEdicion.getFellow("edit-aux", true);
//                                            edicion.setValue(nText.getValue());
//                                            edicion.setSclass("edit-resultado");
//                                            if (!copiar) {
//                                                edicion.setCtrlKeys("^c^v");
//                                                edicion.addEventListener(Events.ON_CTRL_KEY, new org.zkoss.zk.ui.event.EventListener() {
//                                                    @Override
//                                                    public void onEvent(Event event) throws Exception {
//                                                        int keyCode = ((KeyEvent) event).getKeyCode();
//                                                        if (keyCode == 67 || keyCode == 86) {
//                                                        }
//                                                    }
//                                                });
//                                                edicion.addEventListener(Events.ON_RIGHT_CLICK, new org.zkoss.zk.ui.event.EventListener() {
//                                                    @Override
//                                                    public void onEvent(Event event) throws Exception {
//                                                    }
//                                                });
//                                            }
//                                            winEdicion.addEventListener(Events.ON_CLOSE, new org.zkoss.zk.ui.event.EventListener() {
//                                                @Override
//                                                public void onEvent(Event event) throws Exception {
//                                                    nText.setValue(edicion.getValue());
//                                                }
//                                            });
//                                            winEdicion.setTitle("Edición de resultado");
//                                            winEdicion.setClosable(true);
//                                            winEdicion.setSizable(true);
//                                            winEdicion.setId("winEditAuxiliar");
//                                            winEdicion.setParent(WinHistoria);
//                                            winEdicion.doModal();
//                                        }
//                                    });
//                                    if (!copiar) {
//                                        nText.setCtrlKeys("^c^v");
//                                        nText.addEventListener(Events.ON_CTRL_KEY, new org.zkoss.zk.ui.event.EventListener() {
//
//                                            @Override
//                                            public void onEvent(Event event) throws Exception {
//                                                int keyCode = ((KeyEvent) event).getKeyCode();
//                                                if (keyCode == 67 || keyCode == 86) {
//                                                }
//                                            }
//                                        });
//                                        nText.addEventListener(Events.ON_RIGHT_CLICK, new org.zkoss.zk.ui.event.EventListener() {
//                                            @Override
//                                            public void onEvent(Event event) throws Exception {
//                                            }
//                                        });
//                                    }
//                                    nText.setRows(5);
//                                    break;
//                                }
//                                case 1: {
//                                    nText.setMaxlength(128);
//                                    break;
//                                }
//                                case 2: {
//                                    nText.setMaxlength(256);
//                                    break;
//                                }
//                                default: {
//                                    nText.setMaxlength(128);
//                                    break;
//                                }
//                            }
////                            nText.setSclass("resultado-completo");
//                            if (!(valorDef.isEmpty())) {
//                                nText.setValue(valorDef);
//                            }
//                            nText.setId("T" + lblNombre.getValue() + new Date().toString());
//                            nText.setWidth("98%");
//                            row.appendChild(nText);
//                            nText.setSclass("resultado");
//                            break;
//                        case "N":
//                            final Decimalbox nDecimal = new Decimalbox();
//                            nDecimal.setCols(20);
//                            nDecimal.setWidth("50%");
//                            if (!(valorDef.isEmpty())) {
//                                nDecimal.setValue(new BigDecimal(valorDef));
//                                // nDecimal.setDisabled(true);
//                            }
////                            nDecimal.setSclass("resultado");
//                            row.appendChild(nDecimal);
//                            break;
//                        case "D":
//                            final Datebox fechita = new Datebox();
//                            if (obligatorio.equals("0")) {
//                                fechita.setConstraint("no empty, no future: Ingrese Dato");
//                            }
//                            fechita.setCols(16);
//                            fechita.setFormat("dd-MMM-yyyy");
//                            fechita.setWidth("30%");
//                            if (!(valorDef.isEmpty())) {
//                                fechita.setText(valorDef);
//                                //fechita.setDisabled(true);
//                            }
//                            fechita.setMold("rounded");
////                           fechita.setSclass("resultado");
//                            row.appendChild(fechita);
//                            break;
//                        case "S":
//                            Radiogroup grpRadio = new Radiogroup();
//                            Radio lblSi = new Radio("SI");
//                            Radio lblNo = new Radio("NO");
//                            lblSi.setParent(grpRadio);
//                            lblNo.setParent(grpRadio);
//                            if (obligatorio.equals("0")) {
//                            }
//                            if (!(valorDef.isEmpty())) {
//                                if (valorDef.equals("SI")) {
//                                    lblSi.setChecked(true);
//                                    lblNo.setChecked(false);
//                                    // lblSi.setDisabled(true);
//                                    // lblNo.setDisabled(true);
//                                }
//                                if (valorDef.equals("NO")) {
//                                    lblSi.setChecked(false);
//                                    lblNo.setChecked(true);
//                                    //  lblSi.setDisabled(true);
//                                    //  lblNo.setDisabled(true);
//                                }
//                            }
//                            lblSi.setStyle("font-size: 16px;");
////                                lblSi.setSclass("resultado");
//                            lblNo.setStyle("font-size: 16px;");
////                                lblNo.setSclass("resultado");
//                            grpRadio.setWidth("98%");
//                            row.appendChild(grpRadio);
//                            break;
//                        case "L": {
//                            Combobox grpLista = new Combobox();
//                            String subString = itemsLista;
//                            while (!(subString.isEmpty())) {
//                                int pos = subString.indexOf("/");
//                                if (pos > 0) {
//                                    String sub = subString.substring(0, pos);
//                                    grpLista.appendItem(sub);
//                                    subString = subString.substring(pos + 1);
//                                } else {
//                                    subString = "";
//                                }
//                            }
//                            grpLista.setValue("(Seleccione)");
//                            if (!(valorDef.isEmpty())) {
//                                grpLista.setText(valorDef);
//                                // grpLista.setDisabled(true);
//                            }
//                            if (obligatorio.equals("0")) {
//                                grpLista.setConstraint("no empty, no future: Ingrese Dato");
//                            }
//                            grpLista.setStyle("font-size: 16px;");
//                            grpLista.setWidth("98%");
////                            grpLista.setSclass("resultado");
//                            row.appendChild(grpLista);
//                            break;
//                        }
//                        case "LD": {
//                            Hbox grpListaDetalle = new Hbox();
//                            grpListaDetalle.setWidth("98%");
//                            Combobox grpLista = new Combobox();
//                            Space espacio = new Space();
//                            String subString = itemsLista;
//                            while (!(subString.isEmpty())) {
//                                int pos = subString.indexOf("/");
//                                if (pos > 0) {
//                                    String sub = subString.substring(0, pos);
//                                    grpLista.appendItem(sub);
//                                    subString = subString.substring(pos + 1);
//                                } else {
//                                    subString = "";
//                                }
//                            }
//                            grpLista.setValue("(Seleccione)");
//                            if (!(valorDef.isEmpty())) {
//                                grpLista.setText(valorDef);
//                                //     grpLista.setDisabled(true);
//                            }
//                            if (obligatorio.equals("0")) {
//                                grpLista.setConstraint("no empty, no future: Ingrese Dato");
//                            }
//                            Textbox porq = new Textbox();
//                            porq.setWidth("190%");
////                            porq.setSclass("resultado");
//                            porq.setMaxlength(256);
//                            grpLista.setStyle("font-size: 16px;");
//                            grpLista.setParent(grpListaDetalle);
//                            espacio.setParent(grpListaDetalle);
//                            porq.setParent(grpListaDetalle);
//                            row.appendChild(grpListaDetalle);
//                            break;
//                        }
//                        case "B":                        //Audiometria
//                            Button btnAplet = new Button(lblDescripcion.getValue());
//                            if (lblDescripcion.getValue().equals("AUDIOMETRIA")) {
//                                btnAplet.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
//
//                                    @Override
//                                    public void onEvent(Event event) throws Exception {
////                                        loadAudioApplet();
//                                    }
//                                });
//                                if (!(valorDef.isEmpty())) {
//                                    btnAplet.setLabel(valorDef);
//                                }
//                                row.appendChild(btnAplet);
//                            }
//                            break;
//                        case "G": {
//                            break;
//                        }
//                        default: {
//                            final Textbox nText1 = new Textbox();
//                            nText1.setMaxlength(128);
//                            nText1.setCols(50);
//                            nText1.setWidth("98%");
//                            nText1.setSclass("resultado");
//                            if (obligatorio.equals("0")) {
//                                nText1.setConstraint("no empty, no future: Ingrese Dato");
//                            }
//                            if (!(valorDef.isEmpty())) {
//                                nText1.setValue(valorDef);
//                            }
//                            row.appendChild(nText1);
//                            break;
//                        }
//                    }
//                    if (row.getChildren().size() < 2) {
//                        Messagebox.show("Error al cargar el formato Nodo <2,", "Información de usuario", Messagebox.OK, Messagebox.ERROR);
//                        final Textbox nText = new Textbox();
//                        nText.setMaxlength(128);
//                        nText.setWidth("98%");
//                        nText.setSclass("resultado");
//                        row.appendChild(nText);
//                        row.appendChild(lblNombre);
//                    } else {
//                        row.appendChild(lblNombre);
//                    }
//                    row.setParent(rowsAnt);
//                }
//            }
//        } catch (Exception e) {
//        }
//    }
//
//    // </editor-fold>
//    //<editor-fold defaultstate="collapsed" desc="CARGA DE XML">
//    private void loadGridResultados(boolean estado) {
//        //desde XmlResultados
//
//        numFormatosAnteced = 1;
//        numFormato.setValue(Integer.toString(numFormatosAnteced));
//
//        try {
//            int x = rowsAnt.getChildren().size();
//            if (x >= 1) {//si hay datos toca eliminar
//                while (rowsAnt.getChildren().size() > 0) {
//                    for (int i = 0; i < rowsAnt.getChildren().size(); i++) {
//                        rowsAnt.getChildren().remove(i);
//                    }
//                }
//            }
//            String xml = objAntecedenes.getAntecedentes();
//            Document doc1 = ConvertirDocumento.getConvertirDocumentoDocument(xml);
//            NodeList listaHijos = doc1.getChildNodes();
//            listaHijos = listaHijos.item(0).getChildNodes();
//            GridResultados(listaHijos, estado);
//        } catch (RuntimeException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void GridResultados(NodeList listaHijos, boolean estado) {
//        Element elemento;
//        NodeList listGrupos, listHijosGrupo = null;
//        Label LblNombre, LblDescripcion, LblOrden;
//        Node NItem, NGurpo, NPagina;
//        Group grupo;
//        Row NewFila;
//        try {
//            for (int t = 1; t < listaHijos.getLength(); t++) {
//                NPagina = listaHijos.item(t);
//                if (NPagina.getNodeType() == Node.ELEMENT_NODE) {
//                    elemento = (Element) NPagina;
//                    if (elemento.getNodeName() != null) {
//                        String NamNText = elemento.getTagName();
//                        if (!(NamNText.equals("datos_generales_standar"))) {
//                            String idforma = elemento.getAttribute("IdFormato");
//                            numFormatosAnteced = numFormatosAnteced + 1;
//                            numFormato.setValue(Integer.toString(numFormatosAnteced));
//                            NamNText = NamNText + " ID Formato: " + elemento.getAttribute("Id");
//                            final Group fila = new Group();
//                            fila.setLabel(NamNText);
//                            fila.setId(idforma);
//                            fila.setOpen(false);
//                            fila.setVisible(false);
//                            fila.setParent(rowsAnt);
//                            listGrupos = NPagina.getChildNodes();
//                            for (int k = 0; k < listGrupos.getLength(); k++) {
//                                String descripcionXmlRes;
//                                NGurpo = listGrupos.item(k);
//                                if (NGurpo.getNodeType() == Node.ELEMENT_NODE) {
//                                    elemento = (Element) NGurpo;
//                                    if (elemento.getNodeName() != null) {
//                                        NamNText = elemento.getTagName();
//                                        grupo = new Group();
//                                        descripcionXmlRes = elemento.getAttribute("descripcion");
//                                        String sexVisible = elemento.getAttribute("sexo");
//                                        boolean visibleEditFrameTExt = true;
//                                        if (!(sexVisible.isEmpty())) {
//                                            if (!(sexVisible.equalsIgnoreCase(historia.getSexo()))) {
//                                                visibleEditFrameTExt = false;
//                                            }
//                                        }
//                                        grupo.setVisible(visibleEditFrameTExt);
//                                        grupo.setLabel(descripcionXmlRes);
//                                        grupo.setStyle(" border: 1px outset; background: #b5dbe5; color: black;text-align: center; ");
//                                        grupo.setId(NamNText);
//                                        grupo.setParent(rowsAnt);
//                                        listHijosGrupo = NGurpo.getChildNodes();
//                                        if (listHijosGrupo.getLength() > 0) {
//                                            for (int y = 0; y < listHijosGrupo.getLength(); y++) {
//                                                NItem = listHijosGrupo.item(y);
//                                                if (NItem.getNodeType() == Node.ELEMENT_NODE) {
//                                                    String nombreXmlRes;
//                                                    String ordenXmlRes;
//                                                    String tipoXmlRes;
//                                                    String ItemsListaXmlRes = null;
//                                                    int lineas = 1;
//                                                    elemento = (Element) NItem;
//                                                    if (elemento.getNodeName() != null) {
//                                                        NamNText = elemento.getTagName();
//                                                        nombreXmlRes = elemento.getTagName();
//                                                        descripcionXmlRes = elemento.getAttribute("descripcion");
//                                                        ordenXmlRes = elemento.getAttribute("orden");
//                                                        tipoXmlRes = elemento.getAttribute("tipo_dato");
//                                                        if ((tipoXmlRes.equals("L")) || (tipoXmlRes.equals("LD"))) {
//                                                            ItemsListaXmlRes = elemento.getAttribute("item_lista");
//                                                        }
//                                                        if (tipoXmlRes.equals("T")) {
//                                                            String xv = elemento.getAttribute("lineas");
//                                                            if (xv.isEmpty()) {
//                                                                lineas = 1;
//                                                            } else {
//                                                                lineas = Integer.parseInt(xv);
//                                                            }
//                                                        }
//                                                        if (tipoXmlRes.isEmpty() && nombreXmlRes.equalsIgnoreCase("dx")) {
//                                                            tipoXmlRes = "G";
//                                                        }
//                                                        String valor = elemento.getTextContent();
//                                                        try {
//                                                            if (valor == null || valor.trim().equalsIgnoreCase("")) {
//                                                                valor = "";
//                                                            }
//                                                        } catch (Exception e) {
//                                                            valor = "";
//                                                        }
//                                                        LblDescripcion = new Label(descripcionXmlRes);
//                                                        if (!(descripcionXmlRes.equals("S/D"))) {
//                                                            LblDescripcion.setSclass("descripcion-resultado");
//                                                        }
//                                                        LblOrden = new Label(ordenXmlRes);
//                                                        LblNombre = new Label(nombreXmlRes);
//
//                                                        NewFila = new Row();
//                                                        NewFila.setSclass("row-resultado");
//                                                        NewFila.appendChild(LblDescripcion);
//                                                        //verifico tipos de datos
//                                                        switch (tipoXmlRes) {
//                                                            case "T": {
//                                                                final Textbox nText = new Textbox(valor);
//                                                                nText.setDisabled(!estado);
//                                                                nText.setSclass("resultado-completo");
//                                                                nText.setId("T" + LblNombre.getValue());
//                                                                if (valor.isEmpty()) {
//                                                                    nText.setSclass("resultado-incompleto");
//                                                                    nText.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                        @Override
//                                                                        public void onEvent(Event event) {
//                                                                            nText.setSclass("resultado-completo");
//                                                                        }
//                                                                    });
//                                                                }
//                                                                switch (lineas) {
//                                                                    case 0: {
//                                                                        nText.setMultiline(true);
//                                                                        if (!copiar) {
//                                                                            nText.setCtrlKeys("^c^v");
//                                                                            nText.addEventListener(Events.ON_CTRL_KEY, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                                @Override
//                                                                                public void onEvent(Event event) {
//                                                                                    int keyCode = ((KeyEvent) event).getKeyCode();
//                                                                                    if (keyCode == 67 || keyCode == 86) {
//                                                                                    }
//                                                                                }
//                                                                            });
//                                                                            nText.addEventListener(Events.ON_RIGHT_CLICK, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                                @Override
//                                                                                public void onEvent(Event event) {
//                                                                                }
//                                                                            });
//                                                                        }
//                                                                        nText.addEventListener(Events.ON_DOUBLE_CLICK, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                            @Override
//                                                                            public void onEvent(Event event) {
//                                                                                Window winEdicion = new Window();
//                                                                                String windowMessage;
//                                                                                windowMessage = "edicion-aux.zul";
//                                                                                winEdicion.setWidth("550px");
//                                                                                Executions.createComponents(windowMessage, winEdicion, null);
//                                                                                final Textbox edicion = (Textbox) winEdicion.getFellow("edit-aux", true);
//                                                                                edicion.setSclass("edit-resultado");
//                                                                                if (!copiar) {
//                                                                                    edicion.setCtrlKeys("^c^v");
//                                                                                    edicion.addEventListener(Events.ON_CTRL_KEY, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                                        @Override
//                                                                                        public void onEvent(Event event) {
//                                                                                            int keyCode = ((KeyEvent) event).getKeyCode();
//                                                                                            if (keyCode == 67 || keyCode == 86) {
//                                                                                            }
//                                                                                        }
//                                                                                    });
//                                                                                    edicion.addEventListener(Events.ON_RIGHT_CLICK, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                                        @Override
//                                                                                        public void onEvent(Event event) {
//                                                                                        }
//                                                                                    });
//                                                                                }
//                                                                                winEdicion.addEventListener(Events.ON_CLOSE, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                                    @Override
//                                                                                    public void onEvent(Event event) {
//                                                                                        nText.setValue(edicion.getValue());
//                                                                                    }
//                                                                                });
//                                                                                edicion.setValue(nText.getValue());
//                                                                                winEdicion.setTitle("Edición de resultado");
//                                                                                winEdicion.setClosable(true);
//                                                                                winEdicion.setSizable(true);
//                                                                                winEdicion.setId("winEditAuxiliar");
//                                                                                winEdicion.setParent(WinHistoria);
//                                                                                winEdicion.doModal();
//                                                                            }
//                                                                        });
//                                                                        nText.setRows(5);
//                                                                        break;
//                                                                    }
//                                                                    case 1: {
//                                                                        nText.setMaxlength(128);
//                                                                        break;
//                                                                    }
//                                                                    case 2: {
//                                                                        nText.setMaxlength(256);
//                                                                        break;
//                                                                    }
//                                                                    default: {
//                                                                        nText.setMaxlength(128);
//                                                                        break;
//                                                                    }
//                                                                }
//                                                                nText.setWidth("98%");
//                                                                NewFila.appendChild(nText);
//                                                                break;
//                                                            }
//                                                            case "N": {
//                                                                final Decimalbox nDecimal;
//                                                                if (valor.isEmpty()) {
//                                                                    nDecimal = new Decimalbox();
//                                                                    nDecimal.setSclass("resultado-incompleto");
//                                                                    nDecimal.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                        @Override
//                                                                        public void onEvent(Event event) {
//                                                                            nDecimal.setSclass("resultado-completo");
//                                                                        }
//                                                                    });
//                                                                } else {
//                                                                    nDecimal = new Decimalbox(new BigDecimal(valor));
//                                                                    nDecimal.setSclass("resultado-completo");
//                                                                }
//                                                                nDecimal.setCols(20);
//                                                                nDecimal.setDisabled(!estado);
//                                                                NewFila.appendChild(nDecimal);
//                                                                break;
//                                                            }
//                                                            case "D": {
//                                                                if (!(estado)) {
//                                                                    final Label nFecha = new Label(valor);
//                                                                    nFecha.setStyle("color:black;");
//                                                                    NewFila.appendChild(nFecha);
//                                                                } else {
//                                                                    final Datebox nFecha = new Datebox();
//                                                                    nFecha.setSclass("resultado");
//                                                                    if (!(valor.trim().isEmpty())) {
//                                                                        nFecha.setFormat("dd-MMM-yyyy");
//                                                                        DateFormat formatter;
//                                                                        formatter = new SimpleDateFormat("dd-MMM-yyyy");
//                                                                        Date fec = null;
//                                                                        try {
//                                                                            fec = (Date) formatter.parse(valor);
//                                                                        } catch (ParseException e) {
//                                                                            StringTokenizer stk = new StringTokenizer(valor, "/");
//                                                                            String d = stk.nextToken();
//                                                                            String m = stk.nextToken();
//                                                                            String a = stk.nextToken();
//                                                                            valor = a + "/" + m + "/" + d;
//                                                                            Date nuv = new Date(valor);
//                                                                            valor = formatter.format(nuv);
//                                                                            fec = (Date) formatter.parse(valor);
//                                                                            System.out.println("ERROR DANDO FORMATO FECHA DEL XML " + valor + "rESxML " + objAntecedenes.getId() + " ID orden " + objAntecedenes.getNroOrd());
//                                                                            throw new RuntimeException(e);
//                                                                        }
//                                                                        nFecha.setValue(fec);
//                                                                    } else {
//                                                                        nFecha.setSclass("resultado-incompleto");
//                                                                        nFecha.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                            @Override
//                                                                            public void onEvent(Event event) {
//                                                                                nFecha.setSclass("resultado-completo");
//                                                                            }
//                                                                        });
//                                                                    }
//                                                                    nFecha.setCols(16);
//                                                                    nFecha.setMold("rounded");// nFecha.setFormat("yyyy/MM/dd"); //nFecha.setFormat("MM-dd-yy");
//                                                                    NewFila.appendChild(nFecha);
//                                                                }
//                                                                break;
//                                                            }
//                                                            case "S": {
//                                                                if (!(estado)) {
//                                                                    final Label nValor = new Label(valor);
//                                                                    nValor.setStyle("color:black;");
//                                                                    NewFila.appendChild(nValor);
//                                                                } else {
//                                                                    Radiogroup grpRadio = new Radiogroup();
//                                                                    final Radio lblSi = new Radio("SI");
//                                                                    final Radio lblNo = new Radio("NO");
//                                                                    if (valor.equals("SI")) {
//                                                                        lblSi.setChecked(true);
//                                                                        lblSi.setSclass("resultado");
//                                                                    } else {
//                                                                        if (valor.equals("NO")) {
//                                                                            lblNo.setChecked(true);
//                                                                            lblNo.setSclass("resultado");
//                                                                        }
//                                                                    }
//                                                                    if (valor.trim().isEmpty()) {
//                                                                        lblSi.setSclass("sino-incompleto");
//                                                                        lblNo.setSclass("sino-incompleto");
//                                                                        lblSi.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                            @Override
//                                                                            public void onEvent(Event event) {
//                                                                                lblSi.setSclass("resultado");
//                                                                                lblNo.setSclass("resultado");
//                                                                            }
//                                                                        });
//
//                                                                        lblNo.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                            @Override
//                                                                            public void onEvent(Event event) {
//                                                                                lblNo.setSclass("resultado");
//                                                                                lblSi.setSclass("resultado");
//                                                                            }
//                                                                        });
//                                                                    }
//                                                                    lblSi.setStyle("font-size: 16px;");
//                                                                    lblNo.setStyle("font-size: 16px;");
//                                                                    lblSi.setParent(grpRadio);
//                                                                    lblNo.setParent(grpRadio);
//                                                                    NewFila.appendChild(grpRadio);
//                                                                }
//                                                                break;
//                                                            }
//                                                            case "L": {
//                                                                if (valor.isEmpty()) {
//                                                                    NewFila.setStyle("color:red;");
//                                                                }
//                                                                final Combobox grpLista = new Combobox();
//                                                                grpLista.setSclass("resultado");
//                                                                String subString = ItemsListaXmlRes;
//                                                                while (!(subString.isEmpty())) {
//                                                                    int pos = subString.indexOf("/");
//                                                                    if (pos > 0) {
//                                                                        String sub = subString.substring(0, pos);
//                                                                        grpLista.appendItem(sub);
//                                                                        subString = subString.substring(pos + 1);
//                                                                    } else {
//                                                                    }
//                                                                }
//                                                                if (valor.trim().isEmpty()) {
//                                                                    grpLista.setValue("(Seleccione)");
//                                                                    grpLista.setSclass("resultado-incompleto");
//                                                                    grpLista.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                        @Override
//                                                                        public void onEvent(Event event) {
//                                                                            grpLista.setSclass("resultado-completo");
//                                                                        }
//                                                                    });
//                                                                } else {
//                                                                    grpLista.setValue(valor);
//                                                                    grpLista.setSclass("resultado");
//                                                                }
//                                                                grpLista.setStyle("font-size: 16px;");
//                                                                grpLista.setWidth("95%");
//                                                                grpLista.setDisabled(!estado);
//                                                                NewFila.appendChild(grpLista);
//                                                                break;
//                                                            }
//                                                            case "LD": {
//                                                                if (!(estado)) {
//                                                                    String va = null;
//                                                                    if (!(valor.isEmpty())) {
//
//                                                                        String g = valor.substring(0, valor.indexOf("|"));
//                                                                        va = g;
//                                                                        g = valor.substring(valor.indexOf("|") + 1);
//                                                                        g = g.substring(0, g.indexOf("|"));
//                                                                        if (!(g.isEmpty())) {
//                                                                            va = va + " , " + g;
//                                                                        }
//                                                                    } else {
//                                                                        va = "";
//                                                                    }
//                                                                    final Label nValor = new Label(va);
//                                                                    nValor.setWidth("95%");
//                                                                    nValor.setStyle("color:black;");
//                                                                    NewFila.appendChild(nValor);
//
//                                                                } else {
//                                                                    Hbox grpListaDetalle = new Hbox();
//                                                                    final Combobox grpLista = new Combobox();
//                                                                    grpLista.setParent(grpListaDetalle);
//                                                                    Space espacio = new Space();
//                                                                    espacio.setParent(grpListaDetalle);
//                                                                    String subString = ItemsListaXmlRes;
//                                                                    final Textbox porq = new Textbox();
//                                                                    porq.setWidth("190%");
//                                                                    porq.setMaxlength(256);
//                                                                    porq.setParent(grpListaDetalle);
//                                                                    while (!(subString.isEmpty())) {
//                                                                        int pos = subString.indexOf("/");
//                                                                        if (pos > -1) {
//                                                                            String sub = subString.substring(0, pos);
//                                                                            grpLista.appendItem(sub);
//                                                                            subString = subString.substring(pos + 1);
//                                                                        } else {
//                                                                        }
//                                                                    }
//                                                                    if (valor.isEmpty()) {
//                                                                        grpLista.setValue("(Seleccione)");
//                                                                        grpLista.setSclass("resuldo-incompleto");
//                                                                        porq.setSclass("resultado-incompleto");
//                                                                        grpLista.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                            @Override
//                                                                            public void onEvent(Event event) {
//                                                                                grpLista.setClass("resultado-completo");
//                                                                                porq.setSclass("resultado-completo");
//                                                                            }
//                                                                        });
//                                                                        porq.setSclass("resultado-incompleto");
//                                                                    } else {
//                                                                        //Si el resultado NO esta vacio
//                                                                        String g = valor.substring(0, valor.indexOf("|"));
//                                                                        grpLista.setValue(g);
//                                                                        g = valor.substring(valor.indexOf("|") + 1);
//                                                                        g = g.substring(0, g.indexOf("|"));
//                                                                        porq.setValue(g);
//                                                                        porq.setSclass("resultado");
//                                                                    }
//                                                                    grpListaDetalle.setStyle("font-size: 16px;");
//                                                                    NewFila.appendChild(grpListaDetalle);
//                                                                }
//                                                                break;
//                                                            }
//                                                            case "B": {
//                                                                alert("No tiene definido tipo de dato");
//                                                                break;
//                                                            }
//
//                                                            case "G": {
//                                                                alert("No tiene definido tipo de dato");
//                                                                break;
//                                                            }
//                                                            default:
//                                                                final Textbox nText1 = new Textbox();
//                                                                nText1.setDisabled(!estado);
//                                                                nText1.setSclass("resultado");
//                                                                if (valor.isEmpty()) {
//                                                                    nText1.setSclass("resultado-incompleto");
//                                                                    nText1.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                        @Override
//                                                                        public void onEvent(Event event) {
//                                                                            nText1.setSclass("resultado-completo");
//                                                                        }
//                                                                    });
//                                                                }
//                                                                nText1.setValue(valor);
//                                                                nText1.setWidth("90%");
//                                                                NewFila.appendChild(nText1);
//                                                                break;
//                                                        }
//                                                        if (NewFila.getChildren().size() < 2) {
//                                                            final Textbox nText1 = new Textbox();
//                                                            nText1.setDisabled(!estado);
//                                                            nText1.setSclass("resultado");
//                                                            if (valor.isEmpty()) {
//                                                                nText1.setSclass("resultado-incompleto");
//                                                                nText1.addEventListener(Events.ON_CHANGE, new org.zkoss.zk.ui.event.EventListener<Event>() {
//
//                                                                    @Override
//                                                                    public void onEvent(Event event) {
//                                                                        nText1.setSclass("resultado-completo");
//                                                                    }
//                                                                });
//                                                            }
//                                                            nText1.setValue(valor);
//                                                            nText1.setWidth("90%");
//                                                            NewFila.appendChild(nText1);
//                                                            Messagebox.show("Existe un error en el resultado: " + LblDescripcion.getValue(),
//                                                                    "Información de usuario", Messagebox.OK, Messagebox.ERROR);
//                                                        } else {
//                                                            NewFila.appendChild(LblNombre);
//                                                            NewFila.appendChild(LblOrden);
//                                                        }
//                                                        try {
//                                                            NewFila.setId(LblNombre.getValue());
//                                                            NewFila.setVisible(visibleEditFrameTExt);
//                                                            NewFila.setParent(rowsAnt);
//                                                        } catch (Exception e) {
//                                                            alert("no puede cargar" + LblNombre.getValue());
//                                                        }
//
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            rowsAnt.setVisible(true);
//            if (estado) {
//                modificar.setValue("T");
//            } else {
//                modificar.setValue("F");
//            }
//        } catch (ParseException | RuntimeException e) {
//        }
//    }
//    // </editor-fold>
//}

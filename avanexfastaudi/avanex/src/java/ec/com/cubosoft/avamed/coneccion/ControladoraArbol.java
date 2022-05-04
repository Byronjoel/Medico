package ec.com.cubosoft.avamed.coneccion;

import com.lowagie.text.DocumentException;
import ec.com.cubosoft.avamed.modelo.core.CsPerxgru;
import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import ec.com.cubosoft.avamed.modelo.medico.Area;
import ec.com.cubosoft.avamed.modelo.nextla.LispetAvanex;
import ec.com.cubosoft.avamed.modelo.nextla.SUsuar;
import ec.com.cubosoft.avamed.modelo.nextla.sessionOk;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import ec.com.cubosoft.avamed.modelo.persona.ResultadoGrafico;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import ec.com.cubosoft.avamed.modelo.vistas.Pedidos;
import ec.com.cubosoft.avamed.modelo.vistas.Resultado;
import ec.com.cubosoft.avamed.modelo.vistas.VserImagen;
import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import ec.com.cubosoft.avamed.procesos.AdmiNegocioSql;
import ec.com.cubosoft.avamed.procesos.iReport;
import java.io.*;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import javax.naming.NamingException;
import net.sf.jasperreports.engine.JRException;
import org.w3c.dom.*;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.*;
import org.zkoss.zul.Timer;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 *
 * @author Patricia Amoroso
 * @version 1.0
 */
public class ControladoraArbol extends GenericForwardComposer {

//    private static final long serialVersionUID = 1L;
    //<editor-fold defaultstate="collapsed" desc="Datos Pagina">
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Datos fijos Sesion">
    CsPerxgru permisosPagina;
    // CsUsuarios usuario;
    Nombre medico;
    //private CsUsuarios objUsuarioActivo;
//    private Iso3166R2 ciudad;
    ArrayList areas = new ArrayList();
    //  private List<CsPerxgru> listPermisos;
    Label counter;
//    private final int autoSave = 30;
//    private int countNum = autoSave;
    Progressmeter pm;
    Timer contador;
    Include barraBotones;
    Menupopup editPopup;
    private String path;

    Area areaMed;
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Datos Informe">
    int numFormatos;
    Document docrecet;
    Bandbox bbAreas;
    Rows rowsXML;
    private String idSeleccion = "", ocupacion = "";
    Pedidos PracticaOrden;
    Treechildren root;
    Rows rootPacs;
    private Iframe reporte;
    Date fechaOrden;
    String idref;
    Boolean bCargaAbreviaturas = false;
//    ArrayList diaTotal;
//    ArrayList diagobs;
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables Negocio">
    AdmiNegocio admNegocio;
    Window WinHistoria;

    // </editor-fol
    //<editor-fold defaultstate="collapsed" desc="InicioSesinon">
    public void onCreate$WindowArbol() {
        try {
            modificarSession();
        } catch (Exception e) {
            //System.out.println(" onCreate$WindowArbol " + usuario);
            throw new RuntimeException(e);
        }
    }

    public void cleanTree() {
        while (root.getItemCount() > 0) {
            root.removeChild(root.getFirstChild());
        }
    }
    private String usuario;
    private sessionOk objsessiActica;
    private boolean abierto;
    private boolean impr;
    private boolean carga;
    private boolean edit;
    private boolean copiar;

    private void modificarSession() {
        try {
            cleanTree();
            admNegocio = new AdmiNegocio();

            ProcesosSession admiSessionUsuario = new ProcesosSession();
            objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
            objsessiActica.setPagina(page.getId());
            List listaper = null;
            CsUsuarios usuarioP;
            SUsuar usuarioN = null;
            usuario = "";
            admiSessionUsuario.AgregarAtributoSession(2, objsessiActica, desktop.getSession());
            if (objsessiActica.getTipo() == 1) {
                listaper = objsessiActica.getPerUsuNext();
                usuarioN = objsessiActica.getUsuarioN();
                usuario = usuarioN.getUsuario();

            } else {
                listaper = objsessiActica.getPerUsuAva();
                usuarioP = objsessiActica.getUsuarioP();
                usuario = usuarioP.getUsuario();
                if (usuarioP.getMedicos() != null) {
                    if (usuarioP.getMedicos().size() > 0) {
                        medico = usuarioP.getMedicos().get(0);

                        bbAreas.setValue(medico.getArea().getDescripcion());
                    } else {
                        Map<String, Object> wSQL = new HashMap<>();
                        List oSQL = new ArrayList();
                        wSQL.put("id ?=", 0);
                        List data = admNegocio.getData(new Nombre(), wSQL, null, oSQL);
                        if (data.size() > 0) {
                            medico = (Nombre) data.get(0);
                        }
                    }

                }
                CsUsuarios objusu = objsessiActica.getUsuarioP();
                if (objusu.getPerUpload().intValue() == 1) {
                    carga = false;
                } else {
                    carga = true;
                }
                if (objusu.getPerAbto() == 1) {
                    abierto = false;
                } else {
                    abierto = true;
                }
                if (objusu.getPriVis() == 0) {
                    impr = false;
                } else {
                    impr = true;
                }
                if (objusu.getPerCyp() == 1) {
                    copiar = false;
                } else {
                    copiar = true;
                }
            }

            if (medico != null) {

//                medico = usuario.getMedicos().get(0);
                areaMed = medico.getArea();
                bbAreas.setValue(medico.getArea().getDescripcion());

            } else {
                Map<String, Object> wSQL = new HashMap<>();
                List oSQL = new ArrayList();
                wSQL.put("id ?=", 0);
                List data = admNegocio.getData(new Nombre(), wSQL, null, oSQL);
                if (data.size() > 0) {
                    medico = (Nombre) data.get(0);
                    areaMed = medico.getArea();
                }
            }
            admNegocio = new AdmiNegocio();
            String idAreaVista;
            if (medico != null) {
                idAreaVista = medico.getArea().getPerArea();
                int pos = 0;
                if (!(idAreaVista.equalsIgnoreCase("0"))) {
                    while (pos < idAreaVista.length()) {
                        String g = idAreaVista.substring(pos, (idAreaVista.indexOf(",")));
                        areas.add(g);
                        pos = idAreaVista.indexOf(",") + 1;
                        idAreaVista = idAreaVista.substring(pos);
                        pos = 0;
                    }
                }
            } else {
                idAreaVista = "0";
            }
            List ListaXmlResultados = getInformes();
            if (ListaXmlResultados != null) {
                if (ListaXmlResultados.size() > 0) {
                    //   System.out.println("Load Tree 146");
                    loadTree(ListaXmlResultados);
                }
            }
        } catch (NamingException e) {
            System.out.println(usuario + "Modificar Sessio" + desktop.getSession() + " " + usuario);
            throw new RuntimeException(e);
        } catch (WrongValueException e) {
            System.out.println(usuario + "Modificar Sessio" + desktop.getSession() + " " + usuario);
            throw new RuntimeException(e);
        }
    }

    private boolean consultarRis(Resultado ris) {
        try {
            byte[] byteArray = null;
            InputStream inputStream = null;
            URLConnection conn = new URL(ris.getLinck()).openConnection();
            inputStream = conn.getInputStream();
            byte[] buffer = new byte[8192];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            byteArray = baos.toByteArray();
            ResultadoGrafico pdf = new ResultadoGrafico();
            pdf.setCod("PDF");
            pdf.setFirstUser(usuario);
            pdf.setDato(byteArray);
            pdf.setDescripcion("RIS");
            pdf.setLockReg(new Short("0"));
            pdf.setIdXmlResultado(ris.getId().longValue());
            pdf = (ResultadoGrafico) admNegocio.guardar(pdf);
            System.out.println("pdf"+pdf);
            if (pdf != null) {
                Map<String, Object> wSQL = new HashMap<String, Object>();
                wSQL.put("id ?=", ris.getId());
                List objectList = admNegocio.getData(new XmlResultado(), wSQL, null, null);
                if (objectList.size() == 1) {
                    XmlResultado resul = (XmlResultado) objectList.get(0);
                    resul.setResultado("PDF");
                    if (admNegocio.actualizar(resul)) {
                        return true;
                    }
                } else {
                    Messagebox.show("Verificar estado Informe ext(722)", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
                    return false;
                }
            } else {
                Messagebox.show("No se puede recuperar informe", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
                return false;
            }
        } //            String dirPDF = getUbicacion() + "Ris.pdf";
        catch (Exception e) {
            System.out.println("ec.com.cubosoft.avamed.procesos.iReport.consultarRis()");
        }
        return false;
    }

    //<editor-fold defaultstate="collapsed" desc="Report">
//    private static PdfResponse pdfXOrden(java.lang.String order, java.lang.String password) {
//        net.cubosoft.misanalisis.ws.EReport service = new net.cubosoft.misanalisis.ws.EReport();
//        net.cubosoft.misanalisis.ws.EReportWS port = service.getEReportWSPort();
//        return port.pdfXOrden(order, password);
//    }
    private List ListapedidosN;
    private LispetAvanex objOrdenN;

    private void loadReport(Resultado resultado) {

        List<Resultado> resultados = new ArrayList<Resultado>();
        resultados.add(resultado);
        loadReport(resultados);
    }

    private void loadReport(List<Resultado> resultados) {

        Window winMensaje = new Window();
        String windowMessage = "msg_informe.zul";
        Executions.createComponents(windowMessage, winMensaje, null);
        winMensaje.setBorder("normal");
        winMensaje.setClosable(true);
        winMensaje.setTitle("Vista Preliminar de Informe");
        final Label msg = new Label();
        msg.setParent(winMensaje);
        msg.setVisible(false);
        final Iframe reporteV = (Iframe) winMensaje.getFellow("reporteV", false);
        String fileName = "resultados.pdf";
        if (resultados.size() == 1) {
            fileName = null;
            fileName = "resultado_O" + resultados.get(0).getIdOrden() + "_P" + resultados.get(0).getIdPractica();
        }
        iReport reportes = new iReport();
//            boolean impre = false;
//            if (impre) {
//
//                impre = true;
//
//            }
//
//
        byte[] buf = null;
        try {
            buf = reportes.getReportVista(resultados, true, impr);
        } catch (Exception e) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraInformes.loadReportVista()");
        }
        if (buf != null) {
            InputStream mediaIS = new ByteArrayInputStream(buf);
            AMedia media = new AMedia(fileName, "pdf", "application/pdf", mediaIS);
            reporteV.setContent(media);
            reporteV.setVisible(true);
        } else {
            Messagebox.show("El informe esta incompleto o no esta cerrado, consulte con el medico responsable",
                    "Información / Esta incompleto el informe", Messagebox.OK, Messagebox.INFORMATION);
            return;
        }
        winMensaje.setId("winMsgPreview");
        winMensaje.setParent(WinHistoria);
        winMensaje.doModal();
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Sesion / pagina / reset/limpiar">
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Consultas">
    private List getInformes() throws NamingException {
        admNegocio = new AdmiNegocio();
        Map<String, Object> wSQL = new HashMap<String, Object>();
        Historia historia = objsessiActica.getObjHistoria();
        List oSQL = new ArrayList();
        wSQL.put("idHistoria?=", historia.getId());
        wSQL.put("numId ?=", historia.getNumId());
        oSQL.add("idOrden");
//        wSQL.put("his.id?=", historia.getId());
//        wSQL.put("his.numId ?=", historia.getNumId());
//        oSQL.add("nroOrd");
        return admNegocio.getData(new Resultado(), wSQL, null, oSQL);
    }

    private List getLinckInformes() throws NamingException {
        admNegocio = new AdmiNegocio();
        Map<String, Object> wSQL = new HashMap<String, Object>();
        ProcesosSession admiSessionUsuario = new ProcesosSession();
        Historia historia = (Historia) admiSessionUsuario.ObtenerAtributoSession(7, desktop.getSession());
        List oSQL = new ArrayList();
        wSQL.put("pacid?=", historia.getId());
        oSQL.add("orden");
        return admNegocio.getData(new VserImagen(), wSQL, null, oSQL);
    }

    private void loadTreeLinck(List<VserImagen> xmlResultados) {
        for (VserImagen resultado : xmlResultados) {
            if ((resultado.getLink() != null)) {
                boolean vista = false;
                String area = resultado.getArea().toString();
                String areaM = "";
                for (int i = 0; i < areas.size(); i++) {
                    if (area.equalsIgnoreCase((String) areas.get(i))) {
                        vista = true;
                        areaM = areas.get(i).toString();
                    } else {
                        if (((String) areas.get(i)).equalsIgnoreCase("0")) {
                            areaM = "0";
                        }
                    }
                }
                if ((vista) || (areaM.equalsIgnoreCase("0"))) {
                    A likc = new A();
                    likc.setLabel(resultado.getDes());
                    likc.setHref(resultado.getLink());
                    likc.setStyle("color:#; font-size: 8px;");
                    likc.setTarget("_blank");
                    Row NewFila = new Row();
                    NewFila.setSclass("row-resultado");
                    NewFila.appendChild(new Label(resultado.getOrden().toString()));
                    NewFila.appendChild(likc);
                    NewFila.setParent(rootPacs);
                }
            }
        }
    }

    private void loadTree(List<Resultado> xmlResultados) {
        Treechildren childrenPaciente = null;
        Treechildren childrenOrden = null;
        BigInteger idHistoriaN = new BigInteger("0");
        BigInteger idOrdenN = new BigInteger("-1");
        Treeitem itemHistoria = null;
        boolean practicasT = false;
        boolean ordenT = false;
        Treeitem itemOrden = null;
        for (Resultado resultado : xmlResultados) {
            if (!idHistoriaN.equals(resultado.getIdHistoria())) {
                idHistoriaN = resultado.getIdHistoria();
                itemHistoria = new Treeitem();
                itemHistoria.setLabel(resultado.getNombres() + " " + resultado.getApellidos());
                itemHistoria.setStyle("color:#6e1213; font-size: 5px;");
                childrenPaciente = new Treechildren();
                idOrdenN = new BigInteger("-1");
                itemHistoria.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {

                    @Override
                    public void onEvent(Event event) throws Exception {
                        System.out.append(event.getTarget().toString());
                        List<Resultado> resultados = new ArrayList<Resultado>();
                        Treeitem itemHistoriaClicked = (Treeitem) event.getTarget();
                        resultados.addAll(getResultadoLoopHistoria(itemHistoriaClicked));
                        if (resultados.size() > 0) {
                            loadReport(resultados);
                        } else {
                            reporte.setVisible(false);
                            Messagebox.show("No existen informes listos para impresion en la orden escogida.");
                        }
                    }
                });
            }
            //Nodo de Orden
            if (!idOrdenN.equals(resultado.getIdOrden())) {
                practicasT = false;

                idOrdenN = resultado.getIdOrden();
                itemOrden = new Treeitem();
                if (resultado.getNroOrd() != null) {
                    if (resultado.getNroOrd() > 0) {
                        itemOrden = new Treeitem(resultado.getIdOrden().toString() + " /  " + resultado.getNroOrd(), resultado.getIdOrden());
                        itemOrden.setStyle("color: red; ");
                        itemOrden.setImage("/images/nextl.png");
                    } else {
                        itemOrden = new Treeitem(resultado.getIdOrden().toString(), resultado.getIdOrden());
                        itemOrden.setStyle("color: #008CBA; ");
                        itemOrden.setImage("/images/ava.png");
                    }
                } else {
                    itemOrden = new Treeitem(resultado.getIdOrden().toString(), resultado.getIdOrden());
                    itemOrden.setStyle("color: #008CBA; ");
                    itemOrden.setImage("/images/ava.png");
                }
                itemOrden.setStyle("color:#e9f354; font-size: 10px;");
                ordenT = true;
                childrenPaciente.setParent(itemHistoria);
                childrenOrden = new Treechildren();
                itemOrden.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        List<Resultado> resultados = new ArrayList<Resultado>();
                        Treeitem itemOrdenClicked = (Treeitem) event.getTarget();
                        resultados.addAll(getResultadoLoopOrden(itemOrdenClicked));
                        if (resultados.size() > 0) {
                            loadReport(resultados);
                        } else {
                            reporte.setVisible(false);
                            Messagebox.show("No existen informes listos para impresion en la orden escogida.");
                        }
                    }
                });
            }
            //Nodo de Informes XML
            Treeitem itemInforme = new Treeitem();
            if (((resultado.getIdPractica() == 415) || (!(resultado.getEstado().equalsIgnoreCase("IN")))) && ((!(resultado.getEstado().equalsIgnoreCase("PE"))))) {
                itemInforme.setValue(resultado);
                Treerow rowInforme = new Treerow();
                boolean vista = false;
                String area = resultado.getIdArea().toString();
                String areaM = "";
                for (int i = 0; i < areas.size(); i++) {
                    if (area.equalsIgnoreCase((String) areas.get(i))) {
                        vista = true;
                        areaM = areas.get(i).toString();
                    } else {
                        if (((String) areas.get(i)).equalsIgnoreCase("0")) {
                            areaM = "0";
                        }
                    }
                }
                if ((vista) || (areaM.equalsIgnoreCase("0"))) {
                    Treecell cellPractica = new Treecell(resultado.getDescripcion());
                    boolean ris = false;
                    if ((resultado.getEstado().equalsIgnoreCase("CO") && (resultado.getResultado().equalsIgnoreCase("RIS")))) {
                        ris = consultarRis(resultado);
                    }
                    cellPractica.setParent(rowInforme);
                    cellPractica.setStyle("color:#037a75; font-weight: bold;font-size:9px;");

                    Treecell cellImgEstado = new Treecell();
                    Image imageLock;
                    String stado = resultado.getEstado();
                    switch (stado) {
                        case "IN": {
                            imageLock = new Image("/images/editing.png");
                            break;
                        }
                        case "CO": {
                            if (!ris) {
                                if (resultado.getResultado().equalsIgnoreCase("RIS")) {
                                    imageLock = new Image("/images/ris.png");
                                } else {
                                    imageLock = new Image("/images/lock.png");
                                }
                            } else {
                                if (resultado.getMedico().equalsIgnoreCase("RIS")) {
                                    imageLock = new Image("/images/ris.png");
                                } else {
                                    imageLock = new Image("/images/lock.png");
                                }
                            }
                            break;
                        }
                        case "AU": {
                            imageLock = new Image("/images/ok.png");
                            break;
                        }
                        case "AR": {
                            imageLock = new Image("/images/archivado.png");
                            break;
                        }
                        case "IM": {
                            imageLock = new Image("/images/pdf.png");
                            break;
                        }
                        default: {
                            imageLock = new Image("/images/document_blank.png");
                            break;
                        }
                    }
                    imageLock.setParent(cellImgEstado);
                    cellImgEstado.setParent(rowInforme);
                    rowInforme.setParent(itemInforme);
                    vista = false;
                    itemInforme.setParent(childrenOrden);
                    practicasT = true;
                    itemInforme.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {

                        @Override
                        public void onEvent(Event event) throws Exception {
                            Treeitem itemInformeClicked = (Treeitem) event.getTarget();
                            Resultado resultado = (Resultado) itemInformeClicked.getValue();
                            if (resultado.getEstado().equals("CO")) {
                                System.out.append("Informe No. " + resultado.getId().toString());
                                loadReport(resultado);
                            } else {
                                if (resultado.getEstado().equals("AU")) {
                                    System.out.append("Informe No. " + resultado.getId().toString());
                                    loadReport(resultado);
                                } else {
                                    if (resultado.getEstado().equals("AR")) {
                                        System.out.append("Informe No. " + resultado.getId().toString());
                                        loadReport(resultado);
                                    } else {
                                        if (resultado.getEstado().equals("IM")) {
                                            System.out.append("Informe No. " + resultado.getId().toString());
                                            loadReport(resultado);
                                        } else {
                                            if (resultado.getEstado().equals("PE")) {
                                                Messagebox.show("Informe Pendiente.");
                                            } else {
                                                if (resultado.getIdPractica() == 415) {
                                                    loadReport(resultado);
                                          
                                                } else {
                                                    reporte.setVisible(false);
                                                    Messagebox.show("Informe Incompleto, no se puede mostrar para impresión.");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    });
                }
            }
            if ((ordenT) && (practicasT)) {
                childrenPaciente.appendChild(itemOrden);
                ordenT = false;
            }
            if (practicasT) {
                childrenOrden.setParent(itemOrden);
            }
        }
        childrenPaciente.setParent(itemHistoria);
        itemHistoria.setParent(root);
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Arbol">
    private List<Resultado> getResultadoLoopHistoria(Treeitem itemHistoria) {
        List<Resultado> resultados = new ArrayList<Resultado>();

        for (Object objeto : itemHistoria.getChildren()) {
            if (objeto.getClass().getCanonicalName().equals(Treechildren.class
                    .getCanonicalName())) {
                for (Object subObjeto : ((Treechildren) objeto).getChildren()) {
                    if (subObjeto.getClass().getCanonicalName().equals(Treeitem.class
                            .getCanonicalName())) {
                        resultados.addAll(getResultadoLoopOrden(((Treeitem) subObjeto)));
                    }
                }
            }
        }
        return resultados;
    }

    private List<Resultado> getResultadoLoopOrden(Treeitem itemOrden) {
        List<Resultado> resultados = new ArrayList<Resultado>();

        for (Object objeto : itemOrden.getChildren()) {
            if (objeto.getClass().getCanonicalName().equals(Treechildren.class
                    .getCanonicalName())) {
                Treechildren children = (Treechildren) objeto;

                for (Object subObjeto : children.getChildren()) {
                    if (subObjeto.getClass().getCanonicalName().equals(Treeitem.class
                            .getCanonicalName())) {
                        Treeitem item = (Treeitem) subObjeto;

                        if (item.getValue().getClass().getCanonicalName().equals(Resultado.class
                                .getCanonicalName())) {
                            Resultado resultado = (Resultado) item.getValue();
                            if (resultado.getEstado().equals("CO")) {
                                resultados.add(resultado);
                            } else {
                                if (resultado.getEstado().equals("AU")) {
                                    resultados.add(resultado);
                                }
                            }
                        }
                    }
                }
            }
        }
        return resultados;
    }
    // </editor-fold>

}

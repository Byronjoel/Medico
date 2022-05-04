//package ec.com.cubosoft.avasus.controller.renderder;
//
//import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
//import ec.com.cubosoft.avamed.modelo.ingreso.PracticaXOrden;
//import ec.com.cubosoft.avamed.modelo.medico.Nombre;
//import ec.com.cubosoft.avamed.modelo.persona.Historia;
//import ec.com.cubosoft.avamed.modelo.persona.ResultadoGrafico;
//import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
//import ec.com.cubosoft.avamed.modelo.practica.NombreP;
//import ec.com.cubosoft.avamed.modelo.vistas.Pedidos;
//import ec.com.cubosoft.avamed.modelo.vistas.Resultado;
//import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
//import ec.com.cubosoft.avamed.procesos.iReport;
//import grid.EventController;
//import grid.ResultadoGraficoVM;
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.math.BigInteger;
//import java.net.URL;
//import java.net.URLConnection;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import javax.naming.NamingException;
//import org.w3c.dom.Document;
//import org.zkoss.util.media.AMedia;
//import org.zkoss.util.media.Media;
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Executions;
//import org.zkoss.zk.ui.event.Event;
//import org.zkoss.zk.ui.event.EventListener;
//import org.zkoss.zk.ui.event.Events;
//import org.zkoss.zk.ui.event.UploadEvent;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zul.Button;
//import org.zkoss.zul.Fileupload;
//import org.zkoss.zul.Grid;
//import org.zkoss.zul.Iframe;
//import org.zkoss.zul.Image;
//import org.zkoss.zul.Label;
//import org.zkoss.zul.Listbox;
//import org.zkoss.zul.Listcell;
//import org.zkoss.zul.Listhead;
//import org.zkoss.zul.Listheader;
//import org.zkoss.zul.Listitem;
//import org.zkoss.zul.Menuitem;
//import org.zkoss.zul.Messagebox;
//import org.zkoss.zul.Popup;
//import org.zkoss.zul.Row;
//import org.zkoss.zul.RowRenderer;
//import org.zkoss.zul.Vlayout;
//import org.zkoss.zul.Window;
//
///**
// *
// * @author JP
// */
//
////auditoria ok
//public class OrdenIngresoRenderer_1 implements RowRenderer {
//
//    DateFormat fecha = new SimpleDateFormat("dd-MMM-yyyy");
//    DateFormat fechaHora = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
//    String usuario;
//    Window WinIngreso;
//
//    private Window WinHistoria;
//
//    boolean imprimir;
//    boolean delet;
//    boolean slab;
//    boolean carga;
//
//    int digitalizado = 0;
//    private Window _main;
//
//    private Grid grid;
//    private Menuitem ejemplo2;
//
//    @Wire
//    Popup mailPop;
//
//    List<ResultadoGrafico> listResultadoGrafico = new ArrayList<ResultadoGrafico>();
//
//    static final Map<String, String> ESTADO = new HashMap<String, String>() {
//
//        {
//            put("PE", "Pendiente");
//            put("CO", "Completo");
//            put("IN", "Incompleto");
//            put("AU", "Auditado");
//            put("IM", "Impreso");
//        }
//        private static final long serialVersionUID = 1L;
//    };
//
//    public OrdenIngresoRenderer(String usuarioe, Boolean impre, Boolean dell, Window imgre, Boolean sl, Boolean cargaAud) {
//        usuario = usuarioe;
//        imprimir = impre;
//        delet = dell;
//        WinIngreso = imgre;
//        slab = sl;
//        carga = cargaAud;
//
//    }
//
//    private OrdenIngresoRenderer() {
//        _main = new Window();
//
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    private boolean consultarRis(XmlResultado ris) {
//        try {
//            byte[] byteArray = null;
//            InputStream inputStream = null;
//            URLConnection conn = new URL(ris.getLinck()).openConnection();
//            inputStream = conn.getInputStream();
//            byte[] buffer = new byte[8192];
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            int bytesRead;
//            while ((bytesRead = inputStream.read(buffer)) != -1) {
//                baos.write(buffer, 0, bytesRead);
//            }
//            byteArray = baos.toByteArray();
//            ResultadoGrafico pdf = new ResultadoGrafico();
//            pdf.setCod("PDF");
//            pdf.setFirstUser(usuario);
//            pdf.setDato(byteArray);
//            pdf.setDescripcion("RIS");
//            pdf.setLockReg(new Short("0"));
//            pdf.setIdXmlResultado(ris.getId().longValue());
//            AdmiNegocio admi = new AdmiNegocio();
//            pdf = (ResultadoGrafico) admi.guardar(pdf);
//            System.out.println("pdf" + pdf);
//            if (pdf != null) {
//                Map<String, Object> wSQL = new HashMap<String, Object>();
//                wSQL.put("id ?=", ris.getId());
//                List objectList = admi.getData(new XmlResultado(), wSQL, null, null);
//                if (objectList.size() == 1) {
//                    XmlResultado resul = (XmlResultado) objectList.get(0);
//                    resul.setResultado("PDF");
//                    if (admi.actualizar(resul)) {
//                        return true;
//                    }
//                } else {
//                    Messagebox.show("Verificar estado Informe ext(722)", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//                    return false;
//                }
//            } else {
//                Messagebox.show("No se puede recuperar informe", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//                return false;
//            }
//        } //            String dirPDF = getUbicacion() + "Ris.pdf";
//        catch (Exception e) {
//            System.out.println("ec.com.cubosoft.avamed.procesos.iReport.consultarRis()");
//        }
//        return false;
//    }
//
//    @Override
//    public void render(Row row, Object data, int index) throws Exception {
//        if (!(slab)) {
//            final Orden orden = (Orden) data;
//            PracticaXOrden laboratorio;
//            row.setValue(orden);
//            row.setValign("top");
//
//            new Label(fecha.format(orden.getFecIngreso())).setParent(row);
//            //new Label(String.valueOf(orden.getId())).setParent(row);
//            final Label imprimirOrden = new Label(String.valueOf(orden.getId()));
//            imprimirOrden.setParent(row);
//            imprimirOrden.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
//                @Override
//                public void onEvent(Event t) throws Exception {
//                    //Messagebox.show("print");
//
//                    Window winMensaje = new Window();
//                    String windowMessage = "msg_informe.zul";
//                    Executions.createComponents(windowMessage, winMensaje, null);
//                    winMensaje.setBorder("normal");
//                    winMensaje.setClosable(true);
//                    winMensaje.setTitle("Vista Preliminar de Informe");
//                    final Iframe frameReporte = (Iframe) winMensaje.getFellow("reporteV", true);
//
//                    PracticaXOrden practica;
//                    Map<String, Object> wSQL = new HashMap<String, Object>();
//                    wSQL.put("idOrden", Long.parseLong(imprimirOrden.getValue()));
//                    //wSQL.put("idPractica", practica.getPractica().getId());
//                    AMedia mediaCarga = null;
//                    try {
//                        //mediaCarga = loadReportXml(wSQL, true, imprimir);
//                        mediaCarga = loadReportXmlAuditoria(wSQL, 7, true, imprimir);
//                        //mediaCarga = loadReportXmlAuditoria(wSQL, 8, true, imprimir);
//                    } catch (Exception e) {
//                        System.out.println("exc" + e.toString());
//                        Messagebox.show("No se han encontrado informes para registro seleccionado");
//                    }
//
//                    if (mediaCarga != null) {
//                        frameReporte.setContent(mediaCarga);
//                        Component parent = new Window();
//                        //Lectura del componente del Window Principal
//                        Collection<Component> comps = Executions.getCurrent().getDesktop().getComponents();
//                        for (Component c : comps) {
//                            if (c.getDefinition().getName().equals("window")) {
//                                parent = c;
//                            }
//                        }
//                        winMensaje.setParent(parent);
//                        winMensaje.doModal();
//                    } else {
//                        // Messagebox.show("No se han encontrado, verifique en la pantalla Informe/s");
//                        //Messagebox.show("El informe no se encuentra liberado");
//                        Messagebox.show("La visualizacion del informe no se encuentra disponible,todos");
//                    }
//
//                    //resultados.addAll(getResultadoLoopOrden(itemOrdenClicked));
//                    //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//                }
//            });
//
//            //final Label imprimirOrden = new Label(String.valueOf(orden.getId()));
//            final List<PracticaXOrden> practicasXOrden = orden.getPracticaXorden();
//            final List<XmlResultado> resultadoXorden = orden.getXmlResultados();
//            final List<ResultadoGrafico> resultadoGrafico = null;
//            final Button ob = new Button("ESTADO!!");
//            ob.setParent(row);
//            ob.addEventListener(Events.ON_CLICK, new EventListener() {
//                @Override
//                public void onEvent(Event event) throws Exception {
//                    Window winMensaje = new Window();
//                    String windowMessage;
//                    windowMessage = "msg_estados.zul";
//                    Executions.createComponents(windowMessage, winMensaje, null);
//                    final Label msg = new Label();
//                    msg.setParent(winMensaje);
//                    msg.setVisible(false);
//                    Button archivar = (Button) winMensaje.getFellow("idArchivar", false);
//                    archivar.addEventListener("onClick", new EventListener() {
//                        @Override
//                        public void onEvent(Event e) throws Exception {
//                            AdmiNegocio admi = new AdmiNegocio();
//                            for (Object practi : practicasXOrden) {
//                                PracticaXOrden pra = (PracticaXOrden) practi;
//                                if (pra.getStsTecnico().equalsIgnoreCase("CO")) {
//                                    pra.setStsTecnico("AR");
//                                    pra.setFecArch(new Date());
//                                    pra.setArchUser(usuario);
//                                    admi.actualizar(pra);
//                                }
//                            }
////                            for (Object resultados : resultadoXorden) {
////                                XmlResultado obgActu;
////                                obgActu = (XmlResultado) resultados;
////                                if (obgActu.getEstado().equalsIgnoreCase("CO") && obgActu.getResultado().equalsIgnoreCase("RIS") && !obgActu.getLinck().isEmpty() && !obgActu.getLkImg().isEmpty()) {
////                                    Messagebox.show("Visualizar informes de imagen antes de archivar!!");
////                                    System.out.println("no abrio ris para archivar");
////                                    break;
////                                } else {
////                                    System.out.println("SI abrio ris para archivar");
////                                    if (obgActu.getEstado().equalsIgnoreCase("CO")) {
////                                        obgActu.setEstado("AR");
////                                        obgActu.setFecArch(new Date());
////                                        obgActu.setArchUser(usuario);
////                                        admi.actualizar(obgActu);
////                                    }
////                                }
////                            }
//                            for (Object resultados : resultadoXorden) {
//                                XmlResultado obgActu;
//                                obgActu = (XmlResultado) resultados;
//                                boolean ris = false;
//                                if (obgActu.getEstado().equalsIgnoreCase("CO")) {
//                                    obgActu.setEstado("AR");
//                                    obgActu.setFecArch(new Date());
//                                    obgActu.setArchUser(usuario);
//                                    admi.actualizar(obgActu);
//                                    ris = consultarRis(obgActu);
//                                }
//
//                            }
//
//                            Window aux;
//                            aux = (Window) msg.getParent();
//                            aux.onClose();
//                        }
//                    }
//                    );
//
//                    Button auditar = (Button) winMensaje.getFellow("idAuditar", false);
//
//                    auditar.addEventListener(
//                            "onClick", new EventListener() {
//                        @Override
//                        public void onEvent(Event e) throws Exception {
//                            AdmiNegocio admi = new AdmiNegocio();
//                            for (Object practi : practicasXOrden) {
//                                PracticaXOrden pra = (PracticaXOrden) practi;
//                                if ((pra.getStsTecnico().equalsIgnoreCase("CO")) || (pra.getStsTecnico().equalsIgnoreCase("AR"))) {
//                                    pra.setStsTecnico("AU");
//                                    pra.setFechaAudit(new Date());
//                                    pra.setAuditUser(usuario);
//                                    admi.actualizar(pra);
//                                }
//                            }
////                                for (Object resul : resultadoXorden) {
////                                    XmlResultado obgActu = new XmlResultado();
////                                    obgActu = (XmlResultado) resul;
////                                    if (obgActu.getEstado().equalsIgnoreCase("CO") && obgActu.getResultado().equalsIgnoreCase("RIS") && !obgActu.getLinck().isEmpty() && !obgActu.getLkImg().isEmpty()) {
////                                        Messagebox.show("Visualizar informes de imagen antes de auditar!!");
////                                        System.out.println("no abrio ris para auditar");
////                                        break;
////                                    } else {
////                                        System.out.println("SI abrio ris para auditar");
////                                        if ((obgActu.getEstado().equalsIgnoreCase("CO")) || (obgActu.getEstado().equalsIgnoreCase("AR"))) {
////                                            obgActu.setEstado("AU");
////                                            obgActu.setFechaAudit(new Date());
////                                            obgActu.setAuditUser(usuario);
////                                            admi.actualizar(obgActu);
////                                        }
////                                    }
////                                }
//                            for (Object resultados : resultadoXorden) {
//                                XmlResultado obgActu;
//                                obgActu = (XmlResultado) resultados;
//                                boolean ris = false;
//                                if ((obgActu.getEstado().equalsIgnoreCase("CO")) || (obgActu.getEstado().equalsIgnoreCase("AR"))) {
//                                    obgActu.setEstado("AU");
//                                    obgActu.setFechaAudit(new Date());
//                                    obgActu.setAuditUser(usuario);
//                                    admi.actualizar(obgActu);
//                                    ris = consultarRis(obgActu);
//                                }
//
//                            }
//
//                            Window aux;
//                            aux = (Window) msg.getParent();
//                            aux.onClose();
//                        }
//                    }
//                    );
//                    winMensaje.setId(
//                            "winMsgEstado");
//                    winMensaje.setParent(WinIngreso);
//
//                    winMensaje.doModal();
//
//                    ob.setDisabled(
//                            true);
//                    ob.setLabel(
//                            "OK");
//                }
//            });
//            new Label(orden.getHistoria().getPaciente()).setParent(row);
//
//            if (orden.getOrganizacion()
//                    != null) {
//                new Label(orden.getOrganizacion().getAbreviatura()).setParent(row);
//            } else {
//                new Label("(Sin Empresa)").setParent(row);
//            }
//            Listbox peticiones = new Listbox();
//
//            int peticionesCompletas = 0;
//            int flag = 0;
//
//            if (!(practicasXOrden.isEmpty())) {
//                Listhead peticionesCab = new Listhead();
//                Listheader desCab = new Listheader();
//                Listheader imgCab = new Listheader();
//                imgCab.setAlign("center");
//                imgCab.setWidth("15%");
//                desCab.setParent(peticionesCab);
//                imgCab.setParent(peticionesCab);
//                peticionesCab.setParent(peticiones);
//                peticionesCab.setVisible(false);
//                Collections.sort(practicasXOrden, new Comparator() {
//                    @Override
//                    public int compare(Object o1, Object o2) {
//                        PracticaXOrden e1 = (PracticaXOrden) o1;
//                        PracticaXOrden e2 = (PracticaXOrden) o2;
//                        return e1.getPractica().getDescripcion().compareToIgnoreCase(e2.getPractica().getDescripcion());
//                    }
//                });
//
//                for (PracticaXOrden practica : practicasXOrden) {
//                    final Listitem peticion = new Listitem();
//                    peticion.setValue(practica);
//                    final Listcell desPractica = new Listcell(practica.getPractica() != null ? practica.getPractica().getDescripcion() : "", "");
//                    Listcell stsPractica = new Listcell();
//                    Image imageLock;
//                    Listcell audito = new Listcell();
//                    ///ER
//                    //Button button1 = new Button();
//                    final Button audiotoriaPdf = new Button();
//                    audiotoriaPdf.setDisabled(false);
//                    audiotoriaPdf.setParent(audito);
//                    audiotoriaPdf.setVisible(false);
//
//                    final Button cargarPdf = new Button();
//                    
//                    cargarPdf.setDisabled(false);
//                    cargarPdf.setParent(audito);
//                    cargarPdf.setVisible(false);
//
//                    final Button eliminarPdf = new Button();
//                    eliminarPdf.setDisabled(false);
//                    eliminarPdf.setParent(audito);
//                    eliminarPdf.setVisible(false);
//
//                    final Button audiotoria = new Button();
//                    audiotoria.setDisabled(true);
//                    audiotoria.setParent(audito);
//                    String pathImagen = null;
//
//                    final Button deletePdf = new Button();
//                    deletePdf.setDisabled(true);
//                    deletePdf.setParent(audito);
//
//                    final Button cambiarEstado = new Button();
//                    cambiarEstado.setDisabled(true);
//                    cambiarEstado.setParent(audito);
//                    cambiarEstado.setVisible(false);
//
//                    if (((practica.getStsTecnico().equals("IM"))||(practica.getStsTecnico().equals("VA"))) && ((practica.getPractica().getId() == 1518) || (practica.getPractica().getId() == 1521))) {
//                        cambiarEstado.setLabel(" CAMBIAR A PE");
//                        cambiarEstado.setVisible(true);
//                        cambiarEstado.setDisabled(false);
//
//                        cambiarEstado.addEventListener(Events.ON_CLICK,
//                                new EventListener() {
//                            @Override
//                            public void onEvent(Event event) throws Exception {
//                                Messagebox.show("Confirma que desea cambiar el estado?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//                                    public void onEvent(Event evt) throws InterruptedException, NamingException {
//                                        if (evt.getName().equals("onOK")) {
//                                            cambiarEstado.setDisabled(true);
//                                            cambiarEstado.setLabel("CAMBIADO");
//                                            cambiarEstado.setStyle("border: 2px #B22222 solid;");
//                                            PracticaXOrden actualizar = peticion.getValue();
//                                            actualizar.setStsTecnico("PE");
//                                            //arreglar
//                                            actualizar.setFecArch(new Date());
//                                            actualizar.setArchUser(usuario);
//                                            AdmiNegocio admi = new AdmiNegocio();
//                                            admi.actualizar(actualizar);
////                                            
//                                        } else if (evt.getName().equals("onCancel")) {
//                                        }
//                                    }
//                                }
//                                );
//                            }
//                        }
//                        );
//
//                    }
//
//                    if ((practica.getStsTecnico().equals("IN"))) {
//                        deletePdf.setVisible(false);
//                        pathImagen = "/images/editing.png";
//                        audiotoria.setVisible(false);
//
//                        // if ((carga == true) && (practica.getPractica().getId() != 415)) {
//                        if ((carga == true)) {
//                            cargarPdf.setLabel("+ PDF");
//                            cargarPdf.setVisible(true);
//                            cargarPdf.setDisabled(false);
//                            cargarPdf.setUpload("true");
//                        } else {
//                            cargarPdf.setLabel("+ PDF");
//                            cargarPdf.setVisible(false);
//                            cargarPdf.setDisabled(true);
//                            cargarPdf.setUpload("true");
//                        }
//
//                        cargarPdf.addEventListener(Events.ON_UPLOAD, new EventListener<UploadEvent>() {
//                            @Override
//                            public void onEvent(UploadEvent t) throws Exception {
//                                try {
//                                 
//                                    
//                                    Media media = t.getMedia();
//                                    //media.getReaderData();
//                                    // Media media = (Media) t.getMedia().getStreamData();
//                                    
//                                    //media.getStreamData();
//                                      if (media != null) {
//                                        
//                                        byte[] imagen = media.getByteData();
//                                        //byte[] imagen = media.get();
//                                        
//                                        AdmiNegocio admi = new AdmiNegocio();
//                                        PracticaXOrden actualizar = peticion.getValue();
//                                        if (practica.getStsTecnico().equals("IN")) {
//                                            XmlResultado idxmlRes = new XmlResultado();
//                                            Map<String, Object> wSQL = new HashMap<>();
//                                            wSQL = new HashMap<>();
//                                            wSQL.put("id ?=", actualizar.getPractica().getId());
//                                            admNegocio = new AdmiNegocio();
//                                            NombreP practica = (NombreP) (admNegocio.getData(new NombreP(), wSQL, null, null).get(0));
//                                            XmlResultado obj = new XmlResultado();
//                                            Map<String, Object> wSQL1 = new HashMap<>();
//                                            List oSQL = new ArrayList();
//                                            wSQL1.put("id ?=", 0);
//                                            admNegocio = new AdmiNegocio();
//                                            List data1 = admNegocio.getData(new Nombre(), wSQL1, null, oSQL);
//                                            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoria.modificarSession()");
//                                            if (data1.size() > 0) {
//                                                medico = (Nombre) data1.get(0);
//                                            }
//
//                                            obj.setFecha(new Date());
//                                            obj.setEstado("IN");
//                                            obj.setMedicos(medico);//llega null
//                                            obj.setHora(new Date());
//                                            obj.setPractica(practica);
//                                            obj.setResultado("PDF");
//                                            obj.setFirstUser(usuario);
//                                            wSQL = new HashMap<>();
//                                            wSQL.put("id ?=", actualizar.getPractica().getId());
//                                            PracticaXOrden pxo = (PracticaXOrden) (admNegocio.getDataObj(new PracticaXOrden(), wSQL, null, null));
//                                            //pxo.setStsTecnico("IM");
//                                            idxmlRes.getId();
//                                            ResultadoGrafico pdf = new ResultadoGrafico();
//                                            pdf.setCod("PDF");
//                                            pdf.setFirstUser(usuario);
//                                            pdf.setDato(imagen);
//                                            pdf.setDescripcion(media.getName());
//                                            pdf.setLockReg(new Short("0"));
//                                            pdf.setIdXmlResultado(idxmlRes.getId());
//                                            obj.setHistoria(new Historia(actualizar.getOrden().getHistoria().getId().longValue()));
//                                            obj.setOrden(new Orden(actualizar.getOrden().getId().longValue()));
//                                            System.out.println("setOrden" + obj.getOrden());
//                                            obj = (XmlResultado) admNegocio.guardar(obj);
//
//                                            pdf.setIdXmlResultado(obj.getId());
//                                            System.out.println("pdf" + pdf.toString());
//                                            pdf = (ResultadoGrafico) admNegocio.guardar(pdf);
//                                            if (pdf != null) {
//                                                //();
//                                                Messagebox.show("Archivo digitalizado !", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//
//                                            } else {
//                                                System.out.println("No se pudo digitalizar vuelva a intentar");
//                                            }
//
//                                        } else if (practica.getStsTecnico().equals("IN")) {
//                                            for (Object resul : resultadoXorden) {
//                                                XmlResultado obgActu = new XmlResultado();
//                                                obgActu = (XmlResultado) resul;
//                                                if (obgActu.getPractica().getId() == actualizar.getPractica().getId()) {
//                                                    if ((obgActu.getEstado().equalsIgnoreCase("IN"))) {
//                                                        obgActu.getId();
//                                                        //System.out.println("obj" + obgActu.getId());
//                                                        ResultadoGrafico pdf = new ResultadoGrafico();
//                                                        pdf.setCod("PDF");
//                                                        pdf.setFirstUser(usuario);
//                                                        pdf.setDato(imagen);
//                                                        pdf.setDescripcion(media.getName());
//                                                        pdf.setLockReg(new Short("0"));
//                                                        pdf.setIdXmlResultado(obgActu.getId());
//                                                        pdf = (ResultadoGrafico) admi.guardar(pdf);
//                                                        if (pdf != null) {
//                                                            //();
//                                                            Messagebox.show("Archivo digitalizado", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//
//                                                        } else {
//                                                            System.out.println("No se pudo digitalizar vuelva a intentar");
//                                                        }
//                                                    }
//                                                }
//
//                                            }
//                                            System.out.println("successfull" + imagen.toString());
//                                        } else {
//                                            System.out.println("No se encontró el archivo");
//                                        }
//                                    }
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    System.out.println("upload" + e.getMessage());
//                                    Messagebox.show("Upload failed");
//                                }
//
//                            }
//
//                        }
//                        );
//
//                        eliminarPdf.setVisible(false); //descativar momentaneamete
//                        //eliminarPdf.setVisible(false);
//                        eliminarPdf.setDisabled(false);
//
//                        eliminarPdf.addEventListener(Events.ON_CLICK, new EventListener() {
//                            @Override
//                            public void onEvent(Event t) throws Exception {
//
//                                //popup.open(mailPop);
//                                EventController obj = new EventController();
////                                obj.submit(t);
//                                PracticaXOrden actualizar = ((Listitem) audiotoria.getParent().getParent()).getValue();
//                                //actualizar.setStsTecnico("PE");
//                                actualizar.setFechaAudit(new Date());
//                                actualizar.setAuditUser(usuario);
//                                AdmiNegocio admi = new AdmiNegocio();
//                                admi.actualizar(actualizar);
//                                Long idORde = actualizar.getOrden().getId();
//                                Integer idPract = actualizar.getPractica().getId();
//                                for (Object resul : resultadoXorden) {
//                                    XmlResultado obgActu = new XmlResultado();
//                                    obgActu = (XmlResultado) resul;
//                                    Long idORdeRef = obgActu.getIdOrden().longValue();
//                                    Integer idPracRef = obgActu.getPractica().getId();
//                                    if (Objects.equals(idPracRef, idPract) && Objects.equals(idORde, idORdeRef)) {
//                                        Map<String, Object> wSQL = new HashMap<String, Object>();
//                                        wSQL.put("idXmlResultado ?=", obgActu.getId());
//                                        List objectList = admi.getData(new ResultadoGrafico(), wSQL, null, null);
//                                        System.out.println("objectList1" + objectList.toString());
//                                        ResultadoGraficoVM res = new ResultadoGraficoVM();
//                                        res.setListResultadoGrafico(objectList);
//                                        if (objectList.size() == 1) {
//                                            ResultadoGrafico obgActugr = (ResultadoGrafico) objectList.get(0);
//                                            //listResultadoGrafico.add(objectList.)
////                                            MailViewModel obj7 =  new MailViewModel();
////                                            obj7.getListResultadoGrafico();
////                                            for (Object object : objectList) {
////                                                //obj7.getListResultadoGrafico().add(0, obgActugr);
////                                                obj7.setListResultadoGrafico(objectList);
////                                               
////                                            }
//                                            //esto descomentar y se elimina
////                                            if (Objects.equals(obgActugr.getIdXmlResultado(), obgActu.getId())) {
////                                                System.out.println("Se elimina el registro " + obgActugr.getId() + " " + obgActugr.getIdXmlResultado());
////                                                admi.eliminar(obgActugr);
////                                            }
//                                        } else if (objectList.size() == 2) {
//                                            Messagebox.show("asd");
//                                            // obj.submit(t);
//                                        }
//                                    }
//                                }
//                            }
//                        });
//
//                        //if ((practica.getStsTecnico().equals("IN")) || (practica.getStsTecnico().equals("IM"))) {
//                        if ((practica.getStsTecnico().equals("IN"))) {
//                            //deletePdf.setVisible(false);
//                            audiotoria.setLabel("ELIMINAR PDF?");
//                            audiotoria.setVisible(true);
//                            if (delet) {
//                                audiotoria.setDisabled(false);
//                                audiotoria.setVisible(true);
//                            } else {
//                                audiotoria.setVisible(false);
//                            }
//
//                            audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
//                                @Override
//                                public void onEvent(Event event) throws Exception {
//                                    //Messagebox.show("Eliminar estado Incompleto");
//                                    Messagebox.show("Seguro desea borrar el pdf?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//                                        public void onEvent(Event evt) throws InterruptedException, NamingException {
//                                            if (evt.getName().equals("onOK")) {
//                                                audiotoria.setDisabled(true);
//                                                audiotoria.setLabel("ELIMINADO");
//                                                audiotoria.setStyle("border: 2px #B22222 solid;");
//                                                PracticaXOrden actualizar = ((Listitem) audiotoria.getParent().getParent()).getValue();
//                                                actualizar.setStsTecnico("IN");
//                                                actualizar.setFechaAudit(new Date());
//                                                actualizar.setAuditUser(usuario);
//                                                AdmiNegocio admi = new AdmiNegocio();
//                                                admi.actualizar(actualizar);
//                                                Long idORde = actualizar.getOrden().getId();
//                                                Integer idPract = actualizar.getPractica().getId();
//                                                for (Object resul : resultadoXorden) {
//                                                    XmlResultado obgActu = new XmlResultado();
//                                                    obgActu = (XmlResultado) resul;
//                                                    Long idORdeRef = obgActu.getIdOrden().longValue();
//                                                    Integer idPracRef = obgActu.getPractica().getId();
//                                                    String XmlresultadoaEliminar = obgActu.getResultado();
//                                                    System.out.println("eliminar" + XmlresultadoaEliminar);
//                                                    if (Objects.equals(idPracRef, idPract) && Objects.equals(idORde, idORdeRef)) {
//                                                        Map<String, Object> wSQL = new HashMap<String, Object>();
//                                                        wSQL.put("idXmlResultado ?=", obgActu.getId());
//                                                        List objectList = admi.getData(new ResultadoGrafico(), wSQL, null, null);
//                                                        if (objectList.size() == 1) {
//                                                            ResultadoGrafico obgActugr = (ResultadoGrafico) objectList.get(0);
//                                                            if (Objects.equals(obgActugr.getIdXmlResultado(), obgActu.getId())) {
//                                                                System.out.println("Se elimina el registro " + obgActugr.getId() + " " + obgActugr.getIdXmlResultado());
//                                                                admi.eliminar(obgActugr);
//                                                            }
//                                                        } else {
//                                                            //Messagebox.show("Existen");
//                                                            System.out.println("llamar sistemas");
//                                                        }
//                                                        // admi.eliminar(obgActu); ELIMINA EL XML
//
//                                                    }
//                                                }
//
//                                            } else if (evt.getName().equals("onCancel")) {
//                                            }
//                                        }
//                                    }
//                                    );
//
//                                }
//
//                            });
//                            peticionesCompletas++;
//
//                        }
//
//                        if (practica.getStsTecnico()
//                                .equals("IM")) {
//                            pathImagen = "/images/pdf.png";
//                            audiotoria.setLabel("ELIMINAR?");
//                            audiotoria.setVisible(true);
////                            if (delet) {
////                                audiotoria.setDisabled(false);
////                                //audiotoria.setVisible(false);
////                            }
//                            if (delet) {
//                                audiotoria.setDisabled(false);
//                                audiotoria.setVisible(true);
//                            } else {
//                                audiotoria.setVisible(false);
//                            }
//
//                            audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
//                                @Override
//                                public void onEvent(Event event) throws Exception {
//                                    Messagebox.show("Seguro desea borrar el registro?", "Confirmación", Messagebox.OK
//                                            | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//                                        public void onEvent(Event evt) throws InterruptedException, NamingException {
//                                            if (evt.getName().equals("onOK")) {
//                                                audiotoria.setDisabled(true);
//                                                audiotoria.setLabel("ELIMINADO");
//                                                audiotoria.setStyle("border: 2px #B22222 solid;");
//                                                PracticaXOrden actualizar = ((Listitem) audiotoria.getParent().getParent()).getValue();
//                                                actualizar.setStsTecnico("PE");
//                                                actualizar.setFechaAudit(new Date());
//                                                actualizar.setAuditUser(usuario);
//                                                AdmiNegocio admi = new AdmiNegocio();
//                                                admi.actualizar(actualizar);
//                                                Long idORde = actualizar.getOrden().getId();
//                                                Integer idPract = actualizar.getPractica().getId();
//                                                for (Object resul : resultadoXorden) {
//                                                    XmlResultado obgActu = new XmlResultado();
//                                                    obgActu = (XmlResultado) resul;
//                                                    Long idORdeRef = obgActu.getIdOrden().longValue();
//                                                    Integer idPracRef = obgActu.getPractica().getId();
//                                                    if (Objects.equals(idPracRef, idPract) && Objects.equals(idORde, idORdeRef)) {
//                                                        Map<String, Object> wSQL = new HashMap<String, Object>();
//                                                        wSQL.put("idXmlResultado ?=", obgActu.getId());
//                                                        List objectList = admi.getData(new ResultadoGrafico(), wSQL, null, null);
//                                                        if (objectList.size() == 1) {
//                                                            ResultadoGrafico obgActugr = (ResultadoGrafico) objectList.get(0);
//                                                            if (Objects.equals(obgActugr.getIdXmlResultado(), obgActu.getId())) {
//                                                                System.out.println("Se elimina el registro " + obgActugr.getId() + " " + obgActugr.getIdXmlResultado());
//                                                                admi.eliminar(obgActugr);
//                                                            }
//                                                        }
//                                                        admi.eliminar(obgActu);
//                                                    }
//                                                }
//
//                                            } else if (evt.getName().equals("onCancel")) {
//                                            }
//                                        }
//                                    }
//                                    );
//
//                                }
//
//                            });
//                            peticionesCompletas++;
//
//                        } else if (practica.getStsTecnico()
//                                .equals("CO")) {
//                            pathImagen = "/images/lock.png";
//                            audiotoria.setLabel("ELIMINAR?");
//                            audiotoria.setVisible(true);
////                            if (delet) {
////                                audiotoria.setDisabled(false);
////                                //audiotoria.setVisible(false);
////                            }
//
//                            if (delet) {
//                                audiotoria.setDisabled(false);
//                                audiotoria.setVisible(true);
//                            } else {
//                                audiotoria.setVisible(false);
//                            }
//                            audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
//                                @Override
//                                public void onEvent(Event event) throws Exception {
//                                    Messagebox.show("Seguro desea borrar el registro?", "Confirmación", Messagebox.OK
//                                            | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//                                        public void onEvent(Event evt) throws InterruptedException, NamingException {
//                                            if (evt.getName().equals("onOK")) {
//                                                audiotoria.setDisabled(true);
//                                                audiotoria.setLabel("ELIMINADO");
//                                                audiotoria.setStyle("border: 2px #B22222 solid;");
//                                                PracticaXOrden actualizar = ((Listitem) audiotoria.getParent().getParent()).getValue();
//                                                actualizar.setStsTecnico("PE");
//                                                actualizar.setFechaAudit(new Date());
//                                                actualizar.setAuditUser(usuario);
//                                                AdmiNegocio admi = new AdmiNegocio();
//                                                admi.actualizar(actualizar);
//                                                Long idORde = actualizar.getOrden().getId();
//                                                Integer idPract = actualizar.getPractica().getId();
//                                                for (Object resul : resultadoXorden) {
//                                                    XmlResultado obgActu = new XmlResultado();
//                                                    obgActu = (XmlResultado) resul;
//                                                    Long idORdeRef = obgActu.getIdOrden().longValue();
//                                                    Integer idPracRef = obgActu.getPractica().getId();
//                                                    if (Objects.equals(idPracRef, idPract) && Objects.equals(idORde, idORdeRef)) {
//                                                        Map<String, Object> wSQL = new HashMap<String, Object>();
//                                                        wSQL.put("idXmlResultado ?=", obgActu.getId());
//                                                        List objectList = admi.getData(new ResultadoGrafico(), wSQL, null, null);
//                                                        if (objectList.size() == 1) {
//                                                            ResultadoGrafico obgActugr = (ResultadoGrafico) objectList.get(0);
//                                                            if (Objects.equals(obgActugr.getIdXmlResultado(), obgActu.getId())) {
//                                                                System.out.println("Se elimina el registro " + obgActugr.getId() + " " + obgActugr.getIdXmlResultado());
//                                                                admi.eliminar(obgActugr);
//                                                            }
//                                                        }
//                                                        admi.eliminar(obgActu);
//                                                    }
//                                                }
//                                            } else if (evt.getName().equals("onCancel")) {
//                                            }
//                                        }
//                                    }
//                                    );
//                                }
//
//                            });
//                            peticionesCompletas++;
//                        }
//
//                    } else if ((practica.getStsTecnico().equals("CO"))) {
//                        pathImagen = "/images/lock.png";
//                        audiotoria.setLabel("ARCHIVAR?");
//                        audiotoria.setVisible(true);
//                        audiotoria.setDisabled(false);
//
//                        if (practica.getIdArea() != null) {
//                            if ((practica.getIdArea() == 23
//                                    || practica.getIdArea() == 25
//                                    || practica.getIdArea() == 35
//                                    || practica.getIdArea() == 33
//                                    || practica.getIdArea() == 28
//                                    || practica.getIdArea() == 29)) {
//                                audiotoriaPdf.setLabel("ACTUALIZA PDF");
//                                audiotoriaPdf.setVisible(true);
//                                audiotoriaPdf.setDisabled(false);
//                            } else {
//
//                                audiotoriaPdf.setVisible(false);
//                                audiotoriaPdf.setDisabled(true);
//
//                            }
//
//                        } else {
//                            System.out.println("pra" + practica.getIdArea());
//                        }
//
//                        audiotoria.addEventListener(Events.ON_CLICK,
//                                new EventListener() {
//                            @Override
//                            public void onEvent(Event event) throws Exception {
//                                Messagebox.show("Confirma que el informe fue archivado?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//                                    public void onEvent(Event evt) throws InterruptedException, NamingException {
//                                        if (evt.getName().equals("onOK")) {
//                                            audiotoria.setDisabled(true);
//                                            audiotoria.setLabel("ARCHIVADO");
//                                            audiotoria.setStyle("border: 2px #B22222 solid;");
//                                            PracticaXOrden actualizar = peticion.getValue();
//                                            actualizar.setStsTecnico("AR");
//                                            //arreglar
//                                            actualizar.setFecArch(new Date());
//                                            actualizar.setArchUser(usuario);
//                                            AdmiNegocio admi = new AdmiNegocio();
//                                            admi.actualizar(actualizar);
//                                            for (Object resul : resultadoXorden) {
//                                                XmlResultado obgActu = new XmlResultado();
//                                                obgActu = (XmlResultado) resul;
//                                                if (obgActu.getPractica().getId() == actualizar.getPractica().getId()) {
//                                                    if ((obgActu.getEstado().equalsIgnoreCase("CO"))) {
//                                                        obgActu.setEstado("AR");
//                                                        obgActu.setFecArch(new Date());
//                                                        obgActu.setArchUser(usuario);
//                                                        admi.actualizar(obgActu);
//                                                    }
//                                                } else {
//                                                }
//                                            }
//                                        } else if (evt.getName().equals("onCancel")) {
//                                        }
//                                    }
//                                }
//                                );
//                            }
//                        }
//                        );
//                        peticionesCompletas++;
//                        audiotoriaPdf.addEventListener(Events.ON_CLICK, new EventListener() {
//                            @Override
//                            public void onEvent(Event t) throws Exception {
//                                Messagebox.show("ACTUALIZAR PDF");
//                                AdmiNegocio admi = new AdmiNegocio();
//                                PracticaXOrden actualizar = peticion.getValue();
//                                for (Object resul : resultadoXorden) {
//                                    XmlResultado obgActu = new XmlResultado();
//                                    obgActu = (XmlResultado) resul;
//                                    if (obgActu.getPractica().getId() == actualizar.getPractica().getId()) {
//                                        if ((obgActu.getEstado().equalsIgnoreCase("CO")) && obgActu.getMedico().equals("RIS")) {
//                                            obgActu.getId();
//                                            //Messagebox.show("link" +  obgActu.getId());
//                                            String url = "";
//                                            url = obgActu.getLinck();
//                                            URL u = new URL(url);
//                                            ByteArrayOutputStream output = new ByteArrayOutputStream();
//                                            try (InputStream inputStream = u.openStream()) {
//                                                int n = 0;
//                                                byte[] buffer = new byte[1024];
//                                                while (-1 != (n = inputStream.read(buffer))) {
//                                                    output.write(buffer, 0, n);
//                                                }
//                                            }
//                                            // System.out.println("" + output.toByteArray());
//                                            byte[] fileArray = output.toByteArray();
//                                            //HASTA AQUI SE OBTIENEN LOS BYTES CAMBIADOS
//                                            //ResultadoGrafico objResultadoGrafico = new ResultadoGrafico();
//                                            Map<String, Object> wSQL = new HashMap<>();
//                                            wSQL = new HashMap<>();
//                                            wSQL.put("id ?=", obgActu.getId());
//                                            List listaresultado = new ArrayList();
//                                            listaresultado = admi.getDataR(obgActu.getId(), fileArray);
//
//                                            System.out.println("listaresul ok" + listaresultado.toString());
//
//                                            Messagebox.show("PDF ACTUALIZADO ");
//                                        } else {
//                                            System.out.println("no se pudo recuperar el link");
//                                        }
//                                    }
//                                }
//
//                            }
//                        });
//
//                        if (carga == true) { //cuando esta cero esta habilitado
//                            cargarPdf.setLabel("+ PDF");
//                            cargarPdf.setVisible(true);
//                            cargarPdf.setDisabled(false);
//                            cargarPdf.setUpload("true");
//                        } else {
//                            cargarPdf.setLabel("+ PDF");
//                            cargarPdf.setVisible(false);
//                            cargarPdf.setDisabled(true);
//                            cargarPdf.setUpload("true");
//                        }
//
////                        cargarPdf.setLabel("+ PDF");
////                        cargarPdf.setVisible(true);
////                        cargarPdf.setDisabled(false);
////                        cargarPdf.setUpload("true");
//                        cargarPdf.addEventListener(Events.ON_UPLOAD, new EventListener<UploadEvent>() {
//                            @Override
//                            public void onEvent(UploadEvent t) throws Exception {
//                                try {
//                                    Media media = t.getMedia();
//                                    if (media != null) {
//                                        byte[] imagen = media.getByteData();
//                                        AdmiNegocio admi = new AdmiNegocio();
//                                        PracticaXOrden actualizar = peticion.getValue();
//                                        if (practica.getStsTecnico().equals("PE")) {
//                                            XmlResultado idxmlRes = new XmlResultado();
//                                            Map<String, Object> wSQL = new HashMap<>();
//                                            wSQL = new HashMap<>();
//                                            wSQL.put("id ?=", actualizar.getPractica().getId());
//                                            admNegocio = new AdmiNegocio();
//                                            NombreP practica = (NombreP) (admNegocio.getData(new NombreP(), wSQL, null, null).get(0));
//                                            XmlResultado obj = new XmlResultado();
//                                            Map<String, Object> wSQL1 = new HashMap<>();
//                                            List oSQL = new ArrayList();
//                                            wSQL1.put("id ?=", 0);
//                                            admNegocio = new AdmiNegocio();
//                                            List data1 = admNegocio.getData(new Nombre(), wSQL1, null, oSQL);
//                                            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoria.modificarSession()");
//                                            if (data1.size() > 0) {
//                                                medico = (Nombre) data1.get(0);
//                                            }
//                                            obj.setFecha(new Date());
//                                            //obj.setEstado("IM");
//                                            obj.setMedicos(medico);//llega null
//                                            obj.setHora(new Date());
//                                            obj.setPractica(practica);
//                                            obj.setResultado("PDF");
//                                            obj.setFirstUser(usuario);
//                                            wSQL = new HashMap<>();
//                                            wSQL.put("id ?=", actualizar.getPractica().getId());
//                                            PracticaXOrden pxo = (PracticaXOrden) (admNegocio.getDataObj(new PracticaXOrden(), wSQL, null, null));
//                                            //idxmlRes.getId();                                           
//                                            ResultadoGrafico pdf = new ResultadoGrafico();
//                                            pdf.setCod("PDF");
//                                            pdf.setFirstUser(usuario);
//                                            pdf.setDato(imagen);
//                                            pdf.setDescripcion(media.getName());
//                                            pdf.setLockReg(new Short("0"));
//                                            pdf.setIdXmlResultado(idxmlRes.getId());
//                                            obj.setHistoria(new Historia(actualizar.getOrden().getHistoria().getId().longValue()));
//                                            obj.setOrden(new Orden(actualizar.getOrden().getId().longValue()));
//                                            System.out.println("setOrden" + obj.getOrden());
//                                            obj = (XmlResultado) admNegocio.guardar(obj);
//                                            //admNegocio.actualizar(pxo);
//                                            pdf.setIdXmlResultado(obj.getId());
//                                            pdf = (ResultadoGrafico) admNegocio.guardar(pdf);
//                                            if (pdf != null) {
//                                                //();
//                                                Messagebox.show("Archivo digitalizado !", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//                                            } else {
//                                                System.out.println("No se pudo digitalizar vuelva a intentar");
//                                            }
////                                        } else if ((practica.getStsTecnico().equals("IM"))
////                                                || (practica.getStsTecnico().equals("CO"))) {
//                                        } else if ((practica.getStsTecnico().equals("CO"))) {
//
//                                            for (Object resul : resultadoXorden) {
//                                                XmlResultado obgActu = new XmlResultado();
//                                                obgActu = (XmlResultado) resul;
//                                                if (obgActu.getPractica().getId() == actualizar.getPractica().getId()) {
//                                                    if ((practica.getStsTecnico().equals("CO"))) {
//                                                        obgActu.getId();
//                                                        System.out.println("obj" + obgActu.getId());
//                                                        ResultadoGrafico pdf = new ResultadoGrafico();
//                                                        pdf.setCod("PDF");
//                                                        pdf.setFirstUser(usuario);
//                                                        pdf.setDato(imagen);
//                                                        pdf.setDescripcion(media.getName());
//                                                        pdf.setLockReg(new Short("0"));
//                                                        pdf.setIdXmlResultado(obgActu.getId());
//                                                        pdf = (ResultadoGrafico) admi.guardar(pdf);
//                                                        if (pdf != null) {
//                                                            //   reset();
//                                                            Messagebox.show("Archivo digitalizado", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//                                                        } else {
//                                                            System.out.println("No se pudo digitalizar vuelva a intentar");
//                                                        }
//                                                    }
//                                                }
//
//                                            }
//                                            System.out.println("successfull" + imagen.toString());
//                                        } else {
//                                            System.out.println("No se encontró el archivo");
//                                        }
//                                    }
//
//                                } catch (Exception e) {
//                                    e.printStackTrace();
//                                    System.out.println("upload" + e.getMessage());
//                                    Messagebox.show("Upload failed");
//                                }
//                            }
//                        }
//                        );
//
//                        //eliminar pdf
//                        deletePdf.setLabel("ELIMINAR PDF");
//                        //audiotoria.setLabel("ELIMINAR PDF?");
//                        deletePdf.setVisible(true);
//                        if (delet) {
//                            deletePdf.setDisabled(false);
//                            deletePdf.setVisible(true);
//                            //audiotoria.setVisible(false);
//                        } else {
//                            deletePdf.setVisible(false);
//                        }
//
//                        deletePdf.addEventListener(Events.ON_CLICK, new EventListener() {
//                            @Override
//                            public void onEvent(Event event) throws Exception {
//                                // Messagebox.show("Eliminar estado Incompleto");
//                                Messagebox.show("Seguro desea borrar el pdf?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//                                    public void onEvent(Event evt) throws InterruptedException, NamingException {
//                                        if (evt.getName().equals("onOK")) {
//                                            deletePdf.setDisabled(true);
//                                            deletePdf.setLabel("ELIMINADO");
//                                            deletePdf.setStyle("border: 2px #B22222 solid;");
//                                            PracticaXOrden actualizar = ((Listitem) deletePdf.getParent().getParent()).getValue();
//                                            actualizar.setStsTecnico("CO");
//                                            actualizar.setFechaAudit(new Date());
//                                            actualizar.setAuditUser(usuario);
//                                            AdmiNegocio admi = new AdmiNegocio();
//                                            admi.actualizar(actualizar);
//                                            Long idORde = actualizar.getOrden().getId();
//                                            Integer idPract = actualizar.getPractica().getId();
//                                            for (Object resul : resultadoXorden) {
//                                                XmlResultado obgActu = new XmlResultado();
//                                                obgActu = (XmlResultado) resul;
//                                                Long idORdeRef = obgActu.getIdOrden().longValue();
//                                                Integer idPracRef = obgActu.getPractica().getId();
//                                                String XmlresultadoaEliminar = obgActu.getResultado();
//                                                System.out.println("eliminar" + XmlresultadoaEliminar);
//
//                                                if (Objects.equals(idPracRef, idPract) && Objects.equals(idORde, idORdeRef)) {
//                                                    Map<String, Object> wSQL = new HashMap<String, Object>();
//                                                    wSQL.put("idXmlResultado ?=", obgActu.getId());
//                                                    List objectList = admi.getData(new ResultadoGrafico(), wSQL, null, null);
//                                                    if (objectList.size() == 1) {
//
//                                                        ResultadoGrafico obgActugr = (ResultadoGrafico) objectList.get(0);
//                                                        if (Objects.equals(obgActugr.getIdXmlResultado(), obgActu.getId())) {
//                                                            System.out.println("Se elimina el registro " + obgActugr.getId() + " " + obgActugr.getIdXmlResultado());
//                                                            admi.eliminar(obgActugr);
//                                                        }
//                                                    } else {
//                                                        //Messagebox.show("Existen");
//                                                        for (int i = 0; i < objectList.size(); i++) {
//                                                            ResultadoGrafico obgActugr = (ResultadoGrafico) objectList.get(0);
//                                                            if (obgActugr.getCod().equals("PDF")) {
//                                                                admi.eliminar(obgActugr);
//                                                            } else {
//                                                                System.out.println("puede venir del RIS no se debe eliminar");
//                                                            }
//
//                                                        }
//                                                        System.out.println("llamar sistemas");
//                                                    }
//                                                    // admi.eliminar(obgActu); ELIMINA EL XML
//
//                                                }
//                                            }
//
//                                        } else if (evt.getName().equals("onCancel")) {
//                                        }
//                                    }
//                                }
//                                );
//                            }
//                        });
//                        // peticionesCompletas++;
//                    } else {
//                        //if ((practica.getStsTecnico().equals("PE")) || (practica.getStsTecnico().equals("CM"))  ) {
//                        if ((practica.getStsTecnico().equals("PE")) || (practica.getStsTecnico().equals("CM")) || (practica.getStsTecnico().equals("VA"))) {
//                            pathImagen = "/images/document_blank.png";
//                            deletePdf.setVisible(false);
//                            audiotoria.setVisible(false);
//
//                            //System.out.println(""+  practica.getIdNombrep().floatValue());
//                            //if ((carga == true) && (practica.getPractica().getId() != 415)) {
//                            // if ((carga == true) && (practica.getIdNombrep().toString().equals("2446"))) {
//                            if ((carga == true)) {
//                                cargarPdf.setLabel("+ PDF");
//                                cargarPdf.setVisible(true);
//                                cargarPdf.setDisabled(false);
//                                cargarPdf.setUpload("true");
//                            } else {
//                                cargarPdf.setLabel("+ PDF");
//                                cargarPdf.setVisible(false);
//                                cargarPdf.setDisabled(true);
//                                cargarPdf.setUpload("true");
//                            }
//
//                            cargarPdf.addEventListener(Events.ON_UPLOAD, new EventListener<UploadEvent>() {
//                                @Override
//                                public void onEvent(UploadEvent t) throws Exception {
//                                    try {
//                                        Media media = t.getMedia();
//                                        if (media != null) {
//                                            byte[] imagen = media.getByteData();
//                                            AdmiNegocio admi = new AdmiNegocio();
//                                            PracticaXOrden actualizar = peticion.getValue();
//                                            //if (practica.getStsTecnico().equals("PE")) {
//                                            //if ((practica.getStsTecnico().equals("PE")) || (practica.getStsTecnico().equals("CM")){
//                                            if ((practica.getStsTecnico().equals("PE")) || (practica.getStsTecnico().equals("CM")) || (practica.getStsTecnico().equals("VA"))) {
//                                                XmlResultado idxmlRes = new XmlResultado();
//                                                Map<String, Object> wSQL = new HashMap<>();
//                                                wSQL = new HashMap<>();
//                                                wSQL.put("id ?=", actualizar.getPractica().getId());
//                                                admNegocio = new AdmiNegocio();
//                                                NombreP practica = (NombreP) (admNegocio.getData(new NombreP(), wSQL, null, null).get(0));
//                                                XmlResultado obj = new XmlResultado();
//                                                Map<String, Object> wSQL1 = new HashMap<>();
//                                                List oSQL = new ArrayList();
//                                                wSQL1.put("id ?=", 0);
//                                                admNegocio = new AdmiNegocio();
//                                                List data1 = admNegocio.getData(new Nombre(), wSQL1, null, oSQL);//DATA1 XMLRESULTADO
//                                                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoria.modificarSession()");
//                                                System.out.println("data 1" + data1.toString());
//                                                if (data1.size() > 0) {
//                                                    medico = (Nombre) data1.get(0);
//                                                }
//
//                                                obj.setFecha(new Date());
//                                                obj.setEstado("IM");
//                                                //obj.setEstado("IN");
//                                                obj.setMedicos(medico);//llega null
//                                                obj.setHora(new Date());
//                                                obj.setPractica(practica);
//                                                obj.setResultado("PDF");
//                                                obj.setFirstUser(usuario);
//                                                wSQL = new HashMap<>();
//                                                wSQL.put("id ?=", actualizar.getPractica().getId());
//                                                PracticaXOrden pxo = (PracticaXOrden) (admNegocio.getDataObj(new PracticaXOrden(), wSQL, null, null));
//                                                //pxo.setStsTecnico("IM");
//                                                idxmlRes.getId();
//                                                ResultadoGrafico pdf = new ResultadoGrafico();
//                                                pdf.setCod("PDF");
//                                                pdf.setFirstUser(usuario);
//                                                pdf.setDato(imagen);
//                                                pdf.setDescripcion(media.getName());
//                                                pdf.setLockReg(new Short("0"));
//                                                pdf.setIdXmlResultado(idxmlRes.getId());
//                                                obj.setHistoria(new Historia(actualizar.getOrden().getHistoria().getId().longValue()));
//                                                obj.setOrden(new Orden(actualizar.getOrden().getId().longValue()));
//                                                System.out.println("setOrden" + obj.getOrden());
//                                                if (data1.isEmpty()) {
//
//                                                }
//                                                obj = (XmlResultado) admNegocio.guardar(obj);
//
//                                                pdf.setIdXmlResultado(obj.getId());
//                                                System.out.println("pdf" + pdf.toString());
//                                                pdf = (ResultadoGrafico) admNegocio.guardar(pdf);
//                                                if (pdf != null) {
//                                                    //();
//                                                    Messagebox.show("Archivo digitalizado !", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//
//                                                } else {
//                                                    System.out.println("No se pudo digitalizar vuelva a intentar");
//                                                }
//
//                                            } else if (practica.getStsTecnico().equals("IN")) {
//                                                for (Object resul : resultadoXorden) {
//                                                    XmlResultado obgActu = new XmlResultado();
//                                                    obgActu = (XmlResultado) resul;
//                                                    if (obgActu.getPractica().getId() == actualizar.getPractica().getId()) {
//                                                        if ((obgActu.getEstado().equalsIgnoreCase("IN"))) {
//                                                            obgActu.getId();
//                                                            System.out.println("obj" + obgActu.getId());
//                                                            ResultadoGrafico pdf = new ResultadoGrafico();
//
//                                                            pdf.setCod("PDF");
//                                                            pdf.setFirstUser(usuario);
//                                                            pdf.setDato(imagen);
//                                                            pdf.setDescripcion(media.getName());
//                                                            pdf.setLockReg(new Short("0"));
//                                                            pdf.setIdXmlResultado(obgActu.getId());
//                                                            pdf = (ResultadoGrafico) admi.guardar(pdf);
//                                                            if (pdf != null) {
//                                                                //();
//                                                                Messagebox.show("Archivo digitalizado", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//
//                                                            } else {
//                                                                System.out.println("No se pudo digitalizar vuelva a intentar");
//                                                            }
//                                                        }
//                                                    }
//
//                                                }
//                                                System.out.println("successfull" + imagen.toString());
//                                            } else {
//                                                System.out.println("No se encontró el archivo");
//                                            }
//                                        }
//
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                        System.out.println("upload" + e.getMessage());
//                                        Messagebox.show("Upload failed");
//                                    }
//
//                                }
//
//                            }
//                            );
//
//                            //CUANDO ES PE
//                            //eliminarPdf.setVisible(true); //descativar momentaneamete
//                            //eliminarPdf.setVisible(false);
//                            //eliminarPdf.setDisabled(false);
//                            audiotoria.setLabel("ELIMINAR?");
//                            //audiotoria.setVisible(true);
//                            //si delet true se activa el boton
//                            if (delet) {
//                                audiotoria.setDisabled(false);
//                                audiotoria.setVisible(true);
//                            } else {
//                                audiotoria.setVisible(false);
//                            }
//
//                            audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
//                                @Override
//                                public void onEvent(Event event) throws Exception {
//                                    Messagebox.show("Seguro desea borrar el registro?", "Confirmación", Messagebox.OK
//                                            | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//                                        public void onEvent(Event evt) throws InterruptedException, NamingException {
//                                            if (evt.getName().equals("onOK")) {
//                                                audiotoria.setDisabled(true);
//                                                audiotoria.setLabel("ELIMINADO");
//                                                audiotoria.setStyle("border: 2px #B22222 solid;");
//                                                PracticaXOrden actualizar = ((Listitem) audiotoria.getParent().getParent()).getValue();
//                                                actualizar.setStsTecnico("PE");
//                                                actualizar.setFechaAudit(new Date());
//                                                actualizar.setAuditUser(usuario);
//                                                AdmiNegocio admi = new AdmiNegocio();
//                                                admi.actualizar(actualizar);
//                                                Long idORde = actualizar.getOrden().getId();
//                                                Integer idPract = actualizar.getPractica().getId();
//                                                for (Object resul : resultadoXorden) {
//                                                    XmlResultado obgActu = new XmlResultado();
//                                                    obgActu = (XmlResultado) resul;
//                                                    Long idORdeRef = obgActu.getIdOrden().longValue();
//                                                    Integer idPracRef = obgActu.getPractica().getId();
//
//                                                    String XmlresultadoaEliminar = obgActu.getResultado();
//                                                    if (XmlresultadoaEliminar.equals("PDF")) {
//                                                        System.out.println("resul" + obgActu.getId());
//                                                        System.out.println("codigo para eliminar");
//
//                                                        if (Objects.equals(idPracRef, idPract) && Objects.equals(idORde, idORdeRef)) {
//                                                            Map<String, Object> wSQL = new HashMap<String, Object>();
//                                                            wSQL.put("idXmlResultado ?=", obgActu.getId());
//                                                            List objectList = admi.getData(new ResultadoGrafico(), wSQL, null, null);
//                                                            if (objectList.size() == 1) {
//                                                                ResultadoGrafico obgActugr = (ResultadoGrafico) objectList.get(0);
//                                                                if (Objects.equals(obgActugr.getIdXmlResultado(), obgActu.getId())) {
//                                                                    System.out.println("Se elimina el registro " + obgActugr.getId() + " " + obgActugr.getIdXmlResultado());
//                                                                    admi.eliminar(obgActugr);
//                                                                }
//                                                            }
//                                                            admi.eliminar(obgActu);
//                                                        }
//
//                                                    } else {
//                                                        System.out.println("no pasa nada");
//                                                    }
//
//                                                }
//
//                                            } else if (evt.getName().equals("onCancel")) {
//                                            }
//                                        }
//                                    }
//                                    );
//
//                                }
//
//                            });
//                            peticionesCompletas++;
//
//                        } else {
//                            if (practica.getStsTecnico().equals("AU")) {
//                                pathImagen = "/images/ok.png";
//                                audiotoria.setLabel("AUDITADO");
//                                audiotoria.setStyle("border: 2px #006400 solid;");
//                                deletePdf.setVisible(false);
//                            } else {
//                                if (practica.getStsTecnico().equals("AR")) {
//                                    deletePdf.setVisible(false);
//                                    pathImagen = "/images/archivado.png";
//                                    audiotoria.setLabel("AUDITAR?");
//                                    audiotoria.setVisible(true);
//                                    audiotoria.setDisabled(false);
//                                    audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
//                                        @Override
//                                        public void onEvent(Event event) throws Exception {
//                                            Messagebox.show("Confirma que el informe fue auditado?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//                                                public void onEvent(Event evt) throws InterruptedException, NamingException {
//                                                    if (evt.getName().equals("onOK")) {
//                                                        audiotoria.setDisabled(true);
//                                                        audiotoria.setLabel("AUDITADO");
//                                                        audiotoria.setStyle("border: 2px #B22222 solid;");
//                                                        PracticaXOrden actualizar = peticion.getValue();
//                                                        actualizar.setStsTecnico("AU");
//                                                        actualizar.setFechaAudit(new Date());
//                                                        actualizar.setAuditUser(usuario);
//                                                        AdmiNegocio admi = new AdmiNegocio();
//                                                        admi.actualizar(actualizar);
//                                                        for (Object resul : resultadoXorden) {
//                                                            XmlResultado obgActu = new XmlResultado();
//                                                            obgActu = (XmlResultado) resul;
//                                                            if (obgActu.getPractica().getId() == actualizar.getPractica().getId()) {
//                                                                if ((obgActu.getEstado().equalsIgnoreCase("CO")) || (obgActu.getEstado().equalsIgnoreCase("AR"))) {
//                                                                    obgActu.setEstado("AU");
//                                                                    obgActu.setFecArch(new Date());
//                                                                    obgActu.setArchUser(usuario);
//                                                                    admi.actualizar(obgActu);
//                                                                }
//                                                            }
//                                                        }
//                                                    } else if (evt.getName().equals("onCancel")) {
////                                        Messagebox.show("Ignore Save", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
//                                                    }
//                                                }
//                                            });
//                                        }
//                                    });
//                                    peticionesCompletas++;
//
////                                    cargarPdf.setLabel("+ PDF");
////                                    cargarPdf.setVisible(true);
////                                    cargarPdf.setDisabled(false);
////                                    cargarPdf.setUpload("true");
//                                    if (carga == true) {
//                                        cargarPdf.setLabel("+ PDF");
//                                        cargarPdf.setVisible(true);
//                                        cargarPdf.setDisabled(false);
//                                        cargarPdf.setUpload("true");
//                                    } else {
//                                        cargarPdf.setLabel("+ PDF");
//                                        cargarPdf.setVisible(false);
//                                        cargarPdf.setDisabled(true);
//                                        cargarPdf.setUpload("true");
//                                    }
//
//                                    
//                                    //cargarPdf.addEventListener(Events.ON_UPLOAD, new EventListener<UploadEvent>() {
//                                    cargarPdf.addEventListener(Events.ON_UPLOAD, new EventListener<UploadEvent>() {
//                                      
//                                    @Override
//                                        public void onEvent(UploadEvent t) throws Exception {
//                                            try {
//                                                Media media = t.getMedia();
//                                                if (media != null) {
//                                                    byte[] imagen = media.getByteData();
//                                                    AdmiNegocio admi = new AdmiNegocio();
//                                                    PracticaXOrden actualizar = peticion.getValue();
//                                                    if (practica.getStsTecnico().equals("PE")) {
//                                                        XmlResultado idxmlRes = new XmlResultado();
//                                                        Map<String, Object> wSQL = new HashMap<>();
//                                                        wSQL = new HashMap<>();
//                                                        wSQL.put("id ?=", actualizar.getPractica().getId());
//                                                        admNegocio = new AdmiNegocio();
//                                                        NombreP practica = (NombreP) (admNegocio.getData(new NombreP(), wSQL, null, null).get(0));
//                                                        XmlResultado obj = new XmlResultado();
//                                                        Map<String, Object> wSQL1 = new HashMap<>();
//                                                        List oSQL = new ArrayList();
//                                                        wSQL1.put("id ?=", 0);
//                                                        admNegocio = new AdmiNegocio();
//                                                        List data1 = admNegocio.getData(new Nombre(), wSQL1, null, oSQL);
//                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoria.modificarSession()");
//                                                        if (data1.size() > 0) {
//                                                            medico = (Nombre) data1.get(0);
//                                                        }
//                                                        obj.setFecha(new Date());
//                                                        obj.setEstado("IM");
//                                                        obj.setMedicos(medico);//llega null
//                                                        obj.setHora(new Date());
//                                                        obj.setPractica(practica);
//                                                        obj.setResultado("PDF");
//                                                        obj.setFirstUser(usuario);
//                                                        wSQL = new HashMap<>();
//                                                        wSQL.put("id ?=", actualizar.getPractica().getId());
//                                                        PracticaXOrden pxo = (PracticaXOrden) (admNegocio.getDataObj(new PracticaXOrden(), wSQL, null, null));
//                                                        //idxmlRes.getId();                                           
//                                                        ResultadoGrafico pdf = new ResultadoGrafico();
//                                                        pdf.setCod("PDF");
//                                                        pdf.setFirstUser(usuario);
//                                                        pdf.setDato(imagen);
//                                                        pdf.setDescripcion(media.getName());
//                                                        pdf.setLockReg(new Short("0"));
//                                                        pdf.setIdXmlResultado(idxmlRes.getId());
//                                                        obj.setHistoria(new Historia(actualizar.getOrden().getHistoria().getId().longValue()));
//
//                                                        obj.setOrden(new Orden(actualizar.getOrden().getId().longValue()));
//                                                        System.out.println("setOrden" + obj.getOrden());
//                                                        obj = (XmlResultado) admNegocio.guardar(obj);
//                                                        //admNegocio.actualizar(pxo);
//                                                        pdf.setIdXmlResultado(obj.getId());
//                                                        pdf = (ResultadoGrafico) admNegocio.guardar(pdf);
//                                                        if (pdf != null) {
//                                                            //();
//
//                                                            Messagebox.show("Archivo digitalizado !", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//                                                        } else {
//                                                            System.out.println("No se pudo digitalizar vuelva a intentar");
//                                                        }
//
//                                                    } else if ((practica.getStsTecnico().equals("IM"))
//                                                            || (practica.getStsTecnico().equals("CO"))
//                                                            || (practica.getStsTecnico().equals("AR"))) {
//
//                                                        for (Object resul : resultadoXorden) {
//                                                            XmlResultado obgActu = new XmlResultado();
//                                                            obgActu = (XmlResultado) resul;
//                                                            if (obgActu.getPractica().getId() == actualizar.getPractica().getId()) {
//                                                                if ((obgActu.getEstado().equalsIgnoreCase("IN"))
//                                                                        || (practica.getStsTecnico().equals("PE"))
//                                                                        || (practica.getStsTecnico().equals("CO"))
//                                                                        || (practica.getStsTecnico().equals("AR"))
//                                                                        || (practica.getStsTecnico().equals("IM"))) {
//
//                                                                    obgActu.getId();
//                                                                    System.out.println("obj" + obgActu.getId());
//                                                                    ResultadoGrafico pdf = new ResultadoGrafico();
//                                                                    pdf.setCod("DIG");
//                                                                    pdf.setFirstUser(usuario);
//                                                                    pdf.setDato(imagen);
//                                                                    pdf.setDescripcion(media.getName());
//                                                                    pdf.setLockReg(new Short("0"));
//                                                                    pdf.setIdXmlResultado(obgActu.getId());
//                                                                    pdf = (ResultadoGrafico) admi.guardar(pdf);
//                                                                    if (pdf != null) {
//                                                                        //();
//                                                                        Messagebox.show("Archivo digitalizado", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//                                                                    } else {
//                                                                        System.out.println("No se pudo digitalizar vuelva a intentar");
//                                                                    }
//                                                                }
//                                                            }
//
//                                                        }
//                                                        System.out.println("successfull" + imagen.toString());
//                                                    } else {
//                                                        System.out.println("No se encontró el archivo");
//                                                    }
//                                                }
//
//                                            } catch (Exception e) {
//                                                e.printStackTrace();
//                                                System.out.println("upload" + e.getMessage());
//                                                Messagebox.show("Upload failed");
//                                            }
//                                        }
//                                    }
//                                    );
//
//                                } else {
//                                    if (practica.getStsTecnico().equals("IM")) {
//                                        pathImagen = "/images/pdf.png";
//                                        audiotoria.setLabel("ELIMINAR?");
//                                        audiotoria.setVisible(true);
//                                        if (delet) {
//                                            audiotoria.setDisabled(false);
//                                            //audiotoria.setVisible(false);
//                                        }
//                                        audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
//                                            @Override
//                                            public void onEvent(Event event) throws Exception {
//                                                Messagebox.show("Seguro desea borrar el registro?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//                                                    public void onEvent(Event evt) throws InterruptedException, NamingException {
//                                                        if (evt.getName().equals("onOK")) {
//                                                            audiotoria.setDisabled(true);
//                                                            audiotoria.setLabel("ELIMINADO");
//                                                            audiotoria.setStyle("border: 2px #B22222 solid;");
//                                                            PracticaXOrden actualizar = ((Listitem) audiotoria.getParent().getParent()).getValue();
//                                                            actualizar.setStsTecnico("PE");
//                                                            actualizar.setFechaAudit(new Date());
//                                                            actualizar.setAuditUser(usuario);
//                                                            AdmiNegocio admi = new AdmiNegocio();
//                                                            admi.actualizar(actualizar);
//                                                            Long idORde = actualizar.getOrden().getId();
//                                                            Integer idPract = actualizar.getPractica().getId();
//                                                            for (Object resul : resultadoXorden) {
//                                                                XmlResultado obgActu = new XmlResultado();
//                                                                obgActu = (XmlResultado) resul;
//                                                                Long idORdeRef = obgActu.getIdOrden().longValue();
//                                                                Integer idPracRef = obgActu.getPractica().getId();
//                                                                if (Objects.equals(idPracRef, idPract) && Objects.equals(idORde, idORdeRef)) {
//                                                                    Map<String, Object> wSQL = new HashMap<String, Object>();
//                                                                    wSQL.put("idXmlResultado ?=", obgActu.getId());
//                                                                    List objectList = admi.getData(new ResultadoGrafico(), wSQL, null, null);
//                                                                    if (objectList.size() == 1) {
//                                                                        ResultadoGrafico obgActugr = (ResultadoGrafico) objectList.get(0);
//                                                                        if (Objects.equals(obgActugr.getIdXmlResultado(), obgActu.getId())) {
//                                                                            System.out.println("Se elimina el registro " + obgActugr.getId() + " " + obgActugr.getIdXmlResultado());
//                                                                            admi.eliminar(obgActugr);
//                                                                        }
//                                                                    }
//                                                                    admi.eliminar(obgActu);
//                                                                }
//                                                            }
//
//                                                        } else if (evt.getName().equals("onCancel")) {
//                                                        }
//                                                    }
//                                                }
//                                                );
//
//                                            }
//
//                                        });
//
//                                        if (carga == true) {
//                                            cargarPdf.setLabel("+ PDF");
//                                            cargarPdf.setVisible(true);
//                                            cargarPdf.setDisabled(false);
//                                            cargarPdf.setUpload("true");
//                                        } else {
//                                            cargarPdf.setLabel("+ PDF");
//                                            cargarPdf.setVisible(false);
//                                            cargarPdf.setDisabled(true);
//                                            cargarPdf.setUpload("true");
//                                        }
//
//                                        cargarPdf.addEventListener(Events.ON_UPLOAD, new EventListener<UploadEvent>() {
//                                            @Override
//                                            public void onEvent(UploadEvent t) throws Exception {
//                                                try {
//                                                    Media media = t.getMedia();
//                                                    if (media != null) {
//                                                        byte[] imagen = media.getByteData();
//                                                        AdmiNegocio admi = new AdmiNegocio();
//                                                        PracticaXOrden actualizar = peticion.getValue();
//                                                        //if (practica.getStsTecnico().equals("PE")) {
//                                                        if ((practica.getStsTecnico().equals("PE")) || (practica.getStsTecnico().equals("CM"))) {
//                                                            XmlResultado idxmlRes = new XmlResultado();
//                                                            Map<String, Object> wSQL = new HashMap<>();
//                                                            wSQL = new HashMap<>();
//                                                            wSQL.put("id ?=", actualizar.getPractica().getId());
//                                                            admNegocio = new AdmiNegocio();
//                                                            NombreP practica = (NombreP) (admNegocio.getData(new NombreP(), wSQL, null, null).get(0));
//                                                            XmlResultado obj = new XmlResultado();
//                                                            Map<String, Object> wSQL1 = new HashMap<>();
//                                                            List oSQL = new ArrayList();
//                                                            wSQL1.put("id ?=", 0);
//                                                            admNegocio = new AdmiNegocio();
//                                                            List data1 = admNegocio.getData(new Nombre(), wSQL1, null, oSQL);//DATA1 XMLRESULTADO
//                                                            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoria.modificarSession()");
//                                                            System.out.println("data 1" + data1.toString());
//                                                            if (data1.size() > 0) {
//                                                                medico = (Nombre) data1.get(0);
//                                                            }
//
//                                                            obj.setFecha(new Date());
//                                                            obj.setEstado("IM");
//                                                            //obj.setEstado("IN");
//                                                            obj.setMedicos(medico);//llega null
//                                                            obj.setHora(new Date());
//                                                            obj.setPractica(practica);
//                                                            obj.setResultado("PDF");
//                                                            obj.setFirstUser(usuario);
//                                                            wSQL = new HashMap<>();
//                                                            wSQL.put("id ?=", actualizar.getPractica().getId());
//                                                            PracticaXOrden pxo = (PracticaXOrden) (admNegocio.getDataObj(new PracticaXOrden(), wSQL, null, null));
//                                                            //pxo.setStsTecnico("IM");
//                                                            idxmlRes.getId();
//                                                            ResultadoGrafico pdf = new ResultadoGrafico();
//                                                            pdf.setCod("PDF");
//                                                            pdf.setFirstUser(usuario);
//                                                            pdf.setDato(imagen);
//                                                            pdf.setDescripcion(media.getName());
//                                                            pdf.setLockReg(new Short("0"));
//                                                            pdf.setIdXmlResultado(idxmlRes.getId());
//                                                            obj.setHistoria(new Historia(actualizar.getOrden().getHistoria().getId().longValue()));
//                                                            obj.setOrden(new Orden(actualizar.getOrden().getId().longValue()));
//                                                            System.out.println("setOrden" + obj.getOrden());
//                                                            if (data1.isEmpty()) {
//
//                                                            }
//                                                            obj = (XmlResultado) admNegocio.guardar(obj);
//
//                                                            pdf.setIdXmlResultado(obj.getId());
//                                                            System.out.println("pdf" + pdf.toString());
//                                                            pdf = (ResultadoGrafico) admNegocio.guardar(pdf);
//                                                            if (pdf != null) {
//                                                                //();
//                                                                Messagebox.show("Archivo digitalizado !", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//
//                                                            } else {
//                                                                System.out.println("No se pudo digitalizar vuelva a intentar");
//                                                            }
//
//                                                        } else if (practica.getStsTecnico().equals("IN")) {
//                                                            for (Object resul : resultadoXorden) {
//                                                                XmlResultado obgActu = new XmlResultado();
//                                                                obgActu = (XmlResultado) resul;
//                                                                if (obgActu.getPractica().getId() == actualizar.getPractica().getId()) {
//                                                                    if ((obgActu.getEstado().equalsIgnoreCase("IN"))) {
//                                                                        obgActu.getId();
//                                                                        System.out.println("obj" + obgActu.getId());
//                                                                        ResultadoGrafico pdf = new ResultadoGrafico();
//
//                                                                        pdf.setCod("PDF");
//                                                                        pdf.setFirstUser(usuario);
//                                                                        pdf.setDato(imagen);
//                                                                        pdf.setDescripcion(media.getName());
//                                                                        pdf.setLockReg(new Short("0"));
//                                                                        pdf.setIdXmlResultado(obgActu.getId());
//                                                                        pdf = (ResultadoGrafico) admi.guardar(pdf);
//                                                                        if (pdf != null) {
//                                                                            //();
//                                                                            Messagebox.show("Archivo digitalizado", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//
//                                                                        } else {
//                                                                            System.out.println("No se pudo digitalizar vuelva a intentar");
//                                                                        }
//                                                                    }
//                                                                }
//
//                                                            }
//                                                            System.out.println("successfull" + imagen.toString());
//                                                        } else {
//                                                            System.out.println("No se encontró el archivo");
//                                                        }
//                                                    }
//
//                                                } catch (Exception e) {
//                                                    e.printStackTrace();
//                                                    System.out.println("upload" + e.getMessage());
//                                                    Messagebox.show("Upload failed");
//                                                }
//
//                                            }
//
//                                        }
//                                        );
//
//                                        peticionesCompletas++;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    imageLock = new Image(pathImagen);
//
//                    imageLock.setWidth(
//                            "20px");
//                    imageLock.setParent(stsPractica);
//                    //Celdas Finales para Tooltip Peticion
//                    final Listcell firstUserP = new Listcell(practica.getFirstUser());
//                    final Listcell fecIniP = new Listcell(fechaHora.format(practica.getFecIni()));
//                    final Listcell fecUpdP = new Listcell();
//
//                    if (practica.getFecUpd()
//                            != null) {
//                        fecUpdP.setLabel(fechaHora.format(practica.getFecUpd()));
//                    }
//
//                    desPractica.addEventListener(Events.ON_RIGHT_CLICK,
//                            new EventListener() {
//
//                        @Override
//                        public void onEvent(Event event) throws Exception {
//                            //ToolTip
//                            StringBuilder toolTip = new StringBuilder();
//                            if (!firstUserP.getLabel().isEmpty()) {
//                                toolTip.append("Creado por: ");
//                                toolTip.append(new AdmiNegocio().getUsuario(firstUserP.getLabel()).getNomUsu());
//                                toolTip.append(" / Fecha Creación: ").append(fecIniP.getLabel());
//                            }
//
//                            if (!fecUpdP.getLabel().isEmpty()) {
//                                toolTip.append(" / Modificada: ").append(fecUpdP.getLabel());
//                            }
//                            desPractica.setTooltiptext(toolTip.toString());
////              Fin ToolTip
//                        }
//                    }
//                    );
//
//                    desPractica.addEventListener(Events.ON_DOUBLE_CLICK,
//                            new EventListener() {
////IMPRIMIR PRACTICA
//                        @Override
//                        public void onEvent(Event event) throws Exception {
//                            Window winMensaje = new Window();
//                            String windowMessage = "msg_informe.zul";
//                            Executions.createComponents(windowMessage, winMensaje, null);
//                            winMensaje.setBorder("normal");
//                            winMensaje.setClosable(true);
//                            winMensaje.setTitle("Vista Preliminar de Informe");
//                            final Iframe frameReporte = (Iframe) winMensaje.getFellow("reporteV", true);
//
//                            PracticaXOrden practica;
//
//                            practica = ((Listitem) desPractica.getParent()).getValue();
//
//                            Map<String, Object> wSQL = new HashMap<String, Object>();
//                            wSQL.put("idOrden", practica.getOrden().getId());
//                            wSQL.put("idPractica", practica.getPractica().getId());
//                            AMedia mediaCarga = null;
//                            try {
//                                mediaCarga = loadReportXml(wSQL, true, imprimir);
//                                //iReport a = new iReport();
//
////                                if (practica.getPractica().getId().equals(415)) {                                    
////                                    desPractica.setImage("/images/lock.png");
////                                    Messagebox.show("415");
////                                }
//                            } catch (Exception e) {
//                                System.out.println("exc" + e.toString());
//                                Messagebox.show("No se han encontrado informes para registro seleccionado");
//                            }
//
//                            if (mediaCarga != null) {
//                                frameReporte.setContent(mediaCarga);
//                                Component parent = new Window();
//                                //Lectura del componente del Window Principal
//                                Collection<Component> comps = Executions.getCurrent().getDesktop().getComponents();
//                                for (Component c : comps) {
//                                    if (c.getDefinition().getName().equals("window")) {
//                                        parent = c;
//                                    }
//                                }
//                                winMensaje.setParent(parent);
//                                winMensaje.doModal();
//                                if (practica.getPractica().getId().equals(415)) {
//                                    stsPractica.setImage("/images/lock.png");
//                                }
//
//                            } else {
//                                // Messagebox.show("No se han encontrado, verifique en la pantalla Informe/s");
//                                //Messagebox.show("El informe no se encuentra liberado");
//                                //desPractica.setImage("/images/lock.png");
//
//                                Messagebox.show("El informe no se encuentra disponible");
//                            }
//                        }
//                    }
//                    );
//
//                    peticion.appendChild(desPractica);
//
//                    peticion.appendChild(stsPractica);
//
//                    peticion.appendChild(audito);
//
//                    peticion.setParent(peticiones);
//                }
//                if ((imprimir) && ((orden.getOrganizacion()
//                        .getAbreviatura().equalsIgnoreCase("ECUAAMERICAN")))) {
//                    row.setVisible(false);
//                }
//
//                peticiones.setParent(row);
//
//            } else {
//                new Label("(No hay peticiones)").setParent(row);
//            }
//
//            //
//            if (orden.getStsTecnico()
//                    .equals("CO")) {       //Si la orden esta completa
//                Image imageEstadoTecnico;
//                imageEstadoTecnico = new Image("/images/status/orden-completed.png");
//                imageEstadoTecnico.setWidth("64px");
//                imageEstadoTecnico.setParent(row);
//
//            } else {        //Si la orden no esta completa
//                Vlayout estadoGroup = new Vlayout();
////            Progressmeter barraEstado = new Progressmeter();
//                Label estadoOs = new Label(ESTADO.get(orden.getStsTecnico()));
//                estadoOs.setParent(estadoGroup);
//                estadoGroup.setParent(row);
//            }
//            final Label usuario = new Label(orden.getFirstUser());
//            final Listcell firstUser = new Listcell(orden.getFirstUser());
//            final Listcell fecIni = new Listcell(fechaHora.format(orden.getFecIni()));
//            final Listcell fecUpd = new Listcell();
//
//            if (orden.getFecUpd()
//                    != null) {
//                fecUpd.setLabel(fechaHora.format(orden.getFecUpd()));
//            }
//
//            usuario.addEventListener(Events.ON_CLICK,
//                    new EventListener() {
//
//                @Override
//                public void onEvent(Event event) throws Exception {
//                    //ToolTip
//                    StringBuilder toolTip = new StringBuilder();
//                    if (!firstUser.getLabel().isEmpty()) {
//                        toolTip.append("Creado por: ");
//                        toolTip.append(new AdmiNegocio().getUsuario(firstUser.getLabel()).getNomUsu());
//                        toolTip.append(" / Fecha Creación: ").append(fecIni.getLabel());
//                    }
//
//                    if (!fecUpd.getLabel().isEmpty()) {
//                        toolTip.append(" / Modificada: ").append(fecUpd.getLabel());
//                    }
//                    usuario.setTooltiptext(toolTip.toString());
////              Fin ToolTip
//                }
//            }
//            );
//
//            usuario.setParent(row);
//            //Fin de usuario
//
//            new Label(String.valueOf(orden.getOrigen().getDescripcion())).setParent(row);
//        } else {
//            //desde aqui solo lab
//            System.out.println("Solo laboratorio");
//            XmlResultado ped = (XmlResultado) data;
//            row.setValue(ped);
//            row.setValign("top");
//            new Label(fecha.format(ped.getFecha())).setParent(row);
//            new Label(String.valueOf(ped.getId())).setParent(row);
//            final Button ob = new Button("ESTADO!!");
//            ob.setParent(row);
//            ob.setDisabled(true);
//            new Label(ped.getHistoria().getPaciente()).setParent(row);
//
//            if (ped.getEmpresa() != null) {
//                new Label(ped.getEmpresa()).setParent(row);
//            } else {
//                new Label("(Sin Empresa)").setParent(row);
//            }
//            Listbox peticiones = new Listbox();
//            if ((ped != null)) {
//
//                Listhead peticionesCab = new Listhead();
//                Listheader desCab = new Listheader();
//                Listheader imgCab = new Listheader();
//
//                imgCab.setAlign("center");
//                imgCab.setWidth("15%");
//
//                desCab.setParent(peticionesCab);
//                imgCab.setParent(peticionesCab);
//
//                peticionesCab.setParent(peticiones);
//                peticionesCab.setVisible(false);
//
//                final Listitem peticion = new Listitem();
//                peticion.setValue(ped);
//                final Listcell desPractica = new Listcell(ped.getPractica() != null ? ped.getPractica().getDescripcion() : "", "");
//                Listcell stsPractica = new Listcell();
//                Image imageLock;
//                Listcell audito = new Listcell();
//                final Button audiotoria = new Button();
//                audiotoria.setDisabled(true);
//                audiotoria.setParent(audito);
//                String pathImagen = null;
////                    NombreP  practica=ped.getPractica();
//                if (ped.getEstado().equals("IN")) {
//                    pathImagen = "/images/editing.png";
//                    audiotoria.setVisible(false);
//                } else if (ped.getEstado().equals("CO")) {
//                    pathImagen = "/images/lock.png";
//                    audiotoria.setLabel("ARCHIVAR?");
//                    audiotoria.setVisible(true);
//                    audiotoria.setDisabled(false);
//                    audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
//                        @Override
//                        public void onEvent(Event event) throws Exception {
//                            Messagebox.show("Confirma que el informe fue archivado?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//                                public void onEvent(Event evt) throws InterruptedException, NamingException {
//                                    if (evt.getName().equals("onOK")) {
//                                        audiotoria.setDisabled(true);
//                                        audiotoria.setLabel("ARCHIVADO");
//                                        audiotoria.setStyle("border: 2px #B22222 solid;");
//                                        XmlResultado actualizar = peticion.getValue();
//                                        actualizar.setEstado("AR");
//                                        actualizar.setFecArch(new Date());
//                                        actualizar.setArchUser(usuario);
//                                        AdmiNegocio admi = new AdmiNegocio();
//                                        admi.actualizar(actualizar);
//                                    } else if (evt.getName().equals("onCancel")) {
////                                        Messagebox.show("Ignore Save", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
//                                    }
//                                }
//                            });
//                        }
//                    });
////                    peticionesCompletas++;
//                } else {
//                    if (ped.getEstado().equals("PE")) {
//                        pathImagen = "/images/document_blank.png";
//                        audiotoria.setVisible(false);
//
//                    } else {
//                        if (ped.getEstado().equals("AU")) {
//                            pathImagen = "/images/ok.png";
//                            audiotoria.setLabel("AUDITADO");
//                            audiotoria.setStyle("border: 2px #006400 solid;");
//                        } else {
//                            if (ped.getEstado().equals("AR")) {
//                                pathImagen = "/images/archivado.png";
//                                audiotoria.setLabel("AUDITAR?");
//                                audiotoria.setVisible(true);
//                                audiotoria.setDisabled(false);
//                                audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
//                                    @Override
//                                    public void onEvent(Event event) throws Exception {
//                                        Messagebox.show("Confirma que el informe fue auditado?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//                                            public void onEvent(Event evt) throws InterruptedException, NamingException {
//                                                if (evt.getName().equals("onOK")) {
//                                                    audiotoria.setDisabled(true);
//                                                    audiotoria.setLabel("AUDITADO");
//                                                    audiotoria.setStyle("border: 2px #B22222 solid;");
//                                                    XmlResultado actualizar = peticion.getValue();
//                                                    actualizar.setEstado("AU");
//                                                    actualizar.setFechaAudit(new Date());
//                                                    actualizar.setAuditUser(usuario);
//                                                    AdmiNegocio admi = new AdmiNegocio();
//                                                    admi.actualizar(actualizar);
//                                                } else if (evt.getName().equals("onCancel")) {
////                                        Messagebox.show("Ignore Save", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
//                                                }
//                                            }
//                                        });
//                                    }
//                                });
////                                peticionesCompletas++;
//                            }
//                        }
//                    }
//                }
//
//                imageLock = new Image(pathImagen);
//                imageLock.setWidth("20px");
//                imageLock.setParent(stsPractica);
//                //Celdas Finales para Tooltip Peticion
//                final Listcell firstUserP = new Listcell(ped.getFirstUser());
//                final Listcell fecIniP = new Listcell(fechaHora.format(ped.getFecIni()));
//                final Listcell fecUpdP = new Listcell();
//                if (ped.getFecUpd() != null) {
//                    fecUpdP.setLabel(fechaHora.format(ped.getFecUpd()));
//                }
//
//                desPractica.addEventListener(Events.ON_RIGHT_CLICK, new EventListener() {
//
//                    @Override
//                    public void onEvent(Event event) throws Exception {
//                        //ToolTip
//                        StringBuilder toolTip = new StringBuilder();
//                        if (!firstUserP.getLabel().isEmpty()) {
//                            toolTip.append("Creado por: ");
//                            toolTip.append(new AdmiNegocio().getUsuario(firstUserP.getLabel()).getNomUsu());
//                            toolTip.append(" / Fecha Creación: ").append(fecIniP.getLabel());
//                        }
//
//                        if (!fecUpdP.getLabel().isEmpty()) {
//                            toolTip.append(" / Modificada: ").append(fecUpdP.getLabel());
//                        }
//                        desPractica.setTooltiptext(toolTip.toString());
////              Fin ToolTip
//                    }
//                });
////IMPRIMIR PRACTICA
////                desPractica.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
////
////                    @Override
////                    public void onEvent(Event event) throws Exception {
////                        Window winMensaje = new Window();
////                        String windowMessage = "msg_informe.zul";
////                        Executions.createComponents(windowMessage, winMensaje, null);
////                        winMensaje.setBorder("normal");
////                        winMensaje.setClosable(true);
////                        winMensaje.setTitle("Vista Preliminar de Informe");
////                        final Iframe frameReporte = (Iframe) winMensaje.getFellow("reporteV", true);
////                        XmlResultado practica;
////                        practica = ((Listitem) desPractica.getParent()).getValue();
////                        Map<String, Object> wSQL = new HashMap<String, Object>();
////                        wSQL.put("orden.id", practica.getOrden().getId());
////                        wSQL.put("practica.id", practica.getPractica().getId());
////                        //wSQL.put("idOrden", practica.getOrden().getId());
////                        //wSQL.put("idPractica", practica.getPractica().getId());
////                        AMedia mediaCarga = null;
////                        try {
////                            mediaCarga = loadReportXml(wSQL, true, imprimir);
////                        } catch (Exception e) {
////                            System.out.println("e"+e);
////                            Messagebox.show("No se han encontrado informes para registro seleccionado");
////                        }
////                        if (mediaCarga != null) {
////                            frameReporte.setContent(mediaCarga);
////                            Component parent = new Window();
////                            //Lectura del componente del Window Principal
////                            Collection<Component> comps = Executions.getCurrent().getDesktop().getComponents();
////                            for (Component c : comps) {
////                                if (c.getDefinition().getName().equals("window")) {
////                                    parent = c;
////                                }
////                            }
////                            winMensaje.setParent(parent);
////                            winMensaje.doModal();
////                        } else {
////                            Messagebox.show("No se han encontrado informes para registro seleccionado");
////                        }
////                    }
////                });
////                peticion.appendChild(desPractica);
////                peticion.appendChild(stsPractica);
////                peticion.appendChild(audito);
////                peticion.setParent(peticiones);
////                peticiones.setParent(row);
//
//            } else {
//                new Label("(No hay peticiones)").setParent(row);
//            }
//            //
//            if (ped.getEstado().equals("CO")) {       //Si la orden esta completa
//                Image imageEstadoTecnico;
//                imageEstadoTecnico = new Image("/images/status/orden-completed.png");
//                imageEstadoTecnico.setWidth("64px");
//                imageEstadoTecnico.setParent(row);
//
//            } else {        //Si la orden no esta completa
//                Vlayout estadoGroup = new Vlayout();
////            Progressmeter barraEstado = new Progressmeter();
//                Label estadoOs = new Label(ESTADO.get(ped.getEstado()));
//                estadoOs.setParent(estadoGroup);
//                estadoGroup.setParent(row);
//            }
//
//            //new Label(ESTADO.get(orden.getStsTecnico())).setParent(row);
//            //Usuario
//            final Label usuario = new Label(ped.getFirstUser());
////        usuario.setTooltiptext(new AdmiNegocio().getUsuario(orden.getFirstUser()).getNomUsu());
//
//            //Celdas Finales para Tooltip Orden
//            final Listcell firstUser = new Listcell(ped.getFirstUser());
//            final Listcell fecIni = new Listcell(fechaHora.format(ped.getFecIni()));
//            final Listcell fecUpd = new Listcell();
//            if (ped.getFecUpd() != null) {
//                fecUpd.setLabel(fechaHora.format(ped.getFecUpd()));
//            }
//
//            usuario.addEventListener(Events.ON_CLICK, new EventListener() {
//
//                @Override
//                public void onEvent(Event event) throws Exception {
//                    //ToolTip
//                    StringBuilder toolTip = new StringBuilder();
//                    if (!firstUser.getLabel().isEmpty()) {
//                        toolTip.append("Creado por: ");
//                        toolTip.append(new AdmiNegocio().getUsuario(firstUser.getLabel()).getNomUsu());
//                        toolTip.append(" / Fecha Creación: ").append(fecIni.getLabel());
//                    }
//
//                    if (!fecUpd.getLabel().isEmpty()) {
//                        toolTip.append(" / Modificada: ").append(fecUpd.getLabel());
//                    }
//                    usuario.setTooltiptext(toolTip.toString());
////              Fin ToolTip
//                }
//            });
//
//            usuario.setParent(row);
//            //Fin de usuario
//
//            new Label(String.valueOf("Lab")).setParent(row);
//        }
//
//    }
//
////    private Document docAnteHis;
////    private Label nomPractica, IdPractica;
////    private Pedidos PracticaInforme;
////    
////    private Label idOrden, idEmpresa;
//    AdmiNegocio admNegocio;
//    List XMLResultados;
//
//    private Nombre medico;
//
//    private void reset() {
//        try {
////            BanDel = false;
////            east.setVisible(false);
////            TabHistoria.setSelected(true);
////            tabbox.setVisible(false);
////            btnBuscar.setDisabled(false);
////            btnGuardar.setDisabled(false);
////            bOrden.setDisabled(false);
////            bHistorial.setDisabled(false);
////            docAnteHis = null;
////            limpiarDatos();
////            DocAnte = null;
////            timerStop();
////            counter.setValue("");
//        } catch (Exception e) {
//            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.reset()" + e.getMessage());
//            //alert("Seleccione menu historia");
//        }
//    }
//
//    private AMedia loadReportXml(Map<String, Object> wSQL, boolean forceVisible, boolean imprimi) {
//        iReport reportes = new iReport();
//        try {
//            byte[] buf = null;
//            try {
//                buf = reportes.getReportXml(wSQL, "r.idOrden", forceVisible, imprimi);
//                reportes.guardarUsuario(usuario);
//            } catch (Exception e) {
//                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraInformes.loadReportVista()");
//            }
//            if (buf != null) {
//                //byte[] buf1 = UtilPdf.marcaDeAgua(buf);
//                InputStream mediaIS = new ByteArrayInputStream(buf);
//                AMedia media = new AMedia("informe.pdf", "pdf", "application/pdf", mediaIS);
//                return media;
//            } else {
//                AMedia media1 = null;
//                return media1;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private AMedia loadReportXmlAuditoria(Map<String, Object> wSQL, Integer tipo, boolean forceVisible, boolean imprimi) {
//        iReport reportes = new iReport();
//        try {
//            byte[] buf = null;
//            try {
//                //buf = reportes.getReportXml(wSQL ,"r.idOrden", forceVisible, imprimi);
//                buf = reportes.getReportXmlAuditoria(wSQL, tipo, "r.idOrden", forceVisible, imprimi);
//            } catch (Exception e) {
//                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraInformes.loadReportVista()" + e);
//            }
//            if (buf != null) {
//                //byte[] buf1 = UtilPdf.marcaDeAgua(buf);
//                InputStream mediaIS = new ByteArrayInputStream(buf);
//                AMedia media = new AMedia("informe.pdf", "pdf", "application/pdf", mediaIS);
//                return media;
//            } else {
//                AMedia media1 = null;
//                return media1;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}

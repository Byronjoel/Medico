package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.modelo.core.CsGrupos;
import ec.com.cubosoft.avamed.modelo.core.CsPerxgru;
import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
import ec.com.cubosoft.avamed.modelo.core.Perxuser;
import ec.com.cubosoft.avamed.modelo.organizacion.Organizacion;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import ec.com.cubosoft.avamed.modelo.practica.*;
import ec.com.cubosoft.avamed.procesos.*;
import ec.com.cubosoft.avamed.modelo.medico.Area;
import ec.com.cubosoft.avamed.modelo.nextla.LispetAvanex;
import ec.com.cubosoft.avamed.modelo.nextla.STrausu;
import ec.com.cubosoft.avamed.modelo.nextla.SUsuar;
import ec.com.cubosoft.avamed.modelo.nextla.sessionOk;
import ec.com.cubosoft.avamed.modelo.persona.ResultadoGrafico;
import ec.com.cubosoft.avamed.modelo.vistas.Resultado;
import ec.com.cubosoft.avasus.controller.renderder.AreaRenderer;
import ec.com.cubosoft.avasus.controller.renderder.OrganizacionRendererCombo;
import ec.com.cubosoft.avasus.controller.renderder.PracticaRenderer;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.naming.NamingException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zul.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.cubosoft.common.soap.PdfResponse;
import org.zkoss.util.media.AMedia;

/**
 *
 * @author Administrador
 */
public class ControladoraInformes extends GenericForwardComposer {

    private Integer idArea, idPractica;
    private Long idOrganizacion;
    //fechas
    SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy", new Locale("es", "ES"));
    Button btnBuscar, btnReset;
    ProcesosSession admiSessionUsuario = new ProcesosSession();
    CsPerxgru permisosMenuIDControles = null;
    Textbox bCedula, bOrden, bHistoria;
    Datebox FecHasta, FecDesde;
    Tree arbol;
    Iframe reporte;
    CsGrupos objGrupoActivoP;
    CsUsuarios objUsuarioActivoP;
    List<CsPerxgru> listPermisos;
    Window WinInformes;
    AdmiNegocio admNegocio;
    //CsGrupos objGrupoActivoN;
    SUsuar objUsuarioActivoN;
    Listbox LbxPracticas, LbxAreas;
    Combobox LbxEmpresas;
//       , LbxEmpresas;
    Bandbox bbAreas, bbPracticas, bbEmpresa;
    Treechildren root;

    public void onCreate$WinInformes() {
        try {
            modificarSession();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    private Integer alta;
    private Integer baja;
    private Integer modi;
    private String usuario;
    private boolean abierto;
    private boolean impr;
    private boolean carga;
    private boolean edit;
    private boolean copiar;

    private void modificarSession() {
        try {
            ProcesosSession admiSessionUsuario = new ProcesosSession();
            sessionOk objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());

            if (objsessiActica != null) {
                objsessiActica.getPagina();
            } else {
                System.out.println("error en la sessión activa");

               // System.out.println("error en la sessión activa objsess getObjOrden" + objsessiActica.getObjOrden() + "usuario nextlab" + objsessiActica.getUsuarioN() + objsessiActica.getUsuario());
            }

            try {
                objsessiActica.setPagina(page.getId());

            } catch (Exception e) {
                System.out.println("verificar error" + e.getMessage());
            }

            List listaper = null;
            objUsuarioActivoP = null;
            objUsuarioActivoN = null;
            usuario = "";
            admiSessionUsuario.AgregarAtributoSession(2, objsessiActica, desktop.getSession());
            if (objsessiActica.getTipo() == 1) {
                listaper = objsessiActica.getPerUsuNext();
                objUsuarioActivoN = objsessiActica.getUsuarioN();
                usuario = objUsuarioActivoN.getUsuario();
                Perxuser objpe = objsessiActica.getPerUsuNex();
                abierto = false;
                impr = false;
                carga = false;
                edit = false;
                copiar = false;
                if (objpe != null) {
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
                }
            } else {
                listaper = objsessiActica.getPerUsuAva();
                objUsuarioActivoP = objsessiActica.getUsuarioP();
                usuario = objUsuarioActivoP.getUsuario();
                if (objUsuarioActivoP.getPerUpload().intValue() == 1) {
                    carga = false;
                } else {
                    carga = true;
                }
                if (objUsuarioActivoP.getPerAbto() == 1) {
                    abierto = false;
                } else {
                    abierto = true;
                }
                if (objUsuarioActivoP.getPriVis() == 0) {
                    impr = false;
                } else {
                    impr = true;
                }
                if (objUsuarioActivoP.getPerCyp() == 1) {
                    copiar = false;
                } else {
                    copiar = true;
                }
            }
            String idMenuPermiso = null;
            for (int i = 0; i < listaper.size(); i++) {

                switch (objsessiActica.getTipo()) {
                    case 0: {
                        CsPerxgru obj = (CsPerxgru) listaper.get(i);
                        idMenuPermiso = obj.getCsPerxgruPK().getCodPer();
                        if (idMenuPermiso.equalsIgnoreCase("EDIT")) {
                            edit = true;
                        }
                        alta = Short.hashCode(obj.getAlta());
                        baja = Short.hashCode(obj.getBaja());
                        modi = Short.hashCode(obj.getModif());
                    }
                    break;
                    case 1: {
                        STrausu obj = (STrausu) listaper.get(i);
                        idMenuPermiso = obj.getSTrausuPK().getTransac();
                        alta = obj.getAlta().compareTo('S');
                        baja = obj.getBaja().compareTo('S');
                        modi = obj.getModif().compareTo('S');
                    }
                    break;
                }
                if ((idMenuPermiso.equalsIgnoreCase("m_informe")) || ((idMenuPermiso.equalsIgnoreCase("Ordenes")))) {
                    i = listaper.size();
                    enableButtons();
                    loadPracticas();
                    loadEmpresas();
                    loadAreas();
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }
    List<XmlResultado> resultadosR;
    List<Resultado> resultadosVista;
    Borderlayout border;
    Fileupload fileChooser = new Fileupload();

    public void enableButtons() {

        btnBuscar.addEventListener(Events.ON_CLICK, new EventListener() {

            @Override
            public void onEvent(Event event) throws Exception {
                find();
            }
        });

        btnBuscar.setVisible(true);
        btnReset.addEventListener(Events.ON_CLICK, new EventListener() {

            @Override
            public void onEvent(Event event) throws Exception {
                reset();
            }
        });
    }

    private List getInformesVista() throws NamingException {
        Map<String, Object> wSQL = new HashMap<String, Object>();
        List<String> predi = new ArrayList<>();
        if (!bOrden.getValue().isEmpty()) {
            wSQL.put("idOrden", Integer.parseInt(bOrden.getValue()));
            predi.add("r.idOrden = :idOrden");
        }
        if (FecDesde.getValue() != null) {
            wSQL.put("fecha1", FecDesde.getValue());
            predi.add("r.fecha >= :fecha1");
        }
        if (FecDesde.getValue() != null) {
            wSQL.put("fecha2", FecHasta.getValue());
            predi.add("r.fecha <= :fecha2");
        }
        if (idOrganizacion != null) {
            wSQL.put("idEmpresa", idOrganizacion);
            predi.add("r.idEmpresa= :idEmpresa");
        }
        if (!bCedula.getValue().isEmpty()) {
            wSQL.put("numId", bCedula.getValue());
            predi.add("r.numId= :numId");
        }
        List data = new ArrayList();
        // System.out.println("1.3" + new Date() + " / " + " / ");
        data = admNegocio.getDataVista(wSQL, 4, "r.idOrden DESC", true, predi);
        // System.out.println("1.4" + new Date() + " / " + " / ");
        if (data.isEmpty()) {
            wSQL = new HashMap<String, Object>();
            predi = new ArrayList<>();
            if (!bOrden.getValue().isEmpty()) {
                wSQL.put("codOrd", Integer.parseInt(bOrden.getValue()));
                predi.add("r.codOrd = :codOrd");
            }
            if (FecDesde.getValue() != null) {
                wSQL.put("fecha1", FecDesde.getValue());
                predi.add("r.fecha >= :fecha1");
            }
            if (FecDesde.getValue() != null) {
                wSQL.put("fecha2", FecHasta.getValue());
                predi.add("r.fecha <= :fecha2");
            }
            if (idOrganizacion != null) {
                wSQL.put("idEmpresa", idOrganizacion);
                predi.add("r.idEmpresa= :idEmpresa");
            }
            if (!bCedula.getValue().isEmpty()) {
                wSQL.put("numId", bCedula.getValue());
                predi.add("r.numId= :numId");
            }
            data = new ArrayList();
            data = admNegocio.getDataVista(wSQL, 4, "r.codOrd DESC", true, predi);
            if (data.isEmpty()) {
                // alert("Consultar lab");
            }
        }
        if (!data.isEmpty()) {
            //  for (Object r : data) {
            //    System.out.print(((Resultado) r).getIdOrden());
            //   }
            return data;
        } else {
            alert("Busqueda vacia");
            return null;
        }
    }

    private void find() throws InterruptedException, NamingException {
        //List<XmlResultado> ListaXmlResultados = getInformes();
        //System.out.println("1" + new Date() + " / " + " / ");
        List<Resultado> ListaXmlResultados = getInformesVista();
        //System.out.println("1.2" + new Date() + " / " + " / ");
        if ((ListaXmlResultados != null)) {
            //  System.out.println("2" + new Date() + " / " + " / ");
            cleanTree();
            //System.out.println("3" + new Date() + " / " + " / ");
            arbol.setVisible(true);
            if (ListaXmlResultados.size() > 0) {
                loadTree(ListaXmlResultados);
                //  System.out.println("4" + new Date() + " / " + " / ");
            }
        } else {
            alert("No hay registros");
        }
    }
    private List ListapedidosN;
    private LispetAvanex objOrdenN;

    private void loadReport(Resultado resultado) {
        List<Resultado> resultados = new ArrayList<Resultado>();
        resultados.add(resultado);

        loadReportVista(resultados);
    }

    private boolean loadReportLab(Resultado resultado) {
        boolean lab;
        List<Resultado> resultados = new ArrayList<Resultado>();
        resultados.add(resultado);
        if ((loadReportVistaLab(resultados))!=false) {
            lab=true;
        }else
            lab=false;
        //lab = loadReportVistaLab(resultados);
        return lab;
    }

//    private static PdfResponse pdfXOrden(java.lang.String order, java.lang.String password) {
//        net.cubosoft.misanalisis.ws.EReport service = new net.cubosoft.misanalisis.ws.EReport();
//        net.cubosoft.misanalisis.ws.EReportWS port = service.getEReportWSPort();
//        return port.pdfXOrden(order, password);
//    }
    //    String nombreZip;
    private boolean loadReportVistaLab(List<Resultado> resultados) {
        boolean lab = false;
        reporte.setVisible(false);
        String fileName = "resultados.pdf";
        if (resultados.size() == 1) {
            fileName = null;
            fileName = "resultado_O" + resultados.get(0).getIdOrden() + "_P" + resultados.get(0).getIdPractica();
        }
        try {
            resultadosVista = resultados;
            iReport reportes = new iReport();

            boolean impre = false;
            if (impr) {
                impre = true;
            }
            if (resultados.size() == 1) {
                Resultado ref = resultados.get(0);
                if (ref.getIdPractica() == 415) {
                    //imageLock = new Image("/images/lock.png");

                    impre = true;
                }
            }
            byte[] buf = null;
            try {
                buf = reportes.getReportVista(resultados, false, impre);
                
            } catch (Exception e) {
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraInformes.loadReportVista()");
            }

            if (buf != null) {
                InputStream mediaIS = new ByteArrayInputStream(buf);
                AMedia media = new AMedia(fileName, "pdf", "application/pdf", mediaIS);
                reporte.setContent(media);
                reporte.setVisible(true);
                lab=true;
            } else {
                //Messagebox.show("El informe esta incompleto o no está cerrado, consulte con el médico responsable",
                //       "Información / Está incompleto el informe", Messagebox.OK, Messagebox.INFORMATION);
                Messagebox.show("El informe todavía no esta liberado",
                        "Información / Está incompleto el informe", Messagebox.OK, Messagebox.INFORMATION);
                //int laboratorio = 0;
                //String text = "El informe todavía no esta liberado , Información / Está incompleto el informe";
                lab=false;
               
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
         return lab;
    }

    private void loadReportVista(List<Resultado> resultados) {

        reporte.setVisible(false);
        String fileName = "resultados.pdf";
        if (resultados.size() == 1) {
            fileName = null;
            fileName = "resultado_O" + resultados.get(0).getIdOrden() + "_P" + resultados.get(0).getIdPractica();
        }
        try {
            resultadosVista = resultados;
            iReport reportes = new iReport();

            boolean impre = false;
            if (impr) {
                impre = true;
            }
            if (resultados.size() == 1) {
                Resultado ref = resultados.get(0);
                if (ref.getIdPractica() == 415) {
                    //imageLock = new Image("/images/lock.png");

                    impre = true;
                }
            }
            byte[] buf = null;
            try {
                buf = reportes.getReportVista(resultados, false, impre);
            } catch (Exception e) {
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraInformes.loadReportVista()");
            }

            if (buf != null) {
                InputStream mediaIS = new ByteArrayInputStream(buf);
                AMedia media = new AMedia(fileName, "pdf", "application/pdf", mediaIS);
                reporte.setContent(media);
                reporte.setVisible(true);
            } else {
                //Messagebox.show("El informe esta incompleto o no está cerrado, consulte con el médico responsable",
                //       "Información / Está incompleto el informe", Messagebox.OK, Messagebox.INFORMATION);
                Messagebox.show("El informe todavía no esta liberado",
                        "Información / Está incompleto el informe", Messagebox.OK, Messagebox.INFORMATION);
                //int laboratorio = 0;
                //String text = "El informe todavía no esta liberado , Información / Está incompleto el informe";
                return;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTree(List<Resultado> xmlResultados) {
        Treeitem itemRoot = new Treeitem("(Todos los Informes)");
        Treechildren childrenRoot = new Treechildren();
        Treechildren childrenPaciente = null;
        Treechildren childrenOrden = null;
        BigInteger idHistoria = new BigInteger("0");
        BigInteger idOrden = new BigInteger("-1");
        Treeitem itemHistoria = null;
        Treeitem itemOrden = null;
        boolean doc = false;
        itemRoot.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {

            @Override
            public void onEvent(Event event) throws Exception {
                List<Resultado> resultados = new ArrayList<Resultado>();
                Treeitem itemRootClicked = (Treeitem) event.getTarget();
//                nombreZip = itemRootClicked.getLabel();
                for (Object objeto : itemRootClicked.getChildren()) {
                    if (objeto.getClass().getCanonicalName().
                            equals(Treechildren.class.getCanonicalName())) {
                        for (Object subObjeto : ((Treechildren) objeto).getChildren()) {
                            if (subObjeto.getClass().getCanonicalName().equals(Treeitem.class.getCanonicalName())) {
                                resultados.addAll(getResultadoLoopHistoriaV(((Treeitem) subObjeto)));
                            }
                        }
                    }
                }

                if (resultados.size() > 0) {
                    loadReportVista(resultados);
                } else {
                    reporte.setVisible(false);
                    Messagebox.show("No existen informes listos para impresión en la orden escogida.");
                }
            }
        });
        //      int nuhis = 1;
        for (Resultado resultado : xmlResultados) {
            //Nodo de Historia
            if (!idHistoria.equals(resultado.getIdHistoria())) {
                idHistoria = resultado.getIdHistoria();
                itemHistoria = new Treeitem(resultado.getNombres() + " " + resultado.getApellidos(), resultado.getIdHistoria());
                childrenRoot.appendChild(itemHistoria);
                childrenPaciente = new Treechildren();
                idOrden = new BigInteger("-1");
                itemHistoria.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {

                    @Override
                    public void onEvent(Event event) throws Exception {

                        List<Resultado> resultados = new ArrayList<Resultado>();
                        Treeitem itemHistoriaClicked = (Treeitem) event.getTarget();
//                        nombreZip = itemHistoriaClicked.getLabel();
                        resultados.addAll(getResultadoLoopHistoriaV(itemHistoriaClicked));
                        if (resultados.size() > 0) {
                            loadReportVista(resultados);
                        } else {
                            reporte.setVisible(false);
                            Messagebox.show("No existen informes listos para impresión en la orden escogida.");
                        }

                    }
                });
            }

            //Nodo de Orden
            if (!idOrden.equals(resultado.getIdOrden())) {
                idOrden = resultado.getIdOrden();
                if (resultado.getNroOrd() != null) {
                    if (resultado.getNroOrd() > 0) {
//                        itemOrden = new Treeitem(resultado.getIdOrden().toString() + " /  " + resultado.getNroOrd(), resultado.getIdOrden());
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
                childrenPaciente.appendChild(itemOrden);
                if (resultado.getEmpresa() != null) {
//                    if (objUsuarioActivoP != null) {
                    if (!(resultado.getEmpresa().equalsIgnoreCase("ECUAAMERICAN"))) //                                && (objUsuarioActivoP.getPriVis() == 0))) {
                    {
                        childrenPaciente.setParent(itemHistoria);
                        childrenOrden = new Treechildren();
                        childrenOrden.setParent(itemOrden);
                        itemOrden.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                            @Override
                            public void onEvent(Event event) throws Exception {
                                //System.out.append(event.getTarget().toString());
                                List<Resultado> resultados = new ArrayList<Resultado>();
                                Treeitem itemOrdenClicked = (Treeitem) event.getTarget();
                                resultados.addAll(getResultadoLoopOrden(itemOrdenClicked));
                                if (resultados.size() > 0) {
                                    loadReportVista(resultados);
                                } else {
                                    reporte.setVisible(false);
                                    Messagebox.show("No existen informes listos para impresión en la orden escogida.");
                                }
                            }
                        });
                    } else {
                        Messagebox.show("Usuario no dispone permiso para visualizar.");
                    }

                } else {
                    childrenPaciente.setParent(itemHistoria);
                    childrenOrden = new Treechildren();
                    childrenOrden.setParent(itemOrden);
                    itemOrden.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            //System.out.append(event.getTarget().toString());
                            List<Resultado> resultados = new ArrayList<Resultado>();
                            Treeitem itemOrdenClicked = (Treeitem) event.getTarget();
                            resultados.addAll(getResultadoLoopOrden(itemOrdenClicked));
                            if (resultados.size() > 0) {
                                loadReportVista(resultados);
                            } else {
                                reporte.setVisible(false);
                                Messagebox.show("No existen informes listos para impresión en la orden escogida.");
                            }
                        }
                    });
                }
            }
            //Nodo de Informes XML
            Treeitem itemInforme = new Treeitem();
            itemInforme.setValue(resultado);
            Treerow rowInforme = new Treerow();
            Treecell cellPractica = new Treecell(resultado.getDescripcion());
            boolean ris = false;
            if ((resultado.getEstado().equalsIgnoreCase("CO") && (resultado.getResultado().equalsIgnoreCase("RIS")))) {
                ris = consultarRis(resultado);
            }
            cellPractica.setParent(rowInforme);
            Treecell cellImgEstado = new Treecell();
            Image imageLock = null;
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
            itemInforme.setParent(childrenOrden);
            boolean lab = false;
            itemInforme.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {

                @Override
                public void onEvent(Event event) throws Exception {

                    Treeitem itemInformeClicked = (Treeitem) event.getTarget();
                    Resultado resultado = (Resultado) itemInformeClicked.getValue();
//                    if (resultado.getResultado().equalsIgnoreCase("RIS")) {
//                        Messagebox.show("Informe Incompleto, no se puede cargar.");
//                    } else {
                    if (resultado.getEstado().equals("CO")) {
                        loadReport(resultado);
                    } else {
                        if (resultado.getEstado().equals("AU")) {
                            loadReport(resultado);
                        } else {
                            if (resultado.getEstado().equals("AR")) {
                                loadReport(resultado);
                            } else {
                                if (resultado.getEstado().equals("IM")) {
                                    loadReport(resultado);
                                } else {
                                    if (resultado.getIdPractica() == 415) {
                                        //loadReport(resultado);
                                        //erick?
                                        if ((loadReportLab(resultado))!=false) {
                                            cellImgEstado.setImage("/images/lock.png");
                                        }else
                                            Messagebox.show("Informe Incompleto, no se puede mostrar para impresión.");

                                    } else {
                                        reporte.setVisible(false);
                                        Messagebox.show("Informe Incompleto, no se puede mostrar para impresión.");
                                    }
                                }
                            }
                        }
//                        }
                    }
                }
            });

        }
        childrenRoot.setParent(itemRoot);
        itemRoot.setParent(root);
        if (doc) {
            alert("Existe otro paciente con ese codigo especifique la busqueda por orden");
        }
    }

    //tree Erick
    private void loadTreeImage(List<Resultado> xmlResultados) {
        Treeitem itemRoot = new Treeitem("(Todos los Informes)");
        Treechildren childrenRoot = new Treechildren();
        Treechildren childrenPaciente = null;
        Treechildren childrenOrden = null;
        BigInteger idHistoria = new BigInteger("0");
        BigInteger idOrden = new BigInteger("-1");
        Treeitem itemHistoria = null;
        Treeitem itemOrden = null;
        boolean doc = false;
        itemRoot.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {

            @Override
            public void onEvent(Event event) throws Exception {
                List<Resultado> resultados = new ArrayList<Resultado>();
                Treeitem itemRootClicked = (Treeitem) event.getTarget();
//                nombreZip = itemRootClicked.getLabel();
                for (Object objeto : itemRootClicked.getChildren()) {
                    if (objeto.getClass().getCanonicalName().
                            equals(Treechildren.class.getCanonicalName())) {
                        for (Object subObjeto : ((Treechildren) objeto).getChildren()) {
                            if (subObjeto.getClass().getCanonicalName().equals(Treeitem.class.getCanonicalName())) {
                                resultados.addAll(getResultadoLoopHistoriaV(((Treeitem) subObjeto)));
                            }
                        }
                    }
                }

                if (resultados.size() > 0) {
                    loadReportVista(resultados);
                } else {
                    reporte.setVisible(false);
                    Messagebox.show("No existen informes listos para impresión en la orden escogida.");
                }
            }
        });
        //      int nuhis = 1;
        for (Resultado resultado : xmlResultados) {
            //Nodo de Historia
            if (!idHistoria.equals(resultado.getIdHistoria())) {
                idHistoria = resultado.getIdHistoria();
                itemHistoria = new Treeitem(resultado.getNombres() + " " + resultado.getApellidos(), resultado.getIdHistoria());
                childrenRoot.appendChild(itemHistoria);
                childrenPaciente = new Treechildren();
                idOrden = new BigInteger("-1");
                itemHistoria.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {

                    @Override
                    public void onEvent(Event event) throws Exception {

                        List<Resultado> resultados = new ArrayList<Resultado>();
                        Treeitem itemHistoriaClicked = (Treeitem) event.getTarget();
//                        nombreZip = itemHistoriaClicked.getLabel();
                        resultados.addAll(getResultadoLoopHistoriaV(itemHistoriaClicked));
                        if (resultados.size() > 0) {
                            loadReportVista(resultados);
                        } else {
                            reporte.setVisible(false);
                            Messagebox.show("No existen informes listos para impresión en la orden escogida.");
                        }

                    }
                });
            }

            //Nodo de Orden
            if (!idOrden.equals(resultado.getIdOrden())) {
                idOrden = resultado.getIdOrden();
                if (resultado.getNroOrd() != null) {
                    if (resultado.getNroOrd() > 0) {
//                        itemOrden = new Treeitem(resultado.getIdOrden().toString() + " /  " + resultado.getNroOrd(), resultado.getIdOrden());
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
                childrenPaciente.appendChild(itemOrden);
                if (resultado.getEmpresa() != null) {
//                    if (objUsuarioActivoP != null) {
                    if (!(resultado.getEmpresa().equalsIgnoreCase("ECUAAMERICAN"))) //                                && (objUsuarioActivoP.getPriVis() == 0))) {
                    {
                        childrenPaciente.setParent(itemHistoria);
                        childrenOrden = new Treechildren();
                        childrenOrden.setParent(itemOrden);
                        itemOrden.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                            @Override
                            public void onEvent(Event event) throws Exception {
                                //System.out.append(event.getTarget().toString());
                                List<Resultado> resultados = new ArrayList<Resultado>();
                                Treeitem itemOrdenClicked = (Treeitem) event.getTarget();
                                resultados.addAll(getResultadoLoopOrden(itemOrdenClicked));
                                if (resultados.size() > 0) {
                                    loadReportVista(resultados);
                                } else {
                                    reporte.setVisible(false);
                                    Messagebox.show("No existen informes listos para impresión en la orden escogida.");
                                }
                            }
                        });
                    } else {
                        Messagebox.show("Usuario no dispone permiso para visualizar.");
                    }

                } else {
                    childrenPaciente.setParent(itemHistoria);
                    childrenOrden = new Treechildren();
                    childrenOrden.setParent(itemOrden);
                    itemOrden.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            //System.out.append(event.getTarget().toString());
                            List<Resultado> resultados = new ArrayList<Resultado>();
                            Treeitem itemOrdenClicked = (Treeitem) event.getTarget();
                            resultados.addAll(getResultadoLoopOrden(itemOrdenClicked));
                            if (resultados.size() > 0) {
                                loadReportVista(resultados);
                            } else {
                                reporte.setVisible(false);
                                Messagebox.show("No existen informes listos para impresión en la orden escogida.");
                            }
                        }
                    });
                }
            }
            //Nodo de Informes XML
            Treeitem itemInforme = new Treeitem();
            itemInforme.setValue(resultado);
            Treerow rowInforme = new Treerow();
            Treecell cellPractica = new Treecell(resultado.getDescripcion());
            boolean ris = false;
            if ((resultado.getEstado().equalsIgnoreCase("CO") && (resultado.getResultado().equalsIgnoreCase("RIS")))) {
                ris = consultarRis(resultado);
            }
            cellPractica.setParent(rowInforme);
            Treecell cellImgEstado = new Treecell();
            Image imageLock = null;
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
            itemInforme.setParent(childrenOrden);
            itemInforme.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {

                @Override
                public void onEvent(Event event) throws Exception {

                    Treeitem itemInformeClicked = (Treeitem) event.getTarget();
                    Resultado resultado = (Resultado) itemInformeClicked.getValue();
//                    if (resultado.getResultado().equalsIgnoreCase("RIS")) {
//                        Messagebox.show("Informe Incompleto, no se puede cargar.");
//                    } else {
                    if (resultado.getEstado().equals("CO")) {
                        loadReport(resultado);
                    } else {
                        if (resultado.getEstado().equals("AU")) {
                            loadReport(resultado);
                        } else {
                            if (resultado.getEstado().equals("AR")) {
                                loadReport(resultado);
                            } else {
                                if (resultado.getEstado().equals("IM")) {
                                    loadReport(resultado);
                                } else {
                                    if (resultado.getIdPractica() == 415) {
                                        loadReport(resultado);
                                        //   imageLock = new Image("/images/lock.png");
                                    } else {
                                        reporte.setVisible(false);
                                        Messagebox.show("Informe Incompleto, no se puede mostrar para impresión.");
                                    }
                                }
                            }
                        }
//                        }
                    }
                }
            });

        }
        childrenRoot.setParent(itemRoot);
        itemRoot.setParent(root);
        if (doc) {
            alert("Existe otro paciente con ese codigo especifique la busqueda por orden");
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
        //            File someFile = new File(dirPDF);
        //            FileOutputStream fos;
        //            fos = new FileOutputStream(someFile);
        //            fos.write(byteArray);
        //            fos.flush();
        //            fos.close();
        catch (Exception e) {
            System.out.println("ec.com.cubosoft.avamed.procesos.iReport.consultarRis()");
        }
        return false;
    }

    private List<Resultado> getResultadoLoopHistoriaV(Treeitem itemHistoria) {
        List<Resultado> resultados = new ArrayList<Resultado>();
        for (Object objeto : itemHistoria.getChildren()) {
            if (objeto.getClass().getCanonicalName().
                    equals(Treechildren.class.getCanonicalName())) {
                for (Object subObjeto : ((Treechildren) objeto).getChildren()) {
                    if (subObjeto.getClass().getCanonicalName().equals(Treeitem.class.getCanonicalName())) {
                        resultados.addAll(getResultadoLoopOrdenV(((Treeitem) subObjeto)));
                    }
                }
            }
        }
        return resultados;
    }

    private List<Resultado> getResultadoLoopOrdenV(Treeitem itemOrden) {

        List<Resultado> resultados = new ArrayList<Resultado>();
        for (Object objeto : itemOrden.getChildren()) {
            if (objeto.getClass().getCanonicalName().
                    equals(Treechildren.class.getCanonicalName())) {

                Treechildren children = (Treechildren) objeto;
                for (Object subObjeto : children.getChildren()) {
                    if (subObjeto.getClass().getCanonicalName().
                            equals(Treeitem.class.getCanonicalName())) {

                        Treeitem item = (Treeitem) subObjeto;

                        if (item.getValue().getClass().getCanonicalName().
                                equals(Resultado.class.getCanonicalName())) {
                            Resultado resultado = (Resultado) item.getValue();
                            if (resultado.getEstado().equals("CO")) {
                                resultados.add(resultado);
                            } else {
                                if (resultado.getEstado().equals("AU")) {
                                    resultados.add(resultado);
                                } else {
                                    if (resultado.getEstado().equals("AR")) {
                                        resultados.add(resultado);
                                    } else {
                                        if (resultado.getEstado().equals("IM")) {
                                            resultados.add(resultado);
                                        }
                                    }
                                }
                            }
                            if (resultado.getResultado().equalsIgnoreCase("RIS")) {
                                resultados.add(resultado);
                            }
                        }
                    }
                }
            }
        }
        return resultados;
    }

    private List<Resultado> getResultadoLoopOrden(Treeitem itemOrden) {

        List<Resultado> resultados = new ArrayList<Resultado>();
        for (Object objeto : itemOrden.getChildren()) {
            if (objeto.getClass().getCanonicalName().
                    equals(Treechildren.class.getCanonicalName())) {
                Treechildren children = (Treechildren) objeto;
                for (Object subObjeto : children.getChildren()) {
                    if (subObjeto.getClass().getCanonicalName().equals(Treeitem.class.getCanonicalName())) {
                        Treeitem item = (Treeitem) subObjeto;
                        if (item.getValue().getClass().getCanonicalName().equals(Resultado.class.getCanonicalName())) {
                            Resultado resultado = (Resultado) item.getValue();
                            if (resultado.getEstado().equals("CO")) {
                                resultados.add(resultado);
                            } else {
                                if (resultado.getEstado().equals("AU")) {
                                    resultados.add(resultado);
                                } else {
                                    if (resultado.getEstado().equals("AR")) {
                                        resultados.add(resultado);
                                    } else {
                                        if (resultado.getEstado().equals("IM")) {
                                            resultados.add(resultado);
                                        }

                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
        return resultados;
    }

    public void reset() {
        cleanTree();
        bHistoria.setValue("");
        bCedula.setValue("");
        bOrden.setValue("");
        FecDesde.setValue(new Date());
        FecHasta.setValue(new Date());
        bbAreas.setValue("");
        bbPracticas.setValue("");
        LbxEmpresas.setValue(null);
        idArea = null;
        idOrganizacion = null;
        idPractica = null;
        arbol.setVisible(false);
        //Reporte
        reporte.setVisible(false);
    }

    public void cleanTree() {
        while (root.getItemCount() > 0) {
            root.removeChild(root.getFirstChild());
        }
    }

    public void loadAreas() throws NamingException {

        Object table = new Area();
        List oSQL = new ArrayList();
        oSQL.add("descripcion");
        Map<String, Object> wSQL = new HashMap<String, Object>();
        wSQL.put("lockReg ?=", 0);
//        wSQL.put("idPadre?=", 0);
        List objectList = admNegocio.getData(table, wSQL, null, oSQL);
        LbxAreas.setItemRenderer(new AreaRenderer());
        LbxAreas.setModel(new ListModelList(objectList));
        bbAreas.setValue(null);
    }

    public void onSelect$LbxAreas() {
        Area area = (Area) LbxAreas.getSelectedItem().getValue();
        bbAreas.setValue(area.getDescripcion());
        idArea = area.getId();
        bbAreas.close();
    }

    public void loadEmpresas() throws NamingException {

        Object table = new Organizacion();
        List oSQL = new ArrayList();
        oSQL.add("abreviatura");
        List objectList = admNegocio.getData(table, false, oSQL);
        LbxEmpresas.setItemRenderer(new OrganizacionRendererCombo());
        LbxEmpresas.setModel(new ListModelList(objectList));
        LbxEmpresas.setValue(null);
    }

    public void onSelect$LbxEmpresas() {
        Organizacion empresa = (Organizacion) LbxEmpresas.getSelectedItem().getValue();
        LbxEmpresas.setValue(empresa.getAbreviatura());
        idOrganizacion = empresa.getId();
        LbxEmpresas.close();
    }
//    public void loadEmpresas() throws NamingException {
//
//        Object table = new Organizacion();
//        List oSQL = new ArrayList();
//        oSQL.add("abreviatura");
//
//        List objectList = admNegocio.getData(table, false, oSQL);
//
//        LbxEmpresas.setItemRenderer(new OrganizacionRenderer());
//        LbxEmpresas.setModel(new ListModelList(objectList));
//
//        bbEmpresa.setValue(null);
//    }
//
//    public void onSelect$LbxEmpresas() {
//        Organizacion empresa = (Organizacion) LbxEmpresas.getSelectedItem().getValue();
//        bbEmpresa.setValue(empresa.getAbreviatura());
//        idOrganizacion = empresa.getId();
//        bbEmpresa.close();
//    }

    public void loadPracticas() throws NamingException {
        admNegocio = new AdmiNegocio();
        Object table = new NombreP();
        List oSQL = new ArrayList();
        oSQL.add("descripcion");

        List objectList = admNegocio.getData(table, false, oSQL);

        LbxPracticas.setItemRenderer(new PracticaRenderer());
        LbxPracticas.setModel(new ListModelList(objectList));

        bbPracticas.setValue(null);
    }

    public void onSelect$LbxPracticas() {
        NombreP practica = (NombreP) LbxPracticas.getSelectedItem().getValue();
        bbPracticas.setValue(practica.getAbreviatura());
        idPractica = practica.getId();
        bbPracticas.close();
    }

}
//
///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package ec.com.cubosoft.avamed.coneccion;
//
//import ec.com.cubosoft.avamed.modelo.core.CsGrupos;
//import ec.com.cubosoft.avamed.modelo.core.CsPerxgru;
//import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
//import ec.com.cubosoft.avamed.modelo.organizacion.Organizacion;
//import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
//import ec.com.cubosoft.avamed.modelo.practica.*;
//import ec.com.cubosoft.avamed.procesos.*;
//import ec.com.cubosoft.avamed.modelo.medico.Area;
//import ec.com.cubosoft.avamed.modelo.vistas.Resultado;
//import ec.com.cubosoft.avasus.controller.renderder.AreaRenderer;
//import ec.com.cubosoft.avasus.controller.renderder.OrganizacionRenderer;
//import ec.com.cubosoft.avasus.controller.renderder.PracticaRenderer;
//import java.text.SimpleDateFormat;
//import java.util.List;
//import javax.naming.NamingException;
//import org.zkoss.zk.ui.util.GenericForwardComposer;
//import org.zkoss.zk.ui.event.*;
//import org.zkoss.zul.*;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.InputStream;
//import java.math.BigInteger;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import org.zkoss.util.media.AMedia;
//
///**
// *
// * @author Administrador
// */
//public class ControladoraInformes extends GenericForwardComposer {
//
//    private Integer idArea, idPractica;
//    private Long idOrganizacion;
//    //fechas
//    SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy");
//    Button btnBuscar, btnReset;
//    ProcesosSession admiSessionUsuario = new ProcesosSession();
//    CsPerxgru permisosMenuIDControles = null;
//    Textbox bCedula, bOrden, bHistoria;
//    Datebox FecHasta, FecDesde;
//    Tree arbol;
//    Iframe reporte;
//    CsGrupos objGrupoActivo;
//    CsUsuarios objUsuarioActivo;
//    Window WinInformes;
//    AdmiNegocio admNegocio;
//    Listbox LbxPracticas, LbxAreas, LbxEmpresas;
//    Bandbox bbAreas, bbPracticas, bbEmpresa;
//    Treechildren root;
//
//    public void onCreate$WinInformes() {
//        try {
//            modificarSession();
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
//    }
//
//    private void modificarSession() {
//        try {
//            admiSessionUsuario.AgregarAtributoSession(4, page.getId(), session);
//            permisosMenuIDControles = admiSessionUsuario.ObtenerPermisosPgina(session);
//            objGrupoActivo = (CsGrupos) admiSessionUsuario.ObtenerAtributoSession(3, session);
//            objUsuarioActivo = (CsUsuarios) admiSessionUsuario.ObtenerAtributoSession(2, session);
//            admNegocio = new AdmiNegocio();
//
//            enableButtons();
//            loadPracticas();
//            loadEmpresas();
//            loadAreas();
//
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        }
//    }
//    List<XmlResultado> resultadosR;
//    List<Resultado> resultadosVista;
//    Borderlayout border;
//    Fileupload fileChooser = new Fileupload();
//
//    public void enableButtons() {
//
//        btnBuscar.addEventListener(Events.ON_CLICK, new EventListener() {
//
//            @Override
//            public void onEvent(Event event) throws Exception {
//                find();
//            }
//        });
//
//        btnBuscar.setVisible(true);
//        btnReset.addEventListener(Events.ON_CLICK, new EventListener() {
//
//            @Override
//            public void onEvent(Event event) throws Exception {
//                reset();
//            }
//        });
//
//    }
//
//    private List getInformesVista() throws NamingException {
//
//        Map<String, Object> wSQL = new HashMap<String, Object>();
//        List<String> predi = new ArrayList<>();
//        if (!bOrden.getValue().isEmpty()) {
//            wSQL.put("idOrden", Integer.parseInt(bOrden.getValue()));
//            predi.add("r.idOrden = :idOrden");
//        }
//        if (FecDesde.getValue() != null) {
//            wSQL.put("fecha1", FecDesde.getValue());
//            predi.add("r.fecha >= :fecha1");
//        }
//        if (FecDesde.getValue() != null) {
//            wSQL.put("fecha2", FecHasta.getValue());
//            predi.add("r.fecha <= :fecha2");
//        }
//        if (idOrganizacion != null) {
//            wSQL.put("idEmpresa", idOrganizacion);
//            predi.add("r.idEmpresa= :idEmpresa");
//        }
//        if (!bCedula.getValue().isEmpty()) {
//            wSQL.put("numId", bCedula.getValue());
//            predi.add("r.numId= :numId");
//        }
//
//        List data = new ArrayList();
//        data = admNegocio.getDataVista(wSQL, 4, true, predi);
//        for (Object r : data) {
//            System.out.print(((Resultado) r).getIdOrden());
//        }
//        return data;
//    }
//
//    private void find() throws InterruptedException, NamingException {
//
////        List<XmlResultado> ListaXmlResultados = getInformes();
//        List<Resultado> ListaXmlResultados = getInformesVista();
//        //Limpia el Arbol
//        cleanTree();
//        arbol.setVisible(true);
//        if (ListaXmlResultados.size() > 0) {
//            loadTree(ListaXmlResultados);
//        }
//    }
//
//    private void loadReport(Resultado resultado) {
//        List<Resultado> resultados = new ArrayList<Resultado>();
//        resultados.add(resultado);
//        loadReportVista(resultados);
//    }
//
//    private void loadReport(List<XmlResultado> resultados) {
//
//        reporte.setVisible(false);
//        String fileName = "resultados.pdf";
//        if (resultados.size() == 1) {
//            fileName = null;
//            fileName = "resultado_O" + resultados.get(0).getIdOrden() + "_P" + resultados.get(0).getIdPractica();
//        }
//        try {
//            String global = "java:global/avasusINF/AdministradorGlobalBean";
//            String generico = "java:global/avasusINF/AdministradorGenericoBean";
//            resultadosR = resultados;
//            iReport reportes = new iReport(global, generico);
//
//            List<CsPerxgru> listPermisos = (List<CsPerxgru>) admiSessionUsuario.ObtenerAtributoSession(6, session);
//            boolean impre = false;
//            for (CsPerxgru objpermiso : listPermisos) {
//                String idPermiso = objpermiso.getCsPerxgruPK().getCodPer().toString();
//                System.out.println(idPermiso);
//                if (idPermiso.equals("IMP")) {
//                    impre = true;
//                }
//            }
//            if (resultados.size() == 1) {
//                XmlResultado ref = resultados.get(0);
//                if (ref.getPractica().getId() == 415) {
//                    impre = true;
//                }
//
//            }
//            byte[] buf = reportes.getReport(resultados, false, impre);
//            if (buf != null) {
//                InputStream mediaIS = new ByteArrayInputStream(buf);
//                AMedia media = new AMedia(fileName, "pdf", "application/pdf", mediaIS);
//                reporte.setContent(media);
//                reporte.setVisible(true);
//            } else {
//                Messagebox.show("El informe esta incompleto o no está cerrado, consulte con el médico responsable",
//                        "Información / Está incompleto el informe", Messagebox.OK, Messagebox.INFORMATION);
//                return;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
////    String nombreZip;
//
//    private void loadReportVista(List<Resultado> resultados) {
//
////        reporte.setVisible(false);
//        String fileName = "resultados.pdf";
//        if (resultados.size() == 1) {
//            fileName = null;
//            fileName = "resultado_O" + resultados.get(0).getIdOrden() + "_P" + resultados.get(0).getIdPractica();
//        }
//        try {
//            String global = "java:global/avasusINF/AdministradorGlobalBean";
//            String generico = "java:global/avasusINF/AdministradorGenericoBean";
//            resultadosVista = resultados;
//            iReport reportes = new iReport(global, generico);
//
//            List<CsPerxgru> listPermisos = (List<CsPerxgru>) admiSessionUsuario.ObtenerAtributoSession(6, session);
//            boolean impre = false;
//            for (CsPerxgru objpermiso : listPermisos) {
//                String idPermiso = objpermiso.getCsPerxgruPK().getCodPer().toString();
//                System.out.println(idPermiso);
//                if (idPermiso.equals("IMP")) {
//                    impre = true;
//                }
//            }
//            if (resultados.size() == 1) {
//                Resultado ref = resultados.get(0);
//                if (ref.getIdPractica() == 415) {
//                    impre = true;
//                }
//
//            }
//            byte[] buf = reportes.getReportVista(resultados, false, impre);
//            if (buf != null) {
//                InputStream mediaIS = new ByteArrayInputStream(buf);
//                AMedia media = new AMedia(fileName, "pdf", "application/pdf", mediaIS);
//                reporte.setContent(media);
//                reporte.setVisible(true);
//            } else {
//                Messagebox.show("El informe esta incompleto o no está cerrado, consulte con el médico responsable",
//                        "Información / Está incompleto el informe", Messagebox.OK, Messagebox.INFORMATION);
//                return;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private void loadTree(List<Resultado> xmlResultados) {
//
//        Treeitem itemRoot = new Treeitem("(Todos los Informes)");
//
//        Treechildren childrenRoot = new Treechildren();
//        Treechildren childrenPaciente = null;
//        Treechildren childrenOrden = null;
//
//        BigInteger idHistoria = new BigInteger("0");
//        BigInteger idOrden = new BigInteger("-1");
//        Treeitem itemHistoria = null;
//        Treeitem itemOrden = null;
//
//        itemRoot.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
//
//            @Override
//            public void onEvent(Event event) throws Exception {
//                List<Resultado> resultados = new ArrayList<Resultado>();
//                Treeitem itemRootClicked = (Treeitem) event.getTarget();
////                nombreZip = itemRootClicked.getLabel();
//                for (Object objeto : itemRootClicked.getChildren()) {
//                    if (objeto.getClass().getCanonicalName().
//                            equals(Treechildren.class.getCanonicalName())) {
//                        for (Object subObjeto : ((Treechildren) objeto).getChildren()) {
//                            if (subObjeto.getClass().getCanonicalName().
//                                    equals(Treeitem.class.getCanonicalName())) {
//                                resultados.addAll(getResultadoLoopHistoriaV(((Treeitem) subObjeto)));
//                            }
//                        }
//                    }
//                }
//
//                if (resultados.size() > 0) {
//                    loadReportVista(resultados);
//                } else {
//                    reporte.setVisible(false);
//                    Messagebox.show("No existen informes listos para impresión en la orden escogida.");
//                }
////                btnDividir.setDisabled(false);
//            }
//        });
//        for (Resultado resultado : xmlResultados) {
//            //Nodo de Historia
//            if (!idHistoria.equals(resultado.getIdHistoria())) {
//                idHistoria = resultado.getIdHistoria();
//
//                //Creacion del Nodo de Historia
//                itemHistoria = new Treeitem(resultado.getNombres() + " " + resultado.getApellidos(), resultado.getIdHistoria());
//                childrenRoot.appendChild(itemHistoria);
//                childrenPaciente = new Treechildren();
//                idOrden = new BigInteger("-1");
//
//                itemHistoria.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
//
//                    @Override
//                    public void onEvent(Event event) throws Exception {
//
//                        System.out.append(event.getTarget().toString());
//                        List<Resultado> resultados = new ArrayList<Resultado>();
//                        Treeitem itemHistoriaClicked = (Treeitem) event.getTarget();
////                        nombreZip = itemHistoriaClicked.getLabel();
//                        resultados.addAll(getResultadoLoopHistoriaV(itemHistoriaClicked));
//                        if (resultados.size() > 0) {
//                            loadReportVista(resultados);
//                        } else {
//                            reporte.setVisible(false);
//                            Messagebox.show("No existen informes listos para impresión en la orden escogida.");
//                        }
//
//                    }
//                });
//            }
//            if (!idOrden.equals(resultado.getIdOrden())) {
//                idOrden = resultado.getIdOrden();
//                itemOrden = new Treeitem(resultado.getIdOrden().toString(), resultado.getIdOrden());
//                childrenPaciente.appendChild(itemOrden);
//                if (resultado.getEmpresa() != null) {
//                    if (!(resultado.getEmpresa().equalsIgnoreCase("ECUAAMERICAN") && (objUsuarioActivo.getPriVis() == 0))) {
//                        childrenPaciente.setParent(itemHistoria);
//                        childrenOrden = new Treechildren();
//                        childrenOrden.setParent(itemOrden);
//                        itemOrden.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
//                            @Override
//                            public void onEvent(Event event) throws Exception {
//                                //System.out.append(event.getTarget().toString());
//                                List<Resultado> resultados = new ArrayList<Resultado>();
//                                Treeitem itemOrdenClicked = (Treeitem) event.getTarget();
//                                resultados.addAll(getResultadoLoopOrden(itemOrdenClicked));
//                                if (resultados.size() > 0) {
//                                    loadReportVista(resultados);
////                                    loadReport(resultados);
//                                } else {
//                                    reporte.setVisible(false);
//                                    Messagebox.show("No existen informes listos para impresión en la orden escogida.");
//                                }
//                            }
//                        });
//                    }
//
//                } else {
//                    childrenPaciente.setParent(itemHistoria);
//                    childrenOrden = new Treechildren();
//                    childrenOrden.setParent(itemOrden);
//                    itemOrden.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
//                        @Override
//                        public void onEvent(Event event) throws Exception {
//                            //System.out.append(event.getTarget().toString());
//                            List<Resultado> resultados = new ArrayList<Resultado>();
//                            Treeitem itemOrdenClicked = (Treeitem) event.getTarget();
//                            resultados.addAll(getResultadoLoopOrden(itemOrdenClicked));
//                            if (resultados.size() > 0) {
//                                loadReportVista(resultados);
//                            } else {
//                                reporte.setVisible(false);
//                                Messagebox.show("No existen informes listos para impresión en la orden escogida.");
//                            }
//                        }
//                    });
//                }
//            }
//            //Nodo de Informes XML
//            Treeitem itemInforme = new Treeitem();
//            itemInforme.setValue(resultado);
//            Treerow rowInforme = new Treerow();
//            Treecell cellPractica = new Treecell(resultado.getDescripcion());
//            cellPractica.setParent(rowInforme);
//            //Imagen de estado
//            Treecell cellImgEstado = new Treecell();
//            Image imageLock;
//            if (resultado.getEstado().equals("IN")) {
//                imageLock = new Image("/images/editing.png");
//            } else {
//                if (resultado.getEstado().equals("CO")) {
//                    imageLock = new Image("/images/lock.png");
//                } else {
//                    if (resultado.getEstado().equals("AU")) {
//                        imageLock = new Image("/images/ok.png");
//                    } else {
//                        if (resultado.getEstado().equals("AR")) {
//                            imageLock = new Image("/images/archivado.png");
//                        } else {
//                            if (resultado.getEstado().equals("IM")) {
//                                imageLock = new Image("/images/pdf.png");
//                            } else {
//                                imageLock = new Image("/images/document_blank.png");
//                            }
//                        }
//                    }
//                }
//            }
//
//            imageLock.setParent(cellImgEstado);
//            cellImgEstado.setParent(rowInforme);
//            //
//            rowInforme.setParent(itemInforme);
//            itemInforme.setParent(childrenOrden);
//
//            itemInforme.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
//
//                @Override
//                public void onEvent(Event event) throws Exception {
//
//                    Treeitem itemInformeClicked = (Treeitem) event.getTarget();
//                    Resultado resultado = (Resultado) itemInformeClicked.getValue();
//                    if (resultado.getEstado().equals("CO")) {
//                        System.out.append("Informe No. " + resultado.getId().toString());
//                        loadReport(resultado);
//                    } else {
//                        if (resultado.getEstado().equals("AU")) {
//                            System.out.append("Informe No. " + resultado.getId().toString());
//                            loadReport(resultado);
//                        } else {
//                            if (resultado.getEstado().equals("AR")) {
//                                System.out.append("Informe No. " + resultado.getId().toString());
//                                loadReport(resultado);
//                            } else {
//                                if (resultado.getEstado().equals("IM")) {
//                                    System.out.append("Informe No. " + resultado.getId().toString());
//                                    loadReport(resultado);
//                                } else {
//                                    reporte.setVisible(false);
//                                    Messagebox.show("Informe Incompleto, no se puede mostrar para impresión.");
//                                }
//                            }
//                        }
//                    }
//                }
//            });
//        }
//
//        childrenRoot.setParent(itemRoot);
//        itemRoot.setParent(root);
//    }
//
//
//    private List<Resultado> getResultadoLoopHistoriaV(Treeitem itemHistoria) {
//        List<Resultado> resultados = new ArrayList<Resultado>();
//        for (Object objeto : itemHistoria.getChildren()) {
////            System.out.print(objeto);
//            if (objeto.getClass().getCanonicalName().
//                    equals(Treechildren.class.getCanonicalName())) {
//                for (Object subObjeto : ((Treechildren) objeto).getChildren()) {
//                    //System.out.print("Subobjeto" + subObjeto.toString());
//                    //System.out.print("Subobjeto Valor" + ((Treeitem) subObjeto).getValue());
//                    if (subObjeto.getClass().getCanonicalName().
//                            equals(Treeitem.class.getCanonicalName())) {
//                        resultados.addAll(getResultadoLoopOrdenV(((Treeitem) subObjeto)));
//                    }
//                }
//            }
//        }
//
//        return resultados;
//    }
//
//    private List<Resultado> getResultadoLoopOrdenV(Treeitem itemOrden) {
//
//        List<Resultado> resultados = new ArrayList<Resultado>();
//        for (Object objeto : itemOrden.getChildren()) {
//            if (objeto.getClass().getCanonicalName().
//                    equals(Treechildren.class.getCanonicalName())) {
//
//                Treechildren children = (Treechildren) objeto;
//                for (Object subObjeto : children.getChildren()) {
//                    if (subObjeto.getClass().getCanonicalName().
//                            equals(Treeitem.class.getCanonicalName())) {
//
//                        Treeitem item = (Treeitem) subObjeto;
//
//                        if (item.getValue().getClass().getCanonicalName().
//                                equals(Resultado.class.getCanonicalName())) {
//                            Resultado resultado = (Resultado) item.getValue();
//                            if (resultado.getEstado().equals("CO")) {
//                                resultados.add(resultado);
//                            } else {
//                                if (resultado.getEstado().equals("AU")) {
//                                    resultados.add(resultado);
//                                } else {
//                                    if (resultado.getEstado().equals("AR")) {
//                                        resultados.add(resultado);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return resultados;
//    }
//
//    private List<Resultado> getResultadoLoopOrden(Treeitem itemOrden) {
//
//        List<Resultado> resultados = new ArrayList<Resultado>();
//        for (Object objeto : itemOrden.getChildren()) {
//            if (objeto.getClass().getCanonicalName().equals(Treechildren.class.getCanonicalName())) {
//
//                Treechildren children = (Treechildren) objeto;
//                for (Object subObjeto : children.getChildren()) {
//                    if (subObjeto.getClass().getCanonicalName().equals(Treeitem.class.getCanonicalName())) {
//
//                        Treeitem item = (Treeitem) subObjeto;
//
//                        if (item.getValue().getClass().getCanonicalName().equals(Resultado.class.getCanonicalName())) {
//                            Resultado resultado = (Resultado) item.getValue();
//                            if (resultado.getEstado().equals("CO")) {
//                                resultados.add(resultado);
//                            } else {
//                                if (resultado.getEstado().equals("AU")) {
//                                    resultados.add(resultado);
//                                } else {
//                                    if (resultado.getEstado().equals("AR")) {
//                                        resultados.add(resultado);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return resultados;
//    }
//
//    public void reset() {
//        cleanTree();
//        bHistoria.setValue("");
//        bCedula.setValue("");
//        bOrden.setValue("");
//        FecDesde.setValue(new Date());
//        FecHasta.setValue(new Date());
//        bbAreas.setValue("");
//        bbPracticas.setValue("");
//        bbEmpresa.setValue("");
//        idArea = null;
//        idOrganizacion = null;
//        idPractica = null;
//        arbol.setVisible(false);
//        //Reporte
//        reporte.setVisible(false);
//    }
//
//    public void cleanTree() {
//        while (root.getItemCount() > 0) {
//            root.removeChild(root.getFirstChild());
//        }
//    }
//
//    public void loadAreas() throws NamingException {
//
//        Object table = new Area();
//        List oSQL = new ArrayList();
//        oSQL.add("descripcion");
//        Map<String, Object> wSQL = new HashMap<String, Object>();
//        wSQL.put("lockReg ?=", 0);
////        wSQL.put("idPadre?=", 0);
//        List objectList = admNegocio.getData(table, wSQL, null, oSQL);
//        LbxAreas.setItemRenderer(new AreaRenderer());
//        LbxAreas.setModel(new ListModelList(objectList));
//        bbAreas.setValue(null);
//    }
//
//    public void onSelect$LbxAreas() {
//        Area area = (Area) LbxAreas.getSelectedItem().getValue();
//        bbAreas.setValue(area.getDescripcion());
//        idArea = area.getId();
//
//        System.out.print("Area escogida = " + idArea);
//
//        bbAreas.close();
//    }
//
//    public void loadEmpresas() throws NamingException {
//
//        Object table = new Organizacion();
//        List oSQL = new ArrayList();
//        oSQL.add("abreviatura");
//
//        List objectList = admNegocio.getData(table, false, oSQL);
//
//        LbxEmpresas.setItemRenderer(new OrganizacionRenderer());
//        LbxEmpresas.setModel(new ListModelList(objectList));
//
//        bbEmpresa.setValue(null);
//    }
//
//    public void onSelect$LbxEmpresas() {
//        Organizacion empresa = (Organizacion) LbxEmpresas.getSelectedItem().getValue();
//        bbEmpresa.setValue(empresa.getAbreviatura());
//        idOrganizacion = empresa.getId();
//
//        System.out.print("Empresa escogida = " + idOrganizacion);
//
//        bbEmpresa.close();
//    }
//
//    public void loadPracticas() throws NamingException {
//
//        Object table = new NombreP();
//        List oSQL = new ArrayList();
//        oSQL.add("descripcion");
//
//        List objectList = admNegocio.getData(table, false, oSQL);
//
//        LbxPracticas.setItemRenderer(new PracticaRenderer());
//        LbxPracticas.setModel(new ListModelList(objectList));
//
//        bbPracticas.setValue(null);
//    }
//
//    public void onSelect$LbxPracticas() {
//        NombreP practica = (NombreP) LbxPracticas.getSelectedItem().getValue();
//        bbPracticas.setValue(practica.getAbreviatura());
//        idPractica = practica.getId();
//        System.out.print("Practica escogida = " + idPractica);
//        bbPracticas.close();
//    }
//}

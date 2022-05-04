package ec.com.cubosoft.avamed.coneccion;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import ec.com.cubosoft.avamed.modelo.core.CsGrupos;
import ec.com.cubosoft.avamed.modelo.core.CsPerxgru;
import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
import ec.com.cubosoft.avamed.modelo.organizacion.Organizacion;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import ec.com.cubosoft.avamed.modelo.practica.*;
import ec.com.cubosoft.avamed.procesos.*;
import ec.com.cubosoft.avamed.modelo.medico.Area;
import ec.com.cubosoft.avasus.controller.renderder.AreaRenderer;
import ec.com.cubosoft.avasus.controller.renderder.OrganizacionRenderer;
import java.awt.Desktop;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.naming.NamingException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zul.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zkoss.util.media.AMedia;

/**
 *
 * @author Administrador
 */
public class ControladoraReporte extends GenericForwardComposer {

    private Integer idArea, idPractica;
    private Long idOrganizacion;
    //fechas
    SimpleDateFormat formato = new SimpleDateFormat("dd-MMM-yyyy", new Locale("es", "ES"));
    Button btnBuscar, btnReset, btnDividir;
    ProcesosSession admiSessionUsuario = new ProcesosSession();
    CsPerxgru permisosMenuIDControles = null;
    Textbox bCedula, bOrden, bHistoria;
    Combobox tip_archivo;
    Datebox FecHasta, FecDesde;
    Tree arbol;
    Iframe reporte;
    CsGrupos objGrupoActivo;
    CsUsuarios objUsuarioActivo;
    Window WinInformes;
    AdmiNegocio admNegocio;
    Listbox LbxPracticas, LbxAreas, LbxEmpresas;
    Bandbox bbAreas, bbPracticas, bbEmpresa;
    Treechildren root;
    String tipoReporte;
    Label TipoReporte;

    public void onCreate$WinReportes() {
        try {
            modificarSession();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private void modificarSession() {
        try {
            admiSessionUsuario.AgregarAtributoSession(4, page.getId(), session);
            permisosMenuIDControles = admiSessionUsuario.ObtenerPermisosPgina(session);
            objGrupoActivo = (CsGrupos) admiSessionUsuario.ObtenerAtributoSession(3, session);
            objUsuarioActivo = (CsUsuarios) admiSessionUsuario.ObtenerAtributoSession(2, session);
            admNegocio = new AdmiNegocio();
            loadXML();
            
            enableButtons();
            loadTipoArchivo();
            loadEmpresas();

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
    }

    private void loadXML() throws SQLException {
        ManejadoraXml admXml = new ManejadoraXml();
        Document doc = admXml.getDocumento("EsquemaReportes.xml", 0);
        String rootXML = doc.getDocumentElement().getNodeName();
        NodeList menuGroups = admXml.getlistanodos(rootXML, doc).item(0).
                getChildNodes();
        if (menuGroups.getLength() > 0) {
            loadTree(menuGroups);
        }
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/avasus");
            Connection con = ds.getConnection();
            System.out.println("El data sourcee" + con.getCatalog());   
            con.close();
        } catch (Exception e) {
            System.out.println("e" + e.getMessage());

        }

    }

    private void loadTree(NodeList menuNodeList) {
        Treeitem itemRoot = new Treeitem("(Reportes)");
        Treechildren childrenRoot = new Treechildren();
        Node menuNode;
        Element menuElement;
        for (int i = 0; i < menuNodeList.getLength(); i++) {
            menuNode = menuNodeList.item(i);
            if (menuNode.getNodeType() == Node.ELEMENT_NODE) {
                Treeitem itemRe = new Treeitem();
                menuElement = (Element) menuNode;
                Treerow rowReporte = new Treerow();
                if (menuElement.getNodeName() != null) {
                    if (menuElement.getAttributes().getNamedItem("show").getTextContent().equals("1")) {
                        Treecell cellReporte = new Treecell(menuElement.getTagName().toUpperCase());
                        System.out.println("El valor ref" + menuElement.getAttributes().getNamedItem("ref").getTextContent());
                        cellReporte.setId(menuElement.getAttributes().getNamedItem("ref").getTextContent());
                        cellReporte.addEventListener(Events.ON_CLICK, new EventListener() {

                            @Override
                            public void onEvent(Event event) throws Exception {
                                Treecell itemRootClicked = (Treecell) event.getTarget();
                                tipoReporte = itemRootClicked.getId().toString();
                                TipoReporte.setValue(itemRootClicked.getLabel().toString());

                            }
                        });
                        cellReporte.setParent(rowReporte);
                    }
                }
                rowReporte.setParent(itemRe);
                itemRe.setParent(childrenRoot);
            }
        }
        childrenRoot.setParent(itemRoot);
        itemRoot.setParent(root);
    }
//    private JasperReport report;
    String path;

    public void cargarReporte() throws SQLException, JRException {
        System.out.println("Cargando reporte");
        //path = "C:/glassfishv3/Reportes/";
        path = "C:/Users/JP3/Documents/glassfishv3/Reportes/";
        File tempFile = new File(path + "not.txt");
        String osName = System.getProperty("os.name");
        if (osName.toUpperCase().indexOf("WINDOWS") == 0) {
            //path = "C:/glassfishv3/Reportes/";
            path = "C:/Users/JP3/Documents/glassfishv3/Reportes/";
        } else if (osName.toUpperCase().indexOf("LINUX") == 0) {
            path = "/usr/share/glassfishv3/Reportes/";
        }
        System.out.print("el formato " + path);
        abrirReporte(path + tipoReporte + ".jrxml");
    }

    private Connection coneccionSQL() {
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/avasus");
            return ds.getConnection();
        } catch (Exception e) {
            System.out.println(e.getMessage());

            System.out.println("2e" + e.getMessage());
        }
        return null;
    }

//    private void cerrarConeccionSQL() {
//        try {
//            Context ctx = new InitialContext();
//            DataSource ds = (DataSource) ctx.lookup("jdbc/avasus");
//            ds.getConnection().close();
//
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            System.out.println("e" + e.toString());
//        }
//
//    }
    private void abrirReporte(String archivo) throws SQLException {
        try {
            System.out.print("abrir reporte");
            JasperReport report = JasperCompileManager.compileReport(archivo);
            Map parameters = new HashMap();
            if ((FecDesde.getValue() == null) && (FecHasta.getValue() == null)
                    || (idOrganizacion == null) && (tipoReporte.isEmpty())) {

                Messagebox.show("No existen condiciones de busqueda, por favor ponga alguna condición.",
                        "Filtro Vacío", Messagebox.OK, Messagebox.EXCLAMATION);

            } else {
                if (FecDesde.getValue().compareTo(FecHasta.getValue()) < 0) {
                    System.out.print("fecha menor");

                } else {
                    System.out.print("fecha menor");
                }
                System.out.print("datos de filtro " + FecDesde.getValue() + FecHasta.getValue() + idOrganizacion);
                parameters.put("empresa", idOrganizacion.intValue());
                parameters.put("fecha", FecDesde.getValue());
                parameters.put("fecha2", FecHasta.getValue());
                java.util.Locale locale = new Locale("es", "ES");
                parameters.put(JRParameter.REPORT_LOCALE, locale);

                JasperPrint print = JasperFillManager.fillReport(report, parameters, coneccionSQL());
//                JasperPrint print = null;
//
//                DataSource ds = null;
//                try {
//                    Context ctx = new InitialContext();
//                    //DataSource ds = (DataSource) ctx.lookup("jdbc/avasus");
//
//                    ds = (DataSource) ctx.lookup("jdbc/avasus");
//                    print = JasperFillManager.fillReport(report, parameters, ds.getConnection());
//                    //return ds.getConnection();
//                } catch (NamingException e) {
//                    System.out.println(e.getMessage());
//                    System.out.println("e" + e.toString());
//                } finally {
//                    ds.getConnection().close();
//                }

                byte[] buf = JasperExportManager.exportReportToPdf(print);
                if (buf != null) {
                    switch (tip_archivo.getValue()) {
                        case "PDF":
                            System.out.println("En PDF");
                            String fileName = "resultados.pdf";
                            InputStream mediaIS = new ByteArrayInputStream(buf);
                            AMedia media = new AMedia(fileName, "pdf", "application/pdf", mediaIS);
                            reporte.setContent(media);
                            reporte.setVisible(true);
                            break;
                        case "Excel":
                            System.out.println("En excel");
                            JRXlsExporter exporter = new JRXlsExporter();
                            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
                            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
                            exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                            exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                            exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                            exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, path + tipoReporte + ".xls");
                            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                            System.out.print("Generando el archivo en la direccion " + path + tipoReporte + ".xls");
                            exporter.exportReport();
                            File tempFile = new File(path + tipoReporte + ".xls");
                             {
                                try {
                                    FileInputStream inputStream;
                                    inputStream = new FileInputStream(tempFile);
                                    Filedownload.save(inputStream, new MimetypesFileTypeMap().getContentType(tempFile), tempFile.getName());
//                                  Runtime.getRuntime().exec("cmd /c start " + tempFile);
                                    String g = path + tipoReporte;
//                                  Filedownload.save(g+ ".xls", "application/xls");

                                } catch (IOException ex) {
                                    Logger.getLogger(ControladoraReporte.class.getName()).log(Level.SEVERE, null, ex);

                                }
                            }
                            break;
                        default:
                            System.out.println("En excel");
                            break;
                    }
                }
                //cerrarConeccionSQL();
            }
        } catch (JRException ex) {
            Logger.getLogger(ControladoraReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    List<XmlResultado> resultadosR;
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

    private void find() throws InterruptedException, NamingException {
        try {
            cargarReporte();
//
//        List<XmlResultado> ListaXmlResultados = getInformes();
//
//        //Limpia el Arbol
//        cleanTree();
//        arbol.setVisible(true);
//        if (ListaXmlResultados.size() > 0) {
//            loadTree(ListaXmlResultados);
//        }
        } catch (SQLException ex) {
            System.out.println("en find reporte");
            Logger.getLogger(ControladoraReporte.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException ex) {
            Logger.getLogger(ControladoraReporte.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    String nombreZip;

    public void reset() {
        FecDesde.setValue(new Date());
        FecHasta.setValue(new Date());
        bbEmpresa.setValue("");
        idOrganizacion = null;
        tipoReporte = null;
        reporte.setVisible(false);
        TipoReporte.setValue("");
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
        Map<String, Object> wSQL = new HashMap<>();
        wSQL.put("lockReg ?=", 0);
        List objectList = admNegocio.getData(table, wSQL, null, oSQL);
        LbxAreas.setItemRenderer(new AreaRenderer());
        LbxAreas.setModel(new ListModelList(objectList));
        bbAreas.setValue(null);
    }

    public void onSelect$LbxAreas() {
        Area area = (Area) LbxAreas.getSelectedItem().getValue();
        bbAreas.setValue(area.getDescripcion());
        idArea = area.getId();

        System.out.print("Area escogida = " + idArea);

        bbAreas.close();
    }

    public void loadEmpresas() throws NamingException {

        Object table = new Organizacion();
        List oSQL = new ArrayList();
        oSQL.add("abreviatura");

        List objectList = admNegocio.getData(table, false, oSQL);

        LbxEmpresas.setItemRenderer(new OrganizacionRenderer());
        LbxEmpresas.setModel(new ListModelList(objectList));

        bbEmpresa.setValue(null);
    }

    public void onSelect$LbxEmpresas() {
        Organizacion empresa = (Organizacion) LbxEmpresas.getSelectedItem().getValue();
        bbEmpresa.setValue(empresa.getAbreviatura());
        idOrganizacion = empresa.getId();

        System.out.print("Empresa escogida = " + idOrganizacion);

        bbEmpresa.close();
    }

    public void loadTipoArchivo() throws NamingException {

        tip_archivo.appendItem("Excel");
        tip_archivo.appendItem("PDF");
//        tip_archivo.appendItem("Word");
        tip_archivo.setSelectedIndex(0);
    }

    public void onSelect$LbxPracticas() {
        NombreP practica = (NombreP) LbxPracticas.getSelectedItem().getValue();
        bbPracticas.setValue(practica.getAbreviatura());
        idPractica = practica.getId();
        System.out.print("Practica escogida = " + idPractica);
        bbPracticas.close();
    }
}

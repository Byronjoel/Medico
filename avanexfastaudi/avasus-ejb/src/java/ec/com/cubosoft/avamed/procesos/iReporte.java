package ec.com.cubosoft.avamed.procesos;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import ec.com.cubosoft.avamed.jpa.AdministradorGlobalBean;
import ec.com.cubosoft.avamed.modelo.persona.ResultadoGrafico;
import ec.com.cubosoft.avamed.modelo.practica.FormatoXPractica;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import ec.com.cubosoft.avamed.modelo.organizacion.ImgLogo;
import ec.com.cubosoft.avamed.modelo.practica.ImgDigital;
import ec.com.cubosoft.avamed.modelo.vistas.Resultado;
import ec.com.cubosoft.avamed.utilities.UtilPdf;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.util.JRXmlUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

/**
 *
 * @author Ruben Quipo
 * @author Juan Pablo Chavez
 *
 */
public class iReporte {

    private List<XmlResultado> listaXmlResultados = null;
    private List<Resultado> listaResultados = null;
    private List<FormatoXPractica> listaFormatoPractica = null;
    private List<ResultadoGrafico> listaResultadoGrafico = null;
    private AdministradorGlobalBean admJPAG;
    private JasperReport jreport = null;
    private JasperPrint jprint = null;
    private PdfCopyFields copiarPDF = null;
    private Integer numHistoria;
    private Long numOrden;
    private Integer numPractica;
    private Integer numImagen;
    private Integer numFirmaMed;
    private boolean creadoPDF = true;
    private String XmlResultado = null;
    private String jrxmlFormato = null;
    private String ubicacion = null;
    private String ubicacionPDF = null;
    private List nombreImagen;// = new ArrayList();
    private List nombreFirma;
    private byte[] bytes = null;
    private byte[] bytesPDF = null;
    private List<byte[]> listaPDF = null;
    private Context contexto;
    private List<byte[]> listaPDFR = null;

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="firmas formato">
    private void consultarfirma(String idUsuMedico) throws NamingException, IOException {
        nombreFirma = new ArrayList();
        AdmiNegocio admNegocio = new AdmiNegocio();
        Map<String, Object> wSQL = new HashMap<String, Object>();
        wSQL.put("usuario ?=", idUsuMedico);
        wSQL.put("perFirma ?=", 0);
        List oSQL = new ArrayList();
        List imgUsuario = admNegocio.getData(new ImgDigital(), wSQL, null, oSQL);
        if ((imgUsuario != null)) {
            if (imgUsuario.size() == 1) {
                ImgDigital firma = (ImgDigital) imgUsuario.get(0);
                if (firma.getPerFirma() == 0) {
                    crearFirma(firma.getFirma(), firma.getUsuario(), ".png");
                    nombreFirma.add(firma.getUsuario() + ".png");
                }
            }
        }

    }

    private void crearFirma(byte[] firma, String nombreImagen, String formatoImagen) throws IOException {
        if (firma.length > 0) {
            BufferedImage img = null;
            FileOutputStream fileOuputStream = new FileOutputStream(getUbicacion() + nombreImagen + formatoImagen);
            fileOuputStream.write(firma);
            fileOuputStream.close();
        } else {
            throw new RuntimeException("El dato recibido para crear un grafico es vacio");
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="ESTANDAR">
    public JasperReport getJreport() {
        return jreport;
    }

    public void setJreport(JasperReport jreport) {
        this.jreport = jreport;
    }

    public PdfCopyFields getCopiarPDF() {
        return copiarPDF;
    }

    public void setCopiarPDF(PdfCopyFields copiarPDF) {
        this.copiarPDF = copiarPDF;
    }

    public JasperPrint getJprint() {
        return jprint;
    }

    public void setJprint(JasperPrint jprint) {
        this.jprint = jprint;
    }

    private byte[] getBytes() {
        return bytes;
    }

    private void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    // </editor-fold>
    public Integer getNumFirmaMed() {
        return numFirmaMed;
    }

    public void setNumFirmaMed(Integer numFirmaMed) {
        this.numFirmaMed = numFirmaMed;
    }

    public iReporte() {
        try {
            crearDirectorio();
            contexto = new InitialContext();
            admJPAG = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        } catch (NamingException ex) {
            Logger.getLogger(iReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public byte[] getReport(List<XmlResultado> resultados, boolean forceVisible, boolean marca)
            throws NamingException, IOException, DocumentException, DocumentException, UnsupportedEncodingException, JRException {
        listaPDF = new ArrayList<>();
        listaXmlResultados = new ArrayList<>();
        listaXmlResultados.clear();
        listaXmlResultados = resultados;
        return loadReport(forceVisible, marca);
    }

    private byte[] loadReport(boolean forceVisible, Boolean marca) throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException {
        int ingreso = 0;
        boolean incompleto = true;

        for (XmlResultado xml : listaXmlResultados) {
            if (xml.getEstado().equals("CO") || xml.getEstado().equals("AU") || xml.getEstado().equals("AR")) {
                incompleto = false;
            } else {
                incompleto = true;
            }
            if (xml.getResultado().equalsIgnoreCase("PDF")) {
                consultarImagen(xml.getId().toString());
            } else {
                if ((xml.getEstado().equals("CO") || xml.getEstado().equals("AU") || xml.getEstado().equals("AR")) || (forceVisible)) {
                    if (xml.getPractica().getCodRef() != null) {
                        if ((xml.getPractica().getCodRef() != 0)) {
                            setNumPractica(xml.getPractica().getCodRef());
                        } else {
                            setNumPractica(xml.getPractica().getId());
                        }
                    } else {
                        setNumPractica(xml.getPractica().getId());
                    }
                    if (xml.getNroOrd() != null) {
                        setNumOrden(xml.getNroOrd());
                    } else {
                        setNumOrden(xml.getNroOrd());
                    }

                    if (isCreadoPDF()) {
                        instanciarPDF();
                    }
                    consultarImagen(xml.getId().toString());
                    if (xml.getMedicos().getUsuarios() != null) {
                        consultarfirma(xml.getMedicos().getUsuarios().getUsuario());
                    }
                    setXmlResultado(xml.getResultado());
                    System.out.println("buscar jasper " + xml.getPractica().getDescripcion());
                    buscarJrxml(getNumPractica(), getCopiarPDF(), xml, xml.getEstado(), marca);
                    ingreso++;
                }
            }
        }
        if (ingreso > 0) {
            System.out.println("ingreso >0");
            if (getListaPDF().size() > 0) {
                for (int i = 0; i < getListaPDF().size(); i++) {
                    PdfReader pdfReader = new PdfReader(getListaPDF().get(i));
                    getCopiarPDF().addDocument(pdfReader);
                }
            }
            getCopiarPDF().close();
            getBytesDocumento();
            System.out.println(getUbicacionPDF() + " " + getUbicacion());
            EliminarFichero(getUbicacionPDF());
            if ((nombreFirma != null) && (!(nombreFirma.isEmpty()))) {
                for (int i = 0; i < nombreFirma.size(); i++) {
                    EliminarFichero(getUbicacion() + nombreFirma.get(i));
                }
            }
            for (int j = 0; j < nombreImagen.size(); j++) {
                EliminarFichero(getUbicacion() + nombreImagen.get(j));
            }
        }
        if (ingreso == 0) {
            System.out.println("ingreso =0");
            if (isCreadoPDF()) {
                instanciarPDF();
            }
            if (getListaPDF().size() > 0) {
                for (int i = 0; i < getListaPDF().size(); i++) {
                    PdfReader pdfReader = new PdfReader(getListaPDF().get(i));
                    getCopiarPDF().addDocument(pdfReader);
                }
            }
            getCopiarPDF().close();
            getBytesDocumento();
        }
        return getBytes();
    }

    public byte[] imprimirReporte(String idHistoria, String idPractica, String idOrden, String IdMedico, String empresa, Date fechaInicio, Date fechaFin, boolean visibleEstado, boolean marca)
            throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException {
        listaPDF = new ArrayList<>();
        listaXmlResultados = admJPAG.getXmlResultado(idHistoria, idPractica, idOrden, IdMedico, empresa, fechaInicio, fechaFin);
        return loadReport(visibleEstado, marca);
    }

    private void crearPDF(int hojaJRxml, String idPractica, PdfCopyFields copiar, String estado, Boolean marca)
            throws DocumentException, JRException, IOException {
        Document documentoXML = JRXmlUtils.parse(new InputSource(new StringReader(getXmlResultado())));
        PdfReader pdfReader = null;
        Map parametros = new HashMap();
        for (int i = 1; i <= hojaJRxml - 1; i++) {
            try {
                setJreport(JasperCompileManager.compileReport(getUbicacion() + "jrxml_P" + idPractica + "_O" + getNumOrden() + "_" + i + ".jrxml"));
            } catch (Exception e) {
                System.out.println("en  Jreport " + getUbicacion() + idPractica + getNumOrden() + " error" + e);
                System.out.println(e.getCause());
            }
            JRXmlDataSource ds;
            if ((idPractica.equals("415")) || (idPractica.equals("1111"))) {
                ds = new JRXmlDataSource(documentoXML, "/resultados/registro_resultado");
            } else {
                ds = new JRXmlDataSource(documentoXML, "resultados");
            }
            try {
                setJprint(JasperFillManager.fillReport(getJreport(), parametros, ds));
            } catch (Exception e) {
                System.out.println("en  setJprint " + e);
            }
            try {
                setBytes(JasperExportManager.exportReportToPdf(getJprint()));
            } catch (Exception e) {

                System.out.println("en  setBytes  " + e);
            }
            if (estado.equalsIgnoreCase("IN")) {
                setBytes(UtilPdf.marcaDeAgua(getBytes(), "INCOMPLETO", "Informe Incompleto", "SOLO USO INTERNO"));
            } else {
                if (estado.equalsIgnoreCase("CO") && (!marca)) {
                    setBytes(UtilPdf.marcaDeAgua(getBytes(), "COMPLETO", "Informe proceso auditoria", "SOLO USO INTERNO"));
                } else if (estado.equalsIgnoreCase("AR") && (!marca)) {
                    setBytes(UtilPdf.marcaDeAgua(getBytes(), "ARCHIVADO", "Informe Archivado", "SOLO USO INTERNO"));
                } else if (estado.equalsIgnoreCase("CO") && (marca)) {
                    System.out.println("Impresion en completo con permiso");
                } else if (estado.equalsIgnoreCase("AR") && (marca)) {
                    System.out.println("Impresion en archivado con permiso");
                }
            }
            pdfReader = new PdfReader(getBytes());
            copiar.addDocument(pdfReader);
            System.out.println("Numero de formato impreso: jrxml_P" + idPractica + "_O" + getNumOrden() + "_" + i + ".jrxml");
        }
    }

    private void getBytesDocumento() throws IOException {
        FileInputStream fis = new FileInputStream(getUbicacionPDF());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        for (int a; (a = fis.read(buf)) != -1;) {
            bos.write(buf, 0, a);
        }
        setBytes(bos.toByteArray());
    }

    private List<FormatoXPractica> obtenerListaFormatos(List<String> idsFormatos, Integer idPractica)
            throws NamingException {
        List<FormatoXPractica> listaFormatosPracticas = new ArrayList<FormatoXPractica>();
        FormatoXPractica objnuevo;
        admJPAG = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        for (int i = 0; i < idsFormatos.size(); i++) {
            objnuevo = admJPAG.getTodosDosParametros("FormatoXPractica", "idPractica", idPractica.toString(), "id", idsFormatos.get(i), Integer.class, Long.class, "idHoja");
            listaFormatosPracticas.add(objnuevo);
        }
        return listaFormatosPracticas;
    }

    private void crearJRxml(String Formato, String nombre, String extension)
            throws UnsupportedEncodingException, FileNotFoundException, IOException {
        String dir = getUbicacion() + nombre + extension;
        File archivo = new File(dir);
        if (archivo.exists()) {
            archivo.delete();
        }
        BufferedWriter escribir = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo), "UTF8"));
        escribir.write(Formato);
        escribir.close();
    }

    private void buscarJrxml(Integer idPractica, PdfCopyFields copiar, XmlResultado xml, String estado, Boolean marca)
            throws NamingException, UnsupportedEncodingException, DocumentException, JRException, IOException {

        int hojaJRxml = 1;
        String cambio = null;
        //   System.out.print("Preparando Orden " + xml.getOrden() + " / Practica: " + idPractica.toString() + " - " + xml.getPractica().getAbreviatura());
        ManejadoraXml admiManejadoraXml = new ManejadoraXml();
        List<String> idsFormatos = admiManejadoraXml.obtenerIdFormato(xml.getResultado());

        if ((idsFormatos != null) && (idsFormatos.size()) > 0) {
            listaFormatoPractica = obtenerListaFormatos(idsFormatos, idPractica);
        } else {
            admJPAG = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
            listaFormatoPractica = admJPAG.getTodosXParametro("FormatoXPractica", "idPractica", idPractica.toString(), Integer.class, "idHoja", false);
        }
        for (FormatoXPractica fp : listaFormatoPractica) {
            if (fp == null) {
                throw new RuntimeException("\n\nFormato(s) para Orden " + xml.getNroOrd()
                        + "\nPrÃ¡ctica: " + idPractica.toString() + " - "
                        + xml.getPractica().getAbreviatura() + "\nCÃ³digo(s): "
                        + idsFormatos.toString() + " no se han encontrado.\n\n Comunique a su administrador el problema");
            } else {
                String XML = new String(fp.getXml(), "UTF-8");

                cambio = cambiarUbicacionImagen(XML);
                crearJRxml(cambio, "jrxml_P" + idPractica + "_O" + getNumOrden() + "_" + hojaJRxml, ".jrxml");
                hojaJRxml++;
            }
        }
        crearPDF(hojaJRxml, idPractica.toString(), copiar, estado, marca);

    }

    private void consultarImagen(String idXml) throws NamingException, IOException {
        nombreImagen = new ArrayList();
        admJPAG = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        listaResultadoGrafico = admJPAG.getTodosXParametro("ResultadoGrafico", "idXmlResultado", idXml, Integer.class, "id", false);
        int numImage = listaResultadoGrafico.size();
        setNumImagen(numImage);
        if (numImage >= 1) {
            for (ResultadoGrafico res : listaResultadoGrafico) {
                if ((res.getDato() != null) && (res.getDato().length > 0)) {
                    if ((res.getCod().equalsIgnoreCase("PDF") || (res.getCod().equalsIgnoreCase("DIG")))) {
                        setBytesPDF(res.getDato());
                        setListaPDF(getBytesPDF());
                    } else {
                        crearImagen(res.getDato(), res.getCod() + "_P" + getNumPractica() + "_O" + getNumOrden() + "_X" + res.getIdXmlResultado(), ".png");
                        nombreImagen.add(res.getCod() + "_P" + getNumPractica() + "_O" + getNumOrden() + "_X" + res.getIdXmlResultado() + ".png");
                    }
                } else {
                    System.err.print("Imagenes solicitadas para renderizar son nulas para Id:" + idXml);
                }
            }
        }
    }

    private void crearImagen(byte[] arregloBits, String nombreImagen, String formatoImagen) throws IOException {

        if (arregloBits.length > 0) {
            InputStream is = new ByteArrayInputStream(arregloBits);
            BufferedImage imagen = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
            imagen = ImageIO.read(is);
            ImageIO.write(imagen, "png", new File(getUbicacion() + nombreImagen + formatoImagen));
        } else {
            throw new RuntimeException("El dato recibido para crear un grafico es vacio");
        }

    }

    private void crearDirectorio() {
        String dir = new File(System.getProperty("user.dir")).getAbsolutePath();
        System.out.println("crear Directorio " + dir + "/tmp");
        File file = new File(dir + "/tmp");

        if (!file.exists()) {
            file.mkdirs();
        }

        setUbicacion(dir + "/tmp/");
    }

    public String cambiarUbicacionImagen(String contenido) {
        String resultado = null;
        String path = iReport.class.getResource("iReport.class").getPath();
        path = path.substring(path.indexOf("/") + 1, path.indexOf("WEB-INF"));
        String nuevoPath;
        String osName = System.getProperty("os.name");
        if (osName.toUpperCase().indexOf("LINUX") == 0) {
            nuevoPath = "/" + path + "resources/logosl/";
        } else {
            nuevoPath = path + "resources/logos/";
        }

//        System.out.println("nuevo path " + nuevoPath);
        String derecho = "", izquierdo = "", logoPetrex = "", logoBanco = "", firmaMed = "";
        String reemplazo = getUbicacion();
        StringBuilder cadena = new StringBuilder();
        String a = "\\";
        char[] b = a.substring(0, 1).toCharArray();
        char d;
        for (int i = 0; i < reemplazo.length(); i++) {
            d = reemplazo.charAt(i);
            if (reemplazo.charAt(i) == b[0]) {
                d = '/';
            }
            cadena.append(d);
        }
        if (nombreImagen != null) {
            if (nombreImagen.size() == 2) {
                if (nombreImagen.get(0).toString().contains("AU-OD_")) {
                    derecho = cadena.toString() + nombreImagen.get(0);
                    System.out.println(" path  derecho" + derecho);
                } else if (nombreImagen.get(0).toString().contains("AU-OI_")) {
                    izquierdo = cadena.toString() + nombreImagen.get(0);
                    System.out.println(" path  derecho" + derecho);
                }
                if (nombreImagen.get(1).toString().contains("AU-OD_")) {
                    derecho = cadena.toString() + nombreImagen.get(1);
                    System.out.println(" path  derecho" + derecho);
                } else if (nombreImagen.get(1).toString().contains("AU-OI_")) {
                    System.out.println(" path  derecho" + derecho);
                    izquierdo = cadena.toString() + nombreImagen.get(1);
                }
            } else if (nombreImagen.size() < 2) {
                if (nombreImagen.isEmpty()) {
                    derecho = nuevoPath + "blanco.png";
                    izquierdo = nuevoPath + "blanco.png";
                }
                if (nombreImagen.size() == 1) {
                    derecho = nuevoPath + "blanco.png";
                    izquierdo = cadena.toString() + nombreImagen.get(0);
                }
            }
        }
        if ((nombreFirma != null) && (!(nombreFirma.isEmpty()))) {
            if (nombreFirma.size() > 0) {
                System.out.println(" path  firma" + firmaMed);
                firmaMed = cadena.toString() + nombreFirma.get(0);
            } else {
                firmaMed = nuevoPath + "blancoFirma.png";
            }
        } else {
            firmaMed = nuevoPath + "blancoFirma.png";
        }
        logoPetrex = nuevoPath + "logopetrex.jpg";
        logoBanco = nuevoPath + "bancopichincha.jpg";
        resultado = contenido.replaceAll("/tmp/", nuevoPath);
        resultado = resultado.replaceAll("logoPetrex", logoPetrex);
        resultado = resultado.replaceAll("logoBanco", logoBanco);
        resultado = resultado.replaceAll("imagenD", derecho);
        resultado = resultado.replaceAll("imagenI", izquierdo);
        resultado = resultado.replaceAll("firmaMed", firmaMed);
        osName = System.getProperty("os.name");
        if (osName.toUpperCase().indexOf("LINUX") == 0) {
            try {
                String g = "C:\\";
                int pat = resultado.indexOf(g);
                while (pat > 0) {
                    String pati1 = resultado.substring(0, pat);
                    String pati2 = "/usr/share/glassfishv3/logos/";
                    String pati3 = resultado.substring(pat + 24, resultado.length());
                    resultado = pati1 + pati2 + pati3;
                    pat = resultado.indexOf(g);
                }
            } catch (Exception e) {
                System.out.println("reemplazo" + e.getMessage());
            }
        } else {
        }

        return resultado;
    }

    private void instanciarPDF() throws DocumentException, FileNotFoundException {
        String dirPDF = getUbicacion() + "Instanciando Reporte_P" + getNumPractica() + "_O" + getNumOrden() + ".pdf";
        System.out.println("isntancia pdf" + dirPDF);
        PdfCopyFields copiar = new PdfCopyFields(new FileOutputStream(dirPDF));
        setCopiarPDF(copiar);
        setCreadoPDF(false);
        setUbicacionPDF(dirPDF);
    }

    private void EliminarFichero(String fichero) {
        File archivo = new File(fichero);
        System.out.println("Eliminar Archivo");
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    /**
     *
     * @return
     */
    public String getXmlResultado() {
        return XmlResultado;
    }

    /**
     *
     * @param XmlResultado
     */
    public void setXmlResultado(String XmlResultado) {
        this.XmlResultado = XmlResultado;
    }

    /**
     *
     * @return
     */
    public String getJrxmlFormato() {
        return jrxmlFormato;
    }

    /**
     *
     * @param jrxmlFormato
     */
    public void setJrxmlFormato(String jrxmlFormato) {
        this.jrxmlFormato = jrxmlFormato;
    }

    /**
     *
     * @return
     */
    public Integer getNumHistoria() {
        return numHistoria;
    }

    /**
     *
     * @param numHistoria
     */
    public void setNumHistoria(Integer numHistoria) {
        this.numHistoria = numHistoria;
    }

    private Integer getNumPractica() {
        return numPractica;
    }

    private void setNumPractica(Integer numPractica) {
        this.numPractica = numPractica;
    }

    public Long getNumOrden() {
        return numOrden;
    }

    public void setNumOrden(Long numOrden) {
        this.numOrden = numOrden;
    }

    public boolean isCreadoPDF() {
        return creadoPDF;
    }

    public void setCreadoPDF(boolean creadoPDF) {
        this.creadoPDF = creadoPDF;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public List getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(List nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public Integer getNumImagen() {
        return numImagen;
    }

    public void setNumImagen(Integer numImagen) {
        this.numImagen = numImagen;
    }

    public String getUbicacionPDF() {
        return ubicacionPDF;
    }

    public void setUbicacionPDF(String ubicacionPDF) {
        this.ubicacionPDF = ubicacionPDF;
    }

    public byte[] getBytesPDF() {
        return bytesPDF;
    }

    public void setBytesPDF(byte[] bytesPDF) {
        this.bytesPDF = bytesPDF;
    }

    public List<byte[]> getListaPDF() {
        return listaPDF;
    }

    public void setListaPDF(byte[] BytesPdf) {
        this.listaPDF.add(BytesPdf);
    }

    public List<byte[]> getListaPDFR() {
        return listaPDFR;
    }

    public void setListaPDFR(byte[] BytesPdf) {
        this.listaPDFR.add(BytesPdf);
    }
}

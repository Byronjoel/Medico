package ec.com.cubosoft.avamed.procesos;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import ec.com.cubosoft.avamed.jpa.AdministradorGlobalBean;
import ec.com.cubosoft.avamed.modelo.nextla.Lisord;
import ec.com.cubosoft.avamed.modelo.nextla.LispetAvanex;
import ec.com.cubosoft.avamed.modelo.persona.Receta;
import ec.com.cubosoft.avamed.modelo.persona.ResultadoGrafico;
import ec.com.cubosoft.avamed.modelo.practica.FormatoXPractica;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import ec.com.cubosoft.avamed.modelo.practica.FormatoXAntecedentes;
import ec.com.cubosoft.avamed.modelo.organizacion.ImgLogo;
import ec.com.cubosoft.avamed.modelo.persona.XmlAntecedentes;
import ec.com.cubosoft.avamed.modelo.practica.ImgDigital;
import ec.com.cubosoft.avamed.modelo.publico.AudImagen;
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
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
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
import javax.swing.JOptionPane;
import net.cubosoft.common.soap.PdfResponse;
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
public class iReport {

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
    private Integer numEmpresa;
    private Integer numEmpresaRef;
    private Integer numImagen;
    private Integer numFirmaMed;
    private boolean creadoPDF = true;
    private String XmlResultado = null;

    private String jrxmlFormato = null;
    private String ubicacion = null;
    private String ubicacionPDF = null;
    private List nombreImagen;// = new ArrayList();
    private List nombreFirma;
    private List nombreLogo;
    private byte[] bytes = null;
    private byte[] bytesPDF = null;
    private List<byte[]> listaPDF = null;
    private Context contexto;
    private List ListapedidosN;
    private LispetAvanex objOrdenN;

    //urgente
    private List ListOrdUrg;

    //<editor-fold defaultstate="collapsed" desc="ANTECEDENTES">
    private List<XmlAntecedentes> listaAntecedente = null;
    private List<FormatoXAntecedentes> listaFormatoAntecedentes = null;
    private String XmlAntecedentes = null;

    public String getXmlAntecedentes() {
        return XmlAntecedentes;
    }

    public void setXmlAntecedentes(String XmlAntecedentes) {
        this.XmlAntecedentes = XmlAntecedentes;
    }

    private List<FormatoXAntecedentes> obtenerFormatosAntecedentes(List<String> idsFormatos, Integer idEmpresa) throws NamingException {
        List<FormatoXAntecedentes> listaFormatosAnte = new ArrayList<FormatoXAntecedentes>();
        FormatoXAntecedentes objnuevo;
        admJPAG = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        for (int i = 0; i < idsFormatos.size(); i++) {
            objnuevo = admJPAG.getTodoParametros("FormatoXAntecedentes", "idEmpresa", idEmpresa.toString(), "id", idsFormatos.get(i), Integer.class, Long.class, "idHoja");
            if (objnuevo != null) {
                listaFormatosAnte.add(objnuevo);
            }
        }
        return listaFormatosAnte;
    }

    private void buscarJrxmlAnt(Integer idEmprea, PdfCopyFields copiar, XmlAntecedentes ant, String estado, Boolean marca)
            throws NamingException, UnsupportedEncodingException, DocumentException, JRException, IOException {
        int hojaJRxml = 1;
        String cambio = null;
        ManejadoraXml admiManejadoraXml = new ManejadoraXml();
        List<String> idsFormatos = admiManejadoraXml.obtenerIdFormato(ant.getAntecedentes());
        if ((idsFormatos != null) && (idsFormatos.size()) > 0) {
            listaFormatoAntecedentes = obtenerFormatosAntecedentes(idsFormatos, idEmprea);
            if (listaFormatoAntecedentes.isEmpty()) {
                if (numEmpresaRef != null) {
                    if (numEmpresaRef > 0) {
                        listaFormatoAntecedentes = obtenerFormatosAntecedentes(idsFormatos, numEmpresaRef);
                    } else {
                        listaFormatoAntecedentes = obtenerFormatosAntecedentes(idsFormatos, idEmprea);
                    }
                } else {
                    listaFormatoAntecedentes = obtenerFormatosAntecedentes(idsFormatos, idEmprea);
                }
            }
        } else {
            admJPAG = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
            listaFormatoAntecedentes = admJPAG.getTodosXParametro("FormatoXAntecedentes", "idEmpresa", idEmprea.toString(), Integer.class, "idHoja", false);
        }
        for (FormatoXAntecedentes fp : listaFormatoAntecedentes) {
            if (fp == null) {
                throw new RuntimeException("\n\nFormato(s) para Orden " + ant.getIdHistoria()
                        + "\nPrÃ¡ctica: " + idEmprea.toString() + " - "
                        + ant.getEmpresa() + "\nCÃ³digo(s): "
                        + idsFormatos.toString() + " no se han encontrado.\n\n Comunique a su administrador el problema");
            } else {
                String XML = new String(fp.getXml(), "UTF-8");
                cambio = cambiarUbicacionImagen(XML);
                crearJRxml(cambio, "jrxml_P" + idEmprea + "_O" + hojaJRxml, ".jrxml");
                hojaJRxml++;
            }
        }
        crearPDFAnte(hojaJRxml, idEmprea.toString(), copiar, estado, marca);
    }

    private void crearPDFAnte(int hojaJRxml, String idEmpresa, PdfCopyFields copiar, String estado, Boolean marca)
            throws DocumentException, JRException, IOException {
        Document documentoXML = JRXmlUtils.parse(new InputSource(new StringReader(getXmlAntecedentes())));
        PdfReader pdfReader = null;
        Map parametros = new HashMap();
        for (int i = 1; i <= hojaJRxml - 1; i++) {
            try {
                setJreport(JasperCompileManager.compileReport(getUbicacion() + "jrxml_P" + idEmpresa + "_O" + i + ".jrxml"));
            } catch (Exception e) {
                System.out.println("en  Jreport " + getUbicacion() + idEmpresa + " error" + e);
                System.out.println(e.getCause());
            }
            JRXmlDataSource ds;
            ds = new JRXmlDataSource(documentoXML, "resultados");
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
            pdfReader = new PdfReader(getBytes());
            copiar.addDocument(pdfReader);
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="RECETA">
    private List<Receta> listaReceta = null;
    private String XmlReceta = null;
    private List<byte[]> listaPDFR = null;

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
            AdmiNegocio admi = new AdmiNegocio();
            pdf = (ResultadoGrafico) admi.guardar(pdf);

            //System.out.println("pdf"+pdf);
            if (pdf != null) {
                Map<String, Object> wSQL = new HashMap<String, Object>();
                wSQL.put("id ?=", ris.getId());
                List objectList = admi.getData(new XmlResultado(), wSQL, null, null);
                if (objectList.size() == 1) {
                    XmlResultado resul = (XmlResultado) objectList.get(0);
                    resul.setResultado("PDF");
                    if (admi.actualizar(resul)) {
                        return true;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Verificar estado Informe ext(722)");
                    // Messagebox.show("Verificar estado Informe ext(722)", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
                    return false;
                }
            } else {
                JOptionPane.showMessageDialog(null, "No se puede recuperar informe");
                //Messagebox.show("No se puede recuperar informe", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
                return false;
            }
        } //            String dirPDF = getUbicacion() + "Ris.pdf";
        catch (Exception e) {
            System.out.println("ec.com.cubosoft.avamed.procesos.iReport.consultarRis()");
        }
        return false;
    }

    public byte[] getRecetaXml(Map<String, Object> wSQL, Long idReceta)
            throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException, Exception {
        listaReceta = admJPAG.getXmlReceta(idReceta);
        listaPDFR = new ArrayList<>();
        if (listaReceta.isEmpty()) {
            return null;
        } else {
            return loadRecetaXml();
        }
    }

    private byte[] loadRecetaXml() throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException {
        int ingreso = 0;
        boolean incompleto = true;

        for (Receta xml : listaReceta) {

            setNumPractica(xml.getIdPractica());

            setNumOrden(xml.getIdOrden().longValue());

            if (isCreadoPDF()) {
                instanciarRecetaPDF();

            }
            if (xml.getUsuario() != null) {
                consultarfirma(xml.getUsuario());
            }
            setXmlReceta(xml.getTexto());
            buscarJrxmlReceta(1166, getCopiarPDF(), xml);
            ingreso++;
        }
        if (ingreso > 0) {
            if (getListaPDFR().size() > 0) {
                for (int i = 0; i < getListaPDFR().size(); i++) {
                    PdfReader pdfReader = new PdfReader(getListaPDFR().get(i));
                    getCopiarPDF().addDocument(pdfReader);
                }
            }
            getCopiarPDF().close();
            getBytesDocumento();
            EliminarFichero(getUbicacionPDF());
            if ((nombreFirma != null) && (!(nombreFirma.isEmpty()))) {
                for (Object nombreFirma1 : nombreFirma) {
                    EliminarFichero(getUbicacion() + nombreFirma1);
                }
            }
            if ((nombreLogo != null) && (!(nombreLogo.isEmpty()))) {
                for (Object nombreFirma1 : nombreLogo) {
                    EliminarFichero(getUbicacion() + nombreFirma1);
                }
            }
        }
        if (ingreso == 0) {
            if (isCreadoPDF()) {
                instanciarRecetaPDF();
            }
            if (getListaPDFR().size() > 0) {
                for (int i = 0; i < getListaPDFR().size(); i++) {
                    PdfReader pdfReader = new PdfReader(getListaPDFR().get(i));
                    getCopiarPDF().addDocument(pdfReader);
                }
            }
            getCopiarPDF().close();
            getBytesDocumento();
        }
        return getBytes();
    }

    private void buscarJrxmlReceta(Integer idPractica, PdfCopyFields copiar, Receta xml)
            throws NamingException, UnsupportedEncodingException, DocumentException, JRException, IOException {
        int hojaJRxml = 1;
        String cambio = null;
        ManejadoraXml admiManejadoraXml = new ManejadoraXml();
        List<String> idsFormatos = admiManejadoraXml.obtenerIdFormato(xml.getTexto());
        if ((idsFormatos != null) && (idsFormatos.size()) > 0) {
            listaFormatoPractica = obtenerListaFormatos(idsFormatos, idPractica);
        } else {
            admJPAG = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
            listaFormatoPractica = admJPAG.getTodosXParametro("FormatoXPractica", "idPractica", idPractica.toString(), Integer.class, "idHoja", true);
        }
        for (FormatoXPractica fp : listaFormatoPractica) {
            if (fp == null) {
                throw new RuntimeException("\n\nFormato(s) para Orden " + xml.getIdOrden()
                        + "\nPrÃ¡ctica: " + idPractica.toString() + " - "
                        + idsFormatos.toString() + " no se han encontrado.\n\n Comunique a su administrador el problema");
            } else {
                String XML = new String(fp.getXml(), "UTF-8");

                cambio = cambiarUbicacionImagen(XML);
                crearRecetaJRxml(cambio, "jrxml_P" + idPractica + "_O" + getNumOrden() + "_" + hojaJRxml, ".jrxml");
                hojaJRxml++;
            }
        }
        crearReceta(hojaJRxml, idPractica.toString(), copiar);

    }

    private void crearRecetaJRxml(String Formato, String nombre, String extension)
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

    private void crearReceta(int hojaJRxml, String idPractica, PdfCopyFields copiar)
            throws DocumentException, JRException, IOException {
        Document documentoXML = JRXmlUtils.parse(new InputSource(new StringReader(getXmlReceta())));
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
            pdfReader = new PdfReader(getBytes());
            copiar.addDocument(pdfReader);
        }
    }

    private void instanciarRecetaPDF() throws DocumentException, FileNotFoundException {
        String dirPDF = getUbicacion() + "Instanciando Receta_P" + getNumPractica() + "_O" + getNumOrden() + ".pdf";
        PdfCopyFields copiar = new PdfCopyFields(new FileOutputStream(dirPDF));
        setCopiarPDF(copiar);
        setCreadoPDF(false);
        setUbicacionPDF(dirPDF);
    }

    public void setXmlReceta(String XmlReceta) {
        this.XmlReceta = XmlReceta;
    }

    public String getXmlReceta() {
        return XmlReceta;
    }

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

    private void consultarLogo(BigInteger idOrganizacion) throws NamingException, IOException {
        nombreLogo = new ArrayList();
        AdmiNegocio admNegocio = new AdmiNegocio();
        Map<String, Object> wSQL = new HashMap<String, Object>();
        wSQL.put("idEmpresa ?=", idOrganizacion);
        wSQL.put("perLogo ?=", 0);
        wSQL.put("lockReg ?=", 0);
        List oSQL = new ArrayList();
        List imgLogo = admNegocio.getData(new ImgLogo(), wSQL, null, oSQL);
        if ((imgLogo != null)) {
            if (imgLogo.size() == 1) {
                ImgLogo logos = (ImgLogo) imgLogo.get(0);
                if (logos.getPerLogo() == 0) {
                    crearLogo(logos.getLogo(), logos.getIdEmpresa().toString(), ".png");
                    nombreLogo.add(logos.getIdEmpresa() + ".png");
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

    private void crearLogo(byte[] logo, String nombreImagen, String formatoImagen) throws IOException {
        if (logo.length > 0) {
            BufferedImage img = null;
            FileOutputStream fileOuputStream = new FileOutputStream(getUbicacion() + nombreImagen + formatoImagen);
            fileOuputStream.write(logo);
            fileOuputStream.close();
        } else {
            throw new RuntimeException("El dato recibido para crear un grafico es vacio");
        }
    }

    // </editor-fold>
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

    private void buscarJrxmlXml(Integer idPractica, PdfCopyFields copiar, Resultado xml, String estado, Boolean marca)
            throws NamingException, UnsupportedEncodingException, DocumentException, JRException, IOException {

        int hojaJRxml = 1;
        String cambio = null;
        // System.out.print("Preparando Orden " + xml.getIdOrden() + " / Practica: " + idPractica.toString() + " - " + xml.getDescripcion());
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
                throw new RuntimeException("\n\nFormato(s) para Orden " + xml.getIdOrden()
                        + "\nPrÃ¡ctica: " + idPractica.toString() + " - "
                        + xml.getDescripcion() + "\nCÃ³digo(s): "
                        + idsFormatos.toString() + " no se han encontrado.\n\n Comunique a su administrador el problema");
            } else {
                String XML = new String(fp.getXml(), "UTF-8");
                XML.replace("<textFieldExpression><![CDATA[new SimpleDateFormat(\"EEEE dd, MMMM yyyy\").format(new Date($F{fecha}))]]></textFieldExpression>", "<textFieldExpression><![CDATA[new SimpleDateFormat(\"EEEE dd, MMMM yyyy\", new Locale(\"es\", \"ES\")).format(new Date($F{fecha}))]]></textFieldExpression>");
                cambio = cambiarUbicacionImagen(XML);
                crearJRxml(cambio, "jrxml_P" + idPractica + "_O" + getNumOrden() + "_" + hojaJRxml, ".jrxml");
                hojaJRxml++;
            }
        }
        crearPDF(hojaJRxml, idPractica.toString(), copiar, estado, marca);
    }

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
    //<editor-fold defaultstate="collapsed" desc="GENETALES SET GET">
    public Integer getNumEmpresa() {
        return numEmpresa;
    }

    public void setNumEmpresa(Integer numEmpresa) {
        this.numEmpresa = numEmpresa;
    }

    public Integer getNumFirmaMed() {
        return numFirmaMed;
    }

    public void setNumFirmaMed(Integer numFirmaMed) {
        this.numFirmaMed = numFirmaMed;
    }

    public Integer getNumEmpresaRef() {
        return numEmpresaRef;
    }

    public void setNumEmpresaRef(Integer numEmpresaRef) {
        this.numEmpresaRef = numEmpresaRef;
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

    private static PdfResponse pdfXOrden(java.lang.String order, java.lang.String password) {
        net.cubosoft.misanalisis.ws.EReport service = new net.cubosoft.misanalisis.ws.EReport();
        net.cubosoft.misanalisis.ws.EReportWS port = service.getEReportWSPort();
        return port.pdfXOrden(order, password);
    }

    public iReport() {
        try {
            crearDirectorio();
            contexto = new InitialContext();
            admJPAG = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        } catch (NamingException ex) {
            Logger.getLogger(iReport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // </editor-fold>
    public byte[] getReportVista(List<Resultado> resultados, boolean forceVisible, boolean marca)
            throws NamingException, IOException, DocumentException, DocumentException, UnsupportedEncodingException, JRException {
        listaPDF = new ArrayList<>();
        listaResultados = null;
        listaResultados = new ArrayList<>();
        listaResultados.clear();
        listaResultados = resultados;
        return loadReportVista(forceVisible, marca);
    }

    public byte[] getReportXml(Map<String, Object> wSQL, String orden, boolean forceVisible, boolean marca)
            throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException, Exception {
        listaPDF = new ArrayList<byte[]>();
        listaResultados = admJPAG.getXmlVista(wSQL, 1, orden, false, null);

        if (listaResultados.isEmpty()) {
            return null;
        } else {
            return loadReportXml(forceVisible, marca);
        }
    }

    public byte[] getReportXmlRespaldo(Map<String, Object> wSQL, String orden, boolean forceVisible, boolean marca)
            throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException, Exception {
        listaPDF = new ArrayList<byte[]>();
        listaResultados = admJPAG.getXmlVista(wSQL, 1, orden, false, null);
        if (listaResultados.isEmpty()) {
            return null;
        } else {
            return loadReportXml(forceVisible, marca);
        }
    }

    public byte[] getReportXmlAuditoria(Map<String, Object> wSQL, Integer tipo, String orden, boolean forceVisible, boolean marca)
            throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException, Exception {
        listaPDF = new ArrayList<byte[]>();
        //listaXmlResultados = admJPAG.getXmlVista(wSQL, tipo, orden, false, null);
        //aqui
        listaResultados = admJPAG.getXmlVista(wSQL, tipo, orden, false, null);
        if (listaResultados.isEmpty()) {
            return null;
        } else {
            return loadReportXml(forceVisible, marca);
        }
//        for (Resultado elem : listaResultados) {
//
//        }

//        if (listaXmlResultados.isEmpty()) {
//            return null;
//        } else {
//            return loadReportXmlAuditoria(forceVisible, marca);
//            //return loadReportXml(forceVisible, marca);
//        }
    }

    private String usuario = "";

    public void guardarUsuario(String user) {

//        String aux = "";
//        if (user.equals("")) {
//            aux = "";
//        } else {
//            aux = user;
//        }
        usuario = user;
        // return aux;
    }

    public byte[] getReportCompleto(Map<String, Object> wSQLA, Map<String, Object> wSQLI, String orden, boolean forceVisible, boolean marca)
            throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException, Exception {
        listaPDF = new ArrayList<byte[]>();
        listaResultados = admJPAG.getXmlVista(wSQLI, 1, orden, false, null);
        listaAntecedente = admJPAG.getDBData(new XmlAntecedentes(), wSQLA, null, null);
        if (listaResultados.isEmpty()) {
            return null;
        } else {
            return loadReportCompleto(forceVisible, marca);
        }
    }

    private byte[] loadReportVista(boolean forceVisible, Boolean marca) throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException {
        int ingreso = 0;
        boolean incompleto = true;
        Image imageLock = null;
        for (Resultado xml : listaResultados) {
            if (xml.getEstado().equals("CO") || xml.getEstado().equals("AU") || xml.getEstado().equals("AR")) {
                incompleto = false;
            } else {
                incompleto = true;
            }
            //si tiene antencedentes hay q imprimir
            if (xml.getPerImpa() == 0) {
                listaAntecedente = null;
                setNumEmpresa(null);
                setXmlAntecedentes(null);
                Map<String, Object> wSQLA = new HashMap<>();
                wSQLA.put("idEmpresa", xml.getIdEmpresa());
                wSQLA.put("idHistoria.id", xml.getIdHistoria());
                wSQLA.put("orden.id", xml.getIdOrden());
                listaAntecedente = admJPAG.getDBData(new XmlAntecedentes(), wSQLA, null, null);
                for (XmlAntecedentes ant : listaAntecedente) {
                    if ((ant.getEstado().equals("CO"))) {
                        setNumEmpresa(ant.getIdEmpresa());
                        if (ant.getOrden().getOrganizacion().getCodRef() > 0) {
                            setNumEmpresaRef(ant.getOrden().getOrganizacion().getCodRef());
                        }
                        if (isCreadoPDF()) {
                            instanciarPDF();
                        }
                        if (xml.getIdEmpresa() != null) {
                            consultarLogo(xml.getIdEmpresa());
                        }
                        setXmlAntecedentes(ant.getAntecedentes());
                        buscarJrxmlAnt(getNumEmpresa(), getCopiarPDF(), ant, ant.getEstado(), marca);
                        ingreso++;
                    }
                }
            }
            if (xml.getResultado().equalsIgnoreCase("PDF") || (xml.getEstado().equals("CO") && xml.getResultado().equals("RIS"))) {
                consultarImagen(xml.getId().toString());
            } else {
                if ((xml.getResultado().equalsIgnoreCase("WS")) || (xml.getEstado().equals("CO")
                        || xml.getEstado().equals("AU") || xml.getEstado().equals("AR")) || (forceVisible)) {
                    setNumPractica(xml.getIdPractica());
                    setNumOrden(xml.getIdOrden().longValue());
                    if (isCreadoPDF()) {
                        instanciarPDF();
                    }
                    if ((xml.getIdPractica() == 415)) {
                        if (consultarWs(xml.getNroOrd())) {
                            AdmiNegocio objAc = new AdmiNegocio();
                            Map<String, Object> wSQL = new HashMap<>();
                            wSQL.put("idOrden ?=", xml.getIdOrden());
                            wSQL.put("idPractica ?=", 415);
                            try {
                                XmlResultado XMLRes = (XmlResultado) objAc.getDataObj(new XmlResultado(), wSQL, null, null);
                                if (XMLRes != null) {
                                    if (XMLRes.getEstado().equalsIgnoreCase("IN")) {
                                        XMLRes.setEstado("CO");

                                        //imageLock = new Image("/images/lock.png");
                                        objAc.actualizar(XMLRes);

                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("No se pudo actualizar XML Laboratorio");
                            }

                        } else {
                            if (!xml.getResultado().equalsIgnoreCase("WS")) {
                                consultarImagen(xml.getId().toString());
                                if (xml.getUsuario() != null) {
                                    if (!(xml.getUsuario().isEmpty())) {
                                        consultarfirma(xml.getUsuario());
                                    }
                                }
                                if (xml.getIdEmpresa() != null) {
                                    consultarLogo(xml.getIdEmpresa());
                                }
                                setXmlResultado(xml.getResultado());
                                buscarJrxmlXml(getNumPractica(), getCopiarPDF(), xml, xml.getEstado(), marca);
                                ingreso++;
                            }
                        }
                    } else {
                        consultarImagen(xml.getId().toString());
                        if (xml.getUsuario() != null) {
                            if (!(xml.getUsuario().isEmpty())) {
                                consultarfirma(xml.getUsuario());
                            }
                        }
                        setXmlResultado(xml.getResultado());
                        buscarJrxmlXml(getNumPractica(), getCopiarPDF(), xml, xml.getEstado(), marca);
                        ingreso++;
                    }
                }
            }
        }
        if (ingreso > 0) {
            if (getListaPDF().size() > 0) {
                for (int i = 0; i < getListaPDF().size(); i++) {
                    //aqui se crea un archio mas
                    PdfReader pdfReader = new PdfReader(getListaPDF().get(i));
                    getCopiarPDF().addDocument(pdfReader);
                }
            }
            getCopiarPDF().close();
            getBytesDocumento();
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

    private List<XmlResultado> XMLlistaResultados = null;

    public String getReportXmllink(Map<String, Object> wSQL, Integer tipo, String orden, boolean forceVisible, boolean marca)
            throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException, Exception {
        listaPDF = new ArrayList<byte[]>();
        String link = "";
        XMLlistaResultados = admJPAG.getXmlVista(wSQL, tipo, orden, false, null);

        if (XMLlistaResultados.isEmpty()) {
            return null;
        } else {
            for (XmlResultado elem : XMLlistaResultados) {
                //elem.getLkImg().equals(null)
                if (elem.getLkImg() != null) {
                    link = elem.getLkImg();
                } else {
                    link = null;
                }

            }
            return link;
        }
    }

    private List<AudImagen> listAudimagen = null;

    public String getReportXmllinkAudimagen(Map<String, Object> wSQL, Integer tipo, String orden, boolean forceVisible, boolean marca)
            throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException, Exception {
        listaPDF = new ArrayList<byte[]>();
        String link = "";
        listAudimagen = admJPAG.getXmlVista(wSQL, tipo, orden, false, null);

        if (listAudimagen.isEmpty()) {
            return null;
        } else {
            for (AudImagen elem : listAudimagen) {
                //elem.getLkImg().equals(null)
                if (elem.getLkImg() != null) {
                    link = elem.getLkImg();
                } else {
                    link = null;
                }

            }
            return link;
        }
    }

    private byte[] loadReportCompleto(boolean forceVisible, Boolean marca) throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException {
        int ingreso = 0;
        boolean incompleto = true;
        for (XmlAntecedentes ant : listaAntecedente) {
            if ((ant.getEstado().equals("CO")) || (forceVisible)) {
                setNumEmpresa(ant.getIdEmpresa());
                if (ant.getOrden().getOrganizacion().getCodRef() > 0) {
                    setNumEmpresaRef(ant.getOrden().getOrganizacion().getCodRef());
                }
                if (isCreadoPDF()) {
                    instanciarPDF();
                }
                if (ant.getIdEmpresa() != null) {
                    consultarLogo(BigInteger.valueOf(ant.getIdEmpresa().longValue()));
                }
                setXmlAntecedentes(ant.getAntecedentes());
                buscarJrxmlAnt(getNumEmpresa(), getCopiarPDF(), ant, ant.getEstado(), marca);
                ingreso++;
            }
        }
        for (Resultado xml : listaResultados) {
            if (xml.getEstado().equals("CO") || xml.getEstado().equals("AU") || xml.getEstado().equals("AR")) {
                incompleto = false;
            } else {
                incompleto = true;
            }
            if (xml.getResultado().equalsIgnoreCase("PDF")) {
                consultarImagen(xml.getId().toString());
            } else {
                if ((xml.getResultado().equalsIgnoreCase("WS")) || (xml.getEstado().equals("IM") || xml.getEstado().equals("CO") || xml.getEstado().equals("AU") || xml.getEstado().equals("AR")) || (forceVisible)) {
                    setNumPractica(xml.getIdPractica());
                    setNumOrden(xml.getIdOrden().longValue());
                    if (isCreadoPDF()) {
                        instanciarPDF();
                    }
                    if ((xml.getIdPractica() == 415)) {
                        if (consultarWs(xml.getNroOrd())) {
                            AdmiNegocio objAc = new AdmiNegocio();
                            Map<String, Object> wSQL = new HashMap<>();
                            wSQL.put("idOrden ?=", xml.getIdOrden());
                            wSQL.put("idPractica ?=", 415);
                            try {
                                XmlResultado XMLRes = (XmlResultado) objAc.getDataObj(new XmlResultado(), wSQL, null, null);
                                if (XMLRes != null) {
                                    if (XMLRes.getEstado().equalsIgnoreCase("IN")) {
                                        XMLRes.setEstado("CO");
                                        objAc.actualizar(XMLRes);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("No se pudo actualizar XML Laboratorio");
                            }
                        } else {
                            if (!xml.getResultado().equalsIgnoreCase("WS")) {
                                consultarImagen(xml.getId().toString());
                                if (xml.getUsuario() != null) {
                                    consultarfirma(xml.getUsuario());
                                }
                                setXmlResultado(xml.getResultado());
                                buscarJrxmlXml(getNumPractica(), getCopiarPDF(), xml, xml.getEstado(), marca);
                                ingreso++;
                            }
                        }
                    } else {
                        consultarImagen(xml.getId().toString());
                        if (xml.getUsuario() != null) {
                            consultarfirma(xml.getUsuario());
                        }
                        setXmlResultado(xml.getResultado());
                        buscarJrxmlXml(getNumPractica(), getCopiarPDF(), xml, xml.getEstado(), marca);
                        ingreso++;
                    }
                }
            }
        }
        if (ingreso > 0) {
            if (getListaPDF().size() > 0) {
                for (int i = 0; i < getListaPDF().size(); i++) {
                    PdfReader pdfReader = new PdfReader(getListaPDF().get(i));
                    getCopiarPDF().addDocument(pdfReader);
                }
            }
            getCopiarPDF().close();
            getBytesDocumento();
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

    private byte[] loadReportXml(boolean forceVisible, Boolean marca) throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException {
        int ingreso = 0;
        boolean incompleto = true;
        boolean ris = false;

        for (Resultado xml : listaResultados) {
            if (xml.getEstado().equals("CO") || xml.getEstado().equals("AU") || xml.getEstado().equals("AR") || xml.getEstado().equals("IM")) {
                incompleto = false;
            } else {
                incompleto = true;
            }
            //si tiene antencedentes hay q imprimir
            if (xml.getPerImpa() == 0) {
                listaAntecedente = null;
                setNumEmpresa(null);
                setXmlAntecedentes(null);
                Map<String, Object> wSQLA = new HashMap<>();
                wSQLA.put("idEmpresa", xml.getIdEmpresa());
                wSQLA.put("idHistoria.id", xml.getIdHistoria());
                wSQLA.put("orden.id", xml.getIdOrden());
                listaAntecedente = admJPAG.getDBData(new XmlAntecedentes(), wSQLA, null, null);
                for (XmlAntecedentes ant : listaAntecedente) {
                    if ((ant.getEstado().equals("CO")) || (forceVisible)) {
                        setNumEmpresa(ant.getIdEmpresa());
                        if (ant.getOrden().getOrganizacion().getCodRef() > 0) {
                            setNumEmpresaRef(ant.getOrden().getOrganizacion().getCodRef());
                        }
                        if (isCreadoPDF()) {
                            instanciarPDF();
                        }
                        if (xml.getIdEmpresa() != null) {
                            consultarLogo(xml.getIdEmpresa());
                        }
                        setXmlAntecedentes(ant.getAntecedentes());
                        buscarJrxmlAnt(getNumEmpresa(), getCopiarPDF(), ant, ant.getEstado(), marca);
                        ingreso++;
                    }
                }
            }
            if ((xml.getResultado().equalsIgnoreCase("RIS"))) {
                ris = consultarRis(xml);
            }

//            ris = consultarRis(xml);
            if ((xml.getResultado().equalsIgnoreCase("PDF")) || (xml.getResultado().equalsIgnoreCase("RIS")) || (xml.getResultado().equalsIgnoreCase("LABORATORIO"))) {
                //if ((xml.getResultado().equalsIgnoreCase("PDF")) || (xml.getResultado().equalsIgnoreCase("RIS"))  || (xml.getResultado().equalsIgnoreCase("LABORATORIO"))  ) {
                // if (xml.getResultado().equalsIgnoreCase("PDF")) {
//            if ((xml.getResultado().equalsIgnoreCase("RIS"))) {
//                ris = consultarRis(xml);
//            }
                //if ((xml.getResultado().equalsIgnoreCase("PDF"))) {
                consultarImagen(xml.getId().toString());
            } else {
                if ((xml.getResultado().equalsIgnoreCase("WS")) || (xml.getEstado().equals("IM") || xml.getEstado().equals("CO") || xml.getEstado().equals("AU") || xml.getEstado().equals("AR")) || (forceVisible)) {
                    setNumPractica(xml.getIdPractica());
                    setNumOrden(xml.getIdOrden().longValue());
                    if (isCreadoPDF()) {
                        instanciarPDF();
                    }
                    if ((xml.getIdPractica() == 415)) {
                        if (consultarWs(xml.getNroOrd())) {
                            AdmiNegocio objAc = new AdmiNegocio();
                            Map<String, Object> wSQL = new HashMap<>();
                            wSQL.put("idOrden ?=", xml.getIdOrden());
                            wSQL.put("idPractica ?=", 415);
                            try {
                                XmlResultado XMLRes = (XmlResultado) objAc.getDataObj(new XmlResultado(), wSQL, null, null);
                                if (XMLRes != null) {
                                    if (XMLRes.getEstado().equalsIgnoreCase("IN")) {
                                        XMLRes.setEstado("CO");
                                        objAc.actualizar(XMLRes);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("No se pudo actualizar XML Laboratorio");
                            }

                        } else {
                            if (!xml.getResultado().equalsIgnoreCase("WS")) {
                                consultarImagen(xml.getId().toString());
                                if (xml.getUsuario() != null) {
                                    consultarfirma(xml.getUsuario());
                                }
                                if (xml.getIdEmpresa() != null) {
                                    consultarLogo(xml.getIdEmpresa());
                                }
                                setXmlResultado(xml.getResultado());
                                buscarJrxmlXml(getNumPractica(), getCopiarPDF(), xml, xml.getEstado(), marca);
                                ingreso++;
                            }
                        }
                    } else {
                        consultarImagen(xml.getId().toString());
                        if (xml.getUsuario() != null) {
                            consultarfirma(xml.getUsuario());
                        }
                        if (xml.getIdEmpresa() != null) {
                            consultarLogo(xml.getIdEmpresa());
                        }
                        setXmlResultado(xml.getResultado());
                        buscarJrxmlXml(getNumPractica(), getCopiarPDF(), xml, xml.getEstado(), marca);
                        ingreso++;
                    }
                }
            }
        }

//        for (Resultado elem : listaResultados) {
//            ris = consultarRis(elem);
//        }
        if (ingreso
                > 0) {
            if (getListaPDF().size() > 0) {
                for (int i = 0; i < getListaPDF().size(); i++) {
                    PdfReader pdfReader = new PdfReader(getListaPDF().get(i));
                    getCopiarPDF().addDocument(pdfReader);
                }
            }
            getCopiarPDF().close();
            getBytesDocumento();
            EliminarFichero(getUbicacionPDF());
            if ((nombreFirma != null) && (!(nombreFirma.isEmpty()))) {
                for (Object nombreFirma1 : nombreFirma) {
                    EliminarFichero(getUbicacion() + nombreFirma1);
                }
            }
            if ((nombreLogo != null) && (!(nombreLogo.isEmpty()))) {
                for (Object nombreFirma1 : nombreLogo) {
                    EliminarFichero(getUbicacion() + nombreFirma1);
                }
            }
            for (Object nombreImagen1 : nombreImagen) {
                EliminarFichero(getUbicacion() + nombreImagen1);
            }
//            EliminarFichero(getUbicacion());
        }
        if (ingreso
                == 0) {
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

    private byte[] loadReportXmlAuditoria(boolean forceVisible, Boolean marca) throws NamingException, IOException, DocumentException, UnsupportedEncodingException, JRException {
        int ingreso = 0;
        boolean incompleto = true;

        for (XmlResultado eleXmlResultado : listaXmlResultados) {
            if (eleXmlResultado.getEstado().equals("CO") || eleXmlResultado.getEstado().equals("AU") || eleXmlResultado.getEstado().equals("AR") || eleXmlResultado.getEstado().equals("IM")) {
                incompleto = false;
            } else {
                incompleto = true;
            }

            if (eleXmlResultado.getPractica().getPerImpa() == 0) {
                listaAntecedente = null;
                setNumEmpresa(null);
                setXmlAntecedentes(null);
                Map<String, Object> wSQLA = new HashMap<>();
                wSQLA.put("idEmpresa", eleXmlResultado.getEmpresa());
                System.out.println("empresa:" + eleXmlResultado.getEmpresa());
                wSQLA.put("idHistoria.id", eleXmlResultado.getIdHistoria());
                wSQLA.put("orden.id", eleXmlResultado.getIdOrden());
                listaAntecedente = admJPAG.getDBData(new XmlAntecedentes(), wSQLA, null, null);

            }

        }

        for (Resultado xml : listaResultados) {
            if (xml.getEstado().equals("CO") || xml.getEstado().equals("AU") || xml.getEstado().equals("AR") || xml.getEstado().equals("IM")) {
                incompleto = false;
            } else {
                incompleto = true;
            }
            //si tiene antencedentes hay q imprimir
            if (xml.getPerImpa() == 0) {
                listaAntecedente = null;
                setNumEmpresa(null);
                setXmlAntecedentes(null);
                Map<String, Object> wSQLA = new HashMap<>();
                wSQLA.put("idEmpresa", xml.getIdEmpresa());
                wSQLA.put("idHistoria.id", xml.getIdHistoria());
                wSQLA.put("orden.id", xml.getIdOrden());
                listaAntecedente = admJPAG.getDBData(new XmlAntecedentes(), wSQLA, null, null);
                for (XmlAntecedentes ant : listaAntecedente) {
                    if ((ant.getEstado().equals("CO")) || (forceVisible)) {
                        setNumEmpresa(ant.getIdEmpresa());
                        if (ant.getOrden().getOrganizacion().getCodRef() > 0) {
                            setNumEmpresaRef(ant.getOrden().getOrganizacion().getCodRef());
                        }
                        if (isCreadoPDF()) {
                            instanciarPDF();
                        }
                        if (xml.getIdEmpresa() != null) {
                            consultarLogo(xml.getIdEmpresa());
                        }
                        setXmlAntecedentes(ant.getAntecedentes());
                        buscarJrxmlAnt(getNumEmpresa(), getCopiarPDF(), ant, ant.getEstado(), marca);
                        ingreso++;
                    }
                }
            }
            if (xml.getResultado().equalsIgnoreCase("PDF")) {
                consultarImagen(xml.getId().toString());
            } else {
                if ((xml.getResultado().equalsIgnoreCase("WS")) || (xml.getEstado().equals("IM") || xml.getEstado().equals("CO") || xml.getEstado().equals("AU") || xml.getEstado().equals("AR")) || (forceVisible)) {
                    setNumPractica(xml.getIdPractica());
                    setNumOrden(xml.getIdOrden().longValue());
                    if (isCreadoPDF()) {
                        instanciarPDF();
                    }
                    if ((xml.getIdPractica() == 415)) {
                        if (consultarWs(xml.getNroOrd())) {
                            AdmiNegocio objAc = new AdmiNegocio();
                            Map<String, Object> wSQL = new HashMap<>();
                            wSQL.put("idOrden ?=", xml.getIdOrden());
                            wSQL.put("idPractica ?=", 415);
                            try {
                                XmlResultado XMLRes = (XmlResultado) objAc.getDataObj(new XmlResultado(), wSQL, null, null);
                                if (XMLRes != null) {
                                    if (XMLRes.getEstado().equalsIgnoreCase("IN")) {
                                        XMLRes.setEstado("CO");
                                        objAc.actualizar(XMLRes);
                                    }
                                }
                            } catch (Exception e) {
                                System.out.println("No se pudo actualizar XML Laboratorio");
                            }

                        } else {
                            if (!xml.getResultado().equalsIgnoreCase("WS")) {
                                if (xml.getUsuario() != null) {
                                    consultarfirma(xml.getUsuario());
                                }
                                if (xml.getIdEmpresa() != null) {
                                    consultarLogo(xml.getIdEmpresa());
                                }
                                setXmlResultado(xml.getResultado());
                                buscarJrxmlXml(getNumPractica(), getCopiarPDF(), xml, xml.getEstado(), marca);
                                ingreso++;
                            }
                        }
                    } else {
                        consultarImagen(xml.getId().toString());
                        if (xml.getUsuario() != null) {
                            consultarfirma(xml.getUsuario());
                        }
                        if (xml.getIdEmpresa() != null) {
                            consultarLogo(xml.getIdEmpresa());
                        }
                        setXmlResultado(xml.getResultado());
                        buscarJrxmlXml(getNumPractica(), getCopiarPDF(), xml, xml.getEstado(), marca);
                        ingreso++;
                    }
                }
            }
        }
        if (ingreso > 0) {
            if (getListaPDF().size() > 0) {
                for (int i = 0; i < getListaPDF().size(); i++) {
                    PdfReader pdfReader = new PdfReader(getListaPDF().get(i));
                    getCopiarPDF().addDocument(pdfReader);
                }
            }
            getCopiarPDF().close();
            getBytesDocumento();
            EliminarFichero(getUbicacionPDF());
            if ((nombreFirma != null) && (!(nombreFirma.isEmpty()))) {
                for (Object nombreFirma1 : nombreFirma) {
                    EliminarFichero(getUbicacion() + nombreFirma1);
                }
            }
            if ((nombreLogo != null) && (!(nombreLogo.isEmpty()))) {
                for (Object nombreFirma1 : nombreLogo) {
                    EliminarFichero(getUbicacion() + nombreFirma1);
                }
            }
            for (Object nombreImagen1 : nombreImagen) {
                EliminarFichero(getUbicacion() + nombreImagen1);
            }
//            EliminarFichero(getUbicacion());
        }
        if (ingreso == 0) {
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

    private boolean consultarWs(Long idOrden) throws NamingException, IOException {
        if (idOrden != null) {
            AdmiNegocioSql negoSQL = new AdmiNegocioSql();
            Map<String, Object> wSQL = new HashMap<>();
            wSQL.put("nroOrd ?=", idOrden);
            ListapedidosN = negoSQL.getData(new LispetAvanex(), wSQL, null, null);
            if (ListapedidosN.size() > 0) {
                objOrdenN = (LispetAvanex) ListapedidosN.get(0);
                PdfResponse objres;
                try {
                    objres = pdfXOrden(objOrdenN.getNroOrd().toString(), objOrdenN.getClave());
                    if (objres.getCompletos() > 0) {
                        //cambiar estado
                        String pd = objres.getPdf();
                        byte[] ret = com.lowagie.text.pdf.codec.Base64.decode(pd);
                        if (ret != null) {
                            setBytes(ret);
                            setListaPDF(getBytes());
                            return true;
                        } else {
                            return false;

                        }
                    } else {
                        return false;

                    }
                } catch (Exception e) {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
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
        }
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

    private void consultarImagen(String idXml) throws NamingException, IOException {
        nombreImagen = new ArrayList();
        admJPAG = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        listaResultadoGrafico
                = admJPAG.getTodosXParametro("ResultadoGrafico", "idXmlResultado", idXml, Integer.class,
                        "id", false);
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
        File file = new File(dir + "/tmp");

        if (!file.exists()) {
            file.mkdirs();
        }

        setUbicacion(dir + "/tmp/");
    }

    public String cambiarUbicacionImagen(String contenido) {
        String resultado = null;
        String path = iReport.class
                .getResource("iReport.class").getPath();
        path = path.substring(path.indexOf("/") + 1, path.indexOf("WEB-INF"));
        String nuevoPath;
        String osName = System.getProperty("os.name");
        if (osName.toUpperCase().indexOf("LINUX") == 0) {
            nuevoPath = "/" + path + "resources/logosl/";
        } else {
            nuevoPath = path + "resources/logos/";
        }
        String derecho = "", izquierdo = "", logoPetrex = "", logoBanco = "", firmaMed = "", logoem = "";
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
                if (nombreImagen.get(0).toString().indexOf("AU-OD_") >= 0) {
                    derecho = cadena.toString() + nombreImagen.get(0);
                } else if (nombreImagen.get(0).toString().indexOf("AU-OI_") >= 0) {
                    izquierdo = cadena.toString() + nombreImagen.get(0);
                }
                if (nombreImagen.get(1).toString().indexOf("AU-OD_") >= 0) {
                    derecho = cadena.toString() + nombreImagen.get(1);
                } else if (nombreImagen.get(1).toString().indexOf("AU-OI_") >= 0) {
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
                firmaMed = cadena.toString() + nombreFirma.get(0);
            } else {
                firmaMed = nuevoPath + "blancoFirma.png";
            }
        } else {
            firmaMed = nuevoPath + "blancoFirma.png";
        }
        if ((nombreLogo != null) && (!(nombreLogo.isEmpty()))) {
            if (nombreLogo.size() > 0) {
                logoem = cadena.toString() + nombreLogo.get(0);
            } else {
                logoem = nuevoPath + "blancoLogo.png";
            }
        } else {
            logoem = nuevoPath + "blancoLogo.png";
        }
        logoPetrex = nuevoPath + "logopetrex.jpg";
        logoBanco = nuevoPath + "bancopichincha.jpg";
        resultado = contenido.replaceAll("/tmp/", nuevoPath);
        resultado = resultado.replaceAll("logoPetrex", logoPetrex);
        resultado = resultado.replaceAll("logoBanco", logoBanco);
        resultado = resultado.replaceAll("imagenD", derecho);
        resultado = resultado.replaceAll("imagenI", izquierdo);
        resultado = resultado.replaceAll("firmaMed", firmaMed);
        resultado = resultado.replaceAll("logoEmp", logoem);
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
        PdfCopyFields copiar = new PdfCopyFields(new FileOutputStream(dirPDF));
        setCopiarPDF(copiar);
        setCreadoPDF(false);
        setUbicacionPDF(dirPDF);
    }

    private void EliminarFichero(String fichero) {
        File archivo = new File(fichero);
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    public Character preferenciaOrden(Long idOrden) throws NamingException, IOException {

        Character urg = 'N';
        if (idOrden != null) {
            AdmiNegocioSql negoSQL = new AdmiNegocioSql();
            Map<String, Object> wSQL = new HashMap<>();
            wSQL.put("nroOrd ?=", idOrden);
            try {
                ListOrdUrg = negoSQL.getData(new Lisord(), wSQL, null, null);
                if (ListOrdUrg.size() > 0) {
                    System.out.println("listordurg" + ListOrdUrg);
                    Lisord resul = (Lisord) ListOrdUrg.get(0);
                    urg = resul.getUrgente();
                    System.out.println("if" + urg);
                    //  System.out.println("jjjjh" + resul.getUrgente());
                    return urg;
                } else {
                    System.out.println("no se encontro estado urg");
                    System.out.println("else" + urg);
                    return urg;
                }

            } catch (Exception e) {
                System.out.println("e" + e);
            }

        }
        return urg;
    }

}

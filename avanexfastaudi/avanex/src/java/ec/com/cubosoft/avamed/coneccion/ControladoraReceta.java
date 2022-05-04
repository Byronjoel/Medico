package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.modelo.core.Ciediez;
import ec.com.cubosoft.avamed.modelo.core.CsGrupos;
import ec.com.cubosoft.avamed.modelo.core.CsPerxgru;
import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
import ec.com.cubosoft.avamed.modelo.core.Farmacos;
import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import ec.com.cubosoft.avamed.modelo.persona.Receta;
import ec.com.cubosoft.avamed.negocio.CalcularEdad;
import ec.com.cubosoft.avamed.negocio.ConvertirDocumento;
import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import ec.com.cubosoft.avamed.procesos.AdmiUsuario;
import ec.com.cubosoft.avamed.procesos.CreacionXml;
import ec.com.cubosoft.avamed.procesos.iReport;
import ec.com.cubosoft.avasus.controller.renderder.CieRender;
import java.io.*;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.naming.NamingException;
import org.w3c.dom.*;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.*;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 * @author Juan Pablo Chavez
 * @version 1.0.1
 * @version 1.0.2
 *
 * @author Patricia Amoroso
 * @version 1.0
 */
public class ControladoraReceta extends GenericForwardComposer {
    
    private static final long serialVersionUID = 1L;

    //<editor-fold defaultstate="collapsed" desc="Datos fijos Sesion">
    CsPerxgru permisosPagina;
    CsUsuarios usuario;
    Nombre medico;
    Orden objOrden;
    CsGrupos objGrupoActivo;
    CsUsuarios objUsuarioActivo;
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Datos Informe">
//    Label IdPractica, nomPractica, idOrden;
    Textbox bOrden, bHistorial;
//    Date fechaOrden;

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables Negocio">
    AdmiNegocio admNegocio;
    // </editor-fold>
    Window WinHistoria;
    ///////////////////////////
    //<editor-fold defaultstate="collapsed" desc="InicioSesinon">
//    Combobox farmacos;
    Textbox farmacos;
    Grid presentacion;
    Rows prese;
    Textbox medicamentos;
    Textbox txtdiagnostico;
    Textbox indicaciones;
    Combobox cmbDiagnoticos;
    Button imprimirR;
    Historia historia;
    Integer practica;
    boolean tipoT;
    Receta objRec;
    SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd", new Locale("es", "ES"));
    Borderlayout recet;

    private void modificarSession() {
        tipoT = false;
        objRec = new Receta();
        ProcesosSession admiSessionUsuario = new ProcesosSession();
        historia = (Historia) admiSessionUsuario.ObtenerAtributoSession(7, desktop.getSession());
        admNegocio = new AdmiNegocio();
        usuario = (CsUsuarios) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
        practica=(Integer) admiSessionUsuario.ObtenerAtributoSession(9, desktop.getSession());
        if (usuario.getMedicos().size() == 1) {
            medico = usuario.getMedicos().get(0);
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
        objGrupoActivo = (CsGrupos) admiSessionUsuario.ObtenerAtributoSession(3, session);
        objUsuarioActivo = (CsUsuarios) admiSessionUsuario.ObtenerAtributoSession(2, session);
        admNegocio = new AdmiNegocio();
        farmacos.addEventListener(Events.ON_CHANGE, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                if (farmacos.getValue().length() >= 4) {
                    List<Object> farma = obtenerFacemaco(farmacos.getValue());
                    try {
                        for (Object object : farma) {
                            final Farmacos farm = (Farmacos) object;
                            final Row row = new Row();
                            row.setValue(farm);
                            Label nom = new Label(farm.getNomGenerico());
                            nom.setStyle("font-size: 8px;");
                            nom.setParent(row);
                            Label pres = new Label(farm.getPresentacion());
                            pres.setStyle("font-size: 7px;");
                            pres.setParent(row);
                            Label conc = new Label(farm.getConcentracion());
                            conc.setStyle("font-size: 7px;");
                            conc.setParent(row);
                            row.addEventListener(Events.ON_CLICK, new EventListener() {
                                @Override
                                public void onEvent(Event t) throws Exception {
                                    final Farmacos farms = (Farmacos) row.getValue();
                                    String med = medicamentos.getValue() + " " + farms.getNomGenerico() + "  " + farms.getConcentracion() + " (" + farms.getPresentacion() + ")  \n";
                                    medicamentos.setValue(med);
                                    medicamentos.setFocus(true);
                                    prese.getChildren().clear();
                                    farmacos.setValue("");
                                    farmacos.setFocus(true);

                                }
                            });
                            row.setParent(prese);
                        }
                    } catch (Exception e) {
                        System.out.println(usuario.getUsuario() + "Error en comboDX" + e.getMessage());
                    }
                }
                
            }
        });
        farmacos.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                prese.getChildren().clear();
                farmacos.setValue("");
            }
        });
        imprimirR.addEventListener(Events.ON_CLICK, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                Long id = guardarReceta();
                if (id != null) {
                    Window winMensaje = new Window();
                    String windowMessage = "msg_informe.zul";
                    Executions.createComponents(windowMessage, winMensaje, null);
                    winMensaje.setBorder("normal");
                    //winMensaje.setAction("show: slideDown;hide: slideUp");
                    winMensaje.setClosable(true);
                    winMensaje.setTitle("Vista Preliminar de Informe");
                    final Label msg = new Label();
                    msg.setParent(winMensaje);
                    msg.setVisible(false);
                    final Iframe frameReporte = (Iframe) winMensaje.getFellow("reporteV", false);
                    Map<String, Object> wSQL = new HashMap<String, Object>();
                    String pat = "R" + historia.getNumId() + "P R" + id;
                    wSQL.put("id", id);
                    AMedia mediaCarga = null;
                    mediaCarga = loadReceta(pat, wSQL, id);
                    if (mediaCarga != null) {
                        frameReporte.setContent(mediaCarga);
                    } else {
                        Messagebox.show("El informe esta incompleto o no esta¡ cerrado, consulte con el medico responsable",
                                "Información / Esta¡ incompleto el informe", Messagebox.OK, Messagebox.INFORMATION);
                    }
                    winMensaje.addEventListener("onClose", new EventListener() {
                        
                        @Override
                        public void onEvent(Event event) throws Exception {
                            medicamentos.setValue("");
                            indicaciones.setValue("");
                            imprimirR.setDisabled(true);
                            txtdiagnostico.setValue("");
                            txtdiagnostico.setDisabled(true);
                            medicamentos.setDisabled(true);
                            indicaciones.setDisabled(true);
                                    cmbDiagnoticos.setDisabled(true);
                                    farmacos.setDisabled(true);
                        }
                    });
                    winMensaje.setId("winReceta");
                    winMensaje.setParent(WinHistoria);
                    winMensaje.doModal();
                } else {
                    Messagebox.show("Error al guardar Receta", "RECETA", Messagebox.OK, Messagebox.INFORMATION);
                }
            }
        });
        
        cmbDiagnoticos.addEventListener(Events.ON_CHANGE, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                if (cmbDiagnoticos.getValue().length() >= 3) {
                    List<Object> cies = obtenerCie(cmbDiagnoticos.getValue());
                    cmbDiagnoticos.setItemRenderer(new CieRender());
                    cmbDiagnoticos.setModel(new ListModelList(cies));
                }
                cmbDiagnoticos.open();
            }
        });
        cmbDiagnoticos.addEventListener(Events.ON_SELECT, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                txtdiagnostico.setValue(txtdiagnostico.getValue() + " " + cmbDiagnoticos.getValue());
                while (cmbDiagnoticos.getItemCount() > 0) {
                    cmbDiagnoticos.removeChild(cmbDiagnoticos.getFirstChild());
                }
                cmbDiagnoticos.setValue(" ");
            }
        });
    }
    
    public List<Object> obtenerCie(String dato) throws NamingException {
        AdmiNegocio admNeg = new AdmiNegocio();
        List oSQL = new ArrayList();
        oSQL.add("descripcion");
        Map<String, Object> wSQL = new HashMap<String, Object>();
        wSQL.put("descripcion ?like", "%" + dato.toUpperCase() + "%");
        wSQL.put("lockReg ?=", 0);
        List objectList = admNeg.getDataLimit(new Ciediez(), wSQL, null, oSQL, 15);
        return objectList;
    }
    
    public AMedia loadReceta(String pat, Map<String, Object> wSQL, Long idReceta) {
        iReport reportes = new iReport();
        AMedia media = null;
        try {
            byte[] buf = reportes.getRecetaXml(wSQL, idReceta);
            if (buf != null) {
                InputStream mediaIS = new ByteArrayInputStream(buf);
                media = new AMedia(pat + ".pdf", "pdf", "application/pdf", mediaIS);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return media;
    }
    Document docrecet;
    DateFormat formatoHora = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss" , new Locale("es", "ES"));
    
    private Long guardarReceta() {
        CreacionXml admXml;
        try {
            admXml = new CreacionXml();
            docrecet = admXml.crearDocumento();
        } catch (Exception e) {
            System.out.println(usuario.getUsuario() + "Error new CreacionXml() + crearDocumento()");
        }
        docrecet = createDatosGeneralesXML(docrecet);
        Element elemento = null;
        elemento = docrecet.createElement("diagnostico");
        try {
            elemento.appendChild(docrecet.createTextNode(txtdiagnostico.getValue()));
        } catch (Exception e) {
            elemento.appendChild(docrecet.createTextNode(""));
        }
        
        docrecet.getDocumentElement().appendChild(elemento);
        elemento = docrecet.createElement("medicina");
        try {
            elemento.appendChild(docrecet.createTextNode(medicamentos.getValue()));
        } catch (Exception e) {
            elemento.appendChild(docrecet.createTextNode(""));
        }
        
        docrecet.getDocumentElement().appendChild(elemento);
        Element element = null;
        element = docrecet.createElement("indicacion");
        element.appendChild(docrecet.createTextNode(indicaciones.getValue()));
        docrecet.getDocumentElement().appendChild(element);
        String g = ConvertirDocumento.getConvertirDocumentoString(docrecet);
        objRec.setMedico(usuario.getNomUsu());
        objRec.setUsuario(usuario.getUsuario());
            objRec.setTexto(ConvertirDocumento.getConvertirDocumentoString(docrecet));
        admNegocio = new AdmiNegocio();
        try {
            if (!tipoT) {

                objRec.setIdOrden(BigInteger.valueOf(Long.parseLong(bOrden.getValue())));
                objRec.setIdPractica(practica);
                objRec.setFecha(new Date());
                objRec.setIdHistoria(BigInteger.valueOf(historia.getId()));
                objRec.setFirstUser(usuario.getUsuario());
                objRec.setEstado("CO");
                objRec = (Receta) admNegocio.guardar(objRec);
                tipoT = true;
                return objRec.getId();
            } else {
                if (admNegocio.actualizar(objRec)) {
                    objRec.setLastUser(usuario.getUsuario());
                    objRec.setFecUpd(new Date());
                    alert("Actualizado");
                    return objRec.getId();
                } else {
                    alert("No Actualizado");
                    return null;
                }
            }
        } catch (Exception e) {
            System.out.println(g);
            return null;
        }
        
    }
        Label IdPractica, nomPractica, idOrden;

    Date fechaOrden;
    private Document createDatosGeneralesXML(Document doc) {
        try {
            Element grpDatos = null;
            Element elemento = null;
            String x1 = "datos_generales_standar";
            grpDatos = doc.createElement(x1); // creamos el elemento raiz
            doc.getDocumentElement().appendChild(grpDatos); //pegamos la raiz al documento
            //ORIGEN Pais
            elemento = doc.createElement("etnia"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "T");
            elemento.setAttribute("descripcion", "ETNIA");
            try {
                elemento.appendChild(doc.createTextNode(historia.getEtnia() != null ? historia.getEtnia() : ""));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
           
            
            elemento = doc.createElement("paciente"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "NOMBRE");
            try {
                elemento.appendChild(doc.createTextNode(historia.getNombres() + " " + historia.getApellidos()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("nombres"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "NOMBRES");
            try {
                elemento.appendChild(doc.createTextNode(historia.getNombres()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("apellidos"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "APELLIDOS");
            try {
                elemento.appendChild(doc.createTextNode(historia.getApellidos()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("fecha_nace"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "fecha_nace");
            try {
                elemento.appendChild(doc.createTextNode(historia.getFechaNace() != null ? formato.format(historia.getFechaNace()) : ""));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("id_historia"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "IDHISTORIA");
            try {
                elemento.appendChild(doc.createTextNode(historia.getId().toString()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("identificacion"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "CI");
            try {
                elemento.appendChild(doc.createTextNode(historia.getNumId()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("estado_civil"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "ESTADOCIVIL");
            try {
                elemento.appendChild(doc.createTextNode(historia.getEstadoCivil()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("id_orden"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "IDORDEN");
            try {
                elemento.appendChild(doc.createTextNode(idOrden.getValue()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("telefono"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "TELEFONO");
            try {
                elemento.appendChild(doc.createTextNode(historia.getTelefono() == null ? "" : historia.getTelefono()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("sexo"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "SEXO");
            try {
                elemento.appendChild(doc.createTextNode(historia.getSexo()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("profesion"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "PROFESION");
            try {
                elemento.appendChild(doc.createTextNode(historia.getProfesion()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("edad"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "EDAD");
            CalcularEdad calcEdad = new CalcularEdad(historia.getFechaNace(), fechaOrden);
            try {
                elemento.appendChild(doc.createTextNode(calcEdad.obtenerAnios()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("ocupacion"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "OCUPACION");
            try {
                elemento.appendChild(doc.createTextNode(historia.getOcupacion()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("instruccion_paciente"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "INSTRUCCION PACIENTE");
            try {
                elemento.appendChild(doc.createTextNode(historia.getInstruccion() == null ? "" : historia.getInstruccion()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("ciudad_nace"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "CIUDAD NACIMIENTO");
            try {
                elemento.appendChild(doc.createTextNode(historia.getCiudadNace() == null ? "" : historia.getCiudadNace()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("direccion_paciente"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "DIRECCION");
            try {
                elemento.appendChild(doc.createTextNode(historia.getDireccion() == null ? "" : historia.getDireccion()));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("titulo_paciente"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "TITULO");
            String titulo = "";
            if ((historia.getTitulo() == null) || (historia.getTitulo().isEmpty())) {
                if (historia.getSexo().equalsIgnoreCase("M")) {
                    titulo = "SR";
                } else {
                    titulo = "SRA";
                }
            } else {
                titulo = historia.getTitulo();
            }
            elemento.appendChild(doc.createTextNode(titulo));
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("medico"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "MEDICO");
            try {
                elemento.appendChild(doc.createTextNode((medico.getNombreMedico() == null ? "" : medico.getNombreMedico())));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("especialidad"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "ESPECIALIDAD");
            try {
                elemento.appendChild(doc.createTextNode((medico.getEspecialidad()== null ? "" : medico.getEspecialidad())));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("cod_med"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "CODIGO-MEDICO");
            try {
                elemento.appendChild(doc.createTextNode((medico.getCodMedico() == null ? "" : medico.getCodMedico())));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("titulo_medico"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "TITULO");
            try {
                elemento.appendChild(doc.createTextNode((medico.getTitulo() == null ? "" : medico.getTitulo())));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode("Dr."));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("fecha"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "FECHA");
            Date fecha;
            if (fechaOrden != null) {
                fecha = fechaOrden;
            } else {
                fecha = new Date();
            }
            elemento.appendChild(doc.createTextNode(formato.format(fecha)));
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("fecha_hora"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "FECHA-HORA");
            String f = formatoHora.format(fecha);
            elemento.appendChild(doc.createTextNode(f));
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz

        } catch (RuntimeException e) {
            System.out.println("Error Datos Generales Standard " + e.getCause().getMessage() + bOrden.getValue());
            throw new RuntimeException(e);
        }
        return doc;
    }
    
    public void onCreate$recet() {
        try {
            modificarSession();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Cargar informe /// formato">
    public List<Object> obtenerFacemaco(String dato) throws NamingException {
        AdmiNegocio admNeg = new AdmiNegocio();
        List oSQL = new ArrayList();
        oSQL.add("descripcion");
        Map<String, Object> wSQL = new HashMap<String, Object>();
        wSQL.put("descripcion ?like", "%" + dato.toUpperCase() + "%");
        wSQL.put("lockReg ?=", 0);
        List objectList = admNeg.getDataLimit(new Farmacos(), wSQL, null, oSQL, 25);
        return objectList;
        
    }
    // </editor-fold>

}

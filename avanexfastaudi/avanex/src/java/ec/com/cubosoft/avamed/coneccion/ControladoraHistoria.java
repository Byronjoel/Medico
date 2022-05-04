package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.mensajes.AdmMensajes;
import ec.com.cubosoft.avamed.mensajes.TipoMensaje;
import ec.com.cubosoft.avamed.modelo.core.Ciediez;
import ec.com.cubosoft.avamed.modelo.ingreso.DxOrden;
import ec.com.cubosoft.avamed.modelo.core.CsPerxgru;
import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.ingreso.PracticaXOrden;
import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import ec.com.cubosoft.avamed.modelo.medico.Area;
import ec.com.cubosoft.avamed.modelo.core.Perxuser;
import ec.com.cubosoft.avamed.modelo.nextla.STrausu;
import ec.com.cubosoft.avamed.modelo.nextla.SUsuar;
import ec.com.cubosoft.avamed.modelo.nextla.sessionOk;
import ec.com.cubosoft.avamed.modelo.organizacion.Organizacion;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import ec.com.cubosoft.avamed.modelo.persona.Receta;
import ec.com.cubosoft.avamed.modelo.persona.ResultadoGrafico;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import ec.com.cubosoft.avamed.modelo.practica.FormatoXAntecedentes;
import ec.com.cubosoft.avamed.modelo.practica.FormatoXPractica;
import ec.com.cubosoft.avamed.modelo.practica.NombreP;
import ec.com.cubosoft.avamed.modelo.persona.XmlAntecedentes;
import ec.com.cubosoft.avamed.modelo.publico.Iso3166R2;
import ec.com.cubosoft.avamed.modelo.vistas.Pedidos;
import ec.com.cubosoft.avamed.negocio.CalcularEdad;
import ec.com.cubosoft.avamed.negocio.ConvertirDocumento;
import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import ec.com.cubosoft.avamed.procesos.AdmiNegocioSql;
import ec.com.cubosoft.avamed.procesos.CreacionXml;
import ec.com.cubosoft.avamed.procesos.ManejadoraXml;
import ec.com.cubosoft.avamed.procesos.iReport;
import ec.com.cubosoft.avamed.utilities.UtilFichero;
import ec.com.cubosoft.avasus.controller.renderder.AreaRenderer;
import ec.com.cubosoft.avasus.controller.renderder.CieRender;
import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.*;
import org.zkoss.zul.*;
import org.zkoss.zul.Timer;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.KeyEvent;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 * @author Juan Pablo Chavez
 * @version 1.0.1
 * @version 1.0.2
 *
 * @author Patricia Amoroso
 * @version 1.0
 */
public class ControladoraHistoria extends GenericForwardComposer {

    //<editor-fold defaultstate="collapsed" desc="Datos y componentes Pagina">
    SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd", new Locale("es", "ES"));
    DateFormat formatoHora = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    static final Map<String, String> INSTRUCCION = new HashMap<String, String>() {
        {
            put("1", "Primaria");
            put("2", "Secundaria");
            put("3", "Superior");
            put("4", "Cuarto Nivel");
            put("O", "Otro");
        }
        private static final long serialVersionUID = 1L;
    };
    private Window WinHistoria;
    private Label counter;
    private Label horaReferencia;
    private Timer contador;
    private final int autoSave = 99;
    private int countNum = autoSave;
    private Progressmeter pm;
    private static final long serialVersionUID = 1L;
    Menupopup editPopup;
    private Include barraBotones;
    private Boolean includeTree = false;
    private Listbox LbxAreas;
    public Button btnBuscar, btnReset, btnCancel, btnNew, btnGuardar;
    private East east;
    private Tabbox tabbox;
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Datos fijos Sesion">
    private String path;
    private Iso3166R2 ciudad;
    private ArrayList areasMedico;
    private Nombre medico;
    private String grupoUsr;
    private Area area;

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Datos Registros">
    private Label idNombre, idEmpresa, idInstruccion, idCI, idOrden, idHistoria, mSolicitante, idProfesion, idDireccion, idSexo, idECivil, idEdad;
    private Label nomPractica, IdPractica;
    private Historia objHistoria;
    private Orden objOrden;
    private Grid grdDatosRegistros;
    List XMLReceta;
    List XMLAntecedentes;
    List XMLResultados;
    private Button cieAdmin;

    public Historia getObjHistoria() {
        return objHistoria;
    }

    public Orden getObjOrden() {
        return objOrden;
    }

    public void setObjHistoria(Historia objHistoria) {
        this.objHistoria = objHistoria;
    }

    public void setObjOrden(Orden objOrden) {
        this.objOrden = objOrden;
    }

    private void limpiarRegistros() {
        try {
            grdDatosRegistros.setVisible(false);
            idNombre.setValue("");
            idEmpresa.setValue("");
            idInstruccion.setValue("");
            idCI.setValue("");
            idOrden.setValue("");
            idHistoria.setValue("");
            mSolicitante.setValue("");
            idProfesion.setValue("");
            idDireccion.setValue("");
            idSexo.setValue("");
            idECivil.setValue("");
            idEdad.setValue("");
            setObjOrden(null);
            setObjHistoria(null);
            nomPractica.setValue("");
            nomPractica.setVisible(false);
            IdPractica.setValue("");
            XMLReceta = null;
            XMLResultados = null;
            XMLAntecedentes = null;
        } catch (Exception e) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.limpiarRegistros()" + e.getMessage());
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Variables Negocio">
    AdmiNegocio admNegocio;

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="InicioSesinon">
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    private Integer getHeight() {
        Integer rtn;
        String height;
        height = WinHistoria.getHeight();
        if (height.contains("%")) {
            height = "100";
        } else {
            if (height.contains("px")) {
                height = height.substring(0, height.indexOf("px"));
            }
        }
        rtn = new Integer(height);
        return rtn;
    }

    public void enableButtons() {
        try {
            boolean bandGuardar = false;
            btnBuscar = (Button) barraBotones.getFellow("btnBuscar", false);
            btnReset = (Button) barraBotones.getFellow("btnReset", false);
            btnCancel = (Button) barraBotones.getFellow("btnCancel", false);
            btnGuardar = (Button) barraBotones.getFellow("btnGuardar", false);
            btnNew = (Button) barraBotones.getFellow("btnNew", false);
            btnBuscar.setDisabled(false);
            btnBuscar.addEventListener(Events.ON_CLICK, new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    if (((bOrden.getValue().isEmpty()) && (bHistorial.getValue().isEmpty())) || ((!(bOrden.getValue().matches("[0-9]*"))))) {
                        Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.INCOMPLETO), "Información", Messagebox.OK, Messagebox.INFORMATION);
                    } else {
                        try {
                            find();

                        } catch (InterruptedException | NamingException e) {
                        }
                    }
                }
            });
            pm.addEventListener(Events.ON_RIGHT_CLICK, new EventListener() {
                @Override
                public void onEvent(Event t) throws Exception {
                    timerRestart();
                }
            });
            btnBuscar.setVisible(true);
            btnReset.setDisabled(false);
            btnReset.setVisible(true);

            cieAdmin.addEventListener(Events.ON_CLICK, new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {

                    Window winMensaje = new Window();
                    String windowDiagnostico = "lista_cie.zul";
                    Executions.createComponents(windowDiagnostico, winMensaje, null);
                    winMensaje.setBorder("normal");
//                winMensaje.setWidth("40%");
                    winMensaje.setWidth("60%");
                    final Rows rowcie = (Rows) winMensaje.getFellow("rowcie", false);
                    final Textbox busdes = (Textbox) winMensaje.getFellow("findes", false);
                    busdes.addEventListener("onOK", new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            AdmiNegocio admNeg = new AdmiNegocio();
                            List oSQL = new ArrayList();
                            oSQL.add("lockReg");
                            Map<String, Object> wSQL = new HashMap<String, Object>();
                            wSQL.put("descripcion ?like", "%" + busdes.getValue().toUpperCase() + "%");
                            List objectList = admNeg.getData(new Ciediez(), wSQL, null, oSQL);
                            if (objectList.size() > 0) {
                                int fo = rowcie.getChildren().size();
                                Integer i = 0;
                                int j = 0;
                                while (rowcie.getChildren().size() > 0) {          //CondiciÃ³n trivial: siempre cierta
                                    try {
                                        j = i;
                                        rowcie.getChildren().remove(j);
                                        String vJO = "cambiar" + rowcie.getChildren().size();
                                        i++;
                                        if (i == fo) {
                                        }
                                    } catch (Exception e) {
                                        j = 0;
                                        i = 0;
                                    }

                                }
                                loadCie(objectList, rowcie);
                            }
                        }
                    });
                    final Textbox buscod = (Textbox) winMensaje.getFellow("findcod", false);
                    buscod.addEventListener("onOK", new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            AdmiNegocio admNeg = new AdmiNegocio();
                            List oSQL = new ArrayList();
                            oSQL.add("lockReg");
                            Map<String, Object> wSQL = new HashMap<String, Object>();
                            wSQL.put("codCie ?like", "%" + buscod.getValue().toUpperCase() + "%");
                            List objectList = admNeg.getData(new Ciediez(), wSQL, null, oSQL);
                            if (objectList.size() > 0) {
                                int fo = rowcie.getChildren().size();
                                Integer i = 0;
                                int j = 0;
                                while (rowcie.getChildren().size() > 0) {          //CondiciÃ³n trivial: siempre cierta
                                    try {
                                        j = i;
                                        rowcie.getChildren().remove(j);
                                        i++;
                                        if (i == fo) {
                                        }
                                    } catch (Exception e) {
                                        System.out.println(usuario + "error salio con " + rowcie.getChildren().size() + bOrden.getValue());
                                        j = 0;
                                        i = 0;
                                    }

                                }
                                loadCie(objectList, rowcie);
                            }
                        }
                    });
                    final Button nuev = (Button) winMensaje.getFellow("nuev", false);
                    final Textbox cod = (Textbox) winMensaje.getFellow("cod", false);
                    final Button guar = (Button) winMensaje.getFellow("guar", false);
                    final Textbox cieDes = (Textbox) winMensaje.getFellow("cieDes", false);
                    final Textbox cieGrup = (Textbox) winMensaje.getFellow("cieGrup", false);
                    final Textbox cieRef = (Textbox) winMensaje.getFellow("cieRef", false);
                    nuev.addEventListener(Events.ON_CLICK, new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {

                            AdmiNegocio admNeg = new AdmiNegocio();
                            List oSQL = new ArrayList();
                            oSQL.add("codCie");
                            Map<String, Object> wSQL = new HashMap<String, Object>();
                            wSQL.put("tipo ?like", "ECUA");
                            List objectList = admNeg.getData(new Ciediez(), wSQL, null, oSQL);
                            int pos = objectList.size();
                            Ciediez objcie = (Ciediez) objectList.get(0);
                            String g = getCodiCie(objcie);
                            do {
                                wSQL = new HashMap<String, Object>();
                                wSQL.put("codCie ?like", g);
                                objectList = admNeg.getData(new Ciediez(), wSQL, null, oSQL);
                                if (objectList.size() == 0) {
                                    cod.setValue(g);
                                    guar.setDisabled(false);
                                    cieDes.setDisabled(false);
                                    cieGrup.setDisabled(false);
                                    cieRef.setDisabled(false);

                                }
                            } while (objectList.size() > 0);
                        }
                    });

                    guar.addEventListener(Events.ON_CLICK, new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            AdmiNegocio admNeg = new AdmiNegocio();
                            Ciediez nuevC = new Ciediez();
                            nuevC.setCodCie(cod.getValue());
                            nuevC.setDescripcion(cieDes.getValue());
                            nuevC.setGrupo(cieGrup.getValue());
                            nuevC.setCodRef(cieRef.getValue());
                            nuevC.setTipo("ECUA");
                            nuevC.setLockReg(new Short("0"));
                            nuevC.setFirstUser(usuario);
                            try {
                                nuevC = (Ciediez) admNeg.guardar(nuevC);
                                cieDes.setValue("");
                                cieGrup.setValue("");
                                cieRef.setValue("");

                                cod.setValue("");
                                guar.setDisabled(true);
                            } catch (Exception e) {
                                alert("Error al guardar");
                            }

                        }
                    });
                    AdmiNegocio admNeg = new AdmiNegocio();
                    List oSQL = new ArrayList();
                    oSQL.add("lockReg");
                    Map<String, Object> wSQL = new HashMap<>();
                    List objectList = admNeg.getData(new Ciediez(), wSQL, null, oSQL);
                    loadCie(objectList, rowcie);
                    winMensaje.setClosable(true);
                    winMensaje.setTitle("Administrar CIE10 y Diagnósticos");
                    winMensaje.setParent(WinHistoria);
                    winMensaje.doModal();
                }
            });
            btnReset.addEventListener(Events.ON_CLICK, new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    reset();
                }
            });
//            if (alta == 0) {
//                btnNew.setDisabled(false);
//                btnNew.setVisible(true);
//                bandGuardar = true;
//            } else {
//                bandGuardar = true;
//                btnNew.setStyle("color: #A2B5CD;");
//            }
            if (modi == 1) {
                btnGuardar.setDisabled(false);
                btnGuardar.setVisible(true);
                bandGuardar = true;
            } else {
                btnGuardar.setStyle("color: #A2B5CD;");
            }
            if (bandGuardar) { //activo sincronizar
                btnGuardar.setDisabled(false);
                btnGuardar.addEventListener(Events.ON_CLICK, new EventListener() {

                    @Override
                    public void onEvent(Event event) throws Exception {
                        eventGuardar();
                    }
                });
                btnGuardar.setVisible(true);
                btnGuardar.setDisabled(false);
            } else {
                btnNew.setSclass("button-disabled");
            }
            TabReceta.addEventListener(Events.ON_SELECT, new EventListener() {

                @Override
                public void onEvent(Event event) throws Exception {
                    if (!banReceta) {
                        banReceta = true;
                        llamarReceta();
                    } else {
                    }
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getCodiCie(Ciediez objcie) throws NamingException {

        String cod = objcie.getCodCie();
        String h = cod.substring(1);
        Integer g = Integer.parseInt(h);
        g = g + 3;
        return "Z000" + g.toString();
    }
    String usuario = "";
    private Integer alta;
    private Integer baja;
    private Integer modi;
    private boolean abierto;
    private boolean impr;
    private boolean carga;
    private boolean edit;
    private boolean copiar;
    private String grupo;
    private Boolean perAdmiDx;
    private boolean delete;
    private boolean auditoria;

    private void modificarSession() {
        try {
            ProcesosSession admiSessionUsuario = new ProcesosSession();
            List listaper = null;
            CsUsuarios usuarioP = null;
            SUsuar usuarioN = null;
            sessionOk objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
            objsessiActica.setPagina(page.getId());

            admiSessionUsuario.AgregarAtributoSession(2, objsessiActica, desktop.getSession());
            if (objsessiActica.getTipo() == 1) {
                listaper = objsessiActica.getPerUsuNext();
                usuarioN = objsessiActica.getUsuarioN();
                usuario = usuarioN.getUsuario();
                grupo = usuarioN.getCodGru();
                //Perxuser objpe = objsessiActica.getPerUsuNex();
                //perAdmiDx = false;
                try {
                    Perxuser objpe = objsessiActica.getPerUsuNex();
                    perAdmiDx = false;

                    if (objsessiActica.getPerUsuNex() != null) {
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
                        if (objpe.getPerAuditoria() == 0) {
                            auditoria = true;
                        } else {
                            auditoria = false;
                        }

                    } else {
                        Messagebox.show("Contacte con el administrador por favor");
                    }
                } catch (Exception e) {
                    System.out.println("Exception de Session" + objsessiActica.getPerUsuNex());
                }
            } else {
                listaper = objsessiActica.getPerUsuAva();
                usuarioP = objsessiActica.getUsuarioP();
                usuario = usuarioP.getUsuario();
                grupo = objsessiActica.getGrupoP().getCodGru();
                if ((usuarioP.getCsGrupos().getCodGru().equals("ADMIN")) || (usuarioP.getCsGrupos().getCodGru().equals("MED")) || (usuarioP.getCsGrupos().getCodGru().equals("AUD"))) {
                    //  cieAdmin.setDisabled(false);
                    auditoria = true;
                }
                if ((usuarioP.getCsGrupos().getCodGru().equals("ADMIN")) || (usuario.equalsIgnoreCase("CHPE")) || (usuario.equalsIgnoreCase("MYCC"))) {
                    cieAdmin.setDisabled(false);
                }
                if (usuarioP.getPerUpload().intValue() == 1) {
                    carga = false;
                } else {
                    carga = true;
                }
                if (usuarioP.getPerAbto() == 1) {
                    abierto = false;
                } else {
                    abierto = true;
                }
                if (usuarioP.getPriVis() == 0) {
                    impr = false;
                } else {
                    impr = true;
                }
                if (usuarioP.getPerCyp() == 1) {
                    copiar = false;
                } else {
                    copiar = true;
                }
            }
            try {
                ciudad = (Iso3166R2) objsessiActica.getCiudad();
            } catch (Exception e) {
                ciudad = new Iso3166R2();
                System.out.println("Exception de ciudad ObtenerAtributoSession(5" + bOrden.getValue());
            }
            Boolean hb = false;
            String idMenuPermiso = null;
            Integer alt = null;
            Integer baj = null;
            Integer mod = null;
            for (Object object : listaper) {
                switch (objsessiActica.getTipo()) {
                    case 0: {
                        CsPerxgru obj = (CsPerxgru) object;
                        idMenuPermiso = obj.getCsPerxgruPK().getCodPer();
                        alt = Short.hashCode(obj.getAlta());
                        baj = Short.hashCode(obj.getBaja());
                        mod = Short.hashCode(obj.getModif());
                        if (idMenuPermiso.equalsIgnoreCase("EDIT")) {
                            edit = true;
                        }
                    }
                    break;
                    case 1: {
                        STrausu obj = (STrausu) object;
                        idMenuPermiso = obj.getSTrausuPK().getTransac();
                        alt = obj.getAlta().compareTo('S');
                        baj = obj.getBaja().compareTo('S');
                        mod = obj.getModif().compareTo('S');
                    }
                    break;
                }
                if ((idMenuPermiso.equalsIgnoreCase("m_historia")) || ((idMenuPermiso.equalsIgnoreCase("Ordenes")))) {
                    hb = true;
                    alta = alt;
                    baja = baj;
                    modi = mod;
                } else {
                    if (grupo != null) {
                        if (grupo.equalsIgnoreCase("MED")) {
                            hb = true;
                            alta = alt;
                            baja = baj;
                            modi = mod;
                        }
                    }
                }

            }
            if (usuarioP != null) {
                if (usuarioP.getMedicos().size() > 0) {
                    medico = usuarioP.getMedicos().get(0);
                    area = medico.getArea();
                    bbAreas.setValue(medico.getArea().getDescripcion());
                } else {
                    Map<String, Object> wSQL = new HashMap<>();
                    List oSQL = new ArrayList();
                    wSQL.put("id ?=", 0);
                    admNegocio = new AdmiNegocio();
                    List data = admNegocio.getData(new Nombre(), wSQL, null, oSQL);
                    System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoria.modificarSession()");
                    if (data.size() > 0) {
                        medico = (Nombre) data.get(0);
                        area = medico.getArea();
                    }
                }
                admNegocio = new AdmiNegocio();
                String idAreaVista;
                areasMedico = new ArrayList();
                if (usuarioP.getMedicos().size() > 0) {
                    medico = usuarioP.getMedicos().get(0);
                    idAreaVista = medico.getArea().getPerArea();
                    int pos = 0;
                    if (!(idAreaVista.equalsIgnoreCase("0"))) {
                        while (pos < idAreaVista.length()) {
                            String g = idAreaVista.substring(pos, (idAreaVista.indexOf(",")));
                            areasMedico.add(g);
                            pos = idAreaVista.indexOf(",") + 1;
                            idAreaVista = idAreaVista.substring(pos);
                            pos = 0;
                        }
                    }
                } else {
                    idAreaVista = "0";
                }

            } else {
                Map<String, Object> wSQL = new HashMap<>();
                List oSQL = new ArrayList();
                wSQL.put("id ?=", 0);
                admNegocio = new AdmiNegocio();
                List data = admNegocio.getData(new Nombre(), wSQL, null, oSQL);
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoria.modificarSession()");
                if (data.size() > 0) {
                    medico = (Nombre) data.get(0);
                    area = medico.getArea();
                }
            }
            if (hb) {
                if (auditoria) {
                    enableButtons();
                    loadAreas();
                } else {
                    alert("Usuario no tiene permiso");
                }
            } else {
                alert("Usuario no tiene permiso");
                reset();
            }
        } catch (NamingException e) {
            System.out.println(usuario + "Modificar Sessio" + desktop.getSession() + " " + usuario);
            throw new RuntimeException(e);

        } catch (WrongValueException e) {
            System.out.println(usuario + "Modificar Sessio" + desktop.getSession() + " " + usuario);
            throw new RuntimeException(e);
        }
    }

    public void timerStop() {
        contador.setRunning(false);
        counter.setValue("Guardado automatico en :" + new Integer(autoSave).toString() + " seg.");
        pm.setValue(0);
    }

    public void timerRestart() {
        timerStop();
        if (bbAreas.getValue().equals("Medicina Ocupacional")) {
            int autoSave1 = 99;
            countNum = autoSave1;
        } else {
            countNum = autoSave;
        }
        horaReferencia.setValue("UPT " + formatoHora.format(new Date()));
        contador.setRunning(true);
    }

    public void onCreate$WinHistoria() {
        try {
            //        System.out.println("oncreate WinHistoria");
            modificarSession();
            this.contador = new Timer();
            this.contador.setPage(this.WinHistoria.getPage());
            this.contador.setDelay(1000);
            this.contador.setRepeats(true);
            this.contador.addEventListener("onTimer", new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    if (countNum == 0) {
                        contador.setRunning(false);
                        try {
                            if (quietSave()) {
                                timerRestart();
                            } else {
                                Messagebox.show("Fallo automatico, recuperar informe hora " + horaReferencia.getValue() + " llamar ext 722", "Información de usuario", Messagebox.OK, Messagebox.INFORMATION);
                                System.out.println(usuario + " No se pudo el auto guardardo int numFormatosInforme " + numFormatosInforme + " orden " + bOrden.getValue() + usuario);
                            }
                            return;
                        } catch (Exception ex) {
                            if (bDXBlanco) {
                                timerRestart();
                                bDXBlanco = false;
                            }
                            System.out.println(usuario + "onCreate$WinHistoria()");
                            throw new RuntimeException(ex);
                        }
                    }
                    counter.setValue("Guardado automatico en: " + --countNum + " seg.");
                    //  pm.setValue((autoSave - countNum)/3);
                    pm.setValue((countNum));
                }
            });
            this.contador.setRunning(false);
        } catch (Exception e) {
            System.out.println(usuario + "onCreate$WinHistoria() 409");
            throw new RuntimeException(e);
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="TEMPORIZADOR">
    private boolean autoGuardar() {
        try {
            crearDirectorioPrincipal();
            CreacionXml admXml = new CreacionXml();
            newDoc = admXml.crearDocumento();
            try {
                newDoc = XMLInforme(newDoc);
            } catch (Exception e) {
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.autoGuardar()  425");
            }

            int numNodo = newDoc.getChildNodes().item(0).getChildNodes().getLength();
            if (numNodo == numFormatosInforme) {
                System.out.println(usuario + " Numero formatos OK" + bOrden.getValue());
                return guardarObjetos("IN", false, true, true);
            } else {
                System.out.println(usuario + "Numero formatos ERROR " + bOrden.getValue());
                return false;
            }
        } catch (Exception ex) {
            Logger.getLogger(ControladoraHistoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private boolean quietSave() throws Exception {
        boolean svt = false;
        try {
            if (banAntecedentes) {
                Textbox modifica = (Textbox) anteV.getFellow("modificar", false);
                if (modifica.getValue().equalsIgnoreCase("T")) {
                    ProcesosSession admiSessionUsuario = new ProcesosSession();
                    sessionOk objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
                    setXMLantecedentes(objsessiActica.getObjXmlAntecedentes());
                    guardarAntecedentes(true, "IN");
                } else {
                    if (modifica.getValue().equalsIgnoreCase("N")) {
                        guardarAntecedentes(false, "IN");
                    } else {
                        if (modifica.getValue().equalsIgnoreCase("F")) {
                        }
                    }
                }
                svt = true;
            }
        } catch (Exception e) {
            System.out.println("guardarAntecedentes");
        }
        try {
            autoGuardar();
            svt = true;
//            eventGuardarInforme();
        } catch (Exception e) {
            System.out.println("eventGuardarInforme");
        }
        return svt;
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="FILTRO BUSQUEDA">
    //<editor-fold defaultstate="collapsed" desc="Datos Filtro Busqueda">
    private Textbox bOrden, bHistorial, Valor;
    private Bandbox bbAreas;

    private void cleanFiltro() {
        bHistorial.setValue("");
        bHistorial.setDisabled(false);
        bOrden.setValue("");
        bOrden.setDisabled(false);
        bbAreas.setDisabled(false);
        PracticaInforme = null;
    }
    // </editor-fold>
    private Pedidos PracticaInforme;
    private Context contexto;
    private String aglb;
    private String ageb;
    private Textbox idMed;

    private void find() throws InterruptedException, NamingException {
        docAnteHis = null;
        DocAnte = null;
        banAntecedentes = false;
        bOrden.setValue(bOrden.getValue().trim());
        bHistorial.setValue(bHistorial.getValue().trim());
        try {
            setObjHistoria((Historia) getHistoria(new Historia(), bHistorial.getValue().trim(), bOrden.getValue().trim(), ""));
            if (objHistoria != null) {
                btnBuscar.setDisabled(true);
                bHistorial.setValue(objHistoria.getId().toString());
                bHistorial.setDisabled(true);
                ocupacion = objHistoria.getOcupacion();
                if (ocupacion == null) {
                    ocupacion = "";
                }
                idSexo.setValue(new AdmMensajes().getDescripcionAbreviatura(objHistoria.getSexo()));
                idCI.setValue(objHistoria.getNumId().trim());
                idHistoria.setValue(objHistoria.getId().toString());
                idProfesion.setValue(objHistoria.getProfesion());
                idInstruccion.setValue(INSTRUCCION.get(objHistoria.getInstruccion()));
                idDireccion.setValue(objHistoria.getDireccion());
                idECivil.setValue(new AdmMensajes().getDescripcionAbreviatura(objHistoria.getEstadoCivil()).trim());
                idNombre.setValue(objHistoria.getNombres() + " " + objHistoria.getApellidos());
                List orden = objHistoria.getOrden();
                if (!(bOrden.getValue().isEmpty())) {
                    for (int i = 0; i < orden.size(); i++) {
                        Orden or = (Orden) orden.get(i);
                        boolean ac = false;
                        if (!(bOrden.getValue().isEmpty())) {
                            if (or.getCodOrd() != null) {
                                if (or.getCodOrd() == (Long.parseLong(bOrden.getValue()))) {
                                    ac = true;
                                } else {
                                    if (or.getId() == (Long.parseLong(bOrden.getValue()))) {
                                        ac = true;
                                    }
                                }
                            } else {
                                if (or.getId() == (Long.parseLong(bOrden.getValue()))) {
                                    ac = true;
                                }
                            }
                            if (ac) {
                                setObjOrden(or);
                                idEmpresa.setValue(or.getOrganizacion().getAbreviatura());
                                objEmpresa = or.getOrganizacion();
                                if (or.getmSolicitante() != null) {
                                    mSolicitante.setValue(or.getmSolicitante());
                                } else {
                                    mSolicitante.setValue(".");
                                }
                                CalcularEdad calcEdad = new CalcularEdad(objHistoria.getFechaNace(), or.getFecIni());
                                if (calcEdad.getAnio() == 0) {
                                    if (calcEdad.getMes() == 0) {
                                        idEdad.setValue(calcEdad.obtenerDias());
                                    } else {
                                        idEdad.setValue(calcEdad.obtenerMeses());
                                    }
                                } else {
                                    idEdad.setValue(calcEdad.obtenerAnios());
                                }
                                idOrden.setValue(or.getId().toString());
                                i = orden.size();
                                selectPracticaOrden();
                            } else {

                            }

                        }
                    }
                } else {
                    selectOrdenesXHistoria(objHistoria.getOrden());
                }
                grdDatosRegistros.setVisible(true);
                tabbox.setVisible(true);
            } else {
                Messagebox.show("Verifique los datos y complete su Información", "Información", Messagebox.OK, Messagebox.INFORMATION);
                reset();
            }
        } catch (Exception e) {
            alert("No existe historia para la orden verificar pedido AVANEX" + bHistorial.getValue().trim() + " Orden: " + bOrden.getValue().trim());
            reset();
        }
    }

    private void selectPracticaSXOrden(List practicasXOrden) throws InterruptedException {
        try {
            Window winMensaje = new Window();
            Executions.createComponents("lista-practicas.zul", winMensaje, null);
            final Label msg = new Label();
            msg.setParent(winMensaje);
            msg.setVisible(false);
            final Listbox listaPracticas = (Listbox) winMensaje.getFellow("listaPracticas", false);
            for (Object objeto : practicasXOrden) {
                boolean stp = true;
                boolean stc = true;
                Pedidos practicaXOrden = (Pedidos) objeto;
                final Listitem filaPracticaOrden = new Listitem();
                filaPracticaOrden.setValue(practicaXOrden);
                Listcell idPractic = new Listcell(practicaXOrden.getIdPractica() + "Ref " + practicaXOrden.getCodRef());
                Listcell DesPractica = new Listcell(practicaXOrden.getAbreviatura());
                Listcell cellImgEstado = new Listcell();
                final Image imageLock;
                switch (practicaXOrden.getStsTecnico()) {
                    case "IN":
                        imageLock = new Image("/images/editing.png");
                        stc = false;
                        break;
                    case "CO":
                        imageLock = new Image("/images/lock.png");
                        stc = false;
                        break;
                    case "AU":
                        imageLock = new Image("/images/ok.png");
                        stc = false;
                        stp = false;
                        break;
                    case "AR":
                        imageLock = new Image("/images/archivado.png");
                        stc = false;
                        stp = false;
                        break;
                    case "IM":
                        imageLock = new Image("/images/pdf.png");
                        stp = false;
                        stc = false;
                        break;
                    case "PE":
                        imageLock = new Image("/images/document_blank.png");
                        break;
                    default:
                        imageLock = new Image("/images/document_blank.png");
                        break;
                }
                imageLock.setParent(cellImgEstado);
                Listcell IdArea = new Listcell(practicaXOrden.getIdArea().toString());
                Listcell AreaDescripcion = new Listcell(practicaXOrden.getDescripcion());
                filaPracticaOrden.appendChild(IdArea);
                filaPracticaOrden.appendChild(AreaDescripcion);
                filaPracticaOrden.appendChild(cellImgEstado);
                filaPracticaOrden.appendChild(DesPractica);
                filaPracticaOrden.appendChild(idPractic);
                if (practicaXOrden.getLockReg() == 0) {
                    filaPracticaOrden.setParent(listaPracticas);
                }
            }
            listaPracticas.addEventListener(Events.ON_SELECT, new EventListener() {

                @Override
                public void onEvent(Event event) throws Exception {
                    Listitem itemPractica = listaPracticas.getSelectedItem();
                    PracticaInforme = (Pedidos) itemPractica.getValue();
                    findInformesLoad();
                    Window aux;
                    aux = (Window) msg.getParent();
                    aux.onClose();
                }
            });
            winMensaje.setWidth("500px");
            winMensaje.setTitle("Ordenes");
            winMensaje.setClosable(true);
            winMensaje.setId("winMsgListaPracticas");
            winMensaje.setParent(WinHistoria);
            winMensaje.doModal();
        } catch (ComponentNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void selectOrdenesXHistoria(List OrdenesXHistoria) {
        try {
            if (OrdenesXHistoria.size() > 0) {
                Window winMensaje = new Window();
                Executions.createComponents("lista-ordenes.zul", winMensaje, null);
                final Label msg = new Label();
                msg.setParent(winMensaje);
                msg.setVisible(false);
                final Listbox listaOrdenes = (Listbox) winMensaje.getFellow("listaOrdenes", false);
                for (Object objOrdenHistoria : OrdenesXHistoria) {
                    String emp;
                    Orden idOrdenLista = (Orden) objOrdenHistoria;
                    final Listitem filaOrdenHistoria = new Listitem();
                    filaOrdenHistoria.setValue(idOrdenLista);
                    Listcell orden = new Listcell(idOrdenLista.getId().toString());
                    Listcell ordenFecha = new Listcell(formato.format(idOrdenLista.getFecIni()));
                    if ((idOrdenLista.getOrganizacion() == null) || (idOrdenLista.getOrganizacion().getAbreviatura() == null)) {
                        emp = "";
                    } else {
                        emp = idOrdenLista.getOrganizacion().getAbreviatura();
                    }
                    Listcell empresa = new Listcell(emp);
                    filaOrdenHistoria.appendChild(orden);
                    filaOrdenHistoria.appendChild(ordenFecha);
                    filaOrdenHistoria.appendChild(empresa);
                    filaOrdenHistoria.setParent(listaOrdenes);
                }
                listaOrdenes.addEventListener(Events.ON_SELECT, new EventListener() {

                    @Override
                    public void onEvent(Event event) throws Exception {
                        Listitem a = listaOrdenes.getSelectedItem();
                        Orden idOrdenLista = a.getValue();
                        if (!(idOrdenLista.getOrganizacion() == null)) {
                            setObjOrden(idOrdenLista);
                            bOrden.setValue(idOrdenLista.getId().toString());
                            bOrden.setDisabled(true);
                            idOrden.setValue(idOrdenLista.getId().toString());
                            idEmpresa.setValue(idOrdenLista.getOrganizacion().getAbreviatura());
                            objEmpresa = idOrdenLista.getOrganizacion();
                            if (idOrdenLista.getmSolicitante() != null) {
                                mSolicitante.setValue(idOrdenLista.getmSolicitante());
                            } else {
                                mSolicitante.setValue(".");
                            }
                            CalcularEdad calcEdad = new CalcularEdad(objHistoria.getFechaNace(), idOrdenLista.getFecIni());
                            if (calcEdad.getAnio() == 0) {
                                if (calcEdad.getMes() == 0) {
                                    idEdad.setValue(calcEdad.obtenerDias());
                                } else {
                                    idEdad.setValue(calcEdad.obtenerMeses());
                                }
                            } else {
                                idEdad.setValue(calcEdad.obtenerAnios());
                            }
                            idOrden.setValue(idOrdenLista.getId().toString());
                            selectPracticaOrden();
                        } else {
                            Messagebox.show("Verifique los datos de la Orden y complete su Información", "Información", Messagebox.OK, Messagebox.INFORMATION);
                            reset();
                        }
                        Window aux;
                        aux = (Window) msg.getParent();
                        aux.onClose();
                    }
                });
                winMensaje.setWidth("500px");
                winMensaje.setTitle("Ordenes");
                winMensaje.setId("winMsgListaOrdenes");
                winMensaje.setParent(WinHistoria);
                winMensaje.doModal();
            } else {
                Messagebox.show(new AdmMensajes(TipoMensaje.ERROR_BUSCAR_VACIO).getMessage(TipoMensaje.ERROR_BUSCAR_VACIO), "INFORMACION", Messagebox.OK, Messagebox.INFORMATION);
                reset();
            }
        } catch (ComponentNotFoundException e) {
            System.out.println(usuario + "selectOrdenesXHistoria 707");
            throw new RuntimeException(e);
        }
    }

    private void selectPracticaOrden() throws NamingException, InterruptedException {
        Map<String, Object> wSQL = new HashMap<>();
        List<String> predi = new ArrayList<>();
        int tipo = 6;
        if (!bOrden.getValue().isEmpty()) {
            wSQL.put("idOrden", objOrden.getId());
            predi.add("p.idOrden = :idOrden");
        }
        if (area.getDescripcion() != null) {
            if (area.getId() != 0) {
                wSQL.put("idArea", area.getId());
                predi.add("p.idArea=:idArea");
            }
        }
        if (!bOrden.getValue().isEmpty()) {
            wSQL.put("idHistoria", Integer.parseInt(idHistoria.getValue()));
            predi.add("p.idHistoria= :idHistoria");
        }
        wSQL.put("lockReg", 0);
        predi.add("p.lockReg= :lockReg");
        String orden = "p.abreviatura";
        List practicasXOrden = admNegocio.getDataVista(wSQL, tipo, orden, true, predi);
        banIMPAntecedentes = false;
        if (practicasXOrden.size() == 1) {
            bOrden.setDisabled(true);
            bHistorial.setDisabled(true);
            PracticaInforme = (Pedidos) practicasXOrden.get(0);
            findInformesLoad();
        } else if (practicasXOrden.isEmpty()) {
            reset();
            Messagebox.show("No se encontraron registros para la busqueda: " + wSQL.toString());
        } else {
            selectPracticaSXOrden(practicasXOrden);
            bOrden.setDisabled(true);
            bHistorial.setDisabled(true);
        }
    }
    private Document docAnteHis;

    private void findInformesLoad() throws InterruptedException, NamingException {
        docAnteHis = null;
        nomPractica.setValue(PracticaInforme.getAbreviatura());
        nomPractica.setVisible(true);
        banModificar = false;
        IdPractica.setValue(PracticaInforme.getIdPractica().toString());
        Map<String, Object> wSQL = new HashMap<>();
        wSQL.put("practica.id ?=", PracticaInforme.getIdPractica());
        wSQL.put("orden.id ?=", PracticaInforme.getIdOrden());
        XMLResultados = admNegocio.getData(new XmlResultado(), wSQL, null, null);
        int size = XMLResultados.size();
        switch (size) {
            case 0: {
                //nuevo
                if ((PracticaInforme.getStsTecnico().equalsIgnoreCase("PE")) || (PracticaInforme.getStsTecnico().equalsIgnoreCase("IN")) || (PracticaInforme.getStsTecnico().equalsIgnoreCase("CM"))) {
                    if (carga) {
                        Window winEdit = new Window();
                        Executions.createComponents("msg_upload.zul", winEdit, null);
                        final Label msg = new Label();
                        msg.setParent(winEdit);
                        msg.setVisible(false);
                        Button edit = (Button) winEdit.getFellow("MsgUpdate", false);
                        edit.addEventListener(Events.ON_UPLOAD, new EventListener() {
                            @Override
                            public void onEvent(Event event) throws Exception {
                                if (event instanceof UploadEvent) {
                                    Media media = ((UploadEvent) event).getMedia();
                                    if (media != null) {
                                        BigInteger y = BigInteger.valueOf(Long.parseLong(idOrden.getValue()));
                                        Map<String, Object> wSQL = new HashMap<>();
                                        wSQL = new HashMap<>();
                                        wSQL.put("id ?=", PracticaInforme.getIdPractica());
                                        admNegocio = new AdmiNegocio();
                                        NombreP practica = (NombreP) (admNegocio.getData(new NombreP(), wSQL, null, null).get(0));
                                        XmlResultado obj = new XmlResultado();
                                        obj.setMedico(medico.getNombre() + " " + medico.getApellido());
                                        obj.setEmpresa(idEmpresa.getValue());
                                        obj.setFecha(new Date());
                                        obj.setEstado("IM");
                                        obj.setMedicos(medico);
                                        obj.setHora(new Date());
                                        obj.setPractica(practica);
                                        obj.setResultado("PDF");
                                        obj.setFirstUser(usuario);
                                        wSQL = new HashMap<>();
                                        wSQL.put("id ?=", PracticaInforme.getId().longValue());
                                        PracticaXOrden pxo = (PracticaXOrden) (admNegocio.getDataObj(new PracticaXOrden(), wSQL, null, null));
                                        pxo.setStsTecnico("IM");
                                        ResultadoGrafico pdf = new ResultadoGrafico();
                                        byte[] imagen = media.getByteData();
                                        pdf.setCod("DIG");
                                        pdf.setFirstUser(usuario);
                                        pdf.setDato(imagen);
                                        pdf.setDescripcion("DIGITALIZADO");
                                        pdf.setLockReg(new Short("0"));
                                        try {
                                            obj.setHistoria(new Historia(PracticaInforme.getIdHistoria().longValue()));
                                            obj.setOrden(new Orden(PracticaInforme.getIdOrden().longValue()));
                                            System.out.println("setOrden" + obj.getOrden());
                                            obj.setNroOrd(PracticaInforme.getCodOrd());
                                            obj.setIdOrdenNextlab(PracticaInforme.getCodOrd());
                                            //ESTABA COMENTADO  obj.setIdOrden(PracticaInforme.getIdOrden()); yo le descomente
                                            obj.setIdOrden(PracticaInforme.getIdOrden());
                                            System.out.println("objIdOrden" + obj.getIdOrden());
                                            obj = (XmlResultado) admNegocio.guardar(obj);
                                            admNegocio.actualizar(pxo);
                                            pdf.setIdXmlResultado(obj.getId());
                                            pdf = (ResultadoGrafico) admNegocio.guardar(pdf);
                                            if (pdf != null) {
                                                reset();
                                                Messagebox.show("Archivo digitalizado", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
                                            } else {
                                            }
                                        } catch (Exception e) {
                                            Messagebox.show("No se pudo guarda el archivo", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
                                        }
                                    }
                                }
                                Window aux;
                                aux = (Window) msg.getParent();
                                aux.onClose();
                            }
                        });
                        Button cancel = (Button) winEdit.getFellow("MsgCancel", false);
                        cancel.addEventListener(Events.ON_CLICK, new EventListener() {

                            @Override
                            public void onEvent(Event event) throws Exception {
                                Messagebox.show("No dispone permisos para Informar " + wSQL.toString());
                                reset();
//                                Window aux;
//                                aux = (Window) msg.getParent();
//                                aux.onClose();
//                                if (PracticaInforme.getPerImpa() == 0) {
//                                    Long obj = PracticaInforme.getIdEmpresa().longValue();
//                                    Long id = Long.parseLong("128");
//                                    if (Objects.equals(obj, id)) {
//                                        //nuevo y de halliburton busco historial
//                                        Map<String, Object> wSQL1 = new HashMap<>();
//                                        wSQL1.put("empresa ?like", "HALLIBURTON");
//                                        wSQL1.put("historia.id ?=", PracticaInforme.getIdHistoria());
//                                        wSQL1.put("practica.id ?=", 580);
//                                        List oSQL = new ArrayList<>();
//                                        oSQL.add("idOrden");
//                                        List histos = admNegocio.getData(new XmlResultado(), wSQL1, null, oSQL);
//                                        if (histos.size() > 0) {
//                                            XmlResultado histo = (XmlResultado) histos.get(0);
//                                            if ((histo.getPractica().getId() == 580) && (PracticaInforme.getIdPractica() == 1258)) {//obtengo las 3 primeras hojas 
//                                                String hisAntece = histo.getResultado();
//                                                int u = hisAntece.indexOf("<d");
//                                                int d = hisAntece.indexOf("<pag");
//                                                int t = hisAntece.indexOf("</pagina2>");
//                                                String g = hisAntece.substring(0, u) + hisAntece.substring(d, t) + "</pagina2> </resultados>";
//                                                docAnteHis = ConvertirDocumento.getConvertirDocumentoDocument(g);
//                                                Messagebox.show("CONFIRMAR ANTECEDENTES RECUPERADOS!!! " + histo.getFecIni(), "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//                                            }
//                                        }
//                                    }
//                                    //buscar antecedentes
//                                    Map<String, Object> wSQL = new HashMap<>();
//                                    wSQL.put("idPractica ?=", IdPractica.getValue());
//                                    wSQL.put("idEmpresa ?=", PracticaInforme.getIdEmpresa());
//                                    wSQL.put("idHistoria.id ?=", PracticaInforme.getIdHistoria());
//                                    wSQL.put("lockReg ?=", 0);
//                                    List oSQL = new ArrayList();
//                                    oSQL.add("orden.id");
//                                    List XMLAntecentes = admNegocio.getDataLimit(new XmlAntecedentes(), wSQL, oSQL, null, 1);
//                                    if (XMLAntecentes.size() > 0) {
//                                        XmlAntecedentes antecede = (XmlAntecedentes) XMLAntecentes.get(0);
//                                        //obtengo antecedentes hidtoria empresa
//                                        String hisAntece = antecede.getAntecedentes();
//                                        int u = hisAntece.indexOf("<d");
//                                        int d = hisAntece.indexOf("<pag");
//                                        int t = hisAntece.indexOf("</pagina2>");
//                                        String g = hisAntece.substring(0, u) + hisAntece.substring(d, t) + "</pagina2> </resultados>";
//                                        docAnteHis = ConvertirDocumento.getConvertirDocumentoDocument(g);
//                                        Messagebox.show("CONFIRMAR ANTECEDENTES HISTORIAL!!! " + "(ant)" + PracticaInforme.getDescripcion() + " " + PracticaInforme.getIdOrden(), "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//                                    }
//                                }
//                                if (EntradasInforme()) {
//                                    carga();
//                                    timerRestart();
//                                } else {
//                                }
                            }
                        });
                        winEdit.setId("winEditarHistoria");
                        winEdit.setParent(WinHistoria);
                        winEdit.doModal();
                    } else {
                        Messagebox.show("No dispone permisos para Informar " + wSQL.toString());
                        reset();
//                        if (PracticaInforme.getPerImpa() == 0) {
//                            wSQL = new HashMap<>();
//                            wSQL.put("idPractica ?=", IdPractica.getValue());
//                            wSQL.put("idEmpresa ?=", PracticaInforme.getIdEmpresa());
//                            wSQL.put("idHistoria.id ?=", PracticaInforme.getIdHistoria());
//                            wSQL.put("lockReg ?=", 0);
//                            List oSQL = new ArrayList<>();
//                            oSQL.add("orden.id");
//                            List XMLAntecentes = admNegocio.getData(new XmlAntecedentes(), wSQL, oSQL, null);
//                            if (XMLAntecentes.size() > 0) {
//                                int nu = XMLAntecentes.size();
//                                XmlAntecedentes ante = (XmlAntecedentes) XMLAntecentes.get(nu - 1);
//                                String Antece = ante.getAntecedentes();
//                                int u = Antece.indexOf("<d");
//                                int d = Antece.indexOf("<pag");
//                                // int t = Antece.indexOf("</pagina2>");
//                                String g = Antece.substring(0, u) + Antece.substring(d, Antece.length());
//                                docAnteHis = ConvertirDocumento.getConvertirDocumentoDocument(g);
//                                Messagebox.show("CONFIRMAR ANTECEDENTES HISTORIAL!!! " + PracticaInforme.getDescripcion() + " " + ante.getOrden().getId() + " (ant) ", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//                            }
//                            if (docAnteHis == null) {
//                                Long obj = PracticaInforme.getIdEmpresa().longValue();
//                                Long id = Long.parseLong("128");
//                                if (Objects.equals(obj, id)) {
//                                    //nuevo y de halliburton busco historial
//                                    Map<String, Object> wSQL1 = new HashMap<>();
//                                    wSQL1.put("empresa ?like", "HALLIBURTON");
//                                    wSQL1.put("historia.id ?=", PracticaInforme.getIdHistoria());
//                                    wSQL1.put("practica.id ?=", 580);
//                                    oSQL = new ArrayList<>();
//                                    oSQL.add("idOrden");
//                                    List histos = admNegocio.getData(new XmlResultado(), wSQL1, null, oSQL);
//                                    if (histos.size() > 0) {
//                                        XmlResultado histo = (XmlResultado) histos.get(0);
//                                        if (histo.getPractica().getId() == 580) {//obtengo las 3 primeras hojas 
//                                            String hisAntece = histo.getResultado();
//                                            int u = hisAntece.indexOf("<d");
//                                            int d = hisAntece.indexOf("<pag");
//                                            int t = hisAntece.indexOf("</pagina2>");
//                                            String g = hisAntece.substring(0, u) + hisAntece.substring(d, t) + "</pagina2> </resultados>";
//                                            docAnteHis = ConvertirDocumento.getConvertirDocumentoDocument(g);
//                                            Messagebox.show("CONFIRMAR ANTECEDENTES RECUPERADOS!!! " + histo.getOrden().getId() + "(his)", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        if (EntradasInforme()) {
//                            carga();
//                            timerRestart();
//                        } else {
//                        }
                    }
                } else {
                    Messagebox.show("VERIFICAR ESTADO PRACTICA " + PracticaInforme.getStsTecnico(), "Informativo", Messagebox.OK, Messagebox.INFORMATION);
                    reset();
                }
            }
            break;
            case 1: {
                Messagebox.show("No dispone permisos para Informar " + wSQL.toString());
                reset();
//                resultadoXML = (XmlResultado) XMLResultados.get(0);
//                if (!resultadoXML.getEmpresa().equalsIgnoreCase("RIS")) {
//                    if ((resultadoXML.getEmpresa().equalsIgnoreCase("ECUAAMERICAN") && (impr))) {
//                        alert("No dispone permisos empresa privada");
//                        reset();
//                    } else {
//                        if (resultadoXML.getEstado().equalsIgnoreCase("IM")) {
//                            Messagebox.show("INFORME DIGITALIZADO", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//                            reset();
//                        } else {
//                            if (ResultadosInforme()) {
//                                carga();
//                            } else {
//                                reset();
//                            }
//
//                        }
//                    }
//                } else {
//                    Messagebox.show("INFORME BLOQUEADO PARA AVASUS", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
//                    reset();
//                }
            }
            break;
            default: {
                reset();
                Messagebox.show("Informe existente o verificar estado ext 722", "Informativo", Messagebox.OK, Messagebox.INFORMATION);
            }
        }
        btnBuscar.setDisabled(true);
    }

    public void carga() {
        try {
            gridInforme.renderAll();
            gridInforme.invalidate();
            gridInforme.setVisible(true);
            final Include arbol = new Include("/templates/Arbol.zul");
            ProcesosSession admiSessionUsuario = new ProcesosSession();
            sessionOk objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
            objsessiActica.setObjHistoria(getObjHistoria());
            admiSessionUsuario.AgregarAtributoSession(2, objsessiActica, desktop.getSession());
            east.setVisible(true);
            east.appendChild(arbol);
        } catch (Exception e) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.carga()");
        }

    }

    public void onSelect$LbxAreas() {
        area = (Area) LbxAreas.getSelectedItem().getValue();
        bbAreas.setValue(area.getDescripcion());
        if (area.getId() == 0) {
        }
        bbAreas.close();
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
    }

    private void lockCab() {
        bbAreas.setDisabled(true);
        bHistorial.setDisabled(true);
        bOrden.setDisabled(true);
        if (banIMPAntecedentes) {
            findAntecedentesEmpresa();
        } else {
            TabAntecedentes.setDisabled(true);
            TabHistoria.setSelected(true);;
        }
    }

    private boolean hayFormatoEmpresa() {
        Map<String, Object> wSQL = new HashMap<>();
        List oSQL = new ArrayList();
        wSQL.put("idPractica ?=", PracticaInforme.getIdPractica());
        if (getObjOrden().getOrganizacion().getCodRef() > 0) {
            wSQL.put("idEmpresa ?=", getObjOrden().getOrganizacion().getCodRef());
        } else {
            wSQL.put("idEmpresa ?=", getObjOrden().getOrganizacion().getId());
        }
        //    wSQL.put("idEmpresa ?=", getObjOrden().getOrganizacion().getId());
        wSQL.put("lockReg ?=", 0);
        oSQL.add("idHoja");
        List<Object> Formatos = null;
        try {
            Formatos = admNegocio.getDataAsc(new FormatoXAntecedentes(), wSQL, null, oSQL);
        } catch (NamingException ex) {
            System.out.println(usuario + "  Formatos = admNegocio.getDataAsc(new FormatoXPractica(), wSQL, null, oSQL); " + bOrden.getValue() + ex.getMessage());
            Logger.getLogger(ControladoraHistoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (!Formatos.isEmpty()) {
            return true;
        } else {
            //  alert(PracticaInforme.getIdPractica()+" / "+getObjOrden().getOrganizacion().getId()+" 0");
            return false;
        }
    }

    private void findAntecedentesEmpresa() {
        try {//            if (!(Formatos.isEmpty())) {
            if (hayFormatoEmpresa()) {
                loadAntecedentes();
                TabAntecedentes.setStyle("color:red;");
                TabAntecedentes.setDisabled(false);
                TabAntecedentes.setSelected(true);
                banAntecedentes = true;
            } else {
                TabAntecedentes.setDisabled(true);
                TabHistoria.setSelected(true);
            }
        } catch (Exception e) {
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PROCESOS GENERALES">
    private AMedia loadReportVista(String pa, Map<String, Object> wSQLA, Map<String, Object> wSQLI, boolean forceVisible) {
        iReport reportes = new iReport();
        boolean impre = false;
        if (impr) {
            impre = true;
        }
        if (forceVisible) {
            impre = true;
        }
        AMedia media = null;
        try {
            byte[] buf = null;
            if ((banAntecedentes) && (banIMPAntecedentes)) {
                try {
                    buf = reportes.getReportCompleto(wSQLA, wSQLI, "r.idOrden", forceVisible, impre);
                } catch (Exception e) {
                    alert("No se puede visualizar");
                }
            } else {
                try {
                    buf = reportes.getReportCompleto(wSQLA, wSQLI, "r.idOrden", forceVisible, impre);
                } catch (Exception e) {
                    alert("No se puede visualizar");
                }

            }
            if (buf != null) {
                InputStream mediaIS = new ByteArrayInputStream(buf);
                media = new AMedia(pa + ".pdf", "pdf", "application/pdf", mediaIS);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return media;
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="BOTONES">
    private void eventGuardar() {
        try {
            if (banAntecedentes) {
                Textbox modifica = (Textbox) anteV.getFellow("modificar", false);
                if (modifica.getValue().equalsIgnoreCase("T")) {//modificar
                    ProcesosSession admiSessionUsuario = new ProcesosSession();
                    sessionOk objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
                    setXMLantecedentes(objsessiActica.getObjXmlAntecedentes());
                    guardarAntecedentes(true, "CO");
                } else {
                    if (modifica.getValue().equalsIgnoreCase("N")) {//nuevo
                        guardarAntecedentes(false, "CO");
                    } else {
                        if (modifica.getValue().equalsIgnoreCase("F")) {//no hay antece
                        }
                    }

                }
            }
        } catch (Exception e) {
            System.out.println("guardarAntecedentes");
        }
        try {
            eventGuardarInforme();
        } catch (Exception e) {
            System.out.println("eventGuardarInforme");
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="HISTORIA">
    private String ocupa, depar, orige, grupo_r;
    Document newDoc, DocAnte;
    private boolean visibleFrameCarga, editAudiometria, completo, banModificar;
    private XmlResultado resultadoXML;
    private Organizacion objEmpresa;
    private Tab TabHistoria;
    private ArrayList diaTotal;
    private ArrayList diagobs;
    private int numFormatosInforme;
    private Grid gridInforme;
    private Rows rowsInforme;
    private String diagnosticoXml = "", recomendacionesXml = "", prescripcionesXml = "", ocupacion = "";
    boolean fin;

    private void eventGuardarInforme() {
        try {
            fin = false;
            try {
                crearDirectorioPrincipal();
            } catch (Exception e) {
                System.out.println(usuario + "Error crearDirectorioPrincipal()" + bOrden.getValue());
            }
            CreacionXml admXml;
            try {
                admXml = new CreacionXml();
                newDoc = admXml.crearDocumento();
            } catch (Exception e) {
                alert(usuario + "Error new CreacionXml() + crearDocumento()" + bOrden.getValue());
                System.out.println(usuario + "Error new CreacionXml() + crearDocumento()" + bOrden.getValue());
            }
            try {
                try {
                    newDoc = XMLInforme(newDoc);
                } catch (Exception e) {
                    alert(usuario + "Error CreamosXMLGuardar(newDoc)" + bOrden.getValue());
                    System.out.println(usuario + "Error CreamosXMLGuardar(newDoc)" + bOrden.getValue());
                }
                int numNodo = newDoc.getChildNodes().item(0).getChildNodes().getLength();
                if (numNodo == numFormatosInforme) {
                    Window winMensaje = new Window();
                    String windowMessage;
                    if ((completo)) {
                        windowMessage = "msg_completo.zul";
                    } else {
                        windowMessage = "msg_incompleto.zul";
                    }
                    Executions.createComponents(windowMessage, winMensaje, null);
                    final Label msg = new Label();
                    msg.setParent(winMensaje);
                    msg.setVisible(false);
                    Button cbMsgSaveI = (Button) winMensaje.getFellow("cbMsgSaveI", false);
                    cbMsgSaveI.addEventListener("onClick", new EventListener() {

                        @Override
                        public void onEvent(Event e) throws Exception {
                            guardarObjetos("IN", true, true, true);
                            reset();
                            Window aux;
                            aux = (Window) msg.getParent();
                            aux.onClose();
                        }
                    });
                    Button cbMsgSave = (Button) winMensaje.getFellow("cbMsgSave", false);
                    cbMsgSave.addEventListener("onClick", new EventListener() {
                        @Override
                        public void onEvent(Event e) throws Exception {
                            guardarObjetos("CO", true, true, true);
                            reset();
                            System.out.println("save2");
                            Window aux;
                            aux = (Window) msg.getParent();
                            aux.onClose();

                        }
                    });
                    Button cbMsgSavePrint = (Button) winMensaje.getFellow("cbMsgSavePrint", false);
                    cbMsgSavePrint.addEventListener("onClick", new EventListener() {
                        @Override
                        public void onEvent(Event e) throws Exception {
                            //Cierro la ventana auxiliar
                            //  System.out.println("save");
                            Window aux;
                            aux = (Window) msg.getParent();
                            aux.onClose();
                            if (!guardarObjetos("CO", true, true, true)) {
                                return;
                            }
                            Window winMensaje = new Window();
                            String windowMessage = "msg_preview.zul";
                            Executions.createComponents(windowMessage, winMensaje, null);
                            winMensaje.setBorder("normal");
                            //winMensaje.setAction("show: slideDown;hide: slideUp");
                            winMensaje.setClosable(true);
                            winMensaje.setTitle("Vista Informe");
                            final Label msg = new Label();
                            msg.setParent(winMensaje);
                            msg.setVisible(false);
                            final Iframe frameReporte = (Iframe) winMensaje.getFellow("reporteV", false);
                            Map<String, Object> wSQL = new HashMap<>();
                            String pat = "O" + resultadoXML.getOrden().getId() + "P" + resultadoXML.getPractica().getId();
                            wSQL.put("idOrden", resultadoXML.getOrden().getId());
                            wSQL.put("idPractica", resultadoXML.getPractica().getId());
                            Map<String, Object> wSQLA = new HashMap<>();
                            wSQLA.put("idEmpresa", resultadoXML.getOrden().getOrganizacion().getId());
                            wSQLA.put("idHistoria.id", resultadoXML.getHistoria().getId());
                            wSQLA.put("orden.id", resultadoXML.getOrden().getId());
                            wSQLA.put("idPractica", resultadoXML.getPractica().getId());
                            AMedia mediaCarga;
                            mediaCarga = loadReportVista(pat, wSQLA, wSQL, true);
                            if (mediaCarga != null) {
                                frameReporte.setContent(mediaCarga);
                                final Button validate = (Button) winMensaje.getFellow("cbMsgValidate", false);
                                validate.setDisabled(true);
                                reset();
                            } else {
                                Messagebox.show("El informe esta incompleto o no esta¡ cerrado, consulte con el medico responsable",
                                        "Información / Esta¡ incompleto el informe", Messagebox.OK, Messagebox.INFORMATION);
                            }
                            winMensaje.setId("winMsgPreview");
                            winMensaje.addEventListener("onClose", new EventListener() {

                                @Override
                                public void onEvent(Event event) throws Exception {

                                    reset();
                                }
                            });
                            winMensaje.setParent(WinHistoria);
                            winMensaje.doModal();
                        }// hasta aui
                    });
                    Button cbMsgPreview = (Button) winMensaje.getFellow("cbMsgPreview", false);
                    cbMsgPreview.addEventListener("onClick", new EventListener() {
                        @Override
                        public void onEvent(Event e) throws Exception {
//                            System.out.println("preview");
                            Window aux;
                            aux = (Window) msg.getParent();
                            aux.onClose();
                            if (!guardarObjetos("IN", false, true, true)) {
                                return;
                            } else {
                                Window winMensaje = new Window();
                                Executions.createComponents("msg_preview.zul", winMensaje, null);
                                winMensaje.setBorder("normal");
                                winMensaje.setClosable(true);
                                winMensaje.addEventListener("onClose", new EventListener() {
                                    @Override
                                    public void onEvent(Event event) throws Exception {
                                        if (!fin) {
                                            timerRestart();
                                        }
                                    }
                                });
                                winMensaje.setTitle("Vista Preliminar de Informe");
                                final Label msg = new Label();
                                msg.setParent(winMensaje);
                                msg.setVisible(false);
                                final Iframe frameReporte = (Iframe) winMensaje.getFellow("reporteV", false);
                                Map<String, Object> wSQL = new HashMap<>();
                                String pat = "O" + resultadoXML.getOrden().getId() + "P" + resultadoXML.getPractica().getId();
                                wSQL.put("idOrden", resultadoXML.getOrden().getId());
                                wSQL.put("idPractica", resultadoXML.getPractica().getId());
                                timerStop();
                                Map<String, Object> wSQLA = new HashMap<>();
                                wSQLA.put("idEmpresa", resultadoXML.getOrden().getOrganizacion().getId());
                                wSQLA.put("idHistoria.id", resultadoXML.getHistoria().getId());
                                wSQLA.put("orden.id", resultadoXML.getOrden().getId());
                                wSQLA.put("idPractica", resultadoXML.getPractica().getId());
                                AMedia mediaCarga;
                                mediaCarga = loadReportVista(pat, wSQLA, wSQL, true);
                                if (mediaCarga != null) {
                                    frameReporte.setContent(mediaCarga);
                                    final Button validate = (Button) winMensaje.getFellow("cbMsgValidate", false);
                                    validate.setDisabled(true);
                                    if ((medico.getId() == 0)) {
                                        validate.setDisabled(true);
                                    }
                                    if ((grupo.equalsIgnoreCase("MED") || (grupo.equalsIgnoreCase("LAB")) || (grupo.equalsIgnoreCase("ADMIN")))) {
                                        validate.setDisabled(false);
                                    }
                                    System.out.println("validando" + resultadoXML.getId());
                                    validate.addEventListener("onClick", new EventListener() {
                                        @Override
                                        public void onEvent(Event e) throws Exception {
                                            //      System.out.println(".onEvent()  Actualizar estado ID XML");
                                            resultadoXML.setEstado("CO");
                                            if (admNegocio.actualizar(resultadoXML)) {
                                                validate.setDisabled(true);
                                                Map<String, Object> wSQL = new HashMap<>();
                                                String pat = "O" + resultadoXML.getOrden().getId() + "P" + resultadoXML.getPractica().getId();
                                                wSQL.put("idOrden", resultadoXML.getOrden().getId());
                                                wSQL.put("idPractica", resultadoXML.getPractica().getId());
                                                Map<String, Object> wSQLA = new HashMap<>();
                                                wSQLA.put("idEmpresa", resultadoXML.getOrden().getOrganizacion().getId());
                                                wSQLA.put("idHistoria.id", resultadoXML.getHistoria().getId());
                                                wSQLA.put("orden.id", resultadoXML.getOrden().getId());
                                                wSQLA.put("idPractica", resultadoXML.getPractica().getId());
                                                AMedia mediaCarga;
                                                mediaCarga = loadReportVista(pat, wSQLA, wSQL, true);
                                                if (mediaCarga != null) {
                                                    frameReporte.setContent(mediaCarga);
                                                }
                                                fin = true;
                                                reset();
                                            } else {
                                                System.out.println("No se  puede actualizar");
                                            }

                                        }
                                    }
                                    );
                                    winMensaje.setId("winMsgPreview");
                                    winMensaje.setParent(WinHistoria);
                                    winMensaje.doModal();
                                } else {
                                    Messagebox.show("El informe esta incompleto o no esta¡ cerrado, consulte con el medico responsable",
                                            "Información / Esta¡ incompleto el informe", Messagebox.OK, Messagebox.INFORMATION);
                                }
                            }
                        }
                    });
                    if (completo) {
                        winMensaje.setId("winMsgCompleto");
                    } else {
                        winMensaje.setId("winMsgIncompleto");
                    }
                    winMensaje.setParent(WinHistoria);
                    winMensaje.doModal();
                } else {
                    System.out.println(usuario + "ERROR AL GENERAR EL INFORME  (numNodo)" + bOrden.getValue());
                    System.out.println(newDoc.getTextContent());
                    alert("ERROR AL GENERAR EL INFORME  (numNodo) " + numNodo);
                }
            } catch (DOMException | ComponentNotFoundException | WrongValueException e) {
                String g = ConvertirDocumento.getConvertirDocumentoString(newDoc);
                System.out.println(usuario + "ERROR AL GENERAR EL INFORME" + g + bOrden.getValue());
                alert("ERROR AL GENERAR EL INFORME ");
            }
        } catch (WrongValueException e) {
            alert("ERROR AL GENERAR EL INFORME (eventGuardar)" + bOrden.getValue());
        }
    }

    private void guardarDXORDEN() {
        try {
            actualizarStandar(resultadoXML);
            if (!ocupa.isEmpty()) {
                Historia obgac = resultadoXML.getHistoria();
                obgac.setOcupacion(ocupa);
                admNegocio.actualizar(obgac);
            }
            String edad = null;
            try {
                CalcularEdad calcEdad = new CalcularEdad(objHistoria.getFechaNace(), objOrden.getFecIni());
                if (calcEdad.getAnio() == 0) {
                    if (calcEdad.getMes() == 0) {
                        edad = calcEdad.obtenerDias();
                    } else {
                        edad = calcEdad.obtenerMeses();
                    }
                } else {
                    edad = calcEdad.obtenerAnios();
                }
            } catch (Exception e) {
                edad = "";
            }
            //            diaTotal//seleccion
            Map<String, Object> wSQL = new HashMap<>();
            wSQL.put("idOrden ?=", resultadoXML.getOrden().getId());
            List oSQL = new ArrayList();
            List dxx_orden = admNegocio.getData(new DxOrden(), wSQL, null, oSQL);
            if (diaTotal != null) {
                for (int i = 0; i < diaTotal.size(); i++) {
                    DxOrden diag1 = new DxOrden();
                    Element eledx = (Element) diaTotal.get(i);
                    diag1.setCodCie(eledx.getAttribute("cod"));
                    diag1.setCodRef(eledx.getAttribute("codRef"));
                    diag1.setDescripcion(eledx.getTextContent());
                    diag1.setIdHistoria(resultadoXML.getHistoria().getId());
                    diag1.setIdOrden(resultadoXML.getOrden().getId().intValue());
                    diag1.setIdPractica(resultadoXML.getPractica().getId());
                    diag1.setNombre(resultadoXML.getHistoria().getPaciente());
                    diag1.setFirstUser(resultadoXML.getFirstUser());
                    diag1.setIdXml(BigInteger.valueOf(resultadoXML.getId()));
                    i = i + 1;
                    eledx = (Element) diaTotal.get(i);
                    diag1.setDxMedico(eledx.getTextContent());
                    diag1.setEdad(edad);
                    boolean dxg = false;
                    for (Object dxx : dxx_orden) {
                        DxOrden objGDX = (DxOrden) dxx;
                        if (diag1.getCodCie().equals(objGDX.getCodCie())) {
                            objGDX.setOcupacion(ocupa);
                            objGDX.setDepartamento(depar);
                            objGDX.setOrigen(orige);
                            objGDX.setGrupoRiesgos(grupo_r);
                            dxg = true;
                            try {
                                admNegocio.actualizar(objGDX);
                            } catch (Exception e) {
                                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.guardarDXORDEN() 1200" + e.getMessage());
                            }
                        } else {
                            //siga buscando pra actualizar
                        }
                    }
                    if (!(dxg)) {
                        diag1.setOcupacion(ocupa);
                        diag1.setDepartamento(depar);
                        diag1.setOrigen(orige);
                        diag1.setGrupoRiesgos(grupo_r);
                        diag1 = (DxOrden) admNegocio.guardar(diag1);

                    }
                }
            }
        } catch (NamingException ex) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.guardarDXORDEN()" + ex.getMessage());
            Logger
                    .getLogger(ControladoraHistoria.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean guardarObjetos(String estado, boolean limpiar, boolean avisos, boolean cie) throws InterruptedException {
        XmlResultado nuevo = null;
        boolean guardarExamenD = true;
        boolean guardarExamenI = true;
        ocupa = "";
        depar = "";
        orige = "";
        grupo_r = "";
        if ((estado.equals("CO")) && (medico.getId() == 0)) {
//            boolean cop = false;
//            if (copiar) {
//                cop = true;
//            }
            if (medico.getId() > 0) {
                estado = "CO";
            } else {
                estado = "IN";
                Messagebox.show("Usted no es un Médico, por tal razón el informe quedará incompleto. \n\r" + "El informe no se cerrará hasta que un médico lo revise", "Información de usuario", Messagebox.OK, Messagebox.INFORMATION);
            }
        }
        try {
            if (banModificar) { //MODIFICAR
                resultadoXML.setMedico(medico.getNombreMedico());
                resultadoXML.setMedicos(medico);
                resultadoXML.setLastUser(usuario);
                resultadoXML.setEmpresa(idEmpresa.getValue());
                resultadoXML.setEstado(estado);
                EstadoInforme(newDoc, estado);
                int numNodo = newDoc.getChildNodes().item(0).getChildNodes().getLength();
                if (numNodo == numFormatosInforme) {
                    resultadoXML.setResultado(ConvertirDocumento.getConvertirDocumentoString(newDoc));
                    if (!diagnosticoXml.isEmpty()) {
                        resultadoXML.setDx(diagnosticoXml);
                    } else {
                        resultadoXML.setDx("");
                        //System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.guardar() diagnosticoXml.isEmpty()");
                    }
                    if (admNegocio.actualizar(resultadoXML)) {
                        if (cie) {
                            if (BanDel) {
                                Map<String, Object> wSQL = new HashMap<>();
                                wSQL.put("idOrden ?=", resultadoXML.getOrden().getId());
                                wSQL.put("idPractica ?=", resultadoXML.getPractica().getId());
                                List oSQL = new ArrayList();
                                List diagnosti = admNegocio.getData(new DxOrden(), wSQL, null, oSQL);
                                for (Object diagno : diagnosti) {
                                    if (admNegocio.eliminar(diagno)) {
                                        System.out.println(usuario + " Eliminado Diagnostico" + bOrden.getValue() + usuario);
                                    }
                                }
                            }
                            guardarDXORDEN();
                        }
                        if (editAudiometria) {
                            List listgraficos = getCObjetos(new ResultadoGrafico(), "idXmlResultado", resultadoXML.getId(), false, true);
                            for (Object object : listgraficos) {
                                ResultadoGrafico objElimina = (ResultadoGrafico) object;
                                admNegocio.eliminar(objElimina);
                            }
                            guardarExamenD = saveAudiometria(resultadoXML.getId(), "DER");
                            guardarExamenI = saveAudiometria(resultadoXML.getId(), "IZQ");
                            editAudiometria = false;
                        }

                    } else {
                        throw new RuntimeException("Error al guardar el informe , Fallo de conección / actualización");
                    }
                } else {
                    Messagebox.show("El informe  no fue actualizado reporte el mensaje por favor", "Error", Messagebox.OK, Messagebox.ERROR);
                    return false;
                }
            } else { //NUEVO
                setNewXmlResultado();
                resultadoXML.setEstado(estado);
                EstadoInforme(newDoc, estado);
                int nodos = newDoc.getChildNodes().item(0).getChildNodes().getLength();
                if (nodos == numFormatosInforme) {
                    resultadoXML.setResultado(ConvertirDocumento.getConvertirDocumentoString(newDoc));
                    if (resultadoXML.getResultado() != null) {
                        if (!diagnosticoXml.isEmpty()) {
                            resultadoXML.setDx(diagnosticoXml);
                        }
                        banModificar = true;
                        nuevo = (XmlResultado) admNegocio.guardar(resultadoXML);
                        //actualiza ped
                        resultadoXML = nuevo;
                    } else {
                        Messagebox.show("No se puede crear Documento", "Error", Messagebox.OK, Messagebox.ERROR);
                    }

                    if (cie) {
                        if (BanDel) {
                            Map<String, Object> wSQL = new HashMap<>();
                            wSQL.put("idOrden ?=", resultadoXML.getOrden().getId());
                            wSQL.put("idPractica ?=", resultadoXML.getPractica().getId());
                            List oSQL = new ArrayList();
                            List diagnosti = admNegocio.getData(new DxOrden(), wSQL, null, oSQL);
                            for (Object diagno : diagnosti) {
                                if (admNegocio.eliminar(diagno)) {
                                    System.out.println(usuario + " Eliminado Diagnostico" + bOrden.getValue() + usuario);
                                }
                            }
                        }
                        guardarDXORDEN();
                    }
                } else {
                    Messagebox.show("Resultado del Informe no fue Modificado reporte el Mensaje", "Información", Messagebox.OK, Messagebox.INFORMATION);
                    return false;
                }
                if ((nuevo != null) && (editAudiometria)) {
                    guardarExamenD = saveAudiometria(nuevo.getId(), "DER");
                    guardarExamenI = saveAudiometria(nuevo.getId(), "IZQ");
                    editAudiometria = false;
                }
            }
        } catch (NamingException | IOException | RuntimeException e) {
            reset();
            throw new RuntimeException(e);
        } finally {
            if (!(guardarExamenD) || (!(guardarExamenI))) {
                Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.INCOMPLETO) + " Resultado Examen ", "Información", Messagebox.OK, Messagebox.ERROR);
                return false;
            } else {
                if (limpiar && avisos) {
                    Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.GRABAR), "Informacion", Messagebox.OK, Messagebox.INFORMATION);

                }
            }
        }
        return true;
    }

    private void setNewXmlResultado() throws NamingException {
        resultadoXML = new XmlResultado();
        try {
            resultadoXML.setMedico(medico.getNombreMedico());
        } catch (Exception e) {
            resultadoXML.setMedico("");
        }
        try {
            resultadoXML.setCodAna(PracticaInforme.getIdNombrep().toString());
        } catch (Exception e) {
            //   alert("Registrar Cod Analisis Informe "+ PracticaInforme.getIdNombrep());
        }

        resultadoXML.setNroOrd(getObjOrden().getCodOrd());
        resultadoXML.setIdOrdenNextlab(getObjOrden().getCodOrd());
        resultadoXML.setCodPac(getObjHistoria().getIdNextlab());
        resultadoXML.setCodOri(getObjOrden().getCodOri());
        resultadoXML.setDesOri(getObjOrden().getDesOri());
        resultadoXML.setMedicos(medico);
        resultadoXML.setEmpresa(idEmpresa.getValue());
        resultadoXML.setFecha(new Date());
        resultadoXML.setHistoria(getObjHistoria());
        resultadoXML.setOrden(getObjOrden());
        resultadoXML.setHora(new Date());
        resultadoXML.setPractica(new NombreP(PracticaInforme.getIdPractica()));
        resultadoXML.setFirstUser(usuario);
    }

    private void EstadoInforme(Document doc, String estado) {
        NodeList listaHijos = newDoc.getChildNodes();
        listaHijos = listaHijos.item(0).getChildNodes();
        Node NPagina;
        NPagina = listaHijos.item(0);
        listaHijos = listaHijos.item(0).getChildNodes();
        try {
            if (NPagina.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoP = (Element) NPagina;
                if (elementoP.getNodeName() != null) {
                    String NamNText = elementoP.getTagName();
                    if (NamNText.equals("datos_generales_standar")) {
                        Element elementoI = doc.createElement("estado"); //creamos un nuevo elemento
                        elementoI.setAttribute("orden", "0");
                        elementoI.setAttribute("tipo_dato", "L");
                        elementoI.setAttribute("descripcion", "ESTADO");
                        elementoI.appendChild(doc.createTextNode(estado));
                        elementoP.appendChild(elementoI);
                    }
                }
            }
        } catch (DOMException e) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.EstadoInforme()" + e.getMessage());
        }
    }
    boolean stado;

    private boolean ResultadosInforme() {
        try {
            stado = false;
            if (((hayFormatoEmpresa()) && (PracticaInforme.getPerImpa() == 0)) || ((PracticaInforme.getPerImpa() == 1))) {
                if (resultadoXML.getEstado().equals("IN")) {
                    stado = true;
                    loadGridResultados(true);
                }
                if ((resultadoXML.getEstado().equals("CO")) || (resultadoXML.getEstado().equals("AR"))) {
                    final Label g = new Label("false");
                    boolean peredit = false;
                    if (edit) {
                        peredit = true;
                        try {
                            Window winEdit = new Window();
                            Executions.createComponents("msg_edicion.zul", winEdit, null);
                            final Label msg = new Label();
                            msg.setParent(winEdit);
                            msg.setVisible(false);
                            Button edit = (Button) winEdit.getFellow("MsgEditar", false);
                            edit.addEventListener(Events.ON_CLICK, new EventListener() {

                                @Override
                                public void onEvent(Event e) throws Exception {
                                    stado = true;
                                    loadGridResultados(true);

                                    Window aux;
                                    aux = (Window) msg.getParent();
                                    aux.onClose();
                                }
                            });
                            Button cancel = (Button) winEdit.getFellow("MsgCancel", false);
                            cancel.addEventListener(Events.ON_CLICK, new EventListener() {
                                @Override
                                public void onEvent(Event e) throws Exception {
                                    btnGuardar.setDisabled(true);
                                    stado = false;
                                    loadGridResultados(false);
                                    Window aux;
                                    aux = (Window) msg.getParent();
                                    aux.onClose();
                                }
                            });
                            winEdit.setId("winEditarHistoria");
                            winEdit.setParent(WinHistoria);
                            winEdit.doModal();
                        } catch (SuspendNotAllowedException e) {
                            e.printStackTrace(System.out);
                        }
                    } else {
                    }
                    if (!peredit) {
                        Messagebox.show("No tiene permisos para modificar", "Información de usuario", Messagebox.OK, Messagebox.INFORMATION);
                        reset();
                    }
                    if (resultadoXML.getEstado().equals("AU")) {
                        reset();
                        Messagebox.show("El informe se encuentra auditado", "Información de usuario", Messagebox.OK, Messagebox.INFORMATION);

                    } else if (resultadoXML.getEstado().equals("AR")) {
                        reset();
                        Messagebox.show("El informe se encuentra archivado", "Información de usuario", Messagebox.OK, Messagebox.INFORMATION);
                    }
                }
                return true;
            } else {
                return false;
            }
        } catch (ComponentNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Document DatosGeneralesInforme(Document doc) {
        try {
            Element grpDatos;
            Element elemento;
            String x1 = "datos_generales_standar";
            grpDatos = doc.createElement(x1); // creamos el elemento raiz
            doc.getDocumentElement().appendChild(grpDatos); //pegamos la raiz al documento
            //ORIGEN Pais
            elemento = doc.createElement("pais_codigo"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "T");
            elemento.setAttribute("descripcion", "Codigo");
            try {
                elemento.appendChild(doc.createTextNode(ciudad.getIdIso2().getIso31661().getAlfa3() != null ? ciudad.getIdIso2().getIso31661().getAlfa3() : ""));
            } catch (DOMException e) {
                System.out.println("Exception de ciudad " + ciudad.getIdIso2().getRegion1() + bOrden.getValue());
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("pais_descripcion"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "T");
            elemento.setAttribute("descripcion", "Pais");
            try {
                elemento.appendChild(doc.createTextNode(ciudad.getIdIso2().getIso31661().getPais() != null ? ciudad.getIdIso2().getIso31661().getPais() : ""));
            } catch (DOMException e) {
                System.out.println("Exception de pais descripcion " + ciudad.getIdIso2().getRegion1() + bOrden.getValue() + e.getMessage());
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("estado_codigo"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "T");
            elemento.setAttribute("descripcion", "Codigo");
            try {
                elemento.appendChild(doc.createTextNode(ciudad.getIdIso2().getIso2() != null ? ciudad.getIdIso2().getIso2() : ""));
            } catch (DOMException e) {
                System.out.println("Exception de estado codigo " + ciudad.getIdIso2().getRegion1() + e.getMessage());
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("estado_descripcion"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "T");
            elemento.setAttribute("descripcion", "Estado");
            try {
                elemento.appendChild(doc.createTextNode(ciudad.getIdIso2().getRegion1() != null ? ciudad.getIdIso2().getRegion1() : ""));
            } catch (DOMException e) {
                System.out.println("Exception de estado codigo " + ciudad.getIdIso2().getRegion1() + e.getMessage());
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            //ORIGEN canton
            elemento = doc.createElement("canton_codigo"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "T");
            elemento.setAttribute("descripcion", "Codigo");
            try {
                elemento.appendChild(doc.createTextNode(ciudad.getCodigo() != null ? ciudad.getCodigo() : ""));
            } catch (DOMException e) {
                System.out.println("Exception de estado codigo " + ciudad.getIdIso2().getRegion1() + e.getMessage());
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("canton_descripcion"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "T");
            elemento.setAttribute("descripcion", "Canton");
            try {
                elemento.appendChild(doc.createTextNode(ciudad.getRegion2() != null ? ciudad.getRegion2() : ""));
            } catch (DOMException e) {
                System.out.println("Excepcion de canton_descripcion " + ciudad.getIdIso2().getRegion1() + e.getMessage());
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            //ORIGEN parroquia
            elemento = doc.createElement("parroquia_codigo"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "T");
            elemento.setAttribute("descripcion", "Codigo");
            elemento.appendChild(doc.createTextNode(""));
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("parroquia_descripcion"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "T");
            elemento.setAttribute("descripcion", "Parroquia");
            elemento.appendChild(doc.createTextNode(""));
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("etnia"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "T");
            elemento.setAttribute("descripcion", "ETNIA");
            try {
                elemento.appendChild(doc.createTextNode(objHistoria.getEtnia() != null ? objHistoria.getEtnia() : ""));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("practica"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "PRACTICA");
            try {
                elemento.appendChild(doc.createTextNode(IdPractica.getValue()));
            } catch (DOMException e) {
                alert("PRACTICA EN NULL ID " + bOrden.getValue());
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("nombre_practica"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "NOMBRE_PRACTICA");
            try {
                elemento.appendChild(doc.createTextNode(nomPractica.getValue()));
            } catch (DOMException e) {
                alert("PRACTICA EN NULL NOMBRE " + bOrden.getValue());
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("solicitante"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "SOLICITANTE");
            try {
                elemento.appendChild(doc.createTextNode(mSolicitante.getValue()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("paciente"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "NOMBRE");
            try {
                elemento.appendChild(doc.createTextNode(idNombre.getValue()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("nombres"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "NOMBRES");
            try {
                elemento.appendChild(doc.createTextNode(objHistoria.getNombres()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            ///

            elemento = doc.createElement("ruc"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "RUC");
            try {

                elemento.appendChild(doc.createTextNode(objEmpresa.getRuc() != null ? objEmpresa.getRuc() : "."));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz

            elemento = doc.createElement("ciiu"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "ciiu");
            try {
                elemento.appendChild(doc.createTextNode(objEmpresa.getContacto() != null ? objEmpresa.getContacto() : "."));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            ///

            elemento = doc.createElement("apellidos"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "APELLIDOS");
            try {
                elemento.appendChild(doc.createTextNode(objHistoria.getApellidos()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("fecha_nace"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "fecha_nace");
            try {
                elemento.appendChild(doc.createTextNode(objHistoria.getFechaNace() != null ? formato.format(objHistoria.getFechaNace()) : ""));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("id_historia"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "IDHISTORIA");
            try {
                elemento.appendChild(doc.createTextNode(idHistoria.getValue()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("empresa"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "EMPRESA");
            try {
                elemento.appendChild(doc.createTextNode(idEmpresa.getValue()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("identificacion"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "CI");
            try {
                elemento.appendChild(doc.createTextNode(idCI.getValue()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("estado_civil"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "ESTADOCIVIL");
            try {
                elemento.appendChild(doc.createTextNode(idECivil.getValue()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("id_orden"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "IDORDEN");
            try {
                elemento.appendChild(doc.createTextNode(idOrden.getValue()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("telefono"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "TELEFONO");
            try {
                elemento.appendChild(doc.createTextNode(objHistoria.getTelefono() == null ? "" : objHistoria.getTelefono()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("sexo"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "SEXO");
            try {
                elemento.appendChild(doc.createTextNode(idSexo.getValue()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.DatosGeneralesInforme()" + e.getMessage());
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("profesion"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "PROFESION");
            try {
                elemento.appendChild(doc.createTextNode(idProfesion.getValue()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.DatosGeneralesInforme()" + e.getMessage());
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("edad"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "EDAD");
            try {
                elemento.appendChild(doc.createTextNode(idEdad.getValue()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.DatosGeneralesInforme()" + e.getMessage());
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("ocupacion"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "OCUPACION");
            try {
                elemento.appendChild(doc.createTextNode(ocupacion));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.DatosGeneralesInforme()" + e.getMessage());
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("instruccion_paciente"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "INSTRUCCION PACIENTE");
            try {
                elemento.appendChild(doc.createTextNode(objHistoria.getInstruccion() == null ? "" : objHistoria.getInstruccion()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.DatosGeneralesInforme()" + e.getMessage());
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("ciudad_nace"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "CIUDAD NACIMIENTO");
            try {
                elemento.appendChild(doc.createTextNode(objHistoria.getCiudadNace() == null ? "" : objHistoria.getCiudadNace()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.DatosGeneralesInforme()" + e.getMessage());
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("direccion_paciente"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "DIRECCION");
            try {
                elemento.appendChild(doc.createTextNode(objHistoria.getDireccion() == null ? "" : objHistoria.getDireccion()));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.DatosGeneralesInforme()" + e.getMessage());
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("titulo_paciente"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "TITULO");
            String titulo;
            if ((objHistoria.getTitulo() == null) || (objHistoria.getTitulo().isEmpty())) {
                if (objHistoria.getSexo().equalsIgnoreCase("M")) {
                    titulo = "SR";
                } else {
                    titulo = "SRA";
                }
            } else {
                titulo = objHistoria.getTitulo();
            }
            elemento.appendChild(doc.createTextNode(titulo));
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("medico"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "MEDICO");
            try {
                elemento.appendChild(doc.createTextNode((medico.getNombreMedico() == null ? "" : medico.getNombreMedico())));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.DatosGeneralesInforme()" + e.getMessage());
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("especialidad"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "ESPECIALIDAD");
            try {
                elemento.appendChild(doc.createTextNode((medico.getEspecialidad() == null ? "" : medico.getEspecialidad())));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.DatosGeneralesInforme()" + e.getMessage());
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("cod_med"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "CODIGO-MEDICO");
            try {
                elemento.appendChild(doc.createTextNode((medico.getCodMedico() == null ? "" : medico.getCodMedico())));
            } catch (DOMException e) {
                elemento.appendChild(doc.createTextNode(""));
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.DatosGeneralesInforme()" + e.getMessage());
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
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.DatosGeneralesInforme()" + e.getMessage());
            }
            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("fecha"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "FECHA");
            Date fecha;
            if (objOrden.getFecIni() != null) {
                fecha = objOrden.getFecIni();
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
            elemento = doc.createElement("nro_ord"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "Nextlab");
            try {
                if (objOrden.getCodOrd() != null) {
                    elemento.appendChild(doc.createTextNode(objOrden.getCodOrd().toString()));
                }

            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }

            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
            elemento = doc.createElement("med_sol"); //creamos un nuevo elemento
            elemento.setAttribute("orden", "0");
            elemento.setAttribute("tipo_dato", "L");
            elemento.setAttribute("descripcion", "MedicoSolici");
            try {
                elemento.appendChild(doc.createTextNode((objOrden.getmSolicitante() == null ? "" : objOrden.getmSolicitante())));
            } catch (Exception e) {
                elemento.appendChild(doc.createTextNode(""));
            }

            grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
        } catch (RuntimeException e) {
            System.out.println("Error Datos Generales Standard " + e.getCause().getMessage() + bOrden.getValue() + e.getMessage());
            throw new RuntimeException(e);
        }
        return doc;
    }

    private Document XMLInforme(Document doc) {
        completo = true;
        diagnosticoXml = "";
        Element elemento = null;
        Element Frame = null;
        Element pag = null;
        Row fila;
        Label etiqueta;
        int con = 0;
        List grupos = null;
        try {
            con = gridInforme.getRows().getGroupCount();
            grupos = rowsInforme.getGroups();
        } catch (Exception e) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLInforme() 2010");
        }
        boolean datos = false;
        try {
            doc = DatosGeneralesInforme(doc);
            datos = true;
        } catch (RuntimeException e) {
            System.out.println(usuario + "Error al generar DATOS GENERALES XML " + nomPractica + e.getCause().getMessage() + " " + bOrden.getValue());
            alert("Error al generar DATOS GENERALES XML");
        }
        try {
            if (datos) {
                boolean subgrupo = false;
                for (int i = 0; i < con; i++) {
                    Group grp = (Group) grupos.get(i);
                    int x = grp.getItemCount();
                    int y = grp.getChildren().size();
                    if ((x == 0) && (!(subgrupo))) {//es pagina creamos en elemento de pagina
                        try {
                            String x1 = grp.getLabel().toLowerCase();
                            String idformato = grp.getId();
                            Integer c = x1.indexOf(":") + 2;
                            String x2 = x1.substring(c);
                            subgrupo = true;
                            if (x1.length() > 7) {
                                x1 = x1.substring(0, 7);
                            }
                            String pagina = x1.substring(0, x1.length() - 1);
                            if (pagina.equals("pagina")) {
                                pag = doc.createElement(x1); // creamos el elemento raiz
                                pag.setAttribute("Id", x2);
                                pag.setAttribute("IdFormato", idformato);
                                doc.getDocumentElement().appendChild(pag); //pegamos la raiz al documento
                            } else {
                                subgrupo = false;
                                x1 = grp.getId().toLowerCase();
                                Frame = doc.createElement(x1); // creamos el elemento raiz
                                Frame.setAttribute("descripcion", grp.getLabel());
                                if (grp.isVisible()) {
                                } else {
                                    Frame.setAttribute("sexo", "!" + idSexo.getValue());
                                }
                                pag.appendChild(Frame); //pegamos la raiz al documento
                            }
                        } catch (Exception e) {
                            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLInforme() 1976");
                        }
                    } else {
                        String x1 = null;
                        try {
                            subgrupo = false;
                            x1 = grp.getId().toLowerCase();
                            Frame = doc.createElement(x1); // creamos el elemento raiz
                            Frame.setAttribute("descripcion", grp.getLabel());
                            if (grp.isVisible()) {
                            } else {
                                Frame.setAttribute("sexo", "!" + idSexo.getValue());
                            }
                            pag.appendChild(Frame); //pegamos la raiz al documento

                        } catch (Exception e) {
                            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLInforme() 1990" + grp.getId().toLowerCase());
                        }
                    }
                    String tipoDato = "", nombre = "", descripcion = "", orden = "", dato = "";
                    for (int j = 0; j < x; j++) {
                        fila = (Row) grp.getItems().get(j);
                        dato = "";
                        Integer lineas = -1;
                        String ItemLista = "";
                        if (fila.getChildren().size() > 0) {
                            for (int k = 0; k < fila.getChildren().size(); k++) {
                                switch (k) {
                                    case 0: {
                                        try {
                                            etiqueta = (Label) fila.getChildren().get(k);
                                            descripcion = etiqueta.getValue(); //creamos un nuevo elemento

                                        } catch (Exception e) {
                                            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLInforme() 1998" + descripcion);
                                        }

                                    }
                                    break;
                                    case 1: //asignar depende del tipo
                                    {
                                        try {
                                            String tipo = fila.getChildren().get(k).toString();
                                            int c = tipo.indexOf(" ");
                                            tipo = tipo.substring(1, c);
                                            switch (tipo) {
                                                case "Textbox": {
                                                    try {
                                                        Textbox valor = (Textbox) fila.getChildren().get(k);
                                                        tipoDato = "T";
                                                        dato = valor.getText();
                                                        if (valor.isMultiline()) {
                                                            lineas = 0;
                                                        } else {
                                                            if (valor.getMaxlength() == 128) {
                                                                lineas = 1;
                                                            } else {
                                                                if (valor.getMaxlength() == 256) {
                                                                    lineas = 2;
                                                                } else {
                                                                    lineas = 1;
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLInforme() 2023" + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Hlayout": {
                                                    try {
                                                        Hlayout valor = (Hlayout) fila.getChildren().get(k);
                                                        if (valor.getChildren().size() > 0) {
                                                            for (int l = 0; l < valor.getChildren().size(); l++) {
                                                                switch (l) {
                                                                    case 0:
                                                                        //tomo la descripcion
                                                                        break;
                                                                    case 1: //asignar depende del tipo
                                                                    {
                                                                        String tipo1 = valor.getChildren().get(l).toString();
                                                                        int c1 = tipo1.indexOf(" ");
                                                                        tipo1 = tipo1.substring(1, c);
                                                                        if (tipo1.equals("Textbox")) {
                                                                            Textbox valor1 = (Textbox) valor.getChildren().get(k);
                                                                            tipoDato = "T";
                                                                            dato = valor1.getText();

                                                                            if (valor1.isMultiline()) {
                                                                                lineas = 0;
                                                                            } else {
                                                                                if (valor1.getMaxlength() == 128) {
                                                                                    lineas = 1;
                                                                                } else {
                                                                                    if (valor1.getMaxlength() == 256) {
                                                                                        lineas = 2;
                                                                                    } else {
                                                                                        lineas = 1;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLInforme() 2066" + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Vlayout": {
                                                    try {
                                                        Vlayout valor = (Vlayout) fila.getChildren().get(k);
                                                        if (valor.getChildren().size() > 0) {
                                                            for (int l = 0; l < valor.getChildren().size(); l++) {
                                                                String tipo1;
                                                                int c1;
                                                                switch (l) {
                                                                    case 0: //asignar depende del tipo
                                                                    {
                                                                        tipo1 = valor.getChildren().get(l).toString();
                                                                        c1 = tipo1.indexOf(" ");
                                                                        tipo1 = tipo1.substring(1, c);
                                                                        if (tipo1.equals("Textbox")) {
                                                                            Textbox valor1 = (Textbox) valor.getChildren().get(l);
                                                                            dato = valor1.getText();
                                                                            lineas = 0;
                                                                        }
                                                                        break;
                                                                    }
                                                                    case 1: //asignar depende del tipo
                                                                    {
                                                                        tipo1 = valor.getChildren().get(l).toString();
                                                                        c1 = tipo1.indexOf(" ");
                                                                        tipo1 = tipo1.substring(1, c);
                                                                        if (tipo1.contains("Button")) {

                                                                            Button valorb = (Button) valor.getChildren().get(l);
                                                                            if (valorb.getLabel().toUpperCase().contains("DIAG")) {
                                                                                tipoDato = "G";
                                                                            }
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (WrongValueException e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLInforme() 2108" + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Decimalbox": {
                                                    try {
                                                        Decimalbox valor = (Decimalbox) fila.getChildren().get(k);
                                                        tipoDato = "N";
                                                        dato = valor.getText();
                                                    } catch (WrongValueException e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLInforme()2133" + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Datebox": {
                                                    try {
                                                        Datebox valor = (Datebox) fila.getChildren().get(k);
                                                        tipoDato = "D";
                                                        valor.setFormat("dd-MMM-yyyy");
                                                        dato = valor.getText();
                                                    } catch (WrongValueException e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLInforme()2144" + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Radiogroup": {
                                                    try {
                                                        Radiogroup grpRadio = (Radiogroup) fila.getChildren().get(k);
                                                        if (!(grpRadio.isVisible())) {
                                                        }
                                                        int pos = grpRadio.getSelectedIndex();
                                                        if (pos > -1) {
                                                            Radio lblradio = grpRadio.getSelectedItem();
                                                            dato = lblradio.getLabel();
                                                        } else {
                                                            dato = " ";
                                                        }
                                                        tipoDato = "S";
                                                    } catch (WrongValueException e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLInforme()2162" + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Combobox": {
                                                    try {
                                                        String pos = "";
                                                        Combobox grpRadio = (Combobox) fila.getChildren().get(k);
                                                        int po = grpRadio.getSelectedIndex();
                                                        if (po > -1) {
                                                            pos = grpRadio.getSelectedItem().getLabel();
                                                        }
                                                        if (pos.isEmpty()) {
                                                            dato = "";
                                                        } else {
                                                            dato = pos;
                                                        }
                                                        ItemLista = "";
                                                        for (int l = 0; l < grpRadio.getItemCount(); l++) {
                                                            Comboitem v = grpRadio.getItemAtIndex(l);
                                                            ItemLista = ItemLista + v.getLabel() + "/";
                                                        }
                                                        tipoDato = "L";
                                                    } catch (Exception e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLInforme()2186" + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Hbox": {
                                                    try {
                                                        String pos = "";
                                                        Hbox grpGrupo = (Hbox) fila.getChildren().get(k);
                                                        Combobox comboLista = (Combobox) grpGrupo.getChildren().get(0);
                                                        Textbox porq = (Textbox) grpGrupo.getChildren().get(2);
                                                        int po = comboLista.getSelectedIndex();
                                                        if (po > -1) {
                                                            pos = comboLista.getSelectedItem().getLabel();
                                                        }
                                                        if (pos.isEmpty()) {
                                                            dato = "";
                                                        } else {
                                                            dato = pos + "|" + porq.getValue() + "|";
                                                        }
                                                        ItemLista = "";
                                                        for (int l = 0; l < comboLista.getItemCount(); l++) {
                                                            Comboitem v = comboLista.getItemAtIndex(l);
                                                            ItemLista = ItemLista + v.getLabel() + "/";
                                                        }
                                                        tipoDato = "LD";
                                                    } catch (Exception e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLInforme()2212" + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Div": {
                                                    tipoDato = "G";
                                                    dato = "";

                                                    break;
                                                }
                                                case "Button": {
                                                    try {
                                                        Button btn = (Button) fila.getChildren().get(k);
                                                        if (btn.getLabel().toUpperCase().contains("DIAG")) {
                                                            tipoDato = "G";
                                                            dato = "";
                                                        } else {
                                                            tipoDato = "B";
                                                            dato = "";
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLInforme() 2233" + descripcion);
                                                    }
                                                    break;
                                                }
                                                default:
                                                    if (tipo.equals("Button")) {
                                                        tipoDato = "B";
                                                        dato = "";
                                                    } else {
                                                        tipoDato = "T";
                                                        dato = "";
                                                    }
                                                    break;
                                            }
                                        } catch (WrongValueException e) {
                                            System.out.println(usuario + "Error Asignacion de tipo " + descripcion + " " + bOrden.getValue() + e.getMessage());
                                        }
                                    }
                                    break;
                                    case 3: {
                                        etiqueta = (Label) fila.getChildren().get(k);
                                        orden = etiqueta.getValue(); //creamos un nuevo elemento
                                    }
                                    break;
                                    case 2: {//nombre
                                        try {
                                            etiqueta = (Label) fila.getChildren().get(k);
                                            nombre = etiqueta.getValue();
                                            nombre = nombre.trim();
                                            elemento = doc.createElement(nombre); //
                                            if (nombre.contains("dx")) {
                                                try {
                                                    Element dxx = null;
                                                    if (tipoDato.equals("G")) {
                                                        dato = "";
                                                        String datoText = "";
                                                        diagobs = new ArrayList();
                                                        diaTotal = new ArrayList();
                                                        Div divi = (Div) fila.getChildren().get(1);
                                                        Grid gridCie = null;
                                                        Textbox diagn = null;
                                                        Checkbox imp = null;
                                                        if (divi.getChildren().size() == 3) {
                                                            gridCie = (Grid) divi.getChildren().get(0);
                                                            diagn = (Textbox) divi.getChildren().get(1);
                                                            imp = (Checkbox) divi.getChildren().get(2);
                                                            Rows filasDX = gridCie.getRows();
                                                            for (int l = 0; l < filasDX.getChildren().size(); l++) {
                                                                Row diag = (Row) filasDX.getChildren().get(l);
                                                                Combobox DXcie = null;
                                                                DXcie = (Combobox) diag.getChildren().get(0);
                                                                Textbox diagnmed = (Textbox) diag.getChildren().get(1);
                                                                String val = diagnmed.getValue();
                                                                int je;

                                                                Ciediez obje = null;
                                                                try {
                                                                    if (DXcie.getItemCount() > 0) {

                                                                        obje = (Ciediez) DXcie.getSelectedItem().getValue();
                                                                        je = l + 1;
                                                                        dato = dato + je + ".  ";

                                                                    } else {
                                                                        val = "";
                                                                        bDXBlanco = true;
//                                                                        Messagebox.show("1.- No puede guardar diagnosticos sin datos  2303", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                                                                    }
                                                                } catch (Exception e) {
                                                                    if (DXcie.getModel() != null) {

                                                                        for (int m = 0; m < DXcie.getModel().getSize(); m++) {
                                                                            Ciediez ob = (Ciediez) DXcie.getModel().getElementAt(m);
                                                                            if (ob.getDescripcion().equals(DXcie.getText())) {//es el mismo
                                                                                obje = ob;
                                                                                je = l + 1;
                                                                                dato = dato + je + ".  ";
                                                                            }
                                                                        }

                                                                    } else {
                                                                        obje = new Ciediez();
                                                                        Messagebox.show("2.- No puede guardar diagnosticos sin datos" + DXcie.getText(), "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                                                                    }
                                                                    if (obje == null) {
                                                                        Messagebox.show("No puede guardar diagnosticos sin seleccion", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                                                                    }
                                                                }
                                                                if (obje != null) {
                                                                    Element dx1 = null;
                                                                    try {
                                                                        dx1 = doc.createElement("dx" + l); //creamos un nuevo elemento
                                                                    } catch (DOMException e) {
                                                                        System.out.println(usuario + "dx" + l + " " + e.getMessage() + " " + bOrden.getValue() + e.getMessage());
                                                                    }
                                                                    String tip = obje.getTipo();
                                                                    if (val != null) {
                                                                        if (!val.isEmpty()) {
                                                                            if (imp != null) {
                                                                                if (imp.isChecked()) {
                                                                                    dato = dato + "(" + obje.getCodRef() + ")  " + obje.getDescripcion() + "   [" + val + "]  \n";
                                                                                } else {
                                                                                    dato = dato + obje.getDescripcion() + "   [" + val + "]  \n";
                                                                                }
                                                                            }
                                                                        } else {
                                                                            if (imp != null) {
                                                                                if (imp.isChecked()) {
                                                                                    if (obje.getCodRef() != null) {
                                                                                        dato = dato + "(" + obje.getCodRef() + ")  " + obje.getDescripcion() + "\n";
                                                                                    } else {
                                                                                        dato = dato + obje.getDescripcion() + "\n";
                                                                                    }
                                                                                } else {
                                                                                    dato = dato + obje.getDescripcion() + "\n";
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    dx1.setAttribute("cod", obje.getCodCie());
                                                                    dx1.setAttribute("tipo", obje.getTipo());
                                                                    try {
                                                                        dx1.setAttribute("codRef", obje.getCodRef());
                                                                    } catch (DOMException e) {
                                                                        dx1.setAttribute("codRef", "");
                                                                        System.out.println("dx1.setAttribute(codRef, obje.getCodRef());" + obje.getDescripcion());
                                                                    }
                                                                    try {
                                                                        dx1.setAttribute("codGrp", obje.getGrupo());
                                                                    } catch (DOMException e) {
                                                                        dx1.setAttribute("codGrp", "");
                                                                        System.out.println("dx1.setAttribute(codGrp, obje.getGrupo());" + obje.getDescripcion());
                                                                    }
                                                                    dx1.appendChild(doc.createTextNode(obje.getDescripcion()));
                                                                    diagobs.add(dx1);
                                                                    Element dx2 = doc.createElement("obs" + l); //creamos un nuevo elemento
                                                                    dx2.setAttribute("cod", obje.getCodCie());
                                                                    dx2.appendChild(doc.createTextNode(val));
                                                                    diagobs.add(dx2);
                                                                }
                                                            }
                                                            if (!(diagn.getValue().isEmpty())) {
                                                                datoText = diagn.getValue();
                                                                dxx = doc.createElement("dxx" + nombre); //creamos un nuevo elemento
                                                                dxx.appendChild(doc.createTextNode(datoText));
                                                            }
                                                            dato = dato + datoText;
                                                        }
                                                    } else {
                                                        if (tipoDato.equals("T")) {
                                                            diagobs = null;
                                                        }
                                                    }
                                                    diagnosticoXml = dato;
                                                    elemento.setAttribute("tipo_dato", tipoDato);
                                                    elemento.setAttribute("descripcion", descripcion);
                                                    elemento.setAttribute("orden", orden);
                                                    elemento.appendChild(doc.createTextNode(dato));
                                                    if (dxx != null) {
                                                        elemento.appendChild(dxx);
                                                    }
                                                    if ((diagobs != null) && (diagobs.size() > 0)) {
                                                        for (int p = 0; p < diagobs.size(); p++) {
                                                            elemento.appendChild((Node) diagobs.get(p));
                                                            diaTotal.add(diagobs.get(p));
                                                        }
                                                    }
                                                } catch (DOMException | WrongValueException e) {
                                                    System.out.println(usuario + " nombre.contains(dx)  " + descripcion + e.getCause().getMessage() + " " + bOrden.getValue() + e.getMessage());
                                                }
                                            } else {
                                                if (nombre.equals("recomendaciones")) {
                                                    recomendacionesXml = dato;
                                                }
                                                if (nombre.equals("prescripciones")) {
                                                    prescripcionesXml = dato;
                                                }
                                                nombre = nombre.trim();
                                                if (ItemLista != null) {
                                                    elemento.setAttribute("item_lista", ItemLista);
                                                    ItemLista = null;
                                                }
                                                if (lineas > -1) {
                                                    elemento.setAttribute("lineas", lineas.toString());
                                                    lineas = -1;
                                                }
                                                elemento.setAttribute("tipo_dato", tipoDato);
                                                elemento.setAttribute("descripcion", descripcion);
                                                elemento.setAttribute("orden", orden);
                                                if (dato == null) {
                                                    dato = "";
                                                }
                                                if (tipoDato.equals("B")) {
                                                    String derecho = (String) session.getAttribute("derecho");
                                                    String izquierdo = (String) session.getAttribute("izquierdo");
                                                    if (derecho == null) {
                                                        derecho = "";
                                                    }
                                                    if (izquierdo == null) {
                                                        izquierdo = "";
                                                    }
                                                    if ((derecho.isEmpty()) || (izquierdo.isEmpty())) {
                                                        completo = (!editAudiometria) || (!banModificar);
                                                    }
                                                    dato = "";
                                                } else {
                                                    if (dato.isEmpty()) {
                                                        completo = false;
                                                    }
                                                }
                                                try {
                                                    if (!(fila.isVisible())) {
                                                        dato = " ";
                                                    }
                                                } catch (Exception e) {
                                                    dato = " ";
                                                    System.out.println("!(fila.isVisible() " + e.getMessage() + " " + bOrden.getValue() + e.getMessage());
                                                }
                                                elemento.appendChild(doc.createTextNode(dato));
                                            }
                                        } catch (Exception e) {
                                            System.out.println(usuario + "etiqueta  " + descripcion + e.getCause().getMessage() + " " + bOrden.getValue() + e.getMessage());
                                        }
                                        break;
                                    }
                                }
                            }
                            Frame.appendChild(elemento); //pegamos el elemento hijo a la raiz
                        }
                    }
                }
                NodeList listaHijos = doc.getChildNodes();
                listaHijos = listaHijos.item(0).getChildNodes();
                Node NPagina;
                NPagina = listaHijos.item(0);
                try {
                    if (NPagina.getNodeType() == Node.ELEMENT_NODE) {
                        Element elementoP = (Element) NPagina;
                        if (elementoP.getNodeName() != null) {
                            String NamNText = elementoP.getTagName();
                            if (NamNText.equals("datos_generales_standar")) {
                                //agregamos un nuevo nodo
                                Element elementoI = doc.createElement("dx"); //creamos un nuevo elemento
                                elementoI.setAttribute("orden", "0");
                                elementoI.setAttribute("descripcion", "DIAGNOSTICO");
                                elementoI.appendChild(doc.createTextNode(diagnosticoXml));
                                elementoP.appendChild(elementoI);
                                elementoI = doc.createElement("recomendaciones"); //creamos un nuevo elemento
                                elementoI.setAttribute("orden", "0");
                                elementoI.setAttribute("descripcion", "RECOMENDACIONES");
                                if (recomendacionesXml == null) {
                                    recomendacionesXml = "";
                                }
                                elementoI.appendChild(doc.createTextNode(recomendacionesXml));
                                elementoP.appendChild(elementoI);
                                elementoI = doc.createElement("prescripciones"); //creamos un nuevo elemento
                                elementoI.setAttribute("orden", "0");
                                elementoI.setAttribute("descripcion", "PRESCRIPCIONES");
                                if (prescripcionesXml == null) {
                                    prescripcionesXml = "";
                                }
                                elementoI.appendChild(doc.createTextNode(prescripcionesXml));
                                elementoP.appendChild(elementoI);
                            }
                        }
                    }
                } catch (Exception e) {
                    System.out.println(usuario + "No se actualizo datos_generales_standar  " + e.getCause().getMessage() + " " + bOrden.getValue() + e.getMessage());
                }
                NodeList lista = doc.getElementsByTagName("datos_generales_standar");
            }
        } catch (RuntimeException e) {
            System.out.println(usuario + "Error el generar XML " + e.getCause().getMessage() + " " + bOrden.getValue() + e.getMessage());
            alert("Error el generar XML ");
        }
        return doc;
    }

    private void actualizarStandar(XmlResultado Archivo) {
        Node NPagina;
        Node NHijos;
        Element elemento;
        Document doc1 = null;
        doc1 = ConvertirDocumento.getConvertirDocumentoDocument(Archivo.getResultado());
        NodeList listaHijost = doc1.getChildNodes();
        NodeList listaHijos;
        NodeList listaHijosDatos;
        NodeList listaHijosDatosFrame;
        String valocupacion = null;
        try {
            int totn = listaHijost.getLength();
            for (int i = 0; i < totn; i++) {
                listaHijos = listaHijost.item(i).getChildNodes();
                for (int t = 1; t < listaHijos.getLength(); t++) {
                    NPagina = listaHijos.item(t);
                    if (NPagina.getNodeType() == Node.ELEMENT_NODE) {
                        elemento = (Element) NPagina;
                        if (elemento.getNodeName() != null) {
                            listaHijosDatos = NPagina.getChildNodes();
                            for (int j = 0; j < listaHijosDatos.getLength(); j++) {
                                NHijos = listaHijosDatos.item(j);
                                if (NHijos.getNodeType() == Node.ELEMENT_NODE) {
                                    elemento = (Element) NHijos;
                                    if (elemento.getNodeName() != null) {
                                        String NamNText1 = elemento.getTagName();
                                        if (NamNText1.equals("ocupacion")) {
                                            valocupacion = elemento.getTextContent();
                                        }
                                        listaHijosDatosFrame = NHijos.getChildNodes();
                                        for (int k = 0; k < listaHijosDatosFrame.getLength(); k++) {
                                            NHijos = listaHijosDatosFrame.item(k);
                                            if (NHijos.getNodeType() == Node.ELEMENT_NODE) {
                                                elemento = (Element) NHijos;
                                                if (elemento.getNodeName() != null) {
                                                    NamNText1 = elemento.getTagName();
                                                    switch (NamNText1) {
                                                        case "origen": {
                                                            try {
                                                                orige = elemento.getTextContent();
                                                            } catch (Exception e) {
                                                                orige = "";
                                                            }
                                                            break;
                                                        }
                                                        case "departamento": {
                                                            try {
                                                                depar = elemento.getTextContent();
                                                            } catch (DOMException e) {
                                                                depar = "";
                                                            }
                                                            break;
                                                        }
                                                        case "ocupacions": {
                                                            valocupacion = elemento.getTextContent();
                                                            break;
                                                        }
                                                        case "grupo_riesgo": {
                                                            try {
                                                                grupo_r = elemento.getTextContent();
                                                            } catch (DOMException e) {
                                                                grupo_r = "";
                                                            }
                                                            break;
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
        } catch (Exception e) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.actualizarStandar()" + e.getMessage());
        }
        ocupa = valocupacion;
    }

    //<editor-fold defaultstate="collapsed" desc="CARGAR GRID XML">
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
                            numFormatosInforme = numFormatosInforme + 1;
                            NamNText = NamNText + " ID Formato: " + elemento.getAttribute("Id");
                            final Group fila = new Group();
                            fila.setLabel(NamNText);
                            fila.setId(idforma);
                            fila.setOpen(false);
                            fila.setVisible(false);
                            fila.setParent(rowsInforme);
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
                                            if (!(sexVisible.equalsIgnoreCase(idSexo.getValue()))) {
                                                visibleEditFrameTExt = false;
                                            }
                                        }
                                        grupo.setVisible(visibleEditFrameTExt);
                                        grupo.setLabel(descripcionXmlRes);
                                        grupo.setStyle(" border: 1px outset; background: #B9D103; color: black;text-align: center; ");
                                        grupo.setId(NamNText);
                                        grupo.setParent(rowsInforme);
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
//                                                                nText.setId("T" + LblNombre.getValue());
                                                                if (valor.isEmpty()) {
                                                                    nText.setSclass("resultado-incompleto");
                                                                    nText.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

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
                                                                            nText.addEventListener(Events.ON_CTRL_KEY, new EventListener<Event>() {

                                                                                @Override
                                                                                public void onEvent(Event event) {
                                                                                    int keyCode = ((KeyEvent) event).getKeyCode();
                                                                                    if (keyCode == 67 || keyCode == 86) {
                                                                                    }
                                                                                }
                                                                            });
                                                                            nText.addEventListener(Events.ON_RIGHT_CLICK, new EventListener<Event>() {

                                                                                @Override
                                                                                public void onEvent(Event event) {
                                                                                }
                                                                            });
                                                                        }
                                                                        nText.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener<Event>() {

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
                                                                                    edicion.addEventListener(Events.ON_CTRL_KEY, new EventListener<Event>() {

                                                                                        @Override
                                                                                        public void onEvent(Event event) {
                                                                                            int keyCode = ((KeyEvent) event).getKeyCode();
                                                                                            if (keyCode == 67 || keyCode == 86) {
                                                                                            }
                                                                                        }
                                                                                    });
                                                                                    edicion.addEventListener(Events.ON_RIGHT_CLICK, new EventListener<Event>() {

                                                                                        @Override
                                                                                        public void onEvent(Event event) {
                                                                                        }
                                                                                    });
                                                                                }
                                                                                winEdicion.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

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
                                                                boolean cuadroT = false;
                                                                final Hlayout cuadroa = new Hlayout();
                                                                try {
                                                                    Valor = null;
                                                                    Valor = nText;
                                                                } catch (Exception e) {
                                                                    System.out.println(usuario + e.getMessage() + bOrden.getValue() + "Valor = null Valor = nText");
                                                                }
                                                                if (cuadroT) {
                                                                    nText.setWidth("400px");
//                                                                    if (abrevi) {
//                                                                        cuadroa.appendChild(nAbre);
//                                                                    }
                                                                    cuadroa.appendChild(nText);
                                                                    NewFila.appendChild(cuadroa);
                                                                } else {
                                                                    NewFila.appendChild(nText);
                                                                }
//                                                                NewFila.appendChild(nText);
                                                                break;
                                                            }
                                                            case "N": {
                                                                final Decimalbox nDecimal;
                                                                if (valor.isEmpty()) {
                                                                    nDecimal = new Decimalbox();
                                                                    nDecimal.setSclass("resultado-incompleto");
                                                                    nDecimal.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

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
                                                                        formatter = new SimpleDateFormat("dd-MMM-yyyy");
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
                                                                            System.out.println("ERROR DANDO FORMATO FECHA DEL XML " + valor + "rESxML " + resultadoXML.getId() + " ID orden " + resultadoXML.getOrden().getId());
                                                                            throw new RuntimeException(e);
                                                                        }
                                                                        nFecha.setValue(fec);
                                                                    } else {
                                                                        nFecha.setSclass("resultado-incompleto");
                                                                        nFecha.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

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
                                                                        lblSi.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

                                                                            @Override
                                                                            public void onEvent(Event event) {
                                                                                lblSi.setSclass("resultado");
                                                                                lblNo.setSclass("resultado");
                                                                            }
                                                                        });
                                                                        lblNo.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

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
                                                                    grpLista.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

                                                                        @Override
                                                                        public void onEvent(Event event) {
                                                                            grpLista.setSclass("resultado-completo");
                                                                        }
                                                                    });
                                                                } else {
                                                                    grpLista.setValue(valor);
                                                                    grpLista.setSclass("resultado-completo");
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
                                                                        grpLista.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

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
                                                                final Button btnAplet = new Button(LblDescripcion.getValue());
                                                                if (LblDescripcion.getValue().equals("AUDIOMETRIA")) {
                                                                    btnAplet.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                                                                        @Override
                                                                        public void onEvent(Event event) {
                                                                            confirmEditAudiometria();
                                                                        }
                                                                    });
                                                                    NewFila.appendChild(btnAplet);
                                                                }
                                                                btnAplet.setDisabled(!estado);
                                                            }
                                                            break;
                                                            case "G": {
                                                                try {
                                                                    Node valora;
                                                                    try {
                                                                        valora = elemento.getChildNodes().item(1);
                                                                        if (valora != null) {
                                                                            if (valora.getNodeName().contains("dxx")) {
                                                                                valor = valora.getTextContent();
                                                                            } else {
                                                                                valor = "";
                                                                            }
                                                                        }
                                                                    } catch (DOMException e) {
                                                                        valor = "";
                                                                    }
                                                                    final Div grupodx = new Div();
                                                                    grupodx.setSclass("resultado-incompleto");
                                                                    final Grid dxgrid = new Grid();
                                                                    final Rows FilasCie = new Rows();
                                                                    dxgrid.setWidth("98%");
                                                                    Columns colum = new Columns();
                                                                    Column dxc = new Column("Diagnósticos");
                                                                    Column dxm = new Column("Especificaciones, ubicación del diagnostico");
                                                                    Column dxcom = new Column("");
                                                                    dxcom.setWidth("10%");
                                                                    colum.appendChild(dxc);
                                                                    colum.appendChild(dxm);
                                                                    final Button control = new Button("+");
                                                                    control.setWidth("10%");
                                                                    control.setDisabled(!estado);
                                                                    control.addEventListener(Events.ON_CLICK, new EventListener() {
                                                                        @Override
                                                                        public void onEvent(Event event) throws Exception {
                                                                            agregarDx(FilasCie);
                                                                        }
                                                                    });
                                                                    final Button vendx = new Button("?");
                                                                    vendx.setWidth("10%");
                                                                    dxcom.appendChild(control);
                                                                    dxcom.appendChild(vendx);
                                                                    colum.appendChild(dxcom);
                                                                    dxgrid.appendChild(colum);
                                                                    dxgrid.appendChild(FilasCie);
                                                                    final Textbox txtAbierto = new Textbox();
                                                                    txtAbierto.setValue(valor);
                                                                    txtAbierto.setMultiline(true);
                                                                    txtAbierto.setRows(5);
                                                                    txtAbierto.setWidth("98%");
                                                                    int g = elemento.getChildNodes().getLength();
                                                                    for (int i = 0; i < elemento.getChildNodes().getLength() - 1; i++) {
                                                                        String o = elemento.getChildNodes().item(i).getLocalName();
                                                                        Node dx1 = elemento.getElementsByTagName("dx" + i).item(0);
                                                                        Node obs1 = elemento.getElementsByTagName("obs" + i).item(0);
                                                                        int n = 0;
                                                                        if (dx1 != null) {
                                                                            n = dx1.getAttributes().getLength();
                                                                        }
                                                                        if (n > 2) {
                                                                            if ((dx1 != null) && (obs1 != null)) {
                                                                                agregarDxI(dx1.getAttributes().getNamedItem("codRef").getNodeValue(), dx1.getAttributes().getNamedItem("codGrp").getNodeValue(), dx1.getTextContent(), dx1.getAttributes().getNamedItem("cod").getNodeValue(), dx1.getAttributes().getNamedItem("tipo").getNodeValue(), obs1.getTextContent(), FilasCie, estado);
                                                                            }
                                                                        } else {
                                                                            i = elemento.getChildNodes().getLength();
                                                                        }

                                                                    }
                                                                    final Checkbox imp = new Checkbox("Imprimir códigos (opcional especialidades)");
                                                                    imp.setWidth("10%");
                                                                    imp.addEventListener(Events.ON_CHECK, new EventListener() {
                                                                        @Override
                                                                        public void onEvent(Event event) throws Exception {
                                                                        }
                                                                    });
                                                                    imp.setDisabled(true);
                                                                    txtAbierto.setDisabled(true);
                                                                    if (abierto) {
                                                                        imp.setDisabled(!estado);
                                                                        txtAbierto.setDisabled(!estado);
                                                                    }
                                                                    grupodx.appendChild(dxgrid);
                                                                    grupodx.appendChild(txtAbierto);
                                                                    grupodx.appendChild(imp);
                                                                    NewFila.appendChild(grupodx);
                                                                } catch (Exception e) {
                                                                    System.out.println(usuario + "Error Diagnosticos " + bOrden.getValue() + e.getMessage());
                                                                    throw new RuntimeException(e);
                                                                }

                                                                break;
                                                            }
                                                            default:
                                                                final Textbox nText1 = new Textbox();
                                                                nText1.setDisabled(!estado);
                                                                nText1.setSclass("resultado");
                                                                if (valor.isEmpty()) {
                                                                    nText1.setSclass("resultado-incompleto");
                                                                    nText1.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

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
                                                                nText1.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {

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
//                                                            NewFila.setId(LblNombre.getValue());
                                                            NewFila.setVisible(visibleEditFrameTExt);
                                                            NewFila.setParent(rowsInforme);
                                                        } catch (Exception e) {
                                                            alert("no puede cargar" + LblNombre.getValue());
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
            banModificar = estado;
            rowsInforme.setVisible(true);
            if (PracticaInforme.getPerImpa() == 0) {
                banIMPAntecedentes = true;
            } else {
                banIMPAntecedentes = false;
            }
            lockCab();
        } catch (ParseException | RuntimeException e) {
        }
    }

    private void loadGridResultados(boolean estado) {
        //desde XmlResultados
        editAudiometria = false;
        numFormatosInforme = 1;
        if (estado) {
            timerRestart();
        }
        try {
            int x = rowsInforme.getChildren().size();
            if (x >= 1) {//si hay datos toca eliminar
                while (rowsInforme.getChildren().size() > 0) {
                    for (int i = 0; i < rowsInforme.getChildren().size(); i++) {
                        rowsInforme.getChildren().remove(i);
                    }
                }
            }
            String xml = resultadoXML.getResultado();
            Document doc1 = ConvertirDocumento.getConvertirDocumentoDocument(xml);
            NodeList listaHijos = doc1.getChildNodes();
            listaHijos = listaHijos.item(0).getChildNodes();
            GridResultados(listaHijos, estado);
            if (resultadoXML.getPractica().getPerImpa() == 0) {
                //  btnGuardar.setDisabled(true);
                banIMPAntecedentes = true;
            } else {
                banIMPAntecedentes = false;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CARGAR GRID FORMATO">
    private boolean EntradasInforme() {
        NodeList listTexFrames = null;
        Node nodoText, nodoFrame;
        Element elemento;
        List dataGrid = new ArrayList();
        rowsInforme.getChildren().clear();
        Document doc1 = null;
        try {
            Map<String, Object> wSQL = new HashMap<>();
            List oSQL = new ArrayList();
            Integer idPrac;
            if (PracticaInforme.getCodRef() != null) {
                if (PracticaInforme.getCodRef() == 0) {
                    idPrac = PracticaInforme.getIdPractica();
                } else {
                    idPrac = PracticaInforme.getCodRef();
                }
            } else {
                idPrac = PracticaInforme.getIdPractica();
            }
            wSQL.put("idPractica ?=", idPrac);
            wSQL.put("lockReg ?=", 0);
            oSQL.add("idHoja");
            List<Object> Formatos = null;
            try {
                Formatos = admNegocio.getDataAsc(new FormatoXPractica(), wSQL, null, oSQL);
            } catch (NamingException ex) {
                System.out.println(usuario + "  Formatos = admNegocio.getDataAsc(new FormatoXPractica(), wSQL, null, oSQL); " + bOrden.getValue() + ex.getMessage());
                Logger.getLogger(ControladoraHistoria.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            if (((hayFormatoEmpresa()) && (PracticaInforme.getPerImpa() == 0)) || ((PracticaInforme.getPerImpa() == 1))) {
                if (!(Formatos.isEmpty())) {
                    numFormatosInforme = Formatos.size() + 1;
                    for (int t = 0; t < Formatos.size(); t++) {
                        FormatoXPractica formatPage = (FormatoXPractica) Formatos.get(t);
                        String xml = null;
                        try {
                            xml = new String(formatPage.getXml(), "UTF8");
                        } catch (UnsupportedEncodingException e) {
                            Messagebox.show("Formato en pruebas");
                            reset();
                        }
                        DocumentBuilder docBuilder = null;
                        try {
                            docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                            doc1 = docBuilder.parse(new InputSource(new StringReader(xml)));
                        } catch (ParserConfigurationException ex) {
                            System.out.println(usuario + "   docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder(); " + bOrden.getValue() + ex.getMessage());
                            Logger
                                    .getLogger(ControladoraHistoria.class
                                            .getName()).log(Level.SEVERE, null, ex);
                        } catch (SAXException ex) {
                            System.out.println(usuario + "  doc1 = docBuilder.parse(new InputSource(new StringReader(xml))); " + bOrden.getValue() + ex.getMessage());
                            Logger
                                    .getLogger(ControladoraHistoria.class
                                            .getName()).log(Level.SEVERE, null, ex);
                        }
                        ManejadoraXml AdmXml = new ManejadoraXml();
                        NodeList lista = AdmXml.getlistanodos("frame", doc1);
                        Group fila = new Group();
                        fila.setId(formatPage.getId().toString());
                        System.out.println("num"+formatPage.getId().toString());
                        fila.setLabel("PAGINA" + t + "  ID Formato: " + formatPage.getIdHoja());
                         System.out.println("t"+t);
                        fila.setVisible(false);
                        fila.setParent(rowsInforme);
                        for (int k = 0; k < lista.getLength(); k++) {
                            listTexFrames = lista.item(k).getChildNodes();
                            nodoFrame = lista.item(k);
                            visibleFrameCarga = true; //lista de frame o grupos
                            listTexFrames = FrameInforme(nodoFrame);
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
                                                ElementInforme(elemento, nodoText);
                                            } catch (InterruptedException ex) {
                                                System.out.println(usuario + " Error al crear elemento " + elemento.getTagName() + bOrden.getValue() + ex.getMessage());
                                                alert("Error al crear elemento " + ex.getMessage());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        rowsInforme.setVisible(true);
                    }
                    if (PracticaInforme.getPerImpa() == 0) {
                        banIMPAntecedentes = true;
                    } else {
                        banIMPAntecedentes = false;
                    }
                    stado = true;
                    lockCab();
                    return true;
                } else {
                    Messagebox.show("No se encontraron formatos cargados,y/o verificar la empresa por favor  " + PracticaInforme.getAbreviatura());
                    reset();
                    return false;
                }
            } else {//si es 0
                Messagebox.show("Revisar estado practica y antecedentes " + PracticaInforme.getAbreviatura());
                reset();
                return false;
            }
        } catch (IOException e) {
            System.out.println(usuario + "  loadGridEntradas " + bOrden.getValue() + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private NodeList FrameInforme(Node NFrame) {
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
                            if (!(sexoVisible.equalsIgnoreCase(idSexo.getValue()))) {
                                n = "NO APLICA PARA:" + sexoVisible;
                                visibleFrameCarga = false;
                            }
                        }
                        grupo = new Group();
                        grupo.setLabel(n);
                        grupo.setStyle("border: 1px outset; background:#B9D103; color: black;text-align: center;");
                        grupo.setVisible(frameVisible);
                        if (nomGrupo.equals("S/D")) {
                            nomGrupo = " ";
                        }
                        try {
                            grupo.setId(nomGrupo);
                            grupo.setParent(rowsInforme);
                        } catch (Exception e) {
//                             grupo.setParent(rowsInforme);
                            alert("Revisar grupo no unico" + nomGrupo + " " + e.getMessage());
                        }
                    }
                }
            }
            return listTextFrames;
        } catch (DOMException e) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.FrameInforme()" + e.getMessage());
            return null;
        }
    }

    private void ElementInforme(Element elemento, Node NText) throws InterruptedException {
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
                //RECORRO LAS PROPIEDADES DE LAS TEXTFIELD
                for (int j = 0; j < listaNodoProperty.getLength(); j++) {
                    tagItem = listaNodoProperty.item(j);
                    if (tagItem.getNodeType() == Node.ELEMENT_NODE) {
                        elemento = (Element) tagItem;
                        item_atributos = elemento.getAttributes();
                        Node valname = item_atributos.item(0);
                        Node valvalor = item_atributos.item(1);
                        if ((valname != null) && (valvalor != null)) {
                            String va = valname.getNodeValue();
                            //PROPIEDAD ORDEN
                            switch (va) {
                                case "orden":
                                    orden = valvalor.getNodeValue();
                                    lblOrden.setValue(valvalor.getNodeValue());
                                    break;
                                case "nombre":
                                    lblNombre.setValue(valvalor.getNodeValue());
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
                    }
                }
                //agregar las columnas
                if ((!(orden.equals("0"))) && (!(orden.isEmpty()))) {//Si es 0 no se hace nada se salta
                    row = new Row();
                    row.setSclass("row-resultado");
                    row.appendChild(lblDescripcion);
                    switch (tipoDato) {
                        case "T": {
                            final Textbox nText = new Textbox();
                            if (obligatorio.equals("0")) {
                                nText.setConstraint("no empty, no future: Ingrese Dato");
                            }
                            switch (lineas) {
                                case 0: {
                                    nText.setMultiline(true);
                                    nText.setRows(5);
                                    nText.addEventListener("onDoubleClick", new EventListener() {
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
                                            if (!copiar) {
                                                edicion.setCtrlKeys("^c^v");
                                                edicion.addEventListener(Events.ON_CTRL_KEY, new EventListener() {
                                                    @Override
                                                    public void onEvent(Event event) throws Exception {
                                                        int keyCode = ((KeyEvent) event).getKeyCode();
                                                        if (keyCode == 67 || keyCode == 86) {
                                                        }
                                                    }
                                                });
                                                edicion.addEventListener(Events.ON_RIGHT_CLICK, new EventListener() {
                                                    @Override
                                                    public void onEvent(Event event) throws Exception {
                                                    }
                                                });
                                            }
                                            winEdicion.addEventListener(Events.ON_CLOSE, new EventListener() {
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
                                    if (!copiar) {
                                        nText.setCtrlKeys("^c^v");
                                        nText.addEventListener(Events.ON_CTRL_KEY, new EventListener() {

                                            @Override
                                            public void onEvent(Event event) throws Exception {
                                                int keyCode = ((KeyEvent) event).getKeyCode();
                                                if (keyCode == 67 || keyCode == 86) {
                                                }
                                            }
                                        });
                                        nText.addEventListener(Events.ON_RIGHT_CLICK, new EventListener() {
                                            @Override
                                            public void onEvent(Event event) throws Exception {
                                            }
                                        });
                                    }
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
                            if (!(valorDef.isEmpty())) {
                                nText.setValue(valorDef);
                            }
//                            nText.setId("T" + lblNombre.getValue());
                            nText.setWidth("98%");
//                            nText.setSclass("resultado-completo");
                            row.appendChild(nText);
                            break;
                        }
                        case "N": {
                            final Decimalbox nDecimal = new Decimalbox();
                            nDecimal.setCols(20);
                            nDecimal.setWidth("98%");
                            if (!(valorDef.isEmpty())) {
                                nDecimal.setValue(new BigDecimal(valorDef));
                                nDecimal.setDisabled(true);
                            }
//                            nDecimal.setSclass("resultado-completo");
                            row.appendChild(nDecimal);
                            break;
                        }
                        case "D": {
                            final Datebox fechita = new Datebox();
                            if (obligatorio.equals("0")) {
                                fechita.setConstraint("no empty, no future: Ingrese Dato");
                            }
                            fechita.setCols(16);
                            fechita.setFormat("dd-MMM-yyyy");
                            fechita.setWidth("98%");
                            if (!(valorDef.isEmpty())) {
                                fechita.setText(valorDef);
                                fechita.setDisabled(true);
                            }
                            fechita.setMold("rounded");
//                            fechita.setSclass("resultado-completo");
                            row.appendChild(fechita);
                            break;
                        }
                        case "S": {
                            final Radiogroup grpRadio = new Radiogroup();
                            Radio lblSi = new Radio("SI");
                            Radio lblNo = new Radio("NO");
                            lblSi.setParent(grpRadio);
                            lblNo.setParent(grpRadio);
                            if (!(valorDef.isEmpty())) {
                                if (valorDef.equals("SI")) {
                                    lblSi.setChecked(true);
                                    lblNo.setChecked(false);
                                    lblSi.setDisabled(true);
                                    lblNo.setDisabled(true);
                                }
                                if (valorDef.equals("NO")) {
                                    lblSi.setChecked(false);
                                    lblNo.setChecked(true);
                                    lblSi.setDisabled(true);
                                    lblNo.setDisabled(true);
                                }
                            }
                            lblSi.setStyle("font-size: 16px;");
                            lblNo.setStyle("font-size: 16px;");
                            grpRadio.setWidth("98%");
                            row.appendChild(grpRadio);
                            break;
                        }
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
                                grpLista.setDisabled(true);
                            }
                            if (obligatorio.equals("0")) {
                                grpLista.setConstraint("no empty, no future: Ingrese Dato");
                            }
                            grpLista.setStyle("font-size: 16px;");
                            grpLista.setWidth("98%");
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
                                grpLista.setDisabled(true);
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
                        case "B": {//Audiometria
                            final Button btnAplet = new Button(lblDescripcion.getValue());
                            if (lblDescripcion.getValue().equals("AUDIOMETRIA")) {
                                btnAplet.addEventListener(Events.ON_CLICK, new EventListener() {

                                    @Override
                                    public void onEvent(Event event) throws Exception {
                                        loadAudioApplet();
                                    }
                                });
                                if (!(valorDef.isEmpty())) {
                                    btnAplet.setLabel(valorDef);
                                }
                                row.appendChild(btnAplet);
                            }
                            break;
                        }
                        case "G": {
                            final Div grupodx = new Div();
//                            grupodx.setSclass("resultado-incompleto");
                            final Grid dxgrid = new Grid();
                            dxgrid.setWidth("98%");
                            final Columns colum = new Columns();
                            Column dxc = new Column("DIAGNOSTICOS");
                            Column dxm = new Column("Especificaciones, ubicación del diagnostico");
                            final Column dxcom = new Column("");
                            dxcom.setWidth("10%");
                            colum.appendChild(dxc);
                            colum.appendChild(dxm);
                            final Button control = new Button("+");
                            control.setWidth("10%");
                            final Rows FilasCie = new Rows();
                            control.addEventListener("onClick", new EventListener() {
                                @Override
                                public void onEvent(Event event) throws Exception {
                                    agregarDx(FilasCie);
                                }
                            });
                            final Button vendx = new Button("?");
                            vendx.setDisabled(true);
//                            if (!usuario.getCsGrupos().getCodGru().equals("ADMIN")) {
//                                vendx.setDisabled(true);
//                            }
                            vendx.setWidth("10%");
                            dxcom.appendChild(control);
                            dxcom.appendChild(vendx);
                            colum.appendChild(dxcom);
                            dxgrid.appendChild(colum);
                            dxgrid.appendChild(FilasCie);
                            final Textbox txtAbierto = new Textbox();
                            txtAbierto.setWidth("98%");
                            txtAbierto.setStyle("border-color: #006400;");
                            txtAbierto.setMultiline(true);
                            txtAbierto.setRows(5);
                            txtAbierto.addEventListener(Events.ON_CLICK, new EventListener() {

                                @Override
                                public void onEvent(Event event) throws Exception {

                                    Window winEdicion = new Window();
                                    String windowMessage;
                                    windowMessage = "edicion-aux.zul";
                                    winEdicion.setWidth("550px");
                                    Executions.createComponents(windowMessage, winEdicion, null);
                                    final Textbox edicion = (Textbox) winEdicion.getFellow("edit-aux", true);
//                                    edicion.setSclass("edit-resultado");
                                    if (!copiar) {
                                        edicion.setCtrlKeys("^c^v");
                                        edicion.addEventListener(Events.ON_CTRL_KEY, new EventListener() {

                                            @Override
                                            public void onEvent(Event event) throws Exception {
                                                int keyCode = ((KeyEvent) event).getKeyCode();
                                                if (keyCode == 67 || keyCode == 86) {
                                                }
                                            }
                                        });
                                    }
                                    edicion.setValue(txtAbierto.getValue());
                                    winEdicion.addEventListener(Events.ON_CLICK, new EventListener() {

                                        @Override
                                        public void onEvent(Event event) throws Exception {
                                            txtAbierto.setValue(edicion.getValue());
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
                            if (!copiar) {
                                txtAbierto.setCtrlKeys("^c^v");
                                txtAbierto.addEventListener(Events.ON_CTRL_KEY, new EventListener() {

                                    @Override
                                    public void onEvent(Event event) throws Exception {
                                        int keyCode = ((KeyEvent) event).getKeyCode();
                                        if (keyCode == 67 || keyCode == 86) {
                                        }
                                    }
                                });
                            }
                            final Checkbox imp = new Checkbox("Imprimir códigos (opcional especialidades)");
                            imp.setWidth("10%");
                            imp.addEventListener("onCheck", new EventListener() {
                                @Override
                                public void onEvent(Event event) throws Exception {

                                }
                            });
                            imp.setDisabled(true);
                            txtAbierto.setDisabled(true);
                            if (abierto) {
                                imp.setDisabled(false);
                                txtAbierto.setDisabled(false);
                            }
                            grupodx.appendChild(dxgrid);
                            grupodx.appendChild(txtAbierto);
                            grupodx.appendChild(imp);
                            row.appendChild(grupodx);
                            break;
                        }
                        default: {
                            final Textbox nText1 = new Textbox();
                            nText1.setMaxlength(128);
                            nText1.setCols(50);
                            nText1.setWidth("98%");
//                            nText1.setSclass("resultado");
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
                        Messagebox.show("Error al cargar el formato Nodo <2 ," + lblNombre.getValue(), "Información de usuario", Messagebox.OK, Messagebox.ERROR);
                        final Textbox nText = new Textbox();
                        nText.setMaxlength(128);
                        nText.setWidth("98%");
//                        nText.setSclass("resultado");
                        row.appendChild(nText);
                        row.appendChild(lblNombre);
                        row.appendChild(lblOrden);
                    } else {
                        row.appendChild(lblNombre);
                        row.appendChild(lblOrden);
                    }
                    try {
                        row.setParent(rowsInforme);
                    } catch (Exception e) {
                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.ElementInforme()4025" + e.getMessage());
                    }

//                    try {
//                        System.out.println("   "+lblNombre.getValue());
////                        row.setId(lblNombre.getValue());
//                        row.setParent(rowsInforme);
//                    } catch (Exception e) {
//                        System.out.println("duplicidad   "+lblNombre.getValue());
//                        alert("Agregado verificar duplicidad" + lblNombre.getValue());
//                        row.setParent(rowsInforme);
//                    }
                }
            }
        } catch (Exception e) {
        }
    }
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="AUDIOMETRIA">

    private boolean saveAudiometria(Long id, String oido) throws FileNotFoundException, IOException, NamingException {
        String codigo = "", descripcion = "";
        if (oido.equalsIgnoreCase("IZQ")) {
            oido = "izquierdo";
            codigo = "AU-OI";
            descripcion = "OIDO IZQUIERDO";
        }
        if (oido.equalsIgnoreCase("DER")) {
            oido = "derecho";
            codigo = "AU-OD";
            descripcion = "OIDO DERECHO";
        }
        String oidoData = (String) session.getAttribute(oido);
        session.setAttribute(oido, "");
        ResultadoGrafico grafico;
        //Save de Imagen Oido
        if (oidoData != null) {
            grafico = new ResultadoGrafico();
            grafico.setIdXmlResultado(id);
            byte[] imagen = UtilFichero.decode64S(oidoData);
            //Imagen de Audiometria Oido
            grafico.setCod(codigo);
            grafico.setFirstUser(usuario);
            grafico.setDato(imagen);
            grafico.setDescripcion(descripcion);
            grafico = (ResultadoGrafico) admNegocio.guardar(grafico);
            if (grafico == null) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private void confirmEditAudiometria() {
        try {
            Window winEditar = new Window();
            Executions.createComponents("msg_audiometria.zul", winEditar, null);
            final Label msg = new Label();
            msg.setParent(winEditar);
            msg.setVisible(false);
            Button edit = (Button) winEditar.getFellow("MsgEditar", false);
            edit.addEventListener(Events.ON_CLICK, new EventListener() {

                @Override
                public void onEvent(Event e) throws Exception {
                    loadAudioApplet();
                    Window aux;
                    aux = (Window) msg.getParent();
                    aux.onClose();
                }
            });
            winEditar.setId("winEditar");
            winEditar.setParent(WinHistoria);
            winEditar.doModal();
        } catch (ComponentNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void loadAudioApplet() {
        try {
            Window winMensaje = new Window();
            Executions.createComponents("audiometria.zul", winMensaje, null);
            editAudiometria = true;
            contador.setRunning(false);
            winMensaje.setTitle("Audiometria");
            if (getHeight() == 100) {
                winMensaje.setWidth(Integer.toString(new Double(getHeight() * (0.8)).intValue()) + "%");
                winMensaje.setHeight(Integer.toString(new Double(getHeight() * (0.88)).intValue()) + "%");
            } else {
                winMensaje.setWidth(Integer.toString(new Double(getHeight() * (1.415)).intValue()) + "px");
                winMensaje.setHeight(Integer.toString(new Double(getHeight() * (0.9)).intValue()) + "px");
            }
            final Label msg = new Label();
            msg.setParent(winMensaje);
            msg.setVisible(false);
            Button edit = (Button) winMensaje.getFellow("btnClose", false);
            edit.addEventListener(Events.ON_CLICK, new EventListener() {

                @Override
                public void onEvent(Event e) throws Exception {

                    Window aux;
                    aux = (Window) msg.getParent();
                    aux.onClose();
                    timerRestart();
                }
            });
            winMensaje.setId("winAudio");
            winMensaje.setParent(WinHistoria);
            winMensaje.doModal();
        } catch (ComponentNotFoundException e) {
            throw new RuntimeException(e.getCause().toString(), e);
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="DIAGNOSTICOS">
    boolean bDXBlanco;
    boolean BanDel;

    private void agregarDx(final Rows filasci) {
        final Row dxun = new Row();
        final Textbox dx1 = new Textbox();
        dx1.setWidth("100%");
        dx1.setMaxlength(256);
        final Combobox dxc1 = new Combobox();
        dxc1.addEventListener("onChange", new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                if (dxc1.getValue().length() >= 3) {
                    List<Object> cies = obtenerCie(dxc1.getValue());
                    dxc1.setItemRenderer(new CieRender());
                    dxc1.setModel(new ListModelList(cies));
                }
                dxc1.open();
            }
        });
        dxc1.addEventListener("onSelect", new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {

                dxc1.setDisabled(true);
            }
        });
        dxc1.setWidth("100%");
        final Div ctrles = new Div();
        final Button dxEliminar = new Button("-");
        dxEliminar.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                Row elim = new Row();
                Div eli = new Div();
                eli = (Div) dxEliminar.getParent();
                elim = (Row) eli.getParent();
                filasci.removeChild(elim);
                BanDel = true;
                //eliminado
            }
        });
        dxEliminar.setWidth("40%");
        ctrles.appendChild(dxEliminar);
        dxun.appendChild(dxc1);
        dxun.appendChild(dx1);
        dxun.appendChild(ctrles);
        filasci.appendChild(dxun);
        if (WinHistoria.isMaximized()) {
            WinHistoria.setMaximized(false);
        } else {
            WinHistoria.setMaximized(true);
        }
    }

    private void agregarDxI(String codr, String codg, String desci, String codCie, String tipodx, String dxmed, final Rows filasci, boolean estado) {
        final Row dxun = new Row();
        final Textbox dx1 = new Textbox();
        dx1.setWidth("100%");
        dx1.setMaxlength(256);
        dx1.setValue(dxmed);
        dx1.addEventListener("onDoubleClick", new EventListener() {

            @Override
            public void onEvent(Event event) throws Exception {
                Window winEdicion = new Window();
                String windowMessage;
                windowMessage = "edicion-aux.zul";
                winEdicion.setWidth("550px");
                Executions.createComponents(windowMessage, winEdicion, null);
                final Textbox edicion = (Textbox) winEdicion.getFellow("edit-aux", true);
                edicion.setSclass("edit-resultado");
                edicion.setValue(dx1.getValue());
                winEdicion.addEventListener("onClose", new EventListener() {

                    @Override
                    public void onEvent(Event event) throws Exception {
                        dx1.setValue(edicion.getValue());
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
        final Combobox dxc1 = new Combobox();
        dxc1.addEventListener("onChange", new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                String val = dxc1.getValue();
                if (dxc1.getValue().length() >= 4) {
                    List<Object> cies = obtenerCie(dxc1.getValue());
                    try {
                        dxc1.setItemRenderer(new CieRender());
                        dxc1.setModel(new ListModelList(cies));
                    } catch (Exception e) {
                        System.out.println(usuario + "Error en comboDX" + e.getMessage() + bOrden.getValue());
                    }
                }
                dxc1.open();
            }
        });
        dxc1.addEventListener("onSelect", new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
            }
        });
        dxc1.setDisabled(true);
        Comboitem def = new Comboitem(desci);
        Ciediez obj = new Ciediez(codCie);
        obj.setDescripcion(desci);
        obj.setCodRef(codr);
        obj.setTipo(tipodx);
        def.setValue(obj);
        dxc1.appendChild(def);
        dxc1.setSelectedItem(def);
        dxc1.setWidth("100%");
        final Div ctrles = new Div();
        final Button dxEliminar = new Button("-");
        dxEliminar.addEventListener("onClick", new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                Row elim = new Row();
                Div eli = new Div();
                eli = (Div) dxEliminar.getParent();
                elim = (Row) eli.getParent();
                filasci.removeChild(elim);
                BanDel = true;
//                eliminarDx();
            }
        });
        dxEliminar.setWidth("40%");
        ctrles.appendChild(dxEliminar);
        if (!estado) {
            dx1.setDisabled(true);
            dxc1.setDisabled(true);
            dxEliminar.setDisabled(true);
        }
        dxun.appendChild(dxc1);
        dxun.appendChild(dx1);
        dxun.appendChild(ctrles);
        filasci.appendChild(dxun);
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

    public void loadCie(List objCiediez, Rows listCie) throws NamingException {
        for (Object objCie : objCiediez) {
            Ciediez idCIE = (Ciediez) objCie;
            final Row filaOrdenHistoria = new Row();
            filaOrdenHistoria.setValue(idCIE);
            Label Cod = new Label(idCIE.getCodCie());
            Label Descripcion = new Label(idCIE.getDescripcion());
            Radiogroup grpactivo = new Radiogroup();
            Radio lblSi = new Radio("SI");
            Radio lblNo = new Radio("NO");
            lblSi.setParent(grpactivo);
            lblNo.setParent(grpactivo);
            if (idCIE.getLockReg().toString().equals("0")) {
                lblSi.setSelected(true);
            }
            lblSi.addEventListener("onCheck", new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    try {
                        Ciediez objac = filaOrdenHistoria.getValue();
                        objac.setLockReg(Short.valueOf("0"));
                        if (admNegocio.actualizar(objac)) {
                            filaOrdenHistoria.setValue(objac);
                        };

                    } catch (NamingException ex) {
                        Logger.getLogger(ControladoraHistoria.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }

                }
            }
            );
            lblNo.addEventListener("onCheck", new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    Ciediez objac = filaOrdenHistoria.getValue();
                    objac.setLockReg(Short.valueOf("1"));
                    if (admNegocio.actualizar(objac)) {
                        filaOrdenHistoria.setValue(objac);
                    };
                }
            }
            );
            filaOrdenHistoria.appendChild(Cod);
            filaOrdenHistoria.appendChild(Descripcion);
            filaOrdenHistoria.appendChild(grpactivo);
            filaOrdenHistoria.setParent(listCie);
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="LIMPIAR">
    private void limpiarHistoria() {
        cleanGridInforme();
        gridInforme.setVisible(false);
        ocupa = "";
        depar = "";
        orige = "";
        grupo_r = "";
        newDoc = null;
        banModificar = false;
        resultadoXML = null;
        diaTotal = null;
        diagobs = null;
        numFormatosInforme = 0;
        diagnosticoXml = "";
        recomendacionesXml = "";
        prescripcionesXml = "";
        ocupacion = "";

    }

    private void cleanGridInforme() {
        rowsInforme.getChildren().clear();
    }
    // </editor-fold>
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="ANTECEDENTES">
    private Tab TabAntecedentes;
    private Rows rowsAnt;
    private int numFormatosAntecedentes;
    private XmlAntecedentes XMLantecedentes;
    private Tabpanel antecedentesP;
    boolean banAntecedentes = false;
    boolean banIMPAntecedentes = false;

    Include anteV;
    Treechildren root;

    public XmlAntecedentes getXMLantecedentes() {
        return XMLantecedentes;
    }

    public void setXMLantecedentes(XmlAntecedentes XMLantecedentes) {
        this.XMLantecedentes = XMLantecedentes;
    }

    private void limpiarAntecedentes() {
        try {
            XMLAntecedentes = null;
            banAntecedentes = false;
            numFormatosAntecedentes = 0;
            List hijo = antecedentesP.getChildren();
            int j = hijo.size();
            if (j > 0) {
                antecedentesP.removeChild((Component) hijo.get(0));
            }
        } catch (Exception e) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.limpiarAntecedentes()" + e.getMessage());
        }
    }

    private void limpiarArbol() {
        try {
            List hijo = east.getChildren();
            for (Object object : hijo) {
                east.removeChild((Component) object);
            }
        } catch (Exception e) {
        }
    }

    private Document XMLAntecente(Document doc) {
        Element elemento = null;
        Element Frame = null;
        Element pag = null;
        Row fila;
        Label etiqueta;
        int con = rowsAnt.getGroupCount();
        List grupos = rowsAnt.getGroups();
        boolean datos = false;
        try {
            doc = DatosGeneralesInforme(doc);
            datos = true;
        } catch (RuntimeException e) {
            System.out.println(usuario + "Error al generar DATOS GENERALES ANTECEDENTES " + nomPractica + e.getCause().getMessage() + " " + bOrden.getValue());
            alert("Error al generar DATOS GENERALES ANTECEDENTES");
        }
        try {
            if (datos) {
                boolean subgrupo = false;
                for (int i = 0; i < con; i++) {
                    Group grp = (Group) grupos.get(i);
                    int x = grp.getItemCount();
                    int y = grp.getChildren().size();
                    if ((x == 0) && (!(subgrupo))) {//es pagina creamos en elemento de pagina
                        String x1 = grp.getLabel().toLowerCase();
                        String idformato = grp.getId();
                        Integer c = x1.indexOf(":") + 2;
                        String x2 = x1.substring(c);
                        subgrupo = true;
                        if (x1.length() > 7) {
                            x1 = x1.substring(0, 7);
                        }
                        String pagina = x1.substring(0, x1.length() - 1);
                        if (pagina.equals("pagina")) {
                            pag = doc.createElement(x1); // creamos el elemento raiz
                            pag.setAttribute("Id", x2);
                            pag.setAttribute("IdFormato", idformato);
                            doc.getDocumentElement().appendChild(pag); //pegamos la raiz al documento
                        } else {
                            subgrupo = false;
                            x1 = grp.getId().toLowerCase();
                            Frame = doc.createElement(x1); // creamos el elemento raiz
                            Frame.setAttribute("descripcion", grp.getLabel());
                            if (grp.isVisible()) {
                            } else {
                                Frame.setAttribute("sexo", "!" + idSexo.getValue());
                            }
                            pag.appendChild(Frame); //pegamos la raiz al documento
                        }
                    } else {
                        subgrupo = false;
                        String x1 = grp.getId().toLowerCase();
                        Frame = doc.createElement(x1); // creamos el elemento raiz
                        Frame.setAttribute("descripcion", grp.getLabel());
                        if (grp.isVisible()) {
                        } else {
                            Frame.setAttribute("sexo", "!" + idSexo.getValue());
                        }
                        pag.appendChild(Frame); //pegamos la raiz al documento
                    }
                    String tipoDato = "", nombre = "", descripcion = "", orden = "", dato = "";
                    for (int j = 0; j < x; j++) {
                        fila = (Row) grp.getItems().get(j);
                        dato = "";
                        Integer lineas = -1;
                        String ItemLista = "";
                        if (fila.getChildren().size() > 0) {
                            int g = fila.getChildren().size();
                            for (int k = 0; k < fila.getChildren().size(); k++) {
                                switch (k) {
                                    case 0: {
                                        etiqueta = (Label) fila.getChildren().get(k);
                                        descripcion = etiqueta.getValue(); //creamos un nuevo elemento
                                    }
                                    break;
                                    case 1: //asignar depende del tipo
                                    {
                                        try {
                                            String tipo = fila.getChildren().get(k).toString();
                                            int c = tipo.indexOf(" ");
                                            tipo = tipo.substring(1, c);
                                            switch (tipo) {
                                                case "Textbox": {
                                                    try {
                                                        Textbox valor = (Textbox) fila.getChildren().get(k);
                                                        tipoDato = "T";
                                                        dato = valor.getText();
                                                        if (valor.isMultiline()) {
                                                            lineas = 0;
                                                        } else {
                                                            if (valor.getMaxlength() == 128) {
                                                                lineas = 1;
                                                            } else {
                                                                if (valor.getMaxlength() == 256) {
                                                                    lineas = 2;
                                                                } else {
                                                                    lineas = 1;
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLAntecente()" + e.getMessage() + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Hlayout": {
                                                    try {
                                                        Hlayout valor = (Hlayout) fila.getChildren().get(k);
                                                        if (valor.getChildren().size() > 0) {
                                                            for (int l = 0; l < valor.getChildren().size(); l++) {
                                                                switch (l) {
                                                                    case 0:
                                                                        break;
                                                                    case 1: //asignar depende del tipo
                                                                    {
                                                                        String tipo1 = valor.getChildren().get(l).toString();
                                                                        int c1 = tipo1.indexOf(" ");
                                                                        tipo1 = tipo1.substring(1, c);
                                                                        if (tipo1.equals("Textbox")) {
                                                                            Textbox valor1 = (Textbox) valor.getChildren().get(k);
                                                                            tipoDato = "T";
                                                                            dato = valor1.getText();

                                                                            if (valor1.isMultiline()) {
                                                                                lineas = 0;
                                                                            } else {
                                                                                if (valor1.getMaxlength() == 128) {
                                                                                    lineas = 1;
                                                                                } else {
                                                                                    if (valor1.getMaxlength() == 256) {
                                                                                        lineas = 2;
                                                                                    } else {
                                                                                        lineas = 1;
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLAntecente()" + e.getMessage() + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Vlayout": {
                                                    try {
                                                        Vlayout valor = (Vlayout) fila.getChildren().get(k);
                                                        if (valor.getChildren().size() > 0) {
                                                            for (int l = 0; l < valor.getChildren().size(); l++) {
                                                                String tipo1;
                                                                int c1;
                                                                switch (l) {
                                                                    case 0: //asignar depende del tipo
                                                                    {
                                                                        tipo1 = valor.getChildren().get(l).toString();
                                                                        c1 = tipo1.indexOf(" ");
                                                                        tipo1 = tipo1.substring(1, c);
                                                                        if (tipo1.equals("Textbox")) {
                                                                            Textbox valor1 = (Textbox) valor.getChildren().get(l);
                                                                            dato = valor1.getText();
                                                                            lineas = 0;
                                                                        }
                                                                        break;
                                                                    }
                                                                    case 1: //asignar depende del tipo
                                                                    {
                                                                        tipo1 = valor.getChildren().get(l).toString();
                                                                        c1 = tipo1.indexOf(" ");
                                                                        tipo1 = tipo1.substring(1, c);
                                                                        if (tipo1.contains("Button")) {

                                                                            Button valorb = (Button) valor.getChildren().get(l);
                                                                            if (valorb.getLabel().toUpperCase().contains("DIAG")) {
                                                                                tipoDato = "G";
                                                                            }
                                                                        }
                                                                        break;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    } catch (WrongValueException e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLAntecente()" + e.getMessage() + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Decimalbox": {
                                                    try {
                                                        Decimalbox valor = (Decimalbox) fila.getChildren().get(k);
                                                        tipoDato = "N";
                                                        dato = valor.getText();
                                                    } catch (Exception e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLAntecente()" + e.getMessage() + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Datebox": {
                                                    try {

                                                        Datebox valor = (Datebox) fila.getChildren().get(k);
                                                        tipoDato = "D";
                                                        valor.setFormat("dd-MMM-yyyy");
                                                        dato = valor.getText();
                                                    } catch (Exception e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLAntecente()" + e.getMessage() + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Radiogroup": {
                                                    try {
                                                        Radiogroup grpRadio = (Radiogroup) fila.getChildren().get(k);
                                                        if (!(grpRadio.isVisible())) {
                                                        }
                                                        int pos = grpRadio.getSelectedIndex();
                                                        if (pos > -1) {
                                                            Radio lblradio = grpRadio.getSelectedItem();
                                                            dato = lblradio.getLabel();
                                                        } else {
                                                            dato = " ";
                                                        }
                                                        tipoDato = "S";
                                                    } catch (Exception e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLAntecente()" + e.getMessage() + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Combobox": {
                                                    try {
                                                        String pos = "";
                                                        Combobox grpRadio = (Combobox) fila.getChildren().get(k);
                                                        int po = grpRadio.getSelectedIndex();
                                                        if (po > -1) {
                                                            pos = grpRadio.getSelectedItem().getLabel();
                                                        }
                                                        if (pos.isEmpty()) {
                                                            dato = "";
                                                        } else {
                                                            dato = pos;
                                                        }
                                                        ItemLista = "";
                                                        for (int l = 0; l < grpRadio.getItemCount(); l++) {
                                                            Comboitem v = grpRadio.getItemAtIndex(l);
                                                            ItemLista = ItemLista + v.getLabel() + "/";
                                                        }
                                                        tipoDato = "L";
                                                    } catch (Exception e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLAntecente()" + e.getMessage() + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Hbox": {
                                                    try {
                                                        String pos = "";
                                                        Hbox grpGrupo = (Hbox) fila.getChildren().get(k);
                                                        Combobox comboLista = (Combobox) grpGrupo.getChildren().get(0);
                                                        Textbox porq = (Textbox) grpGrupo.getChildren().get(2);
                                                        int po = comboLista.getSelectedIndex();
                                                        if (po > -1) {
                                                            pos = comboLista.getSelectedItem().getLabel();
                                                        }
                                                        if (pos.isEmpty()) {
                                                            dato = "";
                                                        } else {
                                                            dato = pos + "|" + porq.getValue() + "|";
                                                        }
                                                        ItemLista = "";
                                                        for (int l = 0; l < comboLista.getItemCount(); l++) {
                                                            Comboitem v = comboLista.getItemAtIndex(l);
                                                            ItemLista = ItemLista + v.getLabel() + "/";
                                                        }
                                                        tipoDato = "LD";
                                                    } catch (Exception e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLAntecente()" + e.getMessage() + descripcion);
                                                    }
                                                    break;
                                                }
                                                case "Div": {
                                                    tipoDato = "G";
                                                    dato = "";
                                                    break;
                                                }
                                                case "Button": {
                                                    try {
                                                        Button btn = (Button) fila.getChildren().get(k);
                                                        if (btn.getLabel().toUpperCase().contains("DIAG")) {
                                                            tipoDato = "G";
                                                            dato = "";
                                                        } else {
                                                            tipoDato = "B";
                                                            dato = "";
                                                        }
                                                    } catch (Exception e) {
                                                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.XMLAntecente()" + e.getMessage() + descripcion);
                                                    }
                                                    break;
                                                }
                                                default:
                                                    if (tipo.equals("Button")) {
                                                        tipoDato = "B";
                                                        dato = "";
                                                    } else {
                                                        tipoDato = "T";
                                                        dato = "";
                                                    }
                                                    break;
                                            }
                                        } catch (WrongValueException e) {
                                            System.out.println(usuario + "Error Asignacion de tipo " + descripcion + " " + bOrden.getValue() + e.getMessage() + descripcion);
                                        }
                                    }
                                    break;
                                    case 3: {
                                        try {
                                            etiqueta = (Label) fila.getChildren().get(k);
                                            orden = etiqueta.getValue(); //creamos un nuevo elemento
                                        } catch (Exception e) {
                                            System.out.println(usuario + "Error Asignacion de tipo " + descripcion + " " + bOrden.getValue() + e.getMessage() + descripcion);
                                        }
                                    }
                                    break;
                                    case 2: {//nombre
                                        try {
                                            etiqueta = (Label) fila.getChildren().get(k);
                                            nombre = etiqueta.getValue();
                                        } catch (Exception e) {
                                            System.out.println(usuario + "etiqueta  " + descripcion + e.getCause().getMessage() + " " + bOrden.getValue() + e.getMessage());
                                        }
                                    }
                                    break;
                                }
                            }
                            elemento = doc.createElement(nombre.trim()); //creamos un nuevo elemento
                            if (ItemLista != null) {
                                elemento.setAttribute("item_lista", ItemLista);
                                ItemLista = null;
                            }
                            if (lineas > -1) {
                                elemento.setAttribute("lineas", lineas.toString());
                                lineas = -1;
                            }
                            if (dato == null) {
                                dato = "";
                            }
                            elemento.setAttribute("tipo_dato", tipoDato);
                            elemento.setAttribute("descripcion", descripcion);
                            elemento.setAttribute("orden", orden);
                            elemento.appendChild(doc.createTextNode(dato));
                            Frame.appendChild(elemento); //pegamos el elemento hijo a la raiz
                        }
                    }
                }
                NodeList listaHijos = doc.getChildNodes();
                listaHijos = listaHijos.item(0).getChildNodes();
                Node NPagina;
                NPagina = listaHijos.item(0);
            }
        } catch (RuntimeException e) {
            System.out.println(usuario + "Error el generar XML " + e.getMessage() + " " + bOrden.getValue() + e.getMessage());
            alert("Error el generar XML ");
        }
        return doc;
    }

    private boolean guardarAntecedentes(String estado, boolean limpiar, boolean avisos, boolean modifica) throws InterruptedException {
        XmlAntecedentes nuevoAntece = new XmlAntecedentes();
        ProcesosSession admiSessionUsuario = new ProcesosSession();
        sessionOk objsessiActica = null;
        try {
            if (modifica) {//modifica
                try {
//                    nuevoAntece = ((XmlAntecedentes) admiSessionUsuario.ObtenerAtributoSession(12, desktop.getSession()));
                    objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
                    nuevoAntece = objsessiActica.getObjXmlAntecedentes();
                } catch (Exception e) {
                    System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.guardarAntecedentes()" + e.getMessage());
                    alert("No se pudo recuperar el Antecedente");
                }
                int nodos = DocAnte.getChildNodes().item(0).getChildNodes().getLength();
                if (nodos == numFormatosAntecedentes) {
                    nuevoAntece.setEstado(estado);
                    nuevoAntece.setIdMedico(medico.getId());
                    nuevoAntece.setMedico(medico.getNombreMedico());
                    nuevoAntece.setLastUser(usuario);
                    nuevoAntece.setOrden(objOrden);
//                    nuevoAntece.setIdOrden(BigInteger.valueOf(objOrden.getId()));
                    nuevoAntece.setAntecedentes(ConvertirDocumento.getConvertirDocumentoString(DocAnte));
                    if (admNegocio.actualizar(nuevoAntece)) {
                        setXMLantecedentes(nuevoAntece);
                        if (objsessiActica != null) {
                            //  objsessiActica.setDocAntecedentes(DocAnte);
                            objsessiActica.setObjXmlAntecedentes(getXMLantecedentes());
                            objsessiActica.setDocAntecedentes(DocAnte);
                            admiSessionUsuario.AgregarAtributoSession(2, objsessiActica, desktop.getSession());
                        } else {
                            Messagebox.show("Registro antecedentes no fue actualiado", "Información", Messagebox.OK, Messagebox.INFORMATION);
                        }
                    } else {
                        Messagebox.show("Registro antecedentes no fue actualiado", "Información", Messagebox.OK, Messagebox.INFORMATION);
                    }
                } else {
                    Messagebox.show("Resultado del Informe no fue Modificado reporte el Mensaje", "Información", Messagebox.OK, Messagebox.INFORMATION);
                    return false;
                }
            } else {//nuevo
                nuevoAntece.setEstado(estado);
                nuevoAntece.setEmpresa(objOrden.getOrganizacion().getAbreviatura());
                nuevoAntece.setIdEmpresa(objOrden.getOrganizacion().getId().intValue());
                nuevoAntece.setIdHistoria(objHistoria);
                nuevoAntece.setFecha(new Date());
                nuevoAntece.setIdMedico(medico.getId());
                nuevoAntece.setMedico(medico.getNombreMedico());
                nuevoAntece.setFirstUser(usuario);
                try {
                    nuevoAntece.setIdPractica(PracticaInforme.getIdPractica());
                } catch (Exception e) {
                }
                nuevoAntece.setCodOri(getObjOrden().getCodOri());
                nuevoAntece.setCodPac(getObjHistoria().getIdNextlab());
                nuevoAntece.setNroOrd(getObjOrden().getCodOrd());
                //   nuevoAntece.setIdOrden(BigInteger.valueOf(objOrden.getId()));
                nuevoAntece.setOrden(objOrden);
                int nodos = DocAnte.getChildNodes().item(0).getChildNodes().getLength();
                if (nodos == numFormatosAntecedentes) {
                    nuevoAntece.setAntecedentes(ConvertirDocumento.getConvertirDocumentoString(DocAnte));
                    nuevoAntece = (XmlAntecedentes) admNegocio.guardar(nuevoAntece);
                    setXMLantecedentes(nuevoAntece);
                    objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
                    objsessiActica.setObjXmlAntecedentes(getXMLantecedentes());
                    objsessiActica.setDocAntecedentes(DocAnte);
                    admiSessionUsuario.AgregarAtributoSession(2, objsessiActica, desktop.getSession());
                    //  admiSessionUsuario.AgregarAtributoSession(12, getXMLantecedentes(), desktop.getSession());
                    Textbox modi = (Textbox) anteV.getFellow("modificar", false);
                    modi.setValue("T");
                } else {
                    Messagebox.show("Resultado del Informe no fue Modificado reporte el Mensaje", "Información", Messagebox.OK, Messagebox.INFORMATION);
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.guardarAntecedentes() 4654");
        } finally {
            if (limpiar) {
            } else {
            }
        }
        return true;
    }

    private void CrearAntecedentes(boolean modifica, String estado) {
        try {
            try {
                crearDirectorioPrincipal();
            } catch (Exception e) {
                System.out.println(usuario + "Error Antecedentes crearDirectorioPrincipal()" + bOrden.getValue());
            }
            CreacionXml admXml;
            try {
                admXml = new CreacionXml();
                DocAnte = admXml.crearDocumento();
            } catch (Exception e) {
                System.out.println(usuario + "Error new Antecedentes CreacionXml() + crearDocumento()" + bOrden.getValue());
            }
            try {
                try {
                    DocAnte = XMLAntecente(DocAnte);
                } catch (Exception e) {
                    System.out.println(usuario + "Error Antecedentes CreamosXMLGuardar(newDoc)" + bOrden.getValue());
                }
                int numNodo = DocAnte.getChildNodes().item(0).getChildNodes().getLength();
                if (numNodo == numFormatosAntecedentes) {
                    try {
                        guardarAntecedentes(estado, true, true, modifica);
                    } catch (InterruptedException ex) {
                        System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.GuardarAntecedentes()" + ex.getMessage());
                        Logger
                                .getLogger(ControladoraHistoria.class
                                        .getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (DOMException | ComponentNotFoundException | WrongValueException e) {
                String g = ConvertirDocumento.getConvertirDocumentoString(newDoc);
                System.out.println(usuario + "ERROR AL GENERAR ANTECEDENTES" + g + bOrden.getValue());
                alert("ERROR AL GENERAR EL ANTECEDENTES ");
            }
        } catch (WrongValueException e) {
            alert("ERROR AL GENERAR EL ANTECEDENTES (eventGuardar)" + bOrden.getValue());
        }
    }

    private void loadAntecedentes() {
        try {
            anteV = new Include("/ocupacional/inputAntecedentes.zul");
            ProcesosSession admiSessionUsuario = new ProcesosSession();
            sessionOk objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
            objsessiActica.setObjHistoria(getObjHistoria());
            objsessiActica.setObjOrden(getObjOrden());
            objsessiActica.setIdPractica(Integer.parseInt(IdPractica.getValue()));
            objsessiActica.setEstado(stado);
            if (docAnteHis != null) {
                objsessiActica.setDocAntecedentes(docAnteHis);
            } else {
                objsessiActica.setDocAntecedentes(null);
            }
            admiSessionUsuario.AgregarAtributoSession(2, objsessiActica, desktop.getSession());
            antecedentesP.appendChild(anteV);
        } catch (Exception e) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.loadAntecedentes()");
        }
    }

    private void guardarAntecedentes(boolean modifica, String estado) {
        try {
            if ((banAntecedentes) || (modifica)) {
                rowsAnt = (Rows) anteV.getFellow("rowsAnt", false);
                Textbox num = (Textbox) anteV.getFellow("numFormato", false);
                numFormatosAntecedentes = Integer.parseInt(num.getValue());
                CrearAntecedentes(modifica, estado);
            }
        } catch (Exception e) {
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="RECETA">
    private Iframe Rreceta;
    private Tab TabReceta;
    private Tabpanel recetaP;
    boolean banReceta = false;

    private void limpiarReceta() {
        try {
            List hijo = recetaP.getChildren();
            for (Object object : hijo) {
                recetaP.removeChild((Component) object);
            }
        } catch (Exception e) {
        }
        banReceta = false;
    }

    private void llamarReceta() {
        try {
            XMLReceta = null;
            Map<String, Object> wSQL = new HashMap<>();
            wSQL.put("idOrden ?=", BigInteger.valueOf(Long.parseLong(idOrden.getValue())));
            wSQL.put("idPractica ?=", Integer.parseInt(IdPractica.getValue()));
            XMLReceta = admNegocio.getData(new Receta(), wSQL, null, null);
            if (XMLReceta.size() == 1) {
                loadReceta((Receta) XMLReceta.get(0), true);
            } else {
                loadReceta(null, false);
            }
        } catch (NamingException ex) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.llamarReceta()");
            Logger
                    .getLogger(ControladoraHistoria.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadReceta(final Receta obj, Boolean bandera) {
        if (bandera) {
            System.out.println("Nuevo");
            if (obj.getEstado().equalsIgnoreCase("IN")) {
                final Include recetaV = new Include("/ocupacional/inputReceta.zul");
                ProcesosSession admiSessionUsuario = new ProcesosSession();

                sessionOk objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
                objsessiActica.setObjHistoria(getObjHistoria());
                objsessiActica.setObjOrden(getObjOrden());
                objsessiActica.setIdPractica(Integer.parseInt(IdPractica.getValue()));
                admiSessionUsuario.AgregarAtributoSession(2, objsessiActica, desktop.getSession());
                recetaP.appendChild(recetaV);
            } else {
                Map<String, Object> wSQL = new HashMap<>();
                String pat = "R" + obj.getId() + "P R";
                wSQL.put("id", obj.getId());
                AMedia mediaCarga = null;
                ControladoraReceta rec = new ControladoraReceta();
                mediaCarga = rec.loadReceta(pat, wSQL, obj.getId());
                if (mediaCarga != null) {
                    Rreceta = new Iframe();
                    Rreceta.setWidth("100%");
                    Rreceta.setHeight("100%");
                    Rreceta.setContent(mediaCarga);
                    Rreceta.setVisible(true);
                    recetaP.appendChild(Rreceta);
                } else {
                    Messagebox.show("El informe esta incompleto o no esta¡ cerrado, consulte con el medico responsable",
                            "Información / Esta¡ incompleto el informe", Messagebox.OK, Messagebox.INFORMATION);
                }
            }
        } else {
            System.out.println("Nuevo");
            final Include recetaV = new Include("/ocupacional/inputReceta.zul");
            ProcesosSession admiSessionUsuario = new ProcesosSession();
            sessionOk objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
            objsessiActica.setObjHistoria(getObjHistoria());
            objsessiActica.setObjOrden(getObjOrden());
            objsessiActica.setIdPractica(Integer.parseInt(IdPractica.getValue()));
            admiSessionUsuario.AgregarAtributoSession(2, objsessiActica, desktop.getSession());

            recetaP.appendChild(recetaV);
        }

    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="RESET/LIMPIAR">
    private void limpiarDatos() {
        try {

            cleanFiltro();
            limpiarRegistros();
            limpiarHistoria();
            limpiarReceta();
            limpiarAntecedentes();
            limpiarArbol();
        } catch (Exception e) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.limpiarDatos()" + e.getMessage());
            alert("Seleccione menu historia");
        }
    }

    private void reset() {
        try {
            BanDel = false;
            east.setVisible(false);
            TabHistoria.setSelected(true);
            tabbox.setVisible(false);
            btnBuscar.setDisabled(false);
            btnGuardar.setDisabled(false);
            bOrden.setDisabled(false);
            bHistorial.setDisabled(false);
            docAnteHis = null;
            limpiarDatos();
            DocAnte = null;
            timerStop();
            counter.setValue("");
        } catch (Exception e) {
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.reset()" + e.getMessage());
            alert("Seleccione menu historia");
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="CONSULTAS">
    private Object getHistoria(Object tabla, String idHistoria, String idOrden, String NumCedula) {
        Object his = null;
        try {
            Map<String, Object> wSQL = new HashMap<>();
            List oSQL = new ArrayList<>();
            wSQL.put("orden.codOrd ?=", idOrden);
            wSQL.put("id ?=", idHistoria);
            wSQL.put("numId ?=", NumCedula);
            oSQL.add("id");
            try {
                his = admNegocio.getData(tabla, wSQL, null, oSQL).get(0);
            } catch (Exception e) {
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraHistoriaT.getHistoria()");
            }
            if (his == null) {
                wSQL = new HashMap<>();
                oSQL = new ArrayList<>();
                wSQL.put("orden.id ?=", idOrden);
                wSQL.put("id ?=", idHistoria);
                wSQL.put("numId ?=", NumCedula);
                oSQL.add("id");
                his = admNegocio.getData(tabla, wSQL, null, oSQL).get(0);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ControladoraHistoria.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return his;
    }

    public List getCObjetos(Object tabla, String refcolumnaOrden, Object valorLike, boolean conLike, boolean bandera) throws NamingException {
        Map<String, Object> wSQL = new HashMap<>();
        List oSQL = new ArrayList();
        String ref;
        if (bandera) {
            wSQL.put("lockReg ?=", 0);

        } else {
            wSQL.put("lockReg ?=", 1);
        }
        if (conLike) {
            ref = refcolumnaOrden + " ?like";

        } else {
            ref = refcolumnaOrden + " ?=";

        }
        wSQL.put(ref, valorLike);

        oSQL.add(refcolumnaOrden);
        return admNegocio.getData(tabla, wSQL, null, oSQL);
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="ABREVIATURAS">
    public List<Object> getCabreviaturas(Object tabla, String idAreaP, String IdAreaM) throws NamingException {
        Map<String, Object> wSQL = new HashMap<>();
        wSQL.put("lockReg ?=", 0);
        wSQL.put("Medico.area.id ?=", IdAreaM);
        wSQL.put("Medico.area.id ?=", idAreaP);
        return admNegocio.getData(tabla, wSQL, null, null);
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Carpetas y archivos fisico">
    private void crearDirectorioPrincipal() {
        try {
            String dir = new File(System.getProperty("user.dir")).getAbsolutePath();
            File file = new File(dir + "/auditoria");
            if (!file.exists()) {
                file.mkdirs();
            }
            setPath(dir + "/auditoria");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // </editor-fold>
}

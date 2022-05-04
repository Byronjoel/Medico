/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.coneccion;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopyFields;
import com.lowagie.text.pdf.PdfReader;
import ec.com.cubosoft.avamed.mensajes.AdmMensajes;
import ec.com.cubosoft.avamed.mensajes.TipoMensaje;
import ec.com.cubosoft.avamed.modelo.core.*;
import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.ingreso.PracticaXOrden;
import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import ec.com.cubosoft.avamed.modelo.nextla.LisoriEmpr;
import ec.com.cubosoft.avamed.modelo.nextla.LispetAvanex;
import ec.com.cubosoft.avamed.modelo.nextla.Listcata;
import ec.com.cubosoft.avamed.modelo.nextla.STrausu;
import ec.com.cubosoft.avamed.modelo.nextla.SUsuar;
import ec.com.cubosoft.avamed.modelo.nextla.sessionOk;
import ec.com.cubosoft.avamed.modelo.organizacion.Organizacion;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import ec.com.cubosoft.avamed.modelo.practica.NombreP;
import ec.com.cubosoft.avamed.negocio.CalcularEdad;
import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import ec.com.cubosoft.avamed.procesos.AdmiNegocioSql;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.ComponentNotFoundException;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zul.Button;
import org.zkoss.zul.*;

/**
 * @author Juan Pablo Chavez
 * @version 1.0.1
 *
 * @author Patricia Amoroso
 * @version 1.0
 */
public class ControladoraIngreso extends GenericForwardComposer {

    private Button btnBuscar, btnReset, modOrd, btnNew, btnGuardar, vPaciente, btnOpen, btnPreview, btnUpdate, btnDelete, btnPrint, btnSync, btnCancel;
    private Textbox txtOrden, txtHistoria, txtPaciente, idMedP;
    private Label idNombre, idOrigen, idSexo, idEdad, idCI, idOrden, idOrdenPo, idMed;
    private Include barraBotones;
    private Combobox idEmpr;
    private Integer alta;
    private Integer baja;
    private Integer modi;
    SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd", new Locale("es", "ES"));
    private Historia objHistoria;

    private LispetAvanex objOrdenN;
    private List ListapedidosN = new ArrayList();
    private Listbox pedidos;
    private Orden objOrdenP;
    Window WinIngreso;
    private String usuario;
    private String anos;
    Button vOrden;
    Window modalDialog;
    Rows practicas;
    private Nombre medico;
    private boolean creado = true;
    List areas = new ArrayList();
    private boolean anular = true;
    private boolean abierto;
    private boolean impr;
    private boolean carga;
    private boolean edit;
    private boolean copia;
    Organizacion empresa;

    //<editor-fold defaultstate="collapsed" desc="Cargar Pagina">
    public void onCreate$WinIngreso() {
        try {
            modificarSession();
        } catch (Exception e) {
            System.out.println("modificar session: " + e.toString());
        }

    }
//

    private void modificarSession() {
        try {
            AdmiNegocio admNegocio = new AdmiNegocio();
            //probar aqui by Erick
            ProcesosSession admiSessionUsuario = new ProcesosSession();
            System.out.println("admisession" + admiSessionUsuario);
            sessionOk objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
            if (objsessiActica != null) {
                objsessiActica.getPagina();
            } else {
                System.out.println("error en la sessión activa");
            }

            //modificado por Erick
            //1
            if (page.getId() != null) {
                //System.out.println("page ok");
            } else {
                //System.out.println("page.getId: " + page.getId() + "page title" + page.getTitle());
            }
            //2 
            objsessiActica.setPagina(page.getId());
            List listaper = new ArrayList();
            CsUsuarios usuarioP = null;
            SUsuar usuarioN = null;
            //usuario = "";
            usuario = null;
            admiSessionUsuario.AgregarAtributoSession(2, objsessiActica, desktop.getSession());
            if (objsessiActica.getTipo() == 1) {
                listaper = objsessiActica.getPerUsuNext();
                usuarioN = objsessiActica.getUsuarioN();
                usuario = usuarioN.getUsuario();
                Perxuser objpe = objsessiActica.getPerUsuNex();
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
                        copia = false;
                    } else {
                        copia = true;
                    }
                }
            } else {
                listaper = objsessiActica.getPerUsuAva();
                usuarioP = objsessiActica.getUsuarioP();
                usuario = usuarioP.getUsuario();

            }
            if (usuarioP != null) {
                if (usuarioP.getMedicos().size() > 0) {
                    medico = usuarioP.getMedicos().get(0);
                } else {
                    Map<String, Object> wSQL = new HashMap<>();
                    List oSQL = new ArrayList();
                    wSQL.put("id ?=", 0);
                    List data = admNegocio.getData(new Nombre(), wSQL, null, oSQL);
                    if (data.size() > 0) {
                        medico = (Nombre) data.get(0);
                    }
                }
            } else {
                Map<String, Object> wSQL = new HashMap<>();
                List oSQL = new ArrayList();
                wSQL.put("id ?=", 0);
                List data = admNegocio.getData(new Nombre(), wSQL, null, oSQL);
                if (data.size() > 0) {
                    medico = (Nombre) data.get(0);
                }
            }
            for (Object object : listaper) {
                switch (objsessiActica.getTipo()) {
                    case 0: {
                        CsPerxgru obj = (CsPerxgru) object;
                        alta = Short.hashCode(obj.getAlta());
                        baja = Short.hashCode(obj.getBaja());
                        modi = Short.hashCode(obj.getModif());
                    }
                    break;
                    case 1: {
                        STrausu obj = (STrausu) object;
                        alta = obj.getAlta().compareTo('S');
                        baja = obj.getBaja().compareTo('S');
                        modi = obj.getModif().compareTo('S');
                    }
                    break;
                }
            }
            enableButtons();
            vPaciente.setDisabled(true);
            vPaciente.addEventListener(Events.ON_CLICK, new EventListener() {
                @Override
                public void onEvent(Event e) throws Exception {
                    //confirmo paciente nextlab (OK CON HISTOR)
                    cargarWinHistoria(2);
                }
            });
            vOrden.addEventListener(Events.ON_CLICK, new EventListener() {
                @Override
                public void onEvent(Event e) throws Exception {
                    //confirmo paciente nextlab (OK CON HISTOR)
                    abrirOrden(1, getObjOrdenP().getId(), 0, 0);

                    //cerrarConeccionSQL();
                }
            });
            modOrd.addEventListener(Events.ON_CLICK, new EventListener() {
                @Override
                public void onEvent(Event e) throws Exception {
                    //confirmo paciente nextlab (OK CON HISTOR)
                    if (objOrdenP != null) {
                        objOrdenP.setLastUser(usuario);
                        objOrdenP.setmSolicitante(idMedP.getValue());
                        if (empresa != null) {
                            objOrdenP.setOrganizacion(empresa);
                        }
                        AdmiNegocio obj = new AdmiNegocio();
                        if (objOrdenP.getFecIni() == null) {
                            objOrdenP.setFecIni(new Date());
                        }
                        if (obj.actualizar(objOrdenP)) {
                            Messagebox.show("Actualizado", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                        } else {
                            Messagebox.show("No se puede actuzalizar Orden Avasus", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);

                        }
                    }
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//

    public void enableButtons() {
        try {
            btnBuscar = (Button) barraBotones.getFellow("btnBuscar", false);
            btnReset = (Button) barraBotones.getFellow("btnReset", false);
            btnCancel = (Button) barraBotones.getFellow("btnCancel", false);
            btnGuardar = (Button) barraBotones.getFellow("btnGuardar", false);
            btnUpdate = (Button) barraBotones.getFellow("btnUpdate", false);
            btnGuardar.setDisabled(false);
            btnGuardar.addEventListener(Events.ON_CLICK, new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    //  guardar();
                }
            });
            btnNew = (Button) barraBotones.getFellow("btnNew", false);
            btnBuscar.setDisabled(false);
            btnBuscar.addEventListener(Events.ON_CLICK, new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    if ((txtOrden.getValue().isEmpty())) {
                        Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.INCOMPLETO), "Información", Messagebox.OK, Messagebox.INFORMATION);
                    } else {
                        find();
                    }
                }
            });
            txtOrden.addEventListener(Events.ON_OK, new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    if ((txtOrden.getValue().isEmpty()) || (!(txtOrden.getValue().matches("[0-9]*")))) {
                        Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.ERROR_BUSCAR), "Verifique los valores por favor, Información", Messagebox.OK, Messagebox.INFORMATION);
                    } else {
                        try {
                            find();
                        } catch (Exception e) {
                            Messagebox.show("Verifique los valores", "Información", Messagebox.OK, Messagebox.INFORMATION);

                        }
                    }
                }
            });
            btnBuscar.setVisible(true);
            btnReset.setDisabled(false);
            btnReset.setVisible(true);
            btnReset.addEventListener(Events.ON_CLICK, new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    reset();
                }
            });

            btnUpdate.addEventListener(Events.ON_CLICK, new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    //  if ((usuario.equalsIgnoreCase("ADMIN")) || (usuario.equalsIgnoreCase("PRUEBAS_S"))) {
                    //        alert(usuario);
                    actualizarOrden();
                    alert("Confirmar Pedido");
                    //   } else {
                    //       alert("No dispone pormisos");
                    //   }
                }
            });
            btnUpdate.setDisabled(true);
            btnUpdate.setVisible(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onClick$btnCancelar() {
        reset();
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Datos Paciente">
    private void actualizarOrden() {

//            AdmiNegocioSql negoSQL = new AdmiNegocioSql();
//            Map<String, Object> wSQL = new HashMap<>();
//            wSQL = new HashMap<>();
//            wSQL.put("lispetPK.nroOrd ?=", objOrdenN.getNroOrd());
//            List objPed = negoSQL.getData(new Lispet(), wSQL, null, null);
        construirDetalle(objOrdenP, false, true);

    }

    private void datosPaciente() {
        Window winMensaje = new Window();
        String windowMessage;
        windowMessage = "msg_paciente.zul";
        Executions.createComponents(windowMessage, winMensaje, null);
        final Label msg = new Label();
        msg.setParent(winMensaje);
        msg.setVisible(false);
        final Label datos = (Label) winMensaje.getFellow("datos", false);
        // datos.setValue(objHistoria.getPaciente() + "  /  " + objHistoria.getNumId() + " / " + getAnos());
        Button MsgConfirmar = (Button) winMensaje.getFellow("MsgConfirmar", false);
        MsgConfirmar.addEventListener(Events.ON_CLICK, new EventListener() {
            @Override
            public void onEvent(Event e) throws Exception {
                //hay historia Actualizar
                //confirmo paciente nextlab (OK CON HISTOR)
//                cargarWinHistoria(2);
                cargarWinHistoria(1);
                blockBusque(true);
                Window aux;
                aux = (Window) msg.getParent();
                aux.onClose();
            }
        });
        Button MsgNuevo = (Button) winMensaje.getFellow("MsgNuevo", false);
        MsgNuevo.addEventListener(Events.ON_CLICK, new EventListener() {
            @Override
            public void onEvent(Event e) throws Exception {
                //No es elcrar como nuevo verifico primero si no cumple idcedula con id next
                cargarWinHistoria(0);
                blockBusque(true);
                Window aux;
                aux = (Window) msg.getParent();
                aux.onClose();
            }
        });
        Button MsgCancel = (Button) winMensaje.getFellow("MsgCancel", false);
        MsgCancel.addEventListener(Events.ON_CLICK, new EventListener() {
            @Override
            public void onEvent(Event e) throws Exception {
                //Cierro la ventana sin actualizar historia Continuar ok historia
                if (existeOrdenAvasus()) {
                    cargarDatosHistoria();
                    crearDetalle(objOrdenP, 0);
                } else {
                    crearUpdOrden(0);
                    cargarDatosHistoria();
                }
                Window aux;
                aux = (Window) msg.getParent();
                aux.onClose();
            }// hasta aui
        });
        Button MsgSalir = (Button) winMensaje.getFellow("MsgSalir", false);
        MsgSalir.addEventListener(Events.ON_CLICK, new EventListener() {
            @Override
            public void onEvent(Event e) throws Exception {
                //Cierro la ventana auxiliar verificacion
                reset();
                Window aux;
                aux = (Window) msg.getParent();
                aux.onClose();
            }// hasta aui
        });
        winMensaje.setId("winMsgCompleto");
        winMensaje.setParent(WinIngreso);
        winMensaje.doModal();
    }

    private void find() {
        try {
            
            btnUpdate.setDisabled(false);
            objHistoria = null;
            objOrdenN = null;
            empresa = null;
            AdmiNegocioSql negoSQL = new AdmiNegocioSql();
            Map<String, Object> wSQL = new HashMap<>();
            wSQL.put("codPac ?=", txtHistoria.getValue());
            wSQL.put("nroOrd ?=", txtOrden.getValue());
            //System.out.println("2" + new Date() + " / " + txtOrden.getValue() + " / ");
            ListapedidosN = negoSQL.getData(new LispetAvanex(), wSQL, null, null);
            //System.out.println("3" + new Date() + " / " + txtOrden.getValue() + " / ");
            if (ListapedidosN.size() > 0) {
                setListapedidosN(ListapedidosN);
                btnBuscar.setDisabled(false);
                setObjOrdenN((LispetAvanex) ListapedidosN.get(0));
                // System.out.println("3.1" + new Date() + " / " + txtOrden.getValue() + " / ");
                if ((existeOrdenAvasus()) && (getObjOrdenN() != null)) {
                    //si hay orden avasus
                    txtOrden.setDisabled(true);
                    setObjHistoria(getObjOrdenP().getHistoria());
                    crearUpdOrden(1);//actualiza orden
                    cargarDatosHistoria();
                    vPaciente.setDisabled(false);
                    vOrden.setDisabled(false);
                    blockBusque(true);
                    btnUpdate.setDisabled(false);
                } else {
                    //hay orden nextlab
                    if (getObjOrdenN() != null) {
    
                        objOrdenN.setDocPac(validarCedula(objOrdenN.getDocPac()));
                        crearAvasus();
                    } else {
                        reset();
                        Messagebox.show("No existe datos para la consulta", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                    }
                }
               
            } else {
                reset();
                Messagebox.show("No existe la orden en Nextlab", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
            }
        } catch (NamingException ex) {
            Logger.getLogger(ControladoraIngreso.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    private void crearAvasus() {

        try {
            Map<String, Object> wSQL = new HashMap<>();
            txtOrden.setDisabled(true);
            wSQL = new HashMap<>();
            wSQL.put("idNextlab ?=", objOrdenN.getCodPac());
            wSQL.put("lockReg ?=", 0);
            AdmiNegocio negoPos = new AdmiNegocio();
            List histos = negoPos.getData(new Historia(), wSQL, null, null);
            if (!histos.isEmpty()) {
                //si hay  historia en avasus
                //  if (histos.size() > 1) {
                if (histos.size() > 0) {
                    try {
                        visualizarConfirmacion(histos);
                    } catch (Exception e) {
                        Messagebox.show("Error en carga de datos Historia", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                    }
                } else {
                    if (histos.size() == 1) {
                        try {
                            vPaciente.setDisabled(false);
                            vOrden.setDisabled(false);
                            setObjHistoria((Historia) histos.get(0));
                            datosPaciente();
                            blockBusque(true);
                        } catch (Exception e) {
                            Messagebox.show("Error en carga de datos Paciente", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                        }

                    } else {
                        Messagebox.show("Verifique #<1 de historias", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                    }
                }
            } else {
                //buscar con num CI en el avasus no hay en avasus
                String nombre = objOrdenN.getApePac() + " " + objOrdenN.getApmPac() + " " + objOrdenN.getNomPac() + " " + objOrdenN.getNomPac2();
                String Cd = objOrdenN.getDocPac();
                Messagebox.show("Actualizar datos paciente para crear orden!! (Nuevo en avasus)", nombre + " / " + Cd, Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION, new org.zkoss.zk.ui.event.EventListener() {
                    public void onEvent(Event evt) throws InterruptedException {
                        if (evt.getName().equals("onOK")) {
                            cargarWinHistoria(0);
                            blockBusque(true);
                        } else if (evt.getName().equals("onCancel")) {
                            reset();
                            blockBusque(false);
                        }
                    }
                });
            }
        } catch (NamingException ex) {
            Logger.getLogger(ControladoraIngreso.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //public boolean validarCedula(String cedula, String tipoCI) {
    public String validarCedula(String cedula) {
        boolean ban = false;
        cedula = cedula.trim();
        //int length = cedula.length();
        if (!(cedula.matches("[+-]?[\\d]*[.]?[\\d]+"))) {            
            ban = false;            
        } else {            
            ban = true;
        }
        return cedula;
    }

    private boolean existeOrdenAvasus() {
        AdmiNegocio nego = new AdmiNegocio();
        try {
            Map<String, Object> wSQL = new HashMap<>();
            wSQL.put("codOrd ?=", txtOrden.getValue());
            wSQL.put("lockReg ?=", 0);
            List objOrd = nego.getData(new Orden(), wSQL, null, null);
            if (objOrd.size() > 0) {
                setObjOrdenP((Orden) objOrd.get(0));
                return true;
            } else {
                if (objOrd.isEmpty()) {
                    return false;
                } else {
                    return false;
                }
            }
        } catch (NamingException ex) {
            Logger.getLogger(ControladoraIngreso.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void crearDetalle(Orden ord, Integer tipo) {
        //postrgres
        AdmiNegocio nego = new AdmiNegocio();
        boolean labFila = false;
        boolean crearOrden = true;
        boolean labCrear = true;
        boolean isLAb = false;
        areas = new ArrayList();
        PracticaXOrden praclba = null;

        cleanGrid();
        List practi = null;
        try {
       
            Map<String, Object> wSQL = new HashMap<>();
            wSQL.put("orden.id ?=", ord.getId());
            wSQL.put("lockReg ?=", 0);
            practi = nego.getData(new PracticaXOrden(), wSQL, null, null);
            if (practi.size() > 0) {
       
                pos = 0;
                crearOrden = false;
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraIngreso.crearDetalle()" + new Date());
                for (Object objpra : practi) {
                    PracticaXOrden det = (PracticaXOrden) objpra;
       
                    if (det.getPractica().getId() != 415) {
                        praclba = det;
                        pos = pos + 1;
                        final Listitem uno = new Listitem();
                        final Listcell num = new Listcell(Integer.toString(pos));
                        String servi;
                        if (praclba.getIdNombrep() != null) {
                            servi = praclba.getIdNombrep().toString();
                        } else {
                            servi = "S/Análisis";
                        }
                        final Listcell ser = new Listcell(servi);
                        final Listcell ref = new Listcell(praclba.getPractica().getDescripcion());
                        final Listcell orden = new Listcell();
                        final Button impOden = new Button("Orden");
       
                        impOden.setStyle("border: 1px solid #391a3a;background-color:#8e1792; color: white;font-size: 20px;font-weight: bold;width: 100px;");
                        impOden.setParent(orden);
                        try {
                            impOden.setId(praclba.getIdArea().toString() + "p" + praclba.getPractica().getId());
                        } catch (Exception e) {
                            if (praclba.getPractica().getArea() != null) {
                                impOden.setId(praclba.getPractica().getArea().getId().toString() + "p" + praclba.getPractica().getId());
                            }
                        }
                        impOden.addEventListener(Events.ON_CLICK, new EventListener() {
                            @Override
                            public void onEvent(Event event) throws Exception {
                                if (!impOden.getId().isEmpty()) {
                                    String idAre = impOden.getId().substring(0, impOden.getId().lastIndexOf("p"));
                                    String pa = impOden.getId().substring(impOden.getId().lastIndexOf("p") + 1);
                                    abrirOrden(2, objOrdenP.getId(), null, Integer.decode(idAre));
                                }
                            }
                        }
                        );
                        Listcell fecA;
                        if (praclba.getFecIni() != null) {
                            fecA = new Listcell(formato.format(praclba.getFecIni()));
                        } else {
                            fecA = new Listcell("");
                        }
                        Listcell fecUpdate;
                        if (praclba.getFecUpd() != null) {
                            fecUpdate = new Listcell(formato.format(praclba.getFecUpd()));
                        } else {
                            fecUpdate = new Listcell("");
                        }
                        int aux = 0;
                        Listcell estado = new Listcell(praclba.getStsTecnico());
                        agregarArea(praclba.getIdArea());
                        num.setParent(uno);
                        ser.setParent(uno);
                        ref.setParent(uno);
                        orden.setParent(uno);
                        fecA.setParent(uno);
                        fecUpdate.setParent(uno);
                        estado.setParent(uno);

                        final Listcell anula = new Listcell();
                        final Button anlar = new Button("ANULAR");
                        //anular existente nueva carga
                        anlar.setId(praclba.getId().toString());
                        anlar.addEventListener(Events.ON_CLICK, new EventListener() {
                            @Override
                            public void onEvent(Event event) throws Exception {
                                AdmiNegocio objN = new AdmiNegocio();
                                Integer idPrac = Integer.parseInt(anlar.getId());
                                Map<String, Object> wSQL2 = new HashMap<>();
                                wSQL2 = new HashMap<>();
                                wSQL2.put("orden.id ?=", objOrdenP.getId());
                                wSQL2.put("id ?=", idPrac);
                                wSQL2.put("stsTecnico ?=", "PE");
                                wSQL2.put("lockReg ?=", 0);
                                PracticaXOrden pra = (PracticaXOrden) objN.getDataObj(new PracticaXOrden(), wSQL2, null, null);
                                if (pra != null) {
                                    pra.setLockReg(new Short("1"));
                                    pra.setLastUser(usuario);
                                    try {
                                        if (objN.actualizar(pra)) {
                                            anlar.setDisabled(true);
                                            alert("Registro Anulado");
                                        } else {
                                            alert("No se puede Anular");
                                        }
                                    } catch (Exception e) {
                                        alert("No se puede Anular");
                                    }
                                } else {
                                    alert("No se puede Anular");
                                }
                            }
                        }
                        );
                        anlar.setParent(anula);
                        anlar.setDisabled(!anular);
                        anula.setParent(uno);
                        uno.setParent(pedidos);
      
                    } else {
                        praclba = det;
                        labFila = true;
                        pos = pos + 1;
                        final Listitem uno = new Listitem();
                        final Listcell num = new Listcell(Integer.toString(pos));
                        final Listcell ser = new Listcell("Laboratorio");
                        final Listcell ref = new Listcell("Laboratorio");
                        final Listcell orden = new Listcell();
                        final Button impOden = new Button("Orden");
                        // impOden.setDisabled(true);
                        impOden.setStyle("border: 1px solid #391a3a;background-color:#8e1792; color: white;font-size: 20px;font-weight: bold;width: 100px;");
                        impOden.setParent(orden);
                        impOden.setId(praclba.getPractica().getArea().getId().toString());
                        impOden.addEventListener(Events.ON_CLICK, new EventListener() {
                            @Override
                            public void onEvent(Event event) throws Exception {
                                String idAre = impOden.getId();
                                abrirOrden(2, objOrdenP.getId(), null, Integer.decode(idAre));
                            }
                        });
                        Listcell fecA;
                        if (praclba.getFecIni() != null) {
                            fecA = new Listcell(formato.format(praclba.getFecIni()));
                        } else {
                            fecA = new Listcell("");
                        }
                        Listcell fecUpdate;
                        if (praclba.getFecUpd() != null) {
                            fecUpdate = new Listcell(formato.format(praclba.getFecUpd()));
                        } else {
                            fecUpdate = new Listcell("");
                        }
                        Listcell estado = new Listcell(praclba.getStsTecnico());
                
                        num.setParent(uno);
                        ser.setParent(uno);
                        ref.setParent(uno);
                        orden.setParent(uno);
                        fecA.setParent(uno);
                        fecUpdate.setParent(uno);
                        estado.setParent(uno);
                        final Listcell anula = new Listcell();
                        final Button anlar = new Button("ANULAR");
                        anlar.setParent(anula);
                        anlar.setDisabled(anular);
                        anula.setParent(uno);
                        uno.setParent(pedidos);
                    }
                }
            } else {
                pos = 0;
                construirDetalle(ord, crearOrden, false);
            }
        } catch (NumberFormatException | NamingException e) {
            Messagebox.show("Error S/U  (PXO)", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
        }
      //  System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraIngreso.crearDetalle() " + txtOrden.getValue() + " / " + new Date());
        if (!areas.isEmpty()) {
            vOrden.setDisabled(false);
        }
        Messagebox.show("CONFIRME PEDIDO CON EL SISTEMA NEXTLAB!!", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
    }

    private void agregarPedido(Orden ord, Boolean crearOrden, boolean actuali) {
        AdmiNegocio nego = new AdmiNegocio();
        nego = new AdmiNegocio();
        boolean labCrear = true;
        boolean existe = false;
        for (Object object : ListapedidosN) {
            try {
                existe = false;
                boolean ExisteLab = false;
                final LispetAvanex pedido = (LispetAvanex) object;
                AdmiNegocioSql negoSQL = new AdmiNegocioSql();
                Map<String, Object> wSQL = new HashMap<>();
                wSQL.put("codAna ?=", pedido.getCodAna());
                List oSQL = new ArrayList();
                // oSQL.add("desPrac");
                List objCatalogo = negoSQL.getData(new Listcata(), wSQL, null, null);
                Listcata objRef = null;
                if (objCatalogo != null) {
                    if (objCatalogo.size() > 0) {
                        if (objCatalogo.size() == 1) {
                            objRef = (Listcata) objCatalogo.get(0);
                            Long val = Long.parseLong("4");
                            Long pract = objRef.getIdPra();
                            int j = 0;
                            if (((pract < val) && ((objRef.getTipoS().compareTo('L') == 0)))) {
                                if (labCrear) {
                                    ExisteLab = true;
                                } else {
                                    ExisteLab = true;
                                }
                            } else {
                               
                            }
                        } else {
                            ExisteLab = false;
                        }
                        if (!ExisteLab) {
                            pos = pos + 1;
                            NombreP prac = null;
                            boolean Numprac = false;
                            if (objCatalogo.size() > 2) {
                                Numprac = true;
                            } else {
                                objRef = (Listcata) objCatalogo.get(0);
                                wSQL = new HashMap<>();
                                wSQL.put("id ?=", objRef.getIdPra());
                                wSQL.put("lockReg ?=", 0);
                                prac = (NombreP) nego.getDataObj(new NombreP(), wSQL, null, null);
                                if (!existePracticaOrden(1, pedido, objOrdenP, objRef.getIdPra(), crearOrden)) {
                                    PracticaXOrden pediOre = new PracticaXOrden();
                                    pediOre.setPractica(prac);
                                    pediOre.setOrden(ord);
                                    pediOre.setIdPlan(0);
                                    pediOre.setStsTecnico(pedido.getStsPet());
                                    //aqui creo
                                    pediOre.setFirstUser(usuario);
                                    String cd;
                                    if (objRef.getCodAna().substring(0, 1).equalsIgnoreCase("0")) {
                                        cd = objRef.getCodAna().substring(1);
                                        //System.out.println("cd " + cd);
                                    } else {
                                        cd = objRef.getCodAna();
                                        //System.out.println("cd " + cd);
                                    }
                                    pediOre.setIdNombrep(Long.decode(cd));
                                    if (prac.getArea() != null) {
                                        pediOre.setIdArea(prac.getArea().getId());
                                    }
                                    try {
                                        pediOre = (PracticaXOrden) nego.guardar(pediOre);
                                    } catch (Exception e) {
                                        alert("Creado :" + pediOre.getOrden().getId() + " CodAna: " + pediOre.getIdNombrep() + " Práctica: " + pediOre.getPractica().getDescripcion());
                                    }
                                    if (pediOre.getId() != null) {
                                        try {
                                            agregarArea(prac.getArea().getId());
                                        } catch (Exception e) {
//                                            System.out.println("pedi" + pediOre.getId());
//                                            System.out.println("pedido" + e.toString());
                                            //alert(pediOre"");
                                        }

                                    } else { //no creado
                                    }
                                } else {
                                    existe = true;
                                }
                            }
                            String servi = null;
                            Label ref = new Label();
                            final Listitem uno = new Listitem();
                            final Listcell num = new Listcell(Integer.toString(pos));
                            final Listcell forden = new Listcell();
                            final Combobox SelecPrac = new Combobox();
                            final Button anlar = new Button("ANULAR");
                            if (Numprac) {
                                objRef = (Listcata) objCatalogo.get(0);
                                if (objRef.getTipoS().compareTo('S') == 0) {
//                                    if (objRef == null) {
//                                        servi = "SELECCIONE";
//                                    }
                                    servi = objRef.getDesAna();

                                }
                                if (!existePracticaOrden(0, pedido, objOrdenP, objRef.getIdPra(), crearOrden)) {
                                    ref.setValue("S/D");
                                    SelecPrac.setWidth("90%");
                                    SelecPrac.setStyle("border: 1px solid #391a3a;background-color:#faaef0; color: white;font-weight: bold;width: 100px;");
                                    SelecPrac.setParent(forden);
                                    final Button impOden = new Button("Orden");
                                    for (Object object1 : objCatalogo) {
                                        Listcata obj1 = (Listcata) object1;
                                        if (obj1.getTipoS().compareTo('N') == 0) {
                                            SelecPrac.setAutocomplete(true);
                                            wSQL = new HashMap<>();
                                            wSQL.put("id ?=", obj1.getIdPra());
                                            wSQL.put("lockReg ?=", 0);
                                            oSQL = new ArrayList();
                                            oSQL.add("descripcion");
                                            prac = (NombreP) nego.getDataObj(new NombreP(), wSQL, null, oSQL);
                                            if (prac != null) {
                                                Comboitem obj = new Comboitem(prac.getDescripcion());
                                                obj.setValue(prac);
                                                obj.setParent(SelecPrac);
                                            }
                                        }
                                    }
                                    SelecPrac.addEventListener(Events.ON_OK, new EventListener() {
                                        @Override
                                        public void onEvent(Event event) throws Exception {
                                            SelecPrac.setDisabled(false);
                                            NombreP gjh = (NombreP) SelecPrac.getSelectedItem().getValue();
                                            if (gjh != null) {
                                                SelecPrac.setValue(gjh.getDescripcion());
                                            } else {
                                                alert("No se encontro practica seleccionada ");
                                            }
                                            Messagebox.show("Confirmación", gjh.getDescripcion(), Messagebox.OK | Messagebox.CANCEL, Messagebox.INFORMATION, new org.zkoss.zk.ui.event.EventListener() {
                                                public void onEvent(Event evt) throws InterruptedException {
                                                    if (evt.getName().equals("onOK")) {
                                                        SelecPrac.setStyle("border: 1px solid #391a3a; font-weight: bold;width: 100px;");
                                                        if (!existePracticaOrden(1, pedido, ord, Long.parseLong(gjh.getId().toString()), crearOrden)) {
                                                            AdmiNegocio neg = new AdmiNegocio();
                                                            neg = new AdmiNegocio();
                                                            PracticaXOrden pediOre = new PracticaXOrden();
                                                            pediOre.setPractica(gjh);
                                                            pediOre.setOrden(ord);
                                                            pediOre.setIdPlan(0);
                                                            pediOre.setStsTecnico(pedido.getStsPet());
                                                            pediOre.setFirstUser(usuario);
                                                            pediOre.setIdNombrep(Long.decode(pedido.getCodAna()));
                                                            SelecPrac.setDisabled(false);
                                                            if (gjh.getArea() != null) {
                                                                pediOre.setIdArea(gjh.getArea().getId());
                                                            }
                                                            try {
                                                                pediOre = (PracticaXOrden) neg.guardar(pediOre);
                                                            } catch (Exception e) {
                                                                alert("Creado :" + pediOre.getOrden().getId() + " CodAna: " + pediOre.getIdNombrep() + " Práctica: " + pediOre.getPractica().getDescripcion());
                                                            }
                                                            if (pediOre != null) {
                                                                if (pediOre.getId() != null) {
                                                                    try {
                                                                        agregarArea(gjh.getArea().getId());
                                                                    } catch (Exception e) {
                                                                    }

                                                                    SelecPrac.setDisabled(true);
                                                                    if (gjh.getArea() != null) {
                                                                        try {
                                                                            impOden.setId(gjh.getArea().getId().toString() + "p" + gjh.getId());
                                                                            anlar.setId(pediOre.getPractica().getId().toString());
                                                                            impOden.setDisabled(false);
                                                                        } catch (Exception e) {
                                                                        }
                                                                    } else {
                                                                        impOden.setId("0" + "p" + gjh.getId());
                                                                    }
                                                                } else { //no creado
                                                                    System.out.println(".onEvent()");
                                                                }
                                                            } else {
                                                                alert("No se puede registrar ");
                                                            }
                                                        } else {
                                                            alert("Existe practica");
                                                        }
                                                    } else if (evt.getName().equals("onCancel")) {
                                                    }
                                                }

                                            });
                                        }
                                    });

                                  
                                    impOden.setStyle("border: 1px solid #391a3a;background-color:#8e1792; color: #391a3a;font-weight: bold;width: 100px;");
                                    impOden.setParent(forden);
                                    impOden.setDisabled(true);
                                    impOden.addEventListener(Events.ON_CLICK, new EventListener() {
                                        @Override
                                        public void onEvent(Event event) throws Exception {
                                            String idAre = impOden.getId().substring(0, impOden.getId().lastIndexOf("p"));
                                            String pa = impOden.getId().substring(impOden.getId().lastIndexOf("p") + 1);
                                            abrirOrden(2, objOrdenP.getId(), null, Integer.decode(idAre));
                                        }
                                    });
                                } else {
                                    if (!actuali) {
                                        wSQL = new HashMap<>();
                                        wSQL.put("orden.id ?=", objOrdenP.getId());
                                        wSQL.put("idNombrep ?=", pedido.getCodAna());
                                        wSQL.put("lockReg ?=", 0);
                                        List practi = null;
                                        try {
                                            practi = nego.getData(new PracticaXOrden(), wSQL, null, null);
                                        } catch (Exception e) {
                                        }
                                        if ((!practi.isEmpty())) {
                                            PracticaXOrden objP = (PracticaXOrden) practi.get(0);
                                            prac = objP.getPractica();
                                            servi = objRef.getDesAna();
                                            ref.setValue(prac.getDescripcion());
                                            final Button impOden = new Button("Orden");
                                            impOden.setStyle("border: 1px solid #391a3a;background-color:#8e1792; color: #391a3a;font-weight: bold;width: 100px;");
                                            impOden.setParent(forden);
                                            impOden.setId(prac.getArea().getId().toString() + "p" + prac.getId());

                                            anlar.setId(prac.getId().toString());
                                            impOden.addEventListener(Events.ON_CLICK, new EventListener() {
                                                @Override
                                                public void onEvent(Event event) throws Exception {
                                                    String idAre = impOden.getId().substring(0, impOden.getId().lastIndexOf("p"));
                                                    String pa = impOden.getId().substring(impOden.getId().lastIndexOf("p") + 1);
                                                    abrirOrden(2, objOrdenP.getId(), null, Integer.decode(idAre));
                                                }
                                            }
                                            );
                                        }
                                    } else {
                                        existe = true;
                                    }
                                }
         
                            } else {
                                servi = objRef.getDesAna();
                                ref.setValue(prac.getDescripcion());
                                final Button impOden = new Button("Orden");
                                impOden.setStyle("border: 1px solid #391a3a;background-color:#8e1792; color: #391a3a;font-weight: bold;width: 100px;");
                                impOden.setParent(forden);
                                if (prac.getArea() != null) {
                                    try {
                                        if (!existe) {
                                            impOden.setId(prac.getArea().getId().toString() + "p" + prac.getId());
                                        }
                                    } catch (Exception e) {
                                        alert("No unico ID");
                                    }
                                } else {
                                    impOden.setId("0" + "p" + prac.getId());
                                }
                                impOden.addEventListener(Events.ON_CLICK, new EventListener() {
                                    @Override
                                    public void onEvent(Event event) throws Exception {
                                        String idAre = impOden.getId().substring(0, impOden.getId().lastIndexOf("p"));
                                        String pa = impOden.getId().substring(impOden.getId().lastIndexOf("p") + 1);
                                        abrirOrden(2, objOrdenP.getId(), null, Integer.decode(idAre));
                                    }
                                });
                            }
                            Listcell fecA;
                            if (objOrdenN.getFecOrd() != null) {
                                fecA = new Listcell(formato.format(objOrdenN.getFecOrd()));
                            } else {
                                fecA = new Listcell("");
                            }
                            Listcell fecUpdate;
                            if (objOrdenN.getFechaModif() != null) {
                                fecUpdate = new Listcell(formato.format(objOrdenN.getFechaModif()));
                            } else {
                                fecUpdate = new Listcell("");
                            }
                            final Listcell ser = new Listcell(servi);
                            final Listcell refe = new Listcell();
                            if (!ref.getValue().equalsIgnoreCase("S/D")) {
                                ref.setParent(refe);
                            } else {
                                SelecPrac.setParent(refe);
                            }
                            Listcell estado = new Listcell(pedido.getStsPet());
                            num.setParent(uno);
                            ser.setParent(uno);
                            refe.setParent(uno);
                            forden.setParent(uno);
                            fecA.setParent(uno);
                            fecUpdate.setParent(uno);
                            estado.setParent(uno);
                            final Listcell anula = new Listcell();
                            anlar.setParent(anula);
                            if (prac != null) {
                                if (!existe) {
                                    anlar.setId(prac.getId().toString());
                                }

                            }
                            anlar.addEventListener(Events.ON_CLICK, new EventListener() {
                                @Override
                                public void onEvent(Event event) throws Exception {
                                    AdmiNegocio objN = new AdmiNegocio();
                                    if (!anlar.getId().isEmpty()) {
                                        Integer idPrac = Integer.parseInt(anlar.getId());

                                        Map<String, Object> wSQL2 = new HashMap<>();
                                        wSQL2 = new HashMap<>();
                                        wSQL2.put("orden.id ?=", objOrdenP.getId());
                                        wSQL2.put("practica.id ?=", idPrac);
                                        wSQL2.put("stsTecnico ?=", "PE");
                                        wSQL2.put("lockReg ?=", 0);
                                        PracticaXOrden pra = (PracticaXOrden) objN.getDataObj(new PracticaXOrden(), wSQL2, null, null);
                                        if (pra != null) {
                                            pra.setLastUser(usuario);
                                            pra.setLockReg(new Short("1"));
                                            if (objN.actualizar(pra)) {
                                                anlar.setDisabled(true);
                                                alert("Registro Anulado / no podra seleccionar otra práctica");
                                            } else {
                                                alert("No se puede Anular");
                                            }
                                        } else {
                                            alert("No se puede Anular");
                                        }
                                    }
                                }
                            }
                            );
                            anlar.setDisabled(!anular);
                            anula.setParent(uno);
                            if ((!existe)) {
                                try {
                                    uno.setParent(pedidos);
                                } catch (Exception e) {
                                    String g;
                                    if (!ref.getValue().equalsIgnoreCase("S/D")) {
                                        g = "S/D";
                                        ref.setParent(refe);
                                    } else {
                                        g = "D";
                                        SelecPrac.setParent(refe);
                                    }
                                   
                                }

                            }
                        } else {
                            PracticaXOrden pediOr;
                            if ((labCrear) && (ExisteLab)) {
                                pediOr = crearDetalleLaboratorio(ord, objRef, pedido);
                                if (pediOr != null) {
                                    labCrear = false;
                                    final Listitem unPracticaOrden = new Listitem();
                                    final Listcell num = new Listcell(Integer.toString(pos));
                                    final Listcell ser = new Listcell("Laboratorio");
                                    final Listcell ref = new Listcell("Laboratorio");
                                    final Listcell orden = new Listcell();
                                    final Button impOden = new Button("Orden");
                                    impOden.setStyle("border: 1px solid #391a3a;background-color:#8e1792; color: white;font-size: 20px;font-weight: bold;width: 100px;");
                                    impOden.setParent(orden);
                                    impOden.setId(pediOr.getIdArea().toString());
                                    impOden.addEventListener(Events.ON_CLICK, new EventListener() {
                                        @Override
                                        public void onEvent(Event event) throws Exception {
                                            String idAre = impOden.getId();
                                            abrirOrden(2, objOrdenP.getId(), null, Integer.decode(idAre));
                                        }
                                    });
                                    Listcell fecA;
                                    if (pediOr.getFecIni() != null) {
                                        fecA = new Listcell(formato.format(pediOr.getFecIni()));
                                    } else {
                                        fecA = new Listcell("");
                                    }
                                    Listcell fecUpdate;
                                    if (pediOr.getFecUpd() != null) {
                                        fecUpdate = new Listcell(formato.format(pediOr.getFecUpd()));
                                    } else {
                                        fecUpdate = new Listcell("");
                                    }
                                    Listcell estado = new Listcell(pediOr.getStsTecnico());
                                    num.setParent(unPracticaOrden);
                                    ser.setParent(unPracticaOrden);
                                    ref.setParent(unPracticaOrden);
                                    orden.setParent(unPracticaOrden);
                                    fecA.setParent(unPracticaOrden);
                                    fecUpdate.setParent(unPracticaOrden);
                                    estado.setParent(unPracticaOrden);
                                    final Listcell anula = new Listcell();
                                    final Button anlar = new Button("ANULAR");
                                    anlar.setParent(anula);
                                    anlar.setDisabled(!anular);
                                    anlar.setId(pediOr.getPractica().getId().toString());
                                    anlar.addEventListener(Events.ON_CLICK, new EventListener() {
                                        @Override
                                        public void onEvent(Event event) throws Exception {
                                            AdmiNegocio objN = new AdmiNegocio();
                                            Integer idPrac = Integer.parseInt(anlar.getId());
                                            Map<String, Object> wSQL2 = new HashMap<>();
                                            wSQL2 = new HashMap<>();
                                            wSQL2.put("orden.id ?=", objOrdenP.getId());
                                            wSQL2.put("practica.id ?=", idPrac);
                                            wSQL2.put("stsTecnico ?=", "PE");
                                            wSQL2.put("lockReg ?=", 0);
                                            PracticaXOrden pra = (PracticaXOrden) objN.getDataObj(new PracticaXOrden(), wSQL2, null, null);
                                            if (pra != null) {
                                                pra.setLastUser(usuario);
                                                pra.setLockReg(new Short("1"));
                                                if (objN.actualizar(pra)) {
                                                    anlar.setDisabled(true);
                                                    alert("Registro Anulado / No se podra seleccionar la misma practica");
                                                } else {
                                                    alert("No se puede Anular");
                                                }
                                            } else {
                                                alert("No se puede Anular");
                                            }
                                        }
                                    }
                                    );
                                    anula.setParent(unPracticaOrden);
                                    if (!existe) {
                                        unPracticaOrden.setParent(pedidos);
                                    }
                                }
                            }
                        }
                    } else {
                        alert("Lista catalogo 0");
                    }
                } else {
                    alert("No existe analisis de referencia" + pedido.getCodAna());
                }
            } catch (NamingException ex) {
                Logger.getLogger(ControladoraIngreso.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    Integer pos;

    private void construirDetalle(Orden ord, Boolean crearOrden, Boolean actua) {
        if (crearOrden) {
            pos = 0;
            areas = new ArrayList();
            agregarPedido(ord, crearOrden, actua);
        } else {
            agregarPedido(ord, crearOrden, actua);
        }
    }

    private PracticaXOrden crearDetalleLaboratorio(Orden ord, Listcata objRef, LispetAvanex pedido) throws NamingException {
        AdmiNegocio nego = new AdmiNegocio();
        Map<String, Object> wSQL = new HashMap<>();
        wSQL.put("orden.id ?=", ord.getId());
        wSQL.put("practica.id ?=", 415);
        PracticaXOrden practi = null;
        try {
            practi = (PracticaXOrden) nego.getDataObj(new PracticaXOrden(), wSQL, null, null);
        } catch (NamingException ex) {
            Logger.getLogger(ControladoraIngreso.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (practi == null) {
            PracticaXOrden pediOr;
            pediOr = new PracticaXOrden();
            pediOr.setPractica(new NombreP(415));
            pediOr.setOrden(ord);
            pediOr.setIdPlan(0);
            pediOr.setIdArea(30);
            pediOr.setStsTecnico(pedido.getStsPet());
            pediOr.setFirstUser(usuario);
            try {
                if (objRef.getCodAna().substring(0, 1).equalsIgnoreCase("0")) {
                    pediOr.setIdNombrep(Long.decode(objRef.getCodAna().substring(1)));
                } else {
                    pediOr.setIdNombrep(Long.decode(objRef.getCodAna()));
                }
            } catch (Exception e) {
            }
            try {
                pediOr = (PracticaXOrden) nego.guardar(pediOr);
                //  agregarArea(30);
            } catch (Exception e) {
                alert("Creado :" + pediOr.getOrden().getId() + " CodAna: " + pediOr.getIdNombrep() + " Práctica: " + pediOr.getPractica().getDescripcion());
            }

            XmlResultado objprac = new XmlResultado();
            objprac.setHistoria(getObjHistoria());
            objprac.setOrden(getObjOrdenP());
            objprac.setCodPac(getObjOrdenN().getCodPac());
            objprac.setCodAna("");
            wSQL = new HashMap<>();
            List oSQL = new ArrayList();
            wSQL.put("id ?=", 415);
            AdmiNegocio admNegocio = new AdmiNegocio();
            NombreP prac = null;
            try {
                prac = (NombreP) admNegocio.getDataObj(new NombreP(), wSQL, null, oSQL);
            } catch (NamingException ex) {
                Logger.getLogger(ControladoraIngreso.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            objprac.setPractica(prac);
            objprac.setCodOri(getObjOrdenN().getCodOri());
            objprac.setFirstUser(usuario);
            objprac.setEstado("CO");
            objprac.setFecha(objOrdenN.getFechaModif());
            //objprac.setFecha(formato.format(getObjOrdenN().getFecOrd()));
            objprac.setResultado("WS");
            objprac.setMedicos(medico);
            objprac.setMedico(medico.getNombre() + " " + medico.getApellido());
            objprac.setNroOrd(getObjOrdenN().getNroOrd());
            objprac.setIdOrdenNextlab(getObjOrdenN().getNroOrd());
            try {
                objprac = (XmlResultado) nego.guardar(objprac);
            } catch (Exception e) {
                Messagebox.show("No se puede crear laboratorio WS" + objRef.getIdPra(), "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
            }

            if (objprac.getId() == null) {
                Messagebox.show("No se puede crear referencia informe de laboratorio" + objRef.getIdPra(), "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
            }
            return pediOr;
        } else {
            return null;
        }
    }

    private boolean existePracticaOrden(Integer tipo, LispetAvanex ped, Orden orden, Long pract, boolean crear) {
        AdmiNegocio nego = new AdmiNegocio();
        try {
            Map<String, Object> wSQL = new HashMap<>();
            wSQL.put("orden.id ?=", orden.getId());
            if (tipo == 1) {
                wSQL.put("practica.id ?=", pract);
            } else {
                wSQL.put("lockReg ?=", 0);
            }
            wSQL.put("idNombrep ?=", ped.getCodAna());
            List practi = nego.getData(new PracticaXOrden(), wSQL, null, null);
            if (practi.size() > 0) {
                PracticaXOrden prac = (PracticaXOrden) practi.get(0);
                NombreP pracOrd = prac.getPractica();
                //el uno esta deshabilitado y cero habilitado
                Short obhh = new Short("1");
                if (pracOrd.getPerAdd() == obhh) {
                    prac.setLastUser(usuario);
                    if (prac.getStsTecnico().equalsIgnoreCase("PE") || (prac.getStsTecnico().equalsIgnoreCase("PI")) || (prac.getStsTecnico().equalsIgnoreCase("SM"))) {
                        prac.setStsTecnico(ped.getStsPet());
                    }
                    try {
                        agregarArea(prac.getIdArea());
                    } catch (Exception e) {
                  
                        Messagebox.show("No se puede agregar Area" + prac.getIdArea(), "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                        return true;
                    }
                    try {
                        return nego.actualizar(prac);
                    } catch (Exception e) {
                        //System.out.println("No se puede actualizar Servicio" + prac.getPractica().getId());
                        Messagebox.show("No se puede actualizar Servicio" + prac.getPractica().getId(), "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                        return true;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (NamingException ex) {
            Logger.getLogger(ControladoraIngreso.class
                    .getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    private void crearUpdOrden(Integer tipo) {
        //0 nuevo // 1 actualizar
        try {
            //  System.out.println("3"+new Date());
            AdmiNegocioSql negoSQL = new AdmiNegocioSql();
            Map<String, Object> wSQL = new HashMap<>();
            wSQL.put("codoriEmpr ?=", objOrdenN.getCodOri());
            idOrigen.setValue(objOrdenN.getDesOri());
            LisoriEmpr objCatalogo = (LisoriEmpr) negoSQL.getDataObj(new LisoriEmpr(), wSQL, null, null);
            if (objCatalogo != null) {
                if (objCatalogo.getAux() > 0) {
                    empresa = new Organizacion(objCatalogo.getAux());
                } else {
                    empresa = new Organizacion(Long.parseLong("1"));
                }
            } else {
                empresa = new Organizacion(objCatalogo.getAux());
            }
            //   idEmpresa.setValue(emp.getAbreviatura());
            if (tipo == 0) {
                //    System.out.println("4"+new Date());
                objOrdenP = new Orden();
                if (objOrdenN.getNomMed() != null) {
                    objOrdenP.setmSolicitante(objOrdenN.getNomMed());
                    idMed.setValue(objOrdenN.getNomMed());
                    idMedP.setValue(objOrdenN.getNomMed());
                } else {
                    objOrdenP.setmSolicitante("");
                }
                objOrdenP.setHistoria(objHistoria);
                objOrdenP.setCodOri(objOrdenN.getCodOri());
                objOrdenP.setFecIngreso(objOrdenN.getFecOrd());
                objOrdenP.setFirstUser(usuario);
                objOrdenP.setIdOrigen(1);//mejorar
                objOrdenP.setStsAdmin(objOrdenN.getStsAdm());
             
                objOrdenP.setStsTecnico(objOrdenN.getStsOrd());
                objOrdenP.setCodOrd(objOrdenN.getNroOrd());
                objOrdenP.setOrganizacion(empresa);
                objOrdenP.setDesOri(objOrdenN.getDesOri());
                AdmiNegocio obj = new AdmiNegocio();
                try {
                    objOrdenP = (Orden) obj.guardar(objOrdenP);
                    if (objOrdenP.getId() != null) {
                        setObjOrdenP(objOrdenP);
                        crearDetalle(objOrdenP, 0);
                    } else {
                        Messagebox.show("No se puede crear Orden Avasus", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                    }
                } catch (Exception e) {
                }

            } else {
                //   System.out.println("5"+new Date());
                empresa = objOrdenP.getOrganizacion();
                objOrdenP.setCodOri(objOrdenN.getCodOri());
                objOrdenP.setDesOri(objOrdenN.getDesOri());
                objOrdenP.setLastUser(usuario);
                if (objOrdenN.getNomMed() != null) {

                    idMed.setValue(objOrdenN.getNomMed());
                } else {
                    objOrdenP.setmSolicitante("");

                }
                idMedP.setValue(objOrdenP.getmSolicitante());
                objOrdenP.setStsAdmin(objOrdenN.getStsAdm());
                // System.out.println("estN " + objOrdenN.getStsOrd().toString());
                // System.out.println("estP " + objOrdenP.getStsTecnico().toString());

                if (objOrdenN.getStsOrd().equalsIgnoreCase("VA")) {
                    objOrdenP.getStsTecnico().equalsIgnoreCase("CO");

                } else {
                    System.out.println("estado queda en CO");
                }

                objOrdenP.setStsTecnico(objOrdenN.getStsOrd());
                objOrdenP.setOrganizacion(empresa);
                AdmiNegocio obj = new AdmiNegocio();
                if (objOrdenP.getFecIni() == null) {
                    objOrdenP.setFecIni(new Date());
                }
                try {
                    if (obj.actualizar(objOrdenP)) {
                        //    System.out.println("6"+new Date());
                        crearDetalle(objOrdenP, 1);
                    } else {
                        System.out.println("No se puede Actualizar Orden Avasus" + objOrdenP.getId());
                        Messagebox.show("No se puede Actualizar Orden Avasus", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);

                    }
                } catch (Exception e) {
                    Messagebox.show("No se puede Actualizar", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                }

            }
            if (empresa != null) {
                cargarEmpresas();
            }
            modOrd.setDisabled(false);
            idEmpr.setDisabled(false);
            idMedP.setDisabled(false);
     
        } catch (NamingException ex) {
            Logger.getLogger(ControladoraIngreso.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void visualizarConfirmacion(List historias) {
        try {
            Window winSeleccion = new Window();
            Executions.createComponents("lista-historia.zul", winSeleccion, null);
            winSeleccion.setTitle("Seleccione registro AVASUS confirme datos");
            final Label msg = new Label();
            msg.setParent(winSeleccion);
            msg.setVisible(false);
            final Listbox listaHistorias = (Listbox) winSeleccion.getFellow("listaHistorias", false);
            for (Object objeto : historias) {
                Historia historia = (Historia) objeto;
                final Listitem filaHistorias = new Listitem();
                filaHistorias.setValue(historia);
                final Listcell idAvasus = new Listcell(historia.getId().toString());
                final Listcell idNextlab = new Listcell(historia.getIdNextlab().toString());
                final Listcell paciente = new Listcell(historia.getApellidos() + " " + historia.getNombres());
                final Listcell ci = new Listcell(historia.getNumId());
                //final Listcell fecNac = new Listcell(formato.format(historia.getFechaNace()));
                //final Listcell fecNac = new Listcell(formato.format(historia.getFechaNace()));
                final Listcell control = new Listcell();
                final Button cont = new Button("OK");
                cont.addEventListener(Events.ON_CLICK, new EventListener() {

                    @Override
                    public void onEvent(Event event) throws Exception {
                        Listitem filaHistori = (Listitem) cont.getParent().getParent();
                        cont.setLabel("Seleccion");
                        cont.setDisabled(true);
                        Window aux;
                        aux = (Window) msg.getParent();
                        aux.onClose();
                        vPaciente.setDisabled(false);
                        setObjHistoria((Historia) filaHistori.getValue());
                        datosPaciente();
                        blockBusque(true);
                    }
                });
                cont.setParent(control);
                filaHistorias.appendChild(idAvasus);
                filaHistorias.appendChild(idNextlab);
                filaHistorias.appendChild(paciente);
                filaHistorias.appendChild(ci);
                //filaHistorias.appendChild(fecNac);
                filaHistorias.appendChild(control);
                filaHistorias.setParent(listaHistorias);
            }
            winSeleccion.setWidth("500px");
            winSeleccion.setTitle("Historias");
            winSeleccion.setClosable(true);
            winSeleccion.setId("winMsgListaHistorias");
            winSeleccion.setParent(WinIngreso);
            winSeleccion.doModal();
        } catch (ComponentNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void cargarDatosHistoria() {
        System.out.println("Datos Historia" + new Date() + " / " + txtOrden.getValue() + " / ");
        idNombre.setValue(objHistoria.getApellidos() + "  " + objHistoria.getNombres());
        switch (objHistoria.getSexo()) {
            case "F": {
                idSexo.setValue("FEMENINO");
            }
            break;
            case "M": {
                idSexo.setValue("MASCULINO");
            }
            break;
            case "I": {
                idSexo.setValue("INDETERMINADO");
            }
            break;
            case "N": {
                idSexo.setValue("NO DECLARADO");
            }
            break;
        }
        try {
            edad(objHistoria.getFechaNace());
            idEdad.setValue(getAnos());
        } catch (Exception e) {
            idEdad.setValue("");
        }
    
        idCI.setValue(objHistoria.getNumId().trim());

        if (objOrdenP != null) {
            idOrden.setValue(objOrdenP.getCodOrd().toString().trim());
            idOrdenPo.setValue(objOrdenP.getId().toString().trim());
            idMedP.setValue(objOrdenP.getmSolicitante());
        } else {
            idOrden.setValue(objOrdenN.getNroOrd().toString().trim());
            idOrdenPo.setValue("S/O");
            idMedP.setValue(objOrdenN.getNomMed());
        }

    }
    Boolean clos;

    private void cargarWinHistoria(int tipo) {
        //0 no hay avasus  //1 hay avasus  //2 actualizar avasus
        Window winMensaje = new Window();
        String windowMessage;
        clos = false;
        windowMessage = "inputHistoria.zul";
        final Label msg = new Label();
        msg.setParent(winMensaje);
        msg.setVisible(false);
        try {
            Executions.createComponents(windowMessage, winMensaje, null);
        } catch (Exception e) {
        }
        final Button guardarBtn = (Button) winMensaje.getFellow("guardarBtn", false);
        final Button ban = (Button) winMensaje.getFellow("ban", false);
        if (tipo == 0) {//nextlab
            final Listitem datoN = (Listitem) winMensaje.getFellow("datoN", false);
            datoN.setValue(getObjOrdenN());
            guardarBtn.setLabel("GUARDAR");
            guardarBtn.setStyle(" border: 1px solid #FF0000;");
        } else {
            final Listitem datoP = (Listitem) winMensaje.getFellow("datoP", false);
            datoP.setValue(getObjHistoria());
            final Listitem datoN = (Listitem) winMensaje.getFellow("datoN", false);
            guardarBtn.setLabel("ACTUALIZAR HISTORIA");
            guardarBtn.setStyle(" border: 1px solid #FF0000;");
            datoN.setValue(getObjOrdenN());
        }
        ban.addEventListener(Events.ON_CLICK, new EventListener() {
            @Override
            public void onEvent(Event e) throws Exception {
                final Listitem dato = (Listitem) winMensaje.getFellow("datoP", false);
                if (dato.getValue() != null) {
                    setObjHistoria(dato.getValue());
                    vPaciente.setDisabled(false);
                    if (getObjHistoria() != null) {
                        if (tipo < 2) {
                            if (existeOrdenAvasus()) {
                                crearUpdOrden(1);//actualiza
                            } else {
                                crearUpdOrden(0);//crear
                            }
                        }
                        cargarDatosHistoria();
                    } else {
                        reset();
                        Messagebox.show("No se puede crear Historia Avasus", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                    }
                } else {
                    reset();
                    Messagebox.show("No se puede crear Historia Avasus", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                }
                clos = true;
                Window aux;
                aux = (Window) msg.getParent();
                aux.onClose();
            }// hasfta aui
        });
        winMensaje.addEventListener(Events.ON_CLOSE, new EventListener() {
            @Override
            public void onEvent(Event e) throws Exception {
                if (!clos) {
                    final Listitem dato = (Listitem) winMensaje.getFellow("datoP", false);
                    if (dato.getValue() != null) {
                        setObjHistoria(dato.getValue());
                        if (getObjHistoria() != null) {
                            if (tipo < 2) {
                                if (existeOrdenAvasus()) {
                                    crearUpdOrden(1);//actualiza
                                } else {
                                    crearUpdOrden(0);//crear
                                }
                            }
                            cargarDatosHistoria();
                        } else {
                            reset();
                            Messagebox.show("No se puede crear Historia Avasus", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                        }
                    } else {
                        reset();
                        Messagebox.show("No se puede crear Historia Avasus", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                    }
                }
            }// hasfta aui
        });
        winMensaje.setClosable(true);
        winMensaje.setWidth("80%");
        winMensaje.setPosition("center");
        winMensaje.setParent(WinIngreso);
        winMensaje.setTitle("Historia");
        winMensaje.doModal();
    }

    private void agregarArea(Integer are) {
        boolean exis = false;
        try {
            for (int i = 0; i < areas.size(); i++) {
                if (Objects.equals(are, areas.get(i))) {
                    exis = true;
                    i = areas.size();
                }
            }
            if (!(exis)) {
                areas.add(are);
            }
        } catch (Exception e) {
            alert("Area :" + are);
        }

    }

    public void setCreado(boolean creado) {
        this.creado = creado;
    }

    public void setCopiar(PdfCopyFields copiar) {
        this.copiar = copiar;
    }

    private void edad(Date fecha) {
        String anos;
        CalcularEdad calcEdad = new CalcularEdad(fecha, new Date());
        if (calcEdad.getAnio() == 0) {
            if (calcEdad.getMes() == 0) {
                anos = calcEdad.obtenerDias();
            } else {
                anos = calcEdad.obtenerMeses();
            }
        } else {
            anos = calcEdad.obtenerAnios();
        }
        setAnos(anos);

    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="GET SET">
    public Historia getObjHistoria() {
        return objHistoria;
    }

    public void setObjHistoria(Historia objHistoria) {
        if (objHistoria != null) {
            if (!(objHistoria.getFechaNace() == null)) {
                try {
                    edad(objHistoria.getFechaNace());
                } catch (Exception e) {
                }
            }
            this.objHistoria = objHistoria;
        } else {

        }
    }

    public LispetAvanex getObjOrdenN() {
        return objOrdenN;
    }

    public void setObjOrdenN(LispetAvanex objOrdenN) {
        this.objOrdenN = objOrdenN;
    }

    public List getListapedidosN() {
        return ListapedidosN;
    }

    public void setListapedidosN(List ListapedidosN) {
        this.ListapedidosN = ListapedidosN;
    }

    public String getAnos() {
        return anos;
    }

    public void setAnos(String anos) {
        this.anos = anos;
    }

    public Orden getObjOrdenP() {
        return objOrdenP;
    }

    public void setObjOrdenP(Orden objOrdenP) {
        this.objOrdenP = objOrdenP;
    }
    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Limpiar">

    private void reset() {
        empresa = null;
        modOrd.setDisabled(true);
        idEmpr.setDisabled(true);
        idMedP.setDisabled(true);
        vPaciente.setDisabled(true);
        vOrden.setDisabled(true);
        btnBuscar.setDisabled(false);
        txtPaciente.setDisabled(false);
        txtHistoria.setDisabled(false);
        txtOrden.setDisabled(false);

        limpiar();
        cleanGrid();
    }

    private void limpiar() {
        objHistoria = null;
        objOrdenN = null;
        objOrdenP = null;
        txtOrden.setValue("");
        txtPaciente.setValue("");
        txtHistoria.setValue("");
        idNombre.setValue("");
//        idEmpresa.setValue("");
        idOrigen.setValue("");
        idSexo.setValue("");
        idEdad.setValue("");
        idCI.setValue("");
        idOrden.setValue("");
        idOrdenPo.setValue("");
        idMedP.setValue("");
        idMed.setValue("");
        idEmpr.setValue("");
//        idMedSoli.setValue("");
    }

    private void cleanGrid() {
        List<Listitem> hijos = pedidos.getItems();
        for (int i = hijos.size() - 1; i > -1; i--) {
            pedidos.removeItemAt(i);
        }
    }

    private void blockBusque(boolean bloc) {
        txtOrden.setDisabled(bloc);
        txtHistoria.setDisabled(bloc);
        txtPaciente.setDisabled(bloc);
//        btnBuscar.setDisabled(bloc);
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="PDF ">
//    private Connection coneccionSQL() throws SQLException {

//        DataSource ds = null;
//        try {
//            Context ctx = new InitialContext();
//            //DataSource ds = (DataSource) ctx.lookup("jdbc/avasus");
//            ds = (DataSource) ctx.lookup("jdbc/avasus");
//            Connection con = ds.getConnection();
//            return con;
//        } catch (SQLException | NamingException e) {
//            System.out.println(e.getMessage());
//            System.out.println("e" + e.toString());
//        }
//        return null;
//    }

    private void cerrarConeccionSQL() {
        try {
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/avasus");
            ds.getConnection().close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("e" + e.toString());
        }

    }

    public static void awaitGarbageCollection() throws Exception {
        Runtime.getRuntime().gc();
        Thread.sleep(100);
        System.runFinalization();
    }

    private byte[] bytes = null;
    private String ubicacion = null;
    private PdfCopyFields copiar = null;
    private List<byte[]> listaPDF = null;
    JasperReport report = null;
    private boolean creadoPDF = true;
    JasperPrint print = null;

    private byte[] getBytes() {
        return bytes;
    }

    public List<byte[]> getListaPDF() {
        return listaPDF;
    }

    public PdfCopyFields getCopiar() {
        return copiar;
    }

    private void abrirOrden(int tipo, Long orden, Integer practica, Integer area) {
        String path;
        String pathd = null;
        //path = "C:/glassfishv3/Reportes/";
        path = "C:/Users/JP3/Documents/glassfishv3/Reportes/";
        String osName = System.getProperty("os.name");
        if (osName.toUpperCase().indexOf("WINDOWS") == 0) {
            //path = "C:/glassfishv3/Reportes/";
            //C:\Users\JP3\Documents\glassfishv3\Reportes
            path = "C:/Users/JP3/Documents/glassfishv3/Reportes/";
        } else if (osName.toUpperCase().indexOf("LINUX") == 0) {
            path = "/usr/share/glassfishv3/Reportes/";
        }
        byte[] buf = null;
        PdfReader pdfReader = null;

        try {           
            Context ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/avasus");
            Connection con = ds.getConnection();
            switch (tipo) {
                case 1: {
                    if (isCreadoPDF()) {
                        pathd = path + orden.toString();
                        instanciarPDF(pathd);
                    }
                    Map parameters = null;
                    for (int i = 0; i <= areas.size() - 1; i++) {
                        setReport(JasperCompileManager.compileReport(path + "reporte.jrxml"));
                        parameters = new HashMap();
                        parameters.put("edad", getAnos());
                        parameters.put("orden", orden);
                        if (area == null) {
                            parameters.put("practica", practica);
                        }
                        parameters.put("usuario", usuario);
                        parameters.put("area", areas.get(i));

                        java.util.Locale locale = new Locale("es", "ES");
                        parameters.put(JRParameter.REPORT_LOCALE, locale);

                        setPrint(JasperFillManager.fillReport(getReport(), parameters, con)); //System.out.println("cerrando conexion");                                                
                        //setPrint(JasperFillManager.fillReport(getReport(), parameters, coneccionSQL()));

                        setBytes(JasperExportManager.exportReportToPdf(getPrint()));
                        pdfReader = new PdfReader(getBytes());
                        getCopiar().addDocument(pdfReader);

                    }

                    getCopiar().close();
                    getBytesDocumento();
                    buf = getBytes();
                    String fileName = "resultados.pdf";
                    InputStream mediaIS = new ByteArrayInputStream(buf);
                    AMedia media = new AMedia(fileName, "pdf", "application/pdf", mediaIS);
                    Iframe reporte = null;
                    if (media != null) {
                        Window winMensaje = new Window();
                        Executions.createComponents("win_orden.zul", winMensaje, null);
                        winMensaje.setBorder("normal");
                        winMensaje.setClosable(true);
                        winMensaje.setTitle("Vista Pedido");
                        final Label msg = new Label();
                        msg.setParent(winMensaje);
                        msg.setVisible(false);
                        final Iframe frameReporte = (Iframe) winMensaje.getFellow("reporteV", false);
                        frameReporte.setContent(media);
                        winMensaje.setId("winMsgPreview");
                        winMensaje.setParent(WinIngreso);
                        winMensaje.doModal();
                        try {

                            File obj = new File(pathd + ".pdf");
                            if (obj.exists()) {
                                obj.delete();
                            }
                        } catch (Exception e) {
                            System.out.println("e" + e);
                        }

                    }
                    con.close();
                }
                break;
                case 2: {
                    try {
                        setReport(JasperCompileManager.compileReport(path + "reporte.jrxml"));
                    } catch (Exception e) {
                        alert("SetReport: reporte.jr");
                    }

//                    report = JasperCompileManager.compileReport(path + "reporte.jrxml");
                    Map parameters = new HashMap();
                    parameters.put("edad", getAnos());
                    parameters.put("orden", orden);
                    if (area == null) {
                        parameters.put("practica", practica);
                    }
                    parameters.put("usuario", usuario);
                    parameters.put("area", area);
                    java.util.Locale locale = new Locale("es", "ES");
                    parameters.put(JRParameter.REPORT_LOCALE, locale);

                    //Context ctx = new InitialContext();
                    //DataSource ds = (DataSource) ctx.lookup("jdbc/avasus");
                    //ds = (DataSource) ctx.lookup("jdbc/avasus");
                    JasperPrint print = JasperFillManager.fillReport(report, parameters, con);
                    //JasperPrint print = JasperFillManager.fillReport(report, parameters, ds.getConnection());
                    buf = JasperExportManager.exportReportToPdf(print);

                    if (buf != null) {
                        String fileName = "resultados.pdf";
                        InputStream mediaIS = new ByteArrayInputStream(buf);
                        AMedia media = new AMedia(fileName, "pdf", "application/pdf", mediaIS);
                        Iframe reporte = null;
                        if (media != null) {
                            Window winMensaje = new Window();
                            Executions.createComponents("win_orden.zul", winMensaje, null);
                            winMensaje.setBorder("normal");
                            winMensaje.setClosable(true);
                            winMensaje.setTitle("Vista Pedido");
                            final Label msg = new Label();
                            msg.setParent(winMensaje);
                            msg.setVisible(false);
                            final Iframe frameReporte = (Iframe) winMensaje.getFellow("reporteV", false);
                            frameReporte.setContent(media);
                            winMensaje.setId("winMsgPreview");
                            winMensaje.setParent(WinIngreso);
                            winMensaje.doModal();
                        }
                    }
                    con.close();
                }
                break;
                case 3: {
                    report = JasperCompileManager.compileReport(path + "todo.jrxml");
                }
            }
            awaitGarbageCollection();
        } catch (Exception e) {
            alert(e.toString());
            System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraIngreso.abrirOrden()");
        }

    }

    public void setBytes(byte[] bytesPDF) {
        this.bytes = bytesPDF;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    private void getBytesDocumento() throws IOException {
        FileInputStream fis = new FileInputStream(getUbicacion());
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        for (int a; (a = fis.read(buf)) != -1;) {
            bos.write(buf, 0, a);
        }
        setBytes(bos.toByteArray());
    }

    private void instanciarPDF(String path) throws DocumentException, FileNotFoundException {
        String dirPDF = path + ".pdf";
        PdfCopyFields copiar = new PdfCopyFields(new FileOutputStream(dirPDF));
        setCopiar(copiar);
        setCreado(false);
        setUbicacion(dirPDF);
    }

    public boolean isCreadoPDF() {
        return creadoPDF;
    }

    public JasperReport getReport() {
        return report;
    }

    public void setReport(JasperReport report) {
        this.report = report;
    }

    public JasperPrint getPrint() {
        return print;
    }

    public void setPrint(JasperPrint print) {
        this.print = print;
    }

    // </editor-fold>
    private void cargarEmpresas() {
        AdmiNegocio objAdmin = new AdmiNegocio();
        Map<String, Object> wSQL = new HashMap<>();
        wSQL = new HashMap<>();
        wSQL.put("lockReg ?=", 0);
        List oSQL = new ArrayList();
        oSQL.add("razonSocial");
        List objEmpresas = null;
        try {
            objEmpresas = objAdmin.getData(new Organizacion(), wSQL, null, oSQL);

        } catch (NamingException ex) {
            Logger.getLogger(ControladoraIngreso.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        for (Object object1 : objEmpresas) {
            Organizacion obj1 = (Organizacion) object1;
            Comboitem obj = new Comboitem(obj1.getRazonSocial());
            if (Objects.equals(obj1.getId(), empresa.getId())) {
                empresa = obj1;
                idEmpr.setValue(empresa.getRazonSocial());
            }
            obj.setValue(obj1);
            obj.setParent(idEmpr);
        }
        idEmpr.setDisabled(false);
        idEmpr.addEventListener(Events.ON_SELECT, new EventListener() {
            @Override
            public void onEvent(Event event) throws Exception {
                // idEmpr.setDisabled(false);
                Organizacion gjh = (Organizacion) idEmpr.getSelectedItem().getValue();
                idEmpr.setValue(gjh.getRazonSocial());
                empresa = gjh;
            }
        });
    }
}

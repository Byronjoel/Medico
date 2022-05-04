package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.modelo.core.*;
import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.organizacion.Organizacion;
import ec.com.cubosoft.avamed.modelo.practica.*;
import ec.com.cubosoft.avamed.procesos.*;
import ec.com.cubosoft.avamed.modelo.medico.Area;
import ec.com.cubosoft.avamed.modelo.nextla.STrausu;
import ec.com.cubosoft.avamed.modelo.nextla.SUsuar;
import ec.com.cubosoft.avamed.modelo.nextla.sessionOk;
import ec.com.cubosoft.avasus.controller.renderder.AreaRenderer;
import ec.com.cubosoft.avasus.controller.renderder.OrdenIngresoRenderer;
import ec.com.cubosoft.avasus.controller.renderder.OrganizacionRendererCombo;
import ec.com.cubosoft.avasus.controller.renderder.PracticaRenderer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.*;

/**
 * @author Juan Pablo Chavez
 * @version 1.0.1
 *
 * @author Patricia Amoroso
 * @version 1.0
 */
public class ControladoraAuditoriaSineditar extends GenericForwardComposer {

    private Integer idArea, idPractica;
    private Long idOrganizacion;
    Datebox FecHasta, FecDesde;
    Button btnBuscar, btnReset, btnCancel, btnNew, btnGuardar, btnOpen,
            btnPreview, btnUpdate, btnDelete, btnPrint, btnSync;
    Listbox LbxPracticas, LbxAreas;
    Combobox LbxEmpresas;
    Textbox txtPacienteBusqueda, OrdenDesde, OrdenHasta;
    Window WinAuditoria;
    Grid GridOrdenes;
    Rows rowsTabla;
    CsGrupos objGrupoActivoP;
    CsUsuarios objUsuarioActivoP;
    SUsuar objUsuarioActivoN;
    List<CsPerxgru> listPermisos;
    ProcesosSession admiSessionUsuario = new ProcesosSession();
    AdmiNegocio admNegocio;
    AdmiNegocioSql negoSQL;
    private Footer footer_orden;
    Include barraBotones;
    Div cabecera;

    public void onCreate$WinAuditoria() {
        modificarSession();
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
    private boolean delete;
    private boolean auditoria;

    private void modificarSession() {
        try {
            GridOrdenes.setVflex(true);
            ProcesosSession admiSessionUsuario = new ProcesosSession();
            List listaper = null;
            objUsuarioActivoP = null;
            objUsuarioActivoN = null;
            usuario = "";
            sessionOk objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
            objsessiActica.setPagina(page.getId());
            admiSessionUsuario.AgregarAtributoSession(2, objsessiActica, desktop.getSession());
            admNegocio = new AdmiNegocio();
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
                    if (objpe.getPerDelete() == 1) {
                        delete = true;
                    } else {
                        delete = false;
                    }
                    if (objpe.getPerAuditoria() == 0) {
                        auditoria = true;
                    } else {
                        auditoria = false;
                    }
                }
            } else {
                listaper = objsessiActica.getPerUsuAva();
                objUsuarioActivoP = objsessiActica.getUsuarioP();
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
//                if ((idMenuPermiso.equalsIgnoreCase("m_archivo")) || ((idMenuPermiso.equalsIgnoreCase("ConsResult")))) {
                if ((idMenuPermiso.equalsIgnoreCase("m_archivo"))) {
                    if (auditoria) {
                        i = listaper.size();
                        enableButtons();
                        loadEmpresas();
                    } else {
                        alert("No dispone permisos Archivos");
                        cabecera.setVisible(false);
                        barraBotones.setVisible(false);
                        //   WinAuditoria.setVisible(false);
                    }
                } else {
                    if (auditoria) {
                        i = listaper.size();
                        enableButtons();
                        loadEmpresas();
                    } else {
                        alert("No dispone permisos");
                        cabecera.setVisible(false);
                        barraBotones.setVisible(false);
                        //            WinAuditoria.setVisible(false);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void enableButtons() {
        try {

            btnBuscar = (Button) barraBotones.getFellow("btnBuscar", false);
            btnReset = (Button) barraBotones.getFellow("btnReset", false);
            btnCancel = (Button) barraBotones.getFellow("btnCancel", false);
            btnGuardar = (Button) barraBotones.getFellow("btnGuardar", false);
            btnNew = (Button) barraBotones.getFellow("btnNew", false);
            btnBuscar.setDisabled(false);
            btnBuscar.addEventListener(Events.ON_CLICK, new EventListener() {

                @Override
                public void onEvent(Event event) throws Exception {
                    buscar();
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List getOrdenesIngreso() throws NamingException, InterruptedException {
        List listOrdenes = null;
        if ((FecDesde.getValue() == null) 
                && (FecHasta.getValue() == null)
                && (OrdenDesde.getValue().isEmpty()) 
                && (OrdenHasta.getValue().isEmpty()) 
                && (txtPacienteBusqueda.getValue().isEmpty())
                && ((LbxEmpresas.getValue().equals("(Todos)")) 
                || (LbxEmpresas.getValue().isEmpty()))
                || (!(OrdenDesde.getValue().matches("[0-9]*")))
                || (!(OrdenHasta.getValue().matches("[0-9]*")))) {
            Messagebox.show("No existen condiciones de busqueda, por favor ponga alguna condición.",
                    "Filtro Vacío", Messagebox.OK, Messagebox.EXCLAMATION);
        }
        listOrdenes = getOrdenes();
        System.out.println(new Date());
        return listOrdenes;
    }

    private List getOrdenes() throws NamingException {
        /*
        AdmiNegocioSql negoSQL = new AdmiNegocioSql();
        List buscarOrdenNestlab = null;
        buscarOrdenNestlab = buscarOrdenNestlab(OrdenDesde.getValue());
         */
        Map<String, Object> wSQL = new HashMap<String, Object>();
        Object table = new Orden();
        List oSQL = new ArrayList();
        
        //System.out.println("orden desede: " + OrdenDesde.getValue());
        //admNegocio.getDataLimitNestalb(table, wSQL, null, oSQL, 30);
        //OrdenDesde.setValue(OrdenDesde.getValue());
        // OrdenHasta.setValue(OrdenDesde.getValue());
       // System.out.println(new Date());

        wSQL.put("id ?>=", OrdenDesde.getValue());
        wSQL.put("id ?<=", OrdenHasta.getValue());
        wSQL.put("organizacion.id ?=", idOrganizacion);
        wSQL.put("fecIngreso ?>=", FecDesde.getValue());
        wSQL.put("fecIngreso ?<=", FecHasta.getValue());
        wSQL.put("historia.paciente ?like", txtPacienteBusqueda.getValue().toUpperCase());
        oSQL.add("id");

        try {
            return admNegocio.getDataLimit(table, wSQL, null, oSQL, 30);
        } catch (Exception e) {
            return null;
        }

    }

    public List buscarOrdenNestlab(String orden) {
        AdmiNegocioSql negoSQL = new AdmiNegocioSql();
        Object table = new Orden();
        Map<String, Object> wSQL = new HashMap<>();
        List oSQL = new ArrayList();
        wSQL.put("nroOrd ?>=", OrdenDesde.getValue());
        wSQL.put("nroOrd ?>=", OrdenDesde.getValue());
        oSQL.add("id");
        //en sql como esta organizacion
        //wSQL.put("organizacion.id ?=", idOrganizacion);
        //wSQL.put("fecIngreso ?>=", FecDesde.getValue());
        //wSQL.put("fecIngreso ?<=", FecHasta.getValue());
        //wSQL.put("historia.paciente ?like", txtPacienteBusqueda.getValue().toUpperCase());
        //oSQL.add("id");
        try {
            return negoSQL.getDataSearchNestlab(table, wSQL, oSQL, oSQL);
        } catch (Exception e) {
            return null;
        }

//        ListapedidosN = negoSQL.getData(new LispetAvanex(), wSQL, null, null);
//        //System.out.println("2" + new Date() + " / " + txtOrden.getValue() + " / ");
//        if (ListapedidosN.size() > 0) {
//            setListapedidosN(ListapedidosN);
//            btnBuscar.setDisabled(false);
//            setObjOrdenN((LispetAvanex) ListapedidosN.get(0));
//            if ((existeOrdenAvasus()) && (getObjOrdenN() != null)) {
//                //si hay orden avasus
//                txtOrden.setDisabled(true);
//                setObjHistoria(getObjOrdenP().getHistoria());
//                crearUpdOrden(1);//actualiza orden
//                cargarDatosHistoria();
//                vPaciente.setDisabled(false);
//                vOrden.setDisabled(false);
//                blockBusque(true);
//                btnUpdate.setDisabled(false);
//            } else {
//                //hay orden nextlab
//                if (getObjOrdenN() != null) {
//                    // System.out.println("Orden Existente  Nextlab");
//                    //  alert("Orden Existente  Nextlab");
//                    crearAvasus();
//
//                } else {
//                    reset();
//                    Messagebox.show("No existe datos para la consulta", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
//                }
//            }
//        } else {
//            reset();
//            Messagebox.show("No existe la orden en Nextlab", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
//        }
    }

    /*
    así estaba
    private List getOrdenes() throws NamingException {
        System.out.println(new Date());
        Object table = new Orden();
        Map<String, Object> wSQL = new HashMap<String, Object>();
        List oSQL = new ArrayList();
        wSQL.put("id ?>=", OrdenDesde.getValue());
        wSQL.put("id ?<=", OrdenHasta.getValue());
        wSQL.put("organizacion.id ?=", idOrganizacion);
        wSQL.put("fecIngreso ?>=", FecDesde.getValue());
        wSQL.put("fecIngreso ?<=", FecHasta.getValue());
        wSQL.put("historia.paciente ?like", txtPacienteBusqueda.getValue().toUpperCase());
        oSQL.add("id");
        try {
            return admNegocio.getDataLimit(table, wSQL, null, oSQL, 30);
        } catch (Exception e) {
            return null;
        }

    }
     */
    public void buscar() {
        //Limpieza de Grid
        cleanGrid();
        try {
            List<Orden> listOrdenesIngreso = getOrdenesIngreso();
            System.out.println(new Date());
//            GridOrdenes.setRowRenderer(new OrdenIngresoRenderer(usuario, impr, delete, WinAuditoria, false));
//            GridOrdenes.setModel(new ListModelList(listOrdenesIngreso));
//            GridOrdenes.renderAll();
//            GridOrdenes.invalidate();
//            footer_orden.setLabel(String.valueOf(listOrdenesIngreso.size()) + " Orden(es) en listado");
            System.out.println(new Date());
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void cleanCab() {
        txtPacienteBusqueda.setValue("");
        OrdenDesde.setValue("");
        OrdenHasta.setValue("");
        FecDesde.setValue(new Date());
        FecHasta.setValue(new Date());
        LbxEmpresas.setValue(null);
        idArea = null;
        idOrganizacion = null;
        idPractica = null;
    }

    private void reset() {
        btnBuscar.setDisabled(false);
        cleanCab();
        cleanGrid();
    }

    public void onClick$btnCancelar() {
        reset();
    }

    private void cleanGrid() {
        rowsTabla.getChildren().clear();
        footer_orden.setLabel("");
    }

    public void loadAreas() throws NamingException {
        Object table = new Area();
        List oSQL = new ArrayList();
        oSQL.add("descripcion");
        Map<String, Object> wSQL = new HashMap<String, Object>();
        wSQL.put("lockReg ?=", 0);
        List objectList = admNegocio.getData(table, wSQL, null, oSQL);
        LbxAreas.setItemRenderer(new AreaRenderer());
        LbxAreas.setModel(new ListModelList(objectList));
    }

    public void onSelect$LbxAreas() {
        Area area = (Area) LbxAreas.getSelectedItem().getValue();
        idArea = area.getId();
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

    public void loadPracticas() throws NamingException {
        Object table = new NombreP();
        List oSQL = new ArrayList();
        oSQL.add("descripcion");
        List objectList = admNegocio.getData(table, false, oSQL);
        LbxPracticas.setItemRenderer(new PracticaRenderer());
        LbxPracticas.setModel(new ListModelList(objectList));
    }

    public void onSelect$LbxPracticas() {
        NombreP practica = (NombreP) LbxPracticas.getSelectedItem().getValue();
        idPractica = practica.getId();
    }
}

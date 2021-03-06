
package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.mensajes.AdmMensajes;
import ec.com.cubosoft.avamed.mensajes.TipoMensaje;
import ec.com.cubosoft.avamed.modelo.core.CsGrupos;
import javax.naming.NamingException;
import org.xml.sax.*;
import ec.com.cubosoft.avamed.modelo.core.CsPerxgru;
import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.ingreso.PracticaXOrden;
import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import ec.com.cubosoft.avamed.modelo.practica.*;
import ec.com.cubosoft.avamed.negocio.CalcularEdad;
import ec.com.cubosoft.avamed.negocio.ConvertirDocumento;
import ec.com.cubosoft.avamed.procesos.*;
import ec.com.cubosoft.avamed.operaciones.Comp;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zk.ui.event.*;
import org.zkoss.zul.*;

/**
 *
 * @author Administrador
 */
public class ControladoraReportes extends GenericForwardComposer {

    //fechas
//    SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
//    //<editor-fold defaultstate="collapsed" desc="PLANTILLA CARGA">
//    Button btnBuscar;
//    Button btnReset;
//    Button btnCancelar;
//    Button btnSalir;
//    Button btnNuevo;
//    Button btnGuardar;
//    Button btnPreview;
//    Button btnUpdate;
//    Button btnDelete;
//    Button btnImprimir;
//    Button btnSincronizar;
//    boolean bandGuardar;
//    ProcesosSession admiSessionUsuario = new ProcesosSession();
//    CsPerxgru permisosMenuIDControles = null;
//
//    public void onCreate$WinControles() {
//        try {
//            modificarSession();
//        } catch (Exception e) {
//          e.printStackTrace(System.out);
//        }
//
//    }
//
//    private void modificarSession() {
//        try {
//            admiSessionUsuario.AgregarAtributoSession(4, page.getId(), session);
//            permisosMenuIDControles = admiSessionUsuario.ObtenerPermisosPgina(session);
//            HabilitarBotones();
//
//        } catch (Exception e) {
//            System.out.print(e.getMessage() + e.getCause());
//        }
//
//    }
//
//    public void HabilitarBotones() {
//        try {
//            btnBuscar.setDisabled(false);
//            btnBuscar.setVisible(true);
//            btnReset.setDisabled(false);
//            btnReset.setVisible(true);
//            btnCancelar.setDisabled(false);
//            btnCancelar.setVisible(true);
//            btnSalir.setDisabled(false);
//            btnSalir.setVisible(true);
//            if (permisosMenuIDControles != null) {
//                if (permisosMenuIDControles.getAlta() == 1) { //activo nuevo
//                    btnNuevo.setDisabled(false);
//                    btnNuevo.setVisible(true);
//                    bandGuardar = true;
//                } else {
//                    bandGuardar = true;
//                    btnNuevo.setStyle("color: #A2B5CD;");
//
//                }
//                if (permisosMenuIDControles.getBaja() == 1) { //activo eliminacion
//                    btnDelete.setVisible(true);
//                    btnDelete.setDisabled(false);
//
//                } else {
//                    btnDelete.setStyle("color: #A2B5CD;");
//                }
//                if (permisosMenuIDControles.getModif() == 1) { //activo actualizar
//                    btnUpdate.setDisabled(false);
//                    btnUpdate.setVisible(true);
//                    bandGuardar = true;
//                } else {
//                    btnUpdate.setStyle("color: #A2B5CD;");
//                }
//                if (permisosMenuIDControles.getImprime() == 1) { //activo imprimir
//                    btnImprimir.setDisabled(false);
//                    btnImprimir.setVisible(true);
//                } else {
//                    btnImprimir.setStyle("color: #A2B5CD;");
//                }
//                if (permisosMenuIDControles.getSync() == 1) { //activo sincronizar
//                    btnSincronizar.setDisabled(false);
//                    btnSincronizar.setVisible(true);
//                } else {
//                    btnSincronizar.setStyle("color: #A2B5CD;");
//                }
//                if (bandGuardar) { //activo sincronizar
//                    btnGuardar.setDisabled(false);
//                    btnGuardar.setVisible(true);
//                } else {
//                    btnGuardar.setStyle("color: #A2B5CD;");
//                }
//
//
//            }
//            //cambiar permisos de los reportes
//            btnGuardar.setDisabled(false);
//            btnGuardar.setVisible(true);
//
//        } catch (Exception e) {
//        }
//
//    }
//// </editor-fold>
//    //<editor-fold defaultstate="collapsed" desc="EVENTOS CONTROL">
//    Textbox bOrden;
//    Textbox bHistorial;
//    String Practica;
//    String Area;
//
//    public void onClick$btnBuscar() throws InterruptedException {
//        try {
//            if ((bOrden.getValue().isEmpty()) && (bHistorial.getValue().isEmpty())) {
//                //no se puuede realizar la busqueda
//                Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.INCOMPLETO), "Informacion", Messagebox.OK, Messagebox.INFORMATION);
//            } else {
//                FiltroBusqueda();
//                btnBuscar.setDisabled(true);
//            }
//        } catch (Exception e) {
//            Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.ERROR_BUSCAR), "Informacion", Messagebox.OK, Messagebox.ERROR);
//            limpiarDatos();
//        }
//        limpiarFiltroBusqueda();
//    }
//    Document newDoc;
//
//    public void onClick$btnGuardar() throws InterruptedException {
//
//        try {
//            newDoc = admXml.crearDocumento();
//            newDoc = CreamosXML(newDoc);
//            if (!(Completo)) {
//                Messagebox.show("Guardar como incompleto?", "Archivo Incompleto", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
//
//                    public void onEvent(Event event) throws Exception {
//                        try {
//                            switch (((Integer) event.getData()).intValue()) {
//                                case Messagebox.YES:
//                                    guardar("IN");
//                                    break;
//                                case Messagebox.NO:
//                                    guardar("CO");
//                                    break;
//                            }
//                        } catch (Exception e) {
//                        } finally {
//                            reset();
//                        }
//                    }
//                });
//            } else {
//                Messagebox.show("Guardar como Completo?", "Archivo Completo", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
//
//                    public void onEvent(Event event) throws Exception {
//                        try {
//                            switch (((Integer) event.getData()).intValue()) {
//                                case Messagebox.YES:
//                                    guardar("IN");
//                                    break;
//                                case Messagebox.NO:
//                                    guardar("CO");
//                                    break;
//                            }
//                        } catch (Exception e) {
//                        } finally {
//                            reset();
//                        }
//                    }
//                });
//            }
//
//        } catch (Exception e) {
//        }
//
//    }
//    //<editor-fold defaultstate="collapsed" desc="Ordenes y PRacticas Historia">
//    Listbox LsbOrdenesXHistoria;
//    Listbox LsbPracticasXOrden;
//    Window WinOrdenes;
//    String idSeleccion = "";
//    Historia objHistoria;
//
//    private void FiltroBusqueda() throws InterruptedException {
//        try {
//            List<Object> objsHistoria = null;
//            boolean BOrd = false;
//            if (bOrden.getValue().isEmpty()) {
//                //me da una historia
//                BOrd = true;
//            }
//
//            objsHistoria = admNegocioHibe.getDatosHistoria(bHistorial.getValue(), bOrden.getValue(),null);
//            if (!(objsHistoria == null)) {
//                objHistoria = (Historia) objsHistoria.get(0);
//                idNombre.setValue(objHistoria.getNombres());
//                idSexo.setValue(new AdmMensajes().getDescripcionAbreviatura(objHistoria.getSexo()));
//                idCI.setValue(objHistoria.getNumId());
//                idHistoria.setValue(objHistoria.getId().toString());
//                bHistorial.setValue(objHistoria.getId().toString());
//                bHistorial.setDisabled(true);
//
//                idEmpresa.setValue("falta1");
//                idECivil.setValue(new AdmMensajes().getDescripcionAbreviatura(objHistoria.getEstadoCivil()));
//                idEdad.setValue(new CalcularEdad(objHistoria.getFechaNace(), new Date()).obtenerAnios());
//                idHistoria.setValue(objHistoria.getId().toString());
//                grdDatos.setVisible(true);
//                List PracXFecha = admNegocioHibe.getFechasXId("id", objHistoria.getId().toString());
//                if (PracXFecha.size() > 0) {
//                    CrearTree(PracXFecha);
//                }
//                GrpArbol.setVisible(true);
//                List ListaOrdenesHistoria = null;
//                if (bOrden.getValue().isEmpty()) {
//                    if (BOrd) {
//                        Historia obj = (Historia) objsHistoria.get(0);
//                        ListaOrdenesHistoria = obj.getOrden();
//                        CargarOrdenesHistoria(ListaOrdenesHistoria);
//                    }
//                    //cargo lista de ordenes de la persona
//                } else {
//                    //me tengo la historia
//                    FiltroBusquedaPracticas(bOrden.getValue());
//                }
//            } else {
//                //no hay la historia ni la orden
//                Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.ERROR_BUSCAR_VACIO), "Informacion", Messagebox.OK, Messagebox.INFORMATION);
//            }
//        } catch (NamingException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//    List ListaPracticasxOrden = null;
//    Integer posPractica;
//    NombreP objPractica;
//
//    private void FiltroBusquedaPracticas(String idOrdenB) throws InterruptedException {
//        try {
//            CsGrupos objGrupoActivo = (CsGrupos) admiSessionUsuario.ObtenerAtributoSession(3, session);
//            CsUsuarios objUsuarioActivo = (CsUsuarios) admiSessionUsuario.ObtenerAtributoSession(2, session);
//            ListaPracticasxOrden = admNegocioHibe.getPracticaxOrden(idOrdenB, idHistoria.getValue(),bbPracticas.getValue(), bbAreas.getValue(), objUsuarioActivo.getUsuario(), objGrupoActivo.getDesGru());
//            if (ListaPracticasxOrden.size() == 1) {
//                PracticaXOrden objpo = (PracticaXOrden) ListaPracticasxOrden.get(0);
//                objPractica = (NombreP) objpo.getPractica();
//                List PracticaResulataXML = admNegocioHibe.getXMLResultados(null,objPractica.getId().toString(),idOrdenB,null,null);
//                if (PracticaResulataXML.size() == 1) {
//                    XmlResultado resXml = (XmlResultado) PracticaResulataXML.get(0);
//                    cargarGridResultados(resXml);
//
//                    alert("verifico estado y muesto el resultado");
//                    NomPractica.setValue(objPractica.getAbreviatura());
//                    NomPractica.setVisible(true);
//                    NomPractica.setStyle("font-size: 26px;color:#00008B");
//                } else {
//                    NomPractica.setValue(objPractica.getAbreviatura());
//                    NomPractica.setVisible(true);
//                    NomPractica.setStyle("font-size: 26px;color:#00008B");
//                    crearGridReporte();
//                }
//            } else {
//                if (ListaPracticasxOrden.size() >= 1) {
//                    cargarPracticasXOrden(ListaPracticasxOrden);
//                } else {
//                    if (ListaPracticasxOrden == null) {
//                        Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.ERROR_BUSCAR_VACIO), "Informacion", Messagebox.OK, Messagebox.INFORMATION);
//                    } else {
//                        Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.ERROR_BUSCAR_VACIO), "Informacion", Messagebox.OK, Messagebox.INFORMATION);
//
//                    }
//                }
//            }
//        } catch (Exception e) {
//            Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.ERROR_BUSCAR_VACIO), "Informacion", Messagebox.OK, Messagebox.INFORMATION);
//        }
//    }
//    Label NomPractica;
//
//    void FiltroBusquedaResultado(String idPrac, String nombre) throws InterruptedException {
//        try {
//            NomPractica.setValue(nombre);
//            NomPractica.setVisible(true);
//            NomPractica.setStyle("font-size: 26px;color:#00008B");
//            List PracticaResulataXML = admNegocioHibe.getXMLResultados(null,idPrac,bOrden.getValue(),null,null);
//            if (PracticaResulataXML.size() == 1) {
//                XmlResultado obj = new XmlResultado();
//                obj = (XmlResultado) PracticaResulataXML.get(0);
//                cargarGridResultados(obj);
//                alert("verifico estado y muesto el resultado");
//            } else {
//                crearGridReporte();
//            }
//        } catch (Exception e) {
//            Messagebox.show(new AdmMensajes(TipoMensaje.ERROR_BUSCAR, e).getMessage(TipoMensaje.ERROR_BUSCAR), "INFORMACION", Messagebox.OK, Messagebox.INFORMATION);
//        }
//    }
//    String nombrePractica;
//
//    private void cargarPracticasXOrden(List PracticasXOrden) throws InterruptedException {
//        try {
//            int x = LsbPracticasXOrden.getChildren().size();
//            if (x >= 1) {//si hay datos toca eliminar
//                while (LsbPracticasXOrden.getChildren().size() > 0) {
//                    for (int i = 0; i < LsbPracticasXOrden.getChildren().size(); i++) {
//                        LsbPracticasXOrden.getChildren().remove(i);
//                    }
//                }
//            }
//            for (Object objPracticaXOrden : PracticasXOrden) {
//                PracticaXOrden objPracOrden = (PracticaXOrden) objPracticaXOrden;
//                objPractica = (NombreP) objPracOrden.getPractica();
//                Listitem filaPracticaOrden = new Listitem();
//                Listcell idPractica = new Listcell(objPractica.getId().toString());
//                Date n = new Date();
//                Listcell DesPractica = new Listcell(objPractica.getDescripcion());
//                filaPracticaOrden.appendChild(idPractica);
//                filaPracticaOrden.appendChild(DesPractica);
//                filaPracticaOrden.setParent(LsbPracticasXOrden);
//            }
//
//            Window winPracticas;
//            winPracticas = new Window("Practicas", "none", true);
//            winPracticas.setWidth("400px");
//            winPracticas.setHflex("500px");
//            winPracticas.setParent(Wrep);
//            winPracticas.doModal();
//
//            winPracticas.addEventListener("onClose", new EventListener() {
//
//                @Override
//                public void onEvent(Event event) throws Exception {
//                    if (idSeleccion.isEmpty()) {
//                        alert("No se ha seleccionado nada");
//                    } else {
//                        String v = idSeleccion;
//                        idSeleccion = "";
//                        FiltroBusquedaResultado(v, nombrePractica);
//                    }
//                }
//            });
//            final Listbox prac = new Listbox();
//            while (LsbPracticasXOrden.getChildren().size() > 0) {
//                for (int i = 0; i < LsbPracticasXOrden.getChildren().size(); i++) {
//                    final Listitem x1 = (Listitem) LsbPracticasXOrden.getChildren().get(i);
//                    x1.setParent(prac);
//                }
//            }
//            prac.setParent(winPracticas);
//            prac.addEventListener("onSelect", new EventListener() {
//
//                @Override
//                public void onEvent(Event event) throws Exception {
//                    Listitem a = prac.getSelectedItem();
//                    posPractica = prac.getSelectedIndex();
//                    Listcell b = (Listcell) a.getChildren().get(0);
//                    String x = b.getLabel().toString();
//                    b = (Listcell) a.getChildren().get(1);
//                    idSeleccion = x;
//                    nombrePractica = b.getLabel().toString();
//                    FiltroBusquedaResultado(x, nombrePractica);
//                    Window aux;
//                    aux = (Window) prac.getParent();
//                    aux.onClose();
//                }
//            });
//        } catch (Exception e) {
//            Messagebox.show(new AdmMensajes(TipoMensaje.ERROR_BUSCAR, e).getMessage(TipoMensaje.ERROR_BUSCAR), "INFORMACION", Messagebox.OK, Messagebox.INFORMATION);
//        }
//    }
//
//    private void CargarOrdenesHistoria(List OrdenesXHistoria) throws InterruptedException {
//        try {
//            if (OrdenesXHistoria.size() > 0) {
//                int x = LsbOrdenesXHistoria.getChildren().size();
//                if (x >= 1) {//si hay datos toca eliminar
//                    while (LsbOrdenesXHistoria.getChildren().size() > 0) {
//                        for (int i = 0; i < LsbOrdenesXHistoria.getChildren().size(); i++) {
//                            LsbOrdenesXHistoria.getChildren().remove(i);
//                        }
//                    }
//                }
//                for (Object objOrdenHistoria : OrdenesXHistoria) {
//                    OrdenA idOrdenLista = (OrdenA) objOrdenHistoria;
//                    Listitem filaOrdenHistoria = new Listitem();
//                    Listcell OrdenA = new Listcell(idOrdenLista.getId().toString());
//                    Date n = new Date();
//                    Listcell OrdenFecha = new Listcell(formato.format(idOrdenLista.getFecIni()));
//                    filaOrdenHistoria.appendChild(OrdenA);
//                    filaOrdenHistoria.appendChild(OrdenFecha);
//                    filaOrdenHistoria.setParent(LsbOrdenesXHistoria);
//                }
//                Window winOrdenes = new Window("Ordenes", "none", true);
//                winOrdenes.setWidth("500px");
//                winOrdenes.setHflex("500px");
//                winOrdenes.setParent(Wrep);
//                winOrdenes.doModal();
//                winOrdenes.addEventListener("onClose", new EventListener() {
//
//                    @Override
//                    public void onEvent(Event event) throws Exception {
//                        if (idSeleccion.isEmpty()) {
//                            alert("No se ha seleccionado nada");
//                        } else {
//                            String v = idSeleccion;
//                            idSeleccion = "";
//                            FiltroBusquedaPracticas(v);
//                        }
//                    }
//                });
//                final Listbox ord = new Listbox();
//                while (LsbOrdenesXHistoria.getChildren().size() > 0) {
//                    for (int i = 0; i < LsbOrdenesXHistoria.getChildren().size(); i++) {
//                        final Listitem x1 = (Listitem) LsbOrdenesXHistoria.getChildren().get(i);
//                        x1.setParent(ord);
//                    }
//                }
//                ord.setParent(winOrdenes);
//                ord.addEventListener("onSelect", new EventListener() {
//
//                    @Override
//                    public void onEvent(Event event) throws Exception {
//                        Listitem a = ord.getSelectedItem();
//                        Listcell b = (Listcell) a.getChildren().get(0);
//                        String x = b.getLabel().toString();
//                        bOrden.setValue(x);
//                        idOrden.setValue(x);
//                        bOrden.setDisabled(true);
//                        bHistorial.setValue(idHistoria.getValue());
//                        bHistorial.setDisabled(true);
//
//                        idSeleccion = x;
//                        FiltroBusquedaPracticas(x);
//                        Window aux;
//                        aux = (Window) ord.getParent();
//                        aux.onClose();
//                    }
//                });
//            } else {
//                Messagebox.show(new AdmMensajes(TipoMensaje.ERROR_BUSCAR_VACIO).getMessage(TipoMensaje.ERROR_BUSCAR_VACIO), "INFORMACION", Messagebox.OK, Messagebox.INFORMATION);
//            }
//        } catch (Exception e) {
//            Messagebox.show(new AdmMensajes(TipoMensaje.ERROR_BUSCAR, e).getMessage(TipoMensaje.ERROR_BUSCAR), "INFORMACION", Messagebox.OK, Messagebox.INFORMATION);
//        }
//    }
//    // </editor-fold>
//
//    private void limpiarFiltroBusqueda() {
//        try {
//            bbPracticas.setValue(null);
//            bbAreas.setValue(null);
//            bHistorial.setValue(null);
//            bHistorial.setDisabled(false);
//            bOrden.setValue(null);
//            bOrden.setDisabled(false);
//        } catch (Exception e) {
//            System.out.print(e.getCause());
//        }
//    }
//
//    private void limpiarDatos() {
//        try {
//            idNombre.setValue(null);
//            idEmpresa.setValue(null);
//            idSexo.setValue(null);
//            idCI.setValue(null);
//            idHistoria.setValue(null);
//            idOrden.setValue(null);
//            idECivil.setValue(null);
//            idEdad.setValue(null);
//            idHistoria.setValue(null);
//            grdDatos.setVisible(false);
//            int x = GrdFilasResultados.getChildren().size();
//            if (x >= 1) {//si hay datos toca eliminar
//                LimpiarGrds(GrdFilasResultados);
//            }
//            GrdReporte.setVisible(false);
//            limpiarFiltroBusqueda();
//        } catch (Exception e) {
//            System.out.print(e.getCause());
//        }
//    }
//
//    public void onClick$btnReset() {
//        reset();
//    }
//
//    public void reset() {
//        try {
//            ListaPracticasxOrden = null;
//            NomPractica.setVisible(false);
//            NomPractica.setValue("");
//            btnBuscar.setDisabled(false);
//            limpiarDatos();
//        } catch (Exception e) {
//            System.out.print(e.getMessage());
//        }
//    }
//
//    public void onClick$btnCancelar() {
//        reset();
//    }
//
//    public void onClick$btnSalir() throws InterruptedException {
//        try {
//            reset();
//            Document newDoc1;
//            newDoc1 = admXml.crearDocumento();
//            newDoc1 = CreamosXML(newDoc1);
//            //Enviar DOCUMENTO
//            admXml.GuardarDocumento(newDoc1, "Resultados.xml");
//            Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.GRABAR), "Informacion", Messagebox.OK, Messagebox.INFORMATION);
//        } catch (Exception ex) {
//                Messagebox.show(new AdmMensajes(TipoMensaje.ERROR_GRABAR, ex).getMessage(TipoMensaje.ERROR_GRABAR), "ERROR", Messagebox.OK, Messagebox.ERROR);
//        }
//
//
//    }
//// </editor-fold>
//    //<editor-fold defaultstate="collapsed" desc="plantilla">
//    Comp asc = new Comp(true), dsc = new Comp(false);
//    Grid GrdReporte;
//    Rows GrdFilasResultados;
//    Column ClmOrden;
//    ManejadoraXml AdmXml = new ManejadoraXml();
//    CreacionXml admXml = new CreacionXml();
//    Document doc1;
//    Button btnGuardarReporte;
//    boolean b = false;
//
//    public void onCreate$Wrep() {
//        if (!(b)) {
//            b = true;
//            crearComboAreas();
//            crearComboPracticas();
//            ClmOrden.setSortAscending(asc);
//            ClmOrden.setSortDescending(dsc);
//        }
//    }
//    Textbox aux;
//    Textbox cuadro;
//    Window WinReporte;
//    int banDatosGenerales = 0;
//
//    private void crearGridReporte() {
//        NodeList listTexFrames, listTexFramDatosGenerales = null;
//        Node NText, NFrame;
//        Element elemento;
//        //verificar q el grid este vacio o vaciar
//        int x = GrdFilasResultados.getChildren().size();
//        if (x >= 1) {//si hay datos toca eliminar
//            while (GrdFilasResultados.getChildren().size() > 0) {
//                for (int i = 0; i
//                        < GrdFilasResultados.getChildren().size(); i++) {
//                    GrdFilasResultados.getChildren().remove(i);
//                }
//            }
//        }
//        List<FormatoXPractica> Formatos = null;
//        try {
//            Formatos = AdmXml.abrirReportes("51");
//            for (int t = 0; t < Formatos.size(); t++) {
//                FormatoXPractica obj = Formatos.get(t);
//                String xml = new String(obj.getXml(),"UTF8");
//
//
//                DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
//                StringReader reader = null;
//                InputSource source = null;
//                reader = new StringReader(xml);
//                source = new InputSource(reader);
//                doc1 = docBuilder.parse(source);
//                NodeList lista = AdmXml.getlistanodos("frame", doc1);
//
//                final Group fila = new Group();
//
//                fila.setLabel("PAGINA" + t + "  ID Formato: " + obj.getId());
//                fila.setStyle("border: 1px double;background-color:#B5B5B5;font-size: 26px;color:#4F4F4F");
//                fila.setOpen(false);
//                fila.setParent(GrdFilasResultados);
//                //de los frames
//                for (int k = 0; k
//                        < lista.getLength(); k++) {
//                    //si es frame cre un grupo y se pasa a crear los textfail
//                    //lista de frame //obtengo el frame 0 q tiene los datos generales
//                    listTexFrames = lista.item(k).getChildNodes();
//                    NFrame = lista.item(k);
//                    //lista de frame o grupos
//                    listTexFrames = crearFrameGrupo(NFrame);
//                    if (banDatosGenerales == 2) {
//                        for (int i = 0; i < listTexFrames.getLength(); i++) {
//                            NText = listTexFrames.item(i);
//                            if (NText.getNodeType() == Node.ELEMENT_NODE) {
//                                elemento = (Element) NText;
//                                if (elemento.getNodeName() != null) {
//                                    AdmTextField(elemento, NText);
//                                }
//                            }
//                        }
//                    } else {
//                        if (banDatosGenerales == 1) {
//                            listTexFramDatosGenerales = lista.item(k).getChildNodes();
//                            CrearGrupoDatos(listTexFramDatosGenerales);
//                        }
//                    }
//                }
//                GrdReporte.setVisible(true);
//            }
//            ClmOrden.onSort();
//        } catch (Exception e) {
//        }
//    }
//
//    private NodeList crearFrameGrupo(Node NFrame) {
//        NodeList listTextFrames = null;
//        NodeList listanodoproperty = null;
//        Group grupo;
//        if (NFrame.getNodeType() == Node.ELEMENT_NODE) {
//            //le hago el nodo elemento
//            Element elemento = (Element) NFrame;
//            if (elemento.getNodeName() != null) {
//                String NomNodo = elemento.getTagName();
//                if (NomNodo.equals("frame")) {
//                    //es un grupo y tomo los textField del grupo
//                    listTextFrames = NFrame.getChildNodes();
//                    if (listTextFrames.getLength() > 0) {
//                        //tomo las propiedades del frame
//                        listanodoproperty = listTextFrames.item(1).getChildNodes();
//                    }
//                    String nomGrupo = null;
//                    for (int j = 0; j
//                            < listanodoproperty.getLength(); j++) {
//                        Node tagitem = listanodoproperty.item(j);
//                        if (tagitem.getNodeType() == Node.ELEMENT_NODE) {
//                            elemento = (Element) tagitem;
//                            NamedNodeMap item_atributos = elemento.getAttributes();
//                            //obtener del xml el atributos show
//                            Node valname = item_atributos.item(0);
//                            Node valvalor = item_atributos.item(1);
//                            String va = valname.getNodeValue();
//                            //NOMBRE DEL GRUPO
//                            if (va.equals("nombre")) {
//                                nomGrupo = valvalor.getNodeValue();
//                            }
//                            if (va.equals("descripcion")) {
//                                String n = valvalor.getNodeValue();
//                                if (n.equals("DATOS GENERALES")) {
//                                    banDatosGenerales = 1;
//                                } else {
//                                    banDatosGenerales = 2;
//                                    grupo = new Group();
//                                    grupo.setLabel(n);
//                                    grupo.setStyle("border: 1px outset;background-color:#CFCFCF;font-size: 22px;color:#4F4F4F;float:center;");
//                                    grupo.setId(nomGrupo);
//                                    grupo.setParent(GrdFilasResultados);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return listTextFrames;
//    }
//
//    void AdmTextField(Element elemento, Node NText) {
//        NodeList listanodoTextFile, listanodoproperty = null;
//        Label LblOrden, LblNombre, LblDescripcion;
//        Node tagitem;
//        NamedNodeMap item_atributos;
//        Row newColumnaFila = null;
//        String NamNText = elemento.getTagName();
//        if (NamNText.equals("textField")) {
//            listanodoTextFile = NText.getChildNodes();
//            LblOrden = new Label();
//            LblNombre = new Label("SN");
//            LblDescripcion = new Label();
//            newColumnaFila = new Row();
//            newColumnaFila.setStyle("border: 0px outset;background-color:##FFFFFF;font-size: 22px;color:#4F4F4F;hover:#2F4F4F");
//            String orden = null;
//            String tipoDato = "T";
//            if (listanodoTextFile.getLength() > 0) {
//                listanodoproperty = listanodoTextFile.item(1).getChildNodes();
//            }  //no hay propiedades
//            int x = listanodoproperty.getLength();
//            LblNombre.setValue("SN");
//            LblNombre.setStyle("color:red;");
//            LblDescripcion.setValue("S/D");
//            LblDescripcion.setStyle("color:red;");
//            if (x == 1) {
//                //si no tiene propiedades
//                orden = "1";
//                LblOrden.setValue(orden);
//                tipoDato = "sinPropiedades";
//            }
//            for (int j = 0; j
//                    < listanodoproperty.getLength(); j++) {
//                tagitem = listanodoproperty.item(j);
//                if (tagitem.getNodeType() == Node.ELEMENT_NODE) {
//                    elemento = (Element) tagitem;
//                    item_atributos = elemento.getAttributes();
//                    //obtener del xml el atributos show
//                    Node valname = item_atributos.item(0);
//                    Node valvalor = item_atributos.item(1);
//                    String va = valname.getNodeValue();
//                    //PROPIEDAD ORDEN
//                    if (va.equals("orden")) {
//                        orden = valvalor.getNodeValue();
//                        LblOrden.setValue(valvalor.getNodeValue());
//                    } else {
//                        //PROPIEDAD NOMBRE
//                        if (va.equals("nombre")) {
//                            LblNombre.setValue(valvalor.getNodeValue());
//                            LblNombre.setStyle("color:#4F4F4F;");
//                        } else {
//                            //PROPIEDAD descripcion
//                            if (va.equals("descripcion")) {
//                                LblDescripcion.setValue(valvalor.getNodeValue().toUpperCase());
//                                LblDescripcion.setStyle("color:#4F4F4F;");
//                            } else {//PROPIEDAD tipo_dato
//                                if (va.equals("tipo_dato")) {
//                                    tipoDato = valvalor.getNodeValue();
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//            //agregar las columnas
//            if (!(orden.equals("0"))) {//no se hace nada se salta
//                newColumnaFila.appendChild(LblDescripcion);
//                //tipo de dato
//                if (tipoDato.equals("T")) {
//                    final Textbox nText = new Textbox();
//                    nText.addEventListener("onDoubleClick", new EventListener() {
//
//                        @Override
//                        public void onEvent(Event event) throws Exception {
//                            String val = nText.getValue();
//                            Window winaux;
//                            winaux = new Window("Edit", "none", true);
//                            winaux.setParent(Wrep);
//                            winaux.doModal();
//                            final Textbox aux;
//                            aux = new Textbox();
//                            aux.setStyle("border: 0px outset;background-color:##FFFFFF;font-size: 22px;color:#4F4F4F;hover:#2F4F4F");
//                            aux.setMultiline(true);
//                            aux.setWidth("450px");
//                            aux.setHeight("320px");
//                            aux.setValue(val);
//                            aux.setParent(winaux);
//                            winaux.addEventListener("onClose", new EventListener() {
//
//                                @Override
//                                public void onEvent(Event event) throws Exception {
//                                    String x = aux.getValue();
//                                    nText.setValue(aux.getValue());
//                                }
//                            });
//                        }
//                    });
//                    nText.setMultiline(true);
//                    nText.setCols(50);
//                    newColumnaFila.appendChild(nText);
//                } else {
//                    if (tipoDato.equals("N")) {
//                        final Decimalbox nDecimal = new Decimalbox();
//                        nDecimal.setCols(20);
//                        newColumnaFila.appendChild(nDecimal);
//                    } else {
//                        if (tipoDato.equals("D")) {
//                            final Datebox nFecha = new Datebox();
//                            nFecha.setCols(16);
//                            nFecha.setMold("rounded");// nFecha.setFormat("yyyy/MM/dd"); //nFecha.setFormat("MM-dd-yy");
//                            newColumnaFila.appendChild(nFecha);
//                        } else {
//                            if (tipoDato.equals("S")) {
//                                Radiogroup grpRadio = new Radiogroup();
//                                Radio lblSi = new Radio("SI");
//                                Radio lblNo = new Radio("NO");
//                                lblSi.setParent(grpRadio);
//                                lblNo.setParent(grpRadio);
//                                newColumnaFila.appendChild(grpRadio);
//                            } else {
//                                if (tipoDato.equals("SP")) {
//                                    Radiogroup grpRadio = new Radiogroup();
//                                    Radio lblSi = new Radio("SI");
//                                    Radio lblNo = new Radio("NO");
//                                    Textbox porq = new Textbox();
//                                    lblSi.setParent(grpRadio);
//                                    lblNo.setParent(grpRadio);
//                                    porq.setParent(grpRadio);
//                                    newColumnaFila.appendChild(grpRadio);
//                                } else {
//                                    final Textbox nText = new Textbox();
//                                    nText.addEventListener("onDoubleClick", new EventListener() {
//
//                                        @Override
//                                        public void onEvent(Event event) throws Exception {
//                                            String val = nText.getValue();
//                                            Window winaux;
//                                            winaux = new Window("Edit", "none", true);
//                                            winaux.setParent(Wrep);
//                                            winaux.doModal();
//                                            final Textbox aux;
//                                            aux = new Textbox();
//                                            aux.setMultiline(true);
//                                            aux.setWidth("350px");
//                                            aux.setHeight("120px");
//                                            aux.setValue(val);
//                                            aux.setParent(winaux);
//                                            winaux.addEventListener("onClose", new EventListener() {
//
//                                                @Override
//                                                public void onEvent(Event event) throws Exception {
//                                                    String x = aux.getValue();
//                                                    nText.setValue(aux.getValue());
//                                                }
//                                            });
//                                        }
//                                    });
//                                    nText.setMultiline(true);
//                                    nText.setCols(50);
//                                    newColumnaFila.appendChild(nText);
//                                }
//                            }
//                        }
//                    }
//                }
//                newColumnaFila.appendChild(LblOrden);
//                newColumnaFila.appendChild(LblNombre);
//                newColumnaFila.setParent(GrdFilasResultados);
//            }
//        }
//    }
//    Rows GrdFilasDatos;
//
//    void CrearGrupoDatos(NodeList listTexFramDatosGenerales) {
//        Row NewFila;
//        Node NText;
//        Element elemento;
//        Label LblOrden, LblNombre, LblDescripcion;
//        NodeList listanodoTextFile, listanodoproperty = null;
//        Node tagitem;
//        NamedNodeMap item_atributos;
//        for (int i = 0; i < listTexFramDatosGenerales.getLength(); i++) {
//
//            NText = listTexFramDatosGenerales.item(i);
//            if (NText.getNodeType() == Node.ELEMENT_NODE) {
//                elemento = (Element) NText;
//                String NamNText = elemento.getTagName();
//                if (NamNText.equals("textField")) {
//                    listanodoTextFile = NText.getChildNodes();
//                    LblOrden = new Label();
//                    LblNombre = new Label("SN");
//                    LblDescripcion = new Label();
//                    String orden = null;
//                    String tipoDato = "T";
//                    NewFila = new Row();
//                    if (listanodoTextFile.getLength() > 0) {
//                        listanodoproperty = listanodoTextFile.item(1).getChildNodes();
//                    }  //no hay propiedades
//                    int x = listanodoproperty.getLength();
//                    LblNombre.setValue("SN");
//                    LblNombre.setStyle("color:red;");
//                    LblDescripcion.setValue("S/D");
//                    LblDescripcion.setStyle("color:red;");
//                    if (x == 1) {
//                        //si no tiene propiedades
//                        orden = "1";
//                        LblOrden.setValue(orden);
//                        tipoDato = "sinPropiedades";
//                    }
//                    for (int j = 0; j < listanodoproperty.getLength(); j++) {
//                        tagitem = listanodoproperty.item(j);
//                        if (tagitem.getNodeType() == Node.ELEMENT_NODE) {
//                            elemento = (Element) tagitem;
//                            item_atributos = elemento.getAttributes();
//                            //obtener del xml el atributos show
//                            Node valname = item_atributos.item(0);
//                            Node valvalor = item_atributos.item(1);
//                            String va = valname.getNodeValue();
//                            //PROPIEDAD ORDEN
//                            if (va.equals("orden")) {
//                                orden = valvalor.getNodeValue();
//                                LblOrden.setValue(valvalor.getNodeValue());
//                            } else {
//                                //PROPIEDAD NOMBRE
//                                if (va.equals("nombre")) {
//                                    LblNombre.setValue(valvalor.getNodeValue());
//                                    LblNombre.setStyle("color:#4F4F4F;float:right;");
//                                } else {
//                                    //PROPIEDAD descripcion
//                                    if (va.equals("descripcion")) {
//                                        LblDescripcion.setValue(valvalor.getNodeValue());
//                                        LblDescripcion.setStyle("color:#4F4F4F;float:right;");
//                                    } else {//PROPIEDAD tipo_dato
//                                        if (va.equals("tipo_dato")) {
//                                            tipoDato = valvalor.getNodeValue();
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    //agregar las columnas
//                    if (!(orden.equals("0"))) {//no se hace nada se salta
//                        NewFila.appendChild(LblDescripcion);
//                        //tipo de dato
//                        if (tipoDato.equals("T")) {
//                            final Textbox nText = new Textbox();
//                            nText.setMultiline(true);
//                            nText.setCols(50);
//                            NewFila.appendChild(nText);
//                        } else {
//                            if (tipoDato.equals("N")) {
//                                final Decimalbox nDecimal = new Decimalbox();
//                                nDecimal.setCols(20);
//                                NewFila.appendChild(nDecimal);
//                            } else {
//                                if (tipoDato.equals("D")) {
//                                    final Datebox nFecha = new Datebox();
//                                    nFecha.setCols(16);
//                                    nFecha.setMold("rounded");// nFecha.setFormat("yyyy/MM/dd"); //nFecha.setFormat("MM-dd-yy");
//                                    NewFila.appendChild(nFecha);
//                                } else {
//                                    if (tipoDato.equals("S")) {
//                                        Radiogroup grpRadio = new Radiogroup();
//                                        Radio lblSi = new Radio("SI");
//                                        Radio lblNo = new Radio("NO");
//                                        lblSi.setParent(grpRadio);
//                                        lblNo.setParent(grpRadio);
//                                        NewFila.appendChild(grpRadio);
//                                    } else {
//                                        if (tipoDato.equals("SP")) {
//                                            Radiogroup grpRadio = new Radiogroup();
//                                            Radio lblSi = new Radio("SI");
//                                            Radio lblNo = new Radio("NO");
//                                            Textbox porq = new Textbox();
//                                            lblSi.setParent(grpRadio);
//                                            lblNo.setParent(grpRadio);
//                                            porq.setParent(grpRadio);
//                                            NewFila.appendChild(grpRadio);
//                                        } else {
//                                            if (tipoDato.equals("L")) {
//                                                final Label nText = new Label();
//                                                nText.setMultiline(true);
//                                                NewFila.appendChild(nText);
//                                            } else {
//                                                final Label nText = new Label();
//                                                nText.setMultiline(true);
//                                                NewFila.appendChild(nText);
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                        NewFila.appendChild(LblOrden);
//                        NewFila.appendChild(LblNombre);
//                        NewFila.setParent(GrdFilasDatos);
//                    }
//                }
//            }
//        }
//    }
//
//    private void cargarGridResultados(XmlResultado resXml) {
//        NodeList listPaginas, listGrupos, listHijosGrupo = null;
//        Label LblOrden, LblNombre, LblDescripcion;
//        Node NItem, NGurpo, NPagina;
//        Group grupo;
//        Row NewFila;
//        boolean estado = false;
//        if (resXml.getEstado().equals("IN")) {
//            estado = true;
//        }
//        if (resXml.getEstado().equals("CO")) {
//            estado = false;
//        }
//        Element elemento;
//        //verificar q el grid este vacio o vaciar
//        int x = GrdFilasResultados.getChildren().size();
//        if (x >= 1) {//si hay datos toca eliminar
//            while (GrdFilasResultados.getChildren().size() > 0) {
//                for (int i = 0; i
//                        < GrdFilasResultados.getChildren().size(); i++) {
//                    GrdFilasResultados.getChildren().remove(i);
//                }
//            }
//        }
//        String xml = resXml.getResultado();
//        doc1 = new ConvertirDocumento().getConvertirDocumentoDocument(xml);
//        NodeList listaHijos = doc1.getChildNodes();
//        listaHijos = listaHijos.item(0).getChildNodes();
//        int xd = listaHijos.getLength();
//        CrearGrupoDatos(listaHijos.item(0).getChildNodes());
//        for (int t = 1; t < listaHijos.getLength(); t++) {
//            NPagina = listaHijos.item(t);
//            if (NPagina.getNodeType() == Node.ELEMENT_NODE) {
//                elemento = (Element) NPagina;
//                if (elemento.getNodeName() != null) {
//                    String NamNText = elemento.getTagName();
//                    NamNText = NamNText + "" + elemento.getAttribute("ID");
//                    final Group fila = new Group();
//                    fila.setLabel(NamNText);
//                    fila.setStyle("background-color:#B5B5B5;font-size: 26px;color:#4F4F4F");
//                    fila.setOpen(false);
//                    fila.setParent(GrdFilasResultados);
//                    listGrupos = NPagina.getChildNodes();
//                    for (int k = 0; k < listGrupos.getLength(); k++) {
//                        NGurpo = listGrupos.item(k);
//                        if (NGurpo.getNodeType() == Node.ELEMENT_NODE) {
//                            elemento = (Element) NGurpo;
//                            if (elemento.getNodeName() != null) {
//                                NamNText = elemento.getTagName();
//                                grupo = new Group();
//                                grupo.setLabel(NamNText);
//                                grupo.setStyle("border: 1px outset;background-color:#CFCFCF;font-size: 22px;color:#4F4F4F;float:center;");
//                                grupo.setParent(GrdFilasResultados);
//                                listHijosGrupo = NGurpo.getChildNodes();
//                                try {
//                                    for (int y = 0; y < listHijosGrupo.getLength(); y++) {
//                                        NItem = listHijosGrupo.item(y);
//                                        if (NItem.getNodeType() == Node.ELEMENT_NODE) {
//                                            elemento = (Element) NItem;
//                                            if (elemento.getNodeName() != null) {
//                                                NamNText = elemento.getTagName();
//                                                String des = elemento.getAttribute("descripcion");
//                                                String tipo = elemento.getAttribute("tipo_dato");
//                                                String orde = elemento.getAttribute("orden");
//                                                String valor = elemento.getTextContent();
//
//                                                LblDescripcion = new Label(des);
//                                                LblDescripcion.setStyle("color:#4F4F4F;");
//                                                LblOrden = new Label();
//                                                LblNombre = new Label("SN");
//                                                LblNombre.setStyle("color:red;");
//                                                NewFila = new Row();
//                                                NewFila.appendChild(LblDescripcion);
//                                                //verifico tipos de datos
//                                                if (tipo.equals("T")) {
//                                                    final Textbox nText = new Textbox(valor);
//                                                    nText.addEventListener("onDoubleClick", new EventListener() {
//
//                                                        @Override
//                                                        public void onEvent(Event event) throws Exception {
//                                                            String val = nText.getValue();
//                                                            Window winaux;
//                                                            winaux = new Window("Edit", "none", true);
//                                                            winaux.setParent(Wrep);
//                                                            winaux.doModal();
//                                                            final Textbox aux;
//                                                            aux = new Textbox();
//                                                            aux.setStyle("border: 0px outset;background-color:##FFFFFF;font-size: 22px;color:#4F4F4F;hover:#2F4F4F");
//                                                            aux.setMultiline(true);
//                                                            aux.setWidth("450px");
//                                                            aux.setHeight("320px");
//                                                            aux.setValue(val);
//                                                            aux.setParent(winaux);
//                                                            winaux.addEventListener("onClose", new EventListener() {
//
//                                                                @Override
//                                                                public void onEvent(Event event) throws Exception {
//                                                                    String x = aux.getValue();
//                                                                    nText.setValue(aux.getValue());
//                                                                }
//                                                            });
//                                                        }
//                                                    });
//                                                    nText.setMultiline(true);
//                                                    nText.setCols(50);
//                                                    if (!(estado)) {
//                                                        nText.setDisabled(true);
//                                                    }
//                                                    NewFila.appendChild(nText);
//                                                } else {
//                                                    if (tipo.equals("N")) {
//                                                        final Decimalbox nDecimal;
//                                                        if (valor.isEmpty()) {
//                                                            nDecimal = new Decimalbox();
//                                                        } else {
//                                                            long xg = Long.parseLong(valor);
//                                                            nDecimal = new Decimalbox(BigDecimal.valueOf(Long.parseLong(valor)));
//                                                        }
//
//                                                        nDecimal.setCols(20);
//                                                        if (!(estado)) {
//                                                            nDecimal.setDisabled(true);
//
//                                                        }
//                                                        NewFila.appendChild(nDecimal);
//                                                    } else {
//                                                        if (tipo.equals("D")) {
//
//                                                            if (!(estado)) {
//                                                                final Label nFecha = new Label(valor);
//                                                                NewFila.appendChild(nFecha);
//                                                            } else {
//                                                                final Datebox nFecha = new Datebox();
//                                                                nFecha.setCols(16);
//                                                                nFecha.setMold("rounded");// nFecha.setFormat("yyyy/MM/dd"); //nFecha.setFormat("MM-dd-yy");
//                                                                NewFila.appendChild(nFecha);
//                                                            }
//                                                        } else {
//                                                            if (tipo.equals("S")) {
//                                                                if (!(estado)) {
//                                                                    final Label nValor = new Label(valor);
//                                                                    NewFila.appendChild(nValor);
//                                                                } else {
//                                                                    Radiogroup grpRadio = new Radiogroup();
//                                                                    Radio lblSi = new Radio("SI");
//                                                                    Radio lblNo = new Radio("NO");
//                                                                    if (valor.equals("SI")) {
//                                                                        lblSi.setChecked(true);
//                                                                    } else {
//                                                                        if (valor.equals("NO")) {
//                                                                            lblNo.setChecked(true);
//                                                                        }
//                                                                    }
//                                                                    lblSi.setParent(grpRadio);
//                                                                    lblNo.setParent(grpRadio);
//                                                                    NewFila.appendChild(grpRadio);
//                                                                }
//                                                            } else {
//                                                                if (tipo.equals("SP")) {
//                                                                    if (!(estado)) {
//                                                                        final Label nValor = new Label(valor);
//                                                                        NewFila.appendChild(nValor);
//                                                                    } else {
//                                                                        Radiogroup grpRadio = new Radiogroup();
//                                                                        Radio lblSi = new Radio("SI");
//                                                                        Radio lblNo = new Radio("NO");
//                                                                        Textbox porq = new Textbox();
//                                                                        lblSi.setParent(grpRadio);
//                                                                        lblNo.setParent(grpRadio);
//                                                                        porq.setParent(grpRadio);
//                                                                        NewFila.appendChild(grpRadio);
//                                                                    }
//
//                                                                } else {
//                                                                    if (!(estado)) {
//                                                                        final Label nValor = new Label(valor);
//                                                                        NewFila.appendChild(nValor);
//                                                                    } else {
//                                                                        final Textbox nText = new Textbox();
//                                                                        nText.addEventListener("onDoubleClick", new EventListener() {
//
//                                                                            @Override
//                                                                            public void onEvent(Event event) throws Exception {
//                                                                                String val = nText.getValue();
//                                                                                Window winaux;
//                                                                                winaux = new Window("Edit", "none", true);
//                                                                                winaux.setParent(Wrep);
//                                                                                winaux.doModal();
//                                                                                final Textbox aux;
//                                                                                aux = new Textbox();
//                                                                                aux.setMultiline(true);
//                                                                                aux.setWidth("350px");
//                                                                                aux.setHeight("120px");
//                                                                                aux.setValue(val);
//                                                                                aux.setParent(winaux);
//                                                                                winaux.addEventListener("onClose", new EventListener() {
//
//                                                                                    @Override
//                                                                                    public void onEvent(Event event) throws Exception {
//                                                                                        String x = aux.getValue();
//                                                                                        nText.setValue(aux.getValue());
//                                                                                    }
//                                                                                });
//                                                                            }
//                                                                        });
//                                                                        nText.setMultiline(true);
//                                                                        nText.setCols(50);
//                                                                        NewFila.appendChild(nText);
//                                                                    }
//
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                                NewFila.appendChild(LblOrden);
//                                                NewFila.appendChild(LblNombre);
//                                                NewFila.setParent(GrdFilasResultados);
//                                            }
//
//
//                                        }
//                                    }
//                                } catch (Exception e) {
//                                    System.out.print(e.getCause().getMessage());
//                                }
//
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//        GrdReporte.setVisible(true);
//        GrdFilasResultados.setVisible(true);
//    }
////    public void onClick$btnGuardarReporte() throws InterruptedException {
////        try {
////            Document newDoc;
////            newDoc = admXml.crearDocumento();
////            newDoc = CreamosXML(newDoc);
////            admXml.GuardarDocumento(newDoc, "Resultados.xml");
////            Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.GRABAR), "Informacion", Messagebox.OK, Messagebox.INFORMATION);
////        } catch (Exception ex) {
////            Messagebox.show(new AdmMensajes(TipoMensaje.ERROR_GRABAR, ex).getMessage(TipoMensaje.ERROR_GRABAR), "ERROR", Messagebox.OK, Messagebox.ERROR);
////        }
////    }
//    XmlResultado resultadoXML;
//
//    private void guardar(String estado) throws InterruptedException {
//
//        try {
//
//            CsUsuarios objUsuarioActivo = (CsUsuarios) admiSessionUsuario.ObtenerAtributoSession(2, session);
//            long x = Long.parseLong(idHistoria.getValue());
//            objHistoria = (Historia) admNegocioHibe.getObjetoxID(Historia.class, x);
//            long y = Long.parseLong(idOrden.getValue());
//            OrdenA ord = (OrdenA) admNegocioHibe.getObjetoxID(OrdenA.class, y);
//            String nop = NomPractica.getValue();
//            NombreP nom = (NombreP) admNegocioHibe.getObjeto("NombreP", "abreviatura", NomPractica.getValue(),false);
////                   Nombre nomMed = (Nombre) admNegocioHibe.getDatosMedico(objUsuarioActivo.getUsuario(),objUsuarioActivo.getCsGrupos().getDesGru());
//            Nombre nomMed = (Nombre) admNegocioHibe.getDatosMedico("CESA", "MED");
////            newDoc = admXml.crearDocumento();
////            newDoc = CreamosXML(newDoc);
//            resultadoXML = new XmlResultado();
//            resultadoXML.setEstado(estado);
//            resultadoXML.setFecha(new Date());
//            resultadoXML.setHistoria(objHistoria);
//            resultadoXML.setMedico(objUsuarioActivo.getNomUsu());
//            resultadoXML.setOrden(ord);
//            resultadoXML.setPractica(nom);
//            resultadoXML.setMedicos(nomMed);
//            resultadoXML.setFecIni(new Date());
//            resultadoXML.setFirstUser(objUsuarioActivo.getUsuario());
//            resultadoXML.setResultado(ConvertirDocumento.getConvertirDocumentoString(newDoc));
//            XmlResultado nuevo = (XmlResultado) admNegocioHibe.guardar(resultadoXML);
//            if (!(nuevo == null)) {
//                reset();
//                btnBuscar.setDisabled(false);
//            }
//            Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.GRABAR), "Informacion", Messagebox.OK, Messagebox.INFORMATION);
//        } catch (Exception e) {
//            Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.ERROR_GRABAR), "Informacion", Messagebox.OK, Messagebox.INFORMATION);
//        } finally {
//        }
//    }
//
//    private Document CreamosDatosGeneralesXML(Document doc) {
//        Element elemento = null;
//        List hijas = GrdFilasDatos.getChildren();
//        Element grpDatos = null;
//        Label etiqueta;
//        String x1 = "datos_generales";
//        grpDatos = doc.createElement(x1); // creamos el elemento raiz
//        doc.getDocumentElement().appendChild(grpDatos); //pegamos la raiz al documento
//        String tipoDato = "";
//        String nombre = "";
//        String descripcion = "";
//        String orden = "";
//        int x = GrdFilasDatos.getChildren().size();
//        for (int j = 0; j < x; j++) {
//            Row fila = (Row) hijas.get(j);
//            String dato = null;
//            if (fila.getChildren().size() > 0) {
//                for (int k = 0; k < fila.getChildren().size(); k++) {
//                    switch (k) {
//                        case 2:
//                            etiqueta = (Label) fila.getChildren().get(k);
//                            orden = etiqueta.getValue(); //creamos un nuevo elemento
//                            //tomo el orden
//                            break;
//                        case 3:
//                            etiqueta = (Label) fila.getChildren().get(k);
//                            nombre = etiqueta.getValue();
//                            elemento = doc.createElement(nombre); //creamos un nuevo elemento
//                            elemento.setAttribute("orden", orden);
//                            elemento.setAttribute("tipo_dato", tipoDato);
//                            elemento.setAttribute("descripcion", descripcion);
//                            elemento.appendChild(doc.createTextNode(dato));
//                            break;
//                        case 0:
//                            etiqueta = (Label) fila.getChildren().get(k);
//                            descripcion = etiqueta.getValue(); //creamos un nuevo elemento
//                            //tomo la descripcion
//                            break;
//                        case 1: //asignar depende del tipo
//                        {
//                            String tipo = fila.getChildren().get(k).toString();
//                            int c = tipo.indexOf(" ");
//                            tipo = tipo.substring(1, c);
//                            if (tipo.equals("Textbox")) {
//                                Textbox valor = (Textbox) fila.getChildren().get(k);
//                                tipoDato = "T";
//                                dato = valor.getText();
//                            } else {
//                                if (tipo.equals("Decimalbox")) {
//                                    Decimalbox valor = (Decimalbox) fila.getChildren().get(k);
//                                    dato = valor.getText();
//                                    tipoDato = "N";
//                                } else {
//                                    if (tipo.equals("Datebox")) {
//                                        Datebox valor = (Datebox) fila.getChildren().get(k);
//                                        dato = valor.getText();
//                                        tipoDato = "D";
//                                    } else {
//                                        if (tipo.equals("Radiogroup")) {
//                                            Radiogroup grpRadio = (Radiogroup) fila.getChildren().get(k);
//                                            int pos = grpRadio.getSelectedIndex();
//                                            if (pos > 0) {
//                                                Radio lblradio = grpRadio.getSelectedItem();
//                                                dato = lblradio.getLabel();
//                                            } else {
//                                                dato = " ";
//                                            }
//                                            tipoDato = "S";
//                                        } else {
//                                            if (tipo.equals("Label")) {
//                                                Label valor = (Label) fila.getChildren().get(k);
//                                                tipoDato = "L";
//                                                dato = valor.getValue();
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                            break;
//                        }
//                    }
//                }
//                grpDatos.appendChild(elemento); //pegamos el elemento hijo a la raiz
//            }
//        }
//        return doc;
//    }
//    boolean Completo;
//
//    private Document CreamosXML(Document doc) {
//        Completo = true;
//        try {
//            Element elemento = null;
//            int con = GrdReporte.getRows().getGroupCount();
//            List hijas = GrdFilasResultados.getGroups();
//            Element Frame = null;
//            Element pag = null;
//            Row fila;
//            Label etiqueta;
//            //creamos DATOS GENERALES XML
//            doc = CreamosDatosGeneralesXML(doc);
//            for (int i = 0; i < con; i++) {
//                Group grp = (Group) hijas.get(i);
//                int x = grp.getItemCount();
//                if (x == 0) {//es pagina creamos en elemento de pagina
//                    String x1 = grp.getLabel().toLowerCase();
//                    Integer c = x1.indexOf(":") + 2;
//                    String x2 = x1.substring(c);
//                    x1 = x1.substring(0, 7);
//                    pag = doc.createElement(x1); // creamos el elemento raiz
//                    pag.setAttribute("Id", x2);
//                    doc.getDocumentElement().appendChild(pag); //pegamos la raiz al documento
//                } else {
//                    String x1 = grp.getId().toLowerCase();
//                    Frame = doc.createElement(x1); // creamos el elemento raiz
//                    pag.appendChild(Frame); //pegamos la raiz al documento
//                }
//                String tipoDato = "";
//                String nombre = "";
//                String descripcion = "";
//                String orden = "";
//                for (int j = 0; j < x; j++) {
//                    fila = (Row) grp.getItems().get(j);
//                    String dato = null;
//                    if (fila.getChildren().size() > 0) {
//                        for (int k = 0; k
//                                < fila.getChildren().size(); k++) {
//                            switch (k) {
//                                case 0:
//                                    etiqueta = (Label) fila.getChildren().get(k);
//                                    descripcion = etiqueta.getValue(); //creamos un nuevo elemento
//                                    //tomo la descripcion
//                                    break;
//                                case 1: //asignar depende del tipo
//                                {
//                                    String tipo = fila.getChildren().get(k).toString();
//                                    int c = tipo.indexOf(" ");
//                                    tipo = tipo.substring(1, c);
//                                    if (tipo.equals("Textbox")) {
//                                        Textbox valor = (Textbox) fila.getChildren().get(k);
//                                        tipoDato = "T";
//                                        dato = valor.getText();
//                                    } else {
//                                        if (tipo.equals("Decimalbox")) {
//                                            Decimalbox valor = (Decimalbox) fila.getChildren().get(k);
//                                            dato = valor.getText();
//                                            tipoDato = "N";
//                                        } else {
//                                            if (tipo.equals("Datebox")) {
//                                                Datebox valor = (Datebox) fila.getChildren().get(k);
//                                                dato = valor.getText();
//                                                tipoDato = "D";
//                                            } else {
//                                                if (tipo.equals("Radiogroup")) {
//                                                    Radiogroup grpRadio = (Radiogroup) fila.getChildren().get(k);
//                                                    int pos = grpRadio.getSelectedIndex();
//                                                    if (pos > 0) {
//                                                        Radio lblradio = grpRadio.getSelectedItem();
//                                                        dato = lblradio.getLabel();
//                                                    } else {
//                                                        dato = " ";
//                                                    }
//                                                    tipoDato = "S";
//                                                }
//                                            }
//                                        }
//                                    }
//                                    break;
//                                }
//                                case 2:
//                                    etiqueta = (Label) fila.getChildren().get(k);
//                                    orden = etiqueta.getValue(); //creamos un nuevo elemento
//                                    //tomo el orden
//                                    break;
//                                case 3:
//                                    etiqueta = (Label) fila.getChildren().get(k);
//                                    nombre = etiqueta.getValue();
//                                    elemento = doc.createElement(nombre); //creamos un nuevo elemento
//                                    elemento.setAttribute("orden", orden);
//                                    elemento.setAttribute("tipo_dato", tipoDato);
//                                    elemento.setAttribute("descripcion", descripcion);
//                                    if (dato.isEmpty()) {
//                                        Completo = false;
//                                    }
//                                    elemento.appendChild(doc.createTextNode(dato));
//                                    break;
//                            }
//                        }
//                        Frame.appendChild(elemento); //pegamos el elemento hijo a la raiz
//                    }
//                }
//            }
//        } catch (Exception e) {
//            System.out.print(e.getMessage());
//        }
//        return doc;
//    }
//// </editor-fold>
//    //<editor-fold defaultstate="collapsed" desc="Datos">
//    Label idNombre;
//    Label idEmpresa;
//    Label idSexo;
//    Label idEdad;
//    Label idCI;
//    Label idHistoria;
//    Label idOrden;
//    Label idECivil;
//    Grid grdDatos;
//
//    private void LimpiarGrds(Rows Nombre) {
//        while (Nombre.getChildren().size() > 0) {
//            for (int i = 0; i
//                    < Nombre.getChildren().size(); i++) {
//                Nombre.getChildren().remove(i);
//
//
//            }
//        }
//        while (TreePadre.getChildren().size() > 0) {
//            for (int i = 0; i
//                    < TreePadre.getChildren().size(); i++) {
//                TreePadre.getChildren().remove(i);
//
//
//            }
//        }
//    }
//
//    private void LimpiarLsbs(Listbox Nombre) {
//    }
//    // </editor-fold>
//    Window Wrep;
//    //<editor-fold defaultstate="collapsed" desc="COMBOBOX">
//    String nombreI = "";
//    AdmiNegocio admNegocioHibe;
//    Bandbox bbPracticas;
//    Listbox LbxPracticas;
//    Bandbox bbAreas;
//    Listbox LbxAreas;
//
//    public void onSelect$LbxPracticas() {
//        try {
//            Listitem objItem;
//            objItem = LbxPracticas.getSelectedItem();
//            List<Component> listcel = objItem.getChildren();
//            Listcell celda = (Listcell) listcel.get(0);
//            Practica = celda.getLabel();
//            bbPracticas.setValue(celda.getLabel());
//            bbPracticas.close();
//
//
//        } catch (Exception e) {
//            System.out.print("Error metodo onSelect$LbxPracticas");
//
//
//        }
//    }
//
//    private void crearComboPracticas() {
//        List<Object> listAux;
//        admNegocioHibe = new AdmiNegocio();
//
//
//        try {
//            if (!(bbPracticas.isVisible())) {
//                listAux = admNegocioHibe.getData(new NombreP(), pageScope, ListaPracticasxOrden, ListaPracticasxOrden) getObjetosOrdenado("NombreP", " descripcion",false);
//
//
//                if (listAux.size() > 0) {
//                    CargarListItemsNombres(listAux);
//
//
//                }
//            }
//        } catch (Exception e) {
//            System.out.print("Error metodo crearComboPracticas");
//
//
//        }
//    }
//
//    public void onChanging$bbPracticas() {
//        List<Object> listAux = null;
//
//
//        boolean bE = false;
//
//
//        try {
//            if (bbPracticas.getValue().length() > 0) {
//                listAux = admNegocioHibe.getObjetosLike("NombreP", "descripcion", bbPracticas.getValue(),false);
//
//
//                if (listAux.size() > 0) {
//                    bE = true;
//
//
//                }
//            }
//        } catch (Exception e) {
//        } finally {
//            if (!(bE)) {
//                try {
//                    listAux = admNegocioHibe.getObjetosOrdenado("NombreP", "desripcion",false);
//                } catch (NamingException ex) {
//                    throw new RuntimeException(ex);
//                }
//            }
//            NombreP objNombre = null;
//            String[] datos = new String[listAux.size()];
//
//
//            for (int i = 0; i
//                    < datos.length; i++) {
//                objNombre = (NombreP) listAux.get(i);
//                datos[i] = objNombre.getDescripcion();
//
//
//            }
//            ListModel datosNombre = new SimpleListModel(datos);
//            LbxPracticas.setModel(datosNombre);
//
//
//        }
//    }
//
//    private void CargarListItemsNombres(List listAux) throws InterruptedException {
//        NombreP objNombre = null;
//        String[] datos = new String[listAux.size()];
//
//
//        try {
//            for (int i = 0; i
//                    < listAux.size(); i++) {
//                objNombre = (NombreP) listAux.get(i);
//                datos[i] = objNombre.getDescripcion();
//
//
//            }
//            ListModel datosNombre = new SimpleListModel(datos);
//            LbxPracticas.setModel(datosNombre);
//            bbPracticas.setVisible(true);
//
//
//        } catch (Exception e) {
//            Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.ERROR_BUSCAR_VACIO), "Informacion", Messagebox.OK, Messagebox.INFORMATION);
//            System.out.print("Error metodo CargarListItemAreas");
//
//
//        }
//    }
//
//    public void onSelect$LbxAreas() {
//        try {
//            Listitem objItem;
//            objItem = LbxAreas.getSelectedItem();
//            List<Component> listcel = objItem.getChildren();
//            Listcell celda = (Listcell) listcel.get(0);
//            Area = celda.getLabel();
//            bbAreas.setValue(celda.getLabel());
//            bbAreas.close();
//
//
//        } catch (Exception e) {
//            System.out.print("Error metodo onSelect$LbxAreas");
//
//
//        }
//    }
//
//    public void onChanging$bbAreas() {
//        try {
//            if (!(bbAreas.getValue().equals(""))) {
//                List<Object> datos = admNegocioHibe.getObjetosLike("Area", "descripcion", bbAreas.getValue(),false);
//
//
//                if (datos.size() > 0) {//hay Datos consultamos listitems de la lista
//                    //hay Datos consultamos listitems de la lista
//                    int x = bbAreas.getChildren().size();
//
//
//                    while (bbAreas.getChildren().size() - 2 > 0) {
//                        for (int i = 0; i
//                                < bbAreas.getChildren().size() - 2; i++) {
//                            bbAreas.getChildren().remove(i);
//
//
//                        }
//                        x = bbAreas.getChildren().size();
//
//
//                    }
//                    CargarListItemAreas(datos);
//
//
//
//                }
//
//            }
//        } catch (NamingException ex) {
//           throw new RuntimeException(ex);
//        } catch (InterruptedException ex) {
//            throw new RuntimeException(ex);
//        }
//
//
//    }
//
//    private void crearComboAreas() {
//        List<Object> listAux;
//        admNegocioHibe = new AdmiNegocio();
//
//
//        try {
//            if (!(bbAreas.isVisible())) {
//                listAux = admNegocioHibe.getObjetosOrdenado("Area", "descripcion",false);
//
//
//                if (listAux.size() > 0) {
//                    CargarListItemAreas(listAux);
//
//
//                }
//            }
//        } catch (Exception e) {
//            System.out.print("Error metodo crearComboAreas");
//
//
//        }
//    }
//
//    private void CargarListItemAreas(List listAux) throws InterruptedException {
//        Area objArea = null;
//        String[] datos = new String[listAux.size()];
//
//
//        try {
//            for (int i = 0; i
//                    < listAux.size(); i++) {
//                objArea = (Area) listAux.get(i);
//                datos[i] = objArea.getDescripcion();
//
//
//            }
//            ListModel datosArea = new SimpleListModel(datos);
//            LbxAreas.setModel(datosArea);
//            bbAreas.setVisible(true);
//
//
//        } catch (Exception e) {
//            Messagebox.show(new AdmMensajes().getMessage(TipoMensaje.ERROR_BUSCAR_VACIO), "Informacion", Messagebox.OK, Messagebox.INFORMATION);
//            System.out.print("Error metodo CargarListItemAreas");
//
//
//        }
//    }
// </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="TREE ADMINOSTRADOR">
//    Treechildren TreePadre;
//    Div GrpArbol;
//    Groupbox GrpCentro;
//
//    private void CrearTree(List listaPracticasHistoria) {
//        try {
//            Treechildren TreeHijoE = new Treechildren();
//            int x=TreePadre.getChildren().size();
//            while (TreePadre.getChildren().size() > 0) {
//                for (int i = 0; i < TreePadre.getChildren().size(); i++) {
//                    TreeHijoE = (Treechildren) TreePadre.getChildren().get(i);
//                     x=TreeHijoE.getChildren().size();
//                    while (TreeHijoE.getChildren().size() > 0) {
//                        for (int j = 0; j < TreeHijoE.getChildren().size(); j++) {
//
//                            TreeHijoE.getChildren().remove(i);
//                        }
//                    }
//                    TreePadre.getChildren().remove(i);
//                }
//            }
//
//            if (!(listaPracticasHistoria.isEmpty())) {
//                for (Object objEpi : listaPracticasHistoria) {
//                    Date objRegFecha = (Date) objEpi;
//                    final Treeitem ItemHijo = new Treeitem();
//                    final Treerow FilaHijo = new Treerow();
//                    final Treecell ValFilaHijo = new Treecell(formato.format(objRegFecha));
//                    ValFilaHijo.setParent(FilaHijo);
//                    FilaHijo.setParent(ItemHijo);
//                    List PraXFecha = admNegocioHibe.getPracticaXFechaHistoria(objRegFecha, idHistoria.getValue());
//                    if (PraXFecha.size() > 0) {//creamos un arbol en el hijo
//                        Treechildren TreeHijo = new Treechildren();
//                        for (Object objPracFecha : PraXFecha) {
//                            final Treeitem ItemNieto = new Treeitem();
//                            XmlResultado objXmlResultado = (XmlResultado) objPracFecha;
//                            final Treerow FilaNieto = new Treerow();
//                            NombreP objPracti=new NombreP();
//                            objPracti=objXmlResultado.getPractica();
//                            final Treecell ValFilaNieto = new Treecell(objPracti.getDescripcion());
//                            ValFilaNieto.setParent(FilaNieto);
//                            FilaNieto.setParent(ItemNieto);
//                            ItemNieto.setParent(TreeHijo);
//                        }
//                        TreeHijo.setParent(ItemHijo);
//                    }
//                    ItemHijo.setParent(TreePadre);
//                }
//            }
//            GrpArbol.setVisible(true);
//        } catch (Exception e) {
//            System.out.print(e.getMessage());
//        }
//    }
    // </editor-fold>
}

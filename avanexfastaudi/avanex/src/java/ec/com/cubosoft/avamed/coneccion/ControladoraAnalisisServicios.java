package ec.com.cubosoft.avamed.coneccion;

//import ec.com.cubosoft.avamed.modelo.core.*;
import ec.com.cubosoft.avamed.modelo.nextla.LisanaPrac;
import ec.com.cubosoft.avamed.modelo.nextla.Lissec;
import ec.com.cubosoft.avamed.modelo.nextla.Listcata;
import ec.com.cubosoft.avamed.modelo.practica.NombreP;
import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import ec.com.cubosoft.avamed.procesos.AdmiNegocioSql;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.*;

/**
 * @author Juan Pablo Chavez
 * @version 1.0.1
 *
 * @author Patricia Amoroso
 * @version 1.0
 */
public class ControladoraAnalisisServicios extends GenericForwardComposer {

    private Button searchButton;
    //busqueda
    private Textbox txtOrden, txtHistoria, txtPaciente;
    Listbox ListDatosCatagolo;
    SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd", new Locale("es", "ES"));
    Window WinIngreso;
    Textbox val;
    Textbox buscar;

    //<editor-fold defaultstate="collapsed" desc="Cargar Pagina">
    public void onCreate$WinHistoria() {
        try {
            cargarSeccion();
            searchButton.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    if (buscar.getValue().length() > 4) {
                        try {
                            AdmiNegocioSql negoSQL = new AdmiNegocioSql();
                            Map<String, Object> wSQL = new HashMap<>();
                            wSQL = new HashMap<>();
                            wSQL.put("activo ?=", 'S');
                            wSQL.put("codSec ?>=", 101);
                            wSQL.put("desAna ?like", "%" + buscar.getValue());
                            List oSQL = new ArrayList<>();
                            oSQL.add("codSec");
                            List objCatalogo = negoSQL.getData(new LisanaPrac(), wSQL, null, oSQL);
                            if (objCatalogo.size() > 0) {
                                reset();
                                loadSession(objCatalogo);
                            } else {
                            }
                        } catch (NamingException ex) {
                            Logger.getLogger(ControladoraAnalisisServicios.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            });
            AdmiNegocioSql negoSQL = new AdmiNegocioSql();
            Map<String, Object> wSQL = new HashMap<>();
            wSQL = new HashMap<>();
            wSQL.put("activo ?=", 'S');
            wSQL.put("codSec ?>=", 101);
            List oSQL = new ArrayList<>();
            oSQL.add("codSec");
            List objCatalogo = negoSQL.getData(new LisanaPrac(), wSQL, null, oSQL);
            loadSession(objCatalogo);
        } catch (NamingException ex) {
            Logger.getLogger(ControladoraAnalisisServicios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarSeccion() {
        try {
            AdmiNegocioSql negoSQL = new AdmiNegocioSql();
            Map<String, Object> wSQL = new HashMap<>();
            wSQL = new HashMap<>();
            List oSQL = new ArrayList<>();
            oSQL.add("codSec");
            List objCatalogo = negoSQL.getData(new Lissec(), wSQL, null, null);
            for (Object object : objCatalogo) {
                Lissec seccion = (Lissec) object;
                Comboitem nu = new Comboitem(seccion.getDesSec());
                nu.setValue(seccion);
            }

        } catch (NamingException ex) {
            Logger.getLogger(ControladoraAnalisisServicios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void loadSession(List objCatalogo) throws NamingException {
        try {
            AdmiNegocioSql negoSQL = new AdmiNegocioSql();
            Map<String, Object> wSQL = new HashMap<>();
            wSQL = new HashMap<>();

            List oSQL = new ArrayList<>();

            for (Object object : objCatalogo) {
                LisanaPrac nuevo = (LisanaPrac) object;
                final Listitem nuevoItem = new Listitem();
                nuevoItem.setValue(nuevo);
                final Listcell codsec = new Listcell(nuevo.getCodSec());
                codsec.setParent(nuevoItem);
                String g = nuevo.getCodAna();
                final Listcell cod = new Listcell(g);
                cod.setParent(nuevoItem);
                final Listcell nombre = new Listcell(nuevo.getDesAna());
                nombre.setParent(nuevoItem);
                //crear lista de servicios del avasus
                final Listcell practicas = new Listcell();
                wSQL = new HashMap<>();
                wSQL.put("codAna ?=", nuevo.getCodAna());
                wSQL.put("tipoS ?like ", "N");
                oSQL = new ArrayList<>();
                List objCata = negoSQL.getData(new Listcata(), wSQL, null, null);
                if (objCata.size() > 1) {
                    final Listbox ListRef = new Listbox();
                    for (Object object1 : objCata) {
                        Listcata objCat = (Listcata) object1;
                        AdmiNegocio nego = new AdmiNegocio();
                        wSQL = new HashMap<>();
                        wSQL.put(" lockReg ?=", 0);
                        wSQL.put("id ?=", objCat.getIdPra());
                        oSQL = new ArrayList<>();
                        oSQL.add("descripcion");
                        List servi = nego.getData(new NombreP(), wSQL, oSQL, null);
                        if (servi.size() == 1) {
                            NombreP ser = (NombreP) servi.get(0);
                            Listitem bjser = new Listitem(ser.getId() + " : " + ser.getDescripcion());
                            bjser.setParent(ListRef);
                        } else {
                            Listitem bjser = new Listitem("S/D Lo:0:NP id: " + objCat.getIdPra() + " :SZ:" + servi.size());
                            bjser.setParent(ListRef);
                        }

                    }
                    ListRef.setParent(practicas);
                } else {
                    AdmiNegocio nego = new AdmiNegocio();
                    wSQL = new HashMap<>();
                    wSQL.put(" lockReg ?=", 0);
                    wSQL.put("id ?=", nuevo.getCodPrac());
                    oSQL = new ArrayList<>();
                    oSQL.add("descripcion");
                    List servi = nego.getData(new NombreP(), wSQL, oSQL, null);
                    final Listbox ListRef = new Listbox();
                    if (servi.size() == 1) {
                        NombreP objprac = (NombreP) servi.get(0);
                        Listitem refere = new Listitem(objprac.getDescripcion());
                        refere.setParent(ListRef);

                    } else {
                        Listitem refere = new Listitem("S/D");
                        refere.setParent(ListRef);
                    }
                    ListRef.setParent(practicas);

                }
                practicas.setParent(nuevoItem);
                final Listcell referencias = new Listcell();
                final Combobox refe = new Combobox();
                refe.setWidth("98%");
                refe.addEventListener(Events.ON_OK, new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        if (refe.getValue().length() > 2) {
                            AdmiNegocio nego = new AdmiNegocio();
                            Map<String, Object> wSQL = new HashMap<>();
                            wSQL.put(" lockReg ?=", 0);
                            wSQL.put(" descripcion ?like", "%" + refe.getValue().toUpperCase());
                            List oSQL = new ArrayList<>();
                            oSQL.add("descripcion");
                            List servic = nego.getData(new NombreP(), wSQL, oSQL, null);
                            for (Object nomb : servic) {
                                NombreP ser = (NombreP) nomb;
                                final Comboitem nu = new Comboitem(ser.getDescripcion());
                                nu.setValue(ser);
                                nu.setParent(refe);
                            }
                            // refe.setOpen(true);
                        }

                    }
                });
                refe.addEventListener(Events.ON_DOUBLE_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        for (int i = refe.getItemCount() - 1; i > -1; i--) {
                            refe.removeChild(refe.getItemAtIndex(i));
                        }
                        refe.setValue("");
                    }
                });
                final Button guar = new Button();
                guar.setLabel("AGREGAR");
                guar.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        LisanaPrac obj = nuevoItem.getValue();
                        Listcata objca = new Listcata();
                        NombreP ser = null;
                        try {
                            ser = refe.getSelectedItem().getValue();
                        } catch (Exception e) {
                            System.out.println(".onEvent()");
                        }
                        objca.setCodAna(obj.getCodAna());
                        objca.setDesPrac(ser.getDescripcion());
                        objca.setIdPra(Long.decode(ser.getId().toString()));
                        objca.setTipoS('N');
                        objca.setId(obj.getCodAna() + "S" + ser.getId());
                        try {
                            objca = (Listcata) negoSQL.guardar(objca);
                            alert("Agregado :" + ser.getDescripcion() + " Cod: " + obj.getDesAna());
                        } catch (Exception e) {
                            System.out.println(".onEvent()");
                        }

                    }
                });
                refe.addEventListener(Events.ON_DOUBLE_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        System.out.println("LIMPIAR" + refe.getValue().length());
                        for (int i = refe.getItemCount() - 1; i > -1; i--) {
                            refe.removeChild(refe.getItemAtIndex(i));
                        }
                        refe.setValue("");
                    }
                });
                refe.setParent(referencias);
                referencias.setParent(nuevoItem);
                final Listcell control = new Listcell();
                guar.setParent(control);
                control.setParent(nuevoItem);
                final Button elim = new Button();
                elim.setLabel("ELIMINAR");
                elim.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        LisanaPrac obj = nuevoItem.getValue();
                        Listcata objca = new Listcata();
                        NombreP ser = null;
                        try {
                            ser = refe.getSelectedItem().getValue();
                        } catch (Exception e) {
                            System.out.println(".onEvent()");
                        }
                        Map<String, Object> wSQ = new HashMap<>();
                        wSQ.put("codAna ?=", obj.getCodAna());
                        wSQ.put("idPra ?= ", ser.getId());
                        wSQ.put("tipoS ?= ", 'N');
                        List objCa = negoSQL.getData(new Listcata(), wSQ, null, null);
                        if (objCa.size() == 1) {
                            Listcata nu = (Listcata) objCa.get(0);
                            negoSQL.eliminar(nu);
                               alert("Eliminado :" + ser.getDescripcion() + " Cod: " + obj.getDesAna());
                    //        System.out.println(".onEvent()");
                        }
                        else
                        {
                            alert("Existe varios registros");
                        }
//                        try {
//                            objca = (Listcata) negoSQL.guardar(objca);
//                        } catch (Exception e) {
//                            System.out.println(".onEvent()");
//                        }

                    }
                });
                final Listcell controle = new Listcell();
                elim.setParent(controle);
                controle.setParent(nuevoItem);
                nuevoItem.setParent(ListDatosCatagolo);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Limpiar Cancer Reset">
//    public void onClick$btnCancelar() {
//        reset();
//    }
    private void reset() {
//        btnBuscar.setDisabled(false);
//        txtPaciente.setDisabled(false);
//        txtHistoria.setDisabled(false);
//        txtOrden.setDisabled(false);
//        limpiar();
        cleanGrid();
    }

    private void cleanGrid() {
        int j=ListDatosCatagolo.getItemCount()-1;
        for (int i = j; i >-1 ; i--) {
            ListDatosCatagolo.removeItemAt(i);
        }
        

    }

    // </editor-fold>
    //<editor-fold defaultstate="collapsed" desc="LLenar datos Pantalla">
    private void blockBusque(boolean bloc) {
        txtOrden.setDisabled(bloc);
        txtHistoria.setDisabled(bloc);
        txtPaciente.setDisabled(bloc);
        searchButton.setDisabled(bloc);
    }

    // </editor-fold>
}

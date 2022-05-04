package ec.com.cubosoft.avamed.coneccion;

//import ec.com.cubosoft.avamed.modelo.core.*;
import ec.com.cubosoft.avamed.modelo.nextla.LisoriEmpr;
import ec.com.cubosoft.avamed.modelo.organizacion.Organizacion;
import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import ec.com.cubosoft.avamed.procesos.AdmiNegocioSql;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class ControladoraEmpFinal extends GenericForwardComposer {

    //busqueda
    Listbox ListDatosCatagolo;
    SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
    Window WinIngreso;
    Textbox buscar;

    //<editor-fold defaultstate="collapsed" desc="Cargar Pagina">
    public void onCreate$WinHistoria() {
        try {
            loadSession();
        } catch (NamingException ex) {
            Logger.getLogger(ControladoraEmpFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadSession() throws NamingException {
        try {
            actualizar(0);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

// </editor-fold>
//<editor-fold defaultstate="collapsed" desc="Limpiar Cancer Reset">
// </editor-fold>
//<editor-fold defaultstate="collapsed" desc="LLenar datos Pantalla">
    public void onOK$buscar() {

        if (buscar.getValue().length() > 2) {
            actualizar(1);
        }
    }

    private void actualizar(int op) {
        try {

            Map<String, Object> wSQL = new HashMap<>();
            AdmiNegocioSql negoSQL = new AdmiNegocioSql();
            wSQL = new HashMap<>();
             wSQL.put("codoriEmpr ?not like ", 'N');
          //  wSQL.put("tipo ?=", 'L');
            if (op == 1) {
                wSQL.put("desOri ?like ", buscar.getValue());
            }
            List oSQL = new ArrayList<>();
            oSQL.add("codoriEmpr");
            List objCatalogo = negoSQL.getData(new LisoriEmpr(), wSQL, null, oSQL);
            
            if (objCatalogo.size() > 0) {
                int numf = ListDatosCatagolo.getItemCount()-1;
                for (int i = numf; i > 0; i--) {
                    ListDatosCatagolo.removeItemAt(i);
                }
            }
            int j = 0;
            for (int i = 0; i < objCatalogo.size(); i++) {
                j = j + 1;
                LisoriEmpr nuevo = (LisoriEmpr) objCatalogo.get(i);
                final Listitem nuevoItem = new Listitem();
                nuevoItem.setValue(nuevo);
                final Listcell cod = new Listcell(nuevo.getCodoriEmpr());
                cod.setParent(nuevoItem);
                final Listcell nombre = new Listcell(nuevo.getDesOri());
                nombre.setParent(nuevoItem);
                final Listcell codEmpre = new Listcell();
                AdmiNegocio nego = new AdmiNegocio();
                Map<String, Object> wSQ = new HashMap<>();
                wSQ = new HashMap<>();
                wSQ.put(" lockReg ?=", 0);
                wSQ.put("id ?=", nuevo.getAux());
                oSQL = new ArrayList<>();
                oSQL.add("abreviatura");
                List empresa = nego.getData(new Organizacion(), wSQ, oSQL, null);
                if (empresa.size() == 1) {

                    Organizacion ser = (Organizacion) empresa.get(0);
                    Label nomEm = new Label(ser.getAbreviatura());
                    nomEm.setParent(codEmpre);
                } else {
                    if (empresa.isEmpty()) {
                        Label nomEm = new Label("S/D");
                        nomEm.setParent(codEmpre);
                    } else {
                        Label nomEm = new Label("S");
                        nomEm.setParent(codEmpre);
                    }
                }
                codEmpre.setParent(nuevoItem);
                final Listcell ComAvasus = new Listcell();
                final Combobox refeAvasus = new Combobox();
                refeAvasus.setParent(ComAvasus);
                refeAvasus.setWidth("98%");
                ComAvasus.setParent(nuevoItem);
                refeAvasus.addEventListener(Events.ON_OK, new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {

                        if (refeAvasus.getValue().length() > 2) {
                            AdmiNegocio nego = new AdmiNegocio();
                            Map<String, Object> wSQ = new HashMap<>();
                            wSQ = new HashMap<>();
                            wSQ.put(" lockReg ?=", 0);
                            wSQ.put("abreviatura ?like", "%" + refeAvasus.getValue().toUpperCase());
                            List oSQL = new ArrayList<>();
                            oSQL.add("abreviatura");
                            List empresa = nego.getData(new Organizacion(), wSQ, oSQL, null);

                            for (Object nomb : empresa) {
                                Organizacion emp = (Organizacion) nomb;
                                Comboitem nu = new Comboitem(emp.getAbreviatura());
                                nu.setValue(emp);
                                nu.setParent(refeAvasus);
                            }
                        }
                    }
                });
                refeAvasus.addEventListener(Events.ON_DOUBLE_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        for (int i = refeAvasus.getItemCount() - 1; i > -1; i--) {
                            refeAvasus.removeChild(refeAvasus.getItemAtIndex(i));
                        }
                        refeAvasus.setValue("");
                    }
                });
                final Listcell control = new Listcell();
                final Button guar = new Button();
                guar.setLabel("Guardar");
                guar.addEventListener(Events.ON_CLICK, new org.zkoss.zk.ui.event.EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        LisoriEmpr obj = nuevoItem.getValue();
                        Organizacion org = refeAvasus.getSelectedItem().getValue();
                        obj.setAux(Long.decode(org.getId().toString()));
                        obj.setTipo('S');
                        obj.setIdNom(org.getId());
                        if (negoSQL.actualizar(obj)) {
                            alert("Actualizado " + obj.getDesOri() + " " + org.getAbreviatura());
                        }

                        guar.setLabel("Actualizado");
                    }
                });
                guar.setParent(control);
                control.setParent(nuevoItem);
                nuevoItem.setParent(ListDatosCatagolo);
                if (j == 400) {
                    i = objCatalogo.size();
               }
            }
        } catch (NamingException ex) {
            Logger.getLogger(ControladoraEmpFinal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

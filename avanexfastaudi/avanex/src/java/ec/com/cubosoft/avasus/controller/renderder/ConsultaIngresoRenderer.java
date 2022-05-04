package ec.com.cubosoft.avasus.controller.renderder;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.ingreso.PracticaXOrden;
import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import ec.com.cubosoft.avamed.procesos.iReport;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

/**
 *
 * @author JP
 */
public class ConsultaIngresoRenderer implements RowRenderer {

    DateFormat fecha = new SimpleDateFormat("dd/MM/yy", new Locale("es", "ES"));
    DateFormat fechaHora = new SimpleDateFormat("dd/MMM/yy/ HH:mm:ss", new Locale("es", "ES"));
    String usuario;
    Window WinIngreso;
    boolean imprimir;
    boolean delet;
    boolean slab;
    static final Map<String, String> ESTADO = new HashMap<String, String>() {
        {
            put("PE", "Pendiente");
            put("CO", "Completo");
            put("IN", "Incompleto");
            put("AU", "Auditado");
            put("IM", "Impreso");
        }
        private static final long serialVersionUID = 1L;
    };

    public ConsultaIngresoRenderer(String usuarioe, Boolean impre, Boolean dell, Window imgre, Boolean sl) {
        usuario = usuarioe;
        imprimir = impre;
        delet = dell;
        WinIngreso = imgre;
        slab = sl;
    }

    @Override
    public void render(Row row, Object data, int index) throws Exception {
        final Orden orden = (Orden) data;
        PracticaXOrden laboratorio;
        row.setValue(orden);
        row.setValign("top");
        new Label(fechaHora.format(orden.getFecIni())).setParent(row);
        new Label(orden.getOrigen().getDescripcion()).setParent(row);

        new Label(String.valueOf(orden.getId())).setParent(row);
        new Label(String.valueOf(orden.getHistoria().getId())).setParent(row);
        final List<PracticaXOrden> practicasXOrden = orden.getPracticaXorden();
        final Label paciente = new Label(orden.getHistoria().getPaciente());
        paciente.setStyle("color: #006400; font-size: 12px; font-weight: bold;");
        paciente.setParent(row);
        if (orden.getOrganizacion() != null) {
            new Label(orden.getOrganizacion().getAbreviatura()).setParent(row);
        } else {
            new Label("(Sin Empresa)").setParent(row);
        }
        Listbox peticiones = new Listbox();
        if (!(practicasXOrden.isEmpty())) {
            Listhead peticionesCab = new Listhead();
            Listheader desCab = new Listheader();
            Listheader imgCab = new Listheader();
            imgCab.setAlign("center");
            imgCab.setWidth("15%");
            desCab.setParent(peticionesCab);
            imgCab.setParent(peticionesCab);
            peticionesCab.setParent(peticiones);
            peticionesCab.setVisible(false);
            Collections.sort(practicasXOrden, new Comparator() {

                @Override
                public int compare(Object o1, Object o2) {
                    PracticaXOrden e1 = (PracticaXOrden) o1;
                    PracticaXOrden e2 = (PracticaXOrden) o2;
                    return e1.getPractica().getDescripcion().compareToIgnoreCase(e2.getPractica().getDescripcion());
                }
            });
            for (PracticaXOrden practica : practicasXOrden) {
                final Listitem peticion = new Listitem();
                peticion.setValue(practica);
                final Listcell desPractica = new Listcell(practica.getPractica() != null ? practica.getPractica().getDescripcion() : "", "");
                Listcell stsPractica = new Listcell();
                Image imageLock;
                String pathImagen = null;
                if (practica.getStsTecnico().equals("IN")) {
                    pathImagen = "/images/editing.png";
                } else if ((practica.getStsTecnico().equals("CO"))) {
                    pathImagen = "/images/lock.png";
                } else {
                    if (practica.getStsTecnico().equals("PE")) {
                        pathImagen = "/images/document_blank.png";
                    } else {
                        if (practica.getStsTecnico().equals("AU")) {
                            pathImagen = "/images/ok.png";
                        } else {
                            if (practica.getStsTecnico().equals("AR")) {
                                pathImagen = "/images/archivado.png";
                            } else {
                                if (practica.getStsTecnico().equals("IM")) {
                                    pathImagen = "/images/pdf.png";
                                }
                            }
                        }
                    }
                }
                imageLock = new Image(pathImagen);
                imageLock.setWidth("20px");
                imageLock.setParent(stsPractica);
                //Celdas Finales para Tooltip Peticion
                final Listcell firstUserP = new Listcell(practica.getFirstUser());
                final Listcell fecIniP = new Listcell(fechaHora.format(practica.getFecIni()));
                final Listcell fecUpdP = new Listcell();
                if (practica.getFecUpd() != null) {
                    fecUpdP.setLabel(fechaHora.format(practica.getFecUpd()));
                }
                desPractica.addEventListener(Events.ON_RIGHT_CLICK, new EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        //ToolTip
                        StringBuilder toolTip = new StringBuilder();
                        if (!firstUserP.getLabel().isEmpty()) {
                            toolTip.append("Creado por: ");
                            toolTip.append(new AdmiNegocio().getUsuario(firstUserP.getLabel()).getNomUsu());
                            toolTip.append(" / Fecha Creación: ").append(fecIniP.getLabel());
                        }
                        if (!fecUpdP.getLabel().isEmpty()) {
                            toolTip.append(" / Modificada: ").append(fecUpdP.getLabel());
                        }
                        desPractica.setTooltiptext(toolTip.toString());
                    }
                });
                peticion.appendChild(desPractica);
                peticion.appendChild(stsPractica);
                peticion.setParent(peticiones);
            }

            if ((imprimir) && ((orden.getOrganizacion().getAbreviatura().equalsIgnoreCase("ECUAAMERICAN")))) {
                row.setVisible(false);
            }
            peticiones.setParent(row);

        } else {
            new Label("(No hay peticiones)").setParent(row);
        }
        if (orden.getmSolicitante() != null) {
            new Label(String.valueOf(orden.getmSolicitante())).setParent(row);
        } else {
            new Label("").setParent(row);
        }
        new Label(orden.getFirstUser()).setParent(row);
        if (orden.getFecUpd() != null) {
            new Label(fechaHora.format(orden.getFecUpd())).setParent(row);
        } else {
            new Label("").setParent(row);
        }

        new Label(String.valueOf(orden.getLockReg())).setParent(row);

    }

    private AMedia loadReportXml(Map<String, Object> wSQL, boolean forceVisible, boolean imprimi) {
        iReport reportes = new iReport();
        try {
            byte[] buf = reportes.getReportXml(wSQL, "r.idOrden", forceVisible, imprimi);

            if (buf != null) {
                //byte[] buf1 = UtilPdf.marcaDeAgua(buf);
                InputStream mediaIS = new ByteArrayInputStream(buf);
                AMedia media = new AMedia("informe.pdf", "pdf", "application/pdf", mediaIS);
                return media;
            } else {
                AMedia media1 = null;
                return media1;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package ec.com.cubosoft.avasus.controller.renderder;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.ingreso.PracticaXOrden;
import ec.com.cubosoft.avamed.modelo.persona.ResultadoGrafico;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import ec.com.cubosoft.avamed.procesos.iReport;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import javax.naming.NamingException;
import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Button;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Window;

/**
 *
 * @author JP
 */
public class OrdenIngresoRendererSinEditar implements RowRenderer {

    DateFormat fecha = new SimpleDateFormat("dd-MMM-yyyy", new Locale("es", "ES"));
    DateFormat fechaHora = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", new Locale("es", "ES"));
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

    public OrdenIngresoRendererSinEditar(String usuarioe, Boolean impre, Boolean dell, Window imgre, Boolean sl) {
        usuario = usuarioe;
        imprimir = impre;
        delet = dell;
        WinIngreso = imgre;
        slab = sl;
    }

    @Override
    public void render(Row row, Object data, int index) throws Exception {
        if (!(slab)) {
            final Orden orden = (Orden) data;
            PracticaXOrden laboratorio;
            row.setValue(orden);

            row.setValign("top");
            new Label(fecha.format(orden.getFecIngreso())).setParent(row);
            new Label(String.valueOf(orden.getId())).setParent(row);
            final List<PracticaXOrden> practicasXOrden = orden.getPracticaXorden();
            final List<XmlResultado> resultadoXorden = orden.getXmlResultados();

            
            
            final Button ob = new Button("ESTADO!!");
            ob.setParent(row);
            ob.addEventListener(Events.ON_CLICK, new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {

                    Window winMensaje = new Window();
                    String windowMessage;
                    windowMessage = "msg_estados.zul";
                    Executions.createComponents(windowMessage, winMensaje, null);
                    final Label msg = new Label();
                    msg.setParent(winMensaje);
                    msg.setVisible(false);
                    Button archivar = (Button) winMensaje.getFellow("idArchivar", false);
                    archivar.addEventListener("onClick", new EventListener() {
                        @Override
                        public void onEvent(Event e) throws Exception {
                            AdmiNegocio admi = new AdmiNegocio();
                            for (Object practi : practicasXOrden) {
                                PracticaXOrden pra = (PracticaXOrden) practi;
                                if (pra.getStsTecnico().equalsIgnoreCase("CO")) {
                                    pra.setStsTecnico("AR");
                                    pra.setFecArch(new Date());
                                    pra.setArchUser(usuario);
                                    admi.actualizar(pra);

                                }
                            }
                            for (Object resultados : resultadoXorden) {
                                XmlResultado obgActu;
                                obgActu = (XmlResultado) resultados;
                                if (obgActu.getEstado().equalsIgnoreCase("CO") && obgActu.getResultado().equalsIgnoreCase("RIS") && !obgActu.getLinck().isEmpty() && !obgActu.getLkImg().isEmpty()) {
                                    Messagebox.show("Visualizar informes de imagen antes de archivar!!");
                                    System.out.println("no abrio ris para archivar");
                                    break;
                                } else {
                                    System.out.println("SI abrio ris para archivar");
                                    if (obgActu.getEstado().equalsIgnoreCase("CO")) {
                                        obgActu.setEstado("AR");
                                        obgActu.setFecArch(new Date());
                                        obgActu.setArchUser(usuario);
                                        admi.actualizar(obgActu);
                                    }
                                }
                            }
//                            orden.setStsTecnico("AR");
//                           admi.actualizar(orden);
                            Window aux;
                            aux = (Window) msg.getParent();
                            aux.onClose();
                        }
                    });
                    Button auditar = (Button) winMensaje.getFellow("idAuditar", false);
                    auditar.addEventListener(
                            "onClick", new EventListener() {
                        @Override
                        public void onEvent(Event e) throws Exception {

                            AdmiNegocio admi = new AdmiNegocio();
                            for (Object practi : practicasXOrden) {
                                PracticaXOrden pra = (PracticaXOrden) practi;

                                if ((pra.getStsTecnico().equalsIgnoreCase("CO")) || (pra.getStsTecnico().equalsIgnoreCase("AR"))) {
                                    pra.setStsTecnico("AU");
                                    pra.setFechaAudit(new Date());
                                    pra.setAuditUser(usuario);
                                    admi.actualizar(pra);
                                }
                            }
                            for (Object resul : resultadoXorden) {
                                XmlResultado obgActu = new XmlResultado();
                                obgActu = (XmlResultado) resul;
                                if (obgActu.getEstado().equalsIgnoreCase("CO") && obgActu.getResultado().equalsIgnoreCase("RIS") && !obgActu.getLinck().isEmpty() && !obgActu.getLkImg().isEmpty()) {
                                    Messagebox.show("Visualizar informes de imagen antes de auditar!!");
                                    System.out.println("no abrio ris para auditar");
                                    break;
                                } else {

                                    System.out.println("SI abrio ris para auditar");
                                    if ((obgActu.getEstado().equalsIgnoreCase("CO")) || (obgActu.getEstado().equalsIgnoreCase("AR"))) {
                                        obgActu.setEstado("AU");
                                        obgActu.setFechaAudit(new Date());
                                        obgActu.setAuditUser(usuario);
                                        admi.actualizar(obgActu);
                                    }
                                }
                            }
//                          orden.setStsTecnico("AU");
//                          admi.actualizar(orden);
                            Window aux;
                            aux = (Window) msg.getParent();
                            aux.onClose();
                        }
                    }
                    );
                    winMensaje.setId(
                            "winMsgEstado");
                    winMensaje.setParent(WinIngreso);

                    winMensaje.doModal();

                    ob.setDisabled(
                            true);
                    ob.setLabel(
                            "OK");
                }
            });                                
            new Label(orden.getHistoria().getPaciente()).setParent(row);

            if (orden.getOrganizacion()
                    != null) {
                new Label(orden.getOrganizacion().getAbreviatura()).setParent(row);
            } else {
                new Label("(Sin Empresa)").setParent(row);
            }
            Listbox peticiones = new Listbox();

            int peticionesCompletas = 0;

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
                    Listcell audito = new Listcell();
                    final Button audiotoria = new Button();
                    final Button audiotoriaPdf = new Button();                    
                    audiotoria.setDisabled(false);
                    audiotoriaPdf.setDisabled(true);
                    audiotoria.setParent(audito);
                    audiotoria.setParent(audito);
                    String pathImagen = null;
                    if (practica.getStsTecnico().equals("IN")) {
                        pathImagen = "/images/editing.png";
                        audiotoria.setVisible(false);
                    } else if ((practica.getStsTecnico().equals("CO"))) {
                        pathImagen = "/images/lock.png";
                        audiotoria.setLabel("ARCHIVAR?");
                        audiotoria.setVisible(true);
                        audiotoria.setDisabled(false);
                        audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
                            @Override
                            public void onEvent(Event event) throws Exception {
                                Messagebox.show("Confirma que el informe fue archivado?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
                                    public void onEvent(Event evt) throws InterruptedException, NamingException {
                                        if (evt.getName().equals("onOK")) {
                                            audiotoria.setDisabled(true);
                                            audiotoria.setLabel("ARCHIVADO");
                                            audiotoria.setStyle("border: 2px #B22222 solid;");
                                            PracticaXOrden actualizar = peticion.getValue();
                                            actualizar.setStsTecnico("AR");
                                            //arreglar
                                            actualizar.setFecArch(new Date());
                                            actualizar.setArchUser(usuario);
                                            AdmiNegocio admi = new AdmiNegocio();
                                            admi.actualizar(actualizar);
                                            for (Object resul : resultadoXorden) {
                                                XmlResultado obgActu = new XmlResultado();
                                                obgActu = (XmlResultado) resul;
                                                if (obgActu.getPractica().getId() == actualizar.getPractica().getId()) {
                                                    if ((obgActu.getEstado().equalsIgnoreCase("CO"))) {
                                                        obgActu.setEstado("AR");
                                                        obgActu.setFecArch(new Date());
                                                        obgActu.setArchUser(usuario);
                                                        admi.actualizar(obgActu);
                                                    }
                                                } else {
                                                }
                                            }
                                        } else if (evt.getName().equals("onCancel")) {
                                        }
                                    }
                                }
                                );
                            }
                        });
                        peticionesCompletas++;
                    } else {
                        if (practica.getStsTecnico().equals("PE")) {
                            pathImagen = "/images/document_blank.png";
                            audiotoria.setVisible(false);
                        } else {
                            if (practica.getStsTecnico().equals("AU")) {
                                pathImagen = "/images/ok.png";
                                audiotoria.setLabel("AUDITADO");
                                audiotoria.setStyle("border: 2px #006400 solid;");
                            } else {
                                if (practica.getStsTecnico().equals("AR")) {
                                    pathImagen = "/images/archivado.png";
                                    audiotoria.setLabel("AUDITAR?");
                                    audiotoria.setVisible(true);
                                    audiotoria.setDisabled(false);
                                    audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
                                        @Override
                                        public void onEvent(Event event) throws Exception {
                                            Messagebox.show("Confirma que el informe fue auditado?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
                                                public void onEvent(Event evt) throws InterruptedException, NamingException {
                                                    if (evt.getName().equals("onOK")) {
                                                        audiotoria.setDisabled(true);
                                                        audiotoria.setLabel("AUDITADO");
                                                        audiotoria.setStyle("border: 2px #B22222 solid;");
                                                        PracticaXOrden actualizar = peticion.getValue();
                                                        actualizar.setStsTecnico("AU");
                                                        actualizar.setFechaAudit(new Date());
                                                        actualizar.setAuditUser(usuario);
                                                        AdmiNegocio admi = new AdmiNegocio();
                                                        admi.actualizar(actualizar);
                                                        for (Object resul : resultadoXorden) {
                                                            XmlResultado obgActu = new XmlResultado();
                                                            obgActu = (XmlResultado) resul;
                                                            if (obgActu.getPractica().getId() == actualizar.getPractica().getId()) {
                                                                if ((obgActu.getEstado().equalsIgnoreCase("CO")) || (obgActu.getEstado().equalsIgnoreCase("AR"))) {
                                                                    obgActu.setEstado("AU");
                                                                    obgActu.setFecArch(new Date());
                                                                    obgActu.setArchUser(usuario);
                                                                    admi.actualizar(obgActu);
                                                                }
                                                            }
                                                        }
                                                    } else if (evt.getName().equals("onCancel")) {
//                                        Messagebox.show("Ignore Save", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
                                                    }
                                                }
                                            });
                                        }
                                    });
                                    peticionesCompletas++;
                                } else {
                                    if (practica.getStsTecnico().equals("IM")) {
                                        pathImagen = "/images/pdf.png";
                                        audiotoria.setLabel("ELIMINAR?");
                                        audiotoria.setVisible(true);
                                        if (delet) {
                                            audiotoria.setDisabled(false);
                                        }
                                        audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
                                            @Override
                                            public void onEvent(Event event) throws Exception {
                                                Messagebox.show("Seguro desea borrar el registro?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
                                                    public void onEvent(Event evt) throws InterruptedException, NamingException {
                                                        if (evt.getName().equals("onOK")) {
                                                            audiotoria.setDisabled(true);
                                                            audiotoria.setLabel("ELIMINADO");
                                                            audiotoria.setStyle("border: 2px #B22222 solid;");
                                                            PracticaXOrden actualizar = ((Listitem) audiotoria.getParent().getParent()).getValue();
                                                            actualizar.setStsTecnico("PE");
                                                            actualizar.setFechaAudit(new Date());
                                                            actualizar.setAuditUser(usuario);
                                                            AdmiNegocio admi = new AdmiNegocio();
                                                            admi.actualizar(actualizar);
                                                            Long idORde = actualizar.getOrden().getId();
                                                            Integer idPract = actualizar.getPractica().getId();
                                                            for (Object resul : resultadoXorden) {
                                                                XmlResultado obgActu = new XmlResultado();
                                                                obgActu = (XmlResultado) resul;
                                                                Long idORdeRef = obgActu.getIdOrden().longValue();
                                                                Integer idPracRef = obgActu.getPractica().getId();
                                                                if (Objects.equals(idPracRef, idPract) && Objects.equals(idORde, idORdeRef)) {
                                                                    Map<String, Object> wSQL = new HashMap<String, Object>();
                                                                    wSQL.put("idXmlResultado ?=", obgActu.getId());
                                                                    List objectList = admi.getData(new ResultadoGrafico(), wSQL, null, null);
                                                                    if (objectList.size() == 1) {
                                                                        ResultadoGrafico obgActugr = (ResultadoGrafico) objectList.get(0);
                                                                        if (Objects.equals(obgActugr.getIdXmlResultado(), obgActu.getId())) {
                                                                            System.out.println("Se elimina el registro " + obgActugr.getId() + " " + obgActugr.getIdXmlResultado());
                                                                            admi.eliminar(obgActugr);
                                                                        }
                                                                    }
                                                                    admi.eliminar(obgActu);
                                                                }
                                                            }

                                                        } else if (evt.getName().equals("onCancel")) {
                                                        }
                                                    }
                                                }
                                                );

                                            }

                                        });
                                        peticionesCompletas++;
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
//              Fin ToolTip
                        }
                    });

                    desPractica.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
//IMPRIMIR PRACTICA
                        @Override
                        public void onEvent(Event event) throws Exception {
                            Window winMensaje = new Window();
                            String windowMessage = "msg_informe.zul";
                            Executions.createComponents(windowMessage, winMensaje, null);
                            winMensaje.setBorder("normal");
                            winMensaje.setClosable(true);
                            winMensaje.setTitle("Vista Preliminar de Informe");
                            final Iframe frameReporte = (Iframe) winMensaje.getFellow("reporteV", true);

                            PracticaXOrden practica;

                            practica = ((Listitem) desPractica.getParent()).getValue();
                            Map<String, Object> wSQL = new HashMap<String, Object>();
                            wSQL.put("idOrden", practica.getOrden().getId());
                            wSQL.put("idPractica", practica.getPractica().getId());
                            AMedia mediaCarga = null;
                            try {
                                mediaCarga = loadReportXml(wSQL, true, imprimir);
                            } catch (Exception e) {
                                Messagebox.show("No se han encontrado informes para registro seleccionado");
                            }

                            if (mediaCarga != null) {
                                frameReporte.setContent(mediaCarga);
                                Component parent = new Window();
                                //Lectura del componente del Window Principal
                                Collection<Component> comps = Executions.getCurrent().getDesktop().getComponents();
                                for (Component c : comps) {
                                    if (c.getDefinition().getName().equals("window")) {
                                        parent = c;
                                    }
                                }
                                winMensaje.setParent(parent);
                                winMensaje.doModal();
                            } else {
                               // Messagebox.show("No se han encontrado, verifique en la pantalla Informe/s");
                                Messagebox.show("El informe no se encuentra liberado");
                            }
                        }
                    });

                    peticion.appendChild(desPractica);
                    peticion.appendChild(stsPractica);
                    peticion.appendChild(audito);
                    peticion.setParent(peticiones);
                }
                if ((imprimir) && ((orden.getOrganizacion().getAbreviatura().equalsIgnoreCase("ECUAAMERICAN")))) {
                    row.setVisible(false);
                }
                peticiones.setParent(row);

            } else {
                new Label("(No hay peticiones)").setParent(row);
            }

            //
            if (orden.getStsTecnico()
                    .equals("CO")) {       //Si la orden esta completa
                Image imageEstadoTecnico;
                imageEstadoTecnico = new Image("/images/status/orden-completed.png");
                imageEstadoTecnico.setWidth("64px");
                imageEstadoTecnico.setParent(row);

            } else {        //Si la orden no esta completa
                Vlayout estadoGroup = new Vlayout();
//            Progressmeter barraEstado = new Progressmeter();
                Label estadoOs = new Label(ESTADO.get(orden.getStsTecnico()));
                estadoOs.setParent(estadoGroup);
                estadoGroup.setParent(row);
            }
            final Label usuario = new Label(orden.getFirstUser());
            final Listcell firstUser = new Listcell(orden.getFirstUser());
            final Listcell fecIni = new Listcell(fechaHora.format(orden.getFecIni()));
            final Listcell fecUpd = new Listcell();

            if (orden.getFecUpd()
                    != null) {
                fecUpd.setLabel(fechaHora.format(orden.getFecUpd()));
            }

            usuario.addEventListener(Events.ON_CLICK,
                    new EventListener() {

                @Override
                public void onEvent(Event event) throws Exception {
                    //ToolTip
                    StringBuilder toolTip = new StringBuilder();
                    if (!firstUser.getLabel().isEmpty()) {
                        toolTip.append("Creado por: ");
                        toolTip.append(new AdmiNegocio().getUsuario(firstUser.getLabel()).getNomUsu());
                        toolTip.append(" / Fecha Creación: ").append(fecIni.getLabel());
                    }

                    if (!fecUpd.getLabel().isEmpty()) {
                        toolTip.append(" / Modificada: ").append(fecUpd.getLabel());
                    }
                    usuario.setTooltiptext(toolTip.toString());
//              Fin ToolTip
                }
            }
            );

            usuario.setParent(row);
            //Fin de usuario

            new Label(String.valueOf(orden.getOrigen().getDescripcion())).setParent(row);
        } else {
            //desde aqui solo lab
            System.out.println("Solo laboratorio");
            XmlResultado ped = (XmlResultado) data;
            row.setValue(ped);
            row.setValign("top");
            new Label(fecha.format(ped.getFecha())).setParent(row);
            new Label(String.valueOf(ped.getId())).setParent(row);
            final Button ob = new Button("ESTADO!!");
            ob.setParent(row);
            ob.setDisabled(true);
            new Label(ped.getHistoria().getPaciente()).setParent(row);

            if (ped.getEmpresa() != null) {
                new Label(ped.getEmpresa()).setParent(row);
            } else {
                new Label("(Sin Empresa)").setParent(row);
            }
            Listbox peticiones = new Listbox();
            if ((ped != null)) {

                Listhead peticionesCab = new Listhead();
                Listheader desCab = new Listheader();
                Listheader imgCab = new Listheader();

                imgCab.setAlign("center");
                imgCab.setWidth("15%");

                desCab.setParent(peticionesCab);
                imgCab.setParent(peticionesCab);

                peticionesCab.setParent(peticiones);
                peticionesCab.setVisible(false);

                final Listitem peticion = new Listitem();
                peticion.setValue(ped);
                final Listcell desPractica = new Listcell(ped.getPractica() != null ? ped.getPractica().getDescripcion() : "", "");
                Listcell stsPractica = new Listcell();
                Image imageLock;
                Listcell audito = new Listcell();
                final Button audiotoria = new Button();
                audiotoria.setDisabled(true);
                audiotoria.setParent(audito);
                String pathImagen = null;
//                    NombreP  practica=ped.getPractica();
                if (ped.getEstado().equals("IN")) {
                    pathImagen = "/images/editing.png";
                    audiotoria.setVisible(false);
                } else if (ped.getEstado().equals("CO")) {
                    pathImagen = "/images/lock.png";
                    audiotoria.setLabel("ARCHIVAR?");
                    audiotoria.setVisible(true);
                    audiotoria.setDisabled(false);
                    audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
                        @Override
                        public void onEvent(Event event) throws Exception {
                            Messagebox.show("Confirma que el informe fue archivado?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
                                public void onEvent(Event evt) throws InterruptedException, NamingException {
                                    if (evt.getName().equals("onOK")) {
                                        audiotoria.setDisabled(true);
                                        audiotoria.setLabel("ARCHIVADO");
                                        audiotoria.setStyle("border: 2px #B22222 solid;");
                                        XmlResultado actualizar = peticion.getValue();
                                        actualizar.setEstado("AR");
                                        actualizar.setFecArch(new Date());
                                        actualizar.setArchUser(usuario);
                                        AdmiNegocio admi = new AdmiNegocio();
                                        admi.actualizar(actualizar);
                                    } else if (evt.getName().equals("onCancel")) {
//                                        Messagebox.show("Ignore Save", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
                                    }
                                }
                            });
                        }
                    });
//                    peticionesCompletas++;
                } else {
                    if (ped.getEstado().equals("PE")) {
                        pathImagen = "/images/document_blank.png";
                        audiotoria.setVisible(false);
                    } else {
                        if (ped.getEstado().equals("AU")) {
                            pathImagen = "/images/ok.png";
                            audiotoria.setLabel("AUDITADO");
                            audiotoria.setStyle("border: 2px #006400 solid;");
                        } else {
                            if (ped.getEstado().equals("AR")) {
                                pathImagen = "/images/archivado.png";
                                audiotoria.setLabel("AUDITAR?");
                                audiotoria.setVisible(true);
                                audiotoria.setDisabled(false);
                                audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
                                    @Override
                                    public void onEvent(Event event) throws Exception {
                                        Messagebox.show("Confirma que el informe fue auditado?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
                                            public void onEvent(Event evt) throws InterruptedException, NamingException {
                                                if (evt.getName().equals("onOK")) {
                                                    audiotoria.setDisabled(true);
                                                    audiotoria.setLabel("AUDITADO");
                                                    audiotoria.setStyle("border: 2px #B22222 solid;");
                                                    XmlResultado actualizar = peticion.getValue();
                                                    actualizar.setEstado("AU");
                                                    actualizar.setFechaAudit(new Date());
                                                    actualizar.setAuditUser(usuario);
                                                    AdmiNegocio admi = new AdmiNegocio();
                                                    admi.actualizar(actualizar);
                                                } else if (evt.getName().equals("onCancel")) {
//                                        Messagebox.show("Ignore Save", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
                                                }
                                            }
                                        });
                                    }
                                });
//                                peticionesCompletas++;
                            }
                        }
                    }
                }

                imageLock = new Image(pathImagen);
                imageLock.setWidth("20px");
                imageLock.setParent(stsPractica);
                //Celdas Finales para Tooltip Peticion
                final Listcell firstUserP = new Listcell(ped.getFirstUser());
                final Listcell fecIniP = new Listcell(fechaHora.format(ped.getFecIni()));
                final Listcell fecUpdP = new Listcell();
                if (ped.getFecUpd() != null) {
                    fecUpdP.setLabel(fechaHora.format(ped.getFecUpd()));
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
//              Fin ToolTip
                    }
                });
//IMPRIMIR PRACTICA
                desPractica.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {

                    @Override
                    public void onEvent(Event event) throws Exception {
                        Window winMensaje = new Window();
                        String windowMessage = "msg_informe.zul";
                        Executions.createComponents(windowMessage, winMensaje, null);
                        winMensaje.setBorder("normal");
                        winMensaje.setClosable(true);
                        winMensaje.setTitle("Vista Preliminar de Informe");
                        final Iframe frameReporte = (Iframe) winMensaje.getFellow("reporteV", true);
                        XmlResultado practica;
                        practica = ((Listitem) desPractica.getParent()).getValue();
                        Map<String, Object> wSQL = new HashMap<String, Object>();
                        wSQL.put("orden.id", practica.getOrden().getId());
                        wSQL.put("practica.id", practica.getPractica().getId());
                        AMedia mediaCarga = null;
                        try {
                            mediaCarga = loadReportXml(wSQL, true, imprimir);
                        } catch (Exception e) {
                            Messagebox.show("No se han encontrado informes para registro seleccionado");
                        }
                        if (mediaCarga != null) {
                            frameReporte.setContent(mediaCarga);
                            Component parent = new Window();
                            //Lectura del componente del Window Principal
                            Collection<Component> comps = Executions.getCurrent().getDesktop().getComponents();
                            for (Component c : comps) {
                                if (c.getDefinition().getName().equals("window")) {
                                    parent = c;
                                }
                            }
                            winMensaje.setParent(parent);
                            winMensaje.doModal();
                        } else {
                            Messagebox.show("No se han encontrado informes para registro seleccionado");
                        }
                    }
                });
                peticion.appendChild(desPractica);
                peticion.appendChild(stsPractica);
                peticion.appendChild(audito);
                peticion.setParent(peticiones);
                peticiones.setParent(row);
            } else {
                new Label("(No hay peticiones)").setParent(row);
            }
            //
            if (ped.getEstado().equals("CO")) {       //Si la orden esta completa
                Image imageEstadoTecnico;
                imageEstadoTecnico = new Image("/images/status/orden-completed.png");
                imageEstadoTecnico.setWidth("64px");
                imageEstadoTecnico.setParent(row);

            } else {        //Si la orden no esta completa
                Vlayout estadoGroup = new Vlayout();
//            Progressmeter barraEstado = new Progressmeter();
                Label estadoOs = new Label(ESTADO.get(ped.getEstado()));
                estadoOs.setParent(estadoGroup);
                estadoGroup.setParent(row);
            }

            //new Label(ESTADO.get(orden.getStsTecnico())).setParent(row);
            //Usuario
            final Label usuario = new Label(ped.getFirstUser());
//        usuario.setTooltiptext(new AdmiNegocio().getUsuario(orden.getFirstUser()).getNomUsu());

            //Celdas Finales para Tooltip Orden
            final Listcell firstUser = new Listcell(ped.getFirstUser());
            final Listcell fecIni = new Listcell(fechaHora.format(ped.getFecIni()));
            final Listcell fecUpd = new Listcell();
            if (ped.getFecUpd() != null) {
                fecUpd.setLabel(fechaHora.format(ped.getFecUpd()));
            }

            usuario.addEventListener(Events.ON_CLICK, new EventListener() {

                @Override
                public void onEvent(Event event) throws Exception {
                    //ToolTip
                    StringBuilder toolTip = new StringBuilder();
                    if (!firstUser.getLabel().isEmpty()) {
                        toolTip.append("Creado por: ");
                        toolTip.append(new AdmiNegocio().getUsuario(firstUser.getLabel()).getNomUsu());
                        toolTip.append(" / Fecha Creación: ").append(fecIni.getLabel());
                    }

                    if (!fecUpd.getLabel().isEmpty()) {
                        toolTip.append(" / Modificada: ").append(fecUpd.getLabel());
                    }
                    usuario.setTooltiptext(toolTip.toString());
//              Fin ToolTip
                }
            });

            usuario.setParent(row);
            //Fin de usuario

            new Label(String.valueOf("Lab")).setParent(row);
        }
    }

    private AMedia loadReportXml(Map<String, Object> wSQL, boolean forceVisible, boolean imprimi) {
        iReport reportes = new iReport();
        try {
            byte[] buf = null;
            try {
                buf = reportes.getReportXml(wSQL, "r.idOrden", forceVisible, imprimi);
            } catch (Exception e) {
                System.out.println("ec.com.cubosoft.avamed.coneccion.ControladoraInformes.loadReportVista()");
            }
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

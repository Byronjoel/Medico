package ec.com.cubosoft.avasus.controller.renderder;

import ec.com.cubosoft.avamed.modelo.core.Perxuser;
import ec.com.cubosoft.avamed.modelo.nextla.SUsuar;
import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import ec.com.cubosoft.avamed.procesos.iReport;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.zkoss.util.media.AMedia;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Window;

public class ConsultaResultadosRenderer implements RowRenderer {

    DateFormat fecha = new SimpleDateFormat("dd-MMM-yyyy", new Locale("es", "ES"));
    DateFormat fechaHora = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss", new Locale("es", "ES"));
    //CsUsuarios usuario;
    SUsuar usuario;
    // sessionOk usuario;
    Window WinConsulta;
    boolean imprimir;
    boolean slab;

    Perxuser permisosxUsuarioN;

    List resultadoXorden;
    AdmiNegocio admNegocio;
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

    public ConsultaResultadosRenderer(SUsuar usuarioN, boolean impre, Window WinConsult, boolean b, Perxuser permisosxUsuario) {
        usuario = usuarioN;
        imprimir = impre;
        WinConsulta = WinConsult;
        slab = b;
        permisosxUsuarioN = permisosxUsuario;
    }

    @Override
    public void render(Row row, Object data, int index) throws Exception {
//        if (!(slab)) {
//            final Orden ordenResul = (Orden) data;
//            PracticaXOrden laboratorio;
//            row.setValue(ordenResul);
//
//            row.setValign("top");
//            new Label(fecha.format(ordenResul.getFecIngreso())).setParent(row);
//            new Label(String.valueOf(ordenResul.getId())).setParent(row);
//            //XMLRESULTADO
//            admNegocio = new AdmiNegocio();
//            new Label(ordenResul.getHistoria().getPaciente()).setParent(row);
//
//            if (ordenResul.getOrganizacion() != null) {
//                new Label(ordenResul.getOrganizacion().getAbreviatura()).setParent(row);
//            } else {
//                new Label("(Sin Empresa)").setParent(row);
//            }
//            Listbox peticiones = new Listbox();
//
//            int peticionesCompletas = 0;
//            if (!(ordenResul!=null)) {
//                Listhead peticionesCab = new Listhead();
//                Listheader desCab = new Listheader();
//                Listheader imgCab = new Listheader();
//                imgCab.setAlign("center");
//                imgCab.setWidth("15%");
//                desCab.setParent(peticionesCab);
//                imgCab.setParent(peticionesCab);
//                peticionesCab.setParent(peticiones);
//                peticionesCab.setVisible(false);
////                Collections.sort(practicasXOrden, new Comparator() {
////
////                    @Override
////                    public int compare(Object o1, Object o2) {
////                        PracticaXOrden e1 = (PracticaXOrden) o1;
////                        PracticaXOrden e2 = (PracticaXOrden) o2;
////                        return e1.getPractica().getDescripcion().compareToIgnoreCase(e2.getPractica().getDescripcion());
////                    }
////                });
//                for (Resultado practica : ordenResul) {
//                    final Listitem peticion = new Listitem();
//                    peticion.setValue(practica);
//                    final Listcell desPractica = new Listcell(practica.getPractica() != null ? practica.getPractica().getDescripcion() : "", "");
//                    Listcell stsPractica = new Listcell();
//                    Image imageLock;
//
//                    String pathImagen = null;
//                    if (practica.getStsTecnico().equals("IN")) {
//                        pathImagen = "/images/editing.png";
//
//                    } else if ((practica.getStsTecnico().equals("CO"))) {
//                        pathImagen = "/images/lock.png";
//                        peticionesCompletas++;
//                    } else {
//                        if (practica.getStsTecnico().equals("PE")) {
//                            pathImagen = "/images/document_blank.png";
//                        } else {
//                            if (practica.getStsTecnico().equals("AU")) {
//                                pathImagen = "/images/ok.png";
//                            } else {
//                                if (practica.getStsTecnico().equals("AR")) {
//                                    pathImagen = "/images/archivado.png";
//                                    peticionesCompletas++;
//                                } else {
//                                    if (practica.getStsTecnico().equals("IM")) {
//                                        pathImagen = "/images/pdf.png";
//                                        peticionesCompletas++;
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    imageLock = new Image(pathImagen);
//                    imageLock.setWidth("20px");
//                    imageLock.setParent(stsPractica);
//                    //Celdas Finales para Tooltip Peticion
//                    final Listcell firstUserP = new Listcell(practica.getFirstUser());
//                    final Listcell fecIniP = new Listcell(fechaHora.format(practica.getFecIni()));
//                    final Listcell fecUpdP = new Listcell();
//                    if (practica.getFecUpd() != null) {
//                        fecUpdP.setLabel(fechaHora.format(practica.getFecUpd()));
//                    }
//
//                    desPractica.addEventListener(Events.ON_RIGHT_CLICK, new EventListener() {
//
//                        @Override
//                        public void onEvent(Event event) throws Exception {
//                            //ToolTip
//                            StringBuilder toolTip = new StringBuilder();
//                            if (!firstUserP.getLabel().isEmpty()) {
//                                toolTip.append("Creado por: ");
//                                toolTip.append(new AdmiNegocio().getUsuario(firstUserP.getLabel()).getNomUsu());
//                                toolTip.append(" / Fecha Creación: ").append(fecIniP.getLabel());
//                            }
//
//                            if (!fecUpdP.getLabel().isEmpty()) {
//                                toolTip.append(" / Modificada: ").append(fecUpdP.getLabel());
//                            }
//                            desPractica.setTooltiptext(toolTip.toString());
////              Fin ToolTip
//                        }
//                    });
//                    peticion.appendChild(desPractica);
////                ComponentsCtrl.applyForward(desPractica,"onMouseOver=onMouseOver");
//                    peticion.appendChild(stsPractica);
//                    peticion.setParent(peticiones);
//                }
//                //  permisosxUsuarioN = new Perxuser();
//
//                // permisosxUsuarioN.getUsrnext();
//                if ((permisosxUsuarioN.getPriVis() == 0) && ((orden.getOrganizacion().getAbreviatura().equalsIgnoreCase("ECUAAMERICAN")))) {
//                    row.setVisible(false);
//                }
////                if ((usuario.getVenus() == 0) && ((orden.getOrganizacion().getAbreviatura().equalsIgnoreCase("ECUAAMERICAN")))) {
////                    row.setVisible(false);
////                }
//
//                peticiones.setParent(row);
//
//            } else {
//                new Label("(No hay peticiones)").setParent(row);
//            }
//
//            //
//            if (orden.getStsTecnico().equals("CO")) {       //Si la orden esta completa
//                Image imageEstadoTecnico;
//                imageEstadoTecnico = new Image("/images/status/orden-completed.png");
//                imageEstadoTecnico.setWidth("64px");
//                imageEstadoTecnico.setParent(row);
//
//            } else {        //Si la orden no esta completa
//                Vlayout estadoGroup = new Vlayout();
////            Progressmeter barraEstado = new Progressmeter();
//                Label estadoOs = new Label(ESTADO.get(orden.getStsTecnico()));
//                estadoOs.setParent(estadoGroup);
//                estadoGroup.setParent(row);
//            }
//
//            //new Label(ESTADO.get(orden.getStsTecnico())).setParent(row);
//            //Usuario
//            final Label usuario = new Label(orden.getFirstUser());
////        usuario.setTooltiptext(new AdmiNegocio().getUsuario(orden.getFirstUser()).getNomUsu());
//
//            //Celdas Finales para Tooltip OrdenA
//            final Listcell firstUser = new Listcell(orden.getFirstUser());
//            final Listcell fecIni = new Listcell(fechaHora.format(orden.getFecIni()));
//            final Listcell fecUpd = new Listcell();
//            if (orden.getFecUpd() != null) {
//                fecUpd.setLabel(fechaHora.format(orden.getFecUpd()));
//            }
//
//            usuario.addEventListener(Events.ON_CLICK, new EventListener() {
//
//                @Override
//                public void onEvent(Event event) throws Exception {
//                    //ToolTip
//                    StringBuilder toolTip = new StringBuilder();
//                    if (!firstUser.getLabel().isEmpty()) {
//                        toolTip.append("Creado por: ");
//                        toolTip.append(new AdmiNegocio().getUsuario(firstUser.getLabel()).getNomUsu());
//                        toolTip.append(" / Fecha Creación: ").append(fecIni.getLabel());
//                    }
//
//                    if (!fecUpd.getLabel().isEmpty()) {
//                        toolTip.append(" / Modificada: ").append(fecUpd.getLabel());
//                    }
//                    usuario.setTooltiptext(toolTip.toString());
////              Fin ToolTip
//                }
//            });
//
//            usuario.setParent(row);
//            //Fin de usuario
//
//            new Label(String.valueOf(orden.getOrigen().getDescripcion())).setParent(row);
//        } else {
//            //desde aqui solo lab
//            System.out.println("Solo laboratorio");
//            XmlResultado ped = (XmlResultado) data;
//            row.setValue(ped);
//            row.setValign("top");
//            new Label(fecha.format(ped.getFecha())).setParent(row);
//            new Label(String.valueOf(ped.getId())).setParent(row);
//            final Button ob = new Button("ESTADO!!");
//            ob.setParent(row);
//            ob.setDisabled(true);
//            new Label(ped.getHistoria().getPaciente()).setParent(row);
//
//            if (ped.getEmpresa() != null) {
//                new Label(ped.getEmpresa()).setParent(row);
//            } else {
//                new Label("(Sin Empresa)").setParent(row);
//            }
//            Listbox peticiones = new Listbox();
//            if ((ped != null)) {
//
//                Listhead peticionesCab = new Listhead();
//                Listheader desCab = new Listheader();
//                Listheader imgCab = new Listheader();
//
//                imgCab.setAlign("center");
//                imgCab.setWidth("15%");
//
//                desCab.setParent(peticionesCab);
//                imgCab.setParent(peticionesCab);
//
//                peticionesCab.setParent(peticiones);
//                peticionesCab.setVisible(false);
//
//                final Listitem peticion = new Listitem();
//                peticion.setValue(ped);
//                final Listcell desPractica = new Listcell(ped.getPractica() != null ? ped.getPractica().getDescripcion() : "", "");
//                Listcell stsPractica = new Listcell();
//                Image imageLock;
//                Listcell audito = new Listcell();
//                final Button audiotoria = new Button();
//                audiotoria.setDisabled(true);
//                audiotoria.setParent(audito);
//                String pathImagen = null;
////                    NombreP  practica=ped.getPractica();
//                if (ped.getEstado().equals("IN")) {
//                    pathImagen = "/images/editing.png";
//                    audiotoria.setVisible(false);
//
//                } else if (ped.getEstado().equals("CO")) {
//                    pathImagen = "/images/lock.png";
//                    audiotoria.setLabel("ARCHIVAR?");
//                    audiotoria.setVisible(true);
//                    audiotoria.setDisabled(false);
//                    audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
//                        @Override
//                        public void onEvent(Event event) throws Exception {
//                            Messagebox.show("Confirma que el informe fue archivado?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//                                public void onEvent(Event evt) throws InterruptedException, NamingException {
//                                    if (evt.getName().equals("onOK")) {
//                                        audiotoria.setDisabled(true);
//                                        audiotoria.setLabel("ARCHIVADO");
//                                        audiotoria.setStyle("border: 2px #B22222 solid;");
//                                        XmlResultado actualizar = peticion.getValue();
//                                        actualizar.setEstado("AR");
//                                        actualizar.setFecArch(new Date());
//                                        actualizar.setArchUser(usuario.getUsuario());
//                                        //actualizar.setArchUser(permisosxUsuarioN.getUsrnext());
//                                        AdmiNegocio admi = new AdmiNegocio();
//                                        admi.actualizar(actualizar);
//                                    } else if (evt.getName().equals("onCancel")) {
////                                        Messagebox.show("Ignore Save", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
//                                    }
//                                }
//                            });
//                        }
//                    });
////                    peticionesCompletas++;
//                } else {
//                    if (ped.getEstado().equals("PE")) {
//                        pathImagen = "/images/document_blank.png";
//                        audiotoria.setVisible(false);
//                    } else {
//                        if (ped.getEstado().equals("AU")) {
//                            pathImagen = "/images/ok.png";
//                            audiotoria.setLabel("AUDITADO");
//                            audiotoria.setStyle("border: 2px #006400 solid;");
//                        } else {
//                            if (ped.getEstado().equals("AR")) {
//                                pathImagen = "/images/archivado.png";
//                                audiotoria.setLabel("AUDITAR?");
//                                audiotoria.setVisible(true);
//                                audiotoria.setDisabled(false);
//                                audiotoria.addEventListener(Events.ON_CLICK, new EventListener() {
//                                    @Override
//                                    public void onEvent(Event event) throws Exception {
//                                        Messagebox.show("Confirma que el informe fue auditado?", "Confirmación", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//                                            public void onEvent(Event evt) throws InterruptedException, NamingException {
//                                                if (evt.getName().equals("onOK")) {
//                                                    audiotoria.setDisabled(true);
//                                                    audiotoria.setLabel("AUDITADO");
//                                                    audiotoria.setStyle("border: 2px #B22222 solid;");
//                                                    XmlResultado actualizar = peticion.getValue();
//                                                    actualizar.setEstado("AU");
//                                                    actualizar.setFechaAudit(new Date());
//                                                    actualizar.setAuditUser(usuario.getUsuario());
//                                                    // actualizar.setAuditUser(permisosxUsuarioN.getUsrnext());
//                                                    AdmiNegocio admi = new AdmiNegocio();
//                                                    admi.actualizar(actualizar);
//                                                } else if (evt.getName().equals("onCancel")) {
////                                        Messagebox.show("Ignore Save", "Warning", Messagebox.OK, Messagebox.EXCLAMATION);
//                                                }
//                                            }
//                                        });
//                                    }
//                                });
////                                peticionesCompletas++;
//                            }
//                        }
//                    }
//                }
//
//                imageLock = new Image(pathImagen);
//                imageLock.setWidth("20px");
//                imageLock.setParent(stsPractica);
//                //Celdas Finales para Tooltip Peticion
//                final Listcell firstUserP = new Listcell(ped.getFirstUser());
//                final Listcell fecIniP = new Listcell(fechaHora.format(ped.getFecIni()));
//                final Listcell fecUpdP = new Listcell();
//                if (ped.getFecUpd() != null) {
//                    fecUpdP.setLabel(fechaHora.format(ped.getFecUpd()));
//                }
//
//                desPractica.addEventListener(Events.ON_RIGHT_CLICK, new EventListener() {
//
//                    @Override
//                    public void onEvent(Event event) throws Exception {
//                        //ToolTip
//                        StringBuilder toolTip = new StringBuilder();
//                        if (!firstUserP.getLabel().isEmpty()) {
//                            toolTip.append("Creado por: ");
//                            toolTip.append(new AdmiNegocio().getUsuario(firstUserP.getLabel()).getNomUsu());
//                            toolTip.append(" / Fecha Creación: ").append(fecIniP.getLabel());
//                        }
//
//                        if (!fecUpdP.getLabel().isEmpty()) {
//                            toolTip.append(" / Modificada: ").append(fecUpdP.getLabel());
//                        }
//                        desPractica.setTooltiptext(toolTip.toString());
////              Fin ToolTip
//                    }
//                });
//
//                desPractica.addEventListener(Events.ON_DOUBLE_CLICK, new EventListener() {
//
//                    @Override
//                    public void onEvent(Event event) throws Exception {
//                        Window winMensaje = new Window();
//                        String windowMessage = "msg_informe.zul";
//                        Executions.createComponents(windowMessage, winMensaje, null);
//                        winMensaje.setBorder("normal");
//                        winMensaje.setClosable(true);
//                        winMensaje.setTitle("Vista Preliminar de Informe");
//                        final Iframe frameReporte = (Iframe) winMensaje.getFellow("reporteV", true);
//
//                        XmlResultado practica;
//
//                        practica = ((Listitem) desPractica.getParent()).getValue();
//
//                        if ((practica.getEstado().equalsIgnoreCase("IN"))) {
//
//                            Map<String, Object> wSQL = new HashMap<String, Object>();
//                            wSQL.put("idOrden", practica.getNroOrd());
//                            wSQL.put("idPractica", practica.getPractica().getId());
//                            AMedia mediaCarga = null;
//                            try {
//                                mediaCarga = loadReportXml(wSQL, true, imprimir);
//                            } catch (Exception e) {
//                                Messagebox.show("No se han encontrado informes para registro seleccionado");
//                            }
//                            if (mediaCarga != null) {
//                                frameReporte.setContent(mediaCarga);
//                                Component parent = new Window();
//                                Collection<Component> comps = Executions.getCurrent().getDesktop().getComponents();
//                                for (Component c : comps) {
//                                    if (c.getDefinition().getName().equals("window")) {
//                                        parent = c;
//                                    }
//                                }
//                                winMensaje.setParent(parent);
//                                winMensaje.doModal();
//                            } else {
//                                Messagebox.show("No se han encontrado informes para registro seleccionado");
//                            }
//                        } else {
//                            Messagebox.show("No se permite visualizar el informe para el registro seleccionado");
//                        }
//
//                    }
//
//                }
//                );
//
//                peticion.appendChild(desPractica);
////                ComponentsCtrl.applyForward(desPractica,"onMouseOver=onMouseOver");
//
//                peticion.appendChild(stsPractica);
//
//                peticion.appendChild(audito);
//
//                peticion.setParent(peticiones);
//
//                peticiones.setParent(row);
//
//            } else {
//                new Label("(No hay peticiones)").setParent(row);
//            }
//
//            //
//            if (ped.getEstado().equals("CO")) {       //Si la orden esta completa
//                Image imageEstadoTecnico;
//                imageEstadoTecnico = new Image("/images/status/orden-completed.png");
//                imageEstadoTecnico.setWidth("64px");
//                imageEstadoTecnico.setParent(row);
//
//            } else {        //Si la orden no esta completa
//                Vlayout estadoGroup = new Vlayout();
////            Progressmeter barraEstado = new Progressmeter();
//                Label estadoOs = new Label(ESTADO.get(ped.getEstado()));
//                estadoOs.setParent(estadoGroup);
//                estadoGroup.setParent(row);
//            }
//
//            //new Label(ESTADO.get(orden.getStsTecnico())).setParent(row);
//            //Usuario
//            final Label usuario = new Label(ped.getFirstUser());
////        usuario.setTooltiptext(new AdmiNegocio().getUsuario(orden.getFirstUser()).getNomUsu());
//
//            //Celdas Finales para Tooltip OrdenA
//            final Listcell firstUser = new Listcell(ped.getFirstUser());
//            final Listcell fecIni = new Listcell(fechaHora.format(ped.getFecIni()));
//            final Listcell fecUpd = new Listcell();
//            if (ped.getFecUpd() != null) {
//                fecUpd.setLabel(fechaHora.format(ped.getFecUpd()));
//            }
//
//            usuario.addEventListener(Events.ON_CLICK, new EventListener() {
//
//                @Override
//                public void onEvent(Event event) throws Exception {
//                    //ToolTip
//                    StringBuilder toolTip = new StringBuilder();
//                    if (!firstUser.getLabel().isEmpty()) {
//                        toolTip.append("Creado por: ");
//                        toolTip.append(new AdmiNegocio().getUsuario(firstUser.getLabel()).getNomUsu());
//                        toolTip.append(" / Fecha Creación: ").append(fecIni.getLabel());
//                    }
//
//                    if (!fecUpd.getLabel().isEmpty()) {
//                        toolTip.append(" / Modificada: ").append(fecUpd.getLabel());
//                    }
//                    usuario.setTooltiptext(toolTip.toString());
////              Fin ToolTip
//                }
//            });
//
//            usuario.setParent(row);
//            //Fin de usuario
//
//            new Label(String.valueOf("Lab")).setParent(row);
//        }
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

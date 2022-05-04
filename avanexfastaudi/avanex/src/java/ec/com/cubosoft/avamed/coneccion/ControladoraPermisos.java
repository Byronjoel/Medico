package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.modelo.core.*;
import ec.com.cubosoft.avamed.modelo.nextla.STrausu;
import ec.com.cubosoft.avamed.modelo.nextla.SUsuar;
import ec.com.cubosoft.avamed.modelo.nextla.sessionOk;
import ec.com.cubosoft.avamed.modelo.publico.Iso3166R2;
import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import ec.com.cubosoft.avamed.procesos.AdmiNegocioSql;
import javax.naming.NamingException;
import org.zkoss.zhtml.Messagebox;
import java.util.List;
import ec.com.cubosoft.avamed.procesos.ManejadoraXml;
import ec.com.cubosoft.avamed.procesos.AdmiUsuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Menu;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Toolbar;
import org.zkoss.zul.Toolbarbutton;

/**
 * @author Administrador
 */
public class ControladoraPermisos extends GenericForwardComposer {

    //<editor-fold defaultstate="expanded" desc="Variables">
    //INICIAR SESSION
    //Tipos de datos Genericos
    String usu = "", pas = "";
    Boolean banderaC = false;
    //Entidades y Clases
    AdmiUsuario admUsuario;
    CsUsuarios current, objUsuarioActivo;
    CsGrupos currentG;
    CsPerxgru permisosMenuID;
    Iso3166R2 selectedCiudad;
    List<CsPerxgru> listaper = null;
    ProcesosSession admiSessionUsuario;
    //
    Textbox id_user, id_password;
    Combobox id_ciudad;
    Menubar BarraMenu;
    Toolbar barraProcesos;
    private static String archivo;

    public String getUsu() {
        return usu;
    }

    public void setUsu(String usu) {
        this.usu = usu;
    }

    public String getPas() {
        return pas;
    }

    public void setPas(String pas) {
        this.pas = pas;
    }

    public void setCurrentG(CsGrupos currentG) {
        this.currentG = currentG;
    }

    public CsGrupos getCurrentG() {
        return currentG;
    }

    public void setCurrent(CsUsuarios current) {
        this.current = current;
    }

    public CsUsuarios getCurrent() {
        return current;
    }

    public void onCreate$winLogin() {
        loadContexto();
        loadCiudades();
    }

    public void loadCiudades() {
        try {

            AdmiNegocio admNeg = new AdmiNegocio();
            Map<String, Object> wSQL = new HashMap<String, Object>();
            List oSQL = new ArrayList<Object>();

            wSQL.put("lockReg ?=", 0);
            oSQL.add("region2");
            List ciudades = admNeg.getData(new Iso3166R2(), wSQL, null, oSQL);

//            id_ciudad.setStyle(" height:25px ;border: 1px solid #3366FF; border-left: 5px solid #3366FF; text-transform:uppercase;font-size: 20px;");
            //Locale locale = org.zkoss.util.Locales.getLocale("ES_MX",'_');
            //session.setAttribute(Attributes.PREFERRED_LOCALE, locale);
            for (Object object : ciudades) {
                Iso3166R2 ciudad = (Iso3166R2) object;
                Comboitem itemCiudad = new Comboitem(ciudad.getRegion2());
                itemCiudad.setId(ciudad.getId().toString());
                itemCiudad.setValue(ciudad);
                itemCiudad.setParent(id_ciudad);

                //itemCiudad.setZclass("z-comboitem-text");
                if (ciudad.getRegion2().equalsIgnoreCase("Quito")) {
                    id_ciudad.setSelectedItem(itemCiudad);
                selectedCiudad = itemCiudad.getValue();
                }
            }

        } catch (NamingException e) {
            throw new RuntimeException(e);
        }

    }

    public void loadContexto() {
        ManejadoraXml admXml = new ManejadoraXml();
        Document doc = admXml.getDocumento("inicioA.xml", 0);
        String rootXML = doc.getDocumentElement().getNodeName();
        //obtener lista de permisos de la raiz
        NodeList listaHijos = doc.getChildNodes();
        listaHijos = listaHijos.item(0).getChildNodes();
        Node NPagina;
        Node NHijos;
        NPagina = listaHijos.item(0);
        String dato;
        admUsuario = new AdmiUsuario();
        for (int j = 0; j < listaHijos.getLength(); j++) {
            NHijos = listaHijos.item(j);
            if (NHijos.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoP = (Element) NHijos;
                if (elementoP.getNodeName() != null) {
                    String NamNText = elementoP.getTagName();
                    switch (NamNText) {
//                        case "global": {
//                            dato = elementoP.getTextContent();
//                            admUsuario.setContextoGlobal(dato);
//                        }
//                        break;
//                        case "generico": {
//                            dato = elementoP.getTextContent();
//                            admUsuario.setContextoGenerico(dato);
//                        }
//                        break;
                        case "menu": {
                            archivo = elementoP.getTextContent();
                        }
                        break;
                        default: {
                            archivo = "EsquemaMenus.xml";
                        }
                    }

                }
            }
        }
        //Si existen menus en el XML entonces se cargan
    }

    public void onSelect$id_ciudad() {
        selectedCiudad = id_ciudad.getSelectedItem().getValue();
    }
    private sessionOk objsessiActica;
      private SUsuar usuarioN;
    private CsUsuarios usuarioP;
    public void onClick$iniciar() {
        try {
            VerificarUsuarioNextlab();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void VerificarUsuarioNextlab() throws InterruptedException, NamingException {
        boolean activar = false;
        SUsuar usuario = null;
        AdmiNegocio admNe;
        //  admiSessionUsuario = new ProcesosSession();
        AdmiNegocioSql admNeg = new AdmiNegocioSql();
        Map<String, Object> wSQL = new HashMap<String, Object>();
        List oSQL = new ArrayList<Object>();
        wSQL.put("usuario ?=", id_user.getValue().toUpperCase().trim());
        wSQL.put("clave ?=", id_password.getValue().trim());
        usuario = (SUsuar) admNeg.getDataObj(new SUsuar(), wSQL, null, oSQL);
        if (!(usuario == null)) {
            objsessiActica = new sessionOk();
            objsessiActica.setUsuarioN(usuario);
            objsessiActica.setTipo(1);
            objsessiActica.setUsuario(usuario.getUsuario());
            objsessiActica.setGrupo(usuario.getUsuario());
            wSQL = new HashMap<String, Object>();
            wSQL.put("usrnext ?=", usuario.getUsuario().trim());
            admNe = new AdmiNegocio();
            Perxuser perUsuarioN = (Perxuser) admNe.getDataObj(new Perxuser(), wSQL, null, oSQL);
            objsessiActica.setPerUsuNex(perUsuarioN);
            activar = false;
            //System.out.println("ControladoraPermisos.VerificarUsuarioNextlab()");
        }
        if (!(usuario == null)) {
            //obtener permiso
            activar = true;
            wSQL = new HashMap<String, Object>();
            oSQL = new ArrayList<Object>();
            wSQL.put("sTrausuPK.usuario ?=", usuario.getUsuario());
            List LiperUsr = admNeg.getData(new STrausu(), wSQL, null, oSQL);
            if (LiperUsr.size() > 0) {
                objsessiActica.setPerUsuNext(LiperUsr);
                activar = true;
            } else {
                activar = false;
            }
        } else {
            //var si es medico avasus
            admNe = new AdmiNegocio();
            wSQL = new HashMap<>();
            oSQL = new ArrayList<>();
            wSQL.put("usuario ?=", id_user.getValue().toUpperCase().trim());
            wSQL.put("pwdUsu ?=", id_password.getValue().trim());
            wSQL.put("lockReg ?=", 0);
            usuarioP = (CsUsuarios) admNe.getDataObj(new CsUsuarios(), wSQL, null, null);
            if (!(usuarioP == null)) {
                if ((usuarioP.getCsGrupos().getCodGru().equalsIgnoreCase("MED"))||((usuarioP.getCsGrupos().getCodGru().equalsIgnoreCase("ADMIN")))) {
                    objsessiActica = new sessionOk();
                    objsessiActica.setUsuarioP(usuarioP);
                    objsessiActica.setTipo(0);
                    objsessiActica.setGrupoP(usuarioP.getCsGrupos());
                    objsessiActica.setUsuario(usuarioP.getUsuario());
                    objsessiActica.setGrupo(usuarioP.getCsGrupos().getCodGru());
                    wSQL = new HashMap<>();
                    oSQL = new ArrayList<>();
                    wSQL.put("csPerxgruPK.codGru ?=", usuarioP.getCsGrupos().getCodGru());
                    wSQL.put("lockReg ?=", 0);
                    List listaper = admNe.getData(new CsPerxgru(), wSQL, null, null);
                    if (listaper.size() > 0) {
                        objsessiActica.setPerUsuAva(listaper);
                        activar = true;
                    } else {
                        activar = false;
                    }
                } else {
                    activar = false;
                }
            }
        }
        if (activar) {
            crearSession(objsessiActica);
            if (id_password.getText().equals("0000")) {
                Messagebox.show("Ha accedido correctamente al Sistema, pero esta usando la clave por defecto,\n"
                        + "Usted será redirigido a una pantalla para cambiar su clave.", "Cambio de clave necesario",
                        Messagebox.OK, Messagebox.INFORMATION, new EventListener() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        execution.sendRedirect("/security/changePwd.zul");
                    }
                });
            } else {
                execution.sendRedirect("/ocupacional/menu.zul");
            }
        } else {
            limpiar();
            Messagebox.show("Verificar datos para abrir sessión", "No se pudo iniciar sesión",
                    Messagebox.OK, Messagebox.EXCLAMATION);
        }
    }
   private void crearSession(sessionOk objsessiAc) {
        List<Object> listSession = new ArrayList<Object>();
        objsessiAc.setCiudad(selectedCiudad);
        objsessiAc.setPagina(page.getTitle());
        ProcesosSession admiSessionUsuario = new ProcesosSession();
        String dirIP;
        try {
            dirIP = java.net.InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            dirIP = session.getRemoteAddr();
        }
        //  dirIP = session.getRemoteAddr();
        listSession.add(0, dirIP); //en la por 0 esta el usuario
        //  dirIP = session.getRemoteAddr();
        // listSession.add(0, dirIP); //en la por 0 esta el usuario
        dirIP = session.getRemoteAddr();
        listSession.add(1, dirIP); //en la por 0 esta el usuario
        objsessiAc.setDirIp(dirIP);
        listSession.add(2, objsessiAc); //USUARIO
        admiSessionUsuario.crearSession(session, dirIP, listSession);
        if (id_password.getText().equals("0000")) {
         
            Messagebox.show("Ha accedido correctamente al Sistema, pero esta usando la clave por defecto,\n"
                    + "Usted será redirigido a una pantalla para cambiar su clave.", "Cambio de clave necesario",
                    Messagebox.OK, Messagebox.INFORMATION, new EventListener() {

                @Override
                public void onEvent(Event event) throws Exception {
                    execution.sendRedirect("/security/changePwd.zul");
                }
            });
        } else {
            execution.sendRedirect("/ocupacional/menu.zul");
        }
    }

//    public void VerificarUsuario() throws InterruptedException, NamingException {
//        List<Object> informe = new ArrayList<Object>();
//        CsGrupos dato;
//        currentG = new CsGrupos();
//        admiSessionUsuario = new ProcesosSession();
//        dato = (CsGrupos) admUsuario.getGrupoUsuario(id_user.getText().toUpperCase(), id_password.getText().toUpperCase());
//        current = (CsUsuarios) admUsuario.getUsuario(id_user.getText().toUpperCase(), id_password.getText().toUpperCase(), true);
//
//        if (dato != null) {
//            currentG = dato;
//            current.setCsGrupos(currentG);
//
//            //String nomSession=current.getNomUsu();        //Variable comentada porque no se usaba
//            //CREAR SESSION//
//            String dirIP;
//            try {
//                dirIP = java.net.InetAddress.getLocalHost().getHostAddress();
//            } catch (Exception e) {
//                dirIP = session.getRemoteAddr();
//            }
//
//            dirIP = session.getRemoteAddr();
//            informe.add(0, dirIP); //en la por 0 esta el usuario
//            dirIP = session.getRemoteAddr();
//            informe.add(1, dirIP); //en la por 0 esta el usuario
//            informe.add(2, current); //USUARIO
//            informe.add(3, currentG); //GRUPO USUARIO
//            informe.add(4, page.getTitle()); //GRUPO USUARIO
//            informe.add(5, selectedCiudad);  //Ciudad del Usuario
//            listaper = admUsuario.getperxgrup(currentG.getCodGru());
//            informe.add(6, listaper);  //Ciudad del Usuario
//            admiSessionUsuario.crearSession(session, dirIP, informe);
//            if (id_password.getText().equals("0000")) {
//                Messagebox.show("Ha accedido correctamente al Sistema, pero esta usando la clave por defecto,\n"
//                        + "Usted será redirigido a una pantalla para cambiar su clave.", "Cambio de clave necesario",
//                        Messagebox.OK, Messagebox.INFORMATION, new EventListener() {
//
//                    @Override
//                    public void onEvent(Event event) throws Exception {
//                        execution.sendRedirect("/security/changePwd.zul");
//                    }
//                });
//            } else {
//                execution.sendRedirect("/ocupacional/menu.zul");
//            }
//
//        } else {
//            Messagebox.show("Usuario o Clave Incorrecta", "No se pudo iniciar sesión",
//                    Messagebox.OK, Messagebox.ERROR);
//        }
////        Messagebox.show(selectedCiudad.getRegion2()+" / "+selectedCiudad.getIdIso2().getRegion1());
//    }

    public void limpiar() {
        id_password.setText("");
        id_user.setText("");

    }

    public void onCreate$BarraMenu() {
        banderaC = true;
    }

    public String getUsuario() {
        admiSessionUsuario = new ProcesosSession();
        String usuario = null;
        objUsuarioActivo = (CsUsuarios) admiSessionUsuario.ObtenerAtributoSession(2, session);
        banderaC = false;
        usuario = objUsuarioActivo.getUsuario() + " / " + objUsuarioActivo.getNomUsu();
        return usuario;
    }

    public String getReferencia() {
        String usuario = null;
        admiSessionUsuario = new ProcesosSession();
        if (banderaC) {
            loadXML();
            ModificarSession();
            objUsuarioActivo = (CsUsuarios) admiSessionUsuario.ObtenerAtributoSession(2, session);
            banderaC = false;
            usuario = objUsuarioActivo.getUsuario() + " / " + objUsuarioActivo.getNomUsu();
        }
        return usuario;
    }

    private void ModificarSession() {
        permisosMenuID = new CsPerxgru();
        admiSessionUsuario = new ProcesosSession();
        if (permisosMenuID != null) {
            admiSessionUsuario.AgregarAtributoSession(6, listaper, session);
        }
    }

    private void loadXML() {
        ManejadoraXml admXml = new ManejadoraXml();
        Document doc = admXml.getDocumento(archivo, 0);
        String rootXML = doc.getDocumentElement().getNodeName();
        //obtener lista de permisos de la raiz
        NodeList menuGroups = admXml.getlistanodos(rootXML, doc).item(0).getChildNodes();
        //Si existen menus en el XML entonces se cargan
        if (menuGroups.getLength() > 0) {
            loadMenu(menuGroups);
//            createMenu();
            permissionMenu();
        }
    }

    private void loadMenu(NodeList menuNodeList) {
        NodeList submenuNodeList;
        Node menuNode, menuitemNode;
        Element menuElement, menuitemElement;

        for (int i = 0; i < menuNodeList.getLength(); i++) {
            menuNode = menuNodeList.item(i);
            if (menuNode.getNodeType() == Node.ELEMENT_NODE) {
                menuElement = (Element) menuNode;

                if (menuElement.getNodeName() != null) {
                    boolean toolbar = false;
//                   listMenus.add(elemento.getNodeName().toUpperCase());
//                   listSmenus.add(elemento.getNodeName());
                    Menupopup menupopup = createMenu(menuElement);
                    submenuNodeList = menuNode.getChildNodes();

                    if (menuElement.getAttributes().getNamedItem("toolbar") != null) {
                        if (menuElement.getAttributes().getNamedItem("toolbar").getTextContent().equals("1")) {
                            toolbar = true;
                        }
                    }
                    //RECORRER LOS SUBMENUS
                    for (int j = 0; j < submenuNodeList.getLength(); j++) {
                        menuitemNode = submenuNodeList.item(j);
                        if (menuitemNode.getNodeType() == Node.ELEMENT_NODE) {
                            menuitemElement = (Element) menuitemNode;
                            if (menuitemElement.getAttributes().getNamedItem("show").getTextContent().equals("1")) {

                                if (menuitemElement.getAttributes().getNamedItem("toolbar") != null) {
                                    if (menuitemElement.getAttributes().getNamedItem("toolbar").getTextContent().equals("1")) {
                                        toolbar = true;
                                    } else {
                                        toolbar = false;
                                    }
                                }
//                                System.out.println("Toolbar " + toolbar);
                                createMenuItem(menuitemElement, menupopup, toolbar);
                            }
                        }
                    }
                }
            }
        }

        //Menu de Home y Exit
        menuHomeExit();
    }

   private void permissionMenu() {
        List<Component> listMenuPop = new ArrayList();
        List<Component> listMenusBarra = new ArrayList();
        List<Component> listItem = new ArrayList();
        List<Component> listtoolButton = new ArrayList();

        boolean ban = false;
        String idMenuItem;
        String idMenuPermiso;

        listMenusBarra = BarraMenu.getChildren();

        loadPermissionUser();

        for (Component objmenu : listMenusBarra) {
            listMenuPop = objmenu.getChildren();
            for (Component objmenup : listMenuPop) {
                listItem = objmenup.getChildren();
                listtoolButton = barraProcesos.getChildren();
                for (Component objmenuItem : listItem) {
                    idMenuItem = objmenuItem.getId();
                    for (CsPerxgru objpermiso : listaper) {
                        idMenuPermiso = objpermiso.getCsPerxgruPK().getCodPer();
//                        System.out.println(idMenuPermiso);
                        ban = (idMenuItem.equals(idMenuPermiso));
                        if (ban) {
                            Menuitem menu = (Menuitem) objmenuItem;
                            menu.setDisabled(false);
                            for (Component toolButton : listtoolButton) {
                                Toolbarbutton toolBarButton = (Toolbarbutton) toolButton;
                                String x = toolButton.getId();
                                String idTB = x.substring(1, x.length());
                                if (idMenuPermiso.equals(idTB)) {
                                    toolBarButton.setDisabled(false);
                                }
                            }
                        }
                    }//fin permisos
                }//fin for menu item
            }//fin menupop
        }//fin Menus
    }

    private Menupopup createMenu(Element elementMenu) {
        Menupopup menupopup = null;
        Menu menu;
        String nameMenu = elementMenu.getNodeName();

        menu = new Menu(nameMenu.toUpperCase());
        menu.setId(nameMenu);
        menu.setParent(BarraMenu);
        menupopup = new Menupopup();
        menupopup.setParent(menu);

        return menupopup;
    }

    private void createMenuItem(Element menuitemElement, Menupopup menupopup, boolean toolbar) {

        String menuitemName = null;
        String menuitemHref = null;
        boolean menuLock = true;

        if (menuitemElement.getAttributes().getNamedItem("text") != null) {
            menuitemName = menuitemElement.getAttributes().getNamedItem("text").getTextContent();
        } else {
            menuitemName = menuitemElement.getNodeName();
            if (menuitemName.indexOf("_") > 0) {
                menuitemName = menuitemName.substring(2).toUpperCase();
            }
        }

        Menuitem menuitem = new Menuitem(menuitemName);
        menuitem.setId(menuitemElement.getNodeName());

        menuitemHref = menuitemElement.getAttributes().getNamedItem("href").getTextContent();
        menuitem.setHref(menuitemHref);

        if (menuitemElement.getAttributes().getNamedItem("free") != null) {
            if (menuitemElement.getAttributes().getNamedItem("free").getTextContent().equals("1")) {
                menuLock = false;
            }
        }

        menuitem.setDisabled(menuLock);
        menuitem.setImage("/images/menu/" + menuitemElement.getNodeName() + ".png");
        menuitem.setParent(menupopup);

        if (toolbar) {
            Toolbarbutton toolbarbutton;

            toolbarbutton = new Toolbarbutton();
            toolbarbutton.setId("t" + menuitemElement.getNodeName());
            toolbarbutton.setLabel(menuitemName);
            toolbarbutton.setHref(menuitemHref);
            toolbarbutton.setDisabled(menuLock);
            toolbarbutton.setImage("/images/menu/" + menuitemElement.getNodeName() + ".png");
            toolbarbutton.setParent(barraProcesos);
            toolbarbutton.setWidth("100px");
            toolbarbutton.setStyle("font-weight: bold;font-size:9px;");
        }
    }

    private void menuHomeExit() {

        Toolbarbutton toolbarbutton;
        //Home
        toolbarbutton = new Toolbarbutton();
        toolbarbutton.setId("t_home_button");
        toolbarbutton.setLabel("Inicio");
        toolbarbutton.setHref("/ocupacional/menu.zul");
        toolbarbutton.setImage("/images/menu/m_home.png");
        toolbarbutton.setParent(barraProcesos);
        toolbarbutton.setWidth("100px");
        toolbarbutton.setStyle("font-weight: bold;font-size:9px;");
        //Salir
        toolbarbutton = new Toolbarbutton();
        toolbarbutton.setId("t_login_button");
        toolbarbutton.setLabel("Salir");
        toolbarbutton.setHref("/login.zul");
        toolbarbutton.setImage("/images/menu/m_login.png");
        toolbarbutton.setParent(barraProcesos);
        toolbarbutton.setWidth("100px");
        toolbarbutton.setStyle("font-weight: bold;font-size:9px;");
        //ERICK
        toolbarbutton = new Toolbarbutton();
        toolbarbutton.setId("t_login_button");
        toolbarbutton.setLabel("ERICK");
        toolbarbutton.setHref("/login.zul");
        toolbarbutton.setImage("/images/menu/m_login.png");
        toolbarbutton.setParent(barraProcesos);
        toolbarbutton.setWidth("100px");
        toolbarbutton.setStyle("font-weight: bold;font-size:9px;");
    }

    private void loadPermissionUser() {
        admUsuario = new AdmiUsuario();
        admiSessionUsuario = new ProcesosSession();
        listaper = (List<CsPerxgru>) admiSessionUsuario.ObtenerAtributoSession(6, session);

    }

}

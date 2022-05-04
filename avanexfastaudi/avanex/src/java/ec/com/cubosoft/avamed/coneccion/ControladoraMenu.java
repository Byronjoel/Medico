package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.modelo.core.*;
import ec.com.cubosoft.avamed.modelo.nextla.STrausu;
import ec.com.cubosoft.avamed.modelo.nextla.SUsuar;
import ec.com.cubosoft.avamed.modelo.nextla.sessionOk;
import ec.com.cubosoft.avamed.modelo.publico.Iso3166R2;
import ec.com.cubosoft.avamed.procesos.AdmiNegocioSql;
import java.util.List;
import ec.com.cubosoft.avamed.procesos.ManejadoraXml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
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
public class ControladoraMenu extends GenericForwardComposer {

    //<editor-fold defaultstate="expanded" desc="Variables">
    //INICIAR SESSION
    //Tipos de datos Genericos
    Boolean banderaC = false;
    //Entidades y Clases
    AdmiNegocioSql admUsuario;
    // CsUsuarios current;
    private String usuario;
    private SUsuar usuarioN;
    private CsUsuarios usuarioP;
    private List perUsuAva;
    private String pagina;
    private List perUsuNext;

    CsPerxgru permisosMenuID;
    Iso3166R2 selectedCiudad;
    Textbox id_user, id_password;
    Combobox id_ciudad;
    Menubar BarraMenu;
    Toolbar barraProcesos;
    private static String archivo;
    private sessionOk objsessiActica;

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public SUsuar getUsuarioN() {
        return usuarioN;
    }

    public void setUsuarioN(SUsuar usuarioN) {
        this.usuarioN = usuarioN;
    }

    public CsUsuarios getUsuarioP() {
        return usuarioP;
    }

    public void setUsuarioP(CsUsuarios usuarioP) {
        this.usuarioP = usuarioP;
    }

    public List getPerUsuAva() {
        return perUsuAva;
    }

    public void setPerUsuAva(List perUsuAva) {
        this.perUsuAva = perUsuAva;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public List getPerUsuNext() {
        return perUsuNext;
    }

    public void setPerUsuNext(List perUsuNext) {
        this.perUsuNext = perUsuNext;
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
        admUsuario = new AdmiNegocioSql();
        for (int j = 0; j < listaHijos.getLength(); j++) {
            NHijos = listaHijos.item(j);
            if (NHijos.getNodeType() == Node.ELEMENT_NODE) {
                Element elementoP = (Element) NHijos;
                if (elementoP.getNodeName() != null) {
                    String NamNText = elementoP.getTagName();
                    switch (NamNText) {
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
        }        //Si existen menus en el XML entonces se cargan
    }

    public void onSelect$id_ciudad() {
        selectedCiudad = id_ciudad.getSelectedItem().getValue();
    }

    public void limpiar() {
        try {
            id_user.setValue(" ");
            id_password.setValue(" ");
        } catch (Exception e) {
            System.out.println("ControladoraPermisos.limpiar()");
        }
    }

    public void onCreate$BarraMenu() {
        banderaC = true;
        loadContexto();
    }

    public String getReferencia() {
        if (banderaC) {
            ProcesosSession admiSessionUsuario = new ProcesosSession();
            objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, session);
            if (objsessiActica != null) {
                if (banderaC) {
                    loadXML();
                    banderaC = false;
                    ModificarSession();
                    usuario = objsessiActica.getUsuario();
                }
            } else {
                alert("Session inactiva");
            }
        }
        return usuario;
    }

    private void ModificarSession() {
        permisosMenuID = new CsPerxgru();

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
            //tood el menu sin permisos
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
                    //menu
                    Menupopup menupopup = createMenu(menuElement);
                    submenuNodeList = menuNode.getChildNodes();
                    if (menuElement.getAttributes().getNamedItem("toolbar") != null) {
                        if (menuElement.getAttributes().getNamedItem("toolbar").getTextContent().equals("1")) {
                            toolbar = true;
                        }
                    }
                    //submenu
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
                                createMenuItem(menuitemElement, menupopup, toolbar);
                            }
                        }
                    }
                }
            }
        }
        menuHomeExit();
    }

    private void permissionMenu() {
        //PARA VALIDAR SI TIENE ESE PERMISO
        ProcesosSession admiSessionUsuario = new ProcesosSession();
        objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, session);
        if (objsessiActica != null) {
            List<Component> listMenuPop;
            List<Component> listMenusBarra;
            List<Component> listItem;
            List<Component> listtoolButton;
            boolean ban = false;
            String idMenuP;
            String idMenuN;
            String idMenuPermiso = null;
            boolean hab = false;
            List listaper = null;
            String codPer;
            loadPermissionUser();
            listMenusBarra = BarraMenu.getChildren();
            for (Component objmenu : listMenusBarra) {
                listMenuPop = objmenu.getChildren();
                for (Component objmenup : listMenuPop) {
                    listItem = objmenup.getChildren();
                    listtoolButton = barraProcesos.getChildren();
                    for (Component objmenuItem : listItem) {
                        idMenuP = objmenuItem.getId();
                        idMenuN = (String) objmenuItem.getAttribute("tras");
                        if (objsessiActica.getTipo() == 1) {
                            listaper = objsessiActica.getPerUsuNext();
                        } else {
                            listaper = objsessiActica.getPerUsuAva();
                        }
                        for (Object object : listaper) {
                            switch (objsessiActica.getTipo()) {
                                case 0: {
                                    CsPerxgru obj = (CsPerxgru) object;
                                    idMenuPermiso = obj.getCsPerxgruPK().getCodPer();
                                }
                                break;
                                case 1: {
                                    STrausu obj = (STrausu) object;
                                    idMenuPermiso = obj.getSTrausuPK().getTransac();
                                }
                                break;
                            }
                            ban = (idMenuP.equalsIgnoreCase(idMenuPermiso));
                            if (!ban) {
                                ban = (idMenuN.equalsIgnoreCase(idMenuPermiso));
                                if (ban) {
                                    hab = true;
                                }
                            }
                            if (ban) {
                                Menuitem menu = (Menuitem) objmenuItem;
                                menu.setDisabled(false);
                                for (int i = 0; i < listtoolButton.size(); i++) {
                                    Toolbarbutton toolBarButton = (Toolbarbutton) listtoolButton.get(i);
                                    String x = toolBarButton.getId();
                                    String idTB = x.substring(1, x.length());
                                    if ((idMenuPermiso.equals(idTB))) {
                                        hab = false;
                                        toolBarButton.setDisabled(false);
                                        i = listtoolButton.size();
                                    } else {
                                        //si es trsn
                                        if ((idMenuPermiso.equals(idMenuN) && ((idMenuP.equals(idTB))))) {
                                            toolBarButton.setDisabled(false);
                                            i = listtoolButton.size();
                                            hab = false;
                                        } else {
                                            //pregunto si es tipo
                                        }
                                    }
                                }
                            }
                        }//fin permisos
                    }//fin for menu item
                }//fin menupop
            }//fin Menus
        } else {
            alert("Session inactiva");
        }
    }

    private Menupopup createMenu(Element elementMenu) {
        Menupopup menupopup = null;
        Menu menu;
        String trans = "";
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
        String trans = "";
        if (menuitemElement.getAttributes().getNamedItem("tras") != null) {
            trans = menuitemElement.getAttributes().getNamedItem("tras").getTextContent();

        }
        menuitem.setAttribute("tras", trans);
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
        toolbarbutton.setLabel("Inicioss");
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
        
        //Byron
        
         toolbarbutton = new Toolbarbutton();
        toolbarbutton.setId("t_byron_button");
        toolbarbutton.setLabel("Pruebas");
        toolbarbutton.setHref("/ocupacional/pruebaByron.zul");
        toolbarbutton.setImage("/images/menu/m_cambiarclave.png");
        toolbarbutton.setParent(barraProcesos);
        toolbarbutton.setWidth("100px");
        toolbarbutton.setStyle("font-weight: bold;font-size:9px;");
    }

    private void loadPermissionUser() {
//        admUsuario = new AdmiUsuario();
//        admiSessionUsuario = new ProcesosSession();
//        listaper = (List<CsPerxgru>) admiSessionUsuario.ObtenerAtributoSession(6, session);

    }
    //   listaper = (List<CsPerxgru>) admiSessionUsuario.ObtenerAtributoSession(6, session);
}

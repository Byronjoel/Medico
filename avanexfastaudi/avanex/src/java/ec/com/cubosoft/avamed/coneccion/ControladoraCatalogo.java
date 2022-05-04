package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import ec.com.cubosoft.avamed.procesos.ManejadoraXml;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
public class ControladoraCatalogo extends GenericForwardComposer {

    //<editor-fold defaultstate="expanded" desc="Variables">
    //INICIAR SESSION
    //Tipos de datos Genericos
    String usu = "", pas = "";
    Boolean banderaC = false;
    //Entidades y Clases
    AdmiNegocio admUsuario;
    ProcesosSession admiSessionUsuario;
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

    public void onCreate$winLogin() {
    }

    public void onClick$anaServ() {
        try {
            execution.sendRedirect("/ocupacional/analServ.zul");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onClick$ServAna() {
        try {
            execution.sendRedirect("/ocupacional/serviAna.zul");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onClick$catalogo() {
        try {
            execution.sendRedirect("/ocupacional/Analisis.zul");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onClick$OriEmp() {
        try {
//           VerificarUsuario();
            execution.sendRedirect("/ocupacional/Origen.zul");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onClick$Origenes() {
        try {
//           VerificarUsuario();
            execution.sendRedirect("/ocupacional/empresas.zul");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void onClick$todos() {
        try {
//           VerificarUsuario();
            execution.sendRedirect("/ocupacional/anaFinal.zul");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void limpiar() {
        id_password.setText("");
        id_user.setText("");

    }

    public void onCreate$BarraMenu() {
        banderaC = true;
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
            //     permissionMenu();
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

//    private void permissionMenu() {
//        List<Component> listMenuPop;
//        List<Component> listMenusBarra ;
//        List<Component> listItem;
//        List<Component> listtoolButton;
//
//        boolean ban = false;
//        String idMenuItem;
//        String idMenuPermiso;
//
//        listMenusBarra = BarraMenu.getChildren();
//
//        loadPermissionUser();
//
//        for (Component objmenu : listMenusBarra) {
//            listMenuPop = objmenu.getChildren();
//            for (Component objmenup : listMenuPop) {
//                listItem = objmenup.getChildren();
//                listtoolButton = barraProcesos.getChildren();
//                for (Component objmenuItem : listItem) {
//                    idMenuItem = objmenuItem.getId();
//                    for (CsPerxgru objpermiso : listaper) {
//                        idMenuPermiso = objpermiso.getCsPerxgruPK().getCodPer();
//                        ban = (idMenuItem.equals(idMenuPermiso));
//                        if (ban) {
//                            Menuitem menu = (Menuitem) objmenuItem;
//                            menu.setDisabled(false);
//                            for (Component toolButton : listtoolButton) {
//                                Toolbarbutton toolBarButton = (Toolbarbutton) toolButton;
//                                String x = toolButton.getId();
//                                String idTB = x.substring(1, x.length());
//                                if (idMenuPermiso.equals(idTB)) {
//                                    toolBarButton.setDisabled(false);
//                                }
//                            }
//                        }
//                    }//fin permisos
//                }//fin for menu item
//            }//fin menupop
//        }//fin Menus
//    }
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

    private void createMenuItem(Element menuitemElement, Menupopup menupopup) {
        createMenuItem(menuitemElement, menupopup, false);
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
    }

//    private void loadPermissionUser() {
//        admUsuario = new AdmiNegocio();
//        admiSessionUsuario = new ProcesosSession();
//        listaper = (List<CsPerxgru>) admiSessionUsuario.ObtenerAtributoSession(6, session);
//
//    }
//
//    private void ModificarSession() {
//        permisosMenuID = new CsPerxgru();
//        //PARA VALIDAR SI TIENE ESE PERMISO
//        admiSessionUsuario = new ProcesosSession();
//        if (permisosMenuID != null) {
//            admiSessionUsuario.AgregarAtributoSession(6, listaper, session);
//        }
//    }
}

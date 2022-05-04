package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.modelo.core.*;
import ec.com.cubosoft.avamed.modelo.nextla.SUsuar;
import ec.com.cubosoft.avamed.modelo.nextla.sessionOk;
import ec.com.cubosoft.avamed.modelo.publico.Iso3166R2;
import ec.com.cubosoft.avamed.procesos.AdmiNegocioSql;
import java.util.List;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbar;

/**
 * @author Administrador
 */
public class Controladoramenus extends GenericForwardComposer {

    //<editor-fold defaultstate="expanded" desc="Variables">
    //INICIAR SESSION
    //Tipos de datos Genericos
    String usu = "", pas = "";
    Boolean banderaC = false;
    //Entidades y Clases
    AdmiNegocioSql admUsuario;
    private SUsuar usuarioN;
    private CsUsuarios usuarioP;
    private List perUsuAva;
    private String pagina;
    private List perUsuNext;
    CsPerxgru permisosMenuID;
    Iso3166R2 selectedCiudad;
    Textbox id_user, id_password;
    Combobox id_ciudad;
    Menubar cabeceraMenu;
    Toolbar barraProcesos;
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
    private sessionOk objsessiActica;

    public String getUsuario() {
        String usuario = null;
        ProcesosSession admiSessionUsuario = new ProcesosSession();
        objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, session);
        if (objsessiActica != null) {
            banderaC = false;
            usuario = objsessiActica.getUsuario() + " / " + objsessiActica.getGrupo();
        } else {
            usuario = "Session inactiva";
            alert("Session inactiva");
        }
        return usuario;
    }

}

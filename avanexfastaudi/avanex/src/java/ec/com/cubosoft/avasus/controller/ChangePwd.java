/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avasus.controller;

import ec.com.cubosoft.avamed.coneccion.ProcesosSession;
import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import javax.naming.NamingException;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

/**
 *
 * @author Juan Pablo Chavez
 *
 */
public class ChangePwd extends GenericForwardComposer {

    ProcesosSession sessionUser;
    CsUsuarios user;
    String usuario, nomUsuario;

    AdmiNegocio adminNegocio;
    //
    Textbox idUser, idPassword, idNewPassword, idNewPassword2;

    public void onCreate$changePwd() {
        sessionUser = new ProcesosSession();
        user = (CsUsuarios) sessionUser.ObtenerAtributoSession(2, session);
        usuario = user.getUsuario().toUpperCase();
        nomUsuario = user.getNomUsu();
    }

    public String getUsuario() {
        return usuario;
    }

    public String getNameUsuario() {
        return nomUsuario;
    }

    public void onClick$cancelar() {
        execution.sendRedirect("/ocupacional/menu.zul");
    }

    public void onClick$aceptar() throws InterruptedException, NamingException {

        if (!loadUser()) {
            Messagebox.show("Usuario o Clave actual Incorrecta", "No se puede proceder a cambiar su clave.",
                    Messagebox.OK, Messagebox.ERROR);
            return;
        }

        if (isDefaultNewPassword()) {
            Messagebox.show("La nueva clave que usted digitó es muy simple, por favor intente con otra clave",
                    "No se puede proceder a cambiar su clave.",
                    Messagebox.OK, Messagebox.ERROR);
            return;
        }

        if (!confirmNewPassword()) {
            Messagebox.show("La nueva clave no esta correctamente confirmada 2 veces, intente nuevamente.",
                    "No se puede proceder a cambiar su clave.",
                    Messagebox.OK, Messagebox.ERROR);
            return;
        }

        if (updateUser()) {
            Messagebox.show("Su Clave ha sido cambiada correctamente", "Cambio de clave",
                    Messagebox.OK, Messagebox.INFORMATION, new EventListener() {
                @Override
                public void onEvent(Event event) throws Exception {
                    execution.sendRedirect("/ocupacional/menu.zul");
                }
            });

        } else {
            Messagebox.show("Existió un error al guardar su nueva clave.",
                    "No se pudo guardar su nueva clave.",
                    Messagebox.OK, Messagebox.ERROR);
            return;
        }

    }

    public boolean isDefaultNewPassword() {
        boolean rtn;

        if (idNewPassword.getText().equals("0000")) {
            rtn = true;
            reset();
        } else {
            rtn = false;
        }

        return rtn;
    }

    public boolean loadUser() throws NamingException {
        boolean rtn;

        adminNegocio = new AdmiNegocio();
        user = (CsUsuarios) adminNegocio.getUsuario(idUser.getText().toUpperCase(),
                idPassword.getText().toUpperCase(), false);
        if (user != null) {
            rtn = true;
        } else {
            reset();
            rtn = false;
        }

        return rtn;
    }

    public boolean confirmNewPassword() {
        boolean rtn;

        if (idNewPassword.getText().equals(idNewPassword2.getText())) {
            rtn = true;
        } else {
            rtn = false;
            reset();
        }

        return rtn;

    }

    public boolean updateUser() {
        try {
            boolean rtn;
            adminNegocio = new AdmiNegocio();
            adminNegocio = new AdmiNegocio();
            user.setPwdUsu(idNewPassword.getText().trim().toUpperCase());

            if (adminNegocio.actualizar(user)) {
                rtn = true;
            } else {
                rtn = false;
            }

            return rtn;
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void reset() {
        //idUser.setText("");
        idPassword.setText("");
        idNewPassword.setText("");
        idNewPassword2.setText("");
    }

}

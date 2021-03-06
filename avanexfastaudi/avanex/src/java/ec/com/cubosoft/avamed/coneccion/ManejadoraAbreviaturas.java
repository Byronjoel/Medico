/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.coneccion;

/**
 *
 * @author Administrador
 */
import java.util.List;
import javax.naming.NamingException;
import org.zkoss.zk.ui.util.GenericForwardComposer;


import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import ec.com.cubosoft.avamed.modelo.practica.Abreviatura;

public class ManejadoraAbreviaturas extends GenericForwardComposer {

    Abreviatura currentAbreviatura = new Abreviatura();
    AdmiNegocio objdao = new AdmiNegocio();
    List datosAbreviaturas = null;

    public Abreviatura getCurrentAbreviatura() {
        return currentAbreviatura;
    }

    public void setCurrentAbreviatura(Abreviatura currentAbreviatura) {
        this.currentAbreviatura = currentAbreviatura;
    }

    public List getAllEventsA() {
        try {
            datosAbreviaturas = objdao.getData(new Abreviatura(), true,null);
            return datosAbreviaturas;
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List getAllEventsTerminos() {
        try {
            datosAbreviaturas = objdao.getData(new Abreviatura(), true,null);
            return datosAbreviaturas;
        } catch (NamingException ex) {
            throw new RuntimeException(ex);
        }
    }
}


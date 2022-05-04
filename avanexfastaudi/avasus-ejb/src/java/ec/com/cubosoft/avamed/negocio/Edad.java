/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.avamed.negocio;

/**
 *
 * @author pc
 */
public class Edad implements InterfaceCalculoEdad {

    @Override
    public String obtenerAnios() {
        return "0 AÑOS";
    }

    @Override
    public String obtenerMeses() {
        return "0 MESES";
    }

    @Override
    public String obtenerDias() {
        return "0 DIAS";
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.avamed.utilities;

/**
 *
 * @author ruben
 */
public class IdentificarSO {

    public String nombreSO(){

        String sistema="";
        String osName = System.getProperty("os.name");
        if (osName.toUpperCase().indexOf("WINDOWS") == 0) {

            sistema="WINDOWS";
        }else if (osName.toUpperCase().indexOf("LINUX") == 0) {

            sistema="LINUX";
        }

        return sistema;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.core;
/**
 *
 * @author Administrador
 */
public class CSValidarNumeros {
    public static int isnumero = 1;
    public static boolean  validarNumero(String numero)
    {
        try {
                long l = Long.parseLong(numero);
            } catch (Exception e)
            {
                return false;
            }
        return true;
     }
}

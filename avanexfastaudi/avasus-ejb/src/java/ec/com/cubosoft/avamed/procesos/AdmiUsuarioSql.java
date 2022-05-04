/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.procesos;

import ec.com.cubosoft.avamed.jpa.AdministradorGlobalBean;

import java.util.List;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author DESARROLLADOR1
 */
public class AdmiUsuarioSql {

    private Context contexto;
    private AdministradorGlobalBean admJPA;
//    public static String contextoGenerico = "java:global/avasus/AdministradorGenericoBean";
//    public static String contextoGlobal = "java:global/avasus/AdministradorGlobalBean";
    public static String contextoGlobalSql = "java:global/avanex/AdministradorGlobalBeanSql";
//    public static String contextoGenerico;
//    public static String contextoGlobal;

    public AdmiUsuarioSql() {
    }


}



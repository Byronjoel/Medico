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
public class AdmiUsuario {

    private Context contexto;
    private AdministradorGlobalBean admJPA;
    public static String contextoGenerico = "java:global/avanex/AdministradorGenericoBean";
    public static String contextoGlobal = "java:global/avanex/AdministradorGlobalBean";
      public static String contextoGlobalSql = "java:global/avanex/AdministradorGlobalBeanSql";
//    public static String contextoGenerico;
//    public static String contextoGlobal;

    public AdmiUsuario() {
    }

   
    public String getContextoGenerico() {
        return contextoGenerico;
    }

    public String getContextoGlobal() {
        return contextoGlobal;
    }

//    public void setContextoGlobal(String contextoGlobal) {
//        AdmiUsuario.contextoGlobal = contextoGlobal;
//    }
//
//    public void setContextoGenerico(String contextoGenerico) {
//        AdmiUsuario.contextoGenerico = contextoGenerico;
//    }

   

   


   
}
//PARA VERIFICAR PERMISO
//    for (CsPerxgru objpermiso : ListPusuario) {
//            idPermiso = objpermiso.getCsPerxgruPK().getCodPer().toString();
//            String a = "AUX";
//            if (idPermiso.equals(valor)) {
//                permisosMenuID = objpermiso;
//            }
//        }


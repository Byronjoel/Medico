/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.procesos;

import ec.com.cubosoft.avamed.jpa.AdministradorGlobalBean;
import ec.com.cubosoft.avamed.jpa.AdministradorGlobalBeanSql;
import static ec.com.cubosoft.avamed.procesos.AdmiUsuario.contextoGlobal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Administrador
 */
public class AdmiNegocioSql {

    public Context contexto;

//<editor-fold defaultstate="expanded" desc="SQL BASICOS GENERALES">
    public Object guardar(Object obj) throws NamingException {
        Object dato = null;
        AdministradorGlobalBeanSql admJPAG;
        contexto = new InitialContext();
        admJPAG = (AdministradorGlobalBeanSql) contexto.lookup(AdmiUsuario.contextoGlobalSql);
        try {
            dato = admJPAG.guardar(obj);
            return dato;
        } catch (Exception e) {

            System.out.println("Error guardar+" + e.getMessage());
            return null;
        }
        //return dato;
    }

    public boolean actualizar(Object obj) throws NamingException {
        boolean dato = false;
        AdministradorGlobalBeanSql admJPAG;
        contexto = new InitialContext();
        admJPAG = (AdministradorGlobalBeanSql) contexto.lookup(AdmiUsuario.contextoGlobalSql);
         try {
             dato = admJPAG.actualizar(obj);
        return dato;
        } catch (Exception e) {

            System.out.println("Error guardar+" + e.getMessage());
            return false;
        }
      
    }

    public boolean eliminar(Object obj) throws NamingException {
        boolean dato = false;
        AdministradorGlobalBeanSql admJPAG;
        contexto = new InitialContext();
        admJPAG = (AdministradorGlobalBeanSql) contexto.lookup(AdmiUsuario.contextoGenerico);
        dato = admJPAG.eliminar(obj);

        return dato;
    }

    //</editor-fold>
//<editor-fold defaultstate="expanded" desc="JPA2 P">
    public Object getGrupoUsuario(String usuario, String pass) {
        Object dato = null;
        AdministradorGlobalBean admJPA;
        try {
            contexto = new InitialContext();
            admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
            dato = admJPA.getGrupoUsuario(usuario, pass);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            e.printStackTrace(System.out);
        }

        return dato;
    }

    public List<Object> getData(Object table, Map<String, Object> where, List group, List order) throws NamingException {
        List data = null;
        List<String> vacios = new ArrayList<>();
        //Se verifica que parametros estan vacios
        for (Map.Entry<String, Object> entry : where.entrySet()) {
            if ((entry.getValue() == null) || (entry.getValue().toString().isEmpty())) {
                vacios.add(entry.getKey());
            } else {
                //Se remueven los filtros que digan (Todos), (Varios a la vez)
                if (entry.getValue().equals("(Todos)")) {
                    vacios.add(entry.getKey());
                }
            }
        }
        //Se remueven los vacios
        for (String clave : vacios) {
            where.remove(clave);
            //System.out.print(clave);
        }
        for (String vacio : vacios) {
        }
        for (Map.Entry<String, Object> entry : where.entrySet()) {
            //System.out.print(entry.getKey() + " valor " + entry.getValue() + " Clase " + entry.getValue().getClass());
        }
        AdministradorGlobalBeanSql admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBeanSql) contexto.lookup(AdmiUsuario.contextoGlobalSql);
        try {
            //by patty
            data = admJPA.getDBData(table, where, group, order);
            //byErick
            //data = admJPA.getDBDatabyDate(table, where, group, order);
            //data = admJPA.getDBtEST(table, where, group, order);
            return data;
        } catch (Exception e) {
            return null;
        }

    }

    public List<Object> getDataSearchNestlab(Object table, Map<String, Object> where, List group, List order) throws NamingException {
        List data = null;
        List<String> vacios = new ArrayList<>();
        //Se verifica que parametros estan vacios
        for (Map.Entry<String, Object> entry : where.entrySet()) {
            if ((entry.getValue() == null) || (entry.getValue().toString().isEmpty())) {
                vacios.add(entry.getKey());
            } else {
                //Se remueven los filtros que digan (Todos), (Varios a la vez)
                if (entry.getValue().equals("(Todos)")) {
                    vacios.add(entry.getKey());
                }
            }
        }
        //Se remueven los vacios
        for (String clave : vacios) {
            where.remove(clave);
            //System.out.print(clave);
        }
        for (String vacio : vacios) {
        }
        for (Map.Entry<String, Object> entry : where.entrySet()) {
            //System.out.print(entry.getKey() + " valor " + entry.getValue() + " Clase " + entry.getValue().getClass());
        }
        AdministradorGlobalBeanSql admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBeanSql) contexto.lookup(AdmiUsuario.contextoGlobalSql);
        try {
            //by patty
            data = admJPA.getDBData(table, where, group, order);
            //byErick
            //data = admJPA.getDBDatabyDate(table, where, group, order);
            //data = admJPA.getDBtEST(table, where, group, order);
            return data;
        } catch (Exception e) {
            return null;
        }

    }
    public Object getDataObj(Object table, Map<String, Object> where, List group, List order) throws NamingException {
        Object data = null;
        List<String> vacios = new ArrayList<String>();
        //Se verifica que parametros estan vacios
        for (Map.Entry<String, Object> entry : where.entrySet()) {
            if ((entry.getValue() == null) || (entry.getValue().toString().isEmpty())) {
                vacios.add(entry.getKey());
            } else {
                //Se remueven los filtros que digan (Todos), (Varios a la vez)
                if (entry.getValue().equals("(Todos)")) {
                    vacios.add(entry.getKey());
                }
            }
        }
        //Se remueven los vacios
        for (String clave : vacios) {
            where.remove(clave);
            //System.out.print(clave);
        }
        for (String vacio : vacios) {

        }
        for (Map.Entry<String, Object> entry : where.entrySet()) {
            //    System.out.print(entry.getKey() + " valor " + entry.getValue() + " Clase " + entry.getValue().getClass());
        }
        AdministradorGlobalBeanSql admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBeanSql) contexto.lookup(AdmiUsuario.contextoGlobalSql);
        try {
            data = admJPA.getDBDataObj(table, where, group, order);
            return data;
        } catch (Exception e) {
            return null;
        }

    }

    //</editor-fold>
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.jpa;

import ec.com.cubosoft.avamed.modelo.practica.FormatoXAntecedentes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author pc
 */
@Stateless
public class AdministradorGenericoBean extends GenericoDAOP<Object> {

    public AdministradorGenericoBean() {
    }

 public Context contexto;
public FormatoXAntecedentes getTodoParametros(String entidad, String campo, String valor, String campo1, String valor1, Class tipo, Class tipo1, String orden) {
        List<Object> lista = null;
        FormatoXAntecedentes objformato = null;
        String clase = tipo.getName().substring(10);
        String clase1 = tipo1.getName().substring(10);
        try {
            String s = "SELECT o FROM " + entidad + " o WHERE o." + campo + " = :valor "
                    + " and o." + campo1 + " = :valor1 ORDER BY o." + orden + "";
            Query q = emP.createQuery(s);//.setParameter("valor", valor);
            if (clase.equals("Long")) {
                q.setParameter("valor", new Long(valor));
            }
            if (clase.equals("Integer")) {
                q.setParameter("valor", new Integer(valor));
            }
            if (clase.equals("BigInteger")) {
                q.setParameter("valor", new BigInteger(valor));
            }
            if (clase.equals("String")) {
                q.setParameter("valor", valor);
            }
            if (clase1.equals("Long")) {
                q.setParameter("valor1", new Long(valor1));
            }
            if (clase1.equals("Integer")) {
                q.setParameter("valor1", new Integer(valor1));
            }
            if (clase1.equals("BigInteger")) {
                q.setParameter("valor1", new BigInteger(valor1));
            }
            if (clase1.equals("String")) {
                q.setParameter("valor1", valor1);
            }

            lista = q.getResultList();
            if (lista.size() == 1) {
                objformato = (FormatoXAntecedentes) lista.get(0);
            }
        } catch (Exception e) {
            throw new PersistenceException("Error al consultar los datos", e);
        }
        return objformato;
    }
}

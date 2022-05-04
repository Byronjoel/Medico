/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @param <T>
 * @author Ruben
 */
public class GenericoDAOP<T> implements GenericoInterface<T> {

    @PersistenceContext(unitName = "AvasusJPAPU")
    protected EntityManager emP;

    @Override
    public T guardar(T entidad) {
        try {
            emP.persist(entidad);
            
            //  emP.refresh(entidad);
            // emP.getTransaction().commit();
            return entidad;
        } catch (Exception e) {
            System.out.print(e.getCause().toString());
            e.printStackTrace(System.out);
            return null;
        }

    }

    @Override
    public boolean actualizar(T entidad) {
        try {
                       
            emP.merge(entidad);
            //   emP.refresh(entidad);
            // emP.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.print(e.getCause().toString());
            e.printStackTrace(System.out);
            return false;
        }
    }

    @Override
    public boolean eliminar(T entidad) {
        try {
            emP.remove(emP.merge(entidad));
            //    emP.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    @Override
    public T buscarPorId(Class entidad, Object id) {
        return (T) emP.find(entidad, id);
    }

}

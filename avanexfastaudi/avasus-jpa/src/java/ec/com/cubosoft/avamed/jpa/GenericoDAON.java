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
public class GenericoDAON<T> implements GenericoInterface<T> {

    @PersistenceContext(unitName = "NextlabJPAPU")
    protected EntityManager emN;

    @Override
    public T guardar(T entidad) {
        emN.persist(entidad);
        return entidad;
    }

    @Override
    public boolean actualizar(T entidad) {
        try {
     //       emN.getTransaction().begin();
            emN.merge(entidad);
        //    emN.getTransaction().commit();
            return true;
        } catch (Exception e) {
            System.out.print(e.getCause().toString());
            e.printStackTrace(System.out);
            return false;
        } finally {
       //     if (emN.getTransaction().isActive()) {
        //        emN.getTransaction().rollback();
        //    }
        }
    }

    @Override
    public boolean eliminar(T entidad) {
        try {
            emN.remove(emN.merge(entidad));
            //     emN.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return false;
        }
    }

    @Override
    public T buscarPorId(Class entidad, Object id) {
        return (T) emN.find(entidad, id);
    }

}

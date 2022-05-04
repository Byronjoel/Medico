/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.avamed.jpa;

/**
 *
 * @author Ruben
 */
public interface GenericoInterface<T> {

    T guardar(T entidad);
    boolean actualizar(T entidad);
    boolean eliminar(T entidad);
    T buscarPorId(Class entidad,Object id);
}

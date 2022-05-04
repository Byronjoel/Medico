/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.core;

import java.io.Serializable;
import javax.sql.DataSource;
//import org.apache.commons.dbcp.BasicDataSource;


public class CSDataBaseFactory implements Serializable {

    /**
     * Nombre del archivo de propiedades donde se define el nombre
     * de la clave DataSourceName con el nombre de la fuente de datos
     */
    private String archivoDePropiedades = "ApplicationResources";
    /**
     * Fuente de datos
     */
    private DataSource dataSource;
    /**
     * Verdadero si la fuente de datos está cargado correctamente
     */
    private boolean enabled = false;
    /**
     * La instacia de ésta clase
     */
    private static CSDataBaseFactory SINGLETON = new CSDataBaseFactory();
    /**
     * Constructor, es privado por lo tanto no se puede crear objetos de esta clase
     * Nombre de la fuente de datos, esta debe estar en el archivo Properties
     */
    public CSDataBaseFactory() {
        try {
            conectarFuenteDeDatos();
        } catch (Exception ex) {
        }
    }
    private void conectarFuenteDeDatos() throws Exception {
        enabled = false;
        //conexión a BD para ambiente escritorio
        // por el momento no utilizo archivo de propiedades, pero debería
        try {
            //log.fatal("conectarFuenteDeDatos() Conectando a base de datos utilizando BasicDatasource");
//            BasicDataSource ds = new BasicDataSource();
//            ds.setDriverClassName("org.postgresql.Driver");
//
//            ds.setUsername("postgres");
//            ds.setPassword("postgres");
//            ds.setUrl("jdbc:postgresql://localhost:5432/avasus");
//            dataSource = ds;
        } catch (Exception e) {
            //  log.fatal("conectarFuenteDeDatos() " + e);
            throw new Exception("No se puede recuperar la conexión a la BD", e);
        }
    }
    /**
     * Obtiene la fuente de datos representado como un objeto de tipo DataSource
     * @return la fuente de datos
     * @throws java.lang.Exception
     */
    public DataSource getDataSource() throws Exception {
        if (dataSource == null) {
            //log.info("getDataSource() Intentando recuperar la fuente de datos..");
            try {
                conectarFuenteDeDatos();
            } catch (Exception ex) {
                //log.fatal("getDataSource() " + ex);
                throw ex;
            }
        } else {
            enabled = true;
        }
        return dataSource;

    }
    /**
     * Permite setear la fuente de datos
     * @param dataSource la fuente de datos
     */
    public void setDataSource(DataSource dataSource) {
        if (dataSource == null) {
            enabled = false;
        }
        this.dataSource = dataSource;
        enabled = true;
    }
    /**
     * Devuelve true o false según exista o nó la fuente de datos
     * @return true si el dataSource está activa, caso contrario false
     */
    public boolean isEnabled() {
        return enabled;
    }
    /**
     * Obtiene una referencia al objeto de tipo BeanDataBaseFactory
     * este objeto es único dentro de la aplicación
     * @return el objeto
     */
    public static CSDataBaseFactory getInstance() {
        return SINGLETON;
    }
}

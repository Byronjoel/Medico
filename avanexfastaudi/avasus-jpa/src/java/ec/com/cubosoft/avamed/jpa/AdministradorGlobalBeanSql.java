/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.jpa;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author pc
 */
@Stateless

public class AdministradorGlobalBeanSql extends GenericoDAON<Object> {

    /*
     * Constructor de la clase AdministradorGlobalBean
     */
    public AdministradorGlobalBeanSql() {
    }

    public List getDBData(Object tabla, Map<String, Object> where, List group, List order) {
        try {
            List data;
            List datos;

            CriteriaBuilder cb = emN.getCriteriaBuilder();
            //   LockModeType obj = emN.getLockMode(tabla);
            CriteriaQuery<Object> cq = cb.createQuery();
            Root from = cq.from(tabla.getClass());
            Predicate p = cb.conjunction();
            for (Map.Entry<String, Object> param : where.entrySet()) {
                String columna, condicion, foranea;
                //Sacamos nombre de columna
                if (!param.getKey().contains("?")) {
                    columna = param.getKey().trim();
                    condicion = "=";
                } else {
                    columna = param.getKey().substring(0, param.getKey().indexOf("?")).trim();
                    condicion = param.getKey().substring(param.getKey().indexOf("?") + 1).trim();
                }
                //Validacion de la columna en otra tabla, es decir si es foranea y otro Objeto
                if (columna.contains(".")) {
                    foranea = columna.substring(0, param.getKey().indexOf(".")).trim();
                    columna = columna.substring(param.getKey().indexOf(".") + 1).trim();
                    Path<String> subpath = null;
                    if (columna.contains(".")) {    //Segunda Tabla Foranea
                        String foranea2 = null;
                        foranea2 = columna.substring(0, columna.indexOf(".")).trim();
                        columna = columna.substring(columna.indexOf(".") + 1).trim();
                        if (columna.contains(".")) {
                            String foranea3 = null;
                            foranea3 = columna.substring(0, columna.indexOf(".")).trim();
                            columna = columna.substring(columna.indexOf(".") + 1).trim();
                            subpath = from.join(foranea).join(foranea2).join(foranea3).get(columna);
                        } else {
                            subpath = from.join(foranea).join(foranea2).get(columna);
                        }
                    } else {
                        subpath = from.join(foranea).get(columna);
                    }
                    if (condicion.equals("like")) {
                        p = cb.and(p, cb.like(subpath, "%" + param.getValue().toString() + "%"));
                    } else if (condicion.equals(">=")) {
                        //Validacion del Tipo de Clase de Objeto que viene
                        switch (param.getValue().getClass().getSimpleName()) {
                            case "Date":
                                Date fecha = (Date) param.getValue();
                                p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(Date.class), fecha));
                                break;
                            case "String":
                                p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                                break;
                            case "Integer":
                                p = cb.and(p, cb.ge(subpath.as(Integer.class), new Integer(param.getValue().toString())));
                                break;
                            case "Long":
                                p = cb.and(p, cb.ge(subpath.as(Long.class), new Long(param.getValue().toString())));
                                break;
                            case "BigDecimal":
                                p = cb.and(p, cb.ge(subpath.as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                                break;
                            case "BigInteger":
                                p = cb.and(p, cb.ge(subpath.as(BigInteger.class), new BigInteger(param.getValue().toString())));
                                break;
                            default:
                                p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                                break;
                        }
                    } else if (condicion.equals("<=")) {
                        //Validacion del Tipo de Clase de Objeto que viene
                        switch (param.getValue().getClass().getSimpleName()) {
                            case "Date":
                                Date fecha = (Date) param.getValue();
                                p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(Date.class), fecha));
                                break;
                            case "String":
                                p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                                break;
                            case "Integer":
                                p = cb.and(p, cb.le(subpath.as(Integer.class), new Integer(param.getValue().toString())));
                                break;
                            case "Long":
                                p = cb.and(p, cb.le(subpath.as(Long.class), new Long(param.getValue().toString())));
                                break;
                            case "BigDecimal":
                                p = cb.and(p, cb.le(subpath.as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                                break;
                            case "BigInteger":
                                p = cb.and(p, cb.le(subpath.as(BigInteger.class), new BigInteger(param.getValue().toString())));
                                break;
                            default:
                                p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                                break;
                        }
                    } else {
                        p = cb.and(p, cb.equal(subpath, param.getValue()));
                    }
                } else {    //Si son Columnas propias ya entran directo al where
                    switch (condicion) {
                        case "like":
                            p = cb.and(p, cb.like(from.get(columna), "%" + param.getValue().toString() + "%"));
                            break;
                        case "not like":
                            p = cb.and(p, cb.notLike(from.get(columna), "%" + param.getValue().toString() + "%"));
                            break;
                        case ">=":
                            //Validacion del Tipo de Clase de Objeto que viene
                            switch (param.getValue().getClass().getSimpleName()) {
                                case "Date":
                                    Date fecha = (Date) param.getValue();
                                    p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(Date.class), fecha));
                                    break;
                                case "String":
                                    p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                                    break;
                                case "Integer":
                                    p = cb.and(p, cb.ge(from.get(columna).as(Integer.class), new Integer(param.getValue().toString())));
                                    break;
                                case "Long":
                                    p = cb.and(p, cb.ge(from.get(columna).as(Long.class), new Long(param.getValue().toString())));
                                    break;
                                case "BigDecimal":
                                    p = cb.and(p, cb.ge(from.get(columna).as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                                    break;
                                case "BigInteger":
                                    p = cb.and(p, cb.ge(from.get(columna).as(BigInteger.class), new BigInteger(param.getValue().toString())));
                                    break;
                                default:
                                    p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                                    break;
                            }
                            break;
                        case "<=":
                            //Validacion del Tipo de Clase de Objeto que viene
                            switch (param.getValue().getClass().getSimpleName()) {
                                case "Date":
                                    Date fecha = (Date) param.getValue();
                                    p = cb.and(p, cb.lessThanOrEqualTo(from.get(columna).as(Date.class), fecha));
                                    break;
                                case "String":
                                    p = cb.and(p, cb.lessThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                                    break;
                                case "Integer":
                                    p = cb.and(p, cb.le(from.get(columna).as(Integer.class), new Integer(param.getValue().toString())));
                                    break;
                                case "Long":
                                    p = cb.and(p, cb.le(from.get(columna).as(Long.class), new Long(param.getValue().toString())));
                                    break;
                                case "BigDecimal":
                                    p = cb.and(p, cb.le(from.get(columna).as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                                    break;
                                case "BigInteger":
                                    p = cb.and(p, cb.le(from.get(columna).as(BigInteger.class), new BigInteger(param.getValue().toString())));
                                    break;
                                default:
                                    p = cb.and(p, cb.lessThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                                    break;
                            }
                            break;
                        case "!":
                            //Validacion del Tipo de Clase de Objeto que viene
                            p = cb.and(p, cb.equal(from.get(columna), param.getValue()));
//                        System.out.println(param.getValue().toString());
                            break;
                        default:
                            p = cb.and(p, cb.equal(from.get(columna), param.getValue()));
//                        System.out.println(param.getValue().toString());
                            break;
                    }
                }
                //p = cb.and(p, cb.equal(from.get(param.getKey()), param.getValue()));
            }
            cq.where(p);
            cq.distinct(true);
            if (order != null) {
                List orden = new ArrayList();
                for (Object item : order) {
                    if (item.toString().contains(".")) {
                        String foranea, columna;
                        foranea = item.toString().substring(0, item.toString().indexOf(".")).trim();
                        columna = item.toString().substring(item.toString().indexOf(".") + 1).trim();
                        orden.add(cb.desc(from.join(foranea).get(columna)));
                        //cq.orderBy(cb.asc(from.join(foranea).get(columna)));
                    } else {
                        orden.add(cb.desc(from.get(item.toString())));
                    }
                }
                cq.orderBy(orden);
            }
            TypedQuery<Object> typedQuery = emN.createQuery(cq);
            data = typedQuery.getResultList();
            for (Object dato : data) {
                emN.refresh(dato);
            }
            return data;
        } catch (Exception e) {
            return null;
        } finally {
            //     emN.getTransaction().commit();
            // emN.close();
        }
    }

    public List getDBDatabyDate(Object tabla, Map<String, Object> where, List group, List order) {
        try {
            List data;
            List datos;

            CriteriaBuilder cb = emN.getCriteriaBuilder();
            //   LockModeType obj = emN.getLockMode(tabla);
            CriteriaQuery<Object> cq = cb.createQuery();

            Root from = cq.from(tabla.getClass());
            Predicate p = cb.conjunction();
            for (Map.Entry<String, Object> param : where.entrySet()) {
                String columna, condicion, foranea;
                //Sacamos nombre de columna
                if (!param.getKey().contains("?")) {
                    columna = param.getKey().trim();

                    condicion = "=";
                } else {
                    columna = param.getKey().substring(0, param.getKey().indexOf("?")).trim();
                    // System.out.println("getkey " + param.getKey().indexOf("?"));
                    // System.out.println("columna " + param.getKey().substring(0, param.getKey().indexOf("?")).trim());
                    condicion = param.getKey().substring(param.getKey().indexOf("?") + 1).trim();
                    // System.out.println("condicion " + condicion);
                    // System.out.println("switch " + param.getValue().getClass().getSimpleName());

                }
                //Validacion de la columna en otra tabla, es decir si es foranea y otro Objeto
                if (columna.contains(".")) {
                    foranea = columna.substring(0, param.getKey().indexOf(".")).trim();
                    columna = columna.substring(param.getKey().indexOf(".") + 1).trim();
                    Path<String> subpath = null;
                    if (columna.contains(".")) {    //Segunda Tabla Foranea
                        String foranea2 = null;
                        foranea2 = columna.substring(0, columna.indexOf(".")).trim();
                        columna = columna.substring(columna.indexOf(".") + 1).trim();
                        if (columna.contains(".")) {
                            String foranea3 = null;
                            foranea3 = columna.substring(0, columna.indexOf(".")).trim();
                            columna = columna.substring(columna.indexOf(".") + 1).trim();
                            subpath = from.join(foranea).join(foranea2).join(foranea3).get(columna);
                        } else {
                            subpath = from.join(foranea).join(foranea2).get(columna);
                        }
                    } else {
                        subpath = from.join(foranea).get(columna);
                    }
                    if (condicion.equals("like")) {
                        p = cb.and(p, cb.like(subpath, "%" + param.getValue().toString() + "%"));
                    } else if (condicion.equals(">=")) {
                        //Validacion del Tipo de Clase de Objeto que viene
                        switch (param.getValue().getClass().getSimpleName()) {
                            case "Date":
                                Date fecha = (Date) param.getValue();
                                p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(Date.class), fecha));
                                break;
                            case "String":
                                p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                                break;
                            case "Integer":
                                p = cb.and(p, cb.ge(subpath.as(Integer.class), new Integer(param.getValue().toString())));
                                break;
                            case "Long":
                                p = cb.and(p, cb.ge(subpath.as(Long.class), new Long(param.getValue().toString())));
                                break;
                            case "BigDecimal":
                                p = cb.and(p, cb.ge(subpath.as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                                break;
                            case "BigInteger":
                                p = cb.and(p, cb.ge(subpath.as(BigInteger.class), new BigInteger(param.getValue().toString())));
                                break;
                            default:
                                p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                                break;
                        }
                    } else if (condicion.equals("<=")) {
                        //Validacion del Tipo de Clase de Objeto que viene
                        switch (param.getValue().getClass().getSimpleName()) {
                            case "Date":
                                Date fecha = (Date) param.getValue();
                                p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(Date.class), fecha));
                                break;
                            case "String":
                                p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                                break;
                            case "Integer":
                                p = cb.and(p, cb.le(subpath.as(Integer.class), new Integer(param.getValue().toString())));
                                break;
                            case "Long":
                                p = cb.and(p, cb.le(subpath.as(Long.class), new Long(param.getValue().toString())));
                                break;
                            case "BigDecimal":
                                p = cb.and(p, cb.le(subpath.as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                                break;
                            case "BigInteger":
                                p = cb.and(p, cb.le(subpath.as(BigInteger.class), new BigInteger(param.getValue().toString())));
                                break;
                            default:
                                p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                                break;
                        }
                    } else {
                        p = cb.and(p, cb.equal(subpath, param.getValue()));
                    }
                } else {    //Si son Columnas propias ya entran directo al where
                    switch (condicion) {
                        case "like":
                            p = cb.and(p, cb.like(from.get(columna), "%" + param.getValue().toString() + "%"));
                            break;
                        case "not like":
                            p = cb.and(p, cb.notLike(from.get(columna), "%" + param.getValue().toString() + "%"));
                            break;
                        case ">=":
                            //Validacion del Tipo de Clase de Objeto que viene
                            switch (param.getValue().getClass().getSimpleName()) {

                                case "Date":
                                    Date fecha = (Date) param.getValue();
                                    p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(Date.class), fecha));
                                    break;
                                case "String":
                                    p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                                    break;
                                case "Integer":
                                    p = cb.and(p, cb.ge(from.get(columna).as(Integer.class), new Integer(param.getValue().toString())));
                                    break;
                                case "Long":
                                    p = cb.and(p, cb.ge(from.get(columna).as(Long.class), new Long(param.getValue().toString())));
                                    break;
                                case "BigDecimal":
                                    p = cb.and(p, cb.ge(from.get(columna).as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                                    break;
                                case "BigInteger":
                                    p = cb.and(p, cb.ge(from.get(columna).as(BigInteger.class), new BigInteger(param.getValue().toString())));
                                    break;
                                default:
                                    p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                                    break;
                            }
                            break;
                        case "<=":
                            //Validacion del Tipo de Clase de Objeto que viene
                            switch (param.getValue().getClass().getSimpleName()) {
                                case "Date":
                                    Date fecha = (Date) param.getValue();
                                    p = cb.and(p, cb.lessThanOrEqualTo(from.get(columna).as(Date.class), fecha));
                                    break;
                                case "String":
                                    p = cb.and(p, cb.lessThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                                    break;
                                case "Integer":
                                    p = cb.and(p, cb.le(from.get(columna).as(Integer.class), new Integer(param.getValue().toString())));
                                    break;
                                case "Long":
                                    p = cb.and(p, cb.le(from.get(columna).as(Long.class), new Long(param.getValue().toString())));
                                    break;
                                case "BigDecimal":
                                    p = cb.and(p, cb.le(from.get(columna).as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                                    break;
                                case "BigInteger":
                                    p = cb.and(p, cb.le(from.get(columna).as(BigInteger.class), new BigInteger(param.getValue().toString())));
                                    break;
                                default:
                                    p = cb.and(p, cb.lessThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                                    break;
                            }
                            break;
                        case "!":
                            //Validacion del Tipo de Clase de Objeto que viene
                            p = cb.and(p, cb.equal(from.get(columna), param.getValue()));
//                        System.out.println(param.getValue().toString());
                            break;
                        default:
                            //Date fecha = (Date) param.getValue();
                            //p = cb.and(p, cb.lessThanOrEqualTo(from.get(columna).as(Date.class), fecha));
                            p = cb.and(p, cb.equal(from.get(columna), param.getValue()));
                            //System.out.println("qwe " + cb.toString() + "asd " + p);
                            //System.out.println(columna + " orden" + param.getValue().toString());
                            break;
                    }
                }
                //p = cb.and(p, cb.equal(from.get(param.getKey()), param.getValue()));
                //Date fecha = (Date) param.getValue();
                // p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(Date.class), fecha));
                //p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(Date.class), "2019-10-03"));
                //p = (cb.like(cb.lower(from.get(columna).as(String.class)), "%" + param.getValue() + "%"));
            }

            cq.where(p);
            cq.distinct(true);

            if (order != null) {
                List orden = new ArrayList();
                for (Object item : order) {
                    if (item.toString().contains(".")) {
                        String foranea, columna;
                        foranea = item.toString().substring(0, item.toString().indexOf(".")).trim();
                        columna = item.toString().substring(item.toString().indexOf(".") + 1).trim();
                        orden.add(cb.desc(from.join(foranea).get(columna)));
                        //cq.orderBy(cb.asc(from.join(foranea).get(columna)));
                    } else {
                        orden.add(cb.desc(from.get(item.toString())));
                    }
                }
                cq.orderBy(orden);
            }

            // TypedQuery<Object> query = emN.createQuery("SELECT c from dbo.lispet_avanex c where c.");
            TypedQuery<Object> typedQuery = emN.createQuery(cq);
            data = typedQuery.getResultList();
            for (Object dato : data) {
                emN.refresh(dato);
            }
            return data;
        } catch (Exception e) {
            return null;
        } finally {
            //     emN.getTransaction().commit();
            // emN.close();
        }
    }

    public List getDBtEST(Object tabla, Map<String, Object> where, List group, List order) {
        List data;
        EntityManager em = emN.getEntityManagerFactory().createEntityManager();

        CriteriaBuilder cb = emN.getCriteriaBuilder();
        //   LockModeType obj = emN.getLockMode(tabla);
        CriteriaQuery<Object> cq = cb.createQuery();
        Root from = cq.from(tabla.getClass());
        Predicate p = cb.conjunction();

        String query = "SELECT nro_Ord from dbo.lispet_avanex c where c.nro_Ord = :order";

        try {
            TypedQuery<Object> typedQuery = (TypedQuery<Object>) emN.createQuery(query, tabla.getClass());
            // TypedQuery<Object> typedQuery = emN.createQuery(cq);
            data = typedQuery.getResultList();
            for (Object dato : data) {
                emN.refresh(dato);
            }
            return data;
        } catch (Exception e) {
            return null;
        }

    }

    public Object getDBDataObj(Object tabla, Map<String, Object> where, List group, List order) {
        try {
            Object data;
            List datos;
            CriteriaBuilder cb = emN.getCriteriaBuilder();

            CriteriaQuery<Object> cq = cb.createQuery();
            Root from = cq.from(tabla.getClass());
            Predicate p = cb.conjunction();
            for (Map.Entry<String, Object> param : where.entrySet()) {
                String columna, condicion, foranea;
                //Sacamos nombre de columna
                if (!param.getKey().contains("?")) {
                    columna = param.getKey().trim();
                    condicion = "=";
                } else {
                    columna = param.getKey().substring(0, param.getKey().indexOf("?")).trim();
                    condicion = param.getKey().substring(param.getKey().indexOf("?") + 1).trim();
                }
                //Validacion de la columna en otra tabla, es decir si es foranea y otro Objeto
                if (columna.contains(".")) {
                    foranea = columna.substring(0, param.getKey().indexOf(".")).trim();
                    columna = columna.substring(param.getKey().indexOf(".") + 1).trim();
                    Path<String> subpath = null;
                    if (columna.contains(".")) {    //Segunda Tabla Foranea
                        String foranea2 = null;
                        foranea2 = columna.substring(0, columna.indexOf(".")).trim();
                        columna = columna.substring(columna.indexOf(".") + 1).trim();
                        if (columna.contains(".")) {
                            String foranea3 = null;
                            foranea3 = columna.substring(0, columna.indexOf(".")).trim();
                            columna = columna.substring(columna.indexOf(".") + 1).trim();
                            subpath = from.join(foranea).join(foranea2).join(foranea3).get(columna);
                        } else {
                            subpath = from.join(foranea).join(foranea2).get(columna);
                        }
                    } else {
                        subpath = from.join(foranea).get(columna);
                    }
                    if (condicion.equals("like")) {
                        p = cb.and(p, cb.like(subpath, "%" + param.getValue().toString() + "%"));
                    } else if (condicion.equals(">=")) {
                        //Validacion del Tipo de Clase de Objeto que viene
                        switch (param.getValue().getClass().getSimpleName()) {
                            case "Date":
                                Date fecha = (Date) param.getValue();
                                p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(Date.class), fecha));
                                break;
                            case "String":
                                p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                                break;
                            case "Integer":
                                p = cb.and(p, cb.ge(subpath.as(Integer.class), new Integer(param.getValue().toString())));
                                break;
                            case "Long":
                                p = cb.and(p, cb.ge(subpath.as(Long.class), new Long(param.getValue().toString())));
                                break;
                            case "BigDecimal":
                                p = cb.and(p, cb.ge(subpath.as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                                break;
                            case "BigInteger":
                                p = cb.and(p, cb.ge(subpath.as(BigInteger.class), new BigInteger(param.getValue().toString())));
                                break;
                            default:
                                p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                                break;
                        }
                    } else if (condicion.equals("<=")) {
                        //Validacion del Tipo de Clase de Objeto que viene
                        switch (param.getValue().getClass().getSimpleName()) {
                            case "Date":
                                Date fecha = (Date) param.getValue();
                                p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(Date.class), fecha));
                                break;
                            case "String":
                                p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                                break;
                            case "Integer":
                                p = cb.and(p, cb.le(subpath.as(Integer.class), new Integer(param.getValue().toString())));
                                break;
                            case "Long":
                                p = cb.and(p, cb.le(subpath.as(Long.class), new Long(param.getValue().toString())));
                                break;
                            case "BigDecimal":
                                p = cb.and(p, cb.le(subpath.as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                                break;
                            case "BigInteger":
                                p = cb.and(p, cb.le(subpath.as(BigInteger.class), new BigInteger(param.getValue().toString())));
                                break;
                            default:
                                p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                                break;
                        }
                    } else {
                        p = cb.and(p, cb.equal(subpath, param.getValue()));
                    }
                } else {    //Si son Columnas propias ya entran directo al where
                    if (condicion.equals("like")) {
                        p = cb.and(p, cb.like(from.get(columna), "%" + param.getValue().toString() + "%"));
                    } else if (condicion.equals(">=")) {
                        //Validacion del Tipo de Clase de Objeto que viene
                        switch (param.getValue().getClass().getSimpleName()) {
                            case "Date":
                                Date fecha = (Date) param.getValue();
                                p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(Date.class), fecha));
                                break;
                            case "String":
                                p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                                break;
                            case "Integer":
                                p = cb.and(p, cb.ge(from.get(columna).as(Integer.class), new Integer(param.getValue().toString())));
                                break;
                            case "Long":
                                p = cb.and(p, cb.ge(from.get(columna).as(Long.class), new Long(param.getValue().toString())));
                                break;
                            case "BigDecimal":
                                p = cb.and(p, cb.ge(from.get(columna).as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                                break;
                            case "BigInteger":
                                p = cb.and(p, cb.ge(from.get(columna).as(BigInteger.class), new BigInteger(param.getValue().toString())));
                                break;
                            default:
                                p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                                break;
                        }
                    } else if (condicion.equals("<=")) {
                        //Validacion del Tipo de Clase de Objeto que viene
                        if (param.getValue().getClass().getSimpleName().equals("Date")) {
                            Date fecha = (Date) param.getValue();
                            p = cb.and(p, cb.lessThanOrEqualTo(from.get(columna).as(Date.class), fecha));
                        } else if (param.getValue().getClass().getSimpleName().equals("String")) {
                            p = cb.and(p, cb.lessThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                        } else if (param.getValue().getClass().getSimpleName().equals("Integer")) {
                            p = cb.and(p, cb.le(from.get(columna).as(Integer.class), new Integer(param.getValue().toString())));
                        } else if (param.getValue().getClass().getSimpleName().equals("Long")) {
                            p = cb.and(p, cb.le(from.get(columna).as(Long.class), new Long(param.getValue().toString())));
                        } else if (param.getValue().getClass().getSimpleName().equals("BigDecimal")) {
                            p = cb.and(p, cb.le(from.get(columna).as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                        } else if (param.getValue().getClass().getSimpleName().equals("BigInteger")) {
                            p = cb.and(p, cb.le(from.get(columna).as(BigInteger.class), new BigInteger(param.getValue().toString())));
                        } else {
                            p = cb.and(p, cb.lessThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                        }
                    } else if (condicion.equals("!")) {
                        //Validacion del Tipo de Clase de Objeto que viene
                        p = cb.and(p, cb.equal(from.get(columna), param.getValue()));
//                    System.out.println(param.getValue().toString());
                    } else {
                        p = cb.and(p, cb.equal(from.get(columna), param.getValue()));
                    }
                }
            }
            cq.where(p);
            cq.distinct(true);
            if (order != null) {
                List orden = new ArrayList();
                for (Object item : order) {
                    if (item.toString().contains(".")) {
                        String foranea, columna;
                        foranea = item.toString().substring(0, item.toString().indexOf(".")).trim();
                        columna = item.toString().substring(item.toString().indexOf(".") + 1).trim();
                        orden.add(cb.asc(from.join(foranea).get(columna)));
                        //cq.orderBy(cb.asc(from.join(foranea).get(columna)));
                    } else {
                        orden.add(cb.asc(from.get(item.toString())));
                        //cq.orderBy(cb.asc(from.get(item.toString())));
                    }
                }
                cq.orderBy(orden);
            }
            TypedQuery<Object> typedQuery = emN.createQuery(cq);
            try {
                data = typedQuery.getSingleResult();
                return data;
            } catch (Exception e) {
                return null;
            }
        } catch (Exception e) {
            return null;
        } finally {
            // emN.close();
        }
    }
    //    //<editor-fold defaultstate="expanded" desc="GET Grupo por Usuario">

}

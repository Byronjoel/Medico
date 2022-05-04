/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.jpa;

import ec.com.cubosoft.avamed.modelo.persona.ResultadoGrafico;
import ec.com.cubosoft.avamed.modelo.practica.FormatoXAntecedentes;
import ec.com.cubosoft.avamed.modelo.practica.FormatoXPractica;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
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
public class AdministradorGlobalBean extends GenericoDAOP<Object> {

    /*
     * Constructor de la clase AdministradorGlobalBean
     */
    public AdministradorGlobalBean() {
    }

    //<editor-fold defaultstate="expanded" desc="GET Grupo por Usuario">
    public Object getGrupoUsuario(String usuario, String pass) {

        List<Object> film = null;
        Object filmList = null;
        try {
            String s = "SELECT cg FROM CsUsuarios as cu "
                    + " INNER JOIN cu.csGrupos as cg "
                    + " WHERE  Upper(cu.usuario) = :usuario and Upper(cu.pwdUsu) = :pass and (cu.lockReg = 0)";
            Query q = emP.createQuery(s);
            q.setParameter("usuario", usuario);
            q.setParameter("pass", pass);

            film = q.getResultList();
            if (film.size() == 1) {
                filmList = film.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return filmList;
    }
//    //<editor-fold defaultstate="expanded" desc="GET Usuario">

    public Object getUsuario(String usuario, String pass, boolean lockReg) {
        List<Object> film = null;
        Object filmList = null;
        String s = null;
        String lr = "";

        if (lockReg) {
            lr = " and cu.lockReg = 0 ";
        }
        s = "SELECT cu FROM CsUsuarios  as cu WHERE Upper(cu.usuario) = :usuario and Upper(cu.pwdUsu) = :pass ";
        s = s + lr;

        try {
            Query q = emP.createQuery(s);
            q.setParameter("usuario", usuario);
            q.setParameter("pass", pass);
            film = q.getResultList();
            if (film.size() == 1) {
                filmList = film.get(0);
            }
        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        return filmList;
    }

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

    public List getTodosXParametro(String entidad, String campo, String valor, Class tipo, String orden, boolean lockReg) {
        List<Object> lista = null;
        String clase = tipo.getName().substring(10);
        String lockreg = "";
        if (lockReg) {
            lockreg = " o.lockReg = 0 and ";
        } else {
            lockreg = "";

        }
        int val = Integer.parseInt(valor);
        if (val == 415) {
            lockreg = " o.lockReg = 0 and ";
        }
        try {
            String s = "SELECT o FROM " + entidad + " o WHERE " + lockreg + " o." + campo + " = :valor ORDER BY o." + orden + "";
            //   System.out.println("getTodosXParametro " + s);
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
            lista = q.getResultList();
        } catch (Exception e) {
            throw new PersistenceException("Error al consultar los datos", e);
        }
        return lista;
    }

//    //</editor-fold>
//    //<editor-fold defaultstate="expanded" desc="GET Grupo">
    public List getperxgrupo(String ref, boolean lockReg) {
        List<Object> film = null;
        String s = null;
        String lr = "";
        if (lockReg) {
            lr = " and cp.lockReg = 0 ";
        }
        s = "SELECT cp FROM CsPerxgru as cp WHERE cp.csPerxgruPK.codGru = :ref and cp.csPerxgruPK.idApp like 'avasus   %'";
        s = s + lr;

        try {
            Query q = emP.createQuery(s);
            q.setParameter("ref", ref);
            film = q.getResultList();

//            for (Iterator<Object> it = film.iterator(); it.hasNext();) {
//                Object object = it.next();
//                CsPerxgru permiso = (CsPerxgru) object;
//                System.out.print((permiso.getCsPerxgruPK().getCodPer()));
//            }
        } catch (Exception e) {
            throw new PersistenceException(e);
        }

        return film;
    }

    public List getXmlVista(Long idHisto, String idEstado, String IdCI) {
        List<Object> film = null;
        Query q = null;
        BigInteger IdHistoria = null, IdOrden = null;
        String Fecha = null, consulta = "";
//        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        if (idHisto != null) {
            IdHistoria = new BigInteger(idHisto.toString());
        }
        try {
            consulta = "SELECT o FROM XmlResultado o "
                    + " JOIN o.historia h"
                    + " WHERE (h.id = :idHistoria or h.numId = :IdCI )"
                    + " and o.estado!=:estado order by o.idOrden ";
            q = emP.createQuery(consulta);

            if (idHisto != null) {
                q.setParameter("idHistoria", IdHistoria);
            }
            if (idEstado != null) {
                q.setParameter("estado", idEstado);
            }

            if (IdCI != null) {
                q.setParameter("IdCI", IdCI);
            }
            film = q.getResultList();
        } catch (Exception e) {
            throw new PersistenceException("No se pudo recuperar los datos", e);
        }
        return film;
    }

    public List getXmlVistaResultadoGrafico(Long aux, byte[] byteResultadoGrafico) {
        List<Object> film = null;
        Query q = null;
        BigInteger id_aux = null, IdOrden = null;
        String Fecha = null, consulta = "";
        String test = "";
        byte[] fileArray = null;
        Byte byteResultado = null;
//        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        if (aux != null) {
            id_aux = new BigInteger(aux.toString());
        }
        if (byteResultadoGrafico != null) {
            fileArray = byteResultadoGrafico;
        }
        try {
            /*
            consulta = " UPDATE ResultadoGrafico "
                    + "SET dato= "+Arrays.toString(byteResultadoGrafico) 
                    + " WHERE idXmlResultado = " + id_aux ;
             */
            consulta = "SELECT r FROM ResultadoGrafico r"
                    + " WHERE (r.idXmlResultado = " + id_aux + " )";
            q = emP.createQuery(consulta);
            //q.setParameter("dato", byteResultadoGrafico);            
            //q.setParameter("dato", (byteResultadoGrafico));
            //  q.setParameter("dato", fileArray);
            film = q.getResultList();

            if (!film.isEmpty()) {
                ResultadoGrafico g = (ResultadoGrafico) film.get(0);
                g.setDato(byteResultadoGrafico);
                emP.merge(g);
            }

        } catch (Exception e) {
            //System.out.println("asd" + e.toString());
            throw new PersistenceException("No se pudo recuperar los datos", e);
        }

        return film;
    }

    //erick
    public void VerErrorxCaso(int consulta) {
        int aux = 0;
        if (consulta != aux) {
            aux = consulta;
            //System.out.println("Ver error en la consulta por tipo de caso switch " + consulta + "" + aux);
        }

    }

    public List getXmlVista(Map<String, Object> wSQL, int tipo, String orden, boolean crear, List<String> predi) {
        List<Object> film = null;
        Query q = null;
        BigInteger IdHistoria = null, IdOrden = null;
        String Fecha = null, consulta = "";
        try {
            switch (tipo) {
                case 1:
                    consulta = "SELECT r FROM Resultado r WHERE r.idPractica = :idPractica and r.idOrden = :idOrden";
                    VerErrorxCaso(1);
                    break;
                case 2:
                    consulta = "SELECT r FROM Resultado r WHERE r.numId like %:numId% and r.fecha between :fecha1 and :fecha2";
                    VerErrorxCaso(2);
                    break;
                case 3:
                    consulta = "SELECT r FROM Resultado r WHERE r.idOrden = :idOrden or r.nroOrd=:idOrden and r.fecha between :fecha1 and :fecha2 and idHistoria=:idHistoria and numId=:numId "
                            + "and idEmpresa=:idEmpresa and idPractica=:idPractica and idArea:=idArea";
                    VerErrorxCaso(3);
                    break;
                case 4:
                    consulta = "SELECT r FROM Resultado r WHERE ";
                    VerErrorxCaso(4);
                    break;
                case 6:
                    consulta = "SELECT p FROM Pedidos p WHERE ";
                    VerErrorxCaso(6);
                    break;
                case 7:
                    consulta = "SELECT r FROM Resultado r WHERE r.idOrden = " + wSQL.get("idOrden");
                    VerErrorxCaso(7);
                    break;
                case 8:
                    //consulta = "SELECT r FROM Resultado r WHERE r.idOrden = " + wSQL.get("idOrden");
                    consulta = "SELECT r FROM XmlResultado r WHERE r.idOrden = " + wSQL.get("idOrden");
                    VerErrorxCaso(8);
                    break;

                case 9:
                    consulta = "SELECT r FROM XmlResultado r WHERE r.idOrden = " + wSQL.get("idOrden") + " and r.idPractica = " + wSQL.get("idPractica");
                    break;
                    
                case 10:
                    consulta = "SELECT r FROM AudImagen r WHERE r.orden = " + wSQL.get("idOrden") + " and r.practica = " + wSQL.get("idPractica");
                    break;

            }
            int i = 0;
            if (crear) {
                for (int j = 0; j < predi.size(); j++) {
                    for (Map.Entry<String, Object> entrySet : wSQL.entrySet()) {
                        String key = entrySet.getKey();

                        if (predi.get(j).contains(key)) {
                            consulta = consulta + predi.get(j) + " ";
                            if (j < predi.size() - 1) {
                                consulta = consulta + " AND ";
                            }
                        }
                    }
                }

                if (!orden.isEmpty()) {
                    consulta = consulta + " Order by " + orden;

                }
                q = emP.createQuery(consulta);
                for (Map.Entry<String, Object> entidad : wSQL.entrySet()) {
                    String key = entidad.getKey();
                    Object value = entidad.getValue();
                    q.setParameter(key, value);
                }

            } else {
                try {
                    q = emP.createQuery(consulta);
                } catch (Exception e) {
                    System.out.println("e" + e.fillInStackTrace());

                }
                if ((tipo == 7) || (tipo == 8) || (tipo == 9) || (tipo == 10) ) {
                    //System.out.println("asd"+tipo);
                } else {
                    for (Map.Entry<String, Object> entidad : wSQL.entrySet()) {

                        String key = entidad.getKey();
                        Object value = entidad.getValue();
                        q.setParameter(key, value);
                    }
                }

            }
            // System.out.println("4.9" + new Date() + " / " + " / ");
            film = q.getResultList();
            //  System.out.println("5" + new Date() + " / " + " / ");
        } catch (Exception e) {
            throw new PersistenceException("No se pudo recuperar los datos", e);
        }
        return film;
    }

    public List getXmlReceta(Long idReceta) {
        List<Object> film = null;
        Query q = null;
        Long IdReceta = null;
        String Fecha = null, consulta = "";
        if (IdReceta == null) {
            IdReceta = idReceta;
        }
        try {
            consulta = "SELECT o FROM Receta o "
                    + " WHERE o.id = :idReceta ";
            q = emP.createQuery(consulta);

            if (IdReceta != null) {
                q.setParameter("idReceta", IdReceta);
            }

            film = q.getResultList();
        } catch (Exception e) {
            throw new PersistenceException("No se pudo recuperar los datos", e);
        }
        return film;
    }

    public String getIdEmpresa(Long idEmpresa) {
        List<Object> film = null;
        String id = "";
        Query q = null;
        Long IdEmpresa = null;
        String Fecha = null, consulta = "";
        if (IdEmpresa == null) {
            IdEmpresa = idEmpresa;
        }
        try {
            consulta = "SELECT o FROM Receta o "
                    + " WHERE o.id = :idReceta ";
            q = emP.createQuery(consulta);

            if (IdEmpresa != null) {
                q.setParameter("idReceta", IdEmpresa);
            }

            film = q.getResultList();
        } catch (Exception e) {
            throw new PersistenceException("No se pudo recuperar los datos", e);
        }

        return id;
    }

    public List getXmlResultado(String idHistoria, String idPractica, String idOrden, String IdMedico, String empresa, Date fechaInicio, Date fechaFin) {
        List<Object> film = null;
        String consulta = "";
        Query q = null;
        String fechaI = null, fechaF = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "ES"));
        Long orden = null, historia = null;
        Integer medico = null, practica = null;

        if (idHistoria != null) {
            historia = new Long(idHistoria);
        }
        if (idPractica != null) {
            practica = new Integer(idPractica);
        }
        if (idOrden != null) {
            orden = new Long(idOrden);
        }
        if (IdMedico != null) {
            medico = new Integer(IdMedico);
        }
        if (fechaInicio != null) {
            fechaI = formato.format(fechaInicio);
        }
        if (fechaFin != null) {
            fechaF = formato.format(fechaFin);
        }

        List relaciones = new ArrayList();
        List alias1 = new ArrayList();
        List alias2 = new ArrayList();
        List alias3 = new ArrayList();
        List parametros = new ArrayList();
        List campos = new ArrayList();
        List variables = new ArrayList();

        relaciones.add("historia");
        relaciones.add("practica");
        relaciones.add("orden");
        relaciones.add("medicos");
        relaciones.add("organizacion");

        alias1.add("xr.");
        alias1.add("xr.");
        alias1.add("xr.");
        alias1.add("xr.");
        alias1.add("o.");
        alias2.add("h");
        alias2.add("p");
        alias2.add("o");
        alias2.add("m");
        alias2.add("org");

        parametros.add(historia);
        parametros.add(practica);
        parametros.add(orden);
        parametros.add(medico);
        parametros.add(empresa);
        parametros.add(fechaI);
        parametros.add(fechaF);

        campos.add("id");
        campos.add("id");
        campos.add("id");
        campos.add("id");
        campos.add("empresa");
        campos.add("fecha");
        campos.add("fecha");

        variables.add("idHistoria");
        variables.add("idPractica");
        variables.add("idOrden");
        variables.add("idMedico");
        variables.add("empresa");
        variables.add("fechaInicio");
        variables.add("fechaFin");

        alias3.add(" h.");
        alias3.add(" p.");
        alias3.add(" o.");
        alias3.add(" m.");
        alias3.add(" xr.");
        alias3.add(" xr.");
        alias3.add(" xr.");
        try {
            consulta = crearConsulta("xr", "XmlResultado", "xr", relaciones, alias1, alias2, parametros, campos, variables, alias3, true);
            consulta += " ORDER BY o.id , p.descripcion";

            q = emP.createQuery(consulta);
            System.out.print("Consulta : ---- > " + consulta.toString());
            System.out.print("Historia: - > " + idHistoria + " Orden: - > " + idOrden + " Practica: - > " + idPractica + " Medico: - > " + IdMedico + " FechaInicio: - > " + fechaInicio + " FechaFin: - > " + fechaFin);

            if (idHistoria != null) {
                q.setParameter("idHistoria", historia);
            }
            if (idPractica != null) {
                q.setParameter("idPractica", practica);
            }
            if (idOrden != null) {
                q.setParameter("idOrden", orden);
            }
            if (IdMedico != null) {
                q.setParameter("idMedico", medico);
            }
            if (empresa != null) {
                q.setParameter("empresa", empresa);
            }
            if (fechaInicio != null) {
//                q.setParameter("fechaInicio", fechaI + "%");
                q.setParameter("fechaInicio", fechaInicio, TemporalType.DATE);
            }
            if (fechaFin != null) {
                q.setParameter("fechaFin", fechaFin, TemporalType.DATE);
            }

            film = q.getResultList();

        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new PersistenceException("Erros al buscar los datos", e);
        }
        return film;
    }

    public List getDBDataLimit(Object tabla, Map<String, Object> where, List group, List order, Integer limit) {
        List data;
        CriteriaBuilder cb = emP.getCriteriaBuilder();
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
                    case ">=":
                        //Validacion del Tipo de Clase de Objeto que viene
                        if (param.getValue().getClass().getSimpleName().equals("Date")) {
                            Date fecha = (Date) param.getValue();
                            p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(Date.class), fecha));
                        } else if (param.getValue().getClass().getSimpleName().equals("String")) {
                            p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                        } else if (param.getValue().getClass().getSimpleName().equals("Integer")) {
                            p = cb.and(p, cb.ge(from.get(columna).as(Integer.class), new Integer(param.getValue().toString())));
                        } else if (param.getValue().getClass().getSimpleName().equals("Long")) {
                            p = cb.and(p, cb.ge(from.get(columna).as(Long.class), new Long(param.getValue().toString())));
                        } else if (param.getValue().getClass().getSimpleName().equals("BigDecimal")) {
                            p = cb.and(p, cb.ge(from.get(columna).as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                        } else if (param.getValue().getClass().getSimpleName().equals("BigInteger")) {
                            p = cb.and(p, cb.ge(from.get(columna).as(BigInteger.class), new BigInteger(param.getValue().toString())));
                        } else {
                            p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
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
//                    System.out.println(param.getValue().toString());
                        break;
                    default:
                        p = cb.and(p, cb.equal(from.get(columna), param.getValue()));
//                    System.out.println(param.getValue().toString());
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
                    //cq.orderBy(cb.asc(from.get(item.toString())));
                }
            }
            cq.orderBy(orden);
        }
        TypedQuery<Object> typedQuery = emP.createQuery(cq);
        typedQuery.setMaxResults(limit);
        data = typedQuery.getResultList();
        return data;
    }

    //creado por Erick
    public List getDBDataLimitBuscarOrdenNestlab(Object tabla, Map<String, Object> where, List group, List order, Integer limit) {

        List<Object> film = null;
        String consulta = "";
        Query q = null;
        String fechaI = null, fechaF = null;
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", new Locale("es", "ES"));
        Long orden = null, historia = null;
        Integer medico = null, practica = null;

        //System.out.println("orden" + order);
        List ordenNestalb = null;

        try {
            consulta = "SELECT o.cod_ord FROM Orden o "
                    + " WHERE o.id = :id ";
            q = emP.createQuery(consulta);
            // System.out.print("Consulta : ---- > " + consulta.toString());
            //System.out.print("Historia: - > " + idHistoria + " Orden: - > " + idOrden + " Practica: - > " + idPractica + " Medico: - > " + IdMedico + " FechaInicio: - > " + fechaInicio + " FechaFin: - > " + fechaFin);

            film = q.getResultList();

        } catch (Exception e) {
            e.printStackTrace(System.out);
            throw new PersistenceException("Erros al buscar los datos", e);
        }

        return ordenNestalb;
    }

    public List getDBDataAsdc(Object tabla, Map<String, Object> where, List group, List order) {

        List data;
        List datos;
        CriteriaBuilder cb = emP.getCriteriaBuilder();

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
                    if (param.getValue().getClass().getSimpleName().equals("Date")) {
                        Date fecha = (Date) param.getValue();
                        p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(Date.class), fecha));
                    } else if (param.getValue().getClass().getSimpleName().equals("String")) {
                        p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                    } else if (param.getValue().getClass().getSimpleName().equals("Integer")) {
                        p = cb.and(p, cb.ge(subpath.as(Integer.class), new Integer(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("Long")) {
                        p = cb.and(p, cb.ge(subpath.as(Long.class), new Long(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigDecimal")) {
                        p = cb.and(p, cb.ge(subpath.as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigInteger")) {
                        p = cb.and(p, cb.ge(subpath.as(BigInteger.class), new BigInteger(param.getValue().toString())));
                    } else {
                        p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                    }
                } else if (condicion.equals("<=")) {
                    //Validacion del Tipo de Clase de Objeto que viene
                    if (param.getValue().getClass().getSimpleName().equals("Date")) {
                        Date fecha = (Date) param.getValue();
                        p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(Date.class), fecha));
                    } else if (param.getValue().getClass().getSimpleName().equals("String")) {
                        p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                    } else if (param.getValue().getClass().getSimpleName().equals("Integer")) {
                        p = cb.and(p, cb.le(subpath.as(Integer.class), new Integer(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("Long")) {
                        p = cb.and(p, cb.le(subpath.as(Long.class), new Long(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigDecimal")) {
                        p = cb.and(p, cb.le(subpath.as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigInteger")) {
                        p = cb.and(p, cb.le(subpath.as(BigInteger.class), new BigInteger(param.getValue().toString())));
                    } else {
                        p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                    }
                } else {
                    p = cb.and(p, cb.equal(subpath, param.getValue()));
                }
            } else {    //Si son Columnas propias ya entran directo al where
                if (condicion.equals("like")) {
                    p = cb.and(p, cb.like(from.get(columna), "%" + param.getValue().toString() + "%"));
                } else if (condicion.equals(">=")) {
                    //Validacion del Tipo de Clase de Objeto que viene
                    if (param.getValue().getClass().getSimpleName().equals("Date")) {
                        Date fecha = (Date) param.getValue();
                        p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(Date.class), fecha));
                    } else if (param.getValue().getClass().getSimpleName().equals("String")) {
                        p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                    } else if (param.getValue().getClass().getSimpleName().equals("Integer")) {
                        p = cb.and(p, cb.ge(from.get(columna).as(Integer.class), new Integer(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("Long")) {
                        p = cb.and(p, cb.ge(from.get(columna).as(Long.class), new Long(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigDecimal")) {
                        p = cb.and(p, cb.ge(from.get(columna).as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigInteger")) {
                        p = cb.and(p, cb.ge(from.get(columna).as(BigInteger.class), new BigInteger(param.getValue().toString())));
                    } else {
                        p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
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
//                    System.out.println(param.getValue().toString());
                }
            }
            //p = cb.and(p, cb.equal(from.get(param.getKey()), param.getValue()));
        }

//        CriteriaQuery<Object> select = cq.select(from);
//        //select.where(cb.and(p1,p2));
//        select.where(p);
        //cq.where(p1, p2);
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
        TypedQuery<Object> typedQuery = emP.createQuery(cq);

        data = typedQuery.getResultList();
//        System.out.print("Registros: " + data.size());
        return data;
    }

    public List getDBData(Object tabla, Map<String, Object> where, List group, List order) {

        List data = null;
        List datos;
        CriteriaBuilder cb = emP.getCriteriaBuilder();
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
                    if (param.getValue().getClass().getSimpleName().equals("Date")) {
                        Date fecha = (Date) param.getValue();
                        p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(Date.class), fecha));
                    } else if (param.getValue().getClass().getSimpleName().equals("String")) {
                        p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                    } else if (param.getValue().getClass().getSimpleName().equals("Integer")) {
                        p = cb.and(p, cb.ge(subpath.as(Integer.class), new Integer(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("Long")) {
                        p = cb.and(p, cb.ge(subpath.as(Long.class), new Long(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigDecimal")) {
                        p = cb.and(p, cb.ge(subpath.as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigInteger")) {
                        p = cb.and(p, cb.ge(subpath.as(BigInteger.class), new BigInteger(param.getValue().toString())));
                    } else {
                        p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                    }
                } else if (condicion.equals("<=")) {
                    //Validacion del Tipo de Clase de Objeto que viene
                    if (param.getValue().getClass().getSimpleName().equals("Date")) {
                        Date fecha = (Date) param.getValue();
                        p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(Date.class), fecha));
                    } else if (param.getValue().getClass().getSimpleName().equals("String")) {
                        p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                    } else if (param.getValue().getClass().getSimpleName().equals("Integer")) {
                        p = cb.and(p, cb.le(subpath.as(Integer.class), new Integer(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("Long")) {
                        p = cb.and(p, cb.le(subpath.as(Long.class), new Long(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigDecimal")) {
                        p = cb.and(p, cb.le(subpath.as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigInteger")) {
                        p = cb.and(p, cb.le(subpath.as(BigInteger.class), new BigInteger(param.getValue().toString())));
                    } else {
                        p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
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
                        if (param.getValue().getClass().getSimpleName().equals("Date")) {
                            Date fecha = (Date) param.getValue();
                            p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(Date.class), fecha));
                        } else if (param.getValue().getClass().getSimpleName().equals("String")) {
                            p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                        } else if (param.getValue().getClass().getSimpleName().equals("Integer")) {
                            p = cb.and(p, cb.ge(from.get(columna).as(Integer.class), new Integer(param.getValue().toString())));
                        } else if (param.getValue().getClass().getSimpleName().equals("Long")) {
                            p = cb.and(p, cb.ge(from.get(columna).as(Long.class), new Long(param.getValue().toString())));
                        } else if (param.getValue().getClass().getSimpleName().equals("BigDecimal")) {
                            p = cb.and(p, cb.ge(from.get(columna).as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                        } else if (param.getValue().getClass().getSimpleName().equals("BigInteger")) {
                            p = cb.and(p, cb.ge(from.get(columna).as(BigInteger.class), new BigInteger(param.getValue().toString())));
                        } else {
                            p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                        }
                        break;
                    case "<=":
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
                    //cq.orderBy(cb.asc(from.get(item.toString())));
                }
            }
            cq.orderBy(orden);
        }
        TypedQuery<Object> typedQuery = emP.createQuery(cq);
        try {
            data = typedQuery.getResultList();
        } catch (Exception e) {
            System.out.println("Administradorglobalbena 1108" + e);
        }

//        System.out.print("Registros: " + data.size());
        return data;
    }

    public List getDBDataValidate(Object tabla, Map<String, Object> where, List group, List order) {

        List data = null;
        List datos;
        CriteriaBuilder cb = emP.getCriteriaBuilder();
        CriteriaQuery<Object> cq = cb.createQuery();
        Root from = cq.from(tabla.getClass());
        Predicate p = cb.conjunction();

        List<Object> film = null;
        //Query q = null;
        BigInteger IdHistoria = null, IdOrden = null;
        String Fecha = null, consulta = "";
        try {
            consulta = "SELECT r FROM Nombre r WHERE r.id = :idPractica and r.per_add = 0";
            Query q = emP.createQuery(consulta);
            film = q.getResultList();
        } catch (Exception e) {
            System.out.println("E getDBDataValidate" + e.toString());
        }
        return film;
    }

    public Object getDBDataObj(Object tabla, Map<String, Object> where, List group, List order) {

        Object data;
        List datos;
        CriteriaBuilder cb = emP.getCriteriaBuilder();

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
                    if (param.getValue().getClass().getSimpleName().equals("Date")) {
                        Date fecha = (Date) param.getValue();
                        p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(Date.class), fecha));
                    } else if (param.getValue().getClass().getSimpleName().equals("String")) {
                        p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                    } else if (param.getValue().getClass().getSimpleName().equals("Integer")) {
                        p = cb.and(p, cb.ge(subpath.as(Integer.class), new Integer(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("Long")) {
                        p = cb.and(p, cb.ge(subpath.as(Long.class), new Long(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigDecimal")) {
                        p = cb.and(p, cb.ge(subpath.as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigInteger")) {
                        p = cb.and(p, cb.ge(subpath.as(BigInteger.class), new BigInteger(param.getValue().toString())));
                    } else {
                        p = cb.and(p, cb.greaterThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                    }
                } else if (condicion.equals("<=")) {
                    //Validacion del Tipo de Clase de Objeto que viene
                    if (param.getValue().getClass().getSimpleName().equals("Date")) {
                        Date fecha = (Date) param.getValue();
                        p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(Date.class), fecha));
                    } else if (param.getValue().getClass().getSimpleName().equals("String")) {
                        p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                    } else if (param.getValue().getClass().getSimpleName().equals("Integer")) {
                        p = cb.and(p, cb.le(subpath.as(Integer.class), new Integer(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("Long")) {
                        p = cb.and(p, cb.le(subpath.as(Long.class), new Long(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigDecimal")) {
                        p = cb.and(p, cb.le(subpath.as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigInteger")) {
                        p = cb.and(p, cb.le(subpath.as(BigInteger.class), new BigInteger(param.getValue().toString())));
                    } else {
                        p = cb.and(p, cb.lessThanOrEqualTo(subpath.as(String.class), param.getValue().toString()));
                    }
                } else {
                    p = cb.and(p, cb.equal(subpath, param.getValue()));
                }
            } else {    //Si son Columnas propias ya entran directo al where
                if (condicion.equals("like")) {
                    p = cb.and(p, cb.like(from.get(columna), "%" + param.getValue().toString() + "%"));
                } else if (condicion.equals(">=")) {
                    //Validacion del Tipo de Clase de Objeto que viene
                    if (param.getValue().getClass().getSimpleName().equals("Date")) {
                        Date fecha = (Date) param.getValue();
                        p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(Date.class), fecha));
                    } else if (param.getValue().getClass().getSimpleName().equals("String")) {
                        p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
                    } else if (param.getValue().getClass().getSimpleName().equals("Integer")) {
                        p = cb.and(p, cb.ge(from.get(columna).as(Integer.class), new Integer(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("Long")) {
                        p = cb.and(p, cb.ge(from.get(columna).as(Long.class), new Long(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigDecimal")) {
                        p = cb.and(p, cb.ge(from.get(columna).as(BigDecimal.class), new BigDecimal(param.getValue().toString())));
                    } else if (param.getValue().getClass().getSimpleName().equals("BigInteger")) {
                        p = cb.and(p, cb.ge(from.get(columna).as(BigInteger.class), new BigInteger(param.getValue().toString())));
                    } else {
                        p = cb.and(p, cb.greaterThanOrEqualTo(from.get(columna).as(String.class), param.getValue().toString()));
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
//                    System.out.println(param.getValue().toString());
                }
            }
            //p = cb.and(p, cb.equal(from.get(param.getKey()), param.getValue()));
        }

//        CriteriaQuery<Object> select = cq.select(from);
//        //select.where(cb.and(p1,p2));
//        select.where(p);
        //cq.where(p1, p2);
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
        TypedQuery<Object> typedQuery = emP.createQuery(cq);
        try {
            data = typedQuery.getSingleResult();
            return data;
        } catch (Exception e) {
            return null;
        }

//        System.out.print("Registros: " + data.size());
    }

    public static String crearConsulta(String retorno, String entidades, String alias, List relaciones, List alias1, List alias2, List parametros, List campos, List variables, List alias3, boolean xmlRes) {
        int indice = 0;
        int indice1 = 0;
        String join = crearJoin(retorno, entidades, alias, relaciones, alias1, alias2);
        StringBuilder consulta = new StringBuilder(join + " WHERE " + alias + ".lockReg = 0 and ");

        for (int n = 0; n < parametros.size(); n++) {
            if (parametros.get(n) != (null)) {

                if (indice > 0) {
                    if (n < (parametros.size()) && parametros.get(n - 1) == null) {
                        consulta.append(" and ");
                    }
                }
                if (xmlRes) {
                    if (n == (parametros.size() - 2) || n == (parametros.size() - 1)) {
                        if (n < parametros.size() - 1) {
                            if ((parametros.get(n + 1) != null)) {
                                consulta.append(" xr.");
                                consulta.append(campos.get(n));
                                consulta.append(" BETWEEN ");
                                indice1++;
                            }
                        }
                        if (indice1 == 0) {
                            consulta.append(" xr.");
                            consulta.append(campos.get(n));
                            consulta.append(" = ");
                        }
                    } else {
                        if (n == (parametros.size() - 4) || n == (parametros.size() - 3)) {
                            if (n < parametros.size() - 3) {
                                if ((parametros.get(n + 1) != null)) {
                                    consulta.append(" xr.");
                                    consulta.append(campos.get(n));
                                    consulta.append(" BETWEEN ");
                                    indice1++;
                                }
                            }
                            if (!(alias3.get(n).equals("xr"))) {
                                consulta.append(alias3.get(n));
                                consulta.append(campos.get(n));
                                consulta.append("= ");
                            } else {
                                if (indice1 == 0) {
                                    consulta.append(" xr.");
                                    consulta.append(campos.get(n));
                                    consulta.append(" = ");
                                }
                            }
                        } else {

                            consulta.append(alias3.get(n));
                            consulta.append(campos.get(n));
                        }
                    }
                } else {
                    consulta.append(alias3.get(n));
                    consulta.append(campos.get(n));
                }

                if (n < parametros.size()) {
                    if (xmlRes) {
                        if ((n == parametros.size() - 2 || n == parametros.size() - 1) || ((n == parametros.size() - 4 || n == parametros.size() - 3))) {
                            consulta.append(" :");
                        } else {
                            consulta.append(" = :");
                        }
                    } else {
                        consulta.append(" = :");
                    }
                    consulta.append(variables.get(n));
                    if (n < (parametros.size() - 1) && parametros.get(n + 1) != null) {
                        consulta.append(" and ");
                    }
                    indice++;
                }
            } else if (n == parametros.size() - 1 && indice == 0) {
                consulta = new StringBuilder(" SELECT e FROM " + entidades + " e WHERE e.lockReg = 0 ");
            }
        }
        return consulta.toString();
    }

    public static String crearJoin(String retorno, String entidad, String alias, List relaciones, List alias1, List alias2) {
        StringBuilder consulta = new StringBuilder("SELECT " + retorno + " FROM " + entidad + " " + alias);
        try {

            if ((relaciones.size() == alias1.size())) {
                if (alias1.size() == alias2.size()) {
                    for (int i = 0; i < relaciones.size(); i++) {

                        consulta.append(" JOIN ");
                        consulta.append(alias1.get(i));
                        consulta.append(relaciones.get(i));
                        consulta.append(" ");
                        consulta.append(alias2.get(i));
                    }
                } else {
                    consulta = null;
                }
            } else {
                consulta = null;
            }
        } catch (Exception e) {
            e.getCause();
        }

//        System.out.print("CONSULTA" +consulta.toString());
        return consulta.toString();
    }

    public FormatoXPractica getTodosDosParametros(String entidad, String campo, String valor, String campo1, String valor1, Class tipo, Class tipo1, String orden) {
        List<Object> lista = null;
        FormatoXPractica objformato = null;
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
                objformato = (FormatoXPractica) lista.get(0);
            }
        } catch (Exception e) {
            throw new PersistenceException("Error al consultar los datos", e);
        }
        return objformato;
    }

}

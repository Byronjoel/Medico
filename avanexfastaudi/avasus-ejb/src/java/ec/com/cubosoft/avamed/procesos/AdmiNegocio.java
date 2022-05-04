/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.procesos;

import ec.com.cubosoft.avamed.jpa.AdministradorGenericoBean;
import ec.com.cubosoft.avamed.jpa.AdministradorGlobalBean;

import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
import static ec.com.cubosoft.avamed.procesos.AdmiUsuario.contextoGlobal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Administrador
 */
public class AdmiNegocio {

    public Context contexto;

//<editor-fold defaultstate="expanded" desc="SQL BASICOS GENERALES">
    public Object guardar(Object obj) throws NamingException {
        try {
            Object dato = null;
            AdministradorGenericoBean admJPAG;
            contexto = new InitialContext();
            admJPAG = (AdministradorGenericoBean) contexto.lookup(AdmiUsuario.contextoGenerico);
            try {
                dato = admJPAG.guardar(obj);
                return dato;
            } catch (Exception e) {
                return null;
            }

        } catch (NamingException ex) {
//            Logger.getLogger(AdmiNegocio.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public boolean actualizar(Object obj) throws NamingException {
        boolean dato = false;
        AdministradorGenericoBean admJPAG;
        contexto = new InitialContext();
        admJPAG = (AdministradorGenericoBean) contexto.lookup(AdmiUsuario.contextoGenerico);
        dato = admJPAG.actualizar(obj);
        return dato;
    }

    public boolean actualizarPdf(Object obj) throws NamingException {
        boolean dato = false;
        AdministradorGenericoBean admJPAG;
        contexto = new InitialContext();
        admJPAG = (AdministradorGenericoBean) contexto.lookup(AdmiUsuario.contextoGenerico);
        dato = admJPAG.eliminar(obj);
        return dato;
    }

    public boolean eliminar(Object obj) throws NamingException {
        boolean dato = false;
        AdministradorGenericoBean admJPAG;
        contexto = new InitialContext();
        admJPAG = (AdministradorGenericoBean) contexto.lookup(AdmiUsuario.contextoGenerico);
        dato = admJPAG.eliminar(obj);

        return dato;
    }
    //</editor-fold>
//<editor-fold defaultstate="expanded" desc="jpa JP">

    public List<Object> getData(Object table) throws NamingException {
        return getData(table, null, null, null);
    }

    public List<Object> getData(Object table, List order) throws NamingException {
        return getData(table, null, null, order);
    }

    public List<Object> getData(Object table, boolean lock, List order) throws NamingException {
        Map<String, Object> wSQL = new HashMap<String, Object>();
        if (lock) {
            wSQL.put("lockReg ?=", 1);
        } else {
            wSQL.put("lockReg ?=", 0);
        }
        return getData(table, wSQL, null, order);
    }

    public List<Object> getDataLimit(Object table, Map<String, Object> where, List group, List order, Integer limit) throws NamingException {
        List data = null;
       // int i=1;
        List<String> vacios = new ArrayList<>();
        //Se verifica que parametros estan vacios
        for (Map.Entry<String, Object> entry : where.entrySet()) {
            if ((entry.getValue() == null) || (entry.getValue().toString().isEmpty())) {
                vacios.add(entry.getKey());
          //     i++;
           //    System.out.println(vacios.toString());
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
            System.out.print(entry.getKey() + " valor " + entry.getValue() + " Clase " + entry.getValue().getClass());
        }
        AdministradorGlobalBean admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        data = admJPA.getDBDataLimit(table, where, group, order, limit);
        return data;
    }

    //creado por Erick
    public List<Object> getDataLimitNestalb(Object table, Map<String, Object> where, List group, List order, Integer limit) throws NamingException {
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
            System.out.print(entry.getKey() + " valor " + entry.getValue() + " Clase " + entry.getValue().getClass());
        }
        AdministradorGlobalBean admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        data = admJPA.getDBDataLimitBuscarOrdenNestlab(table, where, group, order, limit);
        return data;
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
        AdministradorGlobalBean admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        data = admJPA.getDBData(table, where, group, order);
        return data;
    }

    public List<Object> getDataValidateStateAndper_add(Object table, Map<String, Object> where, List group, List order) throws NamingException {

        List data = null;

        AdministradorGlobalBean admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        data = admJPA.getDBDataValidate(table, where, group, order);
        return data;
    }

    public List<Object> getDataAsc(Object table, Map<String, Object> where, List group, List order) throws NamingException {
        List data = null;
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
            System.out.print(entry.getKey() + " valor " + entry.getValue() + " Clase " + entry.getValue().getClass());
        }
//        where.entrySet().stream().forEach((entry) -> {
//            System.out.print(entry.getKey() + " valor " + entry.getValue() + " Clase " + entry.getValue().getClass());
//        });
        AdministradorGlobalBean admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        data = admJPA.getDBDataAsdc(table, where, group, order);
        return data;
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
        AdministradorGlobalBean admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        try {
            data = admJPA.getDBDataObj(table, where, group, order);
            return data;
        } catch (Exception e) {
            return null;
        }

    }

    public List<Object> getDataVista(Map<String, Object> where, int tipo, String orden, Boolean crear, List<String> pred) throws NamingException {
        List data = null;
        AdministradorGlobalBean admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        data = admJPA.getXmlVista(where, tipo, orden, crear, pred);
        return data;
    }

    public List<Object> getData(Long idHis, String esta, String ci) throws NamingException {
        List data = null;
        List<String> vacios = new ArrayList<String>();
        AdministradorGlobalBean admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        data = admJPA.getXmlVista(idHis, esta, ci);
        return data;
    }

    public List<Object> getXmlResultado(Map<String, Object> where) throws NamingException {
        List data = null;
        List<String> vacios = new ArrayList<String>();

        AdministradorGlobalBean admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        data = admJPA.getXmlVista(null, null, null);
        return data;
    }
   
     public List<Object> getDataR(Long aux, byte[] byteResultadoGrafico) throws NamingException {
        List data = null;
        List<String> vacios = new ArrayList<String>();
        AdministradorGlobalBean admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        //data = admJPA.getXmlVistaResultadoGrafico(aux, byteResultadoGrafico,where);
        data = admJPA.getXmlVistaResultadoGrafico(aux, byteResultadoGrafico);
        return data;
    }
     
    public void getResultadoGrafico(Long aux, byte[] byteResultadoGrafico) throws NamingException {
        List data = null;
        List<String> vacios = new ArrayList<String>();

        AdministradorGlobalBean admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        admJPA.getXmlVistaResultadoGrafico(aux, byteResultadoGrafico);
//data = admJPA.getXmlVistaResultadoGrafico(aux,byteResultadoGrafico);
        // return data;
    }
    
    

    
    public List getperxgrup(String ref) {
        List dato = null;
        AdministradorGlobalBean admJPA;
        try {
            contexto = new InitialContext();
            admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
            dato = admJPA.getperxgrupo(ref, true);
        } catch (Exception e) {
            e.printStackTrace(System.out);
        }
//        objmane = new HManejadoraCore();
//        dato = objmane.getperxgrupo(ref);
        return dato;
    }

    public Object getUsuario(String usuario, String pass, boolean lock)
            throws NamingException {
        Object dato = null;
        AdministradorGlobalBean admJPA;
        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
        dato = admJPA.getUsuario(usuario, pass, lock);
        return dato;
    }

    public Object getGrupoUsuario(String usuario, String pass) {
        Object dato = null;
        AdministradorGlobalBean admJPA;
        try {
            contexto = new InitialContext();
            admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
            dato = admJPA.getGrupoUsuario(usuario, pass);
//            objmane = new HManejadoraCore();
//            dato = objmane.getgrupousuario(usuario, pass);
        } catch (Exception e) {
            System.out.print(e.getMessage());
            e.printStackTrace(System.out);
        }

        return dato;
    }
    //</editor-fold>
//<editor-fold defaultstate="expanded" desc="JPA2 P">

    public Object getObjetoxID(Class ref, long idRef) throws NamingException {
        Object dato = null;
        AdministradorGlobalBean admJPA;

        contexto = new InitialContext();
        admJPA = (AdministradorGlobalBean) contexto.lookup(AdmiUsuario.contextoGlobal);
        dato = admJPA.buscarPorId(ref, idRef);

        return dato;
    }

//    public List<Object> getObjetosA(String ref, int id, boolean bandera)
//            throws NamingException {
//        List datos = null;
//        AdministradorGenericoBean admJPAG;
//
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(AdmiUsuario.contextoGenerico);
//        datos = admJPAG.getobjetosA(ref, id, bandera);
//
//        return datos;
//    }
//    public List<Object> getobjetosReportes(String ref, int id, boolean bandera)
//            throws NamingException {
//        List datos = null;
//        AdministradorGenericoBean admJPAG;
//
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(AdmiUsuario.contextoGenerico);
//        datos = admJPAG.getobjetosReportes(ref, id, bandera);
//
//        return datos;
//    }
    public CsUsuarios getUsuario(String usuario) throws NamingException {
        //CsUsuarios rtn = null;
        CsUsuarios table = new CsUsuarios();
        List data;

        Map<String, Object> wSQL = new HashMap<String, Object>();
        wSQL.put("usuario ?=", usuario);

        data = getData(table, wSQL, null, null);

        if ((data != null) && (data.size() > 0)) {
            table = (CsUsuarios) data.get(0);

        } else {
            table.setUsuario(usuario);
            table.setNomUsu(usuario);
        }

        return table;
    }

// public Object getObjeto(String tabla, String refcolumna, String refvalor, boolean bandera) throws NamingException {
//        Object dato = null;
//        AdministradorGenericoBean admJPAG;
//
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        dato = admJPAG.getobjeto(tabla, refcolumna, refvalor, bandera);
//
//        return dato;
//    }
// public Object getDatosMedico(String usuario, String grupo) throws NamingException {
//        Object dato = null;
//        AdministradorGlobalBean admJPA;
//
//        contexto = new InitialContext();
//        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//        dato = admJPA.getDatosMedico(usuario, grupo);
//
//        return dato;
//    }
//
//   
//public List<Object> getObjetosAbreviaturas(String ref, boolean bandera)
//            throws NamingException {
//        List datos = null;
//        AdministradorGenericoBean admJPAG;
//
//
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        datos = admJPAG.getobjetos(ref, bandera);
//
//        return datos;
//    }
// public List getDatosHistoria(String idHistoria, String idOrden, String NumCedula)
//            throws NamingException {
//
//        List<Object> datos;
//        AdministradorGlobalBean admJPA;
//
//        contexto = new InitialContext();
//        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//        if (idOrden.isEmpty()) {
//            idOrden = null;
//        }
//        if (idHistoria.isEmpty()) {
//            idHistoria = null;
//        }
//        if (NumCedula.isEmpty()) {
//            NumCedula = null;
//        }
////            datos = admJPA.getDatosHistoria(idHistoria, idOrden);
//        datos = admJPA.getDatosHistoria(idHistoria, idOrden, NumCedula);
////                    ria(idHistoria, idOrden);
//
//        return datos;
//    }
//  public List getFechasXId(String columna, String valhistoria) throws NamingException {
//        List<Object> datos;
//        AdministradorGlobalBean admJPA;
//
//        contexto = new InitialContext();
//        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//        datos = admJPA.getFechasPorId(columna, valhistoria);
//
//        return datos;
//    }
//
//  public List<Object> getPracticaxOrden(String idOrden, String idHistoria,
//            String DesPractica, String DesArea, String Usuario, String DesGrupo)
//            throws NamingException {
//
//        List datos;
//
//        AdministradorGlobalBean admJPA;
//        contexto = new InitialContext();
//        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//        if (idHistoria.isEmpty()) {
//            idHistoria = null;
//        }
//        if (idOrden.isEmpty()) {
//            idOrden = null;
//        }
//
//        if (DesPractica == null) {
//            DesPractica = null;
//        } else {
//            if (DesPractica.isEmpty()) {
//
//                DesPractica = null;
//            } else {
//                if (DesPractica.equals("(Todos)")) {
//                    DesPractica = null;
//                } else {
//                }
//            }
//        }
//        if (DesArea == null) {
//            DesArea = null;
//
//        } else {
//            if (DesArea.isEmpty()) {
//                //obtengo el la descripcion del area por el usuario y le asgino
//                DesArea = null;
//            } else {
//                if (DesArea.equals("(Todos)")) {
//                    DesArea = null;
//                } else {
//                    if (DesGrupo.equals("MED")) {
//                        List LisArea = admJPA.getAreaXusuario(Usuario);
//                        Area are = (Area) LisArea.get(0);
//                        DesArea = are.getDescripcion();
//                    }
//                }
//            }
//
//        }
//
//        datos = admJPA.getPracticaXorden(null, idOrden, idHistoria, DesPractica, DesArea, false, "p.descripcion");
////            datos = admJPA.getPracticaXorden(idOrden, idHistoria, DesPractica, DesArea, false, false);s
//        return datos;
//    }
//  public List<Object> getObjetosOrdenado(String RefEntidad, String RefColumna,
//            boolean bandera) throws NamingException {
//
//        List datos = null;
//        AdministradorGenericoBean admJPAG;
//
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        datos = admJPAG.getObjetosOrdenado(RefEntidad, RefColumna, bandera);
//
//        return datos;
//    }
//   public List<Object> getXMLResultadosInforme(String campo, String idHistoria,
//            String IdPractica, String area, String idOrden, String IdMedico,
//            String Empresa, String cedula, Date FecInicio, Date FecHAsta,
//            String ordenadoPo, boolean agrupadoPor) throws NamingException {
//
//        List datos = null;
//        AdministradorGlobalBean admJPA;
//
//        contexto = new InitialContext();
//        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//        if (campo.isEmpty()) {
//            campo = null;
//        }
//        if ((area.isEmpty()) || (area.equals("(Todos)"))) {
//            area = null;
//        }
//        if (idHistoria.isEmpty()) {
//            idHistoria = null;
//        }
//        if (IdPractica.isEmpty()) {
//            IdPractica = null;
//        }
//        if (idOrden.isEmpty()) {
//            idOrden = null;
//        }
//        if (IdMedico.isEmpty()) {
//            IdMedico = null;
//        }
//        if (Empresa.isEmpty()) {
//            Empresa = null;
//        }
//        if (cedula.isEmpty()) {
//            cedula = null;
//        }
//        if (ordenadoPo.isEmpty()) {
//            ordenadoPo = null;
//        }
//
//        datos = admJPA.getXmlResultadoInforme(campo, idHistoria, IdPractica, idOrden, IdMedico, Empresa, cedula, area, FecInicio, FecHAsta, ordenadoPo, agrupadoPor);
////"xr.fecha", objHistee.getId().toString(), "", "", "", "", "", "", null, null, "xr.fecha", true);
//        return datos;
//    }
//
//   public List<Object> getObjetosLike(String Entidad, String Columna,
//            String Parametro, boolean bandera) throws NamingException {
//
//        List datos = null;
//        AdministradorGenericoBean admJPAG;
//
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        datos = admJPAG.getObjetosLike(Entidad, Columna, Parametro, bandera);
//
//        return datos;
//    }
//    public List<Object> getCDatos(Object table, Map<String, Object> where, Object seleccion, List group, List order) throws NamingException {
//        List data = null;
//        List<String> vacios = new ArrayList<String>();
//
//        //Se verifica que parametros estan vacios
//        for (Map.Entry<String, Object> entry : where.entrySet()) {
//            if ((entry.getValue() == null) || (entry.getValue().toString().isEmpty())) {
//                vacios.add(entry.getKey());
//            }
//        }
//        //Se remueven los vacios
//        for (String clave : vacios) {
//            where.remove(clave);
//        }
//        for (Map.Entry<String, Object> entry : where.entrySet()) {
//            System.out.print(entry.getKey() + " / " + entry.getValue().getClass());
//            System.out.print(entry.getKey() + " valor " + entry.getValue());
//        }
//
//        AdministradorGenericoBean admJPAG;
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        data = admJPAG.getCListaBase(table, where, seleccion, group, order);
//
//        return data;
//    }
//
//    public Object getCDato(Object table, Map<String, Object> where, Object seleccion, List group, List order) throws NamingException {
//        Object data = null;
//        List<String> vacios = new ArrayList<String>();
//
//        //Se verifica que parametros estan vacios
//        for (Map.Entry<String, Object> entry : where.entrySet()) {
//            if ((entry.getValue() == null) || (entry.getValue().toString().isEmpty())) {
//                vacios.add(entry.getKey());
//            }
//        }
//        //Se remueven los vacios
//        for (String clave : vacios) {
//            where.remove(clave);
//            //System.out.print(clave);
//        }
//        for (Map.Entry<String, Object> entry : where.entrySet()) {
//            System.out.print(entry.getKey() + " / " + entry.getValue().getClass());
//            System.out.print(entry.getKey() + " valor " + entry.getValue());
//        }
//        AdministradorGenericoBean admJPAG;
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        data = admJPAG.getCObjetoBase(table, where, seleccion, group, order);
//        return data;
//    }
//    //</editor-fold>
////<editor-fold defaultstate="expanded" desc="jpa V 1.0">
//
//    public List<Object> getTodosXParametro(String endidad, String campo,
//            String valor, Class tipo, String campo1, boolean lockReg)
//            throws NamingException {
//
//        List datos = null;
//        AdministradorGenericoBean admJPAG;
//
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        datos = admJPAG.getTodosXParametro(endidad, campo, valor, tipo, campo1, lockReg);
//
//        return datos;
//    }
//
//    public List<Object> getTodosXFecha(String entidad, String campo, Date valor, String orden, boolean lockReg)
//            throws NamingException {
//
//        List datos = null;
//        AdministradorGenericoBean admJPAG;
//
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        datos = admJPAG.getTodosXFecha(entidad, campo, valor, orden, lockReg);
//
//        return datos;
//    }
//
//    public List<Object> getTodo(String entidad, String campo, Date valor, String orden, boolean lockReg)
//            throws NamingException {
//
//        List datos = null;
//        AdministradorGenericoBean admJPAG;
//
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
////        datos = admJPAG.getTodos(entidad, campo, valor, orden, lockReg);
//
//        return datos;
//    }
//
//    public Object guardarA(Object obj) throws NamingException {
//        Object dato = null;
//        AdministradorGenericoBean admJPAG;
//
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        dato = admJPAG.guardar(obj);
//
//        return dato;
//
//    }
//
//   
//
//    public List<Object> getObjetos(String Ref, boolean bandera) throws NamingException {
//        List datos = null;
//        AdministradorGenericoBean admJPAG;
//
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        datos = admJPAG.getobjetos(Ref, bandera);
//
//        return datos;
//    }
//
//    
//
//    public List<Object> getObjetosPaciente(String RefEntidad, String Valor) throws NamingException {
//
//        List datos = null;
//        AdministradorGenericoBean admJPAG;
//
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        datos = admJPAG.getObjetosPaciente(RefEntidad, Valor);
//
//        return datos;
//    }
//
//   
//
//   
//
//    
////    String idHistoria, String idPractica , String idOrden , String IdMedico , String empresa , Date fechaInicio , Date fechaFin
//
//    
//
//    public List<Object> getPacienteHistoria(String ref, String valor, boolean lockReg) throws NamingException {
//
//        List datos = null;
//        AdministradorGlobalBean admJPA;
//        contexto = new InitialContext();
//        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//        if (ref.isEmpty()) {
//            ref = null;
//        }
//        if (valor.isEmpty()) {
//            valor = null;
//        }
//        datos = admJPA.getPacienteHistoria(ref, valor, lockReg);
//        return datos;
//    }
//
//    public List<Object> getOrdenesIngreso(String campo, String idHistoria,
//            String IdPractica, String area, String idOrdenI, String idOrdenF, String IdMedico,
//            String Empresa, String cedula, Date FecInicio, Date FecHAsta,
//            String ordenadoPo, boolean agrupadoPor) throws NamingException {
//
//        List datos = null;
//        AdministradorGlobalBean admJPA;
//
//        contexto = new InitialContext();
//        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//        if (campo.isEmpty()) {
//            campo = null;
//        }
//        if ((area.isEmpty()) || (area.equals("(Todos)"))) {
//            area = null;
//        }
//        if (idHistoria.isEmpty()) {
//            idHistoria = null;
//        }
//        if (IdPractica.isEmpty()) {
//            IdPractica = null;
//        }
//        if (idOrdenI.isEmpty()) {
//            idOrdenI = null;
//        }
//        if (idOrdenF.isEmpty()) {
//            idOrdenF = null;
//        }
//        if (IdMedico.isEmpty()) {
//            IdMedico = null;
//        }
//        if (Empresa.isEmpty()) {
//            Empresa = null;
//        }
//        if (cedula.isEmpty()) {
//            cedula = null;
//        }
//        if (ordenadoPo.isEmpty()) {
//            ordenadoPo = null;
//        }
//        datos = admJPA.getOrdenesIngresoData(campo, idHistoria, IdPractica, idOrdenI, idOrdenF, IdMedico, Empresa, cedula, area, FecInicio, FecHAsta, ordenadoPo, agrupadoPor);
//        return datos;
//    }
//
//   
//
//   
//
//    public List<Object> getOrdenesXHistoria(String idHistoria) throws NamingException {
//        List datos = null;
//        AdministradorGlobalBean admJPA;
//
//        contexto = new InitialContext();
//        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//        datos = admJPA.getOrdenXHistoria(idHistoria);
//
//        return datos;
//    }
//
//    public String getAreaXusuario(String usuarioRef, String Grupo) {
//        AdministradorGlobalBean admJPA;
//        String areaUsu = null;
//        Area area;
//        try {
//            contexto = new InitialContext();
//            admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//            if (Grupo.equals("MED")) {
//                List LisArea = admJPA.getAreaXusuario(usuarioRef);
//                area = (Area) LisArea.get(0);
//                if (area.getId() != 0) {
//                    areaUsu = area.getDescripcion();
//                } else {
//                    areaUsu = "(Todos)";
//                }
//
//            } else {
//                areaUsu = "(Todos)";
//            }
//        } catch (Exception e) {
//            e.printStackTrace(System.out);
//        } finally {
//            return areaUsu;
//        }
//    }
//
//    
//
//   
//
//   
//
//    public List getPracticasPorOrdenXml(String idOrden, String idHistoria)
//            throws NamingException {
//
//        List<Object> datos = null;
//        AdministradorGlobalBean admJPA;
//        contexto = new InitialContext();
//        if (idHistoria.isEmpty()) {
//            idHistoria = null;
//        }
//        if (idOrden.isEmpty()) {
//            idOrden = null;
//        }
//        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//        datos = admJPA.getPracticasPorOrdenXml(idOrden, idHistoria);
//
//        return datos;
//    }
//
//    public List getPracticasPorOrden(String idOrden)
//            throws NamingException {
//
//        List<Object> datos = null;
//        AdministradorGlobalBean admJPA;
//        contexto = new InitialContext();
//
//        if (idOrden.isEmpty()) {
//            idOrden = null;
//        }
//        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//        datos = admJPA.getPracticasPorOrden(idOrden);
//
//        return datos;
//    }
//
//    public List getPracticaXFechaHistoria(Date FecHistorias, String idHistoria)
//            throws NamingException {
//
//        List<Object> datos = null;
//        AdministradorGlobalBean admJPA;
//        contexto = new InitialContext();
//        if (idHistoria.isEmpty()) {
//            idHistoria = null;
//        }
//        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//        datos = admJPA.getPracticasPorFecha(FecHistorias, idHistoria);
//
//        return datos;
//    }
//
//    
//
//    
//
//    
    //</editor-fold>
}
//<editor-fold defaultstate="expanded" desc="Get Criterial">
//    public List<Object> getCXMLResultados(Object table, String idHistoria, String IdPractica,
//            String idOrden, String IdMedico, String Empresa) throws NamingException {
//
//        List datos = null;
//        AdministradorGenericoBean admJPA;
////SELECT xr FROM XmlResultado xr JOIN xr.historia h JOIN xr.practica p JOIN xr.orden o JOIN xr.medicos m JOIN o.organizacion org WHERE xr.lockReg = 0 and  h.id = :idHistoria and  p.id = :idPractica and  o.id = :idOrden ORDER BY o.id , p.descripcion
//        Map<String, Object> where2 = new HashMap<String, Object>();
//        contexto = new InitialContext();
//        admJPA = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//
//        if (!(idHistoria.isEmpty())) {
//            where2.put("historia.id ?=", Integer.parseInt(idHistoria));
//        }
//
//        if (!(IdPractica.isEmpty())) {
//            where2.put("practica.id ?=", Integer.parseInt(IdPractica));
//        }
//
//        if (!(idOrden.isEmpty())) {
//            where2.put("orden.id ?=", Long.parseLong(idOrden));
//        }
//
//        if (!(IdMedico.isEmpty())) {
//            where2.put("medicos.id ?=", Integer.parseInt(IdMedico));
//        }
//
//        if (!(Empresa.isEmpty())) {
//            where2.put("empresa ?like", Integer.parseInt(IdMedico));
//        }
//        datos = admJPA.getCListaBase(table, where2, null, null, null);
////                XmlResultado(idHistoria, IdPractica, idOrden, IdMedico, null, null, null);
//
//        return datos;
//    }
//
//    public Object getCObjeto(Object tabla, String refcolumna, String refvalor, boolean bandera) throws NamingException {
//        Object dato = null;
//        try {
//            Map<String, Object> where2 = new HashMap<String, Object>();
//
//            int lock;
//            if (bandera) {
//                lock = 0;
//            } else {
//                lock = 1;
//            }
//            where2.put("lockReg ?=", lock);
//            String refe = refcolumna + " ?= ";
//            where2.put(refe, refvalor);
//            List data = null;
//            AdministradorGenericoBean admJPAG;
//            contexto = new InitialContext();
//            admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//            data = admJPAG.getCListaBase(tabla, where2, dato, null, null);
//            if (data.size() == 1) {
//                dato = data.get(0);
//            }
//        } catch (Exception e) {
//        }
//        return dato;
//    }
//
//    public List getCIObjeto(Object tabla, String refcolumna, Long refvalor, boolean bandera) throws NamingException {
//        Object dato = null;
//        List data = null;
//        try {
//            Map<String, Object> where2 = new HashMap<String, Object>();
//
//            int lock;
//            if (bandera) {
//                lock = 0;
//            } else {
//                lock = 1;
//            }
//            where2.put("lockReg ?=", lock);
//            String refe = refcolumna + " ?= ";
//            where2.put(refe, refvalor);
//
//            AdministradorGenericoBean admJPAG;
//            contexto = new InitialContext();
//            admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//            data = admJPAG.getCListaBase(tabla, where2, null, null, null);
//            if (data.size() == 1) {
//                dato = data.get(0);
//            }
//        } catch (Exception e) {
//        }
//        return data;
//    }
//
//    public List<Object> getCObjetosOrdenado(Object tabla, String RefColumna, boolean bandera) throws NamingException {
//        Map<String, Object> where2 = new HashMap<String, Object>();
//        List<Object> OrdenX = new ArrayList<Object>();
//        OrdenX.add(RefColumna);
//        int lock;
//        if (bandera) {
//            lock = 0;
//        } else {
//            lock = 1;
//        }
//        where2.put("lockReg ?=", lock);
//
//        List datos = null;
//        AdministradorGenericoBean admJPAG;
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        datos = admJPAG.getCListaBase(tabla, where2, null, null, OrdenX);
//
//        return datos;
//    }
//
//    public List<Object> getCabreviaturas(Object tabla, String idAreaP, String IdAreaM) throws NamingException {
//
//        List datos = null;
//        AdministradorGenericoBean admJPAG;
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        Map<String, Object> where2 = new HashMap<String, Object>();
//
//        where2.put("lockReg ?=", 0);
//        if (!(IdAreaM.isEmpty())) {
//            where2.put("Medico.area.id ?=", Integer.parseInt(IdAreaM));
//        }
//        if (!(idAreaP.isEmpty())) {
//            where2.put("Medico.area.id |=", Integer.parseInt(idAreaP));
//        }
//        datos = admJPAG.getCListaBase(tabla, where2, null, null, null);
//        return datos;
//    }
//
//    public String getCAreaXusuario(String usuarioRef, String Grupo) {
//        String areaUsu = null;
//        Area area;
//        try {
//            Object table = new Nombre();
//            Map<String, Object> where2 = new HashMap<String, Object>();
//            where2.put("usuarios.usuario ?like", usuarioRef);
//            List data = null;
//            AdministradorGenericoBean admJPA;
//            contexto = new InitialContext();
//            admJPA = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//            if (Grupo.equals("MED")) {
//                List<String> selec = new ArrayList<String>();
//                selec.add("area");
//                data = admJPA.getCListaBase(table, where2, "area", null, null);
//                area = (Area) data.get(0);
//                if (area.getId() != 0) {
//                    areaUsu = area.getDescripcion();
//                } else {
//                    areaUsu = "(Todos)";
//                }
//            } else {
//                areaUsu = "(Todos)";
//            }
//        } catch (NamingException ex) {
//            Logger.getLogger(AdmiNegocio.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            return areaUsu;
//        }
//    }
//
//    public List getCDatosHistoria(Object tabla, String idHistoria, String idOrden, String NumCedula)
//            throws NamingException {
//
//        List<Object> datos;
//        AdministradorGenericoBean admJPA;
//
//        Map<String, Object> where2 = new HashMap<String, Object>();
//
//        contexto = new InitialContext();
//        admJPA = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        if (!(idOrden.isEmpty())) {
//
//            where2.put("orden.id ?=", Long.parseLong(idOrden));
//        }
//        if (!(idHistoria.isEmpty())) {
//            where2.put("id ?=", Integer.parseInt(idHistoria));
//        }
//        if (!(NumCedula.isEmpty())) {
//            where2.put("numId ?=", NumCedula);
//        }
//        datos = admJPA.getCListaBase(tabla, where2, null, null, null);
////                DatosHistoria(idHistoria, idOrden, NumCedula);
//        return datos;
//    }
////SELECT o FROM ResultadoGrafico o WHERE  o.idXmlResultado = :valor ORDER BY o.idXmlResultado
//
//    public List<Object> getCTodosXParametro(Object Tabla, String campo,
//            String valor, Class tipo, String campo1, boolean lockReg)
//            throws NamingException {
//        Map<String, Object> wSQL = new HashMap<String, Object>();
//        List datos = null;
//        int lock;
//        AdministradorGenericoBean admJPAG;
//        if (lockReg) {
//            lock = 0;
//        } else {
//            lock = 1;
//        }
//        wSQL.put("lockReg ?=", lock);
//        if (!(valor.isEmpty())) {
//            wSQL.put(campo + "?=", valor);
//        }
//        contexto = new InitialContext();
//        admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        List orden = new ArrayList();
//        orden.add(campo1);
//        datos = admJPAG.getCListaBase(Tabla, wSQL, null, null, orden);
//        return datos;
//    }
//
//    public List<Object> getCObjetosLike(Object tabla, String Columna,
//            String Parametro, boolean bandera) throws NamingException {
//        List datos = null;
//        AdministradorGenericoBean admJPAG;
//        try {
//            contexto = new InitialContext();
//            admJPAG = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//            Map<String, Object> wSQL = new HashMap<String, Object>();
//            wSQL.put("descripcion ?like", Parametro);
//
//            datos = admJPAG.getCListaBase(tabla, wSQL, null, null, null);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//        return datos;
//    }
//
//    public List<Object> getCPracticaxOrden(Object tabla, String idOrden, String idHistoria,
//            String DesPractica, String DesArea, String Usuario, String DesGrupo)
//            throws NamingException {
//
//        List datos;
//        Map<String, Object> wSQL = new HashMap<String, Object>();
//        AdministradorGenericoBean admJPA;
//        contexto = new InitialContext();
//        admJPA = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//        AdministradorGlobalBean admJPAG;
////        contexto = new InitialContext();
//        admJPAG = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
////        SELECT  po  FROM PracticaXOrden po JOIN po.orden o JOIN o.historia h JOIN po.practica p JOIN p.area a 
////        WHERE po.lockReg = 0 and  h.id = :idHistoria and  p.descripcion = :practica and  a.descripcion = :area ORDER BY p.descripcion
//        if (!(idHistoria.isEmpty())) {
//            wSQL.put("orden.historia.id ?=", Integer.parseInt(idHistoria));
//
////            idHistoria = null;
//        }
//        if (!(idOrden.isEmpty())) {
//            wSQL.put("orden.id ?=", Long.parseLong(idOrden));
//        }
//
//        if (DesPractica == null) {
//            DesPractica = null;
//        } else {
//            if (DesPractica.isEmpty()) {
//
//                DesPractica = null;
//            } else {
//                if (DesPractica.equals("(Todos)")) {
//                    DesPractica = null;
//                } else {
//
//                    wSQL.put("practica.descripcion ?like", DesPractica);
//                }
//            }
//        }
//        if (DesArea == null) {
//            DesArea = null;
//
//        } else {
//            if (DesArea.isEmpty()) {
//                //obtengo el la descripcion del area por el usuario y le asgino
//                DesArea = null;
//            } else {
//                if (DesArea.equals("(Todos)")) {
//                    DesArea = null;
//                } else {
//                    if (DesGrupo.equals("MED")) {
//                        List LisArea = admJPAG.getAreaXusuario(Usuario);
//                        Area are = (Area) LisArea.get(0);
//                        DesArea = are.getDescripcion();
//                        wSQL.put("practica.area.descripcion ?like", DesArea);
//
//                    } else {
//                        wSQL.put("practica.area.descripcion ?like", DesArea);
//                    }
//                }
//            }
//
//        }
//        List orden = new ArrayList();
//        orden.add("practica.descripcion");
//        datos = admJPA.getCListaBase(tabla, wSQL, null, null, orden);
//        System.out.println("numero de CLista " + datos.size());
//        List datos2 = admJPAG.getPracticaXorden(null, idOrden, idHistoria, DesPractica, DesArea, false, "p.descripcion");
//        System.out.println("numero de PracticaxOrden " + datos2.size());
//        return datos;
//    }
//
//    public List getCPracticasPorOrdenXml(String idOrden, String idHistoria)
//            throws NamingException {
//
//        List<Object> datos = null;
//        AdministradorGlobalBean admJPA;
//        contexto = new InitialContext();
//        if (idHistoria.isEmpty()) {
//            idHistoria = null;
//        }
//        if (idOrden.isEmpty()) {
//            idOrden = null;
//        }
//        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//        datos = admJPA.getPracticasPorOrdenXml(idOrden, idHistoria);
//
//        return datos;
//    }
//
//    public List geCPracticaXFechaHistoria(Date FecHistorias, String idHistoria)
//            throws NamingException {
//
//        List<Object> datos = null;
//        AdministradorGlobalBean admJPA;
//        contexto = new InitialContext();
//        if (idHistoria.isEmpty()) {
//            idHistoria = null;
//        }
//        admJPA = (AdministradorGlobalBean) contexto.lookup(contextoGlobal);
//        datos = admJPA.getPracticasPorFecha(FecHistorias, idHistoria);
//
//        return datos;
//    }
//
//    public List<Object> getCXMLResultadosInforme(Object Tabla, String campo, String idHistoria,
//            String IdPractica, String area, String idOrden, String IdMedico,
//            String Empresa, String cedula, Date FecInicio, Date FecHAsta,
//            String ordenadoPo, boolean agrupadoPor) throws NamingException {
//        String seleccion = null;
//        List datos = null;
//        AdministradorGenericoBean admJPA;
//        Map<String, Object> condiciones = new HashMap<String, Object>();
//        contexto = new InitialContext();
//        admJPA = (AdministradorGenericoBean) contexto.lookup(contextoGenerico);
//
//
//
//        if (!(campo.isEmpty())) {
//            seleccion = campo;
//
//        }
//        if ((!(area.isEmpty())) || (!(area.equals("(Todos)")))) {
//            condiciones.put("historia.area.descripcion ?like", area);
//        }
//        if (!(idHistoria.isEmpty())) {
//            condiciones.put("historia.id ?=", Integer.parseInt(idHistoria));
//        }
//        if (!(IdPractica.isEmpty())) {
//            condiciones.put("practica.id ?=", Integer.parseInt(IdPractica));
//        }
//        if (!(idOrden.isEmpty())) {
//            condiciones.put("orden.id ?=", Long.parseLong(idOrden));
//        }
//        if (!(IdMedico.isEmpty())) {
//            condiciones.put("Medicos.id ?=", Integer.parseInt(IdMedico));
//        }
//        if (!(Empresa.isEmpty())) {
//            condiciones.put("orden.organizacion.descripcio ?like", Empresa);
//        }
//        if (!(cedula.isEmpty())) {
//            condiciones.put("historia.numId ?like", cedula);
//        }
//        if (!(FecInicio == null)) {
//            condiciones.put("fecIni ?>=", FecInicio);
//        }
//        if (!(FecInicio == null)) {
//            condiciones.put("fecIni ?<=", FecHAsta);
//        }
//        List orden = new ArrayList();
//        if (!(ordenadoPo.isEmpty())) {
////            orden.add(ordenadoPo);
//            orden.add("orden.id");
//            orden.add("practica.descripcion");
//        }
//        List grupo = new ArrayList();
//        grupo.add(ordenadoPo);
//
//        datos = admJPA.getCListaBase(Tabla, condiciones, seleccion, grupo, orden);
//        return datos;
//    }
//</editor-fold>


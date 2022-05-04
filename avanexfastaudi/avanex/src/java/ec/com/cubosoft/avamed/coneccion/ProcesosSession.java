package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.modelo.core.CsPerxgru;
import ec.com.cubosoft.avamed.modelo.nextla.sessionOk;
import java.util.ArrayList;
import java.util.List;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 * @author Patricia Amoroso
 * @author Juan Pablo Chavez
 */
public class ProcesosSession extends GenericForwardComposer {

    List<Object> informSession = new ArrayList<Object>();

    //la session se llama Usuario
    public void crearSession(Session session, String NombreSession, List<Object> informe) {
        session.setAttribute("activo", informe);
      //  session.setMaxInactiveInterval(30);
    }

    public Object ObtenerAtributoSession(int pos, Session session) {
        Object atributo = null;
        try {
            informSession = (List) session.getAttribute("activo");
            if ((pos > 0) && (pos <= informSession.size())) {
                atributo = informSession.get(pos);
                return atributo;
            } else {
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error al obtener Atributo Session"+e.getMessage());
            return null;
        }
    }

    public void AgregarAtributoSession(int pos, Object valor, Session session) {
        //PARA VALIDAR SI TIENE ESE PERMISO
        informSession = (List) session.getAttribute("activo");
        switch (pos) {
            case 2:
                sessionOk sessi = (sessionOk) valor;
                informSession.remove(pos);
                informSession.add(pos, sessi);
                break;
            default:
        }
        session.setAttribute("activo", informSession);
    }

    public CsPerxgru ObtenerPermisosPgina(Session session) {
        //PARA VALIDAR SI TIENE ESE PERMISO
        String idPermiso = new String();
        List<CsPerxgru> listPermisos = (List<CsPerxgru>) ObtenerAtributoSession(6, session);
        String a = (String) ObtenerAtributoSession(4, session);
        CsPerxgru permisosMenuID = null;
        for (CsPerxgru objpermiso : listPermisos) {
            idPermiso = objpermiso.getCsPerxgruPK().getCodPer().toString();
            if (idPermiso.equals(a)) {
                permisosMenuID = objpermiso;
            }
        }
        return permisosMenuID;
    }

}

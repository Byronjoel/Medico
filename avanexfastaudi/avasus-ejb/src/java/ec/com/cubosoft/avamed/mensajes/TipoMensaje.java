package ec.com.cubosoft.avamed.mensajes;

import java.io.Serializable;

/**
 * Esta clase permite administrar los mensajes de error más personalizados.
 * <P>
 * <H6>Soporte:APLINFO <I>
 * patyamor193@hotmail.com  </I></H6>
 * @author Patricia Amoroso
 */

public enum TipoMensaje implements Serializable {
    GRABAR,
    ERROR_GRABAR,
    ELIMINAR,
    ERROR_ELIMINAR,
    MODIFICAR,
    ERROR_MODIFICAR,
    ERROR_BUSCAR,
    ERROR_BUSCAR_VACIO,
    INCOMPLETO,
    OK,
    ERROR_LISTAR,
    ERROR_NUEVO_CODIGO,
    ERROR_FECHADB,
    USER_INACTIVO,
    USER_NO_REGISTERED,
    USER_NO_AD,
    INICIAR_SESION,
    SYSTEM_ERROR,
    CAMBIAR_CLAVE,
    ERROR_CAMBIAR_CLAVE,
    ERROR_MAIL,
    ERROR_DOCUMENTO,
    USER_NO_AUTORIZADO,
    USER_NO_LOGIN,
    SYSTEMA_NULL,
    REGISTRO_EXISTE,
    XML_ERROR,
    ERROR_ARCHIVO
}

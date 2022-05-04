package ec.com.cubosoft.avamed.mensajes;

import java.util.ResourceBundle;

/**
 * Esta clase permite administrar los mensajes de error más personalizados.
 * <P>
 * <H6>Soporte:APLINFO <I>patyamor193@hotmail.com  </I></H6>
 *
 * @author Patricia Amoroso
 *
 *
 */
//extends Exception
public class AdmMensajes implements java.io.Serializable {

    private ResourceBundle bundle = ResourceBundle.getBundle("ec.com.cubosoft.avamed.mensajes.message");
    private TipoMensaje msn;

    public AdmMensajes() {
    }

    //<editor-fold defaultstate="collapsed" desc="TIPOS DE ERRORES">
    /**
     * Constructor.
     *
     * @param apliErrorMessage tipo de mensaje
     * @param message el Mensaje
     * @param cause la causa
     */
    public AdmMensajes(TipoMensaje apliErrorMessage, String message, Throwable cause) {
//        super(message, cause);
        this.msn = apliErrorMessage;
    }

    /**
     * Constructor.
     *
     * @param apliErrorMessage tipo de mensaje
     * @param cause la causa
     */
    public AdmMensajes(TipoMensaje apliErrorMessage) {
//        super(cause.getMessage(), cause);
        this.msn = apliErrorMessage;
    }

    public AdmMensajes(TipoMensaje apliErrorMessage, Throwable cause) {
//        super(cause.getMessage(), cause);
        this.msn = apliErrorMessage;
    }

    /**
     * Constructor.
     *
     * @param apliErrorMessage tipo de mensaje
     * @param message el mensaje
     */
    public AdmMensajes(TipoMensaje apliErrorMessage, String message) {
//        super(message);
        this.msn = apliErrorMessage;
    }

//    /**
//     * Constructor.
//     * @param apliErrorMessage el tipo de mensaje.
//     */
//    public AdmMensajes(TipoMensaje apliErrorMessage) {
////        super("No aplica");
//        this.msn = apliErrorMessage;
//    }
// </editor-fold>
    /**
     * Obtiene el tipo de mensaje.
     *
     * @return el tipo de mensaje.
     */
    public TipoMensaje getApliErrorMessage() {
        return msn;
    }

    /**
     * Obtiene el mensaje de error.
     *
     * @return el mensjae
     */
//    @Override
    public String getMessage(TipoMensaje msn) {
        String msnOut = "Msn no existe";
        switch (msn) {
            case GRABAR:
                msnOut = bundle.getString("msg.grabar");
                break;
            case ERROR_GRABAR:
                msnOut = bundle.getString("msg.grabar.error");
                break;
            case ELIMINAR:
                msnOut = bundle.getString("msg.eliminar");
                break;
            case ERROR_ELIMINAR:
                msnOut = bundle.getString("msg.eliminar.error");
                break;
            case MODIFICAR:
                msnOut = bundle.getString("msg.actualizar");
                break;
            case ERROR_MODIFICAR:
                msnOut = bundle.getString("msg.actualizar.error");
                break;
            case ERROR_BUSCAR:
                msnOut = bundle.getString("msg.buscar.error");
                break;
            case OK:
                msnOut = bundle.getString("msg.ok");
                break;
            case ERROR_BUSCAR_VACIO:
                msnOut = bundle.getString("msg.buscar.vacio");
                break;

            case ERROR_LISTAR:
                msnOut = bundle.getString("msg.listar.error");
                break;
            case INCOMPLETO:
                msnOut = bundle.getString("msg.incompleto");
                break;

            case ERROR_NUEVO_CODIGO:
                msnOut = bundle.getString("msg.nuevoCodigo.error");
                break;
            case ERROR_FECHADB:
                msnOut = bundle.getString("msg.fechaBd.error");
                break;
            case USER_INACTIVO:
                msnOut = bundle.getString("msg.user.inactivo");
                break;
            case USER_NO_REGISTERED:
                msnOut = bundle.getString("msg.user.noRegistered");
                break;
            case USER_NO_AD:
                msnOut = bundle.getString("msg.user.noActiveDirectory");
                break;
            case INICIAR_SESION:
                msnOut = bundle.getString("msg.iniciarSesion.error");
                break;
            case SYSTEM_ERROR:
                msnOut = bundle.getString("msg.sistema.error");
                break;
            case CAMBIAR_CLAVE:
                msnOut = bundle.getString("msg.cambiarClave");
                break;
            case ERROR_CAMBIAR_CLAVE:
                msnOut = bundle.getString("msg.cambiarClave.error");
                break;
            case ERROR_MAIL:
                msnOut = bundle.getString("msg.mail.error");
                break;
            case ERROR_DOCUMENTO:
                msnOut = bundle.getString("msg.documento.error");
                break;
            case SYSTEMA_NULL:
                msnOut = bundle.getString("msg.systema.null");
                break;
            case USER_NO_AUTORIZADO:
                msnOut = bundle.getString("msg.user.noAurorizado");
                break;
            case USER_NO_LOGIN:
                msnOut = bundle.getString("msg.user.noLogin");
                break;
            case REGISTRO_EXISTE:
                msnOut = bundle.getString("msg.archivo");
                break;
            case XML_ERROR:
                msnOut = bundle.getString("xml.error");
                break;
            case ERROR_ARCHIVO:
                msnOut = bundle.getString("msg.archivo.error");
                break;
        }
        return msnOut;
    }

    public String getDescripcionAbreviatura(String abr) {

        String msnOut = "#";
//                     "Abreviatura no existe";
        if (abr == null) {
            abr = "";
        } else {
            if (abr.equals("C")) {
                msnOut = "CASADO";
            } else {
                if (abr.equals("S")) {
                    msnOut = "SOLTERO";
                } else {
                    if (abr.equals("D")) {
                        msnOut = "DIVORCIADO";
                    } else {
                        if (abr.equals("U")) {
                            msnOut = "UNION LIBRE";
                        } else {
                            if (abr.equals("V")) {
                                msnOut = "VIUDO";
                            } else {
                                if (abr.equals("M")) {
                                    msnOut = "MASCULINO";
                                } else {
                                    if (abr.equals("F")) {
                                        msnOut = "FEMENINO";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return msnOut;
    }
}

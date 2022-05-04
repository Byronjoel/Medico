/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * Patricia Amoroso Ochoa
 */
package ec.com.cubosoft.core;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrador
 */
public class CSValidarString {

    public static String encriptarMD5(String palabra) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] b = md.digest(palabra.getBytes());
        int size = b.length;
        StringBuffer h = new StringBuffer(size);
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255; // unsigned conversion
            if (u < 16) {
                h.append("0" + Integer.toHexString(u));
                //h.append("0"+Integer.toOctalString(u));
            } else {
                h.append(Integer.toHexString(u));
                // h.append(Integer.toOctalString(u));
            }
        }
        return h.toString();
    }

    public static Boolean ValidaString(String cadena) {
        Pattern patron = Pattern.compile("[^A-Za-z áéíóúÁÉÍÓÚ]");
        Matcher encaja = patron.matcher(cadena);
        if (!encaja.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean tamano(String cadena, Integer tipo) {
        boolean bandera = false;
        switch (tipo) {
            case 1:
                if (cadena.length() == 8) {
                    bandera = true;
                }
                break;
            case 2:
                if (cadena.length() <= 15) {
                    bandera = true;
                }
                break;
            case 3:
                if (cadena.length() <= 25) {
                    bandera = true;
                }
                break;
            case 4:
                if (cadena.length() <= 64) {
                    bandera = true;
                }
                break;
            case 5:
                if (cadena.length() <= 128) {
                    bandera = true;
                }
                break;
            default:
                bandera = false;
        }
        ;
        return bandera;
    }

    public static Boolean Validafrase(String cadena) {
        Pattern patron = Pattern.compile("[^A-Za-z áéíóúÁÉÍÓÚ,:;.]");
        Matcher encaja = patron.matcher(cadena);
        if (!encaja.find()) {
            return true;
        } else {
            return false;
        }
    }

    public static Boolean ValidaSintafrase(String cadena) {
        String aux = cadena;
        boolean bandera;
        String x = "";
        Integer i = 0;
        int f = 0;
        while (aux.length() >= 1) {
            f = aux.indexOf(" ");
            if (f > 0) {
                aux = aux.substring(0, aux.indexOf(" "));
            } else {
                aux = aux.substring(0, aux.indexOf("."));
            }
            x = aux.substring(aux.length() - 1);
            i = i + aux.length() + 1;
            if ((x.equals(";")) || (x.equals(":")) || (x.equals(",")) || (x.equals("."))) {
                bandera = true;

            } else {
                Pattern patron = Pattern.compile("[^A-Za-záéíóúÁÉÍÓÚ]");
                Matcher encaja = patron.matcher(x);
                if (!encaja.find()) {
                    bandera = true;
                } else {
                    bandera = false;
                }
            }
            aux = cadena.substring(i, cadena.length());
        }
        return true;
    }

    public static String remplazartildes(String cadena) {
        cadena = cadena.replace("á", "a");
        cadena = cadena.replace("é", "e");
        cadena = cadena.replace("í", "i");
        cadena = cadena.replace("ó", "o");
        cadena = cadena.replace("ú", "u");
        cadena = cadena.replace("Á", "A");
        cadena = cadena.replace("É", "E");
        cadena = cadena.replace("Í", "I");
        cadena = cadena.replace("Ó", "O");
        cadena = cadena.replace("Ú", "U");
        cadena = cadena.replace("ñ", "n");
        cadena = cadena.replace("Ñ", "N");
        return cadena;

    }
    public String remplazarGuiones(String cadena) {
        cadena = cadena.replace("-", " ");
        cadena = cadena.replace("_", " ");
        return cadena;
    }
    public String remplazarEspacios(String cadena) {
        cadena = cadena.replace(" ", "-");
        cadena = cadena.replace(" ", "_");
        return cadena;
    }
}

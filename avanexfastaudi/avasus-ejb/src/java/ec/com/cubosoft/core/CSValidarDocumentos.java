/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.core;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Administrador
 */
public class CSValidarDocumentos {

    public static short RUC_SOCIEDADES_PRIVADAS_EXTRANJEROS = 1;
    public static short RUC_SOCIEDADES_PUBLICAS = 2;
    public static short RUC_PERSONAS_NATURALES = 3;
    public static short CEDULA_VALIDA = 4;
    public static short RUC_CEDULA_NO_VALIDA = -1;
    private static short PROVINCIA_INICIAL = 1;
    private static short PROVINCIA_FINAL = 24;
    /**
     * Tipo de persona
     * 1 = persona natural, ingresa cédula
     * 2= persona jurídica, ingresa RUC
     */
    public static short PERSONA_NATURAL = 1;
    public static short PERSONA_JURIDICA = 2;
    public static short PASSAPORTE = 3;
    private static CSValidarDocumentos SINGLETON = new CSValidarDocumentos();
    public static int validaRucCedula(String ruc) {
        //valido el numero sea cedula 10  o ruc 13
        if (ruc.length() == 13 || ruc.length() == 10) {
            try {
                //comprobamos si dato es numérico
                long l = Long.parseLong(ruc);
            } catch (Exception e) {
                return RUC_CEDULA_NO_VALIDA;
            }
            try {
                int provincia = Integer.parseInt(ruc.substring(0, 2));
                //si esta entre 0
                if (provincia < PROVINCIA_INICIAL || provincia > PROVINCIA_FINAL) // recomendable utilizar archivo de propiedades
                {
                    return RUC_CEDULA_NO_VALIDA;
                }
                int tercerDigito = Integer.parseInt(ruc.substring(2, 3));
                if (tercerDigito == 9) { // es RUC para Sociedades Privadas y Extranjeros sin cédula

                    int[] modulo11 = {4, 3, 2, 7, 6, 5, 4, 3, 2};
                    int numeros[] = new int[9];
                    for (int i = 0; i < 9; i++) {
                        numeros[i] = Integer.parseInt(ruc.substring(i, i + 1));
                        modulo11[i] *= numeros[i];
                    }
                    int suma = 0;
                    for (int i = 0; i < 9; i++) {
                        suma += modulo11[i];
                    }
                    int digitVerificador = 11 - (suma % 11);
                    if (suma % 11 == 0) {
                        digitVerificador = 0;
                    } // si el residuo es 0 digitoVerificador es 0

                    int decimoDigito = Integer.parseInt(ruc.substring(9, 10));
                    if (decimoDigito != digitVerificador) {
                        return RUC_CEDULA_NO_VALIDA;
                    }
                    int numeroEstablecimientos = Integer.parseInt(ruc.substring(10, 13));
                    if (numeroEstablecimientos > 9) {
                        return RUC_CEDULA_NO_VALIDA;
                    }
                    return RUC_SOCIEDADES_PRIVADAS_EXTRANJEROS;
                } else if (tercerDigito == 6) { // del RUC para Sociedades Públicas

                    int[] modulo11 = {3, 2, 7, 6, 5, 4, 3, 2};
                    int numeros[] = new int[8];
                    for (int i = 0; i < 8; i++) {
                        numeros[i] = Integer.parseInt(ruc.substring(i, i + 1));
                        modulo11[i] *= numeros[i];
                    }
                    int suma = 0;
                    for (int i = 0; i < 8; i++) {
                        suma += modulo11[i];
                    }
                    int digitVerificador = 11 - (suma % 11);
                    if (suma % 11 == 0) {
                        digitVerificador = 0;
                    } // si el residuo es 0 digitoVerificador es 0

                    int decimoDigito = Integer.parseInt(ruc.substring(8, 9));
                    if (decimoDigito != digitVerificador) {
                        return RUC_CEDULA_NO_VALIDA;
                    }
                    int numeroEstablecimientos = Integer.parseInt(ruc.substring(9, 13));
                    if (numeroEstablecimientos > 9) {
                        return RUC_CEDULA_NO_VALIDA;
                    }
                    return RUC_SOCIEDADES_PUBLICAS;
                } else if (tercerDigito < 6) { // RUC para Personas Naturales o cédula

                    int[] modulo11 = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int numeros[] = new int[9];
                    for (int i = 0; i < 9; i++) {
                        numeros[i] = Integer.parseInt(ruc.substring(i, i + 1));
                        modulo11[i] *= numeros[i];
                        if (modulo11[i] >= 10) {
                            modulo11[i] -= 9;
                        }
                    }
                    int suma = 0;
                    for (int i = 0; i < 9; i++) {
                        suma += modulo11[i];
                    }
                    int digitVerificador = 10 - (suma % 10);
                    if (suma % 10 == 0) {
                        digitVerificador = 0;
                    } // si el residuo es 0 digitoVerificador es 0

                    int decimoDigito = Integer.parseInt(ruc.substring(9, 10));
                    if (decimoDigito != digitVerificador) {
                        return RUC_CEDULA_NO_VALIDA;
                    }
                    if (ruc.length() == 13) {
                        int numeroEstablecimientos = Integer.parseInt(ruc.substring(10, 13));
                        if (numeroEstablecimientos > 9) {
                            return RUC_CEDULA_NO_VALIDA;
                        }
                        return RUC_PERSONAS_NATURALES;
                    }
                    return CEDULA_VALIDA; // es una cedula
                }
            } catch (Exception e) {
//                log.error("validaRucCedula(" + ruc + ") " + e);
                return RUC_CEDULA_NO_VALIDA;
            }
        }
        return RUC_CEDULA_NO_VALIDA; // ruc o cédula incorrecta
    }
    public static boolean isRucCedulaValido(String ruc) {
        int dato = validaRucCedula(ruc);
        if (dato < 1 || dato > 4) {
            return false;
        }
        return true;
    }
    public static boolean isCedulaValido(String ruc) {
        int dato = validaRucCedula(ruc);
        if (dato != 4) {
            return false;
        }
        return true;
    }
    public static boolean isRucValido(String ruc) {
        int dato = validaRucCedula(ruc);
        if (dato < 1 || dato > 3) {
            return false;
        }
        return true;
    }
    public static boolean validarEmail(String email) throws Exception {
        try {
            Pattern p = Pattern.compile("^[\\w-]+(\\.[\\w-]+)*@([a-z0-9-]+(\\.[a-z0-9-]+)*?\\.[a-z]{2,6}|(\\d{1,3}\\.){3}\\d{1,3})(:\\d{4})?$");
            Matcher m = p.matcher(email);
            if (!m.find()) {
                return false;
            }
        } catch (Exception e) {
            //log.info("validarEmail(" + email + ") " + e);
            throw new Exception("No se ha podido verificar el email", e);
        }
        return true;
    }
}

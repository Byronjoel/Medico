/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Administrador
 */
public class CSValidarFecha {

    public Date getFechaActual() throws Exception {
        return getFechaDb();
    }
    public static Date getFechaDb() throws Exception {
        String sql1 = null;
        PreparedStatement pst1;
        ResultSet rs1;
        Date fecha = new Date();
        Connection con = CSDataBaseFactory.getInstance().getDataSource().getConnection();
        String dbProductName = con.getMetaData().getDatabaseProductName();
        try {
            sql1 = "SELECT now()";
            pst1 = con.prepareStatement(sql1);
            rs1 = pst1.executeQuery();
            rs1.next();
            fecha = rs1.getDate(1);
            rs1.close();
            pst1.close();
        } catch (Exception e) {
            throw e;
        } finally {
            con.close();
        }
        return fecha;
    }
    public static boolean validarFecha(String fecha) throws Exception {
        boolean bandera = false;
        try {
            int a = Integer.parseInt(fecha.substring(0, 4));
            int m = Integer.parseInt(fecha.substring(5, 7));
            int d = Integer.parseInt(fecha.substring(8));
            int ref1;
            if ((fecha.length() == 10) || (fecha.length() == 9) || (fecha.length() == 7)) {  //valido ano
                if ((a > 1000) && (a < 3000)) {
                    if ((a % 4 == 0) && ((a % 100 != 0) || (a % 400 == 0))) { //bisiesto
                        if ((m == 02) || (m == 2)) { //dias
                            if ((d > 0) && (d <= 29)) {
                                bandera = true;
                            } else {
                                bandera = false;
                            }
                        }
                    }
                    //si es o no bisiesto debe cumplir
                    if ((m > 0) && (m <= 12)) {
                        if ((m == 1) || (m == 3) || (m == 5) || (m == 7) || (m == 8) || (m == 10) || (m == 12)) {  //dias 31
                            if ((d > 0) && (d <= 31)) {
                                bandera = true;
                            } else {
                                bandera = false;
                            }
                        } else {
                            if (m == 2) {
                                if ((d > 0) && (d <= 28)) {
                                    bandera = true;
                                } else {
                                    bandera = false;
                                }
                            } else {
                                if ((d > 0) && (d <= 30)) {
                                    bandera = true;
                                } else {
                                    bandera = false;
                                }
                            }
                        }
                    } else {
                        bandera = false;
                    }
                }
            }
        } catch (Exception e) {
            bandera = false;
        } finally {
            return bandera;
        }
    }
    public static boolean validarNacimiento(String FecNa) {
        boolean band = false;
        try {
            String fech = getFechaDb().toString();
            int aa = Integer.parseInt(fech.substring(0, 4));
            int ma = Integer.parseInt(fech.substring(5, 7));
            int da = Integer.parseInt(fech.substring(8));
            int an = Integer.parseInt(FecNa.substring(0, 4));
            int mn = Integer.parseInt(FecNa.substring(5, 7));
            int dn = Integer.parseInt(FecNa.substring(8));
            if ((aa - an) > 0) {
                band = true;
            } else {
                if ((aa - an) < 0) {
                    band = false;
                } else {
                    if ((ma - mn) > 0) {
                        band = true;
                    } else {
                        if (((ma - mn) == 0) && ((da - dn) > 0)) {
                            band = true;
                        } else {
                            band = false;
                        }
                    }
                }
            }
        } catch (Exception ex) {
            band = false;
        } finally {
            return band;
        }
    }
    public String CalcularEdad(String FecNacimiento) throws Exception {
        if ((validarFecha(FecNacimiento)) && (validarNacimiento(FecNacimiento))) {
            String fechaActual = getFechaActual().toString();
            int aa = Integer.parseInt(fechaActual.substring(0, 4));
            int ma = Integer.parseInt(fechaActual.substring(5, 7));
            int da = Integer.parseInt(fechaActual.substring(8));
            int an = Integer.parseInt(FecNacimiento.substring(0, 4));
            int mn = Integer.parseInt(FecNacimiento.substring(5, 7));
            int dn = Integer.parseInt(FecNacimiento.substring(8));
            int anos = aa - an;
            int mes = ma - mn;
            int dias = da - dn;
            if (mes < 0) {
                anos = anos - 1;
                if (dias >= 0) {
                    mes = 12 - (mes * (-1));
                } else {
                    if (dias <= 0) {
                        mes = 11 - (mes * (-1));
                    }
                }
            } else {
                if (mes == 0) {
                    if (dias < 0) {
                        anos = anos - 1;
                    }

                } else {
                    if (mes > 1) {
                        if (dias < 0) {
                            mes = mes - 1;

                        }
                    }
                }

            }
            String edad = anos + " Anos " + "con " + mes + " meses de edad";
            return edad;
        } else {
            return "Incorrecto";
        }
    }
    public boolean verificar(String fecini, String fecfin, String fecref) throws Exception {
        boolean banderai = false;
        boolean banderaf = false;

        if ((validarFecha(fecini)) && (validarFecha(fecfin)) && (validarFecha(fecref))) {
            String fechaActual = getFechaActual().toString();
            int ai = Integer.parseInt(fecini.substring(0, 4));
            int mi = Integer.parseInt(fecini.substring(5, 7));
            int di = Integer.parseInt(fecini.substring(8));
            int af = Integer.parseInt(fecfin.substring(0, 4));
            int mf = Integer.parseInt(fecfin.substring(5, 7));
            int df = Integer.parseInt(fecfin.substring(8));
            int ar = Integer.parseInt(fecref.substring(0, 4));
            int mr = Integer.parseInt(fecref.substring(5, 7));
            int dr = Integer.parseInt(fecref.substring(8));
            if (ai < ar) {
                banderai = true;
            } else {
                if ((ai == ar) && ((mi < mr))) {
                    banderai = true;
                } else {
                    if ((mi == mr) && (di <= dr)) {
                        banderai = true;
                    }
                }
            }
            if ((af > ar)) {
                banderaf = true;
            } else {
                if ((af == ar) && ((mf > mr))) {
                    banderaf = true;
                } else {
                    if ((mf == mr) && (df >= dr)) {
                        banderaf = true;
                    }
                }
            }
        }
        if ((banderai == true) && (banderaf == true)) {
            return true;
        } else {
            return false;
        }
    }
}
/*
 * Ahora que está todo en silencio
y que la calma me besa el corazón
os quiero decir adiós
porque ha llegado la hora
de que andéis el camino ya sin mi,
hay tanto por lo que vivir
no llores cielo y vuélvete a enamorar
me gustaría volver a verte sonreír

Pero mi vida
yo nunca podré olvidarte
y sólo el viento sabe
lo que has sufrido por amarme
hay tantas cosas
que nunca te dije en vida
que eres todo cuanto amo
y ahora que ya no estoy junto a ti
te cuidaré desde aquí

Sé que la culpa os acosa
y os susurra al oído: “pude hacer más”
no hay nada que reprochar
ya no hay demonios
en el fondo del cristal
y sólo bebo todos los besos
que no te di

Pero mi vida
yo nunca podré olvidarte
y sólo el viento sabe
lo que has sufrido por amarme
hay tantas cosas
que nunca te dije en vida
que eres todo cuanto amo
y ahora que ya no estoy junto a ti
vivo cada vez que habláis de mi
y muero otra vez si lloráis
he aprendido al fin a disfrutar
y soy feliz

No llores cielo
y vuélvete a enamorar
nunca me olvides
me tengo que marchar

Pero mi vida
yo nunca podré olvidarte
y sólo el viento sabe
lo que has sufrido por amarme
hay tantas cosas
que nunca te dije en vida
que eres todo cuanto amo
y ahora que ya no estoy junto a ti
desde mi cielo
os arroparé en la noche
y os acunaré en los sueños
y espantaré todos los miedos,
desde mi cielo
os esperaré escribiendo
no estoy solo pues me cuidan
la libertad y la esperanza
yo nunca os olvidaré
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.negocio;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author pc
 */
public final class CalcularEdad extends Edad {

    private int Anio;
    private int Mes;
    private int Dia;

    public CalcularEdad() {
    }

    public CalcularEdad(Date FechaNace, Date FechaActual) {
        getEdad(FechaNace, FechaActual);
    }

    public void getEdad(Date FechaNace, Date FechaActual) {
        try {
            SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
            if (FechaNace != null) {
                String fechaNace = formato.format(FechaNace);
                String fechaActual = formato.format(FechaActual);
                String[] fActual = fechaActual.split("/");
                String[] fNace = fechaNace.split("/");
                int anio = Integer.parseInt(fActual[0]) - Integer.parseInt(fNace[0]);
                int mes = Integer.parseInt(fActual[1]) - Integer.parseInt(fNace[1]);
                int dia = Integer.parseInt(fActual[2]) - Integer.parseInt(fNace[2]);
                int anioActual = Integer.parseInt(fActual[0]);
                int mesActual = Integer.parseInt(fActual[1]);

                Datos(anio, mes, dia, mesActual, anioActual);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void Datos(int anio, int mes, int dias, int mesActual, int anioActual) {
        if (mes < 0) {
            anio = anio - 1;
            mes = 12 + mes;
            if (dias < 0) {
                mes = mes - 1;
                dias = Dias(dias, mesActual, anioActual);
            }
        } else if (mes == 0) {
            if (dias < 0) {
                mes = 11;
                anio = anio - 1;
                dias = Dias(dias, mesActual, anioActual);
            }
        } else {
            if (dias < 0) {
                mes = mes - 1;
                dias = Dias(dias, mesActual, anioActual);
            }
        }
        this.Anio = anio;
        this.Mes = mes;
        this.Dia = dias;
    }

    public Integer Dias(int dias, int mesActual, int anioActual) {
        int dia = dias;
        if (dia < 0) {
            switch (mesActual) {
                case 2:
                    int año = anioActual;
                    if ((año % 4 == 0) && ((año % 100 != 0) || (año % 400 == 0))) {
                        dia = 29 + dia;
                    } else {
                        dia = 28 + dia;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    dia = 30 + dia;
                    break;
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 10:
                case 12:
                    dia = 31 + dia;
                    break;
            }
        }
        return dia;
    }

    @Override
    public String obtenerAnios() {
        return String.valueOf(getAnio()) + " AÑOS ";
    }

    @Override
    public String obtenerMeses() {
        return String.valueOf(getMes()) + " MESES ";
    }

    @Override
    public String obtenerDias() {
        return String.valueOf(getDia()) + " DIAS ";
    }

    public int getAnio() {
        return Anio;
    }

    public void setAnio(int Anio) {
        this.Anio = Anio;
    }

    public int getDia() {
        return Dia;
    }

    public void setDia(int Dia) {
        this.Dia = Dia;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int Mes) {
        this.Mes = Mes;
    }
}

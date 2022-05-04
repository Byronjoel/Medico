package ec.com.cubosoft.avamed.operaciones;

import java.util.Comparator;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Administrador
 */
public class Comp implements Comparator {

    private boolean _asc;

    public Comp(boolean asc) {
        _asc = asc;
    }

    public int compare(Object o1, Object o2) {
        Row fila = (Row) o1;
        int x;
        int v = 0;
        Row fila1 = (Row) o2;
        Label s1 = (Label) fila.getChildren().get(0);
        int s = Integer.parseInt(s1.getValue());
        Label s2 = (Label) fila1.getChildren().get(0);
        int s3 = Integer.parseInt(s2.getValue());
        if (s > s3) {
            v = 1;
        } else {
            if (s == s3) {
                v = 0;
            } else {
                if (s < s3) {
                    v = -1;
                }
            }
        }
        if (_asc) {
            x = v;
        } else {
            x = -v;
        }
        return x;
    }
}
//_asc ? v : -v;


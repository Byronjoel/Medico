/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.nextla;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DESARROLLO
 */
@Embeddable
public class LispetPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "nro_ord")
    private int nroOrd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "cod_ana")
    private String codAna;

    public LispetPK() {
    }

    public LispetPK(int nroOrd, String codAna) {
        this.nroOrd = nroOrd;
        this.codAna = codAna;
    }

    public int getNroOrd() {
        return nroOrd;
    }

    public void setNroOrd(int nroOrd) {
        this.nroOrd = nroOrd;
    }

    public String getCodAna() {
        return codAna;
    }

    public void setCodAna(String codAna) {
        this.codAna = codAna;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) nroOrd;
        hash += (codAna != null ? codAna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LispetPK)) {
            return false;
        }
        LispetPK other = (LispetPK) object;
        if (this.nroOrd != other.nroOrd) {
            return false;
        }
        if ((this.codAna == null && other.codAna != null) || (this.codAna != null && !this.codAna.equals(other.codAna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.nextla.LispetPK[ nroOrd=" + nroOrd + ", codAna=" + codAna + " ]";
    }
    
}

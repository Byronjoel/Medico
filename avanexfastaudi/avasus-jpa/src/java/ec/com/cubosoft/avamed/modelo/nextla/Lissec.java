/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.nextla;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DESARROLLO
 */
@Entity
@Table(name = "lissec")
@NamedQueries({
    @NamedQuery(name = "Lissec.findAll", query = "SELECT l FROM Lissec l")})
public class Lissec implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "cod_sec")
    private String codSec;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "des_sec")
    private String desSec;

    public Lissec() {
    }

    public Lissec(String codSec) {
        this.codSec = codSec;
    }

    public Lissec(String codSec, String desSec) {
        this.codSec = codSec;
        this.desSec = desSec;
    }

    public String getCodSec() {
        return codSec;
    }

    public void setCodSec(String codSec) {
        this.codSec = codSec;
    }

    public String getDesSec() {
        return desSec;
    }

    public void setDesSec(String desSec) {
        this.desSec = desSec;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codSec != null ? codSec.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lissec)) {
            return false;
        }
        Lissec other = (Lissec) object;
        if ((this.codSec == null && other.codSec != null) || (this.codSec != null && !this.codSec.equals(other.codSec))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.nextla.Lissec[ codSec=" + codSec + " ]";
    }
    
}

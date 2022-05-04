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
@Table(name = "s_transa")
@NamedQueries({
    @NamedQuery(name = "STransa.findAll", query = "SELECT s FROM STransa s")})
public class STransa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "transac")
    private String transac;
    @Column(name = "descrip")
    private String descrip;

    public STransa() {
    }

    public STransa(String transac) {
        this.transac = transac;
    }

    public String getTransac() {
        return transac;
    }

    public void setTransac(String transac) {
        this.transac = transac;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transac != null ? transac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof STransa)) {
            return false;
        }
        STransa other = (STransa) object;
        if ((this.transac == null && other.transac != null) || (this.transac != null && !this.transac.equals(other.transac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.nextla.STransa[ transac=" + transac + " ]";
    }
    
}

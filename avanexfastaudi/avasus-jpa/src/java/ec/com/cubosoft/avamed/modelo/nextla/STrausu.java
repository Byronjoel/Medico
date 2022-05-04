/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.nextla;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author DESARROLLO
 */
@Entity
@Table(name = "s_trausu")
@NamedQueries({
    @NamedQuery(name = "STrausu.findAll", query = "SELECT s FROM STrausu s")})
public class STrausu implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected STrausuPK sTrausuPK;
    @Column(name = "alta")
    private Character alta;
    @Column(name = "baja")
    private Character baja;
    @Column(name = "modif")
    private Character modif;

    public STrausu() {
    }

    public STrausu(STrausuPK sTrausuPK) {
        this.sTrausuPK = sTrausuPK;
    }

    public STrausu(String usuario, String transac) {
        this.sTrausuPK = new STrausuPK(usuario, transac);
    }

    public STrausuPK getSTrausuPK() {
        return sTrausuPK;
    }

    public void setSTrausuPK(STrausuPK sTrausuPK) {
        this.sTrausuPK = sTrausuPK;
    }

    public Character getAlta() {
        return alta;
    }

    public void setAlta(Character alta) {
        this.alta = alta;
    }

    public Character getBaja() {
        return baja;
    }

    public void setBaja(Character baja) {
        this.baja = baja;
    }

    public Character getModif() {
        return modif;
    }

    public void setModif(Character modif) {
        this.modif = modif;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sTrausuPK != null ? sTrausuPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof STrausu)) {
            return false;
        }
        STrausu other = (STrausu) object;
        if ((this.sTrausuPK == null && other.sTrausuPK != null) || (this.sTrausuPK != null && !this.sTrausuPK.equals(other.sTrausuPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.nextla.STrausu[ sTrausuPK=" + sTrausuPK + " ]";
    }
    
}

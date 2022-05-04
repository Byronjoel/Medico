/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.core;

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
@Table(name = "perxuser", catalog = "avasus", schema = "core")
@NamedQueries({
    @NamedQuery(name = "Perxuser.findAll", query = "SELECT p FROM Perxuser p")})
public class Perxuser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "usrnext")
    private String usrnext;
    @Basic(optional = false)
    @NotNull
    @Column(name = "per_cyp")
    private short perCyp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pri_vis")
    private short priVis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "per_abto")
    private short perAbto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "per_upload")
    private short perUpload;
    @Basic(optional = false)
    @NotNull
    @Column(name = "per_delete")
    private short perDelete;

    @Basic(optional = false)
    @NotNull
    @Column(name = "per_anular")
    private short perAnular;
    
     @Basic(optional = false)
    @NotNull
    @Column(name = "per_auditoria")
    private short perAuditoria;

    public Perxuser() {
    }

    public Perxuser(String usrnext) {
        this.usrnext = usrnext;
    }

    public Perxuser(String usrnext, short perCyp, short priVis, short perAbto, short perUpload, short perDelete) {
        this.usrnext = usrnext;
        this.perCyp = perCyp;
        this.priVis = priVis;
        this.perAbto = perAbto;
        this.perUpload = perUpload;
        this.perDelete = perDelete;
    }

    public short getPerAnular() {
        return perAnular;
    }

    public short getPerAuditoria() {
        return perAuditoria;
    }

    public void setPerAuditoria(short perAuditoria) {
        this.perAuditoria = perAuditoria;
    }

    public void setPerAnular(short perAnular) {
        this.perAnular = perAnular;
    }

    public String getUsrnext() {
        return usrnext;
    }

    public void setUsrnext(String usrnext) {
        this.usrnext = usrnext;
    }

    public short getPerCyp() {
        return perCyp;
    }

    public void setPerCyp(short perCyp) {
        this.perCyp = perCyp;
    }

    public short getPriVis() {
        return priVis;
    }

    public void setPriVis(short priVis) {
        this.priVis = priVis;
    }

    public short getPerAbto() {
        return perAbto;
    }

    public void setPerAbto(short perAbto) {
        this.perAbto = perAbto;
    }

    public short getPerUpload() {
        return perUpload;
    }

    public void setPerUpload(short perUpload) {
        this.perUpload = perUpload;
    }

    public short getPerDelete() {
        return perDelete;
    }

    public void setPerDelete(short perDelete) {
        this.perDelete = perDelete;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usrnext != null ? usrnext.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Perxuser)) {
            return false;
        }
        Perxuser other = (Perxuser) object;
        if ((this.usrnext == null && other.usrnext != null) || (this.usrnext != null && !this.usrnext.equals(other.usrnext))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.core.Perxuser[ usrnext=" + usrnext + " ]";
    }

}

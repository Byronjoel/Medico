/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.publico;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "iso3166_r2",catalog = "avasus", schema = "public")

public class Iso3166R2 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
     @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
     @Column(name = "region2")
    private String region2;
      @Column(name = "codigo")
    private String codigo;
    @Column(name = "fec_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Size(max = 18)
    @Column(name = "first_user")
    private String firstUser;
    @Size(max = 18)
    @Column(name = "last_user")
    private String lastUser;
    @Column(name = "lock_reg")
    private Short lockReg;
    @JoinColumn(name = "id_iso2", referencedColumnName = "iso2")
    @ManyToOne(optional = false)
    private Iso31662 idIso2;

    public Iso3166R2() {
    }

    public Iso3166R2(Integer id) {
        this.id = id;
    }

    public Iso3166R2(Integer id, String region2) {
        this.id = id;
        this.region2 = region2;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegion2() {
        return region2;
    }

    public void setRegion2(String region2) {
        this.region2 = region2;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecUpd() {
        return fecUpd;
    }

    public void setFecUpd(Date fecUpd) {
        this.fecUpd = fecUpd;
    }

    public String getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(String firstUser) {
        this.firstUser = firstUser;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser;
    }

    public Short getLockReg() {
        return lockReg;
    }

    public void setLockReg(Short lockReg) {
        this.lockReg = lockReg;
    }

    public Iso31662 getIdIso2() {
        return idIso2;
    }

    public void setIdIso2(Iso31662 idIso2) {
        this.idIso2 = idIso2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iso3166R2)) {
            return false;
        }
        Iso3166R2 other = (Iso3166R2) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.publico.Iso3166R2[ id=" + id + " ]";
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.avamed.modelo.core;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "cs_grupos", catalog = "avasus", schema = "core")

@NamedQueries({
    @NamedQuery(name = "CsGrupos.findAll", query = "SELECT c FROM CsGrupos c")})
public class CsGrupos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_gru")
    private String codGru;
    @Column(name = "des_gru")
    private String desGru;
    @Basic(optional = false)
    @Column(name = "fec_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Basic(optional = false)
    @Column(name = "lock_reg")
    private short lockReg;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Column(name = "first_user")
    private String firstUser;
    @Column(name = "last_user")
    private String lastUser;
//    @Column(name = "first_oid")
//    private BigInteger firstOid;
//    @Column(name = "last_oid")
//    private BigInteger lastOid;
    @Basic(optional = false)
    @Column(name = "lock_gru")
    private short lockGru;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "csGrupos")
    private List<CsUsuarios> csUsuariosList;

    public CsGrupos() {
    }

    public CsGrupos(String codGru) {
        this.codGru = codGru;
    }

    public CsGrupos(String codGru, Date fecIni, short lockReg, short lockGru) {
        this.codGru = codGru;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
        this.lockGru = lockGru;
    }

    public String getCodGru() {
        return codGru;
    }

    public void setCodGru(String codGru) {
        this.codGru = codGru;
    }

    public String getDesGru() {
        return desGru;
    }

    public void setDesGru(String desGru) {
        this.desGru = desGru;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public short getLockReg() {
        return lockReg;
    }

    public void setLockReg(short lockReg) {
        this.lockReg = lockReg;
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

    

    public short getLockGru() {
        return lockGru;
    }

    public void setLockGru(short lockGru) {
        this.lockGru = lockGru;
    }

    public List<CsUsuarios> getCsUsuariosList() {
        return csUsuariosList;
    }

    public void setCsUsuariosList(List<CsUsuarios> csUsuariosList) {
        this.csUsuariosList = csUsuariosList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codGru != null ? codGru.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CsGrupos)) {
            return false;
        }
        CsGrupos other = (CsGrupos) object;
        if ((this.codGru == null && other.codGru != null) || (this.codGru != null && !this.codGru.equals(other.codGru))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.core.CsGrupos[codGru=" + codGru + "]";
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.avamed.modelo.core;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "cs_perxgru", catalog = "avasus", schema = "core")
@NamedQueries({
    @NamedQuery(name = "CsPerxgru.findAll", query = "SELECT c FROM CsPerxgru c")})
public class CsPerxgru implements Serializable {
    private static final long serialVersionUID = 1L;

    //@Entity(name="")
    @EmbeddedId
    protected CsPerxgruPK csPerxgruPK;
    @Basic(optional = false)
    @Column(name = "lock_dgru")

    private short lockDgru;
    @Basic(optional = false)
    @Column(name = "alta")
    private short alta;
    @Basic(optional = false)
    @Column(name = "baja")
    private short baja;
    @Basic(optional = false)
    @Column(name = "modif")
    private short modif;
    @Basic(optional = false)
    @Column(name = "imprime")
    private short imprime;
    @Basic(optional = false)
    @Column(name = "sync")
    private short sync;
    @Basic(optional = false)
    @Column(name = "tipo_per")
    private String tipoPer;
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
  

    public CsPerxgru() {
    }

    public CsPerxgru(CsPerxgruPK csPerxgruPK) {
        this.csPerxgruPK = csPerxgruPK;
    }

    public CsPerxgru(CsPerxgruPK csPerxgruPK, short lockDgru, short alta, short baja, short modif, short imprime, short sync, String tipoPer, Date fecIni, short lockReg) {
        this.csPerxgruPK = csPerxgruPK;
        this.lockDgru = lockDgru;
        this.alta = alta;
        this.baja = baja;
        this.modif = modif;
        this.imprime = imprime;
        this.sync = sync;
        this.tipoPer = tipoPer;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
    }

    public CsPerxgru(String codGru, String codPer, String idApp) {
        this.csPerxgruPK = new CsPerxgruPK(codGru, codPer, idApp);
    }

    public CsPerxgruPK getCsPerxgruPK() {
        return csPerxgruPK;
    }

    public void setCsPerxgruPK(CsPerxgruPK csPerxgruPK) {
        this.csPerxgruPK = csPerxgruPK;
    }

    public short getLockDgru() {
        return lockDgru;
    }

    public void setLockDgru(short lockDgru) {
        this.lockDgru = lockDgru;
    }

    public short getAlta() {
        return alta;
    }

    public void setAlta(short alta) {
        this.alta = alta;
    }

    public short getBaja() {
        return baja;
    }

    public void setBaja(short baja) {
        this.baja = baja;
    }

    public short getModif() {
        return modif;
    }

    public void setModif(short modif) {
        this.modif = modif;
    }

    public short getImprime() {
        return imprime;
    }

    public void setImprime(short imprime) {
        this.imprime = imprime;
    }

    public short getSync() {
        return sync;
    }

    public void setSync(short sync) {
        this.sync = sync;
    }

    public String getTipoPer() {
        return tipoPer;
    }

    public void setTipoPer(String tipoPer) {
        this.tipoPer = tipoPer;
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

   
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (csPerxgruPK != null ? csPerxgruPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CsPerxgru)) {
            return false;
        }
        CsPerxgru other = (CsPerxgru) object;
        if ((this.csPerxgruPK == null && other.csPerxgruPK != null) || (this.csPerxgruPK != null && !this.csPerxgruPK.equals(other.csPerxgruPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.core.CsPerxgru[csPerxgruPK=" + csPerxgruPK + "]";
    }

}

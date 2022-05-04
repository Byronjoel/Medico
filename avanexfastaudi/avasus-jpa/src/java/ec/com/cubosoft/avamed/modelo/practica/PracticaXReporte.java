/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.avamed.modelo.practica;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "practica_x_reporte", catalog = "avasus", schema = "practica")
@NamedQueries({
    @NamedQuery(name = "PracticaXReporte.findAll", query = "SELECT p FROM PracticaXReporte p")})
public class PracticaXReporte implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="PRACTICA_X_REPORTE_ID_GENERATOR" ,sequenceName="PRACTICA.PRACTICA_X_REPORTE_ID_SEQ",allocationSize=1 )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PRACTICA_X_REPORTE_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fec_ini", insertable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Basic(optional = false)
    @Column(name = "lock_reg")
    private short lockReg;
    @Column(name = "first_user")
    private String firstUser;
    @Column(name = "last_user")
    private String lastUser;
    @Column(name = "first_oid")
    private BigInteger firstOid;
    @Column(name = "last_oid")
    private BigInteger lastOid;
    @Basic(optional = false)
    @Column(name = "orden_practica")
    private short ordenPractica;
    @JoinColumn(name = "id_reporte", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Reporte reporte;
    @JoinColumn(name = "id_nombre", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private NombreP nombreP;

    public PracticaXReporte() {
    }

    public PracticaXReporte(Integer id) {
        this.id = id;
    }

    public PracticaXReporte(Integer id, short lockReg, short ordenPractica) {
        this.id = id;
        this.lockReg = lockReg;
        this.ordenPractica = ordenPractica;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public short getLockReg() {
        return lockReg;
    }

    public void setLockReg(short lockReg) {
        this.lockReg = lockReg;
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

    public BigInteger getFirstOid() {
        return firstOid;
    }

    public void setFirstOid(BigInteger firstOid) {
        this.firstOid = firstOid;
    }

    public BigInteger getLastOid() {
        return lastOid;
    }

    public void setLastOid(BigInteger lastOid) {
        this.lastOid = lastOid;
    }

    public short getOrdenPractica() {
        return ordenPractica;
    }

    public void setOrdenPractica(short ordenPractica) {
        this.ordenPractica = ordenPractica;
    }

    public Reporte getReporte() {
        return reporte;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }

    public NombreP getNombreP() {
        return nombreP;
    }

    public void setNombreP(NombreP nombreP) {
        this.nombreP = nombreP;
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
        if (!(object instanceof PracticaXReporte)) {
            return false;
        }
        PracticaXReporte other = (PracticaXReporte) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.practica.PracticaXReporte[id=" + id + "]";
    }

}

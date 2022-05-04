/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.medico;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "detalle_especialidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DetalleEspecialidad.findAll", query = "SELECT d FROM DetalleEspecialidad d"),
    @NamedQuery(name = "DetalleEspecialidad.findById", query = "SELECT d FROM DetalleEspecialidad d WHERE d.id = :id"),
    @NamedQuery(name = "DetalleEspecialidad.findByFecIni", query = "SELECT d FROM DetalleEspecialidad d WHERE d.fecIni = :fecIni"),
    @NamedQuery(name = "DetalleEspecialidad.findByFecUpd", query = "SELECT d FROM DetalleEspecialidad d WHERE d.fecUpd = :fecUpd"),
    @NamedQuery(name = "DetalleEspecialidad.findByFirstUser", query = "SELECT d FROM DetalleEspecialidad d WHERE d.firstUser = :firstUser"),
    @NamedQuery(name = "DetalleEspecialidad.findByLastUser", query = "SELECT d FROM DetalleEspecialidad d WHERE d.lastUser = :lastUser"),
    @NamedQuery(name = "DetalleEspecialidad.findByLockReg", query = "SELECT d FROM DetalleEspecialidad d WHERE d.lockReg = :lockReg")})
public class DetalleEspecialidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fec_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "first_user")
    private String firstUser;
    @Size(max = 15)
    @Column(name = "last_user")
    private String lastUser;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lock_reg")
    private short lockReg;
    @Column(name = "first_oid")
    private BigInteger firstOid;
    @Column(name = "last_oid")
    private BigInteger lastOid;
    @JoinColumn(name = "id_especialidad", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Especialidad idEspecialidad;

    public DetalleEspecialidad() {
    }

    public DetalleEspecialidad(Integer id) {
        this.id = id;
    }

    public DetalleEspecialidad(Integer id, Date fecIni, String firstUser, short lockReg) {
        this.id = id;
        this.fecIni = fecIni;
        this.firstUser = firstUser;
        this.lockReg = lockReg;
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

    public short getLockReg() {
        return lockReg;
    }

    public void setLockReg(short lockReg) {
        this.lockReg = lockReg;
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

    public Especialidad getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(Especialidad idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
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
        if (!(object instanceof DetalleEspecialidad)) {
            return false;
        }
        DetalleEspecialidad other = (DetalleEspecialidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.medico.DetalleEspecialidad[ id=" + id + " ]";
    }
    
}

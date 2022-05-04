/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.medico;

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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "especialidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Especialidad.findAll", query = "SELECT e FROM Especialidad e"),
    @NamedQuery(name = "Especialidad.findById", query = "SELECT e FROM Especialidad e WHERE e.id = :id"),
    @NamedQuery(name = "Especialidad.findByDescripcion", query = "SELECT e FROM Especialidad e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "Especialidad.findByFecIni", query = "SELECT e FROM Especialidad e WHERE e.fecIni = :fecIni"),
    @NamedQuery(name = "Especialidad.findByLockReg", query = "SELECT e FROM Especialidad e WHERE e.lockReg = :lockReg"),
    @NamedQuery(name = "Especialidad.findByFecUpd", query = "SELECT e FROM Especialidad e WHERE e.fecUpd = :fecUpd"),
    @NamedQuery(name = "Especialidad.findByFirstUser", query = "SELECT e FROM Especialidad e WHERE e.firstUser = :firstUser"),
    @NamedQuery(name = "Especialidad.findByLastUser", query = "SELECT e FROM Especialidad e WHERE e.lastUser = :lastUser"),
    @NamedQuery(name = "Especialidad.findByFirstOid", query = "SELECT e FROM Especialidad e WHERE e.firstOid = :firstOid"),
    @NamedQuery(name = "Especialidad.findByLastOid", query = "SELECT e FROM Especialidad e WHERE e.lastOid = :lastOid")})
public class Especialidad implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fec_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lock_reg")
    private short lockReg;
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
    @Column(name = "first_oid")
    private BigInteger firstOid;
    @Column(name = "last_oid")
    private BigInteger lastOid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEspecialidad")
    private List<DetalleEspecialidad> detalleEspecialidadList;

    public Especialidad() {
    }

    public Especialidad(Integer id) {
        this.id = id;
    }

    public Especialidad(Integer id, String descripcion, Date fecIni, short lockReg, String firstUser) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
        this.firstUser = firstUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    @XmlTransient
    public List<DetalleEspecialidad> getDetalleEspecialidadList() {
        return detalleEspecialidadList;
    }

    public void setDetalleEspecialidadList(List<DetalleEspecialidad> detalleEspecialidadList) {
        this.detalleEspecialidadList = detalleEspecialidadList;
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
        if (!(object instanceof Especialidad)) {
            return false;
        }
        Especialidad other = (Especialidad) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.medico.Especialidad[ id=" + id + " ]";
    }
    
}

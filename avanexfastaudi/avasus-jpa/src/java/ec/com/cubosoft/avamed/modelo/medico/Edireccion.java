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
@Table(name = "edireccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Edireccion.findAll", query = "SELECT e FROM Edireccion e"),
    @NamedQuery(name = "Edireccion.findById", query = "SELECT e FROM Edireccion e WHERE e.id = :id"),
    @NamedQuery(name = "Edireccion.findByTipo", query = "SELECT e FROM Edireccion e WHERE e.tipo = :tipo"),
    @NamedQuery(name = "Edireccion.findByUso", query = "SELECT e FROM Edireccion e WHERE e.uso = :uso"),
    @NamedQuery(name = "Edireccion.findByPreferencia", query = "SELECT e FROM Edireccion e WHERE e.preferencia = :preferencia"),
    @NamedQuery(name = "Edireccion.findByDetalle", query = "SELECT e FROM Edireccion e WHERE e.detalle = :detalle"),
    @NamedQuery(name = "Edireccion.findByFecIni", query = "SELECT e FROM Edireccion e WHERE e.fecIni = :fecIni"),
    @NamedQuery(name = "Edireccion.findByLockReg", query = "SELECT e FROM Edireccion e WHERE e.lockReg = :lockReg"),
    @NamedQuery(name = "Edireccion.findByFecUpd", query = "SELECT e FROM Edireccion e WHERE e.fecUpd = :fecUpd"),
    @NamedQuery(name = "Edireccion.findByFirstUser", query = "SELECT e FROM Edireccion e WHERE e.firstUser = :firstUser"),
    @NamedQuery(name = "Edireccion.findByLastUser", query = "SELECT e FROM Edireccion e WHERE e.lastUser = :lastUser")})
public class Edireccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "tipo")
    private String tipo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "uso")
    private String uso;
    @Size(max = 3)
    @Column(name = "preferencia")
    private String preferencia;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "detalle")
    private String detalle;
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
    @JoinColumn(name = "id_nombre", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Nombre idNombre;

    public Edireccion() {
    }

    public Edireccion(Integer id) {
        this.id = id;
    }

    public Edireccion(Integer id, String tipo, String uso, String detalle, Date fecIni, short lockReg, String firstUser) {
        this.id = id;
        this.tipo = tipo;
        this.uso = uso;
        this.detalle = detalle;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getPreferencia() {
        return preferencia;
    }

    public void setPreferencia(String preferencia) {
        this.preferencia = preferencia;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
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

    public Nombre getIdNombre() {
        return idNombre;
    }

    public void setIdNombre(Nombre idNombre) {
        this.idNombre = idNombre;
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
        if (!(object instanceof Edireccion)) {
            return false;
        }
        Edireccion other = (Edireccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.medico.Edireccion[ id=" + id + " ]";
    }
    
}

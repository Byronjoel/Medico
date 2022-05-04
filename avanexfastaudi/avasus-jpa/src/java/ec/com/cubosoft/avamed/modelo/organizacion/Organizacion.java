/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.organizacion;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "nombre", catalog = "avasus", schema = "organizacion")
@NamedQueries({
    @NamedQuery(name = "Organizacion.findAll", query = "SELECT o FROM Organizacion o")})
public class Organizacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "ORGANIZACION_ID_GENERATOR", sequenceName = "ORGANIZACION.NOMBRE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORGANIZACION_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "abreviatura")
    private String abreviatura;
    @Basic(optional = false)
    @Column(name = "razon_social")
    private String razonSocial;
    @Column(name = "ruc")
    private String ruc;
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
    @Column(name = "first_oid")
    private BigInteger firstOid;
    @Column(name = "last_oid")
    private BigInteger lastOid;
    @Column(name = "contacto")
    private String contacto;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL)
    private List<Orden> orden;
    
        @Column(name = "cod_ref")
    private Integer codRef;
    public Organizacion() {
    }
    public Organizacion(Long id) {
        this.id = id;
    }

    public Organizacion(Long id, String abreviatura, String razonSocial, Date fecIni, short lockReg) {
        this.id = id;
        this.abreviatura = abreviatura;
        this.razonSocial = razonSocial;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
    }

    public Integer getCodRef() {
        return codRef;
    }

    public void setCodRef(Integer codRef) {
        this.codRef = codRef;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
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

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Orden> getOrden() {
        return orden;
    }

    public void setOrden(List<Orden> orden) {
        this.orden = orden;
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
        if (!(object instanceof Organizacion)) {
            return false;
        }
        Organizacion other = (Organizacion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.organizacion.Organizacion[id=" + id + "]";
    }
}

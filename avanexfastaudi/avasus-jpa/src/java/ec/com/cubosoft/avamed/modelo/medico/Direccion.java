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
@Table(name = "direccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Direccion.findAll", query = "SELECT d FROM Direccion d"),
    @NamedQuery(name = "Direccion.findById", query = "SELECT d FROM Direccion d WHERE d.id = :id"),
    @NamedQuery(name = "Direccion.findByCallePrincipal", query = "SELECT d FROM Direccion d WHERE d.callePrincipal = :callePrincipal"),
    @NamedQuery(name = "Direccion.findByCalleSecundaria", query = "SELECT d FROM Direccion d WHERE d.calleSecundaria = :calleSecundaria"),
    @NamedQuery(name = "Direccion.findByTipoVivienda", query = "SELECT d FROM Direccion d WHERE d.tipoVivienda = :tipoVivienda"),
    @NamedQuery(name = "Direccion.findByNumeroVivienda", query = "SELECT d FROM Direccion d WHERE d.numeroVivienda = :numeroVivienda"),
    @NamedQuery(name = "Direccion.findByPisoVivienda", query = "SELECT d FROM Direccion d WHERE d.pisoVivienda = :pisoVivienda"),
    @NamedQuery(name = "Direccion.findByReferencias", query = "SELECT d FROM Direccion d WHERE d.referencias = :referencias"),
    @NamedQuery(name = "Direccion.findByColorVivienda", query = "SELECT d FROM Direccion d WHERE d.colorVivienda = :colorVivienda"),
    @NamedQuery(name = "Direccion.findByCiudad", query = "SELECT d FROM Direccion d WHERE d.ciudad = :ciudad"),
    @NamedQuery(name = "Direccion.findByPais", query = "SELECT d FROM Direccion d WHERE d.pais = :pais"),
    @NamedQuery(name = "Direccion.findByCodigoPostal", query = "SELECT d FROM Direccion d WHERE d.codigoPostal = :codigoPostal"),
    @NamedQuery(name = "Direccion.findByFecIni", query = "SELECT d FROM Direccion d WHERE d.fecIni = :fecIni"),
    @NamedQuery(name = "Direccion.findByLockReg", query = "SELECT d FROM Direccion d WHERE d.lockReg = :lockReg"),
    @NamedQuery(name = "Direccion.findByFecUpd", query = "SELECT d FROM Direccion d WHERE d.fecUpd = :fecUpd"),
    @NamedQuery(name = "Direccion.findByFirstUser", query = "SELECT d FROM Direccion d WHERE d.firstUser = :firstUser"),
    @NamedQuery(name = "Direccion.findByLastUser", query = "SELECT d FROM Direccion d WHERE d.lastUser = :lastUser")})
public class Direccion implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Size(max = 128)
    @Column(name = "calle_principal")
    private String callePrincipal;
    @Size(max = 128)
    @Column(name = "calle_secundaria")
    private String calleSecundaria;
    @Size(max = 3)
    @Column(name = "tipo_vivienda")
    private String tipoVivienda;
    @Size(max = 15)
    @Column(name = "numero_vivienda")
    private String numeroVivienda;
    @Size(max = 15)
    @Column(name = "piso_vivienda")
    private String pisoVivienda;
    @Size(max = 256)
    @Column(name = "referencias")
    private String referencias;
    @Size(max = 25)
    @Column(name = "color_vivienda")
    private String colorVivienda;
    @Size(max = 25)
    @Column(name = "ciudad")
    private String ciudad;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "pais")
    private String pais;
    @Size(max = 15)
    @Column(name = "codigo_postal")
    private String codigoPostal;
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
    @Size(max = 15)
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

    public Direccion() {
    }

    public Direccion(Integer id) {
        this.id = id;
    }

    public Direccion(Integer id, String pais, Date fecIni, short lockReg) {
        this.id = id;
        this.pais = pais;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCallePrincipal() {
        return callePrincipal;
    }

    public void setCallePrincipal(String callePrincipal) {
        this.callePrincipal = callePrincipal;
    }

    public String getCalleSecundaria() {
        return calleSecundaria;
    }

    public void setCalleSecundaria(String calleSecundaria) {
        this.calleSecundaria = calleSecundaria;
    }

    public String getTipoVivienda() {
        return tipoVivienda;
    }

    public void setTipoVivienda(String tipoVivienda) {
        this.tipoVivienda = tipoVivienda;
    }

    public String getNumeroVivienda() {
        return numeroVivienda;
    }

    public void setNumeroVivienda(String numeroVivienda) {
        this.numeroVivienda = numeroVivienda;
    }

    public String getPisoVivienda() {
        return pisoVivienda;
    }

    public void setPisoVivienda(String pisoVivienda) {
        this.pisoVivienda = pisoVivienda;
    }

    public String getReferencias() {
        return referencias;
    }

    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    public String getColorVivienda() {
        return colorVivienda;
    }

    public void setColorVivienda(String colorVivienda) {
        this.colorVivienda = colorVivienda;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
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
        if (!(object instanceof Direccion)) {
            return false;
        }
        Direccion other = (Direccion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.medico.Direccion[ id=" + id + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.organizacion;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DESARROLLADOR
 */
@Entity
@Table(name = "avas_digital", catalog = "avasus", schema = "persona")
@NamedQueries({
    @NamedQuery(name = "AvasDigital.findAll", query = "SELECT a FROM AvasDigital a")})
public class AvasDigital implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="AVAS_DIGITAL_ID_GENERATOR",sequenceName="persona.avas_digital_id_seq",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AVAS_DIGITAL_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "digital")
    private String digital;
    @Size(max = 3)
    @Column(name = "estado")
    private String estado;
    @Size(max = 64)
    @Column(name = "descripcion")
    private String descripcion;
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
    @Column(name = "lock_reg")
    private short lockReg;
    @Size(max = 30)
    @Column(name = "first_user")
    private String firstUser;
    @Size(max = 30)
    @Column(name = "last_user")
    private String lastUser;
    @Size(max = 15)
    @Column(name = "id_usuario")
    private String idUsuario;
    @OneToMany(mappedBy = "idAvasDigital")
    private Collection<ResultadoDigital> resultadoDigitalCollection;

    public AvasDigital() {
    }

    public AvasDigital(Long id) {
        this.id = id;
    }

    public AvasDigital(Long id, String digital, Date fecIni, short lockReg) {
        this.id = id;
        this.digital = digital;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDigital() {
        return digital;
    }

    public void setDigital(String digital) {
        this.digital = digital;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Collection<ResultadoDigital> getResultadoDigitalCollection() {
        return resultadoDigitalCollection;
    }

    public void setResultadoDigitalCollection(Collection<ResultadoDigital> resultadoDigitalCollection) {
        this.resultadoDigitalCollection = resultadoDigitalCollection;
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
        if (!(object instanceof AvasDigital)) {
            return false;
        }
        AvasDigital other = (AvasDigital) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.persona.AvasDigital[ id=" + id + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.organizacion;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.Size;

/**
 *
 * @author DESARROLLADOR
 */
@Entity
@Table(name = "img_logo", catalog = "avasus", schema = "organizacion")

public class ImgLogo implements Serializable {
    @Lob
    @Column(name = "logo")
    private byte[] logo;
    @Column(name = "per_logo")
    private Short perLogo;
    
    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "empresa")
    private BigInteger idEmpresa;
     @Id
    @SequenceGenerator(name = "IMGLOGO_ID_GENERATOR", sequenceName = "organizacion.img_logo_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "IMGLOGO_ID_GENERATOR")
    @Basic(optional = false)
       @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "fec_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Column(name = "lock_reg")
    private Short lockReg;

    public ImgLogo() {
    }

    public ImgLogo(Integer id) {
        this.id = id;
    }

    public ImgLogo(Integer id, Date fecIni) {
        this.id = id;
        this.fecIni = fecIni;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public Short getPerLogo() {
        return perLogo;
    }

    public void setPerLogo(Short perLogo) {
        this.perLogo = perLogo;
    }

    public BigInteger getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(BigInteger idEmpresa) {
        this.idEmpresa = idEmpresa;
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

    public Short getLockReg() {
        return lockReg;
    }

    public void setLockReg(Short lockReg) {
        this.lockReg = lockReg;
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
        if (!(object instanceof ImgLogo)) {
            return false;
        }
        ImgLogo other = (ImgLogo) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.practica.ImgDigital[ id=" + id + " ]";
    }

   
    
}

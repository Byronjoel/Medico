/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.publico;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
 * @author JP3
 */
@Entity
@Table(name = "aud_imagen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AudImagen.findAll", query = "SELECT a FROM AudImagen a")
    , @NamedQuery(name = "AudImagen.findById", query = "SELECT a FROM AudImagen a WHERE a.id = :id")
    , @NamedQuery(name = "AudImagen.findByValidado", query = "SELECT a FROM AudImagen a WHERE a.validado = :validado")
    , @NamedQuery(name = "AudImagen.findByOrden", query = "SELECT a FROM AudImagen a WHERE a.orden = :orden")
    , @NamedQuery(name = "AudImagen.findByPractica", query = "SELECT a FROM AudImagen a WHERE a.practica = :practica")
    , @NamedQuery(name = "AudImagen.findByFecIni", query = "SELECT a FROM AudImagen a WHERE a.fecIni = :fecIni")
    , @NamedQuery(name = "AudImagen.findByFecUpd", query = "SELECT a FROM AudImagen a WHERE a.fecUpd = :fecUpd")
    , @NamedQuery(name = "AudImagen.findByArea", query = "SELECT a FROM AudImagen a WHERE a.area = :area")
    , @NamedQuery(name = "AudImagen.findByEstadoU", query = "SELECT a FROM AudImagen a WHERE a.estadoU = :estadoU")
    , @NamedQuery(name = "AudImagen.findByEstadoD", query = "SELECT a FROM AudImagen a WHERE a.estadoD = :estadoD")
    , @NamedQuery(name = "AudImagen.findByFirstUser", query = "SELECT a FROM AudImagen a WHERE a.firstUser = :firstUser")
    , @NamedQuery(name = "AudImagen.findByLastUser", query = "SELECT a FROM AudImagen a WHERE a.lastUser = :lastUser")
    , @NamedQuery(name = "AudImagen.findByLkImg", query = "SELECT a FROM AudImagen a WHERE a.lkImg = :lkImg")
    , @NamedQuery(name = "AudImagen.findByLkInf", query = "SELECT a FROM AudImagen a WHERE a.lkInf = :lkInf")
    , @NamedQuery(name = "AudImagen.findByLockReg", query = "SELECT a FROM AudImagen a WHERE a.lockReg = :lockReg")
    , @NamedQuery(name = "AudImagen.findByCodigo", query = "SELECT a FROM AudImagen a WHERE a.codigo = :codigo")
    , @NamedQuery(name = "AudImagen.findByHistoria", query = "SELECT a FROM AudImagen a WHERE a.historia = :historia")
    , @NamedQuery(name = "AudImagen.findByEstadoinformes", query = "SELECT a FROM AudImagen a WHERE a.estadoinformes = :estadoinformes")})
public class AudImagen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "validado")
    private short validado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "orden")
    private long orden;
    @Basic(optional = false)
    @NotNull
    @Column(name = "practica")
    private long practica;
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
    @Column(name = "area")
    private long area;
    @Column(name = "estado_u")
    private Integer estadoU;
    @Column(name = "estado_d")
    private Integer estadoD;
    @Size(max = 30)
    @Column(name = "first_user")
    private String firstUser;
    @Size(max = 30)
    @Column(name = "last_user")
    private String lastUser;
    @Size(max = 256)
    @Column(name = "lk_img")
    private String lkImg;
    @Size(max = 256)
    @Column(name = "lk_inf")
    private String lkInf;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lock_reg")
    private short lockReg;
    @Column(name = "codigo")
    private BigInteger codigo;
    @Column(name = "historia")
    private BigInteger historia;
    @Column(name = "estadoinformes")
    private Integer estadoinformes;

    public AudImagen() {
    }

    public AudImagen(Long id) {
        this.id = id;
    }

    public AudImagen(Long id, short validado, long orden, long practica, Date fecIni, long area, short lockReg) {
        this.id = id;
        this.validado = validado;
        this.orden = orden;
        this.practica = practica;
        this.fecIni = fecIni;
        this.area = area;
        this.lockReg = lockReg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public short getValidado() {
        return validado;
    }

    public void setValidado(short validado) {
        this.validado = validado;
    }

    public long getOrden() {
        return orden;
    }

    public void setOrden(long orden) {
        this.orden = orden;
    }

    public long getPractica() {
        return practica;
    }

    public void setPractica(long practica) {
        this.practica = practica;
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

    public long getArea() {
        return area;
    }

    public void setArea(long area) {
        this.area = area;
    }

    public Integer getEstadoU() {
        return estadoU;
    }

    public void setEstadoU(Integer estadoU) {
        this.estadoU = estadoU;
    }

    public Integer getEstadoD() {
        return estadoD;
    }

    public void setEstadoD(Integer estadoD) {
        this.estadoD = estadoD;
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

    public String getLkImg() {
        return lkImg;
    }

    public void setLkImg(String lkImg) {
        this.lkImg = lkImg;
    }

    public String getLkInf() {
        return lkInf;
    }

    public void setLkInf(String lkInf) {
        this.lkInf = lkInf;
    }

    public short getLockReg() {
        return lockReg;
    }

    public void setLockReg(short lockReg) {
        this.lockReg = lockReg;
    }

    public BigInteger getCodigo() {
        return codigo;
    }

    public void setCodigo(BigInteger codigo) {
        this.codigo = codigo;
    }

    public BigInteger getHistoria() {
        return historia;
    }

    public void setHistoria(BigInteger historia) {
        this.historia = historia;
    }

    public Integer getEstadoinformes() {
        return estadoinformes;
    }

    public void setEstadoinformes(Integer estadoinformes) {
        this.estadoinformes = estadoinformes;
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
        if (!(object instanceof AudImagen)) {
            return false;
        }
        AudImagen other = (AudImagen) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.publico.AudImagen[ id=" + id + " ]";
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.vistas;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author DESARROLLADOR
 */
@Entity
@Table(name = "pedidos", catalog = "avasus", schema = "ingreso")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pedidos.findAll", query = "SELECT p FROM Pedidos p")
    ,
    @NamedQuery(name = "Pedidos.findByFecAtencion", query = "SELECT p FROM Pedidos p WHERE p.fecAtencion = :fecAtencion")
    ,
    @NamedQuery(name = "Pedidos.findByStsAdmin", query = "SELECT p FROM Pedidos p WHERE p.stsAdmin = :stsAdmin")
    ,
    @NamedQuery(name = "Pedidos.findByStsTecnico", query = "SELECT p FROM Pedidos p WHERE p.stsTecnico = :stsTecnico")
    ,
    @NamedQuery(name = "Pedidos.findByIdOrden", query = "SELECT p FROM Pedidos p WHERE p.idOrden = :idOrden")
    ,
    @NamedQuery(name = "Pedidos.findByIdPractica", query = "SELECT p FROM Pedidos p WHERE p.idPractica = :idPractica")
    ,
    @NamedQuery(name = "Pedidos.findByIdHistoria", query = "SELECT p FROM Pedidos p WHERE p.idHistoria = :idHistoria")
    ,
    @NamedQuery(name = "Pedidos.findByIdEmpresa", query = "SELECT p FROM Pedidos p WHERE p.idEmpresa = :idEmpresa")
    ,
    @NamedQuery(name = "Pedidos.findByMSolicitante", query = "SELECT p FROM Pedidos p WHERE p.mSolicitante = :mSolicitante")
    ,
    @NamedQuery(name = "Pedidos.findByAbreviatura", query = "SELECT p FROM Pedidos p WHERE p.abreviatura = :abreviatura")
    ,
    @NamedQuery(name = "Pedidos.findByCodRef", query = "SELECT p FROM Pedidos p WHERE p.codRef = :codRef")
    ,
    @NamedQuery(name = "Pedidos.findByIdArea", query = "SELECT p FROM Pedidos p WHERE p.idArea = :idArea")
    ,
    @NamedQuery(name = "Pedidos.findByDescripcion", query = "SELECT p FROM Pedidos p WHERE p.descripcion = :descripcion")})
public class Pedidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private BigInteger id;
    @Column(name = "lock_reg")
    private Short lockReg;
    @Column(name = "per_impa")
    private Short perImpa;
    @Column(name = "cod_ord")
    private Long codOrd;
    @Column(name = "cod_ori")
    private String codOri;
    @Column(name = "id_nombrep")
    private BigInteger idNombrep;

    @Column(name = "fec_atencion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecAtencion;

    @Column(name = "sts_admin")
    private String stsAdmin;
    @Column(name = "sts_tecnico")
    private String stsTecnico;
    @Column(name = "id_orden")
    private BigInteger idOrden;
    @Column(name = "id_practica")
    private Integer idPractica;
    @Column(name = "id_historia")
    private BigInteger idHistoria;
    @Column(name = "id_empresa")
    private BigInteger idEmpresa;
    @Column(name = "m_solicitante")
    private String mSolicitante;
    @Column(name = "abreviatura")
    private String abreviatura;
    @Column(name = "cod_ref")
    private Integer codRef;
    @Column(name = "id_area")
    private Integer idArea;
    @Column(name = "descripcion")
    private String descripcion;

    public Pedidos() {
    }

    public Date getFecAtencion() {
        return fecAtencion;
    }

    public void setFecAtencion(Date fecAtencion) {
        this.fecAtencion = fecAtencion;
    }

    public String getStsAdmin() {
        return stsAdmin;
    }

    public void setStsAdmin(String stsAdmin) {
        this.stsAdmin = stsAdmin;
    }

    public String getmSolicitante() {
        return mSolicitante;
    }

    public void setmSolicitante(String mSolicitante) {
        this.mSolicitante = mSolicitante;
    }

    public short getLockReg() {
        return lockReg;
    }

    public void setLockReg(short lockReg) {
        this.lockReg = lockReg;
    }

    public String getStsTecnico() {
        return stsTecnico;
    }

    public short getPerImpa() {
        return perImpa;
    }

    public void setPerImpa(short perImpa) {
        this.perImpa = perImpa;
    }

    public void setStsTecnico(String stsTecnico) {
        this.stsTecnico = stsTecnico;
    }

    public BigInteger getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(BigInteger idOrden) {
        this.idOrden = idOrden;
    }

    public Integer getIdPractica() {
        return idPractica;
    }

    public void setIdPractica(Integer idPractica) {
        this.idPractica = idPractica;
    }

    public BigInteger getIdHistoria() {
        return idHistoria;
    }

    public void setIdHistoria(BigInteger idHistoria) {
        this.idHistoria = idHistoria;
    }

    public BigInteger getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(BigInteger idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getMSolicitante() {
        return mSolicitante;
    }

    public void setMSolicitante(String mSolicitante) {
        this.mSolicitante = mSolicitante;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Integer getCodRef() {
        return codRef;
    }

    public void setCodRef(Integer codRef) {
        this.codRef = codRef;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public void setLockReg(Short lockReg) {
        this.lockReg = lockReg;
    }

    public void setPerImpa(Short perImpa) {
        this.perImpa = perImpa;
    }

    public Long getCodOrd() {
        return codOrd;
    }

    public void setCodOrd(Long codOrd) {
        this.codOrd = codOrd;
    }

    public String getCodOri() {
        return codOri;
    }

    public void setCodOri(String codOri) {
        this.codOri = codOri;
    }

    public BigInteger getIdNombrep() {
        return idNombrep;
    }

    public void setIdNombrep(BigInteger idNombrep) {
        this.idNombrep = idNombrep;
    }

}

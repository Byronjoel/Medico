/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.persona;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DESARROLLO
 */
@Entity
@Table(name = "xml_antecedentes", catalog = "avasus", schema = "persona")
@NamedQueries({
    @NamedQuery(name = "XmlAntecedentes.findAll", query = "SELECT x FROM XmlAntecedentes x")})
public class XmlAntecedentes implements Serializable {

            @Column(name = "cod_ref")
    private Integer codRef;
    
    @Column(name = "nro_ord")
    private Long nroOrd;
    @Column(name = "cod_ori")
    private String codOri;
    @Column(name = "cod_pac")
    private Long codPac;
    @Column(name = "cod_ana")
    private Long codAna;
    @Column(name = "id_empresa")
    private Integer idEmpresa;
    @JoinColumn(name = "id_historia", referencedColumnName = "id")
    @ManyToOne
    private Historia idHistoria;

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "xml_antecedentes_id_seq", sequenceName = "persona.xml_antecedentes_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "xml_antecedentes_id_seq")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "antecedentes")
    private String antecedentes;
    @Column(name = "estado")
    private String estado;
    @Column(name = "medico")
    private String medico;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    
     @ManyToOne
    @JoinColumn(name="id_orden", referencedColumnName="id")
    @OrderBy("id ASC")
    private Orden orden;
    
//    @Column(name = "id_orden")
//    private BigInteger idOrden;
    @Column(name = "id_medico")
    private Integer idMedico;
    @Basic(optional = false)
    @Column(name = "fec_ini", insertable = false, updatable = false)
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
    @Column(name = "empresa")
    private String empresa;
       @Column(name = "id_practica")
    private Integer idPractica;

    public XmlAntecedentes() {
    }

    public XmlAntecedentes(Long id) {
        this.id = id;
    }

    public Integer getIdPractica() {
        return idPractica;
    }

    public Integer getCodRef() {
        return codRef;
    }

    public void setCodRef(Integer codRef) {
        this.codRef = codRef;
    }

    public void setIdPractica(Integer idPractica) {
        this.idPractica = idPractica;
    }

    public Long getCodAna() {
        return codAna;
    }

    public void setCodAna(Long codAna) {
        this.codAna = codAna;
    }

    public Long getNroOrd() {
        return nroOrd;
    }

    public void setNroOrd(Long nroOrd) {
        this.nroOrd = nroOrd;
    }

    public String getCodOri() {
        return codOri;
    }

    public void setCodOri(String codOri) {
        this.codOri = codOri;
    }

    public Long getCodPac() {
        return codPac;
    }

    public void setCodPac(Long codPac) {
        this.codPac = codPac;
    }

    public XmlAntecedentes(Long id, String antecedentes, Date fecIni, short lockReg) {
        this.id = id;
        this.antecedentes = antecedentes;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAntecedentes() {
        return antecedentes;
    }

    public void setAntecedentes(String antecedentes) {
        this.antecedentes = antecedentes;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Orden getOrden() {
        return orden;
    }

//    public BigInteger getIdHistoria() {
//        return idHistoria;
//    }
//
//    public void setIdHistoria(BigInteger idHistoria) {
//        this.idHistoria = idHistoria;
//    }
//    public BigInteger getIdOrden() {
//        return idOrden;
//    }
//
//    public void setIdOrden(BigInteger idOrden) {
//        this.idOrden = idOrden;
//    }
    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
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
        if (!(object instanceof XmlAntecedentes)) {
            return false;
        }
        XmlAntecedentes other = (XmlAntecedentes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.practica.XmlAntecedentes[ id=" + id + " ]";
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public Historia getIdHistoria() {
        return idHistoria;
    }

    public void setIdHistoria(Historia idHistoria) {
        this.idHistoria = idHistoria;
    }

}

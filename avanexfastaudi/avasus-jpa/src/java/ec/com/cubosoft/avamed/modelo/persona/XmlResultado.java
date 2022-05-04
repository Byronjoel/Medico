/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.avamed.modelo.persona;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import ec.com.cubosoft.avamed.modelo.practica.NombreP;
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
import javax.validation.constraints.Size;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "xml_resultado", catalog = "avasus", schema = "persona")
@NamedQueries({@NamedQuery(name = "XmlResultado.findAll", query = "SELECT x FROM XmlResultado x")})
public class XmlResultado implements Serializable {
//    @Column(name = "id_medico")
//    private Integer idMedico;
//    @Column(name = "id_orden_nextlab")
//    private Long idOrdenNextlab;
//    @Size(max = 30)
    
    @Column(name = "id_orden_nextlab")
    private Long idOrdenNextlab;

    //
    @Column(name = "cod_ana")
    private String codAna;

    @Column(name = "nro_ord")
    private Long nroOrd;
    @Column(name = "des_ori")
    private String desOri;
    @Column(name = "cod_ori")
    private String codOri;
//    @Column(name = "id_medico")
//    private Integer idMedico;
    @Column(name = "linck")
    private String linck;
    @Column(name = "lk_img")
    private String lkImg;
    @Column(name = "cod_pac")
    private Long codPac;
    
    
     @Column(name = "arch_user")
    private String archUser;
    @Column(name = "fec_arch")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecArch;
    @Column(name = "audit_user")
    private String auditUser;
    @Column(name = "fecha_audit")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAudit;
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="XML_RESULTADO_ID_GENERATOR",sequenceName="PERSONA.XML_RESULTADO_ID_SEQ",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="XML_RESULTADO_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "resultado")
    private String resultado;
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
    @Column(name = "id_historia", updatable=false,insertable=false)
    private BigInteger idHistoria;
    @Column(name = "id_practica", updatable=false,insertable=false)
    private Integer idPractica;
    @Column(name = "id_orden", updatable=false,insertable=false)
    private BigInteger idOrden;
//    @Column(name = "id_medico")
//    private Integer idMedico;
    @Basic(optional = false)
    @Column(name = "fec_ini", insertable=false, updatable=false)
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
    @Column(name = "dx")
    private String dx;
    @Column(name = "empresa")
    private String empresa;

    @ManyToOne
    @JoinColumn(name="id_practica", referencedColumnName="id")
    @OrderBy("descripcion ASC")
    private NombreP practica;

    @ManyToOne
    @JoinColumn(name="id_orden", referencedColumnName="id")
    @OrderBy("id ASC")
    private Orden orden;

    @ManyToOne
    @JoinColumn(name="id_historia", referencedColumnName="id")
    @OrderBy("id ASC")
    private Historia historia;

    @ManyToOne
    @JoinColumn(name="id_medico", referencedColumnName="id")
    @OrderBy("apellido ASC")
    private Nombre medicos;

    public XmlResultado() {
    }

    public XmlResultado(Long id) {
        this.id = id;
    }

    public XmlResultado(Long id, Date fecIni, short lockReg) {
        this.id = id;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getArchUser() {
        return archUser;
    }

    public void setArchUser(String archUser) {
        this.archUser = archUser;
    }

    public Date getFecArch() {
        return fecArch;
    }

    public void setFecArch(Date fecArch) {
        this.fecArch = fecArch;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
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

    public Long getIdOrdenNextlab() {
        return idOrdenNextlab;
    }

    public void setIdOrdenNextlab(Long idOrdenNextlab) {
        this.idOrdenNextlab = idOrdenNextlab;
    }

    public String getCodAna() {
        return codAna;
    }

    public void setCodAna(String codAna) {
        this.codAna = codAna;
    }

    public Long getNroOrd() {
        return nroOrd;
    }

    public void setNroOrd(Long nroOrd) {
        this.nroOrd = nroOrd;
    }

    public String getDesOri() {
        return desOri;
    }

    public void setDesOri(String desOri) {
        this.desOri = desOri;
    }

    public String getCodOri() {
        return codOri;
    }

    public void setCodOri(String codOri) {
        this.codOri = codOri;
    }

    public String getLinck() {
        return linck;
    }

    public void setLinck(String linck) {
        this.linck = linck;
    }

    public String getLkImg() {
        return lkImg;
    }

    public void setLkImg(String lkImg) {
        this.lkImg = lkImg;
    }

    public Long getCodPac() {
        return codPac;
    }

    public void setCodPac(Long codPac) {
        this.codPac = codPac;
    }

//    public BigInteger getIdHistoria() {
//        return idHistoria;
//    }
//
//    public void setIdHistoria(BigInteger idHistoria) {
//        this.idHistoria = idHistoria;
//    }
//
//    public Integer getIdPractica() {
//        return idPractica;
//    }
//
//    public void setIdPractica(Integer idPractica) {
//        this.idPractica = idPractica;
//    }
//
//    public BigInteger getIdOrden() {
//        return idOrden;
//    }
//
//    public void setIdOrden(BigInteger idOrden) {
//        this.idOrden = idOrden;
//    }
//
//    public Integer getIdMedico() {
//        return idMedico;
//    }
//
//    public void setIdMedico(Integer idMedico) {
//        this.idMedico = idMedico;
//    }

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

    public String getDx() {
        return dx;
    }

    public void setDx(String dx) {
        this.dx = dx;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public Historia getHistoria() {
        return historia;
    }

    public void setHistoria(Historia historia) {
        this.historia = historia;
    }

    public Nombre getMedicos() {
        return medicos;
    }

    public void setMedicos(Nombre medicos) {
        this.medicos = medicos;
    }

    public Orden getOrden() {
        return orden;
    }

    public void setOrden(Orden orden) {
        this.orden = orden;
    }

    public NombreP getPractica() {
        return practica;
    }

    public void setPractica(NombreP practica) {
        this.practica = practica;
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
        if (!(object instanceof XmlResultado)) {
            return false;
        }
        XmlResultado other = (XmlResultado) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
     public BigInteger getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(BigInteger idOrden) {
        this.idOrden = idOrden;
    }

    public BigInteger getIdHistoria() {
        return idHistoria;
    }

    public void setIdHistoria(BigInteger idHistoria) {
        this.idHistoria = idHistoria;
    }

    public Integer getIdPractica() {
        return idPractica;
    }

    public void setIdPractica(Integer idPractica) {
        this.idPractica = idPractica;
    }
    
    
    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.persona.XmlResultado[id=" + id + "]";
    }

   

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
    }

    public Date getFechaAudit() {
        return fechaAudit;
    }

    public void setFechaAudit(Date fechaAudit) {
        this.fechaAudit = fechaAudit;
    }

}


/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.ingreso;

import ec.com.cubosoft.avamed.modelo.medico.Area;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import ec.com.cubosoft.avamed.modelo.practica.NombreP;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

/**
 *
 * @author pc
 */
@Entity
@Table(name = "practica_x_orden", catalog = "avasus", schema = "ingreso")
@NamedQueries({
    @NamedQuery(name = "PracticaXOrden.findAll", query = "SELECT p FROM PracticaXOrden p")})
public class PracticaXOrden implements Serializable {

//    @Column(name = "first_oid")
//    private BigInteger firstOid;
//    @Column(name = "last_oid")
//    private BigInteger lastOid;
//    @Basic(optional = false)
//    @Column(name = "id_orden")
//    private long idOrden;
//    @Basic(optional = false)
//
//    @Column(name = "id_practica")
//    private int idPractica;
    @Column(name = "id_area")
    private Integer idArea;

    @Column(name = "audit_user")
    private String auditUser;
    @Column(name = "fecha_audit")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaAudit;
    @Column(name = "arch_user")
    private String archUser;
    @Column(name = "fec_arch")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecArch;
    
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "PRACTICA_ORDEN_ID_GENERATOR", sequenceName = "INGRESO.PRACTICA_ORDEN_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRACTICA_ORDEN_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "valor_paciente")
    private BigDecimal valorPaciente;
    @Column(name = "valor_seguro")
    private BigDecimal valorSeguro;
    @Column(name = "fec_atencion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecAtencion;
    @Basic(optional = false)
    @Column(name = "id_plan")
    private long idPlan;
    @Column(name = "sts_admin")
    private String stsAdmin;
    @Column(name = "id_nombrep")
    private Long idNombrep;
    @Basic(optional = false)
    @Column(name = "sts_tecnico")
    private String stsTecnico;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Basic(optional = false)
    @Column(name = "fec_ini", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Basic(optional = false)
    @Column(name = "first_user")
    private String firstUser;
    @Column(name = "last_user")
    private String lastUser;

    @Basic(optional = false)
    @Column(name = "cantidad")
    private short cantidad;
    @Basic(optional = false)
    @Column(name = "lock_reg")
    private short lockReg;
//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "id_area")
//    private Area area;
     @ManyToOne
    @JoinColumn(name = "id_orden",referencedColumnName = "id")
    private Orden orden;
    
    
     
    
    @ManyToOne
    @JoinColumn(name = "id_practica",referencedColumnName = "id")
    private NombreP practica;

    public PracticaXOrden() {
    }

    public PracticaXOrden(Long id) {
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

    public PracticaXOrden(Long id, long idPlan, String stsTecnico, Date fecIni, String firstUser, short cantidad, short lockReg) {
        this.id = id;
        this.idPlan = idPlan;
        this.stsTecnico = stsTecnico;
        this.fecIni = fecIni;
        this.firstUser = firstUser;
//        this.idOrden = idOrden;
        this.cantidad = cantidad;
        this.lockReg = lockReg;
//        this.idPractica = idPractica;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorPaciente() {
        return valorPaciente;
    }

    public void setValorPaciente(BigDecimal valorPaciente) {
        this.valorPaciente = valorPaciente;
    }

    public BigDecimal getValorSeguro() {
        return valorSeguro;
    }

    public void setValorSeguro(BigDecimal valorSeguro) {
        this.valorSeguro = valorSeguro;
    }

    public Date getFecAtencion() {
        return fecAtencion;
    }

    public void setFecAtencion(Date fecAtencion) {
        this.fecAtencion = fecAtencion;
    }

    public long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(long idPlan) {
        this.idPlan = idPlan;
    }

    public String getStsAdmin() {
        return stsAdmin;
    }

    public void setStsAdmin(String stsAdmin) {
        this.stsAdmin = stsAdmin;
    }

    public Long getIdNombrep() {
        return idNombrep;
    }

    public void setIdNombrep(Long idNombrep) {
        this.idNombrep = idNombrep;
    }

    public String getStsTecnico() {
        return stsTecnico;
    }

    public void setStsTecnico(String stsTecnico) {
        this.stsTecnico = stsTecnico;
    }

    public Date getFecUpd() {
        return fecUpd;
    }

    public void setFecUpd(Date fecUpd) {
        this.fecUpd = fecUpd;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
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


//  
//    public Area getArea() {
//        return area;
//    }
//
//    public void setArea(Area area) {
//        this.area = area;
//    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }
    public short getCantidad() {
        return cantidad;
    }

    public void setCantidad(short cantidad) {
        this.cantidad = cantidad;
    }

    public short getLockReg() {
        return lockReg;
    }

    public void setLockReg(short lockReg) {
        this.lockReg = lockReg;
    }
//
//    public int getIdPractica() {
//        return idPractica;
//    }
//
//    public void setIdPractica(int idPractica) {
//        this.idPractica = idPractica;
//    }

//    public Area getArea() {
//        return area;
//    }
//
//    public void setArea(Area idArea) {
//        this.area = idArea;
//    }
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
        if (!(object instanceof PracticaXOrden)) {
            return false;
        }
        PracticaXOrden other = (PracticaXOrden) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.ingreso.PracticaXOrden[id=" + id + "]";
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

//    public BigInteger getFirstOid() {
//        return firstOid;
//    }
//
//    public void setFirstOid(BigInteger firstOid) {
//        this.firstOid = firstOid;
//    }

//    public BigInteger getLastOid() {
//        return lastOid;
//    }
//
//    public void setLastOid(BigInteger lastOid) {
//        this.lastOid = lastOid;
//    }
//
//    public long getIdOrden() {
//        return idOrden;
//    }

//    public void setIdOrden(long idOrden) {
//        this.idOrden = idOrden;
//    }
//
//    public int getIdPractica() {
//        return idPractica;
//    }
//
//    public void setIdPractica(int idPractica) {
//        this.idPractica = idPractica;
//    }

//    public Integer getIdArea() {
//        return idArea;
//    }
//
//    public void setIdArea(Integer idArea) {
//        this.idArea = idArea;
//    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.ingreso;

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
@Table(name = "pedido_web", catalog = "avasus", schema = "ingreso")
@NamedQueries({
    @NamedQuery(name = "PedidoWeb.findAll", query = "SELECT p FROM PedidoWeb p")})
public class PedidoWeb implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "PEDIDOWEB_ID_GENERATOR", sequenceName = "ingreso.pedido_web_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PEDIDOWEB_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_empresa")
    private BigInteger idEmpresa;
    @Basic(optional = false)

    @Column(name = "id_plan")
    private long idPlan;

    @Column(name = "sts_admin")
    private String stsAdmin;

    @Column(name = "sts_tecnico")
    private String stsTecnico;

    @Column(name = "observaciones")
    private String observaciones;
    @Basic(optional = false)

    @Column(name = "fec_envio")
    @Temporal(TemporalType.DATE)
    private Date fecEnvio;
    @Column(name = "fec_pedido")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecPedido;
    @Basic(optional = false)
    @Column(name = "fec_ini", insertable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Basic(optional = false)
    @Column(name = "lock_reg", insertable=false)
    private short lockReg;
    @Basic(optional = false)
    @Column(name = "first_user")
    private String firstUser;
    @Basic(optional = false)

    @Column(name = "user_dwn")
    private String userDwn;

    @Column(name = "last_user")
    private String lastUser;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "num_id")
    private String numId;

    @Column(name = "m_solicitante")
    private String mSolicitante;
    @Column(name = "pedido")
    private String pedido;

    public PedidoWeb() {
    }

    public PedidoWeb(Long id) {
        this.id = id;
    }

    public PedidoWeb(Long id, long idPlan, Date fecEnvio, Date fecIni, short lockReg, String firstUser, String userDwn, String nombres, String apellidos, String pedido) {
        this.id = id;
        this.idPlan = idPlan;
        this.fecEnvio = fecEnvio;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
        this.firstUser = firstUser;
        this.userDwn = userDwn;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(BigInteger idEmpresa) {
        this.idEmpresa = idEmpresa;
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

    public String getStsTecnico() {
        return stsTecnico;
    }

    public void setStsTecnico(String stsTecnico) {
        this.stsTecnico = stsTecnico;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Date getFecEnvio() {
        return fecEnvio;
    }

    public void setFecEnvio(Date fecEnvio) {
        this.fecEnvio = fecEnvio;
    }

    public Date getFecPedido() {
        return fecPedido;
    }

    public void setFecPedido(Date fecPedido) {
        this.fecPedido = fecPedido;
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

    public String getUserDwn() {
        return userDwn;
    }

    public void setUserDwn(String userDwn) {
        this.userDwn = userDwn;
    }

  

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNumId() {
        return numId;
    }

    public void setNumId(String numId) {
        this.numId = numId;
    }

    public String getMSolicitante() {
        return mSolicitante;
    }

    public void setMSolicitante(String mSolicitante) {
        this.mSolicitante = mSolicitante;
    }

    public String getPedido() {
        return pedido;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
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
        if (!(object instanceof PedidoWeb)) {
            return false;
        }
        PedidoWeb other = (PedidoWeb) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.ingreso.PedidoWeb[ id=" + id + " ]";
    }

}

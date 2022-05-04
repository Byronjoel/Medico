package ec.com.cubosoft.avamed.modelo.ingreso;

import ec.com.cubosoft.avamed.modelo.empresa.Origen;
import ec.com.cubosoft.avamed.modelo.organizacion.Organizacion;
import ec.com.cubosoft.avamed.modelo.persona.Episodio;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import ec.com.cubosoft.avamed.modelo.persona.XmlAntecedentes;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
 * @author pc
 */
@Entity
@Table(name = "orden", catalog = "avasus", schema = "ingreso")
@NamedQueries({
    @NamedQuery(name = "Orden.findAll", query = "SELECT o FROM Orden o ORDER by o.id")})
public class Orden implements Serializable {

    @Column(name = "cod_ord")
    private Long codOrd;
    @Column(name = "des_ori")
    private String desOri;
    @Column(name = "cod_ori")
    private String codOri;
    @Column(name = "first_oid")
    private BigInteger firstOid;
    @Column(name = "last_oid")
    private BigInteger lastOid;

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "ORDEN_ID_GENERATOR", sequenceName = "INGRESO.ORDEN_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDEN_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "m_solicitante")
    private String mSolicitante;
//    @Column(name = "id_empresa")
//    private BigInteger idEmpresa;

    @Basic(optional = false)
    @Column(name = "id_plan")
    private long idPlan;

    @Basic(optional = false)
    @Column(name = "id_origen")
    private int idOrigen;

    @Column(name = "id_referencia")
    private Integer idReferencia;

    @Column(name = "nro_externo")
    private String nroExterno;

    @Column(name = "sts_admin")
    private String stsAdmin;

    @Column(name = "sts_tecnico")
    private String stsTecnico;

    @Column(name = "observaciones")
    private String observaciones;

    @Basic(optional = false)
    @Column(name = "fec_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fecIngreso;

    @Column(name = "fec_entrega")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecEntrega;

    @Basic(optional = false)
    @Column(name = "fec_ini", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;

    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;

    @Basic(optional = false)
    @Column(name = "lock_reg")
    private short lockReg;

    @Basic(optional = false)
    @Column(name = "first_user")
    private String firstUser;

    @Column(name = "last_user")
    private String lastUser;

    @ManyToOne
    @JoinColumn(name = "id_historia", nullable = false)
    private Historia historia;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    private Organizacion organizacion;

    @ManyToOne
    @JoinColumn(name = "id_origen", nullable = false, insertable = false, updatable = false)
    private Origen origen;

    @OneToMany(mappedBy = "ordens", cascade = CascadeType.ALL)
    private List<Episodio> episodios;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<PracticaXOrden> practicaXorden;
    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<XmlResultado> xmlResultados;

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL)
    private List<XmlAntecedentes> xmlAntecedentes;

    public Orden() {
    }

    public Orden(Long id) {
        this.id = id;
    }

    public List<XmlResultado> getXmlResultados() {
        return xmlResultados;
    }

    public void setXmlResultados(List<XmlResultado> xmlResultados) {
        this.xmlResultados = xmlResultados;
    }

    public List<XmlAntecedentes> getXmlAntecedentes() {
        return xmlAntecedentes;
    }

    public void setXmlAntecedentes(List<XmlAntecedentes> xmlAntecedentes) {
        this.xmlAntecedentes = xmlAntecedentes;
    }

    public Orden(Long id, long idPlan, int idOrigen, Date fecIngreso, Date fecIni, short lockReg, String firstUser) {
        this.id = id;
        this.idPlan = idPlan;
        this.idOrigen = idOrigen;
        this.fecIngreso = fecIngreso;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
        this.firstUser = firstUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public BigInteger getIdEmpresa() {
//        return idEmpresa;
//    }
//
//    public void setIdEmpresa(BigInteger idEmpresa) {
//        this.idEmpresa = idEmpresa;
//    }
    public long getIdPlan() {
        return idPlan;
    }

    public void setIdPlan(long idPlan) {
        this.idPlan = idPlan;
    }

    public int getIdOrigen() {
        return idOrigen;
    }

    public String getmSolicitante() {
        return mSolicitante;
    }

    public void setmSolicitante(String mSolicitante) {
        this.mSolicitante = mSolicitante;
    }

    public void setIdOrigen(int idOrigen) {
        this.idOrigen = idOrigen;
    }

    public Integer getIdReferencia() {
        return idReferencia;
    }

    public void setIdReferencia(Integer idReferencia) {
        this.idReferencia = idReferencia;
    }

    public String getNroExterno() {
        return nroExterno;
    }

    public void setNroExterno(String nroExterno) {
        this.nroExterno = nroExterno;
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

    public Date getFecIngreso() {
        return fecIngreso;
    }

    public void setFecIngreso(Date fecIngreso) {
        this.fecIngreso = fecIngreso;
    }

    public Date getFecEntrega() {
        return fecEntrega;
    }

    public void setFecEntrega(Date fecEntrega) {
        this.fecEntrega = fecEntrega;
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

    public Historia getHistoria() {
        return historia;
    }

    public void setHistoria(Historia historia) {
        this.historia = historia;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        this.episodios = episodios;
    }

    public Organizacion getOrganizacion() {
        return organizacion;
    }

    public void setOrganizacion(Organizacion organizacion) {
        this.organizacion = organizacion;
    }

    public List<PracticaXOrden> getPracticaXorden() {
        return practicaXorden;
    }

    public void setPracticaXorden(List<PracticaXOrden> practicaXorden) {
        this.practicaXorden = practicaXorden;
    }
//
//    public List<XmlResultado> getXmlResultados() {
//        return xmlResultados;
//    }
//
//    public void setXmlResultados(List<XmlResultado> xmlResultados) {
//        this.xmlResultados = xmlResultados;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orden)) {
            return false;
        }
        Orden other = (Orden) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    public void setOrigen(Origen origen) {
        this.origen = origen;
    }

    public Origen getOrigen() {
        return origen;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.ingreso.Orden[id=" + id + "]";
    }

//    public long getIdHistoria() {
//        return idHistoria;
//    }
//
//    public void setIdHistoria(long idHistoria) {
//        this.idHistoria = idHistoria;
//    }
//
//    public Integer getIdEmpresa() {
//        return idEmpresa;
//    }
//
//    public void setIdEmpresa(Integer idEmpresa) {
//        this.idEmpresa = idEmpresa;
//    }
//    public String getOrdestado() {
//        return ordestado;
//    }
//
//    public void setOrdestado(String ordestado) {
//        this.ordestado = ordestado;
//    }
//    public Long getNroOrd() {
//        return nroOrd;
//    }
//
//    public void setNroOrd(Long nroOrd) {
//        this.nroOrd = nroOrd;
//    }
    public String getCodOri() {
        return codOri;
    }

    public void setCodOri(String codOri) {
        this.codOri = codOri;
    }

//    public long getIdHistoria() {
//        return idHistoria;
//    }
//
//    public void setIdHistoria(long idHistoria) {
//        this.idHistoria = idHistoria;
//    }
//
//    public BigInteger getIdEmpresa() {
//        return idEmpresa;
//    }
//
//    public void setIdEmpresa(BigInteger idEmpresa) {
//        this.idEmpresa = idEmpresa;
//    }
//    public long getIdHistoria() {
//        return idHistoria;
//    }
//
//    public void setIdHistoria(long idHistoria) {
//        this.idHistoria = idHistoria;
//    }
//
//    public BigInteger getIdEmpresa() {
//        return idEmpresa;
//    }
//
//    public void setIdEmpresa(BigInteger idEmpresa) {
//        this.idEmpresa = idEmpresa;
//    }
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

    public Long getCodOrd() {
        return codOrd;
    }

    public void setCodOrd(Long codOrd) {
        this.codOrd = codOrd;
    }

    public String getDesOri() {
        return desOri;
    }

    public void setDesOri(String desOri) {
        this.desOri = desOri;
    }

}

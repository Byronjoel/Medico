/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.practica;

import ec.com.cubosoft.avamed.modelo.ingreso.DxOrden;
import ec.com.cubosoft.avamed.modelo.ingreso.PracticaXOrden;
import ec.com.cubosoft.avamed.modelo.medico.Area;
import ec.com.cubosoft.avamed.modelo.persona.XmlResultado;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "nombre", catalog = "avasus", schema = "practica")
@NamedQueries({
    @NamedQuery(name = "NombreP.findAll", query = "SELECT n FROM NombreP n")})
public class NombreP implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "NOMBREP_ID_GENERATOR", sequenceName = "PRACTICA.NOMBRE_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NOMBREP_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "abreviatura")
    private String abreviatura;
    @Column(name = "orden_imp")
    private Integer ordenImp;
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
    @Column(name = "first_oid")
    private BigInteger firstOid;
    @Column(name = "last_oid")
    private BigInteger lastOid;
    @Column(name = "inf_ad1")
    private String infAd1;
    @Column(name = "inf_ad2")
    private String infAd2;
    @Column(name = "inf_ad3")
    private String infAd3;

    @Column(name = "srv_stnd")
    private short srvStnd;
    @Column(name = "cod_ref")
    private Integer codRef;

    @Basic(optional = false)
    @Column(name = "per_impa")
    private short perImpa;

    @Basic(optional = false)
    @Column(name = "per_add")
    private short perAdd;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nombreP")
    private List<Termino> terminoList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "practica")
    private List<XmlResultado> xmlResultados;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nombreP")
    private List<PracticaXReporte> practicaXReporteList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "practica")
    private List<PracticaXOrden> practicaXorden;

    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_area", nullable = false)
    private Area area;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "practica")
//    private List<DxOrden> dxorden;
//    @OneToMany(cascade = CascadeType.ALL,mappedBy = "nombreP")
//    private List<PracticaAsociada> practicaAsociadaList;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nombreP")
//    private List<Sinonimo> sinonimoList;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "nombreP")
//    private List<Codificacion> codificacionList;
    public NombreP() {
    }

    public NombreP(Integer id) {
        this.id = id;
    }

    public short getPerAdd() {
        return perAdd;
    }

    public void setPerAdd(short perAdd) {
        this.perAdd = perAdd;
    }

    public NombreP(Integer id, String descripcion, String abreviatura, Date fecIni, short lockReg, String firstUser) {
        this.id = id;
        this.descripcion = descripcion;
        this.abreviatura = abreviatura;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
        this.firstUser = firstUser;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(String abreviatura) {
        this.abreviatura = abreviatura;
    }

    public Integer getOrdenImp() {
        return ordenImp;
    }

    public void setOrdenImp(Integer ordenImp) {
        this.ordenImp = ordenImp;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public short getSrvStnd() {
        return srvStnd;
    }

    public void setSrvStnd(short srvStnd) {
        this.srvStnd = srvStnd;
    }

    public Integer getCodRef() {
        return codRef;
    }

    public short getPerImpa() {
        return perImpa;
    }

    public void setPerImpa(short perImpa) {
        this.perImpa = perImpa;
    }

    public void setCodRef(Integer codRef) {
        this.codRef = codRef;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInfAd1() {
        return infAd1;
    }

    public void setInfAd1(String infAd1) {
        this.infAd1 = infAd1;
    }

    public String getInfAd2() {
        return infAd2;
    }

    public void setInfAd2(String infAd2) {
        this.infAd2 = infAd2;
    }

    public String getInfAd3() {
        return infAd3;
    }

    public void setInfAd3(String infAd3) {
        this.infAd3 = infAd3;
    }
//
//    public Integer getIdArea() {
//        return idArea;
//    }
//
//    public void setIdArea(Integer idArea) {
//        this.idArea = idArea;
//    }

    public List<Termino> getTerminoList() {
        return terminoList;
    }

    public void setTerminoList(List<Termino> terminoList) {
        this.terminoList = terminoList;
    }

    public List<XmlResultado> getXmlResultados() {
        return xmlResultados;
    }

    public void setXmlResultados(List<XmlResultado> xmlResultados) {
        this.xmlResultados = xmlResultados;
    }

    public List<PracticaXReporte> getPracticaXReporteList() {
        return practicaXReporteList;
    }

    public void setPracticaXReporteList(List<PracticaXReporte> practicaXReporteList) {
        this.practicaXReporteList = practicaXReporteList;
    }

//    public List<Codificacion> getCodificacionList() {
//        return codificacionList;
//    }
//
//    public void setCodificacionList(List<Codificacion> codificacionList) {
//        this.codificacionList = codificacionList;
//    }
//
//    public List<PracticaAsociada> getPracticaAsociadaList() {
//        return practicaAsociadaList;
//    }
//
//    public void setPracticaAsociadaList(List<PracticaAsociada> practicaAsociadaList) {
//        this.practicaAsociadaList = practicaAsociadaList;
//    }
//
//    public List<Sinonimo> getSinonimoList() {
//        return sinonimoList;
//    }
//
//    public void setSinonimoList(List<Sinonimo> sinonimoList) {
//        this.sinonimoList = sinonimoList;
//    }
    public List<PracticaXOrden> getPracticaXorden() {
        return practicaXorden;
    }

    public void setPracticaXorden(List<PracticaXOrden> practicaXorden) {
        this.practicaXorden = practicaXorden;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
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
        if (!(object instanceof NombreP)) {
            return false;
        }
        NombreP other = (NombreP) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

//    public void setIdArea(Integer idArea) {
//        this.idArea = idArea;
//    }
//
//    public Integer getIdArea() {
//        return idArea;
//    }
    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.practica.NombreP[id=" + id + "]";
    }
}

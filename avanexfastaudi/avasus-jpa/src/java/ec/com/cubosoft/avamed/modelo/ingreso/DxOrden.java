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

/**
 *
 * @author DESARROLLADOR
 */
@Entity
@Table(name = "dx_orden",  catalog ="avasus",schema = "ingreso")
@NamedQueries({
    @NamedQuery(name = "DxOrden.findAll", query = "SELECT d FROM DxOrden d")})
public class DxOrden implements Serializable {

    @Column(name = "first_oid")
    private BigInteger firstOid;
    @Column(name = "last_oid")
    private BigInteger lastOid;

    @Column(name = "nro_ord")
    private Long nroOrd;
    @Column(name = "cod_pac")
    private Long codPac;
  
   
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DX_ORDEN_ID_GENERATOR", sequenceName = "ingreso.dx_orden_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DX_ORDEN_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "cod_cie")
    private String codCie;
    
      @Column(name = "id_xml")
    private BigInteger idXml;
  
    @Column(name = "cod_ref")
    private String codRef;

    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)

    @Column(name = "id_historia")
    private long idHistoria;
    @Basic(optional = false)

    @Column(name = "id_orden")
    private int idOrden;
    @Basic(optional = false)
  
    @Column(name = "id_practica")
    private int idPractica;
    
    @Column(name = "nombre")
    private String nombre;
  
    @Column(name = "edad")
    private String edad;
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

     @Column(name = "grupo_riesgos")
    private String grupoRiesgos;

    @Column(name = "first_user")
    private String firstUser;
  
 
    @Column(name = "last_user")
    private String lastUser;
   
    @Column(name = "dx_medico")
    private String dxMedico;

    @Column(name = "origen")
    private String origen;

    @Column(name = "departamento")
    private String departamento;

    @Column(name = "ocupacion")
    private String ocupacion;
    
    public DxOrden() {
    }

    public DxOrden(Long id) {
        this.id = id;
    }

    public DxOrden(Long id, long idHistoria, int idOrden, int idPractica, Date fecIni, short lockReg, String firstUser) {
        this.id = id;
        this.idHistoria = idHistoria;
        this.idOrden = idOrden;
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

    public String getCodCie() {
        return codCie;
    }

    public void setCodCie(String codCie) {
        this.codCie = codCie;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getIdHistoria() {
        return idHistoria;
    }

    public void setIdHistoria(long idHistoria) {
        this.idHistoria = idHistoria;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdPractica() {
        return idPractica;
    }

    public void setIdPractica(int idPractica) {
        this.idPractica = idPractica;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
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

    public String getDxMedico() {
        return dxMedico;
    }

    public void setDxMedico(String dxMedico) {
        this.dxMedico = dxMedico;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
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
        if (!(object instanceof DxOrden)) {
            return false;
        }
        DxOrden other = (DxOrden) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.ingreso.DxOrden[ id=" + id + " ]";
    }

    public String getGrupoRiesgos() {
        return grupoRiesgos;
    }

    public void setGrupoRiesgos(String grupoRiesgos) {
        this.grupoRiesgos = grupoRiesgos;
    }

    public String getCodRef() {
        return codRef;
    }

    public void setCodRef(String codRef) {
        this.codRef = codRef;
    }

    public BigInteger getIdXml() {
        return idXml;
    }

    public void setIdXml(BigInteger idXml) {
        this.idXml = idXml;
    }

    public Long getNroOrd() {
        return nroOrd;
    }

    public void setNroOrd(Long nroOrd) {
        this.nroOrd = nroOrd;
    }

    public Long getCodPac() {
        return codPac;
    }

    public void setCodPac(Long codPac) {
        this.codPac = codPac;
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

    
}

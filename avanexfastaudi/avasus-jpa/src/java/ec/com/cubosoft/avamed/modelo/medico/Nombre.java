/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.avamed.modelo.medico;

import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "nombre", catalog = "avasus", schema = "medico")

public class Nombre implements Serializable {
    private static final long serialVersionUID = 1L;
    
   @Id
    @SequenceGenerator(name="NOMBRE_ID_GENERATOR",sequenceName="MEDICO.NOMBRE_ID_SEQ",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NOMBRE_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "fecha_nace")
    @Temporal(TemporalType.DATE)
    private Date fechaNace;

    @Basic(optional = false)
    @Column(name = "sexo")
    private String sexo;

    @Column(name = "pais_id")
    private String paisId;

    @Column(name = "tipo_id")
    private String tipoId;

    @Column(name = "num_id")
    private String numId;

    @Column(name = "cod_medico")
    private String codMedico;

    @Basic(optional = false)
    @Column(name = "fec_ini",insertable=false)
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
//
//    @Basic(optional = false)
//    @Column(name = "id_area")
//    private int idArea;

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "usuario", insertable=false, updatable=false)
    private String usuario;
       
    @OneToMany(mappedBy="medicos",cascade=CascadeType.ALL)
    private List<XmlResultado> xmlResultados;
    
     @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    @JoinColumn(name="id_area",nullable=true)
    private Area area;

    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    @JoinColumn(name="usuario",nullable=true)
    private CsUsuarios usuarios;

//     
//    @Column(name = "titulo")
//    private String titulo;
//    
    public Nombre() {
    }

    public Nombre(Integer id) {
        this.id = id;
    }

//    public String getTitulo() {
//        return titulo;
//    }
//
//    public void setTitulo(String titulo) {
//        this.titulo = titulo;
//    }

    public Nombre(Integer id, String nombre, String sexo, Date fecIni, short lockReg, String firstUser) {
        this.id = id;
        this.nombre = nombre;
        this.sexo = sexo;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
        this.firstUser = firstUser;
//        this.idArea = idArea;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Date getFechaNace() {
        return fechaNace;
    }

    public void setFechaNace(Date fechaNace) {
        this.fechaNace = fechaNace;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getPaisId() {
        return paisId;
    }

    public void setPaisId(String paisId) {
        this.paisId = paisId;
    }

    public String getTipoId() {
        return tipoId;
    }

    public void setTipoId(String tipoId) {
        this.tipoId = tipoId;
    }

    public String getNumId() {
        return numId;
    }

    public void setNumId(String numId) {
        this.numId = numId;
    }

    public String getCodMedico() {
        return codMedico;
    }

    public void setCodMedico(String codMedico) {
        this.codMedico = codMedico;
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

    public List<XmlResultado> getXmlResultados() {
        return xmlResultados;
    }

    public void setXmlResultados(List<XmlResultado> xmlResultados) {
        this.xmlResultados = xmlResultados;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    
    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public CsUsuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(CsUsuarios usuarios) {
        this.usuarios = usuarios;
    }
       
    public String getNombreMedico() {
        
//        if(getSexo().equals("M")) {
//            return getTitulo()+ "DR. "+getNombre()+" "+getApellido();
//        }
//        
//        if(getSexo().equals("F")) {
//            return "DRA. "+getNombre()+" "+getApellido();
//        }
        
        return getTitulo()+ " "+getNombre()+" "+getApellido();
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
        if (!(object instanceof Nombre)) {
            return false;
        }
        Nombre other = (Nombre) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.medico.Nombre[ id=" + id + " ]";
    }
    
}

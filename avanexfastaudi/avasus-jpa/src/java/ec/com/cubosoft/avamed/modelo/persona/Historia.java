/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.persona;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
//import org.mozilla.javascript.edu.emory.mathcs.backport.java.util.Collections;

/**
 *
 * @author Ruben
 */
@Entity
@Table(name = "historia", catalog = "avasus", schema = "persona")
public class Historia implements Serializable {

    @OneToMany(mappedBy = "idHistoria")
    private Collection<XmlAntecedentes> xmlAntecedentesCollection;
 
    @Column(name = "pariente")
    private String pariente;
    @Column(name = "email")
    private String email;

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "HISTORIA_ID_GENERATOR", sequenceName = "PERSONA.HISTORIA_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "HISTORIA_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "apellidos||' '||nombres", updatable=false, insertable=false)
    private String paciente;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "sufijo")
    private String sufijo;
    @Column(name = "estado_civil")
    private String estadoCivil;
    @Column(name = "etnia")
    private String etnia;
    @Column(name = "fecha_nace")
    @Temporal(TemporalType.DATE)
    private Date fechaNace;
    @Column(name = "pais_nace")
    private String paisNace;
    @Basic(optional = false)
    @Column(name = "orden_nac")
    private short ordenNac;
    @Column(name = "fecha_muerte")
    @Temporal(TemporalType.DATE)
    private Date fechaMuerte;
    @Column(name = "profesion")
    private String profesion;
    @Column(name = "instruccion")
    private String instruccion;
    @Column(name = "pais_id")
    private String paisId;
    @Column(name = "tipo_id")
    private String tipoId;
    @Column(name = "num_id")
    private String numId;
    @Column(name = "ocupacion")
    private String ocupacion;
    @Column(name = "ciudad_nace")
    private String ciudadNace;
    @Basic(optional = false)
    @Column(name = "sexo")
    private String sexo;
    @Column(name = "tipo_sangre")
    private String tipoSangre;
    @Column(name = "tipo_edad")
    private String tipoEdad;
    @Column(name = "pwd")
    private String pwd;
    @Basic(optional = false)
    @Column(name = "fec_ini", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Basic(optional = false)
    @Column(name = "lock_reg")
    private short lockReg;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Column(name = "first_user")
    private String firstUser;
    @Column(name = "last_user")
    private String lastUser;
    @Column(name = "first_oid")
    private BigInteger firstOid;
    @Column(name = "last_oid")
    private BigInteger lastOid;
    @Basic(optional = false)
    @Column(name = "plural_nac")
    private boolean pluralNac;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "id_nextlab")
    private Long idNextlab;
    @Column(name = "direccion")
    private String direccion;
    @OneToMany(mappedBy = "historia", cascade = CascadeType.ALL)
    @OrderColumn(name = "id")
    private List<Orden> orden;
    @OneToMany(mappedBy = "historia", cascade = CascadeType.ALL)
    private List<Episodio> episodios;
    @OneToMany(mappedBy = "historia", cascade = CascadeType.ALL)
    private List<XmlResultado> xmlResultados;

    public Historia() {
    }

    public Historia(Long id) {
        this.id = id;
    }

    public Historia(Long id, String nombres, String apellidos, short ordenNac, String sexo, Date fecIni, short lockReg, boolean pluralNac) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.ordenNac = ordenNac;
        this.sexo = sexo;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
        this.pluralNac = pluralNac;
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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSufijo() {
        return sufijo;
    }

    public void setSufijo(String sufijo) {
        this.sufijo = sufijo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getEtnia() {
        return etnia;
    }

    public void setEtnia(String etnia) {
        this.etnia = etnia;
    }

    public Date getFechaNace() {
        return fechaNace;
    }

    public void setFechaNace(Date fechaNace) {
        this.fechaNace = fechaNace;
    }

    public String getPaisNace() {
        return paisNace;
    }

    public void setPaisNace(String paisNace) {
        this.paisNace = paisNace;
    }

    public short getOrdenNac() {
        return ordenNac;
    }

    public void setOrdenNac(short ordenNac) {
        this.ordenNac = ordenNac;
    }

    public Date getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(Date fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public String getInstruccion() {
        return instruccion;
    }

    public void setInstruccion(String instruccion) {
        this.instruccion = instruccion;
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

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getCiudadNace() {
        return ciudadNace;
    }

    public void setCiudadNace(String ciudadNace) {
        this.ciudadNace = ciudadNace;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTipoSangre() {
        return tipoSangre;
    }

    public void setTipoSangre(String tipoSangre) {
        this.tipoSangre = tipoSangre;
    }

    public String getTipoEdad() {
        return tipoEdad;
    }

    public void setTipoEdad(String tipoEdad) {
        this.tipoEdad = tipoEdad;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public short getLockReg() {
        return lockReg;
    }

    public void setLockReg(short lockReg) {
        this.lockReg = lockReg;
    }

    public Date getFecUpd() {
        return fecUpd;
    }

    public void setFecUpd(Date fecUpd) {
        this.fecUpd = fecUpd;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getPluralNac() {
        return pluralNac;
    }

    public void setPluralNac(boolean pluralNac) {
        this.pluralNac = pluralNac;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Long getIdNextlab() {
        return idNextlab;
    }

    public void setIdNextlab(Long idNextlab) {
        this.idNextlab = idNextlab;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Orden> getOrden() {
        return orden;
    }

    public void setOrden(List<Orden> orden) {
        this.orden = orden;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        this.episodios = episodios;
    }

    public List<XmlResultado> getXmlResultados() {
        return xmlResultados;
    }

    public void setXmlResultados(List<XmlResultado> xmlResultados) {
        this.xmlResultados = xmlResultados;
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
        if (!(object instanceof Historia)) {
            return false;
        }
        Historia other = (Historia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    public String getPaciente() {
        return paciente;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.persona.Historia[id=" + id + ", paciente= " +paciente+"]";
    }

    public String getPariente() {
        return pariente;
    }

    public void setPariente(String pariente) {
        this.pariente = pariente;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<XmlAntecedentes> getXmlAntecedentesCollection() {
        return xmlAntecedentesCollection;
    }

    public void setXmlAntecedentesCollection(Collection<XmlAntecedentes> xmlAntecedentesCollection) {
        this.xmlAntecedentesCollection = xmlAntecedentesCollection;
    }
}

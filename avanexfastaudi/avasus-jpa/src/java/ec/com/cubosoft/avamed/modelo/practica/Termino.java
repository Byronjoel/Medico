/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.avamed.modelo.practica;

import ec.com.cubosoft.avamed.modelo.persona.Episodio;
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
@Table(name = "termino", catalog = "avasus", schema = "practica")

public class Termino implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @SequenceGenerator(name="TERMINO_ID_GENERATOR",sequenceName="PRACTICA.TERMINO_ID_SEQ",allocationSize=1 )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TERMINO_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name = "fec_ini",insertable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;

    @Basic(optional = false)
    @Column(name = "lock_reg")
    private short lockReg;

    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;

    @Basic(optional = false)
    @Column(name = "first_user")
    private String firstUser;

    @Column(name = "last_user")
    private String lastUser;

    @Column(name = "first_oid")
    private BigInteger firstOid;

    @Column(name = "last_oid")
    private BigInteger lastOid;

    @Column(name = "unidad")
    private String unidad;

    @Basic(optional = false)
    @Column(name = "tipo_dato")
    private char tipoDato;

    @Basic(optional = false)
    @Column(name = "orden")
    private short orden;

    @Column(name = "xdefecto")
    private String xdefecto;

    @Column(name = "grupo")
    private String grupo;

    @Column(name = "formula")
    private String formula;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_nombre", referencedColumnName = "id")
    private NombreP nombreP;
    
    @OneToMany(mappedBy = "terminos",cascade=CascadeType.ALL)
    private List<Episodio> episodios;


    public Termino() {
    }

    public Termino(Integer id) {
        this.id = id;
    }

    public Termino(Integer id, String descripcion, short lockReg, String firstUser, char tipoDato, short orden) {
        this.id = id;
        this.descripcion = descripcion;
        this.lockReg = lockReg;
        this.firstUser = firstUser;
        this.tipoDato = tipoDato;
        this.orden = orden;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public char getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(char tipoDato) {
        this.tipoDato = tipoDato;
    }

    public short getOrden() {
        return orden;
    }

    public void setOrden(short orden) {
        this.orden = orden;
    }

    public String getXdefecto() {
        return xdefecto;
    }

    public void setXdefecto(String xdefecto) {
        this.xdefecto = xdefecto;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public NombreP getNombreP() {
        return nombreP;
    }

    public void setNombreP(NombreP nombreP) {
        this.nombreP = nombreP;
    }

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        this.episodios = episodios;
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
        if (!(object instanceof Termino)) {
            return false;
        }
        Termino other = (Termino) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.practica.Termino[id=" + id + "]";
    }

}

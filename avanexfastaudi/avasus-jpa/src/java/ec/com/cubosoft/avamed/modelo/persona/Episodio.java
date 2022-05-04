/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.avamed.modelo.persona;

import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import ec.com.cubosoft.avamed.modelo.practica.Termino;
import java.io.Serializable;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ruben
 */
@Entity
@Table(name = "episodio", catalog = "avasus", schema = "persona")

public class Episodio implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name="EPISODIO_ID_GENERATOR",sequenceName="PERSONA.EPISODIO_ID_SEQ",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="EPISODIO_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "practica")
    private String practica;

    @Basic(optional = false)
    @Column(name = "termino")
    private String termino;

    @Basic(optional = false)
    @Column(name = "id_practica")
    private int idPractica;

//    @Basic(optional = false)
//    @Column(name = "id_termino")
//    private long idTermino;

    @Column(name = "unidad")
    private String unidad;

    @Column(name = "grupo")
    private String grupo;

    @Basic(optional = false)
    @Column(name = "tipo_dato")
    private char tipoDato;

    @Basic(optional = false)
    @Column(name = "orden")
    private short orden;

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
    @Column(name = "first_user")
    private String firstUser;
    @Column(name = "last_user")
    private String lastUser;
    @Column(name = "first_oid")
    private BigInteger firstOid;
    @Column(name = "last_oid")
    private BigInteger lastOid;
//    @Column(name = "id_orden")
//    private BigInteger idOrden;
    @Basic(optional = false)
    @Column(name = "orden_practica")
    private short ordenPractica;
//
//    @Basic(optional = false)
//    @Column(name = "id_historia")
//    private long idHistoria;

    @Basic(optional = false)
    @Column(name = "tipo_orden")
    private String tipoOrden;
    @Column(name = "resultado")
    private String resultado;
    @Column(name = "medico")
    private String medico;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
//    @Column(name = "id_medico")
//    private Integer idMedico;
    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    @JoinColumn(name="id_historia",nullable=true)
    private Historia historia;

    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    @JoinColumn(name="id_medico",nullable=true)
    private Nombre nombres;

    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    @JoinColumn(name="id_termino")
    private Termino terminos;

    @ManyToOne(cascade={CascadeType.ALL}, fetch=FetchType.LAZY)
    @JoinColumn(name="id_orden",nullable=true)
    private Orden ordens;

    public Episodio() {
    }

    public Episodio(Long id) {
        this.id = id;
    }

    public Episodio(Long id, String termino, int idPractica, long idTermino, char tipoDato, short orden, Date fecIni, short lockReg, short ordenPractica, String tipoOrden, String estado) {
        this.id = id;
        this.termino = termino;
        this.idPractica = idPractica;
//        this.idTermino = idTermino;
        this.tipoDato = tipoDato;
        this.orden = orden;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
        this.ordenPractica = ordenPractica;
        //this.idHistoria = idHistoria;
        this.tipoOrden = tipoOrden;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPractica() {
        return practica;
    }

    public void setPractica(String practica) {
        this.practica = practica;
    }

    public String getTermino() {
        return termino;
    }

    public void setTermino(String termino) {
        this.termino = termino;
    }

    public int getIdPractica() {
        return idPractica;
    }

    public void setIdPractica(int idPractica) {
        this.idPractica = idPractica;
    }

//    public long getIdTermino() {
//        return idTermino;
//    }
//
//    public void setIdTermino(long idTermino) {
//        this.idTermino = idTermino;
//    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
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

//    public BigInteger getIdOrden() {
//        return idOrden;
//    }
//
//    public void setIdOrden(BigInteger idOrden) {
//        this.idOrden = idOrden;
//    }

    public short getOrdenPractica() {
        return ordenPractica;
    }

    public void setOrdenPractica(short ordenPractica) {
        this.ordenPractica = ordenPractica;
    }
//
//    public long getIdHistoria() {
//        return idHistoria;
//    }
//
//    public void setIdHistoria(long idHistoria) {
//        this.idHistoria = idHistoria;
//    }

    public String getTipoOrden() {
        return tipoOrden;
    }

    public void setTipoOrden(String tipoOrden) {
        this.tipoOrden = tipoOrden;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Nombre getNombres() {
        return nombres;
    }

    public void setNombres(Nombre nombres) {
        this.nombres = nombres;
    }

    public Termino getTerminos() {
        return terminos;
    }

    public void setTerminos(Termino terminos) {
        this.terminos = terminos;
    }

    public Historia getHistoria() {
        return historia;
    }

    public void setHistoria(Historia historia) {
        this.historia = historia;
    }

    public Orden getOrdens() {
        return ordens;
    }

    public void setOrdens(Orden ordens) {
        this.ordens = ordens;
    }

    

//    public Integer getIdMedico() {
//        return idMedico;
//    }
//
//    public void setIdMedico(Integer idMedico) {
//        this.idMedico = idMedico;
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
        if (!(object instanceof Episodio)) {
            return false;
        }
        Episodio other = (Episodio) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.persona.Episodio[id=" + id + "]";
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.persona;

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
 * @author DESARROLLO
 */
@Entity
@Table(name = "receta", catalog = "avasus", schema = "persona")
@NamedQueries({
    @NamedQuery(name = "Receta.findAll", query = "SELECT r FROM Receta r")})
public class Receta implements Serializable {


    @Column(name = "estado")
    private String estado;

    @Column(name = "usuario")
    private String usuario;
    @Column(name = "first_user")
    private String firstUser;
    @Column(name = "last_user")
    private String lastUser;

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "RECETA_ID_SEQ", sequenceName = "persona.receta_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RECETA_ID_SEQ")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;

    @Column(name = "medico")
    private String medico;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "id_historia")
    private BigInteger idHistoria;
    @Column(name = "id_orden")
    private BigInteger idOrden;
    @Column(name = "id_practica")
    private Integer idPractica;
//    @Column(name = "id_medico")
//    private Integer idMedico;
    @Basic(optional = false)
    @Column(name = "fec_ini", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Basic(optional = false)
    @Column(name = "lock_reg")
    private short lockReg;
    @Column(name = "texto")
    private String texto;

    public Receta() {
    }

    public Receta(Long id) {
        this.id = id;
    }

    public Receta(Long id, Date fecIni, short lockReg) {
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

    public BigInteger getIdHistoria() {
        return idHistoria;
    }

    public void setIdHistoria(BigInteger idHistoria) {
        this.idHistoria = idHistoria;
    }

    public BigInteger getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(BigInteger idOrden) {
        this.idOrden = idOrden;
    }

    public Integer getIdPractica() {
        return idPractica;
    }

    public void setIdPractica(Integer idPractica) {
        this.idPractica = idPractica;
    }

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

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
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
        if (!(object instanceof Receta)) {
            return false;
        }
        Receta other = (Receta) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.persona.Receta[ id=" + id + " ]";
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}

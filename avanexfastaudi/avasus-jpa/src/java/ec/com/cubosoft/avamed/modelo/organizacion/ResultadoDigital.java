/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.organizacion;

import ec.com.cubosoft.avamed.modelo.organizacion.AvasDigital;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "resultado_digital", catalog = "avasus", schema = "persona")
@NamedQueries({
    @NamedQuery(name = "ResultadoDigital.findAll", query = "SELECT r FROM ResultadoDigital r")})
public class ResultadoDigital implements Serializable {
    private static final long serialVersionUID = 1L;
     @Id
    @SequenceGenerator(name="RESULTADO_DIGITAL_ID_GENERATOR",sequenceName="persona.resultado_digital_id_seq",allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="RESULTADO_DIGITAL_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Lob
    @Column(name = "dato")
    private byte[] dato;
    @Size(max = 100)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 30)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 2147483647)
    @Column(name = "ruta")
    private String ruta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fec_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Column(name = "lock_reg")
    private Short lockReg;
    @Size(max = 30)
    @Column(name = "first_user")
    private String firstUser;
    @Size(max = 30)
    @Column(name = "last_user")
    private String lastUser;
    @Column(name = "first_oid")
    private BigInteger firstOid;
    @Column(name = "last_oid")
    private BigInteger lastOid;
    @Size(max = 256)
    @Column(name = "observacion")
    private String observacion;
    @JoinColumn(name = "id_avas_digital", referencedColumnName = "id")
    @ManyToOne
    private AvasDigital idAvasDigital;

    public ResultadoDigital() {
    }

    public ResultadoDigital(Long id) {
        this.id = id;
    }

    public ResultadoDigital(Long id, Date fecIni) {
        this.id = id;
        this.fecIni = fecIni;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDato() {
        return dato;
    }

    public void setDato(byte[] dato) {
        this.dato = dato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
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

    public Short getLockReg() {
        return lockReg;
    }

    public void setLockReg(Short lockReg) {
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

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public AvasDigital getIdAvasDigital() {
        return idAvasDigital;
    }

    public void setIdAvasDigital(AvasDigital idAvasDigital) {
        this.idAvasDigital = idAvasDigital;
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
        if (!(object instanceof ResultadoDigital)) {
            return false;
        }
        ResultadoDigital other = (ResultadoDigital) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.persona.ResultadoDigital[ id=" + id + " ]";
    }
    
}

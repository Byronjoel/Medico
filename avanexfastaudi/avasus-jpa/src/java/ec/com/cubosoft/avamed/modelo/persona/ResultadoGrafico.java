/*
 * To change this template, choose Tools | Templates
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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "resultado_grafico", catalog = "avasus", schema = "persona")
@NamedQueries({
    @NamedQuery(name = "ResultadoGrafico.findAll", query = "SELECT r FROM ResultadoGrafico r")})
public class ResultadoGrafico implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "RESULTADO_GRAFICO_ID_GENERATOR", sequenceName = "PERSONA.RESULTADO_GRAFICO_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RESULTADO_GRAFICO_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "id_xml_resultado")
    private Long idXmlResultado;
    @Lob
    @Column(name = "dato")
    private byte[] dato;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "ruta")
    private String ruta;
    @Basic(optional = false)
    @Column(name = "fec_ini", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Basic(optional = false)
    @Column(name = "lock_reg", insertable = false)
    private Short lockReg;
    @Column(name = "first_user")
    private String firstUser;
    @Column(name = "last_user")
    private String lastUser;
    @Column(name = "first_oid")
    private BigInteger firstOid;
    @Column(name = "last_oid")
    private BigInteger lastOid;

    public ResultadoGrafico() {
    }

    public ResultadoGrafico(Integer id) {
        this.id = id;
    }

    public ResultadoGrafico(String descripcion) {
        this.descripcion = descripcion;
    }

    public ResultadoGrafico(Integer id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public ResultadoGrafico(Integer id, Date fecIni) {
        this.id = id;
        this.fecIni = fecIni;
    }

    public ResultadoGrafico(Integer id, Date fecIni, short lockReg) {
        this.id = id;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getIdXmlResultado() {
        return idXmlResultado;
    }

    public void setIdXmlResultado(Long idXmlResultado) {
        this.idXmlResultado = idXmlResultado;
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

    public String getCod() {
        return codigo;
    }

    public void setCod(String codigo) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ResultadoGrafico)) {
            return false;
        }
        ResultadoGrafico other = (ResultadoGrafico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.persona.ResultadoGrafico[id=" + id + "]";
    }

}

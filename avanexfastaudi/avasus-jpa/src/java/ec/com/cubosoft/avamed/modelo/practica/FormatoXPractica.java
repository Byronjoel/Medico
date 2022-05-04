/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.avamed.modelo.practica;

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
 * @author ruben
 */
@Entity
@Table(name = "formato_x_practica", catalog = "avasus", schema = "practica")
@NamedQueries({
    @NamedQuery(name = "FormatoXPractica.findAll", query = "SELECT f FROM FormatoXPractica f")})
public class FormatoXPractica implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="FORMATO_ID_GENERATOR",sequenceName="PRACTICA.FORMATO_X_PRACTICA_ID_SEQ",allocationSize=1 )
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FORMATO_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_practica")
    private Integer idPractica;
    @Basic(optional = false)
    @Column(name = "id_hoja")
    private int idHoja;
    @Column(name = "descripcion")
    private String descripcion;
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
    @Column(name = "fisrt_oid")
    private BigInteger fisrtOid;
    @Column(name = "last_oid")
    private BigInteger lastOid;
    @Lob
    @Column(name = "xml")
    private byte[] xml;

    public FormatoXPractica() {
    }

    public FormatoXPractica(Long id) {
        this.id = id;
    }

    public FormatoXPractica(Long id, int idHoja, Date fecIni, short lockReg) {
        this.id = id;
        this.idHoja = idHoja;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdPractica() {
        return idPractica;
    }

    public void setIdPractica(Integer idPractica) {
        this.idPractica = idPractica;
    }

    public int getIdHoja() {
        return idHoja;
    }

    public void setIdHoja(int idHoja) {
        this.idHoja = idHoja;
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

    public BigInteger getFisrtOid() {
        return fisrtOid;
    }

    public void setFisrtOid(BigInteger fisrtOid) {
        this.fisrtOid = fisrtOid;
    }

    public BigInteger getLastOid() {
        return lastOid;
    }

    public void setLastOid(BigInteger lastOid) {
        this.lastOid = lastOid;
    }

    public byte[] getXml() {
        return xml;
    }

    public void setXml(byte[] xml) {
        this.xml = xml;
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
        if (!(object instanceof FormatoXPractica)) {
            return false;
        }
        FormatoXPractica other = (FormatoXPractica) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.persona.FormatoXPractica[id=" + id + "]";
    }

}

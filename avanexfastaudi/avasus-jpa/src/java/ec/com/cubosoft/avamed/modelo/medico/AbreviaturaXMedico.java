/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.medico;

import ec.com.cubosoft.avamed.modelo.practica.Abreviatura;
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
import javax.persistence.ManyToOne;

import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "abreviatura_x_medico", catalog = "avasus", schema = "medico")
@XmlRootElement

public class AbreviaturaXMedico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "ABREVIATURA_X_MEDICO_ID_GENERATOR", sequenceName = "MEDICO.ABREVIATURA_X_MEDICO_ID_SEG", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ABREVIATURA_X_MEDICO_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "id_abreviatura", referencedColumnName = "id")
    private Abreviatura abreviatura;
    @Basic(optional = false)
    @Column(name = "fec_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Basic(optional = false)
    @Column(name = "lock_reg")
    private short lockReg;
    @Size(max = 2147483647)
    @Column(name = "first_user")
    private String firstUser;
    @Size(max = 2147483647)
    @Column(name = "last_user")
    private String lastUser;
  
    
    @ManyToOne
    @JoinColumn(name = "id_medico", referencedColumnName = "id")
    private Nombre Medico;

    public Nombre getMedico() {
        return Medico;
    }

    public void setMedico(Nombre Medico) {
        this.Medico = Medico;
    }

    public AbreviaturaXMedico() {
    }

    public AbreviaturaXMedico(Integer id) {
        this.id = id;
    }

    public AbreviaturaXMedico(Integer id, Date fecIni, short lockReg) {
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

    public Abreviatura getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(Abreviatura abreviatura) {
        this.abreviatura = abreviatura;
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

   

   

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AbreviaturaXMedico)) {
            return false;
        }
        AbreviaturaXMedico other = (AbreviaturaXMedico) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.medico.AbreviaturaXMedico[ id=" + id + " ]";
    }
}

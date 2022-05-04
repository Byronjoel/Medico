/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.avamed.modelo.core;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author DESARROLLADOR
 */
@Entity
@Table(name = "ciediez", catalog = "avasus", schema = "core")
@NamedQueries({
    @NamedQuery(name = "Ciediez.findAll", query = "SELECT c FROM Ciediez c")})
public class Ciediez implements Serializable {

    @Column(name = "grupo")
    private String grupo;

    @Column(name = "cod_ref")
    private String codRef;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "cod_cie")
    private String codCie;
    @Size(max = 150)
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fec_ini")
    @Temporal(TemporalType.TIME)
    private Date fecIni;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIME)
    private Date fecUpd;
    @Column(name = "lock_reg")
    private Short lockReg;
    @Column(name = "lock_arc")
    private Short lockArc;
    @Size(max = 15)
    @Column(name = "first_user")
    private String firstUser;
    @Size(max = 15)
    @Column(name = "last_user")
    private String lastUser;
    @Column(name = "tipo")
    private String tipo;

    public Ciediez() {
    }

    public Ciediez(String codCie) {
        this.codCie = codCie;
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

    public Short getLockArc() {
        return lockArc;
    }

    public void setLockArc(Short lockArc) {
        this.lockArc = lockArc;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codCie != null ? codCie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ciediez)) {
            return false;
        }
        Ciediez other = (Ciediez) object;
        if ((this.codCie == null && other.codCie != null) || (this.codCie != null && !this.codCie.equals(other.codCie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.core.Ciediez[ codCie=" + codCie + " ]";
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getCodRef() {
        return codRef;
    }

    public void setCodRef(String codRef) {
        this.codRef = codRef;
    }
    
}

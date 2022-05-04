/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.core;

import ec.com.cubosoft.avamed.modelo.medico.Nombre;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author pc
 */
@Entity
@Table(name = "cs_usuarios", catalog = "avasus", schema = "core")
@NamedQueries({
    @NamedQuery(name = "CsUsuarios.findAll", query = "SELECT c FROM CsUsuarios c")})
public class CsUsuarios implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "per_upload")
    private short perUpload;
    @Column(name = "per_delete")
    private Short perDelete;

 
//    @Lob
//    @Column(name = "firma")
//    private byte[] firma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "pri_vis")
    private short priVis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "per_abto")
    private short perAbto;

    @Basic(optional = false)
    @NotNull
    @Column(name = "per_cyp")
    private short perCyp;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "pwd_usu")
    private String pwdUsu;
    @Column(name = "doc_usu")
    private String docUsu;
    @Basic(optional = false)
    @Column(name = "nom_usu")
    private String nomUsu;
    @Basic(optional = false)
    @Column(name = "fec_ini")
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
    
    @Basic(optional = false)
    @Column(name = "lock_usu")
    private short lockUsu;
    @JoinColumn(name = "cod_gru", referencedColumnName = "cod_gru")
    @ManyToOne(optional = false)
    private CsGrupos csGrupos;
    @OneToMany(mappedBy = "usuarios", cascade = CascadeType.ALL)
    private List<Nombre> medicos;
    
    public CsUsuarios() {
    }

    public CsUsuarios(String usuario) {
        this.usuario = usuario;
    }

    public CsUsuarios(String usuario, String pwdUsu, String nomUsu, Date fecIni, short lockReg, short lockUsu) {
        this.usuario = usuario;
        this.pwdUsu = pwdUsu;
        this.nomUsu = nomUsu;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
        this.lockUsu = lockUsu;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPwdUsu() {
        return pwdUsu;
    }

    public Short getPerDelete() {
        return perDelete;
    }

    public void setPerDelete(Short perDelete) {
        this.perDelete = perDelete;
    }

    public void setPwdUsu(String pwdUsu) {
        this.pwdUsu = pwdUsu;
    }

    public String getDocUsu() {
        return docUsu;
    }

    public void setDocUsu(String docUsu) {
        this.docUsu = docUsu;
    }

    public String getNomUsu() {
        return nomUsu;
    }

    public void setNomUsu(String nomUsu) {
        this.nomUsu = nomUsu;
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

   
    public short getLockUsu() {
        return lockUsu;
    }

    public void setLockUsu(short lockUsu) {
        this.lockUsu = lockUsu;
    }

    public CsGrupos getCsGrupos() {
        return csGrupos;
    }

    public void setCsGrupos(CsGrupos csGrupos) {
        this.csGrupos = csGrupos;
    }

    public List<Nombre> getMedicos() {
        return medicos;
    }

    public void setMedicos(List<Nombre> medicos) {
        this.medicos = medicos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (usuario != null ? usuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CsUsuarios)) {
            return false;
        }
        CsUsuarios other = (CsUsuarios) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.core.CsUsuarios[usuario=" + usuario + "]";
    }

    public short getPerCyp() {
        return perCyp;
    }

    public void setPerCyp(short perCyp) {
        this.perCyp = perCyp;
    }

//    public byte[] getFirma() {
//        return firma;
//    }
//
//    public void setFirma(byte[] firma) {
//        this.firma = firma;
//    }

    public short getPriVis() {
        return priVis;
    }

    public void setPriVis(short priVis) {
        this.priVis = priVis;
    }

    public short getPerAbto() {
        return perAbto;
    }

    public void setPerAbto(short perAbto) {
        this.perAbto = perAbto;
    }

    public Short getPerUpload() {
        return perUpload;
    }

    public void setPerUpload(Short perUpload) {
        this.perUpload = perUpload;
    }

  
    public void setPerUpload(short perUpload) {
        this.perUpload = perUpload;
    }

}

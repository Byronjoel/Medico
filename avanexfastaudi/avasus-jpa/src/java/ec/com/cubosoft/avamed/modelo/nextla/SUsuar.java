/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.nextla;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@Table(name = "s_usuar")
@NamedQueries({
    @NamedQuery(name = "SUsuar.findAll", query = "SELECT s FROM SUsuar s")})
public class SUsuar implements Serializable {

    @Column(name = "web")
    private Short web;
    @Column(name = "venus")
    private Short venus;
    @Column(name = "avasus")
    private Short avasus;
    @Column(name = "lirelab")
    private Short lirelab;
    @Column(name = "printy")
    private Short printy;
    @Column(name = "bloqueo")
    private Short bloqueo;
   
    @Column(name = "pwd_usu")
    private String pwdUsu;
    @Column(name = "cod_gru")
    private String codGru;
    @Column(name = "fec_ini")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Column(name = "fec_fin")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecFin;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Column(name = "doc_usu")
    private String docUsu;
    @Column(name = "mail_usu")
    private String mailUsu;
    @Column(name = "pwd_p12")
    private String pwdP12;
       @Column(name = "telf_usu")
    private String telfUsu;
//    @Lob
//    @Column(name = "foto")
//    private byte[] foto;
//    @Lob
//    @Column(name = "firma")
//    private byte[] firma;
//    @Lob
//    @Column(name = "p12")
//    private byte[] p12;
//    @Column(name = "firgerprint_hash")
//    private String firgerprintHash;
//    @Lob
//    @Column(name = "sello")
//    private byte[] sello;
//    @Lob
//    @Column(name = "firma1")
//    private byte[] firma1;
    @Column(name = "firma_pie")
    private String firmaPie;
    @Column(name = "entrega")
    private Short entrega;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "descrip")
    private String descrip;
    @Column(name = "clave")
    private String clave;
    @Column(name = "leyenda")
    private String leyenda;
    @Column(name = "clave1")
    private String clave1;

    public SUsuar() {
    }

    public SUsuar(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getLeyenda() {
        return leyenda;
    }

    public void setLeyenda(String leyenda) {
        this.leyenda = leyenda;
    }


    public String getClave1() {
        return clave1;
    }

    public void setClave1(String clave1) {
        this.clave1 = clave1;
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
        if (!(object instanceof SUsuar)) {
            return false;
        }
        SUsuar other = (SUsuar) object;
        if ((this.usuario == null && other.usuario != null) || (this.usuario != null && !this.usuario.equals(other.usuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.nextla.SUsuar[ usuario=" + usuario + " ]";
    }

    public Short getWeb() {
        return web;
    }

    public void setWeb(Short web) {
        this.web = web;
    }

    public Short getVenus() {
        return venus;
    }

    public void setVenus(Short venus) {
        this.venus = venus;
    }

    public Short getAvasus() {
        return avasus;
    }

    public void setAvasus(Short avasus) {
        this.avasus = avasus;
    }

    public Short getLirelab() {
        return lirelab;
    }

    public void setLirelab(Short lirelab) {
        this.lirelab = lirelab;
    }

    public Short getPrinty() {
        return printy;
    }

    public void setPrinty(Short printy) {
        this.printy = printy;
    }

    public Short getBloqueo() {
        return bloqueo;
    }

    public void setBloqueo(Short bloqueo) {
        this.bloqueo = bloqueo;
    }

    public String getPwdUsu() {
        return pwdUsu;
    }

    public void setPwdUsu(String pwdUsu) {
        this.pwdUsu = pwdUsu;
    }

    public String getCodGru() {
        return codGru;
    }

    public void setCodGru(String codGru) {
        this.codGru = codGru;
    }

    public Date getFecIni() {
        return fecIni;
    }

    public void setFecIni(Date fecIni) {
        this.fecIni = fecIni;
    }

    public Date getFecFin() {
        return fecFin;
    }

    public void setFecFin(Date fecFin) {
        this.fecFin = fecFin;
    }

    public Date getFecUpd() {
        return fecUpd;
    }

    public void setFecUpd(Date fecUpd) {
        this.fecUpd = fecUpd;
    }

    public String getDocUsu() {
        return docUsu;
    }

    public void setDocUsu(String docUsu) {
        this.docUsu = docUsu;
    }

    public String getMailUsu() {
        return mailUsu;
    }

    public void setMailUsu(String mailUsu) {
        this.mailUsu = mailUsu;
    }

    public String getPwdP12() {
        return pwdP12;
    }

    public void setPwdP12(String pwdP12) {
        this.pwdP12 = pwdP12;
    }

    public String getTelfUsu() {
        return telfUsu;
    }

    public void setTelfUsu(String telfUsu) {
        this.telfUsu = telfUsu;
    }

//    public byte[] getFoto() {
//        return foto;
//    }
//
//    public void setFoto(byte[] foto) {
//        this.foto = foto;
//    }
//
//    public byte[] getFirma() {
//        return firma;
//    }
//
//    public void setFirma(byte[] firma) {
//        this.firma = firma;
//    }
//
//    public byte[] getP12() {
//        return p12;
//    }
//
//    public void setP12(byte[] p12) {
//        this.p12 = p12;
//    }
//
//    public String getFirgerprintHash() {
//        return firgerprintHash;
//    }
//
//    public void setFirgerprintHash(String firgerprintHash) {
//        this.firgerprintHash = firgerprintHash;
//    }
//
//    public byte[] getSello() {
//        return sello;
//    }
//
//    public void setSello(byte[] sello) {
//        this.sello = sello;
//    }
//
//    public byte[] getFirma1() {
//        return firma1;
//    }
//
//    public void setFirma1(byte[] firma1) {
//        this.firma1 = firma1;
//    }

    public String getFirmaPie() {
        return firmaPie;
    }

    public void setFirmaPie(String firmaPie) {
        this.firmaPie = firmaPie;
    }

    public Short getEntrega() {
        return entrega;
    }

    public void setEntrega(Short entrega) {
        this.entrega = entrega;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.nextla;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DESARROLLO
 */
@Entity
@Table(name = "lisanaPrac")
@NamedQueries({
    @NamedQuery(name = "LisanaPrac.findAll", query = "SELECT l FROM LisanaPrac l")})
public class LisanaPrac implements Serializable {
    
    
  private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
     @Column(name = "cod_ana")
    private String codAna;
   
    @Column(name = "metodo")
    private String metodo;
    @Column(name = "cod_prac")
    private Long codPrac;
  
    @Column(name = "tip_ana")
    private String tipAna;
   
    @Column(name = "aux")
    private String aux;

  
   
    @Column(name = "des_ana")
    private String desAna;
    @Column(name = "txt_prev")
    private String txtPrev;
    @Column(name = "txt_post")
    private String txtPost;
    @Column(name = "ord_prn")
    private Short ordPrn;
    @Column(name = "msg_ana")
    private String msgAna;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "cod_gra")
    private String codGra;
    @Column(name = "imprime_des")
    private Character imprimeDes;
    @Column(name = "COD_SEC")
    private String codSec;
    @Column(name = "microbiologia")
    private Character microbiologia;
    @Column(name = "activo")
    private Character activo;
    @Column(name = "cod_recep")
    private String codRecep;
    @Column(name = "cod_ind")
    private String codInd;
    @Column(name = "des_ana_inf")
    private String desAnaInf;
    @Column(name = "dia_ana")
    private String diaAna;
//    @Column(name = "tipo_s")
//    private Character tipoS;

    public LisanaPrac() {
    }

   
    public String getDesAna() {
        return desAna;
    }

    public void setDesAna(String desAna) {
        this.desAna = desAna;
    }

    public String getTxtPrev() {
        return txtPrev;
    }

    public void setTxtPrev(String txtPrev) {
        this.txtPrev = txtPrev;
    }

    public String getTxtPost() {
        return txtPost;
    }

    public void setTxtPost(String txtPost) {
        this.txtPost = txtPost;
    }

    public Short getOrdPrn() {
        return ordPrn;
    }

    public void setOrdPrn(Short ordPrn) {
        this.ordPrn = ordPrn;
    }

    public String getMsgAna() {
        return msgAna;
    }

    public void setMsgAna(String msgAna) {
        this.msgAna = msgAna;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCodGra() {
        return codGra;
    }

    public void setCodGra(String codGra) {
        this.codGra = codGra;
    }

    public Character getImprimeDes() {
        return imprimeDes;
    }

    public void setImprimeDes(Character imprimeDes) {
        this.imprimeDes = imprimeDes;
    }

    public String getCodSec() {
        return codSec;
    }

    public void setCodSec(String codSec) {
        this.codSec = codSec;
    }

    public Character getMicrobiologia() {
        return microbiologia;
    }

    public void setMicrobiologia(Character microbiologia) {
        this.microbiologia = microbiologia;
    }

    public Character getActivo() {
        return activo;
    }

    public void setActivo(Character activo) {
        this.activo = activo;
    }

    public String getCodRecep() {
        return codRecep;
    }

    public void setCodRecep(String codRecep) {
        this.codRecep = codRecep;
    }

    public String getCodInd() {
        return codInd;
    }

    public void setCodInd(String codInd) {
        this.codInd = codInd;
    }

    public String getDesAnaInf() {
        return desAnaInf;
    }

    public void setDesAnaInf(String desAnaInf) {
        this.desAnaInf = desAnaInf;
    }

    public String getDiaAna() {
        return diaAna;
    }

    public void setDiaAna(String diaAna) {
        this.diaAna = diaAna;
    }

  

  
  

    public LisanaPrac(String codAna) {
        this.codAna = codAna;
    }

    public String getCodAna() {
        return codAna;
    }

    public void setCodAna(String codAna) {
        this.codAna = codAna;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public Long getCodPrac() {
        return codPrac;
    }

    public void setCodPrac(Long codPrac) {
        this.codPrac = codPrac;
    }

    public String getTipAna() {
        return tipAna;
    }

    public void setTipAna(String tipAna) {
        this.tipAna = tipAna;
    }

    public String getAux() {
        return aux;
    }

    public void setAux(String aux) {
        this.aux = aux;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codAna != null ? codAna.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LisanaPrac)) {
            return false;
        }
        LisanaPrac other = (LisanaPrac) object;
        if ((this.codAna == null && other.codAna != null) || (this.codAna != null && !this.codAna.equals(other.codAna))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.nextla.LisanaPrac[ codAna=" + codAna + " ]";
    }
    
}

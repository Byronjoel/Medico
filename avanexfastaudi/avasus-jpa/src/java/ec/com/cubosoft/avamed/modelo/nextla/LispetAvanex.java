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
@Table(name = "lispet_avanex")
@NamedQueries({
    @NamedQuery(name = "LispetAvanex.findAll", query = "SELECT l FROM LispetAvanex l")})
public class LispetAvanex implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "nro_ord")
    private Long nroOrd;
    @Column(name = "cod_pac")
    private Long codPac;
      @Column(name = "clave")
    private String clave;
    @Column(name = "email_pac")
    private String emailPac;
    @Column(name = "tit_pac")
    private String titPac;
    @Column(name = "tipo_doc")
    private String tipoDoc;
    @Column(name = "nom_med")
    private String nomMed;
    @Column(name = "fecha_modif")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_pet")
    private long idPet;

    @Basic(optional = false)
    @NotNull
    @Column(name = "fec_ord")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecOrd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fec_ent")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecEnt;
    @Size(max = 10)
    @Column(name = "cod_med")
    private String codMed;
    @Size(max = 5)
    @Column(name = "cod_ori")
    private String codOri;
    @Size(max = 2)
    @Column(name = "sts_ord")
    private String stsOrd;
    @Size(max = 2)
    @Column(name = "sts_adm")
    private String stsAdm;
    @Size(max = 40)
    @Column(name = "des_ori")
    private String desOri;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "Expr1")
    private String expr1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "cod_ana")
    private String codAna;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "sts_pet")
    private String stsPet;

    @Size(max = 128)
    @Column(name = "des_ana")
    private String desAna;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Expr4")
    private int expr4;
    @Column(name = "doc_pac")
    private String docPac;
    @Column(name = "nom_pac")
    private String nomPac;
    @Column(name = "nom_pac2")
    private String nomPac2;
    @Column(name = "ape_pac")
    private String apePac;
    @Column(name = "apm_pac")
    private String apmPac;
    @Column(name = "fna_pac")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fnaPac;
    @Column(name = "sex_pac")
    private Character sexPac;
    @Column(name = "tel_pac")
    private String telPac;
    @Basic(optional = false)
    @Column(name = "Expr2")
    private String expr2;

    public LispetAvanex() {
    }

    public Date getFecOrd() {
        return fecOrd;
    }

    public void setFecOrd(Date fecOrd) {
        this.fecOrd = fecOrd;
    }

    public Date getFecEnt() {
        return fecEnt;
    }

    public void setFecEnt(Date fecEnt) {
        this.fecEnt = fecEnt;
    }

    public Long getCodPac() {
        return codPac;
    }

    public void setCodPac(Long codPac) {
        this.codPac = codPac;
    }

    public String getCodMed() {
        return codMed;
    }

    public void setCodMed(String codMed) {
        this.codMed = codMed;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getCodOri() {
        return codOri;
    }

    public void setCodOri(String codOri) {
        this.codOri = codOri;
    }

    public String getStsOrd() {
        return stsOrd;
    }

    public void setStsOrd(String stsOrd) {
        this.stsOrd = stsOrd;
    }

    public String getStsAdm() {
        return stsAdm;
    }

    public void setStsAdm(String stsAdm) {
        this.stsAdm = stsAdm;
    }

    public String getDesOri() {
        return desOri;
    }

    public void setDesOri(String desOri) {
        this.desOri = desOri;
    }

    public String getExpr1() {
        return expr1;
    }

    public void setExpr1(String expr1) {
        this.expr1 = expr1;
    }

    public String getCodAna() {
        return codAna;
    }

    public void setCodAna(String codAna) {
        this.codAna = codAna;
    }

    public String getStsPet() {
        return stsPet;
    }

    public void setStsPet(String stsPet) {
        this.stsPet = stsPet;
    }

    public long getIdPet() {
        return idPet;
    }

    public void setIdPet(long idPet) {
        this.idPet = idPet;
    }

    public String getDesAna() {
        return desAna;
    }

    public void setDesAna(String desAna) {
        this.desAna = desAna;
    }

    public int getExpr4() {
        return expr4;
    }

    public void setExpr4(int expr4) {
        this.expr4 = expr4;
    }

    public String getDocPac() {
        return docPac;
    }

    public void setDocPac(String docPac) {
        this.docPac = docPac;
    }

    public String getNomPac() {
        return nomPac;
    }

    public void setNomPac(String nomPac) {
        this.nomPac = nomPac;
    }

    public String getNomPac2() {
        return nomPac2;
    }

    public void setNomPac2(String nomPac2) {
        this.nomPac2 = nomPac2;
    }

    public String getApePac() {
        return apePac;
    }

    public void setApePac(String apePac) {
        this.apePac = apePac;
    }

    public String getApmPac() {
        return apmPac;
    }

    public void setApmPac(String apmPac) {
        this.apmPac = apmPac;
    }

    public Date getFnaPac() {
        return fnaPac;
    }

    public void setFnaPac(Date fnaPac) {
        this.fnaPac = fnaPac;
    }

    public Character getSexPac() {
        return sexPac;
    }

    public void setSexPac(Character sexPac) {
        this.sexPac = sexPac;
    }

    public String getTelPac() {
        return telPac;
    }

    public void setTelPac(String telPac) {
        this.telPac = telPac;
    }

    public String getExpr2() {
        return expr2;
    }

    public void setExpr2(String expr2) {
        this.expr2 = expr2;
    }

    public String getNomMed() {
        return nomMed;
    }

    public void setNomMed(String nomMed) {
        this.nomMed = nomMed;
    }

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public String getTitPac() {
        return titPac;
    }

    public void setTitPac(String titPac) {
        this.titPac = titPac;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public void setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
    }

    public Long getNroOrd() {
        return nroOrd;
    }

    public void setNroOrd(Long nroOrd) {
        this.nroOrd = nroOrd;
    }

    public String getEmailPac() {
        return emailPac;
    }

    public void setEmailPac(String emailPac) {
        this.emailPac = emailPac;
    }

}

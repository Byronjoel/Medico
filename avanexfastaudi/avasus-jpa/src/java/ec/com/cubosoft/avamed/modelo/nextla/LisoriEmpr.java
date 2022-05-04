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

/**
 *
 * @author DESARROLLO
 */
@Entity
@Table(name = "lisoriEmpr")
@NamedQueries({
    @NamedQuery(name = "LisoriEmpr.findAll", query = "SELECT l FROM LisoriEmpr l")})
public class LisoriEmpr implements Serializable {

    @Column(name = "aux")
    private Long aux;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_oriEmpr")
    private String codoriEmpr;
    @Column(name = "des_ori")
    private String desOri;
    @Column(name = "cod_seg")
    private String codSeg;
     @Column(name = "cod_pln")
    private String codPln;
    @Column(name = "email_ori")
    private String emailOri;
    @Column(name = "footer")
    private String footer;
    @Column(name = "header")
    private String header;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Column(name = "fax")
    private String fax;
    @Column(name = "num_auto")
    private Character numAuto;
    @Column(name = "ref_externa")
    private Character refExterna;
    @Column(name = "ven_ind")
    private Integer venInd;
    @Column(name = "ven_cua")
    private Character venCua;
    @Column(name = "ven_letra")
    private String venLetra;
    @Column(name = "id_nom")
    private Long idNom;
    @Column(name = "tipo")
    private Character tipo;

    public LisoriEmpr() {
    }

    public LisoriEmpr(String codoriEmpr) {
        this.codoriEmpr = codoriEmpr;
    }

    public String getCodoriEmpr() {
        return codoriEmpr;
    }

    public void setCodoriEmpr(String codoriEmpr) {
        this.codoriEmpr = codoriEmpr;
    }

    public String getDesOri() {
        return desOri;
    }

    public void setDesOri(String desOri) {
        this.desOri = desOri;
    }

    public String getCodSeg() {
        return codSeg;
    }

    public void setCodSeg(String codSeg) {
        this.codSeg = codSeg;
    }

    public String getCodPln() {
        return codPln;
    }

    public void setCodPln(String codPln) {
        this.codPln = codPln;
    }

    public String getEmailOri() {
        return emailOri;
    }

    public void setEmailOri(String emailOri) {
        this.emailOri = emailOri;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Character getNumAuto() {
        return numAuto;
    }

    public void setNumAuto(Character numAuto) {
        this.numAuto = numAuto;
    }

    public Character getRefExterna() {
        return refExterna;
    }

    public void setRefExterna(Character refExterna) {
        this.refExterna = refExterna;
    }

    public Integer getVenInd() {
        return venInd;
    }

    public void setVenInd(Integer venInd) {
        this.venInd = venInd;
    }

    public Character getVenCua() {
        return venCua;
    }

    public void setVenCua(Character venCua) {
        this.venCua = venCua;
    }

    public String getVenLetra() {
        return venLetra;
    }

    public void setVenLetra(String venLetra) {
        this.venLetra = venLetra;
    }

    public Long getIdNom() {
        return idNom;
    }

    public void setIdNom(Long idNom) {
        this.idNom = idNom;
    }


    public Character getTipo() {
        return tipo;
    }

    public void setTipo(Character tipo) {
        this.tipo = tipo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codoriEmpr != null ? codoriEmpr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LisoriEmpr)) {
            return false;
        }
        LisoriEmpr other = (LisoriEmpr) object;
        if ((this.codoriEmpr == null && other.codoriEmpr != null) || (this.codoriEmpr != null && !this.codoriEmpr.equals(other.codoriEmpr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.nextla.LisoriEmp[ codoriEmp=" + codoriEmpr + " ]";
    }

    public Long getAux() {
        return aux;
    }

    public void setAux(Long aux) {
        this.aux = aux;
    }
    
}

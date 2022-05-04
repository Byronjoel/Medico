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
@Table(name = "view_pedido")
@NamedQueries({
    @NamedQuery(name = "ViewPedido.findAll", query = "SELECT v FROM ViewPedido v")})
public class ViewPedido implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private String id;
    
    
    
    @Basic(optional = false)
    @Column(name = "nro_ord")
    private Long nroOrd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_ana")
    private String codAna;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sts_pet")
    private String stsPet;
      @Column(name = "cod_ori")
    private String codOri;
    @Column(name = "cod_pac")
    private Long codPac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cod_anaPrac")
    private String codanaPrac;
    @Column(name = "id_pra")
    private BigInteger idPra;
    @Column(name = "tipo_s")
    private Character tipoS;
    @Size(max = 128)
    @Column(name = "descripcion")
    private String descripcion;
    @Size(max = 64)
    @Column(name = "area")
    private String area;
    @Size(max = 20)
    @Column(name = "nom_pac")
    private String nomPac;
    @Size(max = 20)
    @Column(name = "nom_pac2")
    private String nomPac2;
    @Size(max = 20)
    @Column(name = "ape_pac")
    private String apePac;
    @Size(max = 20)
    @Column(name = "apm_pac")
    private String apmPac;
    @Size(max = 15)
    @Column(name = "doc_pac")
    private String docPac;
    @Column(name = "sex_pac")
    private Character sexPac;
    @Size(max = 6)
    @Column(name = "tit_pac")
    private String titPac;

    public ViewPedido() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getNroOrd() {
        return nroOrd;
    }

    public void setNroOrd(Long nroOrd) {
        this.nroOrd = nroOrd;
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

    public String getCodOri() {
        return codOri;
    }

    public void setCodOri(String codOri) {
        this.codOri = codOri;
    }

    public Long getCodPac() {
        return codPac;
    }

    public void setCodPac(Long codPac) {
        this.codPac = codPac;
    }

    public String getCodanaPrac() {
        return codanaPrac;
    }

    public void setCodanaPrac(String codanaPrac) {
        this.codanaPrac = codanaPrac;
    }

    public BigInteger getIdPra() {
        return idPra;
    }

    public void setIdPra(BigInteger idPra) {
        this.idPra = idPra;
    }

    public Character getTipoS() {
        return tipoS;
    }

    public void setTipoS(Character tipoS) {
        this.tipoS = tipoS;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getDocPac() {
        return docPac;
    }

    public void setDocPac(String docPac) {
        this.docPac = docPac;
    }

    public Character getSexPac() {
        return sexPac;
    }

    public void setSexPac(Character sexPac) {
        this.sexPac = sexPac;
    }

    public String getTitPac() {
        return titPac;
    }

    public void setTitPac(String titPac) {
        this.titPac = titPac;
    }
    
}

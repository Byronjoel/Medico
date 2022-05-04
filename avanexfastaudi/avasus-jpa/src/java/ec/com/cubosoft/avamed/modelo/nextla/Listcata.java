/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.nextla;

import java.io.Serializable;
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
@Table(name = "listcata")
@NamedQueries({
    @NamedQuery(name = "Listcata.findAll", query = "SELECT l FROM Listcata l")})
public class Listcata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "cod_ana")
    private String codAna;
    @Column(name = "des_ana")
    private String desAna;
    @Column(name = "id_pra")
    private Long idPra;
    @Column(name = "des_prac")
    private String desPrac;
    @Column(name = "COD_SEC")
    private String codSec;
    @Column(name = "tipo_s")
    private Character tipoS;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private String id;

    public Listcata() {
    }

    public Listcata(String id) {
        this.id = id;
    }

    public String getCodAna() {
        return codAna;
    }

    public void setCodAna(String codAna) {
        this.codAna = codAna;
    }

    public String getDesAna() {
        return desAna;
    }

    public void setDesAna(String desAna) {
        this.desAna = desAna;
    }

    public Long getIdPra() {
        return idPra;
    }

    public void setIdPra(Long idPra) {
        this.idPra = idPra;
    }

    public String getDesPrac() {
        return desPrac;
    }

    public void setDesPrac(String desPrac) {
        this.desPrac = desPrac;
    }

    public String getCodSec() {
        return codSec;
    }

    public void setCodSec(String codSec) {
        this.codSec = codSec;
    }

    public Character getTipoS() {
        return tipoS;
    }

    public void setTipoS(Character tipoS) {
        this.tipoS = tipoS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (!(object instanceof Listcata)) {
            return false;
        }
        Listcata other = (Listcata) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.nextla.Listcata[ id=" + id + " ]";
    }
    
}

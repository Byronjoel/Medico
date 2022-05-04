/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.publico;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "iso3166_1", catalog = "avasus", schema = "public")

public class Iso31661 implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "lock_reg")
    private short lockReg;
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "id_nnuu")
    private short idNnuu;
    @Column(name = "alfa2")
    private String alfa2;
    @Id
    @Basic(optional = false)
    @Column(name = "alfa3")
    private String alfa3;
    @Basic(optional = false)
    @Column(name = "pais")
    private String pais;
    @Column(name = "codigo5")
    private Integer codigo5;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "iso31661")
    private List<Iso31662> iso31662;
   
    public Iso31661() {
    }

    public Iso31661(String alfa3) {
        this.alfa3 = alfa3;
    }

    public Iso31661(String alfa3, short idNnuu, String pais) {
        this.alfa3 = alfa3;
        this.idNnuu = idNnuu;
        this.pais = pais;
    }

    public short getIdNnuu() {
        return idNnuu;
    }

    public void setIdNnuu(short idNnuu) {
        this.idNnuu = idNnuu;
    }

    public String getAlfa2() {
        return alfa2;
    }

    public void setAlfa2(String alfa2) {
        this.alfa2 = alfa2;
    }

    public String getAlfa3() {
        return alfa3;
    }

    public void setAlfa3(String alfa3) {
        this.alfa3 = alfa3;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Integer getCodigo5() {
        return codigo5;
    }

    public void setCodigo5(Integer codigo5) {
        this.codigo5 = codigo5;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (alfa3 != null ? alfa3.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iso31661)) {
            return false;
        }
        Iso31661 other = (Iso31661) object;
        if ((this.alfa3 == null && other.alfa3 != null) || (this.alfa3 != null && !this.alfa3.equals(other.alfa3))) {
            return false;
        }
        return true;
    }

    public List<Iso31662> getIso31662() {
        return iso31662;
    }

    public void setIso31662(List<Iso31662> iso31662) {
        this.iso31662 = iso31662;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.publico.Iso31661[ alfa3=" + alfa3 + " ]";
    }

    public short getLockReg() {
        return lockReg;
    }

    public void setLockReg(short lockReg) {
        this.lockReg = lockReg;
    }
    
}

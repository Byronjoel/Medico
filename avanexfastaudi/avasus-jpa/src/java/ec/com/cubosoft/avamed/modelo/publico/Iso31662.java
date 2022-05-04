/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.publico;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "iso3166_2",catalog = "avasus", schema = "public")

public class Iso31662 implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
       @Column(name = "iso2")
    private String iso2;
    @Basic(optional = false)
       @Column(name = "region1")
    private String region1;
    @Column(name = "id")
    private Float id;

    @ManyToOne
    @JoinColumn(name="iso1", referencedColumnName="alfa3")
    private Iso31661  iso31661;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idIso2")
    private Collection<Iso3166R2> iso3166R2;

    public Iso31661 getIso31661() {
        return iso31661;
    }

    public void setIso31661(Iso31661 iso31661) {
        this.iso31661 = iso31661;
    }

    public Iso31662() {
    }

    public Iso31662(String iso2) {
        this.iso2 = iso2;
    }

    public Iso31662(String iso2, String region1) {
        this.iso2 = iso2;
        this.region1 = region1;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public String getRegion1() {
        return region1;
    }

    public void setRegion1(String region1) {
        this.region1 = region1;
    }

    public Float getId() {
        return id;
    }

    public void setId(Float id) {
        this.id = id;
    }

    @XmlTransient
    public Collection<Iso3166R2> getIso3166R2() {
        return iso3166R2;
    }

    public void setIso3166R2Collection(Collection<Iso3166R2> iso3166R2) {
        this.iso3166R2 = iso3166R2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iso2 != null ? iso2.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Iso31662)) {
            return false;
        }
        Iso31662 other = (Iso31662) object;
        if ((this.iso2 == null && other.iso2 != null) || (this.iso2 != null && !this.iso2.equals(other.iso2))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.publico.Iso31662[ iso2=" + iso2 + " ]";
    }
    
}

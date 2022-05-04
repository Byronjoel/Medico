/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.medico;

import ec.com.cubosoft.avamed.modelo.ingreso.PracticaXOrden;
import ec.com.cubosoft.avamed.modelo.practica.NombreP;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Juan Pablo
 */
@Entity
@Table(name = "area", catalog = "avasus", schema = "medico")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "AREA_ID_GENERATOR", sequenceName = "MEDICO.AREA_ID_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AREA_ID_GENERATOR")
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fec_ini", insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecIni;
    @Basic(optional = false)
    @Column(name = "lock_reg")
    private short lockReg;
    @Column(name = "fec_upd")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecUpd;
    @Basic(optional = false)
    @Column(name = "first_user")
    private String firstUser;
    @Column(name = "last_user")
    private String lastUser;
   
    @Column(name = "per_area")
    private String perArea;
    @OneToMany(mappedBy = "area", cascade = CascadeType.ALL)
    private List<Nombre> nombreList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "area")
    private List<NombreP> practica;
    
    
//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "area")
//    private List<PracticaXOrden> practicaxorden;

    public Area() {
    }

    public Area(Integer id) {
        this.id = id;
    }

    public List<NombreP> getPractica() {
        return practica;
    }

    public void setPractica(List<NombreP> practica) {
        this.practica = practica;
    }

    public Area(Integer id, String descripcion, Date fecIni, short lockReg, String firstUser) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecIni = fecIni;
        this.lockReg = lockReg;
        this.firstUser = firstUser;
    }

    public String getPerArea() {
        return perArea;
    }

    public void setPerArea(String perArea) {
        this.perArea = perArea;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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


   
//    public List<PracticaXOrden> getPracticaxorden() {
//        return practicaxorden;
//    }
//
//    public void setPracticaxorden(List<PracticaXOrden> practicaxorden) {
//        this.practicaxorden = practicaxorden;
//    }

    public List<Nombre> getNombreList() {
        return nombreList;
    }

    public void setNombreList(List<Nombre> nombreList) {
        this.nombreList = nombreList;
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
        if (!(object instanceof Area)) {
            return false;
        }
        Area other = (Area) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.medico.Area[ id=" + id + " ]";
    }
}

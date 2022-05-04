/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author DESARROLLADOR
 */
@Entity
@Table(name = "farmacos", catalog = "avasus", schema = "core")
@NamedQueries({
    @NamedQuery(name = "Farmacos.findAll", query = "SELECT f FROM Farmacos f")})
public class Farmacos implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "lock_reg")
    private short lockReg;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idFarmaco")
    private Collection<Presentacion> presentacionCollection;
    private static final long serialVersionUID = 1L;
    @Size(max = 300)
    @Column(name = "descripcion")
    private String descripcion;

       @Size(max = 8)
    @Column(name = "tipo")
    private String tipo;
    @Size(max = 256)

    @Column(name = "nom_comercial")
    private String nomComercial;
    @Size(max = 256)
    @Column(name = "nom_generico")
    private String nomGenerico;
    @Size(max = 124)
    @Column(name = "contraindicaciones")
    private String contraindicaciones;
    @Size(max = 124)
    @Column(name = "presentacion")
    private String presentacion;
    @Size(max = 124)
    @Column(name = "laboratorio")
    private String laboratorio;
    @Size(max = 256)
    @Column(name = "concentracion")
    private String concentracion;
    @Size(max = 124)
    @Column(name = "grupo")
    private String grupo;
    @Size(max = 16)
    @Column(name = "codigo")
    private String codigo;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public Farmacos() {
    }

    public Farmacos(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Short getLockReg() {
        return lockReg;
    }

    public void setLockReg(Short lockReg) {
        this.lockReg = lockReg;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNomComercial() {
        return nomComercial;
    }

    public void setNomComercial(String nomComercial) {
        this.nomComercial = nomComercial;
    }

    public String getNomGenerico() {
        return nomGenerico;
    }

    public void setNomGenerico(String nomGenerico) {
        this.nomGenerico = nomGenerico;
    }

    public String getContraindicaciones() {
        return contraindicaciones;
    }

    public void setContraindicaciones(String contraindicaciones) {
        this.contraindicaciones = contraindicaciones;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(String concentracion) {
        this.concentracion = concentracion;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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
        if (!(object instanceof Farmacos)) {
            return false;
        }
        Farmacos other = (Farmacos) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.core.Farmacos[ id=" + id + " ]";
    }

    public Collection<Presentacion> getPresentacionCollection() {
        return presentacionCollection;
    }

    public void setPresentacionCollection(Collection<Presentacion> presentacionCollection) {
        this.presentacionCollection = presentacionCollection;
    }

    public void setLockReg(short lockReg) {
        this.lockReg = lockReg;
    }

}

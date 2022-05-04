/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.vistas;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

/**
 *
 * @author DESARROLLO
 */
@Entity
@Table(name = "vser_imagen", catalog = "avasus", schema = "public")
@NamedQueries({
    @NamedQuery(name = "VserImagen.findAll", query = "SELECT v FROM VserImagen v")})
public class VserImagen implements Serializable {

    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "empresa")
    private BigInteger empresa;

    @Column(name = "des")
    private String des;
    private static final long serialVersionUID = 1L;
    @Column(name = "pacid")
    private BigInteger pacid;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "ci")
    private String ci;
    @Column(name = "orden")
    private BigInteger orden;
    @Column(name = "practica")
    private Integer practica;
    @Column(name = "area")
    private Integer area;
    @Column(name = "cod")
    @Id
    private BigInteger cod;
    @Column(name = "imagen")
    private String imagen;
    @Column(name = "link")
    private String link;

    public VserImagen() {
    }

    public BigInteger getPacid() {
        return pacid;
    }

    public void setPacid(BigInteger pacid) {
        this.pacid = pacid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public BigInteger getOrden() {
        return orden;
    }

    public void setOrden(BigInteger orden) {
        this.orden = orden;
    }

    public Integer getPractica() {
        return practica;
    }

    public void setPractica(Integer practica) {
        this.practica = practica;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public BigInteger getCod() {
        return cod;
    }

    public void setCod(BigInteger cod) {
        this.cod = cod;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigInteger getEmpresa() {
        return empresa;
    }

    public void setEmpresa(BigInteger empresa) {
        this.empresa = empresa;
    }
    
}

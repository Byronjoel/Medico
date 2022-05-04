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
 * @author DESARROLLADOR
 */
@Entity
@Table(name = "resultado", catalog = "avasus", schema = "persona")
// wSQL.put("historia.paciente ?like", txtPacienteBusqueda.getValue().toUpperCase());
//                wSQL.put("orden.id ?=", 0);
//                wSQL.put("fecha ?>=", FecDesde.getValue());
//                wSQL.put("fecha ?<=", FecHasta.getValue());

@NamedQueries({
    @NamedQuery(name = "Resultado.findByOrden", query = "SELECT r FROM Resultado r WHERE r.idOrden = :idOrden")
    ,
    @NamedQuery(name = "Resultado.findByPaciente", query = "SELECT r FROM Resultado r WHERE r.idOrden = :idOrden and r.numId =:numId and r.fecha between :fecha1 and :fecha2")})
public class Resultado implements Serializable {

//    @Column(name = "his")
//    private Serializable his;
//    @Column(name = "ord")
//    private Serializable ord;
    @Column(name = "cod_ord")
    private Long codOrd;


   
   
    @Column(name = "cod_ref")
    private Integer codRef;

    @Column(name = "lk_img")
    private String lkImg;
    @Column(name = "nro_ord")
    private Long nroOrd;

    @Column(name = "per_impa")
    private Short perImpa;

    @Column(name = "id_medico")
    private Integer idMedico;
    @Column(name = "usuario")
    private String usuario;

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id")
    private BigInteger id;

    @Column(name = "linck")
    private String linck;

    @Column(name = "resultado")
    private String resultado;

    @Column(name = "estado")
    private String estado;

    @Column(name = "medico")
    private String medico;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "id_historia")
    private BigInteger idHistoria;
    @Column(name = "id_practica")
    private Integer idPractica;
    @Column(name = "id_orden")
    private BigInteger idOrden;
    @Column(name = "dx")
    private String dx;

    @Column(name = "empresa")
    private String empresa;
    @Column(name = "id_empresa")
    private BigInteger idEmpresa;

    @Column(name = "sts_admin")
    private String stsAdmin;

    @Column(name = "sts_tecnico")
    private String stsTecnico;
    @Column(name = "fec_ingreso")
    @Temporal(TemporalType.DATE)
    private Date fecIngreso;

    @Column(name = "nombres")
    private String nombres;

    @Column(name = "apellidos")
    private String apellidos;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "estado_civil")
    private String estadoCivil;

    @Column(name = "num_id")
    private String numId;

    @Column(name = "ocupacion")
    private String ocupacion;

    @Column(name = "sexo")
    private String sexo;

    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "orden_imp")
    private Integer ordenImp;
    @Column(name = "id_area")
    private Integer idArea;
    @Column(name = "srv_stnd")
    private Short srvStnd;

    public Resultado() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getMedico() {
        return medico;
    }

    public void setMedico(String medico) {
        this.medico = medico;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigInteger getIdHistoria() {
        return idHistoria;
    }

    public void setIdHistoria(BigInteger idHistoria) {
        this.idHistoria = idHistoria;
    }

    public Integer getIdPractica() {
        return idPractica;
    }

    public String getLinck() {
        return linck;
    }

    public void setLinck(String linck) {
        this.linck = linck;
    }

    public void setIdPractica(Integer idPractica) {
        this.idPractica = idPractica;
    }

    public BigInteger getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(BigInteger idOrden) {
        this.idOrden = idOrden;
    }

    public String getDx() {
        return dx;
    }

    public void setDx(String dx) {
        this.dx = dx;
    }

//    public Historia getHis() {
//        return his;
//    }
//
//    public void setHis(Historia his) {
//        this.his = his;
//    }
//
//    public Orden getOrd() {
//        return ord;
//    }
//
//    public void setOrd(Orden ord) {
//        this.ord = ord;
//    }
    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public BigInteger getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(BigInteger idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getStsAdmin() {
        return stsAdmin;
    }

    public void setStsAdmin(String stsAdmin) {
        this.stsAdmin = stsAdmin;
    }

    public String getStsTecnico() {
        return stsTecnico;
    }

    public void setStsTecnico(String stsTecnico) {
        this.stsTecnico = stsTecnico;
    }

    public Date getFecIngreso() {
        return fecIngreso;
    }

    public void setFecIngreso(Date fecIngreso) {
        this.fecIngreso = fecIngreso;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public void setEstadoCivil(String estadoCivil) {
        this.estadoCivil = estadoCivil;
    }

    public String getNumId() {
        return numId;
    }

    public void setNumId(String numId) {
        this.numId = numId;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

//    public Serializable getHis() {
//        return his;
//    }
//
//    public void setHis(Historia his) {
//        this.his = his;
//    }
//
//    public Serializable getOrd() {
//        return ord;
//    }
//
//    public void setOrd(Orden ord) {
//        this.ord = ord;
//    }
    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getOrdenImp() {
        return ordenImp;
    }

    public void setOrdenImp(Integer ordenImp) {
        this.ordenImp = ordenImp;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }

    public Short getSrvStnd() {
        return srvStnd;
    }

    public void setSrvStnd(Short srvStnd) {
        this.srvStnd = srvStnd;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

//    public Serializable getHis() {
//        return his;
//    }
//
//    public void setHis(Serializable his) {
//        this.his = his;
//    }
//
//    public Serializable getOrd() {
//        return ord;
//    }
//
//    public void setOrd(Serializable ord) {
//        this.ord = ord;
//    }
//    public String getOrdestado() {
//        return ordestado;
//    }
//
//    public void setOrdestado(String ordestado) {
//        this.ordestado = ordestado;
//    }
//
//    public Serializable getHis() {
//        return his;
//    }
//
//    public void setHis(Serializable his) {
//        this.his = his;
//    }
//
//    public Serializable getOrd() {
//        return ord;
//    }
//
//    public void setOrd(Serializable ord) {
//        this.ord = ord;
//    }
//    public Integer getCodRef() {
//        return codRef;
//    }
//
//    public void setCodRef(Integer codRef) {
//        this.codRef = codRef;
//    }
    public Short getPerImpa() {
        return perImpa;
    }

    public void setPerImpa(Short perImpa) {
        this.perImpa = perImpa;
    }

   

  
    public Integer getCodRef() {
        return codRef;
    }

    public void setCodRef(Integer codRef) {
        this.codRef = codRef;
    }

    public String getLkImg() {
        return lkImg;
    }

    public void setLkImg(String lkImg) {
        this.lkImg = lkImg;
    }

    

    public Long getNroOrd() {
        return nroOrd;
    }

    public void setNroOrd(Long nroOrd) {
        this.nroOrd = nroOrd;
    }

   
    public Long getCodOrd() {
        return codOrd;
    }

    public void setCodOrd(Long codOrd) {
        this.codOrd = codOrd;
    }

   
}

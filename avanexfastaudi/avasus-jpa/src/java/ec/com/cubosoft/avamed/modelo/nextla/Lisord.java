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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author JP3
 */
@Entity
@Table(name = "lisord")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lisord.findAll", query = "SELECT l FROM Lisord l")
    , @NamedQuery(name = "Lisord.findByNroOrd", query = "SELECT l FROM Lisord l WHERE l.nroOrd = :nroOrd")
    , @NamedQuery(name = "Lisord.findByFecOrd", query = "SELECT l FROM Lisord l WHERE l.fecOrd = :fecOrd")
    , @NamedQuery(name = "Lisord.findByFecEnt", query = "SELECT l FROM Lisord l WHERE l.fecEnt = :fecEnt")
    , @NamedQuery(name = "Lisord.findByCodPac", query = "SELECT l FROM Lisord l WHERE l.codPac = :codPac")
    , @NamedQuery(name = "Lisord.findByCama", query = "SELECT l FROM Lisord l WHERE l.cama = :cama")
    , @NamedQuery(name = "Lisord.findByPiso", query = "SELECT l FROM Lisord l WHERE l.piso = :piso")
    , @NamedQuery(name = "Lisord.findBySala", query = "SELECT l FROM Lisord l WHERE l.sala = :sala")
    , @NamedQuery(name = "Lisord.findByCodMed", query = "SELECT l FROM Lisord l WHERE l.codMed = :codMed")
    , @NamedQuery(name = "Lisord.findByCodCiu", query = "SELECT l FROM Lisord l WHERE l.codCiu = :codCiu")
    , @NamedQuery(name = "Lisord.findByCodPrv", query = "SELECT l FROM Lisord l WHERE l.codPrv = :codPrv")
    , @NamedQuery(name = "Lisord.findByCodDis", query = "SELECT l FROM Lisord l WHERE l.codDis = :codDis")
    , @NamedQuery(name = "Lisord.findByCodBar", query = "SELECT l FROM Lisord l WHERE l.codBar = :codBar")
    , @NamedQuery(name = "Lisord.findByCodSer", query = "SELECT l FROM Lisord l WHERE l.codSer = :codSer")
    , @NamedQuery(name = "Lisord.findByCodOri", query = "SELECT l FROM Lisord l WHERE l.codOri = :codOri")
    , @NamedQuery(name = "Lisord.findByCodUni", query = "SELECT l FROM Lisord l WHERE l.codUni = :codUni")
    , @NamedQuery(name = "Lisord.findByDirPac", query = "SELECT l FROM Lisord l WHERE l.dirPac = :dirPac")
    , @NamedQuery(name = "Lisord.findByCpoPac", query = "SELECT l FROM Lisord l WHERE l.cpoPac = :cpoPac")
    , @NamedQuery(name = "Lisord.findBySinPac", query = "SELECT l FROM Lisord l WHERE l.sinPac = :sinPac")
    , @NamedQuery(name = "Lisord.findByStsOrd", query = "SELECT l FROM Lisord l WHERE l.stsOrd = :stsOrd")
    , @NamedQuery(name = "Lisord.findByCodSeg", query = "SELECT l FROM Lisord l WHERE l.codSeg = :codSeg")
    , @NamedQuery(name = "Lisord.findByCodPln", query = "SELECT l FROM Lisord l WHERE l.codPln = :codPln")
    , @NamedQuery(name = "Lisord.findByStsAdm", query = "SELECT l FROM Lisord l WHERE l.stsAdm = :stsAdm")
    , @NamedQuery(name = "Lisord.findByPreingresado", query = "SELECT l FROM Lisord l WHERE l.preingresado = :preingresado")
    , @NamedQuery(name = "Lisord.findByUrgente", query = "SELECT l FROM Lisord l WHERE l.urgente = :urgente")
    , @NamedQuery(name = "Lisord.findByFactura", query = "SELECT l FROM Lisord l WHERE l.factura = :factura")
    , @NamedQuery(name = "Lisord.findByHora", query = "SELECT l FROM Lisord l WHERE l.hora = :hora")
    , @NamedQuery(name = "Lisord.findByExportacion", query = "SELECT l FROM Lisord l WHERE l.exportacion = :exportacion")
    , @NamedQuery(name = "Lisord.findByFechaFactura", query = "SELECT l FROM Lisord l WHERE l.fechaFactura = :fechaFactura")
    , @NamedQuery(name = "Lisord.findByExportSeguro", query = "SELECT l FROM Lisord l WHERE l.exportSeguro = :exportSeguro")
    , @NamedQuery(name = "Lisord.findByCodAut", query = "SELECT l FROM Lisord l WHERE l.codAut = :codAut")
    , @NamedQuery(name = "Lisord.findByReceta", query = "SELECT l FROM Lisord l WHERE l.receta = :receta")
    , @NamedQuery(name = "Lisord.findByFecReceta", query = "SELECT l FROM Lisord l WHERE l.fecReceta = :fecReceta")
    , @NamedQuery(name = "Lisord.findByNumCarnet", query = "SELECT l FROM Lisord l WHERE l.numCarnet = :numCarnet")
    , @NamedQuery(name = "Lisord.findByBonos", query = "SELECT l FROM Lisord l WHERE l.bonos = :bonos")
    , @NamedQuery(name = "Lisord.findByCodRaz", query = "SELECT l FROM Lisord l WHERE l.codRaz = :codRaz")
    , @NamedQuery(name = "Lisord.findByCodEve", query = "SELECT l FROM Lisord l WHERE l.codEve = :codEve")
    , @NamedQuery(name = "Lisord.findByCodEpi", query = "SELECT l FROM Lisord l WHERE l.codEpi = :codEpi")
    , @NamedQuery(name = "Lisord.findByCodSeg2", query = "SELECT l FROM Lisord l WHERE l.codSeg2 = :codSeg2")
    , @NamedQuery(name = "Lisord.findByCodPln2", query = "SELECT l FROM Lisord l WHERE l.codPln2 = :codPln2")
    , @NamedQuery(name = "Lisord.findByFecFac", query = "SELECT l FROM Lisord l WHERE l.fecFac = :fecFac")
    , @NamedQuery(name = "Lisord.findByCodDiag", query = "SELECT l FROM Lisord l WHERE l.codDiag = :codDiag")
    , @NamedQuery(name = "Lisord.findByRefExterna", query = "SELECT l FROM Lisord l WHERE l.refExterna = :refExterna")
    , @NamedQuery(name = "Lisord.findByNroEnv", query = "SELECT l FROM Lisord l WHERE l.nroEnv = :nroEnv")
    , @NamedQuery(name = "Lisord.findByCodSuc", query = "SELECT l FROM Lisord l WHERE l.codSuc = :codSuc")
    , @NamedQuery(name = "Lisord.findByFechaModif", query = "SELECT l FROM Lisord l WHERE l.fechaModif = :fechaModif")
    , @NamedQuery(name = "Lisord.findByFormaEnvio", query = "SELECT l FROM Lisord l WHERE l.formaEnvio = :formaEnvio")
    , @NamedQuery(name = "Lisord.findByNroRef", query = "SELECT l FROM Lisord l WHERE l.nroRef = :nroRef")
    , @NamedQuery(name = "Lisord.findByUsuarioAux", query = "SELECT l FROM Lisord l WHERE l.usuarioAux = :usuarioAux")
    , @NamedQuery(name = "Lisord.findByUsuario", query = "SELECT l FROM Lisord l WHERE l.usuario = :usuario")
    , @NamedQuery(name = "Lisord.findByTipoTurno", query = "SELECT l FROM Lisord l WHERE l.tipoTurno = :tipoTurno")
    , @NamedQuery(name = "Lisord.findByFecTurno", query = "SELECT l FROM Lisord l WHERE l.fecTurno = :fecTurno")
    , @NamedQuery(name = "Lisord.findByFecEntPar", query = "SELECT l FROM Lisord l WHERE l.fecEntPar = :fecEntPar")
    , @NamedQuery(name = "Lisord.findByExpOrd", query = "SELECT l FROM Lisord l WHERE l.expOrd = :expOrd")
    , @NamedQuery(name = "Lisord.findByLockOrd", query = "SELECT l FROM Lisord l WHERE l.lockOrd = :lockOrd")
    , @NamedQuery(name = "Lisord.findByValidado", query = "SELECT l FROM Lisord l WHERE l.validado = :validado")
    , @NamedQuery(name = "Lisord.findByLiberado", query = "SELECT l FROM Lisord l WHERE l.liberado = :liberado")
    , @NamedQuery(name = "Lisord.findByWeb", query = "SELECT l FROM Lisord l WHERE l.web = :web")
    , @NamedQuery(name = "Lisord.findByStsOrd2", query = "SELECT l FROM Lisord l WHERE l.stsOrd2 = :stsOrd2")})
public class Lisord implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "nro_ord")
    private Integer nroOrd;
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
    @Column(name = "cod_pac")
    private Integer codPac;
    @Size(max = 5)
    @Column(name = "cama")
    private String cama;
    @Size(max = 5)
    @Column(name = "piso")
    private String piso;
    @Size(max = 5)
    @Column(name = "sala")
    private String sala;
    @Size(max = 10)
    @Column(name = "cod_med")
    private String codMed;
    @Size(max = 5)
    @Column(name = "cod_ciu")
    private String codCiu;
    @Size(max = 5)
    @Column(name = "cod_prv")
    private String codPrv;
    @Size(max = 5)
    @Column(name = "cod_dis")
    private String codDis;
    @Size(max = 5)
    @Column(name = "cod_bar")
    private String codBar;
    @Size(max = 5)
    @Column(name = "cod_ser")
    private String codSer;
    @Size(max = 5)
    @Column(name = "cod_ori")
    private String codOri;
    @Size(max = 5)
    @Column(name = "cod_uni")
    private String codUni;
    @Size(max = 40)
    @Column(name = "dir_pac")
    private String dirPac;
    @Size(max = 10)
    @Column(name = "cpo_pac")
    private String cpoPac;
    @Size(max = 512)
    @Column(name = "sin_pac")
    private String sinPac;
    @Size(max = 2)
    @Column(name = "sts_ord")
    private String stsOrd;
    @Size(max = 5)
    @Column(name = "cod_seg")
    private String codSeg;
    @Size(max = 5)
    @Column(name = "cod_pln")
    private String codPln;
    @Size(max = 2)
    @Column(name = "sts_adm")
    private String stsAdm;
    @Column(name = "preingresado")
    private Character preingresado;
    @Column(name = "urgente")
    private Character urgente;
    @Column(name = "factura")
    private Long factura;
    @Column(name = "hora")
    @Temporal(TemporalType.TIMESTAMP)
    private Date hora;
    @Column(name = "exportacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exportacion;
    @Column(name = "fecha_factura")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaFactura;
    @Column(name = "export_seguro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exportSeguro;
    @Size(max = 20)
    @Column(name = "COD_AUT")
    private String codAut;
    @Column(name = "receta")
    private Character receta;
    @Column(name = "fec_receta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecReceta;
    @Size(max = 20)
    @Column(name = "num_carnet")
    private String numCarnet;
    @Size(max = 20)
    @Column(name = "bonos")
    private String bonos;
    @Size(max = 5)
    @Column(name = "COD_RAZ")
    private String codRaz;
    @Size(max = 5)
    @Column(name = "COD_EVE")
    private String codEve;
    @Size(max = 5)
    @Column(name = "COD_EPI")
    private String codEpi;
    @Size(max = 5)
    @Column(name = "cod_seg2")
    private String codSeg2;
    @Size(max = 5)
    @Column(name = "cod_pln2")
    private String codPln2;
    @Column(name = "fec_fac")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecFac;
    @Size(max = 10)
    @Column(name = "COD_DIAG")
    private String codDiag;
    @Size(max = 15)
    @Column(name = "ref_externa")
    private String refExterna;
    @Column(name = "nro_env")
    private Integer nroEnv;
    @Size(max = 5)
    @Column(name = "cod_suc")
    private String codSuc;
    @Column(name = "fecha_modif")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaModif;
    @Size(max = 2)
    @Column(name = "forma_envio")
    private String formaEnvio;
    @Column(name = "nro_ref")
    private Integer nroRef;
    @Size(max = 10)
    @Column(name = "usuario_aux")
    private String usuarioAux;
    @Size(max = 10)
    @Column(name = "usuario")
    private String usuario;
    @Size(max = 5)
    @Column(name = "tipo_turno")
    private String tipoTurno;
    @Column(name = "fec_turno")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecTurno;
    @Column(name = "fec_ent_par")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecEntPar;
    @Column(name = "exp_ord")
    private Short expOrd;
    @Column(name = "lock_ord")
    private Short lockOrd;
    @Column(name = "validado")
    private Short validado;
    @Column(name = "liberado")
    private Short liberado;
    @Column(name = "web")
    private Short web;
    @Size(max = 2)
    @Column(name = "sts_ord2")
    private String stsOrd2;

    public Lisord() {
    }

    public Lisord(Integer nroOrd) {
        this.nroOrd = nroOrd;
    }

    public Lisord(Integer nroOrd, Date fecOrd, Date fecEnt) {
        this.nroOrd = nroOrd;
        this.fecOrd = fecOrd;
        this.fecEnt = fecEnt;
    }

    public Integer getNroOrd() {
        return nroOrd;
    }

    public void setNroOrd(Integer nroOrd) {
        this.nroOrd = nroOrd;
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

    public Integer getCodPac() {
        return codPac;
    }

    public void setCodPac(Integer codPac) {
        this.codPac = codPac;
    }

    public String getCama() {
        return cama;
    }

    public void setCama(String cama) {
        this.cama = cama;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getSala() {
        return sala;
    }

    public void setSala(String sala) {
        this.sala = sala;
    }

    public String getCodMed() {
        return codMed;
    }

    public void setCodMed(String codMed) {
        this.codMed = codMed;
    }

    public String getCodCiu() {
        return codCiu;
    }

    public void setCodCiu(String codCiu) {
        this.codCiu = codCiu;
    }

    public String getCodPrv() {
        return codPrv;
    }

    public void setCodPrv(String codPrv) {
        this.codPrv = codPrv;
    }

    public String getCodDis() {
        return codDis;
    }

    public void setCodDis(String codDis) {
        this.codDis = codDis;
    }

    public String getCodBar() {
        return codBar;
    }

    public void setCodBar(String codBar) {
        this.codBar = codBar;
    }

    public String getCodSer() {
        return codSer;
    }

    public void setCodSer(String codSer) {
        this.codSer = codSer;
    }

    public String getCodOri() {
        return codOri;
    }

    public void setCodOri(String codOri) {
        this.codOri = codOri;
    }

    public String getCodUni() {
        return codUni;
    }

    public void setCodUni(String codUni) {
        this.codUni = codUni;
    }

    public String getDirPac() {
        return dirPac;
    }

    public void setDirPac(String dirPac) {
        this.dirPac = dirPac;
    }

    public String getCpoPac() {
        return cpoPac;
    }

    public void setCpoPac(String cpoPac) {
        this.cpoPac = cpoPac;
    }

    public String getSinPac() {
        return sinPac;
    }

    public void setSinPac(String sinPac) {
        this.sinPac = sinPac;
    }

    public String getStsOrd() {
        return stsOrd;
    }

    public void setStsOrd(String stsOrd) {
        this.stsOrd = stsOrd;
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

    public String getStsAdm() {
        return stsAdm;
    }

    public void setStsAdm(String stsAdm) {
        this.stsAdm = stsAdm;
    }

    public Character getPreingresado() {
        return preingresado;
    }

    public void setPreingresado(Character preingresado) {
        this.preingresado = preingresado;
    }

    public Character getUrgente() {
        return urgente;
    }

    public void setUrgente(Character urgente) {
        this.urgente = urgente;
    }

    public Long getFactura() {
        return factura;
    }

    public void setFactura(Long factura) {
        this.factura = factura;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public Date getExportacion() {
        return exportacion;
    }

    public void setExportacion(Date exportacion) {
        this.exportacion = exportacion;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Date getExportSeguro() {
        return exportSeguro;
    }

    public void setExportSeguro(Date exportSeguro) {
        this.exportSeguro = exportSeguro;
    }

    public String getCodAut() {
        return codAut;
    }

    public void setCodAut(String codAut) {
        this.codAut = codAut;
    }

    public Character getReceta() {
        return receta;
    }

    public void setReceta(Character receta) {
        this.receta = receta;
    }

    public Date getFecReceta() {
        return fecReceta;
    }

    public void setFecReceta(Date fecReceta) {
        this.fecReceta = fecReceta;
    }

    public String getNumCarnet() {
        return numCarnet;
    }

    public void setNumCarnet(String numCarnet) {
        this.numCarnet = numCarnet;
    }

    public String getBonos() {
        return bonos;
    }

    public void setBonos(String bonos) {
        this.bonos = bonos;
    }

    public String getCodRaz() {
        return codRaz;
    }

    public void setCodRaz(String codRaz) {
        this.codRaz = codRaz;
    }

    public String getCodEve() {
        return codEve;
    }

    public void setCodEve(String codEve) {
        this.codEve = codEve;
    }

    public String getCodEpi() {
        return codEpi;
    }

    public void setCodEpi(String codEpi) {
        this.codEpi = codEpi;
    }

    public String getCodSeg2() {
        return codSeg2;
    }

    public void setCodSeg2(String codSeg2) {
        this.codSeg2 = codSeg2;
    }

    public String getCodPln2() {
        return codPln2;
    }

    public void setCodPln2(String codPln2) {
        this.codPln2 = codPln2;
    }

    public Date getFecFac() {
        return fecFac;
    }

    public void setFecFac(Date fecFac) {
        this.fecFac = fecFac;
    }

    public String getCodDiag() {
        return codDiag;
    }

    public void setCodDiag(String codDiag) {
        this.codDiag = codDiag;
    }

    public String getRefExterna() {
        return refExterna;
    }

    public void setRefExterna(String refExterna) {
        this.refExterna = refExterna;
    }

    public Integer getNroEnv() {
        return nroEnv;
    }

    public void setNroEnv(Integer nroEnv) {
        this.nroEnv = nroEnv;
    }

    public String getCodSuc() {
        return codSuc;
    }

    public void setCodSuc(String codSuc) {
        this.codSuc = codSuc;
    }

    public Date getFechaModif() {
        return fechaModif;
    }

    public void setFechaModif(Date fechaModif) {
        this.fechaModif = fechaModif;
    }

    public String getFormaEnvio() {
        return formaEnvio;
    }

    public void setFormaEnvio(String formaEnvio) {
        this.formaEnvio = formaEnvio;
    }

    public Integer getNroRef() {
        return nroRef;
    }

    public void setNroRef(Integer nroRef) {
        this.nroRef = nroRef;
    }

    public String getUsuarioAux() {
        return usuarioAux;
    }

    public void setUsuarioAux(String usuarioAux) {
        this.usuarioAux = usuarioAux;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTipoTurno() {
        return tipoTurno;
    }

    public void setTipoTurno(String tipoTurno) {
        this.tipoTurno = tipoTurno;
    }

    public Date getFecTurno() {
        return fecTurno;
    }

    public void setFecTurno(Date fecTurno) {
        this.fecTurno = fecTurno;
    }

    public Date getFecEntPar() {
        return fecEntPar;
    }

    public void setFecEntPar(Date fecEntPar) {
        this.fecEntPar = fecEntPar;
    }

    public Short getExpOrd() {
        return expOrd;
    }

    public void setExpOrd(Short expOrd) {
        this.expOrd = expOrd;
    }

    public Short getLockOrd() {
        return lockOrd;
    }

    public void setLockOrd(Short lockOrd) {
        this.lockOrd = lockOrd;
    }

    public Short getValidado() {
        return validado;
    }

    public void setValidado(Short validado) {
        this.validado = validado;
    }

    public Short getLiberado() {
        return liberado;
    }

    public void setLiberado(Short liberado) {
        this.liberado = liberado;
    }

    public Short getWeb() {
        return web;
    }

    public void setWeb(Short web) {
        this.web = web;
    }

    public String getStsOrd2() {
        return stsOrd2;
    }

    public void setStsOrd2(String stsOrd2) {
        this.stsOrd2 = stsOrd2;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nroOrd != null ? nroOrd.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lisord)) {
            return false;
        }
        Lisord other = (Lisord) object;
        if ((this.nroOrd == null && other.nroOrd != null) || (this.nroOrd != null && !this.nroOrd.equals(other.nroOrd))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.nextla.Lisord[ nroOrd=" + nroOrd + " ]";
    }
    
}

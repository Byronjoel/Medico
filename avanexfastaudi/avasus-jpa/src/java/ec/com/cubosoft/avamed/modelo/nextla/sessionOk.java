/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.com.cubosoft.avamed.modelo.nextla;

import ec.com.cubosoft.avamed.modelo.core.CsGrupos;
import ec.com.cubosoft.avamed.modelo.core.CsUsuarios;
import ec.com.cubosoft.avamed.modelo.core.Perxuser;
import ec.com.cubosoft.avamed.modelo.ingreso.Orden;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import ec.com.cubosoft.avamed.modelo.persona.XmlAntecedentes;
import ec.com.cubosoft.avamed.modelo.practica.NombreP;
import ec.com.cubosoft.avamed.modelo.publico.Iso3166R2;
import java.io.Serializable;
import java.util.List;
import org.w3c.dom.Document;

/**
 *
 * @author DESARROLLO
 */
public class sessionOk implements Serializable {

    private String dirIp;
    private Iso3166R2 ciudad;
    private SUsuar usuarioN;
    private Perxuser perUsuNex;
    private CsUsuarios usuarioP;
    private CsGrupos grupoP;
    private List perUsuAva;
    private String pagina;
    private List perUsuNext;
    private Integer idPractica;
    private Boolean estado;
    private Document documento;
      private Document docAntecedentes;
    private Historia objHistoria;
    private Orden objOrden;
    private NombreP practica;
    private Integer tipo;
    private String usuario;
    private String grupo;

    private XmlAntecedentes objXmlAntecedentes;

    public String getDirIp() {
        return dirIp;
    }

    public Integer getTipo() {
        return tipo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Document getDocAntecedentes() {
        return docAntecedentes;
    }

    public void setDocAntecedentes(Document docAntecedentes) {
        this.docAntecedentes = docAntecedentes;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public void setDirIp(String dirIp) {
        this.dirIp = dirIp;
    }

    public Perxuser getPerUsuNex() {
        return perUsuNex;
    }

    public void setPerUsuNex(Perxuser perUsuNex) {
        this.perUsuNex = perUsuNex;
    }

    public Iso3166R2 getCiudad() {
        return ciudad;
    }

    public void setCiudad(Iso3166R2 ciudad) {
        this.ciudad = ciudad;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public SUsuar getUsuarioN() {
        return usuarioN;
    }

    public void setUsuarioN(SUsuar usuarioN) {
        this.usuarioN = usuarioN;
    }

    public CsUsuarios getUsuarioP() {
        return usuarioP;
    }

    public void setUsuarioP(CsUsuarios usuarioP) {
        this.usuarioP = usuarioP;
    }

    public CsGrupos getGrupoP() {
        return grupoP;
    }

    public void setGrupoP(CsGrupos grupoP) {
        this.grupoP = grupoP;
    }

    public List getPerUsuAva() {
        return perUsuAva;
    }

    public void setPerUsuAva(List perUsuAva) {
        this.perUsuAva = perUsuAva;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
    }

    public List getPerUsuNext() {
        return perUsuNext;
    }

    public void setPerUsuNext(List perUsuNext) {
        this.perUsuNext = perUsuNext;
    }

    public Historia getObjHistoria() {
        return objHistoria;
    }

    public void setObjHistoria(Historia objHistoria) {
        this.objHistoria = objHistoria;
    }

    public Orden getObjOrden() {
        return objOrden;
    }

    public void setObjOrden(Orden objOrden) {
        this.objOrden = objOrden;
    }

    public NombreP getPractica() {
        return practica;
    }

    public void setPractica(NombreP practica) {
        this.practica = practica;
    }

    public XmlAntecedentes getObjXmlAntecedentes() {
        return objXmlAntecedentes;
    }

    public void setObjXmlAntecedentes(XmlAntecedentes objXmlAntecedentes) {
        this.objXmlAntecedentes = objXmlAntecedentes;
    }

    public Integer getIdPractica() {
        return idPractica;
    }

    public void setIdPractica(Integer idPractica) {
        this.idPractica = idPractica;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Document getDocumento() {
        return documento;
    }

    public void setDocumento(Document documento) {
        this.documento = documento;
    }

}

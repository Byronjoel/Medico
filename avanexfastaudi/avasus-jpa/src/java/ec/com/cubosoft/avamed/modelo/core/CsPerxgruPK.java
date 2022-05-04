/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ec.com.cubosoft.avamed.modelo.core;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author pc
 */
@Embeddable
public class CsPerxgruPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "cod_gru")
    private String codGru;

    @Basic(optional = false)
    @Column(name = "cod_per")
    private String codPer;

    @Basic(optional = false)
    @Column(name = "id_app")
    private String idApp;

    public CsPerxgruPK() {
    }

    public CsPerxgruPK(String codGru, String codPer, String idApp) {
        this.codGru = codGru;
        this.codPer = codPer;
        this.idApp = idApp;
    }

    public String getCodGru() {
        return codGru;
    }

    public void setCodGru(String codGru) {
        this.codGru = codGru;
    }

    public String getCodPer() {
        return codPer;
    }

    public void setCodPer(String codPer) {
        this.codPer = codPer;
    }

    public String getIdApp() {
        return idApp;
    }

    public void setIdApp(String idApp) {
        this.idApp = idApp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codGru != null ? codGru.hashCode() : 0);
        hash += (codPer != null ? codPer.hashCode() : 0);
        hash += (idApp != null ? idApp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CsPerxgruPK)) {
            return false;
        }
        CsPerxgruPK other = (CsPerxgruPK) object;
        if ((this.codGru == null && other.codGru != null) || (this.codGru != null && !this.codGru.equals(other.codGru))) {
            return false;
        }
        if ((this.codPer == null && other.codPer != null) || (this.codPer != null && !this.codPer.equals(other.codPer))) {
            return false;
        }
        if ((this.idApp == null && other.idApp != null) || (this.idApp != null && !this.idApp.equals(other.idApp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ec.com.cubosoft.avamed.modelo.core.CsPerxgruPK[codGru=" + codGru + ", codPer=" + codPer + ", idApp=" + idApp + "]";
    }

}

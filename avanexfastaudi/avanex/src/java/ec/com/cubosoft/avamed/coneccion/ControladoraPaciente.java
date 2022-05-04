package ec.com.cubosoft.avamed.coneccion;

import ec.com.cubosoft.avamed.modelo.nextla.LispetAvanex;
import ec.com.cubosoft.avamed.modelo.nextla.sessionOk;
import ec.com.cubosoft.avamed.modelo.persona.Historia;
import ec.com.cubosoft.avamed.modelo.publico.Iso31661;
import ec.com.cubosoft.avamed.modelo.publico.Iso3166R2;
import ec.com.cubosoft.avamed.procesos.AdmiNegocio;
import java.util.*;
import javax.naming.NamingException;
import org.zkoss.zul.*;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;

/**
 * @author Juan Pablo Chavez
 * @version 1.0.1
 * @version 1.0.2
 *
 * @author Patricia Amoroso
 * @version 1.0
 */
public class ControladoraPaciente extends GenericForwardComposer {

    Listitem datoN;
    Listitem datoP;
    private Textbox idDireccion, idHistoria, idHistoriaN, idSexo, idEtniaR, idSubfijo, idTelefono, idPlural, idProfesion, idOcupacion, idPuesto, idTitulo, idApellidos, idNombres, idNumSer;
    private Combobox idBloqueo, idTipoS, idInstrucción, idCivil, idPaisNac, idCiudadNac;
//    idTipo,
    private Button ban;
    private Datebox idFecha;
    LispetAvanex objPaciente;
    Historia objHistoria;
    private sessionOk objsessiActica;

    public void onCreate$valor() {
        try {
            ProcesosSession admiSessionUsuario = new ProcesosSession();
            objsessiActica = (sessionOk) admiSessionUsuario.ObtenerAtributoSession(2, desktop.getSession());
            System.out.println("objsessiActica"+objsessiActica);
            loadCiudades();
            loadPais();
            objPaciente = datoN.getValue();
            objHistoria = datoP.getValue();
            guardarBtn.addEventListener("onClick", new EventListener() {
                @Override
                public void onEvent(Event e) throws Exception {
                    if ((!idCivil.getValue().isEmpty()) && (!idInstrucción.getValue().isEmpty()) && (!idProfesion.getValue().isEmpty()) && (!idOcupacion.getValue().isEmpty())) {
                        objPaciente = datoN.getValue();
                        guardar();
                        Events.sendEvent("onClick", ban, null);
                    }
                }// hasta aui
            });
            try {
                if ((objPaciente != null) && (objHistoria == null)) {
                    try {
                        System.out.println("Datos Historia" + new Date() + " / " + objPaciente.getCodPac() + " / ");
                        cargarPacietne(objPaciente);
                    } catch (Exception e) {
                        System.out.println("onCreate$WinHistoria()61" + e);
                    }

                } else {
                    try {
                        cargarHistoria(objHistoria);
                    } catch (Exception e) {
                        System.out.println("onCreate$WinHistoria()68" + e);
                    }

                }
            } catch (Exception e) {
                System.out.println("onCreate$WinHistoria()54" + e);
                throw new RuntimeException(e);
            }
            //cargarDatos();
        } catch (Exception e) {
            System.out.println("onCreate$WinHistoria() 409" + e);
            throw new RuntimeException(e);
        }
    }
    // public Historia objHistoria;
    private Button guardarBtn;

    public Historia getObjHistoria() {
        return objHistoria;
    }

    public void setObjHistoria(Historia objHistoria) {
        this.objHistoria = objHistoria;
    }

    //<editor-fold defaultstate="collapsed" desc="Carpetas y archivos fisico">
    private void guardar() {
        boolean nuev = false;
        String apMPAc;
        String nom2PAc;
        if (objPaciente.getApmPac() == null) {
            apMPAc = "";
        } else {
            apMPAc = objPaciente.getApmPac();
        }
        if (objPaciente.getNomPac2() == null) {
            nom2PAc = "";
        } else {
            nom2PAc = objPaciente.getNomPac2();
        }
        if (objHistoria == null) {
            try {
                objHistoria = new Historia();
                objHistoria.setApellidos(objPaciente.getApePac() + " " + apMPAc);
                objHistoria.setNombres(objPaciente.getNomPac() + " " + nom2PAc);
                objHistoria.setNumId(idNumSer.getValue());
                objHistoria.setIdNextlab(objPaciente.getCodPac());
                objHistoria.setFirstUser(objsessiActica.getUsuario());
                nuev = true;
            } catch (Exception e) {
                System.out.println("onCreate$WinHistoria() 118");
            }

        } else {
            try {
                objHistoria.setApellidos(objPaciente.getApePac() + " " + apMPAc);
                objHistoria.setNombres(objPaciente.getNomPac() + " " + nom2PAc);
                objHistoria.setNumId(idNumSer.getValue());
                objHistoria.setLastUser(objsessiActica.getUsuario());
            } catch (Exception e) {
                System.out.println("onCreate$WinHistoria() 128");
            }

            //si hay historia actualizar
        }
        if (!idTipoS.getValue().isEmpty()) {
            objHistoria.setTipoSangre(idTipoS.getValue());
        }
        objHistoria.setCiudadNace(idCiudadNac.getValue());
        objHistoria.setSexo(idSexo.getValue().substring(0, 1));
        if (objPaciente.getEmailPac() != null) {
            objHistoria.setEmail(objPaciente.getEmailPac());
        } else {
            objHistoria.setEmail("");
        }
        if (!idCivil.getValue().isEmpty()) {
            objHistoria.setEstadoCivil(idCivil.getValue().substring(0, 1));
        } else {
            objHistoria.setEstadoCivil("");
        }
        try {
            objHistoria.setTitulo(idTitulo.getValue());
        } catch (Exception e) {
            alert("Verifique el título");
            System.out.println("obj Historia 137");
        }
        try {
            objHistoria.setTipoId(objPaciente.getTipoDoc());
        } catch (Exception e) {
            alert("Verifique documento");
            System.out.println("obj Historia 142");
        }
        objHistoria.setEtnia(idEtniaR.getValue());
        objHistoria.setOcupacion(idOcupacion.getValue());
        objHistoria.setProfesion(idProfesion.getValue());
        objHistoria.setSufijo(idSubfijo.getValue());
        try {
            if (idPaisNac.getSelectedIndex() > 0) {
                Iso31661 pais = idPaisNac.getSelectedItem().getValue();
                objHistoria.setPaisNace(pais.getAlfa3());
            } else {
                objHistoria.setPaisNace(idPaisNac.getValue());
                //objHistoria.setPaisNace("ECU");
            }
        } catch (Exception e) {
            alert("Verifique País"+e);
        }
        String ins = idInstrucción.getValue();
        switch (ins) {
            case "PRIMARIA": {
                objHistoria.setInstruccion("1");
            }
            break;
            case "SECUNDARIA": {
                objHistoria.setInstruccion("2");
            }
            break;
            case "SUPERIOR": {
                objHistoria.setInstruccion("3");
            }
            break;
            case "CUARTO NIVEL": {
                objHistoria.setInstruccion("4");
            }
            break;
            case "OTRO": {
                objHistoria.setInstruccion("0");
            }
            break;
            default: {
                objHistoria.setInstruccion("0");
                break;
            }
        }
        objHistoria.setTelefono(idTelefono.getValue());
        objHistoria.setFechaNace(objPaciente.getFnaPac());
        objHistoria.setDireccion(idDireccion.getValue());
        AdmiNegocio obj = new AdmiNegocio();
        try {
            if (nuev) {
                try {
                    objHistoria = (Historia) obj.guardar(objHistoria);
                    if ((objHistoria.getId().toString() != null)) {
                        idHistoria.setValue(objHistoria.getId().toString());
                        datoP.setValue(objHistoria);
                        guardarBtn.setLabel("OK");
                        guardarBtn.setDisabled(true);
                        guardarBtn.setStyle(" border: 1px solid #1e8449  ;");
                        nuev = false;
                    }
                } catch (Exception e) {
                    alert("Verifique Datos para registrar");
                    System.out.println("obj Historia 198");
                }

            } else {
                if (objHistoria.getId() != null) {
                    objHistoria.setLastUser(objsessiActica.getUsuario());
                    try {
                        if (objHistoria.getFecIni() == null) {
                            objHistoria.setFecIni(new Date());
                            alert("Reporte");
                        }
                        if (obj.actualizar(objHistoria)) {
                            datoP.setValue(objHistoria);
                            guardarBtn.setLabel("OK");
                            guardarBtn.setDisabled(true);
                            guardarBtn.setStyle(" border: 1px solid #1e8449;");
                        } else {
                            alert("Verifique Datos para registrar");
                        }
                    } catch (Exception e) {
                        Messagebox.show("No se puede actualizar", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
                    }
                } else {
                    nuev = true;
                }
            }
            //    System.out.println("" + objHistoria.getApellidos() + " " + objHistoria.getId().toString());
        } catch (Exception e) {
            System.out.println(""+e);
        }
        if (objHistoria.getId() != null) {
//              alert("Verifique Datos para registrar historia");
            //  System.out.println("obj guarda crear orden" + objHistoria.getApellidos() + " " + objHistoria.getId().toString());
        } else {
            System.out.println("No se puede crear Historia Avasus" + objHistoria.getApellidos() + " " + objHistoria.getId().toString());
            Messagebox.show("No se puede crear Historia Avasus", "Advertencia", Messagebox.OK, Messagebox.INFORMATION);
        }
    }

    private void cargarPacietne(LispetAvanex obj) {
        idTitulo.setValue(obj.getTitPac());
        String apMPAc;
        String nom2PAc;
        if (obj.getApmPac() == null) {
            apMPAc = "";
        } else {
            apMPAc = obj.getApmPac();
        }
        if (obj.getNomPac2() == null) {
            nom2PAc = "";
        } else {
            nom2PAc = obj.getNomPac2();
        }
        idApellidos.setValue(obj.getApePac() + " " + apMPAc);
        idNombres.setValue(obj.getNomPac() + " " + nom2PAc);
        idTelefono.setValue(obj.getTelPac());
        Character s = obj.getSexPac();
        switch (s) {
            case 'F': {
                idSexo.setValue("FEMENINO");
            }
            break;
            case 'M': {
                idSexo.setValue("MASCULINO");
            }
            break;
            case 'I': {
                idSexo.setValue("INDETERMINADO");
            }
            break;
            case 'N': {
                idSexo.setValue("NO DECLARADO");
            }
            break;
        }
        idNumSer.setValue(obj.getDocPac());
        if (obj.getFnaPac() != null) {
            try {
                idFecha.setValue(obj.getFnaPac());
            } catch (Exception e) {
            }
        } else {
            alert("Fecha de Nacimiento no puede ser null");
        }
        try {
            cargarStylo();
        } catch (Exception e) {
        }

    }

    private void cargarHistoria(Historia obj) {
        idHistoria.setValue(obj.getId().toString());
        idHistoriaN.setValue(obj.getIdNextlab().toString());
        idBloqueo.setValue(Short.toString(obj.getLockReg()));
        Character s = objPaciente.getSexPac();
        switch (s) {
            case 'F': {
                idSexo.setValue("FEMENINO");
            }
            break;
            case 'M': {
                idSexo.setValue("MASCULINO");
            }
            break;
            case 'I': {
                idSexo.setValue("INDETERMINADO");
            }
            break;
            case 'N': {
                idSexo.setValue("NO DECLARADO");
            }
            break;
            default: {
                break;
            }
        }
        if (obj.getEtnia() != null) {
            idEtniaR.setValue(obj.getEtnia());
        }
        if (obj.getDireccion() != null) {
            idDireccion.setValue(obj.getDireccion());
        }
        if (obj.getSufijo() != null) {
            idSubfijo.setValue(obj.getSufijo());
        }
        try {
            idTelefono.setValue(objPaciente.getTelPac());
        } catch (Exception e) {
            idTelefono.setValue("");
        }
        if (objPaciente.getFnaPac() != null) {
            try {
                idFecha.setValue(objPaciente.getFnaPac());
            } catch (Exception e) {

            }
        } else {
            alert("Fecha de Nacimiento no puede ser null");

        }
        if (obj.getPaisNace() != null) {
            idPaisNac.setValue(obj.getPaisNace());
        }
        if (obj.getCiudadNace() != null) {
            idCiudadNac.setValue(obj.getCiudadNace());
        }

        if (obj.getPluralNac()) {
            idPlural.setValue("SI");
        } else {
            idPlural.setValue("NO");
        }
        if (obj.getInstruccion() != null) {
            String in = obj.getInstruccion();
            switch (in) {
                case "1": {
                    idInstrucción.setValue("PRIMARIA");
                }
                break;
                case "2": {
                    idInstrucción.setValue("SECUNDARIA");
                }
                break;
                case "3": {
                    idInstrucción.setValue("SUPERIOR");
                }
                break;
                case "4": {
                    idInstrucción.setValue("CUARTO NIVEL");
                }
                break;
                case "0": {
                    idInstrucción.setValue("OTRO");
                }
                break;
                default: {
                    idInstrucción.setValue("OTRO");
                    break;
                }
            }
        }
        if (obj.getProfesion() != null) {
            idProfesion.setValue(obj.getProfesion());
        }
        if (obj.getOcupacion() != null) {
            idOcupacion.setValue(obj.getOcupacion());
        }
        if (obj.getTitulo() != null) {
            idTitulo.setValue(obj.getTitulo());
        }
        idApellidos.setValue(obj.getApellidos());
        idNombres.setValue(obj.getNombres());
        String e = obj.getEstadoCivil();
        if (obj.getEstadoCivil() != null) {
            switch (e) {
                case "S": {
                    idCivil.setValue("Soltero");
                }
                break;
                case "C": {
                    idCivil.setValue("Casado");
                }
                break;
                case "D": {
                    idCivil.setValue("Divorciado");
                }
                break;
                case "V": {
                    idCivil.setValue("Viudo");
                }
                break;
                case "U": {
                    idCivil.setValue("Union Libre");
                }
                break;
            }
        }
//        if (!(obj.getTipoId() == null)) {
//            idTipo.setValue(obj.getTipoId());
//        }
        if (objPaciente.getDocPac() != null) {
            idNumSer.setValue(objPaciente.getDocPac());
        }
        //  idNumSer.setValue("");
        cargarStylo();

    }
// idHistoria, idHistoriaN, idSexo, idEtniaR, idSubfijo, idTelefono, idPlural, idProfesion, idOcupacion, idPuesto, idTitulo, idApellidos, idNombres, idNumSer;

    private void cargarStylo() {
        try {
            if (!idInstrucción.getValue().isEmpty()) {
                idInstrucción.setSclass("comb-dat-lle");
            } else {
                idInstrucción.setSclass("comb-dat-vac");
            }
            if (!idCivil.getValue().isEmpty()) {
                idCivil.setSclass("comb-dat-lle");
            } else {
                idCivil.setSclass("comb-dat-vac");
            }
            if (!idDireccion.getValue().isEmpty()) {
                idDireccion.setSclass("text-dat-lle");
            } else {
                idDireccion.setSclass("text-dat-vac");
            }
//        if (!idSexo.getValue().isEmpty()) {
//            idSexo.setSclass("comb-dat-lle");
//        } else {
//            idSexo.setSclass("comb-dat-vac");
//        }
//        if (!idEtniaR.getValue().isEmpty()) {
//            idEtniaR.setSclass("text-dat-lle");
//        } else {
//            idEtniaR.setSclass("text-dat-vac");
//        }
//        if (!idSubfijo.getValue().isEmpty()) {
//            idSubfijo.setSclass("text-dat-lle");
//        } else {
//            idSubfijo.setSclass("text-dat-vac");
//        }

            if (!idProfesion.getValue().isEmpty()) {
                idProfesion.setSclass("text-dat-lle");
            } else {
                idProfesion.setSclass("text-dat-vac");
            }
            if (!idOcupacion.getValue().isEmpty()) {
                idOcupacion.setSclass("text-dat-lle");
            } else {
                idOcupacion.setSclass("text-dat-vac");
            }
        } catch (Exception e) {
        }

//        if (!idPuesto.getValue().isEmpty()) {
//            idPuesto.setSclass("text-dat-lle");
//        } else {
//            idPuesto.setSclass("text-dat-vac");
//        }
    }

    private void loadPais() throws NamingException {
        AdmiNegocio admNeg = new AdmiNegocio();
        Map<String, Object> wSQL = new HashMap<String, Object>();
        wSQL.put("lockReg ?=", 0);
        List oSQL = new ArrayList<Object>();
        oSQL.add("pais");
        List ciudades = admNeg.getData(new Iso31661(), wSQL, null, oSQL);
        for (int i = 0; i < ciudades.size(); i++) {
            Iso31661 ciudad = (Iso31661) ciudades.get(i);
            Comboitem itemCiudad = new Comboitem(ciudad.getPais());
            itemCiudad.setValue(ciudad);
            itemCiudad.setParent(idPaisNac);
            String pa = ciudad.getPais();
            if (pa.equalsIgnoreCase("Ecuador")) {
                idPaisNac.setText(ciudad.getPais());
            }
        }
    }

    private void loadCiudades() throws NamingException {
        AdmiNegocio admNeg = new AdmiNegocio();
        Map<String, Object> wSQL = new HashMap<String, Object>();
        List oSQL = new ArrayList<Object>();
        oSQL.add("region2");
        List ciudades = admNeg.getData(new Iso3166R2(), wSQL, null, oSQL);
        for (Object object : ciudades) {
            Iso3166R2 ciudad = (Iso3166R2) object;
            Comboitem itemCiudad = new Comboitem(ciudad.getRegion2());
            itemCiudad.setId(ciudad.getId().toString());
            itemCiudad.setValue(ciudad);
            itemCiudad.setParent(idCiudadNac);
        }
    }
    // </editor-fold>
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.restricciones;

import data.AbstractDataSets;
import data.DataProject;
import data.DataProyectoListener;
import java.util.ArrayList;
import org.w3c.dom.Node;
import restricciones.ClasesNoCruzanRecreo.RClasesNoCruzanRecreo;
import restricciones.ProfesorMaximoHorasPorDia.RProfesorMaximoHorasPorDia;
import restricciones.clasesCondensadasParaProfesor.RClasesCondensadasParaProfesor;
import restricciones.noHuecosEntreMedias.RNoHuecosEntreMedias;
import restricciones.profesorCiertosDias.RProfesorCiertosDias;
import restricciones.profesorNoUbicuo.RProfesorNoUbicuo;
import restricciones.grupoNoUbicuo.RGrupoNoUbicuo;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class RestrictionsData extends AbstractDataSets {

    private static final long serialVersionUID = 27112013L;
    private final ArrayList<Restriccion> listaRestricciones;
    private ArrayList<Restriccion> restriccionesDisponibles;

    /**
     *
     * @param dataProyecto
     */
    public RestrictionsData(DataProject dataProyecto) {
        super(dataProyecto);
        this.listaRestricciones = new ArrayList<Restriccion>();
        this.restriccionesDisponibles=new ArrayList<Restriccion>();
    }

    /**
     *
     * @return
     */
    public ArrayList<Restriccion> getListaRestricciones() {
        return listaRestricciones;
    }

    /**
     *
     * @param nodeRestricciones
     */
    public void dataToDOM(Node nodeRestricciones) {
        for (Restriccion r : listaRestricciones) {
            r.writeRestriccion(nodeRestricciones);
        }
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean add(Restriccion e) {
        boolean resul = listaRestricciones.add(e);
        fireDataEvent(e, DataProyectoListener.ADD);
        setDirty(true);
        return resul;
    }

    /**
     * Borra todas las restricciones
     */
    public void clear() {
        this.listaRestricciones.clear();
        setDirty(true);
        fireDataEvent(null, DataProyectoListener.REMOVE);
    }

    /**
     *
     * @param r
     * @return
     */
    public boolean remove(Restriccion r) {
        boolean resul = listaRestricciones.remove(r);
        fireDataEvent(r, DataProyectoListener.REMOVE);
        setDirty(true);
        return resul;
    }
    /**
     *
     * @return
     */
    public ArrayList<Restriccion> getRestriccionesDisponibles() {
        return restriccionesDisponibles;
    }
}

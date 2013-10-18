/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.restricciones;

import data.AbstractDataSets;
import data.DataProyecto;
import data.DataProyectoListener;
import java.io.Serializable;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import restricciones.ClasesNoCruzanRecreo.RClasesNoCruzanRecreo;
import restricciones.ProfesorMaximoHorasPorDia.RProfesorMaximoHorasPorDia;
import restricciones.clasesCondensadasParaProfesor.RClasesCondensadasParaProfesor;
import restricciones.noHuecosEntreMedias.RNoHuecosEntreMedias;
import restricciones.profesorCiertosDias.RProfesorCiertosDias;
import restricciones.profesorNoUbicuo.RProfesorNoUbicuo;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DataRestricciones extends AbstractDataSets implements Serializable {

    private ArrayList<Restriccion> listaRestricciones;
    private ArrayList<Restriccion> restriccionesDisponibles;
    Document documentoXML;

    /**
     *
     * @param dataProyecto
     */
    public DataRestricciones(DataProyecto dataProyecto) {
        super(dataProyecto);
        this.listaRestricciones = new ArrayList<Restriccion>();
        populateRestriccionesDisponibles();
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
     *
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
     * Llena la lista de restricciones disponibles. Este método sólo debería
     * usarse una vez al crearse el objeto.
     */
    private void populateRestriccionesDisponibles() {
        restriccionesDisponibles = new ArrayList<Restriccion>();
        restriccionesDisponibles.add(new RNoHuecosEntreMedias());
        restriccionesDisponibles.add(new RProfesorNoUbicuo());
        restriccionesDisponibles.add(new RProfesorCiertosDias());
        restriccionesDisponibles.add(new RClasesCondensadasParaProfesor());
        restriccionesDisponibles.add(new RClasesNoCruzanRecreo());
        restriccionesDisponibles.add(new RProfesorMaximoHorasPorDia());
    }

    /**
     *
     * @return
     */
    public ArrayList<Restriccion> getRestriccionesDisponibles() {
        return restriccionesDisponibles;
    }
}

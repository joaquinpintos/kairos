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

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class RestrictionsData extends AbstractDataSets {

    private static final long serialVersionUID = 27112013L;
    private final ArrayList<Restriccion> listaRestricciones;
   

    /**
     *
     * @param dataProyecto
     */
    public RestrictionsData(DataProject dataProyecto) {
        super(dataProyecto);
        this.listaRestricciones = new ArrayList<Restriccion>();
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
        return resul;
    }

    /**
     * Borra todas las restricciones
     */
    public void clear() {
        this.listaRestricciones.clear();
    }

    /**
     *
     * @param r
     * @return
     */
    public boolean remove(Restriccion r) {
        boolean resul = listaRestricciones.remove(r);
        return resul;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class AbstractDataSets implements Serializable {

    private static final long serialVersionUID = 27112013L;
    private DataProject dataProyecto;
    private final ArrayList<DataProyectoListener> listeners;

    /**
     *
     * @param dataProyecto
     */
    public AbstractDataSets(DataProject dataProyecto) {
        this.dataProyecto = dataProyecto;
        listeners = new ArrayList<DataProyectoListener>();

    }


    /**
     *
     */
    public void clearListeners() {
        listeners.clear();
    }



    /**
     *
     * @return
     */
    public DataProject getDataProyecto() {
        return dataProyecto;
    }

    /**
     *
     * @param dataProyecto
     */
    public void setDataProyecto(DataProject dataProyecto) {
        this.dataProyecto = dataProyecto;
    }
}

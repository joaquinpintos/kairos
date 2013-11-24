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

    private static final long serialVersionUID = 1L;
    private DataProyecto dataProyecto;
    private final ArrayList<DataProyectoListener> listeners;

    /**
     *
     * @param dataProyecto
     */
    public AbstractDataSets(DataProyecto dataProyecto) {
        this.dataProyecto = dataProyecto;
        listeners = new ArrayList<DataProyectoListener>();

    }

    /**
     *
     * @param l
     */
    public void addListener(DataProyectoListener l) {
        listeners.add(l);
    }

    /**
     *
     * @param l
     */
    public void removeListener(DataProyectoListener l) {
        listeners.remove(l);
    }

    /**
     *
     */
    public void clearListeners() {
        listeners.clear();
    }

    /**
     *
     * @param data
     * @param type
     */
    public void fireDataEvent(Object data, int type) {
        for (DataProyectoListener l : listeners) {
            if (l != null) {
                l.dataEvent(data, type);
            }
        }
    }

    /**
     *
     * @param value
     */
    public void setDirty(boolean value) {
        try {
            dataProyecto.setDirty(value);
        } catch (NullPointerException e) {
        }
    }

    /**
     *
     * @return
     */
    public DataProyecto getDataProyecto() {
        return dataProyecto;
    }

    /**
     *
     * @param dataProyecto
     */
    public void setDataProyecto(DataProyecto dataProyecto) {
        this.dataProyecto = dataProyecto;
    }
}

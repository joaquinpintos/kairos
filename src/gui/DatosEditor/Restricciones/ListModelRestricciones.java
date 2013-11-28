/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Restricciones;

import data.DataKairos;
import data.restricciones.Restriccion;
import java.util.HashSet;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Modelo para representar en una lista las restricciones
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListModelRestricciones implements ListModel {

    private HashSet<ListDataListener> listeners;
    private final DataKairos dk;


    /**
     *
     * @param dk
     */
    public ListModelRestricciones(DataKairos dk) {
        this.dk=dk;
        listeners = new HashSet<ListDataListener>();
    }


    @Override
    public int getSize() {
        return dk.getDP().getRestrictionsData().getListaRestricciones().size();
    }


    @Override
    public Object getElementAt(int index) {
        return dk.getDP().getRestrictionsData().getListaRestricciones().get(index);
    }

    /**
     *
     */
    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }
    /**
     *
     * @param r
     * @param needReinicializarDatos
     */
    public void restrictionChanged(Restriccion r, Boolean needReinicializarDatos) {
        if (needReinicializarDatos) {
            r.inicializarDatos();
        }
        //Propago la señal a eventos de clase superior
        for (ListDataListener l : listeners) {
            l.contentsChanged(new ListDataEvent(this, 0, 0, 0));
        }
    }
  
}

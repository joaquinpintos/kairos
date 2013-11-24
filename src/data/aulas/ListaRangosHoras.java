/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.aulas;

import data.RangoHoras;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class ListaRangosHoras {

    private ArrayList<RangoHoras> rangos;
    private Aula parent;

    /**
     *
     */
    public ListaRangosHoras() {
        rangos = new ArrayList<RangoHoras>();
    }

    /**
     *
     * @param rangos
     */
    public ListaRangosHoras(ArrayList<RangoHoras> rangos) {
        this.rangos = rangos;
    }

    /**
     *
     * @return
     */
    public ArrayList<RangoHoras> getRangos() {
        return rangos;
    }

    /**
     *
     * @param rangos
     */
    public void setRangos(ArrayList<RangoHoras> rangos) {
        this.rangos = rangos;
    }

    /**
     *
     * @param rangoHoras
     */
    public void addRango(RangoHoras rangoHoras) {
        this.rangos.add(rangoHoras);
    }

    /**
     *
     * @param rangoHoras
     */
    public void removeRango(RangoHoras rangoHoras) {
        this.rangos.remove(rangoHoras);
    }

    /**
     *
     * @return
     */
    public Aula getParent() {
        return parent;
    }

    /**
     *
     * @param parent
     */
    public void setParent(Aula parent) {
        this.parent = parent;
    }
    /**
     *
     * @param index
     * @return
     */
    public RangoHoras get(int index)
    {
        return rangos.get(index);
    }
}

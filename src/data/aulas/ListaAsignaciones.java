/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.aulas;

import data.asignaturas.Tramo;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListaAsignaciones implements Serializable {

    private static final long serialVersionUID = 27112013L;
    ArrayList<Tramo> tramos;
    private final int index;
    private final Aula aula;
    private final boolean tarde;

    /**
     *
     * @param tarde
     * @param index
     * @param aula
     */
    public ListaAsignaciones(Boolean tarde, int index, Aula aula) {
        tramos = new ArrayList<Tramo>();
        this.tarde = tarde;
        this.index = index;
        this.aula = aula;
    }

    /**
     *
     * @return
     */
    public Aula getAula() {
        return aula;
    }

    /**
     *
     * @return
     */
    public ArrayList<Tramo> getHashToGroupContainers() {
        return tramos;
    }

    /**
     *
     * @return
     */
    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        String resul;
        if (esTarde()) {
            resul = "Tarde";
        } else {
            resul = "Mañana";
        }
        return resul;
    }

    /**
     *
     * @return
     */
    public int size() {
        return tramos.size();
    }

    /**
     *
     * @param index
     * @return
     */
    public Tramo get(int index) {
        return tramos.get(index);
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean add(Tramo e) {
        return tramos.add(e);
    }

    /**
     * Añade a esta lista de asignaciones TODOS los grupos con hash el indicado
     * por la variable cont.
     *
     * @return
     */
    public Boolean esTarde() {
        return tarde;
    }

    /**
     *
     * @return
     */
    public double getHorasOcupadas() {
        double resul = 0;
        for (Tramo c : tramos) {
            resul += c.getMinutos() / 60F;
        }
        return resul;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return tramos.isEmpty();
    }

}

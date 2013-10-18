/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import java.io.Serializable;

/**
 *
 * @author David Gutierrez
 */
public class Tramo implements Serializable {

    private int numeroClases;
    private int minutos;
    private GrupoTramos parent;

    /**
     *
     * @param numeroClases
     * @param minutos
     */
    public Tramo(int numeroClases, int minutos) {
        this.numeroClases = numeroClases;
        this.minutos = minutos;
    }

    /**
     *
     * @return
     */
    public int getNumeroClases() {
        return numeroClases;
    }

    /**
     *
     * @param numeroClases
     */
    public void setNumeroClases(int numeroClases) {
        this.numeroClases = numeroClases;
    }

    /**
     *
     * @return
     */
    public int getMinutos() {
        return minutos;
    }

    /**
     *
     * @param minutos
     */
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    /**
     *
     * @return
     */
    public double getTotalHoras() {
        return (double) minutos * numeroClases / 60.;
    }

    @Override
    public String toString() {
        String clase = "";
        if (numeroClases > 1) {
            clase = " clases de ";
        } else {
            clase = " clase de ";
        }
        return getTotalHoras() + "h";
    }

    /**
     *
     * @param parent
     */
    public void setParent(GrupoTramos parent) {
        this.parent = parent;
    }

    /**
     *
     * @param value
     */
    public void setDirty(boolean value) {
        try {
            parent.setDirty(value);
        } catch (NullPointerException e) {
        }

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.profesores.Profesor;
import java.io.Serializable;

/**
 *
 * @author David Gutierrez
 */
public class Tramo implements Serializable, Teachable, Comparable<Tramo> {

    private int minutos;
    private GrupoTramos parent;
    private Profesor profesor;

    /**
     *
     * @param numeroClases
     * @param minutos
     */
    public Tramo(int minutos) {
        this.minutos = minutos;
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

    @Override
    public String toString() {
        return "Tramo de " + getMinutos() + "m";
    }

    /**
     *
     * @param parent
     */
    public void setParent(GrupoTramos parent) {
        this.parent = parent;
    }

    public GrupoTramos getParent() {
        return parent;
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

    @Override
    public void setDocente(Profesor profesor) {
        this.profesor = profesor;
        if (profesor != null) {
            profesor.addDocencia(this);
        }
    }

    public Profesor getDocente() {
        return profesor;
    }

    @Override
    public void removeDocente() {
        Profesor p = profesor;
        profesor = null;
        if (p != null) {
            p.removeDocencia(this);
        }

    }

    public boolean isAsignado() {
        return (profesor != null);
    }

    @Override
    public int compareTo(Tramo o) {
        int resul = 0;
        if (this.minutos < o.getMinutos()) {
            resul = -1;
        }
        if (this.minutos > o.getMinutos()) {
            resul = 1;
        }
        return resul;
    }

}

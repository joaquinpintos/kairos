/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.DataProyectoListener;
import data.aulas.AulaMT;
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
    private AulaMT aulaMT;
    private boolean tarde;

    /**
     *
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
        fireDataEvent(this, DataProyectoListener.MODIFY);
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
        fireDataEvent(this, DataProyectoListener.MODIFY);
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

    @Override
    public void asignaAula(AulaMT aulaMT) {
        this.aulaMT = aulaMT;
        aulaMT.asignaTramo(this);
        parent.updateAsigAulaStatus();
        fireDataEvent(this, DataProyectoListener.MODIFY);
    }

    public void asignaAula(AulaMT aulaMT, boolean batch) {
        //if (this.aulaMT == null) {
        asignaAula(aulaMT);
        //}
    }

    @Override
    public void removeAula() {
        this.aulaMT = null;
        parent.updateAsigAulaStatus();
        fireDataEvent(this, DataProyectoListener.MODIFY);
    }

    public AulaMT getAulaMT() {
        return aulaMT;
    }

    public boolean tieneAula() {
        return (aulaMT != null);
    }

    public boolean isTarde() {
        return tarde;
    }

    public void fireDataEvent(Object obj, int type) {
        getParent().fireDataEvent(obj, type);
    }
}

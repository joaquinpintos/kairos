/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.DataProyectoListener;
import data.aulas.AulaMT;
import data.profesores.Profesor;
import java.awt.Color;
import java.io.Serializable;

/**
 *
 * @author David
 */
public class Tramo implements Serializable, Teachable, Comparable<Tramo> {

    private static final long serialVersionUID = 27112013L;
    private int minutos;
    private GrupoTramos parent;
    private Profesor profesor;
    private AulaMT aulaMT;
    private boolean tarde;
    private Color colorEnTablaDeHorarios;

    /**
     *
     * @param minutos
     */
    public Tramo(int minutos) {
        this.minutos = minutos;
        colorEnTablaDeHorarios = new Color(255, 255, 255);
    }

    /**
     *
     * @return
     */
    public int getMinutos() {
        return minutos;
    }

    public Color getColorEnTablaDeHorarios() {
        return colorEnTablaDeHorarios;
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

    /**
     *
     * @return
     */
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

    public void setDocente(Profesor p) {
        this.profesor = p;
    }

    

    /**
     *
     * @return
     */
    public Profesor getDocente() {
        return profesor;
    }

    /**
     *
     */
    public void removeDocente() {
        profesor = null;
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @param aulaMT
     */
    public void asignaAula(AulaMT aulaMT) {
        this.aulaMT = aulaMT;
        aulaMT.asignaTramo(this);
        parent.updateAsigAulaStatus();
        fireDataEvent(this, DataProyectoListener.MODIFY);
    }

    /**
     *
     * @param aulaMT
     * @param batch
     */
    public void asignaAula(AulaMT aulaMT, boolean batch) {
        //if (this.aulaMT == null) {
        asignaAula(aulaMT);
        //}
    }

    /**
     *
     */
    public void removeAula() {
        this.aulaMT = null;
        parent.updateAsigAulaStatus();
        fireDataEvent(this, DataProyectoListener.MODIFY);
    }

    /**
     *
     * @return
     */
    public AulaMT getAulaMT() {
        return aulaMT;
    }

    /**
     *
     * @return
     */
    public boolean tieneAula() {
        return (aulaMT != null);
    }

    public boolean algunoSinAula() {
        return (aulaMT == null);
    }

    /**
     *
     * @return
     */
    public boolean isTarde() {
        return tarde;
    }

    /**
     *
     * @param obj
     * @param type
     */
    public void fireDataEvent(Object obj, int type) {
        getParent().fireDataEvent(obj, type);
    }

    /**
     *
     * @param colorEnTablaDeHorarios
     */
    public void setColorEnTablaDeHorarios(Color colorEnTablaDeHorarios) {
        this.colorEnTablaDeHorarios = colorEnTablaDeHorarios;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

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
    private boolean algunoSinDocente;

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

    public void asignaAula(AulaMT aulaMT) {
        this.aulaMT = aulaMT;
    }

    /**
     *
     */
    public void removeAula() {
        this.aulaMT = null;
    }

    /**
     *
     * @return
     */
    public AulaMT getAulaMT() {
        return aulaMT;
    }

    public void setAulaMT(AulaMT aulaMT) {
        this.aulaMT = aulaMT;
    }

    /**
     *
     * @return
     */
    public boolean tieneAula() {
        return (aulaMT != null);
    }

    @Override
    public boolean isAlgunoSinAula() {
        return (aulaMT == null);
    }

    /**
     *
     * @return
     */
    public boolean isTarde() {
        return tarde;
    }

    @Override
    public boolean isAlgunoSinDocente() {
        return (profesor == null);
    }

    @Override
    public void setAlgunoSinDocente(boolean value) {
    }

    /**
     *
     * @param colorEnTablaDeHorarios
     */
    public void setColorEnTablaDeHorarios(Color colorEnTablaDeHorarios) {
        this.colorEnTablaDeHorarios = colorEnTablaDeHorarios;
    }

    @Override
    public void setAlgunoSinAula(boolean value) {
    }

}

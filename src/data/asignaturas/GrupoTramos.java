/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.aulas.AulaMT;
import data.profesores.Profesor;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class GrupoTramos implements Serializable,Teachable {

    private static final long serialVersionUID = 27112013L;
    private ArrayList<Tramo> vectorTramos;
    private Grupo parent;
    private boolean algunoSinAula;

    /**
     *
     * @param gr
     */
    public GrupoTramos(Grupo gr) {
        vectorTramos = new ArrayList<Tramo>();
        this.parent = gr;
        algunoSinAula = true;
    }

    /**
     *
     * @return
     */
    public ArrayList<Tramo> getTramos() {
        return vectorTramos;
    }

    /**
     *
     * @param vectorTramos
     */
    public void setArrayListTramos(ArrayList<Tramo> vectorTramos) {
        this.vectorTramos = vectorTramos;
    }

    /**
     *
     * @param tr
     */
    public void add(Tramo tr) {
        tr.setParent(this);
        vectorTramos.add(tr);
        updateAsigAulaStatus();
        setDirty(true);
    }

    /**
     *
     * @param tr
     */
    public void remove(Tramo tr) {
        tr.setParent(null);
        vectorTramos.remove(tr);
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public double getTotalHoras() {
        double suma = 0;
        for (Tramo tr : vectorTramos) {
            suma += tr.getMinutos() / 60F;
        }
        return suma;
    }

    @Override
    public String toString() {
        String salida = getTotalHoras() + "h";
        return salida;
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

    /**
     *
     * @return
     */
    public Grupo getParent() {
        return parent;
    }

    /**
     *
     * @param parent
     */
    public void setParent(Grupo parent) {
        this.parent = parent;
    }

    /**
     *
     * @param index
     * @return
     */
    public Tramo getNotAssigned(int index) {
        Tramo resul = null;
        int visibleIndex = -1;
        for (Tramo tr : vectorTramos) {
            if (tr.tieneAula()) {
                visibleIndex++;
                if (index == visibleIndex) {
                    resul = tr;
                    break;
                }
            }
        }

        return resul;
    }

    /**
     *
     * @return
     */
    public int sizeNotAssigned() {
        int visibleCount = 0;
        for (Tramo tr : vectorTramos) {
            if (tr.tieneAula()) {
                visibleCount++;
            }
        }
        return visibleCount;
    }

    /**
     *
     */
    public void updateAsigAulaStatus() {
        boolean resul = false;
        for (Tramo tr : vectorTramos) {
            if (!tr.tieneAula()) {
                resul = true;
                break;
            }
        }
        if (resul != algunoSinAula) {
            algunoSinAula = resul;
            //Actualizo hacia arriba
            getParent().updateAsigAulaStatus();
        }
    }

    /**
     *
     * @return
     */
    public boolean algunoSinAula() {
        return algunoSinAula;
    }

    public void setAlgunoSinAula(boolean value)
    {
        this.algunoSinAula=value;
    }
    public void setTieneAula(boolean value)
    {
        this.algunoSinAula=!value;
    }
    /**
     *
     * @param obj
     * @param type
     */
    public void fireDataEvent(Object obj, int type) {
        getParent().fireDataEvent(obj, type);
    }

    @Override
    public void setDocente(Profesor profesor) {
    }

    @Override
    public void removeDocente() {
    }

    @Override
    public void asignaAula(AulaMT aula) {
    }

    @Override
    public void removeAula() {
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

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
    private boolean algunoSinDocente;

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
        vectorTramos.add(tr);
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
     * @return
     */
    @Override
    public boolean isAlgunoSinAula() {
        return algunoSinAula;
    }

    @Override
    public void setAlgunoSinAula(boolean value)
    {
        this.algunoSinAula=value;
    }
    public void setTieneAula(boolean value)
    {
        this.algunoSinAula=!value;
    }
     @Override
    public boolean isAlgunoSinDocente() {
        return algunoSinDocente;
    }

    @Override
    public void setAlgunoSinDocente(boolean value) {
        algunoSinDocente=value;
    }
}

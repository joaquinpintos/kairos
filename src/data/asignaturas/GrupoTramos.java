/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class GrupoTramos  implements Serializable  {
    private ArrayList<Tramo> vectorTramos;
    private Grupo parent;

    /**
     *
     * @param gr
     */
    public GrupoTramos(Grupo gr) {
        vectorTramos=new ArrayList<Tramo>();
        this.parent=gr;
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
    public void add(Tramo tr)
    {
        tr.setParent(this);
        vectorTramos.add(tr);
        setDirty(true);
    }
    /**
     *
     * @param tr
     */
    public void remove(Tramo tr)
    {
        tr.setParent(null);
        vectorTramos.remove(tr);
        setDirty(true);
    }
    
    /**
     *
     * @return
     */
    public double getTotalHoras()
    {
        double suma=0;
        for (Tramo tr: vectorTramos)
        {
            suma+=tr.getMinutos()/60F;
        }
        return suma;
    }

    @Override
    public String toString() {
        String salida=getTotalHoras()+"h";
        return salida;
    }
     /**
     *
     * @param value
     */
    public void setDirty(boolean value)
    {
try {
            parent.setDirty(value);
        } catch (NullPointerException e) {
        }
        
    }

    public Grupo getParent() {
        return parent;
    }
    
    public void setParent(Grupo parent) {
        this.parent = parent;
    }

    
}

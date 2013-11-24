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
public class ListaGrupos implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<Grupo> grupos;

    /**
     *
     */
    public ListaGrupos() {
        this.grupos = new ArrayList<Grupo>();
    }

    /**
     *
     * @param VectorGrupos
     */
    public ListaGrupos(ArrayList<Grupo> VectorGrupos) {
        this.grupos = VectorGrupos;
    }

    /**
     *
     * @param gr
     */
    public void addGrupo(Grupo gr) {
        grupos.add(gr);
    }

    @Override
    public String toString() {
//        StringBuffer salida = new StringBuffer();
//        for (Grupo gr : grupos) {
//            salida.append("\n" + gr);
//        }
        return "ListaDeGrupos";
    }

    /**
     *
     * @param index
     * @return
     */
    public Grupo get(int index) {
        return grupos.get(index);
    }

    /**
     *
     * @param gr
     * @return
     */
    public int indexOf(Grupo gr) {
        return grupos.indexOf(gr);
    }

    int getChildCount() {
        return grupos.size();
    }

    /**
     *
     * @return
     */
    public ArrayList<Grupo> getGrupos() {
        return grupos;
    }

    /**
     *
     * @param VectorGrupos
     */
    public void setGrupos(ArrayList<Grupo> VectorGrupos) {
        this.grupos = VectorGrupos;
    }
}

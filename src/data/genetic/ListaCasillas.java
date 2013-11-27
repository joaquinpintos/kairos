/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.genetic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Guarda la lista de casillas disponibles para alojar los segmentos.
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListaCasillas implements Serializable {

    private static final long serialVersionUID = 27112013L;
    private ArrayList<Casilla> casillas;
    private final int minutosPorCasilla;

    /**
     *
     * @param casillas
     * @param minutosPorCasilla
     */
    public ListaCasillas(ArrayList<Casilla> casillas, int minutosPorCasilla) {
        this.casillas = casillas;
        this.minutosPorCasilla = minutosPorCasilla;
    }

    /**
     *
     * @param minutosPorCasilla
     */
    public ListaCasillas(int minutosPorCasilla) {
        casillas = new ArrayList<Casilla>();
        this.minutosPorCasilla = minutosPorCasilla;
    }

    /**
     *
     * @param n
     * @return
     */
    public Casilla get(int n) {
        return casillas.get(n);
    }

    /**
     *
     * @param cas
     */
    public void add(Casilla cas) {
        casillas.add(cas);
    }

    /**
     *
     * @return
     */
    public int size() {
        return casillas.size();
    }

    /**
     *
     * @return
     */
    public ArrayList<Casilla> getCasillas() {
        return casillas;
    }

    /**
     *
     * @param casillas
     */
    public void setCasillas(ArrayList<Casilla> casillas) {
        this.casillas = casillas;
    }

    /**
     *
     * @return
     */
    public int getMinutosTotales() {
        int suma = 0;
        for (Casilla c : casillas) {
            suma += c.getMinutos();
        }
        return suma;
    }

    /**
     *
     * @return
     */
    public int minutosPorCasilla() {
        return minutosPorCasilla;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.genetic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class ListaSegmentos  implements Serializable{

    private ArrayList<Segmento> segmentos;

    /**
     *
     * @param segmentos
     */
    public ListaSegmentos(ArrayList<Segmento> segmentos) {
        this.segmentos = segmentos;
    }

    /**
     *
     */
    public ListaSegmentos() {
        segmentos = new ArrayList<Segmento>();
    }

    /**
     *
     * @param m
     * @return
     */
    public Segmento get(int m) {
        return segmentos.get(m);
    }

    /**
     *
     * @param segmento
     */
    public void add(Segmento segmento) {
        segmentos.add(segmento);
    }

    /**
     *
     * @return
     */
    public int size() {
        return segmentos.size();
    }

    /**
     *
     * @return
     */
    public ArrayList<Segmento> getSegmentos() {
        return segmentos;
    }

    /**
     *
     * @param segmentos
     */
    public void setSegmentos(ArrayList<Segmento> segmentos) {
        this.segmentos = segmentos;
    }

    /**
     *
     * @return
     */
    public int getMinutosTotales() {
        int suma = 0;
        for (Segmento s : segmentos) {
            suma += s.duracion;
        }
        return suma;
    }

    /**
     *
     * @return
     */
    public int getNumeroHuecosLibres() {
        int cuenta = 0;
        for (Segmento s : segmentos) {
            if (s.isHuecoLibre()) cuenta++;
        }
        return cuenta;
    }
}

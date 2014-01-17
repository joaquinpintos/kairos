/*
 * Copyright (C) 2014 David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package data.genetic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListaSegmentos implements Serializable {

    private static final long serialVersionUID = 27112013L;
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
            if (s.isHuecoLibre()) {
                cuenta++;
            }
        }
        return cuenta;
    }
}

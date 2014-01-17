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

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
package data.aulas;

import data.TimeRange;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListaRangosHoras {

    private ArrayList<TimeRange> rangos;
    private Aula parent;

    /**
     *
     */
    public ListaRangosHoras() {
        rangos = new ArrayList<TimeRange>();
    }

    /**
     *
     * @param rangos
     */
    public ListaRangosHoras(ArrayList<TimeRange> rangos) {
        this.rangos = rangos;
    }

    /**
     *
     * @return
     */
    public ArrayList<TimeRange> getRangos() {
        return rangos;
    }

    /**
     *
     * @param rangos
     */
    public void setRangos(ArrayList<TimeRange> rangos) {
        this.rangos = rangos;
    }

    /**
     *
     * @param rangoHoras
     */
    public void addRango(TimeRange rangoHoras) {
        this.rangos.add(rangoHoras);
    }

    /**
     *
     * @param rangoHoras
     */
    public void removeRango(TimeRange rangoHoras) {
        this.rangos.remove(rangoHoras);
    }

    /**
     *
     * @return
     */
    public Aula getParent() {
        return parent;
    }

    /**
     *
     * @param parent
     */
    public void setParent(Aula parent) {
        this.parent = parent;
    }

    /**
     *
     * @param index
     * @return
     */
    public TimeRange get(int index) {
        return rangos.get(index);
    }
}

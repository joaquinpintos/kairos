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

import data.asignaturas.Tramo;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListaAsignaciones implements Serializable {

    private static final long serialVersionUID = 27112013L;
    ArrayList<Tramo> tramos;
    private final int index;
    private final Aula aula;
    private final boolean tarde;

    /**
     *
     * @param tarde
     * @param index
     * @param aula
     */
    public ListaAsignaciones(Boolean tarde, int index, Aula aula) {
        tramos = new ArrayList<Tramo>();
        this.tarde = tarde;
        this.index = index;
        this.aula = aula;
    }

    /**
     *
     * @return
     */
    public Aula getAula() {
        return aula;
    }

    public AulaMT getAulaMT() {
        return new AulaMT(aula, tarde);
    }

    /**
     *
     * @return
     */
    public ArrayList<Tramo> getHashToGroupContainers() {
        return tramos;
    }

    /**
     *
     * @return
     */
    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        String resul;
        if (esTarde()) {
            resul = "Tarde";
        } else {
            resul = "Mañana";
        }
        return resul;
    }

    /**
     *
     * @return
     */
    public int size() {
        return tramos.size();
    }

    /**
     *
     * @param index
     * @return
     */
    public Tramo get(int index) {
        return tramos.get(index);
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean add(Tramo e) {
        return tramos.add(e);
    }

    public boolean remove(Object o) {
        return tramos.remove(o);
    }

    /**
     * Añade a esta lista de asignaciones TODOS los grupos con hash el indicado
     * por la variable cont.
     *
     * @return
     */
    public Boolean esTarde() {
        return tarde;
    }

    /**
     *
     * @return
     */
    public double getHorasOcupadas() {
        double resul = 0;
        for (Tramo c : tramos) {
            resul += c.getMinutos() / 60F;
        }
        return resul;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return tramos.isEmpty();
    }

    public void clear() {
        tramos.clear();
    }

}

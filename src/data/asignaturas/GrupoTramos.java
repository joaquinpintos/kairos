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
package data.asignaturas;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class GrupoTramos implements Serializable, Teachable {

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
    public void setAlgunoSinAula(boolean value) {
        this.algunoSinAula = value;
    }

    public void setTieneAula(boolean value) {
        this.algunoSinAula = !value;
    }

    @Override
    public boolean isAlgunoSinDocente() {
        return algunoSinDocente;
    }

    @Override
    public void setAlgunoSinDocente(boolean value) {
        algunoSinDocente = value;
    }
}

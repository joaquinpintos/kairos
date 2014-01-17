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
public class ListaGrupos implements Serializable {

    private static final long serialVersionUID = 27112013L;
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

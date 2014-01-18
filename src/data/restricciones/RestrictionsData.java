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
package data.restricciones;

import data.AbstractDataSets;
import data.DataProject;
import java.util.ArrayList;
import org.w3c.dom.Node;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class RestrictionsData extends AbstractDataSets {

    private static final long serialVersionUID = 27112013L;
    private final ArrayList<Restriccion> listaRestricciones;

    /**
     *
     * @param dataProyecto
     */
    public RestrictionsData(DataProject dataProyecto) {
        super(dataProyecto);
        this.listaRestricciones = new ArrayList<Restriccion>();
    }

    /**
     *
     * @return
     */
    public ArrayList<Restriccion> getListaRestricciones() {
        return listaRestricciones;
    }

    /**
     *
     * @param nodeRestricciones
     */
    public void dataToDOM(Node nodeRestricciones) {
        for (Restriccion r : listaRestricciones) {
            r.writeRestriccion(nodeRestricciones);
        }
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean add(Restriccion e) {
        boolean resul = listaRestricciones.add(e);
        return resul;
    }

    /**
     * Borra todas las restricciones
     */
    public void clear() {
        this.listaRestricciones.clear();
    }

    /**
     *
     * @param r
     * @return
     */
    public boolean remove(Restriccion r) {
        boolean resul = listaRestricciones.remove(r);
        return resul;
    }
}

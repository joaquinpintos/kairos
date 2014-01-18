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
package data.profesores;

import data.asignaturas.Tramo;
import java.util.ArrayList;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListModelDocenciaAsignada implements ListModel {

    private ArrayList<Tramo> docencia;

    /**
     *
     * @param docencia
     */
    public ListModelDocenciaAsignada(ArrayList<Tramo> docencia) {
        this.docencia = docencia;
    }

    /**
     *
     */
    public ListModelDocenciaAsignada() {
        docencia = new ArrayList<Tramo>();
    }

    /**
     *
     * @param profesor
     */
    public ListModelDocenciaAsignada(Profesor profesor) {
        docencia = profesor.getDocencia();
    }

    @Override
    public int getSize() {
        return docencia.size();
    }

    @Override
    public Object getElementAt(int index) {
        return docencia.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
    }

    /**
     *
     * @return
     */
    public ArrayList<Tramo> getDocencia() {
        return docencia;
    }

    /**
     *
     * @param docencia
     */
    public void setDocencia(ArrayList<Tramo> docencia) {
        this.docencia = docencia;
    }

}

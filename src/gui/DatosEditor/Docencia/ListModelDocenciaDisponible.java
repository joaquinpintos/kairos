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
package gui.DatosEditor.Docencia;

import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.DataAsignaturas;
import data.asignaturas.DocenciaItem;
import data.asignaturas.Grupo;
import data.asignaturas.Tramo;
import java.util.Vector;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListModelDocenciaDisponible implements ListModel {

    private DataAsignaturas dataAsignaturas;
    Vector<DocenciaItem> docencia;

    /**
     *
     * @param dataAsignaturas
     */
    public ListModelDocenciaDisponible(DataAsignaturas dataAsignaturas) {
        super();
        this.dataAsignaturas = dataAsignaturas;
        docencia = new Vector<DocenciaItem>();
        populateData();
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

    private void populateData() {
        for (Carrera carrera : dataAsignaturas.getCarreras()) {
            for (Curso curso : carrera.getCursos()) {
                for (Asignatura asignatura : curso.getAsignaturas()) {
                    for (Grupo grupo : asignatura.getGrupos().getGrupos()) {
                        for (Tramo tr : grupo.getTramosGrupoCompleto().getTramos()) {
                            docencia.add(new DocenciaItem(tr));
                        }
                    }
                }
            }
        }
    }
}

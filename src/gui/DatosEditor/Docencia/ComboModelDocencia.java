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
import javax.swing.DefaultComboBoxModel;

/**
 * Modelo para un objeto combo de los grupos en los que se impartirá docencia
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ComboModelDocencia extends DefaultComboBoxModel {

    private DataAsignaturas dataAsignaturas;

    /**
     *
     * @param dataAsignaturas
     */
    public ComboModelDocencia(DataAsignaturas dataAsignaturas) {
        super();
        this.dataAsignaturas = dataAsignaturas;
        populateData();
    }

    private void populateData() {
        for (Carrera carrera : dataAsignaturas.getCarreras()) {
            for (Curso curso : carrera.getCursos()) {
                for (Asignatura asignatura : curso.getAsignaturas()) {
                    for (Grupo grupo : asignatura.getGrupos().getGrupos()) {
                        for (Tramo tr : grupo.getTramosGrupoCompleto().getTramos()) {
                            this.addElement(new DocenciaItem(tr));
                        }
                    }
                }
            }
        }
    }
}

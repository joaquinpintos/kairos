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
package data;

import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.Grupo;
import data.asignaturas.Tramo;
import data.profesores.DataProfesores;
import data.genetic.DatosPorAula;
import java.util.HashSet;

/**
 * Esta clase chequea los datos en dataProyecto y comprueba que son válidos para
 * ejecutar el algoritmo genético. Los métodos de chequeo devuelven "" si todo
 * va bien y mensaje de error en caso contrario.
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class CheckDataProyecto {

    DataProject dataProyecto;

    /**
     *
     * @param dataProyecto
     */
    public CheckDataProyecto(DataProject dataProyecto) {
        this.dataProyecto = dataProyecto;
    }

    /**
     *
     * @return
     */
    public HashSet<String> chequeaProfesores() {
        HashSet<String> resul = new HashSet<String>();
        DataProfesores dataProfesores = dataProyecto.getDataProfesores();
        if (dataProfesores.getDepartamentos().isEmpty()) {
            resul.add("No hay departamentos.");
        }
        if (dataProfesores.cuentaProfesores() == 0) {
            resul.add("No hay profesores.");
        }
        return resul;
    }

    /**
     *
     * @return
     */
    public HashSet<String> chequeaSiLosGruposCaben() {
        HashSet<String> resul = new HashSet<String>();
        for (DatosPorAula dp : dataProyecto.getMapDatosPorAula().values()) {
            if (dp.getListaCasillas().getMinutosTotales() < dp.getListaSegmentos().getMinutosTotales()) {
                resul.add("En el aula " + dp.getHashAula() + " no caben los segmentos (" + dp.getListaCasillas().getMinutosTotales() + " < " + dp.getListaSegmentos().getMinutosTotales());
            }
        }

        return resul;
    }

    /**
     *
     * @return
     */
    public HashSet<String> chequeaSiTodosLosTramosTienenAsignadaDocencia() {
        HashSet<String> resul = new HashSet<String>();
        mainLoop:
        for (Grupo gr : dataProyecto.getDataAsignaturas().getAllGrupos()) {
            for (Tramo tr : gr.getTramosGrupoCompleto().getTramos()) {
                if (!tr.isAsignado()) {
                    resul.add("Hay grupos sin profesor asignado");
                    break mainLoop;
                }
            }
        }
        return resul;
    }

    /**
     *
     * @return
     */
    public HashSet<String> chequeSiTodosLosTramosTieneUnaAulaAsignada() {
        HashSet<String> resul = new HashSet<String>();
        for (Carrera carr : dataProyecto.getDataAsignaturas().getCarreras()) {
            for (Curso curso : carr.getCursos()) {
                for (Asignatura asig : curso.getAsignaturas()) {
                    for (Grupo gr : asig.getGrupos().getGrupos()) {
                        if (gr.isAlgunoSinAula()) {
                            resul.add("Grupo " + gr.getNombreConCarrera() + " tiene tramos sin aula asignada.");
                        }

                    }
                }
            }

        }
        return resul;
    }
}

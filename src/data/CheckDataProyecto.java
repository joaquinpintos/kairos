/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

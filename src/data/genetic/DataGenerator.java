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
package data.genetic;

import data.DataProject;
import data.KairosTime;
import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.DataAsignaturas;
import data.asignaturas.Grupo;
import data.asignaturas.Tramo;
import data.aulas.Aula;
import data.aulas.DataAulas;
import data.TimeRange;
import data.profesores.DataProfesores;
import java.util.ArrayList;

/**
 * Esta clase construye el conjunto de segmentos y de casillas a partir de los
 * datos introducidos en el programa.
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DataGenerator {

//    ArrayList<Segmento> segmentos;
//    ArrayList<Casilla> casillas;
    private DataAsignaturas dataAsignaturas;
    private DataProfesores dataProfesores;
    private DataAulas dataAulas;
    int minutosCasilla;
    private final DataProject dataProject;

    /**
     *
     * @param minutosRango
     * @param dataProject
     */
    public DataGenerator(int minutosRango, DataProject dataProject) {
        this.minutosCasilla = minutosRango;
        this.dataProject = dataProject;
        this.dataAsignaturas = dataProject.getDataAsignaturas();
        this.dataProfesores = dataProject.getDataProfesores();
        this.dataAulas = dataProject.getDataAulas();
    }

    /**
     *
     * @return @throws Exception
     */
    public boolean generaDatos() throws Exception {
        boolean resul;
        dataProject.clearDatosPorAula();
//        calculaHashMapDeAsignacionesAulasAGrupos();
        calculaCasillas();
        calculaSegmentos();
        resul = rellenaSegmentosConHuecosLibres();
        return resul;
    }

    /**
     *
     */
    public void calculaSegmentos() {
        for (Carrera carrera : dataAsignaturas.getCarreras()) {
            for (Curso curso : carrera.getCursos()) {
                for (Asignatura asignatura : curso.getAsignaturas()) {
                    for (Grupo grupo : asignatura.getGrupos().getGrupos()) {
                        auxCalculaSegmentos(grupo);
                    }
                }
            }
        }

    }

    /**
     *
     * @param grupo
     */
    public void auxCalculaSegmentos(Grupo grupo) {
        ListaSegmentos ls = new ListaSegmentos();
        //Bucle para generar los segmentos de grupos completos.
        for (Tramo tr : grupo.getTramosGrupoCompleto().getTramos()) {
//            for (int n = 0; n < tr.getNumeroClases(); n++) {
            String hashAula = tr.getAulaMT().getHash();
            Segmento s = new Segmento(tr, tr.getMinutos() / dataProject.getMinutosPorCasilla(), dataProject.getMinutosPorCasilla());
            dataProject.getDatosPorAula(hashAula).addSegmento(s);
            ls.add(s);
//            }
//            dataProject.getMapSegmentosPorAsignaturaGrupo().put(grupo.getHashCarreraGrupoCurso(), ls);
        }

    }

    /**
     *
     * @throws Exception
     */
    public void calculaCasillas() throws Exception {

        ListaCasillas nuevaListaCasillas;
        Casilla cas = null;
        for (Aula aula : dataAulas.getAulas()) {

            //Aulas por la mañana hash nombreAula@M
            nuevaListaCasillas = new ListaCasillas(dataProject.getMinutosPorCasilla());
            String hashAulaMañana = aula.getHash(false);//Aulas por la mañana
            for (int dia : dataProject.getDiasSemanaLectivos()) {
                auxCalculaCasillas(aula, hashAulaMañana, dia, nuevaListaCasillas, dataProject.getMañana1());
                auxCalculaCasillas(aula, hashAulaMañana, dia, nuevaListaCasillas, dataProject.getMañana2());
            }
//            System.out.println("Aula "+hashAulaMañana+" con "+nuevaListaCasillas.size()+" casillas");
            dataProject.getDatosPorAula(hashAulaMañana).setListaCasillas(nuevaListaCasillas);
            dataProject.getDatosPorAula(hashAulaMañana).setHashAula(hashAulaMañana);

            //Aulas por la mañana hash nombreAula@T
            nuevaListaCasillas = new ListaCasillas(dataProject.getMinutosPorCasilla());
            String hashAulaTarde = aula.getHash(true);//Aulas por la tarde
            for (int dia : dataProject.getDiasSemanaLectivos()) {
                {

                    auxCalculaCasillas(aula, hashAulaTarde, dia, nuevaListaCasillas, dataProject.getTarde1());
                    auxCalculaCasillas(aula, hashAulaTarde, dia, nuevaListaCasillas, dataProject.getTarde2());
                }

            }
//            System.out.println("Aula "+hashAulaMañana+" con "+nuevaListaCasillas.size()+" casillas");
            dataProject.getDatosPorAula(hashAulaTarde).setListaCasillas(nuevaListaCasillas);
            dataProject.getDatosPorAula(hashAulaTarde).setHashAula(hashAulaTarde);
        }

    }

    private void auxCalculaCasillas(Aula aula, String hashAula, int dia, ListaCasillas nuevaListaCasillas, TimeRange rango) throws Exception {
        ArrayList<KairosTime> horas = rango.split(minutosCasilla);
        Casilla cas = null;
        for (KairosTime hora : horas) {
            cas = new Casilla(hashAula, hora, dia, minutosCasilla);
            cas.setAula(aula);
            nuevaListaCasillas.add(cas);
            cas.setFinaldeRango(false);
        }
        //Ultima casilla de cada rango la marco como final.
        if (cas != null) {
            cas.setFinaldeRango(true);
        }

    }

    /**
     *
     * @return
     */
    public DataAsignaturas getDataAsignaturas() {
        return dataAsignaturas;
    }

    /**
     *
     * @param dataAsignaturas
     */
    public void setDataAsignaturas(DataAsignaturas dataAsignaturas) {
        this.dataAsignaturas = dataAsignaturas;
    }

    /**
     *
     * @return
     */
    public DataProfesores getDataProfesores() {
        return dataProfesores;
    }

    /**
     *
     * @param dataProfesores
     */
    public void setDataProfesores(DataProfesores dataProfesores) {
        this.dataProfesores = dataProfesores;
    }

    /**
     *
     * @return
     */
    public DataAulas getDataAulas() {
        return dataAulas;
    }

    /**
     *
     * @param dataAulas
     */
    public void setDataAulas(DataAulas dataAulas) {
        this.dataAulas = dataAulas;
    }

    private boolean rellenaSegmentosConHuecosLibres() {
        //Calculo tiempo disponible en casillas y tiempo actual en segmentos
        //luego relleno los segmentos con huecos libres hasta completar la casilla.
        //cada segmento de hueco libre tiene la misma duración que una casilla.
        //Tengo que calcular huecos libres de mañana y tarde, y rellenarlos en consecuencia.
        boolean resul = true;

        for (DatosPorAula da : dataProject.getMapDatosPorAula().values()) {
            da.rellenaSegmentosConHuecosLibres();
        }

//        int numCasillas = dataProject.getListaCasillas().size();
//        int minutosCasillas = dataProject.getListaCasillas().get(0).getMinutos();
//
//
//        long duracionTotalSegmentosMañana = 0;
//        int cuantos = 0;
//        for (Segmento s : dataProject.getListaSegmentos().getSegmentos()) {
//            if (!s.isTarde()) {
//                duracionTotalSegmentosMañana += s.getDuracion();
//                cuantos++;
//            }
//        }
//        System.out.println("Duracion segmentos mañana: " + duracionTotalSegmentosMañana / 60 + "h, nº= " + cuantos);
//
//        //Calculo duracion casillas mañana
//
//        long duracionTotalCasillasMañana = dataProject.getListaCasillas().getNumCasillasMañana() * minutosCasillas;
//        int contador = 0;
//        while (duracionTotalSegmentosMañana < duracionTotalCasillasMañana) {
//            Segmento s = new Segmento(null, minutosCasillas, -1);
//            s.setHuecoLibre(true);
//            s.setTarde(false);
//            dataProject.getListaSegmentos().add(s);
//            duracionTotalSegmentosMañana += minutosCasillas;
//            contador++;
//        }
//        System.out.println("Duracion casillas mañana: " + duracionTotalCasillasMañana / 60 + "h");
//
//        System.out.println("Nueva duracion segmentos mañana: " + duracionTotalSegmentosMañana / 60 + "h, nº= " + cuantos + contador);
//        System.out.println("Huecos libres: " + contador);
//        if (duracionTotalCasillasMañana < duracionTotalSegmentosMañana) {
//            System.out.println("Error: horas de clase de mañana superior a horas-aula disponibles");
//            resul = false;
//        }
//
//        //Ahora repito lo mismo pero con la tarde
//        long duracionTotalSegmentosTarde = 0;
//        cuantos = 0;
//        for (Segmento s : dataProject.getListaSegmentos().getSegmentos()) {
//            if (s.isTarde()) {
//                duracionTotalSegmentosTarde += s.getDuracion();
//                cuantos++;
//            }
//        }
//        System.out.println("Duracion segmentos tarde: " + duracionTotalSegmentosTarde / 60 + "h, nº= " + cuantos);
//
//        //Calculo duracion casillas tarde
//
//        long duracionTotalCasillasTarde = dataProject.getListaCasillas().getNumCasillasTarde() * minutosCasillas;
//        contador = 0;
//        while (duracionTotalSegmentosTarde < duracionTotalCasillasTarde) {
//            Segmento s = new Segmento(null, minutosCasillas, -1);
//            s.setHuecoLibre(true);
//            s.setTarde(true);
//            dataProject.getListaSegmentos().add(s);
//            duracionTotalSegmentosTarde += minutosCasillas;
//            contador++;
//        }
//        System.out.println("Duracion casillas tarde: " + duracionTotalCasillasTarde / 60 + "h");
//        System.out.println("Nueva duracion segmentos tarde: " + duracionTotalSegmentosTarde / 60 + "h, nº= " + cuantos + contador);
//        System.out.println("Huecos libres: " + contador);
//        if (duracionTotalCasillasTarde < duracionTotalSegmentosTarde) {
//            System.out.println("Error: horas de clase de tarde superior a horas-aula disponibles");
//            resul = false;
//        }
//
//
//
//
        return resul;
    }

    /**
     *
     */
//    public void calculaHashMapDeAsignacionesAulasAGrupos() {
//        HashMap<String, String> mapa = dataProject.getDataAulas().getMapGruposCompletosToAulas();
//        mapa.clear();
//        for (Aula aula:dataProject.getDataAulas().getAulas())
//        {
//            mapa.putAll(aula.getMapAsignacionesAulasAGrupos());
//        }
//    }
}

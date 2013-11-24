/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.genetic;

import data.DataProyecto;
import data.Hora;
import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.DataAsignaturas;
import data.asignaturas.Grupo;
import data.asignaturas.Tramo;
import data.aulas.Aula;
import data.aulas.DataAulas;
import data.RangoHoras;
import data.profesores.DataProfesores;
import java.util.ArrayList;

/**
 * Esta clase construye el conjunto de segmentos y de casillas a partir de los
 * datos introducidos en el programa.
 *
 * @author david
 */
public class DataGenerator {

//    ArrayList<Segmento> segmentos;
//    ArrayList<Casilla> casillas;
    private DataAsignaturas dataAsignaturas;
    private DataProfesores dataProfesores;
    private DataAulas dataAulas;
    int minutosCasilla;
    private final DataProyecto dataProyecto;

    /**
     *
     * @param minutosRango
     * @param dataProyecto
     */
    public DataGenerator(int minutosRango, DataProyecto dataProyecto) {
        this.minutosCasilla = minutosRango;
        this.dataProyecto = dataProyecto;
        this.dataAsignaturas = dataProyecto.getDataAsignaturas();
        this.dataProfesores = dataProyecto.getDataProfesores();
        this.dataAulas = dataProyecto.getDataAulas();
    }

    /**
     *
     * @return @throws Exception
     */
    public boolean generaDatos() throws Exception {
        boolean resul;
        dataProyecto.clearDatosPorAula();
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
            Segmento s = new Segmento(tr, tr.getMinutos() / dataProyecto.getMinutosPorCasilla(), dataProyecto.getMinutosPorCasilla());
            dataProyecto.getDatosPorAula(hashAula).addSegmento(s);
            ls.add(s);
//            }
//            dataProyecto.getMapSegmentosPorAsignaturaGrupo().put(grupo.getHashCarreraGrupoCurso(), ls);
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
            nuevaListaCasillas = new ListaCasillas(dataProyecto.getMinutosPorCasilla());
            String hashAulaMañana = aula.getHash(false);//Aulas por la mañana
            for (int dia : dataProyecto.getDiasSemanaLectivos()) {
                auxCalculaCasillas(aula, hashAulaMañana, dia, nuevaListaCasillas, dataProyecto.getMañana1());
                auxCalculaCasillas(aula, hashAulaMañana, dia, nuevaListaCasillas, dataProyecto.getMañana2());
            }
//            System.out.println("Aula "+hashAulaMañana+" con "+nuevaListaCasillas.size()+" casillas");
            dataProyecto.getDatosPorAula(hashAulaMañana).setListaCasillas(nuevaListaCasillas);
            dataProyecto.getDatosPorAula(hashAulaMañana).setHashAula(hashAulaMañana);

            //Aulas por la mañana hash nombreAula@T
            nuevaListaCasillas = new ListaCasillas(dataProyecto.getMinutosPorCasilla());
            String hashAulaTarde = aula.getHash(true);//Aulas por la tarde
            for (int dia : dataProyecto.getDiasSemanaLectivos()) {
                {

                    auxCalculaCasillas(aula, hashAulaTarde, dia, nuevaListaCasillas, dataProyecto.getTarde1());
                    auxCalculaCasillas(aula, hashAulaTarde, dia, nuevaListaCasillas, dataProyecto.getTarde2());
                }

            }
//            System.out.println("Aula "+hashAulaMañana+" con "+nuevaListaCasillas.size()+" casillas");
            dataProyecto.getDatosPorAula(hashAulaTarde).setListaCasillas(nuevaListaCasillas);
            dataProyecto.getDatosPorAula(hashAulaTarde).setHashAula(hashAulaTarde);
        }

    }

    private void auxCalculaCasillas(Aula aula, String hashAula, int dia, ListaCasillas nuevaListaCasillas, RangoHoras rango) throws Exception {
        ArrayList<Hora> horas = rango.split(minutosCasilla);
        Casilla cas = null;
        for (Hora hora : horas) {
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

        for (DatosPorAula da : dataProyecto.getMapDatosPorAula().values()) {
            da.rellenaSegmentosConHuecosLibres();
        }

//        int numCasillas = dataProyecto.getListaCasillas().size();
//        int minutosCasillas = dataProyecto.getListaCasillas().get(0).getMinutos();
//
//
//        long duracionTotalSegmentosMañana = 0;
//        int cuantos = 0;
//        for (Segmento s : dataProyecto.getListaSegmentos().getSegmentos()) {
//            if (!s.isTarde()) {
//                duracionTotalSegmentosMañana += s.getDuracion();
//                cuantos++;
//            }
//        }
//        System.out.println("Duracion segmentos mañana: " + duracionTotalSegmentosMañana / 60 + "h, nº= " + cuantos);
//
//        //Calculo duracion casillas mañana
//
//        long duracionTotalCasillasMañana = dataProyecto.getListaCasillas().getNumCasillasMañana() * minutosCasillas;
//        int contador = 0;
//        while (duracionTotalSegmentosMañana < duracionTotalCasillasMañana) {
//            Segmento s = new Segmento(null, minutosCasillas, -1);
//            s.setHuecoLibre(true);
//            s.setTarde(false);
//            dataProyecto.getListaSegmentos().add(s);
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
//        for (Segmento s : dataProyecto.getListaSegmentos().getSegmentos()) {
//            if (s.isTarde()) {
//                duracionTotalSegmentosTarde += s.getDuracion();
//                cuantos++;
//            }
//        }
//        System.out.println("Duracion segmentos tarde: " + duracionTotalSegmentosTarde / 60 + "h, nº= " + cuantos);
//
//        //Calculo duracion casillas tarde
//
//        long duracionTotalCasillasTarde = dataProyecto.getListaCasillas().getNumCasillasTarde() * minutosCasillas;
//        contador = 0;
//        while (duracionTotalSegmentosTarde < duracionTotalCasillasTarde) {
//            Segmento s = new Segmento(null, minutosCasillas, -1);
//            s.setHuecoLibre(true);
//            s.setTarde(true);
//            dataProyecto.getListaSegmentos().add(s);
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
//        HashMap<String, String> mapa = dataProyecto.getDataAulas().getMapGruposCompletosToAulas();
//        mapa.clear();
//        for (Aula aula:dataProyecto.getDataAulas().getAulas())
//        {
//            mapa.putAll(aula.getMapAsignacionesAulasAGrupos());
//        }
//    }
}

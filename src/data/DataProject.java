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
import data.horarios.Horario;
import data.asignaturas.DataAsignaturas;
import data.asignaturas.Grupo;
import data.asignaturas.Tramo;
import data.aulas.DataAsignacionAulas;
import data.aulas.Aula;
import data.aulas.DataAulas;
import data.profesores.DataProfesores;
import data.profesores.Departamento;
import data.profesores.Profesor;
import data.genetic.DatosPorAula;
import data.genetic.ListaSegmentos;
import data.genetic.PosibleSolucion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import data.restricciones.RestrictionsData;
import java.io.File;
import java.math.BigInteger;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DataProject implements Serializable {

    private static final long serialVersionUID = 27112013L;
    private File pathForPDF; //Ruta para guardar los PDF
    private final DataProfesores dataProfesores;
    private final DataAsignaturas dataAsignaturas;
    private final DataAulas dataAulas;
    private final AcademicCalendar academicCalendar;
    private final HashMap<String, DatosPorAula> mapDatosPorAula;
    private final DataAsignacionAulas dataAsignacionAulas;
    private HashMap<String, Profesor> mapProfesor;
    //Esta clase contiene un horario ya construido. Se usa después de hallar una solución
    private Horario horario;
    //  private ListaSegmentos listaSegmentos;
    //Datos generales sobre el proyecto
    private final ConfigProject configProyecto;

    //private MainWindow mainWindow;
    //Clase con los datos del calendario académico: Días lectivos, inicio/fin, etc.
    //Para cada aula representa por su hash nombre@mañana/tarde, asocia un objeto de 
    //datos por aula, con toda la información relativa al aula/genetic algorithm
    //Cada asignatura (sin importar grupo) representada por su hash, me da
    //la lista de segmentos que pertenecen a ella, divididos por grupos
    private final HashMap<String, ListaSegmentos> mapSegmentosPorAsignaturaGrupo;
    private final HashMap<Profesor, ArrayList<ListaSegmentos>> mapSegmentosImpartidosPorProfesor;
    private final RestrictionsData dataRestricciones;
    //Optimo encontrado, si hubiera alguno
    private PosibleSolucion optimo;
    private String tituloPaginasImprimir;
    //Los minutos que ocupa una casilla. Se calcula en función de los tramos de clase
    //por ejemplo, si todas las clases son de 60 minutos, una casilla son 60 minutos
    //Si hay clases de 90 y 60, las casillas son de 30=mcd(60,90)
    private int minutosPorCasilla;
    /**
     * Estado del proyecto: puede tomar valores dentro de MYCONSTANTS.
     */
    private int status;

    /**
     * Constructor por defecto
     */
    public DataProject() {

        this.dataProfesores = new DataProfesores(this);
        this.dataAsignaturas = new DataAsignaturas(this);
        this.dataAulas = new DataAulas(this);
        //this.listaSegmentos = new ListaSegmentos();
        horario = new Horario(null);
        mapDatosPorAula = new HashMap<String, DatosPorAula>();
        mapSegmentosPorAsignaturaGrupo = new HashMap<String, ListaSegmentos>();
        mapSegmentosImpartidosPorProfesor = new HashMap<Profesor, ArrayList<ListaSegmentos>>();
        academicCalendar = new AcademicCalendar();
        dataRestricciones = new RestrictionsData(this);
        dataAsignacionAulas = new DataAsignacionAulas(this);
        status = DataKairos.STATUS_PROJECT_NO_SOLUTION;
        configProyecto = new ConfigProject(this);
    }

    /**
     *
     * @return
     */
    public HashMap<Profesor, ArrayList<ListaSegmentos>> getMapSegmentosImpartidosPorProfesor() {
        return mapSegmentosImpartidosPorProfesor;
    }
    /**
     *
     */
    public ArrayList<String> hashProfesores;

    /**
     *
     * @return
     */
    /**
     *
     * @param nombreProyecto
     */
    /**
     *
     * @return
     */
    public Horario getHorario() {
        return horario;
    }

    /**
     *
     * @param horario
     */
    public void setHorario(Horario horario) {
        this.horario = horario;
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
     * @return
     */
    public DataAsignaturas getDataAsignaturas() {
        return dataAsignaturas;
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
     */
    public void reconstruyeHashMapProfesor() {
        mapProfesor = new HashMap<String, Profesor>();
        for (Departamento dep : dataProfesores.getDepartamentos()) {
            for (Profesor pro : dep.getProfesores()) {
                mapProfesor.put(pro.hash(), pro);
            }
        }
    }

    /**
     *
     * @return
     */
    public HashMap<String, Profesor> getMapProfesor() {
        return mapProfesor;
    }

//    public void setListaSegmentos(ListaSegmentos nuevaListaSegmentos) {
//        this.listaSegmentos = nuevaListaSegmentos;
//    }
//
//
//    public ListaSegmentos getListaSegmentos() {
//        return this.listaSegmentos;
//    }
    /**
     *
     */
    public void clear() {
        dataProfesores.clear();
        dataAulas.clear();
        dataAsignaturas.clear();
//        horario.getHorarios().clear();
        academicCalendar.clear();
        dataRestricciones.clear();//=new DataRestricciones(this);//.clear();
        dataAsignacionAulas.clear();
        horario.clear();
    }

    /**
     *
     * @return
     */
    public TimeRange getMañana1() {
        return this.academicCalendar.getMorning1();
    }

    /**
     *
     * @param mañana1
     */
    public void setMañana1(TimeRange mañana1) {
        this.academicCalendar.setMorning1(mañana1);
    }

    /**
     *
     * @return
     */
    public TimeRange getMañana2() {
        return this.academicCalendar.getMorning2();
    }

    /**
     *
     * @param mañana2
     */
    public void setMañana2(TimeRange mañana2) {
        this.academicCalendar.setMorning2(mañana2);
    }

    /**
     *
     * @return
     */
    public TimeRange getTarde1() {
        return this.academicCalendar.getEvening1();
    }

    /**
     *
     * @param tarde1
     */
    public void setTarde1(TimeRange tarde1) {
        this.academicCalendar.setEvening1(tarde1);
    }

    /**
     *
     * @return
     */
    public TimeRange getTarde2() {
        return this.academicCalendar.getEvening2();
    }

    /**
     *
     * @param tarde2
     */
    public void setTarde2(TimeRange tarde2) {
        this.academicCalendar.setEvening2(tarde2);
    }

    /**
     *
     */
    public void clearDatosPorAula() {
        mapDatosPorAula.clear();
    }

    /**
     *
     * @param hashAula
     * @return
     */
    public DatosPorAula getDatosPorAula(String hashAula) {
        if (!mapDatosPorAula.containsKey(hashAula)) {
            mapDatosPorAula.put(hashAula, new DatosPorAula(minutosPorCasilla));
        }

        return mapDatosPorAula.get(hashAula);
    }

    /**
     *
     * @return
     */
    public HashMap<String, DatosPorAula> getMapDatosPorAula() {
        return mapDatosPorAula;
    }

    /**
     *
     * @param pr
     * @return
     */
    public ArrayList<ListaSegmentos> getSegmentosProfesor(Profesor pr) {
        return mapSegmentosImpartidosPorProfesor.get(pr);
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getDiasSemanaLectivos() {
        return academicCalendar.getAcademicWeekDays();
    }

    /**
     *
     * @param diasSemanaLectivos
     */
    public void setDiasSemanaLectivos(ArrayList<Integer> diasSemanaLectivos) {
        this.academicCalendar.setAcademicWeekDays(diasSemanaLectivos);
    }

    /**
     *
     * @return
     */
    public AcademicCalendar getAcademicCalendar() {
        return academicCalendar;
    }

    /**
     *
     * @return
     */
    public RestrictionsData getRestrictionsData() {
        return dataRestricciones;
    }

    /**
     *
     * @param optimo
     */
    public void setOptimo(PosibleSolucion optimo) {
        this.optimo = optimo;
    }

    /**
     *
     * @return
     */
    public int getMinutosPorCasilla() {
        return minutosPorCasilla;
    }

    /**
     *
     * @return
     */
    public DataAsignacionAulas getAsignacionAulas() {
        return dataAsignacionAulas;
    }

    /**
     *
     * @return
     */
    public File getPathForPDF() {
        return pathForPDF;
    }

    /**
     *
     * @param pathForPDF
     */
    public void setPathForPDF(File pathForPDF) {
        this.pathForPDF = pathForPDF;
    }

    /**
     *
     * @return
     */
    public String getTituloPaginasImprimir() {
        return tituloPaginasImprimir;
    }

    /**
     *
     * @param tituloPaginasImprimir
     */
    public void setTituloPaginasImprimir(String tituloPaginasImprimir) {
        this.tituloPaginasImprimir = tituloPaginasImprimir;
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public int mcd(int a, int b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }

    /**
     *
     */
    public void calculaMinutosPorCasilla() {
        boolean first = true;
        minutosPorCasilla = 0;
        for (Grupo gr : dataAsignaturas.getAllGrupos()) {
            for (Tramo tr : gr.getTramosGrupoCompleto().getTramos()) {
                if (first) {
                    minutosPorCasilla = tr.getMinutos();
                    first = false;
                } else {
                    minutosPorCasilla = mcd(minutosPorCasilla, tr.getMinutos());
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     *
     * @param hashAula
     * @return
     */
    public Aula getAulaPorHash(String hashAula) {
        Aula resul = null;
        boolean tarde = hashAula.contains("@T");

        for (Aula aula : dataAulas.getAulas()) {
            if (aula.getHash(tarde).equals(hashAula)) {
                resul = aula;
                break;
            }
        }
        return resul;
    }

    /**
     *
     * @return
     */
    public ConfigProject getConfigProyecto() {
        return configProyecto;
    }

    /**
     * Vuelve a asignar colores para las asignaturas de manera que en un aula no
     * se repitan (a menos que se quede sin colores!)
     */
    public void recoloreaAsignaturas() {
//        DataAsignaturas da = dataAsignaturas;
        HashMap<Asignatura, Integer> aulasAsigToColores = new HashMap<Asignatura, Integer>();
        for (Carrera ca : dataAsignaturas.getCarreras()) {
            for (Curso cu : ca.getCursos()) {
                for (Asignatura asig : cu.getAsignaturas()) {
                    for (Grupo gr : asig.getGrupos().getGrupos()) {
                        for (Tramo tr : gr.getTramosGrupoCompleto().getTramos()) {
                            asignaColor(tr, aulasAsigToColores);

                        }
                    }
                }
            }
        }
    }

    private void asignaColor(Tramo tr, HashMap<Asignatura, Integer> colAsig) {
//        Aula aula = tr.getAulaMT().getAula();
        Asignatura asig = tr.getParent().getParent().getParent();
        if (!colAsig.containsKey(asig)) {
            colAsig.put(asig, colAsig.size() % MyConstants.COLORES_ASIGNATURAS.length);
        }
        int contaColor = colAsig.get(asig);
        tr.setColorEnTablaDeHorarios(MyConstants.COLORES_ASIGNATURAS[contaColor]);
//         contaColor++;
//        if (!(contaColor < MyConstants.coloresAsignaturas.length)) {
//            contaColor = 0;
//        }
        colAsig.put(asig, contaColor);

    }

}

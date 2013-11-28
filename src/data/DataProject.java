/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

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
    private final ConfigProyecto configProyecto;

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
    private boolean dirty;
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
        configProyecto = new ConfigProyecto(this);
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
        setDirty(true);
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
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public RangoHoras getMañana1() {
        return this.academicCalendar.getMañana1();
    }

    /**
     *
     * @param mañana1
     */
    public void setMañana1(RangoHoras mañana1) {
        this.academicCalendar.setMañana1(mañana1);
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public RangoHoras getMañana2() {
        return this.academicCalendar.getMañana2();
    }

    /**
     *
     * @param mañana2
     */
    public void setMañana2(RangoHoras mañana2) {
        this.academicCalendar.setMañana2(mañana2);
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public RangoHoras getTarde1() {
        return this.academicCalendar.getTarde1();
    }

    /**
     *
     * @param tarde1
     */
    public void setTarde1(RangoHoras tarde1) {
        this.academicCalendar.setTarde1(tarde1);
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public RangoHoras getTarde2() {
        return this.academicCalendar.getTarde2();
    }

    /**
     *
     * @param tarde2
     */
    public void setTarde2(RangoHoras tarde2) {
        this.academicCalendar.setTarde2(tarde2);
        setDirty(true);
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
        return academicCalendar.getDiasSemanaLectivos();
    }

    /**
     *
     * @param diasSemanaLectivos
     */
    public void setDiasSemanaLectivos(ArrayList<Integer> diasSemanaLectivos) {
        this.academicCalendar.setDiasSemanaLectivos(diasSemanaLectivos);
        setDirty(true);
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
        setDirty(true);
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
        setDirty(true);
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
        setDirty(true);
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
    public boolean isDirty() {
        return dirty;
    }

    /**
     *
     * @param dirty
     */
    public void setDirty(boolean dirty) {
//        if (dirty) System.out.println("SET DIRTY!!!!!");else
//            System.out.println("SET NO DIRTY!!!!!");

        this.dirty = dirty;
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
    public ConfigProyecto getConfigProyecto() {
        return configProyecto;
    }

}

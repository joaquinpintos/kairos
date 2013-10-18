/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import data.aulas.CarreraCursoGrupoContainer;
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
import data.restricciones.DataRestricciones;
import java.io.File;
import java.math.BigInteger;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DataProyecto implements Serializable {

    private File pathForPDF; //Ruta para guardar los PDF
    private DataProfesores dataProfesores;
    private DataAsignaturas dataAsignaturas;
    private DataAulas dataAulas;
    private CalendarioAcademico calendarioAcadémico;
    private HashMap<String, DatosPorAula> mapDatosPorAula;
    private DataAsignacionAulas dataAsignacionAulas;
    private HashMap<String, Profesor> mapProfesor;
    //Esta clase contiene un horario ya construido. Se usa después de hallar una solución
    private Horario horario;
    //  private ListaSegmentos listaSegmentos;
    //Datos generales sobre el proyecto
    private String nombreProyecto;
    //private MainWindow mainWindow;
    //Clase con los datos del calendario académico: Días lectivos, inicio/fin, etc.
    //Para cada aula representa por su hash nombre@mañana/tarde, asocia un objeto de 
    //datos por aula, con toda la información relativa al aula/genetic algorithm
    //Cada asignatura (sin importar grupo) representada por su hash, me da
    //la lista de segmentos que pertenecen a ella, divididos por grupos
    private HashMap<String, ListaSegmentos> mapSegmentosPorAsignaturaGrupo;
    private HashMap<Profesor, ArrayList<ListaSegmentos>> mapSegmentosImpartidosPorProfesor;
    private DataRestricciones dataRestricciones;
    //Optimo encontrado, si hubiera alguno
    private PosibleSolucion optimo;
    private String tituloPaginasImprimir;
    //Los minutos que ocupa una casilla. Se calcula en función de los tramos de clase
    //por ejemplo, si todas las clases son de 60 minutos, una casilla son 60 minutos
    //Si hay clases de 90 y 60, las casillas son de 30=mcd(60,90)
    private int minutosPorCasilla;
    private boolean dirty;

    /**
     * Constructor por defecto
     */
    public DataProyecto() {
        this.dataProfesores = new DataProfesores(this);
        this.dataAsignaturas = new DataAsignaturas(this);
        this.dataAulas = new DataAulas(this);
        //this.listaSegmentos = new ListaSegmentos();
        horario = new Horario(null);
        mapDatosPorAula = new HashMap<String, DatosPorAula>();
        mapSegmentosPorAsignaturaGrupo = new HashMap<String, ListaSegmentos>();
        mapSegmentosImpartidosPorProfesor = new HashMap<Profesor, ArrayList<ListaSegmentos>>();
        calendarioAcadémico = new CalendarioAcademico();
        dataRestricciones = new DataRestricciones(this);
        dataAsignacionAulas = new DataAsignacionAulas(this);
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
    public String getNombreProyecto() {
        return nombreProyecto;
    }

    /**
     *
     * @param nombreProyecto
     */
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
        setDirty(true);
    }

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
        calendarioAcadémico.clear();
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
        return this.calendarioAcadémico.getMañana1();
    }

    /**
     *
     * @param mañana1
     */
    public void setMañana1(RangoHoras mañana1) {
        this.calendarioAcadémico.setMañana1(mañana1);
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public RangoHoras getMañana2() {
        return this.calendarioAcadémico.getMañana2();
    }

    /**
     *
     * @param mañana2
     */
    public void setMañana2(RangoHoras mañana2) {
        this.calendarioAcadémico.setMañana2(mañana2);
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public RangoHoras getTarde1() {
        return this.calendarioAcadémico.getTarde1();
    }

    /**
     *
     * @param tarde1
     */
    public void setTarde1(RangoHoras tarde1) {
        this.calendarioAcadémico.setTarde1(tarde1);
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public RangoHoras getTarde2() {
        return this.calendarioAcadémico.getTarde2();
    }

    /**
     *
     * @param tarde2
     */
    public void setTarde2(RangoHoras tarde2) {
        this.calendarioAcadémico.setTarde2(tarde2);
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
     * @return
     */
    public HashMap<String, String> getMapGruposCompletosToAulas() {
        return dataAulas.getMapGruposCompletosToAulas();
    }

    /**
     *
     * @param hashGrupoCurso
     * @param hashAula
     */
    public void addGrupoCompletoToAula(String hashGrupoCurso, String hashAula) {
        dataAulas.addGrupoCompletoToAula(hashGrupoCurso, hashAula);
        dataAsignacionAulas.addHashToAula(hashAula, hashGrupoCurso);
        setDirty(true);
    }

//    public HashMap<String, ListaSegmentos> getMapSegmentosPorAsignaturaGrupo() {
//        return mapSegmentosPorAsignaturaGrupo;
//    }
//
//    public void setMapSegmentosPorAsignaturaGrupo(HashMap<String, ListaSegmentos> mapSegmentosPorAsignaturaGrupo) {
//        this.mapSegmentosPorAsignaturaGrupo = mapSegmentosPorAsignaturaGrupo;
//    }
//    public void actualizaSegmentosImpartidosPorProfesor() {
//        if (arrayGrupos == null) {
//            creaArrayGrupos();
//        }
//        //Relleno de nuevo la lista de segmentos que impartirá cada profesor
//        mapSegmentosImpartidosPorProfesor.clear();//Borro los datos que hubiera
//        for (Grupo gr : arrayGrupos) {
//            if (!mapSegmentosImpartidosPorProfesor.containsKey(gr.getProfesor())) {
//                mapSegmentosImpartidosPorProfesor.put(gr.getProfesor(), new ArrayList<ListaSegmentos>());
//            }
//            String hashGrupo = gr.getHashCarreraGrupoCurso();
//            ListaSegmentos ls = mapSegmentosPorAsignaturaGrupo.get(hashGrupo);//Segmentos pertenecientes a dicho grupo
//            mapSegmentosImpartidosPorProfesor.get(gr.getProfesor()).add(ls);
//        }
//
//
//    }
    /**
     *
     * @param pr
     * @return
     */
    public ArrayList<ListaSegmentos> getSegmentosProfesor(Profesor pr) {
        return mapSegmentosImpartidosPorProfesor.get(pr);
    }

//    private void creaHashProfesores() {
//        //Relleno un array con los hash de todos los profesores.
//        hashProfesores = new ArrayList<String>();
//        for (Departamento d : dataProfesores.getDepartamentos()) {
//            for (Profesor p : d.getProfesores()) {
//                hashProfesores.add(p.hash());
//            }
//        }
//    }

//    private void creaArrayGrupos() {
//        arrayGrupos = new ArrayList<Grupo>();
//        for (Carrera c : dataAsignaturas.getCarreras()) {
//            for (Curso cu : c.getCursos()) {
//                for (Asignatura asig : cu.getAsignaturas()) {
//                    for (Grupo g : asig.getGrupos().getGrupos()) {
//                        arrayGrupos.add(g);
//                    }
//                }
//
//            }
//        }
//    }
    /**
     *
     * @return
     */
    public ArrayList<Integer> getDiasSemanaLectivos() {
        return calendarioAcadémico.getDiasSemanaLectivos();
    }

    /**
     *
     * @param diasSemanaLectivos
     */
    public void setDiasSemanaLectivos(ArrayList<Integer> diasSemanaLectivos) {
        this.calendarioAcadémico.setDiasSemanaLectivos(diasSemanaLectivos);
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public CalendarioAcademico getCalendarioAcadémico() {
        return calendarioAcadémico;
    }

    /**
     *
     * @return
     */
    public DataRestricciones getDataRestricciones() {
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
     * @param con
     * @return
     */
    public boolean isCarreraGrupoCursoContainerAssociadoConAula(CarreraCursoGrupoContainer con) {
        Boolean resul = false;
        for (Aula aula : dataAulas.getAulas()) {
            if ((aula.getAsignacionesMañana().contieneContainer(con)) || (aula.getAsignacionesTarde().contieneContainer(con))) {
                resul = true;
            }
        }
        return resul;
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
        if (dirty) System.out.println("SET DIRTY!!!!!");else
            System.out.println("SET NO DIRTY!!!!!");
        
        this.dirty = dirty;
    }
}

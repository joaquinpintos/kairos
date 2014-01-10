package restricciones.profesorNoUbicuo;

import data.DataProject;
import data.asignaturas.Grupo;
import data.asignaturas.GrupoTramos;
import data.asignaturas.Tramo;
import data.profesores.Profesor;
import data.genetic.Asignacion;
import data.genetic.Casilla;
import data.genetic.DatosPorAula;
import data.genetic.ListaCasillas;
import data.genetic.ListaSegmentos;
import data.genetic.PosibleSolucion;
import data.genetic.Segmento;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import data.restricciones.Restriccion;
import java.util.HashSet;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class RProfesorNoUbicuo extends Restriccion {

    //profesor->lista de hashaulas, y cada hashaula->lista de [segmentos,duracion]
    //A cada profesor le asigna una lista de hashaulas donde imparte.
    //y a cada hashaula, una lista de [segmento,duracion] 
    HashMap<Profesor, HashMap<String, ArrayList<Integer[]>>> dataMañana;
    HashMap<Profesor, HashMap<String, ArrayList<Integer[]>>> dataTarde;
    private final HashSet<Profesor> profesoresConflictivos;
    long suma;

    /**
     *
     */
    public RProfesorNoUbicuo() {
        super(null);
        profesoresConflictivos = new HashSet<Profesor>();
    }

    /**
     *
     * @param dataProyecto
     */
    public RProfesorNoUbicuo(DataProject dataProyecto) {
        super(dataProyecto);
        profesoresConflictivos = new HashSet<Profesor>();
    }

    @Override
    public void inicializarDatos() {
        //Tengo que:
        //Determinar profesores "conflictivos":
        //Aquellos que den más de una clase en mañana o en tarde
        //Sacar de ahí las aulas conflictivas.
        //Hacer una lista de segmentos conflictivos para cada aula
        //Comparar las casillas de dichos segmentos a ver si coinciden
        //en día y hora.

        //Fase 1: Calculo de mapProfesorToAulas
        HashMap<Profesor, ArrayList<String>> mapProfesorToAulas;
        mapProfesorToAulas = new HashMap<Profesor, ArrayList<String>>();
        for (Profesor p : dataProyecto.getDataProfesores().getTodosProfesores()) {
            //hashaula->lista de segmentos
            ArrayList<String> dd = new ArrayList<String>();
            ArrayList<Tramo> docencia = p.getDocencia();
            for (Tramo d : docencia) {
                GrupoTramos a = d.getParent();
                Grupo b = a.getParent();
//                String hashGrupoCurso = b.getHashCarreraGrupoCurso();
                String hashAula = null;
                hashAula = d.getAulaMT().getHash();
                dd.add(hashAula);
                mapProfesorToAulas.put(p, dd);
            }
        }
        //Fase 1: Calculo de dataMañana y dataTarde

        //Tendré 0, 1 y 2 grupos conflictivos. Calculo y elimino profesores no conflictivos
        //Tengo que contar para cada profesor cuántos aulas tiene de mañana y de tarde
        dataMañana = new HashMap<Profesor, HashMap<String, ArrayList<Integer[]>>>();
        dataTarde = new HashMap<Profesor, HashMap<String, ArrayList<Integer[]>>>();
        profesoresConflictivos.clear();
        for (Profesor p : mapProfesorToAulas.keySet()) {
            HashMap<String, ArrayList<Integer[]>> aulaToSegmentosMañana = new HashMap<String, ArrayList<Integer[]>>();
            HashMap<String, ArrayList<Integer[]>> aulaToSegmentosTarde = new HashMap<String, ArrayList<Integer[]>>();

            for (String hashAula : mapProfesorToAulas.get(p)) {
                //TODO: No permitir que el nombre del aula contenga @
                if (hashAula.contains("@M")) {//es clase de mañana
                    aulaToSegmentosMañana.put(hashAula, new ArrayList<Integer[]>());
                }
                if (hashAula.contains("@T")) {//es clase de tarde
                    aulaToSegmentosTarde.put(hashAula, new ArrayList<Integer[]>());
                }
            }
            if (aulaToSegmentosMañana.size() > 1) {
                dataMañana.put(p, aulaToSegmentosMañana);
            }
            if (aulaToSegmentosTarde.size() > 1) {
                dataTarde.put(p, aulaToSegmentosTarde);
            }
        }

        //LLegados a este punto, tengo dos hashmaps con profesores
        //y asociado a cada uno un hashmap de hashaula->array de segmentos vacío
        //ahora vamos a llenar los array de segmentos
        rellenaConSegmentos(dataMañana);
        rellenaConSegmentos(dataTarde);
    }

    /**
     *
     * @param posibleSolucion
     * @return
     */
    @Override
    public long calculaPeso(PosibleSolucion posibleSolucion) {
        suma = getSuma();
        setPeso(0);
        double coef = 1.3;
        profesoresConflictivos.clear();
        sumaPesos(posibleSolucion, dataMañana, coef);
        sumaPesos(posibleSolucion, dataTarde, coef);
        return getPeso();
    }

    @Override
    public boolean lanzarDialogoDeConfiguracion(Object parent) {
        //No hago nada. Como mucho muestro una ventana de diálogo.
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String descripcion() {
        return "Los profesores no pueden estar en 2 clases a la vez";
    }

    /**
     *
     * @return
     */
    @Override
    public String descripcionCorta() {
        return "Los profesores no pueden estar en 2 clases a la vez";
    }

    /**
     *
     * @return
     */
    @Override
    public String mensajeDeAyuda() {
        return "<html>A no ser que se invente el profesor<br> con el don de la ubicuidad, es mejor<br> tener esta restricción siempre activa.</html>";
    }

    /**
     * Este es un método preparatorio de datos. Para cada hashaula calcula y
     * guarda la lista de segmentos que imparte el profesor dado. Lo que guardo
     * NO son los objetos segmento, sino los índices.
     *
     * @param dataTarde
     */
    private void rellenaConSegmentos(HashMap<Profesor, HashMap<String, ArrayList<Integer[]>>> dataTarde) {

        for (Profesor p : dataTarde.keySet()) {
            for (String hashAula : dataTarde.get(p).keySet()) {
                ArrayList<Integer[]> segmentosConflictivos = dataTarde.get(p).get(hashAula);
                ListaSegmentos ls = dataProyecto.getDatosPorAula(hashAula).getListaSegmentos();
                for (int n = 0; n < ls.getSegmentos().size(); n++) {
                    Segmento s = ls.getSegmentos().get(n);
                    if (s.getProfesor() == p) {
                        Integer[] dato = {n, s.getNumeroDeCasillasQueOcupa()};
                        segmentosConflictivos.add(dato);
                    }
                }
            }
        }
    }

    private void sumaPesos(PosibleSolucion posibleSolucion, HashMap<Profesor, HashMap<String, ArrayList<Integer[]>>> data, double coef) {
//Calculo la función peso para los datos de mañana o tarde
        for (Profesor p : data.keySet()) {
            ArrayList<Casilla> casillasConflictivas = new ArrayList<Casilla>();
            for (String hashAula : data.get(p).keySet()) {
                HashMap<String, DatosPorAula> a = dataProyecto.getMapDatosPorAula();
                DatosPorAula b = a.get(hashAula);
                ListaCasillas lc = b.getListaCasillas();
                ArrayList<Integer[]> segmentosConflictivos = data.get(p).get(hashAula);
                Asignacion asig = posibleSolucion.getAsignacion(hashAula);
                for (int index = 0; index < segmentosConflictivos.size(); index++) {
//                    System.out.println(segmentosConflictivos.get(index)+"<---------------------");
                    int n = segmentosConflictivos.get(index)[0];
                    int durN = segmentosConflictivos.get(index)[1];
                    int numCasilla = asig.enQueCasillaEstaSegmento(n);
                    for (int k = numCasilla; k < numCasilla + durN; k++) {
                        casillasConflictivas.add(lc.getCasillas().get(k));
                    }
                }
            }
            //Para el profesor p y turno mañana/tarde fijado, tengo
            //el arraylist con todas las casillas que está asignado
            //ahora voy a ver si hay alguna que se superponga.
            //Para ello las ordeno con un comparator que mire la hora de inicio
            //(aquí asumo que todas las casillas tienen igual duración)
            //y luego comparo cada una con la siguiente.
            Collections.sort(casillasConflictivas);

            //Ahora que están ordenadas
            for (int n = 0; n < casillasConflictivas.size() - 1; n++) {
                Casilla c1 = casillasConflictivas.get(n);
                Casilla c2 = casillasConflictivas.get(n + 1);
                if (c1.mismoInstante(c2)) {
                    sumaPeso(suma);
                    suma *= coef;
                    if (marcaCasillasConflictivas) {
                        //Para marcarla, necesito saber el inicio real del segmento
                        Asignacion asig = posibleSolucion.getAsignacion(c1.getHashAula());
                        ListaCasillas lc = dataProyecto.getDatosPorAula(c1.getHashAula()).getListaCasillas();
                        int nc1 = lc.getCasillas().indexOf(c1);
                        int ns1 = asig.getQueSegmentoHayEnCasilla(nc1);
                        int c1real = asig.enQueCasillaEstaSegmento(ns1);

                        asig = posibleSolucion.getAsignacion(c2.getHashAula());
                        lc = dataProyecto.getDatosPorAula(c2.getHashAula()).getListaCasillas();
                        int nc2 = lc.getCasillas().indexOf(c2);
                        int ns2 = asig.getQueSegmentoHayEnCasilla(nc2);
                        int c2real = asig.enQueCasillaEstaSegmento(ns2);

                        marcaCasillaComoConflictiva(c1.getHashAula(), c1real);
                        marcaCasillaComoConflictiva(c2.getHashAula(), c2real);
                        profesoresConflictivos.add(p);
                    }
                }
            }
        }//End of for Profesor p...
    }

    /**
     *
     * @return
     */
    @Override
    public String getMensajeError() {
        ArrayList<Profesor> p = new ArrayList(profesoresConflictivos);
        return "<html>Profesores con clase solapadas: " + formatTeachersList(p) + "<html>";
    }

    /**
     *
     * @param parent
     */
    @Override
    public void writeConfig(Node parent) {
        //Nada que configurar!!
    }

    /**
     *
     * @param parent
     */
    @Override
    public void readConfig(Element parent) {
        //Nada que configurar!!
    }
}

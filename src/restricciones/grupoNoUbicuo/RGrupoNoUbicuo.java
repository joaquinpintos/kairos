/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restricciones.grupoNoUbicuo;

import data.DataProject;
import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.Grupo;
import data.asignaturas.Tramo;
import data.genetic.Asignacion;
import data.genetic.Casilla;
import data.genetic.DatosPorAula;
import data.genetic.ListaCasillas;
import data.genetic.ListaSegmentos;
import data.genetic.PosibleSolucion;
import data.genetic.Segmento;
import data.restricciones.Restriccion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class RGrupoNoUbicuo extends Restriccion {

    private static final long serialVersionUID = 27112013L;
    //hashGrupoCurso->lista de hashaulas, y cada hashaula->lista de [segmentos,duracion]
    //A cada grupo le asigna una lista de hashaulas donde imparte.
    //y a cada hashaula, una lista de [segmento,duracion] 
    final HashMap<String, HashMap<String, ArrayList<Integer[]>>> dataMañana;
    final HashMap<String, HashMap<String, ArrayList<Integer[]>>> dataTarde;
    final HashSet<String> gruposConflictivos; //Esto solo sirve para mostrar los grupos que no cumplan las restricciones
    long suma;

    /**
     *
     */
    public RGrupoNoUbicuo() {
        super(null);
        this.gruposConflictivos = new HashSet<String>();
        dataMañana = new HashMap<String, HashMap<String, ArrayList<Integer[]>>>();
        dataTarde = new HashMap<String, HashMap<String, ArrayList<Integer[]>>>();
    }

    /**
     *
     * @param dataProyecto
     */
    public RGrupoNoUbicuo(DataProject dataProyecto) {
        super(dataProyecto);
        this.gruposConflictivos = new HashSet<String>();
        dataMañana = new HashMap<String, HashMap<String, ArrayList<Integer[]>>>();
        dataTarde = new HashMap<String, HashMap<String, ArrayList<Integer[]>>>();
    }

    @Override
    public void inicializarDatos() {
        //Tengo que:
        //Determinar grupos "conflictivos":
        //Aquellos que se den en más de una clase en mañana o en tarde
        //Sacar de ahí las aulas conflictivas.
        //Hacer una lista de segmentos conflictivos para cada aula
        //Comparar las casillas de dichos segmentos a ver si coinciden
        //en día y hora.

        //Fase 1: Calculo de mapGrupoToAulas
        HashMap<String, ArrayList<String>> mapGrupoToAulas;
        mapGrupoToAulas = new HashMap<String, ArrayList<String>>();
        for (Carrera car : dataProyecto.getDataAsignaturas().getCarreras()) {
            for (Curso cur : car.getCursos()) {
                for (Asignatura asig : cur.getAsignaturas()) {
                    for (Grupo gr : asig.getGrupos().getGrupos()) {
                        for (Tramo tr : gr.getTramosGrupoCompleto().getTramos()) {
                            String hashGrupoCurso = tr.getParent().getParent().getHashCarreraGrupoCurso();
                            String hashAula = tr.getAulaMT().getHash();
                            if (!mapGrupoToAulas.containsKey(hashGrupoCurso)) {
                                mapGrupoToAulas.put(hashGrupoCurso, new ArrayList<String>());
                            }
                            mapGrupoToAulas.get(hashGrupoCurso).add(hashAula);

                        }//for tramo
                    }//for grupo
                }//for asig
            }//for cur
        }//for car

        //Fase 1: Calculo de dataMañana y dataTarde
        //Tendré 0, 1 y 2 grupos conflictivos. Calculo y elimino grupos no conflictivos
        //Tengo que contar para cada grupo cuántos aulas tiene de mañana y de tarde
        dataMañana.clear();
        dataTarde.clear();
        gruposConflictivos.clear();

        //Separo en segmentos de mañana y de tarde
        for (String p : mapGrupoToAulas.keySet()) {
            HashMap<String, ArrayList<Integer[]>> aulaToSegmentosMañana = new HashMap<String, ArrayList<Integer[]>>();
            HashMap<String, ArrayList<Integer[]>> aulaToSegmentosTarde = new HashMap<String, ArrayList<Integer[]>>();

            for (String hashAula : mapGrupoToAulas.get(p)) {
                //TODO: ¿Y si el nombre del aula contiene la T o la M????
                if (hashAula.contains("@M")) {//es clase de mañana
                    aulaToSegmentosMañana.put(hashAula, new ArrayList<Integer[]>());
                }
                if (hashAula.contains("@T")) {//es clase de tarde
                    aulaToSegmentosTarde.put(hashAula, new ArrayList<Integer[]>());
                }
            }
            //Si hay grupos esparcidos por la mañana/tarde en más de 1 clase, lo añado a la lista
            if (aulaToSegmentosMañana.size() > 1) {
                dataMañana.put(p, aulaToSegmentosMañana);
            }
            if (aulaToSegmentosTarde.size() > 1) {
                dataTarde.put(p, aulaToSegmentosTarde);
            }
        }

        //LLegados a este punto, tengo dos hashmaps con grupocursohash
        //y asociado a cada uno un hashmap de hashaula->array de segmentos vacío
        //ahora vamos a llenar los array de segmentos
        calculaSegmentos(dataMañana);
        calculaSegmentos(dataTarde);
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
        gruposConflictivos.clear();
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
        return "Los grupos no pueden estar en 2 clases a la vez";
    }

    /**
     *
     * @return
     */
    @Override
    public String descripcionCorta() {
        return "Un mismo grupo no pueden estar en 2 clases a la vez";
    }

    /**
     *
     * @return
     */
    @Override
    public String mensajeDeAyuda() {
        return "<html>Restricción necesaria si las clases de un mismo<br>"
                + "grupo se desarrollan en diferentes aulas</html>";
    }

    /**
     * Este es un método preparatorio de datos. Para cada hashaula calcula y
     * guarda la lista de segmentos que imparte en un grupo dado. Lo que guardo
     * NO son los objetos segmento, sino los índices.
     *
     * @param dataTarde
     */
    private void calculaSegmentos(HashMap<String, HashMap<String, ArrayList<Integer[]>>> dataTarde) {

        for (String hash : dataTarde.keySet()) {
            for (String hashAula : dataTarde.get(hash).keySet()) {
                ArrayList<Integer[]> segmentosConflictivos = dataTarde.get(hash).get(hashAula);
                ListaSegmentos ls = dataProyecto.getDatosPorAula(hashAula).getListaSegmentos();
                for (int n = 0; n < ls.getSegmentos().size(); n++) {
                    Segmento s = ls.getSegmentos().get(n);
                    if (!s.isHuecoLibre()) {
                        if (s.getGrupo().getHashCarreraGrupoCurso().equals(hash)) {
                            Integer[] dato = {n, s.getNumeroDeCasillasQueOcupa()};
                            segmentosConflictivos.add(dato);
                        }
                    }
                }
            }
        }
    }

    private void sumaPesos(PosibleSolucion posibleSolucion, HashMap<String, HashMap<String, ArrayList<Integer[]>>> data, double coef) {
//Calculo la función peso para los datos de mañana o tarde
        for (String hash : data.keySet()) {
            ArrayList<Casilla> casillasConflictivas = new ArrayList<Casilla>();
            for (String hashAula : data.get(hash).keySet()) {
                HashMap<String, DatosPorAula> a = dataProyecto.getMapDatosPorAula();
                DatosPorAula b = a.get(hashAula);
                ListaCasillas lc = b.getListaCasillas();
                ArrayList<Integer[]> segmentosConflictivos = data.get(hash).get(hashAula);
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
            //Para el grupo  y turno mañana/tarde fijado, tengo
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
                        gruposConflictivos.add(hash);
                    }
                }
            }
        }//End of for hashgrupocurso hash...
    }

    /**
     *
     * @return
     */
    @Override
    public String getMensajeError() {
        return "Grupos con clase solapadas: " + gruposConflictivos;
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

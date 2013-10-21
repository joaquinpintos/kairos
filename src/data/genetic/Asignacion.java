/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.genetic;

/**
 *
 * @author david
 */
import data.aulas.Aula;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

/**
 * Clase que representa una posible solución (asignación) de horarios en un
 * aula. La variable asignaciones guarda el array de enteros [1,...,numSeg]
 * donde numSeg es el número de segmento, menor o igual que numCas. cada
 * elemento asignaciones[i] guarda el índice del segmento respecto
 * listaSegmentos, (s=listaSegmentos.get(asignaciones.get(i))), colocados en
 * orden por ejemplo, el primero ocupa las casillas 0,1 y 2, el segundo la 3 y
 * 4, el tercero la 5 y 6, el cuarto la 7, etc. El array cuantasCasillas guarda
 * cuántas casillas ocupa el segmento en la posición i-ésima de asignaciones. El
 * array queCasilla especifica cuál es la primera casilla ocupada por el
 * segmento i-ésimo.
 *
 * @author david
 */
public final class Asignacion implements Serializable {

    private ArrayList<Integer> asignaciones;
    private ArrayList<Integer> cuantasCasillas;
    private ArrayList<Integer> queCasilla;
    private HashSet<Integer> segmentosConflictivos;
    private long peso;
    private int numSegmentos;
    //private DataProyecto dataProyecto;
    private int minutosCasilla;
    private Aula aula;
    private PosibleSolucion posibleSolucion;
    private String hashAula;
    private final DatosPorAula datosPorAula;
    private final int numCasillas;

    /**
     *
     * @param datosPorAula
     */
    public Asignacion(DatosPorAula datosPorAula) {
        this.datosPorAula = datosPorAula;
        this.hashAula = datosPorAula.getHashAula();
        asignaciones = new ArrayList<Integer>();
        this.numSegmentos = datosPorAula.getListaSegmentos().size();
        this.numCasillas = datosPorAula.getListaCasillas().size();
        this.minutosCasilla = datosPorAula.getListaCasillas().minutosPorCasilla();
        asignaciones = new ArrayList<Integer>();
        cuantasCasillas = new ArrayList<Integer>();
        queCasilla = new ArrayList<Integer>();
        segmentosConflictivos = new HashSet<Integer>();
    }

    /**
     *
     * @param k
     */
    public void add(int k) {
        asignaciones.add(k);
    }

    /**
     *
     * @param index
     * @return
     */
    public int get(int index) {
        return asignaciones.get(index);
    }

    /**
     * Devuelve el segmento que ocupa el lugar específicado
     *
     * @param index Indice del segmento a recuperar
     * @return Objeto segmento
     */
    public Segmento getSegmento(int index) {
        return datosPorAula.getListaSegmentos().get(this.get(index));
    }

    /**
     * Calcula array que almacena cuántas casillas ocupa cada segmento
     */
    private void calculaCuantasCasillas() {
        cuantasCasillas = new ArrayList<Integer>();
        Segmento s;

        for (int k = 0; k < numSegmentos; k++) {
            s = datosPorAula.getListaSegmentos().get(asignaciones.get(k));
            int numCasillas = (int) (s.getDuracion() / this.minutosCasilla);
            cuantasCasillas.add(numCasillas);
        }
    }

    /**
     * Calcula un array que almacena en qué casilla específica se sitúan los
     * segmentos
     */
    private void calculaQueCasilla() {
        queCasilla = new ArrayList<Integer>();
        int casilla = 0;
        for (int k = 0; k < numSegmentos; k++) {
            queCasilla.add(casilla);
            casilla += cuantasCasillas.get(k);
        }
    }

    /**
     * Para el segmento indicado por el índice dado, devuelve la lista de
     * casillas consecutivas que ocupa.
     *
     * @param indiceSolucion
     * @return
     */
    public ArrayList<Integer> getRangoCasillasOcupadas(int indiceSolucion) {
        int c = cuantasCasillas.get(indiceSolucion);
        ArrayList<Integer> resul = new ArrayList<Integer>();

        for (int k = 0; k < c; k++) {
            resul.add(queCasilla.get(indiceSolucion) + k);
        }

        //(queCasilla.subList(indiceSolucion, indiceSolucion + c));
        return resul;
    }

    /**
     * Método delegado en el array asignaciones
     *
     * @param index
     * @param valor
     */
    public void set(int index, int valor) {
        if (index == asignaciones.size()) {
            asignaciones.add(valor);
        } else {
            asignaciones.set(index, valor);
        }
    }

    /**
     * Método delegado en el array asignaciones
     *
     * @return
     */
    public int size() {
        return asignaciones.size();
    }

    /**
     * Método delegado en el array asignaciones
     *
     * @param n
     * @return
     */
    public boolean contains(int n) {
        return asignaciones.contains(n);
    }

    /**
     * Genera una posible solución al azar, cuidando de que no haya elementos
     * repetidos.
     *
     * @param datosPorAula
     * @return
     */
    public static Asignacion generador(DatosPorAula datosPorAula) {
        Asignacion nuevaAsignacion = new Asignacion(datosPorAula);
        Random randomGenerator = new Random();
        nuevaAsignacion.setPeso(100000000);//Una solución generada al azar ha de ser mala!!
        for (int k = 0; k < datosPorAula.getListaSegmentos().size(); k++) {
            nuevaAsignacion.getAsignaciones().add(k);
        }
        Collections.shuffle(nuevaAsignacion.getAsignaciones());

        nuevaAsignacion.update();
        return nuevaAsignacion;
    }

    @Override
    public String toString() {
        return asignaciones.toString();
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getAsignaciones() {
        return asignaciones;
    }

    /**
     *
     * @param asignaciones
     */
    public void setAsignaciones(ArrayList<Integer> asignaciones) {
        this.asignaciones = asignaciones;
    }

    /**
     *
     * @return
     */
    public long getPeso() {
        return peso;
    }

    /**
     *
     * @param peso
     */
    public void setPeso(long peso) {
        this.peso = peso;
    }

    /**
     *
     * @return
     */
    public Asignacion copia() {
        ArrayList<Integer> asigs = new ArrayList<Integer>();
        for (int k : asignaciones) {
            asigs.add(k);
        }
        Asignacion sol = new Asignacion(this.datosPorAula);
        sol.setAsignaciones(asigs);
        sol.setPosibleSolucion(posibleSolucion);
        sol.setPeso(peso);
        sol.update();
        return sol;
    }

    /**
     *
     * @return
     */
    public int getNumSegmentos() {
        return numSegmentos;
    }

    /**
     *
     * @param numSegmentos
     */
    public void setNumSegmentos(int numSegmentos) {
        this.numSegmentos = numSegmentos;
    }

    /**
     *
     * @return
     */
    public int getMinutosCasilla() {
        return minutosCasilla;
    }

    /**
     *
     * @param minutosCasilla
     */
    public void setMinutosCasilla(int minutosCasilla) {
        this.minutosCasilla = minutosCasilla;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getCuantasCasillas() {
        return cuantasCasillas;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getQueCasilla() {
        return queCasilla;
    }

    /**
     *
     * @param numCasilla1
     * @param numCasilla2
     * @return
     */
    public boolean isCasillasEnMismoSegmento(int numCasilla1, int numCasilla2) {
        if (numCasilla2 == -1) {
            return false;
        }
        boolean resul = false;
//        int num = numCasilla2;
//        int indice = -1;
//        while (indice == -1) {
//            if (num == numCasilla1) {
//                resul = true;
//                break;
//            }
//            indice = queCasilla.indexOf(num);
//            num--;
//
//        }
        if (getQueSegmentoHayEnCasilla(numCasilla2) == getQueSegmentoHayEnCasilla(numCasilla1)) {
            resul = true;
        }
        return resul;
    }

    /**
     * Devuelve el índice del segmento que está en la casilla dada. Ejemplo:
     * asig=[1,3,9,5... casillas=[1,2,12,16.. Eso significa por ejemplo que en
     * la casilla 12 está el segmento 3 (el segundo) Luego está función para el
     * valor 12 devolverá 2 (segundo segmento)
     *
     * @param numCasilla
     * @return
     */
    public int getIndiceSegmentoQueHayEnCasilla(int numCasilla) {
        int num = numCasilla;
        int indice = -1;
        while (indice == -1) {
            indice = queCasilla.indexOf(num);
            num--;
        }
        return indice;
    }

    /**
     * Para un número de casilla especificado, devuelve el número de segmento
     * que hay en esa casilla
     *
     * @param numCasilla
     * @return
     */
    public int getQueSegmentoHayEnCasilla(int numCasilla) {
        int num = numCasilla;
        int indice = -1;
        while (indice == -1) {
            indice = queCasilla.indexOf(num);
            num--;
        }

        return asignaciones.get(indice);
    }

    /**
     *
     * @param numSegmento
     * @return
     */
    public int enQueCasillaEstaSegmento(int numSegmento) {
        int idx = asignaciones.indexOf(numSegmento);
        return queCasilla.get(idx);
    }

    /**
     *
     */
    public void update() {
        //    reordenaCasillas();
        calculaCuantasCasillas();
        calculaQueCasilla();
    }

    /**
     *
     * @return
     */
    public Aula getAula() {
        return aula;
    }

    /**
     *
     * @param aula
     */
    public void setAula(Aula aula) {
        this.aula = aula;
    }

    /**
     *
     * @return
     */
    public PosibleSolucion getPosibleSolucion() {
        return posibleSolucion;
    }

    /**
     *
     * @param posibleSolucion
     */
    public void setPosibleSolucion(PosibleSolucion posibleSolucion) {
        this.posibleSolucion = posibleSolucion;
    }

    /**
     *
     * @return
     */
    public String getHashAula() {
        return hashAula;
    }

    /**
     *
     * @param hashAula
     */
    public void setHashAula(String hashAula) {
        this.hashAula = hashAula;
    }

    /**
     *
     * @return
     */
    public DatosPorAula getDatosPorAula() {
        return datosPorAula;
    }

    private int obtainPlaceToPut(int k) {
        int resul = 0;
        while (queCasilla.get(resul) < k) {
            resul++;
        }
//if (queCasilla.get(resul)!=k) posibleSolucion.setPeso(posibleSolucion.getPeso()+111);
        return resul;
    }

    /**
     *
     * @param numSegmento
     * @param esConflictivo
     */
    public void marcaComoConflictivo(int numSegmento, boolean esConflictivo) {
        if (esConflictivo) {
            segmentosConflictivos.add(numSegmento);
        } else {
            segmentosConflictivos.remove(numSegmento);
        }
    }

    /**
     *
     * @param numSegmento
     * @return
     */
    public boolean esConflictivo(int numSegmento) {
        return segmentosConflictivos.contains(numSegmento);
    }

    /**
     *
     * @return
     */
    public HashSet<Integer> getSegmentosConflictivos() {
        return segmentosConflictivos;
    }

    /**
     *
     * @return
     */
    public int getNumCasillas() {
        return numCasillas;
    }
}

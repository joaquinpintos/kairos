/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.genetic;

import genetic.crossovers.Crossover;
import genetic.mutators.Mutator;
import data.DataProyecto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import data.restricciones.Restriccion;
import gui.AbstractMainWindow;

/**
 *
 * @author David Gutierrez
 */
public class GeneticAlgorithm {

    private ArrayList<PosibleSolucion> manada;
    private PosibleSolucion optimo;
    private final Crossover cruzador;
    private final ArrayList<Restriccion> restricciones;
    private final DataProyecto dataProyecto;
    private ListaSegmentos listaSegmentos;
    private ListaCasillas listaCasillas;
    private int tamañoManada;
    private int max_iter;
    private final Mutator mutator;
    private GeneticInformer geneticInformer;
    private int numElitismo;
    private double probabilidadMutacion;
    private int numIter;
    private ArrayList<Restriccion> restriccionesFallidas;
    private int nivelCritico;
    private PosibleSolucion solucionInicial;
    private AbstractMainWindow mainWindow;

    /**
     *
     * @return
     */
    public double getProbabilidadMutacion() {
        return probabilidadMutacion;
    }

    /**
     *
     * @param probabilidadMutacion
     */
    public void setProbabilidadMutacion(double probabilidadMutacion) {
        this.probabilidadMutacion = probabilidadMutacion;
    }

    /**
     *
     * @return
     */
    public int getTamañoManada() {
        return tamañoManada;
    }

    /**
     *
     * @param tamañoManada
     */
    public void setTamañoManada(int tamañoManada) {
        this.tamañoManada = tamañoManada;
    }

    /**
     *
     * @return
     */
    public int getMax_iter() {
        return max_iter;
    }

    /**
     *
     * @param max_iter
     */
    public void setMax_iter(int max_iter) {
        this.max_iter = max_iter;
    }

    /**
     *
     * @param cruzador
     * @param mutator
     * @param restricciones
     * @param dataProyecto
     */
    public GeneticAlgorithm(Crossover cruzador, Mutator mutator, ArrayList<Restriccion> restricciones, DataProyecto dataProyecto) {
        this.solucionInicial = null;
        this.cruzador = cruzador;
        this.restricciones = restricciones;
        this.dataProyecto = dataProyecto;
        this.mutator = mutator;
        tamañoManada = 50;
        numElitismo = 5;
    }

    /**
     *
     * @param cruzador
     * @param mutator
     * @param dataProyecto
     */
    public GeneticAlgorithm(Crossover cruzador, Mutator mutator, DataProyecto dataProyecto) {
        this.solucionInicial = null;
        this.cruzador = cruzador;
        this.dataProyecto = dataProyecto;
        this.mutator = mutator;
        restricciones = new ArrayList<Restriccion>();
        tamañoManada = 50;
        numElitismo = 5;

    }

    /**
     *
     */
    public void inicializarDatos() {

        for (Restriccion r : restricciones) {
            r.inicializarDatos();
        }
        manada = new ArrayList<PosibleSolucion>();
        restriccionesFallidas = new ArrayList<Restriccion>();
        setDebug(false);
        generaManadaInicial();
    }

    /**
     * Bucle principal del algoritmo.
     *
     * @return false si ha terminado
     */
    public boolean runSingleLoop() {
        boolean continueLoop = true;
        //Al principio tomo como óptimo una solución cualquiera.
        optimo = manada.get(0).copia();
        //Bucle principal:
        long optimoValor = 100000;
        optimo.setPeso(optimoValor);
        buclePrincipal:
//        while (numIter < max_iter) {
        tamañoManada = manada.size();
        for (PosibleSolucion s : manada) {
            s.setDataProyecto(dataProyecto);
            s.update();//Actualizo datos internos de las soluciones
        }
        calculaPesosManada();
            //Ahora selecciono los mejores y los cruzo.
        //Primero los ordeno de menos peso a más peso
        Collections.sort(manada, new SolucionesComparator());
        //Optimo
        if (manada.get(0).getPeso() < optimo.getPeso()) {
            optimo = manada.get(0).copia();
        }
        //Calculo el valor óptimo que he ido alcanzando.
//            optimoValor = optimo.getPeso();
        if (optimo.getPeso() == 0) {//Óptimo alcanzado. No puedo mejorar más
            optimo = manada.get(0).copia();
            continueLoop = false;
//                break buclePrincipal;
        }
        calcularSiguienteGeneracion();
        numIter++;
        System.out.println("Optimo: "+optimo.getPeso());
        return continueLoop;
    }

    public PosibleSolucion getSolucion() {
        //Finalizado el bucle, genero horario con la mejor solución hallada.
        //Antes refresco los datos internos de las soluciones
        optimo.setDataProyecto(dataProyecto);
        optimo.update();//Actualizo datos internos de las soluciones
        calculaPesosPosibleSolucion(optimo);
//        geneticInformer.finalizado(this);
        return optimo;
    }

    protected void generaManadaInicial() {
        nivelCritico = 3;//Al principio nivel verde
        int nnInicial = 0;
        if (solucionInicial != null) {
            manada.add(solucionInicial);
            nnInicial = 1;
        }
        for (int nn = nnInicial; nn < tamañoManada; nn++) {
            boolean add = manada.add(PosibleSolucion.generador(dataProyecto));
        }
    }

    /**
     *
     * @param r
     */
    public void addRestriccion(Restriccion r) {
        restricciones.add(r);
    }

    private void calculaPesosManada() {
        setNivelCritico(3);
        for (PosibleSolucion s : manada) {
//            if (s.getPeso() == 0) {
            calculaPesosPosibleSolucion(s);
            //System.out.println(s.getAsignaciones()+"-->"+s.getPeso());
            //          }
        }

    }

    /**
     *
     */
    public void calculaPesosOptimo() {
        calculaPesosPosibleSolucion(optimo);
    }

    private void calculaPesosPosibleSolucion(PosibleSolucion s) {
        restriccionesFallidas.clear();
        long nuevoPeso = 0;
        long suma;
        for (Restriccion r : restricciones) {
            r.clearConflictivos();
            suma = r.calculaPeso(s);
            nuevoPeso += suma;
            if (suma > 0) {
                restriccionesFallidas.add(r);
                actualizaNivelCritico(r.getImportancia());
            }
        }
        s.setPeso(nuevoPeso);
    }

    private void calcularSiguienteGeneracion() {
        //La manada está ordenada de mejor a peor. item 0 es el mejor.
        //Cojo los 10 mejores al azar.
        ArrayList<PosibleSolucion> nuevaManada = new ArrayList<PosibleSolucion>();
        for (int k = 0; k < numElitismo; k++) {
            //mutator.elitistMutate(manada.get(k));
            nuevaManada.add(manada.get(k).copia());
        }
        double pesoMediaInversa = 0;//Calculo la suma de la inversa de los pesos
        for (PosibleSolucion s : manada) {
            if (s.getPeso() > 0) {
                pesoMediaInversa += 1 / (double) s.getPeso();
            }
        }
        double ruleta;

        for (int k = 0; k < tamañoManada - numElitismo; k++) {
            ruleta = (Math.random()) * pesoMediaInversa;
            double flechaRuleta = 0;
            PosibleSolucion papa = null, mama = null;
            for (PosibleSolucion s : manada) {
                flechaRuleta += 1 / (double) s.getPeso();
                if (flechaRuleta > ruleta) {
                    papa = s;
                    break;
                }
            }
            ruleta = (Math.random()) * pesoMediaInversa;
            flechaRuleta = 0;
            for (PosibleSolucion s : manada) {
                flechaRuleta += 1 / (double) s.getPeso();
                if (flechaRuleta > ruleta) {
                    mama = s;
                    break;
                }
            }
            //Ahora que tengo a papá y a mamá, a multiplicarse!!
            PosibleSolucion hijo = cruzador.cruce(papa, mama);
            //if (Math.random() < probabilidadMutacion) {
            mutator.mutate(hijo);
            //}
            nuevaManada.add(hijo);
        }

//Metodo de emparejar al azar
//        for (int nh = 0; nh < numHijos; nh++) {
//            Collections.shuffle(mejores);
//            PosibleSolucion hijo = cruzador.cruce(mejores.get(0), mejores.get(1));
//            //Muto el hijo.
//            nuevaManada.add(hijo);
//
//        }
        manada = nuevaManada;//Cambio la manada, paso a la siguiente generación.
    }

    /**
     *
     * @return
     */
    public GeneticInformer getGeneticInformer() {
        return geneticInformer;
    }

    /**
     *
     * @param geneticInformer
     */
    public void setGeneticInformer(GeneticInformer geneticInformer) {
        this.geneticInformer = geneticInformer;
    }

    /**
     *
     * @return
     */
    public int getNumElitismo() {
        return numElitismo;
    }

    /**
     *
     * @param numElitismo
     */
    public void setNumElitismo(int numElitismo) {
        this.numElitismo = numElitismo;
    }

    private void dbg(Object a) {
        System.out.println(a);
    }

    /**
     *
     * @return
     */
    public PosibleSolucion getOptimo() {
        return optimo;
    }

    /**
     *
     * @return
     */
    public int getNumIter() {
        return numIter;
    }

    /**
     *
     * @return
     */
    public String getDescripcionRestriccionesFallidas() {
        //TODO: Da error de concurrentException ya aque mientras se rellena este array se actualiza en otro thread
        String resul = "";
        for (Restriccion r : restriccionesFallidas) {
            resul += r.descripcion() + "\n";
        }
        return resul;

    }

    /**
     *
     * @param b
     */
    public void setDebug(boolean b) {
        for (Restriccion r : restricciones) {
            r.setDebug(b);
        }
    }

    /**
     *
     * @return
     */
    public int getNivelCritico() {
        return nivelCritico;
    }

    /**
     *
     * @param nivel
     */
    public void setNivelCritico(int nivel) {
        nivelCritico = nivel;
    }

    /**
     *
     * @param nivel
     */
    public void actualizaNivelCritico(int nivel) {
        if (nivelCritico > nivel) {
            setNivelCritico(nivel);
        }
    }

    /**
     *
     * @param solInicial
     */
    public void setSolucionInicial(PosibleSolucion solInicial) {
        this.solucionInicial = solInicial;
    }

    /**
     *
     * @param mainWindow
     */
    public void setMainWindow(AbstractMainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void runMainLoop() {
        while (runSingleLoop()){};
    }
}

/**
 * Clase para comparar dos soluciones atendiendo a sus pesos.
 *
 * @author David Gutierrez
 */
class SolucionesComparator implements Comparator<PosibleSolucion> {

    @Override
    public int compare(PosibleSolucion t1, PosibleSolucion t2) {
        int resul = 0;
        if (t1.getPeso() < t2.getPeso()) {
            resul = -1;
        }
        if (t1.getPeso() > t2.getPeso()) {
            resul = 1;
        }
        return resul;
    }
}

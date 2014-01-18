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

import genetic.crossovers.Crossover;
import genetic.mutators.Mutator;
import data.DataProject;
import data.MyConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import data.restricciones.Restriccion;
import gui.AbstractMainWindow;

/**
 *
 * @author David
 */
public class GeneticAlgorithm {

    private ArrayList<PosibleSolucion> manada;
    private PosibleSolucion optimo;
    private final Crossover cruzador;
    private final ArrayList<Restriccion> restricciones;
    private final DataProject dataProyecto;
//    private ListaSegmentos listaSegmentos;
//    private ListaCasillas listaCasillas;
    private int tamañoManada;
    private int max_iter;
    private final Mutator mutator;
//    private GeneticInformer geneticInformer;
    private int numElitismo;
    private double probabilidadMutacion;
    private int numIter;
    private final ArrayList<Restriccion> restriccionesFallidas;
    private int nivelCritico;
    private PosibleSolucion solucionInicial;
    private AbstractMainWindow mainWindow;
    private final SolucionesComparator solucionesComparator;

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
    public GeneticAlgorithm(Crossover cruzador, Mutator mutator, ArrayList<Restriccion> restricciones, DataProject dataProyecto) {
        this.solucionInicial = null;
        this.cruzador = cruzador;
        this.restricciones = restricciones;
        this.dataProyecto = dataProyecto;
        this.mutator = mutator;
        tamañoManada = 50;
        numElitismo = 5;
        restriccionesFallidas = new ArrayList<Restriccion>();
        solucionesComparator = new SolucionesComparator();
    }

    /**
     *
     * @param cruzador
     * @param mutator
     * @param dataProyecto
     */
    public GeneticAlgorithm(Crossover cruzador, Mutator mutator, DataProject dataProyecto) {
        this.solucionInicial = null;
        this.cruzador = cruzador;
        this.dataProyecto = dataProyecto;
        this.mutator = mutator;
        restricciones = new ArrayList<Restriccion>();
        tamañoManada = 50;
        numElitismo = 5;
        restriccionesFallidas = new ArrayList<Restriccion>();
        solucionesComparator = new SolucionesComparator();
    }

    /**
     *
     */
    public void inicializarDatos() {

        for (Restriccion r : restricciones) {
            r.inicializarDatos();
        }
        manada = new ArrayList<PosibleSolucion>();
        restriccionesFallidas.clear();
        setDebug(false);
        generaManadaInicial();
        //Al principio tomo como óptimo una solución cualquiera.
        optimo = manada.get(0).copia();
        optimo.setPeso(100000);
    }

    /**
     * Bucle principal del algoritmo.
     *
     * @return false si ha terminado
     */
    public boolean runSingleLoop() {
        boolean continueLoop = true;
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
        Collections.sort(manada, solucionesComparator);
        //Optimo
        if (manada.get(0).getPeso() < optimo.getPeso()) {
            optimo = manada.get(0).copia();
        }
        //Calculo el valor óptimo que he ido alcanzando.
        if (optimo.getPeso() == 0) {//Óptimo alcanzado. No puedo mejorar más
            continueLoop = false;
        } else {
            calcularSiguienteGeneracion();
        }
        numIter++;
        return continueLoop;
    }

    /**
     *
     * @return
     */
    public PosibleSolucion getSolucion() {
        //Finalizado el bucle, genero horario con la mejor solución hallada.
        //Antes refresco los datos internos de las soluciones
        if (optimo != null) {
            optimo.setDataProyecto(dataProyecto);
            optimo.update();//Actualizo datos internos de las soluciones
            calculaPesosPosibleSolucion(optimo);
        }
//        geneticInformer.finalizado(this);
        return optimo;
    }

    /**
     *
     */
    protected void generaManadaInicial() {
        nivelCritico = 3;//Al principio nivel verde
        int nnInicial = 0;
        if (solucionInicial != null) {
            manada.add(solucionInicial);
            nnInicial = 1;
        }
        for (int nn = nnInicial; nn < tamañoManada; nn++) {
            manada.add(PosibleSolucion.generador(dataProyecto));
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
        for (PosibleSolucion s : manada) {
            calculaPesosPosibleSolucion(s);
        }

    }

    /**
     *
     */
    public void calculaPesosOptimo() {
        calculaPesosPosibleSolucion(optimo, true);
    }

    private void calculaPesosPosibleSolucion(PosibleSolucion s) {
        calculaPesosPosibleSolucion(s, false);
    }

    private void calculaPesosPosibleSolucion(PosibleSolucion s, boolean detailed) {
        if (detailed) {
            restriccionesFallidas.clear();
        }
        s.setLevel(MyConstants.LEVEL_GREEN);
        long nuevoPeso = 0;
        long suma;
        for (Restriccion r : restricciones) {
            if (detailed) {
                r.clearConflictivos();
            }
            suma = r.calculaPeso(s);
            nuevoPeso += suma;
            if (suma > 0) {
                s.updateLevel(r.getImportancia());
                if (detailed) {
                    restriccionesFallidas.add(r);
                }
            }
        }
        s.setPeso(nuevoPeso);
//            System.out.println("Nuevo level: "+s.getLevel());
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

//    private void dbg(Object a) {
//        System.out.println(a);
//    }
    /**
     *
     * @return
     */
    public PosibleSolucion getOptimo() {
//        System.out.println("Peso optimo[alg]:"+optimo.getPeso());
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
        StringBuilder resul = new StringBuilder();
        for (Restriccion r : restriccionesFallidas) {
            resul.append(r.descripcion()).append("\n");
        }
        return resul.toString();

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
        return optimo.getLevel();
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

    /**
     *
     */
    public void runMainLoop() {
        while (runSingleLoop()) {
        };

    }

//    public void calculaRestriccionesIncumplidasOptimo() {
//        long suma;
//        restriccionesFallidas.clear();
//        for (Restriccion r : restricciones) {
//            r.clearConflictivos();
//            suma = r.calculaPeso(optimo);
//            if (suma > 0) {
//                restriccionesFallidas.add(r);
//                actualizaNivelCritico(r.getImportancia());
//            }
//        }
//    }
}

/**
 * Clase para comparar dos soluciones atendiendo a sus pesos.
 *
 * @author David
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

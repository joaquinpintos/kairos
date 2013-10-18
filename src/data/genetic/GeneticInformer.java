/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.genetic;

/**
 * Interfaz para pasar a la clase GeneticAlgorigthm que se encarga de mostrar los
 * datos. También se encarga de procesar la condición de interrupción.
 * @author david
 */
public interface GeneticInformer {
    /**
     *
     * @param g
     */
    public void setInformation(GeneticAlgorithm g);
    /**
     *
     * @return
     */
    public boolean interrumpido();
    /**
     *
     * @param g
     */
    public void finalizado(GeneticAlgorithm g);
}

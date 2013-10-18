/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.crossovers;

import data.genetic.PosibleSolucion;

/**
 *
 * @author David Gutierrez
 */
public interface Crossover {

    /**
     *
     * @param p1
     * @param p2
     * @return
     */
    public PosibleSolucion cruce(PosibleSolucion p1, PosibleSolucion p2);
            
}

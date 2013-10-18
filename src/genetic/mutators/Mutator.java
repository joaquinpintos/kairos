/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.mutators;

import data.genetic.PosibleSolucion;

/**
 *
 * @author david
 */
public abstract class Mutator {

    /**
     *
     */
    protected double factorMutacion;

    /**
     *
     * @param s
     */
    public abstract void mutate(PosibleSolucion s);

    /**
     *
     */
    public abstract void inicializar();

    /**
     *
     * @param factorMutacion
     */
    public void setFactorMutacion(double factorMutacion) {
        this.factorMutacion = factorMutacion;
    }

    /**
     *
     * @return
     */
    public final double getFactorMutacion() {
        return factorMutacion;
    }
}

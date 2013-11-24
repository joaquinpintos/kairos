/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.mutators;

import data.genetic.PosibleSolucion;
import java.util.Random;

/**
 *
 * @author david
 */
public abstract class Mutator {

    /**
     *
     */
    protected Random random;

    /**
     *
     */
    protected double factorMutacion;

    /**
     *
     */
    public Mutator() {
        this.random = new Random();
    }

    /**
     *
     * @param factorMutacion
     */
    public Mutator(double factorMutacion) {
        this.random = new Random();
        this.factorMutacion = factorMutacion;
    }

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

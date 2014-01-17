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
package genetic.mutators;

import data.genetic.PosibleSolucion;
import java.util.Random;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
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

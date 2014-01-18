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

import data.genetic.Asignacion;
import data.genetic.PosibleSolucion;

/**
 * Simple mutator
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class SwapMutator extends Mutator {

    /**
     * Constructor por defecto
     */
    public SwapMutator() {
    }

    /**
     *
     * @param s
     */
    @Override
    public void mutate(PosibleSolucion s) {
        for (Asignacion asig : s.getMapAsignaciones().values()) {
            mutateAsignacion(asig);
        }
    }

    /**
     *
     * @param asig
     */
    public void mutateAsignacion(Asignacion asig) {
        for (int k = 0; k < asig.size(); k++) {
            if ((Math.random() < factorMutacion)) {
                //if ((Math.random() < factorMutacion) || (s.isGenDefectuoso(k)))
                changeIndex(asig, k);

            }
        }
        asig.update();

    }

    /**
     *
     * @param asig
     * @param n1
     */
    public void changeIndex(Asignacion asig, int n1) {
        int numSegmentos = asig.getNumSegmentos();
        int n2 = random.nextInt(numSegmentos);
        if (n2 == numSegmentos) {
            n2--;
        }

        int valor1 = asig.get(n1);
        int valor2 = asig.get(n2);
        //Intercambio los elementos
        asig.getAsignaciones().set(n1, valor2);
        asig.getAsignaciones().set(n2, valor1);
    }

    /**
     *
     */
    @Override
    public void inicializar() {
        //System.out.println("Inicializo mutador, factor: " + factorMutacion);
    }
}

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
import java.util.ArrayList;
import java.util.Collections;

/**
 * Simple mutator
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class InversionMutator extends Mutator {

    /**
     * Constructor por defecto
     */
    public InversionMutator() {
        super();
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
                invertIndex(asig, k);
            }
        }
        asig.update();
    }

    /**
     *
     * @param asig
     * @param n1
     */
    public void invertIndex(Asignacion asig, int n1) {
        final int numSegmentos = asig.getNumSegmentos();
        int n2 = random.nextInt(numSegmentos);
        if (n2 == numSegmentos) {
            n2--;
        }
        int nn1 = Math.min(n1, n2);
        int nn2 = Math.max(n1, n2);

        ArrayList<Integer> cache = new ArrayList<Integer>();

        for (int k = nn1; k <= nn2; k++) {
            cache.add(asig.get(k));
        }
        Collections.reverse(cache);
        int j = 0;
        for (int k = nn1; k <= nn2; k++) {
            asig.getAsignaciones().set(k, cache.get(j));
            j++;
        }
    }

    /**
     *
     */
    @Override
    public void inicializar() {
    }

}

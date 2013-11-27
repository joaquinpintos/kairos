/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.mutators;

import data.genetic.Asignacion;
import data.genetic.PosibleSolucion;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ScrambleMutator extends Mutator {

    /**
     * Constructor por defecto
     */
    public ScrambleMutator() {
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
                scrambleIndex(asig, k);
            }
        }
        asig.update();
    }

    private void scrambleIndex(Asignacion asig, int n1) {
        int numSegmentos = asig.getNumSegmentos();
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
        Collections.shuffle(cache);
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

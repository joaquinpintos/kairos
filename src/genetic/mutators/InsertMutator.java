/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.mutators;

import data.genetic.Asignacion;
import data.genetic.PosibleSolucion;

/**
 * Simple mutator
 *
 * @author david
 */
public class InsertMutator extends Mutator {

    /**
     *
     */
    public InsertMutator() {
    }

    /**
     *
     * @param s
     */
    @Override
    public void mutate(PosibleSolucion s) {
        for (Asignacion asig: s.getMapAsignaciones().values()) {
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
                insertIndex(asig, k);
            }
        }
        asig.update();
    }

    /**
     *
     * @param asig
     * @param n1
     */
    public void insertIndex(Asignacion asig, int n1) {
        int numSegmentos = asig.getNumSegmentos();
        int n2 = (int) (Math.random() * numSegmentos);

        if (n2 == numSegmentos) {
            n2--;
        }
        if (n1 < n2) {
            int value = asig.get(n1);
            for (int k = n1; k < n2; k++) {
                asig.getAsignaciones().set(k, asig.get(k + 1));
            }
            asig.getAsignaciones().set(n2, value);
        } else {
            int value = asig.get(n1);
            for (int k = n1; k > n2; k--) {
                asig.getAsignaciones().set(k, asig.get(k - 1));
            }
            asig.getAsignaciones().set(n2, value);
        }
    }

    /**
     *
     */
    @Override
    public void inicializar() {
    }

}

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

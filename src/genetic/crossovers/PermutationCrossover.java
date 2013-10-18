/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package genetic.crossovers;

import data.genetic.Asignacion;
import data.genetic.PosibleSolucion;

/**
 *
 * @author David Gutierrez
 */
public class PermutationCrossover implements Crossover {
//Este crossover con

    /**
     *
     * @param p1
     * @param p2
     * @return
     */
    @Override
    public PosibleSolucion cruce(PosibleSolucion p1, PosibleSolucion p2) {
        PosibleSolucion hijo=new PosibleSolucion();
        for (String hashAula : p1.getMapAsignaciones().keySet()) {
            Asignacion s1 = p1.getAsignacion(hashAula);
            Asignacion s2 = p2.getAsignacion(hashAula);

            int numSegmentos = s1.getNumSegmentos();
            Asignacion resul = new Asignacion(s1.getDatosPorAula());
            int corte = (int) (Math.random() * numSegmentos);
            for (int k = 0; k < corte; k++) {
                resul.getAsignaciones().add(s1.get(k));//Hasta aquí copio de la primera solución.
                // resul.setGenDefectuoso(k);
            }
            //Ahora relleno el resto con elementos de s2 por orden.
            int k2 = 0;
            for (int k = corte; k < numSegmentos; k++) {
                while (resul.contains(s2.get(k2))) {
                    k2++;
                }
                resul.getAsignaciones().add(s2.get(k2));
                k2++;
            }
            hijo.addAsignacion(hashAula, resul);
        }
        return hijo;
    }
}

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
package genetic.crossovers;

import data.genetic.Asignacion;
import data.genetic.PosibleSolucion;
import java.util.Random;

/**
 *
 * @author David
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
        Random r=new Random();
        PosibleSolucion hijo=new PosibleSolucion();
        for (String hashAula : p1.getMapAsignaciones().keySet()) {
            Asignacion s1 = p1.getAsignacion(hashAula);
            Asignacion s2 = p2.getAsignacion(hashAula);

            int numSegmentos = s1.getNumSegmentos();
            Asignacion resul = new Asignacion(s1.getDatosPorAula());
            int corte = r.nextInt(numSegmentos);//(int) (Math.random() * numSegmentos);
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

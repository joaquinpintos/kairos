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
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class AssortedMutator extends Mutator {

    private int contador;
    private final ArrayList<Mutator> mutators;

    /**
     * Constructor por defecto
     */
    public AssortedMutator() {
        Mutator m1 = new SwapMutator();
        Mutator m2 = new InsertMutator();
        Mutator m3 = new ScrambleMutator();
        Mutator m4 = new InversionMutator();
        mutators=new ArrayList<Mutator>();
        mutators.add(m1);
        mutators.add(m2);
        mutators.add(m3);
        mutators.add(m4);
    }

    
    /**
     *
     * @param s
     */
    @Override
    public void mutate(PosibleSolucion s) {
        Mutator m = seleccionaMutatorQueTocaAhora();
       // m.inicializar();
        m.mutate(s);
    }


    /**
     *
     * @return
     */
    public Mutator seleccionaMutatorQueTocaAhora() {
        contador++;
        contador = (contador % mutators.size());
        return mutators.get(contador);
    }

    /**
     *
     */
    @Override
    public void inicializar() {
        contador = 0;
        for (Mutator m : mutators) {
            m.inicializar();
        }
    }

    /**
     *
     * @param factorMutacion
     */
    @Override
    public void setFactorMutacion(double factorMutacion) {
        super.setFactorMutacion(factorMutacion); //To change body of generated methods, choose Tools | Templates.
        for (Mutator m : mutators) {
            m.setFactorMutacion(factorMutacion);
        }
    }
    
}

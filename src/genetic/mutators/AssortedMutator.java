/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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

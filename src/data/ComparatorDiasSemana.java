/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ComparatorDiasSemana implements Comparator, Serializable {

    private static final long serialVersionUID = 27112013L;
    private final ArrayList<String> dias;

    /**
     *
     */
    public ComparatorDiasSemana() {
        dias = new ArrayList<String>();
        dias.addAll(Arrays.asList(MyConstants.DIAS_SEMANA));
    }

    @Override
    public int compare(Object o1, Object o2) {
        int a = dias.indexOf(o1);
        int b = dias.indexOf(o2);
        int resul = 0;
        if (a < b) {
            resul = -1;
        }
        if (a > b) {
            resul = 1;
        }
        return resul;
    }
}

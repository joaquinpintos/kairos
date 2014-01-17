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

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
package testers;

import java.util.Iterator;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class IteratorTester {

    public IteratorTester() {
    }

    public void test() {
        Iterator<Integer> a = iterador();
        while (a.hasNext()) {
            System.out.println(a.next());
        }
    }

    public Iterator<Integer> iterador() {
        Iterator<Integer> a;
        a = new Iterator<Integer>() {
            int a = 1;
            int b = 0;
            int c = 0;
            int contador = 0;

            @Override
            public boolean hasNext() {
                return (contador < 5);
            }

            @Override
            public Integer next() {
                contador++;
                c = a + b;
                a = b;
                b = c;
                return c;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        return a;

    }
}

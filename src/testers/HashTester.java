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

import java.util.HashMap;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class HashTester {

    /**
     *
     */
    public void prueba() {
        claseHash a1 = new claseHash(2, "HOLA");
        claseHash a2 = new claseHash(2, "HOLA");
        System.out.println(a2.equals(a1));
        HashMap<claseHash, Integer> dict = new HashMap<claseHash, Integer>();
        dict.put(a1, 1);
        System.out.println(dict.get(a2));

    }
}

class claseHash {

    int a;
    String texto;

    public claseHash(int a, String texto) {
        this.a = a;
        this.texto = texto;
    }
//
//    @Override
//    public int hashCode() {
//        int hash = 5;
//        hash = 17 * hash + this.a;
//        hash = 17 * hash + (this.texto != null ? this.texto.hashCode() : 0);
//        return hash;
//    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final claseHash other = (claseHash) obj;
        if (this.a != other.a) {
            return false;
        }
        if ((this.texto == null) ? (other.texto != null) : !this.texto.equals(other.texto)) {
            return false;
        }
        return true;
    }

}

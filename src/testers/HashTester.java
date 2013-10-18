/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
    public void prueba()
    {
        claseHash a1 = new claseHash(2, "HOLA");
        claseHash a2 = new claseHash(2, "HOLA");
        System.out.println(a2.equals(a1));
        HashMap<claseHash, Integer> dict = new HashMap<claseHash,Integer>();
        dict.put(a1,1);
        System.out.println(dict.get(a2));
        
        
    }
}
class claseHash
        {
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

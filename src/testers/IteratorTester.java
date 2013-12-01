/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    public void test()
    {
        Iterator<Integer> a = iterador();
       while (a.hasNext())
       {
           System.out.println(a.next());
       }
    }
    public Iterator<Integer> iterador()
    {
        Iterator<Integer> a;
        a = new Iterator<Integer>() {
            int a=1;
            int b=0;
            int c=0;
            int contador=0;
            
            @Override
            public boolean hasNext() {
                return (contador<5);
            }

            @Override
            public Integer next() {
                contador++;
                c=a+b;
                a=b;
                b=c;
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

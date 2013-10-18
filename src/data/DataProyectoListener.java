/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;


/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public interface DataProyectoListener {
    /**
     *
     */
    public final int ADD=0;
    /**
     *
     */
    public final int MODIFY=1;
    /**
     *
     */
    public final int REMOVE=2;
    
    
    /**
     *
     * @param obj
     * @param type
     */
    public void dataEvent(Object obj,int type);
}

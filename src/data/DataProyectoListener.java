/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 * Interfaz para todo objeto que escuche eventos relativos a cambios en los
 * datos del proyecto
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public interface DataProyectoListener {

    public final int ADD = 0;
    public final int MODIFY = 1;
    public final int REMOVE = 2;

    /**
     * Evento disparado cuando halla cambios en los datos del proyecto
     *
     * @param obj Objeto modificado
     * @param type Tipo de modificación
     */
    public void dataEvent(Object obj, int type);
}

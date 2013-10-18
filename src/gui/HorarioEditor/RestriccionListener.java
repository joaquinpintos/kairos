/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.HorarioEditor;

import data.restricciones.Restriccion;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public interface RestriccionListener {
    /**
     *
     * @param r
     * @param needReinicializarDatos
     */
    public abstract void restrictionChanged(Restriccion r,Boolean needReinicializarDatos);
    
}

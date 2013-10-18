/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.HorarioEditor;

/**
 * Esta interfaz es un listener que escucha los posibles cambios de horario para
 * ver si necesita acualizar datos, recalcular pesos, etc.
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public interface HorarioListener {
    /**
     *
     */
    public void needUpdate();
    /**
     *
     */
    public void needRecalcularPesos();
}

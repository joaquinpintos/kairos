/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor;

import gui.AbstractMainWindow;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public interface DataGUIInterface {
    /**
     *
     */
    public void updateData();
    /**
     *
     * @param mainWindow
     */
    public void setMainWindow(AbstractMainWindow mainWindow);
    public void expandTrees();
}

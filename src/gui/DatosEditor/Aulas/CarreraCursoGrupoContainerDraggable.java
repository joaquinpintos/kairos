/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Aulas;

import data.aulas.CarreraCursoGrupoContainer;
import data.asignaturas.Grupo;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class CarreraCursoGrupoContainerDraggable implements Transferable{
        /**
     *
     */
    final static public DataFlavor MY_FLAVOR = new DataFlavor(CarreraCursoGrupoContainer.class, "Kairos/CarreraCursoGrupo");

    private CarreraCursoGrupoContainer cont;

    /**
     *
     * @param cont
     */
    public CarreraCursoGrupoContainerDraggable(CarreraCursoGrupoContainer cont) {
        this.cont = cont;
    }
     /**
     *
     * @param gr
     */
    public CarreraCursoGrupoContainerDraggable(Grupo gr) {
        this.cont = new CarreraCursoGrupoContainer(gr);
    }

    /**
     *
     * @return
     */
    public CarreraCursoGrupoContainer getContainer() {
        return cont;
    }
  @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{MY_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return (flavor.equals(MY_FLAVOR));

    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return cont;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Docencia;

import data.asignaturas.Tramo;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class TramoDraggable implements Transferable {

    /**
     *
     */
    final static public DataFlavor MY_FLAVOR = new DataFlavor(Tramo.class, "Kairos/Tramo");

    Tramo cont;

    /**
     *
     * @param cont
     */
    public TramoDraggable(Tramo cont) {
        this.cont = cont;
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

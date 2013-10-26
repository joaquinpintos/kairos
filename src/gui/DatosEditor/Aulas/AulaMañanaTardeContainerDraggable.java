/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Aulas;

import data.aulas.AulaMT;
import data.aulas.ListaAsignaciones;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class AulaMañanaTardeContainerDraggable implements Transferable {

    /**
     *
     */
    final static public DataFlavor MY_FLAVOR = new DataFlavor(AulaMT.class, "Kairos/AulaMañanaTardeContainer");

    private final AulaMT cont;

    /**
     *
     * @param cont
     */
    public AulaMañanaTardeContainerDraggable(AulaMT cont) {
        this.cont = cont;
    }

    public AulaMañanaTardeContainerDraggable(ListaAsignaciones la) {
        this.cont = new AulaMT(la.getAula(), la.esTarde());
    }


    /**
     *
     * @return
     */
    public AulaMT getContainer() {
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

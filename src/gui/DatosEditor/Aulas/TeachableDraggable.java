/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Aulas;
import data.asignaturas.Teachable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class TeachableDraggable implements Transferable{
        /**
     *
     */
    final static public DataFlavor MY_FLAVOR = new DataFlavor(Teachable.class, "Kairos/Teachable");

    private Teachable cont;

    /**
     *
     * @param cont
     */
    public TeachableDraggable(Teachable cont) {
        this.cont = cont;
    }

    /**
     *
     * @return
     */
    public Teachable getContainer() {
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

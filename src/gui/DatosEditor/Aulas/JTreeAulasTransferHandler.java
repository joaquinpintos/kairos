/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Aulas;

import data.aulas.ListaAsignaciones;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class JTreeAulasTransferHandler extends TransferHandler {

    @Override
    protected Transferable createTransferable(JComponent c) {
        AulaMañanaTardeContainerDraggable resul = null;
        JTree jtree = (JTree) c;
        Object value = jtree.getSelectionPath().getLastPathComponent();
        if (value instanceof ListaAsignaciones) {
            resul = new AulaMañanaTardeContainerDraggable((ListaAsignaciones) value);
        }
        return resul;
    }

    @Override
    public int getSourceActions(JComponent c) {
        return DnDConstants.ACTION_MOVE;
    }

    @Override
    protected void exportDone(JComponent source, Transferable data, int action) {
        //System.out.println("ExportDone");
        // Here you need to decide how to handle the completion of the transfer,
        // should you remove the item from the list or not...
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support) {
        return (support.getComponent() instanceof JTree) && support.isDataFlavorSupported(AulaMañanaTardeContainerDraggable.MY_FLAVOR);
    }
}

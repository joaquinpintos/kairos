/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Aulas;

import data.DataKairos;
import data.MyConstants;
import data.aulas.CarreraCursoGrupoContainer;
import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JTree;
import javax.swing.TransferHandler;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListGruposTransferHandler extends TransferHandler {

    @Override
    protected Transferable createTransferable(JComponent c) {
        CarreraCursoGrupoContainerDraggable resul = null;
        JList jlist = (JList) c;
        Object value = jlist.getSelectedValue();
        if (value instanceof CarreraCursoGrupoContainer) {
            CarreraCursoGrupoContainer a = (CarreraCursoGrupoContainer) value;
            System.out.println("[DRAG] a=" + a.hashCode());
            resul = new CarreraCursoGrupoContainerDraggable((CarreraCursoGrupoContainer) value);
        }
        return resul;
    }

    @Override
    public Image getDragImage() {
        return MyConstants.GRUPO_ICON.getImage();
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
        return (support.getComponent() instanceof JTree) && support.isDataFlavorSupported(CarreraCursoGrupoContainerDraggable.MY_FLAVOR);
    }
}

class JListGruposDropListener implements DropTargetListener {

    private JIntTreeAulas parent;
    private final DataKairos dk;

    public JListGruposDropListener(JIntTreeAulas parent, DataKairos dk) {
        this.dk = dk;
        this.parent = parent;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        try {
            CarreraCursoGrupoContainer grupoDragged = (CarreraCursoGrupoContainer) dtde.getTransferable().getTransferData(CarreraCursoGrupoContainerDraggable.MY_FLAVOR);
            if (grupoDragged.getAulaNombre() != null) {
                System.out.println("[DROP TO LISTA_AULAS] Grupo" + grupoDragged.getHash());
                dk.getDP().getDataAulas().quitaAsignacionGrupoCompletoFromAula(grupoDragged.getHash(), grupoDragged.getAulaNombre());
//                CarreraGrupoCursosNoAsignadosSimpleListModel model = (CarreraGrupoCursosNoAsignadosSimpleListModel) parent.getjListGrupos().getModel();
//                model.add(grupoDragged);
            }
            parent.updateData();
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(JIntTreeAulas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JIntTreeAulas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
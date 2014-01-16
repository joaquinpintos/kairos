/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Aulas;

import data.DataKairos;
import data.KairosCommand;
import data.asignaturas.Teachable;
import data.aulas.Aula;
import data.aulas.AulaMT;
import data.aulas.ListaAsignaciones;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class JTreeAulasDropListener implements DropTargetListener {

    JTree jTreeAulas;
    private final JIntTreeAulas parent;
    private final DataKairos dk;

    /**
     *
     * @param parent
     * @param dk
     */
    public JTreeAulasDropListener(JIntTreeAulas parent, DataKairos dk) {
        this.jTreeAulas = parent.getjTreeAulas();
        this.parent = parent;
        this.dk = dk;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        TreePath path = jTreeAulas.getPathForLocation(dtde.getLocation().x, dtde.getLocation().y);
        if (path != null) {
            if (path.getLastPathComponent() instanceof ListaAsignaciones) {
                jTreeAulas.setSelectionPath(path);
            } else {
                jTreeAulas.expandPath(path);

            }
        }


    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        //System.out.println("Drop!!");
        TreePath path = jTreeAulas.getPathForLocation(dtde.getLocation().x, dtde.getLocation().y);
        try {
            Object value = path.getLastPathComponent();
            if (value instanceof ListaAsignaciones) {
                ListaAsignaciones asignaciones = (ListaAsignaciones) value;//Objeto arrastrado
                Aula aula = (Aula) path.getParentPath().getLastPathComponent();//Aula a la que incluir

                Teachable teachableDragged;

                teachableDragged = (Teachable) dtde.getTransferable().getTransferData(TeachableDraggable.MY_FLAVOR);
                final AulaMT aulaMT = new AulaMT(aula, asignaciones.esTarde());
                KairosCommand cmd = dk.getController().getAsignarAulaCommand(aulaMT, teachableDragged);
                dk.getController().executeCommand(cmd);
//               teachableDragged.asignaAula(aulaMT);
//                parent.getjTreeGrupoCursos().updateUI();
//                parent.getjTreeAulas().updateUI();
            }
        } catch (UnsupportedFlavorException ex) {
           //NO hago nada,salvo escribir mensaje de error
            System.err.println("Error al hacer drop UnsupportedFlavorException");
        } catch (IOException ex) {
            Logger.getLogger(JIntTreeAulas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
        }

    }
}
//
//class JTreeAulasTransferHandler extends TransferHandler {
//
//    @Override
//    protected Transferable createTransferable(JComponent c) {
//        TeachableDraggable resul = null;
//        JTree jtree = (JTree) c;
//        Object value = jtree.getSelectionPath().getLastPathComponent();
//        if (value instanceof Teachable) {
//            resul = new TeachableDraggable((Teachable) value);
//        }
//        return resul;
//    }
//
//    @Override
//    public int getSourceActions(JComponent c) {
//        return DnDConstants.ACTION_MOVE;
//    }
//
//    @Override
//    protected void exportDone(JComponent source, Transferable data, int action) {
//        //System.out.println("ExportDone");
//        // Here you need to decide how to handle the completion of the transfer,
//        // should you remove the item from the list or not...
//    }
//
//    @Override
//    public boolean canImport(TransferHandler.TransferSupport support) {
//        return (support.getComponent() instanceof JTree) && support.isDataFlavorSupported(TeachableDraggable.MY_FLAVOR);
//    }
//}

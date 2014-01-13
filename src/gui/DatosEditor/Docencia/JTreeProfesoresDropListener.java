/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Docencia;

import data.DataKairos;
import data.asignaturas.Teachable;
import data.profesores.Profesor;
import gui.DatosEditor.Aulas.TeachableDraggable;
import gui.TreeAsignaturas;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.TreePath;

/**
 *
 * @author usuario
 */
public class JTreeProfesoresDropListener implements DropTargetListener {

    private final TreeProfesores parent;
    private final DataKairos dk;

    /**
     *
     * @param parent
     * @param dk
     */
    public JTreeProfesoresDropListener(TreeProfesores parent, DataKairos dk) {
        this.parent = parent;
        this.dk = dk;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        TreePath path = parent.getjTreeProfesores().getPathForLocation(dtde.getLocation().x, dtde.getLocation().y);
        if (path != null) {
            parent.getjTreeProfesores().setSelectionPath(path);
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
        Teachable teachable = null;
        try {
            teachable = (Teachable) dtde.getTransferable().getTransferData(TeachableDraggable.MY_FLAVOR);
            //Operaciones en nodo destino
            TreePath path = parent.getjTreeProfesores().getPathForLocation(dtde.getLocation().x, dtde.getLocation().y);
            if (path != null) {
                Object obj = path.getLastPathComponent();
                if (obj instanceof Profesor) {
                    Profesor prof = (Profesor) obj;
                    teachable.setDocente(prof);
                }
            }
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(JTreeProfesoresDropListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JTreeProfesoresDropListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassCastException ex) {
        }

    }

}

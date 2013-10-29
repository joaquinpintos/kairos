/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Docencia;

import data.DataKairos;
import data.asignaturas.Teachable;
import data.profesores.Profesor;
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
 * @author david
 */
public class JTreeAsignaturasDropListener implements DropTargetListener {

    private final TreeAsignaturas parent;
    private final DataKairos dk;

    public JTreeAsignaturasDropListener(TreeAsignaturas parent, DataKairos dk) {
        this.parent = parent;
        this.dk = dk;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        TreePath path = parent.getjTreeAsignaturas().getPathForLocation(dtde.getLocation().x, dtde.getLocation().y);
        if (path != null) {
            parent.getjTreeAsignaturas().setSelectionPath(path);
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
        Profesor profesor = null;
        try {
            profesor = (Profesor) dtde.getTransferable().getTransferData(ProfesorDraggable.MY_FLAVOR);
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(JTreeAsignaturasDropListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JTreeAsignaturasDropListener.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Operaciones en nodo destino
        TreePath path = parent.getjTreeAsignaturas().getPathForLocation(dtde.getLocation().x, dtde.getLocation().y);
        if (path != null) {
            Object obj = path.getLastPathComponent();
            if (obj instanceof Teachable) {
                Teachable teach=(Teachable) obj;
                teach.removeDocente();
                teach.setDocente(profesor);
            }
        }
    }

}

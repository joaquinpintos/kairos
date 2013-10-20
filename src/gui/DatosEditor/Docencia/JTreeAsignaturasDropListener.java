/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Docencia;

import data.DataKairos;
import data.asignaturas.Docentable;
import data.aulas.ListaAsignaciones;
import data.profesores.Profesor;
import java.awt.datatransfer.Transferable;
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
 * @author david
 */
public class JTreeAsignaturasDropListener implements DropTargetListener {

    private final JTree parent;
    private final DataKairos dk;

    public JTreeAsignaturasDropListener(JTree parent, DataKairos dk) {
        this.parent = parent;
        this.dk = dk;
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
          TreePath path = parent.getPathForLocation(dtde.getLocation().x, dtde.getLocation().y);
        if (path != null) {
                parent.setSelectionPath(path);
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
        Profesor profesor=null;
        try {
            profesor = (Profesor) dtde.getTransferable().getTransferData(ProfesorDraggable.MY_FLAVOR);
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(JTreeAsignaturasDropListener.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JTreeAsignaturasDropListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Operaciones en nodo destino
        TreePath path = parent.getPathForLocation(dtde.getLocation().x, dtde.getLocation().y);
        Object obj=path.getLastPathComponent();
        if (obj instanceof Docentable) 
        {
            ((Docentable)obj).setDocente(profesor);
        }
        parent.updateUI();
    }

}

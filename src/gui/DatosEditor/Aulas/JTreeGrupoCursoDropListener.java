/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Aulas;

import data.asignaturas.Teachable;
import data.aulas.Aula;
import data.aulas.AulaMT;
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
public class JTreeGrupoCursoDropListener implements DropTargetListener {

    JIntTreeAulas parent;
    JTree jTreeGrupoCursos;

    public JTreeGrupoCursoDropListener(JIntTreeAulas parent) {
        this.parent = parent;
        jTreeGrupoCursos = parent.getjTreeGrupoCursos();
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        TreePath path = jTreeGrupoCursos.getPathForLocation(dtde.getLocation().x, dtde.getLocation().y);
        if (path != null) {
            jTreeGrupoCursos.setSelectionPath(path);
            jTreeGrupoCursos.expandPath(path);
        }
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        //System.out.println("Drop!!");
        TreePath path = jTreeGrupoCursos.getPathForLocation(dtde.getLocation().x, dtde.getLocation().y);
        try {
            Object value = path.getLastPathComponent();
            if (value instanceof Teachable) {
                Teachable teachable = (Teachable) value;

                AulaMT aulaMañanaTardeContainer;

                aulaMañanaTardeContainer = (AulaMT) dtde.getTransferable().getTransferData(TeachableDraggable.MY_FLAVOR);
                Aula aula = aulaMañanaTardeContainer.getAula();
                boolean tarde = aulaMañanaTardeContainer.getEsTarde();

                teachable.asignaAula(new AulaMT(aula, tarde));
                parent.getjTreeGrupoCursos().updateUI();
                parent.getjTreeAulas().updateUI();
            }
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(JIntTreeAulas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JIntTreeAulas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
        }

    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }
}

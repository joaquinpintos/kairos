/*
 * Copyright (C) 2014 David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package gui.DatosEditor.Aulas;

import data.DataKairos;
import data.KairosCommand;
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
    private final DataKairos dk;

    /**
     *
     * @param parent
     */
    public JTreeGrupoCursoDropListener(JIntTreeAulas parent, DataKairos dk) {
        this.parent = parent;
        jTreeGrupoCursos = parent.getjTreeGrupoCursos();
        this.dk = dk;
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
                final AulaMT aulaMT = new AulaMT(aula, tarde);

//                teachable.asignaAula(aulaMT);
                KairosCommand cmd = dk.getController().getAsignarAulaCommand(aulaMT, teachable);
                dk.getController().executeCommand(cmd);
                parent.getjTreeGrupoCursos().updateUI();
                parent.getjTreeAulas().updateUI();
            }
        } catch (UnsupportedFlavorException ex) {
            Logger.getLogger(JIntTreeAulas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JIntTreeAulas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
        } catch (ClassCastException ex) {
        }

    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
    }
}

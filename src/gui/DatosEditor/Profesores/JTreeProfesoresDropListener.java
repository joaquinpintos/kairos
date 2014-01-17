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
package gui.DatosEditor.Profesores;

import data.DataKairos;
import data.KairosCommand;
import data.asignaturas.Teachable;
import data.profesores.Profesor;
import gui.DatosEditor.Aulas.TeachableDraggable;
import gui.DatosEditor.Docencia.TreeProfesores;
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
        Teachable teachable;
        try {
            teachable = (Teachable) dtde.getTransferable().getTransferData(TeachableDraggable.MY_FLAVOR);
            //Operaciones en nodo destino
            TreePath path = parent.getjTreeProfesores().getPathForLocation(dtde.getLocation().x, dtde.getLocation().y);
            if (path != null) {
                Object obj = path.getLastPathComponent();
                if (obj instanceof Profesor) {
                    Profesor prof = (Profesor) obj;
                    KairosCommand cmd = dk.getController().getAsignarDocenciaCommand(prof, teachable);
                    dk.getController().executeCommand(cmd);
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

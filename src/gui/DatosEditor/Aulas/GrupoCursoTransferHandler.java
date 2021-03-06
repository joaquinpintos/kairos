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

import data.MyConstants;
import data.asignaturas.Teachable;
import java.awt.Image;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class GrupoCursoTransferHandler extends TransferHandler {

    @Override
    protected Transferable createTransferable(JComponent c) {
        TeachableDraggable resul = null;
        JTree jTree = (JTree) c;
        Object value = jTree.getSelectionPath().getLastPathComponent();
        if (value instanceof Teachable) {
            Teachable a = (Teachable) value;
            resul = new TeachableDraggable((Teachable) value);
        }
        return resul;
    }

    @Override
    public Image getDragImage() {
        MyConstants mc = new MyConstants();
        return mc.GRUPO_ICON.getImage();
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
        return (support.getComponent() instanceof JTree) && support.isDataFlavorSupported(TeachableDraggable.MY_FLAVOR);
    }
}

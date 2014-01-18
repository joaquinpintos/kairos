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

import data.asignaturas.Tramo;
import data.profesores.Profesor;

/**
 * Código ligeramente modificado de
 * http://www.adrianwalker.org/2012/04/filtered-jtree.html
 *
 */
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public final class FilteredTreeModel implements TreeModel {

    private final TreeModel treeModel;
    private final Profesor profesor;

    public FilteredTreeModel(final TreeModel treeModel, Profesor profesor) {
        this.treeModel = treeModel;
        this.profesor = profesor;
    }

    public TreeModel getTreeModel() {
        return treeModel;
    }

    private boolean recursiveMatch(final Object node) {
        boolean matches;
        if ((node instanceof Tramo) && (node != null)) {
            Tramo tr = (Tramo) node;
            matches = profesor.equals(tr.getDocente());
        } else {
            matches = false;
        }

        int childCount = treeModel.getChildCount(node);
        for (int i = 0; i < childCount; i++) {
            Object child = treeModel.getChild(node, i);
            matches |= recursiveMatch(child);
        }
        return matches;
    }

    @Override
    public Object getRoot() {
        return treeModel.getRoot();
    }

    @Override
    public Object getChild(final Object parent, final int index) {
        int count = 0;
        int childCount = treeModel.getChildCount(parent);
        for (int i = 0; i < childCount; i++) {
            Object child = treeModel.getChild(parent, i);
            if (recursiveMatch(child)) {
                if (count == index) {
                    return child;
                }
                count++;
            }
        }
        return null;
    }

    @Override
    public int getChildCount(final Object parent) {
        int count = 0;
        int childCount = treeModel.getChildCount(parent);
        for (int i = 0; i < childCount; i++) {
            Object child = treeModel.getChild(parent, i);
            if (recursiveMatch(child)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean isLeaf(final Object node) {
        return treeModel.isLeaf(node);
    }

    @Override
    public void valueForPathChanged(final TreePath path, final Object newValue) {
        treeModel.valueForPathChanged(path, newValue);
    }

    @Override
    public int getIndexOfChild(final Object parent, final Object childToFind) {
        int childCount = treeModel.getChildCount(parent);
        for (int i = 0; i < childCount; i++) {
            Object child = treeModel.getChild(parent, i);
            if (recursiveMatch(child)) {
                if (childToFind.equals(child)) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public void addTreeModelListener(final TreeModelListener l) {
        treeModel.addTreeModelListener(l);
    }

    @Override
    public void removeTreeModelListener(final TreeModelListener l) {
        treeModel.removeTreeModelListener(l);
    }
}

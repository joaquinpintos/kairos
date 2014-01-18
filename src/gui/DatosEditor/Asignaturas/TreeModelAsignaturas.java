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
package gui.DatosEditor.Asignaturas;

import data.DataKairos;
import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.DataAsignaturas;
import data.asignaturas.Grupo;
import data.asignaturas.Tramo;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class TreeModelAsignaturas implements TreeModel {

    private final DataKairos dk;
    private boolean llegarHastaTramos = false;

    /**
     *
     * @param dk
     */
    public TreeModelAsignaturas(DataKairos dk) {
        this.dk = dk;
    }

    @Override
    public Object getRoot() {
        return dk.getDP().getDataAsignaturas();
    }

    @Override
    public Object getChild(Object parent, int index) {
        Object resul = null;
        if (parent instanceof DataAsignaturas) {
            DataAsignaturas data = (DataAsignaturas) parent;
            resul = data.getCarreras().get(index);
        }
        if (parent instanceof Carrera) {
            Carrera data = (Carrera) parent;
            resul = data.getCursos().get(index);
        }
        if (parent instanceof Curso) {
            Curso data = (Curso) parent;
            resul = data.getAsignaturas().get(index);
        }
        if (parent instanceof Asignatura) {
            Asignatura data = (Asignatura) parent;
            resul = data.getGrupos().get(index);
        }
        if ((llegarHastaTramos) && (parent instanceof Grupo)) {
            Grupo data = (Grupo) parent;
            resul = data.getTramosGrupoCompleto().getTramos().get(index);
        }

        return resul;
    }

    @Override
    public int getChildCount(Object parent) {
        int resul = 0;
        if (parent instanceof DataAsignaturas) {
            DataAsignaturas data = (DataAsignaturas) parent;
            resul = data.getCarreras().size();
        }
        if (parent instanceof Carrera) {
            Carrera data = (Carrera) parent;
            resul = data.getCursos().size();
        }
        if (parent instanceof Curso) {
            Curso data = (Curso) parent;
            resul = data.getAsignaturas().size();
        }
        if (parent instanceof Asignatura) {
            Asignatura data = (Asignatura) parent;
            resul = data.getGrupos().getGrupos().size();
        }
        if ((llegarHastaTramos) && (parent instanceof Grupo)) {
            Grupo data = (Grupo) parent;
            resul = data.getTramosGrupoCompleto().getTramos().size();
        }
        return resul;
    }

    @Override
    public boolean isLeaf(Object node) {
        return (!llegarHastaTramos) && (node instanceof Grupo) || (llegarHastaTramos && (node instanceof Tramo));
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        int resul = -1;
        if (parent instanceof DataAsignaturas) {
            DataAsignaturas data = (DataAsignaturas) parent;
            resul = data.getCarreras().indexOf(child);
        }
        if (parent instanceof Carrera) {
            Carrera data = (Carrera) parent;
            resul = data.getCursos().indexOf(child);
        }
        if (parent instanceof Curso) {
            Curso data = (Curso) parent;
            resul = data.getAsignaturas().indexOf(child);
        }
        if (parent instanceof Asignatura) {
            Asignatura data = (Asignatura) parent;
            resul = data.getGrupos().getGrupos().indexOf(child);
        }
        //Grupo -> -1
        return resul;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
    }

    /**
     *
     * @return
     */
    public boolean isLlegarHastaTramos() {
        return llegarHastaTramos;
    }

    /**
     *
     * @param llegarHastaTramos
     */
    public void setLlegarHastaTramos(boolean llegarHastaTramos) {
        this.llegarHastaTramos = llegarHastaTramos;
    }
}

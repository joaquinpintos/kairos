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
package gui.HorarioEditor;

import data.DataKairos;
import data.MyConstants;
import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.DataAsignaturas;
import data.asignaturas.Grupo;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class TreeCellRendererAsignaturasHorasEfectivas extends JLabel implements TreeCellRenderer {

    ArrayList<TreeModelListener> listeners;
    private final HashMap<Grupo, Integer> horas;
    private final DataKairos dk;

    /**
     *
     * @param horas
     */
    public TreeCellRendererAsignaturasHorasEfectivas(DataKairos dk,HashMap<Grupo, Integer> horas) {
        super();
        this.horas=horas;
        this.setOpaque(true);
        this.dk=dk;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof DataAsignaturas) {
            this.setIcon(null);
            this.setText(value.toString());
        }
        if (value instanceof Carrera) {
            this.setIcon(dk.mc.CARRERA_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setText(value.toString());
        }
        if (value instanceof Curso) {
            this.setIcon(dk.mc.CURSO_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setText(value.toString());
        }
        if (value instanceof Asignatura) {
            this.setIcon(dk.mc.ASIGNATURA_ICON);
            this.setFont(MyConstants.NORMAL_FONT);
            this.setText(value.toString());
        }
        if (value instanceof Grupo) {
            this.setIcon(dk.mc.GRUPO_ICON);
            this.setFont(MyConstants.NORMAL_FONT);
            this.setText(value.toString());
        }

        if (selected) {
            this.setBackground(MyConstants.SELECTED_ITEM_LIST);
        } else {
            this.setBackground(Color.white);
        }
        
        return this;
    }
}

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
import data.MyConstants;
import data.asignaturas.Grupo;
import data.asignaturas.GrupoCursos;
import data.asignaturas.ListaGrupoCursos;
import data.asignaturas.Tramo;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class JTreeGrupoCursosRenderer extends JLabel implements TreeCellRenderer {

    private final DataKairos dk;

    /**
     *
     * @param dk
     */
    public JTreeGrupoCursosRenderer(DataKairos dk) {
        this.dk = dk;
        this.setOpaque(true);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (selected) {
            this.setBackground(MyConstants.SELECTED_ITEM_LIST);
        } else {
            this.setBackground(MyConstants.UNSELECTED_ITEM_LIST);
        }
        if (value instanceof ListaGrupoCursos) {
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setIcon(null);
            this.setText("Grupos");
        }
        if (value instanceof GrupoCursos) {
            GrupoCursos gc = (GrupoCursos) value;
            this.setIcon(dk.mc.AULA_ICON);//TODO: Icono incorrecto
            this.setFont(MyConstants.NEGRITA_FONT);
            if (gc.isAlgunoSinAula()) {
                this.setForeground(MyConstants.CONFLICTIVE_ITEM);
            } else {
                this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
            }
            this.setText(gc.toString());
        }
        if (value instanceof Grupo) {
            Grupo gr = (Grupo) value;
            this.setIcon(dk.mc.ASIGNATURA_ICON);//TODO: Icono incorrecto
            this.setFont(MyConstants.NEGRITA_FONT);
            if (gr.isAlgunoSinAula()) {
                this.setForeground(MyConstants.CONFLICTIVE_ITEM);
            } else {
                this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
            };
            try {
                this.setText(gr.getParent().getNombre());
            } catch (Exception e) {
            }
        }
        if (value instanceof Tramo) {
            Tramo tr = (Tramo) value;
            this.setIcon(dk.mc.TRAMO_ICON);//TODO: Icono incorrecto
            this.setFont(MyConstants.NORMAL_FONT);
            String aula;
            if (tr.tieneAula()) {
                aula = " " + tr.getAulaMT().toString();
                this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
            } else {
                aula = "";
                this.setForeground(MyConstants.CONFLICTIVE_ITEM);
            }

            this.setText(tr.toString() + aula);
        }
        return this;
    }

}

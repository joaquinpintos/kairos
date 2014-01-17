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
package data.profesores;

import data.DataKairos;
import data.MyConstants;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class TreeCellRendererProfesores extends JLabel implements TreeCellRenderer {

    private boolean mostrarHorasDocencia;
    private final DataKairos dk;

    /**
     *
     * @param dk
     */
    public TreeCellRendererProfesores(DataKairos dk) {
        this.mostrarHorasDocencia = true;
        this.setOpaque(true);
        this.dk=dk;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (selected) {
            this.setBackground(MyConstants.SELECTED_ITEM_LIST);
        } else {
            this.setBackground(MyConstants.UNSELECTED_ITEM_LIST);
        }

        if (value instanceof Profesor) {
            renderProfesor((Profesor) value);
        } else {
            setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
            this.setIcon(dk.mc.DEPARTAMENTO_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setText(value.toString());
        }

        return this;
    }

    private void renderProfesor(Profesor p) {
        this.setIcon(dk.mc.PROFESOR_ICON);
        this.setFont(MyConstants.NORMAL_FONT);
        String texto = p.toString();

        if (mostrarHorasDocencia) {
            double horas = p.getHorasDocencia();
            if (horas > 0) {
                setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
            } else {
                setForeground(MyConstants.CONFLICTIVE_ITEM);
            }
            texto += ": " + horas + "h";
        }
        this.setText(texto);
    }

    /**
     *
     * @return
     */
    public boolean isMostrarHorasDocencia() {
        return mostrarHorasDocencia;
    }

    /**
     *
     * @param mostrarHorasDocencia
     */
    public void setMostrarHorasDocencia(boolean mostrarHorasDocencia) {
        this.mostrarHorasDocencia = mostrarHorasDocencia;
    }
}

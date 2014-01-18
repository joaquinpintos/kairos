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

import data.AcademicCalendar;
import data.DataKairos;
import data.MyConstants;
import data.aulas.Aula;
import data.aulas.DataAulas;
import data.aulas.ListaAsignaciones;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class JTreeAulasRenderer extends JLabel implements TreeCellRenderer {

    private final DataKairos dk;

    /**
     *
     * @param dk
     */
    public JTreeAulasRenderer(DataKairos dk) {
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
        if (value instanceof DataAulas) {
            this.setIcon(null);
            this.setText("Datos de aulas");
        }


        if (value instanceof Aula) {
            this.setIcon(dk.mc.AULA_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setText("Aula " + ((Aula) value).getNombre());
        }

        if (value instanceof ListaAsignaciones) {
            ListaAsignaciones lasig = (ListaAsignaciones) value;
            this.setIcon(dk.mc.TRAMO_ICON);
            AcademicCalendar cal = dk.getDP().getAcademicCalendar();
            final double horasOcupadas = lasig.getHorasOcupadas();
            this.setText(lasig.toString() + "  (" + (cal.getTotalHorasLectivasPorSemana(lasig.esTarde()) - horasOcupadas) + " horas libres)");
            if ((cal.getTotalHorasLectivasPorSemana(lasig.esTarde()) - horasOcupadas) < 0) {
                 this.setForeground(Color.RED);
            } else {
                this.setForeground(Color.BLACK);
            }
            this.setFont(MyConstants.NEGRITA_FONT);
        }
        return this;
    }
}

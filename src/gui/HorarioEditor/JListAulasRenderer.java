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

import data.MyConstants;
import data.aulas.AulaMT;
import data.horarios.HorarioItem;
import java.awt.Color;
import java.awt.Component;
import static java.awt.Component.LEFT_ALIGNMENT;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class JListAulasRenderer extends JLabel implements ListCellRenderer<AulaMT> {

    Border borderSelectedConflictivo;
    Border borderAulaSelected;

    public JListAulasRenderer() {
        //super(MyConstants.AULA_ICON);
        super();
        MyConstants mc = new MyConstants();
        this.setIcon(mc.AULA_ICON);
        this.setOpaque(true);
        this.setAlignmentX(LEFT_ALIGNMENT);
        this.setFont(MyConstants.NORMAL_FONT);
        borderSelectedConflictivo = BorderFactory.createLineBorder(Color.RED, 3);
        borderAulaSelected = BorderFactory.createLineBorder(Color.BLACK, 2);
    }

    @Override
    public Component getListCellRendererComponent(JList list, AulaMT data, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
//            this.setBackground(MyConstants.SELECTED_ITEM_LIST);
            this.setBorder(borderAulaSelected);
        } else {
//            this.setBackground(Color.WHITE);
            this.setBorder(null);
        }

        this.setText(data.toString());
//        if (data.getTieneSegmentosConflictivos()) {
//            this.setForeground(MyConstants.CONFLICTIVE_ITEM);
//        } else {
//            this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
//        }
        switch (data.getTieneSegmentosConflictivos()) {
            case HorarioItem.NO_MARK:
                this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
//                this.setBorder(null);
                this.setBackground(Color.white);
                break;
            case HorarioItem.SIMPLE_MARK:
                this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
//                this.setBorder(null);
                this.setBackground(MyConstants.NON_SELECTED_CONFLICTIVE_ITEM);
                break;
            case HorarioItem.DOUBLE_MARK:
                this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
//                this.setBorder(borderSelectedConflictivo);
                this.setBackground(MyConstants.SELECTED_CONFLICTIVE_ITEM);
                break;

        }
        return this;
    }

}

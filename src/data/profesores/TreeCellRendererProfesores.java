/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.profesores;

import data.MyConstants;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class TreeCellRendererProfesores extends JLabel implements TreeCellRenderer {


    /**
     *
     */
    public TreeCellRendererProfesores() {
        this.setOpaque(true);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (selected) {
            this.setBackground(MyConstants.SELECTED_ITEM_LIST);
        } else {
            this.setBackground(MyConstants.UNSELECTED_ITEM_LIST);
        }

        if (value instanceof Profesor) {
            this.setIcon(MyConstants.PROFESOR_ICON);
            this.setFont(MyConstants.NORMAL_FONT);
        } else {
            this.setIcon(MyConstants.DEPARTAMENTO_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
        }
        this.setText(value.toString());
        return this;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Asignaturas;

import data.MyConstants;
import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.DataAsignaturas;
import data.asignaturas.Grupo;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeCellRenderer;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class TreeCellRendererAsignaturas extends JLabel implements TreeCellRenderer {

    ArrayList<TreeModelListener> listeners;

    /**
     *
     */
    public TreeCellRendererAsignaturas() {
        super();
        this.setOpaque(true);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof DataAsignaturas) {
            this.setIcon(null);
        }
        if (value instanceof Carrera) {
            this.setIcon(MyConstants.CARRERA_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
        }
        if (value instanceof Curso) {
            this.setIcon(MyConstants.CURSO_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
        }
        if (value instanceof Asignatura) {
            this.setIcon(MyConstants.ASIGNATURA_ICON);
            this.setFont(MyConstants.NORMAL_FONT);
        }
        if (value instanceof Grupo) {
            this.setIcon(MyConstants.GRUPO_ICON);
            this.setFont(MyConstants.NORMAL_FONT);
        }

        if (selected) {
            this.setBackground(MyConstants.SELECTED_ITEM_LIST);
        } else {
            this.setBackground(Color.white);
        };
        this.setText(value.toString());
        return this;
    }
}

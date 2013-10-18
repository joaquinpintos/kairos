/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.HorarioEditor;

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

    /**
     *
     * @param horas
     */
    public TreeCellRendererAsignaturasHorasEfectivas(HashMap<Grupo, Integer> horas) {
        super();
        this.horas=horas;
        this.setOpaque(true);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof DataAsignaturas) {
            this.setIcon(null);
            this.setText(value.toString());
        }
        if (value instanceof Carrera) {
            this.setIcon(MyConstants.CARRERA_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setText(value.toString());
        }
        if (value instanceof Curso) {
            this.setIcon(MyConstants.CURSO_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setText(value.toString());
        }
        if (value instanceof Asignatura) {
            this.setIcon(MyConstants.ASIGNATURA_ICON);
            this.setFont(MyConstants.NORMAL_FONT);
            this.setText(value.toString());
        }
        if (value instanceof Grupo) {
            this.setIcon(MyConstants.GRUPO_ICON);
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

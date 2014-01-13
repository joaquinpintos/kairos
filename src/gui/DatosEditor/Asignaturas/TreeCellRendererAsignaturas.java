/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Asignaturas;

import data.DataKairos;
import data.MyConstants;
import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.DataAsignaturas;
import data.asignaturas.Grupo;
import data.asignaturas.Tramo;
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
    private final DataKairos dk;
    private boolean printTeachersName;
    private String personalizedRoot = null;
    private boolean printTotalHorasGrupos;

    public boolean isPrintTotalHorasGrupos() {
        return printTotalHorasGrupos;
    }

    public void setPrintTotalHorasGrupos(boolean printTotalHorasGrupos) {
        this.printTotalHorasGrupos = printTotalHorasGrupos;
    }

    /**
     *
     * @param dk
     */
    public TreeCellRendererAsignaturas(DataKairos dk) {
        super();
        this.printTeachersName = true;
        this.printTotalHorasGrupos = true;
        this.setOpaque(true);
        this.dk = dk;
    }

    public boolean isPrintTeachersName() {
        return printTeachersName;
    }

    public void setPrintTeachersName(boolean printTeachersName) {
        this.printTeachersName = printTeachersName;
    }

    public String getPersonalizedRoot() {
        return personalizedRoot;
    }

    public void setPersonalizedRoot(String personalizedRoot) {
        this.personalizedRoot = personalizedRoot;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (value instanceof DataAsignaturas) {
            this.setIcon(null);
            if (personalizedRoot == null) {
                this.setText(value.toString());
            } else {
                this.setText(personalizedRoot);
            }
        }
        if (value instanceof Carrera) {
            this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
            this.setIcon(dk.mc.CARRERA_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setText(value.toString());
        }
        if (value instanceof Curso) {
            this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
            this.setIcon(dk.mc.CURSO_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setText(value.toString());
        }
        if (value instanceof Asignatura) {
//            Asignatura asig=(Asignatura) value;
//            this.setBackground(asig.getColorEnTablaDeHorarios());
            this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
            this.setIcon(dk.mc.ASIGNATURA_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setText(value.toString());
        }
        if (value instanceof Grupo) {
            this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
            this.setIcon(dk.mc.GRUPO_ICON);
            this.setFont(MyConstants.NORMAL_FONT);
            if (printTotalHorasGrupos) {
                this.setText(value.toString());
            } else {
                this.setText(((Grupo) value).toStringSinTotales());
            }
        }
        if (value instanceof Tramo) {
            Tramo tr = (Tramo) value;
            this.setIcon(dk.mc.TRAMO_ICON);

            if (tr.getDocente() != null) {
                this.setFont(MyConstants.NORMAL_FONT);
                this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
                if (printTeachersName) {
                    this.setText(tr.toString() + ". Docente: " + tr.getDocente());
                } else {
                    this.setText(tr.toString());
                }
            } else {
                this.setFont(MyConstants.NEGRITA_FONT);
                this.setForeground(MyConstants.CONFLICTIVE_ITEM);
                this.setText(tr.toString());
            }
        }

        if (selected) {
            this.setBackground(MyConstants.SELECTED_ITEM_LIST);
        } else {
            this.setBackground(Color.white);
        };

        return this;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
            if (gc.algunoSinAula()) {
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
            if (gr.algunoSinAula()) {
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

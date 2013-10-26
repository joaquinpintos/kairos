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
import data.asignaturas.GrupoSinAula;
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
public class JTreeGrupoCursosSinAulaRenderer extends JLabel implements TreeCellRenderer {

    private final DataKairos dk;

    /**
     *
     * @param dk
     */
    public JTreeGrupoCursosSinAulaRenderer(DataKairos dk) {
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
            GrupoCursos gc = (GrupoCursos)value;
            this.setIcon(MyConstants.AULA_ICON);//TODO: Icono incorrecto
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setText(gc.toString()+" "+gc.algunoSinAula());
        }
        if (value instanceof Grupo) {
            Grupo gr = (Grupo)value;
            this.setIcon(MyConstants.ASIGNATURA_ICON);//TODO: Icono incorrecto
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setText(gr.getParent().getNombre()+" "+gr.algunoSinAula());
        }
          if (value instanceof Tramo) {
            Tramo tr = (Tramo)value;
            this.setIcon(MyConstants.TRAMO_ICON);//TODO: Icono incorrecto
            this.setFont(MyConstants.NORMAL_FONT);
            this.setText(tr.toString()+" "+!tr.tieneAula());
        }
        return this;
    }

}

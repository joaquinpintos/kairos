/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Aulas;

import data.CalendarioAcademico;
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
            this.setIcon(MyConstants.AULA_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setText("Aula " + ((Aula) value).getNombre());
        }

        if (value instanceof ListaAsignaciones) {
            ListaAsignaciones lasig = (ListaAsignaciones) value;
            this.setIcon(MyConstants.CLOCK_ICON);
            CalendarioAcademico cal = dk.getDP().getCalendarioAcadémico();
            this.setText(lasig.toString() + "  (" + (cal.getTotalHorasLectivasPorSemana(lasig.esTarde()) - lasig.getHorasOcupadas()) + " horas libres)");
            if ((cal.getTotalHorasLectivasPorSemana(lasig.esTarde()) - lasig.getHorasOcupadas()) < 0) {
                 this.setForeground(Color.RED);
            } else {
                this.setForeground(Color.BLACK);
            }
            this.setFont(MyConstants.NEGRITA_FONT);
        }
        return this;
    }
}

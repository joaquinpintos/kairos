/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.profesores;

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

    /**
     *
     */
    public TreeCellRendererProfesores() {
        this.mostrarHorasDocencia = false;
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
            renderProfesor((Profesor) value);
        } else {
            setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
            this.setIcon(MyConstants.DEPARTAMENTO_ICON);
            this.setFont(MyConstants.NEGRITA_FONT);
            this.setText(value.toString());
        }

        return this;
    }

    private void renderProfesor(Profesor p) {
        this.setIcon(MyConstants.PROFESOR_ICON);
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

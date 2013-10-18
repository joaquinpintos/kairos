/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Restricciones;

import data.MyConstants;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import data.restricciones.Restriccion;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class RestriccionListRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = -7799441088157759804L;
    private JLabel label;
    private Color textSelectionColor = Color.BLACK;
    private Color textNonSelectionColor = Color.BLACK;
    private ImageIcon[] colorIcons;
    private boolean muestraMensajesDeError = false;
    private boolean descripcionesCortas = false;

    /**
     *
     */
    @SuppressWarnings("empty-statement")
    public RestriccionListRenderer() {
        label = new JLabel();
        label.setOpaque(true);
        colorIcons = new ImageIcon[]{MyConstants.RED_ICON, MyConstants.YELLOW_ICON, MyConstants.GREEN_ICON};
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean selected, boolean expanded) {
        Restriccion r = (Restriccion) value;

        if (descripcionesCortas) {
            muestraDescripcionCorta(r, selected);
        } else {
            muestraDescripcionLarga(r, selected);
        }

        return label;
    }

    /**
     *
     * @param r
     * @param selected
     */
    public void muestraDescripcionLarga(Restriccion r, boolean selected) {
        label.setFont(MyConstants.NEGRITA_FONT);
        label.setIcon(colorIcons[r.getImportancia() - 1]);
        if (muestraMensajesDeError) {
            label.setText(r.getMensajeError());
        } else {
            label.setText(r.descripcion());
        }
        label.setToolTipText("Restricción");

        if (selected) {
            label.setBackground(MyConstants.SELECTED_ITEM_LIST);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(MyConstants.UNSELECTED_ITEM_LIST);
            label.setForeground(textNonSelectionColor);
        }
    }

    /**
     *
     * @param r
     * @param selected
     */
    public void muestraDescripcionCorta(Restriccion r, boolean selected) {
        label.setFont(MyConstants.NORMAL_FONT);
        label.setIcon(null);
        label.setText(r.descripcionCorta());
        label.setToolTipText(r.mensajeDeAyuda());

        if (selected) {
            label.setBackground(MyConstants.SELECTED_ITEM_LIST);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(MyConstants.UNSELECTED_ITEM_LIST);
            label.setForeground(textNonSelectionColor);
        }
    }

    /**
     *
     * @return
     */
    public boolean isMuestraMensajesDeError() {
        return muestraMensajesDeError;
    }

    /**
     *
     * @param muestraMensajesDeError
     */
    public void setMuestraMensajesDeError(boolean muestraMensajesDeError) {
        this.muestraMensajesDeError = muestraMensajesDeError;
    }

    /**
     *
     * @return
     */
    public boolean isDescripcionesCortas() {
        return descripcionesCortas;
    }

    /**
     *
     * @param descripcionesCortas
     */
    public void setDescripcionesCortas(boolean descripcionesCortas) {
        this.descripcionesCortas = descripcionesCortas;
    }
}
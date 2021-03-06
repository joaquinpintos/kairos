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
    private final JLabel label;
    private final Color textSelectionColor = Color.BLACK;
    private final Color textNonSelectionColor = Color.BLACK;
    private final ImageIcon[] colorIcons;
    private boolean muestraMensajesDeError = false;
    private boolean descripcionesCortas = false;

    /**
     *
     */
    @SuppressWarnings("empty-statement")
    public RestriccionListRenderer() {
        label = new JLabel();
        label.setOpaque(true);
        MyConstants mc = new MyConstants();
        colorIcons = new ImageIcon[]{mc.RED_ICON, mc.YELLOW_ICON, mc.GREEN_ICON};
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
        label.setFont(MyConstants.NORMAL_FONT);
        int level = r.getLevel();
        if (level<1) level=1;
        if (level>3) level=3;
        label.setIcon(colorIcons[level-1]);
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

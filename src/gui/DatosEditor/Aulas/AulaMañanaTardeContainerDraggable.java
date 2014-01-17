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
package gui.DatosEditor.Aulas;

import data.aulas.AulaMT;
import data.aulas.ListaAsignaciones;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class AulaMañanaTardeContainerDraggable implements Transferable {

    /**
     *
     */
    final static public DataFlavor MY_FLAVOR = new DataFlavor(AulaMT.class, "Kairos/AulaMañanaTardeContainer");

    private final AulaMT cont;

    /**
     *
     * @param cont
     */
    public AulaMañanaTardeContainerDraggable(AulaMT cont) {
        this.cont = cont;
    }

    /**
     *
     * @param la
     */
    public AulaMañanaTardeContainerDraggable(ListaAsignaciones la) {
        this.cont = new AulaMT(la.getAula(), la.esTarde());
    }


    /**
     *
     * @return
     */
    public AulaMT getContainer() {
        return cont;
    }

    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[]{MY_FLAVOR};
    }

    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return (flavor.equals(MY_FLAVOR));

    }

    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
        return cont;
    }

}

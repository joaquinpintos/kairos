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
package gui.HorarioEditor;

import data.DataKairos;
import data.DataProyectoListener;
import data.aulas.AulaMT;
import data.horarios.HorarioItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.AbstractListModel;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */

public class JListAulasModel extends AbstractListModel<AulaMT> implements DataProyectoListener {

    private final DataKairos dk;

    /**
     *
     * @param dk
     */
    public JListAulasModel(DataKairos dk) {
        this.dk = dk;

    }

    @Override
    public int getSize() {
        return dk.getDP().getDataAulas().getAulaContainers().size();
    }

    @Override
    public AulaMT getElementAt(int index) {
        return dk.getDP().getDataAulas().getAulaContainers().get(index);
    }

    /**
     *
     * @param casillasConflictivas
     * @param markType
     */
    public void marcaAulasConSegmentosConflictivos(HashMap<String, HashSet<Integer>> casillasConflictivas, int markType) {
        //Primero desmarco todas las aulas como conflictivas
        HashMap<String, AulaMT> hm = dk.getDP().getDataAulas().getMapHashToContainers();
        for (String hashAula : casillasConflictivas.keySet()) {//Tengo un bucle con los hashAula 11@M 11@T etc.
            if (!casillasConflictivas.get(hashAula).isEmpty()) {
                hm.get(hashAula).setTieneSegmentosConflictivos(markType);
            }
        }
    }

    /**
     *
     */
    public void clearConflictivos() {
        ArrayList<AulaMT> aulaContainers = dk.getDP().getDataAulas().getAulaContainers();
        for (AulaMT a : aulaContainers) {
            a.setTieneSegmentosConflictivos(HorarioItem.NO_MARK);
        }
    }

    /**
     *
     * @param obj
     * @param type
     */
    @Override
    public void dataEvent(Object obj, int type) {
        dk.getDP().getDataAulas().buildArrayAulaContainers();
    }
}
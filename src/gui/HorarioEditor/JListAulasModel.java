/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.HorarioEditor;

import data.DataKairos;
import data.DataProyectoListener;
import data.aulas.AulaMañanaTardeContainer;
import data.horarios.HorarioItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import javax.swing.AbstractListModel;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */

public class JListAulasModel extends AbstractListModel<AulaMañanaTardeContainer> implements DataProyectoListener {

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
    public AulaMañanaTardeContainer getElementAt(int index) {
        return dk.getDP().getDataAulas().getAulaContainers().get(index);
    }

    /**
     *
     * @param casillasConflictivas
     * @param markType
     */
    public void marcaAulasConSegmentosConflictivos(HashMap<String, HashSet<Integer>> casillasConflictivas, int markType) {
        //Primero desmarco todas las aulas como conflictivas
        HashMap<String, AulaMañanaTardeContainer> hm = dk.getDP().getDataAulas().getMapHashToContainers();
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
        ArrayList<AulaMañanaTardeContainer> aulaContainers = dk.getDP().getDataAulas().getAulaContainers();
        for (AulaMañanaTardeContainer a : aulaContainers) {
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
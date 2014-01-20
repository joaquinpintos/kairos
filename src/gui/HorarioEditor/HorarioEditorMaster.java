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
import data.DataProjectListener;
import data.horarios.Horario;
import data.horarios.HorarioItem;
import data.restricciones.Restriccion;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.SwingUtilities;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class HorarioEditorMaster implements DataProjectListener {

    private final ArrayList<JIntHorarioEditor> editors;
    private final DataKairos dk;
    private JList<Restriccion> jListRestricciones;

    public HorarioEditorMaster(DataKairos dk) {
        editors = new ArrayList<JIntHorarioEditor>();
        this.dk = dk;
    }

    public boolean add(JIntHorarioEditor e, boolean controlsVisible) {
        e.setMaster(this);
        e.getHorariosJPanelModel().addListener(this);
        e.setControlsVisible(controlsVisible);
        return editors.add(e);
    }

    public ArrayList<JIntHorarioEditor> getEditors() {
        return editors;
    }

    @Override
    public void dataEvent(Object obj, int type) {
        if (dk.getDP().getHorario().hayUnaSolucion()) {
            if (obj instanceof Restriccion) {
                //Inicializo datos si se ha modificado o añadido
                needRecalcularPesos();

            }
            if (obj instanceof Horario) {

                needRelocateItems();
                needRecalcularPesos();
            }

            //TODO: Implementar eventos por los que se borra la solución actual
        }
    }

    public void needRecalcularPesos() {
//        //Primero chequeo si HAY una solución calculada efectivamente.
//        if (!dk.getDP().getHorario().getHorarios().isEmpty()) {
//            recalculaRestricciones();
//            jListRestricciones.updateUI();
//            if (jListRestricciones.getSelectedIndex() == -1) {
//                if (jListRestricciones.getFirstVisibleIndex() > 0) {
//                    jListRestricciones.setSelectedIndex(0);
//                }
//            }
////            try {
//            resaltaItemsConflictivos((Restriccion) jListRestricciones.getSelectedValue());
////            } catch (java.lang.IndexOutOfBoundsException e) {
////                resaltaItemsConflictivos(null);
////            }
//        } else {
//            resaltaItemsConflictivos(null);
//        }
//        for (JIntHorarioEditor hv : editors) {
//            hv.getjListAulas().updateUI();
//        }
//        jListRestricciones.updateUI();

        recalculaRestricciones();
        jListRestricciones.updateUI();
//        SwingUtilities.invokeLater(new Runnable() {
////
//            @Override
//            public void run() {
        Restriccion rest=null;
        if (jListRestricciones.getSelectedIndex() == -1) {
            if (jListRestricciones.getModel().getSize() > 0) {
                jListRestricciones.setSelectedIndex(0);
                rest = (Restriccion) jListRestricciones.getSelectedValue();

            } else {
                rest = null;
            }
        }
        else
        {
            rest=jListRestricciones.getSelectedValue();
        }
        resaltaItemsConflictivos(rest);
        for (JIntHorarioEditor hv : editors) {
            hv.getjListAulas().updateUI();
        }
//            }
//        });

    }

    public void recalculaRestricciones() {
        long suma = 0;
        for (JIntHorarioEditor hv : editors) {
            hv.getjListRestriccionesModel().clear();
        }
        //ArrayList<Restriccion> nuevasRestr = new ArrayList<Restriccion>();
        for (Restriccion r : dk.getDP().getRestrictionsData().getListaRestricciones()) {
            r.setDebug(true);
            r.setMarcaCasillasConflictivas(true);
            r.clearConflictivos();
            long peso = r.calculaPeso(dk.getDP().getHorario().getSolucion());
            if (peso > 0) {
                for (JIntHorarioEditor hv : editors) {
                    hv.getjListRestriccionesModel().add(r);
                }
            }
            suma += peso;
        }
        //  jListRestriccionesModel.setData(nuevasRestr);

        for (JIntHorarioEditor hv : editors) {
            hv.getjListRestricciones().updateUI();
        }
    }

    /**
     * Marca los items en color conflictivo que estén dados por la restriccion
     *
     * @param restriccion
     */
    public void resaltaItemsConflictivos(Restriccion restriccion) {
        if (dk.getStatus() == DataKairos.STATUS_PROJECT_SOLUTION) {
            JListRestriccionesModel jListRestriccionesModel = (JListRestriccionesModel) jListRestricciones.getModel();
            for (JIntHorarioEditor hv : editors) {
                hv.getHorariosJPanelModel().clearConflictivos();
                hv.getjListAulasModel().clearConflictivos();
            }

            for (Restriccion r : jListRestriccionesModel.getData()) {
//                if (r != restriccion) 
                {
                    for (JIntHorarioEditor hv : editors) {
                        hv.getHorariosJPanelModel().marcaConflictivos(r.getCasillasConflictivas(), HorarioItem.SIMPLE_MARK);
                        hv.getjListAulasModel().marcaAulasConSegmentosConflictivos(r.getCasillasConflictivas(), HorarioItem.SIMPLE_MARK);
                    }
                }
            }
            if (restriccion != null) {
                for (JIntHorarioEditor hv : editors) {
                    hv.getHorariosJPanelModel().marcaConflictivos(restriccion.getCasillasConflictivas(), HorarioItem.DOUBLE_MARK);
                    hv.getjListAulasModel().marcaAulasConSegmentosConflictivos(restriccion.getCasillasConflictivas(), HorarioItem.DOUBLE_MARK);
                }
            }
        }
    }

    public void setjListRestricciones(JList<Restriccion> jListRestricciones) {
        this.jListRestricciones = jListRestricciones;
    }

    private void needRelocateItems() {
        for (JIntHorarioEditor hv : editors) {
            hv.getHorariosJPanelModel().relocateItems();
        }
    }

}

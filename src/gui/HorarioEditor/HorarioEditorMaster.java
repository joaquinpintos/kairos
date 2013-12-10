/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.HorarioEditor;

import data.DataKairos;
import data.DataProyectoListener;
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
public class HorarioEditorMaster implements DataProyectoListener {
    
    private final ArrayList<JIntHorarioEditor> editors;
    private final DataKairos dk;
    private JList<Restriccion> jListRestricciones;
    
    public HorarioEditorMaster(DataKairos dk) {
        editors = new ArrayList<JIntHorarioEditor>();
        this.dk = dk;
    }
    
    public boolean add(JIntHorarioEditor e) {
        e.setMaster(this);
        e.getHorariosJPanelModel().addListener(this);
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
                if ((type == DataProyectoListener.ADD) || (type == DataProyectoListener.MODIFY)) {
                    ((Restriccion) obj).inicializarDatos();
                }
                needRecalcularPesos();
            }
            if (obj instanceof Horario) {
                
                needRelocateItems();
                needRecalcularPesos();
            }
             SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if ((jListRestricciones.getSelectedIndex()==-1)&&(jListRestricciones.getModel().getSize()>0))
                {
                    jListRestricciones.setSelectedIndex(0);
                }
            }
        });
            
            //TODO: Implementar eventos por los que se borra la solución actual

        }
    }
    
    public synchronized void needRecalcularPesos() {
        //Primero chequeo si HAY una solución calculada efectivamente.
        if (!dk.getDP().getHorario().getHorarios().isEmpty()) {
            recalculaRestricciones();
             jListRestricciones.updateUI();
//            if (jListRestricciones.getSelectedIndex() == -1) {
//                if (jListRestricciones.getFirstVisibleIndex()> 0) {
//                    jListRestricciones.setSelectedIndex(0);
//                }
//            }
//            try {
                resaltaItemsConflictivos((Restriccion) jListRestricciones.getSelectedValue());
//            } catch (java.lang.IndexOutOfBoundsException e) {
//                resaltaItemsConflictivos(null);
//            }
        } else {
            resaltaItemsConflictivos(null);
        }
        for (JIntHorarioEditor hv : editors) {
            hv.getjListAulas().updateUI();
        }
        jListRestricciones.updateUI();
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
        JListRestriccionesModel jListRestriccionesModel = (JListRestriccionesModel) jListRestricciones.getModel();
        for (JIntHorarioEditor hv : editors) {
            hv.getHorariosJPanelModel().clearConflictivos();
            hv.getjListAulasModel().clearConflictivos();
        }
        if (restriccion != null) {
            
            for (Restriccion r : jListRestriccionesModel.getData()) {
//                if (r != restriccion) 
                {
                    for (JIntHorarioEditor hv : editors) {
                        hv.getHorariosJPanelModel().marcaConflictivos(r.getCasillasConflictivas(), HorarioItem.SIMPLE_MARK);
                        hv.getjListAulasModel().marcaAulasConSegmentosConflictivos(r.getCasillasConflictivas(), HorarioItem.SIMPLE_MARK);
                    }
                }
            }
            for (JIntHorarioEditor hv : editors) {
                hv.getHorariosJPanelModel().marcaConflictivos(restriccion.getCasillasConflictivas(), HorarioItem.DOUBLE_MARK);
                hv.getjListAulasModel().marcaAulasConSegmentosConflictivos(restriccion.getCasillasConflictivas(), HorarioItem.DOUBLE_MARK);
                hv.updateAllUIS();
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

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

import data.aulas.AulaMT;
import data.DataKairos;
import gui.DatosEditor.DataGUIInterface;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.JList;
import data.restricciones.Restriccion;
import gui.DatosEditor.Restricciones.RestriccionListRenderer;
import gui.AbstractMainWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class JIntHorarioEditor extends javax.swing.JInternalFrame implements DataGUIInterface, HorarioListener {

    private AbstractMainWindow mainWindow;
//    private final HorariosTableModelPorAula modelHorarios;
    private final JListRestriccionesModel jListRestriccionesModel;
    private final JListAulasModel jListAulasModel;
    private final DataKairos dk;
    private final HorariosJPanelModel horariosJPanelModel;
    private AbstractAction volverAOptimizarAction;
    private AbstractAction creaPDFAction;
    private HorarioEditorMaster master;

    /**
     *
     * @param dk 
     */
    public JIntHorarioEditor(DataKairos dk) {
        initComponents();
        this.dk = dk;
        jListAulasModel = new JListAulasModel(dk);
        jListAulas.setModel(jListAulasModel);
        jListAulas.setCellRenderer(new JListAulasRenderer());

//        modelHorarios = new HorariosTableModelPorAula(dk, jListAulasModel);
        jListRestriccionesModel = new JListRestriccionesModel();
        //Modelo de la tabla



        jListRestricciones.setModel(jListRestriccionesModel);
        RestriccionListRenderer rend = new RestriccionListRenderer();
        rend.setMuestraMensajesDeError(true);
        jListRestricciones.setCellRenderer(rend);
        jPanelHorarios.setLayout(null);
        horariosJPanelModel = new HorariosJPanelModel(jPanelHorarios, jListAulasModel, dk);


        //Registro como listener
        registraListener();
        creaAcciones();
    }

    public void setMaster(HorarioEditorMaster master) {
        this.master = master;
        horariosJPanelModel.addListener(master);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTableHorario = new javax.swing.JTable();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListAulas = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanelHorarios = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        jScrollPane3 = new javax.swing.JScrollPane();
        jListRestricciones = new javax.swing.JList();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        jPanel2 = new javax.swing.JPanel();
        jButVolverAOptimizar = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));

        jTableHorario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTableHorario);

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(900, 675));
        getContentPane().setLayout(new java.awt.BorderLayout(5, 5));

        jListAulas.setPreferredSize(new java.awt.Dimension(150, 0));
        jListAulas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListAulasValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jListAulas);

        jSplitPane1.setLeftComponent(jScrollPane2);

        jPanelHorarios.setMinimumSize(new java.awt.Dimension(500, 500));
        jPanelHorarios.setName(""); // NOI18N

        javax.swing.GroupLayout jPanelHorariosLayout = new javax.swing.GroupLayout(jPanelHorarios);
        jPanelHorarios.setLayout(jPanelHorariosLayout);
        jPanelHorariosLayout.setHorizontalGroup(
            jPanelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 718, Short.MAX_VALUE)
        );
        jPanelHorariosLayout.setVerticalGroup(
            jPanelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(jPanelHorarios);

        jSplitPane1.setRightComponent(jScrollPane4);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.X_AXIS));
        jPanel1.add(filler1);

        jListRestricciones.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jListRestricciones.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jListRestricciones.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListRestriccionesValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jListRestricciones);

        jPanel1.add(jScrollPane3);
        jPanel1.add(filler3);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jButVolverAOptimizar.setText("Volver a optimizar");
        jButVolverAOptimizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButVolverAOptimizarActionPerformed(evt);
            }
        });
        jPanel2.add(jButVolverAOptimizar);
        jPanel2.add(filler2);

        jPanel1.add(jPanel2);
        jPanel1.add(filler4);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jListAulasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListAulasValueChanged
        AulaMT a = (AulaMT) jListAulas.getSelectedValue();
        jTableHorario.updateUI();
        horariosJPanelModel.setHashAulaMostrada(a.getHash());
        horariosJPanelModel.rebuildAll();
         Restriccion r = (Restriccion) jListRestricciones.getSelectedValue();
        if (r != null) {
            master.resaltaItemsConflictivos(r);
        } else {
            master.resaltaItemsConflictivos(null);
        }
        mainWindow.repaint();
    }//GEN-LAST:event_jListAulasValueChanged

    private void jListRestriccionesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListRestriccionesValueChanged
        Restriccion r = (Restriccion) jListRestricciones.getSelectedValue();
        if (r != null) {
            master.resaltaItemsConflictivos(r);
        } else {
            master.resaltaItemsConflictivos(null);
        }
        mainWindow.repaint();
    }//GEN-LAST:event_jListRestriccionesValueChanged

    private void jButVolverAOptimizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButVolverAOptimizarActionPerformed
        updateData();
    }//GEN-LAST:event_jButVolverAOptimizarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JButton jButVolverAOptimizar;
    private javax.swing.JList jListAulas;
    private javax.swing.JList jListRestricciones;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelHorarios;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTable jTableHorario;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    @Override
    public void updateData() {
        if (dk.getDP().getHorario().hayUnaSolucion()) {
            dk.getDP().getDataAulas().buildArrayAulaContainers();
            horariosJPanelModel.setMainWindow(mainWindow);//Esto es necesario para redibujar todito
            horariosJPanelModel.updateData();
            jListAulas.updateUI();

            if (jListAulas.getSelectedIndex() == -1) {
                jListAulas.setSelectedIndex(0);
            }
            AulaMT a = (AulaMT) jListAulas.getSelectedValue();
//        modelHorarios.setAulaMostrada(a.getHashAula());
            jTableHorario.updateUI();

//        creaListenersParaRestricciones();
            horariosJPanelModel.setHashAulaMostrada(a.getHash());
            horariosJPanelModel.rebuildAll();
            mainWindow.repaint();
        }
    }

    /**
     *
     * @param mainWindow
     */
    @Override
    public void setMainWindow(AbstractMainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     *
     */
    public JListRestriccionesModel getjListRestriccionesModel() {
        return jListRestriccionesModel;
    }

    public JList<Restriccion> getjListRestricciones() {
        return jListRestricciones;
    }


    /**
     *
     */
    @Override
    public void needUpdate() {
        updateData();
    }

    /**
     *
     */
    @Override
    public synchronized void needRecalcularPesos() {
        //Primero chequeo si HAY una solución calculada efectivamente.
        if (!dk.getDP().getHorario().getHorarios().isEmpty()) {
            jListRestricciones.updateUI();
            Restriccion rSelected = null;
            try {
                rSelected = (Restriccion) jListRestricciones.getSelectedValue();
            } catch (IndexOutOfBoundsException ex) {
            }
            master.recalculaRestricciones();
            //Intento seleccionar otra vez la misma restriccion
            if (jListRestricciones.getModel().getSize() > 0) {
                if (jListRestricciones.getSelectedIndex() == -1) {
                    jListRestricciones.setSelectedIndex(0);
                }
                //  System.out.println("RESALTA " + ((Restriccion) jListRestricciones.getSelectedValue()));
                try {
                    master.resaltaItemsConflictivos((Restriccion) jListRestricciones.getSelectedValue());
                } catch (java.lang.IndexOutOfBoundsException e) {
                    master.resaltaItemsConflictivos(null);
                }
            } else {
                master.resaltaItemsConflictivos(null);
            }

        }

        //Actualizo vistas gráficas
        jListRestricciones.updateUI();
        jListAulas.updateUI();




    }

    /**
     *
     * @return
     */
    public HorariosJPanelModel getHorariosJPanelModel() {
        return horariosJPanelModel;
    }

    public JList getjListAulas() {
        return jListAulas;
    }

   

    void creaListenersParaRestricciones() {
        RestriccionListener l = new RestriccionListener() {
            @Override
            public void restrictionChanged(Restriccion r, Boolean needReinicializarDatos) {
                if (needReinicializarDatos) {
                    r.inicializarDatos();
                }
                master.needRecalcularPesos();
            }
        };
        for (Restriccion r : dk.getDP().getRestrictionsData().getListaRestricciones()) {
            r.addListener(l);

        }
    }

    /**
     *
     * @return
     */
    public JListAulasModel getjListAulasModel() {
        return jListAulasModel;
    }


    private void registraListener() {
        

        //Prueba de listener para resize
        jPanelHorarios.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                horariosJPanelModel.relocate();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
            }
        });


    }

    private void creaAcciones() {
        //<editor-fold defaultstate="collapsed" desc="VolverAOptimizarAction">
        class VolverAOptimizarAction extends AbstractAction {
            
            public VolverAOptimizarAction() {
                super("Volver a optimizar", null);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.getjIntgenGenetic().setVisible(true);
                mainWindow.switchToComponent(mainWindow.getjIntgenGenetic());
                try {
                    mainWindow.getjIntgenGenetic().doGenetic(dk.getDP().getHorario().getSolucion());
                } catch (Exception ex) {
                    Logger.getLogger(JIntHorarioEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
//</editor-fold>
        volverAOptimizarAction = new VolverAOptimizarAction();
        jButVolverAOptimizar.setAction(volverAOptimizarAction);

        //<editor-fold defaultstate="collapsed" desc="CalculaHorasAsignaturasAction">
        class CalculaHorasAsignaturasAction extends AbstractAction {
            
            public CalculaHorasAsignaturasAction() {
                super("Calcular horas", null);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                JDlgCalculaHorasAsignaturas dlg = new JDlgCalculaHorasAsignaturas(null, true, dk);
                dlg.setLocationRelativeTo(null);
                dlg.setVisible(true);
            }
        }
//</editor-fold>
        CalculaHorasAsignaturasAction calculaHorasAsignadasAction = new CalculaHorasAsignaturasAction();
//        jButCalcularHorasCreditos.setAction(calculaHorasAsignadasAction);
    }

    void updateAllUIS() {
        jTableHorario.updateUI();
        jListAulas.updateUI();
        jListRestricciones.updateUI();
    }

    @Override
    public void expandTrees() {
    }

    public void setControlsVisible(boolean b) {
        jListRestricciones.setVisible(b);
//        jScrollPane3.setVisible(b);
        jButVolverAOptimizar.setVisible(b);
    }
}

class JListRestriccionesModel extends AbstractListModel<Restriccion> {

    private final ArrayList<Restriccion> data;

    public JListRestriccionesModel() {
        this.data = new ArrayList<Restriccion>();
    }

    public ArrayList<Restriccion> getData() {
        return data;
    }

    public boolean add(Restriccion e) {
        return data.add(e);
    }

    public void clear() {
        data.clear();
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public Restriccion getElementAt(int index) {
        Restriccion resul = null;
        try {
            resul = data.get(index);
        } catch (Exception e) {
        }
        return resul;
    }

    public synchronized void setData(ArrayList<Restriccion> nuevasRestr) {
        data.clear();
        for (Restriccion r : nuevasRestr) {
            add(r);
        }
    }

    public boolean contains(Restriccion rSelected) {
        return data.contains(rSelected);
    }
}

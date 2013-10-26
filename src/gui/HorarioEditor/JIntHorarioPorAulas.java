/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.HorarioEditor;

import data.aulas.AulaMT;
import data.DataKairos;
import data.DataProyectoListener;
import data.MyConstants;
import data.horarios.Horario;
import gui.DatosEditor.DataGUIInterface;
import gui.MainWindowTabbed;
import java.awt.Component;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import data.restricciones.Restriccion;
import gui.DatosEditor.Restricciones.RestriccionListRenderer;
import data.horarios.HorarioItem;
import gui.AbstractMainWindow;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class JIntHorarioPorAulas extends javax.swing.JInternalFrame implements DataGUIInterface, HorarioListener, DataProyectoListener {

    private AbstractMainWindow mainWindow;
//    private final HorariosTableModelPorAula modelHorarios;
    private final JListRestriccionesModel jListRestriccionesModel;
    private final JListAulasModel jListAulasModel;
    private final DataKairos dk;
    private final HorariosJPanelModel horariosJPanelModel;
    private AbstractAction volverAOptimizarAction;
    private AbstractAction creaPDFAction;

    /**
     *
     * @param dk 
     */
    public JIntHorarioPorAulas(DataKairos dk) {
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jListAulas = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        jScrollPane3 = new javax.swing.JScrollPane();
        jListRestricciones = new javax.swing.JList();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        jPanel2 = new javax.swing.JPanel();
        jButVolverAOptimizar = new javax.swing.JButton();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10));
        jButCalcularHorasCreditos = new javax.swing.JButton();
        jButCrearPDF = new javax.swing.JButton();
        jLabPeso = new javax.swing.JLabel();
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        jScrollPane4 = new javax.swing.JScrollPane();
        jPanelHorarios = new javax.swing.JPanel();

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

        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setResizable(true);
        getContentPane().setLayout(new java.awt.BorderLayout(5, 5));

        jListAulas.setPreferredSize(new java.awt.Dimension(150, 0));
        jListAulas.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListAulasValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jListAulas);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.LINE_START);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.X_AXIS));
        jPanel1.add(filler1);

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

        jButCalcularHorasCreditos.setText("jButton1");
        jButCalcularHorasCreditos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButCalcularHorasCreditosActionPerformed(evt);
            }
        });
        jPanel2.add(jButCalcularHorasCreditos);

        jButCrearPDF.setText("jButton2");
        jPanel2.add(jButCrearPDF);

        jLabPeso.setText("jLabel1");
        jPanel2.add(jLabPeso);

        jPanel1.add(jPanel2);
        jPanel1.add(filler4);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jPanelHorarios.setMinimumSize(new java.awt.Dimension(500, 500));
        jPanelHorarios.setName(""); // NOI18N

        javax.swing.GroupLayout jPanelHorariosLayout = new javax.swing.GroupLayout(jPanelHorarios);
        jPanelHorarios.setLayout(jPanelHorariosLayout);
        jPanelHorariosLayout.setHorizontalGroup(
            jPanelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        jPanelHorariosLayout.setVerticalGroup(
            jPanelHorariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        jScrollPane4.setViewportView(jPanelHorarios);

        getContentPane().add(jScrollPane4, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jListAulasValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListAulasValueChanged
        AulaMT a = (AulaMT) jListAulas.getSelectedValue();
//        modelHorarios.setAulaMostrada(a.getAula().getHash(a.getEsTarde()));
        jTableHorario.updateUI();
        horariosJPanelModel.setHashAulaMostrada(a.getHash());
        horariosJPanelModel.rebuildAll();
        Restriccion r = (Restriccion) jListRestricciones.getSelectedValue();
        if (r != null) {
            resaltaItemsConflictivos(r);
        } else {
            resaltaItemsConflictivos(null);
        }

        horariosJPanelModel.repaintAllItems();
        mainWindow.repaint();
    }//GEN-LAST:event_jListAulasValueChanged

    private void jListRestriccionesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListRestriccionesValueChanged
        Restriccion r = (Restriccion) jListRestricciones.getSelectedValue();
        if (r != null) {
            resaltaItemsConflictivos(r);
        } else {
            resaltaItemsConflictivos(null);
        }
        horariosJPanelModel.repaintAllItems();
        mainWindow.repaint();
    }//GEN-LAST:event_jListRestriccionesValueChanged

    private void jButVolverAOptimizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButVolverAOptimizarActionPerformed
        updateData();
    }//GEN-LAST:event_jButVolverAOptimizarActionPerformed

    private void jButCalcularHorasCreditosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButCalcularHorasCreditosActionPerformed
    }//GEN-LAST:event_jButCalcularHorasCreditosActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JButton jButCalcularHorasCreditos;
    private javax.swing.JButton jButCrearPDF;
    private javax.swing.JButton jButVolverAOptimizar;
    private javax.swing.JLabel jLabPeso;
    private javax.swing.JList jListAulas;
    private javax.swing.JList jListRestricciones;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelHorarios;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
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
    public void recalculaRestricciones() {
        long suma = 0;
        jListRestriccionesModel.clear();
        //ArrayList<Restriccion> nuevasRestr = new ArrayList<Restriccion>();
        for (Restriccion r : dk.getDP().getDataRestricciones().getListaRestricciones()) {
            r.setDebug(true);
            r.setMarcaCasillasConflictivas(true);
            r.clearConflictivos();
            long peso = r.calculaPeso(dk.getDP().getHorario().getSolucion());
            if (peso > 0) {
                jListRestriccionesModel.add(r);
            }
            suma += peso;
        }
        //  jListRestriccionesModel.setData(nuevasRestr);

        jListRestricciones.updateUI();
        jLabPeso.setText("" + suma);
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
        System.out.println("[RECALCULAR PESOS]");
        //Primero chequeo si HAY una solución calculada efectivamente.
        if (!dk.getDP().getHorario().getHorarios().isEmpty()) {
            jListRestricciones.updateUI();
            Restriccion rSelected = null;
            try {
                rSelected = (Restriccion) jListRestricciones.getSelectedValue();
            } catch (IndexOutOfBoundsException ex) {
            }
            recalculaRestricciones();
            //Intento seleccionar otra vez la misma restriccion
//            if ((rSelected != null) && (jListRestriccionesModel.contains(rSelected))) {
//                jListRestricciones.setSelectedValue(rSelected, true);
//            } else {
//                jListRestricciones.setSelectedIndex(0);
//            }
            if (jListRestricciones.getModel().getSize() > 0) {
                if (jListRestricciones.getSelectedIndex() == -1) {
                    jListRestricciones.setSelectedIndex(0);
                }
                //  System.out.println("RESALTA " + ((Restriccion) jListRestricciones.getSelectedValue()));
                try {
                    resaltaItemsConflictivos((Restriccion) jListRestricciones.getSelectedValue());
                } catch (java.lang.IndexOutOfBoundsException e) {
                    System.out.println("Excepción en JInthorarioPOraulas linea 270 más o menos!!");
                    resaltaItemsConflictivos(null);
                }
            } else {
                System.out.println("RESALTA NULL");
                resaltaItemsConflictivos(null);
            }
            horariosJPanelModel.repaintAllItems();

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

    /**
     * Marca los items en color conflictivo que estén dados por la restriccion
     *
     * @param restriccion
     */
    private void resaltaItemsConflictivos(Restriccion restriccion) {
        horariosJPanelModel.clearConflictivos();
        jListAulasModel.clearConflictivos();
        if (restriccion != null) {

            for (Restriccion r : jListRestriccionesModel.getData()) {
//                if (r != restriccion) 
                {
                    horariosJPanelModel.marcaConflictivos(r.getCasillasConflictivas(), HorarioItem.SIMPLE_MARK);
                    jListAulasModel.marcaAulasConSegmentosConflictivos(r.getCasillasConflictivas(), HorarioItem.SIMPLE_MARK);
                }
            }
            System.out.println("Casillas conflictivas: " + restriccion.getCasillasConflictivas());
            horariosJPanelModel.marcaConflictivos(restriccion.getCasillasConflictivas(), HorarioItem.DOUBLE_MARK);
            jListAulasModel.marcaAulasConSegmentosConflictivos(restriccion.getCasillasConflictivas(), HorarioItem.DOUBLE_MARK);

            jTableHorario.updateUI();
            jListAulas.updateUI();
        }
    }

    void creaListenersParaRestricciones() {
        RestriccionListener l = new RestriccionListener() {
            @Override
            public void restrictionChanged(Restriccion r, Boolean needReinicializarDatos) {
                if (needReinicializarDatos) {
                    r.inicializarDatos();
                }
                needRecalcularPesos();
            }
        };
        for (Restriccion r : dk.getDP().getDataRestricciones().getListaRestricciones()) {
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

    /**
     *
     * @param obj
     * @param type
     */
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
                needRecalcularPesos();
            }
            //TODO: Implementar eventos por los que se borra la solución actual

        }
    }

    private void registraListener() {
        horariosJPanelModel.addListener(this);

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
        class VolverAOptimizarAction extends AbstractAction {

            public VolverAOptimizarAction() {
                super("Volver a optimizar", null);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.switchToComponent(mainWindow.getjIntgenGenetic());
                try {
                    mainWindow.getjIntgenGenetic().doGenetic(dk.getDP().getHorario().getSolucion());
                } catch (Exception ex) {
                    Logger.getLogger(JIntHorarioPorAulas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        volverAOptimizarAction = new VolverAOptimizarAction();
        jButVolverAOptimizar.setAction(volverAOptimizarAction);

        class CreaPDFAction extends AbstractAction {

            public CreaPDFAction() {
                super("Imprimir calendarios", null);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                jDlgPrintHorario dlg = new jDlgPrintHorario(mainWindow, true, dk.getDP());
                dlg.setLocationRelativeTo(null);
                dlg.setVisible(true);
            }
        };
        creaPDFAction = new CreaPDFAction();
        jButCrearPDF.setAction(creaPDFAction);


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
        CalculaHorasAsignaturasAction calculaHorasAsignadasAction = new CalculaHorasAsignaturasAction();
        jButCalcularHorasCreditos.setAction(calculaHorasAsignadasAction);
    }
}

class JListRestriccionesModel extends AbstractListModel<Restriccion> {

    private ArrayList<Restriccion> data;

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

class JListAulasRenderer extends JLabel implements ListCellRenderer<AulaMT> {

    Border borderSelectedConflictivo;
    Border borderAulaSelected;

    public JListAulasRenderer() {
        //super(MyConstants.AULA_ICON);
        super();
        this.setIcon(MyConstants.AULA_ICON);
        this.setOpaque(true);
        this.setAlignmentX(LEFT_ALIGNMENT);
        borderSelectedConflictivo = BorderFactory.createLineBorder(Color.RED, 3);
        borderAulaSelected = BorderFactory.createLineBorder(Color.BLACK, 2);
    }

    @Override
    public Component getListCellRendererComponent(JList list, AulaMT data, int index, boolean isSelected, boolean cellHasFocus) {
        if (isSelected) {
//            this.setBackground(MyConstants.SELECTED_ITEM_LIST);
            this.setBorder(borderAulaSelected);
        } else {
//            this.setBackground(Color.WHITE);
            this.setBorder(null);
        }

        this.setText(data.toString());
//        if (data.getTieneSegmentosConflictivos()) {
//            this.setForeground(MyConstants.CONFLICTIVE_ITEM);
//        } else {
//            this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
//        }
        switch (data.getTieneSegmentosConflictivos()) {
            case HorarioItem.NO_MARK:
                this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
//                this.setBorder(null);
                this.setBackground(Color.white);
                break;
            case HorarioItem.SIMPLE_MARK:
                this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
//                this.setBorder(null);
                this.setBackground(MyConstants.NON_SELECTED_CONFLICTIVE_ITEM);
                break;
            case HorarioItem.DOUBLE_MARK:
                this.setForeground(MyConstants.NON_CONFLICTIVE_ITEM);
//                this.setBorder(borderSelectedConflictivo);
                this.setBackground(MyConstants.SELECTED_CONFLICTIVE_ITEM);
                break;

        }
        return this;
    }
}
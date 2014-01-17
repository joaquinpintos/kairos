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

import data.DataKairos;
import data.DataProyectoListener;
import data.KairosCommand;
import data.asignaturas.DataAsignaturas;
import data.aulas.Aula;
import data.aulas.AulaMT;
import data.aulas.DataAulas;
import data.aulas.ListaAsignaciones;
import data.profesores.DataProfesores;
import gui.AbstractMainWindow;
import gui.DatosEditor.DataGUIInterface;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractAction;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.ActionMap;
import javax.swing.DropMode;
import javax.swing.Icon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class JIntTreeAulas extends javax.swing.JInternalFrame implements DataGUIInterface, DataProyectoListener {

    private DataAsignaturas dataAsignaturas;
    private DataAulas dataAulas;
    private DataProfesores dataProfesores;
    private AbstractMainWindow mainWindow;
    private AbstractAction añadirAulaAction;
    private AbstractAction editarAulaAction;
    private AbstractAction eliminarAulaAction;
    private JPopupMenu jpopMenu;
    private final DataKairos dk;
    private AbstractAction vaciarAulaAction;

    /**
     * Creates new form JIntTreeAulas
     *
     * @param dk
     * @throws Exception
     */
    public JIntTreeAulas(DataKairos dk) throws Exception {
        initComponents();
        //this.dataAulas=asig.dataAulasRelleno();

        this.dk = dk;

//        modelHashes = new CarreraGrupoCursosNoAsignadosSimpleListModel(dk);
//        this.jListGrupos.setModel(modelHashes);
//        this.jListGrupos.setDragEnabled(true);
//        this.jListGrupos.setTransferHandler(new ListGruposTransferHandler());
//        this.jListGrupos.setDropMode(DropMode.INSERT);
//        this.jListGrupos.setDropTarget(new DropTarget());
//        jListGrupos.getDropTarget().addDropTargetListener(new JListGruposDropListener(this, dk));
        final TreeModelGrupoCursos treeModelGrupoCursos = new TreeModelGrupoCursos(dk);
        this.jTreeGrupoCursos.setModel(treeModelGrupoCursos);
        treeModelGrupoCursos.addTreeModelListener(createTreeModelListener(jTreeGrupoCursos));
        treeModelAulas = new TreeModelAulas(dk);
        jTreeGrupoCursos.setCellRenderer(new JTreeGrupoCursosRenderer(dk));
        jTreeGrupoCursos.setDragEnabled(true);
        jTreeGrupoCursos.setTransferHandler(new GrupoCursoTransferHandler());
        this.jTreeGrupoCursos.setDropMode(DropMode.INSERT);
        this.jTreeGrupoCursos.setDropTarget(new DropTarget());
        jTreeGrupoCursos.getDropTarget().addDropTargetListener(new JTreeGrupoCursoDropListener(this, dk));

        jTreeAulas.setModel(treeModelAulas);
        treeModelAulas.addTreeModelListener(createTreeModelListener(jTreeAulas));
        jTreeAulas.setDragEnabled(true);
        jTreeAulas.setTransferHandler(new JTreeAulasTransferHandler());
        jTreeAulas.setDropMode(DropMode.ON);
        jTreeAulas.setDropTarget(new DropTarget());
        jTreeAulas.getDropTarget().addDropTargetListener(new JTreeAulasDropListener(this, dk));
        jTreeAulas.setCellRenderer(new JTreeAulasRenderer(dk));

        createActions();
        updateData();
    }
    private final TreeModelAulas treeModelAulas;

    /**
     *
     * @return
     */
    public JTree getjTreeAulas() {
        return jTreeAulas;
    }

    /**
     *
     * @return
     */
    public JTree getjTreeGrupoCursos() {
        return jTreeGrupoCursos;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanelInferior1 = new javax.swing.JPanel();
        filler5 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        jButton1 = new javax.swing.JButton();
        jButEditarAula = new javax.swing.JButton();
        jButEliminarAula = new javax.swing.JButton();
        jButAñadirAula = new javax.swing.JButton();
        filler6 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        filler8 = new javax.swing.Box.Filler(new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0), new java.awt.Dimension(10, 0));
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanelAulas1 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTreeAulas = new javax.swing.JTree();
        jLabel1 = new javax.swing.JLabel();
        jPanelGrupos1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeGrupoCursos = new javax.swing.JTree();

        setResizable(true);
        getContentPane().setLayout(new java.awt.BorderLayout(10, 10));

        jPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        jPanel1.setLayout(new java.awt.BorderLayout(10, 10));

        jPanelInferior1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
        jPanelInferior1.add(filler5);

        jButton1.setText("Expandir todo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanelInferior1.add(jButton1);

        jButEditarAula.setText("Editar Aula");
        jPanelInferior1.add(jButEditarAula);

        jButEliminarAula.setText("Eliminar Aula");
        jButEliminarAula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButEliminarAulaActionPerformed(evt);
            }
        });
        jPanelInferior1.add(jButEliminarAula);

        jButAñadirAula.setText("Añadir Aula");
        jButAñadirAula.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButAñadirAulaActionPerformed(evt);
            }
        });
        jPanelInferior1.add(jButAñadirAula);
        jPanelInferior1.add(filler6);
        jPanelInferior1.add(filler8);

        jPanel1.add(jPanelInferior1, java.awt.BorderLayout.PAGE_END);
        jPanel1.add(filler1, java.awt.BorderLayout.LINE_START);

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setContinuousLayout(true);
        jSplitPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jPanelAulas1.setLayout(new java.awt.BorderLayout(0, 5));

        jTreeAulas.setDropMode(javax.swing.DropMode.ON);
        jTreeAulas.setMaximumSize(new java.awt.Dimension(1000, 1000));
        jTreeAulas.setMinimumSize(new java.awt.Dimension(100, 100));
        jTreeAulas.setName("TreeAulas"); // NOI18N
        jTreeAulas.setPreferredSize(new java.awt.Dimension(400, 300));
        jScrollPane5.setViewportView(jTreeAulas);

        jPanelAulas1.add(jScrollPane5, java.awt.BorderLayout.CENTER);

        jLabel1.setText("Aulas:");
        jPanelAulas1.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setLeftComponent(jPanelAulas1);

        jPanelGrupos1.setLayout(new java.awt.BorderLayout(0, 5));

        jLabel2.setText("Grupos:");
        jPanelGrupos1.add(jLabel2, java.awt.BorderLayout.PAGE_START);
        jPanelGrupos1.add(filler2, java.awt.BorderLayout.LINE_END);

        jScrollPane1.setViewportView(jTreeGrupoCursos);

        jPanelGrupos1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanelGrupos1);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButAñadirAulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButAñadirAulaActionPerformed
    }//GEN-LAST:event_jButAñadirAulaActionPerformed

    private void jButEliminarAulaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButEliminarAulaActionPerformed
    }//GEN-LAST:event_jButEliminarAulaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        updateData();
        expandTrees();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler5;
    private javax.swing.Box.Filler filler6;
    private javax.swing.Box.Filler filler8;
    private javax.swing.JButton jButAñadirAula;
    private javax.swing.JButton jButEditarAula;
    private javax.swing.JButton jButEliminarAula;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelAulas1;
    private javax.swing.JPanel jPanelGrupos1;
    private javax.swing.JPanel jPanelInferior1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTree jTreeAulas;
    private javax.swing.JTree jTreeGrupoCursos;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    @Override
    public final void updateData() {
        jTreeAulas.updateUI();
        jTreeGrupoCursos.updateUI();
        for (int i = 0; i < jTreeAulas.getRowCount(); i++) {
            jTreeAulas.expandRow(i);
        }
        for (int i = 0; i < jTreeGrupoCursos.getRowCount(); i++) {
            jTreeGrupoCursos.expandRow(i);
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
     * Crea acciones de crear aula, editar aula, y eliminar aula
     */
    private void createActions() {
        //<editor-fold defaultstate="collapsed" desc="AñadirAulaAction">
        class AñadirAulaAction extends AbstractAction {

            public AñadirAulaAction() {
                super("Añadir aula", dk.mc.ADD_ICON);
                putValue(MNEMONIC_KEY, KeyEvent.VK_A);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Aula nuevaAula = new Aula("");
                String nombre = JOptionPane.showInputDialog(rootPane, "Nombre de la nueva aula:", "");

                if (nombre != null) {
                    dk.getDP().getDataAulas().addAula(nuevaAula);
                    KairosCommand cmd = dk.getController().getCreateAulaCommand(nuevaAula);
                    dk.getController().executeCommand(cmd);
                }
                updateData();
            }
        }//End of class AñadirAula
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="EditarAulaAction">
        class EditarAulaAction extends AbstractAction {

            public EditarAulaAction() {
                super("Editar", dk.mc.ADD_ICON);
                putValue(MNEMONIC_KEY, KeyEvent.VK_E);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Object sel = jTreeAulas.getSelectionPath().getLastPathComponent();
                if (sel instanceof Aula) {
                    Aula aulaEditar = (Aula) sel;
                    JDlgEditarAula dlg = new JDlgEditarAula(null, dk, aulaEditar);
                    dlg.setVisible(true);
                }

            }
        }//End of class EditarAula
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="EliminarAulaAction">
        class EliminarAulaAction extends AbstractAction {

            public EliminarAulaAction() {
                super("Eliminar", dk.mc.DELETE_ICON);
                putValue(MNEMONIC_KEY, KeyEvent.VK_L);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Object sel = jTreeAulas.getSelectionPath().getLastPathComponent();
                if (sel instanceof Aula) {
                    Aula aulaBorrar = (Aula) sel;
                    if (aulaBorrar.tieneAsignaciones()) {
                        JOptionPane.showMessageDialog(mainWindow, "No se puede borrar un aula con grupos asignados.");
                    } else {
                        dk.getDP().getDataAulas().removeAula(aulaBorrar);
                    }
                }
            }
        }//End of class EliminarAula
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="VaciarAulaAction">
        class VaciarAulaAction extends AbstractAction {
            
            public VaciarAulaAction() {
                super("Vaciar aula", null);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Object sel = jTreeAulas.getSelectionPath().getLastPathComponent();
                if (sel instanceof ListaAsignaciones) {
                    AulaMT aulaMT = ((ListaAsignaciones) sel).getAulaMT();
                    KairosCommand cmd = dk.getController().getVaciarAulaCommand(aulaMT);
                    dk.getController().executeCommand(cmd);
                }
            }
        }
//</editor-fold>

        añadirAulaAction = new AñadirAulaAction();
        jButAñadirAula.setAction(añadirAulaAction);

        editarAulaAction = new EditarAulaAction();
        jButEditarAula.setAction(editarAulaAction);

        eliminarAulaAction = new EliminarAulaAction();
        jButEliminarAula.setAction(eliminarAulaAction);
        
        vaciarAulaAction=new VaciarAulaAction();

        jpopMenu = new JPopupMenu();
        jpopMenu.add(añadirAulaAction);
        jpopMenu.add(editarAulaAction);
        jpopMenu.add(eliminarAulaAction);
        jpopMenu.add(vaciarAulaAction);

        //Mapeo la tecla DEL para borrar la restricción
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Eliminar aula");
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put("Eliminar aula", eliminarAulaAction);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "Añadir aula");
        actionMap = getRootPane().getActionMap();
        actionMap.put("Añadir aula", añadirAulaAction);

        //Mouse listener para jTreeAulas
        jTreeAulas.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            public void doPop(MouseEvent e) {
                jpopMenu.show(e.getComponent(), e.getX(), e.getY());
            }

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    Object obj = jTreeAulas.getSelectionPath().getLastPathComponent();
                    editarAulaAction.setEnabled(obj != null && obj instanceof Aula);
                    eliminarAulaAction.setEnabled(obj != null && obj instanceof Aula);
                } catch (NullPointerException ex) {
                }
                if (e.isPopupTrigger()) {
                    doPop(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    doPop(e);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

    }

    /**
     *
     * @param obj
     * @param type
     */
    @Override
    public void dataEvent(Object obj, int type) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                updateData();
            }
        });

    }

    private TreeModelListener createTreeModelListener(final JTree tree) {
        return new TreeModelListener() {
            @Override
            public void treeNodesChanged(TreeModelEvent e) {
                tree.expandPath(e.getTreePath());
            }

            @Override
            public void treeNodesInserted(TreeModelEvent e) {
                tree.expandPath(e.getTreePath());
            }

            @Override
            public void treeNodesRemoved(TreeModelEvent e) {
                tree.expandPath(e.getTreePath());
            }

            @Override
            public void treeStructureChanged(TreeModelEvent e) {
                tree.expandPath(e.getTreePath());
            }
        };
    }

    @Override
    public void expandTrees() {
        for (int i = 0; i < jTreeAulas.getRowCount(); i++) {
            jTreeAulas.expandRow(i);
        }
        for (int i = 0; i < jTreeGrupoCursos.getRowCount(); i++) {
            jTreeGrupoCursos.expandRow(i);
        }
    }
}

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
package gui.DatosEditor.Profesores;

import data.DataKairos;
import data.DataProyectoListener;
import data.KairosCommand;
import data.asignaturas.Teachable;
import data.profesores.Departamento;
import data.profesores.Profesor;
import data.profesores.TreeCellRendererProfesores;
import data.profesores.TreeModelProfesores;
import gui.AbstractMainWindow;
import gui.DatosEditor.Asignaturas.TreeCellRendererAsignaturas;
import gui.DatosEditor.Asignaturas.TreeModelAsignaturas;
import gui.DatosEditor.Aulas.TeachableDraggable;
import gui.DatosEditor.DataGUIInterface;
import gui.DatosEditor.Docencia.JTreeProfesoresTransferHandler;
import gui.DatosEditor.Docencia.TreeProfesores;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
//  putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));}

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class JIntTreeProfesores extends javax.swing.JInternalFrame implements DataGUIInterface, DataProyectoListener, TreeProfesores {

    private Profesor profesorMostradoEnDocencia;
    private final DataKairos dk;
    Profesor selectedProfesor;
    Departamento selectedDpto;
    AbstractMainWindow mainWindow;
    TreeModel treeModelProfesores;
    private JPopupMenu jPopupMenuProfesores;
    private AbstractAction actionEditarProfesores;
    private AbstractAction actionAñadirProfesores;
    private AbstractAction actionEliminarProfesor;
    private AbstractAction actionEditarDepartamento;
    private AbstractAction actionAñadirDepartamento;
    private AbstractAction actionEliminarDepartamento;
    private AbstractAction actionBorrarDocencia;
    private JPopupMenu jPopupMenuDepartamentos;
    private JPopupMenu jPopupMenuRoot;

    /**
     *
     * @param dk
     */
    public JIntTreeProfesores(DataKairos dk) {
        this.dk = dk;
        selectedDpto = null;
        selectedProfesor = null;
        initComponents();
        treeCellRendererDocenciaProfesor = new TreeCellRendererAsignaturas(dk);
        treeCellRendererDocenciaProfesor.setPrintTeachersName(false);
        treeCellRendererDocenciaProfesor.setPrintTotalHorasGrupos(false);
        jTreeDocenciaProfesor.setCellRenderer(treeCellRendererDocenciaProfesor);
        jTreeDocenciaProfesor.setDropTarget(new DropTarget());
        implementaDropListenerDocencia();
        //Modelo para el árbol
        treeModelProfesores = new TreeModelProfesores(dk);
        treeModelProfesores.addTreeModelListener(createTreeModelListener());
        this.jTreeProfesores.setModel(treeModelProfesores);
        this.jTreeProfesores.setCellRenderer(new TreeCellRendererProfesores(dk));

        //Simple selection
        this.jTreeProfesores.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        this.jTreeProfesores.addTreeSelectionListener(createSelectionListener());
        jTreeProfesores.setTransferHandler(new JTreeProfesoresTransferHandler());
        jTreeProfesores.setDragEnabled(true);
        jTreeProfesores.setDropTarget(new DropTarget());
        try {
            jTreeProfesores.getDropTarget().addDropTargetListener(new JTreeProfesoresDropListener((TreeProfesores) this, dk));
        } catch (TooManyListenersException ex) {
            Logger.getLogger(JIntTreeProfesores.class.getName()).log(Level.SEVERE, null, ex);
        }

        creaAcciones();

        //Aquí añado mouselistener para registrar clicks y double-clicks
        MouseListener ml = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    TreePath selPath = jTreeProfesores.getPathForLocation(e.getX(), e.getY());
                    jTreeProfesores.setSelectionPath(selPath);
                    doPop(e);
                }
                try {
                    TreePath selPath = jTreeProfesores.getPathForLocation(e.getX(), e.getY());
                    final boolean esProfesor = selPath.getLastPathComponent() instanceof Profesor;
                    final boolean esDepartamento = selPath.getLastPathComponent() instanceof Departamento;

                    actionEditarProfesores.setEnabled(selPath != null && esProfesor);
                    actionEliminarProfesor.setEnabled(selPath != null && esProfesor);
                    actionAñadirProfesores.setEnabled(selPath != null);
                    actionAñadirDepartamento.setEnabled(selPath != null);
                    actionEditarDepartamento.setEnabled(selPath != null && esDepartamento);
                    actionEliminarDepartamento.setEnabled(selPath != null && esDepartamento);
                } catch (NullPointerException ex) {
                }
                if (e.getClickCount() == 2) {
                    actionEditarProfesores.actionPerformed(null);
                }

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    TreePath selPath = jTreeProfesores.getPathForLocation(e.getX(), e.getY());
                    jTreeProfesores.setSelectionPath(selPath);
                    doPop(e);
                }
            }

            public void doPop(MouseEvent e) {
                TreePath selPath = jTreeProfesores.getPathForLocation(e.getX(), e.getY());
                final boolean esProfesor = selPath.getLastPathComponent() instanceof Profesor;
                final boolean esDepartamento = selPath.getLastPathComponent() instanceof Departamento;

                if (esProfesor) {
                    jPopupMenuProfesores.show(e.getComponent(), e.getX(), e.getY());
                } else {
                    if (esDepartamento) {
                        jPopupMenuDepartamentos.show(e.getComponent(), e.getX(), e.getY());
                    } else {
                        jPopupMenuRoot.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        };
        jTreeProfesores.addMouseListener(ml);

    }
    protected TreeCellRendererAsignaturas treeCellRendererDocenciaProfesor;

    /**
     *
     * @return
     */
    @Override
    public JTree getjTreeProfesores() {
        return jTreeProfesores;
    }

    /**
     * Creates new form JIntTreeProfesores
     */
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelInferior = new javax.swing.JPanel();
        jPanelInferior1 = new javax.swing.JPanel();
        jButEliminarDepartamento = new javax.swing.JButton();
        jButEditarDepartamento = new javax.swing.JButton();
        jButAñadirDepartamento = new javax.swing.JButton();
        jPanelInferior2 = new javax.swing.JPanel();
        jButBorrarDocencia = new javax.swing.JButton();
        jButEliminarProfesor = new javax.swing.JButton();
        jButEditarProfesor = new javax.swing.JButton();
        jButAñadirProfesor = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeProfesores = new javax.swing.JTree();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTreeDocenciaProfesor = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Profesores");
        setMinimumSize(new java.awt.Dimension(500, 33));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(800, 300));

        jPanelInferior.setPreferredSize(new java.awt.Dimension(1328, 74));
        jPanelInferior.setLayout(new javax.swing.BoxLayout(jPanelInferior, javax.swing.BoxLayout.Y_AXIS));

        jPanelInferior1.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelInferior1.setPreferredSize(new java.awt.Dimension(0, 30));
        jPanelInferior1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButEliminarDepartamento.setText("Eliminar departamento");
        jPanelInferior1.add(jButEliminarDepartamento);

        jButEditarDepartamento.setText("Editar departamentos");
        jButEditarDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButEditarDepartamentoActionPerformed(evt);
            }
        });
        jPanelInferior1.add(jButEditarDepartamento);

        jButAñadirDepartamento.setText("Añadir departamento");
        jPanelInferior1.add(jButAñadirDepartamento);

        jPanelInferior.add(jPanelInferior1);

        jPanelInferior2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButBorrarDocencia.setText("Borrar docencia");
        jPanelInferior2.add(jButBorrarDocencia);

        jButEliminarProfesor.setText("Eliminar profesor");
        jPanelInferior2.add(jButEliminarProfesor);

        jButEditarProfesor.setText("Editar");
        jButEditarProfesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButEditarProfesorActionPerformed(evt);
            }
        });
        jPanelInferior2.add(jButEditarProfesor);

        jButAñadirProfesor.setText("Añadir");
        jButAñadirProfesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButAñadirProfesorActionPerformed(evt);
            }
        });
        jPanelInferior2.add(jButAñadirProfesor);

        jPanelInferior.add(jPanelInferior2);

        getContentPane().add(jPanelInferior, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(filler1, java.awt.BorderLayout.LINE_END);
        getContentPane().add(filler2, java.awt.BorderLayout.LINE_START);

        jSplitPane1.setDividerLocation(300);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setContinuousLayout(true);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane1.setViewportView(jTreeProfesores);

        jPanel1.add(jScrollPane1);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.LINE_AXIS));

        jScrollPane2.setViewportView(jTreeDocenciaProfesor);

        jPanel2.add(jScrollPane2);

        jSplitPane1.setRightComponent(jPanel2);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButEditarProfesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButEditarProfesorActionPerformed
    }//GEN-LAST:event_jButEditarProfesorActionPerformed

    private void jButAñadirProfesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButAñadirProfesorActionPerformed
    }//GEN-LAST:event_jButAñadirProfesorActionPerformed

    private void jButEditarDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButEditarDepartamentoActionPerformed
    }//GEN-LAST:event_jButEditarDepartamentoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.JButton jButAñadirDepartamento;
    private javax.swing.JButton jButAñadirProfesor;
    private javax.swing.JButton jButBorrarDocencia;
    private javax.swing.JButton jButEditarDepartamento;
    private javax.swing.JButton jButEditarProfesor;
    private javax.swing.JButton jButEliminarDepartamento;
    private javax.swing.JButton jButEliminarProfesor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanelInferior;
    private javax.swing.JPanel jPanelInferior1;
    private javax.swing.JPanel jPanelInferior2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTree jTreeDocenciaProfesor;
    private javax.swing.JTree jTreeProfesores;
    // End of variables declaration//GEN-END:variables

    private TreeSelectionListener createSelectionListener() {
        return new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent tse) {
                TreePath path = tse.getPath();
                selectItems(path);
                final Object l = path.getLastPathComponent();
//                System.out.println("Selected: " + path.getLastPathComponent().toString());
                if (l instanceof Profesor) {
                    Profesor p = (Profesor) l;
                    profesorMostradoEnDocencia = p;
                    updateTreeDocencia(p);
                } else {
                    updateTreeDocencia(null);
                }

            }

            protected void updateTreeDocencia(Profesor p) {
                if (p != null) {
                    TreeModelAsignaturas modAsig = new TreeModelAsignaturas(dk);
                    modAsig.setLlegarHastaTramos(true);
                    FilteredTreeModel mod = new FilteredTreeModel(modAsig, p);
                    jTreeDocenciaProfesor.setModel(mod);
                    treeCellRendererDocenciaProfesor.setPersonalizedRoot("Docencia para el profesor " + p);
                    jTreeDocenciaProfesor.updateUI();
                    for (int i = 0; i < jTreeDocenciaProfesor.getRowCount(); i++) {
                        int siz = jTreeDocenciaProfesor.getPathForRow(i).getPathCount();
                        if (siz < 5) {//Nivel root-carrera-curso-asignatura-grupo (tramo sin expandir)
                            jTreeDocenciaProfesor.expandRow(i);
                        }
                    }
                } else {
                    jTreeDocenciaProfesor.setModel(null);
                }
            }
        };
    }

    private TreeModelListener createTreeModelListener() {
        return new TreeModelListener() {
            @Override
            public void treeNodesChanged(TreeModelEvent e) {
                jTreeProfesores.expandPath(e.getTreePath());
            }

            @Override
            public void treeNodesInserted(TreeModelEvent e) {
                jTreeProfesores.expandPath(e.getTreePath());
            }

            @Override
            public void treeNodesRemoved(TreeModelEvent e) {
                jTreeProfesores.expandPath(e.getTreePath());
            }

            @Override
            public void treeStructureChanged(TreeModelEvent e) {
                jTreeProfesores.expandPath(e.getTreePath());
            }
        };
    }

    private void selectItems(TreePath path) {
        int depth = path.getPathCount();
        Object o = (Object) path.getLastPathComponent();
        switch (depth) {
            case 1:
                //Nada
                selectedProfesor = null;
                selectedDpto = null;
                break;
            case 2:
                //dpto
                selectedDpto = (Departamento) o;
                selectedProfesor = null;
                break;
            case 3:
                //Profe
                selectedProfesor = (Profesor) o;
                selectedDpto = null;
                break;
        }

    }

    private void mySingleClick(int selRow, TreePath selPath) {
    }

    private void myDoubleClick(int selRow, TreePath selPath) {
        jButEditarProfesorActionPerformed(null);

    }

    /**
     *
     */
    @Override
    public void updateData() {
        jTreeProfesores.updateUI();

    }

    /**
     *
     * @param mainWindow
     */
    @Override
    public void setMainWindow(AbstractMainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    private void creaAcciones() {
        //<editor-fold defaultstate="collapsed" desc="ActionAñadirProfesores">
        class ActionAñadirProfesores extends AbstractAction {

            public ActionAñadirProfesores() {
                super("Añadir nuevo profesor", dk.mc.PROFESOR_ICON);
                putValue(MNEMONIC_KEY, KeyEvent.VK_N);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!dk.getDP().getDataProfesores().getDepartamentos().isEmpty()) {
                    Profesor nuevoProfesor = new Profesor("", "", "");
                    TreePath treePath = jTreeProfesores.getSelectionPath();
                    if (treePath != null) {
                        if (treePath.getPathCount() > 0) //Entra al menos en el nodo de departamentos
                        {
                            nuevoProfesor.setDepartamento((Departamento) treePath.getPathComponent(1));
                        }
                    }
                    JDlgEditProfesor dlg = new JDlgEditProfesor(null, true, nuevoProfesor, (TreeModelProfesores) treeModelProfesores, treePath, dk);
//                Point p = jTreeProfesores.getMousePosition();
//                if (p == null) {
//                    p = new Point(300, 300);
//                }
                    dlg.setLocationRelativeTo(null);
                    dlg.setVisible(true);
                    updateData();
                }
            }
        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="ActionEditarProfesores">
        class ActionEditarProfesores extends AbstractAction {

            public ActionEditarProfesores() {
                super("Editar profesor", dk.mc.PROFESOR_ICON);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath treePath = jTreeProfesores.getSelectionPath();
                if (treePath != null) {
                    if (treePath.getLastPathComponent() instanceof Profesor) {
                        Profesor p = (Profesor) treePath.getLastPathComponent();
                        JDlgEditProfesor dlg = new JDlgEditProfesor(null, true, p, (TreeModelProfesores) treeModelProfesores, treePath, dk);
                        dlg.setLocationRelativeTo(null);
                        dlg.setVisible(true);
                    }
                }
            }
        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="ActionEliminarProfesor">
        class ActionEliminarProfesor extends AbstractAction {

            public ActionEliminarProfesor() {
                super("Eliminar profesor", dk.mc.PROFESOR_ICON);
                putValue(MNEMONIC_KEY, KeyEvent.VK_R);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath treePath = jTreeProfesores.getSelectionPath();
                if ((treePath != null) && (JOptionPane.showConfirmDialog(rootPane, "¿Está seguro de que desea eliminar al profesor?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
                    if (treePath.getLastPathComponent() instanceof Profesor) {
                        Profesor p = (Profesor) treePath.getLastPathComponent();
//                        Departamento d = p.getDepartamento();
//                        d.deleteProfesor(p);
//                        jTreeProfesores.updateUI();
                        KairosCommand cmd = dk.getController().getDeleteProfesorCommand(p);
                        dk.getController().executeCommand(cmd);

                    }
                }
                updateData();
            }
        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="ActionBorrarDocencia">
        class ActionBorrarDocencia extends AbstractAction {

            public ActionBorrarDocencia() {
                super("Eliminar docencia", dk.mc.PROFESOR_ICON);
                putValue(MNEMONIC_KEY, KeyEvent.VK_R);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath treePath = jTreeProfesores.getSelectionPath();
                if (treePath != null) {
                    if ((treePath.getLastPathComponent() instanceof Profesor) && (JOptionPane.showConfirmDialog(rootPane, "¿Está seguro de que desea eliminar la docencia?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
                        Profesor p = (Profesor) treePath.getLastPathComponent();
//                        dk.getDP().getDataProfesores().clearDocenciaProfesor(p);
                        KairosCommand cmd = dk.getController().getClearDocenciaCommand(p);
                        dk.getController().executeCommand(cmd);
                    }
                }
                updateData();
            }
        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="ActionEditarDepartamento">
        class ActionEditarDepartamento extends AbstractAction {

            public ActionEditarDepartamento() {
                super("Editar departamento", dk.mc.DEPARTAMENTO_ICON);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath treePath = jTreeProfesores.getSelectionPath();
                if (treePath != null) {
                    if (treePath.getLastPathComponent() instanceof Departamento) {
                        Departamento d = (Departamento) treePath.getLastPathComponent();
                        JDlgEditDepartamentos dlg = new JDlgEditDepartamentos(null, true, d);
                        dlg.setLocationRelativeTo(null);
                        dlg.setTitle("Editar departamento");
                        dlg.setVisible(true);
                    }
                }
                updateData();
            }
        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="ActionAñadirDepartamento">
        class ActionAñadirDepartamento extends AbstractAction {

            public ActionAñadirDepartamento() {
                super("Añadir departamento", dk.mc.DEPARTAMENTO_ICON);
            }

            @Override
            public void actionPerformed(ActionEvent e) {

                String nombre = JOptionPane.showInputDialog(rootPane, "Nombre:", "");
                Departamento d = new Departamento(nombre);
                KairosCommand cmd = dk.getController().getCreateDepartamentoCommand(d);
                dk.getController().executeCommand(cmd);

                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        updateData();
                        expandTrees();
                    }
                });
            }
        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="ActionEliminarDepartamento">
        class ActionEliminarDepartamento extends AbstractAction {

            public ActionEliminarDepartamento() {
                super("Eliminar departamento", dk.mc.DEPARTAMENTO_ICON);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath treePath = jTreeProfesores.getSelectionPath();
                if (treePath != null) {
                    if ((treePath.getLastPathComponent() instanceof Departamento) && (JOptionPane.showConfirmDialog(rootPane, "¿Está seguro de que desea eliminar el departamento?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
                        Departamento d = (Departamento) treePath.getLastPathComponent();
//                        JDlgEditDepartamentos dlg = new JDlgEditDepartamentos(null, true, d);
                        try {
                            dk.getDP().getDataProfesores().removeDepartamento(d);
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(mainWindow, "No se puede eliminar un departamento que tiene profesores");
                        }
                    }

                }
            }
        }
//</editor-fold>

        actionAñadirProfesores = new ActionAñadirProfesores();
        actionEditarProfesores = new ActionEditarProfesores();
        actionEliminarProfesor = new ActionEliminarProfesor();
        actionBorrarDocencia = new ActionBorrarDocencia();

        actionAñadirProfesores.setEnabled(false);
        actionEditarProfesores.setEnabled(false);
        actionEliminarProfesor.setEnabled(false);

        actionEditarDepartamento = new ActionEditarDepartamento();
        actionAñadirDepartamento = new ActionAñadirDepartamento();
        actionEliminarDepartamento = new ActionEliminarDepartamento();

        actionEditarDepartamento.setEnabled(false);
        actionEliminarDepartamento.setEnabled(false);

        jButAñadirProfesor.setAction(actionAñadirProfesores);
        jButEditarProfesor.setAction(actionEditarProfesores);
        jButEliminarProfesor.setAction(actionEliminarProfesor);
        jButBorrarDocencia.setAction(actionBorrarDocencia);

        jButAñadirDepartamento.setAction(actionAñadirDepartamento);
        jButEditarDepartamento.setAction(actionEditarDepartamento);
        jButEliminarDepartamento.setAction(actionEliminarDepartamento);

        jPopupMenuProfesores = new JPopupMenu("Menu");
        jPopupMenuProfesores.add(actionEditarProfesores);
        jPopupMenuProfesores.add(actionAñadirProfesores);
        jPopupMenuProfesores.add(actionEliminarProfesor);
        jPopupMenuProfesores.add(actionBorrarDocencia);

        jPopupMenuDepartamentos = new JPopupMenu("Menu departamentos");
        jPopupMenuDepartamentos.add(actionAñadirProfesores);
        jPopupMenuDepartamentos.add(actionAñadirDepartamento);
        jPopupMenuDepartamentos.add(actionEditarDepartamento);
        jPopupMenuDepartamentos.add(actionEliminarDepartamento);

        jPopupMenuRoot = new JPopupMenu("Root menu");
        jPopupMenuRoot.add(actionAñadirDepartamento);

    }

    /**
     *
     * @param obj
     * @param type
     */
    @Override
    public void dataEvent(Object obj, int type) {
        jTreeProfesores.updateUI();
        jTreeDocenciaProfesor.updateUI();
        for (int i = 0; i < jTreeDocenciaProfesor.getRowCount(); i++) {
            jTreeDocenciaProfesor.expandRow(i);
        }
    }

    @Override
    public void expandTrees() {
        for (int i = 0; i < jTreeProfesores.getRowCount(); i++) {
            jTreeProfesores.expandRow(i);
        }
    }

    private void implementaDropListenerDocencia() {
        try {
            jTreeDocenciaProfesor.getDropTarget().addDropTargetListener(new DropTargetListener() {

                @Override
                public void dragEnter(DropTargetDragEvent dtde) {
                }

                @Override
                public void dragOver(DropTargetDragEvent dtde) {
                }

                @Override
                public void dropActionChanged(DropTargetDragEvent dtde) {
                }

                @Override
                public void dragExit(DropTargetEvent dte) {
                }

                @Override
                public void drop(DropTargetDropEvent dtde) {
                    Teachable teachable = null;
                    try {
                        teachable = (Teachable) dtde.getTransferable().getTransferData(TeachableDraggable.MY_FLAVOR);
                        //Operaciones en nodo destino
                        KairosCommand cmd = dk.getController().getAsignarDocenciaCommand(profesorMostradoEnDocencia, teachable);
                        dk.getController().executeCommand(cmd);
                    } catch (UnsupportedFlavorException ex) {
                        Logger.getLogger(JTreeProfesoresDropListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(JTreeProfesoresDropListener.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassCastException ex) {
                    }
                }
            });
        } catch (TooManyListenersException ex) {
            Logger.getLogger(JIntTreeProfesores.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

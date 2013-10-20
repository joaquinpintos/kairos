/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor;

import data.DataKairos;
import data.DataProyectoListener;
import data.MyConstants;
import data.profesores.Departamento;
import data.profesores.Profesor;
import data.profesores.TreeCellRendererProfesores;
import data.profesores.TreeModelProfesores;
import gui.MainWindowTabbed;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractAction;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
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
 * @author david
 */
public class JIntTreeProfesores extends javax.swing.JInternalFrame implements DataGUIInterface, DataProyectoListener {

    private final DataKairos dk;
    Profesor selectedProfesor;
    Departamento selectedDpto;
    MainWindowTabbed mainWindow;
    TreeModel treeModelProfesores;
    private JPopupMenu jPopupMenuProfesores;
    private AbstractAction actionEditarProfesores;
    private AbstractAction actionAñadirProfesores;
    private AbstractAction actionQuitarProfesor;
    private AbstractAction actionEditarDepartamento;
    private AbstractAction actionAñadirDepartamento;
    private AbstractAction actionEliminarDepartamento;

    /**
     *
     * @param dk
     */
    public JIntTreeProfesores(DataKairos dk) {
        this.dk = dk;
        selectedDpto = null;
        selectedProfesor = null;
        initComponents();
        //Modelo para el árbol
        treeModelProfesores = new TreeModelProfesores(dk);
        treeModelProfesores.addTreeModelListener(createTreeModelListener());
        this.jTreeProfesores.setModel(treeModelProfesores);
        this.jTreeProfesores.setCellRenderer(new TreeCellRendererProfesores());

        //Simple selection
        this.jTreeProfesores.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        this.jTreeProfesores.addTreeSelectionListener(createSelectionListener());

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
                    actionEditarProfesores.setEnabled(selPath != null && selPath.getLastPathComponent() instanceof Profesor);
                    actionQuitarProfesor.setEnabled(selPath != null && selPath.getLastPathComponent() instanceof Profesor);

                    actionEditarDepartamento.setEnabled(selPath != null && selPath.getLastPathComponent() instanceof Departamento);
                    actionEliminarDepartamento.setEnabled(selPath != null && selPath.getLastPathComponent() instanceof Departamento);
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
                jPopupMenuProfesores.show(e.getComponent(), e.getX(), e.getY());
            }
        };
        jTreeProfesores.addMouseListener(ml);

    }

    /**
     *
     * @return
     */
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeProfesores = new javax.swing.JTree();
        jButEditarProfesor = new javax.swing.JButton();
        jButAñadirProfesor = new javax.swing.JButton();
        jButEditarDepartamento = new javax.swing.JButton();
        jButAsignarDocencia = new javax.swing.JButton();
        jButAñadirDepartamento = new javax.swing.JButton();
        jButEliminarDepartamento = new javax.swing.JButton();

        setTitle("Profesores");
        setPreferredSize(new java.awt.Dimension(800, 600));

        jScrollPane1.setViewportView(jTreeProfesores);

        jButEditarProfesor.setText("Editar");
        jButEditarProfesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButEditarProfesorActionPerformed(evt);
            }
        });

        jButAñadirProfesor.setText("Añadir");
        jButAñadirProfesor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButAñadirProfesorActionPerformed(evt);
            }
        });

        jButEditarDepartamento.setText("Editar departamentos");
        jButEditarDepartamento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButEditarDepartamentoActionPerformed(evt);
            }
        });

        jButAsignarDocencia.setText("Asignar docencia");
        jButAsignarDocencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButAsignarDocenciaActionPerformed(evt);
            }
        });

        jButAñadirDepartamento.setText("Añadir departamento");

        jButEliminarDepartamento.setText("Eliminar departamento");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(173, 173, 173)
                                .addComponent(jButEditarDepartamento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 91, Short.MAX_VALUE)
                                .addComponent(jButAñadirDepartamento))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButEliminarDepartamento)))
                        .addGap(18, 18, 18)
                        .addComponent(jButAsignarDocencia, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButAñadirProfesor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButEditarProfesor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButEditarProfesor)
                    .addComponent(jButEditarDepartamento)
                    .addComponent(jButAsignarDocencia)
                    .addComponent(jButAñadirDepartamento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButAñadirProfesor)
                    .addComponent(jButEliminarDepartamento))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButEditarProfesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButEditarProfesorActionPerformed
    }//GEN-LAST:event_jButEditarProfesorActionPerformed

    private void jButAñadirProfesorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButAñadirProfesorActionPerformed
    }//GEN-LAST:event_jButAñadirProfesorActionPerformed

    private void jButEditarDepartamentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButEditarDepartamentoActionPerformed
    }//GEN-LAST:event_jButEditarDepartamentoActionPerformed

    private void jButAsignarDocenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButAsignarDocenciaActionPerformed
        if (selectedProfesor != null) {
         //TODO: FIX THIS:   mainWindow.getjIntAsignaciones().seleccionaProfesor(selectedProfesor);
            mainWindow.getjTabPrincipal().setSelectedComponent(mainWindow.getjIntAsignaciones());
        }
    }//GEN-LAST:event_jButAsignarDocenciaActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButAsignarDocencia;
    private javax.swing.JButton jButAñadirDepartamento;
    private javax.swing.JButton jButAñadirProfesor;
    private javax.swing.JButton jButEditarDepartamento;
    private javax.swing.JButton jButEditarProfesor;
    private javax.swing.JButton jButEliminarDepartamento;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTreeProfesores;
    // End of variables declaration//GEN-END:variables

    private TreeSelectionListener createSelectionListener() {
        return new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent tse) {
                TreePath path = tse.getPath();
                selectItems(path);


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
        actionAñadirProfesores.setEnabled(!dk.getDP().getDataProfesores().getDepartamentos().isEmpty());

    }

    /**
     *
     * @param mainWindow
     */
    @Override
    public void setMainWindow(MainWindowTabbed mainWindow) {
        this.mainWindow = mainWindow;
    }

    private void creaAcciones() {
        class ActionAñadirProfesores extends AbstractAction {

            public ActionAñadirProfesores() {
                super("Añadir nuevo profesor", MyConstants.PROFESOR_ICON);
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
                    JDlgEditProfesor dlg = new JDlgEditProfesor(null, true, nuevoProfesor, (TreeModelProfesores) treeModelProfesores, treePath);
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
        class ActionEditarProfesores extends AbstractAction {

            public ActionEditarProfesores() {
                super("Editar profesor", MyConstants.PROFESOR_ICON);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath treePath = jTreeProfesores.getSelectionPath();
                if (treePath != null) {
                    if (treePath.getLastPathComponent() instanceof Profesor) {
                        Profesor p = (Profesor) treePath.getLastPathComponent();
                        JDlgEditProfesor dlg = new JDlgEditProfesor(null, true, p, (TreeModelProfesores) treeModelProfesores, treePath);
                        dlg.setLocationRelativeTo(null);
                        dlg.setVisible(true);
                        dk.getDP().getDataProfesores().fireDataEvent(p, DataProyectoListener.MODIFY);
                    }
                }
                updateData();
            }
        }
        class ActionQuitarProfesor extends AbstractAction {

            public ActionQuitarProfesor() {
                super("Eliminar profesor", MyConstants.PROFESOR_ICON);
                putValue(MNEMONIC_KEY, KeyEvent.VK_R);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath treePath = jTreeProfesores.getSelectionPath();
                if (treePath != null) {
                    if (treePath.getLastPathComponent() instanceof Profesor) {
                        Profesor p = (Profesor) treePath.getLastPathComponent();
                        Departamento d = p.getDepartamento();
                        d.remove(p);
                        jTreeProfesores.updateUI();
                    }
                }
                updateData();
            }
        }
        class ActionEditarDepartamento extends AbstractAction {

            public ActionEditarDepartamento() {
                super("Editar departamento", MyConstants.DEPARTAMENTO_ICON);
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
                        if (dlg.getReturnStatus() == JDlgEditDepartamentos.RET_OK) {
                            dk.getDP().getDataProfesores().fireDataEvent(d, DataProyectoListener.MODIFY);
                        }

                    }
                }
                updateData();
            }
        }
        class ActionAñadirDepartamento extends AbstractAction {

            public ActionAñadirDepartamento() {
                super("Añadir departamento", MyConstants.DEPARTAMENTO_ICON);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Departamento d = new Departamento("");
                JDlgEditDepartamentos dlg = new JDlgEditDepartamentos(null, true, d);
                dlg.setTitle("Añadir departamento");
                dlg.setLocationRelativeTo(null);
                dlg.setVisible(true);
                if (dlg.getReturnStatus() == JDlgEditDepartamentos.RET_OK) {
                    dk.getDP().getDataProfesores().addDepartamento(d);
                }
                updateData();
            }
        }
        class ActionEliminarDepartamento extends AbstractAction {

            public ActionEliminarDepartamento() {
                super("Eliminar departamento", MyConstants.DEPARTAMENTO_ICON);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath treePath = jTreeProfesores.getSelectionPath();
                if (treePath != null) {
                    if (treePath.getLastPathComponent() instanceof Departamento) {
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

        actionAñadirProfesores = new ActionAñadirProfesores();
        actionEditarProfesores = new ActionEditarProfesores();
        actionQuitarProfesor = new ActionQuitarProfesor();

        actionEditarDepartamento = new ActionEditarDepartamento();
        actionAñadirDepartamento = new ActionAñadirDepartamento();
        actionEliminarDepartamento = new ActionEliminarDepartamento();

        jButAñadirProfesor.setAction(actionAñadirProfesores);
        jButEditarProfesor.setAction(actionEditarProfesores);
        jButAñadirDepartamento.setAction(actionAñadirDepartamento);
        jButEditarDepartamento.setAction(actionEditarDepartamento);
        jButEliminarDepartamento.setAction(actionEliminarDepartamento);


        jPopupMenuProfesores = new JPopupMenu("Mi menu");
        jPopupMenuProfesores.add(actionEditarProfesores);
        jPopupMenuProfesores.add(actionAñadirProfesores);
        jPopupMenuProfesores.add(actionQuitarProfesor);
    }

    /**
     *
     * @param obj
     * @param type
     */
    @Override
    public void dataEvent(Object obj, int type) {
        jTreeProfesores.updateUI();
    }
}

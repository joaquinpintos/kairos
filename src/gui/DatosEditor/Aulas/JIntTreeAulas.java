/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Aulas;

import data.aulas.CarreraCursoGrupoContainer;
import data.DataKairos;
import data.DataProyectoListener;
import data.MyConstants;
import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.DataAsignaturas;
import data.asignaturas.Grupo;
import data.aulas.Aula;
import data.aulas.DataAulas;
import data.profesores.DataProfesores;
import gui.AbstractMainWindow;
import gui.DatosEditor.DataGUIInterface;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import javax.swing.AbstractAction;
import javax.swing.AbstractListModel;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.ActionMap;
import javax.swing.DropMode;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import testers.AsigTester;

/**
 *
 * @author david
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

    /**
     * Creates new form JIntTreeAulas
     *
     * @param dk
     * @throws Exception
     */
    public JIntTreeAulas(DataKairos dk) throws Exception {
        initComponents();
        AsigTester asig = new AsigTester();
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
        jTreeGrupoCursos.setCellRenderer(new JTreeGrupoCursosSinAulaRenderer(dk));
        jTreeGrupoCursos.setDragEnabled(true);

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

        jPanelInferior1.setLayout(new javax.swing.BoxLayout(jPanelInferior1, javax.swing.BoxLayout.X_AXIS));
        jPanelInferior1.add(filler5);

        jButton1.setText("jButton1");
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
        mainWindow.expandTree(jTreeGrupoCursos);
        mainWindow.expandTree(jTreeAulas);
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
        class AñadirAulaAction extends AbstractAction {

            public AñadirAulaAction() {
                super("Añadir aula", MyConstants.ADD_ICON);
                putValue(MNEMONIC_KEY, KeyEvent.VK_A);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Aula nuevaAula = new Aula("");
                JDlgEditarAula dlg = new JDlgEditarAula(null, true, nuevaAula);
                dlg.setVisible(true);
                if (dlg.getReturnStatus() == JDlgEditarAula.RET_OK) {
                    System.out.println("Added aula " + nuevaAula);
                    dk.getDP().getDataAulas().addAula(nuevaAula);
                }

            }
        }//End of class AñadirAula

        class EditarAulaAction extends AbstractAction {

            public EditarAulaAction() {
                super("Editar", MyConstants.ADD_ICON);
                putValue(MNEMONIC_KEY, KeyEvent.VK_E);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Object sel = jTreeAulas.getSelectionPath().getLastPathComponent();
                if (sel instanceof Aula) {
                    Aula aulaEditar = (Aula) sel;
                    JDlgEditarAula dlg = new JDlgEditarAula(null, true, aulaEditar);
                    dlg.setVisible(true);
                    if (dlg.getReturnStatus() == JDlgEditarAula.RET_OK) {
                        dk.getDP().getDataAulas().fireDataEvent(aulaEditar, DataProyectoListener.MODIFY);
                    }
                }

            }
        }//End of class EditarAula
        class EliminarAulaAction extends AbstractAction {

            public EliminarAulaAction() {
                super("Eliminar", MyConstants.ADD_ICON);
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

        añadirAulaAction = new AñadirAulaAction();
        jButAñadirAula.setAction(añadirAulaAction);

        editarAulaAction = new EditarAulaAction();
        jButEditarAula.setAction(editarAulaAction);

        eliminarAulaAction = new EliminarAulaAction();
        jButEliminarAula.setAction(eliminarAulaAction);

        jpopMenu = new JPopupMenu();
        jpopMenu.add(añadirAulaAction);
        jpopMenu.add(editarAulaAction);
        jpopMenu.add(eliminarAulaAction);

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
        jTreeAulas.updateUI();
        jTreeGrupoCursos.updateUI();
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
}

class CarreraGrupoCursosNoAsignadosSimpleListModel extends AbstractListModel<CarreraCursoGrupoContainer> {

    ArrayList<CarreraCursoGrupoContainer> data;
    private final DataKairos dk;

    public CarreraGrupoCursosNoAsignadosSimpleListModel(DataKairos dk) {
        this.dk = dk;
        data = new ArrayList<CarreraCursoGrupoContainer>();
        updateData();
    }

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public CarreraCursoGrupoContainer getElementAt(int index) {
        return data.get(index);
    }
//MUY MUY POCO EFICIENTE!!!

    public final void updateData() {
        //Actualizo lista de Carreras-Cursos-Grupos, manualmente
        data.clear();
        HashSet<CarreraCursoGrupoContainer> dataAux = new HashSet<CarreraCursoGrupoContainer>();
        for (Carrera c : dk.getDP().getDataAsignaturas().getCarreras()) {
            for (Curso cu : c.getCursos()) {
                for (Asignatura asig : cu.getAsignaturas()) {
                    for (Grupo g : asig.getGrupos().getGrupos()) {
                        String hash = g.getHashCarreraGrupoCurso();
                        String nombre = g.getNombreConCarrera();
                        CarreraCursoGrupoContainer con = new CarreraCursoGrupoContainer(hash, nombre);
                        if (!dk.getDP().isCarreraGrupoCursoContainerAssociadoConAula(con)) {
                            dataAux.add(con);
                        }
                    }
                }
            }
        }
        for (CarreraCursoGrupoContainer con : dataAux) {
            if (!data.contains(con)) {
                data.add(con);
            }
        }
        Collections.sort(data);
    }

    public void remove(CarreraCursoGrupoContainer c) {
        data.remove(c);
    }

    void add(CarreraCursoGrupoContainer grupoDragged) {
        data.add(grupoDragged);
    }

}

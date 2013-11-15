/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Asignaturas;

import data.DataKairos;
import data.DataProyecto;
import data.DataProyectoListener;
import data.MyConstants;
import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.DataAsignaturas;
import data.asignaturas.Grupo;
import data.asignaturas.Tramo;
import gui.AbstractMainWindow;
import gui.DatosEditor.DataGUIInterface;
import gui.DatosEditor.Docencia.JTreeAsignaturasDropListener;
import gui.DatosEditor.Docencia.JTreeAsignaturasTransferHandler;
import gui.TreeAsignaturas;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.TooManyListenersException;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

/**
 *
 * @author david
 */
public class JIntEditorAsignaturas extends javax.swing.JInternalFrame implements DataGUIInterface, DataProyectoListener, TreeAsignaturas {
    
    private DataProyecto dataProyecto;
    private AbstractMainWindow mainWindow;
    private final DataKairos dk;
    private AbstractAction añadirAsignaturaAction;
    private AbstractAction editarAsignaturaAction;
    private AbstractAction eliminarAsignaturaAction;
    private JPopupMenu jPopMenuAsignaturas;
    private AbstractAction añadirCarreraAction;
    private AbstractAction añadirCursoAction;
    private AbstractAction editarCursoAction;
    private JPopupMenu jPopMenuCursos;
    private JPopupMenu jPopMenuCarreras;
    private AbstractAction eliminarCursoAction;
    private AbstractAction añadirGrupoAction;
    private AbstractAction editarGrupoAction;
    private JPopupMenu jPopMenuGrupos;
    private AbstractAction eliminarGrupoAction;
    private AbstractAction editarCarreraAction;
    private AbstractAction añadirTramosAction;
    private AbstractAction eliminarTramoAction;
    private JPopupMenu jPopMenuTramos;
    private AbstractAction eliminarAction;
    private AbstractAction añadirAction;
    private AbstractAction eliminarCarreraAction;

    /**
     * Creates new form JIntTreeAsignaturas
     *
     * @param dk
     */
    public JIntEditorAsignaturas(DataKairos dk) {
        initComponents();
        this.dk = dk;
        
        this.jTreeAsignaturas.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        TreeModelAsignaturas model = new TreeModelAsignaturas(dk);
        model.setLlegarHastaTramos(true);
        jTreeAsignaturas.setModel(model);
        
        TreeCellRendererAsignaturas treeCellRendererAsignaturas = new TreeCellRendererAsignaturas();
        jTreeAsignaturas.setCellRenderer(treeCellRendererAsignaturas);
        jTreeAsignaturas.setTransferHandler(new JTreeAsignaturasTransferHandler());
        jTreeAsignaturas.setDragEnabled(true);
        jTreeAsignaturas.setDropTarget(new DropTarget());
        try {
            jTreeAsignaturas.getDropTarget().addDropTargetListener(new JTreeAsignaturasDropListener((TreeAsignaturas) this, dk));
        } catch (TooManyListenersException ex) {
        }
        creaActions();
    }

    /**
     *
     * @return
     */
    @Override
    public JTree getjTreeAsignaturas() {
        return jTreeAsignaturas;
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
        jTreeAsignaturas = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButAñadirCarrera = new javax.swing.JButton();
        jButAñadirCursos = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButEliminarAsignatura = new javax.swing.JButton();
        jButEditarAsignatura = new javax.swing.JButton();
        jButAñadirAsignatura = new javax.swing.JToggleButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTreeAsignaturas1 = new javax.swing.JTree();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jButAñadirCarrera1 = new javax.swing.JButton();
        jButAñadirCursos1 = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jButEliminarAsignatura1 = new javax.swing.JButton();
        jButEditarAsignatura1 = new javax.swing.JButton();
        jButAñadirAsignatura1 = new javax.swing.JToggleButton();
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));
        filler4 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 0), new java.awt.Dimension(5, 32767));

        setResizable(true);

        jScrollPane1.setViewportView(jTreeAsignaturas);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButAñadirCarrera.setText("jButton1");
        jPanel3.add(jButAñadirCarrera);

        jButAñadirCursos.setText("jButton2");
        jPanel3.add(jButAñadirCursos);

        jPanel1.add(jPanel3);

        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButEliminarAsignatura.setText("Eliminar asignatura");
        jPanel2.add(jButEliminarAsignatura);

        jButEditarAsignatura.setText("Editar asignatura");
        jPanel2.add(jButEditarAsignatura);

        jButAñadirAsignatura.setText("Añadir asignatura");
        jPanel2.add(jButAñadirAsignatura);

        jPanel1.add(jPanel2);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(filler1, java.awt.BorderLayout.LINE_END);
        getContentPane().add(filler2, java.awt.BorderLayout.LINE_START);

        jInternalFrame1.setResizable(true);

        jScrollPane2.setViewportView(jTreeAsignaturas1);

        jInternalFrame1.getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.Y_AXIS));

        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButAñadirCarrera1.setText("jButton1");
        jPanel5.add(jButAñadirCarrera1);

        jButAñadirCursos1.setText("jButton2");
        jPanel5.add(jButAñadirCursos1);

        jPanel4.add(jPanel5);

        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButEliminarAsignatura1.setText("Eliminar asignatura");
        jPanel6.add(jButEliminarAsignatura1);

        jButEditarAsignatura1.setText("Editar asignatura");
        jPanel6.add(jButEditarAsignatura1);

        jButAñadirAsignatura1.setText("Añadir asignatura");
        jPanel6.add(jButAñadirAsignatura1);

        jPanel4.add(jPanel6);

        jInternalFrame1.getContentPane().add(jPanel4, java.awt.BorderLayout.PAGE_END);
        jInternalFrame1.getContentPane().add(filler3, java.awt.BorderLayout.LINE_END);
        jInternalFrame1.getContentPane().add(filler4, java.awt.BorderLayout.LINE_START);

        getContentPane().add(jInternalFrame1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.Box.Filler filler4;
    private javax.swing.JToggleButton jButAñadirAsignatura;
    private javax.swing.JToggleButton jButAñadirAsignatura1;
    private javax.swing.JButton jButAñadirCarrera;
    private javax.swing.JButton jButAñadirCarrera1;
    private javax.swing.JButton jButAñadirCursos;
    private javax.swing.JButton jButAñadirCursos1;
    private javax.swing.JButton jButEditarAsignatura;
    private javax.swing.JButton jButEditarAsignatura1;
    private javax.swing.JButton jButEliminarAsignatura;
    private javax.swing.JButton jButEliminarAsignatura1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree jTreeAsignaturas;
    private javax.swing.JTree jTreeAsignaturas1;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    @Override
    public void updateData() {
        jTreeAsignaturas.updateUI();
    }

    /**
     *
     * @param mainWindow
     */
    @Override
    public void setMainWindow(AbstractMainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }
    
    private void creaActions() {
        class AñadirAsignaturaAction extends AbstractAction {
            
            public AñadirAsignaturaAction() {
                super("Añadir asignatura", MyConstants.ADD_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                
                Asignatura nuevaAsignatura = new Asignatura("");
                JDlgAñadirAsignatura dlg = new JDlgAñadirAsignatura(mainWindow, true, dk.getDP(), nuevaAsignatura, true);
                dlg.setLocationRelativeTo(null);
                if ((pat != null) && (pat.getPath().length > 1)) {
                    pat.getPathComponent(1);
                    
                    Curso cu = (Curso) pat.getPathComponent(2);
                    Carrera car = (Carrera) pat.getPathComponent(1);
                    dlg.getjComboEstudios().setSelectedItem(car);
                    dlg.getjComboCursos().setSelectedItem(cu);
                }
                
                dlg.setVisible(true);
//                jTreeAsignaturas.updateUI();
            }
        }
        class EditarAsignaturaAction extends AbstractAction {
            
            public EditarAsignaturaAction() {
                super("Editar asignatura", MyConstants.ASIGNATURA_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                Asignatura asigEdit = (Asignatura) pat.getLastPathComponent();
                jDlgEditarAsignatura dlg = new jDlgEditarAsignatura(mainWindow, true, dk.getDP(), asigEdit);
                dlg.setLocationRelativeTo(null);
                dlg.setVisible(true);
                dk.getDP().getDataProfesores().fireDataEvent(asigEdit, DataProyectoListener.MODIFY);
                jTreeAsignaturas.updateUI();
            }
        }
        class EliminarAsignaturaAction extends AbstractAction {
            
            public EliminarAsignaturaAction() {
                super("Eliminar asignatura", MyConstants.REMOVE_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                if (pat.getLastPathComponent() instanceof Asignatura) {
                    Asignatura asigEdit = (Asignatura) pat.getLastPathComponent();
                    if (JOptionPane.showConfirmDialog(rootPane, "¿Desea borrar realmente la asignatura " + asigEdit.getNombre() + "?", "Advertencia", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        Curso cu = asigEdit.getParent();
                        cu.removeAsignatura(asigEdit);
                    }
                    jTreeAsignaturas.updateUI();
                }
            }
        }
        class AñadirCarreraAction extends AbstractAction {
            
            public AñadirCarreraAction() {
                super("Añadir carrera", MyConstants.CARRERA_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Carrera car = new Carrera("");
                JDlgAñadirCarrera dlg = new JDlgAñadirCarrera(mainWindow, true, car);
                dlg.setLocationRelativeTo(null);
                dlg.setTitle("Añadir carrera");
                dlg.setVisible(true);
                if (dlg.getReturnStatus() == JDlgAñadirAsignatura.RET_OK) {
                    dk.getDP().getDataAsignaturas().addCarrera(car);
                }
                updateData();
            }
        }
        class EliminarCarreraAction extends AbstractAction {
            
            public EliminarCarreraAction() {
                super("Eliminar carrera", MyConstants.DELETE_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                if (pat.getLastPathComponent() instanceof Carrera) {
                    Carrera car = (Carrera) pat.getLastPathComponent();
                    if (JOptionPane.showConfirmDialog(rootPane, "¿Desea borrar realmente la carrera" + car.getNombre() + "?", "Advertencia", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        car.getParent().removeCarrera(car);
                    }
                    updateData();
                    
                }
            }
        }
        class AñadirCursosAction extends AbstractAction {
            
            public AñadirCursosAction() {
                super("Añadir curso", MyConstants.CURSO_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                if (pat.getLastPathComponent() instanceof Carrera) {
                    Carrera car = (Carrera) pat.getLastPathComponent();
                    Curso cur = new Curso("");
                    JDlgEditarCurso dlg = new JDlgEditarCurso(mainWindow, true, cur, dk, true);
                    dlg.setLocationRelativeTo(null);
                    dlg.setVisible(true);
                    if (dlg.getReturnStatus() == JDlgEditarCurso.RET_OK) {//Añado el curso
                        dlg.getCarrera().addCurso(cur);
                    }
                    updateData();
                    
                }
            }
        }
        class EliminarCursoAction extends AbstractAction {
            
            public EliminarCursoAction() {
                super("Eliminar curso", MyConstants.DELETE_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                if (pat.getLastPathComponent() instanceof Curso) {
                    Curso cur = (Curso) pat.getLastPathComponent();
                    if (JOptionPane.showConfirmDialog(rootPane, "¿Desea borrar realmente el curso " + cur.getNombre() + "?", "Advertencia", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        cur.getParent().removeCurso(cur);
                    }
                    updateData();
                    
                }
            }
        }
        class EditarCursoAction extends AbstractAction {
            
            public EditarCursoAction() {
                super("Editar curso", MyConstants.CURSO_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                if (pat.getLastPathComponent() instanceof Curso) {
                    Curso cur = (Curso) pat.getLastPathComponent();
                    JDlgEditarCurso dlg = new JDlgEditarCurso(mainWindow, true, cur, dk, false);
                    dlg.setLocationRelativeTo(null);
                    dlg.setVisible(true);
                    updateData();
                    
                }
            }
        }
        class AñadirGrupoAction extends AbstractAction {
            
            public AñadirGrupoAction() {
                super("Añadir grupo", MyConstants.GRUPO_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                if (pat.getLastPathComponent() instanceof Asignatura) {
                    Asignatura asig = (Asignatura) pat.getLastPathComponent();
                    Grupo gr = new Grupo("");
                    JDlgAñadirGrupo dlg = new JDlgAñadirGrupo(mainWindow, true, gr, true);
                    dlg.setLocationRelativeTo(null);
                    dlg.setVisible(true);
                    if (dlg.getReturnStatus() == JDlgAñadirGrupo.RET_OK) {
                        asig.addGrupo(gr);
                    }
                    updateData();
                    
                }
            }
        }
        class EditarGrupoAction extends AbstractAction {
            
            public EditarGrupoAction() {
                super("Editar grupo", MyConstants.GRUPO_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                if (pat.getLastPathComponent() instanceof Grupo) {
                    Grupo gr = (Grupo) pat.getLastPathComponent();
                   String nombre = JOptionPane.showInputDialog(rootPane, "Nombre:", gr.getNombre());
                    if (nombre != null) {
                        gr.setNombre(nombre);
                    }
                    updateData();
                    
                }
            }
        }
        class EliminarGrupoAction extends AbstractAction {
            
            public EliminarGrupoAction() {
                super("Eliminar grupo", MyConstants.DELETE_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                if (pat.getLastPathComponent() instanceof Grupo) {
                    Grupo gr = (Grupo) pat.getLastPathComponent();
                    if (JOptionPane.showConfirmDialog(rootPane, "¿Desea borrar realmente el grupo " + gr.getNombre() + "?", "Advertencia", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        gr.getParent().removeGrupo(gr);
                    }
                    updateData();
                    
                }
            }
        }
        class EditarCarreraAction extends AbstractAction {
            
            public EditarCarreraAction() {
                super("Editar carrera", null);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                if (pat.getLastPathComponent() instanceof Carrera) {
                    Carrera ca = (Carrera) pat.getLastPathComponent();
                    String nombre = JOptionPane.showInputDialog(rootPane, "Nombre:", ca.getNombre());
                    if (nombre != null) {
                        ca.setNombre(nombre);
                    }
                }
            }
        }
        class AñadirTramosAction extends AbstractAction {
            
            public AñadirTramosAction() {
                super("Añadir tramos", MyConstants.TRAMO_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Grupo grupoEditado = null;
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                if (pat.getLastPathComponent() instanceof Grupo) {
                    grupoEditado = (Grupo) pat.getLastPathComponent();
                }
                if (pat.getLastPathComponent() instanceof Tramo) {
                    grupoEditado = (Grupo) pat.getParentPath().getLastPathComponent();
                }
                if (grupoEditado != null) {
                    JDlgAñadirTramos dlg = new JDlgAñadirTramos(mainWindow, true, grupoEditado);
                    dlg.setVisible(true);
                    dlg.setLocationRelativeTo(null);
                    if (dlg.getReturnStatus() == JDlgAñadirTramos.RET_OK) {
                        jTreeAsignaturas.updateUI();
                    }
                }
            }
        }
        class EliminarTramoAction extends AbstractAction {
            
            public EliminarTramoAction() {
                super("Eliminar tramo", MyConstants.DELETE_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Tramo tramoABorrar;
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                if (pat.getLastPathComponent() instanceof Tramo) {
                    tramoABorrar = (Tramo) pat.getLastPathComponent();
                    Grupo gr = (Grupo) pat.getParentPath().getLastPathComponent();
                    if (JOptionPane.showConfirmDialog(rootPane, "¿Desea borrar realmente el tramo?", "Advertencia", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        gr.removeTramoGrupoCompleto(tramoABorrar);
                    }
                    jTreeAsignaturas.updateUI();
                }
            }
        }
        class EliminarAction extends AbstractAction {
            
            public EliminarAction() {
                super("Eliminar", MyConstants.DELETE_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                Object obj = pat.getLastPathComponent();
                //TODO: Falta action eliminarCarrera
                if (obj instanceof Carrera) {
                    eliminarCarreraAction.actionPerformed(e);
                }
                if (obj instanceof Curso) {
                    eliminarCursoAction.actionPerformed(e);
                }
                if (obj instanceof Asignatura) {
                    eliminarAsignaturaAction.actionPerformed(e);
                }
                if (obj instanceof Grupo) {
                    eliminarGrupoAction.actionPerformed(e);
                }
                if (obj instanceof Tramo) {
                    eliminarTramoAction.actionPerformed(e);
                }
            }
        }
        class AñadirAction extends AbstractAction {
            
            public AñadirAction() {
                super("Añadir", MyConstants.ADD_ICON);
            }
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath pat = jTreeAsignaturas.getSelectionPath();
                Object obj = pat.getLastPathComponent();
                //TODO: Falta action eliminarCarrera
                if (obj instanceof DataAsignaturas) {
                    añadirCarreraAction.actionPerformed(e);
                }
                if (obj instanceof Carrera) {
                    añadirCursoAction.actionPerformed(e);
                }
                if (obj instanceof Curso) {
                    añadirAsignaturaAction.actionPerformed(e);
                }
                if (obj instanceof Asignatura) {
                    añadirGrupoAction.actionPerformed(e);
                }
                if (obj instanceof Grupo) {
                    añadirTramosAction.actionPerformed(e);
                }
                if (obj instanceof Tramo) {
                    añadirTramosAction.actionPerformed(e);
                }
            }
        }
        eliminarAction = new EliminarAction();
        añadirAction = new AñadirAction();
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionMap actionMap = getRootPane().getActionMap();
        //Mapeo la tecla DEL para borrar la restricción
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Eliminar");
        actionMap.put("Eliminar", eliminarAction);

        //Mapeo la tecla INS para añadir nueva restricción
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "Añadir");
        actionMap.put("Añadir", añadirAction);
        
        editarCarreraAction = new EditarCarreraAction();
        eliminarGrupoAction = new EliminarGrupoAction();
        añadirGrupoAction = new AñadirGrupoAction();
        editarGrupoAction = new EditarGrupoAction();
        
        eliminarCursoAction = new EliminarCursoAction();
        editarCursoAction = new EditarCursoAction();
        añadirCursoAction = new AñadirCursosAction();
        
        jButAñadirCursos.setAction(añadirCursoAction);
        
        añadirCarreraAction = new AñadirCarreraAction();
        eliminarCarreraAction=new EliminarCarreraAction();
        
        jButAñadirCarrera.setAction(añadirCarreraAction);
        
        añadirAsignaturaAction = new AñadirAsignaturaAction();
        
        jButAñadirAsignatura.setAction(añadirAsignaturaAction);
        
        añadirTramosAction = new AñadirTramosAction();
        eliminarTramoAction = new EliminarTramoAction();
        editarAsignaturaAction = new EditarAsignaturaAction();
        
        jButEditarAsignatura.setAction(editarAsignaturaAction);
        
        eliminarAsignaturaAction = new EliminarAsignaturaAction();
        
        jButEliminarAsignatura.setAction(eliminarAsignaturaAction);
        
        jPopMenuAsignaturas = new JPopupMenu();
        
        jPopMenuAsignaturas.add(editarAsignaturaAction);
        
        jPopMenuAsignaturas.add(eliminarAsignaturaAction);
        
        jPopMenuAsignaturas.add(añadirGrupoAction);
        
        jPopMenuCursos = new JPopupMenu();
        
        jPopMenuCursos.add(añadirAsignaturaAction);
        
        jPopMenuCursos.add(editarCursoAction);
        
        jPopMenuCursos.add(eliminarCursoAction);
        
        jPopMenuCarreras = new JPopupMenu();
        
        jPopMenuCarreras.add(añadirCursoAction);
        
        jPopMenuCarreras.add(editarCarreraAction);
        
        jPopMenuGrupos = new JPopupMenu();
        
        jPopMenuGrupos.add(añadirTramosAction);
        
        jPopMenuGrupos.add(editarGrupoAction);
        
        jPopMenuGrupos.add(eliminarGrupoAction);
        
        jPopMenuTramos = new JPopupMenu();
        
        jPopMenuTramos.add(añadirTramosAction);
        
        jPopMenuTramos.add(eliminarTramoAction);
        //Creo mouse listener para jtreeAsignaturas
        MouseListener ml;
        ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    TreePath selPath = jTreeAsignaturas.getPathForLocation(e.getX(), e.getY());
                    jTreeAsignaturas.setSelectionPath(selPath);
                    doPop(e);
                }
                try {
                    TreePath selPath = jTreeAsignaturas.getPathForLocation(e.getX(), e.getY());
                    añadirAsignaturaAction.setEnabled(selPath != null && selPath.getLastPathComponent() instanceof Curso);
                    editarAsignaturaAction.setEnabled(selPath != null && selPath.getLastPathComponent() instanceof Asignatura);
                    eliminarAsignaturaAction.setEnabled(selPath != null && selPath.getLastPathComponent() instanceof Asignatura);
                    añadirCursoAction.setEnabled(selPath != null && selPath.getLastPathComponent() instanceof Carrera);
                    editarCursoAction.setEnabled(selPath != null && selPath.getLastPathComponent() instanceof Curso);
                    eliminarCursoAction.setEnabled(selPath != null && selPath.getLastPathComponent() instanceof Curso);
                } catch (NullPointerException ex) {
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
            
            private void doPop(MouseEvent e) {
                TreePath selPath = jTreeAsignaturas.getPathForLocation(e.getX(), e.getY());
                jTreeAsignaturas.setSelectionPath(selPath);
                if (selPath.getLastPathComponent() instanceof Carrera) {
                    jPopMenuCarreras.show(e.getComponent(), e.getX(), e.getY());
                }
                if (selPath.getLastPathComponent() instanceof Asignatura) {
                    jPopMenuAsignaturas.show(e.getComponent(), e.getX(), e.getY());
                }
                if (selPath.getLastPathComponent() instanceof Curso) {
                    jPopMenuCursos.show(e.getComponent(), e.getX(), e.getY());
                }
                if (selPath.getLastPathComponent() instanceof Grupo) {
                    jPopMenuGrupos.show(e.getComponent(), e.getX(), e.getY());
                }
                if (selPath.getLastPathComponent() instanceof Tramo) {
                    jPopMenuTramos.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        };
        
        jTreeAsignaturas.addMouseListener(ml);
        
    }

    /**
     *
     * @param obj
     * @param type
     */
    @Override
    public void dataEvent(Object obj, int type
    ) {
        jTreeAsignaturas.updateUI();
    }
}
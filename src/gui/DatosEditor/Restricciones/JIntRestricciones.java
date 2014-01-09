/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Restricciones;

import data.DataKairos;
import data.DataProyectoListener;
import gui.DatosEditor.DataGUIInterface;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractAction;
import data.restricciones.Restriccion;
import gui.AbstractMainWindow;
import java.awt.event.KeyEvent;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public final class JIntRestricciones extends javax.swing.JInternalFrame implements DataGUIInterface, DataProyectoListener {

    private AbstractMainWindow mainWindow;
    private final ListModelRestricciones listModelRestricciones;
    private AbstractAction elegirNivelRestricionAction;
    private AbstractAction editarRestriccionAction;
    private AbstractAction eliminarRestriccion;
    private AbstractAction añadirRestriccionAction;
    private MouseListener jListRestriccionesMouseListener;
    private final DataKairos dk;
    private JPopupMenu jPopupListRestricciones;

    /**
     * Creates new form JIntRestricciones
     *
     * @param dk
     */
    public JIntRestricciones(DataKairos dk) {
        initComponents();
        this.dk = dk;
        listModelRestricciones = new ListModelRestricciones(dk);
        jListRestricciones.setModel(listModelRestricciones);
        RestriccionListRenderer rend = new RestriccionListRenderer();
        jListRestricciones.setCellRenderer(rend);
        creaAccionesYListeners();
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
        jListRestricciones = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        jButCambiarNivel = new javax.swing.JButton();
        jButEliminarRestriccion = new javax.swing.JButton();
        jButCrearNuevaRestriccion = new javax.swing.JButton();
        jButEditarRestriccion = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler2 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        filler3 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 32767));

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        setResizable(true);
        getContentPane().setLayout(new java.awt.BorderLayout(10, 0));

        jScrollPane1.setViewportView(jListRestricciones);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.setPreferredSize(new java.awt.Dimension(786, 50));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 15, 10));

        jButCambiarNivel.setText("Cambiar Nivel");
        jPanel1.add(jButCambiarNivel);

        jButEliminarRestriccion.setText("Eliminar");
        jPanel1.add(jButEliminarRestriccion);

        jButCrearNuevaRestriccion.setText("Crear");
        jPanel1.add(jButCrearNuevaRestriccion);

        jButEditarRestriccion.setText("Editar");
        jPanel1.add(jButEditarRestriccion);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(filler1, java.awt.BorderLayout.LINE_END);
        getContentPane().add(filler2, java.awt.BorderLayout.LINE_START);
        getContentPane().add(filler3, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.Box.Filler filler2;
    private javax.swing.Box.Filler filler3;
    private javax.swing.JButton jButCambiarNivel;
    private javax.swing.JButton jButCrearNuevaRestriccion;
    private javax.swing.JButton jButEditarRestriccion;
    private javax.swing.JButton jButEliminarRestriccion;
    private javax.swing.JList jListRestricciones;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    @Override
    public void updateData() {
        jListRestricciones.updateUI();
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
    public void creaAccionesYListeners() {

        //<editor-fold defaultstate="collapsed" desc="EditarRestriccionAction">
//Acción para editar la restricción
        class EditarRestriccionAction extends AbstractAction {

            public EditarRestriccionAction() {
                super("Editar", dk.mc.RESTRICTION_ICON);
                putValue(MNEMONIC_KEY, KeyEvent.VK_D);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Restriccion r = (Restriccion) jListRestricciones.getSelectedValue();
                    if (r != null) {
                        r.lanzarDialogoDeConfiguracion(this);
                        //Lanzo el evento de que se han modificado las restricciones
                        dk.getDP().getRestrictionsData().fireDataEvent(r, DataProyectoListener.MODIFY);
                        updateData();
                    }

                } catch (ArrayIndexOutOfBoundsException exc) {
                }
            }
        }
//</editor-fold>
        editarRestriccionAction = new EditarRestriccionAction();

        //Necesito definir esto despues de editarRestriccionAction porque hago referencia a él.
        //<editor-fold defaultstate="collapsed" desc="AñadirRestriccionAction">
        class AñadirRestriccionAction extends AbstractAction {

            public AñadirRestriccionAction() {
                super("Añadir nueva", dk.mc.ADD_ICON);
                putValue(MNEMONIC_KEY, KeyEvent.VK_N);

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                jDlgSelectNewRestriccion dlg = new jDlgSelectNewRestriccion(null, true, dk.getDP(), editarRestriccionAction);
                dlg.setLocationRelativeTo(null);
                dlg.setVisible(true);
            }
        }
//</editor-fold>
        añadirRestriccionAction = new AñadirRestriccionAction();

        //<editor-fold defaultstate="collapsed" desc="EliminarRestriccionAction">
        class EliminarRestriccionAction extends AbstractAction {

            public EliminarRestriccionAction() {
                super("Eliminar", dk.mc.DELETE_ICON);
                putValue(MNEMONIC_KEY, KeyEvent.VK_L);

            }

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Restriccion r = (Restriccion) jListRestricciones.getSelectedValue();
                    dk.getDP().getRestrictionsData().remove(r);
                    //Lanzo el evento de que se han modificado las restricciones
                    dk.getDP().getRestrictionsData().fireDataEvent(r, DataProyectoListener.REMOVE);
                    updateData();

                } catch (ArrayIndexOutOfBoundsException exc) {
                }
            }
        }
//</editor-fold>
        eliminarRestriccion = new EliminarRestriccionAction();

        //Acción para elegir el nivel de la restricción
        //<editor-fold defaultstate="collapsed" desc="ElegirNivelRestriccionAction">
        class ElegirNivelRestriccionAction extends AbstractAction {

            private final Frame parent;

            public ElegirNivelRestriccionAction(Frame parent) {
                super("Elegir nivel de importancia", dk.mc.RESTRICTIONLEVEL_ICON);
                this.parent = parent;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (jListRestricciones.getSelectedValue() != null) {
                    JDialogNivelSelector dlg = new JDialogNivelSelector((Restriccion) jListRestricciones.getSelectedValue(), parent, true);
                    dlg.setLocationRelativeTo(null);
                    dlg.setVisible(true);
                    jListRestricciones.updateUI();
                }
            }
        }
//</editor-fold>
        elegirNivelRestricionAction = new ElegirNivelRestriccionAction(mainWindow);

        //Listener para detectar doble click en la lista de restricciones.
        //<editor-fold defaultstate="collapsed" desc="JListRestriccionesMouseListener">
        class JListRestriccionesMouseListener implements MouseListener {

            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 2) {//Al hace doble click se edita la restricción.
                    editarRestriccionAction.actionPerformed(null);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
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

            private void doPop(MouseEvent e) {
                int ind = jListRestricciones.locationToIndex(e.getPoint());
                jListRestricciones.setSelectedIndex(ind);
                jPopupListRestricciones.show(e.getComponent(), e.getX(), e.getY());
            }
        }//End of class JListRestriccionesMouseListener
//</editor-fold>

        //Mapeo la tecla DEL para borrar la restricción
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "Eliminar restricción");
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put("Eliminar restricción", eliminarRestriccion);

        //Mapeo la tecla INS para añadir nueva restricción
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "Añadir restricción");
        actionMap.put("Añadir restricción", añadirRestriccionAction);

        jListRestriccionesMouseListener = new JListRestriccionesMouseListener();

        //Asigno actions y listeners
        jListRestricciones.addMouseListener(jListRestriccionesMouseListener);

        jButCrearNuevaRestriccion.setAction(añadirRestriccionAction);
        jButEditarRestriccion.setAction(editarRestriccionAction);
        jButCambiarNivel.setAction(elegirNivelRestricionAction);
        jButEliminarRestriccion.setAction(eliminarRestriccion);

        //Pop up menu 
        jPopupListRestricciones = new JPopupMenu("Menu");
        jPopupListRestricciones.add(editarRestriccionAction);
        jPopupListRestricciones.add(elegirNivelRestricionAction);
        jPopupListRestricciones.add(eliminarRestriccion);
    }

    /**
     *
     * @param obj
     * @param type
     */
    @Override
    public void dataEvent(Object obj, int type) {
        //Si se añade una nueva restricción, actualizo la lista y la selecciono.
        jListRestricciones.updateUI();

//        if ((type == DataProyectoListener.ADD) && (obj instanceof Restriccion)) {
//            System.out.println("size="+jListRestricciones.getModel().getSize());
//            System.out.println(((Restriccion)obj).descripcionCorta());
//            jListRestricciones.setSelectedValue(obj, true);
//        }
    }

    @Override
    public void expandTrees() {
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.BusquedaHorasLibres;

import data.DataKairos;
import data.profesores.Profesor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class JDlgBuscaHorasLilbres extends javax.swing.JDialog {

    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    private final DataKairos dk;
    private final DefaultListModel mod;

    /**
     * Creates new form JDlgBuscaHorasLilbres
     *
     * @param parent
     * @param modal
     * @param dk
     */
    public JDlgBuscaHorasLilbres(java.awt.Frame parent, boolean modal, DataKairos dk) {
        super(parent, modal);
        initComponents();
        this.dk = dk;
        // Close the dialog when Esc is pressed
        String cancelName = "cancel";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
        ArrayList<Profesor> dataProfesoresCombo = dk.getDP().getDataProfesores().getTodosProfesores();
        for (Profesor p : dataProfesoresCombo) {
            jComboProfesores.addItem(p);
        }
        AutoCompleteDecorator.decorate(jComboProfesores);
        mod = new DefaultListModel();
        jListProfesores.setModel(mod);
        createActions();
    }

    /**
     * @return the return status of this dialog - one of RET_OK or RET_CANCEL
     */
    public int getReturnStatus() {
        return returnStatus;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jComboProfesores = new javax.swing.JComboBox();
        jButAñadir = new javax.swing.JButton();
        jButEliminar = new javax.swing.JButton();
        jButTodo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListProfesores = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        jTextDuracionMinima = new javax.swing.JTextField();

        setTitle("Buscar horas libres");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jButAñadir.setText("Añadir");

        jButEliminar.setText("Eliminar");

        jButTodo.setText("Añadir todos");

        jScrollPane1.setViewportView(jListProfesores);

        jLabel1.setText("Duración mínima (en minutos):");

        jTextDuracionMinima.setText("60");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cancelButton))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jButAñadir)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButEliminar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButTodo))
                            .addComponent(jComboProfesores, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextDuracionMinima, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(75, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jComboProfesores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButAñadir)
                    .addComponent(jButEliminar)
                    .addComponent(jButTodo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextDuracionMinima, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(okButton))
                .addContainerGap())
        );

        getRootPane().setDefaultButton(okButton);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        doClose(RET_OK);
    }//GEN-LAST:event_okButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        doClose(RET_CANCEL);
    }//GEN-LAST:event_cancelButtonActionPerformed

    /**
     * Closes the dialog
     */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
        doClose(RET_CANCEL);
    }//GEN-LAST:event_closeDialog

    private void doClose(int retStatus) {
        returnStatus = retStatus;
        if (retStatus == RET_OK) {
            ArrayList<Profesor> data = new ArrayList<Profesor>();
            for (int n = 0; n < mod.getSize(); n++) {
                data.add((Profesor) mod.getElementAt(n));
            }
            int duracionMinima = -1;//-1: No se comprueba
            try {
                duracionMinima = Integer.valueOf(jTextDuracionMinima.getText());
            } catch (NumberFormatException numberFormatException) {
            }
            JDlgMuestraHorasLibres dlg = new JDlgMuestraHorasLibres(null, true, dk, data, duracionMinima);
            dlg.setLocationRelativeTo(null);
            dlg.setVisible(true);
        }
        setVisible(false);
        dispose();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton jButAñadir;
    private javax.swing.JButton jButEliminar;
    private javax.swing.JButton jButTodo;
    private javax.swing.JComboBox jComboProfesores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jListProfesores;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextDuracionMinima;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables

    private int returnStatus = RET_CANCEL;

    private void createActions() {
        //<editor-fold defaultstate="collapsed" desc="AñadirProfesorAction">
        class AñadirProfesorAction extends AbstractAction {

            public AñadirProfesorAction() {
                super("Añadir", dk.mc.ADD_ICON);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                Profesor p = (Profesor) jComboProfesores.getSelectedItem();
                if (p != null) {
                    if (!mod.contains(p)) {
                        mod.addElement(p);
                    }
                }
            }
        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="RemoveProfesorAction">
        class RemoveProfesorAction extends AbstractAction {

            public RemoveProfesorAction() {
                super("Eliminar", dk.mc.DELETE_ICON);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                for (Object o : jListProfesores.getSelectedValuesList()) {
                    mod.removeElement(o);
                }

            }
        }
//</editor-fold>
        class AñadirTodosAction extends AbstractAction {

            public AñadirTodosAction() {
                super("Añadir todos", dk.mc.ADD_ICON);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                mod.removeAllElements();
                for (Profesor p : dk.getDP().getDataProfesores().getTodosProfesores()) {
                    mod.addElement(p);
                }

            }
        }
        añadirProfesorAction = new AñadirProfesorAction();
        jButAñadir.setAction(añadirProfesorAction);
        removeProfesorAction = new RemoveProfesorAction();
        jButEliminar.setAction(removeProfesorAction);
        añadirTodosAction = new AñadirTodosAction();
        jButTodo.setAction(añadirTodosAction);
    }
    protected AbstractAction añadirTodosAction;
    protected AbstractAction removeProfesorAction;
    protected AbstractAction añadirProfesorAction;

}

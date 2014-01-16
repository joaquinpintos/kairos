/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Profesores;

import data.DataKairos;
import data.KairosCommand;
import data.profesores.Departamento;
import data.profesores.Profesor;
import data.profesores.TreeModelProfesores;
import gui.helpers.EscapeAction;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.tree.TreePath;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com> Gutierrez
 */
public class JDlgEditProfesor extends javax.swing.JDialog {

    Profesor profesor;
    TreeModelProfesores treeModel;
    Profesor newData;
    private final DataKairos dk;

    /**
     * Creates new form JDlgEditProfesor
     *
     * @param parent
     * @param modal
     * @param profesor
     * @param treeModel
     * @param treePath
     */
    public JDlgEditProfesor(java.awt.Frame parent, boolean modal, Profesor profesor, TreeModelProfesores treeModel, TreePath treePath, DataKairos dk) {
        super(parent, modal);
        initComponents();
        this.dk = dk;
        this.profesor = profesor;
        jTextNombreProfesor.setText(profesor.getNombre());
        jTextApellidosProfesor.setText(profesor.getApellidos());
        jTextNombreCorto.setText(profesor.getNombreCorto());
        jTextNombreProfesor.selectAll();
        jTextNombreProfesor.requestFocus();
        this.treeModel = treeModel;

        //jButAceptar.setAction(updateProfesorAction);
        //Creo combo con departamentos
        for (Departamento dep : treeModel.getDataProfesores().getDepartamentos()) {
            jComboDepartamentos.addItem(dep);
        }
        if (this.profesor.getDepartamento() != null) {
            jComboDepartamentos.setSelectedItem(this.profesor.getDepartamento());
        } else {
            jComboDepartamentos.setSelectedIndex(0);
        }
//        updateProfesorAction.setEnabled(false);
        EscapeAction escapeAction = new EscapeAction();
        escapeAction.register(this);

    }

    /**
     *
     * @return
     */
    public JTextField getjTextApellidosProfesor() {
        return jTextApellidosProfesor;
    }

    /**
     *
     * @return
     */
    public JTextField getjTextNombreProfesor() {
        return jTextNombreProfesor;
    }

    /**
     *
     * @return
     */
    public JComboBox getjComboDepartamentos() {
        return jComboDepartamentos;
    }

    /**
     *
     * @return
     */
    public TreeModelProfesores getTreeModel() {
        return treeModel;
    }

    /**
     *
     * @return
     */
    public JTextField getjTextNombreCorto() {
        return jTextNombreCorto;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButAceptar = new javax.swing.JButton();
        jButCancelar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextNombreProfesor = new javax.swing.JTextField();
        jComboDepartamentos = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jTextApellidosProfesor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextNombreCorto = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButAceptar.setText("Aceptar");
        jButAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButAceptarActionPerformed(evt);
            }
        });

        jButCancelar.setText("Cancelar");
        jButCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButCancelarActionPerformed(evt);
            }
        });

        jLabel1.setText("Nombre");

        jComboDepartamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboDepartamentosActionPerformed(evt);
            }
        });

        jLabel2.setText("Apellidos");

        jLabel3.setText("Nombre corto");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 319, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jButCancelar)
                            .addComponent(jButAceptar)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextApellidosProfesor, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                            .addComponent(jTextNombreProfesor)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboDepartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextNombreCorto, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextNombreProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextApellidosProfesor, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextNombreCorto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(jComboDepartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButAceptar)
                .addGap(18, 18, 18)
                .addComponent(jButCancelar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButCancelarActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButCancelarActionPerformed

    private void jButAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButAceptarActionPerformed
        Departamento dep = (Departamento) jComboDepartamentos.getSelectedItem();
        newData = new Profesor(jTextNombreProfesor.getText(), jTextApellidosProfesor.getText(), jTextNombreProfesor.getText(), dep);
        KairosCommand command = dk.getController().getEditProfesorCommand(profesor, newData);
        dk.getController().executeCommand(command);
        setVisible(false);
    }//GEN-LAST:event_jButAceptarActionPerformed

    private void jComboDepartamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboDepartamentosActionPerformed
        jButAceptar.setEnabled(jComboDepartamentos.getSelectedItem() != null);
    }//GEN-LAST:event_jComboDepartamentosActionPerformed
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButAceptar;
    private javax.swing.JButton jButCancelar;
    private javax.swing.JComboBox jComboDepartamentos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextApellidosProfesor;
    private javax.swing.JTextField jTextNombreCorto;
    private javax.swing.JTextField jTextNombreProfesor;
    // End of variables declaration//GEN-END:variables

}
//
//class UpdateProfesorAction extends AbstractAction {
//
//    JDlgEditProfesor parent;
//    private Profesor profesor;
//    private final TreePath treePath;
//
//    public UpdateProfesorAction(JDlgEditProfesor parent, Profesor profe, TreePath treePath) {
//        super("Aceptar");
//        this.parent = parent;
//        putValue(MNEMONIC_KEY, KeyEvent.VK_A);
//        this.profesor = profe;
//        this.treePath = treePath;
//
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent ae) {
////        parent.profesor.setNombre(parent.getjTextNombreProfesor().getText());
////        parent.profesor.setApellidos(parent.getjTextApellidosProfesor().getText());
////        Departamento nuevoDep = (Departamento) parent.getjComboDepartamentos().getSelectedItem();
////        Departamento oldDep = parent.profesor.getDepartamento();
////        if (nuevoDep != oldDep) //Departamento cambiado
////        {
////            oldDep.remove(parent.profesor);
////            nuevoDep.add(parent.profesor);
////        }
//        String nuevoNombre = parent.getjTextNombreProfesor().getText();
//        String nuevosApellidos = parent.getjTextApellidosProfesor().getText();
//        String nuevoNombreCorto = parent.getjTextNombreCorto().getText();
//        Departamento nuevoDep = (Departamento) parent.getjComboDepartamentos().getSelectedItem();
//        Profesor nuevoProfesor = new Profesor(nuevoNombre, nuevosApellidos, nuevoNombreCorto, nuevoDep);
//        parent.getTreeModel().changeProfesor(this.profesor, nuevoProfesor, treePath);
//        parent.setVisible(false);
//    }
//}

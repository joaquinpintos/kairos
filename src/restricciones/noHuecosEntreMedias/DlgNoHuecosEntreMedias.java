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
package restricciones.noHuecosEntreMedias;

import data.restricciones.AbstractDlgRestriccion;
import data.restricciones.Restriccion;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DlgNoHuecosEntreMedias extends AbstractDlgRestriccion {

    private final RNoHuecosEntreMedias r;
    private RNoHuecosEntreMedias editedRestriction;

    /**
     * Creates new form NewOkCancelDialog
     *
     * @param parent
     * @param r
     */
    public DlgNoHuecosEntreMedias(java.awt.Frame parent, RNoHuecosEntreMedias r) {
        super(parent, true);
        initComponents();
        this.r = r;
        jCheckHuecosLibres.setSelected(r.isPenalizarHuecos());
        jCheckNumeroMinimoClases.setSelected(r.isPenalizarPocasClases());
        jTextHorasMinimasClase.setText(r.getNumMinimoCasillasOcupadas() + "");

        // Close the dialog when Esc is pressed
        String cancelName = "Cancelar";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doClose(RET_CANCEL);
            }
        });
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
        jCheckHuecosLibres = new javax.swing.JCheckBox();
        jCheckNumeroMinimoClases = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        jTextHorasMinimasClase = new javax.swing.JTextField();

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

        jCheckHuecosLibres.setText("Penalizar huecos libres entre clases");

        jCheckNumeroMinimoClases.setText("Penalizar días con menos de ");

        jLabel1.setText("horas de clase.");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jCheckHuecosLibres)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCheckNumeroMinimoClases)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextHorasMinimasClase)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel1)
                        .addGap(0, 3, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckHuecosLibres)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckNumeroMinimoClases)
                    .addComponent(jLabel1)
                    .addComponent(jTextHorasMinimasClase, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
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
        setReturnStatus(retStatus);
        if (retStatus == RET_OK) {
            if (!(jCheckHuecosLibres.isSelected() || jCheckNumeroMinimoClases.isSelected())) {
                JOptionPane.showMessageDialog(rootPane, "Tienes que seleccionar al menos una penalización");
            } else {
                try {
                    if (retStatus == RET_OK) {
                        editedRestriction=new RNoHuecosEntreMedias();
                        editedRestriction.setNumMinimoCasillasOcupadas(Integer.valueOf(jTextHorasMinimasClase.getText()));
                        editedRestriction.setPenalizarHuecos(jCheckHuecosLibres.isSelected());
                        editedRestriction.setPenalizarPocasClases(jCheckNumeroMinimoClases.isSelected());
                    }
                    returnStatus = retStatus;
                    setVisible(false);
                    dispose();
                } catch (NumberFormatException ex) {
                }
            }
        } else {
            returnStatus = retStatus;
            setVisible(false);
            dispose();
        }
    }

    @Override
    public Restriccion getEditedRestriction() {
        return editedRestriction;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox jCheckHuecosLibres;
    private javax.swing.JCheckBox jCheckNumeroMinimoClases;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextHorasMinimasClase;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;
}

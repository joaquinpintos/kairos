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
package gui.DatosEditor.Restricciones;

import data.DataKairos;
import data.KairosCommand;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractAction;
import static javax.swing.Action.MNEMONIC_KEY;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import data.restricciones.Restriccion;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class JDialogNivelSelector extends javax.swing.JDialog {

    Restriccion restriccionEditada;
    private AbstractAction aceptarAction;
    private AbstractAction cancelAction;
    private DataKairos dk;

    /**
     * Creates new form JDialogNivelSelector
     *
     * @param r
     * @param parent
     * @param dk
     */
    public JDialogNivelSelector(Restriccion r, java.awt.Frame parent, DataKairos dk) {
        super(parent, true);
        initComponents();
        this.restriccionEditada = r;
        this.dk = dk;
//        jRadNivel1.setIcon(MyConstants.RED_ICON);
//        jRadNivel2.setIcon(MyConstants.YELLOW_ICON);
//        jRadNivel3.setIcon(MyConstants.GREEN_ICON);
//        
        //Marco el nivel actual
        JRadioButton[] niveles;
        niveles = new JRadioButton[]{
            jRadNivel1, jRadNivel2, jRadNivel3
        };
        int level = restriccionEditada.getLevel();
        if (level < 1) {
            level = 1;
        }
        niveles[level - 1].setSelected(true);

        creaAccionesYListeners();
        jButAceptar.setAction(aceptarAction);
        // Close the dialog when Esc is pressed
        String cancelName = "Cancelar";
        InputMap inputMap = getRootPane().getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), cancelName);
        ActionMap actionMap = getRootPane().getActionMap();
        actionMap.put(cancelName, cancelAction);
        jButCancelar.setAction(cancelAction);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButAceptar = new javax.swing.JButton();
        jButCancelar = new javax.swing.JButton();
        jRadNivel2 = new javax.swing.JRadioButton();
        jRadNivel3 = new javax.swing.JRadioButton();
        jRadNivel1 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButAceptar.setText("Aceptara");

        jButCancelar.setText("Cancelar");

        buttonGroup1.add(jRadNivel2);
        jRadNivel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jRadNivel2.setForeground(new java.awt.Color(255, 200, 0));
        jRadNivel2.setText("Intermedio");

        buttonGroup1.add(jRadNivel3);
        jRadNivel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jRadNivel3.setForeground(new java.awt.Color(0, 255, 0));
        jRadNivel3.setText("Deseable");

        buttonGroup1.add(jRadNivel1);
        jRadNivel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jRadNivel1.setForeground(new java.awt.Color(255, 0, 0));
        jRadNivel1.setText("Crítico");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jRadNivel2)
                        .addComponent(jRadNivel3)
                        .addComponent(jRadNivel1))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jButCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButAceptar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadNivel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadNivel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadNivel3)
                .addGap(18, 18, 18)
                .addComponent(jButAceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButCancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButAceptar;
    private javax.swing.JButton jButCancelar;
    private javax.swing.JRadioButton jRadNivel1;
    private javax.swing.JRadioButton jRadNivel2;
    private javax.swing.JRadioButton jRadNivel3;
    // End of variables declaration//GEN-END:variables

    private void creaAccionesYListeners() {
        //Creo clases internas
        //<editor-fold defaultstate="collapsed" desc="AceptarAction">
        class AceptarAction extends AbstractAction {

            private final JDialog dlg;

            public AceptarAction(JDialog dlg) {
                super("Aceptar", null);
                this.dlg = dlg;
                putValue(MNEMONIC_KEY, KeyEvent.VK_A);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                int nivel = 1;
                if (jRadNivel1.isSelected()) {
                    nivel = 1;
                }
                if (jRadNivel2.isSelected()) {
                    nivel = 2;
                }
                if (jRadNivel3.isSelected()) {
                    nivel = 3;
                }
//                restriccionEditada.setImportancia(nivel);
                KairosCommand cmd = dk.getController().getCambiarNivelResticcionCommand(restriccionEditada, nivel);
                dk.getController().executeCommand(cmd);
                dlg.setVisible(false);
                dlg.dispose();
            }
        }
//</editor-fold>
        aceptarAction = new AceptarAction(this);
        //<editor-fold defaultstate="collapsed" desc="CancelarAction">
        class CancelarAction extends AbstractAction {

            private final JDialog dlg;

            public CancelarAction(JDialog dlg) {
                super("Cancelar", null);
                this.dlg = dlg;
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                dlg.setVisible(false);
                dlg.dispose();
            }
        }
//</editor-fold>
        cancelAction = new CancelarAction(this);
        //<editor-fold defaultstate="collapsed" desc="JRadMouseListeners">
        class JRadMouseListeners implements MouseListener {

            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    aceptarAction.actionPerformed(null);
                }
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
            }
        }
//</editor-fold>

        JRadMouseListeners jRadMouseListener = new JRadMouseListeners();
        jRadNivel1.addMouseListener(jRadMouseListener);
        jRadNivel2.addMouseListener(jRadMouseListener);
        jRadNivel3.addMouseListener(jRadMouseListener);

    }
}

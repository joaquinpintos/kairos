/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.printDialogs;

import com.itextpdf.text.DocumentException;
import data.DataProject;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;
import printers.AbstractHorariosPrinter;
import printers.PrinterHorarioPorGrupos;
import printers.PrinterHorariosPorAulas;
import printers.PrinterHorariosPorProfesor;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class jDlgPrintHorario extends javax.swing.JDialog {

    private static final int HORARIO_POR_AULAS = 1;
    private static final int HORARIO_POR_GRUPOS = 2;
    private static final int HORARIO_POR_PROFESORES = 3;
    private static final int UN_DOCUMENTO = 1;
    private static final int VARIOS_DOCUMENTOS = 2;
    private static final int ORIENTACION_NORMAL = 0;
    private static final int ORIENTACION_APAISADO = 1;
    /**
     * A return status code - returned if Cancel button has been pressed
     */
    public static final int RET_CANCEL = 0;
    /**
     * A return status code - returned if OK button has been pressed
     */
    public static final int RET_OK = 1;
    private final DataProject dataProyecto;
    private File fileDst;

    /**
     * Creates new form jDlgPrintHorario
     * @param parent 
     * @param modal
     * @param dataProyecto  
     */
    public jDlgPrintHorario(java.awt.Frame parent, boolean modal, DataProject dataProyecto) {
        super(parent, modal);
        initComponents();
        this.dataProyecto = dataProyecto;

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
        jRadPorGrupos.setSelected(true);
        jRadUnicoDocumento.setSelected(true);
        jRadOrientacionNormal.setSelected(true);
        jTextTitle.setText(dataProyecto.getTituloPaginasImprimir());
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

        jGroupTipoHorario = new javax.swing.ButtonGroup();
        jGroupCuantosDocumentos = new javax.swing.ButtonGroup();
        JGroupOrientacion = new javax.swing.ButtonGroup();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jRadPorAula = new javax.swing.JRadioButton();
        jRadPorGrupos = new javax.swing.JRadioButton();
        jRadPorProfesores = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        jRadVariosDocumentos = new javax.swing.JRadioButton();
        jRadUnicoDocumento = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jRadOrientacionNormal = new javax.swing.JRadioButton();
        jRadOrientacionApaisado = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jTextTitle = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        okButton.setText("Aceptar");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancelar");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Tipo de horario"));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jGroupTipoHorario.add(jRadPorAula);
        jRadPorAula.setText("Horarios por aulas");
        jPanel1.add(jRadPorAula);

        jGroupTipoHorario.add(jRadPorGrupos);
        jRadPorGrupos.setText("Horarios por grupos");
        jPanel1.add(jRadPorGrupos);

        jGroupTipoHorario.add(jRadPorProfesores);
        jRadPorProfesores.setText("Horarios por profesores");
        jPanel1.add(jRadPorProfesores);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cuántos documentos"));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jGroupCuantosDocumentos.add(jRadVariosDocumentos);
        jRadVariosDocumentos.setText("Generar un documento por cada horario");
        jPanel2.add(jRadVariosDocumentos);

        jGroupCuantosDocumentos.add(jRadUnicoDocumento);
        jRadUnicoDocumento.setText("Generar un único documento");
        jPanel2.add(jRadUnicoDocumento);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Orientación"));
        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.Y_AXIS));

        JGroupOrientacion.add(jRadOrientacionNormal);
        jRadOrientacionNormal.setText("Normal");
        jPanel3.add(jRadOrientacionNormal);

        JGroupOrientacion.add(jRadOrientacionApaisado);
        jRadOrientacionApaisado.setText("Apaisado");
        jPanel3.add(jRadOrientacionApaisado);

        jLabel1.setText("Título:");

        jLabel2.setText("(Ej: Horarios 1º Cuatrimestre 2013/2014)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(okButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jTextTitle)))
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, okButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(okButton)
                    .addComponent(cancelButton))
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
        if (retStatus == RET_OK) {
            int typeHorario = getTypeHorarioElegido();
            int cuantosDocumentos = getCuantosDocumentosElegido();
            if ((typeHorario == 0) || (cuantosDocumentos == 0)) {
                return;
            }
            returnStatus = retStatus;
            JFileChooser fc = new JFileChooser(dataProyecto.getPathForPDF());
            if (cuantosDocumentos == UN_DOCUMENTO) {
                fc.setDialogTitle("Elige el nombre del archivo a guardar:");
            } else {
                fc.setDialogTitle("Elige el directorio a guardar los archivos:");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            }
            int valorDevuelto = fc.showOpenDialog(this);

            if (valorDevuelto == JFileChooser.APPROVE_OPTION) {
                fileDst = fc.getSelectedFile();
                dataProyecto.setPathForPDF(fileDst);
            }
            if (typeHorario == HORARIO_POR_AULAS) {
                PrinterHorariosPorAulas pr = new PrinterHorariosPorAulas(dataProyecto, fileDst, (cuantosDocumentos == VARIOS_DOCUMENTOS));
                try {
                    setConfig(pr);
                    pr.crearDocumento();
                } catch (DocumentException ex) {
                    Logger.getLogger(jDlgPrintHorario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(jDlgPrintHorario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (typeHorario == HORARIO_POR_PROFESORES) {
                try {
                    PrinterHorariosPorProfesor pr = new PrinterHorariosPorProfesor(dataProyecto, fileDst, (cuantosDocumentos == VARIOS_DOCUMENTOS));
                    setConfig(pr);
                    pr.crearDocumento();
                } catch (DocumentException ex) {
                    Logger.getLogger(jDlgPrintHorario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(jDlgPrintHorario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (typeHorario == HORARIO_POR_GRUPOS) {
                try {
                    PrinterHorarioPorGrupos pr = new PrinterHorarioPorGrupos(dataProyecto, fileDst, (cuantosDocumentos == VARIOS_DOCUMENTOS));
                    setConfig(pr);
                    pr.crearDocumento();
                } catch (DocumentException ex) {
                    Logger.getLogger(jDlgPrintHorario.class.getName()).log(Level.SEVERE, null, ex);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(jDlgPrintHorario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        setVisible(false);
        dispose();

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup JGroupOrientacion;
    private javax.swing.JButton cancelButton;
    private javax.swing.ButtonGroup jGroupCuantosDocumentos;
    private javax.swing.ButtonGroup jGroupTipoHorario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JRadioButton jRadOrientacionApaisado;
    private javax.swing.JRadioButton jRadOrientacionNormal;
    private javax.swing.JRadioButton jRadPorAula;
    private javax.swing.JRadioButton jRadPorGrupos;
    private javax.swing.JRadioButton jRadPorProfesores;
    private javax.swing.JRadioButton jRadUnicoDocumento;
    private javax.swing.JRadioButton jRadVariosDocumentos;
    private javax.swing.JTextField jTextTitle;
    private javax.swing.JButton okButton;
    // End of variables declaration//GEN-END:variables
    private int returnStatus = RET_CANCEL;

    private int getTypeHorarioElegido() {
        int resul = 0;
        if (jRadPorAula.isSelected()) {
            resul = HORARIO_POR_AULAS;
        }
        if (jRadPorGrupos.isSelected()) {
            resul = HORARIO_POR_GRUPOS;
        }
        if (jRadPorProfesores.isSelected()) {
            resul = HORARIO_POR_PROFESORES;
        }
        return resul;
    }

    private int getCuantosDocumentosElegido() {
        int resul = 0;
        if (jRadUnicoDocumento.isSelected()) {
            resul = UN_DOCUMENTO;
        }
        if (jRadVariosDocumentos.isSelected()) {
            resul = VARIOS_DOCUMENTOS;
        }
        return resul;
    }

    private int getOrientacionDocumentoElegido() {
        int resul = 0;
        if (jRadOrientacionNormal.isSelected()) {
            resul = ORIENTACION_NORMAL;
        }
        if (jRadOrientacionApaisado.isSelected()) {
            resul = ORIENTACION_APAISADO;
        }
        return resul;
    }

    private void setConfig(AbstractHorariosPrinter pr) {
        int numFilasMañana = dataProyecto.getMañana1().getDuracionHoras() + dataProyecto.getMañana2().getDuracionHoras();
        numFilasMañana = (numFilasMañana * 60) / dataProyecto.getMinutosPorCasilla();
        int numFilasTarde = dataProyecto.getTarde1().getDuracionHoras() + dataProyecto.getTarde2().getDuracionHoras();
        numFilasTarde = (numFilasTarde * 60) / dataProyecto.getMinutosPorCasilla();
        pr.setTextoTitulo(jTextTitle.getText());
        dataProyecto.setTituloPaginasImprimir(jTextTitle.getText());
        if (getOrientacionDocumentoElegido() == ORIENTACION_APAISADO) {
            pr.setRotated(true);
//            pr.setTamañoTabla(18, numFilasMañana, numFilasTarde);
        } else {
            pr.setRotated(false);
//            pr.setTamañoTabla(26, numFilasMañana, numFilasTarde);
        }
    }
}

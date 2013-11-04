/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor;

import data.CalendarioAcademico;
import data.DataKairos;
import data.MyConstants;
import data.RangoHoras;
import gui.AbstractMainWindow;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author david
 */
public class JIntDatosProyecto extends javax.swing.JInternalFrame implements DataGUIInterface {

    private final DataKairos dk;
    private AbstractMainWindow mainwindow;

    /**
     * Creates new form JIntDatosProyecto
     *
     * @param dk
     */
    public JIntDatosProyecto(DataKairos dk) {
        initComponents();

        this.dk = dk;
        jCheckLunes.setSelected(true);
        jCheckMartes.setSelected(true);
        jCheckMiercoles.setSelected(true);
        jCheckJueves.setSelected(true);
        jCheckViernes.setSelected(true);

        jLabDesgLunes.setText("");
        jLabDesgMartes.setText("");
        jLabDesgMiercoles.setText("");
        jLabDesgJueves.setText("");
        jLabDesgViernes.setText("");

        class MyChangeListener implements ChangeListener {

            @Override
            public void stateChanged(ChangeEvent e) {
                //System.out.println("Cambio!");
                updateData();
            }
        }
        class MyActionListener implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println("Cambio!");
                pasaDatosDeGUIaDataProyecto();
                calculaDiasLectivosTotales();
            }
        }
        MyChangeListener myChangeListener = new MyChangeListener();
        MyActionListener myActionListener = new MyActionListener();
        //jCheckLunes.addChangeListener(myChangeListener);
        jCheckLunes.addActionListener(myActionListener);
        jCheckMartes.addActionListener(myActionListener);
        jCheckMiercoles.addActionListener(myActionListener);
        jCheckJueves.addActionListener(myActionListener);
        jCheckViernes.addActionListener(myActionListener);
        jTextFinPeriodoLectivo.addActionListener(myActionListener);
        jTextInicioPeriodoLectivo.addActionListener(myActionListener);
        jButAceptar.addActionListener(myActionListener);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jCheckLunes = new javax.swing.JCheckBox();
        jCheckMartes = new javax.swing.JCheckBox();
        jCheckMiercoles = new javax.swing.JCheckBox();
        jCheckJueves = new javax.swing.JCheckBox();
        jCheckViernes = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextInicioPeriodoLectivo = new javax.swing.JTextField();
        jTextFinPeriodoLectivo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaDiasNoLectivos = new javax.swing.JTextArea();
        jLabTotalDiasCalculados = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel6 = new javax.swing.JLabel();
        jTextHoraMañanaInicio1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jTextHoraMañanaFin1 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jTextHoraMañanaInicio2 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jTextHoraMañanaFin2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jTextHoraTardeFin2 = new javax.swing.JTextField();
        jTextHoraTardeInicio2 = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jTextHoraTardeInicio1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jTextHoraTardeFin1 = new javax.swing.JTextField();
        jButAceptar = new javax.swing.JButton();
        jLabResulDias = new javax.swing.JLabel();
        jLabDesgLunes = new javax.swing.JLabel();
        jLabDesgMartes = new javax.swing.JLabel();
        jLabDesgMiercoles = new javax.swing.JLabel();
        jLabDesgJueves = new javax.swing.JLabel();
        jLabDesgViernes = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jTextGruposPorDefecto = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        jLabel1.setText("Días de la semana:");

        jCheckLunes.setText("Lunes");
        jCheckLunes.setNextFocusableComponent(jCheckMartes);

        jCheckMartes.setText("Martes");
        jCheckMartes.setNextFocusableComponent(jCheckMiercoles);

        jCheckMiercoles.setText("Miércoles");
        jCheckMiercoles.setNextFocusableComponent(jCheckJueves);

        jCheckJueves.setText("Jueves");
        jCheckJueves.setNextFocusableComponent(jCheckViernes);

        jCheckViernes.setText("Viernes");
        jCheckViernes.setNextFocusableComponent(jTextInicioPeriodoLectivo);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel2.setText("Periodo lectivo:");

        jLabel3.setText("Inicio:");

        jLabel4.setText("Fin:");

        jTextInicioPeriodoLectivo.setNextFocusableComponent(jTextFinPeriodoLectivo);

        jTextFinPeriodoLectivo.setNextFocusableComponent(jTextAreaDiasNoLectivos);

        jLabel5.setText("Días no lectivos:");

        jTextAreaDiasNoLectivos.setColumns(20);
        jTextAreaDiasNoLectivos.setRows(5);
        jTextAreaDiasNoLectivos.setToolTipText("incluir la lista de días no lectivos separadas por comas.No es necesario incluir sábados ni domingos.");
        jTextAreaDiasNoLectivos.setNextFocusableComponent(jTextHoraMañanaInicio1);
        jScrollPane1.setViewportView(jTextAreaDiasNoLectivos);

        jLabTotalDiasCalculados.setText("Total días:");

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel6.setText("Horario de las aulas:");

        jTextHoraMañanaInicio1.setNextFocusableComponent(jTextHoraMañanaFin1);

        jLabel7.setText("a");

        jTextHoraMañanaFin1.setNextFocusableComponent(jTextHoraMañanaInicio2);

        jLabel8.setText("De");

        jLabel9.setText("y");

        jLabel10.setText("de");

        jTextHoraMañanaInicio2.setNextFocusableComponent(jTextHoraMañanaFin2);

        jLabel11.setText("a");

        jTextHoraMañanaFin2.setNextFocusableComponent(jTextHoraTardeInicio1);
        jTextHoraMañanaFin2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextHoraMañanaFin2ActionPerformed(evt);
            }
        });

        jLabel12.setText("Mañana:");

        jLabel13.setText("y");

        jLabel14.setText("de");

        jLabel15.setText("De");

        jTextHoraTardeFin2.setNextFocusableComponent(jButAceptar);

        jTextHoraTardeInicio2.setNextFocusableComponent(jTextHoraTardeFin2);

        jLabel16.setText("a");

        jLabel17.setText("Tarde:");

        jTextHoraTardeInicio1.setNextFocusableComponent(jTextHoraTardeFin1);

        jLabel18.setText("a");

        jTextHoraTardeFin1.setNextFocusableComponent(jTextHoraTardeInicio2);

        jButAceptar.setText("Actualizar");
        jButAceptar.setNextFocusableComponent(jCheckLunes);

        jLabResulDias.setText("0");

        jLabDesgLunes.setText("a");

        jLabDesgMartes.setText("a");

        jLabDesgMiercoles.setText("a");

        jLabDesgJueves.setText("a");

        jLabDesgViernes.setText("a");

        jLabel19.setText("Grupos por defecto:");

        jLabel20.setText("(Ej: A,B,C)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCheckLunes)
                    .addComponent(jCheckMartes)
                    .addComponent(jLabel1)
                    .addComponent(jCheckViernes)
                    .addComponent(jCheckJueves)
                    .addComponent(jCheckMiercoles))
                .addGap(6, 6, 6)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(98, 98, 98))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFinPeriodoLectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jTextInicioPeriodoLectivo, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabTotalDiasCalculados)
                                        .addComponent(jLabDesgLunes))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jLabResulDias, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLabDesgMartes)
                            .addComponent(jLabDesgMiercoles)
                            .addComponent(jLabDesgJueves)
                            .addComponent(jLabDesgViernes))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButAceptar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel17)
                    .addComponent(jLabel6)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jTextGruposPorDefecto, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel20)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextHoraMañanaInicio2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextHoraMañanaFin2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextHoraMañanaInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextHoraMañanaFin1)))
                        .addGap(10, 10, 10)
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 12, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextHoraTardeInicio2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextHoraTardeFin2))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextHoraTardeInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel18)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextHoraTardeFin1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jCheckLunes)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCheckMartes)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCheckMiercoles)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCheckJueves)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCheckViernes))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextHoraMañanaInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextHoraMañanaFin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel9))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextHoraMañanaInicio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextHoraMañanaFin2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextHoraTardeInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextHoraTardeFin1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel13))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jTextHoraTardeInicio2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextHoraTardeFin2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel14))))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel19)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextGruposPorDefecto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButAceptar))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(jTextInicioPeriodoLectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jTextFinPeriodoLectivo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabResulDias, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabTotalDiasCalculados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabDesgLunes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabDesgMartes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabDesgMiercoles)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabDesgJueves)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabDesgViernes)
                                .addGap(0, 4, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextHoraMañanaFin2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextHoraMañanaFin2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextHoraMañanaFin2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButAceptar;
    private javax.swing.JCheckBox jCheckJueves;
    private javax.swing.JCheckBox jCheckLunes;
    private javax.swing.JCheckBox jCheckMartes;
    private javax.swing.JCheckBox jCheckMiercoles;
    private javax.swing.JCheckBox jCheckViernes;
    private javax.swing.JLabel jLabDesgJueves;
    private javax.swing.JLabel jLabDesgLunes;
    private javax.swing.JLabel jLabDesgMartes;
    private javax.swing.JLabel jLabDesgMiercoles;
    private javax.swing.JLabel jLabDesgViernes;
    private javax.swing.JLabel jLabResulDias;
    private javax.swing.JLabel jLabTotalDiasCalculados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea jTextAreaDiasNoLectivos;
    private javax.swing.JTextField jTextFinPeriodoLectivo;
    private javax.swing.JTextField jTextGruposPorDefecto;
    private javax.swing.JTextField jTextHoraMañanaFin1;
    private javax.swing.JTextField jTextHoraMañanaFin2;
    private javax.swing.JTextField jTextHoraMañanaInicio1;
    private javax.swing.JTextField jTextHoraMañanaInicio2;
    private javax.swing.JTextField jTextHoraTardeFin1;
    private javax.swing.JTextField jTextHoraTardeFin2;
    private javax.swing.JTextField jTextHoraTardeInicio1;
    private javax.swing.JTextField jTextHoraTardeInicio2;
    private javax.swing.JTextField jTextInicioPeriodoLectivo;
    // End of variables declaration//GEN-END:variables

    /**
     *
     */
    @Override
    public void updateData() {
        try {
            //Cambio titulo ventana
            mainwindow.setTitle("Kairos - " + dk.getDP().getConfigProyecto().getNombreProyecto());

            //Cambio textos horas aulas
            jTextHoraMañanaInicio1.setText(dk.getDP().getMañana1().getInicio().toString());
            jTextHoraMañanaFin1.setText(dk.getDP().getMañana1().getFin().toString());
            jTextHoraMañanaInicio2.setText(dk.getDP().getMañana2().getInicio().toString());
            jTextHoraMañanaFin2.setText(dk.getDP().getMañana2().getFin().toString());
            jTextHoraTardeInicio1.setText(dk.getDP().getTarde1().getInicio().toString());
            jTextHoraTardeFin1.setText(dk.getDP().getTarde1().getFin().toString());
            jTextHoraTardeInicio2.setText(dk.getDP().getTarde2().getInicio().toString());
            jTextHoraTardeFin2.setText(dk.getDP().getTarde2().getFin().toString());

            // Marco/desmarco casillas de días lectivos semanales
            jCheckLunes.setSelected(dk.getDP().getDiasSemanaLectivos().contains(1));
            jCheckMartes.setSelected(dk.getDP().getDiasSemanaLectivos().contains(2));
            jCheckMiercoles.setSelected(dk.getDP().getDiasSemanaLectivos().contains(3));
            jCheckJueves.setSelected(dk.getDP().getDiasSemanaLectivos().contains(4));
            jCheckViernes.setSelected(dk.getDP().getDiasSemanaLectivos().contains(5));

            //Cambio campos inicio/final periodo lectivo
            jTextInicioPeriodoLectivo.setText(dk.getDP().getCalendarioAcadémico().getStrInicio());
            jTextFinPeriodoLectivo.setText(dk.getDP().getCalendarioAcadémico().getStrFin());

            //Creo cuadro de texto con todos los días no lectivos
            String texto = "";
            for (int n = 0; n < dk.getDP().getCalendarioAcadémico().getDiasNoLectivos().size(); n++) {
                texto += dk.getDP().getCalendarioAcadémico().getDiasNoLectivos().get(n) + "  ";
                texto += dk.getDP().getCalendarioAcadémico().getDescripcionDiasNoLectivos().get(n) + "\n";
            }
            jTextAreaDiasNoLectivos.setText(texto);
            calculaDiasLectivosTotales();
        } catch (Exception ex) {
        };
    }

    private void calculaDiasLectivosTotales() {
        //Calculo numero de dias lectivos totales
        CalendarioAcademico cal = dk.getDP().getCalendarioAcadémico();
        jLabDesgLunes.setText("");
        jLabDesgMartes.setText("");
        jLabDesgMiercoles.setText("");
        jLabDesgJueves.setText("");
        jLabDesgViernes.setText("");
        jLabResulDias.setText("");
        try {
            cal.setInicio(jTextInicioPeriodoLectivo.getText());
            cal.setFin(jTextFinPeriodoLectivo.getText());
            if (cal.getInicio().compareTo(cal.getFin()) > 0) {
                throw new ParseException("Fecha final es menor que la fecha inicial", 0);
            };
            ArrayList<GregorianCalendar> resul = cal.getArrayDiasLectivos();
            jLabResulDias.setText("" + resul.size());

            //Desgloso los días por día de la semana
            Integer[] desglose = {0, 0, 0, 0, 0};

            for (GregorianCalendar dia : resul) {
                switch (dia.get(GregorianCalendar.DAY_OF_WEEK)) {
                    case GregorianCalendar.MONDAY:
                        desglose[0]++;
                        break;
                    case GregorianCalendar.TUESDAY:
                        desglose[1]++;
                        break;
                    case GregorianCalendar.WEDNESDAY:
                        desglose[2]++;
                        break;
                    case GregorianCalendar.THURSDAY:
                        desglose[3]++;
                        break;
                    case GregorianCalendar.FRIDAY:
                        desglose[4]++;
                        break;
                    default:
                        break;
                }

                String strDesglose = "";
                jLabDesgLunes.setText(desglose[0] + " lunes");
                jLabDesgMartes.setText(desglose[1] + " martes");
                jLabDesgMiercoles.setText(desglose[2] + " miércoles");
                jLabDesgJueves.setText(desglose[3] + " jueves");
                jLabDesgViernes.setText(desglose[4] + " viernes");

            }

        } catch (ParseException ex) {
            System.out.println("Fecha no validas");
        }

    }

    private void pasaDatosDeGUIaDataProyecto() {
        //Actualizo los datos en dataProyecto
        try {
            dk.getDP().setMañana1(new RangoHoras(jTextHoraMañanaInicio1.getText(), jTextHoraMañanaFin1.getText()));
            dk.getDP().setMañana2(new RangoHoras(jTextHoraMañanaInicio2.getText(), jTextHoraMañanaFin2.getText()));

            dk.getDP().setTarde1(new RangoHoras(jTextHoraTardeInicio1.getText(), jTextHoraTardeFin1.getText()));
            dk.getDP().setTarde2(new RangoHoras(jTextHoraTardeInicio2.getText(), jTextHoraTardeFin2.getText()));
        } catch (NumberFormatException ex) {
        };
        //Guardo ahora en un array los días lectivos semanales
        ArrayList<Integer> ar = new ArrayList<Integer>();
        if (jCheckLunes.isSelected()) {
            ar.add(1);
        }
        if (jCheckMartes.isSelected()) {
            ar.add(2);
        }
        if (jCheckMiercoles.isSelected()) {
            ar.add(3);
        }
        if (jCheckJueves.isSelected()) {
            ar.add(4);
        }
        if (jCheckViernes.isSelected()) {
            ar.add(5);
        }

        dk.getDP().setDiasSemanaLectivos(ar);
        try {
            dk.getDP().getCalendarioAcadémico().setInicio(jTextInicioPeriodoLectivo.getText());
            jTextInicioPeriodoLectivo.setForeground(Color.BLACK);
        } catch (ParseException ex) {
            jTextInicioPeriodoLectivo.setForeground(MyConstants.CONFLICTIVE_ITEM);
        }
        try {

            dk.getDP().getCalendarioAcadémico().setFin(jTextFinPeriodoLectivo.getText());
            jTextFinPeriodoLectivo.setForeground(Color.BLACK);
        } catch (ParseException ex) {
            jTextFinPeriodoLectivo.setForeground(MyConstants.CONFLICTIVE_ITEM);
        }
        CalendarioAcademico cal = dk.getDP().getCalendarioAcadémico();
        //Parseo el texto de los días no lectivos

        String texto = jTextAreaDiasNoLectivos.getText();
        String[] lineas = texto.split("\n");
        ArrayList<String> nuevosDias = new ArrayList<String>();
        ArrayList<String> nuevosDiasDescripcion = new ArrayList<String>();
        try {
            for (String linea : lineas) {
                GregorianCalendar c = cal.parse(linea);
                String strDia = cal.format(c);
                String strDescripcion = linea.substring(strDia.length()).trim();
                nuevosDias.add(strDia);
                nuevosDiasDescripcion.add(strDescripcion);
            }
            cal.setDiasNoLectivos(nuevosDias);
            cal.setDescripcionDiasNoLectivos(nuevosDiasDescripcion);
            jTextAreaDiasNoLectivos.setForeground(Color.BLACK);
        } catch (ParseException ex) {
            jTextAreaDiasNoLectivos.setForeground(MyConstants.CONFLICTIVE_ITEM);
        }
        dk.getDP().getConfigProyecto().setGruposPorDefecto(jTextGruposPorDefecto.getText());

    }

    /**
     *
     * @param mainWindow
     */
    @Override
    public void setMainWindow(AbstractMainWindow mainWindow) {
        this.mainwindow = mainWindow;
    }

    private void desmarcaErrores() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void marcaErrorFechas(String fechas_de_iniciofin_no_válidas) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

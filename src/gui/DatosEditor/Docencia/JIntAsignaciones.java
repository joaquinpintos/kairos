/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.DatosEditor.Docencia;

import data.DataKairos;
import data.profesores.TreeCellRendererProfesores;
import data.profesores.TreeModelProfesores;
import gui.DatosEditor.Asignaturas.TreeModelAsignaturas;
import gui.DatosEditor.DataGUIInterface;
import gui.MainWindowTabbed;

/**
 *
 * @author david
 */
public class JIntAsignaciones extends javax.swing.JInternalFrame implements DataGUIInterface{
private final DataKairos dk;
    private MainWindowTabbed mainWindow;
    /**
     * Creates new form jIntAsignaciones
     */
    public JIntAsignaciones(DataKairos dk) {
        initComponents();
        this.dk=dk;
        TreeModelProfesores mod=new TreeModelProfesores(dk);
        jTreeProfesores.setModel(mod);
        jTreeProfesores.setCellRenderer(new TreeCellRendererProfesores());
        
        TreeModelAsignaturas asigModel=new TreeModelAsignaturas(dk);
        jTreeAsignaturas.setModel(asigModel);
        
        
        
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTreeProfesores = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();

        jScrollPane1.setViewportView(jTreeAsignaturas);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jTreeProfesores.setMaximumSize(new java.awt.Dimension(30000, 300000));
        jTreeProfesores.setMinimumSize(new java.awt.Dimension(400, 200));
        jTreeProfesores.setPreferredSize(new java.awt.Dimension(400, 200));
        jScrollPane2.setViewportView(jTreeProfesores);

        getContentPane().add(jScrollPane2, java.awt.BorderLayout.LINE_START);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 641, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTree jTreeAsignaturas;
    private javax.swing.JTree jTreeProfesores;
    // End of variables declaration//GEN-END:variables

    @Override
    public void updateData() {
        jTreeProfesores.updateUI();
        jTreeAsignaturas.updateUI();
    }

    @Override
    public void setMainWindow(MainWindowTabbed mainWindow) {
        this.mainWindow=mainWindow;
    }
}

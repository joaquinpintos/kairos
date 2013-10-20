/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import data.DataKairos;
import gui.DatosEditor.Restricciones.JIntRestricciones;
import gui.DatosEditor.DataGUIInterface;
import gui.DatosEditor.JIntGenetic;
import gui.DatosEditor.JIntDatosProyecto;
import gui.DatosEditor.JIntTreeProfesores;
import gui.DatosEditor.JIntWelcome;
import gui.DatosEditor.Aulas.JIntTreeAulas;
import gui.DatosEditor.Asignaturas.JIntTreeAsignaturas;
import data.asignaturas.DataAsignaturas;
import data.aulas.DataAulas;
import data.profesores.DataProfesores;
import gui.DatosEditor.Docencia.JIntAsignaciones;
import gui.HorarioEditor.JIntHorarioPorAulas;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JInternalFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import testers.AsigTester;

/**
 *
 * @author david
 */
public class MainWindowTabbed extends javax.swing.JFrame {

    //Variables globales de aplicación
    boolean dirty;
    private DataProfesores dataProfesores;
    private DataAsignaturas dataAsignaturas;
    private DataAulas dataAulas;
    private DataKairos dk;
    private JIntWelcome jIntWelcome;
    private JIntTreeProfesores jIntTreeProfesores;
    private JIntTreeAsignaturas jIntTreeAsignaturas;
    private JIntTreeAulas jIntTreeAulas;
    private JIntAsignaciones jIntAsignaciones;
    private JIntRestricciones jIntRestricciones;
    private JIntGenetic jIntgenGenetic;
    private JIntHorarioPorAulas jIntHorarioEditor;
    ArrayList<JInternalFrame> listaTabs;
    private File lastFileUsed = new File("archivos/aa");

    /**
     * Creates new form MainWindow
     * @throws Exception 
     */
    public MainWindowTabbed() throws Exception {
        initComponents();
        dirty = false;
        //dataProfesores = new DataProfesores();
        AsigTester asig = new AsigTester();
        listaTabs = new ArrayList<JInternalFrame>();
        dk = new DataKairos();

        //asig.datosRelleno(dataProfesores, dataAsignaturas, dataAulas);
        //    asig.datosRelleno(dk.getDP());
        //dk.getDP().setMainWindow(this);

        //Parámetros básicos de la ventana
        this.setTitle("Kairos");

        //Añado los paneles que necesito
        jIntWelcome = new JIntWelcome(dk);
        addTab("Bienvenida", jIntWelcome);

        JIntDatosProyecto jIntDatosProyecto = new JIntDatosProyecto(dk);
        addTab("Datos del proyecto", jIntDatosProyecto);

        jIntTreeProfesores = new JIntTreeProfesores(dk);
        addTab("Profesores", jIntTreeProfesores);

        jIntTreeAsignaturas = new JIntTreeAsignaturas(dk);
        addTab("Asignaturas", jIntTreeAsignaturas);

        jIntTreeAulas = new JIntTreeAulas(dk);
        addTab("Aulas", jIntTreeAulas);


        jIntAsignaciones = new JIntAsignaciones(dk);
        addTab("Docencia", jIntAsignaciones);
        //dataProfesores.dataToDOM();

        jIntRestricciones = new JIntRestricciones(dk);
        addTab("Restricciones", jIntRestricciones);

        jIntgenGenetic = new JIntGenetic(dk);
        addTab("Optimizacion", jIntgenGenetic);

        jIntHorarioEditor = new JIntHorarioPorAulas(dk);
        addTab("Horario", jIntHorarioEditor);



        //Registro los listeners de cada ventana a otra
        registraListeners();



//        jIntWelcome.loadProyecto(new File("./archivos/aa"));

        jTabPrincipal.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                ((DataGUIInterface) jTabPrincipal.getSelectedComponent()).updateData();
            }
        });

    }

    /**
     *
     * @return
     */
    public JTabbedPane getjTabPrincipal() {
        return jTabPrincipal;
    }

    /**
     *
     * @param jTabPrincipal
     */
    public void setjTabPrincipal(JTabbedPane jTabPrincipal) {
        this.jTabPrincipal = jTabPrincipal;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabPrincipal = new javax.swing.JTabbedPane();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        cargarMenuItem = new javax.swing.JMenuItem();
        guardarMenuItem = new javax.swing.JMenuItem();
        guardarComoMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fileMenu.setMnemonic('f');
        fileMenu.setText("Archivo");

        cargarMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        cargarMenuItem.setMnemonic('o');
        cargarMenuItem.setText("Cargar proyecto");
        cargarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(cargarMenuItem);

        guardarMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        guardarMenuItem.setMnemonic('s');
        guardarMenuItem.setText("Guardar proyecto");
        guardarMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(guardarMenuItem);

        guardarComoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        guardarComoMenuItem.setMnemonic('a');
        guardarComoMenuItem.setText("Guardar proyecto como...");
        guardarComoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarComoMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(guardarComoMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Salir");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("Acerca de...");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed

        JDlgAbout dlg = new JDlgAbout(this, true);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void cargarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarMenuItemActionPerformed
        jIntWelcome.getCargarProyectoAction().actionPerformed(evt);
    }//GEN-LAST:event_cargarMenuItemActionPerformed

    private void guardarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarMenuItemActionPerformed
        jIntWelcome.getGuardarProyectoAction().actionPerformed(evt);
    }//GEN-LAST:event_guardarMenuItemActionPerformed

    private void guardarComoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarComoMenuItemActionPerformed
        jIntWelcome.getGuardarProyectoComoAction().actionPerformed(evt);
    }//GEN-LAST:event_guardarComoMenuItemActionPerformed

    private boolean checkDirty() {
        //Devuelve true si:
        //o bien isDirty() da negativo, es decir, no hay datos sin guardar.
        //o bien isDirty() da positivo Y se elige "SI" en el diálogo de confirmación
        //Se usa el operador "||" en vez de "|" para que el diálogo se evalúe 
        //solo si isDirty() es positivo.
//        return ((!dk.getDP().isDirty()) || (JOptionPane.showConfirmDialog(
//                this,
//                "Hay datos no guardados ¿Continuar de todos modos?",
//                "Aviso",
//                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION));
        return false;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem cargarMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem guardarComoMenuItem;
    private javax.swing.JMenuItem guardarMenuItem;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JTabbedPane jTabPrincipal;
    private javax.swing.JMenuBar menuBar;
    // End of variables declaration//GEN-END:variables

    /**
     *
     * @return
     */
    public JIntWelcome getjIntWelcome() {
        return jIntWelcome;
    }

    /**
     *
     * @param jIntWelcome
     */
    public void setjIntWelcome(JIntWelcome jIntWelcome) {
        this.jIntWelcome = jIntWelcome;
    }

    /**
     *
     * @return
     */
    public JIntTreeProfesores getjIntTreeProfesores() {
        return jIntTreeProfesores;
    }

    /**
     *
     * @param jIntTreeProfesores
     */
    public void setjIntTreeProfesores(JIntTreeProfesores jIntTreeProfesores) {
        this.jIntTreeProfesores = jIntTreeProfesores;
    }

    /**
     *
     * @return
     */
    public JIntTreeAsignaturas getjIntTreeAsignaturas() {
        return jIntTreeAsignaturas;
    }

    /**
     *
     * @param jIntTreeAsignaturas
     */
    public void setjIntTreeAsignaturas(JIntTreeAsignaturas jIntTreeAsignaturas) {
        this.jIntTreeAsignaturas = jIntTreeAsignaturas;
    }

    /**
     *
     * @return
     */
    public JIntTreeAulas getjIntTreeAulas() {
        return jIntTreeAulas;
    }

    /**
     *
     * @return
     */
    public JIntAsignaciones getjIntAsignaciones() {
        return jIntAsignaciones;
    }

    /**
     *
     * @return
     */
    public JIntRestricciones getjIntRestricciones() {
        return jIntRestricciones;
    }

    /**
     *
     * @return
     */
    public JIntHorarioPorAulas getjIntHorarioView() {
        return jIntHorarioEditor;
    }

    /**
     *
     * @return
     */
    public JIntGenetic getjIntgenGenetic() {
        return jIntgenGenetic;
    }

    private void addTab(String nombre, JInternalFrame tab) {
        jTabPrincipal.add(nombre, tab);
        DataGUIInterface d = (DataGUIInterface) tab;
        d.setMainWindow(this);
        listaTabs.add(tab);
    }

    /**
     *
     */
    public void refreshAllTabs() {
        DataGUIInterface gui;
        for (JInternalFrame tab : listaTabs) {
            gui = (DataGUIInterface) tab;
            gui.updateData();
        }
    }

    /**
     *
     */
    public void refrescaVentanaHorarios() {
    }

    /**
     *
     */
    public void expandAllTrees() {
        expandTree(jIntTreeProfesores.getjTreeProfesores());
        expandTree(jIntTreeAsignaturas.getjTreeAsignaturas());
        expandTree(jIntTreeAulas.getjTreeAulas());
    }

    /**
     *
     * @param jtree
     */
    public void expandTree(JTree jtree) {
        for (int i = 0; i < jtree.getRowCount(); i++) {
            jtree.expandRow(i);
        }
    }

    /**
     *
     */
    public void registraListeners() {
        dk.getDP().getDataRestricciones().addListener(jIntHorarioEditor);
        dk.getDP().getDataRestricciones().addListener(jIntRestricciones);

        dk.getDP().getDataProfesores().addListener(jIntTreeProfesores);

        dk.getDP().getDataAulas().addListener(jIntTreeAulas);
        dk.getDP().getDataAsignaturas().addListener(jIntTreeAsignaturas);
    }

    /**
     *
     */
    public void borraListeners() {
        dk.getDP().getDataRestricciones().clearListeners();
        dk.getDP().getDataRestricciones().clearListeners();
        dk.getDP().getDataAsignaturas().clearListeners();

        dk.getDP().getDataProfesores().clearListeners();

        dk.getDP().getDataAulas().clearListeners();
    }

    /**
     *
     * @return
     */
    public File getLastFileUsed() {
        return lastFileUsed;
    }
}

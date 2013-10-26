/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import data.DataKairos;
import data.DataProyecto;
import data.asignaturas.DataAsignaturas;
import data.aulas.DataAulas;
import data.profesores.DataProfesores;
import data.restricciones.Restriccion;
import gui.DatosEditor.Asignaturas.JIntTreeAsignaturas;
import gui.DatosEditor.Aulas.JIntTreeAulas;
import gui.DatosEditor.DataGUIInterface;
import gui.DatosEditor.Docencia.JIntAsignaciones;
import gui.DatosEditor.JIntDatosProyecto;
import gui.DatosEditor.JIntGenetic;
import gui.DatosEditor.JIntTreeProfesores;
import gui.DatosEditor.JIntWelcome;
import gui.DatosEditor.Restricciones.JIntRestricciones;
import gui.HorarioEditor.JIntHorarioPorAulas;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import loader.XMLDataLoaderWriter;

/**
 *
 * @author usuario
 */
public abstract class AbstractMainWindow extends javax.swing.JFrame {

    //Variables globales de aplicación
    boolean dirty;
    private DataProfesores dataProfesores;
    private DataAsignaturas dataAsignaturas;
    private DataAulas dataAulas;
    private final DataKairos dk;
    private JIntWelcome jIntWelcome;
    private JIntTreeProfesores jIntTreeProfesores;
    private JIntTreeAsignaturas jIntTreeAsignaturas;
    private JIntTreeAulas jIntTreeAulas;
    private JIntAsignaciones jIntAsignaciones;
    private JIntRestricciones jIntRestricciones;
    private JIntGenetic jIntgenGenetic;
    private JIntHorarioPorAulas jIntHorarioEditor;
    protected ArrayList<JInternalFrame> listaTabs;
    protected AbstractAction cargarProyectoAction;
    protected AbstractAction guardarProyectoAction;
    protected AbstractAction guardarProyectoComoAction;
    protected AbstractAction importarXMLAction;
    private File lastFileUsed;

    public AbstractMainWindow() throws Exception {
        super();
        dirty = false;
        listaTabs = new ArrayList<JInternalFrame>();
        dk = new DataKairos();

        //Parámetros básicos de la ventana
        this.setTitle("Kairos");
        creaAcciones();
        registraListeners();
    }

    protected void createInternalFrames() throws Exception, TooManyListenersException {
        //Añado los paneles que necesito
//        jIntWelcome = new JIntWelcome(dk);
//        addTab("Bienvenida", jIntWelcome);

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
    }
//
//    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
//        System.exit(0);
//    }
//
//    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
//
//        JDlgAbout dlg = new JDlgAbout(this, true);
//        dlg.setLocationRelativeTo(null);
//        dlg.setVisible(true);
//    }
//
//    private void cargarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
//        jIntWelcome.getCargarProyectoAction().actionPerformed(evt);
//    }
//
//    private void guardarMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
//        jIntWelcome.getGuardarProyectoAction().actionPerformed(evt);
//    }
//
//    private void guardarComoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
//        jIntWelcome.getGuardarProyectoComoAction().actionPerformed(evt);
//    }

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
    /**
     *
     * @return
     */
    public JIntTreeAsignaturas getjIntTreeAsignaturas() {
        return jIntTreeAsignaturas;
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

    abstract protected void addTab(String nombre, JInternalFrame tab);

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
    public final void registraListeners() {
        dk.getDP().getDataRestricciones().addListener(jIntHorarioEditor);
        dk.getDP().getDataRestricciones().addListener(jIntRestricciones);

        dk.getDP().getDataProfesores().addListener(jIntTreeProfesores);

        dk.getDP().getDataAulas().addListener(jIntTreeAulas);
        dk.getDP().getDataAsignaturas().addListener(jIntTreeAulas);
        
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

    abstract public void switchToComponent(DataGUIInterface dataIF);

    public final void creaAcciones() {
        final AbstractMainWindow mainWindow = this;
        class CargarProyectoAction extends AbstractAction {

            public CargarProyectoAction() {
                super("Cargar proyecto", null);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if ((!dk.getDP().isDirty()) || (JOptionPane.showConfirmDialog(rootPane, "Hay datos sin guardar, ¿continuar?", "Datos sin guardar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
                    FileInputStream fich;
                    ObjectInputStream os;
                    try {
                        JFileChooser fc = new JFileChooser(getLastFileUsed());
                        FileNameExtensionFilter filt = new FileNameExtensionFilter("Archivos kairos", "krs");
                        fc.setFileFilter(filt);
                        fc.setDialogTitle("Elige archivo a cargar");
                        int valorDevuelto = fc.showSaveDialog(null);

                        if (valorDevuelto == JFileChooser.APPROVE_OPTION) {
                            setLastFileUsed(fc.getSelectedFile());
                            fich = new FileInputStream(lastFileUsed);
                            os = new ObjectInputStream(fich);
                            DataProyecto o = (DataProyecto) os.readObject();
                            os.close();
                            dk.setDP(o);
                            registraListeners();
//                            getjIntTreeProfesores().updateData();
//                            getjIntTreeAsignaturas().updateData();
//                            getjIntTreeAulas().updateData();
//                            getjIntAsignaciones().updateData();
                            getjIntHorarioView().getHorariosJPanelModel().setMainWindow(mainWindow);

                            for (Restriccion r : dk.getDP().getDataRestricciones().getListaRestricciones()) {
                                r.setDataProyecto(dk.getDP());
                            }
                            for (JInternalFrame tab : listaTabs) {
                                ((DataGUIInterface) tab).updateData();
                            }
                            expandAllTrees();
                            getjIntHorarioView().needRecalcularPesos();

                        }
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(JIntWelcome.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(JIntWelcome.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(JIntWelcome.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
//                        try {
//                            os.close();
//                        } catch (IOException ex) {
//                            Logger.getLogger(JIntWelcome.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                    }
                }
            }
        }

        class GuardarProyectoAction extends AbstractAction {

            public GuardarProyectoAction() {
                super("Guardar proyecto", null);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                FileOutputStream fisal;
                mainWindow.borraListeners();
                try {
                    if (lastFileUsed == null) {
                        guardarProyectoComoAction.actionPerformed(e);
                    } else {
                        fisal = new FileOutputStream(lastFileUsed);
                        ObjectOutputStream oos = new ObjectOutputStream(fisal);
                        oos.writeObject(dk.getDP());
                        oos.close();

                    }
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(rootPane, "No puedo cargar este fichero", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(rootPane, "No puedo cargar este fichero", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                }
            }
        }
        class GuardarProyectoComoAction extends AbstractAction {

            public GuardarProyectoComoAction() {
                super("Guardar proyecto como...", null);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                FileOutputStream fisal;
                mainWindow.borraListeners();
                try {
                    JFileChooser fc = new JFileChooser(lastFileUsed);
                    fc.setDialogTitle("Guardar como...");
                    FileNameExtensionFilter filt = new FileNameExtensionFilter("Archivos kairos", "krs");
                    fc.setFileFilter(filt);
                    int valorDevuelto = fc.showSaveDialog(null);

                    if (valorDevuelto == JFileChooser.APPROVE_OPTION) {
                        if ((!fc.getSelectedFile().exists()) || (JOptionPane.showConfirmDialog(rootPane, "El fichero existe, ¿sobreescribir?", "Atención", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
                            setLastFileUsed(fc.getSelectedFile());
                            fisal = new FileOutputStream(lastFileUsed);
                            ObjectOutputStream oos = new ObjectOutputStream(fisal);
                            oos.writeObject(dk.getDP());
                            oos.close();
                        }
                    }
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Ha ocurrido un error al guardar este fichero.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(rootPane, "Error al guardar. Probablemente no tenga permisos para escribir en el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                }
            }
        }
        class ImportarXMLAction extends AbstractAction {

            public ImportarXMLAction() {
                super("Importar datos XML", null);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                //Este metodo IMPORTA datos de XML
                if ((!dk.getDP().isDirty()) || (JOptionPane.showConfirmDialog(rootPane, "Hay datos sin guardar, ¿continuar?", "Datos sin guardar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
                    JFileChooser fc = new JFileChooser("archivos");
                    fc.setDialogTitle("Elige archivo de proyecto a cargar");
                    FileNameExtensionFilter filt = new FileNameExtensionFilter("Archivo XML", "xml");
                    fc.setFileFilter(filt);
                    int valorDevuelto = fc.showOpenDialog(null);

                    if (valorDevuelto == JFileChooser.APPROVE_OPTION) {
                        dk.clear();
                        boolean cargaCorrecta = loadXMLFromFile(fc.getSelectedFile());
                        if (!cargaCorrecta) {
                            JOptionPane.showMessageDialog(null, "Error al cargar los datos.");
                        }
                        mainWindow.refreshAllTabs();
                        mainWindow.expandAllTrees();
                        dk.getDP().setDirty(false);
                    }
                }
            }
        }

        cargarProyectoAction = new CargarProyectoAction();
        guardarProyectoAction = new GuardarProyectoAction();
        guardarProyectoComoAction = new GuardarProyectoComoAction();
        importarXMLAction = new ImportarXMLAction();
    }

    /**
     *
     * @param fichero
     * @return
     * @throws IOException
     */
    public boolean saveToFile(File fichero) throws IOException {
        XMLDataLoaderWriter xmldlw = new XMLDataLoaderWriter(dk.getDP());
        xmldlw.setFile(fichero);
        return xmldlw.save();
    }

    /**
     *
     * @param fichero
     * @return
     */
    public boolean loadXMLFromFile(File fichero) {
        XMLDataLoaderWriter xmldlw = new XMLDataLoaderWriter(dk.getDP());
        xmldlw.setFile(fichero);
        boolean resul = xmldlw.load(fichero);
        //Reconstruyo hashmap de profesores, util para asignaciones
        if (resul) {
            dk.getDP().reconstruyeHashMapProfesor();
        }
        return resul;
    }

    /**
     *
     * @param lastFileUsed
     */
    public void setLastFileUsed(File lastFileUsed) {
        this.lastFileUsed = lastFileUsed;
    }

}

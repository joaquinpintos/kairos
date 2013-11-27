package gui;

import data.DataKairos;
import data.DataProyecto;
import data.asignaturas.DataAsignaturas;
import data.aulas.DataAulas;
import data.profesores.DataProfesores;
import gui.DatosEditor.Asignaturas.JIntEditorAsignaturas;
import gui.DatosEditor.Aulas.JIntTreeAulas;
import gui.DatosEditor.DataGUIInterface;
import gui.DatosEditor.Docencia.JIntEditorDocencia;
import gui.DatosEditor.JIntDatosProyecto;
import gui.DatosEditor.JIntGenetic;
import gui.DatosEditor.JIntTreeProfesores;
import gui.DatosEditor.JIntWelcome;
import gui.DatosEditor.Restricciones.JIntRestricciones;
import gui.HorarioEditor.JIntHorarioEditor;
import gui.printDialogs.JDlgPrintHojaDeFirma;
import gui.printDialogs.jDlgPrintHorario;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
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
 * Clase abstracta que maneja la ventana principal
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
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
    private JIntEditorAsignaturas jIntTreeAsignaturas;
    private JIntTreeAulas jIntTreeAulas;
    private JIntEditorDocencia jIntEditorDocencia;
    private JIntRestricciones jIntRestricciones;
    private JIntGenetic jIntgenGenetic;
    private JIntHorarioEditor jIntHorarioEditor;

    /**
     *
     */
    protected ArrayList<JInternalFrame> listaTabs;

    protected AbstractAction cargarProyectoAction;

    protected AbstractAction guardarProyectoAction;

    protected AbstractAction guardarProyectoComoAction;

    protected AbstractAction importarXMLAction;

    protected AbstractAction exportarXMLAction;

    protected AbstractAction creaPDFAction;
    private File lastFileUsed;
    private JIntHorarioEditor jIntHorarioEditor2;

    /**
     *
     */
    protected AbstractAction creaPDFHojasDeFirmaAction;

    /**
     *
     * @throws Exception
     */
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

    /**
     *
     * @throws Exception
     * @throws TooManyListenersException
     */
    protected void createInternalFrames() throws Exception, TooManyListenersException {

        JIntDatosProyecto jIntDatosProyecto = new JIntDatosProyecto(dk);
        addTab("Datos del proyecto", jIntDatosProyecto);

        jIntTreeProfesores = new JIntTreeProfesores(dk);
        addTab("Profesores", jIntTreeProfesores);

        jIntTreeAsignaturas = new JIntEditorAsignaturas(dk);
        addTab("Asignaturas", jIntTreeAsignaturas);

        jIntTreeAulas = new JIntTreeAulas(dk);
        addTab("Aulas", jIntTreeAulas);

        jIntEditorDocencia = new JIntEditorDocencia(dk);
        addTab("Docencia", jIntEditorDocencia);
        //dataProfesores.dataToDOM();

        jIntRestricciones = new JIntRestricciones(dk);
        addTab("Restricciones", jIntRestricciones);

        jIntgenGenetic = new JIntGenetic(dk);
        addTab("Optimizacion", jIntgenGenetic);

        jIntHorarioEditor = new JIntHorarioEditor(dk);
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
    public JIntEditorAsignaturas getjIntTreeAsignaturas() {
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
    public JIntEditorDocencia getjIntAsignaciones() {
        return jIntEditorDocencia;
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
    public JIntHorarioEditor getjIntHorarioView() {
        return jIntHorarioEditor;
    }

    /**
     *
     * @return
     */
    public JIntGenetic getjIntgenGenetic() {
        return jIntgenGenetic;
    }

    /**
     *
     * @param nombre
     * @param tab
     */
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
        dk.getDP().getDataRestricciones().addListener(jIntHorarioEditor2);
        dk.getDP().getDataRestricciones().addListener(jIntRestricciones);

        dk.getDP().getDataProfesores().addListener(jIntTreeProfesores);

        dk.getDP().getDataAulas().addListener(jIntTreeAulas);
        dk.getDP().getDataAsignaturas().addListener(jIntTreeAulas);
        dk.getDP().getDataAsignaturas().addListener(jIntTreeAsignaturas);
        dk.getDP().getDataAsignaturas().addListener(dk.getDP().getDataAsignaturas().getListaGrupoCursos());
        //dk.getDP().getDataAsignaturas().getListaGrupoCursos().addListener(jIntTreeAulas);

        dk.getDP().getDataAsignaturas().addListener(jIntEditorDocencia);
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

    /**
     *
     * @param dataIF
     */
    abstract public void switchToComponent(DataGUIInterface dataIF);

    /**
     *
     */
    public final void creaAcciones() {
        final AbstractMainWindow mainWindow = this;
        class CargarProyectoAction extends AbstractAction {

            public CargarProyectoAction() {
                super("Cargar proyecto", null);
                putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_C));
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
//                            getjIntHorarioView().getHorariosJPanelModel().setMainWindow(mainWindow);

//                            for (Restriccion r : dk.getDP().getDataRestricciones().getListaRestricciones()) {
//                                r.setDataProyecto(dk.getDP());
//                            }
                            for (JInternalFrame tab : listaTabs) {
                                ((DataGUIInterface) tab).updateData();
                            }
                            expandAllTrees();
                            getjIntHorarioView().needRecalcularPesos();
                            if (dk.getDP().getHorario().hayUnaSolucion()) {
                                setProjectStatus(DataKairos.STATUS_PROJECT_SOLUTION);
                            } else {
                                setProjectStatus(DataKairos.STATUS_PROJECT_NO_SOLUTION);
                            }
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
                putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_G));
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
                    JOptionPane.showMessageDialog(rootPane, "Archivo no encontrado", "Error al guardar", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(rootPane, "I/O Error", "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                }
            }
        }
        class GuardarProyectoComoAction extends AbstractAction {

            public GuardarProyectoComoAction() {
                super("Guardar proyecto como...", null);
                putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_U));
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                FileOutputStream fisal;
                mainWindow.borraListeners();
                JFileChooser fc = new JFileChooser(lastFileUsed);
                fc.setDialogTitle("Guardar como...");
                FileNameExtensionFilter filt = new FileNameExtensionFilter("Archivos kairos", "krs");
                fc.setFileFilter(filt);
                int valorDevuelto = fc.showSaveDialog(null);

                if (valorDevuelto == JFileChooser.APPROVE_OPTION) {
                    if ((!fc.getSelectedFile().exists()) || (JOptionPane.showConfirmDialog(rootPane, "El fichero existe, ¿sobreescribir?", "Atención", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
                        setLastFileUsed(fc.getSelectedFile());
                        fisal = null;
                        try {
                            fisal = new FileOutputStream(lastFileUsed);
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(AbstractMainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        ObjectOutputStream oos;
                        try {
                            oos = new ObjectOutputStream(fisal);
                            oos.writeObject(dk.getDP());
                            oos.close();
                        } catch (IOException ex) {
                            Logger.getLogger(AbstractMainWindow.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
            }
        }

        class ImportarXMLAction extends AbstractAction {

            public ImportarXMLAction() {
                super("Importar datos XML", null);
                putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_I));
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
                        File fichero = fc.getSelectedFile();
                        XMLDataLoaderWriter xmldlw = new XMLDataLoaderWriter(dk.getDP());
                        xmldlw.setFile(fichero);
                        boolean resul = xmldlw.load(fichero);
                        //Reconstruyo hashmap de profesores, util para asignaciones
                        if (resul) {
                            dk.getDP().reconstruyeHashMapProfesor();
                        }
                        boolean cargaCorrecta = resul;
                        if (!cargaCorrecta) {
                            JOptionPane.showMessageDialog(null, "Error al cargar los datos.");
                        }
                        mainWindow.refreshAllTabs();
                        mainWindow.expandAllTrees();
                        dk.getDP().setDirty(false);
                        if (dk.getDP().getHorario().hayUnaSolucion()) {
                            mainWindow.setProjectStatus(DataKairos.STATUS_PROJECT_SOLUTION);
                        } else {
                            mainWindow.setProjectStatus(DataKairos.STATUS_PROJECT_NO_SOLUTION);
                        }
                    }
                }
            }
        }

        class ExportarXMLAction extends AbstractAction {

            public ExportarXMLAction() {
                super("Exportar datos XML", null);
                putValue(MNEMONIC_KEY, new Integer(KeyEvent.VK_E));
            }

            @Override
            public void actionPerformed(ActionEvent e) {  //Este metodo EXPORTA los datos a XML
                JFileChooser fc = new JFileChooser(mainWindow.getLastFileUsed());
                fc.setDialogTitle("Elige archivo a guardar");
                int valorDevuelto = fc.showSaveDialog(null);

                if (valorDevuelto == JFileChooser.APPROVE_OPTION) {
                    boolean guardadoCorrecto = true;
                    try {
                        XMLDataLoaderWriter xmldlw = new XMLDataLoaderWriter(dk.getDP());
                        xmldlw.setFile(fc.getSelectedFile());
                        guardadoCorrecto = xmldlw.save();

                    } catch (IOException ex) {
                        Logger.getLogger(JIntWelcome.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (!guardadoCorrecto) {
                        JOptionPane.showMessageDialog(null, "Error al guardar los datos.");
                    }
                }
            }
        }

        cargarProyectoAction = new CargarProyectoAction();
        guardarProyectoAction = new GuardarProyectoAction();
        guardarProyectoComoAction = new GuardarProyectoComoAction();
        importarXMLAction = new ImportarXMLAction();
        exportarXMLAction = new ExportarXMLAction();

        class CreaPDFHorariosAction extends AbstractAction {

            public CreaPDFHorariosAction() {
                super("Imprimir calendarios", null);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                jDlgPrintHorario dlg = new jDlgPrintHorario(mainWindow, true, dk.getDP());
                dlg.setLocationRelativeTo(null);
                dlg.setVisible(true);
            }
        }
        creaPDFAction = new CreaPDFHorariosAction();

        class CreaPDFHojasDeFirmaAction extends AbstractAction {

            public CreaPDFHojasDeFirmaAction() {
                super("Imprimir hojas de firma", null);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JDlgPrintHojaDeFirma dlg = new JDlgPrintHojaDeFirma(mainWindow, true, dk.getDP());
                dlg.setLocationRelativeTo(null);
                dlg.setVisible(true);
            }
        }
        creaPDFHojasDeFirmaAction = new CreaPDFHojasDeFirmaAction();
    }

    /**
     *
     * @param lastFileUsed
     */
    public void setLastFileUsed(File lastFileUsed) {
        this.lastFileUsed = lastFileUsed;
    }

    /**
     *
     * @param status
     */
    public void setProjectStatus(int status) {
        switch (status) {
            case DataKairos.STATUS_NO_PROJECT: {
                for (JInternalFrame t : listaTabs) {
                    t.setVisible(false);
                }
                cargarProyectoAction.setEnabled(true);
                guardarProyectoAction.setEnabled(false);
                guardarProyectoComoAction.setEnabled(false);
                importarXMLAction.setEnabled(true);
                exportarXMLAction.setEnabled(false);
                creaPDFAction.setEnabled(false);
                creaPDFHojasDeFirmaAction.setEnabled(false);
                break;
            }
            case DataKairos.STATUS_PROJECT_NO_SOLUTION: {
                cargarProyectoAction.setEnabled(true);
                guardarProyectoAction.setEnabled(true);
                guardarProyectoComoAction.setEnabled(true);
                importarXMLAction.setEnabled(true);
                exportarXMLAction.setEnabled(true);
                creaPDFAction.setEnabled(false);
                creaPDFHojasDeFirmaAction.setEnabled(false);
                break;
            }
            case DataKairos.STATUS_PROJECT_SOLUTION: {
                cargarProyectoAction.setEnabled(true);
                guardarProyectoAction.setEnabled(true);
                guardarProyectoComoAction.setEnabled(true);
                importarXMLAction.setEnabled(true);
                exportarXMLAction.setEnabled(true);
                creaPDFAction.setEnabled(true);
                creaPDFHojasDeFirmaAction.setEnabled(true);
                break;
            }
            default: {
                //TODO: Falta poner status computing solution
                break;
            }
        }
    }
}

package gui;

import data.DataKairos;
import data.DataProject;
import data.asignaturas.DataAsignaturas;
import data.aulas.DataAulas;
import data.profesores.DataProfesores;
import gui.BusquedaHorasLibres.JDlgBuscaHorasLilbres;
import gui.DatosEditor.Asignaturas.JIntEditorAsignaturas;
import gui.DatosEditor.Aulas.JIntTreeAulas;
import gui.DatosEditor.DataGUIInterface;
import gui.DatosEditor.Docencia.JIntEditorDocencia;
import gui.DatosEditor.JIntDatosProyecto;
import gui.DatosEditor.JIntGenetic;
import gui.DatosEditor.JIntTreeProfesores;
import gui.DatosEditor.Restricciones.JIntRestricciones;
import gui.HorarioEditor.HorarioEditorMaster;
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
    private File lastFileUsed;
    private DataProfesores dataProfesores;
    private DataAsignaturas dataAsignaturas;
    private DataAulas dataAulas;
    protected final DataKairos dk;
    private JIntDatosProyecto jIntDatosProyecto;
    private JIntTreeProfesores jIntTreeProfesores;
    private JIntEditorAsignaturas jIntTreeAsignaturas;
    private JIntTreeAulas jIntTreeAulas;
    private JIntEditorDocencia jIntEditorDocencia;
    private JIntRestricciones jIntRestricciones;
    private JIntGenetic jIntgenGenetic;
    private final HorarioEditorMaster horarioEditorMaster;
    protected ArrayList<JInternalFrame> listaTabs;
    protected AbstractAction cargarProyectoAction;
    protected AbstractAction guardarProyectoAction;
    protected AbstractAction guardarProyectoComoAction;
    protected AbstractAction exportarXMLAction;
    protected AbstractAction creaPDFHojasDeFirmaAction;
    protected AbstractAction newProjectAction;
    protected AbstractAction creaPDFAction;
    protected AbstractAction importarXMLAction;
    protected AbstractAction buscaHorasLibresAction;

    /**
     *
     * @throws Exception
     */
    public AbstractMainWindow() throws Exception {
        super();
        dirty = false;
        listaTabs = new ArrayList<JInternalFrame>();
        dk = new DataKairos();
        horarioEditorMaster = new HorarioEditorMaster(dk);
        //Parámetros básicos de la ventana
        this.setTitle("Kairos");
        createActions();
        addListeners();

    }

    /**
     *
     * @throws Exception
     * @throws TooManyListenersException
     */
    protected void createInternalFrames() throws Exception, TooManyListenersException {

        jIntDatosProyecto = new JIntDatosProyecto(dk);
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

        JIntHorarioEditor jIntHorarioEditor = new JIntHorarioEditor(dk);
        addTab("Horario", jIntHorarioEditor);
        JIntHorarioEditor jIntHorarioEditor2 = new JIntHorarioEditor(dk);
        addTab("Horario2", jIntHorarioEditor2);
        jIntHorarioEditor2.getjListRestricciones().setVisible(false);
        horarioEditorMaster.add(jIntHorarioEditor);
        horarioEditorMaster.add(jIntHorarioEditor2);
        horarioEditorMaster.setjListRestricciones(jIntHorarioEditor.getjListRestricciones());
    }
    /**
     *
     * @return
     */
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
//    public JIntHorarioEditor getjIntHorarioView() {
//        return jIntHorarioEditor;
//    }
//
//    /**
//     *
//     * @return
//     */
//    public JIntHorarioEditor getjIntHorarioView2() {
//        return jIntHorarioEditor2;
//    }
    public HorarioEditorMaster getHorarioEditorMaster() {
        return horarioEditorMaster;
    }

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
    /**
     *
     */
    public final void addListeners() {
        dk.getDP().getRestrictionsData().addListener(horarioEditorMaster);
        dk.getDP().getRestrictionsData().addListener(jIntRestricciones);

        dk.getDP().getDataProfesores().addListener(jIntTreeProfesores);

        dk.getDP().getDataAulas().addListener(jIntTreeAulas);
        dk.getDP().getDataAsignaturas().addListener(jIntTreeAulas);
        dk.getDP().getDataAsignaturas().addListener(jIntTreeAsignaturas);
        dk.getDP().getDataAsignaturas().addListener(dk.getDP().getDataAsignaturas().getListaGrupoCursos());

        dk.getDP().getDataAsignaturas().addListener(jIntEditorDocencia);
    }

    /**
     *
     */
    public void borraListeners() {
        dk.getDP().getRestrictionsData().clearListeners();
        dk.getDP().getRestrictionsData().clearListeners();
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
    public final void createActions() {
        final AbstractMainWindow mainWindow = this;
        //<editor-fold defaultstate="collapsed" desc="NewProjectAction">
        class NewProjectAction extends AbstractAction {

            public NewProjectAction() {
                super("Nuevo proyecto", null);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if (dk.getDP() != null) {
                    if ((!dk.getDP().isDirty()) || (JOptionPane.showConfirmDialog(rootPane, "Los datos no guardados se perderán ¿Continuar?", "Atención", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
                        String s = (String) JOptionPane.showInputDialog(
                                mainWindow,
                                "Introduce el nombre del proyecto",
                                "",
                                JOptionPane.PLAIN_MESSAGE,
                                null,
                                null,
                                "");

                        dk.createNewDP(s);
                        setProjectStatus(DataKairos.STATUS_PROJECT_NO_SOLUTION);
                        jIntDatosProyecto.updateData();

                    }
                }

            }

        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="CargarProyectoAction">
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
                            DataProject o = (DataProject) os.readObject();
                            os.close();
                            dk.setDP(o);
                            addListeners();
//                            getjIntHorarioView().getHorariosJPanelModel().setMainWindow(mainWindow);

//                            for (Restriccion r : dk.getDP().getDataRestricciones().getListaRestricciones()) {
//                                r.setDataProyecto(dk.getDP());
//                            }
                            for (JInternalFrame tab : listaTabs) {
                                ((DataGUIInterface) tab).updateData();
                                ((DataGUIInterface) tab).expandTrees();
                            }

                            if (dk.getDP().getHorario().hayUnaSolucion()) {
                                setProjectStatus(DataKairos.STATUS_PROJECT_SOLUTION);
                            } else {
                                setProjectStatus(DataKairos.STATUS_PROJECT_NO_SOLUTION);
                            }
                            horarioEditorMaster.needRecalcularPesos();
                        }
                    } catch (FileNotFoundException ex) {
                    } catch (IOException ex) {
                    } catch (ClassNotFoundException ex) {
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
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="GuardarProyectoAction">
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
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="GuardarProyectoComoAction">
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
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="ImportarXMLAction">
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
                        for (JInternalFrame tab : listaTabs) {
                            ((DataGUIInterface) tab).updateData();
                            ((DataGUIInterface) tab).expandTrees();
                        }
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
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="ExportarXMLAction">
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
                    }
                    if (!guardadoCorrecto) {
                        JOptionPane.showMessageDialog(null, "Error al guardar los datos.");
                    }
                }
            }
        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="BuscaHorasLibresAction">
        class BuscaHorasLibresAction extends AbstractAction {

            public BuscaHorasLibresAction() {
                super("Buscar horas libres", null);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                JDlgBuscaHorasLilbres dlg = new JDlgBuscaHorasLilbres(null, true, dk);
                dlg.setLocationRelativeTo(null);
                dlg.setVisible(true);
            }
        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="CreaPDFHorariosAction">
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
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="CreaPDFHojasDeFirmaAction">
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
//</editor-fold>
        newProjectAction = new NewProjectAction();
        cargarProyectoAction = new CargarProyectoAction();
        guardarProyectoAction = new GuardarProyectoAction();
        guardarProyectoComoAction = new GuardarProyectoComoAction();
        importarXMLAction = new ImportarXMLAction();
        exportarXMLAction = new ExportarXMLAction();
        buscaHorasLibresAction = new BuscaHorasLibresAction();
        creaPDFAction = new CreaPDFHorariosAction();
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
                buscaHorasLibresAction.setEnabled(false);
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
                buscaHorasLibresAction.setEnabled(false);
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
                buscaHorasLibresAction.setEnabled(true);
                break;
            }
            default: {
                //TODO: Falta poner status computing solution
                break;
            }
        }
    }
}

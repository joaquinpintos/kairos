package gui;

import data.DataKairos;
import static data.DataKairos.STATUS_NO_PROJECT;
import data.DataProject;
import data.MyConstants;
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
import gui.DatosEditor.Profesores.JIntTreeProfesores;
import gui.DatosEditor.Restricciones.JIntRestricciones;
import gui.HorarioEditor.HorarioEditorMaster;
import gui.HorarioEditor.JIntHorarioEditor;
import gui.printDialogs.JDlgPrintHojaDeFirma;
import gui.printDialogs.jDlgPrintHorario;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TooManyListenersException;
import javax.imageio.ImageIO;
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
    protected JIntDatosProyecto jIntDatosProyecto;
    protected JIntTreeProfesores jIntTreeProfesores;
    protected JIntEditorAsignaturas jIntTreeAsignaturas;
    protected JIntTreeAulas jIntTreeAulas;
    protected JIntEditorDocencia jIntEditorDocencia;
    protected JIntRestricciones jIntRestricciones;
    protected JIntGenetic jIntgenGenetic;
    protected final HorarioEditorMaster horarioEditorMaster;
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
    protected AbstractAction exitAction;
    private final HashSet<AbstractAction> actions;
    //This map stores status->set of actions to be enabled
    private final HashMap<Integer, HashSet<AbstractAction>> enabledActions;

    /**
     *
     * @throws Exception
     */
    public AbstractMainWindow() throws Exception {
        super();
        try {
            super.setIconImage(ImageIO.read(getClass().getClassLoader().getResource("data/images/appIcon.png")));
        } catch (IOException e) {
            System.err.println("Error al cargar icono");
        }

        dirty = false;
        listaTabs = new ArrayList<JInternalFrame>();
        dk = new DataKairos();
        horarioEditorMaster = new HorarioEditorMaster(dk);
        //Parámetros básicos de la ventana
        this.setTitle("Kairos");
        actions = new HashSet<AbstractAction>();
        enabledActions = new HashMap<Integer, HashSet<AbstractAction>>();
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
        addTab("Datos del proyecto", jIntDatosProyecto,KeyEvent.VK_1);

        jIntTreeProfesores = new JIntTreeProfesores(dk);
        addTab("Profesores", jIntTreeProfesores,KeyEvent.VK_2);

        jIntTreeAsignaturas = new JIntEditorAsignaturas(dk);
        addTab("Asignaturas", jIntTreeAsignaturas,KeyEvent.VK_3);

        jIntTreeAulas = new JIntTreeAulas(dk);
        addTab("Aulas", jIntTreeAulas,KeyEvent.VK_4);

        jIntEditorDocencia = new JIntEditorDocencia(dk);
//        addTab("Docencia", jIntEditorDocencia);
        //dataProfesores.dataToDOM();

        jIntRestricciones = new JIntRestricciones(dk);
        addTab("Restricciones", jIntRestricciones,KeyEvent.VK_5);

        jIntgenGenetic = new JIntGenetic(dk);
        addTab("Optimizacion", jIntgenGenetic,KeyEvent.VK_6);

        JIntHorarioEditor jIntHorarioEditor = new JIntHorarioEditor(dk);
        addTab("Horario", jIntHorarioEditor,KeyEvent.VK_7);
        JIntHorarioEditor jIntHorarioEditor2 = new JIntHorarioEditor(dk);
        addTab("Horario2", jIntHorarioEditor2,KeyEvent.VK_8);
        jIntHorarioEditor2.getjListRestricciones().setVisible(false);
        horarioEditorMaster.add(jIntHorarioEditor, true);
        horarioEditorMaster.add(jIntHorarioEditor2, false);
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
    abstract protected void addTab(String nombre, JInternalFrame tab,int accel);

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
        dk.getDP().getDataProfesores().addListener(jIntTreeAsignaturas);
        dk.getDP().getDataProfesores().addListener(jIntTreeAulas);

        dk.getDP().getDataAulas().addListener(jIntTreeAulas);
        dk.getDP().getDataAsignaturas().addListener(jIntTreeAulas);
        dk.getDP().getDataAsignaturas().addListener(jIntTreeProfesores);
        dk.getDP().getDataAsignaturas().addListener(jIntTreeAsignaturas);
        dk.getDP().getDataAsignaturas().addListener(dk.getDP().getDataAsignaturas().getListaGrupoCursos());

        dk.getDP().getDataAsignaturas().addListener(jIntEditorDocencia);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitAction.actionPerformed(null);
            }
        });
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
                        addListeners();
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
                        int valorDevuelto = fc.showOpenDialog(null);

                        if (valorDevuelto == JFileChooser.APPROVE_OPTION) {
                            setLastFileUsed(fc.getSelectedFile());
                            fich = new FileInputStream(lastFileUsed);
                            os = new ObjectInputStream(fich);
                            DataProject o = (DataProject) os.readObject();
                            os.close();
                            dk.setDP(o);
                            o.setDirty(false);
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
//                            horarioEditorMaster.needRecalcularPesos();
                        }
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(rootPane, "Archivo no encontrado", "Error al cargar", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(rootPane, "Formato de datos no soportado", "Error al cargar", JOptionPane.ERROR_MESSAGE);
                    } catch (ClassNotFoundException ex) {
                        JOptionPane.showMessageDialog(rootPane, "Error de E/S", "Error al cargar las clases", JOptionPane.ERROR_MESSAGE);
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
                        dk.getDP().setDirty(false);

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
                            JOptionPane.showMessageDialog(rootPane, "Archivo no encontrado", "Error al guardar", JOptionPane.ERROR_MESSAGE);
                        }
                        ObjectOutputStream oos;
                        try {
                            oos = new ObjectOutputStream(fisal);
                            oos.writeObject(dk.getDP());
                            oos.close();
                            dk.getDP().setDirty(false);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(rootPane, "Error de E/S", "Error al guardar", JOptionPane.ERROR_MESSAGE);
                        }

                    }
                }
            }
        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="ExitAction">
        class ExitAction extends AbstractAction {

            public ExitAction() {
                super("Salir", null);
            }

            @Override
            public void actionPerformed(ActionEvent e) {
                if ((!dk.getDP().isDirty()) || (JOptionPane.showConfirmDialog(rootPane, "Hay datos sin guardar, ¿desea salir?", "Datos sin guardar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
                    System.exit(0);
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
        registerAction(newProjectAction, DataKairos.STATUS_NO_PROJECT, DataKairos.STATUS_PROJECT_NO_SOLUTION, DataKairos.STATUS_PROJECT_SOLUTION);

        cargarProyectoAction = new CargarProyectoAction();
        registerAction(cargarProyectoAction, DataKairos.STATUS_NO_PROJECT, DataKairos.STATUS_PROJECT_NO_SOLUTION, DataKairos.STATUS_PROJECT_SOLUTION);

        guardarProyectoAction = new GuardarProyectoAction();
        registerAction(guardarProyectoAction, DataKairos.STATUS_PROJECT_NO_SOLUTION, DataKairos.STATUS_PROJECT_SOLUTION);

        guardarProyectoComoAction = new GuardarProyectoComoAction();
        registerAction(guardarProyectoComoAction, DataKairos.STATUS_PROJECT_NO_SOLUTION, DataKairos.STATUS_PROJECT_SOLUTION);

        exitAction = new ExitAction();
        registerAction(exitAction, DataKairos.STATUS_NO_PROJECT, DataKairos.STATUS_PROJECT_NO_SOLUTION, DataKairos.STATUS_PROJECT_SOLUTION);

        importarXMLAction = new ImportarXMLAction();
        registerAction(importarXMLAction, DataKairos.STATUS_NO_PROJECT, DataKairos.STATUS_PROJECT_NO_SOLUTION, DataKairos.STATUS_PROJECT_SOLUTION);

        exportarXMLAction = new ExportarXMLAction();
        registerAction(exportarXMLAction, DataKairos.STATUS_PROJECT_NO_SOLUTION, DataKairos.STATUS_PROJECT_SOLUTION);

        buscaHorasLibresAction = new BuscaHorasLibresAction();
        registerAction(buscaHorasLibresAction, DataKairos.STATUS_PROJECT_SOLUTION);

        creaPDFAction = new CreaPDFHorariosAction();
        registerAction(creaPDFAction, DataKairos.STATUS_PROJECT_SOLUTION);

        creaPDFHojasDeFirmaAction = new CreaPDFHojasDeFirmaAction();
        registerAction(creaPDFHojasDeFirmaAction, DataKairos.STATUS_PROJECT_SOLUTION);
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
        dk.setStatus(status);
        HashSet<AbstractAction> en = enabledActions.get(status);
        for (AbstractAction ac:actions)
        {
            ac.setEnabled(en.contains(ac));
        }
    }

    protected AbstractAction registerAction(AbstractAction action, int status) {
        actions.add(action);
        if (!enabledActions.containsKey(status)) {
            enabledActions.put(status, new HashSet<AbstractAction>());
        }
        enabledActions.get(status).add(action);
        return action;
    }

    protected AbstractAction registerAction(AbstractAction action, int status1, int status2) {
        registerAction(action, status1);
        registerAction(action, status2);
        return action;
    }

    protected AbstractAction registerAction(AbstractAction action, int status1, int status2, int status3) {
        registerAction(action, status1, status2);
        registerAction(action, status3);
        return action;
    }

    protected AbstractAction registerAction(AbstractAction action, int status1, int status2, int status3, int status4) {
        registerAction(action, status1, status2);
        registerAction(action, status3, status4);
        return action;
    }
}

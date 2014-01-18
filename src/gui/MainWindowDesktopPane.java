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
package gui;

import data.DataKairos;
import data.MyConstants;
import gui.DatosEditor.DataGUIInterface;
import gui.HorarioEditor.JIntHorarioEditor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class MainWindowDesktopPane extends AbstractMainWindow {

    private final ArrayList<AbstractAction> actionsViewFrame;
    private JMenuItem creaPDFHojaFirmaMenuItem;
    private JMenuItem newMenuItem;
    private AbstractAction tileAction;
    private final ArrayList<JInternalFrame> frames;
    private JMenu toolsMenu;
    private JMenu windowMenu;
    private AbstractAction tileHorariosAction;
    private AbstractAction tileDocenciaAction;
    private JMenuItem redoMenuItem;
    private JMenuItem undoMenuItem;

    /**
     * Creates new form MainWindow
     *
     * @throws Exception
     */
    public MainWindowDesktopPane() throws Exception {
        super();
        frames = new ArrayList<JInternalFrame>();
        actionsViewFrame = new ArrayList<AbstractAction>();
        initComponents();
        jDesktopPane.setBackground(MyConstants.BACKGROUND_APP_COLOR);
        createInternalFrames();
        addListeners();
        setProjectStatus(DataKairos.STATUS_NO_PROJECT);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Código del widget">                          
    private void initComponents() {

        jDesktopPane = new JDesktopPane();
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        creaAccionesEspecificas();
        createJMenus();

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jDesktopPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jDesktopPane, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
        );

        pack();
    }

    /**
     *
     * @throws IllegalArgumentException
     */
    protected void createJMenus() throws IllegalArgumentException {

        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        editMenu = new javax.swing.JMenu();
        undoMenuItem = new javax.swing.JMenuItem();
        redoMenuItem = new javax.swing.JMenuItem();
        newMenuItem = new javax.swing.JMenuItem();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        importXMLMenuItem = new javax.swing.JMenuItem();
        exportXMLMenuItem = new javax.swing.JMenuItem();
        creaPDFMenuItem = new javax.swing.JMenuItem();
        creaPDFHojaFirmaMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        toolsMenu = new javax.swing.JMenu();
        windowMenu = new javax.swing.JMenu();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        editMenu.setMnemonic('E');
        editMenu.setText("Editar");

        viewMenu = new javax.swing.JMenu();
        viewMenu.setMnemonic('V');
        viewMenu.setText("Ver");

        fileMenu.setMnemonic('A');
        fileMenu.setText("Archivo");

        windowMenu.setMnemonic('n');
        windowMenu.setText("Ventanas");

        toolsMenu.setMnemonic('H');
        toolsMenu.setText("Herramientas");

        newMenuItem.setAction(newProjectAction);
        fileMenu.add(newMenuItem);

        openMenuItem.setAction(cargarProyectoAction);
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        fileMenu.add(openMenuItem);

        saveMenuItem.setAction(guardarProyectoAction);
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileMenu.add(saveMenuItem);

        fileMenu.add(guardarProyectoComoAction);

        fileMenu.add(importarXMLAction);

        fileMenu.add(exportarXMLAction);

        toolsMenu.add(creaPDFAction);

        toolsMenu.add(creaPDFHojasDeFirmaAction);

        toolsMenu.add(buscaHorasLibresAction);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.setAction(exitAction);

        fileMenu.add(exitMenuItem);

        undoMenuItem.setAction(undoCommandAction);
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        editMenu.add(undoMenuItem);

        redoMenuItem.setAction(redoCommandAction);
        redoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        editMenu.add(redoMenuItem);

        JMenuItem tileMenuItem = new JMenuItem(tileAction);
        windowMenu.add(tileMenuItem);
        windowMenu.add(tileHorariosAction);
        windowMenu.add(tileDocenciaAction);
        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(viewMenu);
        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentMenuItem.setMnemonic('c');
        contentMenuItem.setText("Ayuda");
        helpMenu.add(contentMenuItem);

        aboutMenuItem.setMnemonic('S');
        aboutMenuItem.setText("Sobre este programa...");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JDlgAbout dlg = new JDlgAbout(null);
                dlg.setLocationRelativeTo(null);
                dlg.setVisible(true);
            }
        });
        helpMenu.add(aboutMenuItem);

        menuBar.add(toolsMenu);
        menuBar.add(windowMenu);
        menuBar.add(helpMenu);
    }

    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JMenuItem contentMenuItem;
    private javax.swing.JMenuItem copyMenuItem;
    private javax.swing.JMenuItem cutMenuItem;
    private javax.swing.JMenuItem deleteMenuItem;
    private javax.swing.JMenu editMenu;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenu helpMenu;
    private JDesktopPane jDesktopPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pasteMenuItem;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;

    private JMenu viewMenu;
    private JMenuItem importXMLMenuItem;
    private JMenuItem exportXMLMenuItem;
    private JMenuItem creaPDFMenuItem;
    // End of variables declaration                   

    /**
     *
     * @param title
     * @param tab
     */
    @Override
    protected void addTab(String title, final JInternalFrame tab, int accel) {
        frames.add(tab);
        tab.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        jDesktopPane.add(tab);
        tab.setVisible(false);
        tab.setClosable(true);
//        tab.setResizable(true);
        tab.setMaximizable(true);
        tab.setIconifiable(true);
        tab.setTitle(title);
        DataGUIInterface d = (DataGUIInterface) tab;
        d.setMainWindow(this);
        listaTabs.add(tab);
        AbstractAction viewFrameAction = new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!tab.isVisible()) {
                    tab.setVisible(true);
                    ((DataGUIInterface) tab).updateData();
                } else {
                    if (tab.isSelected()) {
                        tab.setVisible(false);
                    } else {
                        //Triquiñuela para poner una ventana encima
                        tab.setVisible(false);
                        tab.setVisible(true);
                    }
                }

            }
        };
        registerAction(viewFrameAction, DataKairos.STATUS_PROJECT_SOLUTION, DataKairos.STATUS_PROJECT_NO_SOLUTION, DataKairos.STATUS_COMPUTING_SOLUTION);
        actionsViewFrame.add(viewFrameAction);
        viewFrameAction.putValue(Action.NAME, title);
        JMenuItem viewFrameMenuItem = new JMenuItem(viewFrameAction);
        viewFrameMenuItem.setAccelerator(KeyStroke.getKeyStroke(accel, ActionEvent.CTRL_MASK));
        viewMenu.add(viewFrameMenuItem);

    }

    /**
     *
     * @param dataif
     */
    @Override
    public void switchToComponent(DataGUIInterface dataif) {
    }

//    /**
//     *
//     * @param status
//     */
//    @Override
//    public final void setProjectStatus(int status) {
//        super.setProjectStatus(status);
//        switch (status) {
//            case DataKairos.STATUS_NO_PROJECT: {
//                for (AbstractAction ac : actionsViewFrame) {
//                    ac.setEnabled(false);
//                }
//                break;
//            }
//            case DataKairos.STATUS_PROJECT_NO_SOLUTION: {
//                for (AbstractAction ac : actionsViewFrame) {
//                    ac.setEnabled(true);
//                }
//                break;
//            }
//            case DataKairos.STATUS_PROJECT_SOLUTION: {
//                for (AbstractAction ac : actionsViewFrame) {
//                    ac.setEnabled(true);
//                }
//                break;
//            }
//        }
//    }
    public void creaAccionesEspecificas() {

        //<editor-fold defaultstate="collapsed" desc="TileAction">
        class TileAction extends AbstractAction {

            private JDesktopPane desk; // the desktop to work with

            public TileAction(JDesktopPane desk) {
                super("Mosaico");
                this.desk = desk;
            }

            @Override
            public void actionPerformed(ActionEvent ev) {
                // How many frames do we have?
                JInternalFrame[] allframes = desk.getAllFrames();
                ArrayList<JInternalFrame> visibleFrames = new ArrayList<JInternalFrame>();
                for (JInternalFrame fr : allframes) {
                    if ((fr.isVisible()) && (fr.isResizable())) {
                        //TODO: Mosaico no funciona para frames maximizados
                        visibleFrames.add(fr);
                    }
                }
                tileFrames(visibleFrames);
            }

        }
//</editor-fold>

        //<editor-fold defaultstate="collapsed" desc="TileActionHorarios">
        class TileHorariosAction extends AbstractAction {

            private JDesktopPane desk; // the desktop to work with

            public TileHorariosAction(JDesktopPane desk) {
                super("Mosaico de horarios");
                this.desk = desk;
            }

            @Override
            public void actionPerformed(ActionEvent ev) {
                // How many frames do we have?
                ArrayList<JInternalFrame> visibleFrames = new ArrayList<JInternalFrame>();
                for (JIntHorarioEditor fr : horarioEditorMaster.getEditors()) {
                    fr.setVisible(true);
                    visibleFrames.add(fr);
                }
                tileFrames(visibleFrames);
            }
        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="TileDocenciaAction">
        class TileDocenciaAction extends AbstractAction {

            private JDesktopPane desk; // the desktop to work with

            public TileDocenciaAction(JDesktopPane desk) {
                super("Mosaico de profesores y asignaturas");
                this.desk = desk;
            }

            @Override
            public void actionPerformed(ActionEvent ev) {
                // How many frames do we have?
                ArrayList<JInternalFrame> visibleFrames = new ArrayList<JInternalFrame>();
                jIntTreeProfesores.setVisible(true);
                visibleFrames.add(jIntTreeProfesores);
                jIntTreeAsignaturas.setVisible(true);
                visibleFrames.add(jIntTreeAsignaturas);
                tileFrames(visibleFrames);
            }
        }
//</editor-fold>
        tileAction = new TileAction(jDesktopPane);
        registerAction(tileAction, DataKairos.STATUS_PROJECT_SOLUTION, DataKairos.STATUS_PROJECT_NO_SOLUTION, DataKairos.STATUS_COMPUTING_SOLUTION);

        tileHorariosAction = new TileHorariosAction(jDesktopPane);
        registerAction(tileHorariosAction, DataKairos.STATUS_PROJECT_SOLUTION, DataKairos.STATUS_COMPUTING_SOLUTION);

        tileDocenciaAction = new TileDocenciaAction(jDesktopPane);
        registerAction(tileDocenciaAction, DataKairos.STATUS_PROJECT_SOLUTION, DataKairos.STATUS_PROJECT_NO_SOLUTION, DataKairos.STATUS_COMPUTING_SOLUTION);
    }

    protected void tileFrames(ArrayList<JInternalFrame> visibleFrames) {
        int count = visibleFrames.size();
        if (count > 0) {
            // Determine the necessary grid size
            int sqrt = (int) Math.sqrt(count);
            int rows = sqrt;
            int cols = sqrt;
            if (rows * cols < count) {
                cols++;
                if (rows * cols < count) {
                    rows++;
                }
            }
            // Define some initial values for size & location.
            Dimension size = jDesktopPane.getSize();
            int w = size.width / cols;
            int h = size.height / rows;
            int x = 0;
            int y = 0;
            // Iterate over the frames, deiconifying any iconified frames and then
            // relocating & resizing each.
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols && ((i * cols) + j < count); j++) {
                    JInternalFrame f = visibleFrames.get((i * cols) + j);
                    if (!f.isClosed() && f.isIcon()) {
                        try {
                            f.setIcon(false);
                        } catch (PropertyVetoException ignored) {
                        }
                    }

                    jDesktopPane.getDesktopManager().resizeFrame(f, x, y, w, h);
                    x += w;
                }
                y += h; // start the next row
                x = 0;
            }
        }
    }

}

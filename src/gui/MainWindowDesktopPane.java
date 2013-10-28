/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import data.MyConstants;
import gui.DatosEditor.DataGUIInterface;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 *
 * @author david
 */
public class MainWindowDesktopPane extends AbstractMainWindow {

    

    /**
     * Creates new form MainWindow
     *
     * @throws Exception
     */
    public MainWindowDesktopPane() throws Exception {
        super();
        initComponents();
        jDesktopPane.setBackground(MyConstants.BACKGROUND_APP_COLOR);
        createInternalFrames();
        registraListeners();
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
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        openMenuItem = new javax.swing.JMenuItem();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        importXMLMenuItem = new javax.swing.JMenuItem();
        exportXMLMenuItem = new javax.swing.JMenuItem();
        creaPDFMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        editMenu = new javax.swing.JMenu();
        cutMenuItem = new javax.swing.JMenuItem();
        copyMenuItem = new javax.swing.JMenuItem();
        pasteMenuItem = new javax.swing.JMenuItem();
        deleteMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        contentMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        viewMenu = new javax.swing.JMenu();
        viewMenu.setMnemonic('V');
        viewMenu.setText("Ver");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        fileMenu.setMnemonic('f');
        fileMenu.setText("File");

        openMenuItem.setAction(cargarProyectoAction);
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));

        fileMenu.add(openMenuItem);

        saveMenuItem.setAction(guardarProyectoAction);
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setMnemonic('a');
        saveAsMenuItem.setText("Save As ...");
        saveAsMenuItem.setDisplayedMnemonicIndex(5);
        saveAsMenuItem.setAction(guardarProyectoComoAction);
        fileMenu.add(saveAsMenuItem);

        importXMLMenuItem.setAction(importarXMLAction);
        fileMenu.add(importXMLMenuItem);

        exportXMLMenuItem.setAction(exportarXMLAction);
        fileMenu.add(exportXMLMenuItem);

        creaPDFMenuItem.setAction(creaPDFAction);
        fileMenu.add(creaPDFMenuItem);

        exitMenuItem.setMnemonic('x');
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        editMenu.setMnemonic('e');
        editMenu.setText("Edit");

        cutMenuItem.setMnemonic('t');
        cutMenuItem.setText("Cut");
        editMenu.add(cutMenuItem);

        copyMenuItem.setMnemonic('y');
        copyMenuItem.setText("Copy");
        editMenu.add(copyMenuItem);

        pasteMenuItem.setMnemonic('p');
        pasteMenuItem.setText("Paste");
        editMenu.add(pasteMenuItem);

        deleteMenuItem.setMnemonic('d');
        deleteMenuItem.setText("Delete");
        editMenu.add(deleteMenuItem);

        menuBar.add(editMenu);

        menuBar.add(viewMenu);
        helpMenu.setMnemonic('h');
        helpMenu.setText("Help");

        contentMenuItem.setMnemonic('c');
        contentMenuItem.setText("Contents");
        helpMenu.add(contentMenuItem);

        aboutMenuItem.setMnemonic('a');
        aboutMenuItem.setText("About");
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

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
    }// </editor-fold>                        

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(0);
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

    @Override
    protected void addTab(String title, final JInternalFrame tab) {
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
        viewFrameAction.putValue(Action.NAME, title);
        viewMenu.add(viewFrameAction);

    }

    @Override
    public void switchToComponent(DataGUIInterface dataif) {
    }

}

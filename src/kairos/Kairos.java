/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kairos;

import gui.MainWindowDesktopPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;

/**
 *
 * @author david
 */
public class Kairos {

    /**
     * @param args the command line arguments
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        if (true) {
          // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//           for (UIManager.LookAndFeelInfo in:UIManager.getInstalledLookAndFeels())
//           {
//               System.out.println(in.getName());
//           }
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
//           try {
//               UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//           } catch (ClassNotFoundException classNotFoundException) {
//           } catch (InstantiationException instantiationException) {
//           } catch (IllegalAccessException illegalAccessException) {
//           } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
//           }
//        MainWindowTabbed mainWindow = new MainWindowTabbed();
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    MainWindowDesktopPane mainWindow;
                    try {
                        mainWindow = new MainWindowDesktopPane();
                        mainWindow.setLocationRelativeTo(null);
                        mainWindow.setVisible(true);
                    } catch (Exception ex) {
                        Logger.getLogger(Kairos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

        } else {
        }
    }
}

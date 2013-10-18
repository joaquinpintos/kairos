/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kairos;

import data.profesores.Profesor;
import gui.MainWindowDesktopPane;
import gui.MainWindowTabbed;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import testers.AsigTester;
import testers.HashTester;
import testers.ITextTester;

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
       if (true){
          // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//           for (UIManager.LookAndFeelInfo in:UIManager.getInstalledLookAndFeels())
//           {
//               System.out.println(in.getName());
//           }
           //UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
           try {
               UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
           } catch (ClassNotFoundException classNotFoundException) {
           } catch (InstantiationException instantiationException) {
           } catch (IllegalAccessException illegalAccessException) {
           } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
           }
        MainWindowTabbed mainWindow = new MainWindowTabbed();
//           MainWindowDesktopPane mainWindow=new MainWindowDesktopPane();
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setVisible(true);
       }
       else{
           ITextTester asig = new ITextTester();
           asig.mcdTester();
       }
    }
}

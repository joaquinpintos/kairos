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
package kairos;

import gui.MainWindowDesktopPane;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import testers.IteratorTester;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
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
            IteratorTester t=new IteratorTester();
            t.test();
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.helpers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author david
 */
public class FilterComboBox extends JComboBox {

    private List<String> array;

    /**
     *
     */
    public FilterComboBox() {
        super();
    }

    /**
     *
     */
    public void createFilterData() {
        array = new ArrayList<String>();
        int fin=this.getItemCount();
        for (int n=0;n<fin;n++)
        {
            array.add(this.getItemAt(n).toString());
        }

        
        this.setEditable(true);
        final JTextField textfield = (JTextField) this.getEditor().getEditorComponent();
        textfield.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent ke) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        comboFilter(textfield.getText());
                    }
                });
            }
        });

    }

    /**
     *
     * @param enteredText
     */
    public void comboFilter(String enteredText) {
        List<String> filterArray = new ArrayList<String>();
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i).toLowerCase().contains(enteredText.toLowerCase())) {
                filterArray.add(array.get(i));
            }
        }
        if (filterArray.size() > 0) {
            this.setModel(new DefaultComboBoxModel(filterArray.toArray()));
            this.setSelectedItem(enteredText);
            this.showPopup();
        } else {
            this.hidePopup();
        }
    }

    /* Testing Codes */
}
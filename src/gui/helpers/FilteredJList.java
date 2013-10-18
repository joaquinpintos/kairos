/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.helpers;

import gui.helpers.FilteredJList.FilterModel;
import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

// inner class provides filter-by-keystroke field 

// inner class provides filter-by-keystroke field 
/**
 *
 * @author david
 */



public class FilteredJList extends JList {

    private FilterField filterField;
    private int DEFAULT_FIELD_WIDTH = 20;

    /**
     *
     */
    public FilteredJList() {
        super();
        setModel(new FilterModel());
        filterField = new FilterField(DEFAULT_FIELD_WIDTH, this);

    }

    @Override
    public final void setModel(ListModel m) {
        if (!(m instanceof FilterModel)) {
            throw new IllegalArgumentException();
        }
        super.setModel(m);
    }

    /**
     *
     * @param o
     */
    public void addItem(Object o) {
        FilterModel mod = (FilterModel) getModel();
        mod.addElement(o);
    }

    /**
     *
     * @return
     */
    public JTextField getFilterField() {
        return filterField;
    }

    class FilterModel extends AbstractListModel {

        ArrayList items;
        ArrayList filterItems;

        public FilterModel() {
            super();
            items = new ArrayList();
            filterItems = new ArrayList();
        }

        @Override
        public Object getElementAt(int index) {
            if (index < filterItems.size()) {
                return filterItems.get(index);
            } else {
                return null;
            }
        }

        @Override
        public int getSize() {
            return filterItems.size();
        }

        public void addElement(Object o) {
            items.add(o);
            refilter();

        }

        public void refilter() {
            filterItems.clear();
            String term = getFilterField().getText();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i).toString().indexOf(term, 0) != -1) {
                    filterItems.add(items.get(i));
                }
            }
            fireContentsChanged(this, 0, getSize());
        }
    }
    // FilterField inner class listed below
}
// inner class provides filter-by-keystroke field 
class FilterField extends JTextField implements DocumentListener {

    private FilteredJList listFilteredJList;

    public FilterField(int width, FilteredJList listFilteredJList) {
        super(width);
        getDocument().addDocumentListener(this);
        this.listFilteredJList = listFilteredJList;
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        FilterModel mod = (FilterModel) listFilteredJList.getModel();
        mod.refilter();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        FilterModel mod = (FilterModel) listFilteredJList.getModel();
        mod.refilter();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        FilterModel mod = (FilterModel) listFilteredJList.getModel();
        mod.refilter();
    }
}

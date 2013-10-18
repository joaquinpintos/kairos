/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.profesores;

import data.asignaturas.DocenciaItem;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author david
 */
public class ListModelDocenciaAsignada implements ListModel{

    private ArrayList<DocenciaItem> docencia;

    /**
     *
     * @param docencia
     */
    public ListModelDocenciaAsignada(ArrayList<DocenciaItem> docencia) {
        this.docencia = docencia;
    }

    /**
     *
     */
    public ListModelDocenciaAsignada() {
        docencia=new ArrayList<DocenciaItem>();
    }
    
    /**
     *
     * @param profesor
     */
    public ListModelDocenciaAsignada(Profesor profesor) {
        docencia=profesor.getDocencia();
    }
    

    
    @Override
    public int getSize() {
        return docencia.size();
    }

    @Override
    public Object getElementAt(int index) {
        return docencia.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
    }

    /**
     *
     * @return
     */
    public ArrayList<DocenciaItem> getDocencia() {
        return docencia;
    }

    /**
     *
     * @param docencia
     */
    public void setDocencia(ArrayList<DocenciaItem> docencia) {
        this.docencia = docencia;
    }
    
}

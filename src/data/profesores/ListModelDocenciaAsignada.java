/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.profesores;

import data.asignaturas.Tramo;
import java.util.ArrayList;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListModelDocenciaAsignada implements ListModel{

    private ArrayList<Tramo> docencia;

    /**
     *
     * @param docencia
     */
    public ListModelDocenciaAsignada(ArrayList<Tramo> docencia) {
        this.docencia = docencia;
    }

    /**
     *
     */
    public ListModelDocenciaAsignada() {
        docencia=new ArrayList<Tramo>();
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
    public ArrayList<Tramo> getDocencia() {
        return docencia;
    }

    /**
     *
     * @param docencia
     */
    public void setDocencia(ArrayList<Tramo> docencia) {
        this.docencia = docencia;
    }
    
}

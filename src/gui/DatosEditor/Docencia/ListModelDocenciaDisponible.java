/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Docencia;

import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.DataAsignaturas;
import data.asignaturas.DocenciaItem;
import data.asignaturas.Grupo;
import java.util.Vector;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author david
 */
public class ListModelDocenciaDisponible implements ListModel {

    private DataAsignaturas dataAsignaturas;
    Vector<DocenciaItem> docencia;

    /**
     *
     * @param dataAsignaturas
     */
    public ListModelDocenciaDisponible(DataAsignaturas dataAsignaturas) {
        super();
        this.dataAsignaturas = dataAsignaturas;
        docencia = new Vector<DocenciaItem>();
        populateData();
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

    private void populateData() {
        for (Carrera carrera : dataAsignaturas.getCarreras()) {
            for (Curso curso : carrera.getCursos()) {
                for (Asignatura asignatura : curso.getAsignaturas()) {
                    for (Grupo grupo : asignatura.getGrupos().getGrupos()) {
                        docencia.add(new DocenciaItem(carrera, curso, asignatura, grupo));
                    }
                }
            }
        }
    }
}

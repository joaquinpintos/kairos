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
import data.asignaturas.Tramo;
import javax.swing.DefaultComboBoxModel;

/**
 * Modelo para un objeto combo de los grupos en los que se impartirá docencia
 *
 * @author david
 */
public class ComboModelDocencia extends DefaultComboBoxModel {

    private DataAsignaturas dataAsignaturas;

    /**
     *
     * @param dataAsignaturas
     */
    public ComboModelDocencia(DataAsignaturas dataAsignaturas) {
        super();
        this.dataAsignaturas = dataAsignaturas;
        populateData();
    }

    private void populateData() {
        for (Carrera carrera : dataAsignaturas.getCarreras()) {
            for (Curso curso : carrera.getCursos()) {
                for (Asignatura asignatura : curso.getAsignaturas()) {
                    for (Grupo grupo : asignatura.getGrupos().getGrupos()) {
                        for (Tramo tr : grupo.getTramosGrupoCompleto().getTramos()) {
                            this.addElement(new DocenciaItem(tr));
                        }
                    }
                }
            }
        }
    }
}

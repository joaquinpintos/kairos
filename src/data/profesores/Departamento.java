/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.profesores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase Departamento. Contiene una lista de profesores (Vector)
 *
 * @author david
 */
public class Departamento implements Serializable {

    private String nombre;
    private ArrayList<Profesor> profesores;
    DataProfesores parent;

    /**
     *
     * @param nombre
     */
    public Departamento(String nombre) {
        this.nombre = nombre;
        profesores = new ArrayList<Profesor>();
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public ArrayList<Profesor> getProfesores() {
        return profesores;
    }

    /**
     *
     * @param pro
     */
    public void addProfesor(Profesor pro) {
        this.profesores.add(pro);
        pro.setDepartamento(this);
        ordenaProfesores();
        setDirty(true);
    }

    /**
     *
     * @param pro
     */
    public void remove(Profesor pro) {
        //Antes de borrarlo miro los grupos a los que tenga asignada docencia
        //y los libero
        pro.clearDocencia();
        this.profesores.remove(pro);
        pro.setDepartamento(null);
        setDirty(true);
    }

    void removeSinBorrarDocencia(Profesor pro) {
        this.profesores.remove(pro);
        pro.setDepartamento(null);
        setDirty(true);
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    private void ordenaProfesores() {
        Collections.sort((List) profesores);
    }

    /**
     *
     * @param parent
     */
    public void setParent(DataProfesores parent) {
        this.parent = parent;
    }

    /**
     *
     * @param value
     */
    public void setDirty(boolean value) {
        try {
            parent.setDirty(value);
        } catch (NullPointerException e) {
        }
    }
}

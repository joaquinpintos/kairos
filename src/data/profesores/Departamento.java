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
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class Departamento implements Serializable {

    private static final long serialVersionUID = 27112013L;
    private String nombre;
    private final ArrayList<Profesor> profesores;
    DataProfesores parent;

    /**
     *
     * @param nombre
     */
    public Departamento(String nombre) throws IllegalArgumentException {
        setNombre(nombre);
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
    public final void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre.contains("@")) {
            throw new IllegalArgumentException("El nombre del departamento no puede contener el carácter @");
        } else {
            this.nombre = nombre;
        }
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public ArrayList<Profesor> getProfesores() {
        return profesores;
    }

    public boolean addProfesor(Profesor e) {
        return profesores.add(e);
    }


    /**
     *
     * @param pro
     */
    public void createProfesor(Profesor pro) {
        this.profesores.add(pro);
        pro.setDepartamento(this);
        ordenaProfesores();
        setDirty(true);
    }

    public boolean removeProfesor(Object o) {
        return profesores.remove(o);
    }

    /**
     *
     * @param pro
     */
    public void deleteProfesor(Profesor pro) {
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
        if (parent != null) {
            parent.setDirty(value);
        }
    }
}

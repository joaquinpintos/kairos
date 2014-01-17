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
    public void createProfesorOLD(Profesor pro) {
        this.profesores.add(pro);
        pro.setDepartamento(this);
        ordenaProfesores();
    }

    public boolean removeProfesor(Object o) {
        return profesores.remove(o);
    }

   
    @Override
    public String toString() {
        return this.nombre;
    }

    public void ordenaProfesores() {
        Collections.sort((List) profesores);
    }

    /**
     *
     * @param parent
     */
    public void setParent(DataProfesores parent) {
        this.parent = parent;
    }

}

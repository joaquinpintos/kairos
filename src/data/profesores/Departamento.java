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

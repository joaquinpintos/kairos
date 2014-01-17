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

import data.asignaturas.Tramo;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class Profesor implements Comparable<Profesor>, Serializable {

    private static final long serialVersionUID = 27112013L;
    private String nombre;
    private String apellidos;
    private Departamento departamento;
    private final ArrayList<Tramo> docencia;

    /**
     *
     * @param nombre
     * @param apellidos
     * @param nombreCorto
     */
    public Profesor(String nombre, String apellidos, String nombreCorto) throws IllegalArgumentException {
        this(nombre, apellidos, nombreCorto, null);
    }

    /**
     *
     * @param nombre
     * @param apellidos
     * @param nombreCorto
     * @param departamento
     */
    public Profesor(String nombre, String apellidos, String nombreCorto, Departamento departamento) throws IllegalArgumentException {
        setNombre(nombre);
        this.departamento = departamento;
        setApellidos(apellidos);
        this.nombreCorto = nombreCorto;
        docencia = new ArrayList<Tramo>();
    }

    /**
     *
     * @return
     */
    public Departamento getDepartamento() {
        return departamento;
    }
    /**
     *
     * @param departamento
     */
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        if (nombre != null) {
            return nombre;
        } else {
            return "";
        }
    }

    /**
     *
     * @param nombre
     */
    public final void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre.contains("@")) {
            throw new IllegalArgumentException("El nombre del profesor no puede contener el carácter @");
        } else {
            this.nombre = nombre;
        }
    }

    @Override
    public String toString() {
        return apellidos + ", " + nombre;
    }

    /**
     *
     * @return
     */
    public String getApellidos() {
        if (apellidos != null) {
            return apellidos;
        } else {
            return "";
        }
    }

    /**
     *
     * @param apellidos
     */
    public final void setApellidos(String apellidos) {
        if (apellidos.contains("@")) {
            throw new IllegalArgumentException("Los apellidos del profesor no pueden contener el carácter @");
        } else {
            this.apellidos = apellidos;
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<Tramo> getDocencia() {
        return docencia;
    }

    public void addDocencia(Tramo docenciaItem) {
        if (docenciaItem != null) {
            this.docencia.add(docenciaItem);
        }
    }

    /**
     *
     * @param docenciaItem
     */
    public void removeDocencia(Tramo docenciaItem) {
        this.docencia.remove(docenciaItem);
    }


    /**
     *
     * @return
     */
    public double getHorasDocencia() {
        double suma = 0;
        for (Tramo d : docencia) {
            suma += d.getMinutos();
        }
        return suma / 60;
    }

    /**
     *
     */
    public void ordenaDocencia() {
        Collections.sort((List) docencia);
    }

    @Override
    public int compareTo(Profesor o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 47 * hash + (this.apellidos != null ? this.apellidos.hashCode() : 0);
        hash = 47 * hash + (this.departamento != null ? this.departamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Profesor other = (Profesor) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        if ((this.apellidos == null) ? (other.apellidos != null) : !this.apellidos.equals(other.apellidos)) {
            return false;
        }
        if (this.departamento != other.departamento && (this.departamento == null || !this.departamento.equals(other.departamento))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @return
     */
    public String hash() {
        return this.nombre + "@" + this.apellidos;
    }
    private String nombreCorto;

    /**
     * Get the value of nombreCorto
     *
     * @return the value of nombreCorto
     */
    public String getNombreCorto() {
        return nombreCorto;
    }

    /**
     * Set the value of nombreCorto
     *
     * @param nombreCorto new value of nombreCorto
     */
    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
    }


    public void copyBasicValuesFrom(Profesor newData) {
        this.nombre = newData.nombre;
        this.apellidos = newData.apellidos;
        this.nombreCorto = newData.nombreCorto;
    }
}

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
package data.aulas;

import java.io.Serializable;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class Aula implements Serializable {

    private static final long serialVersionUID = 27112013L;
    String nombre;
    private final ListaAsignaciones asignacionesMañana;
    private final ListaAsignaciones asignacionesTarde;
    private DataAulas parent;

    /**
     *
     * @param nombre
     */
    public Aula(String nombre) throws IllegalArgumentException{
        setNombre(nombre);
        asignacionesMañana = new ListaAsignaciones(false, 0, this);
        asignacionesTarde = new ListaAsignaciones(true, 1, this);
    }

    /**
     *
     * @return
     */
    public boolean tieneAsignaciones() {
        return !(asignacionesMañana.isEmpty() && asignacionesTarde.isEmpty());
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
    public final void setNombre(String nombre) throws IllegalArgumentException{
        if (nombre.contains("@")) {
            throw  new IllegalArgumentException("El nombre del aula no puede contener el carácter @");
        } else {
            this.nombre = nombre;
        }
    }

    @Override
    public String toString() {
        String resul = "";
        resul += "Aula " + this.nombre;
        return resul;
    }

    /**
     *
     * @param tarde
     * @return
     */
    public String getHash(boolean tarde) {
        String resul = this.nombre + "@";
        resul += tarde ? "T" : "M";
        return resul;
    }

    /**
     *
     * @return
     */
    public ListaAsignaciones getAsignacionesMañana() {
        return asignacionesMañana;
    }

    /**
     *
     * @return
     */
    public ListaAsignaciones getAsignacionesTarde() {
        return asignacionesTarde;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
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
        final Aula other = (Aula) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param parent
     */
    public void setParent(DataAulas parent) {
        this.parent = parent;
    }


    public void copyBasicValuesFrom(Aula data) {
        this.nombre=data.getNombre();
    }
}

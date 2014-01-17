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
package data.asignaturas;

import data.aulas.Aula;
import java.io.Serializable;

/**
 * Representa un grupo de alumnos, con sus tramos asociados. Cada grupo se
 * representa por un nombre (1,2,3, A, B, C,...) y pertenece a una
 * {@link Asignatura}. A su vez cada grupo contiene una serie de tramos de
 * docencia, almacenados en un objeto del tipo {@link GrupoTramos}.
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class Grupo implements Serializable, Comparable<Grupo>, Teachable {

    private static final long serialVersionUID = 27112013L;
    private String nombre;
    private final GrupoTramos tramosGrupoCompleto;
    private Asignatura parent;
    private boolean tarde;
    private Aula aulaAsignada;
    private boolean algunoSinAula;
    private boolean algunoSinDocente;

    /**
     * Constructor. Devuelve un grupo nuevo con nombre dado.
     *
     * @param nombre
     */
    public Grupo(String nombre) throws IllegalArgumentException {
        setNombre(nombre);
        this.tramosGrupoCompleto = new GrupoTramos(this);
        this.tarde = false;
        algunoSinAula = true;
    }

    /**
     * Devuelve el nombre
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre del grupo. No puede contener la letra @
     *
     * @param nombre
     */
    public final void setNombre(String nombre) throws IllegalArgumentException {
        if (!nombre.contains("@")) {
            this.nombre = nombre;
        } else {
            throw new IllegalArgumentException("El nombre del grupo no puede contener @");
        }
    }

    /**
     * Devuelve objeto {@link GrupoTramos} para los tramos de grupo completo.
     *
     * @return
     */
    public GrupoTramos getTramosGrupoCompleto() {
        return tramosGrupoCompleto;
    }

    @Override
    public String toString() {
        String salida;
        salida = "Grupo " + this.nombre + ":  ";
        salida += tramosGrupoCompleto.toString();
        return salida;
    }

    public String toStringSinTotales() {
        String salida;
        salida = "Grupo " + this.nombre;
        return salida;
    }

    /**
     * @return Asignatura a la que pertenece.
     */
    public Asignatura getParent() {
        return parent;
    }

    /**
     * Cambia la asignatura a la que pertenece.
     *
     * @param asignatura
     */
    public void setParent(Asignatura asignatura) {
        this.parent = asignatura;
    }

    /**
     *
     * @return Total de horas de docencia del grupo, sumando todos los tramos.
     */
    public double getTotalHoras() {
        return tramosGrupoCompleto.getTotalHoras();

    }


    /**
     *
     * @return True si el grupo da sus clases por la tarde. False en caso
     * contrario.
     */
    public boolean isTarde() {
        return tarde;
    }

    /**
     * Establece si el grupo es de tarde o no.
     *
     * @param tarde True si el grupo da sus clases por la tarde. False en caso
     * contrario.
     */
    public void setTarde(boolean tarde) {
        this.tarde = tarde;
    }

    /**
     * Devuelve un hash que identifica de forma unívoca el {@link Grupo}, {@link Asignatura},
     * {@link Curso} y {@link Carrera} al que pertenece. Ejemplo:
     * Primaria@1@Matematicas@A
     *
     * @return Hash de la forma carrera@curso@asignatura@grupo
     */
    public String getHashCarreraGrupoCurso() {
        Asignatura a = this.getParent();
        Curso b = a.getParent();
        return b.getHash() + "@" + this.nombre;
    }

    /**
     * Devuelve una cadena formateada con el {@link Grupo},
     * {@link Curso} y {@link Carrera} al que pertenece. Ejemplo: Primaria -1º -
     * Grupo 1
     *
     * @return Cadena de la forma carrera - curso - grupo
     */
    public String getNombreConCarrera() {

        return this.getParent().getParent().getParent() + " - " + this.getParent().getParent() + " - Grupo " + this.getNombre();

    }

    /**
     * Devuelve una cadena formateada con el {@link Grupo},
     * {@link Curso} y {@link Carrera} al que pertenece. Ejemplo: G1 1º Primaria
     *
     * @return Cadena de la forma Ggrupo curso carrera
     */
    public String getNombreGrupoCursoYCarrera() {
        return "G" + this.getNombre() + " " + this.getParent().getParent().getNombre() + " " + this.getParent().getParent().getParent().toString();
    }


    @Override
    public int compareTo(Grupo o) {
        return this.nombre.compareTo(o.getNombre());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 89 * hash + (this.parent != null ? this.parent.hashCode() : 0);
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
        final Grupo other = (Grupo) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        if (this.parent != other.parent && (this.parent == null || !this.parent.equals(other.parent))) {
            return false;
        }
        return true;
    }



    @Override
    public boolean isAlgunoSinAula() {
        return algunoSinAula;
    }
     public boolean tieneAula() {
        return !algunoSinAula;
    }


    @Override
    public void setAlgunoSinAula(boolean value) {
        this.algunoSinAula = value;
    }

    public void copyBasicValuesFrom(Grupo grNew) {
        this.nombre=grNew.getNombre();
    }
     @Override
    public boolean isAlgunoSinDocente() {
        return algunoSinDocente;
    }

    @Override
    public void setAlgunoSinDocente(boolean value) {
        algunoSinDocente=value;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.DataProyectoListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class Curso implements Serializable, Comparable<Curso>, Teachable {

    private static final long serialVersionUID = 27112013L;
    private ArrayList<DataProyectoListener> listeners;
    private String nombre;
    private Carrera parent;
    private final ArrayList<Asignatura> asignaturas;
    int contaColor;
    private boolean algunoSinAula;
    private boolean algunoSinDocente;

    /**
     *
     * @param nombre
     */
    public Curso(String nombre) throws IllegalArgumentException {
        setNombre(nombre);
        asignaturas = new ArrayList<Asignatura>();
        contaColor = 0;
        algunoSinAula = false;
        algunoSinAula=false;
    }

    public boolean addAsignatura(Asignatura e) {
        return asignaturas.add(e);
    }


    public void ordenaAsignaturas() {
        Collections.sort(asignaturas);
    }

    public boolean removeAsignatura(Asignatura o) {
        return asignaturas.remove(o);
    }

    /**
     *
     * @return
     */
    public ArrayList<Asignatura> getAsignaturas() {
        return asignaturas;
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
            throw new IllegalArgumentException("El nombre del curso no puede contener el carácter @");
        } else {
            this.nombre = nombre;
        }
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public Carrera getParent() {
        return this.parent;
    }

    /**
     *
     * @param parent
     */
    public void setParent(Carrera parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "Curso " + nombre;
    }

    /**
     *
     * @return
     */
    public String getHash() {
        return this.getParent().getNombre() + "@" + this.nombre;
    }

    @Override
    public int compareTo(Curso o) {
        return this.nombre.compareTo(o.getNombre());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 37 * hash + (this.parent != null ? this.parent.hashCode() : 0);
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
        final Curso other = (Curso) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        if (this.parent != other.parent && (this.parent == null || !this.parent.equals(other.parent))) {
            return false;
        }
        return true;
    }

    void setDirty(boolean value) {
        if (parent != null) {
            parent.setDirty(value);
        }
    }


    /**
     *
     */
    public void updateAsigAulaStatus() {
        boolean resul = false;
        for (Asignatura asig : asignaturas) {
            if (asig.isAlgunoSinAula()) {
                resul = true;
                break;
            }
        }
        if (resul != algunoSinAula) {
            algunoSinAula = resul;
            //Actualizo hacia arriba
            getParent().updateAsigAulaStatus();
        }
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

    public void setTieneAula(boolean value) {
        this.algunoSinAula = !value;
    }

    @Override
    public boolean isAlgunoSinDocente() {
        return algunoSinDocente;
    }

    @Override
    public void setAlgunoSinDocente(boolean value) {
        algunoSinDocente = value;
    }

    public void copyBasicValuesFrom(Curso data) {
        this.nombre=data.getNombre();
    }
}

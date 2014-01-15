/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.DataProyectoListener;
import data.aulas.AulaMT;
import data.profesores.Profesor;
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

    /**
     *
     * @param nombre
     */
    public Curso(String nombre) throws IllegalArgumentException {
        setNombre(nombre);
        asignaturas = new ArrayList<Asignatura>();
        contaColor = 0;
        algunoSinAula = true;
    }

    public boolean addAsignatura(Asignatura e) {
        return asignaturas.add(e);
    }

    
    /**
     *
     * @param asig
     */
    public void crateAsignatura(Asignatura asig) {
        this.asignaturas.add(asig);
        asig.setCurso(this);
//        asig.setColorEnTablaDeHorarios(MyConstants.coloresAsignaturas[contaColor]);
//        contaColor++;
//        if (!(contaColor < MyConstants.coloresAsignaturas.length)) {
//            contaColor = 0;
//        }
        try {
            this.getParent().getParent().fireDataEvent(asig, DataProyectoListener.ADD);
        } catch (NullPointerException e) {
        }
        ordenaAsignaturas();
        setDirty(true);

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
     * @param obj
     * @param type
     */
    public void fireDataEvent(Object obj, int type) {
        getParent().fireDataEvent(obj, type);
    }

    /**
     *
     * @param aula
     */
    public void asignaAula(AulaMT aula) {
        for (Asignatura asig : asignaturas) {
            asig.asignaAula(aula);
        }
    }

    /**
     *
     */
    public void removeAula() {
        for (Asignatura asig : asignaturas) {
            asig.removeAula();
        }
    }

    /**
     *
     */
    public void updateAsigAulaStatus() {
        boolean resul = false;
        for (Asignatura asig : asignaturas) {
            if (asig.algunoSinAula()) {
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

    public boolean algunoSinAula() {
        return algunoSinAula;
    }
 public boolean tieneAula() {
        return !algunoSinAula;
    }
    public void setAlgunoSinAula(boolean value)
    {
        this.algunoSinAula=value;
    }

    public void setTieneAula(boolean value)
    {
        this.algunoSinAula=!value;
    }
    
}

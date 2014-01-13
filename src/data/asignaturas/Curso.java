/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import com.sun.java.accessibility.util.EventQueueMonitor;
import data.DataProyectoListener;
import data.MyConstants;
import data.aulas.AulaMT;
import data.profesores.Profesor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

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

    /**
     *
     * @param asig
     */
    public void addAsignatura(Asignatura asig) {
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
        Collections.sort(asignaturas);
        setDirty(true);

    }

    /**
     *
     * @param asig
     */
    public void removeAsignatura(Asignatura asig) {
        //TODO: BUG!! AL remover NO se elimina la docencia ni las aulas!!!
        asig.clearDocente();
        asig.clearAulasAsignadas();
        asig.removeAllGrupos();
        this.asignaturas.remove(asig);
        asig.setCurso(null);
        DataAsignaturas dataAsignaturas = this.getParent().getParent();
        try {
            dataAsignaturas.fireDataEvent(asig, DataProyectoListener.REMOVE);
        } catch (NullPointerException e) {
        }
        setDirty(true);
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
     * @param profesor
     */
    @Override
    public void setDocente(Profesor profesor) {
        for (Asignatura asig : asignaturas) {
            asig.setDocente(profesor);
        }
    }

    /**
     *
     */
    @Override
    public void removeDocente() {
        for (Asignatura asig : asignaturas) {
            asig.removeDocente();
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
    @Override
    public void asignaAula(AulaMT aula) {
        for (Asignatura asig : asignaturas) {
            asig.asignaAula(aula);
        }
    }

    /**
     *
     */
    @Override
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

    /**
     *
     * @return
     */
    public boolean algunoSinAula() {
        return algunoSinAula;
    }

    void removeAllAsignaturas() {
         ArrayList<Asignatura> asigClone = (ArrayList<Asignatura>) asignaturas.clone();
        for (Asignatura asig : asigClone) {
            removeAsignatura(asig);
        }
    }
    
}

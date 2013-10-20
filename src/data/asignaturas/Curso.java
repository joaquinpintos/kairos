/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.DataProyectoListener;
import data.MyConstants;
import data.profesores.Profesor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author david
 */
public class Curso implements Serializable, Comparable<Curso>, Teachable {

    private ArrayList<DataProyectoListener> listeners;
    private String nombre;
    private Carrera parent;
    private ArrayList<Asignatura> asignaturas;
    int contaColor;
    //Aquí guardo las asignaturas que tengan algún grupo no asignado.
    private HashSet<Asignatura> asignaturasNOasignadas;

    /**
     *
     * @param nombre
     */
    public Curso(String nombre) {
        this.nombre = nombre;
        asignaturas = new ArrayList<Asignatura>();
        contaColor = 0;
        asignaturasNOasignadas = new HashSet<Asignatura>();
    }

    /**
     *
     * @param asig
     */
    public void addAsignatura(Asignatura asig) {
        this.asignaturas.add(asig);
        asig.setCurso(this);
        asig.setColorEnTablaDeHorarios(MyConstants.coloresAsignaturas[contaColor]);
        contaColor++;
        if (!(contaColor < MyConstants.coloresAsignaturas.length)) {
            contaColor = 0;
        }
        updateEstadoAsignacion(asig);
        try {
            this.getParent().updateEstadoAsignacion(this);
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
        this.asignaturas.remove(asig);
        asig.setCurso(null);
        asignaturasNOasignadas.remove(asig);
        DataAsignaturas dataAsignaturas = this.getParent().getParent();
        try {
            this.getParent().updateEstadoAsignacion(this);
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
    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    /**
     *
     * @return
     */
    public boolean tieneGruposSinAsignar() {
        return !asignaturasNOasignadas.isEmpty();
    }

    /**
     *
     * @param asig
     */
    public void updateEstadoAsignacion(Asignatura asig) {
        if (asig.tieneGruposSinAsignar()) {
            asignaturasNOasignadas.add(asig);
        } else {
            asignaturasNOasignadas.remove(asig);
        }
        this.getParent().updateEstadoAsignacion(this);
    }

    @Override
    public int compareTo(Curso o) {
        return this.nombre.compareTo(o.getNombre());
    }

    void setDirty(boolean value) {
//       try {
        parent.setDirty(value);
//        } catch (NullPointerException e) {
//        }
    }

    @Override
    public void setDocente(Profesor profesor) {
        for (Asignatura asig : asignaturas) {
            asig.setDocente(profesor);
        }
    }

    @Override
    public void removeDocente() {
        for (Asignatura asig : asignaturas) {
            asig.removeDocente();
        }
    }
}

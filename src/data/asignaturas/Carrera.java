/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.profesores.Profesor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author david
 */
public class Carrera implements Serializable,Teachable  {

    private String nombre;
    private DataAsignaturas parent;
    private ArrayList<Curso> cursos;
    //Cursos que tienen alguna asignatura que tiene algun grupo no asignado.
    private HashSet<Curso> cursosNOAsignados;

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
     * @param nombre
     */
    public Carrera(String nombre) {
        this.nombre = nombre;
        this.cursos = new ArrayList<Curso>();
        this.cursosNOAsignados = new HashSet<Curso>();
    }

    /**
     *
     * @param curso
     */
    public void addCurso(Curso curso) {
        this.cursos.add(curso);
        curso.setParent(this);
        Collections.sort(cursos);
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public DataAsignaturas getParent() {
        return parent;
    }

    /**
     *
     * @param curso
     */
    public void removeCurso(Curso curso) {
        this.cursos.remove(curso);
        curso.setParent(null);
        setDirty(true);
    }

    @Override
    public String toString() {
        return nombre;
    }

//    public DataAsignaturas getParent() {
//        return parent;
//    }
//    
//    public void setParent(DataAsignaturas parent) {
//        this.parent = parent;
//    }
    /**
     *
     * @return
     */
    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    /**
     *
     * @return
     */
    public boolean tieneGruposSinAsignar() {
        return !cursosNOAsignados.isEmpty();
    }

    /**
     *
     * @param curso
     */
    public void updateEstadoAsignacion(Curso curso) {
        if (curso.tieneGruposSinAsignar()) {
            cursosNOAsignados.add(curso);
        } else {
            cursosNOAsignados.remove(curso);
        }
    }

    void setDirty(boolean value) {
        try {
            parent.setDirty(value);
        } catch (NullPointerException e) {
        }
    }

    /**
     *
     * @param parent
     */
    public void setParent(DataAsignaturas parent) {
        this.parent = parent;
    }

    @Override
    public void setDocente(Profesor profesor) {
        for (Curso c: cursos)
            c.setDocente(profesor);
    }

    @Override
    public void removeDocente() {
    for (Curso c: cursos)
            c.removeDocente();
    }    }
    

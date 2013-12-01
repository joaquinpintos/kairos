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
public class Carrera implements Serializable, Teachable {

    private static final long serialVersionUID = 27112013L;
    private String nombre;
    private DataAsignaturas parent;
    private final ArrayList<Curso> cursos;
    private boolean algunoSinAula;

    /**
     *
     * @param nombre
     */
    public Carrera(String nombre) throws IllegalArgumentException{
        setNombre(nombre);
        this.cursos = new ArrayList<Curso>();
        algunoSinAula = true;
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
            throw new IllegalArgumentException("El nombre de la carrera no puede contener el carácter @");
        } else {
            this.nombre = nombre;
        }
        setDirty(true);
    }

    /**
     *
     * @param curso
     */
    public void addCurso(Curso curso) {
        this.cursos.add(curso);
        curso.setParent(this);
        Collections.sort(cursos);
        fireDataEvent(curso, DataProyectoListener.ADD);
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
        fireDataEvent(curso, DataProyectoListener.REMOVE);
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

    void setDirty(boolean value) {
        if (parent != null) {
            parent.setDirty(value);
        }
    }

    /**
     *
     * @param parent
     */
    public void setParent(DataAsignaturas parent) {
        this.parent = parent;
    }

    /**
     *
     * @param profesor
     */
    @Override
    public void setDocente(Profesor profesor) {
        for (Curso c : cursos) {
            c.setDocente(profesor);
        }
    }

    /**
     *
     */
    @Override
    public void removeDocente() {
        for (Curso c : cursos) {
            c.removeDocente();
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
        for (Curso c : cursos) {
            c.asignaAula(aula);
        }
    }

    /**
     *
     */
    @Override
    public void removeAula() {
        for (Curso c : cursos) {
            c.removeAula();
        }
    }

    /**
     *
     */
    public void updateAsigAulaStatus() {
        boolean resul = false;
        for (Curso c : cursos) {
            if (c.algunoSinAula()) {
                resul = true;
                break;
            }
        }
        if (resul != algunoSinAula) {
            algunoSinAula = resul;
        }
    }

    /**
     *
     * @return
     */
    public boolean algunoSinAula() {
        return algunoSinAula;
    }

    void removeAllCursos() {
        ArrayList<Curso> cursosClone = (ArrayList<Curso>) cursos.clone();
        for (Curso cu : cursosClone) {
            removeCurso(cu);
        }

    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.aulas.Aula;
import data.aulas.AulaMT;
import data.profesores.Profesor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author david
 */
public class Carrera implements Serializable, Teachable {

    private String nombre;
    private DataAsignaturas parent;
    private final ArrayList<Curso> cursos;
    private boolean algunoSinAula;

    /**
     *
     * @param nombre
     */
    public Carrera(String nombre) {
        this.nombre = nombre;
        this.cursos = new ArrayList<Curso>();
        algunoSinAula=true;
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
        for (Curso c : cursos) {
            c.setDocente(profesor);
        }
    }

    @Override
    public void removeDocente() {
        for (Curso c : cursos) {
            c.removeDocente();
        }
    }

    public void fireDataEvent(Object obj, int type) {
        getParent().fireDataEvent(obj, type);
    }

    @Override
    public void asignaAula(AulaMT aula) {
        for (Curso c : cursos) {
            c.asignaAula(aula);
        }
    }

    @Override
    public void removeAula() {
        for (Curso c : cursos) {
            c.removeAula();
        }
    }

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

    public boolean algunoSinAula() {
        return algunoSinAula;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class Carrera implements Serializable, Teachable, Comparable<Carrera> {

    private static final long serialVersionUID = 27112013L;
    private String nombre;
    private DataAsignaturas parent;
    private final ArrayList<Curso> cursos;
    private boolean algunoSinAula;
    private boolean algunoSinDocente;

    /**
     *
     * @param nombre
     */
    public Carrera(String nombre) throws IllegalArgumentException {
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
    public final void setNombre(String nombre) throws IllegalArgumentException {
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
    }

    public void ordenaCursos() {
        Collections.sort(cursos);
    }

    /**
     *
     * @return
     */
    public DataAsignaturas getParent() {
        return parent;
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
     */
    public void updateAsigAulaStatus() {
        boolean resul = false;
        for (Curso c : cursos) {
            if (c.isAlgunoSinAula()) {
                resul = true;
                break;
            }
        }
        if (resul != algunoSinAula) {
            algunoSinAula = resul;
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

    public void removeCurso(Curso cur) {
        cursos.remove(cur);
    }

    @Override
    public int compareTo(Carrera o) {
        return this.nombre.compareTo(o.getNombre());
    }

    @Override
    public boolean isAlgunoSinDocente() {
        return algunoSinDocente;
    }

    @Override
    public void setAlgunoSinDocente(boolean value) {
        algunoSinDocente = value;
    }

    public void copyBasicValuesFrom(Carrera data) {
        this.nombre=data.getNombre();
    }

}

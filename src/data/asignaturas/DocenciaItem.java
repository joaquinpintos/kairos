/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import java.io.Serializable;

/**
 *
 * @author david
 */
public class DocenciaItem implements Comparable,Serializable{

    private Carrera carrera;
    private Curso curso;
    private Asignatura asignatura;
    private Grupo grupo;

    /**
     *
     * @param carrera
     * @param curso
     * @param asignatura
     * @param grupo
     */
    public DocenciaItem(Carrera carrera, Curso curso, Asignatura asignatura, Grupo grupo) {
        this.carrera = carrera;
        this.curso = curso;
        this.asignatura = asignatura;
        this.grupo = grupo;
    }

    /**
     *
     * @param grupo
     */
    public DocenciaItem(Grupo grupo) {
        this.grupo = grupo;
        this.asignatura = this.grupo.getParent();
        this.curso = this.asignatura.getParent();
        this.carrera = this.curso.getParent();
    }

    /**
     *
     * @return
     */
    public double getHoras() {
        return grupo.getTotalHoras();
    }

    @Override
    public String toString() {
        return carrera + " - " + curso + " - " + asignatura + " + " + grupo;
    }

    /**
     *
     * @return
     */
    public Carrera getCarrera() {
        return carrera;
    }

    /**
     *
     * @param carrera
     */
    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    /**
     *
     * @return
     */
    public Curso getCurso() {
        return curso;
    }

    /**
     *
     * @param curso
     */
    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    /**
     *
     * @return
     */
    public Asignatura getAsignatura() {
        return asignatura;
    }

    /**
     *
     * @param asignatura
     */
    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
    }

    /**
     *
     * @return
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     *
     * @param grupo
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    @Override
    public int compareTo(Object o) {
         return this.toString().compareTo(o.toString());
    }
}

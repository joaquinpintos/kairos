/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import java.io.Serializable;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DocenciaItem implements Comparable, Serializable {

    private static final long serialVersionUID = 27112013L;
    private final Carrera carrera;
    private final Curso curso;
    private final Asignatura asignatura;
    private final Grupo grupo;
    private final Tramo tramo;

    /**
     *
     * @param carrera
     * @param curso
     * @param asignatura
     * @param grupo
     */
//    public DocenciaItem(Carrera carrera, Curso curso, Asignatura asignatura, Grupo grupo) {
//        this.carrera = carrera;
//        this.curso = curso;
//        this.asignatura = asignatura;
//        this.grupo = grupo;
//    }
    /**
     *
     * @param tr
     *
     */
    public DocenciaItem(Tramo tr) {
        this.tramo = tr;
        this.grupo = tr.getParent().getParent();
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
     * @return
     */
    public Curso getCurso() {
        return curso;
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
     * @return
     */
    public Grupo getGrupo() {
        return grupo;
    }


    @Override
    public int compareTo(Object o) {
        return this.toString().compareTo(o.toString());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (this.tramo != null ? this.tramo.hashCode() : 0);
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
        final DocenciaItem other = (DocenciaItem) obj;
        if (this.tramo != other.tramo && (this.tramo == null || !this.tramo.equals(other.tramo))) {
            return false;
        }
        return true;
    }

}

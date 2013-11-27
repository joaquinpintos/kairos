/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.aulas;

import data.asignaturas.Grupo;
import java.io.Serializable;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class CarreraCursoGrupoContainer implements Comparable<CarreraCursoGrupoContainer>, Serializable {

    private static final long serialVersionUID = 27112013L;
    private String hash;
    private String nombre;
    private String aulaNombre;

    /**
     *
     * @param hash
     * @param nombre
     */
    public CarreraCursoGrupoContainer(String hash, String nombre) {
        this.hash = hash;
        this.nombre = nombre;
        this.aulaNombre = null;
    }

    /**
     *
     * @param gr
     */
    public CarreraCursoGrupoContainer(Grupo gr) {
        this.hash = gr.getHashCarreraGrupoCurso();
        this.nombre = gr.getNombreConCarrera();
        this.aulaNombre = null;
    }

    /**
     *
     * @return
     */
    public String getHash() {
        return hash;
    }

    /**
     *
     * @param hash
     */
    public void setHash(String hash) {
        this.hash = hash;
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
    }

    @Override
    public String toString() {
        return nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.hash != null ? this.hash.hashCode() : 0);
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
        final CarreraCursoGrupoContainer other = (CarreraCursoGrupoContainer) obj;
        if ((this.hash == null) ? (other.hash != null) : !this.hash.equals(other.hash)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(CarreraCursoGrupoContainer o) {
        return nombre.compareTo(o.getNombre());
    }

    /**
     *
     * @return
     */
    public String getAulaNombre() {
        return aulaNombre;
    }

    /**
     *
     * @param aulaHash
     */
    public void setAulaNombre(String aulaHash) {
        this.aulaNombre = aulaHash;
    }
}

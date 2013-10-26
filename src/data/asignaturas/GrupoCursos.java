/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.DataProyectoListener;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Esta clase es un container que representa todos los segmentos de clase que
 * pertenecen a un mismo grupo/curso Por ejemplo, todas las clases de 1ºA, de
 * 2ºC, etc. La estructura sería GrupoCurso->Grupo Para representarlo en un
 * arbol sería necesario reconstruir los items intermedios!
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class GrupoCursos implements DataProyectoListener, Serializable {

    private final ArrayList<Grupo> grupos;
    private final String nombreGrupo;
    private final Curso curso;

    public GrupoCursos(String nombreGrupo, Curso curso) {
        this.nombreGrupo = nombreGrupo;
        this.curso = curso;
        grupos = new ArrayList<Grupo>();
        for (Asignatura asig : curso.getAsignaturas()) {
            for (Grupo gr : asig.getGrupos().getGrupos()) {
                if (gr.getNombre().equals(nombreGrupo)) {
                    grupos.add(gr);

                }
            }
        }

    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public Grupo get(int index) {
        return grupos.get(index);
    }

    public int size() {
        return grupos.size();
    }

    public int indexOf(Object o) {
        return grupos.indexOf(o);
    }

    public Curso getCurso() {
        return curso;
    }

    @Override
    public String toString() {
        return "Grupo " + nombreGrupo + " de " + curso.getNombre() + " de " + curso.getParent().getNombre();
    }

    @Override
    public void dataEvent(Object obj, int type) {
        if (obj instanceof Grupo) {
            Grupo gr = (Grupo) obj;
            switch (type) {
                case DataProyectoListener.ADD:
                    addGrupo(gr);
                    break;
                case DataProyectoListener.REMOVE:
                    grupos.remove(gr);
                    break;
                case DataProyectoListener.MODIFY:
                    throw new java.lang.UnsupportedOperationException("Not supported yet.");
            }

        }
    }

    public void addGrupo(Grupo gr) {
        if (gr.getNombre().equals(nombreGrupo)) {
            add(gr);
        }
    }

    private void add(Grupo gr) {
        if (!grupos.contains(gr)) {
            grupos.add(gr);
        }
    }

    public void removeGrupo(Grupo gr) {
        grupos.remove(gr);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.nombreGrupo != null ? this.nombreGrupo.hashCode() : 0);
        hash = 79 * hash + (this.curso != null ? this.curso.hashCode() : 0);
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
        final GrupoCursos other = (GrupoCursos) obj;
        if ((this.nombreGrupo == null) ? (other.nombreGrupo != null) : !this.nombreGrupo.equals(other.nombreGrupo)) {
            return false;
        }
        if (this.curso != other.curso && (this.curso == null || !this.curso.equals(other.curso))) {
            return false;
        }
        return true;
    }

    public ArrayList<Grupo> getGrupos() {
        return grupos;
    }

    public boolean algunoSinAula() {
        boolean resul = false;
        for (Grupo gr : grupos) {
            if (gr.algunoSinAula()) {
                resul = true;
                break;
            }
        }
        return resul;
    }

}

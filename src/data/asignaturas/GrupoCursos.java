/*
 * Copyright (C) 2014 David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
public class GrupoCursos implements DataProyectoListener, Serializable, Teachable {

    private static final long serialVersionUID = 27112013L;
    private final ArrayList<Grupo> grupos;
    private final String nombreGrupo;
    private final Curso curso;
    private boolean algunoSinDocente;

    /**
     *
     * @param nombreGrupo
     * @param curso
     */
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

    /**
     *
     * @return
     */
    public String getNombreGrupo() {
        return nombreGrupo;
    }

    /**
     *
     * @param index
     * @return
     */
    public Grupo get(int index) {
        return grupos.get(index);
    }

    /**
     *
     * @return
     */
    public int size() {
        return grupos.size();
    }

    /**
     *
     * @param o
     * @return
     */
    public int indexOf(Object o) {
        return grupos.indexOf(o);
    }

    /**
     *
     * @return
     */
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

    /**
     *
     * @param gr
     */
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

    /**
     *
     * @param gr
     */
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

    /**
     *
     * @return
     */
    public ArrayList<Grupo> getGrupos() {
        return grupos;
    }

    /**
     *
     * @return
     */
    @Override
    public boolean isAlgunoSinAula() {
        boolean resul = false;
        for (Grupo gr : grupos) {
            if (gr.isAlgunoSinAula()) {
                resul = true;
                break;
            }
        }
        return resul;
    }

    @Override
    public boolean isAlgunoSinDocente() {
        return algunoSinDocente;
    }

    @Override
    public void setAlgunoSinDocente(boolean value) {
        algunoSinDocente = value;
    }

    @Override
    public void setAlgunoSinAula(boolean value) {
    }
}

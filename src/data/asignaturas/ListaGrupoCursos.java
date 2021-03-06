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

import data.AbstractDataSets;
import data.DataProject;
import data.DataProjectListener;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListaGrupoCursos extends AbstractDataSets implements DataProjectListener {

    private static final long serialVersionUID = 27112013L;
    private final ArrayList<GrupoCursos> grupoCursos;

    /**
     *
     * @param dataProject
     */
    public ListaGrupoCursos(DataProject dataProject) {
        super(dataProject);
        grupoCursos = new ArrayList<GrupoCursos>();
    }

    /**
     *
     * @param index
     * @return
     */
    public GrupoCursos get(int index) {
        return grupoCursos.get(index);
    }

    /**
     *
     * @return
     */
    public int size() {
        return grupoCursos.size();
    }

    /**
     *
     * @param o
     * @return
     */
    public int indexOf(Object o) {
        return grupoCursos.indexOf(o);
    }

    /**
     *
     * @param gr
     */
    public void add(Grupo gr) {
        this.add(gr.getNombre(), gr.getParent().getParent());
    }

    /**
     *
     * @param nombreGrupo
     * @param curso
     */
    public void add(String nombreGrupo, Curso curso) {
        this.add(new GrupoCursos(nombreGrupo, curso));
    }

    /**
     *
     * @param e
     */
    public void add(GrupoCursos e) {
        boolean noEsta = true;
        for (GrupoCursos gc : grupoCursos) {
            if (gc.equals(e)) {
                noEsta = false;
                break;
            }
        }
        if (noEsta) {
            grupoCursos.add(e);
        }
    }

    /**
     *
     * @param gc
     */
    public void remove(GrupoCursos gc) {
        grupoCursos.remove(gc);
    }

    @Override
    public void dataEvent(Object obj, int type) {

        if (obj instanceof Grupo) {
            Grupo gr = (Grupo) obj;
            dataEventGrupo(gr, type);
        }
        if (obj instanceof Asignatura) {
            Asignatura asig = (Asignatura) obj;
            dataEventAsignatura(asig, type);
        }
        if (obj instanceof Curso) {
            Curso cur = (Curso) obj;
            dataEventCurso(cur, type);
        }
        if (obj instanceof Carrera) {
            Carrera car = (Carrera) obj;
            for (Curso cur : car.getCursos()) {
                dataEventCurso(cur, type);
            }
        }
    }

    /**
     *
     * @param cur
     * @param type
     */
    protected void dataEventCurso(Curso cur, int type) {
        for (Asignatura asig : cur.getAsignaturas()) {
            dataEventAsignatura(asig, type);
        }
    }

    /**
     *
     * @param asig
     * @param type
     */
    protected void dataEventAsignatura(Asignatura asig, int type) {
        for (Grupo gr : asig.getGrupos().getGrupos()) {
            dataEventGrupo(gr, type);
        }
    }

    /**
     *
     * @param gr
     * @param type
     */
    protected void dataEventGrupo(Grupo gr, int type) {
        GrupoCursos gc = grupoCursoQueContiene(gr);
        switch (type) {
            case DataProjectListener.ADD:
                //Miro si no hay ya un grupoCurso que pueda contener esto
                if (gc == null) {
                    this.add(gr);//Creo nuevo grupoCurso a partir de este
                } else {
                    gc.addGrupo(gr);
                }
                break;
            case DataProjectListener.REMOVE:
                if (gc != null) {
                    removeGrupo(gc, gr);
                } else {//Busco a lo bestia
                    removeGrupo(gr);
                }
                break;
            case DataProjectListener.MODIFY:
//                gc = grupoCursoQueContiene(gr);
                break;
            default:
                break;
        }
    }

    /**
     * Devuelve el grupocurso al que pertenece el grupo dado. Null si no
     * pertenece a ninguno.
     *
     * @param gr
     * @return
     */
    private GrupoCursos grupoCursoQueContiene(Grupo gr) {
        GrupoCursos resul = null;
        try {
//            Curso c = gr.getParent().getParent();
            for (GrupoCursos gc : grupoCursos) {
                if ((gc.getNombreGrupo().equals(gr.getNombre())) && (gc.getCurso().equals(gr.getParent().getParent()))) {
                    resul = gc;
                    break;
                }
            }
        } catch (NullPointerException e) {
        }
        return resul;
    }

    private void removeGrupo(Grupo gr) {
        for (GrupoCursos gcu : grupoCursos) {
            if (gcu.getGrupos().contains(gr)) {
                removeGrupo(gcu, gr);
                break;
            }
        }
    }

    private void removeGrupo(GrupoCursos gc, Grupo gr) {
        if ((gc.getGrupos().contains(gr)) && (gc.getGrupos().size() == 1))//Es el ultimo grupo que queda!!
        {
            remove(gc);
        } else {
            gc.removeGrupo(gr);
        }
    }

    @Override
    public String toString() {
        return "ListaGrupoCursos{" + "grupoCursos=" + grupoCursos.size() + '}';
    }

//    private void rebuildAll() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}

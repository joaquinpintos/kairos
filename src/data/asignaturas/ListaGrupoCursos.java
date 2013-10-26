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
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListaGrupoCursos implements DataProyectoListener, Serializable {

    private final ArrayList<GrupoCursos> grupoCursos;

    public ListaGrupoCursos() {
        grupoCursos = new ArrayList<GrupoCursos>();
    }

    public GrupoCursos get(int index) {
        return grupoCursos.get(index);
    }

    public int size() {
        return grupoCursos.size();
    }

    public int indexOf(Object o) {
        return grupoCursos.indexOf(o);
    }

    public void add(Grupo gr) {
        this.add(gr.getNombre(), gr.getParent().getParent());
    }

    public void add(String nombreGrupo, Curso curso) {
        this.add(new GrupoCursos(nombreGrupo, curso));
    }

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
            System.out.println("Añadido grupoCurso " + e);
            System.out.println(grupoCursos);
        }
    }

    public void remove(GrupoCursos gc) {
        grupoCursos.remove(gc);
    }

    @Override
    public void dataEvent(Object obj, int type) {
        if (obj instanceof Grupo) {
            Grupo gr = (Grupo) obj;
            final GrupoCursos gc = grupoQueContiene(gr);
            switch (type) {
                case DataProyectoListener.ADD:
                    //Miro si no hay ya un grupoCurso que pueda contener esto
                    if (gc == null) {
                        this.add(gr);//Creo nuevo grupoCurso a partir de este
                    } else {
                        gc.addGrupo(gr);
                    }

                    break;
                case DataProyectoListener.REMOVE:
                    if (gc != null) {
                        removeGrupo(gc, gr);
                    }
                    break;
                case DataProyectoListener.MODIFY:
                    throw new java.lang.UnsupportedOperationException("Not supported yet.");
            }
        }

    }

    /**
     * Devuelve true si el grupo dado pertenece a algún grupocurso de la lista
     *
     * @param gr
     * @return
     */
    private GrupoCursos grupoQueContiene(Grupo gr) {
        GrupoCursos resul = null;
        Curso c = gr.getParent().getParent();
        for (GrupoCursos gc : grupoCursos) {
            if ((gc.getNombreGrupo().equals(gr.getNombre())) && (gc.getCurso().equals(gr.getParent().getParent()))) {
                resul = gc;
                break;
            }
        }
        return resul;
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

}

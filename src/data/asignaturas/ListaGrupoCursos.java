/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.AbstractDataSets;
import data.DataProyecto;
import data.DataProyectoListener;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListaGrupoCursos extends AbstractDataSets implements DataProyectoListener, Serializable {

    private final ArrayList<GrupoCursos> grupoCursos;

    public ListaGrupoCursos(DataProyecto dataProyecto) {
        super(dataProyecto);
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
        fireDataEvent(e, ADD);
    }

    public void remove(GrupoCursos gc) {
        grupoCursos.remove(gc);
        fireDataEvent(gc, REMOVE);
    }

    @Override
    public void dataEvent(Object obj, int type) {
        if (obj instanceof Grupo) {
            Grupo gr = (Grupo) obj;
            GrupoCursos gc = grupoCursoQueContiene(gr);
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
                    else
                    {//Busco a lo bestia
                        removeGrupo(gr);
                    }
                    break;
                case DataProyectoListener.MODIFY:
                    gc = grupoCursoQueContiene(gr);
            }
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
            Curso c = gr.getParent().getParent();
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
    private void removeGrupo(Grupo gr)
    {
        for (GrupoCursos gcu:grupoCursos)
        {
            if (gcu.getGrupos().contains(gr))
            {
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
        fireDataEvent(gr, REMOVE);
    }

    @Override
    public String toString() {
        return "ListaGrupoCursos{" + "grupoCursos=" + grupoCursos.size() + '}';
    }

}

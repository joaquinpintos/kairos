/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Aulas;

import data.DataKairos;
import data.asignaturas.Grupo;
import data.asignaturas.GrupoCursos;
import data.asignaturas.ListaGrupoCursos;
import data.asignaturas.Tramo;
import java.util.ArrayList;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author david
 */
public class TreeModelGrupoCursos implements TreeModel {

    private final DataKairos dk;
    ArrayList<TreeModelListener> listeners;

    /**
     *
     * @param dk
     */
    public TreeModelGrupoCursos(DataKairos dk) {
        this.dk = dk;
        this.listeners = new ArrayList<TreeModelListener>();
    }

    @Override
    public Object getRoot() {
        return dk.getDP().getDataAsignaturas().getListaGrupoCursos();
    }
    @Override
    public Object getChild(Object parent, int index) {
        Object resul = null;
        if (parent instanceof ListaGrupoCursos) {
            ListaGrupoCursos lgc = (ListaGrupoCursos) parent;
            resul = lgc.get(index);
        }
        if (parent instanceof GrupoCursos) {
            GrupoCursos gc = (GrupoCursos) parent;
            resul = gc.get(index);
        }
        if (parent instanceof Grupo) {
            Grupo gr = (Grupo) parent;
            resul = gr.getTramosGrupoCompleto().getTramos().get(index);
            
        }
        return resul;
    }

    @Override
    public int getChildCount(Object parent) {
        int resul = 0;
        if (parent instanceof ListaGrupoCursos) {
            ListaGrupoCursos lgc = (ListaGrupoCursos) parent;
            resul = lgc.size();
        }
        if (parent instanceof GrupoCursos) {
            GrupoCursos gc = (GrupoCursos) parent;
            resul = gc.size();
        }
        if (parent instanceof Grupo) {
            Grupo gr = (Grupo) parent;
            resul = gr.getTramosGrupoCompleto().getTramos().size();
        }
        return resul;
    }

    @Override
    public boolean isLeaf(Object node) {
        return (node instanceof Tramo);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        int resul = 0;
        if (parent instanceof ListaGrupoCursos) {
            ListaGrupoCursos lgc = (ListaGrupoCursos) parent;
            resul = lgc.indexOf(child);
        }
        if (parent instanceof GrupoCursos) {
            GrupoCursos gc = (GrupoCursos) parent;
            resul = gc.indexOf(child);
        }
          if (parent instanceof Grupo) {
            Grupo gr = (Grupo) parent;
            resul = gr.getTramosGrupoCompleto().getTramos().indexOf(child);
        }
        return resul;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }

}

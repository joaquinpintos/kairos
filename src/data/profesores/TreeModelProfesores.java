/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.profesores;

import data.DataKairos;
import java.util.ArrayList;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class TreeModelProfesores implements TreeModel {

    ArrayList<TreeModelListener> listeners;
    private final DataKairos dk;

    /**
     *
     * @param dk
     */
    public TreeModelProfesores(DataKairos dk) {
        this.listeners = new ArrayList<TreeModelListener>();
        this.dk = dk;
    }

    @Override
    public Object getRoot() {
        return dk.getDP().getDataProfesores();
    }

    @Override
    public Object getChild(Object parent, int index) {
        Object resul = null;
        if (parent instanceof DataProfesores) {
            DataProfesores data = (DataProfesores) parent;
            resul = data.getDepartamentos().get(index);
        }
        if (parent instanceof Departamento) {
            Departamento data = (Departamento) parent;
            resul = data.getProfesores().get(index);
        }
        //Profesor->null
        return resul;
    }

    @Override
    public int getChildCount(Object parent) {
        int resul = 0;
        if (parent instanceof DataProfesores) {
            DataProfesores data = (DataProfesores) parent;
            resul = data.getDepartamentos().size();
        }
        if (parent instanceof Departamento) {
            Departamento data = (Departamento) parent;
            resul = data.getProfesores().size();
        }
        //Profesor->no children
        return resul;
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        int resul = -1;
        if (parent instanceof DataProfesores) {
            DataProfesores data = (DataProfesores) parent;
            resul = data.getDepartamentos().indexOf(child);
        }
        if (parent instanceof Departamento) {
            Departamento data = (Departamento) parent;
            resul = data.getProfesores().indexOf(child);
        }
        //Profesor->no index
        return resul;
    }

    @Override
    public boolean isLeaf(Object node) {
        return (node instanceof Profesor);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
        listeners.add(l);

    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
        listeners.remove(l);
    }


    /**
     * Copia los datos del nuevo profesor en el viejo
     *
     * @param p
     * @param nuevoP
     * @param path
     */
    public void changeProfesor(Profesor p, Profesor nuevoP, TreePath path) {
        TreeModelEvent evt = new TreeModelEvent(p, path);
        if (path != null) {
            for (TreeModelListener l : listeners) {
                l.treeNodesRemoved(evt);
            }
        }
        p.setNombre(nuevoP.getNombre());
        p.setApellidos(nuevoP.getApellidos());
        p.setNombreCorto(nuevoP.getNombreCorto());
        Departamento d = nuevoP.getDepartamento();
        p.changeDepartamento(d);
        TreePath nuevoPath = new TreePath(new Object[]{getRoot(), d});
        evt = new TreeModelEvent(p, nuevoPath);
        for (TreeModelListener l : listeners) {
            {
                l.treeStructureChanged(evt);
            }
        }
    }

    /**
     *
     * @return
     */
    public DataProfesores getDataProfesores() {
        return dk.getDP().getDataProfesores();
    }
}

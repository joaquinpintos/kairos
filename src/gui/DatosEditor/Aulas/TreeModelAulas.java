/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Aulas;

import data.DataKairos;
import data.aulas.Aula;
import data.aulas.DataAulas;
import data.aulas.ListaAsignaciones;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class TreeModelAulas implements TreeModel {
    private final DataKairos dk;


    /**
     *
     * @param dk
     */
    public TreeModelAulas(DataKairos dk) {
        this.dk=dk;
    }

    @Override
    public Object getRoot() {
        return dk.getDP().getDataAulas();
    }

    @Override
    public Object getChild(Object parent, int index) {
        Object resul = null;
        if (parent instanceof DataAulas) {
            DataAulas data = (DataAulas) parent;
            resul = data.getAulas().get(index);
        }

        if (parent instanceof Aula) {
            Aula aula = (Aula) parent;
            if (index == 0) {
                resul = aula.getAsignacionesMañana();
            }
            if (index == 1) {
                resul = aula.getAsignacionesTarde();
            }
        }

        if (parent instanceof ListaAsignaciones) {
            ListaAsignaciones data = (ListaAsignaciones) parent;
            resul = data.get(index);
        }


//RangoHoras -> null
        return resul;
    }

    @Override
    public int getChildCount(Object parent) {
        int resul = 0;
        if (parent instanceof DataAulas) {
            DataAulas data = (DataAulas) parent;
            resul = data.getAulas().size();
        }
        if (parent instanceof Aula) {
            Aula data = (Aula) parent;
            resul = 2;//Mañana y tarde
        }
//        if (parent instanceof ListaAsignaciones) {
//            ListaAsignaciones data = (ListaAsignaciones) parent;
//            resul = data.size();
//        }

//RangoHoras -> 0
        return resul;
    }

    @Override
    public boolean isLeaf(Object node) {
        return (node instanceof ListaAsignaciones);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue) {
    }

    @Override
    public int getIndexOfChild(Object parent, Object child) {
        int resul = 0;
        if (parent instanceof DataAulas) {
            DataAulas data = (DataAulas) parent;
            resul = data.getAulas().indexOf(child);
        }
        if (parent instanceof Aula) {
            resul = ((ListaAsignaciones) child).getIndex();
        }
        if (parent instanceof ListaAsignaciones) {
            ListaAsignaciones asig=(ListaAsignaciones) parent;
            resul=asig.getHashToGroupContainers().indexOf(child);
        }
//RangoHoras -> 0
        return resul;
    }

    @Override
    public void addTreeModelListener(TreeModelListener l) {
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l) {
    }

}

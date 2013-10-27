/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.AbstractDataSets;
import data.DataProyecto;
import data.DataProyectoListener;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author david
 */
public class DataAsignaturas extends AbstractDataSets {

    private final ArrayList<Carrera> carreras;
    private final ListaGrupoCursos listaGrupoCursos;

    /**
     *
     * @param dataProyecto
     */
    public DataAsignaturas(DataProyecto dataProyecto) {
        super(dataProyecto);
        this.carreras = new ArrayList<Carrera>();
        listaGrupoCursos = new ListaGrupoCursos();
        this.addListener(listaGrupoCursos);//Listener para cuando se añada/borre un grupo
    }

    /**
     *
     */
    public void clear() {
        //borra todos los datos
        carreras.clear();
    }

    /**
     *
     * @param car
     */
    public void addCarrera(Carrera car) {
        this.carreras.add(car);
        car.setParent(this);
        setDirty(true);
//        car.setParent(this);
    }

    /**
     *
     * @param car
     */
    public void removeCarrera(Carrera car) {
        this.carreras.remove(car);
        car.setParent(null);
        setDirty(true);
//        car.setParent(null);
    }

    /**
     *
     * @return
     */
    public ArrayList<Grupo> getAllGrupos() {
        ArrayList<Grupo> resul = new ArrayList<Grupo>();
        for (Carrera c : getCarreras()) {
            for (Curso cur : c.getCursos()) {
                for (Asignatura asig : cur.getAsignaturas()) {
                    for (Grupo gr : asig.getGrupos().getGrupos()) {
                        resul.add(gr);
                    }
                }
            }
        }
        return resul;

    }

    public ArrayList<Carrera> getCarreras() {
        return carreras;
    }

    public ListaGrupoCursos getListaGrupoCursos() {
        return listaGrupoCursos;
    }

    @Override
    public String toString() {
        return "Plan docente";
    }

    //<editor-fold defaultstate="collapsed" desc="Código de exportar/importar desde XML">
    /**
     *
     * @param nodeRoot
     * @return
     */
    public Document dataToDOM(Node nodeRoot) {
        for (Carrera car : this.carreras) {
            nodoCarrera(nodeRoot, car);
        }
        return nodeRoot.getOwnerDocument();
    }

    private void nodoCarrera(Node parent, Carrera car) {
        Element elemCarrera = parent.getOwnerDocument().createElement("estudios");
        elemCarrera.setAttribute("nombre", car.getNombre());
        Node nodeCarrera = parent.appendChild(elemCarrera);
        Node nodeCursos = nodeCarrera.appendChild(parent.getOwnerDocument().createElement("cursos"));
        for (Curso curso : car.getCursos()) {
            nodoCurso(nodeCursos, curso);
        }

    }

    private void nodoCurso(Node parent, Curso curso) {
        Element elemCurso = parent.getOwnerDocument().createElement("curso");
        elemCurso.setAttribute("nombre", curso.getNombre());
        Node nodeCurso = parent.appendChild(elemCurso);
        for (Asignatura asig : curso.getAsignaturas()) {
            nodeAsignatura(nodeCurso, asig);
        }

    }

    private void nodeAsignatura(Node parent, Asignatura asig) {
        Element elemAsignatura = parent.getOwnerDocument().createElement("asignatura");
        elemAsignatura.setAttribute("nombre", asig.getNombre());
        Node nodeAsignatura = parent.appendChild(elemAsignatura);
        Node nodeGrupos = nodeAsignatura.appendChild(parent.getOwnerDocument().createElement("grupos"));
        for (Grupo gr : asig.getGrupos().getGrupos()) {
            nodeGrupo(nodeGrupos, gr);
        }

    }

    private void nodeGrupo(Node parent, Grupo gr) {
        Element elemGrupo = parent.getOwnerDocument().createElement("grupo");
        elemGrupo.setAttribute("nombre", gr.getNombre());
        Node nodeGrupo = parent.appendChild(elemGrupo);

        Node nodeTramosCompleto = nodeGrupo.appendChild(parent.getOwnerDocument().createElement("tramos_grupo_completo"));
        nodeTramos(nodeTramosCompleto, gr.getTramosGrupoCompleto());
    }

    private void nodeTramos(Node parent, GrupoTramos grupoTramos) {
        Element elemTramo;
        for (Tramo tr : grupoTramos.getTramos()) {
            elemTramo = parent.getOwnerDocument().createElement("tramo");
            elemTramo.setAttribute("minutos", tr.getMinutos() + "");
            Node nodeTramo = parent.appendChild(elemTramo);
            if (tr.getDocente() != null) {
                Node nodeDocente = nodeTramo.appendChild(parent.getOwnerDocument().createElement("docente"));
                nodeDocente.setTextContent(tr.getDocente().hash());
            }
            if (tr.getAulaMT() != null) {
                Node nodeAula = nodeTramo.appendChild(parent.getOwnerDocument().createElement("aula"));
                nodeAula.setTextContent(tr.getAulaMT().getHash());
            }

        }
    }
//</editor-fold>

    /**
     *
     * @param c
     * @param asigNueva
     */
    public void addAsignatura(Curso c, Asignatura asigNueva) {
        c.addAsignatura(asigNueva);
        fireDataEvent(asigNueva, DataProyectoListener.ADD);
    }

    public void refrescaEstadoAsignacionAulas() {
        for (Grupo gr: getAllGrupos())
        {
            gr.updateAsigAulaStatus();
        }
    }

}

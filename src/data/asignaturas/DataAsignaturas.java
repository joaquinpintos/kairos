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
import data.DataProyectoListener;
import java.util.ArrayList;
import java.util.Collections;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DataAsignaturas extends AbstractDataSets {

    private static final long serialVersionUID = 27112013L;
    private final ArrayList<Carrera> carreras;
    private final ListaGrupoCursos listaGrupoCursos;

    /**
     *
     * @param dataProyecto
     */
    public DataAsignaturas(DataProject dataProyecto) {
        super(dataProyecto);
        this.carreras = new ArrayList<Carrera>();
        listaGrupoCursos = new ListaGrupoCursos(dataProyecto);
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
    }

    /**
     *
     * @param car
     */
    public void removeCarrera(Carrera car) {
        this.carreras.remove(car);
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

    /**
     *
     * @return
     */
    public ArrayList<Carrera> getCarreras() {
        return carreras;
    }

    /**
     *
     * @return
     */
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

    public void ordenaCarreras() {
        Collections.sort(carreras);
    }

}

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
package loader;

import data.DataKairos;
import data.DataProject;
import data.KairosCommand;
import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.Grupo;
import data.asignaturas.Tramo;
import data.aulas.Aula;
import data.aulas.AulaMT;
import data.genetic.DatosPorAula;
import data.profesores.Profesor;
import java.util.HashMap;
import org.w3c.dom.Element;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DOMLoaderAsignaturas {

    private final DataProject dataProject;
    private final DataKairos dk;

    /**
     *
     * @param dk
     */
    public DOMLoaderAsignaturas(DataKairos dk) {
        this.dataProject = dk.getDP();
        this.dk = dk;
    }

    /**
     *
     * @param rootElement
     */
    public void parseAsignaturas(Element rootElement) {
        final String nombreNodo = "estudios";
        Carrera nuevaCarrera;
        org.w3c.dom.NodeList nodeList = rootElement.getElementsByTagName(nombreNodo);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDep = (Element) nodeList.item(i);
                String nombre = elemDep.getAttribute("nombre");
                nuevaCarrera = new Carrera(nombre);
                // dbg(nombreNodo + ":" + nombre);
//                dataProject.getDataAsignaturas().addCarrera(nuevaCarrera);
                KairosCommand cmd = dk.getController().getCreateCarreraCommand(nuevaCarrera);
                dk.getController().executeCommand(cmd);
                readListaCursos(elemDep, nuevaCarrera);
            }
        }

    }

    private void readListaCursos(Element elemCarrera, Carrera nuevaCarrera) {
        final String nombreNodo = "cursos";
        org.w3c.dom.NodeList nodeList = elemCarrera.getElementsByTagName(nombreNodo);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDep = (Element) nodeList.item(i);
                readCurso(elemDep, nuevaCarrera);
            }
        }

    }

    private void readCurso(Element parent, Carrera carrera) {
        final String nombreNodo = "curso";
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombreNodo);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDep = (Element) nodeList.item(i);
                String nombre = elemDep.getAttribute("nombre");
                Curso nuevoCurso = new Curso(nombre);
                KairosCommand cmd = dk.getController().getCreateCursoCommand(carrera, nuevoCurso);
                dk.getController().executeCommand(cmd);
                readAsignatura(elemDep, nuevoCurso);
            }
        }

    }

    private void readAsignatura(Element parent, Curso curso) {
        final String nombreNodo = "asignatura";
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombreNodo);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDep = (Element) nodeList.item(i);
                String nombre = elemDep.getAttribute("nombre");
                Asignatura nuevaAsignatura = new Asignatura(nombre);
                KairosCommand cmd = dk.getController().getCreateAsignaturaCommand(curso, nuevaAsignatura);
                dk.getController().executeCommand(cmd);

                readListaGrupos(elemDep, nuevaAsignatura);
            }
        }
    }

    private void readListaGrupos(Element parent, Asignatura nuevaAsignatura) {
        final String nombreNodo = "grupos";
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombreNodo);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDep = (Element) nodeList.item(i);
                readGrupo(elemDep, nuevaAsignatura);
            }
        }

    }

    private void readGrupo(Element parent, Asignatura nuevaAsignatura) {
        final String nombreNodo = "grupo";
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombreNodo);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDep = (Element) nodeList.item(i);
                String nombre = elemDep.getAttribute("nombre");
                Grupo nuevoGrupo = new Grupo(nombre);
                KairosCommand cmd = dk.getController().getCreateGrupoCommand(nuevaAsignatura, nuevoGrupo);
                dk.getController().executeCommand(cmd);
                readTramos(elemDep, nuevoGrupo);
            }
        }
    }

    private void readTramos(Element parent, Grupo nuevoGrupo) {
        final String nombreNodo = "tramos_grupo_completo";
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombreNodo);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDep = (Element) nodeList.item(i);
                readTramosGrupoCompleto(elemDep, nuevoGrupo);
            }
        }

    }

    private void readTramosGrupoCompleto(Element parent, Grupo nuevoGrupo) {
        final String nombreNodo = "tramo";
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombreNodo);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDep = (Element) nodeList.item(i);
                readTramo(elemDep, nuevoGrupo);

            }
        }
    }

    private void readTramo(Element parent, Grupo nuevoGrupo) {
        KairosCommand cmd;
        int minutos = new Integer(parent.getAttribute("minutos"));
        Tramo tr = new Tramo(minutos);
//        nuevoGrupo.addTramoGrupoCompleto(tr);
        cmd = dk.getController().getCreateTramoCommand(nuevoGrupo, tr);
        dk.getController().executeCommand(cmd);
        Element elemDocencia = buscaPrimerElementoConNombre(parent, "docente");
        if (elemDocencia != null) {
            HashMap<String, Profesor> map = dataProject.getMapProfesor();
            Profesor prof = map.get(elemDocencia.getTextContent());
//            tr.setDocenteOld(prof);
            cmd = dk.getController().getAsignarDocenciaCommand(prof, tr);
            dk.getController().executeCommand(cmd);

        }
        Element elemAula = buscaPrimerElementoConNombre(parent, "aula");
        if (elemAula != null) {
            HashMap<String, DatosPorAula> map = dataProject.getMapDatosPorAula();
            map.get(elemAula.getTextContent());
            Aula aula = dataProject.getAulaPorHash(elemAula.getTextContent());
            final AulaMT aulaMT = new AulaMT(aula, elemAula.getTextContent().contains("@T"));
//            tr.asignaAulaOld(aulaMT);
            cmd = dk.getController().getAsignarAulaCommand(aulaMT, tr);
            dk.getController().executeCommand(cmd);
        }
    }

    /**
     *
     * @param parent
     * @param nombre
     * @return
     */
    public Element buscaPrimerElementoConNombre(Element parent, String nombre) {
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombre);
        Element resul;
        if (nodeList != null && nodeList.getLength() > 0) {
            resul = (Element) nodeList.item(0);
        } else {
            resul = null;
        }
        return resul;
    }
}

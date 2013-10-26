/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package loader;

import data.DataKairos;
import data.DataProyecto;
import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.Grupo;
import data.asignaturas.Tramo;
import data.aulas.Aula;
import data.genetic.DatosPorAula;
import data.profesores.Profesor;
import java.io.File;
import java.util.HashMap;
import org.w3c.dom.Element;

/**
 *
 * @author david
 */
public class DOMLoaderAsignaturas {

    org.w3c.dom.Document dom;
    private final DataProyecto dataProyecto;

    /**
     *
     * @param dataProyecto
     */
    public DOMLoaderAsignaturas(DataProyecto dataProyecto) {
        this.dataProyecto = dataProyecto;
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
                dataProyecto.getDataAsignaturas().addCarrera(nuevaCarrera);
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
                carrera.addCurso(nuevoCurso);
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
                curso.addAsignatura(nuevaAsignatura);
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
                nuevaAsignatura.addGrupo(nuevoGrupo);
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
        int minutos = new Integer(parent.getAttribute("minutos"));
        Tramo tr = new Tramo(minutos);
        nuevoGrupo.addTramoGrupoCompleto(tr);
        Element elemDocencia = buscaPrimerElementoConNombre(parent, "docente");
        if (elemDocencia != null) {
            HashMap<String, Profesor> map = dataProyecto.getMapProfesor();
            Profesor prof = map.get(elemDocencia.getTextContent());
            tr.setDocente(prof);
        }
        Element elemAula = buscaPrimerElementoConNombre(parent, "aula");
        if (elemAula != null) {
            HashMap<String, DatosPorAula> map = dataProyecto.getMapDatosPorAula();
            DatosPorAula dat = map.get(elemAula.getTextContent());
            Aula aula=dataProyecto.getAulaPorHash(elemAula.getTextContent());
            tr.asignaAula(aula,elemAula.getTextContent().contains("@T"));
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

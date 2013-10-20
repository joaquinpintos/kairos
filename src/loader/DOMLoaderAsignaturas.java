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
import data.profesores.Profesor;
import java.io.File;
import java.util.HashMap;
import org.w3c.dom.Element;

/**
 *
 * @author david
 */
public class DOMLoaderAsignaturas {

    private File file;
    private Carrera currentCarrera;
    private Curso currentCurso;
    org.w3c.dom.Document dom;
    private final DataProyecto dataProyecto;

    /**
     *
     * @param file
     * @param dataProyecto
     */
    public DOMLoaderAsignaturas(File file, DataProyecto dataProyecto) {
        this.file = file;
        this.dataProyecto=dataProyecto;
    }

    /**
     *
     * @param msg
     */
    public void dbg(String msg) {
        System.out.println(msg);
    }

    /**
     *
     * @return
     */
    public boolean load() {
        dataProyecto.getDataAsignaturas().clear();
        boolean isOk = true;
        dom = null;
        javax.xml.parsers.DocumentBuilderFactory dbf;
        javax.xml.parsers.DocumentBuilder db;

        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();

        try {
            db = dbf.newDocumentBuilder();
            dom = db.parse(file);
            buildDocumentStructure();
        } catch (Exception ex) {
            dbg("Excepción al parsear " + ex.getMessage());
            isOk = false;
        } finally {
            return isOk;
        }
    }

    private void buildDocumentStructure() {
        org.w3c.dom.Element rootElement = dom.getDocumentElement();
        parseAsignaturas(rootElement);


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
                    readDocencia(elemDep, nuevoGrupo);
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
                Tramo tr = readTramo(elemDep);
                nuevoGrupo.addTramoGrupoCompleto(tr);
            }
        }
    }

    private Tramo readTramo(Element parent) {
        int numero = new Integer(parent.getAttribute("numero"));
        int minutos = new Integer(parent.getAttribute("minutos"));
        return new Tramo(numero, minutos);
    }

    private void readDocencia(Element parent, Grupo nuevoGrupo) {
        final String nombreNodo = "docente";
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombreNodo);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDep = (Element) nodeList.item(i);
             //   dbg(elemDep.getTextContent());
                Profesor prof;
                HashMap<String, Profesor> map = dataProyecto.getMapProfesor();
                prof = map.get(elemDep.getTextContent());
                //prof=dataProyecto.getDataProfesores().buscaProfesorPorHash(elemDep.getTextContent());
//                prof.addDocencia(nuevoGrupo);
                nuevoGrupo.setDocente(prof);
            }
        }

    }

}
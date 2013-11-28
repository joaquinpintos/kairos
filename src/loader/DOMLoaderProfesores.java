/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package loader;

import data.DataProject;
import data.profesores.Departamento;
import data.profesores.Profesor;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DOMLoaderProfesores {

    private File file;
    private Departamento currentDepartamento;
    private Profesor currentProfesor;
    private org.w3c.dom.Document dom;
    private final DataProject dataProyecto;

    /**
     *
     * @param file
     * @param dataProyecto
     */
    public DOMLoaderProfesores(File file, DataProject dataProyecto) {
        this.file = file;
        this.dataProyecto = dataProyecto;
    }

    /**
     *
     * @return
     */
    public File getFile() {
        return file;
    }

    /**
     *
     * @param file
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     *
     * @return
     */
    public Departamento getCurrentDepartamento() {
        return currentDepartamento;
    }

    /**
     *
     * @param currentDepartamento
     */
    public void setCurrentDepartamento(Departamento currentDepartamento) {
        this.currentDepartamento = currentDepartamento;
    }

    /**
     *
     * @return
     */
    public Profesor getCurrentProfesor() {
        return currentProfesor;
    }

    /**
     *
     * @param currentProfesor
     */
    public void setCurrentProfesor(Profesor currentProfesor) {
        this.currentProfesor = currentProfesor;
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
        boolean isOk = true;
        dom = null;
        javax.xml.parsers.DocumentBuilderFactory dbf;
        javax.xml.parsers.DocumentBuilder db;

        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();

        try {
            db = dbf.newDocumentBuilder();
            dom = db.parse(file);
            buildDocumentStructure();
        } catch (ParserConfigurationException ex) {
            dbg("Excepción al parsear " + ex.getMessage());
            isOk = false;
        } catch (SAXException ex) {
            dbg("Excepción al parsear " + ex.getMessage());
            isOk = false;
        } catch (IOException ex) {
            dbg("Excepción al parsear " + ex.getMessage());
            isOk = false;
        } finally {
        }
        return isOk;
    }

    private void buildDocumentStructure() {
        if (dom != null) {
            //A partir del archivo dom construyo los datos de dataProfesores
            org.w3c.dom.Element rootElement = dom.getDocumentElement();
            parseProfesores(rootElement);
        }

    }

    /**
     *
     * @param rootElement
     */
    public void parseProfesores(Element rootElement) {
        final String nombreNodoDepartamento = "departamento";
        Departamento nuevoDep;
        org.w3c.dom.NodeList nodeList = rootElement.getElementsByTagName(nombreNodoDepartamento);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDep = (Element) nodeList.item(i);
                String nombre = elemDep.getAttribute("nombre");
                nuevoDep = new Departamento(nombre);
                dataProyecto.getDataProfesores().addDepartamento(nuevoDep);
                readProfesor(elemDep, nuevoDep);
            }
        }
    }

    private void readProfesor(Element elemDep, Departamento nuevoDep) {
        final String nombreNodoProfesor = "profesor";
        NodeList nodeList = elemDep.getElementsByTagName(nombreNodoProfesor);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemProf = (Element) nodeList.item(i);
                Profesor nuevoProf = procesaProfesor(elemProf);
                nuevoDep.addProfesor(nuevoProf);
            }
        }

    }

    private Profesor procesaProfesor(Element elemProf) {
        Profesor nuevoProfe;
        String nombre = primerValor(elemProf, "nombre");
        String apellidos = primerValor(elemProf, "apellidos");
        String nombreCorto = primerValor(elemProf, "nombre_corto");
        nuevoProfe = new Profesor(nombre, apellidos, nombreCorto);
        return nuevoProfe;
    }

    private String primerValor(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) {
            Element element = (Element) nodeList.item(0);
            return element.getTextContent();
        } else {
            return "";
        }
    }
}

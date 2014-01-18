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
import data.profesores.Departamento;
import data.profesores.Profesor;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class XMLDataLoaderWriter {

    private File file;
    org.w3c.dom.Document dom;
    private final DataKairos dk;

    /**
     *
     * @param dk
     */
    public XMLDataLoaderWriter(DataKairos dk) {
        this.dk=dk;
        file = new File("./archivos");//TODO cambiar
    }

    /**
     *
     * @param fichero
     * @return
     */
    public boolean load(File fichero) {
        boolean isOk = true;
        dom = null;
        javax.xml.parsers.DocumentBuilderFactory dbf;
        javax.xml.parsers.DocumentBuilder db;

        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();
        try {
            //    try {
            db = dbf.newDocumentBuilder();
            dom = db.parse(fichero);
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLDataLoaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(XMLDataLoaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(XMLDataLoaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        parseData();
//        } catch (Exception ex) {
//            isOk = false;
//        } finally {
//            return isOk;
//        }
        return true;
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

    private void parseData() {
        org.w3c.dom.Element rootElement = dom.getDocumentElement();
        //Leo nombre del proyecto
        String nombreProyecto = rootElement.getAttribute("nombre");
        dk.getDP().getConfigProyecto().setNombreProyecto(nombreProyecto);

        //Datos de configuración del proyecto
        org.w3c.dom.NodeList nodeList = rootElement.getElementsByTagName("config");
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                dk.getDP().getConfigProyecto().parseXMLConfig((Element) nodeList.item(i));
            }
        }
        //Leo datos profesores
        nodeList = rootElement.getElementsByTagName("profesorado");
        if (nodeList != null && nodeList.getLength() > 0) {
            DOMLoaderProfesores domlp = new DOMLoaderProfesores(null, dk);
            for (int i = 0; i < nodeList.getLength(); i++) {
                domlp.parseProfesores((Element) nodeList.item(i));
            }
        }
        //Creo mapa de profesores.
        dk.getDP().reconstruyeHashMapProfesor();

        //Leo datos aulas
        nodeList = rootElement.getElementsByTagName("aulario");
        if (nodeList != null && nodeList.getLength() > 0) {
            DOMLoaderAulas domlp = new DOMLoaderAulas(null, dk);
            for (int i = 0; i < nodeList.getLength(); i++) {
                domlp.parseAulas((Element) nodeList.item(i));
            }
        }
//        dk.getDP().getDataAsignaturas().refrescaEstadoAsignacionAulas();
        //Leo datos asignaturas

        nodeList = rootElement.getElementsByTagName("plan_docente");
        if (nodeList != null && nodeList.getLength() > 0) {
            DOMLoaderAsignaturas domlp = new DOMLoaderAsignaturas(dk);
            for (int i = 0; i < nodeList.getLength(); i++) {
                domlp.parseAsignaturas((Element) nodeList.item(i));
            }
        }
        //Leo datos generales sobre el proyecto
        nodeList = rootElement.getElementsByTagName("calendario_académico");
        if (nodeList != null && nodeList.getLength() > 0) {
            DOMLoaderDatosProyecto domlp = new DOMLoaderDatosProyecto(null, dk.getDP());
            for (int i = 0; i < nodeList.getLength(); i++) {
                domlp.parseDatosProyecto((Element) nodeList.item(i));
            }
        }

        //Leo datos sobre las restricciones
        nodeList = rootElement.getElementsByTagName("restricciones");
        if (nodeList != null && nodeList.getLength() > 0) {
            DOMLoaderRestricciones domlp = new DOMLoaderRestricciones(null, dk.getDP());
            for (int i = 0; i < nodeList.getLength(); i++) {
                domlp.loadRestricciones((Element) nodeList.item(i));
            }
        }

    }

    /**
     *
     * @return @throws IOException
     */
    public boolean save() throws IOException {
        writeDOM(dataToDOM(), new FileWriter(file));
        return true;
    }

    private void writeDOM(Document doc, FileWriter salida) {
        Transformer Transformer;
        try {
            TransformerFactory tranFactory = TransformerFactory.newInstance();
            Transformer = tranFactory.newTransformer();
            DOMSource src = new DOMSource(doc);
            Result dest = new StreamResult(salida);
            Transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            Transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            Transformer.transform(src, dest);

        } catch (TransformerException ex) {
        }
        try {
            salida.close();
        } catch (IOException ex) {
        }

    }

    /**
     *
     * @return
     */
    public Document dataToDOM() {
//        DataProfesores dataProfesores = dk.getDP().getDataProfesores();
//        DataAulas dataAulas = dk.getDP().getDataAulas();
//        DataAsignaturas dataAsignaturas = dk.getDP().getDataAsignaturas();
        TreeSet DOMdepartamentos;
        Document documentoXML = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = factory.newDocumentBuilder();
            documentoXML = parser.newDocument();
        } catch (ParserConfigurationException e) {
            throw new IllegalArgumentException("No he podido construir modelo DOM");
        }
        Element elementRoot = documentoXML.createElement("proyecto");
        elementRoot.setAttribute("nombre", dk.getDP().getConfigProyecto().getNombreProyecto());//Introduzco atributo nombre con nombre del proyecto
        Node nodeRoot = documentoXML.appendChild(elementRoot);
        Node nodeConfig = nodeRoot.appendChild(documentoXML.createElement("config"));
        dk.getDP().getConfigProyecto().dataToDOM(documentoXML, nodeConfig);
        //Grabo todos los datos del calendario académico
        Node nodeCalendarioAcadémico = nodeRoot.appendChild(documentoXML.createElement("calendario_académico"));
        dk.getDP().getAcademicCalendar().dataToDOM(documentoXML, nodeCalendarioAcadémico);

        //Grabo todos los datos del profesorado
        Node nodeProfesorado = nodeRoot.appendChild(documentoXML.createElement("profesorado"));
        dataProfesoresToDOM(documentoXML, nodeProfesorado);

        //Grabo todos los datos de las aulas
        Node nodeAulas = nodeRoot.appendChild(documentoXML.createElement("aulario"));
        dk.getDP().getDataAulas().dataToDOM(nodeAulas);

        //Grabo todos los datos de la asignación docente
        Node nodeAsignaturas = nodeRoot.appendChild(documentoXML.createElement("plan_docente"));
        dk.getDP().getDataAsignaturas().dataToDOM(nodeAsignaturas);

        //Grabo las restricciones
        Node nodeRestricciones = nodeRoot.appendChild(documentoXML.createElement("restricciones"));
        dk.getDP().getRestrictionsData().dataToDOM(nodeRestricciones);

        return documentoXML;
    }

    /**
     *
     * @param documentoXML
     * @param nodeRoot
     */
    public void dataProfesoresToDOM(Document documentoXML, Node nodeRoot) {
        for (Departamento dep : dk.getDP().getDataProfesores().getDepartamentos()) {
            nodoDepartamento(nodeRoot, dep);
        }
    }

    private void nodoDepartamento(Node parent, Departamento dep) {
        Element elemDpto = parent.getOwnerDocument().createElement("departamento");
        elemDpto.setAttribute("nombre", dep.getNombre());
        Node nodeDpto = parent.appendChild(elemDpto);
        Node el = nodeDpto.appendChild(parent.getOwnerDocument().createElement("profesores"));
        for (Profesor profe : dep.getProfesores()) {
            nodoProfesor(el, profe);
        }

    }

    private void nodoProfesor(Node parent, Profesor profe) {
        Element aux;
        Element elemProfe = parent.getOwnerDocument().createElement("profesor");
        Element elemNombre = nodoNombre(parent, profe.getNombre());
        Element elemApellidos = nodoApellido(parent, profe.getApellidos());
        Element elemNombreCorto = nodoNombreCorto(parent, profe.getNombreCorto());
        Node nodeProfe = parent.appendChild(elemProfe);
        nodeProfe.appendChild(elemNombre);
        nodeProfe.appendChild(elemApellidos);
        nodeProfe.appendChild(elemNombreCorto);

    }

    /**
     * Creo un nodo de tipo nombre.
     *
     * @param string Texto que contiene el nodo
     * @return Nodo generado.
     */
    private Element nodoNombre(Node parent, String nombre) {
        Element el;
        el = parent.getOwnerDocument().createElement("nombre");
        el.appendChild(parent.getOwnerDocument().createTextNode(nombre));
        return el;
    }

    /**
     * Creo un nodo de tipo apellido.
     *
     * @param string Texto que contiene el nodo
     * @return Nodo generado.
     */
    private Element nodoApellido(Node parent, String apellido) {
        Element el;
        el = parent.getOwnerDocument().createElement("apellidos");
        el.appendChild(parent.getOwnerDocument().createTextNode(apellido));
        return el;
    }

    /**
     * Crea un nodo de tipo nombre corto
     *
     * @return Nodo generado
     */
    private Element nodoNombreCorto(Node parent, String nombreCorto) {
        Element el;
        el = parent.getOwnerDocument().createElement("nombre_corto");
        el.appendChild(parent.getOwnerDocument().createTextNode(nombreCorto));
        return el;
    }
//  //Devuelve el primer nodo hijo de parent con nombre especificado.
//    private Element buscaPrimerElementoConNombre(Element parent, String nombre) {
//        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombre);
//        Element resul;
//        if (nodeList != null && nodeList.getLength() > 0) {
//            resul = (Element) nodeList.item(0);
//        } else {
//            resul = null;
//        }
//        return resul;
//    }
}

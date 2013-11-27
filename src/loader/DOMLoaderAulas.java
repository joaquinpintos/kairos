/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package loader;

import data.DataProyecto;
import data.aulas.Aula;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author David
 */
public class DOMLoaderAulas {

    private File file;
    private org.w3c.dom.Document dom;
    private final DataProyecto dataProyecto;

    /**
     *
     * @param file
     * @param dataProyecto
     */
    public DOMLoaderAulas(File file, DataProyecto dataProyecto) {
        this.file = file;
        this.dataProyecto=dataProyecto;
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
    public org.w3c.dom.Document getDom() {
        return dom;
    }

    /**
     *
     * @param dom
     */
    public void setDom(org.w3c.dom.Document dom) {
        this.dom = dom;
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
            isOk = false;
        } catch (SAXException ex) {
            isOk = false;
        } catch (IOException ex) {
            isOk = false;
        }
        return isOk;
    }

    private void buildDocumentStructure() {
        //A partir del archivo dom construyo los datos de dataProfesores
        org.w3c.dom.Element rootElement = dom.getDocumentElement();
        readListaAulas(rootElement);

    }

    /**
     *
     * @param parent
     */
    public void parseAulas(Element parent) {
        readAula(parent);
    }

    private void readListaAulas(Element parent) {
        final String nombreNodo = "aulario";
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombreNodo);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDep = (Element) nodeList.item(i);
                readAula(elemDep);
            }
        }

    }

    private void readAula(Element parent) {
        final String nombreNodo = "aula";
        Aula nuevaAula;
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombreNodo);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDep = (Element) nodeList.item(i);
                String nombre = elemDep.getAttribute("nombre");
                nuevaAula = new Aula(nombre);
                dataProyecto.getDataAulas().addAula(nuevaAula);
            }
        }
    }
}

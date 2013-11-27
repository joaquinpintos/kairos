/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testers;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class XMLDOMTester {

    /**
     *
     * @param msg
     */
    public void dbg(String msg) {
        System.out.println(msg);
    }

    /**
     *
     */
    public void runTest() {
        /* Test de carga de XML, en principio de segmentos, utilizando DOM */
        dbg("XML Test");
        org.w3c.dom.Document dom = null;
        javax.xml.parsers.DocumentBuilderFactory dbf;
        javax.xml.parsers.DocumentBuilder db;

        dbf = javax.xml.parsers.DocumentBuilderFactory.newInstance();

        try {
            db = dbf.newDocumentBuilder();
            dom = db.parse("data/segmentos.xml");
        } catch (Exception ex) {
            dbg("Excepción al parsear");
        }


        //Parseo el fichero segmentos.xml
        org.w3c.dom.Element rootElement = dom.getDocumentElement();

        org.w3c.dom.NodeList nodeList = rootElement.getElementsByTagName("carrera");

        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element element = (Element) nodeList.item(i);
                String name=element.getNodeName();
                dbg(name);
            }
        }

    }
}

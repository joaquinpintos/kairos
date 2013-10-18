/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testers;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author david
 */
public class XMLSAXHandler extends DefaultHandler {

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
        try {

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            saxParser.parse("archivos/profesores.xml", this);

        } catch (Exception e) {
            dbg("Excepcion!!");
            e.printStackTrace();
        }
    }

    @Override
    public void startDocument() throws SAXException {
        dbg("Start SAX...");
    }

    @Override
    public void endDocument() throws SAXException {
        dbg("End SAX...");
    }

    @Override
    public void startElement(String uri, String localName, String name,
            Attributes attr) throws SAXException {
        dbg(name);
        dbg(attr.getValue("nombre"));
    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
    }
}
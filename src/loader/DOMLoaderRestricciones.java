/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package loader;

import data.DataKairos;
import data.DataProyecto;
import data.restricciones.Restriccion;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DOMLoaderRestricciones {

    private File file;
    private final DataProyecto dataProyecto;

    /**
     *
     * @param file
     * @param dataProyecto
     */
    public DOMLoaderRestricciones(File file, DataProyecto dataProyecto) {
        this.file = file;
        this.dataProyecto=dataProyecto;
    }

    /**
     *
     * @param rootElement
     */
    public void loadRestricciones(Element rootElement) {
        NodeList nodeList = rootElement.getElementsByTagName("restriccion");
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                this.loadRestriccion((Element) nodeList.item(i));
            }
        }

    }

    /**
     * Lee restricción desde elemento del documento XML Para ello lee primero el
     * valor del tag className y luego invoca un objeto de dicha clase.
     *
     * @param element
     */
    private void loadRestriccion(Element element) {
        //Miro qué tipo de restriccion es
        Element el = buscaPrimerNodoConNombre(element, "className");
        //Creo dinámicamente el objeto
        String nombre = el.getTextContent();
        try {
            Class c = Class.forName(nombre);
            Restriccion r = (Restriccion) c.newInstance();
            r.setDataProyecto(dataProyecto);
            //Leo variables comunes a cada restriccion. En este caso el nivel (rojo, amarillo, verde)
            Element elConfigComun = buscaPrimerNodoConNombre(element, "config_comun");
            
            el = buscaPrimerNodoConNombre(elConfigComun, "nivel");
            int nivel = Integer.valueOf(el.getTextContent());
            r.setImportancia(nivel);
            //Llamo al método sobreescrito de la clase específica
            //Para leer los datos de configuración.
            Element elConfigEspecifico = buscaPrimerNodoConNombre(element, "config_especifico");
            
            r.readConfig(elConfigEspecifico);
            dataProyecto.getDataRestricciones().add(r);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DOMLoaderRestricciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(DOMLoaderRestricciones.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DOMLoaderRestricciones.class.getName()).log(Level.SEVERE, null, ex);
        }





    }

    /**
     *
     * @param parent
     * @param nombre
     * @return
     */
    public Element buscaPrimerNodoConNombre(Element parent, String nombre) {
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

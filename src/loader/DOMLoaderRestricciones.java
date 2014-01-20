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

import data.DataProject;
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

    private final File file;
    private final DataProject dataProject;

    /**
     *
     * @param file
     * @param dataProject
     */
    public DOMLoaderRestricciones(File file, DataProject dataProject) {
        this.file = file;
        this.dataProject = dataProject;
    }

    /**
     *
     * @param rootElement
     */
    public void loadRestricciones(Element rootElement) {
        NodeList nodeList = rootElement.getElementsByTagName("restriction");
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
            r.setDataProyecto(dataProject);
            //Leo variables comunes a cada restriccion. En este caso el nivel (rojo, amarillo, verde)
            Element elConfigComun = buscaPrimerNodoConNombre(element, "common_config");

            el = buscaPrimerNodoConNombre(elConfigComun, "level");
            int nivel = Integer.valueOf(el.getTextContent());
            r.setLevel(nivel);
            //Llamo al método sobreescrito de la clase específica
            //Para leer los datos de configuración.
            Element elConfigEspecifico = buscaPrimerNodoConNombre(element, "specific_config");

            r.readConfig(elConfigEspecifico);
            dataProject.getRestrictionsData().add(r);
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

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
package data;

import java.io.Serializable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Almacena y gestiona variables relativas a la configuración del proyecto
 *
 * @author usuario
 */
public class ConfigProyecto implements Serializable {

    private static final long serialVersionUID = 27112013L;
    private String nombreProyecto;
    //Este string almacena los grupos por defecto cuando se incluye una asignatura nueva
    private String gruposPorDefecto;
    private final DataProject dataProyecto;

    /**
     *
     * @param dataProyecto
     */
    public ConfigProyecto(DataProject dataProyecto) {
        this.dataProyecto = dataProyecto;
        gruposPorDefecto = "";
    }

    /**
     *
     * @return
     */
    public String getNombreProyecto() {
        return nombreProyecto;
    }

    /**
     *
     * @param nombreProyecto
     */
    public void setNombreProyecto(String nombreProyecto) {
        this.nombreProyecto = nombreProyecto;
    }

    /**
     *
     * @return
     */
    public String getGruposPorDefecto() {
        return gruposPorDefecto;
    }

    /**
     *
     * @param gruposPorDefecto
     */
    public void setGruposPorDefecto(String gruposPorDefecto) {
        this.gruposPorDefecto = gruposPorDefecto;
    }

    /**
     *
     * @param documentoXML
     * @param parent
     */
    public void dataToDOM(Document documentoXML, Node parent) {
        añadeNodo(parent, "grupos_por_defecto", gruposPorDefecto);
    }

    //Esta función añade un nodo simple de texto con nombreNodo y valo
    private void añadeNodo(Node parent, String nombreNodo, String valor) {
        Element el = parent.getOwnerDocument().createElement(nombreNodo);
        el.appendChild(parent.getOwnerDocument().createTextNode(valor));
        parent.appendChild(el);
    }

    /**
     *
     * @param parent
     */
    public void parseXMLConfig(Element parent) {
        Element nodo = buscaPrimerElementoConNombre(parent, "grupos_por_defecto");
        gruposPorDefecto = nodo.getTextContent();
    }

    //Devuelve el primer nodo hijo de parent con nombre especificado.
    private Element buscaPrimerElementoConNombre(Element parent, String nombre) {
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

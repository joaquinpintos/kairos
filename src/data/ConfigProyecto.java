/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

    private static final long serialVersionUID = 1L;
    private String nombreProyecto;
    //Este string almacena los grupos por defecto cuando se incluye una asignatura nueva
    private String gruposPorDefecto;
    private final DataProyecto dataProyecto;

    /**
     *
     * @param dataProyecto
     */
    public ConfigProyecto(DataProyecto dataProyecto) {
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

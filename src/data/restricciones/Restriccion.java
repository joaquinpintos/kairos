/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.restricciones;

import data.DataProject;
import data.MyConstants;
import data.genetic.Casilla;
import data.genetic.ListaCasillas;
import data.genetic.PosibleSolucion;
import data.profesores.Profesor;
import gui.HorarioEditor.RestriccionListener;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Interfaz de todos los objetos rest
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public abstract class Restriccion implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 27112013L;

    /**
     *
     */
    protected long peso;
    //importancia: 1,2, o 3 (rojo, amarillo o verde)
    /**
     *
     */
    protected int importancia;
    /**
     *
     */
    protected boolean debug;
    private final HashMap<String, HashSet<Integer>> _casillasConflictivas;
    /**
     *
     */
    protected boolean marcaCasillasConflictivas;
    private HashSet<RestriccionListener> listeners;
    //Si es true, no puede haber más de una restricción como esta.
    private boolean unique;
    /**
     *
     */
    protected DataProject dataProyecto;

    /**
     *
     */
    public Restriccion() {
        this(null);
//        this.dataProyecto = null;
//        this.peso = 100;
//        this.importancia = 1;
//        debug = false;
//        marcaCasillasConflictivas=false;
//        _casillasConflictivas = new HashMap<String, HashSet<Integer>>();
    }

    /**
     *
     * @param dataProyecto
     */
    public Restriccion(DataProject dataProyecto) {
        this.dataProyecto = dataProyecto;
        this.peso = 100;
        this.importancia = 1;
        debug = false;
        marcaCasillasConflictivas = false;
        _casillasConflictivas = new HashMap<String, HashSet<Integer>>();
        listeners = new HashSet<RestriccionListener>();
        unique = false;//Por defecto, las restricciones no son únicas
    }

    /**
     * Este método se encarga de instanciar la clase correcta A subclase de
     * DOMWriterRestricciones y ejecutar el método writeRestriccion.
     * Posteriorment en A hay que implementar el método writeConfig que se
     * encarga de escribir los campos de configuracion
     *
     */
    abstract public void inicializarDatos();

    /**
     *
     * @param posibleSolucion
     * @return
     */
    abstract public long calculaPeso(PosibleSolucion posibleSolucion);

    /**
     * Lanza el diálogo de configuración. Devuelve true si se han actualizad los
     * datos y false si se ha cancelado
     *
     * @param parent
     * @return
     */
    abstract public boolean lanzarDialogoDeConfiguracion(Object parent);

    /**
     *
     * @return
     */
    public long getPeso() {
        return this.peso;
    }

    /**
     *
     * @param peso
     */
    public void setPeso(long peso) {
        this.peso = peso;
    }

    public long getSuma() {
        long resul = 100;
        switch (importancia) {
            case 1:
                resul = 200;
                break;
            case 2:
                resul = 150;
                break;
            case 3:
                resul = 100;
                break;

        }
        return resul;
    }

    /**
     *
     * @param suma
     */
    public void sumaPeso(long suma) {
        this.peso += suma;
    }

    /**
     *
     * @return
     */
    abstract public String descripcion();//Nombre que aparece en la lista de restricciones.

    /**
     *
     * @return
     */
    abstract public String descripcionCorta();//Nombre corto para elegir de la lista de nuevas restricciones

    /**
     *
     * @return
     */
    abstract public String mensajeDeAyuda();//Cadena de texto descriptiva larga para poner en un textbox

    /**
     *
     * @return
     */
    abstract public String getMensajeError();

    public String formatTeachersList(ArrayList<Profesor> data) {
        StringBuilder sb = new StringBuilder();
        int numColor = 0;
        int numTotalColors = MyConstants.COLORES_PROFESORES.length - 1;

        int numProfes = data.size();

        for (int n = 0; n < numProfes; n++) {
            Profesor p = data.get(n);
            if (n > 0) {

                if (n < numProfes - 1) {
                    sb.append(", ");
                } else {
                    sb.append(" y ");
                }
            }

            Color col = MyConstants.COLORES_PROFESORES[numColor];
            String hex = String.format("#%02x%02x%02x", col.getRed(), col.getGreen(), col.getBlue());
            sb.append("<b><span>");
//            sb.append("<b><font color=\"").append(hex).append("\">");
            sb.append(p.getNombre()).append(" ").append(p.getApellidos());
            sb.append("</span></b>");
            numColor++;
            if (numColor > numTotalColors) {
                numColor = 0;
            }

        }

        return sb.toString();
    }

    /**
     *
     * @return
     */
    public DataProject getDataProyecto() {
        return dataProyecto;
    }

    /**
     *
     * @param dataProyecto
     */
    public void setDataProyecto(DataProject dataProyecto) {
        this.dataProyecto = dataProyecto;
    }

    /**
     *
     * @return
     */
    public int getImportancia() {
        return importancia;
    }

    /**
     *
     * @param importancia
     */
    public void setImportancia(int importancia) {
        this.importancia = importancia;
        if (this.importancia < 1) {
            this.importancia = 1;
        }
        if (this.importancia > 3) {
            this.importancia = 3;
        }
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     *
     * @param debug
     */
    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    /**
     *
     * @param string
     */
    protected void dbg(String string) {
        if (isDebug()) {
            System.out.println(string);
        }
    }

    /**
     *
     * @param hashAula
     * @param numCasilla
     * @param esConflictiva
     */
    public void setCasillaConflictiva(String hashAula, int numCasilla, boolean esConflictiva) {
        if (esConflictiva) {
            if (!_casillasConflictivas.containsKey(hashAula)) {
                _casillasConflictivas.put(hashAula, new HashSet<Integer>());
            }
            _casillasConflictivas.get(hashAula).add(numCasilla);
        } else {
            _casillasConflictivas.get(hashAula).remove(numCasilla);
        }
    }

    /**
     *
     */
    public void clearConflictivos() {
        for (HashSet<Integer> aa : _casillasConflictivas.values()) {
            aa.clear();
        }
    }

    /**
     *
     * @return
     */
    public HashMap<String, HashSet<Integer>> getCasillasConflictivas() {
        return _casillasConflictivas;
    }

    /**
     *
     * @param hashAula
     * @param numCasilla
     */
    protected void marcaCasillaComoNOConflictiva(String hashAula, int numCasilla) {
        setCasillaConflictiva(hashAula, numCasilla, false);
    }

    /**
     *
     * @param hashAula
     * @param numCasilla
     */
    protected void marcaCasillaComoConflictiva(String hashAula, int numCasilla) {
        setCasillaConflictiva(hashAula, numCasilla, true);
    }

    /**
     *
     * @param sol
     * @param c
     */
    protected void marcaCasillaComoConflictiva(PosibleSolucion sol, Casilla c) {
        String hashAula = c.getHashAula();
        ListaCasillas lc = dataProyecto.getDatosPorAula(hashAula).getListaCasillas();
        int indiceCasilla = lc.getCasillas().indexOf(c);
        marcaCasillaComoConflictiva(hashAula, indiceCasilla);
    }

    /**
     *
     * @return
     */
    public boolean isMarcaCasillasConflictivas() {
        return marcaCasillasConflictivas;
    }

    /**
     *
     * @param marcaCasillasConflictivas
     */
    public void setMarcaCasillasConflictivas(boolean marcaCasillasConflictivas) {
        this.marcaCasillasConflictivas = marcaCasillasConflictivas;
    }

    /**
     *
     * @param l
     */
    public void addListener(RestriccionListener l) {
        listeners.add(l);
    }

    /**
     *
     * @param l
     */
    public void removeListener(RestriccionListener l) {
        listeners.remove(l);
    }

    /**
     *
     * @param needsReinicializarDatos
     */
    public void fireEventRestriccionChanged(Boolean needsReinicializarDatos) {
        for (RestriccionListener l : listeners) {
            l.restrictionChanged(this, needsReinicializarDatos);
        }
    }

    /**
     *
     * @param parent
     */
    public void writeRestriccion(Node parent) {
        Node nodo = creaNodoSimple(parent, "restriction");
        escribeNombreDeLaClase(nodo);
        Node nodoComun = creaNodoSimple(nodo, "common_config");
        Node nodo2 = creaNodoSimple(nodoComun, "level");
        creaNodoTexto(nodo2, this.getImportancia() + "");
        Node nodoEspecifico = creaNodoSimple(nodo, "specific_config");
        writeConfig(nodoEspecifico);
    }

    /**
     *
     * @param parent
     * @param nombre
     * @return
     */
    public Node creaNodoSimple(Node parent, String nombre) {
        return parent.appendChild(parent.getOwnerDocument().createElement(nombre));
    }

    /**
     *
     * @param parent
     * @param nombre
     * @return
     */
    public Node creaNodoTexto(Node parent, String nombre) {
        return parent.appendChild(parent.getOwnerDocument().createTextNode(nombre));
    }

    /**
     * Crea un nodo XML simple con valor dentro.
     *
     * @param parent Nodo padre
     * @param nombreNodo Nombre del nodo
     * @param contenido Valor que contiene el nodo
     * @return Nodo creado
     */
    public Node creaNodoSimpleConTexto(Node parent, String nombreNodo, String contenido) {
        return creaNodoTexto(creaNodoSimple(parent, nombreNodo), contenido);
    }

    /**
     * Sobrecarga para aceptar valores enteros
     *
     * @param parent Nodo padre
     * @param nombreNodo Nombre del nodo a crear
     * @param intValor Valor entero a incluir en el nodo, convertido en cadena
     * @return
     */
    public Node creaNodoSimpleConTexto(Node parent, String nombreNodo, int intValor) {
        return creaNodoSimpleConTexto(parent, nombreNodo, String.valueOf(intValor));
    }

    private void escribeNombreDeLaClase(Node parent) {
        Node nodo = creaNodoSimple(parent, "className");
        creaNodoTexto(nodo, this.getClass().getName());
    }

    //Clases abstractas para ser implementadas por cada subclase
    //Escribe/lee campos configuración dentro del nodo parent (usualmente <config>)
    /**
     *
     * @param parent
     */
    protected abstract void writeConfig(Node parent);

    /**
     *
     * @param parent
     */
    public abstract void readConfig(Element parent);

    /**
     *
     * @param parent
     * @param nombre
     * @return
     */
    public Element buscaPrimerElementoConNombre(Element parent, String nombre) {
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombre);
        Element resul;
        if (nodeList != null && nodeList.getLength() > 0) {
            resul = (Element) nodeList.item(0);
        } else {
            resul = null;
        }
        return resul;
    }

    /**
     *
     * @param parent
     * @param nombre
     * @return
     */
    public String valorPrimerElementoConNombre(Element parent, String nombre) {
        Element el = buscaPrimerElementoConNombre(parent, nombre);
        return el.getTextContent();
    }

    /**
     *
     * @return
     */
    public boolean isUnique() {
        return unique;
    }

    /**
     *
     * @param unique
     */
    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    /**
     *
     * @return
     */
    public HashSet<RestriccionListener> getListeners() {
        return listeners;
    }

    /**
     *
     * @param value
     */
    public void setDirty(boolean value) {
        try {
            dataProyecto.setDirty(value);
        } catch (NullPointerException e) {
        }
    }
}

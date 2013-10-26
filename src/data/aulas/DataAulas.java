/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.aulas;

import data.AbstractDataSets;
import data.DataProyecto;
import data.DataProyectoListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author david
 */
public class DataAulas extends AbstractDataSets implements Serializable {

    private ArrayList<Aula> aulas;
    //hashGrupoCurso--->Aula@M/T
    private final ArrayList<AulaMT> aulaContainers;
    private final HashMap<String, AulaMT> mapHashToContainers;

    /**
     *
     * @param dataProyecto
     */
    public DataAulas(DataProyecto dataProyecto) {
        super(dataProyecto);
        this.aulas = new ArrayList<Aula>();
        mapHashToContainers = new HashMap<String, AulaMT>();
        aulaContainers = new ArrayList<AulaMT>();
    }

    /**
     *
     * @return
     */
    public ArrayList<AulaMT> getAulaContainers() {
        return aulaContainers;
    }

    /**
     *
     * @return
     */
    public HashMap<String, AulaMT> getMapHashToContainers() {
        return mapHashToContainers;
    }

    /**
     *
     * @return
     */
    public ArrayList<Aula> getAulas() {
        return aulas;
    }


    /**
     *
     * @param aula
     */
    public void addAula(Aula aula) {
        this.aulas.add(aula);
        aula.setParent(this);
        getDataProyecto().getAsignacionAulas().addAula(aula);
        fireDataEvent(aula, DataProyectoListener.ADD);
        setDirty(true);
    }

    /**
     *
     * @param aula
     */
    public void removeAula(Aula aula) {
        this.aulas.remove(aula);
        aula.setParent(null);
        getDataProyecto().getAsignacionAulas().removeAula(aula);
        fireDataEvent(aula, DataProyectoListener.REMOVE);setDirty(true);
    }

    @Override
    public String toString() {
        return "Aulas";
    }

    /**
     *
     * @param nodeRoot
     */
    public void dataToDOM(Node nodeRoot) {

        for (Aula aul : this.aulas) {
            nodoAula(nodeRoot, aul);
        }
    }

    private void nodoAula(Node parent, Aula aula) {
        Element elemAula = parent.getOwnerDocument().createElement("aula");
        elemAula.setAttribute("nombre", aula.getNombre());
        Node nodeAula = parent.appendChild(elemAula);
    }

    /**
     *
     */
    public void clear() {
        aulas.clear();setDirty(true);
        fireDataEvent(null, DataProyectoListener.REMOVE);
    }



    private Aula buscaAula(String hashBuscado, Boolean esTarde) {
        Aula resul = null;
        for (Aula a : getDataProyecto().getDataAulas().getAulas()) {
            if (a.getHash(esTarde) == null ? hashBuscado == null : a.getHash(esTarde).equals(hashBuscado)) {
                resul = a;
                break;
            }
        }
        return resul;
    }

    
     /**
     *
     */
    public void buildArrayAulaContainers() {
        aulaContainers.clear();
        mapHashToContainers.clear();
        for (Aula aula : aulas) {
            AulaMT contMañana = new AulaMT(aula, false);
            aulaContainers.add(contMañana);
            mapHashToContainers.put(aula.getHash(false), contMañana);
            AulaMT contTarde = new AulaMT(aula, true);
            aulaContainers.add(contTarde);
            mapHashToContainers.put(aula.getHash(true), contTarde);
        }
    }
}

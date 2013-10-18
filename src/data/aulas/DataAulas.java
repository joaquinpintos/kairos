/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.aulas;

import data.AbstractDataSets;
import data.DataProyecto;
import data.DataProyectoListener;
import data.asignaturas.Grupo;
import data.genetic.DataGenerator;
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
    private HashMap<String, String> mapGruposCompletosToAulas;
    private ArrayList<AulaMañanaTardeContainer> aulaContainers;
    private HashMap<String, AulaMañanaTardeContainer> mapHashToContainers;

    /**
     *
     * @param dataProyecto
     */
    public DataAulas(DataProyecto dataProyecto) {
        super(dataProyecto);
        this.aulas = new ArrayList<Aula>();
        mapGruposCompletosToAulas = new HashMap<String, String>();
        mapHashToContainers = new HashMap<String, AulaMañanaTardeContainer>();
        aulaContainers = new ArrayList<AulaMañanaTardeContainer>();
    }

    /**
     *
     * @return
     */
    public ArrayList<AulaMañanaTardeContainer> getAulaContainers() {
        return aulaContainers;
    }

    /**
     *
     * @return
     */
    public HashMap<String, AulaMañanaTardeContainer> getMapHashToContainers() {
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

    /**
     *
     * @return
     */
    public HashMap<String, String> getMapGruposCompletosToAulas() {
        return mapGruposCompletosToAulas;
    }

    /**
     *
     * @param hashGrupoCurso
     * @param hashAula
     */
    public void addGrupoCompletoToAula(String hashGrupoCurso, String hashAula) {
        if (mapGruposCompletosToAulas == null) {
            mapGruposCompletosToAulas = new HashMap<String, String>();
        }
        mapGruposCompletosToAulas.put(hashGrupoCurso, hashAula);
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

    /**
     *
     * @param parent
     */
    public void asignacionAulasToDOM(Node parent) {
        Element elAsigAulas = parent.getOwnerDocument().createElement("asignacion_aulas");
        Node nodeAsigAulas = parent.appendChild(elAsigAulas);
        Element asig;
        DataGenerator dataGenerator = new DataGenerator(getDataProyecto().getMinutosPorCasilla(), getDataProyecto());
        dataGenerator.calculaHashMapDeAsignacionesAulasAGrupos();
        for (String keyGrupo : mapGruposCompletosToAulas.keySet()) {
            asig = parent.getOwnerDocument().createElement("asignacionGrupoCompleto");
            asig.setAttribute("grupocurso", keyGrupo);
            asig.setAttribute("aula", mapGruposCompletosToAulas.get(keyGrupo));
            nodeAsigAulas.appendChild(asig);

        }
    }

    /**
     *
     * @param hashGrupoCurso
     * @param hashAula
     */
    public void asignaGrupoCompletoToAula(String hashGrupoCurso, String hashAula) {
        Aula aula;
        Boolean esTarde = hashAula.contains("@T");
        aula = buscaAula(hashAula, esTarde);

        //Ahora que tengo el aula, lo relleno con todos los grupos que tengan ese hash
        for (Grupo gr : getDataProyecto().getDataAsignaturas().getAllGrupos()) {
            if (gr.getHashCarreraGrupoCurso() == null ? hashGrupoCurso == null : gr.getHashCarreraGrupoCurso().equals(hashGrupoCurso)) {
                CarreraCursoGrupoContainer cont = new CarreraCursoGrupoContainer(hashGrupoCurso, gr.getNombreConCarrera());
                aula.asignaGrupo(gr, esTarde);
            }setDirty(true);
        }
    }

    private Aula buscaAula(String hashBuscado, Boolean esTarde) {
        Aula resul = null;
        MainLoop:
        for (Aula a : getDataProyecto().getDataAulas().getAulas()) {
            if (a.getHash(esTarde) == null ? hashBuscado == null : a.getHash(esTarde).equals(hashBuscado)) {
                resul = a;
                break MainLoop;
            }
        }
        return resul;
    }

    /**
     *
     * @param hash
     * @param aulaNombre
     */
    public void quitaAsignacionGrupoCompletoFromAula(String hash, String aulaNombre) {
        for (Aula aula : aulas) {
            if (aula.getNombre() == null ? aulaNombre == null : aula.getNombre().equals(aulaNombre)) {
                aula.quitaAsignacionesDeMañanaConHash(hash);
                aula.quitaAsignacionesDeTardeConHash(hash);
            }
        }setDirty(true);
    }
    
     /**
     *
     */
    public void buildArrayAulaContainers() {
        aulaContainers.clear();
        mapHashToContainers.clear();
        for (Aula aula : aulas) {
            AulaMañanaTardeContainer contMañana = new AulaMañanaTardeContainer(aula, false);
            aulaContainers.add(contMañana);
            mapHashToContainers.put(aula.getHash(false), contMañana);
            AulaMañanaTardeContainer contTarde = new AulaMañanaTardeContainer(aula, true);
            aulaContainers.add(contTarde);
            mapHashToContainers.put(aula.getHash(true), contTarde);
        }
    }
}

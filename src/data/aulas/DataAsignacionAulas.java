/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.aulas;

import data.DataProject;
import data.DataProyectoListener;
import data.asignaturas.Grupo;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DataAsignacionAulas implements Serializable {

    private static final long serialVersionUID = 27112013L;
    private Document documentoXML;
    private File lastFileUsed;
    private final ArrayList<DataProyectoListener> listeners;
    final HashMap<Aula, HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>> asignacionesMañana;
    final HashMap<Aula, HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>> asignacionesTarde;
    private final DataProject dataProyecto;

    /**
     *
     * @param dataProyecto
     */
    public DataAsignacionAulas(DataProject dataProyecto) {

        asignacionesMañana = new HashMap<Aula, HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>>();
        asignacionesTarde = new HashMap<Aula, HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>>();
        listeners = new ArrayList<DataProyectoListener>();
        this.dataProyecto = dataProyecto;
    }

    /**
     *
     * @param aula
     * @param hashContainer
     * @param esTarde
     */
    public void addHashToAula(Aula aula, CarreraCursoGrupoContainer hashContainer, Boolean esTarde) {
        if (esTarde) {
            if (asignacionesMañana.get(aula).containsKey(hashContainer)) {
                asignacionesMañana.get(aula).remove(hashContainer);
            }
            if (!asignacionesTarde.get(aula).containsKey(hashContainer)) {
                asignacionesTarde.get(aula).put(hashContainer, new ArrayList<Grupo>());
            }
        } else {
            if (asignacionesTarde.get(aula).containsKey(hashContainer)) {
                asignacionesTarde.get(aula).remove(hashContainer);
            }
            if (!asignacionesMañana.get(aula).containsKey(hashContainer)) {
                asignacionesMañana.get(aula).put(hashContainer, new ArrayList<Grupo>());
            }
        }
        setDirty(true);
    }

    /**
     *
     * @param aula
     * @param hashContainer
     */
    public void removeHashFromAula(Aula aula, CarreraCursoGrupoContainer hashContainer) {
        if (asignacionesMañana.get(aula).containsKey(hashContainer)) {
            asignacionesMañana.get(aula).remove(hashContainer);
        }
        if (asignacionesTarde.get(aula).containsKey(hashContainer)) {
            asignacionesTarde.get(aula).remove(hashContainer);
        }
        setDirty(true);
    }

    /**
     *
     * @param gr
     */
    public void addGrupo(Grupo gr) {//Aquí no me preocupo si está por la tarde o por la mañana!
        mainLoop:
        for (Aula a : asignacionesMañana.keySet()) {
            for (CarreraCursoGrupoContainer cont : asignacionesMañana.get(a).keySet()) {
                if (gr.getHashCarreraGrupoCurso().equals(cont.getHash())) {
                    if (asignacionesMañana.get(a).get(cont) == null) {
                        asignacionesMañana.get(a).put(cont, new ArrayList<Grupo>());
                    }
                    asignacionesMañana.get(a).get(cont).add(gr);
                    break mainLoop;
                }
            }
        }
        mainLoop:
        for (Aula a : asignacionesTarde.keySet()) {
            for (CarreraCursoGrupoContainer cont : asignacionesTarde.get(a).keySet()) {
                if (gr.getHashCarreraGrupoCurso().equals(cont.getHash())) {
                    if (asignacionesTarde.get(a).get(cont) == null) {
                        asignacionesTarde.get(a).put(cont, new ArrayList<Grupo>());
                    }
                    asignacionesTarde.get(a).get(cont).add(gr);
                    break mainLoop;
                }
            }
        }
        setDirty(true);

    }

    /**
     *
     * @param gr
     */
    public void removeGrupo(Grupo gr) {
        mainLoop:
        for (Aula a : asignacionesMañana.keySet()) {
            for (CarreraCursoGrupoContainer cont : asignacionesMañana.get(a).keySet()) {
                if (gr.getHashCarreraGrupoCurso().equals(cont.getHash())) {
                    asignacionesMañana.get(a).get(cont).remove(gr);
                    if (asignacionesMañana.get(a).get(cont).isEmpty()) {
                        asignacionesMañana.get(a).remove(cont);
                    }
                    break mainLoop;
                }
            }
        }
        mainLoop:
        for (Aula a : asignacionesTarde.keySet()) {
            for (CarreraCursoGrupoContainer cont : asignacionesTarde.get(a).keySet()) {
                if (gr.getHashCarreraGrupoCurso().equals(cont.getHash())) {
                    asignacionesTarde.get(a).get(cont).remove(gr);
                    if (asignacionesTarde.get(a).get(cont).isEmpty()) {
                        asignacionesTarde.get(a).remove(cont);
                    }
                    break mainLoop;
                }
            }
        }
        setDirty(true);
    }

    /**
     *
     */
    public void clear() {
        asignacionesMañana.clear();
        asignacionesTarde.clear();
        setDirty(true);
    }

    /**
     * Método sobrecargado. Busca el aula y los grupos correspondientes. Lento
     * pero compatible!!
     *
     * @param hashAula
     * @param hashGrupoCurso
     */
    public void addHashToAula(String hashAula, String hashGrupoCurso) {
        //Primero busco el aula en cuestión, y veo si es tarde o mañana
        Aula aula;
        HashMap<Aula, HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>> asignaciones;
        Boolean esTarde = hashAula.contains("@T");
        if (esTarde)//Es tarde
        {
            asignaciones = asignacionesTarde;

        } else {
            asignaciones = asignacionesMañana;
        }
        aula = buscaAula(asignaciones, hashAula, esTarde);
        //Ahora que tengo el aula, lo relleno con todos los grupos que tengan ese hash
        for (Grupo gr : dataProyecto.getDataAsignaturas().getAllGrupos()) {
            if (gr.getHashCarreraGrupoCurso() == null ? hashGrupoCurso == null : gr.getHashCarreraGrupoCurso().equals(hashGrupoCurso)) {
                CarreraCursoGrupoContainer cont = new CarreraCursoGrupoContainer(hashGrupoCurso, gr.getNombreConCarrera());
                //  if (!asignaciones.containsKey(aula)) asignaciones.put(aula,new HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>());
                if (!asignaciones.get(aula).containsKey(cont)) {
                    asignaciones.get(aula).put(cont, new ArrayList<Grupo>());
                }
                asignaciones.get(aula).get(cont).add(gr);
            }
        }

        setDirty(true);

    }

    private Aula buscaAula(HashMap<Aula, HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>> asignaciones, String hashBuscado, Boolean esTarde) {
        Aula resul = null;
        for (Aula a : dataProyecto.getDataAulas().getAulas()) {
            if (a.getHash(esTarde) == null ? hashBuscado == null : a.getHash(esTarde).equals(hashBuscado)) {
                resul = a;
                break;
            }
        }
        return resul;
    }

    @Override
    public String toString() {
        return "AsignacionAulas{" + "asignacionesMañana=" + asignacionesMañana + ", asignacionesTarde=" + asignacionesTarde + '}';
    }

    void addAula(Aula aula) {
        if (!asignacionesMañana.containsKey(aula)) {
            asignacionesMañana.put(aula, new HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>());
        }
        if (!asignacionesTarde.containsKey(aula)) {
            asignacionesTarde.put(aula, new HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>());
        }
        setDirty(true);
        fireDataEvent(aula, DataProyectoListener.ADD);
    }

    void removeAula(Aula aula) {
        if (!asignacionesMañana.containsKey(aula)) {
            asignacionesMañana.remove(aula);
        }
        if (!asignacionesTarde.containsKey(aula)) {
            asignacionesTarde.remove(aula);
        }
        setDirty(true);
        fireDataEvent(aula, DataProyectoListener.REMOVE);
    }

    /**
     *
     * @return
     */
    public HashMap<Aula, HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>> getAsignacionesMañana() {
        return asignacionesMañana;
    }

    /**
     *
     * @return
     */
    public HashMap<Aula, HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>> getAsignacionesTarde() {
        return asignacionesTarde;
    }

    /**
     *
     * @param l
     */
    public void addListener(DataProyectoListener l) {
        listeners.add(l);
    }

    /**
     *
     * @param l
     */
    public void removeListener(DataProyectoListener l) {
        listeners.remove(l);
    }

    /**
     *
     * @param data
     * @param type
     */
    public void fireDataEvent(Object data, int type) {
        for (DataProyectoListener l : listeners) {
            l.dataEvent(data, type);
        }
    }

    /**
     *
     * @return
     */
    public Document getDocumentoXML() {
        return documentoXML;
    }

    /**
     *
     * @param documentoXML
     */
    public void setDocumentoXML(Document documentoXML) {
        this.documentoXML = documentoXML;
    }

    /**
     *
     * @return
     */
    public File getLastFileUsed() {
        return lastFileUsed;
    }

    /**
     *
     * @param lastFileUsed
     */
    public void setLastFileUsed(File lastFileUsed) {
        this.lastFileUsed = lastFileUsed;
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
     * @param value
     */
    public void setDirty(boolean value) {
        try {
            dataProyecto.setDirty(value);
        } catch (NullPointerException e) {
        }
    }
}

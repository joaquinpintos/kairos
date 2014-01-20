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
package data.aulas;

import data.DataProject;
import data.DataProjectListener;
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
    private final ArrayList<DataProjectListener> listeners;
    final HashMap<Aula, HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>> asignacionesMañana;
    final HashMap<Aula, HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>> asignacionesTarde;
    private final DataProject dataProject;

    /**
     *
     * @param dataProject
     */
    public DataAsignacionAulas(DataProject dataProject) {

        asignacionesMañana = new HashMap<Aula, HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>>();
        asignacionesTarde = new HashMap<Aula, HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>>();
        listeners = new ArrayList<DataProjectListener>();
        this.dataProject = dataProject;
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
    }

    /**
     *
     */
    public void clear() {
        asignacionesMañana.clear();
        asignacionesTarde.clear();
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
        for (Grupo gr : dataProject.getDataAsignaturas().getAllGrupos()) {
            if (gr.getHashCarreraGrupoCurso() == null ? hashGrupoCurso == null : gr.getHashCarreraGrupoCurso().equals(hashGrupoCurso)) {
                CarreraCursoGrupoContainer cont = new CarreraCursoGrupoContainer(hashGrupoCurso, gr.getNombreConCarrera());
                //  if (!asignaciones.containsKey(aula)) asignaciones.put(aula,new HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>());
                if (!asignaciones.get(aula).containsKey(cont)) {
                    asignaciones.get(aula).put(cont, new ArrayList<Grupo>());
                }
                asignaciones.get(aula).get(cont).add(gr);
            }
        }

    }

    private Aula buscaAula(HashMap<Aula, HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>> asignaciones, String hashBuscado, Boolean esTarde) {
        Aula resul = null;
        for (Aula a : dataProject.getDataAulas().getAulas()) {
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

    public void addAula(Aula aula) {
        if (!asignacionesMañana.containsKey(aula)) {
            asignacionesMañana.put(aula, new HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>());
        }
        if (!asignacionesTarde.containsKey(aula)) {
            asignacionesTarde.put(aula, new HashMap<CarreraCursoGrupoContainer, ArrayList<Grupo>>());
        }
        fireDataEvent(aula, DataProjectListener.ADD);
    }

    public void removeAula(Aula aula) {
        if (!asignacionesMañana.containsKey(aula)) {
            asignacionesMañana.remove(aula);
        }
        if (!asignacionesTarde.containsKey(aula)) {
            asignacionesTarde.remove(aula);
        }
        fireDataEvent(aula, DataProjectListener.REMOVE);
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
    public void addListener(DataProjectListener l) {
        listeners.add(l);
    }

    /**
     *
     * @param l
     */
    public void removeListener(DataProjectListener l) {
        listeners.remove(l);
    }

    /**
     *
     * @param data
     * @param type
     */
    public void fireDataEvent(Object data, int type) {
        for (DataProjectListener l : listeners) {
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
        return dataProject;
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.aulas;

import gui.DatosEditor.Aulas.HashToGroupContainer;
import data.asignaturas.Grupo;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author david
 */
public class Aula implements Serializable {

    String nombre;
    private ListaAsignaciones asignacionesMañana;
    private ListaAsignaciones asignacionesTarde;
    private DataAulas parent;

    /**
     *
     * @param nombre
     */
    public Aula(String nombre) {
        this.nombre = nombre;
        asignacionesMañana = new ListaAsignaciones(false, 0, this);
        asignacionesTarde = new ListaAsignaciones(true, 1, this);
    }

    /**
     *
     * @return
     */
    public boolean tieneAsignaciones() {
        return !(asignacionesMañana.isEmpty() && asignacionesTarde.isEmpty());
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
        setDirty(true);
    }

    @Override
    public String toString() {
        String resul = "";
        resul += "Aula " + this.nombre;
        return resul;
    }

    /**
     *
     * @param tarde
     * @return
     */
    public String getHash(boolean tarde) {
        String resul = this.nombre + "@";
        resul += tarde ? "T" : "M";
        return resul;
    }

    void asignaGrupo(Grupo gr, Boolean esTarde) {
        //Primero miro a cuál de las asignaciones tengo que meter el grupo
        ListaAsignaciones asignaciones;
        if (esTarde) {
            asignaciones = asignacionesTarde;
        } else {
            asignaciones = asignacionesMañana;
        }
        String hash = gr.getHashCarreraGrupoCurso();
        Boolean encontrado = false;
        for (int n = 0; n < asignaciones.size(); n++) {
            HashToGroupContainer hashCont = asignaciones.get(n);
            if (hashCont.getHash() == null ? gr.getHashCarreraGrupoCurso() == null : hashCont.getHash().equals(gr.getHashCarreraGrupoCurso())) {
                encontrado = true;
                hashCont.addGrupo(gr);
            }
        }
        //Aun no hay un contenedor de grupos con ese hash, creo uno
        if (!encontrado) {
            asignaciones.add(new HashToGroupContainer(gr, this));
        }

        setDirty(true);

    }

    /**
     *
     * @return
     */
    public ListaAsignaciones getAsignacionesMañana() {
        return asignacionesMañana;
    }

    /**
     *
     * @return
     */
    public ListaAsignaciones getAsignacionesTarde() {
        return asignacionesTarde;
    }

    /**
     *
     * @return
     */
    public HashMap<String, String> getMapAsignacionesAulasAGrupos() {
        HashMap<String, String> resul = new HashMap<String, String>();
        for (HashToGroupContainer cont : asignacionesMañana.getHashToGroupContainers()) {
            resul.put(cont.getHash(), this.getHash(false));
        }
        for (HashToGroupContainer cont : asignacionesTarde.getHashToGroupContainers()) {
            resul.put(cont.getHash(), this.getHash(true));
        }
        return resul;
    }

    void quitaAsignacionesDeMañanaConHash(String hash) {
        quitaAsignacionesConHash(asignacionesMañana, hash);
        setDirty(true);
    }

    void quitaAsignacionesDeTardeConHash(String hash) {
        quitaAsignacionesConHash(asignacionesTarde, hash);
        setDirty(true);
    }

    void quitaAsignacionesConHash(ListaAsignaciones asignaciones, String hash) {
        for (HashToGroupContainer hg : asignaciones.hashToGroupContainers) {
            if (hg.getHash().equals(hash)) {
                asignaciones.hashToGroupContainers.remove(hg);
                break;
            }
        }
        setDirty(true);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Aula other = (Aula) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param parent
     */
    public void setParent(DataAulas parent) {
        this.parent = parent;
    }

    /**
     *
     * @param value
     */
    public void setDirty(boolean value) {
        try {
        parent.setDirty(value);
        } catch (NullPointerException e) {
        }
    }
}

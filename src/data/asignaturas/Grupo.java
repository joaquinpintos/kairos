/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.DataProyectoListener;
import data.aulas.Aula;
import data.aulas.AulaMT;
import data.profesores.Profesor;
import java.io.Serializable;

/**
 * Representa un grupo simple, con sus tramos asociados.
 *
 * @author david
 */
public class Grupo implements Serializable, Comparable<Grupo>, Teachable {

    private static final long serialVersionUID = 1L;
    private String nombre;
    private final GrupoTramos tramosGrupoCompleto;
    private Asignatura parent;
    private boolean tarde;
    private Aula aulaAsignada;
    private boolean algunoSinAula;

    /**
     *
     * @param nombre
     */
    public Grupo(String nombre) {
        this.nombre = nombre;
        this.tramosGrupoCompleto = new GrupoTramos(this);
        this.tarde = false;
        algunoSinAula = true;
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
    }

    /**
     *
     * @return
     */
    public GrupoTramos getTramosGrupoCompleto() {
        return tramosGrupoCompleto;
    }

    /**
     *
     * @param tr
     */
    public void addTramoGrupoCompleto(Tramo tr) {
        tramosGrupoCompleto.add(tr);
        tr.setParent(tramosGrupoCompleto);
        updateAsigAulaStatus();
        setDirty(true);
        fireDataEvent(tr, DataProyectoListener.ADD);
    }

    /**
     *
     * @param tr
     */
    public void removeTramoGrupoCompleto(Tramo tr) {
        tr.removeAula();
        tr.removeDocente();
        tramosGrupoCompleto.remove(tr);
        tr.setParent(null);
        updateAsigAulaStatus();
        setDirty(true);
        fireDataEvent(tr, DataProyectoListener.REMOVE);
    }

    @Override
    public String toString() {
        String salida;
        salida = "Grupo " + this.nombre + ":  ";
        salida += tramosGrupoCompleto.toString();
        return salida;
    }

    /**
     *
     * @return
     */
    public Asignatura getParent() {
        return parent;
    }

    /**
     *
     * @param asignatura
     */
    public void setParent(Asignatura asignatura) {
        this.parent = asignatura;
    }

    /**
     *
     * @return
     */
    public double getTotalHoras() {
        return tramosGrupoCompleto.getTotalHoras();

    }

    //AL asignar la docencia a este profesor, actualizo el estado de la asignatura 
    //a la que pertenece
    /**
     *
     * @param profesor
     */
    @Override
    public void setDocente(Profesor profesor) {
//        this.profesor = profesor;
        for (Tramo tr : tramosGrupoCompleto.getTramos()) {
            tr.setDocente(profesor);
        }
    }

    /**
     *
     * @return
     */
    public boolean isTarde() {
        return tarde;
    }

    /**
     *
     * @param tarde
     */
    public void setTarde(boolean tarde) {
        this.tarde = tarde;
    }

    //TODO: En la edición manual se podría mover horarios item de un sitio a otro
    /**
     *
     * @return
     */
    public String getHashCarreraGrupoCurso() {//Ejemplo  Primaria@1@Matematicas@A
//        if (!isLibre()) {
        return this.getParent().getParent().getHash() + "@" + this.nombre;
//        } else {
//            return "";
//        }
    }

    /**
     *
     * @return
     */
    public String getNombreConCarrera() {

        return this.getParent().getParent().getParent() + " - " + this.getParent().getParent() + " - Grupo " + this.getNombre();

    }
    public String getNombreGrupoCursoYCarrera(){
        return "G"+this.getNombre()+" "+this.getParent().getParent().getNombre()+" "+this.getParent().getParent().getParent().toString();
    }

    /**
     *
     * @return
     */
    public Aula getAulaAsignada() {
        return aulaAsignada;
    }

    /**
     *
     * @param aulaAsignada
     */
    public void setAulaAsignada(Aula aulaAsignada) {
        this.aulaAsignada = aulaAsignada;
    }

    /**
     *
     */
    public void clearTramos() {
        tramosGrupoCompleto.getTramos().clear();
    }

    @Override
    public int compareTo(Grupo o) {
        return this.nombre.compareTo(o.getNombre());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 89 * hash + (this.parent != null ? this.parent.hashCode() : 0);
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
        final Grupo other = (Grupo) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        if (this.parent != other.parent && (this.parent == null || !this.parent.equals(other.parent))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param value
     */
    public void setDirty(boolean value) {
        if (parent != null) {
            parent.setDirty(value);
        }
    }

    /**
     *
     */
    @Override
    public void removeDocente() {
        for (Tramo tr : tramosGrupoCompleto.getTramos()) {
            tr.removeDocente();
        }
    }

    /**
     *
     * @param aula
     */
    @Override
    public void asignaAula(AulaMT aula) {
        for (Tramo tr : getTramosGrupoCompleto().getTramos()) {
            tr.asignaAula(aula, true);
        }
    }

    /**
     *
     */
    @Override
    public void removeAula() {
        for (Tramo tr : getTramosGrupoCompleto().getTramos()) {
            tr.removeAula();
        }
        updateAsigAulaStatus();
    }

    /**
     *
     */
    public void updateAsigAulaStatus() {
        if (tramosGrupoCompleto.algunoSinAula() != algunoSinAula) {
            algunoSinAula = tramosGrupoCompleto.algunoSinAula();
            if (parent != null) {
                parent.updateAsigAulaStatus();
            }
        }

    }

    /**
     *
     * @return
     */
    public boolean algunoSinAula() {
        return algunoSinAula;
    }

    /**
     *
     * @param obj
     * @param type
     */
    public void fireDataEvent(Object obj, int type) {
        try {
            getParent().fireDataEvent(obj, type);
        } catch (NullPointerException e) {
        }
    }
}

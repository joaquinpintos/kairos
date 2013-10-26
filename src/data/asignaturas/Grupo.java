/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

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
        setDirty(true);
    }

    /**
     *
     * @param tr
     */
    public void removeTramoGrupoCompleto(Tramo tr) {
        tramosGrupoCompleto.remove(tr);
        setDirty(true);
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

    @Override
    public void removeDocente() {
        for (Tramo tr : tramosGrupoCompleto.getTramos()) {
            tr.removeDocente();
        }
    }

    @Override
    public void asignaAula(AulaMT aula) {
        for (Tramo tr : getTramosGrupoCompleto().getTramos()) {
            tr.asignaAula(aula, true);
        }
    }

    @Override
    public void removeAula() {
        for (Tramo tr : getTramosGrupoCompleto().getTramos()) {
            tr.removeAula();
        }
    }

    public void updateAsigAulaStatus() {
        if (tramosGrupoCompleto.algunoSinAula() != algunoSinAula) {
            algunoSinAula = tramosGrupoCompleto.algunoSinAula();
        parent.updateAsigAulaStatus();
        }
        
    }
  public boolean algunoSinAula() {
        return algunoSinAula;
    }
}

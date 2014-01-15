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
import java.util.ArrayList;

/**
 * Representa un grupo de alumnos, con sus tramos asociados. Cada grupo se
 * representa por un nombre (1,2,3, A, B, C,...) y pertenece a una
 * {@link Asignatura}. A su vez cada grupo contiene una serie de tramos de
 * docencia, almacenados en un objeto del tipo {@link GrupoTramos}.
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class Grupo implements Serializable, Comparable<Grupo>, Teachable {

    private static final long serialVersionUID = 27112013L;
    private String nombre;
    private final GrupoTramos tramosGrupoCompleto;
    private Asignatura parent;
    private boolean tarde;
    private Aula aulaAsignada;
    private boolean algunoSinAula;

    /**
     * Constructor. Devuelve un grupo nuevo con nombre dado.
     *
     * @param nombre
     */
    public Grupo(String nombre) throws IllegalArgumentException {
        setNombre(nombre);
        this.tramosGrupoCompleto = new GrupoTramos(this);
        this.tarde = false;
        algunoSinAula = true;
    }

    /**
     * Devuelve el nombre
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre del grupo. No puede contener la letra @
     *
     * @param nombre
     */
    public final void setNombre(String nombre) throws IllegalArgumentException {
        if (!nombre.contains("@")) {
            this.nombre = nombre;
        } else {
            throw new IllegalArgumentException("El nombre del grupo no puede contener @");
        }
    }

    /**
     * Devuelve objeto {@link GrupoTramos} para los tramos de grupo completo.
     *
     * @return
     */
    public GrupoTramos getTramosGrupoCompleto() {
        return tramosGrupoCompleto;
    }

    /**
     * Añade un nuevo {@link Tramo} a los tramos para el grupo completo.
     *
     * @param tramo Tramo a añadir
     */
    public void addTramoGrupoCompleto(Tramo tramo) {
        tramosGrupoCompleto.add(tramo);
        tramo.setParent(tramosGrupoCompleto);
        updateAsigAulaStatus();
        setDirty(true);
        fireDataEvent(tramo, DataProyectoListener.ADD);
    }

    /**
     * Elimina el tramo del grupo de tramos para grupo completo.
     *
     * @param tramo
     */
    public void removeTramoGrupoCompleto(Tramo tramo) {
        tramo.removeAula();
        tramo.removeDocente();
        tramosGrupoCompleto.remove(tramo);
        tramo.setParent(null);
        updateAsigAulaStatus();
        setDirty(true);
        fireDataEvent(tramo, DataProyectoListener.REMOVE);
    }

    @Override
    public String toString() {
        String salida;
        salida = "Grupo " + this.nombre + ":  ";
        salida += tramosGrupoCompleto.toString();
        return salida;
    }

    public String toStringSinTotales() {
        String salida;
        salida = "Grupo " + this.nombre;
        return salida;
    }

    /**
     * @return Asignatura a la que pertenece.
     */
    public Asignatura getParent() {
        return parent;
    }

    /**
     * Cambia la asignatura a la que pertenece.
     *
     * @param asignatura
     */
    public void setParent(Asignatura asignatura) {
        this.parent = asignatura;
    }

    /**
     *
     * @return Total de horas de docencia del grupo, sumando todos los tramos.
     */
    public double getTotalHoras() {
        return tramosGrupoCompleto.getTotalHoras();

    }


    /**
     *
     * @return True si el grupo da sus clases por la tarde. False en caso
     * contrario.
     */
    public boolean isTarde() {
        return tarde;
    }

    /**
     * Establece si el grupo es de tarde o no.
     *
     * @param tarde True si el grupo da sus clases por la tarde. False en caso
     * contrario.
     */
    public void setTarde(boolean tarde) {
        this.tarde = tarde;
    }

    /**
     * Devuelve un hash que identifica de forma unívoca el {@link Grupo}, {@link Asignatura},
     * {@link Curso} y {@link Carrera} al que pertenece. Ejemplo:
     * Primaria@1@Matematicas@A
     *
     * @return Hash de la forma carrera@curso@asignatura@grupo
     */
    public String getHashCarreraGrupoCurso() {
        Asignatura a = this.getParent();
        Curso b = a.getParent();
        return b.getHash() + "@" + this.nombre;
    }

    /**
     * Devuelve una cadena formateada con el {@link Grupo},
     * {@link Curso} y {@link Carrera} al que pertenece. Ejemplo: Primaria -1º -
     * Grupo 1
     *
     * @return Cadena de la forma carrera - curso - grupo
     */
    public String getNombreConCarrera() {

        return this.getParent().getParent().getParent() + " - " + this.getParent().getParent() + " - Grupo " + this.getNombre();

    }

    /**
     * Devuelve una cadena formateada con el {@link Grupo},
     * {@link Curso} y {@link Carrera} al que pertenece. Ejemplo: G1 1º Primaria
     *
     * @return Cadena de la forma Ggrupo curso carrera
     */
    public String getNombreGrupoCursoYCarrera() {
        return "G" + this.getNombre() + " " + this.getParent().getParent().getNombre() + " " + this.getParent().getParent().getParent().toString();
    }

    /**
     * Borrra todos los tramos asignados
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
     * Marca el grupo como modificado (con efectos a la hora de guardar cambios
     * o no)
     *
     * @param value True si el grupo ha sufrido modificaciones. False en caso
     * contrario.
     */
    public void setDirty(boolean value) {
        if (parent != null) {
            parent.setDirty(value);
        }
    }


    /**
     * Asigna aula al grupo. Este procedimiento asigna recursivamente aula a
     * todos los tramos asociados al grupo.
     *
     * @param aula Aula a asignar.
     */
    public void asignaAula(AulaMT aula) {
        for (Tramo tr : getTramosGrupoCompleto().getTramos()) {
            tr.asignaAula(aula, true);
        }
    }

    /**
     * Elimina aula del grupo. Este procedimiento elimina recursivamente el aula
     * a todos los tramos asociados al grupo.
     *
     */
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

    public boolean algunoSinAula() {
        return algunoSinAula;
    }
     public boolean tieneAula() {
        return !algunoSinAula;
    }


    public void setAlgunoSinAula(boolean value) {
        this.algunoSinAula = value;
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

    void clearDocente() {
        for (Tramo tr : tramosGrupoCompleto.getTramos()) {
            tr.removeDocente();
        }
    }

    void clearAulasAsignadas() {
        for (Tramo tr : tramosGrupoCompleto.getTramos()) {
            tr.removeAula();
        }
    }

    void removeAllTramos() {
        ArrayList<Tramo> tramosClone = (ArrayList<Tramo>) tramosGrupoCompleto.getTramos().clone();
        for (Tramo tr : tramosClone) {
            removeTramoGrupoCompleto(tr);
        }
    }

    public void copyBasicValuesFrom(Grupo grNew) {
        this.nombre=grNew.getNombre();
    }
}

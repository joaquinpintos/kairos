/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.aulas;

import data.asignaturas.Tramo;
import data.horarios.HorarioItem;
import java.io.Serializable;

/**
 * Esta clase encapsula un aula y un turno de mañana/tarde
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class AulaMT implements Serializable, Comparable<AulaMT> {

    private static final long serialVersionUID = 1L;
    private Aula aula;
    private Boolean esTarde;
    private int markType;

    /**
     *
     * @param aula
     * @param esTarde
     */
    public AulaMT(Aula aula, boolean esTarde) {
        this.aula = aula;
        this.esTarde = esTarde;
        markType = HorarioItem.NO_MARK;
    }

    /**
     *
     * @return
     */
    public String getHash() {
        return aula.getHash(esTarde);
    }

    /**
     *
     * @return
     */
    public Aula getAula() {
        return aula;
    }

    /**
     *
     * @param aula
     */
    public void setAula(Aula aula) {
        this.aula = aula;
    }

    /**
     *
     * @return
     */
    public Boolean getEsTarde() {
        return esTarde;
    }

    /**
     *
     * @param esTarde
     */
    public void setEsTarde(Boolean esTarde) {
        this.esTarde = esTarde;
    }

    @Override
    public String toString() {
        return "Aula " + aula.getNombre() + " " + (esTarde ? "Tarde" : "Mañana");
    }

    /**
     *
     * @return
     */
    public int getTieneSegmentosConflictivos() {
        return markType;
    }

    /**
     *
     * @param markType
     */
    public void setTieneSegmentosConflictivos(int markType) {
        this.markType = markType;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.aula != null ? this.aula.hashCode() : 0);
        hash = 41 * hash + (this.esTarde != null ? this.esTarde.hashCode() : 0);
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
        final AulaMT other = (AulaMT) obj;
        if (this.aula != other.aula && (this.aula == null || !this.aula.equals(other.aula))) {
            return false;
        }
        if (this.esTarde != other.esTarde && (this.esTarde == null || !this.esTarde.equals(other.esTarde))) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(AulaMT o) {
        return this.toString().compareTo(o.toString());
    }

    /**
     *
     * @param aThis
     */
    public void asignaTramo(Tramo aThis) {
        ListaAsignaciones asig;
        if (esTarde) {
            asig = aula.getAsignacionesTarde();
        } else {
            asig = aula.getAsignacionesMañana();
        }
        asig.add(aThis);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.DataProyectoListener;
import data.aulas.AulaMT;
import data.profesores.Profesor;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class Asignatura implements Serializable, Comparable<Asignatura>, Teachable {

    private static final long serialVersionUID = 27112013L;
    private String nombre; //Nombre completo
    private String nombreCorto;//Nombre corto del curso
    private Curso parent;//Curso al que pertenece
    private final ListaGrupos grupos;
    //Profesores que imparten esta asignatura
    //Deben ser el mísmo número que el número de grupos. Puede haber repetidos.
    private Color colorEnTablaDeHorarios;
    //En este array guardo los grupos que NO tengan asignada docencia
    private int numCreditos;
    private boolean algunoSinAula;

    /**
     *
     * @param nombre
     */
    public Asignatura(String nombre) throws IllegalArgumentException {
        this.numCreditos = 0;
        setNombre(nombre);
        this.nombreCorto = "";
        this.grupos = new ListaGrupos();
        colorEnTablaDeHorarios = new Color(220, 241, 182);//Color inicial. En principio no se usa.
        algunoSinAula = true;
    }

    public void addGrupo(Grupo gr) {
        grupos.getGrupos().add(gr);
    }

    /**
     *
     * @param gr
     */
    public void createGrupo(Grupo gr) {
        this.grupos.addGrupo(gr);
        gr.setParent(this);
        ordenaGrupos();
        setDirty(true);
        //Disparo evento de creación de grupo
        updateAsigAulaStatus();
        fireDataEvent(gr, DataProyectoListener.ADD);
    }

    public void ordenaGrupos() {
        Collections.sort(grupos.getGrupos());
    }

    public void removeGrupo(Grupo gr) {
        grupos.getGrupos().remove(gr);
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
    public final void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre.contains("@")) {
            throw new IllegalArgumentException("El nombre de la asignatura no puede contener el carácter @");
        } else {
            this.nombre = nombre;
        }
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public Curso getParent() {
        return parent;
    }

    /**
     *
     * @return
     */
    public String getNombreCorto() {
        return nombreCorto;
    }

    /**
     *
     * @param nombreCorto
     */
    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
        setDirty(true);
    }

    /**
     *
     * @param curso
     */
    public void setCurso(Curso curso) {
        this.parent = curso;
        setDirty(true);
    }


    /**
     *
     * @return
     */
    public ListaGrupos getGrupos() {
        return grupos;
    }

//    public void setGrupos(ListaGrupos grupos) {
//        this.grupos = grupos;
//    }
    @Override
    public String toString() {
        return this.nombre;// + this.grupos;
    }

    /**
     *
     * @return
     */
    public String getHash() {
        //Ejemplo: Primaria@1@Matematicas
        Curso c = getParent();
        return c.getHash() + "@" + this.toString();
    }

    /**
     *
     * @return
     */
    public int getNumeroDeGrupos() {
        return grupos.getGrupos().size();
    }

    /**
     *
     * @return
     */
    public Color getColorEnTablaDeHorarios() {
        return colorEnTablaDeHorarios;
    }

    /**
     *
     * @param colorEnTablaDeHorarios
     */
    public void setColorEnTablaDeHorarios(Color colorEnTablaDeHorarios) {
        this.colorEnTablaDeHorarios = colorEnTablaDeHorarios;
    }

//    /**
//     *
//     */
//    public synchronized void removeAllGrupos() {
//        for (Grupo gr : grupos.getGrupos()) {
//            this.removeGrupo(gr);
//        }
//    }
    /**
     *
     * @return
     */
    public int getNumCreditos() {
        return numCreditos;
    }

    /**
     *
     * @param numCreditos
     */
    public void setNumCreditos(int numCreditos) {
        this.numCreditos = numCreditos;
    }

    @Override
    public int compareTo(Asignatura o) {
        return this.nombre.compareTo(o.getNombre());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        hash = 23 * hash + (this.parent != null ? this.parent.hashCode() : 0);
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
        final Asignatura other = (Asignatura) obj;
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
     * @param obj
     * @param type
     */
    public void fireDataEvent(Object obj, int type) {
        getParent().fireDataEvent(obj, type);
    }

    /**
     *
     * @param aula
     */
    public void asignaAula(AulaMT aula) {
        for (Grupo gr : grupos.getGrupos()) {
            gr.asignaAula(aula);
        }
    }

    public void removeAula() {
        for (Grupo gr : grupos.getGrupos()) {
            gr.removeAula();
        }
    }

    /**
     *
     */
    public void updateAsigAulaStatus() {
        boolean resul = false;
        for (Grupo gr : grupos.getGrupos()) {
            if (gr.algunoSinAula()) {
                resul = true;
                break;
            }
        }
        if (resul != algunoSinAula) {
            algunoSinAula = resul;
            //Actualizo hacia arriba
            getParent().updateAsigAulaStatus();
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

    public void setTieneAula(boolean value) {
        this.algunoSinAula = !value;
    }

    void clearDocente() {
        for (Grupo gr : grupos.getGrupos()) {
            gr.clearDocente();
        }
    }

    void clearAulasAsignadas() {
        for (Grupo gr : grupos.getGrupos()) {
            gr.clearAulasAsignadas();
        }
    }

    public void copyBasicValuesFrom(Asignatura data) {
        this.nombre = data.getNombre();
        this.numCreditos = data.getNumCreditos();
        this.nombreCorto = data.getNombreCorto();
    }
}

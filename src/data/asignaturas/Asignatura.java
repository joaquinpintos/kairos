/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import java.awt.Color;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author david
 */
public class Asignatura implements Serializable, Comparable<Asignatura> {

    private String nombre; //Nombre completo
    private String nombreCorto;//Nombre corto del curso
    private Curso parent;//Curso al que pertenece
    private final ListaGrupos grupos;
    //Profesores que imparten esta asignatura
    //Deben ser el mísmo número que el número de grupos. Puede haber repetidos.
    private Color colorEnTablaDeHorarios;
    //En este array guardo los grupos que NO tengan asignada docencia
    private final HashSet<Grupo> gruposNOAsignados;
    private int numCreditos;

    /**
     *
     * @param nombre
     */
    public Asignatura(String nombre) {
        this.numCreditos = 0;
        this.nombre = nombre;
        this.nombreCorto = "";
        this.grupos = new ListaGrupos();
        colorEnTablaDeHorarios = new Color(220, 241, 182);//Color inicial. En principio no se usa.
        gruposNOAsignados = new HashSet<Grupo>();

    }

    /**
     *
     * @param gr
     */
    public void addGrupo(Grupo gr) {
        this.grupos.addGrupo(gr);
        gr.setParent(this);
        updateEstadoAsignacion(gr);
        this.getParent().updateEstadoAsignacion(this);
        Collections.sort(grupos.getGrupos());
        setDirty(true);
    }

    /**
     *
     * @param gr
     */
    public void removeGrupo(Grupo gr) {
        this.grupos.getGrupos().remove(gr);

        gr.removeDocencia();
        gruposNOAsignados.remove(gr);
        this.getParent().updateEstadoAsignacion(this);
        gr.setParent(null);
        setDirty(true);

    }

    /**
     *
     * @return
     */
    public boolean tieneGruposSinAsignar() {
        return !gruposNOAsignados.isEmpty();
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
     * @param curso
     */
    public void changeCursoTo(Curso curso) {
        if (this.getParent() != null) {
            this.getParent().removeAsignatura(this);
        }
        if (curso != null) {
            curso.addAsignatura(this);
        }
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

    /**
     *
     * @param gr
     */
    public void updateEstadoAsignacion(Grupo gr) {
        if (!gr.isAsignado()) {
            gruposNOAsignados.add(gr);
        } else {
            gruposNOAsignados.remove(gr);
        }
        this.getParent().updateEstadoAsignacion(this);
    }

    /**
     *
     */
    public synchronized void removeAllGrupos() {
        for (Grupo gr : grupos.getGrupos()) {
            this.removeGrupo(gr);
        }
    }

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

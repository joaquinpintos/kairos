/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import data.DataProyectoListener;
import data.aulas.Aula;
import data.aulas.AulaMT;
import data.profesores.Profesor;
import java.awt.Color;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;

/**
 *
 * @author david
 */
public class Asignatura implements Serializable, Comparable<Asignatura>, Teachable {

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
    public Asignatura(String nombre) {
        this.numCreditos = 0;
        this.nombre = nombre;
        this.nombreCorto = "";
        this.grupos = new ListaGrupos();
        colorEnTablaDeHorarios = new Color(220, 241, 182);//Color inicial. En principio no se usa.
        algunoSinAula = true;
    }

    /**
     *
     * @param gr
     */
    public void addGrupo(Grupo gr) {
        this.grupos.addGrupo(gr);
        gr.setParent(this);
        Collections.sort(grupos.getGrupos());
        setDirty(true);
        //Disparo evento de creación de grupo
        fireDataEvent(gr, DataProyectoListener.ADD);
    }

    /**
     *
     * @param gr
     */
    public void removeGrupo(Grupo gr) {
        this.grupos.getGrupos().remove(gr);
        gr.removeDocente();
        gr.setParent(null);
        setDirty(true);
        //Disparo evento de elminación de grupo
        fireDataEvent(gr, DataProyectoListener.REMOVE);

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

    @Override
    public void setDocente(Profesor profesor) {
        for (Grupo gr : grupos.getGrupos()) {
            gr.setDocente(profesor);
        }
    }

    @Override
    public void removeDocente() {
        for (Grupo gr : grupos.getGrupos()) {
            gr.removeDocente();
        }
    }

    public void fireDataEvent(Object obj, int type) {
        getParent().fireDataEvent(obj, type);
    }

    @Override
    public void asignaAula(AulaMT aula) {
        for (Grupo gr : grupos.getGrupos()) {
            gr.asignaAula(aula);
        }
    }

    @Override
    public void removeAula() {
        for (Grupo gr : grupos.getGrupos()) {
            gr.removeAula();
        }
    }

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
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.profesores;

import data.asignaturas.Grupo;
import data.asignaturas.DocenciaItem;
import data.asignaturas.Tramo;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author david
 */
public class Profesor implements Comparable<Profesor>, Serializable {

    private String nombre;
    private String apellidos;
    private Departamento departamento;
    private ArrayList<Tramo> docencia;

    /**
     *
     * @return
     */
    public Departamento getDepartamento() {
        return departamento;
    }

    /**
     *
     * @param departamento
     */
    public void changeDepartamento(Departamento departamento) {
        //Si lo voy a cambiar de departamento, primero lo borro del antiguo
        if (this.departamento != null) {
            this.departamento.removeSinBorrarDocencia(this);
        }
        this.departamento = departamento;
        //Ahora lo añado al nuevo
        this.departamento.addProfesor(this);
        setDirty(true);
    }

    /**
     *
     * @param departamento
     */
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public String getNombre() {
        if (nombre != null) {
            return nombre;
        } else {
            return "";
        }
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
     * @param nombre
     * @param apellidos
     * @param nombreCorto
     */
    public Profesor(String nombre, String apellidos, String nombreCorto) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.nombreCorto = nombreCorto;
        docencia = new ArrayList<Tramo>();
    }

    /**
     *
     * @param nombre
     * @param apellidos
     * @param nombreCorto
     * @param departamento
     */
    public Profesor(String nombre, String apellidos, String nombreCorto, Departamento departamento) {
        this.nombre = nombre;
        this.departamento = departamento;
        this.apellidos = apellidos;
        this.nombreCorto = nombreCorto;
        docencia = new ArrayList<Tramo>();
    }

    @Override
    public String toString() {
        return apellidos + ", " + nombre;
    }

    /**
     *
     * @return
     */
    public String getApellidos() {
        if (apellidos != null) {
            return apellidos;
        } else {
            return "";
        }
    }

    /**
     *
     * @param apellidos
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public ArrayList<Tramo> getDocencia() {
        return docencia;
    }

    /**
     *
     * @param docenciaItem
     */
    public void addDocencia(Tramo docenciaItem) {
        this.docencia.add(docenciaItem);
        if (docenciaItem.getDocente() != this) {
            docenciaItem.setDocente(this);
        }
        ordenaDocencia();
        setDirty(true);
    }

    /**
     *
     * @param docenciaItem
     */
    public void removeDocencia(Tramo docenciaItem) {
        if (docenciaItem != null) {
            this.docencia.remove(docenciaItem);
            if (docenciaItem.getDocente() != null) {
                docenciaItem.setDocente(null);
                setDirty(true);
            }
        }
    }

    public void clearDocencia() {
        for (Tramo d : docencia) {
            d.setDocente(null);
        }
        docencia.clear();
        setDirty(true);
    }

    /**
     *
     * @return
     */
    public double getHorasDocencia() {
        double suma = 0;
        for (Tramo d : docencia) {
            suma += d.getMinutos();
        }
        return suma/60;
    }

    /**
     *
     */
    public void ordenaDocencia() {
        Collections.sort((List) docencia);
    }

    @Override
    public int compareTo(Profesor o) {
        return this.toString().compareTo(o.toString());
    }

    /**
     *
     * @return
     */
    public String hash() {
        return this.nombre + "@" + this.apellidos;
    }
    private String nombreCorto;

    /**
     * Get the value of nombreCorto
     *
     * @return the value of nombreCorto
     */
    public String getNombreCorto() {
        return nombreCorto;
    }

    /**
     * Set the value of nombreCorto
     *
     * @param nombreCorto new value of nombreCorto
     */
    public void setNombreCorto(String nombreCorto) {
        this.nombreCorto = nombreCorto;
        setDirty(true);
    }

    /**
     *
     * @param value
     */
    public void setDirty(boolean value) {
        try {
            departamento.setDirty(value);
        } catch (NullPointerException e) {
        }
    }
}

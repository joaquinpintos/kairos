/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.profesores;

import data.asignaturas.Grupo;
import data.asignaturas.DocenciaItem;
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
    private ArrayList<DocenciaItem> docencia;

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
        docencia = new ArrayList<DocenciaItem>();
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
        docencia = new ArrayList<DocenciaItem>();
    }

    @Override
    public String toString() {
        return apellidos + ", " + nombre;
    }

    /**
     *
     * @return
     */
    public Profesor copia() {
        return new Profesor(this.nombre, this.apellidos, this.nombreCorto, this.departamento);
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
    public ArrayList<DocenciaItem> getDocencia() {
        return docencia;
    }

    /**
     *
     * @param docenciaItem
     */
    public void addDocencia(DocenciaItem docenciaItem) {
        this.docencia.add(docenciaItem);
        docenciaItem.getGrupo().setProfesor(this);
        ordenaDocencia();
        setDirty(true);
    }

    /**
     *
     * @param grupo
     */
    public void addDocencia(Grupo grupo) {
        this.docencia.add(new DocenciaItem(grupo));
        grupo.setProfesor(this);
        ordenaDocencia();
        setDirty(true);
    }

    /**
     *
     * @param docenciaItem
     */
    public void removeDocencia(DocenciaItem docenciaItem) {
        if (docenciaItem != null) {
            this.docencia.remove(docenciaItem);
            docenciaItem.getGrupo().setProfesor(null);
            setDirty(true);
        }
    }

    /**
     *
     */
    public void clearDocencia() {
        for (DocenciaItem d : docencia) {
            d.getGrupo().setProfesor(null);
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
        for (DocenciaItem d : docencia) {
            suma += d.getHoras();
        }
        return suma;
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
     * Versión sobrecargada
     *
     * @param gr
     */
    public void removeDocencia(Grupo gr) {
        for (DocenciaItem item : docencia) {
            if (item.getGrupo().equals(gr)) {
                this.removeDocencia(item);
                break;
            }
        }
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.profesores;

import data.AbstractDataSets;
import data.DataProject;
import data.DataProyectoListener;
import java.util.ArrayList;

/**
 * Estructura árbol con lista departamentos -> profesores
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DataProfesores extends AbstractDataSets {

    private static final long serialVersionUID = 27112013L;
    private final ArrayList<Departamento> departamentos;

    /**
     *
     * @param dp
     */
    public DataProfesores(DataProject dp) {
        super(dp);
        departamentos = new ArrayList<Departamento>();
    }

    /**
     *
     * @return
     */
    public ArrayList<Departamento> getDepartamentos() {
        return departamentos;
    }

    /**
     *
     * @param dep
     */
    public void addDepartamento(Departamento dep) {
        this.departamentos.add(dep);
    }

    /**
     *
     * @param dep
     */
    public void removeDepartamento(Departamento dep)  {
        //OJO! Al borrar el departamento NO borro los profesores.
        departamentos.remove(dep);
    }

    @Override
    public String toString() {
        return "Profesores";
    }
    /**
     *
     */
    public void clear() {
        departamentos.clear();
    }

    /**
     *
     * @return
     */
    public int cuentaProfesores() {
        int suma = 0;
        for (Departamento d : departamentos) {
            suma += d.getProfesores().size();
        }
        return suma;
    }

    /**
     *
     * @return
     */
    public ArrayList<Profesor> getTodosProfesores() {
        ArrayList<Profesor> resul = new ArrayList<Profesor>();
        for (Departamento d : departamentos) {
            resul.addAll(d.getProfesores());
        }

        return resul;
    }

    /**
     *
     * @param hashBuscado
     * @return
     */
    public Profesor buscaProfesorPorHash(String hashBuscado) {
        Profesor resul = null;
        outerloop:
        for (Departamento d : departamentos) {
            for (Profesor p : d.getProfesores()) {
                if (p.hash().equals(hashBuscado)) {
                    resul = p;
                    break outerloop;
                }
            }
        }
        return resul;
    }

}

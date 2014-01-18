/*
 * Copyright (C) 2014 David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package data.profesores;

import data.AbstractDataSets;
import data.DataProject;
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
    public void removeDepartamento(Departamento dep) {
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

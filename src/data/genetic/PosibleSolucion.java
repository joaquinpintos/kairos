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
package data.genetic;

import data.DataProject;
import data.MyConstants;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public final class PosibleSolucion implements Serializable {

    private static final long serialVersionUID = 27112013L;
    private long peso;
    private int level;//Nivel de incumplimiento de restricciones: rojo, amarillo, verde
    //HashMap classroom hash -> asignation
    private final HashMap<String, Asignacion> asignaciones;
    private DataProject dataProject;

    /**
     *
     * @param asignaciones
     */
    public PosibleSolucion(HashMap<String, Asignacion> asignaciones) {
        this.asignaciones = asignaciones;
        this.level = MyConstants.LEVEL_GREEN;//Default level
    }

    /**
     *
     */
    public PosibleSolucion() {
        asignaciones = new HashMap<String, Asignacion>();
        this.level = MyConstants.LEVEL_GREEN;
    }

    /**
     *
     * @param hashAula
     * @param asig
     */
    public void addAsignacion(String hashAula, Asignacion asig) {
        asig.setHashAula(hashAula);
        asignaciones.put(hashAula, asig);
        asig.setPosibleSolucion(this);
    }


    /**
     *
     * @return
     */
    public long getPeso() {
        return peso;
    }

    /**
     *
     * @param peso
     */
    public void setPeso(long peso) {
        this.peso = peso;
    }


    /**
     *
     * @param dataProject
     */
    public void setDataProyecto(DataProject dataProject) {
        this.dataProject = dataProject;
    }

    /**
     *
     * @param dataProject
     * @return
     */
    public static PosibleSolucion generador(DataProject dataProject) {
        PosibleSolucion newps = new PosibleSolucion();
        // System.out.println(stDataProyecto.getMapDatosPorAula().values());
        for (DatosPorAula da : dataProject.getMapDatosPorAula().values()) {
            // System.out.println("Genero datos para aula " + da.getHashAula());
            newps.addAsignacion(da.getHashAula(), Asignacion.generador(da));
        }
        return newps;
    }

    /**
     *
     * @return
     */
    public HashMap<String, Asignacion> getMapAsignaciones() {
        return asignaciones;
    }

    /**
     *
     * @param hashAula
     * @return
     */
    public Asignacion getAsignacion(String hashAula) {
        return asignaciones.get(hashAula);
    }

    public PosibleSolucion copia() {
        HashMap<String, Asignacion> nuevasAsignaciones = new HashMap<String, Asignacion>();
        for (String hashAula : asignaciones.keySet()) {
            Asignacion asig = asignaciones.get(hashAula);
            nuevasAsignaciones.put(hashAula, asig.copia());
        }
        PosibleSolucion newps = new PosibleSolucion(nuevasAsignaciones);
        newps.setDataProyecto(dataProject);
        newps.setPeso(peso);
        newps.setLevel(level);
        return newps;
    }

    /**
     *
     */
    public void update() {
        for (String hashAula : asignaciones.keySet()) {
            Asignacion asig = asignaciones.get(hashAula);
            asig.update();
        }
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void updateLevel(int newLevel) {

        if (newLevel < level) {
//            System.out.println("New level: "+level+" to "+newLevel);
            level = newLevel;
        }
        if (level < 1) {
            level = 1;
        }
    }
}

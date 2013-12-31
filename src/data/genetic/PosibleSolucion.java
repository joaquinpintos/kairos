/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
    private final HashMap<String, Asignacion> asignaciones;
    private DataProject dataProyecto;

    /**
     *
     * @param asignaciones
     */
    public PosibleSolucion(HashMap<String, Asignacion> asignaciones) {
        this.asignaciones = asignaciones;
        this.level = MyConstants.LEVEL_GREEN;
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
     * @param hashAula
     * @param k
     */
    public void addToAsignacion(String hashAula, int k) {
        asignaciones.get(hashAula).add(k);
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
     * @return
     */
    public DataProject getDataProyecto() {
        return dataProyecto;
    }

    /**
     *
     * @param dataProyecto
     */
    public void setDataProyecto(DataProject dataProyecto) {
        this.dataProyecto = dataProyecto;
    }

    /**
     *
     * @param dataProyecto
     * @return
     */
    public static PosibleSolucion generador(DataProject dataProyecto) {
        PosibleSolucion newps = new PosibleSolucion();
        // System.out.println(stDataProyecto.getMapDatosPorAula().values());
        for (DatosPorAula da : dataProyecto.getMapDatosPorAula().values()) {
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
        newps.setDataProyecto(dataProyecto);
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
        if (level<1) level=1;
    }
}

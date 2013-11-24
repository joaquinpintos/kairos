/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.horarios;

import data.genetic.PosibleSolucion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Esta clase representa un horario calculado. Almacena un array de HorarioItems
 * así como la solución que la generó
 *
 * @author david
 */
public class Horario implements Serializable {

    private static final long serialVersionUID = 1L;
    private ArrayList<HorarioItem> horarios;
    private PosibleSolucion solucion;
    private final HashMap<String, HashMap<Integer, HorarioItem>> mapAulasSegmentoToHorarioItem;
//    private HashMap<Integer,HorarioItem> mapHashCodeToHorarioItem;

    /**
     *
     * @param s
     */
    public Horario(PosibleSolucion s) {
        horarios = new ArrayList<HorarioItem>();
        this.solucion = s;
        mapAulasSegmentoToHorarioItem = new HashMap<String, HashMap<Integer, HorarioItem>>();
//        mapHashCodeToHorarioItem=new HashMap<Integer, HorarioItem>();
    }

    /**
     * Get the value of dataProyecto
     *
     * @return the value of dataProyecto
     */
    public PosibleSolucion getSolucion() {
        return solucion;
    }

//    public HorarioItem getHorarioPorHashCode(int hash)
//    {
//        return mapHashCodeToHorarioItem.get(hash);
//    }
//
//    public HashMap<Integer, HorarioItem> getMapHashCodeToHorarioItem() {
//        return mapHashCodeToHorarioItem;
//    }
//
//    public void rebuildMAPHashToHorarioItems()
//    {
//        mapHashCodeToHorarioItem.clear();
//        for (HorarioItem e:horarios)
//        {
//            mapHashCodeToHorarioItem.put(e.hashCode(),e);
//        }
//    }
    /**
     *
     * @return
     */
    public ArrayList<HorarioItem> getHorarios() {
        return horarios;
    }

    /**
     *
     * @param horarios
     */
    public void setHorarios(ArrayList<HorarioItem> horarios) {
        this.horarios = horarios;
    }

    /**
     *
     * @return
     */
    public int size() {
        return horarios.size();
    }

    /**
     *
     * @param index
     * @return
     */
    public HorarioItem get(int index) {
        return horarios.get(index);
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean add(HorarioItem e) {
//        mapHashCodeToHorarioItem.put(e.hashCode(),e);
        return horarios.add(e);
    }

    /**
     *
     * @return
     */
    public HashMap<String, HashMap<Integer, HorarioItem>> getMapAulasSegmentoToHorarioItem() {
        return mapAulasSegmentoToHorarioItem;
    }

    void añadeItemAMapa(String hashAula, int k, HorarioItem item) {
        if (!mapAulasSegmentoToHorarioItem.containsKey(hashAula)) {
            mapAulasSegmentoToHorarioItem.put(hashAula, new HashMap<Integer, HorarioItem>());
        }

        mapAulasSegmentoToHorarioItem.get(hashAula).put(k, item);
    }

    /**
     *
     * @param hashAula
     * @param numSegmento
     * @return
     */
    public HorarioItem getHorarioItemFor(String hashAula, int numSegmento) {
        return mapAulasSegmentoToHorarioItem.get(hashAula).get(numSegmento);
    }

    /**
     *
     * @return
     */
    public boolean hayUnaSolucion() {
        return !horarios.isEmpty();
    }

    /**
     *
     */
    public void clear() {
        horarios.clear();
        solucion = null;
        mapAulasSegmentoToHorarioItem.clear();
    }

    /**
     *
     * @param solucion
     */
    public void setSolucion(PosibleSolucion solucion) {
        this.solucion = solucion;
    }

}

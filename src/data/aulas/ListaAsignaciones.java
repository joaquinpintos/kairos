/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.aulas;

import gui.DatosEditor.Aulas.HashToGroupContainer;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ListaAsignaciones  implements Serializable {

    ArrayList<HashToGroupContainer> hashToGroupContainers;
    private final Boolean tarde;
    private final int index;
    private final Aula aula;

    /**
     *
     * @param tarde
     * @param index
     * @param aula
     */
    public ListaAsignaciones(Boolean tarde, int index, Aula aula) {
        hashToGroupContainers = new ArrayList<HashToGroupContainer>();
        this.tarde = tarde;
        this.index = index;
        this.aula = aula;
    }

    /**
     *
     * @return
     */
    public ArrayList<HashToGroupContainer> getHashToGroupContainers() {
        return hashToGroupContainers;
    }

    /**
     *
     * @return
     */
    public int getIndex() {
        return index;
    }

    @Override
    public String toString() {
        String resul = "";
        if (esTarde()) {
            resul = "Tarde";
        } else {
            resul = "Mañana";
        }
        return resul;
    }

    /**
     *
     * @return
     */
    public int size() {
        return hashToGroupContainers.size();
    }

    /**
     *
     * @param index
     * @return
     */
    public HashToGroupContainer get(int index) {
        return hashToGroupContainers.get(index);
    }

    /**
     *
     * @param e
     * @return
     */
    public boolean add(HashToGroupContainer e) {
        e.setAula(this.aula);
        return hashToGroupContainers.add(e);
    }

    /**
     * Añade a esta lista de asignaciones TODOS los grupos con hash el indicado
     * por la variable cont.
     *
     * @return  
     */

    public Boolean esTarde() {
        return tarde;
    }

    /**
     *
     * @param con
     * @return
     */
    public boolean contieneContainer(CarreraCursoGrupoContainer con) {
        Boolean resul = false;
        for (HashToGroupContainer hg : hashToGroupContainers) {
            if (hg.getHash().equals(con.getHash()))  {
                resul = true;
            }
        }
        return resul;


    }

    /**
     *
     * @return
     */
    public double getHorasOcupadas() {
        double resul=0;
        for (HashToGroupContainer c:hashToGroupContainers)
        {
            resul+=c.getTotalHoras();
        }
            return resul;
    }

    /**
     *
     * @return
     */
    public boolean isEmpty() {
        return hashToGroupContainers.isEmpty();
    }
    
    
}

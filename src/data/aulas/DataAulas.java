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
package data.aulas;

import data.AbstractDataSets;
import data.DataProject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DataAulas extends AbstractDataSets  {
    private static final long serialVersionUID = 27112013L;
    private final ArrayList<Aula> aulas;
    //hashGrupoCurso--->Aula@M/T
    private final ArrayList<AulaMT> aulaContainers;
    private final HashMap<String, AulaMT> mapHashToContainers;

    /**
     *
     * @param dataProyecto
     */
    public DataAulas(DataProject dataProyecto) {
        super(dataProyecto);
        this.aulas = new ArrayList<Aula>();
        mapHashToContainers = new HashMap<String, AulaMT>();
        aulaContainers = new ArrayList<AulaMT>();
    }

    /**
     *
     * @return
     */
    public ArrayList<AulaMT> getAulaContainers() {
        return aulaContainers;
    }

    /**
     *
     * @return
     */
    public HashMap<String, AulaMT> getMapHashToContainers() {
        return mapHashToContainers;
    }

    /**
     *
     * @return
     */
    public ArrayList<Aula> getAulas() {
        return aulas;
    }


    /**
     *
     * @param aula
     */
    public void addAula(Aula aula) {
        this.aulas.add(aula);
        aula.setParent(this);
        getDataProyecto().getAsignacionAulas().addAula(aula);
    }

    /**
     *
     * @param aula
     */
    public void removeAula(Aula aula) {
        this.aulas.remove(aula);
    }

    @Override
    public String toString() {
        return "Aulas";
    }

    /**
     *
     * @param nodeRoot
     */
    public void dataToDOM(Node nodeRoot) {

        for (Aula aul : this.aulas) {
            nodoAula(nodeRoot, aul);
        }
    }

    private void nodoAula(Node parent, Aula aula) {
        Element elemAula = parent.getOwnerDocument().createElement("aula");
        elemAula.setAttribute("nombre", aula.getNombre());
        parent.appendChild(elemAula);
    }

    /**
     *
     */
    public void clear() {
    }



//    private Aula buscaAula(String hashBuscado, Boolean esTarde) {
//        Aula resul = null;
//        for (Aula a : getDataProyecto().getDataAulas().getAulas()) {
//            if (a.getHash(esTarde) == null ? hashBuscado == null : a.getHash(esTarde).equals(hashBuscado)) {
//                resul = a;
//                break;
//            }
//        }
//        return resul;
//    }

    
     /**
     *
     */
    public void buildArrayAulaContainers() {
        aulaContainers.clear();
        mapHashToContainers.clear();
        for (Aula aula : aulas) {
            AulaMT contMañana = new AulaMT(aula, false);
            aulaContainers.add(contMañana);
            mapHashToContainers.put(aula.getHash(false), contMañana);
            AulaMT contTarde = new AulaMT(aula, true);
            aulaContainers.add(contTarde);
            mapHashToContainers.put(aula.getHash(true), contTarde);
        }
    }

    public void ordenaAulas() {
        Collections.sort(aulas);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.genetic;

import data.aulas.Aula;
import java.io.Serializable;

/**
 * Esta clase guarda la lista de segmentos y casillas para un aula fijada.
 * Suelve venir como elemento un hashmap asociado al aula en cuestión. Los
 * hashtags de las aulas distinguen entre mañana y tarde, de forma que cada aula
 * tiene dos versiones diferentes de sí misma.
 *
 * @author david
 */
public class DatosPorAula   implements Serializable{

    private ListaCasillas listaCasillas;
    private ListaSegmentos listaSegmentos;
    private String hashAula;
    private Aula aula;
    private  int minutosPorCasilla;

    /**
     *
     * @param minutosPorCasilla
     */
    public DatosPorAula(int minutosPorCasilla) {
        this.minutosPorCasilla=minutosPorCasilla;
        listaCasillas = new ListaCasillas(minutosPorCasilla);
        listaSegmentos = new ListaSegmentos();
    }

    /**
     *
     * @param listaCasillas
     * @param listaSegmentos
     */
    public DatosPorAula(ListaCasillas listaCasillas, ListaSegmentos listaSegmentos) {
        this.listaCasillas = listaCasillas;
        this.listaSegmentos = listaSegmentos;
    }


    /**
     *
     * @return
     */
    public ListaCasillas getListaCasillas() {
        return listaCasillas;
    }

    /**
     *
     * @param listaCasillas
     */
    public void setListaCasillas(ListaCasillas listaCasillas) {
        this.listaCasillas = listaCasillas;
    }

    /**
     *
     * @return
     */
    public ListaSegmentos getListaSegmentos() {
        return listaSegmentos;
    }

    /**
     *
     * @param listaSegmentos
     */
    public void setListaSegmentos(ListaSegmentos listaSegmentos) {
        this.listaSegmentos = listaSegmentos;
    }

    void addCasillas(Casilla cas) {
        listaCasillas.add(cas);
    }

    /**
     *
     * @param segmento
     * @return
     */
    public int addSegmento(Segmento segmento) {
        listaSegmentos.add(segmento);
        return listaSegmentos.size() - 1;
    }

    /**
     * Añade nuevos segmentos marcados como "huecos libres"
     * hasta completar el tiempo disponible en casillas con 
     * el tiempo de segmentos a llenar.
     */
    void rellenaSegmentosConHuecosLibres() {
        int duracionSegmentos = listaSegmentos.getMinutosTotales();
        int duracionCasillas = listaCasillas.getMinutosTotales();
        int duracionUnaCasilla = listaCasillas.minutosPorCasilla();

        while (duracionSegmentos < duracionCasillas) {
            Segmento s = new Segmento(null, 1,minutosPorCasilla);
            s.setHuecoLibre(true);
            listaSegmentos.add(s);
            duracionSegmentos += duracionUnaCasilla;
        }
    }

    /**
     *
     * @return
     */
    public String getHashAula() {
        return hashAula;
    }

    /**
     *
     * @param hashAula
     */
    public void setHashAula(String hashAula) {
        this.hashAula = hashAula;
    }

    /**
     *
     * @return
     */
    public Aula getAula() {
        return aula;
    }

    /**
     *
     * @param aula
     */
    public void setAula(Aula aula) {
        this.aula = aula;
    }
}

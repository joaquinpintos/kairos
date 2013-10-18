/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.genetic;

import data.Hora;
import data.MyConstants;
import data.asignaturas.Grupo;
import data.RangoHoras;
import data.profesores.Profesor;
import java.io.Serializable;

/**
 * Esta clase representa un segmento de docencia que hay que asignar a una
 * casilla. Puede tener distintas duraciones y ocupar más de una casilla. En
 * cualquier caso la casilla asignada es la primera que ocupa.
 *
 * @author david
 */
public class Segmento implements Serializable {

    Grupo grupo; //Grupo al que pertenece
    int duracion;//duracion en minutos
    private int numeroDeCasillasQueOcupa;
    private boolean huecoLibre; //True si el segmento no se refiere a una clase, sino a un hueco libre

    /**
     *
     * @param grupo
     * @param numeroDeCasillasQueOcupa
     * @param minutosPorCasilla
     */
    public Segmento(Grupo grupo, int numeroDeCasillasQueOcupa,int minutosPorCasilla) {
        this.grupo = grupo;
        this.duracion = minutosPorCasilla* numeroDeCasillasQueOcupa;
        huecoLibre = false;//Por defecto, no es un hueco libre.
        this.numeroDeCasillasQueOcupa = numeroDeCasillasQueOcupa;
    }

    /**
     * Devuelve el profesor que imparte dicho segmento.
     *
     * @return
     */
    public Profesor getProfesor() {
        if (isHuecoLibre()) {
            return null;
        } else {
            return this.grupo.getProfesor();
        }
    }

    @Override
    public String toString() {
        if (isHuecoLibre()) {
            return "Libre";
        } else {
            return this.grupo.getParent().getNombre() + "-" + this.grupo.getNombre() + " " + this.duracion;
        }
    }

    /**
     *
     * @return
     */
    public Grupo getGrupo() {
        if (isHuecoLibre()) {
            return null;
        } else {
            return grupo;
        }
    }

    /**
     *
     * @param grupo
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     *
     * @return
     */
    public int getDuracion() {
        return duracion; //La duración tiene sentido, aunque sea un hueco libre.
    }

    /**
     *
     * @param duracion
     */
    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    /**
     * Devuelve rango de hora del segmento dado si se situara en la casilla
     * especificada. Sirve para comprobar el solapamiento de distintas
     * asignaturas.
     *
     * @param cas
     * @return
     * @throws Exception
     */
    public RangoHoras getRangoHoraPuestoEnCasilla(Casilla cas) throws Exception {
        Hora hora2 = cas.getHora().copia();
        hora2.sumaMinutos(this.duracion);
        return new RangoHoras(cas.getHora(), hora2);


    }

    /**
     *
     * @return
     */
    public boolean isHuecoLibre() {
        return huecoLibre;
    }

    /**
     *
     * @param huecoLibre
     */
    public void setHuecoLibre(boolean huecoLibre) {
        this.huecoLibre = huecoLibre;
    }

    /**
     * Devuelve el número de casillas que ocupa este segmento.
     *
     * @return
     */
    public int getNumeroDeCasillasQueOcupa() {
        return numeroDeCasillasQueOcupa;
    }
}

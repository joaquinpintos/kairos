/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.genetic;

import data.Hora;
import data.asignaturas.Grupo;
import data.RangoHoras;
import data.asignaturas.Tramo;
import data.profesores.Profesor;
import java.io.Serializable;

/**
 * Esta clase representa un segmento de docencia que hay que asignar a una
 * casilla. Puede tener distintas duraciones y ocupar más de una casilla. En
 * cualquier caso la casilla asignada es la primera que ocupa. Desde la versión
 * 1.1, cada segmento está asociado a un tramo, más que a un grupo. Esto implica
 * entre otras cosas que varios profesores compartan la misma asignatura.
 *
 * @author david
 */
public class Segmento implements Serializable {

    private static final long serialVersionUID = 1L;
    int duracion;//duracion en minutos
    private final int numeroDeCasillasQueOcupa;
    private boolean huecoLibre; //True si el segmento no se refiere a una clase, sino a un hueco libre
    private final Tramo tramo;

    /**
     * Representa un segmento de ocupación. Unidad atómica de docencia, que debe
     * de ubicarse en una casilla.
     *
     * @param tramo
     * @param numeroDeCasillasQueOcupa
     * @param minutosPorCasilla
     */
    public Segmento(Tramo tramo, int numeroDeCasillasQueOcupa, int minutosPorCasilla) {
        this.tramo = tramo;
        this.duracion = minutosPorCasilla * numeroDeCasillasQueOcupa;
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
            return this.tramo.getDocente();
        }
    }

    @Override
    public String toString() {
        if (isHuecoLibre()) {
            return "Libre";
        } else {
            return this.tramo.getParent().getParent().getParent().getNombre() + "-" + this.tramo.getParent().getParent().getNombre() + " " + this.duracion;
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
            return tramo.getParent().getParent();
        }
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

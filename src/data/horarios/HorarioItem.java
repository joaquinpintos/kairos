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
package data.horarios;

import data.MyConstants;
import data.TimeRange;
import data.asignaturas.Asignatura;
import data.asignaturas.Grupo;
import data.asignaturas.Tramo;
import data.aulas.Aula;
import data.profesores.Profesor;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Un item de la lista de elementos que componen un horario.
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class HorarioItem implements Serializable {

    private static final long serialVersionUID = 27112013L;
    /**
     *
     */
    public final static int NO_MARK = 0;
    /**
     *
     */
    public final static int SIMPLE_MARK = 1;
    /**
     *
     */
    public final static int DOUBLE_MARK = 2;
    private Profesor profesor;
    private Tramo tramo;
    private Grupo grupo; //Grupo contiene la información del curso y carrera.
    private Asignatura asignatura;
    private Aula aula;
    private TimeRange rangoHoras;
    private boolean esTarde;
    private int diaSemana;
    private boolean huecoLibre;
    private boolean dummy = false;
    private int numeroSegmento;
    private int numcasilla;
    private int nivelConflictivo;
    private final int numeroDeCasillasQueOcupa;

    /**
     *
     * @param profesor
     * @param grupo
     * @param asignatura
     * @param aula
     * @param rangoHoras
     * @param esTarde
     * @param diaSemana
     * @param esHuecoLibre
     * @param numeroDeCasillasQueOcupa
     */
    public HorarioItem(Profesor profesor, Grupo grupo, Asignatura asignatura, Aula aula, TimeRange rangoHoras, boolean esTarde, int diaSemana, boolean esHuecoLibre, int numeroDeCasillasQueOcupa) {
        this.profesor = profesor;
        this.grupo = grupo;
        this.asignatura = asignatura;
        this.aula = aula;
        this.rangoHoras = rangoHoras;
        this.esTarde = esTarde;
        this.diaSemana = diaSemana;
        this.huecoLibre = esHuecoLibre;
        this.nivelConflictivo = 0;
        this.numeroDeCasillasQueOcupa = numeroDeCasillasQueOcupa;
    }

    /**
     *
     * @return
     */
    public Profesor getProfesor() {
        return profesor;
    }

    /**
     *
     * @param profesor
     */
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    /**
     *
     * @return
     */
    public Grupo getGrupo() {
        return grupo;
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

    /**
     *
     * @return
     */
    public TimeRange getRangoHoras() {
        return rangoHoras;
    }

    /**
     *
     * @param rangoHoras
     */
    public void setRangoHoras(TimeRange rangoHoras) {
        this.rangoHoras = rangoHoras;
    }

    /**
     *
     * @return
     */
    public int getDiaSemana() {
        return diaSemana;
    }

    /**
     *
     * @param diaSemana
     */
    public void setDiaSemana(int diaSemana) {
        this.diaSemana = diaSemana;
    }

    /**
     *
     * @return
     */
    public String printDiaSemana() {
        return MyConstants.DAYS_OF_THE_WEEK[diaSemana - 1];//diaSemana varía entre 1 y 5;
    }

    @Override
    public String toString() {
        String resul;
        if (!isHuecoLibre()) {
            resul = profesor + "|" + asignatura + "|" + grupo + "|" + aula + "|" + rangoHoras + "|" + printDiaSemana();

        } else {
            resul = "Libre";

        }
        return resul;
    }

    /**
     *
     * @return
     */
    public Asignatura getAsignatura() {
        return asignatura;
    }

    /**
     *
     * @param asignatura
     */
    public void setAsignatura(Asignatura asignatura) {
        this.asignatura = asignatura;
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
     *
     * @return
     */
    public int getNumeroSegmento() {
        return numeroSegmento;
    }

    /**
     *
     * @param numeroSegmento
     */
    public void setNumeroSegmento(int numeroSegmento) {
        this.numeroSegmento = numeroSegmento;
    }

    /**
     *
     * @return
     */
    public int getNumcasilla() {
        return numcasilla;
    }

    /**
     *
     * @param numcasilla
     */
    public void setNumcasilla(int numcasilla) {
        this.numcasilla = numcasilla;
    }

    /**
     *
     * @return
     */
    public boolean isEsTarde() {
        return esTarde;
    }

    /**
     *
     * @param esTarde
     */
    public void setEsTarde(boolean esTarde) {
        this.esTarde = esTarde;
    }

    /**
     *
     * @return
     */
    public HorarioItem copia() {
        HorarioItem resul = new HorarioItem(profesor, grupo, asignatura, aula, rangoHoras, esTarde, diaSemana, huecoLibre, numeroDeCasillasQueOcupa);
        resul.setNumcasilla(numcasilla);
        resul.setNumeroSegmento(numeroSegmento);
        return resul;
    }

//    public void copiaDatosDe(HorarioItem dst) {
//
//        //Tengo que copiar los datos de dst SIN ALTERAR LA UBICACION TEMPORAL
//
//        this.profesor = dst.getProfesor();
//        this.grupo = dst.getGrupo();
//        this.asignatura = dst.getAsignatura();
//        this.aula = dst.getAula();
//        this.huecoLibre = dst.isHuecoLibre();
//        this.numeroSegmento = dst.getNumeroSegmento(); //Segmento al que está asociado
//        this.numeroDeCasillasQueOcupa = dst.getNumeroDeCasillasQueOcupa();
//
//        //Datos relativos a la localización: Solo actualizo el rango de horas
//        Hora inic = rangoHoras.getInicio();
//        this.rangoHoras = new RangoHoras(inic, dst.rangoHoras.duracion());//recalculo el nuevo rango de horas
//        this.esTarde = dst.isEsTarde();//Se supone que no necesito copiar esto, ya que hago intercambios entre una misma aula
//
//
//    }
    /**
     *
     * @return
     */
    public int getMarkLevel() {
        return nivelConflictivo;
    }

    /**
     *
     * @param conflictivo
     */
    public void setMarkLevel(int conflictivo) {
        this.nivelConflictivo = conflictivo;
    }

    /**
     *
     * @return
     */
    public int getNumeroDeCasillasQueOcupa() {
        return numeroDeCasillasQueOcupa;
    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getCasillasOcupadas() {
        ArrayList<Integer> resul = new ArrayList<Integer>();
        for (int n = numcasilla; n < numeroDeCasillasQueOcupa; n++) {
            resul.add(n);
        }
        return resul;
    }

    /**
     *
     * @return
     */
    public boolean isDummy() {
        return dummy;
    }

    /**
     *
     * @param dummy
     */
    public void setDummy(boolean dummy) {
        this.dummy = dummy;
    }

    /**
     *
     * @return
     */
    public String getHashAula() {
        return aula.getHash(esTarde);
    }

    public void setTramo(Tramo tramo) {
        this.tramo = tramo;
    }

    public Tramo getTramo() {
        return tramo;
    }

}

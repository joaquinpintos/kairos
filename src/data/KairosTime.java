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
package data;

import java.io.Serializable;

/**
 * Objeto que almnacena una hora determinada en el día, con precisión de
 * horas:minutos
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class KairosTime implements Serializable, Comparable<KairosTime> {

    private static final long serialVersionUID = 27112013L;
    private int horas;
    private int minutos;

    /**
     * Constructor. Admite expresiones como horas=12, minutos=75, que se
     * normaliza a a horas=13, minutos=15.
     *
     * @param horas Horas
     * @param minutos Minutos
     * @throws NumberFormatException Si la hora resultante no está en el rango
     * 0:00-23:59
     */
    public KairosTime(int horas, int minutos) throws NumberFormatException {
        this.horas = horas;
        this.minutos = minutos;
        normalize();
    }

    /**
     * Constructor a partir de una cadena de texto
     *
     * @param cadena Hora en la forma HH:MM., por ejemplo 12:45. Al igual que la
     * anterior, los datos se normalizan en la form 12:75 -> 13:15
     * @throws NumberFormatException Si la hora resultante no está en el rango
     * 0:00-23:59
     */
    public KairosTime(String cadena) throws NumberFormatException {
        String[] arrayHoras = cadena.split(":");
        this.horas = new Integer(arrayHoras[0]);
        this.minutos = new Integer(arrayHoras[1]);
        normalize();
    }

    /**
     * Normaliza los datos haciendo que minutos este entre 0 y 60, modificando
     * la variable horas si es necesario.
     *
     * @throws NumberFormatException
     */
    private void normalize() throws NumberFormatException {
        while (this.minutos >= 60) {
            this.minutos -= 60;
            this.horas++;
        }
        while (this.minutos < 0) {
            this.minutos += 60;
            this.horas--;
        }

        if (horas > 23 || horas < 0) {
            throw new NumberFormatException("Hora incorrecta" + horas);
        }
    }

    /**
     * Añade minutos a la hora dada, normalizando los datos.
     *
     * @param minutos Minutos a añadir
     * @throws NumberFormatException Si la hora resultante no está en el rango
     * 0:00-23:59
     */
    public void sumaMinutos(int minutos) throws NumberFormatException {
        this.minutos += minutos;
        normalize();
    }

    /**
     * Resta minutos a la hora dada, normalizando los datos.
     *
     * @param minutos Minutos a restar
     * @throws NumberFormatException Si la hora resultante no está en el rango
     * 0:00-23:59
     */
    public void restaMinutos(int minutos) throws NumberFormatException {
        this.minutos -= minutos;
        normalize();
    }

    @Override
    public String toString() {
        return String.format("%02d", horas) + ":" + String.format("%02d", minutos);
    }

    /**
     * Devuelve la hora
     *
     * @return Hora
     */
    public int getHoras() {
        return horas;
    }

    /**
     * Cambia la variable hora
     *
     * @param hora Nueva hora
     * @throws NumberFormatException Si la hora no está en 0 y 23
     */
    public void setHoras(int hora) throws NumberFormatException {
        if ((hora < 0) || (hora > 23)) {
            throw new NumberFormatException("Hora negativa!");
        }
        this.horas = hora;
    }

    /**
     * Devuelve los minutos
     *
     * @return Minutos
     */
    public int getMinutos() {
        return minutos;
    }

    /**
     * Cambia los minutos y normaliza
     *
     * @param minutos Nuevos minutos
     * @throws NumberFormatException Si la nueva hora no está en el rango
     * 0:00-23:59
     */
    public void setMinutos(int minutos) throws NumberFormatException {
        this.minutos = minutos;
        normalize();
    }

    /**
     * Compara dos horas con la condición menor o igual
     *
     * @param otraHora Otra hora para comparar
     * @return True si la hora es menor o igual que otraHora. False en caso
     * contrario
     */
    public boolean menorIgualQue(KairosTime otraHora) {//Devuelve true si this es menor que otra.
        boolean resul;
        if (this.horas < otraHora.horas) {
            resul = true;
        } else {
            resul = (this.horas == otraHora.horas) && (this.minutos <= otraHora.minutos);
        }
        return resul;
    }

    /**
     * Compara dos horas con la condición menor estricto
     *
     * @param otraHora
     * @return True si this es menor que otraHora. False en caso contrario
     */
    public boolean menorEstrictoQue(KairosTime otraHora) {
        boolean resul;
        if (this.horas < otraHora.horas) {
            resul = true;
        } else {
            resul = (this.horas == otraHora.horas) && (this.minutos < otraHora.minutos);
        }
        return resul;
    }

    /**
     * Devuelve una copia del objeto
     */
    public KairosTime copia() {
        KairosTime resul = new KairosTime(this.horas, this.minutos);
        return resul;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.horas;
        hash = 67 * hash + this.minutos;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KairosTime other = (KairosTime) obj;
        if (this.horas != other.horas) {
            return false;
        }
        if (this.minutos != other.minutos) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(KairosTime o) {
        int resul = 0;
        if (menorEstrictoQue(o)) {
            resul = -1;
        }
        if (o.menorEstrictoQue(this)) {
            resul = 1;
        }
        return resul;
    }

}

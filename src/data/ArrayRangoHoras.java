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
import java.util.ArrayList;

/**
 * Almacena un array de rangos de horas
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ArrayRangoHoras implements Serializable {

    private static final long serialVersionUID = 27112013L;
    private final ArrayList<RangoHoras> data;

    /**
     * Constructor por defecto, creando un array vacío.
     */
    public ArrayRangoHoras() {
        this.data = new ArrayList<RangoHoras>();
    }

    /**
     * Constructor especificando array
     * @param data
     */
    public ArrayRangoHoras(ArrayList<RangoHoras> data) {
        this.data = data;
    }

    /**
     *
     * @return Tamaño del array
     */
    public int size() {
        return data.size();
    }

    /**
     * Constructor tomando como parámetro una cadena de rangos de horas
     * separados por comas, de la form 8:00-12:00,15:00-16:30
     *
     * @param texto Cadena con los rangos de horas
     */
    public ArrayRangoHoras(String texto) {
        data = new ArrayList<RangoHoras>();
        if (!texto.equals("")) {
            String[] dataSplitted = texto.replace(" ", "").split(",");
            for (String r : dataSplitted) {
                if (!r.equals("")) {
                    data.add(new RangoHoras(r));
                }
            }
        }
    }

    @Override
    public String toString() {
        Boolean first = true;
        StringBuilder resul = new StringBuilder();
        for (RangoHoras r : data) {
            if (!first) {
                resul.append(", ");
            }
            first = false;
            resul.append(r.toString());
        }
        return resul.toString();
    }

    /**
     *
     * @return Array de rangos de horas
     */
    public ArrayList<RangoHoras> getData() {
        return data;
    }

    /**
     * Comprueba si un determinado rango horario está contenido en el periodo
     * horario determinado por este array. Por ejemplo, si el array es
     * 8:00-12:00,15:00-16:30 entonces dando como parámetro el rango 15:10-16:10
     * devolverá true.
     *
     * @param rangoHora Rango horario a comprobar
     * @return True si el rango dado está contenido. False en caso contrario.
     */
    public boolean contiene(RangoHoras rangoHora) {
        boolean resul = false;
        for (RangoHoras r : data) {
            if (r.contieneRango(rangoHora)) {
                resul = true;
                break;
            }
        }
        return resul;
    }

    /**
     * Comprueba si el rango dado tiene algún instante en común (un minuto como
     * mínimo) con el array de rangos almacenado.
     *
     * @param rangoHora Rango a comprobar.
     * @return True si hay coincidencia. False en caso contrario.
     */
    public boolean solapaCon(RangoHoras rangoHora) {
        boolean resul = false;
        for (RangoHoras r : data) {
            if (r.solapaCon(rangoHora)) {
                resul = true;
                break;
            }
        }
        return resul;
    }
}

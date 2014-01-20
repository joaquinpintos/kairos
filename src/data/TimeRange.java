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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase representa un rango de horas, ej: 8:00 a 15:30
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class TimeRange implements Serializable {

    private KairosTime inicio;
    private KairosTime fin;

    /**
     * Constructor con valores nulos por defecto
     */
    public TimeRange() {
        inicio = new KairosTime(0, 0);
        fin = new KairosTime(0, 0);
    }

    /**
     *
     * @param inicio
     * @param fin
     */
    public TimeRange(KairosTime inicio, KairosTime fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    /**
     *
     * @param inicio
     * @param duracion
     */
    public TimeRange(KairosTime inicio, int duracion) {
        try {
            this.inicio = inicio.copia();
            this.fin = inicio.copia();
            this.fin.sumaMinutos(duracion);
        } catch (Exception ex) {
            Logger.getLogger(TimeRange.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param inicio
     * @param fin
     */
    public TimeRange(String inicio, String fin) {
        this.inicio = new KairosTime(inicio);
        this.fin = new KairosTime(fin);
    }

    /**
     *
     * @param ini1
     * @param fin1
     * @param ini2
     * @param fin2
     * @throws Exception
     */
    public TimeRange(int ini1, int fin1, int ini2, int fin2) throws Exception {
        this.inicio = new KairosTime(ini1, fin1);
        this.fin = new KairosTime(ini2, fin2);
    }
    //Hora en un string separado por un guion.

    /**
     *
     * @param hora
     */
    public TimeRange(String hora) {
        String data = hora.replace(" ", "");//Quito espacios de cualquier sitio.
        String[] dataSplit = data.split("-");
        this.inicio = new KairosTime(dataSplit[0]);
        this.fin = new KairosTime(dataSplit[1]);
    }

    /**
     *
     * @return
     */
    public KairosTime getFin() {
        return fin;
    }

    /**
     *
     * @param fin
     */
    public void setFin(KairosTime fin) {
        this.fin = fin;
    }

    /**
     *
     * @return
     */
    public KairosTime getInicio() {
        return inicio;
    }

    /**
     *
     * @param inicio
     */
    public void setInicio(KairosTime inicio) {
        this.inicio = inicio;
    }

    /**
     *
     * @param hora
     * @return
     */
    public boolean contieneEstrictamente(KairosTime hora) {
        return (hora.menorEstrictoQue(this.fin)) && (this.inicio.menorEstrictoQue(hora));
    }

    /**
     *
     * @param r
     * @return
     */
    public boolean contieneRango(TimeRange r) {
        return (r.getFin().menorIgualQue(this.fin)) && (this.inicio.menorIgualQue(r.getInicio()));
    }

    //Devuelve true si los rangos se solapan en una zona no trivial
    /**
     *
     * @param rango
     * @return
     */
    public boolean solapaCon(TimeRange rango) {
        return (rango.contieneEstrictamente(this.inicio) || rango.contieneEstrictamente(this.fin) || this.contieneEstrictamente(rango.inicio) || this.contieneEstrictamente(rango.fin));
    }

    /**
     * Devuelve un array de horas contenidas en el rango, separados por los
     * minutos especificados
     *
     * @param duracion Número de minutos.
     * @return ArrayList con las horas
     * @throws Exception
     */
    public ArrayList<KairosTime> split(int duracion) throws Exception {
        ArrayList<KairosTime> resul = new ArrayList<KairosTime>();
        KairosTime horaFin = this.fin.copia();
        horaFin.restaMinutos(duracion);
        for (KairosTime hora = this.inicio.copia(); hora.menorIgualQue(horaFin); hora.sumaMinutos(duracion)) {
            resul.add(hora.copia());
        }
        return resul;
    }

    /**
     * Devuelve un array de horas contenidas en el rango, separados por los
     * minutos especificados
     *
     * @param duracion Número de minutos.
     * @return ArrayList con las horas
     * @throws Exception
     */
    public ArrayList<TimeRange> splitRangos(int duracion) {
        ArrayList<TimeRange> resul = new ArrayList<TimeRange>();
        try {
            KairosTime horaFin = this.fin.copia();
            horaFin.restaMinutos(duracion);
            for (KairosTime hora = this.inicio.copia(); hora.menorIgualQue(horaFin); hora.sumaMinutos(duracion)) {
                resul.add(new TimeRange(hora.copia(), duracion));
            }
        } catch (Exception exception) {
            resul = null;
        }
        return resul;
    }

    @Override
    public String toString() {
        return inicio + " - " + fin;
    }

    /**
     *
     * @return
     */
    public TimeRange copia() {
        TimeRange resul = null;
        try {
            resul = new TimeRange(inicio.copia(), fin.copia());
        } catch (Exception ex) {
            Logger.getLogger(TimeRange.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resul;
    }

    /**
     * Devuelve la duración en horas de un rango de horas. -1 Si el rango es
     * incorrecto
     *
     * @return
     */
    public int getDuracionHoras() {
        int resul;
        try {
            KairosTime h = fin.copia();
            h.setHoras(fin.getHoras() - inicio.getHoras());//Resto horas
            //ahora resto los minutos, con el método apropiado
            h.restaMinutos(inicio.getMinutos());
            resul = h.getHoras() + h.getMinutos() / 60;
        } catch (Exception ex) {
            resul = -1;
        }
        return resul;
    }

    /**
     * Devuelve la duración en minutos del rango de horas. -1 Si el rango es
     * incorrecto
     *
     * @return
     */
    public int getDuracionMinutos() {
        int resul;
        try {
            KairosTime h = fin.copia();
            h.setHoras(fin.getHoras() - inicio.getHoras());//Resto horas
            //ahora resto los minutos, con el método apropiado
            h.restaMinutos(inicio.getMinutos());
            resul = h.getHoras() * 60 + h.getMinutos();
        } catch (Exception ex) {
            resul = -1;
        }
        return resul;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.inicio != null ? this.inicio.hashCode() : 0);
        hash = 67 * hash + (this.fin != null ? this.fin.hashCode() : 0);
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
        final TimeRange other = (TimeRange) obj;
        if (this.inicio != other.inicio && (this.inicio == null || !this.inicio.equals(other.inicio))) {
            return false;
        }
        if (this.fin != other.fin && (this.fin == null || !this.fin.equals(other.fin))) {
            return false;
        }
        return true;
    }
}

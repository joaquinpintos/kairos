/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public class RangoHoras implements Serializable {

    private Hora inicio;
    private Hora fin;

    /**
     * Constructor con valores nulos por defecto
     */
    public RangoHoras() {
        inicio = new Hora(0, 0);
        fin = new Hora(0, 0);
    }

    /**
     *
     * @param inicio
     * @param fin
     */
    public RangoHoras(Hora inicio, Hora fin) {
        this.inicio = inicio;
        this.fin = fin;
    }

    /**
     *
     * @param inicio
     * @param duracion
     */
    public RangoHoras(Hora inicio, int duracion) {
        try {
            this.inicio = inicio.copia();
            this.fin = inicio.copia();
            this.fin.sumaMinutos(duracion);
        } catch (Exception ex) {
            Logger.getLogger(RangoHoras.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param inicio
     * @param fin
     */
    public RangoHoras(String inicio, String fin) {
        this.inicio = new Hora(inicio);
        this.fin = new Hora(fin);
    }

    /**
     *
     * @param ini1
     * @param fin1
     * @param ini2
     * @param fin2
     * @throws Exception
     */
    public RangoHoras(int ini1, int fin1, int ini2, int fin2) throws Exception {
        this.inicio = new Hora(ini1, fin1);
        this.fin = new Hora(ini2, fin2);
    }
    //Hora en un string separado por un guion.

    /**
     *
     * @param hora
     */
    public RangoHoras(String hora) {
        String data = hora.replace(" ", "");//Quito espacios de cualquier sitio.
        String[] dataSplit = data.split("-");
        this.inicio = new Hora(dataSplit[0]);
        this.fin = new Hora(dataSplit[1]);
    }

    /**
     *
     * @return
     */
    public Hora getFin() {
        return fin;
    }

    /**
     *
     * @param fin
     */
    public void setFin(Hora fin) {
        this.fin = fin;
    }

    /**
     *
     * @return
     */
    public Hora getInicio() {
        return inicio;
    }

    /**
     *
     * @param inicio
     */
    public void setInicio(Hora inicio) {
        this.inicio = inicio;
    }

    /**
     *
     * @param hora
     * @return
     */
    public boolean contieneEstrictamente(Hora hora) {
        return (hora.menorEstrictoQue(this.fin)) && (this.inicio.menorEstrictoQue(hora));
    }

    /**
     *
     * @param r
     * @return
     */
    public boolean contieneRango(RangoHoras r) {
        return (r.getFin().menorIgualQue(this.fin)) && (this.inicio.menorIgualQue(r.getInicio()));
    }

    //Devuelve true si los rangos se solapan en una zona no trivial
    /**
     *
     * @param rango
     * @return
     */
    public boolean solapaCon(RangoHoras rango) {
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
    public ArrayList<Hora> split(int duracion) throws Exception {
        ArrayList<Hora> resul = new ArrayList<Hora>();
        Hora horaFin = this.fin.copia();
        horaFin.restaMinutos(duracion);
        for (Hora hora = this.inicio.copia(); hora.menorIgualQue(horaFin); hora.sumaMinutos(duracion)) {
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
    public ArrayList<RangoHoras> splitRangos(int duracion) throws Exception {
        ArrayList<RangoHoras> resul = new ArrayList<RangoHoras>();
        Hora horaFin = this.fin.copia();
        horaFin.restaMinutos(duracion);
        for (Hora hora = this.inicio.copia(); hora.menorIgualQue(horaFin); hora.sumaMinutos(duracion)) {
            resul.add(new RangoHoras(hora.copia(), duracion));
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
    public RangoHoras copia() {
        RangoHoras resul = null;
        try {
            resul = new RangoHoras(inicio.copia(), fin.copia());
        } catch (Exception ex) {
            Logger.getLogger(RangoHoras.class.getName()).log(Level.SEVERE, null, ex);
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
            Hora h = fin.copia();
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
            Hora h = fin.copia();
            h.setHoras(fin.getHoras() - inicio.getHoras());//Resto horas
            //ahora resto los minutos, con el método apropiado
            h.restaMinutos(inicio.getMinutos());
            resul = h.getHoras()*60 + h.getMinutos();
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
        final RangoHoras other = (RangoHoras) obj;
        if (this.inicio != other.inicio && (this.inicio == null || !this.inicio.equals(other.inicio))) {
            return false;
        }
        if (this.fin != other.fin && (this.fin == null || !this.fin.equals(other.fin))) {
            return false;
        }
        return true;
    }
}

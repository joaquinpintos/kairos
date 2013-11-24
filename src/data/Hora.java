/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;

/**
 *
 * @author david
 */
public class Hora  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int horas;
    private int minutos;

    /**
     *
     * @param horas
     * @param minutos
     * @throws NumberFormatException
     */
    public Hora(int horas, int minutos) throws NumberFormatException {
        this.horas = horas;
        this.minutos = minutos;
        normalize();
    }
    /**
     *
     * @param cadena
     */
    public Hora(String cadena)
    {
        String[] arrayHoras=cadena.split(":");
            this.horas = new Integer(arrayHoras[0]);
            this.minutos = new Integer(arrayHoras[1]);
    }
    

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
            throw new NumberFormatException("Hora incorrecta!!!!! " + horas);
        }
    }

    /**
     *
     * @param minutos
     * @throws Exception
     */
    public void sumaMinutos(int minutos) throws Exception {
        this.minutos += minutos;
        normalize();
    }

    /**
     *
     * @param minutos
     * @throws Exception
     */
    public void restaMinutos(int minutos) throws Exception {
        this.minutos -= minutos;
        normalize();
    }

   
    @Override
    public String toString() {
        return String.format("%02d", horas) + ":" + String.format("%02d", minutos);
    }

    /**
     *
     * @return
     */
    public int getHoras() {
        return horas;
    }

    /**
     *
     * @param hora
     */
    public void setHoras(int hora) {
        if (hora<0) throw new NumberFormatException("Hora negativa!");
        this.horas = hora;
    }

    /**
     *
     * @return
     */
    public int getMinutos() {
        return minutos;
    }

    /**
     *
     * @param minutos
     */
    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    /**
     *
     * @param otra
     * @return
     */
    public boolean menorIgualQue(Hora otra) {//Devuelve true si this es menor que otra.
        boolean resul;
        if (this.horas < otra.horas) {
            resul=true;
        } else {
            resul=(this.horas==otra.horas) && (this.minutos<=otra.minutos);
        }
        return resul;
    }
       /**
     *
     * @param otra
     * @return
     */
    public boolean menorEstrictoQue(Hora otra) {//Devuelve true si this es menor que otra.
        boolean resul;
        if (this.horas < otra.horas) {
            resul=true;
        } else {
            resul=(this.horas==otra.horas) && (this.minutos<otra.minutos);
        }
        return resul;
    }
    /**
     *
     * @return
     * @throws Exception
     */
    public Hora copia() throws Exception
    {
        return new Hora(this.horas,this.minutos);
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
        final Hora other = (Hora) obj;
        if (this.horas != other.horas) {
            return false;
        }
        if (this.minutos != other.minutos) {
            return false;
        }
        return true;
    }
        
}

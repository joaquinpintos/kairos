/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author david
 */
public class Fecha {
    private int dia;
    private int mes;
    private int año;

    /**
     *
     * @param dia
     * @param mes
     * @param año
     */
    public Fecha(int dia, int mes, int año) {
        this.dia = dia;
        this.mes = mes;
        this.año = año;
    }

    /**
     *
     * @return
     */
    public int getAño() {
        return año;
    }

    /**
     *
     * @param año
     */
    public void setAño(int año) {
        this.año = año;
    }

    /**
     *
     * @return
     */
    public int getDia() {
        return dia;
    }

    /**
     *
     * @param dia
     */
    public void setDia(int dia) {
        this.dia = dia;
    }

    /**
     *
     * @return
     */
    public int getMes() {
        return mes;
    }

    /**
     *
     * @param mes
     */
    public void setMes(int mes) {
        this.mes = mes;
    }
    
    
}

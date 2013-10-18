/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ArrayRangoHoras implements Serializable {

    private ArrayList<RangoHoras> data;

    /**
     *
     */
    public ArrayRangoHoras() {
        this.data = new ArrayList<RangoHoras>();
    }

    /**
     *
     * @return
     */
    public int size() {
        return data.size();
    }

    /**
     *
     * @param texto
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
        StringBuffer resul = new StringBuffer();
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
     * @return
     */
    public ArrayList<RangoHoras> getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(ArrayList<RangoHoras> data) {
        this.data = data;
    }

    /**
     *
     * @param rangoHora
     * @return
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
     *
     * @param rangoHora
     * @return
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

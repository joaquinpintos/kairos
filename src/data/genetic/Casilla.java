/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.genetic;

import data.Hora;
import data.MyConstants;
import data.asignaturas.Grupo;
import data.aulas.Aula;
import data.RangoHoras;
import java.io.Serializable;

/**
 * Representa una casilla disponible para introducir un segmento.
 *
 * @author david
 */
public class Casilla implements Comparable<Casilla>, Serializable {

    private Aula aula;
    private Hora hora;
    private int diaSemana;//Entre 1 y 5
    private int minutos;
    private boolean finalDeRango;
    private String hashAula;

    /**
     *
     * @param hashAula
     * @param hora
     * @param diaSemana
     * @param minutos
     */
    public Casilla(String hashAula, Hora hora, int diaSemana, int minutos) {
        this.hashAula = hashAula;
        this.hora = hora;
        this.diaSemana = diaSemana;
        this.minutos = minutos;
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
    public Hora getHora() {
        return hora;
    }

    /**
     *
     * @param hora
     */
    public void setHora(Hora hora) {
        this.hora = hora;
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

    @Override
    public String toString() {
        return "Casilla{" + "aula=" + hashAula + ", hora=" + hora + ", diaSemana=" + diaSemana + ", minutos=" + minutos + '}' + "\n";
    }

    /**
     * Devuelve el rango horario del segmento
     *
     * @return
     * @throws Exception
     */
    public RangoHoras getRangoHora() throws Exception {
        Hora hora2 = hora.copia();
        hora2.sumaMinutos(minutos);
        return new RangoHoras(hora, hora2);

    }

    /**
     * Devuelve el rango horario del segmento desplazado los minutos dados
     *
     * @param estosMinutos
     * @return
     * @throws Exception
     */
    public RangoHoras getRangoSumando(int estosMinutos) throws Exception {
        Hora hora2 = hora.copia();
        hora2.sumaMinutos(estosMinutos);
        return new RangoHoras(hora, hora2);
    }

    /**
     *
     * @param s
     * @return
     * @throws Exception
     */
    public RangoHoras getRangoConSegmento(Segmento s) throws Exception {
        Hora hora2 = hora.copia();
        hora2.sumaMinutos(s.getDuracion());
        return new RangoHoras(hora, hora2);
    }

    /**
     *
     * @param gr
     * @return
     * @throws Exception
     */
    public RangoHoras getRangoConGrupo(Grupo gr) throws Exception {
        Hora hora2 = hora.copia();
        hora2.sumaMinutos(gr.getTramosGrupoCompleto().getTramos().get(0).getMinutos());
        return new RangoHoras(hora, hora2);
    }

    /**
     *
     * @return
     */
    public String printDiaSemana() {
//        String[] dias = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
        return MyConstants.DIAS_SEMANA[diaSemana - 1];//diaSemana varía entre 1 y 5;
    }

    void setFinaldeRango(boolean b) {
        this.finalDeRango = b;
    }

    /**
     *
     * @return
     */
    public boolean isFinalDeRango() {
        return finalDeRango;
    }

    /**
     *
     * @return
     */
    public String getHashAula() {
        return hashAula;
    }

    /**
     *
     * @param hashAula
     */
    public void setHashAula(String hashAula) {
        this.hashAula = hashAula;
    }

    @Override
    public int compareTo(Casilla c) {
        int resul = 0;
        if (diaSemana < c.getDiaSemana()) {
            resul = -1;
        } else if (diaSemana > c.getDiaSemana()) {
            resul = 1;
        } else {
            if (hora.menorEstrictoQue(c.getHora())) {
                resul = -1;
            }
            if (c.getHora().menorEstrictoQue(hora)) {
                resul = 1;
            }
        }
        return resul;
    }

    /**
     * Devuelve true si las dos casillas (this y c) pertenecen al mismo instante
     * de tiempo. Lo que compara es que el tiempo inicial sea el mismo, junto
     * con el día de la semana.
     *
     * @param c
     * @return
     */
    public boolean mismoInstante(Casilla c) {
        boolean resul = true;
        if (c.getDiaSemana() != diaSemana) {
            resul = false;
        }
        if (c.getHora().getHoras() != hora.getHoras()) {
            resul = false;
        }
        if (c.getHora().getMinutos() != hora.getMinutos()) {
            resul = false;
        }
        return resul;
    }
}

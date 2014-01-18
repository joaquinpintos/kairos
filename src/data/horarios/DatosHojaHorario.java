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

import data.DataProject;
import data.Hora;
import data.RangoHoras;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase representa los datos que se muestran en una hoja de horario, para
 * un grupo/clase/profesor fijos. Primero se fija si la hoja es de mañana, tarde
 * o mañana y tarde con los setMañana, setTarde y setMañanaYTarde()
 * Posteriormente se añaden los items con el método add. Para recuperar el item
 * correspondiente al día n, fila m, se usa el método retrieveData.
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DatosHojaHorario {

    HashMap<Integer, HorarioItem> data;
    private ArrayList<RangoHoras> rangosHoras;
    private HashMap<String, Integer> auxHorasHorario;
    private final DataProject dataProyecto;

    /**
     *
     * @param dataProyecto
     */
    public DatosHojaHorario(DataProject dataProyecto) {
        this.dataProyecto = dataProyecto;
        data = new HashMap<Integer, HorarioItem>();
    }

    /**
     *
     * @param includeRecreo
     * @return
     */
    public int setMañana(Boolean includeRecreo) {
        rangosHoras = new ArrayList<RangoHoras>();
        rangosHoras.addAll(dataProyecto.getMañana1().splitRangos(dataProyecto.getMinutosPorCasilla()));
        if (includeRecreo) {
            rangosHoras.add(new RangoHoras(dataProyecto.getMañana1().getFin(), dataProyecto.getMañana2().getInicio()));
        }
        rangosHoras.addAll(dataProyecto.getMañana2().splitRangos(dataProyecto.getMinutosPorCasilla()));
        calculaIndicesAuxiliaresRangosHoras();
        return rangosHoras.size();
    }

    /**
     *
     * @return
     */
    public ArrayList<RangoHoras> getRangosHoras() {
        return rangosHoras;
    }

    /**
     *
     * @param includeRecreo
     * @return
     */
    public int setTarde(Boolean includeRecreo) {
        rangosHoras = new ArrayList<RangoHoras>();
        try {
            //horasHorario.add(new RangoHoras(dataProyecto.getMañana2().getFin(),dataProyecto.getTarde1().getInicio()));
            rangosHoras.addAll(dataProyecto.getTarde1().splitRangos(dataProyecto.getMinutosPorCasilla()));
            if (includeRecreo) {
                rangosHoras.add(new RangoHoras(dataProyecto.getTarde1().getFin(), dataProyecto.getTarde2().getInicio()));
            }
            rangosHoras.addAll(dataProyecto.getTarde2().splitRangos(dataProyecto.getMinutosPorCasilla()));
        } catch (Exception ex) {
            Logger.getLogger(DatosHojaHorario.class.getName()).log(Level.SEVERE, null, ex);
        }
        calculaIndicesAuxiliaresRangosHoras();
        return rangosHoras.size();
    }

    /**
     *
     * @param includeRecreo
     * @return
     */
    public int setMañanaYTarde(Boolean includeRecreo) {
        rangosHoras = new ArrayList<RangoHoras>();
        try {
            rangosHoras.addAll(dataProyecto.getMañana1().splitRangos(dataProyecto.getMinutosPorCasilla()));
            if (includeRecreo) {
                rangosHoras.add(new RangoHoras(dataProyecto.getMañana1().getFin(), dataProyecto.getMañana2().getInicio()));
            }
            rangosHoras.addAll(dataProyecto.getMañana2().splitRangos(dataProyecto.getMinutosPorCasilla()));
            if (includeRecreo) {
                rangosHoras.add(new RangoHoras(dataProyecto.getMañana2().getFin(), dataProyecto.getTarde1().getInicio()));
            }
            rangosHoras.addAll(dataProyecto.getTarde1().splitRangos(dataProyecto.getMinutosPorCasilla()));
            if (includeRecreo) {
                rangosHoras.add(new RangoHoras(dataProyecto.getTarde1().getFin(), dataProyecto.getTarde2().getInicio()));
            }
            rangosHoras.addAll(dataProyecto.getTarde2().splitRangos(dataProyecto.getMinutosPorCasilla()));
        } catch (Exception ex) {
            Logger.getLogger(DatosHojaHorario.class.getName()).log(Level.SEVERE, null, ex);
        }
        calculaIndicesAuxiliaresRangosHoras();
        return rangosHoras.size();
    }

    private void calculaIndicesAuxiliaresRangosHoras() {
        auxHorasHorario = new HashMap<String, Integer>();
        for (int n = 0; n < rangosHoras.size(); n++) {
            auxHorasHorario.put(rangosHoras.get(n).getInicio().toString(), n);
        }
    }

    /**
     * Devuelve el item correspondiente al día y hora especificados. La hora se
     * indica por medio del índice, de forma que la fila número indiceHora de la
     * columna 0 indica la hora real.
     *
     * @param numColumna
     * @param numFila
     * @return
     */
    public HorarioItem retrieveData(int numColumna, int numFila) {
        int numFilas = rangosHoras.size();
        int numDiaLectivo = dataProyecto.getDiasSemanaLectivos().indexOf(numColumna) + 1;
        return data.get((numDiaLectivo - 1) * numFilas + numFila);
    }

    /**
     *
     * @param numDia
     * @param horaInicio
     * @return
     */
    public HorarioItem retrieveData(int numDia, Hora horaInicio) {
        int numFila = horaInicioToNumeroFila(horaInicio);
        int numColumma = dataProyecto.getDiasSemanaLectivos().indexOf(numDia) + 1;
        if (numFila == -1) {
            return null;
        } else {
            return retrieveData(numColumma, numFila);
        }
    }

    /**
     *
     * @param numDia
     * @param r
     * @return
     */
    public HorarioItem retrieveData(int numDia, RangoHoras r) {
        return retrieveData(numDia, r.getInicio());
    }

    /**
     *
     * @param h
     * @return
     */
    public int getNumeroDeFila(HorarioItem h) {
        if (auxHorasHorario == null) {
            calculaIndicesAuxiliaresRangosHoras();
        }
        String hora = h.getRangoHoras().getInicio().toString();
        return auxHorasHorario.get(hora) + 1;
    }

    /**
     *
     * @param h
     */
    public void add(HorarioItem h) {
        Hora hora = h.getRangoHoras().getInicio();
        int numFila = 0;
        try {
            while (!hora.equals(rangosHoras.get(numFila).getInicio())) {
                numFila++;
            }
        } catch (IndexOutOfBoundsException ex) {
            System.err.println("Buscando " + h.getGrupo().getHashCarreraGrupoCurso() + " " + hora + " en " + rangosHoras);
            Logger.getLogger(DatosHojaHorario.class.getName()).log(Level.SEVERE, null, ex);
        }

        int numDiaLectivo = h.getDiaSemana();
        int numCasilla = ((numDiaLectivo - 1) * rangosHoras.size() + numFila);
        data.put(numCasilla, h);
    }

    /**
     * Este método rellena las casillas faltantes de la hoja de horario con
     * casillas libres.
     */
    public void rellenoConHorasLibres() {
        for (int numFila = 0; numFila < rangosHoras.size(); numFila++) {
            for (int numDia = 1; numDia <= 5; numDia++) {
                HorarioItem h = retrieveData(numDia, numFila);
                if (h == null)//Puede ser necesario poner un item aquí?
                {
                    boolean hayQueRellenar = true;
                    //Hago un bucle "para arriba" a ver si pertenece a un horarioitem largo
                    for (int n = 1; n <= numFila; n++) {
                        HorarioItem h2 = retrieveData(numDia, numFila - n);
                        if ((h2 != null) && (h2.getNumeroDeCasillasQueOcupa() > n)) {
                            hayQueRellenar = false;
                            break;
                        }
                    }
                    if (hayQueRellenar) {
                        RangoHoras r = rangosHoras.get(numFila);
                        boolean esTarde;
                        esTarde = ((dataProyecto.getTarde1().contieneRango(r)) || (dataProyecto.getTarde2().contieneRango(r)));
                        int numCasilla = (numDia - 1) * rangosHoras.size() + numFila;
                        HorarioItem h3 = new HorarioItem(null, null, null, null, r, esTarde, numDia, true, 1);
                        h3.setNumcasilla(numCasilla);
                        this.add(h3);
//                        System.out.println("Added item to " + numDia + ", " + numFila + "-->" + r);
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder resul = new StringBuilder("");
        for (int numFila = 0; numFila < rangosHoras.size(); numFila++) {
            resul.append(rangosHoras.get(numFila)).append("\t");
            for (int dia = 1; dia < 6; dia++) {
                HorarioItem h = retrieveData(dia, numFila);
                if (h == null) {
                    resul.append("null\t");
                } else {
                    if (h.isHuecoLibre()) {
                        resul.append("libr\t");
                    } else {
                        resul.append(h.getProfesor().getNombreCorto()).append("\t");
                    }

                }
            }
            resul.append("\n");
        }
        return resul.toString();
    }

    /**
     *
     * @param horaInicio
     * @return
     */
    public int horaInicioToNumeroFila(Hora horaInicio) {
        int numFila = 0;
        while ((!horaInicio.equals(rangosHoras.get(numFila).getInicio())) && numFila < rangosHoras.size()) {
            numFila++;
        }
        if (numFila == rangosHoras.size()) {
            numFila = -1;
        }
        return numFila;
    }
}

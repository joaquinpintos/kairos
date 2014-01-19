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
package restricciones.noHuecosEntreMedias;

import data.genetic.Asignacion;
import data.genetic.ListaSegmentos;
import data.genetic.PosibleSolucion;
import data.genetic.Segmento;
import data.restricciones.AbstractDlgRestriccion;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import data.restricciones.Restriccion;

/**
 * Comprueba que no haya huecos entre medias ni haya menos de 3 horas por clase
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class RNoHuecosEntreMedias extends Restriccion {

    private boolean tramoConHuecosEsteDia;
    private boolean diasPocoOcupados;
    final private HashMap<String, Integer> numeroCasillasPorDia;
    private boolean tramoConHuecosGlobal;
    private boolean penalizarHuecos;
    private boolean penalizarPocasClases;
    private int numMinimoHorassOcupadas;

    /**
     *
     */
    public RNoHuecosEntreMedias() {
        super();
        this.numMinimoHorassOcupadas = 2;
        this.penalizarHuecos = true;
        this.penalizarPocasClases = true;
        setUnique(true);//Restricción única. No puede repetirse.
        numeroCasillasPorDia = new HashMap<String, Integer>();
    }

    @Override
    public void inicializarDatos() {
        //Para cada aula, calculo cuántos casillas contiene cada día
        //VERSION-DEPENDENT: Depende de que cada día tenga el mísmo número de casillas!
        numeroCasillasPorDia.clear();
        int numDiasPorSemana = dataProyecto.getDiasSemanaLectivos().size();
        for (String hashAula : dataProyecto.getMapDatosPorAula().keySet()) {
            int numCasillas = dataProyecto.getMapDatosPorAula().get(hashAula).getListaCasillas().size();
            numeroCasillasPorDia.put(hashAula, numCasillas / numDiasPorSemana);
        }
    }

    /**
     *
     * @param posibleSolucion
     * @return
     */
    @Override
    public long calculaPeso(PosibleSolucion posibleSolucion) {
        int numMinimoCasillasOcupadas = (60 * numMinimoHorassOcupadas) / dataProyecto.getMinutosPorCasilla();
        tramoConHuecosGlobal = false;
        diasPocoOcupados = false;
        long suma = getSuma();
        setPeso(0);
        double coef = 1.3;
        for (String hashAula : posibleSolucion.getMapAsignaciones().keySet()) {
            Asignacion asig = posibleSolucion.getMapAsignaciones().get(hashAula);
//            int numCasillas = asig.getNumCasillas();
//            int num2 = asig.getAsignaciones().size();
            int state;
            //state 0: en casillas libres de principio mañana/tarde
            //paso a 1 si encuentro casilla ocupada
            //state 1: en casillas ocupadas.
            //Paso a 2 si encuentro casilla libre
            //state 2: en casillas libres de fin mañana/tarde
            //Si casilla libre aquí, falla test en este turno
            //0, 1 y 2
//            ListaCasillas lc = dataProyecto.getMapDatosPorAula().get(hashAula).getListaCasillas();
            ListaSegmentos ls = dataProyecto.getMapDatosPorAula().get(hashAula).getListaSegmentos();
            Segmento s;
            tramoConHuecosEsteDia = false;
            int numCasillasPorDia = numeroCasillasPorDia.get(hashAula);
            for (int dia = 0; dia < dataProyecto.getDiasSemanaLectivos().size(); dia++) {
                state = 0; //Estado 0
                int numCasillasOcupadasEsteDia = 0;
                tramoConHuecosEsteDia = false;
                //Bucle de principio a fin del turno (mañana/tarde)
                int numCasillaDesdeHueco = 0;
                for (int numCasilla = numCasillasPorDia * dia; numCasilla < numCasillasPorDia * (dia + 1); numCasilla++) {
                    int numSegmento = asig.getQueSegmentoHayEnCasilla(numCasilla);
                    s = ls.get(numSegmento);
                    if (!s.isHuecoLibre()) {
                        numCasillasOcupadasEsteDia++;
                    }
                    if (penalizarHuecos) {
                        switch (state) {
                            case 0:
                                if (!s.isHuecoLibre()) {
                                    state = 1; //Paso a estado 1
                                }
                                break;
                            case 1:
                                if (s.isHuecoLibre()) {
                                    numCasillaDesdeHueco = numCasilla;
                                    state = 2; //Paso a estado 2
                                }
                                break;
                            case 2:
                                if (!s.isHuecoLibre()) {
                                    this.sumaPeso(suma);
                                    suma *= coef;
                                    tramoConHuecosGlobal = true;
                                    state = 1; //Vuelvo al estado 1
                                    if (marcaCasillasConflictivas) {
                                        for (int n = numCasillaDesdeHueco; n < numCasilla; n++) {
                                            marcaCasillaComoConflictiva(hashAula, n);
                                        }
                                    }
                                }
                                break;
                            default:
                                break;
                        }
                    } //End of if (penalizarHuecos)...
                }//End of bucle for numCasilla....
                //Acabado el bucle en este día, si es necesario compruebo si hay pocas clases o no
                if (penalizarPocasClases) {
                    if ((numCasillasOcupadasEsteDia < numMinimoCasillasOcupadas) && (numCasillasOcupadasEsteDia > 0)) {
                        this.sumaPeso(suma);
                        suma *= coef;
                        diasPocoOcupados = true;
                        if (marcaCasillasConflictivas) {//Marco todas las casillas ocupadas del día
                            for (int numCasilla = numCasillasPorDia * dia; numCasilla < numCasillasPorDia * (dia + 1); numCasilla++) {
                                {
                                    int numSegmento = asig.getQueSegmentoHayEnCasilla(numCasilla);
                                    s = ls.get(numSegmento);
                                    if (!s.isHuecoLibre()) {
                                        marcaCasillaComoConflictiva(hashAula, numCasilla);
                                    }
                                }
                            }
                        }
                    }//End of   if ((numCasillasOcupadasEsteDia < numMinimoCasillasOcupadas)...
                }//End of  if (penalizarPocasClases)
            }
        }

        return getPeso();
    }
    @Override
    public AbstractDlgRestriccion getConfigDlg(Object parent)
{
    return new DlgNoHuecosEntreMedias(null, this);
}
    
//    public boolean getConfigDlg(Object parent) {
//        DlgNoHuecosEntreMedias dlg = new DlgNoHuecosEntreMedias(null, true, this);
//        dlg.setLocationRelativeTo(null);
//        dlg.setVisible(true);
//        boolean resul = false;
//        if (dlg.getReturnStatus() == DlgNoHuecosEntreMedias.RET_OK) {
//            resul = true;
//        }
//        if (dlg.getReturnStatus() == DlgNoHuecosEntreMedias.RET_CANCEL) {
//            resul = false;
//        }
//        return resul;
//    }

    /**
     *
     * @return
     */
    @Override
    public String descripcion() {
        String resul = "<html>";
        if (penalizarHuecos) {
            resul += "No puede haber huecos libres entre clases. ";
        }
        if (penalizarPocasClases) {
            resul += "No puede haber días con menos de <b>" + numMinimoHorassOcupadas + "</b> horas ocupadas";
        }
        return resul + "</html>";
    }

    @Override
    public String descripcionCorta() {
        return "Penalizar huecos y/o días con pocas clases";
    }

    @Override
    public String mensajeDeAyuda() {
        return "<html>Esta restricción penaliza las clases con huecos<br>"
                + "vacíos entre dos asignaturas y/o los días en que<br>"
                + "haya menos de un número fijado de clases.</html>";
    }

    @Override
    public String getMensajeError() {
        String resul = "<html>";

        if (diasPocoOcupados) {
            resul += "Hay dias con menos de <b>" + numMinimoHorassOcupadas + "</b> horas de clase. ";
        }
        if (tramoConHuecosGlobal) {
            resul += "Hay tramos de clases con huecos en medio";
        }
        return resul + "</html>";
    }

    @Override
    protected void writeConfig(Node parent) {
        //Nada que hacer!
    }

    @Override
    public void readConfig(Element parent) {
        //Nada que hacer!
    }

    public boolean isPenalizarHuecos() {
        return penalizarHuecos;
    }

    public void setPenalizarHuecos(boolean penalizarHuecos) {
        this.penalizarHuecos = penalizarHuecos;
    }

    public boolean isPenalizarPocasClases() {
        return penalizarPocasClases;
    }

    /**
     *
     * @param penalizarPocasClases
     */
    public void setPenalizarPocasClases(boolean penalizarPocasClases) {
        this.penalizarPocasClases = penalizarPocasClases;
    }

    /**
     *
     * @return
     */
    public int getNumMinimoCasillasOcupadas() {
        return numMinimoHorassOcupadas;
    }

    /**
     *
     * @param numMinimoCasillasOcupadas
     */
    public void setNumMinimoCasillasOcupadas(int numMinimoCasillasOcupadas) {
        this.numMinimoHorassOcupadas = numMinimoCasillasOcupadas;
    }
    
    
     @Override
    public void copyBasicValuesFrom(Restriccion r) {
        if (r instanceof RNoHuecosEntreMedias) {
            RNoHuecosEntreMedias rcopy = (RNoHuecosEntreMedias) r;
            this.penalizarHuecos=rcopy.isPenalizarHuecos();
            this.penalizarPocasClases=rcopy.isPenalizarPocasClases();
        }

    }

    @Override
    public void clearAuxiliaryData() {
        numeroCasillasPorDia.clear();
    }
    
}
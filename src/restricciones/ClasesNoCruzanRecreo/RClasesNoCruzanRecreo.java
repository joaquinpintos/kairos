
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restricciones.ClasesNoCruzanRecreo;

import data.DataProyecto;
import data.Hora;
import data.RangoHoras;
import data.genetic.Asignacion;
import data.genetic.Casilla;
import data.genetic.ListaCasillas;
import data.genetic.PosibleSolucion;
import data.restricciones.Restriccion;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class RClasesNoCruzanRecreo extends Restriccion implements Serializable {

    private final HashMap<String, Integer[]> casillaJustoAntesRecreo;
    private final HashMap<String, Integer[]> casillaJustoDespuesRecreo;
    private final HashMap<String, Integer[]> casillaJustoAntesFinalDia;
    private final HashMap<String, Integer[]> casillaJustoDespuesFinalDia;

    /**
     *
     */
    public RClasesNoCruzanRecreo() {
        this(null);
    }

    /**
     *
     * @param dataProyecto
     */
    public RClasesNoCruzanRecreo(DataProyecto dataProyecto) {
        super(dataProyecto);
        casillaJustoAntesRecreo = new HashMap<String, Integer[]>();
        casillaJustoDespuesRecreo = new HashMap<String, Integer[]>();
        casillaJustoDespuesFinalDia = new HashMap<String, Integer[]>();
        casillaJustoAntesFinalDia = new HashMap<String, Integer[]>();
    }

    @Override
    public void inicializarDatos() {
        casillaJustoAntesFinalDia.clear();
        casillaJustoAntesRecreo.clear();
        casillaJustoDespuesFinalDia.clear();
        casillaJustoDespuesRecreo.clear();
        //Calculo todas las casillas que son "recreo". Esto NO incluye a los finales del dia
//        Hora horaRecreoMañana = dataProyecto.getMañana1().getFin();
//        Hora horaRecreoTarde = dataProyecto.getTarde1().getFin();
        for (String hashAula : dataProyecto.getMapDatosPorAula().keySet()) {
            casillaJustoAntesRecreo.put(hashAula, new Integer[]{-1, -1, -1, -1, -1});
            casillaJustoDespuesRecreo.put(hashAula, new Integer[]{-1, -1, -1, -1, -1});
            casillaJustoDespuesFinalDia.put(hashAula, new Integer[]{-1, -1, -1, -1, -1});
            casillaJustoAntesFinalDia.put(hashAula, new Integer[]{-1, -1, -1, -1, -1});
            try {
                ListaCasillas lc = dataProyecto.getMapDatosPorAula().get(hashAula).getListaCasillas();
                for (int n = 0; n < lc.size(); n++) {
                    Casilla c = lc.get(n);
                    int dia = c.getDiaSemana();//Va de 1 a 5!!! El array de 0 a 4
                    RangoHoras h = c.getRangoHora();
                    if ((h.getFin().equals(dataProyecto.getMañana1().getFin())) || (h.getFin().equals(dataProyecto.getTarde1().getFin()))) {
                        casillaJustoAntesRecreo.get(hashAula)[dia - 1] = n;
                        casillaJustoDespuesRecreo.get(hashAula)[dia - 1] = n+1;
                    }
//                    if ((h.getInicio().equals(dataProyecto.getMañana2().getInicio())) || (h.getInicio().equals(dataProyecto.getTarde2().getInicio()))) {
//                        casillaJustoDespuesRecreo.get(hashAula)[dia - 1] = n;
//                    }
                    if ((h.getFin().equals(dataProyecto.getMañana2().getFin())) || (h.getFin().equals(dataProyecto.getTarde2().getFin()))) {
                        casillaJustoAntesFinalDia.get(hashAula)[dia - 1] = n;
                        casillaJustoDespuesFinalDia.get(hashAula)[dia - 1] = n+1;
                    }
                }
            } catch (Exception ex) {
            }
        }
//        System.out.println(casillaJustoAntesRecreo);
    }

    /**
     *
     * @param posibleSolucion
     * @return
     */
    @Override
    public long calculaPeso(PosibleSolucion posibleSolucion) {
        setPeso(0);
        long suma = 100;
        double coef = 1.3;
        for (String hashAula : posibleSolucion.getMapAsignaciones().keySet()) {
            Asignacion asig = posibleSolucion.getAsignacion(hashAula);

            for (int n = 0; n < asig.size(); n++) {
                ArrayList<Integer> r = asig.getRangoCasillasOcupadas(n);
                if (r.size() > 1) {
                    diaLoop:
                    for (int dia = 0; dia < 5; dia++) {
                        int numCasillaAntesRecreo = casillaJustoAntesRecreo.get(hashAula)[dia];
                        int numCasillaDespuesRecreo = casillaJustoDespuesRecreo.get(hashAula)[dia];
                        int numCasillaAntesFinDia = casillaJustoAntesFinalDia.get(hashAula)[dia];
                        int numCasillaDespuesFinDia = casillaJustoDespuesFinalDia.get(hashAula)[dia];
                        if (dia==4) numCasillaDespuesFinDia=1;
                          if (asig.isCasillasEnMismoSegmento(numCasillaAntesRecreo, numCasillaDespuesRecreo)) {
                            sumaPeso(suma);
                            suma *= coef;
                            if (marcaCasillasConflictivas) {
                                int numSegmento=asig.getQueSegmentoHayEnCasilla(numCasillaAntesRecreo);
                                    marcaCasillaComoConflictiva(hashAula, asig.enQueCasillaEstaSegmento(numSegmento));
                            }
                        }
                            if (asig.isCasillasEnMismoSegmento(numCasillaAntesFinDia, numCasillaDespuesFinDia)) {
                            sumaPeso(suma);
                            suma *= coef;
                            if (marcaCasillasConflictivas) {
                                int numSegmento=asig.getQueSegmentoHayEnCasilla(numCasillaAntesFinDia);
                                    marcaCasillaComoConflictiva(hashAula, asig.enQueCasillaEstaSegmento(numSegmento));
                            }
                        }
                        
                    }
                }//End of if (r.size() > 1) {
            }

        }
        return getPeso();
    }

    @Override
    public boolean lanzarDialogoDeConfiguracion(Object parent) {
        return true;
    }

    /**
     *
     * @return
     */
    @Override
    public String descripcion() {
        return "Las clases no pueden solaparse con el recreo.";
    }

    /**
     *
     * @return
     */
    @Override
    public String descripcionCorta() {
        return "Las clases no pueden solaparse con el recreo.";
    }

    /**
     *
     * @return
     */
    @Override
    public String mensajeDeAyuda() {
        return "";
    }

    /**
     *
     * @return
     */
    @Override
    public String getMensajeError() {
        return "Hay clases que se solapan con el recreo";
    }

    /**
     *
     * @param parent
     */
    @Override
    protected void writeConfig(Node parent) {
    }

    /**
     *
     * @param parent
     */
    @Override
    public void readConfig(Element parent) {
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data.horarios;

import data.DataProyecto;
import data.asignaturas.Grupo;
import data.profesores.Profesor;
import data.genetic.Asignacion;
import data.genetic.Casilla;
import data.genetic.ListaCasillas;
import data.genetic.ListaSegmentos;
import data.genetic.Segmento;
import data.genetic.PosibleSolucion;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class HorarioConstructor {

    /**
     * Construyo horario a partir de solución dada por array de enteros.
     *
     * @param sol
     * @param dataProyecto
     * @return
     * @throws Exception  
     */
    public static Horario constructor(PosibleSolucion sol, DataProyecto dataProyecto) throws Exception {
        Horario resul = new Horario(sol);
        //Este hashmap me relaciona un hashaula y un numero de segmento con su correspondiente horarioItem
        //Es util para marcar rápidamente los horarioItem conflictivos.

        Segmento s;
        Casilla c;
        Profesor profe;
        Profesor profeNulo = new Profesor("", "", "NUL");
        Grupo grupoNulo = new Grupo("libre");
        for (Asignacion asig : sol.getMapAsignaciones().values()) {
            String hashAula = asig.getHashAula();
            ListaSegmentos listaSegmentos = dataProyecto.getDatosPorAula(hashAula).getListaSegmentos();
            ListaCasillas listaCasillas = dataProyecto.getDatosPorAula(hashAula).getListaCasillas();
            for (int k = 0; k < asig.getAsignaciones().size(); k++)//Recorro todos los segmentos.
            {
                s = listaSegmentos.get(asig.getAsignaciones().get(k));
                c = listaCasillas.get(asig.getQueCasilla().get(k));//Primera casilla en la que he colocado el segmento.
                Grupo g = s.getGrupo();
                profe = s.getProfesor();
//                if (!s.isHuecoLibre()) {
                    if (profe == null) {
                        profe = profeNulo;
                    }
                    if (g == null) {
                        g = grupoNulo;
                    }
                    boolean esTarde=(hashAula.contains("@T"));
                    HorarioItem item = new HorarioItem(profe, g, g.getParent(), c.getAula(), s.getRangoHoraPuestoEnCasilla(c), esTarde,c.getDiaSemana(),s.isHuecoLibre(),s.getNumeroDeCasillasQueOcupa());
                    item.setNumeroSegmento(asig.getAsignaciones().get(k));
                    item.setNumcasilla(asig.getQueCasilla().get(k));
                    resul.add(item);
                    resul.añadeItemAMapa(hashAula,k,item);
//                }
            }
        }
        return resul;
    }
}

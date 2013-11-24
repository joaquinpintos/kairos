/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restricciones.ProfesorMaximoHorasPorDia;

import data.DataProyecto;
import data.genetic.Asignacion;
import data.genetic.Casilla;
import data.genetic.ListaCasillas;
import data.genetic.ListaSegmentos;
import data.genetic.PosibleSolucion;
import data.genetic.Segmento;
import data.profesores.Profesor;
import data.restricciones.Restriccion;
import java.util.HashMap;
import java.util.HashSet;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class RProfesorMaximoHorasPorDia extends Restriccion {

    private static final long serialVersionUID = 1L;
    private int numMaximoHoras;
    private HashSet<Profesor> listaProfesoresConflictivos;
    private HashMap<String, Integer> numeroCasillasPorDia;
    //Almacena temporalmente el número de clases que da un profesor un día determinado
    private HashMap<Profesor, Integer[]> clasesPorDiaProfesor;
    private Integer numMaximoCasillas;

    /**
     * Constructor por defecto
     */
    public RProfesorMaximoHorasPorDia() {
        this(null);
    }

    /**
     *
     * @param dataProyecto
     */
    public RProfesorMaximoHorasPorDia(DataProyecto dataProyecto) {
        super(dataProyecto);
        numMaximoHoras = 3; //Valor inicial
        listaProfesoresConflictivos = new HashSet<Profesor>();
        clasesPorDiaProfesor = new HashMap<Profesor, Integer[]>();
    }

    @Override
    public void inicializarDatos() {
        numMaximoCasillas = numMaximoHoras * 60 / dataProyecto.getMinutosPorCasilla();
        //Para cada aula, calculo cuántos casillas contiene cada día
        //VERSION-DEPENDENT: Depende de que cada día tenga el mísmo número de casillas!
        numeroCasillasPorDia = new HashMap<String, Integer>();
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
        clasesPorDiaProfesor.clear();
        clearConflictivos();
        setPeso(0);
        double coef = 1.3;
        long suma = 100;
        for (String hashAula : posibleSolucion.getMapAsignaciones().keySet()) {
            Asignacion asig = posibleSolucion.getMapAsignaciones().get(hashAula);
//            int numCasillas = asig.getNumCasillas();
//            int num2 = asig.getAsignaciones().size();
            ListaCasillas lc = dataProyecto.getMapDatosPorAula().get(hashAula).getListaCasillas();
            ListaSegmentos ls = dataProyecto.getMapDatosPorAula().get(hashAula).getListaSegmentos();
            Segmento s;
            int numCasillasPorDia = numeroCasillasPorDia.get(hashAula);
            for (int dia = 0; dia < dataProyecto.getDiasSemanaLectivos().size(); dia++) {
                for (int numCasilla = numCasillasPorDia * dia; numCasilla < numCasillasPorDia * (dia + 1); numCasilla++) {
                    int numSegmento = asig.getQueSegmentoHayEnCasilla(numCasilla);
//                    Casilla c = lc.get(numCasilla);
//                    Hora hora = c.getHora();
//                    int diaSemana = c.getDiaSemana();

                    s = ls.get(numSegmento);
                    if (!s.isHuecoLibre()) {
                        Profesor p = s.getProfesor();
                        if (!clasesPorDiaProfesor.containsKey(p)) {
                            clasesPorDiaProfesor.put(p, new Integer[]{0, 0, 0, 0, 0});
                        }
                        clasesPorDiaProfesor.get(p)[dia] += s.getNumeroDeCasillasQueOcupa();
                        numCasilla += s.getNumeroDeCasillasQueOcupa() - 1;
                    }
                }//End of bucle for numCasilla....
                //Acabado el bucle en este día, si es necesario compruebo si hay pocas clases 
            }
        }
        for (Profesor p : clasesPorDiaProfesor.keySet()) {
            Integer[] num = clasesPorDiaProfesor.get(p);
            for (int n = 0; n < 5; n++) {
                if (num[n] > numMaximoCasillas) {
                    sumaPeso(suma);
                    suma *= coef;
                    if (marcaCasillasConflictivas) {
                        listaProfesoresConflictivos.add(p);
                        //tengo que marcar todas las casillas del profesor p en dia n
                        marcaCasillasProfesorDia(p, n, posibleSolucion);

                    }

                }
            }
        }
        return getPeso();
    }

    @Override
    public boolean lanzarDialogoDeConfiguracion(Object parent) {
        JDlgProfesorMaximoHoras dlg = new JDlgProfesorMaximoHoras(null, true, this);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
        boolean resul = false;
        if (dlg.getReturnStatus() == JDlgProfesorMaximoHoras.RET_OK) {
            resul = true;
        }
        if (dlg.getReturnStatus() == JDlgProfesorMaximoHoras.RET_CANCEL) {
            resul = false;
        }
        return resul;
    }

    /**
     *
     * @return
     */
    @Override
    public String descripcion() {
        return "Los profesores no pueden dar más de " + numMaximoHoras + " horas de clas por día";
    }

    /**
     *
     * @return
     */
    @Override
    public String descripcionCorta() {
        return "Numéro máximo de horas por profesor al día";
    }

    /**
     *
     * @return
     */
    @Override
    public String mensajeDeAyuda() {
        return "Especifica el número máximo de horas que puede dar un profesor al día";
    }

    /**
     *
     * @return
     */
    @Override
    public String getMensajeError() {
        return "Los profesores " + listaProfesoresConflictivos + " dan más de " + numMaximoHoras + " horas algún día";
    }

    /**
     *
     * @param parent
     */
    @Override
    protected void writeConfig(Node parent) {
        creaNodoSimpleConTexto(parent, "horas_maximo", numMaximoHoras);
    }

    /**
     *
     * @param parent
     */
    @Override
    public void readConfig(Element parent) {
        String strMaxHoras = valorPrimerElementoConNombre(parent, "horas_maximo");
        this.setNumMaximoHoras(Integer.valueOf(strMaxHoras));
    }

    private void marcaCasillasProfesorDia(Profesor p, int diaSemana, PosibleSolucion posibleSolucion) {
        for (String hashAula : posibleSolucion.getMapAsignaciones().keySet()) {
            Asignacion asig = posibleSolucion.getMapAsignaciones().get(hashAula);
            ListaCasillas lc = dataProyecto.getMapDatosPorAula().get(hashAula).getListaCasillas();
            ListaSegmentos ls = dataProyecto.getMapDatosPorAula().get(hashAula).getListaSegmentos();
            for (int n : asig.getAsignaciones()) {
                Segmento s = ls.get(n);
                if (p.equals(s.getProfesor())) {
                    int numCasilla = asig.enQueCasillaEstaSegmento(n);
                    Casilla c = lc.get(numCasilla);
                    if (c.getDiaSemana() == diaSemana + 1) {
                        marcaCasillaComoConflictiva(hashAula, numCasilla);
                    }
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public int getNumMaximoHoras() {
        return numMaximoHoras;
    }

    /**
     *
     * @param numMaximoHoras
     */
    public void setNumMaximoHoras(int numMaximoHoras) {
        this.numMaximoHoras = numMaximoHoras;
        setDirty(true);

    }
}

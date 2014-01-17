/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restricciones.ProfesorMinimoHorasPorDia;

import data.DataProject;
import data.genetic.Asignacion;
import data.genetic.Casilla;
import data.genetic.ListaCasillas;
import data.genetic.ListaSegmentos;
import data.genetic.PosibleSolucion;
import data.genetic.Segmento;
import data.profesores.Profesor;
import data.restricciones.Restriccion;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class RProfesorMinimoHorasPorDia extends Restriccion {

    private static final long serialVersionUID = 27112013L;
    private int numMinimoHoras;
    private HashSet<Profesor> listaProfesoresConflictivos;
    private HashMap<String, Integer> numeroCasillasPorDia;
    //Almacena temporalmente el número de clases que da un profesor un día determinado
    private HashMap<Profesor, Integer[]> clasesPorDiaProfesor;
    private Integer numMinimoCasillas;

    /**
     * Constructor por defecto
     */
    public RProfesorMinimoHorasPorDia() {
        this(null);
    }

    /**
     *
     * @param dataProyecto
     */
    public RProfesorMinimoHorasPorDia(DataProject dataProyecto) {
        super(dataProyecto);
        numMinimoHoras = 3; //Valor inicial
        listaProfesoresConflictivos = new HashSet<Profesor>();
        clasesPorDiaProfesor = new HashMap<Profesor, Integer[]>();
    }

    @Override
    public void inicializarDatos() {
        numMinimoCasillas = numMinimoHoras * 60 / dataProyecto.getMinutosPorCasilla();
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
        listaProfesoresConflictivos.clear();
        clearConflictivos();
        setPeso(0);
        double coef = 1.3;
        long suma = getSuma();
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
                if ((num[n]>0)&&(num[n] < numMinimoCasillas)) {
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
        JDlgProfesorMinimoHoras dlg = new JDlgProfesorMinimoHoras(null, true, this);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
        boolean resul = false;
        if (dlg.getReturnStatus() == JDlgProfesorMinimoHoras.RET_OK) {
            resul = true;
        }
        if (dlg.getReturnStatus() == JDlgProfesorMinimoHoras.RET_CANCEL) {
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
        return "<html>Los profesores no pueden dar menos de <b>" + numMinimoHoras + "</b> horas de clase en un día</html>";
    }

    /**
     *
     * @return
     */
    @Override
    public String descripcionCorta() {
        return "Numéro mínimo de horas por profesor al día";
    }

    /**
     *
     * @return
     */
    @Override
    public String mensajeDeAyuda() {
        return "<html>Especifica el número mínimo de horas<br>que puede dar un profesor los días<br>que de clase.</html>";
    }

    /**
     *
     * @return
     */
    @Override
    public String getMensajeError() {
       ArrayList<Profesor> p=new ArrayList(listaProfesoresConflictivos);
       boolean plural=(p.size()>1);
        return "<html>"+(plural?"Los":"El")+" profesor"+(plural?"es ":" ") + formatTeachersList(p) + " da"+(plural?"n":"")+" menos de <b>" + numMinimoHoras + "</b> horas algún día que da clase.</html>";
    }

    /**
     *
     * @param parent
     */
    @Override
    protected void writeConfig(Node parent) {
        creaNodoSimpleConTexto(parent, "horas_maximo", numMinimoHoras);
    }

    /**
     *
     * @param parent
     */
    @Override
    public void readConfig(Element parent) {
        String strMaxHoras = valorPrimerElementoConNombre(parent, "horas_maximo");
        this.setNumMinimoHoras(Integer.valueOf(strMaxHoras));
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
    public int getNumMinimoHoras() {
        return numMinimoHoras;
    }

    /**
     *
     * @param numMaximoHoras
     */
    public void setNumMinimoHoras(int numMaximoHoras) {
        this.numMinimoHoras = numMaximoHoras;

    }
}

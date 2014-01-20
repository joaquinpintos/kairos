/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restricciones.ProfesorMaximoHorasPorDia;

import data.DataProject;
import data.genetic.Asignacion;
import data.genetic.Casilla;
import data.genetic.ListaCasillas;
import data.genetic.ListaSegmentos;
import data.genetic.PosibleSolucion;
import data.genetic.Segmento;
import data.profesores.Profesor;
import data.restricciones.AbstractDlgRestriccion;
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
public class RProfesorMaximoHorasPorDia extends Restriccion {

    private static final long serialVersionUID = 27112013L;

    //Basic values
    private int numMaximoHoras;

    //Auxiliary data
    private HashSet<Profesor> listaProfesoresConflictivos;
    private HashMap<String, Integer> numeroCasillasPorDia;
    //Almacena temporalmente el número de clases que da un profesor un día determinado
    private HashMap<Profesor, Integer[]> clasesPorDiaProfesor;
    private int numMaximoCasillas;

    /**
     * Constructor por defecto
     */
    public RProfesorMaximoHorasPorDia() {
        this(null);
    }

    /**
     *
     * @param dataProject
     */
    public RProfesorMaximoHorasPorDia(DataProject dataProject) {
        super(dataProject);
        numMaximoHoras = 3; //Valor inicial
        listaProfesoresConflictivos = new HashSet<Profesor>();
        clasesPorDiaProfesor = new HashMap<Profesor, Integer[]>();
    }

    @Override
    public void inicializarDatos() {
        numMaximoCasillas = numMaximoHoras * 60 / dataProject.getMinutosPorCasilla();
        //Para cada aula, calculo cuántos casillas contiene cada día
        //VERSION-DEPENDENT: Depende de que cada día tenga el mísmo número de casillas!
        numeroCasillasPorDia = new HashMap<String, Integer>();
        int numDiasPorSemana = dataProject.getDiasSemanaLectivos().size();
        for (String hashAula : dataProject.getMapDatosPorAula().keySet()) {
            int numCasillas = dataProject.getMapDatosPorAula().get(hashAula).getListaCasillas().size();
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
            ListaCasillas lc = dataProject.getMapDatosPorAula().get(hashAula).getListaCasillas();
            ListaSegmentos ls = dataProject.getMapDatosPorAula().get(hashAula).getListaSegmentos();
            Segmento s;
            int numCasillasPorDia = numeroCasillasPorDia.get(hashAula);
            for (int dia = 0; dia < dataProject.getDiasSemanaLectivos().size(); dia++) {
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
    public AbstractDlgRestriccion getConfigDlg(Object parent) {
        return new JDlgProfesorMaximoHoras(null, this);
    }

    /**
     *
     * @return
     */
    @Override
    public String descripcion() {
        return "<html>Los profesores no pueden dar más de <b>" + numMaximoHoras + "</b> horas de clase por día</html>";
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
        return "<html>Especifica el número máximo de horas<br>que puede dar un profesor al día</html>";
    }

    /**
     *
     * @return
     */
    @Override
    public String getMensajeError() {
        ArrayList<Profesor> p = new ArrayList(listaProfesoresConflictivos);
        boolean plural = (p.size() > 1);
        return "<html>" + (plural ? "Los" : "El") + " profesor" + (plural ? "es " : " ") + formatTeachersList(p) + " da" + (plural ? "n" : "") + " más de <b>" + numMaximoHoras + "</b> horas algún día</html>";
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
            ListaCasillas lc = dataProject.getMapDatosPorAula().get(hashAula).getListaCasillas();
            ListaSegmentos ls = dataProject.getMapDatosPorAula().get(hashAula).getListaSegmentos();
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
    }

    @Override
    public void copyBasicValuesFrom(Restriccion r) {
        if (r instanceof RProfesorMaximoHorasPorDia) {
            RProfesorMaximoHorasPorDia rcopy = (RProfesorMaximoHorasPorDia) r;
            this.numMaximoHoras = rcopy.getNumMaximoHoras();
        }

    }

    @Override
    public void clearAuxiliaryData() {
        listaProfesoresConflictivos.clear();
        numeroCasillasPorDia.clear();
        clasesPorDiaProfesor.clear();
        numMaximoCasillas = 0;
    }
}

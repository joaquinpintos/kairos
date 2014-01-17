/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package restricciones.clasesCondensadasParaProfesor;

import data.profesores.Profesor;
import data.genetic.PosibleSolucion;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import data.restricciones.Restriccion;
import data.genetic.Asignacion;
import data.genetic.Casilla;
import data.genetic.DatosPorAula;
import data.genetic.ListaCasillas;
import data.genetic.Segmento;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class RClasesCondensadasParaProfesor extends Restriccion {

    private static final long serialVersionUID = 27112013L;
    private Profesor profesor;
    private final HashMap<String, ArrayList<Integer>> segmentosImpartidos; //Segmentos impartidos por el profesor
    private int numeroMaximoDias;

    /**
     * Constructor por defecto
     */
    public RClasesCondensadasParaProfesor() {
        this.numeroMaximoDias = 3;
        segmentosImpartidos = new HashMap<String, ArrayList<Integer>>();
    }

    @Override
    public void inicializarDatos() {
        //Calculo los segmentos que imparte dicho profesor
        segmentosImpartidos.clear();
        HashMap<String, DatosPorAula> map = dataProyecto.getMapDatosPorAula();
        //Busco entre todas las aulas aquellas que tengan segmentos impartidos por el profesor
        for (String hashAula : map.keySet()) {
            ArrayList<Integer> segImpartAula = new ArrayList<Integer>();
            ArrayList<Segmento> ls = map.get(hashAula).getListaSegmentos().getSegmentos();
            for (int n = 0; n < ls.size(); n++) {
                if (!ls.get(n).isHuecoLibre()) {
                    if (ls.get(n).getProfesor().equals(this.profesor)) {
                        segImpartAula.add(n);
                    }
                }
            }
            if (segImpartAula.size() > 0) {
                segmentosImpartidos.put(hashAula, segImpartAula);
            }
        }
    }

    /**
     *
     * @return
     */
    public int getNumeroMaximoDias() {
        return numeroMaximoDias;
    }

    /**
     *
     * @param numeroMaximoDias
     */
    public void setNumeroMaximoDias(int numeroMaximoDias) {
        this.numeroMaximoDias = numeroMaximoDias;
    }

    /**
     *
     * @param posibleSolucion
     * @return
     */
    @Override
    public long calculaPeso(PosibleSolucion posibleSolucion) {
        clearConflictivos();
        setPeso(0);
        long suma = getSuma();
        double coef = 1.3;
        HashSet<Integer> diasQueImparte = new HashSet<Integer>();
        for (String hashAula : segmentosImpartidos.keySet()) {
            Asignacion asig = posibleSolucion.getAsignacion(hashAula);
            ArrayList<Integer> segImpart = segmentosImpartidos.get(hashAula);
            ListaCasillas lc = dataProyecto.getDatosPorAula(hashAula).getListaCasillas();
//            ListaSegmentos ls = dataProyecto.getDatosPorAula(hashAula).getListaSegmentos();
            for (int n = 0; n < segImpart.size(); n++) {
                Casilla cas = lc.get(asig.enQueCasillaEstaSegmento(segImpart.get(n)));

                diasQueImparte.add(cas.getDiaSemana());
            }
        }
        int offset = diasQueImparte.size() - numeroMaximoDias;
//        System.out.println(offset);
        while (offset > 0) {
            sumaPeso(suma);
            suma *= coef;
            if ((offset == 1) && marcaCasillasConflictivas) {
                marcaTodaDocenciaComoConflictiva(posibleSolucion);
            }
            offset--;
        }
        return getPeso();
    }

    private void marcaTodaDocenciaComoConflictiva(PosibleSolucion posibleSolucion) {
//        HashSet<Integer> diasQueImparte = new HashSet<Integer>();
        for (String hashAula : segmentosImpartidos.keySet()) {
            Asignacion asig = posibleSolucion.getAsignacion(hashAula);
            ArrayList<Integer> segImpart = segmentosImpartidos.get(hashAula);
            for (int n = 0; n < segImpart.size(); n++) {
                int numCasilla = asig.enQueCasillaEstaSegmento(segImpart.get(n));
                marcaCasillaComoConflictiva(hashAula, numCasilla);
            }
        }
    }

    @Override
    public boolean lanzarDialogoDeConfiguracion(Object parent) {
        boolean resul = false;
        DlgClasesCondensadasParaProfesor dlg = new DlgClasesCondensadasParaProfesor(null, true, this);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
        if (dlg.getReturnStatus() == DlgClasesCondensadasParaProfesor.RET_OK) {
            resul = true;
        }
        if (dlg.getReturnStatus() == DlgClasesCondensadasParaProfesor.RET_CANCEL) {
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
        return "<html>El profesor <b>" + profesor + "</b> no puede dar sus clases en más de <b>" + numeroMaximoDias + "</b> días</html>";
    }

    /**
     *
     * @return
     */
    @Override
    public String descripcionCorta() {
        return "Condensar las clases de un profesor";
    }

    /**
     *
     * @return
     */
    @Override
    public String mensajeDeAyuda() {
        return "<html>Penaliza si los días en los que se reparte<br>"+
                "la docencia de un profesor supera un máximo dado.";
    }

    /**
     *
     * @return
     */
    @Override
    public String getMensajeError() {
        return "<html>El profesor <b>" + profesor + "</b> reparte su docencia en más de <b>" + numeroMaximoDias + "</b> días</html>";
    }

    /**
     *
     * @return
     */
    public Profesor getProfesor() {
        return profesor;
    }

    /**
     *
     * @param profesor
     * @throws Exception
     */
    public void setProfesor(Profesor profesor) throws Exception {
        if (profesor == null) {
            throw new Exception("");
        }
        this.profesor = profesor;
    }

    /**
     *
     * @param parent
     */
    @Override
    protected void writeConfig(Node parent) {
        creaNodoSimpleConTexto(parent, "profesor", profesor.hash());
        creaNodoSimpleConTexto(parent, "dias_maximo", numeroMaximoDias);
    }

    /**
     *
     * @param parent
     */
    @Override
    public void readConfig(Element parent) {
        String hashProfesor = valorPrimerElementoConNombre(parent, "profesor");

        try {
            Profesor p = dataProyecto.getDataProfesores().buscaProfesorPorHash(hashProfesor);
            this.setProfesor(p);
        } catch (Exception ex) {
            Logger.getLogger(RClasesCondensadasParaProfesor.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.setNumeroMaximoDias(Integer.valueOf(valorPrimerElementoConNombre(parent, "dias_maximo")));

    }
}

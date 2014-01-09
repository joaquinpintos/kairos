package restricciones.profesorCiertosDias;

import data.ArrayRangoHoras;
import data.MyConstants;
import data.profesores.Profesor;
import data.genetic.Asignacion;
import data.genetic.Casilla;
import data.genetic.DatosPorAula;
import data.genetic.PosibleSolucion;
import data.genetic.Segmento;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import data.restricciones.Restriccion;
import java.io.Serializable;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class RProfesorCiertosDias extends Restriccion {

    //false=los días referidos son cuando NO puede.
    //true=los días referidos son los únicos que puede.
    private Boolean puedeEstosDias;
    //Map: dia de la semana -> lista de rangos horarios.
    private final HashMap<Integer, ArrayRangoHoras> rangos;
    static final Character[] inicialesSemana = {'L', 'M', 'X', 'J', 'V'};
    private Profesor profesor;
    private String observaciones;
    private final HashMap<String, ArrayList<Integer[]>> segmentosConflictivos;
    private final HashMap<String, ArrayList<Integer>> casillasConflictivas;
    //Variables para escribir/leer de XML

    /**
     *
     */
    public RProfesorCiertosDias() {
        super(null);
        this.observaciones = "";
        puedeEstosDias = false;
        segmentosConflictivos = new HashMap<String, ArrayList<Integer[]>>();
        casillasConflictivas = new HashMap<String, ArrayList<Integer>>();
        rangos = new HashMap<Integer, ArrayRangoHoras>();
        for (int n = 1; n <= 5; n++) {
            rangos.put(n, new ArrayRangoHoras());
        }
    }

    @Override
    public void inicializarDatos() {
        segmentosConflictivos.clear();
        casillasConflictivas.clear();
        HashMap<String, DatosPorAula> map = dataProyecto.getMapDatosPorAula();
        //Busco entre todas las aulas aquellas que tengan segmentos impartidos por el profesor
        for (String hashAula : map.keySet()) {
            ArrayList<Integer[]> segConflAula = new ArrayList<Integer[]>();
            DatosPorAula datosPorAula = map.get(hashAula);
            ArrayList<Segmento> ls = datosPorAula.getListaSegmentos().getSegmentos();
            for (int n = 0; n < ls.size(); n++) {
                if (ls.get(n).getProfesor() == this.profesor) {
                    Integer[] dato = {n, ls.get(n).getNumeroDeCasillasQueOcupa()};
                    segConflAula.add(dato);
                }
            }
            if (segConflAula.size() > 0) {
                segmentosConflictivos.put(hashAula, segConflAula);
            }

        }

        //Realizo un segundo bucle buscando ahora las casillas conflictivas
        for (String hashAula : segmentosConflictivos.keySet()) //Las aulas que ya he marcado
        {
            boolean isConflictivo = false;
            DatosPorAula datosPorAula = map.get(hashAula);
            ArrayList<Integer> casConflAula = new ArrayList<Integer>();
            ArrayList<Casilla> lc = datosPorAula.getListaCasillas().getCasillas();
            for (int n = 0; n < lc.size(); n++) {

                if (rangosSolapanCon(lc.get(n))) {
                    casConflAula.add(n);
                }
            }

            casillasConflictivas.put(hashAula, casConflAula);//Añado la listaCasillas aunque esté vacía
//            for (int n : casConflAula) {
//                try {
//                } catch (Exception ex) {
//                    Logger.getLogger(RProfesorCiertosDias.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
        }

    }

    /**
     *
     * @param posibleSolucion
     * @return
     */
    @Override
    public long calculaPeso(PosibleSolucion posibleSolucion) {
        setPeso(0);
        double coef = 1.3;
        long suma = getSuma();

        for (String hashAula : segmentosConflictivos.keySet()) {
            Asignacion asig = posibleSolucion.getAsignacion(hashAula);
            //Busco en esta asignación todos los segmentos conflictivos
            ArrayList<Integer[]> sc = segmentosConflictivos.get(hashAula);
            ArrayList<Integer> cc = casillasConflictivas.get(hashAula);
            for (int index = 0; index < sc.size(); index++) {
                int indiceSegmento = sc.get(index)[0];
                int numCas = sc.get(index)[1];
                if (asig.contains(indiceSegmento)) {//Siempre se contiene no?
                    //Casilla en la que está ubicado el segmento
                    int posSegmento = asig.getAsignaciones().indexOf(indiceSegmento);
                    int cas = asig.getQueCasilla().get(posSegmento);
                    Loop:
                    for (int k = cas; k < cas + numCas; k++) {
                        if ((cc.contains(k) && !puedeEstosDias) || (!cc.contains(k) && puedeEstosDias))//Si la casilla asignada al segmento está en las conflictivas...
                        {
                            setCasillaConflictiva(hashAula, cas, true);
                            this.sumaPeso(suma);//(long) suma);
                            suma *= coef;
                            break;
                        }
                    }

                }
            }

        }

//        setPeso(0);
//        long suma=100;
//        for (String hash:posibleSolucion.getMapAsignaciones().keySet())
//        {
//            Asignacion asig=posibleSolucion.getAsignacion(hash);
//            ListaCasillas lc=getDataProyecto().getDatosPorAula(hash).getListaCasillas();
//            for (int n:asig.getAsignaciones())
//            {
//                int cas=asig.getQueCasilla().get(n);
//                if ((lc.get(cas).getDiaSemana()<4) && !asig.getSegmento(n).isHuecoLibre())
//                {
//                    sumaPeso(suma);
//                    //suma*=1.3;
//                }
//            }
//        }
        return getPeso();
    }

    @Override
    public boolean lanzarDialogoDeConfiguracion(Object parent) {
        DlgProfesorCiertosDias dlg = new DlgProfesorCiertosDias(null, true, this);
        dlg.setLocationRelativeTo(null);
        dlg.setVisible(true);
        boolean resul = false;
        if (dlg.getReturnStatus() == DlgProfesorCiertosDias.RET_OK) {
            resul = true;
        }
        if (dlg.getReturnStatus() == DlgProfesorCiertosDias.RET_CANCEL) {
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
        StringBuilder resul = new StringBuilder("<html>");
        String nombreProfesor;
        if (profesor != null) {
            nombreProfesor = profesor.getApellidos() + ", " + profesor.getNombre();
        } else {
            nombreProfesor = "";
        }
        //resul += "(" + this.getImportancia() + ") ";
        if (puedeEstosDias) {
            resul.append("El profesor <b>").append(nombreProfesor).append("</b> SOLO puede estos días: ");
        } else {
            resul.append("El profesor <b>").append(nombreProfesor).append("</b> NO puede estos días: ");
        }

        boolean first = true;
        for (Integer dia : rangos.keySet()) {

            if (rangos.get(dia).size() > 0) {
                if (!first) {
                    resul.append(", ");
                }

                resul.append("<b>").append(inicialesSemana[dia - 1]).append("</b>: ").append(rangos.get(dia).toString()).append("  ");
            }
            first = false;
        }
        return resul.append("</html>").toString();
    }

    /**
     *
     * @return
     */
    @Override
    public String descripcionCorta() {
        return "Profesor sólo puede determinados días";
    }

    /**
     *
     * @return
     */
    @Override
    public String mensajeDeAyuda() {
        return "<html>Con esta restricción podemos fijar para un profesor<br>"+
                     "los días que no puede (o solo puede)d ar clases,<br>"+
                     "penalizando las asignaturas que no cumplan dicho<br>requisito.</html>";
    }

    /**
     *
     * @return
     */
    public Boolean getPuedeEstosDias() {
        return puedeEstosDias;
    }

    /**
     *
     * @param puedeEstosDias
     */
    public void setPuedeEstosDias(Boolean puedeEstosDias) {
        this.puedeEstosDias = puedeEstosDias;
    }

    void setLunes(String text) {
        rangos.put(1, new ArrayRangoHoras(text));
    }

    void setMartes(String text) {
        rangos.put(2, new ArrayRangoHoras(text));
    }

    void setMiercoles(String text) {
        rangos.put(3, new ArrayRangoHoras(text));
    }

    void setJueves(String text) {
        rangos.put(4, new ArrayRangoHoras(text));
    }

    void setViernes(String text) {
        rangos.put(5, new ArrayRangoHoras(text));
    }

    /**
     *
     * @return
     */
    public HashMap<Integer, ArrayRangoHoras> getRangos() {
        return rangos;
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
     */
    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    /**
     *
     * @return
     */
    public String getObservaciones() {
        return observaciones;
    }

    /**
     *
     * @param observaciones
     */
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    /**
     * Devuelve true si la casilla c solapa con alguno de los rangos horarios de
     * la lista
     *
     * @param c
     * @return
     */
    private boolean rangosSolapanCon(Casilla c) {
        boolean resul = false;
        int dia = c.getDiaSemana();
        ArrayRangoHoras rangosConflictivosDeEseDia = rangos.get(dia);
        try {
            resul = rangosConflictivosDeEseDia.solapaCon(c.getRangoHora());
        } catch (Exception ex) {
            Logger.getLogger(RProfesorCiertosDias.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resul;
    }

    /**
     *
     * @return
     */
    @Override
    public String getMensajeError() {
        return descripcion();
    }

    /**
     *
     * @param parent
     */
    @Override
    public void writeConfig(Node parent) {
        Node nodo;

        //Nombre del profesor, en forma de hash
        nodo = creaNodoSimple(parent, "nombre_profesor");
        creaNodoTexto(nodo, this.getProfesor().hash());

        //Puede/no puede
        nodo = creaNodoSimple(parent, "tipo_restriccion");
        String texto;
        if (this.getPuedeEstosDias()) {
            texto = "puede";
        } else {
            texto = "no puede";
        }
        creaNodoTexto(nodo, texto);

        //Datos para el lunes
        for (int numDia=1;numDia<=5;numDia++) {
            String dia=MyConstants.XML_DAYS_OF_THE_WEEK[numDia-1];
            nodo = creaNodoSimple(parent, dia);
            String rango = this.getRangos().get(numDia).toString();
            creaNodoTexto(nodo, rango);
        }

        nodo = creaNodoSimple(parent, "comments");
        creaNodoTexto(nodo, this.getObservaciones());

    }

    /**
     *
     * @param parent
     */
    @Override
    public void readConfig(Element parent) {
        //Ya he leido tipo de clase e instanciado una nueva. Ahora es el moment de llenarla con datos
        //específicos de esta clase (profesorCiertosDias)

        //Leo datos del profesor por hash
        Element el = buscaPrimerElementoConNombre(parent, "nombre_profesor");
        String hashProfesor = el.getTextContent();
        Profesor p = dataProyecto.getDataProfesores().buscaProfesorPorHash(hashProfesor);
        this.setProfesor(p);

        //Leo tipo de restriccion
        el = buscaPrimerElementoConNombre(parent, "tipo_restriccion");
        this.setPuedeEstosDias((el.getTextContent().equals("puede")));

        //Lee datos del lunes
        el = buscaPrimerElementoConNombre(parent, MyConstants.XML_DAYS_OF_THE_WEEK[0]);
        this.setLunes(el.getTextContent());

        //Lee datos del martes
        el = buscaPrimerElementoConNombre(parent, MyConstants.XML_DAYS_OF_THE_WEEK[1]);
        this.setMartes(el.getTextContent());

        //Lee datos del miércoles
        el = buscaPrimerElementoConNombre(parent, MyConstants.XML_DAYS_OF_THE_WEEK[2]);
        this.setMiercoles(el.getTextContent());

        //Lee datos del jueves
        el = buscaPrimerElementoConNombre(parent, MyConstants.XML_DAYS_OF_THE_WEEK[3]);
        this.setJueves(el.getTextContent());

        //Lee datos del viernes
        el = buscaPrimerElementoConNombre(parent, MyConstants.XML_DAYS_OF_THE_WEEK[4]);
        this.setViernes(el.getTextContent());

        //Por último, leo las observaciones
        el = buscaPrimerElementoConNombre(parent, "comments");
        this.setObservaciones(el.getTextContent());
    }
}

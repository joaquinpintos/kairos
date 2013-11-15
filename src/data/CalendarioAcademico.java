/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Esta clase almacena datos relativos a las fechas del curso académico, calcula
 * número de días, etc.
 *
 * @author david
 */
public class CalendarioAcademico implements Serializable {
    //Array con los días de la semana lectivos 1=lunes,...,5=viernes
    private ArrayList<Integer> diasSemanaLectivos;
    GregorianCalendar inicioPeriodoLectivo, finPeriodoLectivo;
    //Formato por defecto de las fechas dd//MM/yyyy
    SimpleDateFormat formatoFechas;
    //String formateado de fechas no lectivas
    ArrayList<String> diasNoLectivos;
    ArrayList<String> descripcionDiasNoLectivos;
    //Rangos de horas de apertura de las aulas
    private RangoHoras mañana1, mañana2;
    private RangoHoras tarde1, tarde2;

    /**
     *
     */
    public CalendarioAcademico() {
        this.diasSemanaLectivos = new ArrayList<Integer>();
        formatoFechas = new SimpleDateFormat("dd/MM/yyyy");
        this.inicioPeriodoLectivo = new GregorianCalendar();
        this.finPeriodoLectivo = new GregorianCalendar();
        diasNoLectivos = new ArrayList<String>();
        descripcionDiasNoLectivos = new ArrayList<String>();
        mañana1 = new RangoHoras();
        mañana2 = new RangoHoras();
        tarde1 = new RangoHoras();
        tarde2 = new RangoHoras();

    }

    /**
     *
     * @return
     */
    public ArrayList<Integer> getDiasSemanaLectivos() {
        return diasSemanaLectivos;
    }

    /**
     *
     * @param diasSemanaLectivos
     */
    public void setDiasSemanaLectivos(ArrayList<Integer> diasSemanaLectivos) {
        this.diasSemanaLectivos = diasSemanaLectivos;
    }

    //Marca los días de la semana lectivos, true=lectivo, false=no lectivo
    /**
     *
     * @param lunes
     * @param martes
     * @param miércoles
     * @param jueves
     * @param viernes
     */
    public void marcaDiasSemanaLectivos(Boolean lunes, Boolean martes, Boolean miércoles, Boolean jueves, Boolean viernes) {
        //Borro todos los datos anteriores
        this.diasSemanaLectivos = new ArrayList<Integer>();

        if (lunes) {
            diasSemanaLectivos.add(1);
        }
        if (martes) {
            diasSemanaLectivos.add(2);
        }
        if (miércoles) {
            diasSemanaLectivos.add(3);
        }
        if (jueves) {
            diasSemanaLectivos.add(4);
        }
        if (viernes) {
            diasSemanaLectivos.add(5);
        }
    }

    //Normaliza días de la semana. Crea un array con los códigos de los días de la semana de la clase GregorianCalendar.
    //Internamente el programa usa 1...5 para lunes...viernes. Este programa los traduce a las constantes de GregorianCalendar.
    /**
     *
     * @param dias
     * @return
     */
    public ArrayList<Integer> normalizaDiasLectivosSemana(ArrayList<Integer> dias) {
        ArrayList<Integer> resul = new ArrayList<Integer>();
        if (dias.contains(1)) {
            resul.add(GregorianCalendar.MONDAY);
        }
        if (dias.contains(2)) {
            resul.add(GregorianCalendar.TUESDAY);
        }
        if (dias.contains(3)) {
            resul.add(GregorianCalendar.WEDNESDAY);
        }
        if (dias.contains(4)) {
            resul.add(GregorianCalendar.THURSDAY);
        }
        if (dias.contains(5)) {
            resul.add(GregorianCalendar.FRIDAY);
        }

        return resul;

    }

    public ArrayList<GregorianCalendar> getArrayDiasLectivos() {
        {
            return getArrayDiasLectivos((GregorianCalendar) inicioPeriodoLectivo.clone(), (GregorianCalendar) finPeriodoLectivo.clone());
        }
    }

    /**
     *
     * @return
     */
    public ArrayList<GregorianCalendar> getArrayDiasLectivos(GregorianCalendar parInicio, GregorianCalendar parFin) {
        ArrayList<GregorianCalendar> resul = new ArrayList<GregorianCalendar>();
        GregorianCalendar inicio = (GregorianCalendar) parInicio.clone();

        ArrayList<Integer> diasLectivos = normalizaDiasLectivosSemana(diasSemanaLectivos);
        int day;
        String strDia;
        GregorianCalendar fin = (GregorianCalendar) parFin.clone();
        fin.add(GregorianCalendar.DAY_OF_MONTH, 1);
        while (!inicio.equals(fin)) {
            day = inicio.get(GregorianCalendar.DAY_OF_WEEK); //Obtiene día de la semana
            strDia = formatoFechas.format(inicio.getTime());
            if (diasLectivos.contains(day) && (!diasNoLectivos.contains(strDia))) {
                resul.add((GregorianCalendar) inicio.clone());
            }
            inicio.add(GregorianCalendar.DAY_OF_MONTH, 1);//Añado un día al bucle
        }
        return resul;
    }

    /**
     *
     * @return
     */
    public GregorianCalendar getInicio() {
        return inicioPeriodoLectivo;
    }

    /**
     *
     * @return
     */
    public GregorianCalendar getFin() {
        return finPeriodoLectivo;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getDiasNoLectivos() {
        return diasNoLectivos;
    }

    /**
     *
     * @param diasNoLectivos
     */
    public void setDiasNoLectivos(ArrayList<String> diasNoLectivos) {
        this.diasNoLectivos = diasNoLectivos;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getDescripcionDiasNoLectivos() {
        return descripcionDiasNoLectivos;
    }

    /**
     *
     * @param descripcionDiasNoLectivos
     */
    public void setDescripcionDiasNoLectivos(ArrayList<String> descripcionDiasNoLectivos) {
        this.descripcionDiasNoLectivos = descripcionDiasNoLectivos;
    }

    /**
     *
     * @return
     */
    public String getStrInicio() {
        return formatoFechas.format(inicioPeriodoLectivo.getTime());
    }

    /**
     *
     * @return
     */
    public String getStrFin() {
        return formatoFechas.format(finPeriodoLectivo.getTime());
    }

    //Sobrecarga
    /**
     *
     * @param strInicio
     * @throws ParseException
     */
    public void setInicio(String strInicio) throws ParseException {
        this.inicioPeriodoLectivo.setTime(formatoFechas.parse(strInicio));
    }

    public void setInicio(Date inicio) {
        this.inicioPeriodoLectivo.setTime(inicio);
    }

    public void setInicio(GregorianCalendar inicio) {
        this.inicioPeriodoLectivo = inicio;
    }

    /**
     *
     * @param strFin
     * @throws ParseException
     */
    public void setFin(String strFin) throws ParseException {
        this.finPeriodoLectivo.setTime(formatoFechas.parse(strFin));
    }

    public void setFin(Date fin) {
        if (fin != null) {
            this.finPeriodoLectivo.setTime(fin);
        }
    }

    public void setFin(GregorianCalendar fin) {
        this.finPeriodoLectivo = fin;
    }

    /**
     *
     * @return
     */
    public RangoHoras getMañana1() {
        return mañana1;
    }

    /**
     *
     * @param mañana1
     */
    public void setMañana1(RangoHoras mañana1) {
        this.mañana1 = mañana1;
    }

    /**
     *
     * @return
     */
    public RangoHoras getMañana2() {
        return mañana2;
    }

    /**
     *
     * @param mañana2
     */
    public void setMañana2(RangoHoras mañana2) {
        this.mañana2 = mañana2;
    }

    /**
     *
     * @return
     */
    public RangoHoras getTarde1() {
        return tarde1;
    }

    /**
     *
     * @param tarde1
     */
    public void setTarde1(RangoHoras tarde1) {
        this.tarde1 = tarde1;
    }

    /**
     *
     * @return
     */
    public RangoHoras getTarde2() {
        return tarde2;
    }

    /**
     *
     * @param tarde2
     */
    public void setTarde2(RangoHoras tarde2) {
        this.tarde2 = tarde2;
    }

    /**
     *
     * @param documentoXML
     * @param parent
     */
    public void dataToDOM(Document documentoXML, Node parent) {
        //Creo nodo con inicio y fin del periodo lectivo
//         <periodo_lectivo>
//                <inicio>04/02/2013</inicio>
//                <fin>6/3/2013</fin>
//            </periodo_lectivo>
        Element elePeriodoLectivo = documentoXML.createElement("periodo_lectivo");
        Node nodePeriodoLectivo = parent.appendChild(elePeriodoLectivo);
        añadeNodo(nodePeriodoLectivo, "inicio", formatoFechas.format(inicioPeriodoLectivo.getTime()));
        añadeNodo(nodePeriodoLectivo, "fin", formatoFechas.format(finPeriodoLectivo.getTime()));

        //Creo el nodo con el horario de aulas
        Element eleHorarioAulas = documentoXML.createElement("horario_aulas");
        Node nodeHorarioAulas = parent.appendChild(eleHorarioAulas);

        //Nodo horario de mañana
        Element elemHorarioMañana = documentoXML.createElement("horario_mañana");
        Node nodeHorarioMañana = nodeHorarioAulas.appendChild(elemHorarioMañana);
        escribeTramo(nodeHorarioMañana, "tramo_1", mañana1);
        escribeTramo(nodeHorarioMañana, "tramo_2", mañana2);

        //Nodo horario de la tarde
        Element elemHorarioTarde = documentoXML.createElement("horario_tarde");
        Node nodeHorarioTarde = nodeHorarioAulas.appendChild(elemHorarioTarde);
        escribeTramo(nodeHorarioTarde, "tramo_1", tarde1);
        escribeTramo(nodeHorarioTarde, "tramo_2", tarde2);

        //Ahora escribo los días de la semana lectivos 1=lunes, ...5=viernes
        //dias_semana_lectivos
        Element eleDiasSemanaLectivos = documentoXML.createElement("dias_semana_lectivos");
        Node nodeDiasSemanaLectivos = parent.appendChild(eleDiasSemanaLectivos);

        for (int dia : diasSemanaLectivos) {
            añadeNodo(nodeDiasSemanaLectivos, "dia", String.valueOf(dia));
        }

        //Ahora los días no lectivos dentro del periodo, sin contar sábados ni domingos
        //<diasNoLectivos> <dia descripcion="">fecha</dia>
        Node nodeDiasNoLectivos = parent.appendChild(documentoXML.createElement("dias_no_lectivos"));

        for (int n = 0; n < diasNoLectivos.size(); n++) {
            String strDia = diasNoLectivos.get(n);
            String descripcion = descripcionDiasNoLectivos.get(n);
            Element eleDia = documentoXML.createElement("dia");
            eleDia.setAttribute("descripcion", descripcion);
            eleDia.setTextContent(strDia);
            nodeDiasNoLectivos.appendChild(eleDia);
        }

    }

    private void escribeTramo(Node parent, String nombreTramo, RangoHoras rango) {
        Element elem1 = parent.getOwnerDocument().createElement(nombreTramo);
        Element elemInicio = parent.getOwnerDocument().createElement("inicio");
        elem1.appendChild(elemInicio);
        elemInicio.appendChild(parent.getOwnerDocument().createTextNode(rango.getInicio().toString()));
        Element elemFin = parent.getOwnerDocument().createElement("fin");
        elem1.appendChild(elemFin);
        elemFin.appendChild(parent.getOwnerDocument().createTextNode(rango.getFin().toString()));
        parent.appendChild(elem1);
    }

    //Esta función añade un nodo simple de texto con nombreNodo y valo
    private void añadeNodo(Node parent, String nombreNodo, String valor) {
        Element el = parent.getOwnerDocument().createElement(nombreNodo);
        el.appendChild(parent.getOwnerDocument().createTextNode(valor));
        parent.appendChild(el);
    }

    /**
     *
     * @param dnl
     * @param descDnl
     */
    public void setDiasNoLectivos(ArrayList<String> dnl, ArrayList<String> descDnl) {
        diasNoLectivos.clear();
        descripcionDiasNoLectivos.clear();

        for (String d : dnl) {
            diasNoLectivos.add(d);
        }
        for (String d : descDnl) {
            descripcionDiasNoLectivos.add(d);
        }
    }

    //Sobrecarga
    /**
     *
     * @param strDia
     * @param descripcion
     */
    public void addDiaNoLectivo(String strDia, String descripcion) {
        diasNoLectivos.add(strDia);
        descripcionDiasNoLectivos.add(descripcion);
    }

    //Funciones delegados de formato y parseo de fechas
    /**
     *
     * @param date
     * @return
     */
    public final String format(GregorianCalendar date) {
        return formatoFechas.format(date.getTime());
    }

    /**
     *
     * @param source
     * @return
     * @throws ParseException
     */
    public GregorianCalendar parse(String source) throws ParseException {
        GregorianCalendar resul = new GregorianCalendar();
        resul.setTime(formatoFechas.parse(source));
        return resul;
    }

    void clear() {
        this.diasSemanaLectivos.clear();
        this.inicioPeriodoLectivo = new GregorianCalendar();
        this.finPeriodoLectivo = new GregorianCalendar();
        diasNoLectivos.clear();
        descripcionDiasNoLectivos.clear();
    }

    /**
     *
     * @param tarde
     * @return
     */
    public double getTotalHorasLectivasPorSemana(boolean tarde) {
        if (tarde) {
            return diasSemanaLectivos.size() * (tarde1.duracion() + tarde2.duracion());
        } else {
            return diasSemanaLectivos.size() * (mañana1.duracion() + mañana2.duracion());
        }
    }
}

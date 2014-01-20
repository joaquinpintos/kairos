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
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class AcademicCalendar implements Serializable {

    private static final long serialVersionUID = 27112013L;
    //Array with the academic week days 1=monday,...,5=friday
    private ArrayList<Integer> academicWeekDays;
    GregorianCalendar beginAcademicPeriod, endAcademicPeriod;
    //Formato por defecto de las fechas dd//MM/yyyy
    SimpleDateFormat KairosDateFormat;
    //String with the non academic days
    ArrayList<String> nonAcademicDays;
    ArrayList<String> nonAcademicDaysDescription;
    private TimeRange morning1, morning2;
    private TimeRange evening1, evening2;

    /**
     * Constructor por defecto
     */
    public AcademicCalendar() {
        this.academicWeekDays = new ArrayList<Integer>();
        KairosDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.beginAcademicPeriod = new GregorianCalendar();
        this.endAcademicPeriod = new GregorianCalendar();
        nonAcademicDays = new ArrayList<String>();
        nonAcademicDaysDescription = new ArrayList<String>();
        morning1 = new TimeRange();
        morning2 = new TimeRange();
        evening1 = new TimeRange();
        evening2 = new TimeRange();

    }

    /**
     * Devuelve los días de semana lectivos, en forma de arraylist de integers
     *
     * @return ArrayList<Integer> con días lectivos 1=lunes,..., 5=viernes
     */
    public ArrayList<Integer> getAcademicWeekDays() {
        return academicWeekDays;
    }

    /**
     * Cambia los días de semana lectivos.
     *
     * @param academicWeekDays ArrayList<Integer> con días lectivos
     * 1=lunes,..., 5=viernes
     */
    public void setAcademicWeekDays(ArrayList<Integer> academicWeekDays) {
        this.academicWeekDays = academicWeekDays;
    }

    /**
     * Marca los días de la semana como lectivos,
     *
     * @param monday true=lectivo, false=no lectivo
     * @param tuesday true=lectivo, false=no lectivo
     * @param wednesday true=lectivo, false=no lectivo
     * @param thursday true=lectivo, false=no lectivo
     * @param friday true=lectivo, false=no lectivo
     */
    public void marcaDiasSemanaLectivos(Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday) {
        //Borro todos los datos anteriores
        this.academicWeekDays = new ArrayList<Integer>();

        if (monday) {
            academicWeekDays.add(1);
        }
        if (tuesday) {
            academicWeekDays.add(2);
        }
        if (wednesday) {
            academicWeekDays.add(3);
        }
        if (thursday) {
            academicWeekDays.add(4);
        }
        if (friday) {
            academicWeekDays.add(5);
        }
    }

    /**
     * Normaliza días de la semana. Crea un array con los códigos de los días de
     * la semana de la clase GregorianCalendar. Internamente el programa usa
     * 1...5 para lunes...viernes. Este programa los traduce a las constantes de
     * GregorianCalendar.
     *
     * @param days Dias de la semana en formato 1=Lunes,...5=Viernes
     * @return Dias de la semana en constantes de GregorianCalendar
     */
    public ArrayList<Integer> normalizeAcademicWeekDays(ArrayList<Integer> days) {
        ArrayList<Integer> resul = new ArrayList<Integer>();
        if (days.contains(1)) {
            resul.add(GregorianCalendar.MONDAY);
        }
        if (days.contains(2)) {
            resul.add(GregorianCalendar.TUESDAY);
        }
        if (days.contains(3)) {
            resul.add(GregorianCalendar.WEDNESDAY);
        }
        if (days.contains(4)) {
            resul.add(GregorianCalendar.THURSDAY);
        }
        if (days.contains(5)) {
            resul.add(GregorianCalendar.FRIDAY);
        }

        return resul;

    }

    /**
     * Método sobrecargado. Calcula y devuelve un array con todos los días
     * lectivos, desde el inicio al final del curso
     *
     * @return Días lectivos, en objetos {@link GregorianCalendar}
     */
    public ArrayList<GregorianCalendar> computeArrayAcademicDays() {
        {
            return computeArrayAcademicDays((GregorianCalendar) beginAcademicPeriod.clone(), (GregorianCalendar) endAcademicPeriod.clone());
        }
    }

    /**
     * Calcula array de dias lectivos, obviando los festivos, sábados y
     * domingos, desde los días inicial y final dados.
     *
     * @param beginPeriod Dia de inicio
     * @param endPeriod Dia final
     * @return Array con los días lectivos, en objetos {@link GregorianCalendar}
     */
    public ArrayList<GregorianCalendar> computeArrayAcademicDays(GregorianCalendar beginPeriod, GregorianCalendar endPeriod) {
        ArrayList<GregorianCalendar> resul = new ArrayList<GregorianCalendar>();
        GregorianCalendar inicio = (GregorianCalendar) beginPeriod.clone();

        ArrayList<Integer> diasLectivos = normalizeAcademicWeekDays(academicWeekDays);
        int day;
        String strDia;
        GregorianCalendar fin = (GregorianCalendar) endPeriod.clone();
        fin.add(GregorianCalendar.DAY_OF_MONTH, 1);
        while (!inicio.equals(fin)) {
            day = inicio.get(GregorianCalendar.DAY_OF_WEEK); //Get day of the week
            strDia = KairosDateFormat.format(inicio.getTime());
            if (diasLectivos.contains(day) && (!nonAcademicDays.contains(strDia))) {
                resul.add((GregorianCalendar) inicio.clone());
            }
            inicio.add(GregorianCalendar.DAY_OF_MONTH, 1);//Add 1 to the loop
        }
        return resul;
    }

    /**
     *
     * @return Inicio del periodo docente
     */
    public GregorianCalendar getBeginning() {
        return beginAcademicPeriod;
    }

    /**
     *
     * @return Fin del periodo docente
     */
    public GregorianCalendar getEnd() {
        return endAcademicPeriod;
    }

    /**
     *
     * @return Días no lectivos en formato texto
     */
    public ArrayList<String> getNonAcademicDays() {
        return nonAcademicDays;
    }

    /**
     * Cambia los días no lectivos durante todo el periodo académico (sólo
     * fiestas, no es necesario incluir sábados ni domingos)
     *
     * @param diasNoLectivos Días no lectivos en formato texto
     */
    public void setNonAcademicDays(ArrayList<String> diasNoLectivos) {
        this.nonAcademicDays = diasNoLectivos;
    }

    /**
     *
     * @return
     */
    public ArrayList<String> getNonAcademicDaysDescription() {
        return nonAcademicDaysDescription;
    }

    /**
     *
     * @param descripcionDiasNoLectivos
     */
    public void setNonAcademicDaysDescription(ArrayList<String> descripcionDiasNoLectivos) {
        this.nonAcademicDaysDescription = descripcionDiasNoLectivos;
    }

    //Sobrecarga
    /**
     *
     * @param strBeginning
     * @throws ParseException
     */
    public void setBeginning(String strBeginning) throws ParseException {
        this.beginAcademicPeriod.setTime(KairosDateFormat.parse(strBeginning));
    }

    /**
     *
     * @param beginPeriod
     */
    public void setBeginning(Date beginPeriod) {
        this.beginAcademicPeriod.setTime(beginPeriod);
    }

    /**
     *
     * @param beginPeriod
     */
    public void setBeginning(GregorianCalendar beginPeriod) {
        this.beginAcademicPeriod = beginPeriod;
    }

    /**
     *
     * @param strEndPeriod
     * @throws ParseException
     */
    public void setEnd(String strEndPeriod) throws ParseException {
        this.endAcademicPeriod.setTime(KairosDateFormat.parse(strEndPeriod));
    }

    /**
     *
     * @param fin
     */
    public void setEnd(Date fin) {
        if (fin != null) {
            this.endAcademicPeriod.setTime(fin);
        }
    }

    /**
     *
     * @param fin
     */
    public void setEnd(GregorianCalendar fin) {
        this.endAcademicPeriod = fin;
    }

    /**
     *
     * @return
     */
    public TimeRange getMorning1() {
        return morning1;
    }

    /**
     *
     * @param morning1
     */
    public void setMorning1(TimeRange morning1) {
        this.morning1 = morning1;
    }

    /**
     *
     * @return
     */
    public TimeRange getMorning2() {
        return morning2;
    }

    /**
     *
     * @param morning2
     */
    public void setMorning2(TimeRange morning2) {
        this.morning2 = morning2;
    }

    /**
     *
     * @return
     */
    public TimeRange getEvening1() {
        return evening1;
    }

    /**
     *
     * @param evening1
     */
    public void setEvening1(TimeRange evening1) {
        this.evening1 = evening1;
    }

    /**
     *
     * @return
     */
    public TimeRange getEvening2() {
        return evening2;
    }

    /**
     *
     * @param evening2
     */
    public void setEvening2(TimeRange evening2) {
        this.evening2 = evening2;
    }

    /**
     * Convierte el objeto en una representación XML
     *
     * @param documentoXML Documento XML donde añadir los datos
     * @param parent Nodo padre donde añadir los datos
     */
    public void dataToDOM(Document documentoXML, Node parent) {
        //Creo nodo con inicio y fin del periodo lectivo
//         <periodo_lectivo>
//                <inicio>04/02/2013</inicio>
//                <fin>6/3/2013</fin>
//            </periodo_lectivo>
        Element elePeriodoLectivo = documentoXML.createElement("periodo_lectivo");
        Node nodePeriodoLectivo = parent.appendChild(elePeriodoLectivo);
        añadeNodo(nodePeriodoLectivo, "inicio", KairosDateFormat.format(beginAcademicPeriod.getTime()));
        añadeNodo(nodePeriodoLectivo, "fin", KairosDateFormat.format(endAcademicPeriod.getTime()));

        //Creo el nodo con el horario de aulas
        Element eleHorarioAulas = documentoXML.createElement("horario_aulas");
        Node nodeHorarioAulas = parent.appendChild(eleHorarioAulas);

        //Nodo horario de mañana
        Element elemHorarioMañana = documentoXML.createElement("horario_mañana");
        Node nodeHorarioMañana = nodeHorarioAulas.appendChild(elemHorarioMañana);
        escribeTramo(nodeHorarioMañana, "tramo_1", morning1);
        escribeTramo(nodeHorarioMañana, "tramo_2", morning2);

        //Nodo horario de la tarde
        Element elemHorarioTarde = documentoXML.createElement("horario_tarde");
        Node nodeHorarioTarde = nodeHorarioAulas.appendChild(elemHorarioTarde);
        escribeTramo(nodeHorarioTarde, "tramo_1", evening1);
        escribeTramo(nodeHorarioTarde, "tramo_2", evening2);

        //Ahora escribo los días de la semana lectivos 1=lunes, ...5=viernes
        //dias_semana_lectivos
        Element eleDiasSemanaLectivos = documentoXML.createElement("dias_semana_lectivos");
        Node nodeDiasSemanaLectivos = parent.appendChild(eleDiasSemanaLectivos);

        for (int dia : academicWeekDays) {
            añadeNodo(nodeDiasSemanaLectivos, "dia", String.valueOf(dia));
        }

        //Ahora los días no lectivos dentro del periodo, sin contar sábados ni domingos
        //<diasNoLectivos> <dia descripcion="">fecha</dia>
        Node nodeDiasNoLectivos = parent.appendChild(documentoXML.createElement("dias_no_lectivos"));

        for (int n = 0; n < nonAcademicDays.size(); n++) {
            String strDia = nonAcademicDays.get(n);
            String descripcion = nonAcademicDaysDescription.get(n);
            Element eleDia = documentoXML.createElement("dia");
            eleDia.setAttribute("descripcion", descripcion);
            eleDia.setTextContent(strDia);
            nodeDiasNoLectivos.appendChild(eleDia);
        }

    }

    private void escribeTramo(Node parent, String nombreTramo, TimeRange rango) {
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
        nonAcademicDays.clear();
        nonAcademicDaysDescription.clear();

        for (String d : dnl) {
            nonAcademicDays.add(d);
        }
        for (String d : descDnl) {
            nonAcademicDaysDescription.add(d);
        }
    }

    //Sobrecarga
    /**
     *
     * @param strDia
     * @param descripcion
     */
    public void addDiaNoLectivo(String strDia, String descripcion) {
        nonAcademicDays.add(strDia);
        nonAcademicDaysDescription.add(descripcion);
    }

    //Funciones delegados de formato y parseo de fechas
    /**
     *
     * @param date
     * @return
     */
    public final String format(GregorianCalendar date) {
        return KairosDateFormat.format(date.getTime());
    }

    /**
     *
     * @param source
     * @return
     * @throws ParseException
     */
    public GregorianCalendar parse(String source) throws ParseException {
        GregorianCalendar resul = new GregorianCalendar();
        resul.setTime(KairosDateFormat.parse(source));
        return resul;
    }

    void clear() {
        this.academicWeekDays.clear();
        this.beginAcademicPeriod = new GregorianCalendar();
        this.endAcademicPeriod = new GregorianCalendar();
        nonAcademicDays.clear();
        nonAcademicDaysDescription.clear();
    }

    /**
     *
     * @param tarde
     * @return
     */
    public double getTotalHorasLectivasPorSemana(boolean tarde) {
        if (tarde) {
            return academicWeekDays.size() * (evening1.getDuracionHoras() + evening2.getDuracionHoras());
        } else {
            return academicWeekDays.size() * (morning1.getDuracionHoras() + morning2.getDuracionHoras());
        }
    }

    /**
     * Devuelve el nombre del día de la semana en función de las constantes de
     * GregorianCalendar
     *
     * @param dia Día de la semana dado por una constante de la clase
     * {@link GregorianCalendar}
     * @return String con el nombre del día de la semana
     */
    public String nombreDiaSemana(GregorianCalendar dia) {
        String resul = "";
        switch (dia.get(GregorianCalendar.DAY_OF_WEEK)) {
            case GregorianCalendar.MONDAY:
                resul = MyConstants.DAYS_OF_THE_WEEK[0];
                break;
            case GregorianCalendar.TUESDAY:
                resul = MyConstants.DAYS_OF_THE_WEEK[1];
                break;
            case GregorianCalendar.WEDNESDAY:
                resul = MyConstants.DAYS_OF_THE_WEEK[2];
                break;
            case GregorianCalendar.THURSDAY:
                resul = MyConstants.DAYS_OF_THE_WEEK[3];
                break;
            case GregorianCalendar.FRIDAY:
                resul = MyConstants.DAYS_OF_THE_WEEK[4];
                break;
            default:
                break;
        }
        return resul;
    }

}

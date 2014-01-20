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
package loader;

import data.DataProject;
import data.TimeRange;
import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Element;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DOMLoaderDatosProyecto {

    private final File file;
    private final DataProject dataProject;

    /**
     *
     * @param file
     * @param dataProject
     */
    public DOMLoaderDatosProyecto(File file, DataProject dataProject) {
        this.file = file;
        this.dataProject = dataProject;
    }

    /**
     *
     * @param msg
     */
    public void dbg(String msg) {
        System.out.println(msg);
    }

    void parseDatosProyecto(Element parent) {
        Element nodo, nodo2, nodo3, nodo4;

        //Leo datos horario aulas. En esta versión asumo que todas las horas son iguales
        nodo = buscaPrimerElementoConNombre(parent, "horario_aulas");
        dataProject.setMañana1(leeRangoHoras("horario_mañana", "tramo_1", nodo));
        dataProject.setMañana2(leeRangoHoras("horario_mañana", "tramo_2", nodo));
        dataProject.setTarde1(leeRangoHoras("horario_tarde", "tramo_1", nodo));
        dataProject.setTarde2(leeRangoHoras("horario_tarde", "tramo_2", nodo));

        //Leo dias lectivos de la semana
        nodo = buscaPrimerElementoConNombre(parent, "dias_semana_lectivos");
        dataProject.setDiasSemanaLectivos(readDiasLectivosSemana(nodo));

        //Leo fecha inicial y final del periodo lectivo
        nodo = buscaPrimerElementoConNombre(parent, "periodo_lectivo");

        try {
            //Nodo inicio
            nodo2 = buscaPrimerElementoConNombre(nodo, "inicio");
            String te = nodo2.getTextContent().trim();
            dataProject.getAcademicCalendar().setBeginning(te);
            //Nodo fin
            nodo2 = buscaPrimerElementoConNombre(nodo, "fin");
            dataProject.getAcademicCalendar().setEnd(nodo2.getTextContent().trim());
        } catch (ParseException ex) {
            Logger.getLogger(DOMLoaderDatosProyecto.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Ahora lee los datos de los días no lectivos
        nodo = buscaPrimerElementoConNombre(parent, "dias_no_lectivos");
        org.w3c.dom.NodeList nodeList = nodo.getElementsByTagName("dia");
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDia = (Element) nodeList.item(i);
                String descripcion = elemDia.getAttribute("descripcion");
                String strDia = elemDia.getTextContent();
                dataProject.getAcademicCalendar().addDiaNoLectivo(strDia, descripcion);
            }
        }

    }

    private TimeRange leeRangoHoras(String parteDia, String tramo, Element el) {
        Element el2 = buscaPrimerElementoConNombre(el, parteDia);
        Element el3 = buscaPrimerElementoConNombre(el2, tramo);
        Element el4 = buscaPrimerElementoConNombre(el3, "inicio");
        String inicio = el4.getTextContent();
        el4 = buscaPrimerElementoConNombre(el3, "fin");
        String fin = el4.getTextContent();
        return new TimeRange(inicio, fin);
    }

    private ArrayList<Integer> readDiasLectivosSemana(Element parent) {
        final String nombreNodo = "dia";
        ArrayList<Integer> diasSemana = new ArrayList<Integer>();
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombreNodo);
        if (nodeList != null && nodeList.getLength() > 0) {
            for (int i = 0; i < nodeList.getLength(); i++) {
                org.w3c.dom.Element elemDep = (Element) nodeList.item(i);
                Integer numeroDia = Integer.valueOf(elemDep.getTextContent());
                diasSemana.add(numeroDia);
            }
        }
        return diasSemana;
    }

    //Devuelve el primer nodo hijo de parent con nombre especificado.
    private Element buscaPrimerElementoConNombre(Element parent, String nombre) {
        org.w3c.dom.NodeList nodeList = parent.getElementsByTagName(nombre);
        Element resul;
        if (nodeList != null && nodeList.getLength() > 0) {
            resul = (Element) nodeList.item(0);
        } else {
            resul = null;
        }
        return resul;
    }
}

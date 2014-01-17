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
package printers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import data.DataProject;
import data.aulas.AulaMT;
import data.horarios.DatosHojaHorario;
import data.horarios.HorarioItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class PrinterHorariosPorAulas extends AbstractHorariosPrinter {

    private final HashMap<AulaMT, DatosHojaHorario> data;
//    private int numFilasTarde ;
//    private int numFilasMañana;

    /**
     *
     * @param dataProyecto
     * @param fileDst
     * @param variosDocumentos
     */
    public PrinterHorariosPorAulas(DataProject dataProyecto, File fileDst, boolean variosDocumentos) {
        super(dataProyecto, fileDst, variosDocumentos);
        data = new HashMap<AulaMT, DatosHojaHorario>();
        buildData();
    }

    /**
     *
     * @throws DocumentException
     * @throws FileNotFoundException
     */
    public void crearDocumento() throws DocumentException, FileNotFoundException {
        if (isRotated()) {
            setTamañoTablaTurnoSimple(16);
        } else {
            setTamañoTablaTurnoSimple(26);
        }
        _crearDocumento();
    }

    private void buildData() {
        ArrayList<HorarioItem> horarios = getDataProyecto().getHorario().getHorarios();
        for (HorarioItem h : horarios) {
            AulaMT cont = new AulaMT(h.getAula(), h.isEsTarde());
            if (!data.containsKey(cont))//Creo key nueva
            {
                DatosHojaHorario dat = new DatosHojaHorario(getDataProyecto());
                if (h.isEsTarde()) {
                    dat.setTarde(false);//TODO: Poner opción de quitar recreos
//                    numFilasTarde = dat.getRangosHoras().size();
                } else {
                    dat.setMañana(false);
//                    numFilasMañana = dat.getRangosHoras().size();
                }
                data.put(cont, dat);
            }
            data.get(cont).add(h);
        }
        ArrayList<AulaMT> aulasOrdenadas = new ArrayList<AulaMT>();
        aulasOrdenadas.addAll(data.keySet());
        Collections.sort(aulasOrdenadas);

        //Una vez construido el diccionario, los añado a la colección de páginas.
        for (AulaMT cont : aulasOrdenadas) {
//            System.out.println("Adding data to print: " + cont + " " + data.get(cont));
            addPage(cont, cont, data.get(cont), null);
        }
    }

    /**
     *
     * @param doc
     * @param obj
     * @throws DocumentException
     */
    @Override
    public void printCabecera(Document doc, Object obj) throws DocumentException {
        addTitle(doc);
        String texto;
        AulaMT cont = (AulaMT) obj;
        texto = "Horarios para "+cont.getAula().getNombre() + " " + (cont.getEsTarde() ? "tarde" : "mañana");
        Paragraph par = new Paragraph(texto);
        par.setAlignment(Paragraph.ALIGN_CENTER);
        doc.add(par);
    }

    /**
     *
     * @param doc
     * @param get
     * @throws DocumentException
     */
    @Override
    public void printPieDePagina(Document doc, Object get) throws DocumentException {
    }

    /**
     *
     * @param h
     * @return
     */
    @Override
    protected Paragraph getParagraphForAsignatura(HorarioItem h) {
        Font font = new Font(Font.FontFamily.HELVETICA, 10);
        return new Paragraph(h.getAsignatura().getNombre() + "\nG." + h.getGrupo().getNombre() + "\n" + h.getProfesor(), font);
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public String getNombreDocumento(Object obj) {
        AulaMT a = (AulaMT) obj;
        return "Horarios_" + a.toString().replace(" ", "_");
    }
}

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
import data.horarios.DatosHojaHorario;
import data.horarios.HorarioItem;
import data.profesores.Profesor;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * @author pdi
 */
public class PrinterHorariosPorProfesor extends AbstractHorariosPrinter {

    private HashMap<Profesor, DatosHojaHorario> data;

    /**
     *
     * @param dataProyecto
     * @param fileDst
     * @param variosDocumentos
     * @throws DocumentException
     * @throws FileNotFoundException
     */
    public PrinterHorariosPorProfesor(DataProject dataProyecto, File fileDst, boolean variosDocumentos) throws DocumentException, FileNotFoundException {
        super(dataProyecto, fileDst, variosDocumentos);
        buildData();
    }

    /**
     *
     * @throws DocumentException
     * @throws FileNotFoundException
     */
    public void crearDocumento() throws DocumentException, FileNotFoundException {
        if (isRotated()) {
            setTamañoTablaTurnoDoble(15F);
        } else {
            setTamañoTablaTurnoDoble(20);
        }
        _crearDocumento();
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
        Profesor p = (Profesor) obj;
        final Paragraph par = new Paragraph("Horarios para el profesor " + p.getNombre()+" "+p.getApellidos());
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
        Font font = new Font(Font.FontFamily.HELVETICA, 8);
        return new Paragraph(h.getAsignatura().getNombre() + " (G." + h.getGrupo().getNombre() + ")\n" + h.getAula(), font);
    }

    private void buildData() {
        data = new HashMap<Profesor, DatosHojaHorario>();
        ArrayList<HorarioItem> horarios = getDataProyecto().getHorario().getHorarios();
        for (HorarioItem h : horarios) {
            Profesor p = h.getProfesor();
            if (p != null) {
                if (!data.containsKey(p))//Creo una clave nueva
                {
                    DatosHojaHorario d = new DatosHojaHorario(getDataProyecto());
                    d.setMañanaYTarde(false);//TODO: Incluir recreos opcionalmente
                    data.put(p, d);
                }
                data.get(p).add(h);
            }
        }
        //Tengo que rellenar las hojas de horarios con casillas libres!!
        ArrayList<Profesor> profesOrdenados = new ArrayList<Profesor>();
        profesOrdenados.addAll(data.keySet());
        Collections.sort(profesOrdenados);
        for (Profesor p : profesOrdenados) {
            if ((!p.getNombre().equals("")) && (!p.getApellidos().equals(""))) {
                data.get(p).rellenoConHorasLibres();
                addPage(p, p, data.get(p), null);
            }
        }
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public String getNombreDocumento(Object obj) {
        Profesor p = (Profesor) obj;
        String resul = "Horarios_";
        String nombreProfesor = p.getApellidos() + "_" + p.getNombre();
        resul += nombreProfesor.replace(" ", "_");
        return resul;
    }
}

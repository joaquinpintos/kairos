/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import data.DataProyecto;
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
public class PrinterHorariosPorProfesor extends AbstractPrinter {

    private HashMap<Profesor, DatosHojaHorario> data;

    /**
     *
     * @param dataProyecto
     * @param fileDst
     * @param variosDocumentos
     * @throws DocumentException
     * @throws FileNotFoundException
     */
    public PrinterHorariosPorProfesor(DataProyecto dataProyecto, File fileDst, boolean variosDocumentos) throws DocumentException, FileNotFoundException {
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
        final Paragraph par = new Paragraph("Horarios para el profesor " + p);
        doc.add(par);
        par.setAlignment(Paragraph.ALIGN_CENTER);
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
                    d.setMañanaYTarde(false);//TODO: Incluir recreos
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

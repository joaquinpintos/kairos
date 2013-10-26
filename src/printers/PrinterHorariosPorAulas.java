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
public class PrinterHorariosPorAulas extends AbstractPrinter {

    private HashMap<AulaMT, DatosHojaHorario> data;
    private int numFilasTarde = 1;
    private int numFilasMañana = 1;

    /**
     *
     * @param dataProyecto
     * @param fileDst
     * @param variosDocumentos
     */
    public PrinterHorariosPorAulas(DataProyecto dataProyecto, File fileDst, boolean variosDocumentos) {
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
                    numFilasTarde = dat.getRangosHoras().size();
                } else {
                    dat.setMañana(false);
                    numFilasMañana = dat.getRangosHoras().size();
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

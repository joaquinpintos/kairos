/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.draw.LineSeparator;
import data.DataProyecto;
import data.asignaturas.Grupo;
import data.aulas.CarreraCursoGrupoContainer;
import data.horarios.DatosHojaHorario;
import data.horarios.HorarioItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class PrinterHorarioPorGrupos extends AbstractHorariosPrinter {

    /**
     *
     * @param dataProyecto
     * @param fileDst
     * @param variosDocumentos
     */
    public PrinterHorarioPorGrupos(DataProyecto dataProyecto, File fileDst, boolean variosDocumentos) {
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
            setTamañoTablaTurnoSimple(16);
        } else {
            setTamañoTablaTurnoSimple(26);
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
        CarreraCursoGrupoContainer cont = (CarreraCursoGrupoContainer) obj;
        addTitle(doc);
        doc.add(new Paragraph());

        Font fontSubtitle = new Font(Font.FontFamily.HELVETICA, 16);
        String texto = "Horarios para %s";
        texto = texto.replace("%s", cont.getNombre());
        Paragraph par = new Paragraph(texto, fontSubtitle);
        par.setAlignment(Paragraph.ALIGN_CENTER);
        doc.add(par);
//        doc.add(new LineSeparator());
        doc.add(new Paragraph());
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

    private void buildData() {
        HashMap<CarreraCursoGrupoContainer, DatosHojaHorario> data = new HashMap<CarreraCursoGrupoContainer, DatosHojaHorario>();
        for (HorarioItem h : getDataProyecto().getHorario().getHorarios()) {
            Grupo gr = h.getGrupo();
                CarreraCursoGrupoContainer cont = new CarreraCursoGrupoContainer(gr);
                if (!data.containsKey(cont)) {
                    DatosHojaHorario dat = new DatosHojaHorario(getDataProyecto());
                    if (h.isEsTarde()) {
                        dat.setTarde(false);
                    } else {
                        dat.setMañana(false);
                    }
                    data.put(cont, dat);
                }
                data.get(cont).add(h);
        } //End of for (horarioitem h:...
        //Relleno las hojas con huecos libres
        for (CarreraCursoGrupoContainer cont : data.keySet()) {
            data.get(cont).rellenoConHorasLibres();
        }

        ArrayList<CarreraCursoGrupoContainer> gruposOrdenados = new ArrayList<CarreraCursoGrupoContainer>();
        gruposOrdenados.addAll(data.keySet());
        Collections.sort(gruposOrdenados);

        for (CarreraCursoGrupoContainer cont : gruposOrdenados) {
            addPage(cont, cont, data.get(cont), null);
        }
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public String getNombreDocumento(Object obj) {
        CarreraCursoGrupoContainer cont = (CarreraCursoGrupoContainer) obj;
        String resul = "Horarios_";
        String nom = cont.getHash();
        nom = nom.replace("@", "_");
        nom = nom.replace("º", "");
        nom = nom.replace(" ", "_");
        resul += nom;
        return resul;
    }
}

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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import data.AcademicCalendar;
import data.DataProject;
import data.KairosTime;
import data.aulas.Aula;
import data.horarios.HorarioItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author usuario
 */
public class HojaDeFirmaPrinter {

    ArrayList<Aula> aulas;
    GregorianCalendar inicio;
    GregorianCalendar fin;
    private final DataProject dp;
    private final File fileDst;

    /**
     *
     * @param dataProject
     * @param fileDst
     * @param aulas
     * @param inicio
     * @param fin
     */
    public HojaDeFirmaPrinter(DataProject dataProject, File fileDst, ArrayList<Aula> aulas, GregorianCalendar inicio, GregorianCalendar fin) {
        this.aulas = aulas;
        this.inicio = inicio;
        this.fin = fin;
        this.dp = dataProject;
        this.fileDst = fileDst;
    }

    /**
     *
     */
    public void imprime() {
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(fileDst));
        } catch (DocumentException ex) {
            Logger.getLogger(HojaDeFirmaPrinter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HojaDeFirmaPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<GregorianCalendar> dias = dp.getAcademicCalendar().computeArrayAcademicDays(inicio, fin);
        if (dias.size() > 0) {
            doc.open();
            for (Aula aula : aulas) {
                for (GregorianCalendar dia : dias) {
                    creaHojaDeFirma(doc, aula, dia);
                    doc.newPage();
                }
            }
            doc.close();
        } else {
            JOptionPane.showMessageDialog(null, "<html>No se ha obtenido ningún día válido. Revisa los días no lectivos<br>"
                    + "y el inicio y fin del periodo académico</html>", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void creaHojaDeFirma(Document doc, Aula aula, GregorianCalendar dia) {
        ArrayList<HorarioItem> data = calculaClasesEsteDia(aula, dia);
        try {
            if (!data.isEmpty()) {
                creaCabecera(doc, aula, dia);
                creaCuerpo(doc, dia, data);
            }
        } catch (DocumentException ex) {
            Logger.getLogger(HojaDeFirmaPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void creaCabecera(Document doc, Aula aula, GregorianCalendar dia) throws DocumentException {
        AcademicCalendar cal = dp.getAcademicCalendar();
        Font font = new Font(Font.FontFamily.HELVETICA, 16);
        Font fontbold = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
        String diaSemana = cal.nombreDiaSemana(dia);
        Paragraph par = new Paragraph();
        par.add(new Phrase("Hoja de firma para aula ", font));
        par.add(new Phrase(aula.getNombre(), fontbold));
        par.add(new Phrase(" correspondiente al día ", font));
        par.add(new Phrase(diaSemana + " " + cal.format(dia), fontbold));

        par.setAlignment(Paragraph.ALIGN_CENTER);
        doc.add(par);
        par.setAlignment(Paragraph.ALIGN_CENTER);
    }

    private void creaCuerpo(Document doc, GregorianCalendar dia, ArrayList<HorarioItem> data) {

        Collections.sort(data, new ComparatorHorarioItems());

        PdfPTable t = new PdfPTable(3);
        t.setSpacingBefore(15f);
        t.setSpacingAfter(10f);
        t.setWidthPercentage(100);
        Font fontbold = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        PdfPCell c = new PdfPCell(new Paragraph("Asignatura", fontbold));
        c.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c.setPadding(5);
        t.addCell(c);
        c = new PdfPCell(new Paragraph("Firma", fontbold));
        c.setPadding(5);
        c.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c.setPadding(5);
        t.addCell(c);
        c = new PdfPCell(new Paragraph("Incidencias", fontbold));
        c.setPadding(5);
        c.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        t.addCell(c);
        for (HorarioItem h : data) {
            creaFilaFirma(t, h);
        }
        try {
            doc.add(t);
        } catch (DocumentException ex) {
            Logger.getLogger(HojaDeFirmaPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     *
     * @param aula
     * @param dia
     * @return
     */
    protected ArrayList<HorarioItem> calculaClasesEsteDia(Aula aula, GregorianCalendar dia) {
        ArrayList<Integer> diasDeLaSemana = new ArrayList<Integer>();
        diasDeLaSemana.add(GregorianCalendar.MONDAY);
        diasDeLaSemana.add(GregorianCalendar.TUESDAY);
        diasDeLaSemana.add(GregorianCalendar.WEDNESDAY);
        diasDeLaSemana.add(GregorianCalendar.THURSDAY);
        diasDeLaSemana.add(GregorianCalendar.FRIDAY);
        int aux = dia.get(GregorianCalendar.DAY_OF_WEEK);
        //Lunes->1, Martes-2,...,Viernes->5
        int diaSemana = diasDeLaSemana.indexOf(aux) + 1;
        ArrayList<HorarioItem> data = new ArrayList<HorarioItem>();
        ArrayList<HorarioItem> horarios = dp.getHorario().getHorarios();
        for (HorarioItem h : horarios) {
            if ((diaSemana == h.getDiaSemana()) && (h.getAula().equals(aula)) && (!h.isHuecoLibre())) {
                data.add(h);
            }
        }
        return data;
    }

    /**
     *
     * @param t
     * @param h
     */
    protected void creaFilaFirma(PdfPTable t, HorarioItem h) {
        Font font = new Font(Font.FontFamily.HELVETICA, 12);
        Font fontProfe = new Font(Font.FontFamily.HELVETICA, 10);
        String name = h.getRangoHoras() + "\n" + h.getAsignatura().getNombre();
        name += "\n" + h.getGrupo().getNombreGrupoCursoYCarrera();
        Paragraph p = new Paragraph(name, font);
        PdfPCell c = new PdfPCell(p);
        c.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        c.setPadding(5);
        c.setFixedHeight(60);
        t.addCell(c);
        String nombreProfesor = h.getProfesor().getNombre() + " " + h.getProfesor().getApellidos();
        p = new Paragraph(nombreProfesor + ":", fontProfe);
        c = new PdfPCell(p);
        c.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c.setPadding(5);
        t.addCell(c);
        t.addCell("");
    }

}

class ComparatorHorarioItems implements Comparator<HorarioItem> {

    @Override
    public int compare(HorarioItem o1, HorarioItem o2) {
        KairosTime h1 = o1.getRangoHoras().getInicio();
        KairosTime h2 = o2.getRangoHoras().getInicio();
        int resul = 0;
        if (h1.menorEstrictoQue(h2)) {
            resul = -1;
        }
        if (h2.menorEstrictoQue(h1)) {
            resul = 1;
        }
        return resul;
    }

}

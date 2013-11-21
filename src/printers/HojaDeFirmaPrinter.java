/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import data.CalendarioAcademico;
import data.DataProyecto;
import data.Hora;
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

/**
 *
 * @author usuario
 */
public class HojaDeFirmaPrinter {

    Aula aula;
    GregorianCalendar inicio;
    GregorianCalendar fin;
    private final DataProyecto dp;
    private final File fileDst;

    public HojaDeFirmaPrinter(DataProyecto dataProyecto, File fileDst, Aula aula, GregorianCalendar inicio, GregorianCalendar fin) {
        this.aula = aula;
        this.inicio = inicio;
        this.fin = fin;
        this.dp = dataProyecto;
        this.fileDst = fileDst;
    }

    public void imprime() {
        Document doc = new Document();
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(fileDst));
        } catch (DocumentException ex) {
            Logger.getLogger(HojaDeFirmaPrinter.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HojaDeFirmaPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
        doc.open();
        ArrayList<GregorianCalendar> dias = dp.getCalendarioAcadémico().getArrayDiasLectivos(inicio, fin);
        for (GregorianCalendar dia : dias) {
            creaHojaDeFirma(doc, dia);
            doc.newPage();
        }
        doc.close();
    }

    private void creaHojaDeFirma(Document doc, GregorianCalendar dia) {
        try {
            creaCabecera(doc, dia);
            creaCuerpo(doc, dia);
        } catch (DocumentException ex) {
            Logger.getLogger(HojaDeFirmaPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void creaCabecera(Document doc, GregorianCalendar dia) throws DocumentException {
        CalendarioAcademico cal = dp.getCalendarioAcadémico();
        Font font = new Font(Font.FontFamily.HELVETICA, 16);
        Paragraph par = new Paragraph("Hoja de firma para aula " + aula.getNombre() + " correspondiente al día " + cal.format(dia),font);

        par.setAlignment(Paragraph.ALIGN_CENTER);
        boolean add = doc.add(par);
        par.setAlignment(Paragraph.ALIGN_CENTER);
    }

    private void creaCuerpo(Document doc, GregorianCalendar dia) {
        //TODO: Falta comprobar que h sea del dia en cuestion

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

        Collections.sort(data, new ComparatorHorarioItems());

        PdfPTable t = new PdfPTable(3);
        t.setSpacingBefore(15f);
        t.setSpacingAfter(10f);
        t.setWidthPercentage(100);
        Font font = new Font(Font.FontFamily.HELVETICA, 14);
        PdfPCell c = new PdfPCell(new Paragraph("Asignatura", font));
        c.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c.setPadding(5);
        t.addCell(c);
        c = new PdfPCell(new Paragraph("Firma", font));
        c.setPadding(5);
        c.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c.setPadding(5);
        t.addCell(c);
        c = new PdfPCell(new Paragraph("Incidencias", font));
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

    protected void creaFilaFirma(PdfPTable t, HorarioItem h) {
        Paragraph p = new Paragraph(h.getRangoHoras() + "\n" + h.getAsignatura().getNombre());
        PdfPCell c = new PdfPCell(p);
        c.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        c.setPadding(5);
        c.setFixedHeight(60);
        t.addCell(c);
        p = new Paragraph(h.getProfesor().toString() + ":");
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
        Hora h1 = o1.getRangoHoras().getInicio();
        Hora h2 = o2.getRangoHoras().getInicio();
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

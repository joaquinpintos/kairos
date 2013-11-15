/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package printers;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
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
            creaCuerpo(dia);
        } catch (DocumentException ex) {
            Logger.getLogger(HojaDeFirmaPrinter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void creaCabecera(Document doc, GregorianCalendar dia) throws DocumentException {
        CalendarioAcademico cal = dp.getCalendarioAcadémico();
        Paragraph par = new Paragraph("Hoja de firma para aula " + aula.getNombre() + " correspondiente al día " + cal.format(dia));
        boolean add = doc.add(par);
        par.setAlignment(Paragraph.ALIGN_CENTER);
    }

    private void creaCuerpo(GregorianCalendar dia) {
        //TODO: Falta comprobar que h sea del dia en cuestion
        int diaSemana=dia.get(GregorianCalendar.DAY_OF_WEEK);
        ArrayList<HorarioItem> data = new ArrayList<HorarioItem>();
        ArrayList<HorarioItem> horarios = dp.getHorario().getHorarios();
        for (HorarioItem h : horarios) {
            if (h.getAula().equals(aula)) {
                data.add(h);
            }
        }
        Collections.sort(data,new ComparatorHorarioItems());
        
        for (HorarioItem h:data)
        {
            
        }
        
        
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

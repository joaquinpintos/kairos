/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package testers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.math.BigInteger;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class ITextTester {

    /**
     *
     */
    public void HelloWorld() {
        /* chapter02/HelloWorld.java */
        Document document = new Document();
        try {
            PdfWriter.getInstance(document,
                    new FileOutputStream("HelloWorld.pdf"));
            document.open();
            document.add(
                    new Paragraph("Hello World"));
            PdfPTable tabla = new PdfPTable(6);
            Paragraph tituloTabla=new Paragraph("Horario del grupo chumpi chumpi");
            //tituloTabla.setAlignment(Paragraph.ALIGN_CENTER);
            PdfPCell celda = new PdfPCell(tituloTabla);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            celda.setBackgroundColor(BaseColor.PINK);
            celda.setColspan(6);
            tabla.addCell(celda);
            
            tabla.addCell("Horas");
            tabla.addCell("Lunes");
            tabla.addCell("Martes");
            tabla.addCell("Miércoles");
            tabla.addCell("Jueves");
            tabla.addCell("Viernes");
            PdfPCell izquierda=new PdfPCell();
            izquierda.setRowspan(5);
            tabla.addCell(izquierda);
            for (int i=0;i<6*5;i++)
            {
                PdfPCell celdaContador=new PdfPCell(new Paragraph(i+"\n"+i));
                if ((i==3)||(i==8)) celdaContador.setRowspan(3);
                tabla.addCell(celdaContador);
            }
            document.add(tabla);








        } catch (Exception e) {
// handle exception
        }

        document.close();
    }
    /**
     *
     */
    public void mcdTester()
    {
        System.out.println("--->"+mcd(60,60));
        Integer[] a={60,60,60,60,60,90,60,120,90};
        int gcd=a[0];
        for (int n=1;n<a.length;n++)
        {
            gcd=mcd(gcd,a[n]);
            System.out.println(gcd);
        }
    }
    
    /**
     *
     * @param a
     * @param b
     * @return
     */
    public int mcd(int a,int b)
    {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }
}

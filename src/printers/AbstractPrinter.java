/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package printers;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import data.DataProyecto;
import data.MyConstants;
import data.RangoHoras;
import data.horarios.DatosHojaHorario;
import data.horarios.HorarioItem;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
abstract public class AbstractPrinter {

    private File fileDst;
    private boolean variosDocumentos;
    private final DataProyecto dataProyecto;
    private ArrayList<Object> cabeceras;
    private ArrayList<DatosHojaHorario> hojasHorarios;
    private ArrayList<Object> piesDePagina;
    private ArrayList<Object> parNombreDocumento;
    private float alturaCeldas;
    /**
     *
     */
    protected final float CM_TO_POINT = 28.3464567F;
    private boolean rotated;
    private float alturaRecreos;
    private float alturaSepMañanaTarde;
    private String textoTitulo = "";

    /**
     *
     * @param dataProyecto
     * @param fileDst
     * @param variosDocumentos
     */
    public AbstractPrinter(DataProyecto dataProyecto, File fileDst, boolean variosDocumentos) {
        this.alturaRecreos = 10;
        this.alturaSepMañanaTarde = 25;
        this.rotated = false;
        this.fileDst = fileDst;
        this.dataProyecto = dataProyecto;
        this.variosDocumentos = variosDocumentos;
        cabeceras = new ArrayList<Object>();
        hojasHorarios = new ArrayList<DatosHojaHorario>();
        piesDePagina = new ArrayList<Object>();
        parNombreDocumento = new ArrayList<Object>();
        alturaCeldas = 25f;
    }

    /**
     *
     * @param doc
     * @throws DocumentException
     */
    public void addTitle(Document doc) throws DocumentException {
        Font fontTitle = new Font(Font.FontFamily.HELVETICA, 24);
        Paragraph par = new Paragraph(getTextoTitulo(), fontTitle);
        par.setAlignment(Paragraph.ALIGN_CENTER);
        doc.add(par);
    }

    /**
     *
     * @return
     */
    public float getAlturaCeldas() {
        return alturaCeldas;
    }

    /**
     *
     * @param alturaCeldas
     */
    public void setAlturaCeldas(float alturaCeldas) {
        this.alturaCeldas = alturaCeldas;
    }

    /**
     * Calcula el tamaño de las celdas para que la tabla tenga la medida dada.
     * Se calcula el tamaño sobre el máximo del número de filas de la mañana y
     * tarde
     *
     * @param tamañoTabla Tamaño deseado de la tabla en centímetros
     */
    public void setTamañoTablaTurnoSimple(float tamañoTabla) {
        int numFilas1 = dataProyecto.getMañana1().duracion();
        numFilas1 += dataProyecto.getMañana1().duracion();
        int numFilas2 = dataProyecto.getTarde1().duracion();
        numFilas2 += dataProyecto.getTarde2().duracion();
        int maxNumFilas = (Math.max(numFilas1, numFilas2) * 60) / dataProyecto.getMinutosPorCasilla();
        setAlturaCeldas((tamañoTabla * CM_TO_POINT - alturaRecreos) / (maxNumFilas + 1));
    }

    /**
     *
     * @param tamañoTabla
     */
    public void setTamañoTablaTurnoDoble(float tamañoTabla) {
        int maxNumFilas = dataProyecto.getMañana1().duracion();
        maxNumFilas += dataProyecto.getMañana2().duracion();
        maxNumFilas += dataProyecto.getTarde1().duracion();
        maxNumFilas += dataProyecto.getTarde2().duracion();
        maxNumFilas = (maxNumFilas * 60) / dataProyecto.getMinutosPorCasilla();
        setAlturaCeldas((tamañoTabla * CM_TO_POINT - alturaRecreos - alturaSepMañanaTarde) / (maxNumFilas + 1));
    }

    /**
     *
     * @throws DocumentException
     * @throws FileNotFoundException
     */
    protected void _crearDocumento() throws DocumentException, FileNotFoundException {
        //        Document doc = new Document(PageSize.A4.rotate());

        if (variosDocumentos) {
            creaVariosDocumentos();
        } else {
            creaUnSoloDocumento();
        }
    }

    private void creaUnSoloDocumento() throws DocumentException, FileNotFoundException {
        Rectangle hoja;
        if (rotated) {
            hoja = PageSize.A4.rotate();
        } else {
            hoja = PageSize.A4;
        }
        Document doc = new Document(hoja);
        PdfWriter.getInstance(doc, new FileOutputStream(fileDst));
        doc.open();
        int size = hojasHorarios.size();
        for (int n = 0; n < size; n++) {
            printCabecera(doc, cabeceras.get(n));
            printHojaHorario(doc, hojasHorarios.get(n));
            printPieDePagina(doc, piesDePagina.get(n));
            doc.newPage();
        }
        doc.close();
    }

    private void creaVariosDocumentos() throws DocumentException, FileNotFoundException {
        Rectangle hoja;
        if (rotated) {
            hoja = PageSize.A4.rotate();
        } else {
            hoja = PageSize.A4;
        }

        int size = hojasHorarios.size();
        for (int n = 0; n < size; n++) {
            String nombreArchivo = getNombreDocumento(parNombreDocumento.get(n)) + ".pdf";
//            System.out.println("Creo archivo " + nombreArchivo);
            Document doc = new Document(hoja);
            File fichero = new File(fileDst, nombreArchivo);//Aquí tengo que unir el directorio y el nombre del fichero
            PdfWriter.getInstance(doc, new FileOutputStream(fichero));
            doc.open();
            printCabecera(doc, cabeceras.get(n));
            printHojaHorario(doc, hojasHorarios.get(n));
            printPieDePagina(doc, piesDePagina.get(n));
            doc.close();
        }

    }

    /**
     *
     * @return
     */
    public File getFileDst() {
        return fileDst;
    }

    /**
     *
     * @param fileDst
     */
    public void setFileDst(File fileDst) {
        this.fileDst = fileDst;
    }

    /**
     *
     * @return
     */
    public boolean isVariosDocumentos() {
        return variosDocumentos;
    }

    /**
     *
     * @param variosDocumentos
     */
    public void setVariosDocumentos(boolean variosDocumentos) {
        this.variosDocumentos = variosDocumentos;
    }

    /**
     *
     * @return
     */
    public DataProyecto getDataProyecto() {
        return dataProyecto;
    }

    /**
     *
     * @param nombreDocumento
     * @param cabecera
     * @param h
     * @param pieDePagina
     */
    public void addPage(Object nombreDocumento, Object cabecera, DatosHojaHorario h, Object pieDePagina) {
        parNombreDocumento.add(nombreDocumento);
        cabeceras.add(cabecera);
        hojasHorarios.add(h);
        piesDePagina.add(pieDePagina);
    }

    /**
     *
     * @param doc
     * @param obj
     * @throws DocumentException
     */
    abstract public void printCabecera(Document doc, Object obj) throws DocumentException;

    /**
     *
     * @param doc
     * @param obj
     * @throws DocumentException
     */
    abstract public void printPieDePagina(Document doc, Object obj) throws DocumentException;

    //Este método necesita ser implementado para calcular el nombre del archivo a crear.
    //Sólo en el caso de múltiples documentos
    /**
     *
     * @param obj
     * @return
     */
    abstract public String getNombreDocumento(Object obj);

    private PdfPCell createCeldaHoras(String texto) {
        Font font = new Font(Font.FontFamily.HELVETICA, 10);

        Paragraph p = new Paragraph(texto, font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        PdfPCell c = new PdfPCell(p);
        c.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
        c.setGrayFill(.9f);
        c.setNoWrap(true);
        c.setFixedHeight(alturaCeldas);
        return c;
    }

    private PdfPCell createCeldaDiasSemana(String texto) {
        Font font = new Font(Font.FontFamily.HELVETICA, 14);
        Paragraph p = new Paragraph(texto, font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        PdfPCell c = new PdfPCell(p);
        c.setGrayFill(.9f);
        c.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
        c.setVerticalAlignment(PdfPCell.ALIGN_CENTER);
        return c;
    }

    /**
     * Esta función genera toda la tabla de la hoja del horario especificada,
     * añadiéndola al documento de itext.
     *
     * @param doc Documento de itext al que añadir.
     * @param data Objeto DatosHojaHorario con los horarios a imprimir.
     * @throws DocumentException
     */
    public void printHojaHorario(Document doc, DatosHojaHorario data) throws DocumentException {
        PdfPTable t = new PdfPTable(6);
        t.setSpacingBefore(15f);
        t.setSpacingAfter(10f);
        t.setWidthPercentage(100);
        //Creo la primera fila
        PdfPCell c;
        Paragraph par = null;
        t.addCell("");
        for (int n = 0; n < 5; n++) {
            c = createCeldaDiasSemana(MyConstants.DIAS_SEMANA[n]);
            c.setUseVariableBorders(true);
            t.addCell(c);
        }
        //Ahora relleno el resto de las filas. Primer columna (0) el rango de horas.
        ArrayList<RangoHoras> horas = data.getRangosHoras();
        for (int numFila = 0; numFila < horas.size(); numFila++) {
            creaFilaRecreo(horas, numFila, t);
            creaSeparadorMañanaTarde(horas, numFila, t);

            c = createCeldaHoras(horas.get(numFila).toString());
            c.setUseVariableBorders(true);

            t.addCell(c);

            for (int numColumm = 1; numColumm <= 5; numColumm++) {
                HorarioItem h = data.retrieveData(numColumm, horas.get(numFila));
                if (h != null) {
                    c = createCeldaHorarioItem(h, numFila, numColumm, 6, horas.size());
                    t.addCell(c);
                }

            }
        }
        doc.add(t);
    }

    private PdfPCell createCeldaHorarioItem(HorarioItem h, int fila, int columna, int numColumnas, int numFilas) {
        Paragraph p;
        PdfPCell c;
        if (!h.isHuecoLibre()) {

            p = getParagraphForAsignatura(h);
            c = new PdfPCell(p);
            c.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
            c.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
            Color col = h.getAsignatura().getColorEnTablaDeHorarios();
            c.setBackgroundColor(new BaseColor(col.getRed(), col.getGreen(), col.getBlue()));
            c.setPadding(5);
            c.setBorderColor(BaseColor.BLACK);
            c.setBorderWidth(1);
            c.setUseBorderPadding(false);
            c.setUseVariableBorders(true);

        } else {
            p = new Paragraph("");//"C"+h.getNumcasilla()+" D"+h.getDiaSemana()+" "+h.getRangoHoras().getInicio());
            c = new PdfPCell(p);
            c.setUseVariableBorders(true);
            c.setBorderColor(BaseColor.LIGHT_GRAY);
            c.setBorderWidth(.3F);
//            c.setBorder(Rectangle.NO_BORDER);
            if (columna == numColumnas - 1) {
                c.setBorderColorRight(BaseColor.BLACK);
            }
//            if (fila == numFilas - 1) {
//                c.setBorder(Rectangle.BOTTOM);
//            }
//            if ((columna == numColumnas - 1) && (fila == numFilas - 1)) {
//                c.setBorder(Rectangle.BOTTOM | Rectangle.RIGHT);
//            }
        }

        if (h.getNumeroDeCasillasQueOcupa() > 1) {
            c.setRowspan(h.getNumeroDeCasillasQueOcupa());

        }

        return c;
    }

    /**
     *
     * @return
     */
    public boolean isRotated() {
        return rotated;
    }

    /**
     *
     * @param rotated
     */
    public void setRotated(boolean rotated) {
        this.rotated = rotated;
    }

    /**
     *
     * @param horas
     * @param numFila
     * @param t
     */
    public void creaFilaRecreo(ArrayList<RangoHoras> horas, int numFila, PdfPTable t) {
        PdfPCell c;
        if ((horas.get(numFila).getInicio().equals(dataProyecto.getMañana2().getInicio())) || (horas.get(numFila).getInicio().equals(dataProyecto.getTarde2().getInicio()))) {
            for (int n = 0; n < 6; n++) {
                c = new PdfPCell(new Paragraph(""));
                c.setFixedHeight(alturaRecreos);
                c.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                t.addCell(c);
            }
        }
    }

    /**
     *
     * @param horas
     * @param numFila
     * @param t
     */
    public void creaSeparadorMañanaTarde(ArrayList<RangoHoras> horas, int numFila, PdfPTable t) {
        PdfPCell c;
        if ((horas.get(numFila).getInicio().equals(dataProyecto.getTarde1().getInicio())) && (numFila > 0)) {
            for (int n = 0; n < 6; n++) {
                c = new PdfPCell(new Paragraph(""));
                c.setFixedHeight(alturaSepMañanaTarde);
                c.setBorder(Rectangle.TOP | Rectangle.BOTTOM);
                t.addCell(c);
            }
        }
    }

    /**
     *
     * @param h
     * @return
     */
    protected Paragraph getParagraphForAsignatura(HorarioItem h) {
        Font font = new Font(Font.FontFamily.HELVETICA, 6);
        Paragraph p = null;
        p = new Paragraph(h.getAsignatura().getNombre(), font);
//        BaseFont bf;
//        try {
//            bf = BaseFont.createFont(
//                    BaseFont.TIMES_ROMAN,
//                    BaseFont.CP1252,
//                    BaseFont.EMBEDDED);
//            Font font = new Font(bf, 6);
//            p = new Paragraph(h.getAsignatura().getNombre(), font);
//        } catch (DocumentException ex) {
//            Logger.getLogger(AbstractPrinter.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(AbstractPrinter.class.getName()).log(Level.SEVERE, null, ex);
//        }
        return p;
    }

    /**
     *
     * @return
     */
    public String getTextoTitulo() {
        return textoTitulo;
    }

    /**
     *
     * @param textoTitulo
     */
    public void setTextoTitulo(String textoTitulo) {
        this.textoTitulo = textoTitulo;
    }
}
//  File temp = File.createTempFile("i-am-a-temp-file", ".tmp" );
// 
//    	    String absolutePath = temp.getAbsolutePath();
//    	    System.out.println("File path : " + absolutePath);
// 
//    	    String filePath = absolutePath.
//    	    	     substring(0,absolutePath.lastIndexOf(File.separator));
// 
//    	    System.out.println("File path : " + filePath);

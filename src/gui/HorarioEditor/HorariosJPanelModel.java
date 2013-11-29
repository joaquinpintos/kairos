/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.HorarioEditor;

import data.DataKairos;
import data.DataProyectoListener;
import data.MyConstants;
import data.RangoHoras;
import data.genetic.Asignacion;
import data.genetic.Casilla;
import data.genetic.ListaCasillas;
import data.genetic.ListaSegmentos;
import data.genetic.PosibleSolucion;
import data.genetic.Segmento;
import data.horarios.HorarioItem;
import data.horarios.DatosHojaHorario;
import gui.AbstractMainWindow;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

/**
 * Esta clase maneja la representación de horarioitem dentro de un jpanel,
 * dibuja las marcas horarias y columnas de la semana, y actualiza las
 * posiciones pra encajarlas
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class HorariosJPanelModel {

    JPanel jPanelHorarios;
    DataKairos dk;
    private final ArrayList<DataProyectoListener> listeners;
    //La solución que estoy mostrando
    private PosibleSolucion solucion;
    private String hashAulaMostrada;
    private ArrayList<RangoHoras> listaDeRangosHorariosMañana;
    private ArrayList<RangoHoras> listaDeRangosHorariosTarde;
    private final ArrayList<DraggableHorarioItemComponent> data;
    private final HashMap<String, DatosHojaHorario> horariosEnTablaPorAula;
    private final HashMap<String, Integer> numeroFilasPorAula;
    private AbstractMainWindow mainWindow;
    private final JListAulasModel jListAulasModel;
    //Constantes de tamaño
    ;
    private final int W_MARGIN = 2;
    private final int W_OFFSET = 3;
    private final int H_MARGIN = 2;
    private final int H_OFFSET = 3;
//    private final int H_RANGO_HORA = 35;
//    private final int H_DIA_SEMANA = H_RANGO_HORA - H_OFFSET;
    private boolean dibujaRectasVerticales = true;
    private boolean dibujaRectasHorizontales = true;
    //HashAula---->[row,col]-->numCasilla (fácil ¿no?)
    private final HashMap<String, HashMap<Integer[], Integer>> mapFilaColumnaToCasilla;
    private int filaRecreoMañana;
    private int filaRecreoTarde;
    private int filaRecreo;

    /**
     *
     * @param jPanelHorarios
     * @param jListAulasModel
     * @param dk
     */
    public HorariosJPanelModel(JPanel jPanelHorarios, JListAulasModel jListAulasModel, DataKairos dk) {
        this.jPanelHorarios = jPanelHorarios;
        this.dk = dk;
        this.jListAulasModel = jListAulasModel;
        horariosEnTablaPorAula = new HashMap<String, DatosHojaHorario>();
        numeroFilasPorAula = new HashMap<String, Integer>();
        mapFilaColumnaToCasilla = new HashMap<String, HashMap<Integer[], Integer>>();
        data = new ArrayList<DraggableHorarioItemComponent>();
        listeners = new ArrayList<DataProyectoListener>();
        calculaFilasRecreo();

    }

    /**
     *
     * @param w
     * @param h
     */
    public void dibujaGuias(int w, int h) {
        dibujaDiasSemana(w, h);
        dibujaHoras(w, h);

    }

    private void dibujaDiasSemana(int w, int h) {
        for (int n = 0; n < 5; n++) {
            JLabel l = new JLabel(MyConstants.DIAS_SEMANA[n], JLabel.CENTER);
            //l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            jPanelHorarios.add(l);
            int x = col2x(n + 1, w, h);
            l.setOpaque(true);
            l.setBackground(MyConstants.FONDO_CASILLA_HORARIO);
            l.setBounds(x, row2y(0, w, h), wCol(w, h), hRow(w, h));
            //TODO: Las rectas se borran inmediatamente
//            if (dibujaRectasVerticales)
//            {
//                Graphics g = jPanelHorarios.getGraphics();
//                g.drawLine(x, 0, x, h);
//            }
        }
    }

    private void dibujaHoras(int w, int h) {
        ArrayList<RangoHoras> horas;
        filaRecreo = 0;
        if (hashAulaMostrada.contains("@T")) {
            horas = listaDeRangosHorariosTarde;
            filaRecreo = filaRecreoTarde;
        } else {
            horas = listaDeRangosHorariosMañana;
            filaRecreo = filaRecreoMañana;
        }
        if (horas != null) {

            for (int n = 0; n < horas.size(); n++) {
                RangoHoras r = horas.get(n);
                JLabel l = new JLabel(r.toString(), JLabel.CENTER);
                l.setBackground(MyConstants.FONDO_CASILLA_HORARIO);
                l.setOpaque(true);
                //l.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
                jPanelHorarios.add(l);
                l.setBounds(col2x(0, w, h), row2y(n + 1, w, h), wCol(w, h), hRow(w, h));

                if ((r.getFin().equals(dk.getDP().getMañana1().getFin())) || ((r.getFin().equals(dk.getDP().getTarde1().getFin())))) {
                    JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
                    jPanelHorarios.add(sep);
                    sep.setBounds(col2x(0, w, h), row2y(n + 2, w, h) - H_OFFSET, w, H_OFFSET);
                    sep.setBorder(BorderFactory.createLineBorder(Color.gray, H_OFFSET));
                    //sep.setPreferredSize(new Dimension(w,H_OFFSET));
                }
            }
        } else {
            System.out.println("NULL!!");
        }
//        jPanelHorarios.setMinimumSize(new Dimension(300, row2y(horas.size() + 1, w, h)));
    }

    /**
     * Devuelve la coordenada x a partir de la columna
     *
     * @param col
     * @param w
     * @param h
     * @return
     */
    public int col2x(int col, int w, int h) {
        int wCol = wCol(w, h);
        int resul = W_MARGIN + col * (wCol + W_OFFSET);
        return resul;

    }

    /**
     *
     * @param row
     * @param w
     * @param h
     * @return
     */
    public int row2y(int row, int w, int h) {
        int hrow = hRow(w, h);
        int resul = H_MARGIN + row * hrow;
        if (row > 0) {
            resul += (row) * H_OFFSET;
        }
//        resul += (row > filaRecreo ? 5 : 0);
        return resul;
    }

    /**
     *
     * @param w
     * @param h
     * @return
     */
    public int wCol(int w, int h) {
        return (w - 5 * W_OFFSET - 2 * W_MARGIN) / 6;
    }

    /**
     *
     * @param w
     * @param h
     * @return
     */
    public int hRow(int w, int h) {
        int numFilas = numeroFilasPorAula.get(hashAulaMostrada) + 1;
        return (h - (numFilas - 1) * H_OFFSET - H_MARGIN) / numFilas;
    }

    /**
     * Calcula la altura de la celda para tamaño de frame indicado.
     *
     * @param numFila
     * @param numColumna
     * @return
     */
    public int filaColumnaToNumeroCasilla(int numFila, int numColumna) {
        int resul = -1;
        int numFilasTotal = numeroFilasPorAula.get(hashAulaMostrada);
        if ((numColumna > 0) && (numFila > 0)) {
            resul = (numColumna - 1) * numFilasTotal + numFila - 1;
        }
        return resul;
    }

    /**
     *
     */
    public final void updateData() {
        dk.getDP().getDataAulas().buildArrayAulaContainers();//Necesario?
//        rebuildMapAulasToContainers();
        horariosEnTablaPorAula.clear();
        numeroFilasPorAula.clear();
        mapFilaColumnaToCasilla.clear();//necesario???

        try {
            for (HorarioItem h : dk.getDP().getHorario().getHorarios()) {
                String hashAula;
                hashAula = h.getHashAula();

                if (!horariosEnTablaPorAula.containsKey(hashAula)) {//Creo una nueva hoja de horario
                    DatosHojaHorario datosHojaHorario = new DatosHojaHorario(dk.getDP());

                    horariosEnTablaPorAula.put(hashAula, datosHojaHorario);
                    if (hashAula.contains("@T")) {
//                        System.out.println("T!!");
                        numeroFilasPorAula.put(hashAula, datosHojaHorario.setTarde(false));
                        listaDeRangosHorariosTarde = datosHojaHorario.getRangosHoras();
                    } else {
//                        System.out.println("M!!");
                        numeroFilasPorAula.put(hashAula, datosHojaHorario.setMañana(false));
                        listaDeRangosHorariosMañana = datosHojaHorario.getRangosHoras();
                    }
                }
                horariosEnTablaPorAula.get(hashAula).add(h);

            }
        } catch (NullPointerException ex) {
            Logger.getLogger(HorariosJPanelModel.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     */
    public void rebuildAll() {
        Dimension d = jPanelHorarios.getSize();
        int w = d.width;
        int h = d.height;
        jPanelHorarios.removeAll();
        dibujaGuias(w, h);
        dibujaItems(w, h);
        mainWindow.repaint();

    }

    /**
     *
     * @param w
     * @param h
     */
    public void dibujaItems(final int w, final int h) {
        final HorariosJPanelModel pane = this;
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run()
        {
            data.clear();
            if (!dk.getDP().getHorario().getHorarios().isEmpty()) {
                int contador = 0;
                for (HorarioItem hItem : dk.getDP().getHorario().getHorarios()) {
                    String hashH = hItem.getHashAula();
                    if (hashAulaMostrada == null ? hashH == null : hashAulaMostrada.equals(hashH)) {
                        DraggableHorarioItemComponent c = new DraggableHorarioItemComponent(hItem, pane, contador);
                        contador++;
                        jPanelHorarios.add(c);
                        data.add(c);
                        drawItem(c, w, h);
                    }
                }
            }//End of   if (!dk.getDP().getHorario().getHorarios().isEmpty())
        }

    }
//);

    /**
     *
     * @param c
     * @param h
     * @param w
     */
    public void drawItem(DraggableHorarioItemComponent c, int w, int h) {
        HorarioItem hItem = c.getH();
//        if (!hItem.isHuecoLibre()) {
        int dia = hItem.getDiaSemana();
        int fila = horariosEnTablaPorAula.get(hashAulaMostrada).getNumeroDeFila(hItem);
        int nu = hItem.getNumeroDeCasillasQueOcupa();
        int alturaItem = hRow(w, h) * nu;
        if (nu > 0) {
            alturaItem += (nu - 1) * H_OFFSET;
        }
        c.setBounds(col2x(dia, w, h), row2y(fila, w, h), wCol(w, h), alturaItem);
//        }
    }

    /**
     *
     */
    public void relocate() {
//        Dimension d = jPanelHorarios.getSize();
//        int w = d.width;
//        int h = d.height;
//        relocateItems(w, h);
//   
        if ((dk.getDP().getHorario().hayUnaSolucion()) && (hashAulaMostrada != null)) {
            rebuildAll();
        }
    }

    public void relocateItems() {
        Dimension d = jPanelHorarios.getSize();
        final int w = d.width;
        final int h = d.height;
        for (DraggableHorarioItemComponent c : data) {
            HorarioItem hItem = c.getH();
//            if (!hItem.isHuecoLibre()) {
            int dia = hItem.getDiaSemana();
            int fila = horariosEnTablaPorAula.get(hashAulaMostrada).getNumeroDeFila(hItem);
            int nu = hItem.getNumeroDeCasillasQueOcupa();
            int alturaItem = hRow(w, h) * nu;
            if (nu > 0) {
                alturaItem += (nu - 1) * H_OFFSET;
            }
            c.setBounds(col2x(dia, w, h), row2y(fila, w, h), wCol(w, h), alturaItem);
//            }
        }
    }

    /**
     *
     * @return
     */
    public String getHashAulaMostrada() {
        return hashAulaMostrada;
    }

    /**
     *
     * @param hashAulaMostrada
     */
    public void setHashAulaMostrada(String hashAulaMostrada) {
        this.hashAulaMostrada = hashAulaMostrada;
    }

    /**
     * Para una coordenada x dentro del panel devuelve número de columna que
     * mejor se ajusta a dicho valor.
     *
     * @param x coordenada x
     * @param w anchura del panel
     * @param h altura del panel
     * @return número de columna que mejor se ajusta
     */
    public int x2col(int x, int w, int h) {
        int numColumna = 0;
        while (col2x(numColumna, w, h) < x) {
            numColumna++;
        }
        if (Math.abs(col2x(numColumna, w, h) - x) > Math.abs(col2x(numColumna - 1, w, h) - x)) {
            numColumna--;
        }
        return numColumna;
    }

    /**
     * Para una coordenada y dentro del panel devuelve número de fila que mejor
     * se ajusta a dicho valor.
     *
     * @param y coordenada y
     * @param w anchura del panel
     * @param h altura del panel
     * @return
     */
    public int y2row(int y, int w, int h) {
        int numFila = 0;
        while (row2y(numFila, w, h) < y) {
            numFila++;
        }
        if (Math.abs(row2y(numFila, w, h) - y) > Math.abs(row2y(numFila - 1, w, h) - y)) {
            numFila--;
        }
        return numFila;
    }

    void prepareToDropItemAt(DraggableHorarioItemComponent draggableItem, int x, int y) {
        solucion = dk.getDP().getHorario().getSolucion();
        Dimension d = jPanelHorarios.getSize();
        final int w = d.width;
        final int h = d.height;

        int numColumna = x2col(x, w, h);
        int numFila = y2row(y, w, h);
        if ((numColumna > 0) && (numFila > 0)) {
            int numCasilla = filaColumnaToNumeroCasilla(numFila, numColumna);

            if (puedoSoltarAqui(draggableItem.getH(), numFila, numColumna)) {
                effectivelyDropItem(draggableItem, numColumna, numFila, w, h);
            } else {
                System.out.println("No puedo soltar aqui");
            }
        }
        relocateItems();
    }

    /**
     * Comprueba si el item puede soltarse en la casilla correspondiente. -No se
     * puede soltar un item sobre otro si el resultado solapa otra asignatura
     * -Hay que comprobar tanto el destino como el final
     *
     * @param h Item a soltar
     * @param numCasillaDst Número de casilla en la que se soltará
     * @return True si se puede soltar. False en caso contrario.
     */
    private boolean puedoSoltarAqui(HorarioItem h, int numFila, int numColumna) {

        boolean resul = true;
        Asignacion asig = solucion.getAsignacion(hashAulaMostrada);
        int totalFilas = numeroFilasPorAula.get(hashAulaMostrada);
        int numCasillaDst = filaColumnaToNumeroCasilla(numFila, numColumna);
        if ((numCasillaDst == h.getNumcasilla() || numFila == 0) || (numColumna == 0) || (numFila > totalFilas + 1 - h.getNumeroDeCasillasQueOcupa())) {
            resul = false;
        }
        //Compruebo si el comienzo es realmente el comienzo de un segmento (a menos que sea el mismo?)
        if (resul) {
            ListaSegmentos ls = dk.getDP().getDatosPorAula(hashAulaMostrada).getListaSegmentos();
            int numSeg = asig.getQueSegmentoHayEnCasilla(numCasillaDst);
            int numCasReal = asig.enQueCasillaEstaSegmento(numSeg);
            if ((numCasReal != numCasillaDst) && (numCasReal != h.getNumcasilla())) {
                resul = false;
            }
        }
        if (resul) {//Compruebo si no voy a partir ningún segmento
//            System.out.println("¿Cabe un segmento que ocupa " + h.getNumeroDeCasillasQueOcupa() + " casillas?");
            ArrayList<Integer> a = calculaCasillaDestino(asig, h.getNumcasilla(), numCasillaDst, h.getNumeroDeCasillasQueOcupa());
            if (a == null) {
                resul = false;
            }
        }
        return resul;

    }

    /**
     * Realiza el cambio de items
     *
     * @param dragSrc Item que está siendo trasladado
     * @param numColumna Número de columna donde es soltado
     * @param numFila Número de fila donde es soltado
     * @param w Anchura del panel de horarios
     * @param h ALtura del panel de horarios
     */
    private void effectivelyDropItem(final DraggableHorarioItemComponent dragSrc, final int numColumna, final int numFila, final int w, final int h) {
        intercambioHorarioItemsNew(dragSrc, numFila, numColumna, w, h);
        fireDataEvent(dk.getDP().getHorario(), DataProyectoListener.MODIFY);
    }

    /**
     * Intercambia el item dSrc con los componentes que haya en la fila-columna
     * dadas. El conjunto de componentes destino tiene la misma duración que
     * dSrc
     *
     * @param dSrc
     * @param filaB
     * @param columnaB
     * @param w
     * @param h
     */
    private void intercambioHorarioItemsNew(DraggableHorarioItemComponent dSrc, int filaB, int columnaB, int w, int h) {

        Asignacion asig = solucion.getAsignacion(hashAulaMostrada);
        int filaA1 = getFilaHorarioComponent(dSrc);
        int columnaA = getColumnaHorarioComponent(dSrc);
        int a = dSrc.getH().getNumcasilla();
        int b = filaColumnaToNumeroCasilla(filaB, columnaB);
        int n = dSrc.getH().getNumeroDeCasillasQueOcupa();

        int n2 = 0;
        int n1 = 0;
        ArrayList<Integer> casillasAMover = calculaCasillaDestino(asig, a, b, n);
        for (int k : casillasAMover) {
            DraggableHorarioItemComponent dMove = getHorarioItemPorCasilla(k);

            if (dMove != null) {
                //Miro si la casilla de destino está "reservada" para dSrc, si lo está, avanzo n1
                while ((columnaA == columnaB) && (filaA1 + n1 >= filaB) && (filaA1 + n1 < filaB + dSrc.getH().getNumeroDeCasillasQueOcupa())) {
                    n1++;
                }
                moveToCelda(dMove, filaA1 + n1, columnaA);
                n1 += dMove.getH().getNumeroDeCasillasQueOcupa();
                n2 += dMove.getH().getNumeroDeCasillasQueOcupa();
            } else {
                n2++;
            }
        }

        moveToCelda(dSrc, filaB, columnaB);

        //Ahora actualizo los datos de la solución
        int numSegmentoSrc = dSrc.getH().getNumeroSegmento();
        ArrayList<Integer> oldAsig = asig.getAsignaciones();
        int index1 = oldAsig.indexOf(numSegmentoSrc);//indice del segmento origen
        int numS = casillasAMover.get(0);
        int index2 = asig.getQueCasilla().indexOf(numS);
        asig.setAsignaciones(changeSegmentos(index1, index2, asig, casillasAMover, numSegmentoSrc));
        asig.update();
    }

    void moveToCelda(DraggableHorarioItemComponent drg, int numFila, int numColumna) {
        ListaCasillas lc = dk.getDP().getDatosPorAula(hashAulaMostrada).getListaCasillas();
        int numCasillaAux = filaColumnaToNumeroCasilla(numFila, numColumna);
        drg.moveToCasilla(lc, numCasillaAux);

    }

    private void intercambioHorarioItems(DraggableHorarioItemComponent dSrc, DraggableHorarioItemComponent dDst, int numCasillaDst) {
        ListaCasillas lc = dk.getDP().getDatosPorAula(hashAulaMostrada).getListaCasillas();
        solucion = dk.getDP().getHorario().getSolucion();//Es necesario que solucion no sea local?
        Asignacion asig = solucion.getAsignacion(hashAulaMostrada);
        //Datos del elemento a trasladar: número de segmento, casilla y cuántas casillas ocupa
        HorarioItem src = dSrc.getH();
        HorarioItem dst = dDst.getH();
        int numSegmentoSrc = src.getNumeroSegmento();
        int numCasillaSrc = src.getNumcasilla();
        int cuantasCasilasSrc = src.getNumeroDeCasillasQueOcupa();

//        int numCasillaDst = dDst.getH().getNumcasilla();
        ArrayList<Integer> segmentosDestino = calculaCasillaDestino(asig, src.getNumcasilla(), numCasillaDst, cuantasCasilasSrc);
        int numCasillaDondeVaDst = numCasillaSrc;
        int offset = 0;
        for (int n = 0; n < segmentosDestino.size(); n++) {
            DraggableHorarioItemComponent dMove = getHorarioItemPorCasilla(numCasillaDst + offset);//data.get(numCasillaDst + n);//Componente a mover
            dMove.moveToCasilla(lc, numCasillaDondeVaDst + offset);
            offset += dMove.getH().getNumeroDeCasillasQueOcupa();
        }
        dSrc.moveToCasilla(lc, numCasillaDst);
        //Ahora actualizo los datos de la solución
        ArrayList<Integer> oldAsig = asig.getAsignaciones();
        int index1 = oldAsig.indexOf(numSegmentoSrc);//indice del segmento origen
        int index2 = oldAsig.indexOf(segmentosDestino.get(0)); //indice del segmento destino
        asig.setAsignaciones(changeSegmentos(index1, index2, asig, segmentosDestino, numSegmentoSrc));
        asig.update();
    }

    /**
     * Devuelve un array de segmentos destino consecutivos que juntos ocupen
     * tanto como el src Por ejemplo, si src=3h, dst puede ser clase 2h+2 huecos
     * libre de media hora=4 segmentos
     *
     * @param asig Asignaciones sobre la que trabajar
     * @param numCasillaDst Casilla de destino de la que extraer los segmentos
     * @param cuantasCasillasSrc Cuántas casillas ha de rellenar con los
     * segmentos
     * @return Array con los segmentos. Devuelve null si no es posible rellenar
     * de forma que los segmentos ocupen exactamente cuantasCasilasSrc casillas.
     */
    private ArrayList<Integer> calculaCasillaDestino(Asignacion asig, int numCasillaSrc, int numCasillaDst, int cuantasCasillasSrc) {
        ListaSegmentos ls = dk.getDP().getDatosPorAula(hashAulaMostrada).getListaSegmentos();
        ArrayList<Integer> resul = new ArrayList<Integer>();

        int a = numCasillaSrc;
        int b = numCasillaDst;
        int n = cuantasCasillasSrc;

        //de donde a donde voy a sacar los segmentos
        int n1 = 0;
        int n2 = 0;
        //Caso 1: No tienen nada en comun src y dst
        if ((b < a - n) || (a + n < b)) {
            n1 = b;
            n2 = b + n;
        }
        //Caso 2: b dentro de [a,a+n]
        if ((a <= b) && (b <= a + n)) {
            n1 = a + n;
            n2 = b + n;
        }
        //Caso 3: a dentro de (b,b+n]
        if ((b < a) && (a <= b + n)) {
            n1 = b;
            n2 = a;
        }

        //Ahora extraigo los segmentos ocupados entre n1 y n2 y compruebo que 
        //encajen de forma exacta, es decir, que no haya ninguno asomando fuera del intervalo
        int totalCasillas = 0;
        int k = n1;
        while (k < n2) {
            int numSegmento = asig.getQueSegmentoHayEnCasilla(k);
            resul.add(k);
            Segmento s = ls.get(numSegmento);
            totalCasillas += s.getNumeroDeCasillasQueOcupa();
            k += s.getNumeroDeCasillasQueOcupa();
        }
        if (k > n2) {//Me he pasado!!!
            resul = null;
        }

//        System.out.println("Casillas [" + n1 + ", " + n2 + "]");
        return resul;
    }

    /**
     *
     * @return
     */
    public PosibleSolucion getSolucion() {
        return solucion;
    }

    /**
     *
     * @param solucion
     */
    public void setSolucion(PosibleSolucion solucion) {
        this.solucion = solucion;
    }

    /**
     *
     * @param mainWindow
     */
    public void setMainWindow(AbstractMainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Devuelve el componente DraggableHorarioIteComponent que se haya situado
     * en la fila/columna especificada. Devuelve null si no hay ninguno (es
     * decir si hay un espacio libre!)
     *
     * @param numFila
     * @param numColumna
     * @return
     */
    private DraggableHorarioItemComponent getHorarioItemEnCelda(int numFila, int numColumna) {

        int numCasilla = filaColumnaToNumeroCasilla(numFila, numColumna);
        return getHorarioItemPorCasilla(numCasilla);
    }

    private DraggableHorarioItemComponent getHorarioItemPorCasilla(int numCasilla) {
        DraggableHorarioItemComponent resul = null;
        OuterLoop:
        for (DraggableHorarioItemComponent d : data) {
            if (d.getH().getNumcasilla() == numCasilla) {
                resul = d;
                break OuterLoop;
            }
        }
        return resul;
    }

    private ArrayList<Integer> changeSegmentos(int index1, int index2, Asignacion asig, List<Integer> casillasDestino, int numSegmentoSrc) {
        //Primero construyo la lista de segmentos a partir de casillas
        ArrayList<Integer> segmentosDestino = new ArrayList<Integer>();
        ArrayList<Integer> oldAsig = asig.getAsignaciones();
        for (int c : casillasDestino) {
            segmentosDestino.add(asig.getQueSegmentoHayEnCasilla(c));
        }

        ArrayList<Integer> nuevaAsig = new ArrayList<Integer>();
        if (index1 < index2) {
            nuevaAsig.addAll(oldAsig.subList(0, index1));
            nuevaAsig.addAll(segmentosDestino);
            nuevaAsig.addAll(oldAsig.subList(index1 + 1, index2));
            nuevaAsig.add(numSegmentoSrc);
            nuevaAsig.addAll(oldAsig.subList(index2 + segmentosDestino.size(), oldAsig.size()));
        }
        if (index1 > index2) {
            nuevaAsig.addAll(oldAsig.subList(0, index2));
            nuevaAsig.add(numSegmentoSrc);
            nuevaAsig.addAll(oldAsig.subList(index2 + segmentosDestino.size(), index1));
            nuevaAsig.addAll(segmentosDestino);
            nuevaAsig.addAll(oldAsig.subList(index1 + 1, oldAsig.size()));

        }
        return nuevaAsig;
    }

    private int getFilaHorarioComponent(DraggableHorarioItemComponent dSrc) {
        int numCasilla = dSrc.getH().getNumcasilla();

        return (numCasilla % numeroFilasPorAula.get(hashAulaMostrada)) + 1;
    }

    private int getColumnaHorarioComponent(DraggableHorarioItemComponent dSrc) {
        int numCasilla = dSrc.getH().getNumcasilla();
        return (numCasilla / numeroFilasPorAula.get(hashAulaMostrada)) + 1;
    }

    /**
     *
     * @param l
     */
    public void addListener(DataProyectoListener l) {
        listeners.add(l);
    }

    /**
     *
     * @param l
     */
    public void removeListener(DataProyectoListener l) {
        listeners.remove(l);
    }

    /**
     *
     * @param data
     * @param type
     */
    public void fireDataEvent(Object data, int type) {
        for (DataProyectoListener l : listeners) {
            l.dataEvent(data, type);
        }
    }

    /**
     *
     */
    /**
     *
     */
    public void clearConflictivos() {
//        for (HorarioItem h : dk.getDP().getHorario().getHorarios()) {
//            h.setMarkLevel(HorarioItem.NO_MARK);
//
//        }
        for (DraggableHorarioItemComponent hItem : data) {
            hItem.setMarkLevel(HorarioItem.NO_MARK);
        }
    }

    /**
     *
     * @param casillasConflictivas
     * @param markType
     */
    public void marcaConflictivos(HashMap<String, HashSet<Integer>> casillasConflictivas, int markType) {
//        System.out.println("[DEBUG] Marco conflictivos "+casillasConflictivas);

        for (String hashAula : casillasConflictivas.keySet()) {
            for (int n : casillasConflictivas.get(hashAula)) {
                DraggableHorarioItemComponent drg = getItemConCasilla(n);
                if ((drg != null) && drg.getH().getHashAula().equals(hashAula)) {
                    drg.setMarkLevel(markType);
                }
            }

        }

    }

    private DraggableHorarioItemComponent getItemConCasilla(int n) {
        DraggableHorarioItemComponent resul = null;
        OuterLoop:
        for (DraggableHorarioItemComponent drg : data) {
            if (drg.getH().getNumcasilla() == n) {
                resul = drg;
                break OuterLoop;

            }
        }
        return resul;
    }

    /**
     * Calcula las filas donde está el recreo, mañana y tarde
     */
    private void calculaFilasRecreo() {
        int contador = 2;
        filaRecreoMañana = -1;
        filaRecreoTarde = -1;
        for (String hashAula : dk.getDP().getMapDatosPorAula().keySet()) {
            int n;
            if ((hashAula.contains("@T")) && (filaRecreoTarde != -1)) {
                contador = -1;
                n = 0;
                InnerLoopTarde:
                for (Casilla c : dk.getDP().getMapDatosPorAula().get(hashAula).getListaCasillas().getCasillas()) {
                    if (c.isFinalDeRango()) {
                        break;
                    }
                    n++;
                }
                filaRecreoTarde = n;
            }
            if ((hashAula.contains("@M")) && (filaRecreoMañana != -1)) {
                contador = -1;
                n = 0;
                InnerLoopMañana:
                for (Casilla c : dk.getDP().getMapDatosPorAula().get(hashAula).getListaCasillas().getCasillas()) {
                    if (c.isFinalDeRango()) {//La primera casilla con final de rango es el recreo.
                        break InnerLoopMañana;
                    }
                    n++;
                }
                filaRecreoMañana = n;
            }
            if (contador == 0) {
                break;
            }
        }
//        System.out.println("mañanaRecreo= " + filaRecreoMañana);
//        System.out.println("tardeRecreo= " + filaRecreoTarde);
    }
}

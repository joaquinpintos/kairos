/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.HorarioEditor;

import data.MyConstants;
import data.RangoHoras;
import data.genetic.Casilla;
import data.genetic.ListaCasillas;
import data.horarios.HorarioItem;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DraggableHorarioItemComponent extends JPanel {

    private boolean draggable = true;
    /**
     * 2D Point representing the coordinate where mouse is, relative parent
     * container
     */
    protected Point anchorPoint;
    /**
     * Default mouse cursor for dragging action
     */
    protected Cursor draggingCursor = Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
    /**
     * If sets <b>TRUE</b> when dragging component, it will be painted over each
     * other (z-Buffer change)
     */
    protected boolean overbearing = true;
    private final HorarioItem h;
    private final HorariosJPanelModel jPanelModel;
    private int debugNumber;
    private final Border selectedConflictiveBorder;
    private final Border conflictiveBorder;
    private Color colorAsig;
    private Color colorBorde;

    /**
     *
     * @param h
     * @param jPanelModel
     * @param debugNumber
     */
    public DraggableHorarioItemComponent(HorarioItem h, HorariosJPanelModel jPanelModel, int debugNumber) {
        this.h = h;
        this.jPanelModel = jPanelModel;
        this.debugNumber = debugNumber;
        selectedConflictiveBorder = BorderFactory.createLineBorder(MyConstants.SELECTED_CONFLICTIVE_ITEM, 5);
        conflictiveBorder = BorderFactory.createLineBorder(MyConstants.NON_SELECTED_CONFLICTIVE_ITEM, 5);
        setContent();
        addDragListeners();
    }

    /**
     *
     * @param debugNumber
     */
    public void setDebugNumber(int debugNumber) {
        this.debugNumber = debugNumber;
    }

    /**
     *
     * @return
     */
    public int getDebugNumber() {
        return debugNumber;
    }

    private void setContent() {
        if (true) {
            setRealContent();
        } else {
            setDebugContent();
        }

    }

    private void setRealContent() {
        if (!this.h.isHuecoLibre()) {//hueco ocupado
            this.setOpaque(true);
            BoxLayout la = new BoxLayout(this, BoxLayout.Y_AXIS);
            this.setLayout(la);
            colorAsig = h.getAsignatura().getColorEnTablaDeHorarios();
            colorBorde = new Color(colorAsig.getRed() / 2, colorAsig.getGreen() / 2, colorAsig.getBlue() / 2);

            this.setBackground(colorAsig);
            String nombre = h.getAsignatura().getNombreCorto();

            if (nombre.equals("")) {
                nombre = h.getAsignatura().getNombre();
            }
            JLabel l1 = new JLabel(nombre, JLabel.CENTER);
            l1.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(l1);
            JLabel l2 = new JLabel("G" + h.getGrupo().getNombre() + " (" + h.getProfesor().getNombreCorto() + ")", JLabel.CENTER);
            l2.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(l2);

        } else//hueco libre
        {
            this.setOpaque(true);
            setBackground(MyConstants.FONDO_CASILLA_HORARIO);
        }
        drawConflictivo();
    }

    private void drawConflictivo() {
        switch (h.getMarkLevel()) {
            case HorarioItem.NO_MARK:
                if (!this.h.isHuecoLibre()) {//hueco ocupado
                    this.setBorder(BorderFactory.createLineBorder(colorBorde, 2));
                } else {
                    this.setBorder(null);
                }
                break;
            case HorarioItem.SIMPLE_MARK:
                this.setBorder(conflictiveBorder);
                break;
            case HorarioItem.DOUBLE_MARK:
                this.setBorder(selectedConflictiveBorder);
                break;
        }
    }

    /**
     *
     * @param markLevel
     */
    public void setMarkLevel(int markLevel) {
        h.setMarkLevel(markLevel);
        drawConflictivo();
    }

    private void setDebugContent() {
        if (!this.h.isHuecoLibre()) {
            this.setOpaque(true);
            BoxLayout la = new BoxLayout(this, BoxLayout.Y_AXIS);
            this.setLayout(la);
            Color colorFondo = h.getAsignatura().getColorEnTablaDeHorarios();
            switch (h.getMarkLevel()) {
                case HorarioItem.NO_MARK:
                    Color borde = new Color(colorFondo.getRed() / 2, colorFondo.getGreen() / 2, colorFondo.getBlue() / 2);
                    this.setBorder(BorderFactory.createLineBorder(borde, 2));
                    break;
                case HorarioItem.SIMPLE_MARK:
                    this.setBorder(conflictiveBorder);
                    break;
                case HorarioItem.DOUBLE_MARK:
                    this.setBorder(selectedConflictiveBorder);
                    break;
            }

            this.setBackground(colorFondo);
            this.add(new JLabel(h.getAsignatura().toString()));
            this.add(new JLabel(h.getGrupo().toString()));
            this.add(new JLabel(h.getProfesor().toString()));
            this.add(new JLabel(h.getMarkLevel() + " c " + h.getNumcasilla()));

        } else {
            this.setOpaque(false);
            BoxLayout la = new BoxLayout(this, BoxLayout.Y_AXIS);
            this.setLayout(la);
            switch (h.getMarkLevel()) {
                case HorarioItem.NO_MARK:
                    this.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
                    break;
                case HorarioItem.SIMPLE_MARK:
                    this.setBorder(conflictiveBorder);
                    break;
                case HorarioItem.DOUBLE_MARK:
                    this.setBorder(selectedConflictiveBorder);
                    break;
            }

            this.add(new JLabel("Libre" + h.getNumcasilla() + ""));
        }
    }

    /**
     * Add Mouse Motion Listener with drag function
     */
    private void addDragListeners() {
        /**
         * This handle is a reference to THIS beacause in next Mouse Adapter
         * "this" is not allowed
         */
        final DraggableHorarioItemComponent handle = this;
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                anchorPoint = e.getPoint();
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                try {//NO sé por qué a veces da nullpointerexception
                    int anchorX = anchorPoint.x;
                    int anchorY = anchorPoint.y;

                    Point parentOnScreen = getParent().getLocationOnScreen();
                    Point mouseOnScreen = e.getLocationOnScreen();
                    int newX = mouseOnScreen.x - parentOnScreen.x - anchorX;
                    int newY = mouseOnScreen.y - parentOnScreen.y - anchorY;
                    if (newX < 5) {
                        newX = 5;
                    }
                    if (newY < 5) {
                        newY = 5;
                    }
                    Point position = new Point(newX, newY);

                    setLocation(position);

                    //Change Z-Buffer if it is "overbearing"
                    if (overbearing) {
                        getParent().setComponentZOrder(handle, 0);
                        repaint();

                    }
                } catch (NullPointerException ex) {
                }
//                System.out.println("Mouse dragged on " + position.toString());
            }
        });

        //Añado un mouseListener para ver cuándo se suelta el ratón
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point position = null;
                try {
                    int anchorX = handle.anchorPoint.x;
                    int anchorY = handle.anchorPoint.y;
//                super.mouseReleased(e); //To change body of generated methods, choose Tools | Templates.
                    Point parentOnScreen = getParent().getLocationOnScreen();
                    Point mouseOnScreen = e.getLocationOnScreen();
                    position = new Point(mouseOnScreen.x - parentOnScreen.x - anchorX, mouseOnScreen.y - parentOnScreen.y - anchorY);

                } catch (NullPointerException ex) {
                    System.out.println("Algo va mal, he metido la pata con una excepción!");
                }
                if (position != null) {
                    dropThisItemAt((int) position.getX(), (int) position.getY());
                }
            }
        });

    }

    private void dropThisItemAt(int x, int y) {
        int xc = (int) this.getBounds().getCenterX();
        int yc = (int) this.getBounds().getCenterY();
        jPanelModel.prepareToDropItemAt(this, x, y);

    }

    /**
     * Remove all Mouse Motion Listener. Freeze component.
     */
    private void removeDragListeners() {
        for (MouseMotionListener listener : this.getMouseMotionListeners()) {
            removeMouseMotionListener(listener);
        }
        setCursor(Cursor.getDefaultCursor());
    }

    /**
     * Get the value of draggable
     *
     * @return the value of draggable
     */
    public boolean isDraggable() {
        return draggable;
    }

    /**
     * Set the value of draggable
     *
     * @param draggable new value of draggable
     */
    public void setDraggable(boolean draggable) {
        this.draggable = draggable;
        if (draggable) {
            addDragListeners();
        } else {
            removeDragListeners();
        }

    }

    /**
     * Get the value of draggingCursor
     *
     * @return the value of draggingCursor
     */
    public Cursor getDraggingCursor() {
        return draggingCursor;
    }

    /**
     * Set the value of draggingCursor
     *
     * @param draggingCursor new value of draggingCursor
     */
    public void setDraggingCursor(Cursor draggingCursor) {
        this.draggingCursor = draggingCursor;
    }

    /**
     * Get the value of overbearing
     *
     * @return the value of overbearing
     */
    public boolean isOverbearing() {
        return overbearing;
    }

    /**
     * Set the value of overbearing
     *
     * @param overbearing new value of overbearing
     */
    public void setOverbearing(boolean overbearing) {
        this.overbearing = overbearing;
    }

    void locateTo(int col2x, int row2y) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return
     */
    public HorarioItem getH() {
        return h;
    }

    void rebuildContent() {
//        this.removeAll();
//        setContent();
    }

    void moveToCasilla(ListaCasillas lc, int numCasillaDst) {
        Casilla c = lc.get(numCasillaDst);
        h.setNumcasilla(numCasillaDst);
        h.setDiaSemana(c.getDiaSemana());
        int dur = h.getRangoHoras().getDuracionMinutos();
        h.setRangoHoras(new RangoHoras(c.getHora(), dur));

    }

}

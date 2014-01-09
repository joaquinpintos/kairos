package data;

import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class MyConstants {

    /**
     * Cadena con los nombres de los días de la semana
     */
    public static final String[] DIAS_SEMANA = {"Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"};
    /**
     * Cadena con los nombres de los días de la semana
     */
    public static final String[] XML_DAYS_OF_THE_WEEK = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};

    /**
     * Logo del programa. Palabra Kairos
     */
    public final ImageIcon LOGOGRANDE = new ImageIcon(getClass().getResource("/data/images/logo.png"));
    /**
     * Relieve de Kairos
     */
    public final ImageIcon RELIEVE_KAIROS = new ImageIcon(getClass().getResource("/data/images/kairos.png"));
    /**
     * Icono para los grupos
     */
    public final ImageIcon GRUPO_ICON = new ImageIcon(getClass().getResource("/data/images/grupo.png"));
    /**
     * Icono para los departamentos
     */
    public final ImageIcon DEPARTAMENTO_ICON = new ImageIcon(getClass().getResource("/data/images/door.png"));
    /**
     * Icono para las carreras
     */
    public final ImageIcon CARRERA_ICON = new ImageIcon(getClass().getResource("/data/images/birrete.png"));
    /**
     * Icono para las asignaturas
     */
    public final ImageIcon ASIGNATURA_ICON = new ImageIcon(getClass().getResource("/data/images/asignatura.png"));
    /**
     * Icono para los profesores
     */
    public final ImageIcon PROFESOR_ICON = new ImageIcon(getClass().getResource("/data/images/profesor.png"));
    /**
     * Icono para los cursos
     */
    public final ImageIcon CURSO_ICON = new ImageIcon(getClass().getResource("/data/images/curso.png"));
    /**
     * Icono para las aulas
     */
    public final ImageIcon AULA_ICON = new ImageIcon(getClass().getResource("/data/images/aula.png"));
    /**
     * Icono para los tramos
     */
    public final ImageIcon TRAMO_ICON = new ImageIcon(getClass().getResource("/data/images/clock.png"));
    /**
     * Icono para las restricciones
     */
    public final ImageIcon RESTRICTION_ICON = new ImageIcon(getClass().getResource("/data/images/restrictionIcon.png"));
    /**
     * Icono para el nivel de las restricciones
     */
    public final ImageIcon RESTRICTIONLEVEL_ICON = new ImageIcon(getClass().getResource("/data/images/levelIcon.png"));

    /**
     * Icono de añadir
     */
    public final ImageIcon ADD_ICON = new ImageIcon(getClass().getResource("/data/images/add.png"));
    /**
     * Icono de eliminar
     */
    public final ImageIcon DELETE_ICON = new ImageIcon(getClass().getResource("/data/images/delete.png"));
    /**
     * Icono para bolita roja
     */
    public final ImageIcon RED_ICON = new ImageIcon(getClass().getResource("/data/images/red.png"));
    /**
     * Icono para bolita amarilla
     */
    public final ImageIcon YELLOW_ICON = new ImageIcon(getClass().getResource("/data/images/yellow.png"));
    /**
     * Icono para bolita verde
     */
    public final ImageIcon GREEN_ICON = new ImageIcon(getClass().getResource("/data/images/green.png"));
    /**
     * Icono de un semáforo con la luz roja encendida
     */
    public final ImageIcon RED_TRAFFIC_LIGHT = new ImageIcon(getClass().getResource("/data/images/redTrafficLight.png"));
    /**
     * Icono de un semáforo con la luz amarilla encendida
     */
    public final ImageIcon YELLOW_TRAFFIC_LIGHT = new ImageIcon(getClass().getResource("/data/images/yellowTrafficLight.png"));
    /**
     * Icono de un semáforo con la luz verde encendida
     */
    public final ImageIcon GREEN_TRAFFIC_LIGHT = new ImageIcon(getClass().getResource("/data/images/greenTrafficLight.png"));
    /**
     * Color de fondo para los items seleccionados de una lista
     */
    public static final Color SELECTED_ITEM_LIST = new Color(230,230,230);
    /**
     * Color de fondo para los items no seleccionados de una lista
     */
    public static final Color UNSELECTED_ITEM_LIST = new Color(255, 255, 255);
    public static final Color SIN_DOCENCIA_ASIGNADA = Color.RED;
    public static final Color CON_DOCENCIA_ASIGNADA = Color.BLUE;
    public static final Color CONFLICTIVE_ITEM = Color.RED;
    public static final Color NON_CONFLICTIVE_ITEM = Color.BLACK;
    public static final Color DIA_NO_LECTIVO = new Color(200, 200, 200);
    public static final Color FONDO_CASILLA_HORARIO = new Color(215, 215, 215);
    public static final Color FONDO_CASILLA_DIAS_SEMANA = new Color(190, 190, 190);
    public static final Color FONDO_CASILLA_HORAS = new Color(190, 190, 190);
    public static final Color HORARIO_HORAS_COLOR = new Color(220, 220, 220);
    public static final Font NEGRITA_FONT = new Font("ARIAL", Font.BOLD, 12);
    public static final Font NORMAL_FONT = new Font("ARIAL", Font.PLAIN, 12);

    public static final Color NON_SELECTED_CONFLICTIVE_ITEM = new Color(255, 210, 210);
    public static final Color SELECTED_CONFLICTIVE_ITEM = Color.RED;
    public static final Color[] COLORES_ASIGNATURAS = {
        new Color(255, 165, 96),
        new Color(107, 255, 99),
        new Color(223, 129, 146),
        new Color(204, 164, 153),
        new Color(153, 221, 146),
        new Color(101, 196, 255)
    };
    public static final Color[] COLORES_PROFESORES = {//http://www.colourlovers.com/palette/203331/As_If_To_Die
        new Color(182/2, 174/2, 141/2),
        new Color(127/2, 138/2, 99/2),
        new Color(97, 76, 59),
        new Color(25, 51, 56),
        new Color(32, 8, 8)
    };

    public static final Color BACKGROUND_APP_COLOR = Color.gray;

    //Niveles críticos al evaluar restricciones para una solución
    public static final int LEVEL_RED = 1;
    public static final int LEVEL_YELLOW = 2;
    public static final int LEVEL_GREEN = 3;
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import static data.DataKairos.STATUS_COMPUTING_SOLUTION;
import static data.DataKairos.STATUS_NO_PROJECT;
import static data.DataKairos.STATUS_PROJECT_NO_SOLUTION;
import static data.DataKairos.STATUS_PROJECT_SOLUTION;
import data.restricciones.Restriccion;
import gui.AbstractMainWindow;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import restricciones.ClasesNoCruzanRecreo.RClasesNoCruzanRecreo;
import restricciones.ProfesorMaximoHorasPorDia.RProfesorMaximoHorasPorDia;
import restricciones.ProfesorMinimoHorasPorDia.RProfesorMinimoHorasPorDia;
import restricciones.clasesCondensadasParaProfesor.RClasesCondensadasParaProfesor;
import restricciones.grupoNoUbicuo.RGrupoNoUbicuo;
import restricciones.noHuecosEntreMedias.RNoHuecosEntreMedias;
import restricciones.profesorCiertosDias.RProfesorCiertosDias;
import restricciones.profesorNoUbicuo.RProfesorNoUbicuo;

/**
 * Esta clase envuelve la clase dataproyecto para que sea más fácil el cargado y
 * guardado serial
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class DataKairos {

    /**
     * Estado cuando no hay ningún proyecto cargado. La mayoría de los menús se
     * deshabilitan.
     */
    public static final int STATUS_NO_PROJECT = 0;
    /**
     * Estado con proyecto en memoria pero sin solución calculada
     */
    public static final int STATUS_PROJECT_NO_SOLUTION = 1;
    /**
     * Estado con proyecto en memoria y con solución calculada
     */
    public static final int STATUS_PROJECT_SOLUTION = 2;
    /**
     * Calculando una solución. En este estado se desactivan muchas funciones
     * que pudieran interferir con el cálculo de la solución
     */
    public static final int STATUS_COMPUTING_SOLUTION = 3;
    private DataProject dataProject;
    public MyConstants mc;
    private int status;
    private final ArrayList<Restriccion> restriccionesDisponibles;
    private final KairosController kairosController;
    private AbstractMainWindow mw;

    /**
     * Constructor por defecto
     */
    public DataKairos() {
        dataProject = new DataProject();
        restriccionesDisponibles = new ArrayList<Restriccion>();
        populateRestricciones();
        mc = new MyConstants();
        kairosController = new KairosController(this);
    }

    /**
     *
     * @return Variable de datos de proyecto
     */
    public DataProject getDP() {
        return dataProject;
    }

    /**
     * Cambia el objeto de datos del proyecto
     *
     * @param dataProyecto Nueva variable de datos de proyecto
     */
    public void setDP(DataProject dataProyecto) {
        this.dataProject = dataProyecto;
    }

    /**
     * Borra todos los datos del objeto de datos del proyecto
     */
    public void clear() {
        dataProject.clear();
    }

    /**
     * Crea nuevo proyecto. Además añade algunas restricciones y datos por
     * defecto.
     *
     * @param nombre Nombre del proyecto
     */
    public void createNewDP(String nombre) {
        dataProject = new DataProject();
        populateRestricciones();
        dataProject.getConfigProyecto().setNombreProyecto(nombre);
        ArrayList<Integer> dd = new ArrayList<Integer>();
        dd.add(1);
        dd.add(2);
        dd.add(3);
        dd.add(4);
        dd.add(5);
        dataProject.getAcademicCalendar().setDiasSemanaLectivos(dd);
        dataProject.getAcademicCalendar().setInicio(new GregorianCalendar());
        dataProject.getRestrictionsData().add(new RProfesorNoUbicuo(dataProject));
        dataProject.getRestrictionsData().add(new RGrupoNoUbicuo(dataProject));
        dataProject.getRestrictionsData().add(new RClasesNoCruzanRecreo(dataProject));
    }

    /**
     * Devuelve el estado de la aplicación.
     *
     * @return Estado de la aplicación. Alguna de las variables null null null
     * null null     {@link STATUS_NO_PROJECT}, {@link STATUS_PROJECT_NO_SOLUTION},
     *{@link STATUS_PROJECT_SOLUTION} o {@link STATUS_COMPUTING_SOLUTION}
     */
    public int getStatus() {
        return status;
    }

    /**
     * Cambia el estado de la aplicación. Los cambios inherentes al cambio de
     * estado (desactivar menús, etc.) no los gestiona este método, sino el
     * encargado de llamarlo.
     *
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    public ArrayList<Restriccion> getRestriccionesDisponibles() {
        return restriccionesDisponibles;
    }

    public final void populateRestricciones() {
        restriccionesDisponibles.clear();
        restriccionesDisponibles.add(new RNoHuecosEntreMedias());
        restriccionesDisponibles.add(new RProfesorNoUbicuo());
        restriccionesDisponibles.add(new RGrupoNoUbicuo());
        restriccionesDisponibles.add(new RProfesorCiertosDias());
        restriccionesDisponibles.add(new RClasesCondensadasParaProfesor());
        restriccionesDisponibles.add(new RClasesNoCruzanRecreo());
        restriccionesDisponibles.add(new RProfesorMaximoHorasPorDia());
        restriccionesDisponibles.add(new RProfesorMinimoHorasPorDia());
    }

    public KairosController getController() {
        return kairosController;
    }

    public void setMainWindow(AbstractMainWindow aThis) {
        mw = aThis;
    }

    public AbstractMainWindow getMainWindow() {
        return mw;
    }

}

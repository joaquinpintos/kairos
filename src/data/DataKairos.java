/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.util.ArrayList;
import java.util.Date;
import restricciones.ClasesNoCruzanRecreo.RClasesNoCruzanRecreo;
import restricciones.grupoNoUbicuo.RGrupoNoUbicuo;
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

    /**
     * Constructor por defecto
     */
    public DataKairos() {
        dataProject = new DataProject();
        mc = new MyConstants();
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
        this.dataProject = dataProject;
    }

    /**
     * Borra todos los datos del objeto de datos del proyecto
     */
    public void clear() {
        dataProject.clear();
    }

    /**
     * Crea nuevo proyecto. Además añade algunas restricciones y datos por defecto.
     *
     * @param nombre Nombre del proyecto
     */
    public void createNewDP(String nombre) {
        dataProject = new DataProject();
        dataProject.getConfigProyecto().setNombreProyecto(nombre);
        ArrayList<Integer> dd = new ArrayList<Integer>();
        dd.add(1);
        dd.add(2);
        dd.add(3);
        dd.add(4);
        dd.add(5);
        dataProject.getAcademicCalendar().setDiasSemanaLectivos(dd);
        dataProject.getRestrictionsData().add(new RProfesorNoUbicuo(dataProject));
        dataProject.getRestrictionsData().add(new RGrupoNoUbicuo(dataProject));
        dataProject.getRestrictionsData().add(new RClasesNoCruzanRecreo(dataProject));
    }

    /**
     * Devuelve el estado de la aplicación.
     *
     * @return Estado de la aplicación. Alguna de las variables null null null     {@link STATUS_NO_PROJECT}, {@link STATUS_PROJECT_NO_SOLUTION},
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

}

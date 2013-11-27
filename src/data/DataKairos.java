/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

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
    private DataProyecto dataProyecto;
    public MyConstants mc;
    private int status;

    /**
     * Constructor por defecto
     */
    public DataKairos() {
        dataProyecto = new DataProyecto();
        mc=new MyConstants();
    }

    /**
     *
     * @return Variable de datos de proyecto
     */
    public DataProyecto getDP() {
        return dataProyecto;
    }

    /**
     * Cambia el objeto de datos del proyecto
     *
     * @param dataProyecto Nueva variable de datos de proyecto
     */
    public void setDP(DataProyecto dataProyecto) {
        this.dataProyecto = dataProyecto;
    }

    /**
     * Borra todos los datos del objeto de datos del proyecto
     */
    public void clear() {
        dataProyecto.clear();
    }

    /**
     * Crea nuevo proyecto. Además añade algunas restricciones por defecto.
     *
     * @param nombre Nombre del proyecto
     */
    public void createNewDP(String nombre) {
        dataProyecto = new DataProyecto();
        dataProyecto.getConfigProyecto().setNombreProyecto(nombre);
        dataProyecto.getDataRestricciones().add(new RProfesorNoUbicuo(dataProyecto));
        dataProyecto.getDataRestricciones().add(new RGrupoNoUbicuo(dataProyecto));
        dataProyecto.getDataRestricciones().add(new RClasesNoCruzanRecreo(dataProyecto));
    }

    /**
     * Devuelve el estado de la aplicación.
     *
     * @return Estado de la aplicación. Alguna de las variables null null     {@link STATUS_NO_PROJECT}, {@link STATUS_PROJECT_NO_SOLUTION},
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

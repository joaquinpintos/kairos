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

    //Estados en los que se puede encontrar el programa
    public static final int STATUS_NO_PROJECT = 0;
    public static final int STATUS_PROJECT_NO_SOLUTION = 1;
    public static final int STATUS_PROJECT_SOLUTION = 2;
    public static final int STATUS_COMPUTING_SOLUTION = 3;
    private DataProyecto dataProyecto;
    private int status;

    /**
     *
     */
    public DataKairos() {
        dataProyecto = new DataProyecto();
    }

    /**
     *
     * @return
     */
    public DataProyecto getDP() {
        return dataProyecto;
    }

    /**
     *
     * @param dataProyecto
     */
    public void setDP(DataProyecto dataProyecto) {
        this.dataProyecto = dataProyecto;
    }

    /**
     *
     */
    public void clear() {
        dataProyecto.clear();
    }

    /**
     *
     * @param nombre
     */
    public void createNewDP(String nombre) {
        dataProyecto = new DataProyecto();
        dataProyecto.getConfigProyecto().setNombreProyecto(nombre);
        dataProyecto.getDataRestricciones().add(new RProfesorNoUbicuo(dataProyecto));
        dataProyecto.getDataRestricciones().add(new RGrupoNoUbicuo(dataProyecto));
        dataProyecto.getDataRestricciones().add(new RClasesNoCruzanRecreo(dataProyecto));
    }

    /**
     *
     * @return
     */
    public int getStatus() {
        return status;
    }

    /**
     *
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

}

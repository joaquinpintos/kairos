/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import restricciones.ClasesNoCruzanRecreo.RClasesNoCruzanRecreo;
import restricciones.profesorNoUbicuo.RProfesorNoUbicuo;

/**
 * Esta clase envuelve la clase dataproyecto para que sea más fácil el cargado y
 * guardado serial
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
  public class DataKairos {
    private DataProyecto dataProyecto;

    /**
     *
     */
    public DataKairos() {
        dataProyecto=new DataProyecto();
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
    public void clear(){
        dataProyecto.clear();
    }

    /**
     *
     * @param nombre
     */
    public void createNewDP(String nombre) {
        dataProyecto=new DataProyecto();
        dataProyecto.setNombreProyecto(nombre);
        dataProyecto.getDataRestricciones().add(new RProfesorNoUbicuo(dataProyecto));
        dataProyecto.getDataRestricciones().add(new RClasesNoCruzanRecreo(dataProyecto));
    }
    
    
}

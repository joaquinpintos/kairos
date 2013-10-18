/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.DatosEditor.Aulas;

import data.aulas.CarreraCursoGrupoContainer;
import data.asignaturas.Grupo;
import data.aulas.Aula;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class HashToGroupContainer   implements Serializable {

    private CarreraCursoGrupoContainer container;
    ArrayList<Grupo> grupos;
    String nombre;
    private Aula aula;

//    public HashToGroupContainer(CarreraCursoGrupoContainer container,Aula aula) {
//        this.container = container;
//        grupos = new ArrayList<Grupo>();
//        this.aula=aula;
//    }
    /**
     *
     * @param gr
     * @param aula
     */
    public HashToGroupContainer(Grupo gr, Aula aula) {
        this.container = new CarreraCursoGrupoContainer(gr);
        container.setAulaNombre(aula.getNombre());
        grupos = new ArrayList<Grupo>();
        addGrupo(gr);
        this.aula = aula;
    }

    /**
     *
     * @param gr
     */
    public final void addGrupo(Grupo gr) {
        if (!grupos.contains(gr)) {
            grupos.add(gr);
            gr.setAulaAsignada(aula);
        }
    }

    /**
     *
     * @param gr
     */
    public void removeGrupo(Grupo gr) {
        grupos.remove(gr);
    }

    /**
     *
     * @return
     */
    public int getTotalHoras() {
        int resul = 0;
        for (Grupo gr : grupos) {
            resul += gr.getTotalHoras();
        }
        return resul;
    }

    @Override
    public String toString() {
        return container.getNombre();
    }

    /**
     *
     * @return
     */
    public String getHash() {
        return container.getHash();
    }

    /**
     *
     * @return
     */
    public ArrayList<Grupo> getGrupos() {
        return grupos;
    }

    /**
     *
     * @return
     */
    public CarreraCursoGrupoContainer getContainer() {
        return container;
    }

    /**
     *
     * @param aula
     */
    public void setAula(Aula aula) {
        this.aula = aula;
    }

    /**
     *
     * @return
     */
    public Aula getAula() {
        return aula;
    }

    /**
     *
     * @return
     */
    public double totalHoras() {
        double resul = 0;
        for (Grupo gr : grupos) {
            resul += gr.getTotalHoras();
        }
        return resul;
    }

//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 89 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
//        hash = 89 * hash + (this.aula != null ? this.aula.hashCode() : 0);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final HashToGroupContainer other = (HashToGroupContainer) obj;
//        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
//            return false;
//        }
//        if (this.aula != other.aula && (this.aula == null || !this.aula.equals(other.aula))) {
//            return false;
//        }
//        return true;
//    }

    
}
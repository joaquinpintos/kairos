/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public final class GrupoSinAula {

    private final Grupo grupo;
    private final ArrayList<Tramo> tramosSinAula;

    public GrupoSinAula(Grupo grupo) {
        this.grupo = grupo;
        tramosSinAula = new ArrayList<Tramo>();
        for (Tramo tr : grupo.getTramosGrupoCompleto().getTramos()) {
            add(tr);
        }
    }

    public Tramo get(int index) {
        return tramosSinAula.get(index);
    }

    public String getNombre() {
        return grupo.getNombre();
    }

    public void add(Tramo tr) {
        if (!tr.tieneAula()) {
            if (!tramosSinAula.contains(tr)) {
                tramosSinAula.add(tr);
            }
        }
    }

    public void remove(Tramo tr) {
        tramosSinAula.remove(tr);
    }

    public String getNombreAsignatura() {
        return grupo.getParent().getNombre();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package data.asignaturas;

import data.profesores.Profesor;

/**
 * Cualquier clase que implemente esta interfaz es susceptible de ser enseñada: grupos, asignaturas, cursos, tramos, etc.
 * @author david
 */
public interface Docentable {
    public void setDocente(Profesor profesor);
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.asignaturas;

/**
 * Cualquier clase que implemente esta interfaz es susceptible de ser enseñada:
 * grupos, asignaturas, cursos, tramos, etc.
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public interface Teachable {

    public boolean isAlgunoSinAula();

    public void setAlgunoSinAula(boolean value);
    
    public boolean isAlgunoSinDocente();
    public void setAlgunoSinDocente(boolean value);

}

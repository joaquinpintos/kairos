/*
 * Copyright (C) 2014 David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package data.genetic;

/**
 * Interfaz para pasar a la clase GeneticAlgorigthm que se encarga de mostrar los
 * datos. También se encarga de procesar la condición de interrupción.
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public interface GeneticInformer {
    /**
     *
     * @param g
     */
    public void setInformation(GeneticAlgorithm g);
    /**
     *
     * @return
     */
    public boolean interrumpido();
    /**
     *
     * @param g
     */
    public void finalizado(GeneticAlgorithm g);
}

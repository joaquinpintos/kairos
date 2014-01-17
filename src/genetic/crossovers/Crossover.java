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
package genetic.crossovers;

import data.genetic.PosibleSolucion;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com> Gutierrez
 */
public interface Crossover {

    /**
     *
     * @param p1
     * @param p2
     * @return
     */
    public PosibleSolucion cruce(PosibleSolucion p1, PosibleSolucion p2);
            
}

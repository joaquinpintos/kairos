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
package data;

/**
 * Interfaz para todo objeto que escuche eventos relativos a cambios en los
 * datos del proyecto
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public interface DataProjectListener {

    public final int ADD = 0;
    public final int MODIFY = 1;
    public final int REMOVE = 2;

    /**
     * Evento disparado cuando halla cambios en los datos del proyecto
     *
     * @param obj Objeto modificado
     * @param type Tipo de modificación
     */
    public void dataEvent(Object obj, int type);
}

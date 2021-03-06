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

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class AbstractDataSets implements Serializable {

    private static final long serialVersionUID = 27112013L;
    private DataProject dataProject;
    private final ArrayList<DataProjectListener> listeners;

    /**
     *
     * @param dataProject
     */
    public AbstractDataSets(DataProject dataProject) {
        this.dataProject = dataProject;
        listeners = new ArrayList<DataProjectListener>();

    }

    /**
     *
     */
    public void clearListeners() {
        listeners.clear();
    }

    /**
     *
     * @return
     */
    public DataProject getDataProyecto() {
        return dataProject;
    }

    /**
     *
     * @param dataProject
     */
    public void setDataProyecto(DataProject dataProject) {
        this.dataProject = dataProject;
    }
}

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
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public abstract class KairosCommand {

    public static final int STD_CMD = 0;
    public static final int BEGIN_BLOCK = 1;
    public static final int END_BLOCK = 2;
    int eventType;
    private boolean undoable = true;
    private final int id;

    public KairosCommand(int cmdType) {
        id = cmdType;
    }

    public int getId() {
        return id;
    }

    abstract public void execute();

    abstract public void undo();

    public void redo() {
        this.execute();
    }

    abstract public String getDescription();

    abstract public Object getDataType();

    abstract int getEventType();

    public boolean isUndoable() {
        return undoable;
    }

    public void setUndoable(boolean value) {
        undoable = value;
    }

}

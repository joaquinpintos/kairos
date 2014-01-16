/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

/**
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public abstract class KairosCommand {

    public static final int STD_CMD  = 0;
    public static final int BEGIN_BLOCK  = 1;
    public static final int END_BLOCK  = 2;
    int eventType;
    private boolean undoable = true;
    private final int id;

    public KairosCommand(int cmdType) {
        id=cmdType;
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

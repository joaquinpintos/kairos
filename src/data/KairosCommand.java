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
    int eventType;
    private boolean undoable=true;
    abstract public void execute();
    abstract public void undo();
    public void redo() 
    {
        this.execute();
    }
    abstract public String getDescription();
    abstract public Object getDataType();
    abstract int getEventType();
    public boolean isUndoable()
    {
        return undoable;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import data.profesores.Departamento;
import data.profesores.Profesor;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * Esta clase realiza las modificaciones complejas pertinentes en los datos
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class KairosController {

    private final DataKairos dk;
    private final Stack<KairosCommand> commandStack;
    private final Stack<KairosCommand> commandUndoStack;
    private final ArrayList<DataProyectoListener> listeners;

    public KairosController(DataKairos dk) {
        this.dk = dk;
        commandStack = new Stack<KairosCommand>();
        commandUndoStack = new Stack<KairosCommand>();
        listeners = new ArrayList<DataProyectoListener>();
    }

    public KairosCommand getEditProfesorCommand(Profesor data, Profesor newData) {
        class EditProfesorActions extends KairosCommand {

            private final Profesor oldData;//Datos para deshacer
            private final Profesor newData;
            private final Profesor data;

            public EditProfesorActions(Profesor data, Profesor newData) {
                this.data = data;
                this.newData = newData;
                this.oldData = new Profesor("", "", "");
            }

            @Override
            public void execute() {
                oldData.copyBasicValuesFrom(data);//Guardo valores antiguos
                oldData.setDepartamento(data.getDepartamento());
                data.copyBasicValuesFrom(newData);//Copio nuevos valores
                final Departamento dep = newData.getDepartamento();
                final Departamento depOld = data.getDepartamento();
                data.setDepartamento(dep);
                depOld.removeProfesor(data);//Remove profesor from old department
                dep.addProfesor(data);//Add profesor to new department
            }

            @Override
            public void undo() {
                data.copyBasicValuesFrom(oldData);//Copio nuevos valores
                final Departamento dep = oldData.getDepartamento();
                final Departamento depOld = data.getDepartamento();
                data.setDepartamento(dep);
                depOld.removeProfesor(data);//Remove profesor from old department
                dep.addProfesor(data);//Add profesor to new department
            }

            @Override
            public void redo() {
                System.out.println("Rehaciendo!");
            }

            @Override
            public String getDescription() {
                return "Edit profesor";
            }

            @Override
            public String toString() {
                return getDescription();
            }

            @Override
            public Object getDataType() {
                return data;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.MODIFY;
            }
        }
        return new EditProfesorActions(data, newData);
    }

    public void executeCommand(KairosCommand cmd) {
        cmd.execute();
        commandStack.push(cmd);
        fireDataEvent(cmd);
        System.err.println(commandStack);
        commandUndoStack.clear();
    }

    public void undoCommand() {
        try {
            KairosCommand cmd = commandStack.pop();
            cmd.undo();
            commandUndoStack.push(cmd);
            fireDataEvent(cmd);
        } catch (EmptyStackException e) {
            System.err.println("Stack empty!");
        }
    }

    public void redoCommand() {
        try {
            KairosCommand cmd = commandUndoStack.pop();
            cmd.redo();
            commandStack.push(cmd);
            fireDataEvent(cmd);
        } catch (EmptyStackException e) {
            System.err.println("Stack empty!");
        }

    }

    public boolean isCommandStackEmpty() {
        return commandStack.empty();
    }

    public boolean isCommandUndoStackEmpty() {
        return commandUndoStack.empty();
    }

    public void fireDataEvent(KairosCommand cmd) {
        System.out.println(listeners);
        for (DataProyectoListener l : listeners) {
            l.dataEvent(cmd.getDataType(), cmd.getEventType());
        }
    }

    public void addListener(DataProyectoListener l) {
        if (l != null) {
            listeners.add(l);
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.Grupo;
import data.asignaturas.GrupoTramos;
import data.asignaturas.Teachable;
import data.asignaturas.Tramo;
import data.profesores.Departamento;
import data.profesores.Profesor;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Stack;

/**
 * Esta clase realiza las modificaciones complejas pertinentes en los datos
 *
 * @author David Gutiérrez Rubio <davidgutierrezrubio@gmail.com>
 */
public class KairosController {

    private final DataKairos dk;
    private final Stack<KairosCommand> commandStackForUndo;
    private final Stack<KairosCommand> commandStackForRedo;
    private final HashSet<DataProyectoListener> listeners;

    public KairosController(DataKairos dk) {
        this.dk = dk;
        commandStackForUndo = new Stack<KairosCommand>();
        commandStackForRedo = new Stack<KairosCommand>();
        listeners = new HashSet<DataProyectoListener>();
    }

    /**
     * Ejecuta el comando especificado y lo añade a la pila de deshacer
     *
     * @param cmd Comando a ejecutar
     */
    public void executeCommand(KairosCommand cmd) {
        cmd.execute();
        if (cmd.isUndoable()) {
            commandStackForUndo.push(cmd);
        }
        fireDataEvent(cmd);
//        System.err.println(commandStack);
        commandStackForRedo.clear();//Borro pila de redo
    }

    /**
     * Deshace el último comando de la pila de deshacer
     */
    public void undoCommand() {
        try {
            KairosCommand cmd = commandStackForUndo.pop();
            cmd.undo();
            commandStackForRedo.push(cmd);
            fireDataEvent(cmd);
        } catch (EmptyStackException e) {
            System.err.println("commandStack empty!");
        }
    }

    /**
     * Rehace el último comando de la pila de rehacer
     */
    public void redoCommand() {
        try {
            KairosCommand cmd = commandStackForRedo.pop();
            cmd.redo();
            commandStackForUndo.push(cmd);
            fireDataEvent(cmd);
        } catch (EmptyStackException e) {
            System.err.println("commandUndoStack empty!");
        }

    }

    public boolean isCommandStackForUndoEmpty() {
        return commandStackForUndo.empty();
    }

    public boolean isCommandStackForRedoEmpty() {
        return commandStackForRedo.empty();
    }

    public void fireDataEvent(KairosCommand cmd) {
//        System.out.println(listeners);
        for (DataProyectoListener l : listeners) {
            l.dataEvent(cmd.getDataType(), cmd.getEventType());
        }
    }

    public void addListener(DataProyectoListener l) {
        if (l != null) {
            listeners.add(l);
        }
    }

    private void updateStatusAsignacionDeAula(Teachable obj) {
        Teachable data = obj;
        if (data instanceof Tramo) {
            Tramo tr = (Tramo) data;
            data = tr.getParent();
        }

        if (data instanceof GrupoTramos) {
            GrupoTramos gt = (GrupoTramos) data;
            boolean tieneAula = true;
            for (Tramo tr : gt.getTramos()) {
                tieneAula &= tr.tieneAula();
            }
            gt.setAlgunoSinAula(!tieneAula);
            data = gt.getParent();
        }
        if (data instanceof Grupo) {
            Grupo gr = (Grupo) data;
            gr.setAlgunoSinAula(gr.getTramosGrupoCompleto().algunoSinAula());
            data = gr.getParent();
        }
        if (data instanceof Asignatura) {
            Asignatura asig = (Asignatura) data;
            boolean tieneAula = true;
            for (Grupo gr : asig.getGrupos().getGrupos()) {
                tieneAula &= gr.tieneAula();
            }
            asig.setTieneAula(tieneAula);
            data = asig.getParent();
        }
        if (data instanceof Curso) {
            Curso cur = (Curso) data;
            boolean tieneAula = true;
            for (Asignatura asig : cur.getAsignaturas()) {
                tieneAula &= asig.tieneAula();
            }
            cur.setTieneAula(tieneAula);
            data = cur.getParent();
        }
        if (data instanceof Carrera) {
            Carrera car = (Carrera) data;
            boolean tieneAula = true;
            for (Curso cur : car.getCursos()) {
                tieneAula &= cur.tieneAula();
            }
            car.setTieneAula(tieneAula);
        }

    }

    //**************************************************************************
    //COMANDOS
    //**************************************************************************
    /**
     * Devuelve un objeto KairosCommand que copia los valores del profesor
     * newData en los del profesor data. Copia los datos básicos (nombre,
     * apellidos) y el departamento. NO copia la docencia asignada.
     *
     * @param data
     * @param newData
     * @return KairosCommand que ejecuta el cambio especificado
     */
    //<editor-fold defaultstate="collapsed" desc="getEditProfesorCommand">
    public KairosCommand getEditProfesorCommand(Profesor data, Profesor newData) {
        class EditProfesorCommand extends KairosCommand {

            private final Profesor oldData;//Datos para deshacer
            private final Profesor newData;
            private final Profesor data;

            public EditProfesorCommand(Profesor data, Profesor newData) {
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
                dep.ordenaProfesores();

            }

            @Override
            public void undo() {
                data.copyBasicValuesFrom(oldData);//Copio nuevos valores
                final Departamento dep = oldData.getDepartamento();
                final Departamento depOld = data.getDepartamento();
                data.setDepartamento(dep);
                depOld.removeProfesor(data);//Remove profesor from old department
                dep.addProfesor(data);//Add profesor to new department
                dep.ordenaProfesores();
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
        return new EditProfesorCommand(data, newData);
    }
//</editor-fold>

    /**
     * Devuelve un objeto KairosCommand que copia los valores de la asignatura
     * newData en los de la asignatura data. No modifica subelementos
     * (grupos,tramos)
     *
     * @param data
     * @param newData
     * @return KairosCommand que ejecuta el cambio especificado
     */
    //<editor-fold defaultstate="collapsed" desc="getEditAsignaturaCommand">
    public KairosCommand getEditAsignaturaCommand(Asignatura data, Asignatura newData) {
        class EditAsignaturaCommand extends KairosCommand {

            private final Asignatura oldData;//Datos para deshacer
            private final Asignatura newData;
            private final Asignatura data;

            public EditAsignaturaCommand(Asignatura data, Asignatura newData) {
                this.data = data;
                this.newData = newData;
                this.oldData = new Asignatura("");
            }

            @Override
            public void execute() {
                oldData.copyBasicValuesFrom(data);
                oldData.setCurso(data.getParent());

                data.copyBasicValuesFrom(newData);
                Curso oldCurso = data.getParent();
                Curso newCurso = newData.getParent();
                data.setCurso(newCurso);

                oldCurso.removeAsignatura(data);
                newCurso.addAsignatura(data);
                newCurso.ordenaAsignaturas();

            }

            @Override
            public void undo() {
                data.copyBasicValuesFrom(oldData);

                Curso oldCurso = data.getParent();
                Curso newCurso = oldData.getParent();
                data.setCurso(newCurso);

                oldCurso.removeAsignatura(data);
                newCurso.addAsignatura(data);
                newCurso.ordenaAsignaturas();

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
        return new EditAsignaturaCommand(data, newData);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getCreateAsignaturaCommand">
    public KairosCommand getCreateAsignaturaCommand(Curso cur,Asignatura asig) {
        class CreateAsignaturaCommand extends KairosCommand {

            private final Asignatura asig;
            private final Curso cur;

            public CreateAsignaturaCommand(Curso cur,Asignatura asig) {
                this.asig = asig;
                this.cur=cur;
            }

            @Override
            public void execute() {
                cur.addAsignatura(asig);
                asig.setCurso(cur);
                cur.ordenaAsignaturas();
                updateStatusAsignacionDeAula(asig);

            }

            @Override
            public void undo() {
                cur.removeAsignatura(asig);
                asig.setCurso(null);
                updateStatusAsignacionDeAula(asig);
            }

            @Override
            public String getDescription() {
                return "Añadir asignatura";
            }

            @Override
            public String toString() {
                return getDescription();
            }

            @Override
            public Object getDataType() {
                return asig;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.ADD;
            }
        }
        return new CreateAsignaturaCommand(cur,asig);
    }
//</editor-fold>

    public KairosCommand getCreateTramoCommand(Grupo gr, Tramo tr) {
        class CreateTramoCommand extends KairosCommand {

            private final Grupo gr;
            private final Tramo tr;

            public CreateTramoCommand(Grupo gr, Tramo tr) {
                this.tr = tr;
                this.gr = gr;
            }

            @Override
            public void execute() {
                gr.getTramosGrupoCompleto().getTramos().add(tr);
                tr.setParent(gr.getTramosGrupoCompleto());
                updateStatusAsignacionDeAula(tr);

            }

            @Override
            public void undo() {
                gr.getTramosGrupoCompleto().getTramos().remove(tr);
                updateStatusAsignacionDeAula(tr);
            }

            @Override
            public String getDescription() {
                return "Añadir tramo";
            }

            @Override
            public String toString() {
                return getDescription();
            }

            @Override
            public Object getDataType() {
                return tr;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.ADD;
            }
        }
        return new CreateTramoCommand(gr, tr);
    }

    public KairosCommand getCreateGrupoCommand(Asignatura asig, Grupo gr) {
        class CreateGrupoCommand extends KairosCommand {

            private final Grupo gr;
            private final Asignatura asig;

            public CreateGrupoCommand(Asignatura asig, Grupo gr) {
                this.gr = gr;
                this.asig = asig;
            }

            @Override
            public void execute() {
                asig.addGrupo(gr);
                gr.setParent(asig);

                updateStatusAsignacionDeAula(gr);

            }

            @Override
            public void undo() {
                asig.removeGrupo(gr);
                gr.setParent(null);
                updateStatusAsignacionDeAula(gr);
            }

            @Override
            public String getDescription() {
                return "Añadir grupo";
            }

            @Override
            public String toString() {
                return getDescription();
            }

            @Override
            public Object getDataType() {
                return gr;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.ADD;
            }
        }
        return new CreateGrupoCommand(asig, gr);
    }

}
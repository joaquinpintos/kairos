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

import data.asignaturas.Asignatura;
import data.asignaturas.Carrera;
import data.asignaturas.Curso;
import data.asignaturas.DataAsignaturas;
import data.asignaturas.Grupo;
import data.asignaturas.GrupoTramos;
import data.asignaturas.Teachable;
import data.asignaturas.Tramo;
import data.aulas.Aula;
import data.aulas.AulaMT;
import data.aulas.DataAulas;
import data.profesores.DataProfesores;
import data.profesores.Departamento;
import data.profesores.Profesor;
import gui.HorarioEditor.DraggableHorarioItemComponent;
import gui.HorarioEditor.HorariosJPanelModel;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.HashMap;
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
    private boolean batchMode;
    
    //Este número representa el tamaño que tiene que tener la pila commandStackForUndo
    //para que el sistema se considere "limpio" (todos los cambios guardados)
    //Si vale -1 el sistema siempre necesitará guardar los cambios.
    //Si vale 0 el sistema está limpio si no hay cambios realizados (el usuario acaba de cargar o guardar)
    //Si vale >0 el sistema está limpio si no deshace ni rehace (el usuario acaba de guardar cuando había un historial de cambios)
    private int cleanNumber;

    public KairosController(DataKairos dk) {
        this.dk = dk;
        commandStackForUndo = new Stack<KairosCommand>();
        commandStackForRedo = new Stack<KairosCommand>();
        listeners = new HashSet<DataProyectoListener>();
        batchMode = false;
        cleanNumber=-1;
    }

    public void setBatchMode(boolean batchMode) {
        this.batchMode = batchMode;
    }

    public void setNotDirty()
    {
        cleanNumber=commandStackForUndo.size();
    }
    
    public void clearCmdStack() {
        commandStackForRedo.clear();
        commandStackForRedo.clear();
        cleanNumber=0;
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
        fireDataEvent(cmd.getDataType(), cmd.getEventType());
//        System.err.println(commandStack);
        commandStackForRedo.clear();//Borro pila de redo
        
        //Pongo el sistema dirty o clean
        //Es dirty si la pila de undo no se encuentra en el estado clean
        dk.setDirty(cleanNumber!=commandStackForUndo.size());
    }

    /**
     * Deshace el último comando de la pila de deshacer
     */
    public void undoCommand() {
        try {
            do {
                KairosCommand cmd = commandStackForUndo.pop();
                cmd.undo();
                commandStackForRedo.push(cmd);
                int eventType;
                switch (cmd.getEventType()) {
                    case DataProyectoListener.ADD:
                        eventType = DataProyectoListener.REMOVE;
                        break;
                    case DataProyectoListener.REMOVE:
                        eventType = DataProyectoListener.ADD;
                        break;
                    default:
                        eventType = cmd.getEventType();
                }
                fireDataEvent(cmd.getDataType(), eventType);
            } while (batchMode);
        } catch (EmptyStackException e) {
            System.err.println("commandStack empty!");
        }
        //Pongo el sistema dirty o clean
        //Es dirty si la pila de undo no se encuentra en el estado clean
        dk.setDirty(cleanNumber!=commandStackForUndo.size());
    }

    /**
     * Rehace el último comando de la pila de rehacer
     */
    public void redoCommand() {
        try {
            do {
                KairosCommand cmd = commandStackForRedo.pop();
                cmd.redo();
                commandStackForUndo.push(cmd);
                fireDataEvent(cmd.getDataType(), cmd.getEventType());
            } while (batchMode);
        } catch (EmptyStackException e) {
            System.err.println("commandUndoStack empty!");
        }
        //Pongo el sistema dirty o clean
        //Es dirty si la pila de undo no se encuentra en el estado clean
        dk.setDirty(cleanNumber!=commandStackForUndo.size());

    }

    public boolean isCommandStackForUndoEmpty() {
        return commandStackForUndo.empty();
    }

    public boolean isCommandStackForRedoEmpty() {
        return commandStackForRedo.empty();
    }

    public void fireDataEvent(Object dataType, int eventType) {
//        System.out.println(listeners);
        for (DataProyectoListener l : listeners) {
            l.dataEvent(dataType, eventType);
        }

        //Llamo manualmente al evento de listaGrupoCursos para actualizar los datos
        dk.getDP().getDataAsignaturas().getListaGrupoCursos().dataEvent(dataType, eventType);
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
            gr.setAlgunoSinAula(gr.getTramosGrupoCompleto().isAlgunoSinAula());
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

    private ArrayList<Tramo> getTramosFromTeachable(Teachable teachable) {
        ArrayList<Tramo> resul = new ArrayList<Tramo>();
        if (teachable instanceof Tramo) {
            resul.add((Tramo) teachable);
        }
        if (teachable instanceof Grupo) {
            Grupo gr = (Grupo) teachable;
            for (Tramo tr : gr.getTramosGrupoCompleto().getTramos()) {
                resul.add(tr);
            }
        }
        if (teachable instanceof Asignatura) {
            Asignatura asig = (Asignatura) teachable;
            for (Grupo gr : asig.getGrupos().getGrupos()) {
                resul.addAll(getTramosFromTeachable(gr));
            }
        }
        if (teachable instanceof Curso) {
            Curso cur = (Curso) teachable;
            for (Asignatura asig : cur.getAsignaturas()) {
                resul.addAll(getTramosFromTeachable(asig));
            }
        }
        if (teachable instanceof Carrera) {
            Carrera car = (Carrera) teachable;
            for (Curso cur : car.getCursos()) {
                resul.addAll(getTramosFromTeachable(cur));
            }
        }

        return resul;
    }

    public ArrayList<Tramo> getTodosLosTramos() {
        //Hago una lista con todos los tramos existentes
        ArrayList<Tramo> todosTramos = new ArrayList<Tramo>();
        for (Carrera car : dk.getDP().getDataAsignaturas().getCarreras()) {
            todosTramos.addAll(getTramosFromTeachable(car));
        }
        return todosTramos;
    }
    //**************************************************************************
    //COMANDOS DE EDICION
    //**************************************************************************
    //<editor-fold defaultstate="collapsed" desc="getEditProfesorCommand">
    /**
     * Devuelve un objeto KairosCommand que copia los valores del profesor
     * newData en los del profesor data. Copia los datos básicos (nombre,
     * apellidos) y el departamento. NO copia la docencia asignada.
     *
     * @param data
     * @param newData
     * @return KairosCommand que ejecuta el cambio especificado
     */
    public KairosCommand getEditProfesorCommand(Profesor data, Profesor newData) {
        class EditProfesorCommand extends KairosCommand {

            private final Profesor oldData;//Datos para deshacer
            private final Profesor newData;
            private final Profesor data;

            public EditProfesorCommand(Profesor data, Profesor newData) {
                super(KairosCommand.STD_CMD);
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

    //<editor-fold defaultstate="collapsed" desc="getEditAulaCommand">
    public KairosCommand getEditAulaCommand(Aula data, Aula newData) {
        class EditAulaCommand extends KairosCommand {

            private final Aula oldData;//Datos para deshacer
            private final Aula newData;
            private final Aula data;

            public EditAulaCommand(Aula data, Aula newData) {
                super(KairosCommand.STD_CMD);
                this.data = data;
                this.newData = newData;
                this.oldData = new Aula("");
            }

            @Override
            public void execute() {
                oldData.copyBasicValuesFrom(data);//Guardo valores antiguos
                data.copyBasicValuesFrom(newData);
            }

            @Override
            public void undo() {
                data.copyBasicValuesFrom(oldData);
            }

            @Override
            public String getDescription() {
                return "Edit aula";
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
        return new EditAulaCommand(data, newData);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getEditCarreraCommand">
    public KairosCommand getEditCarreraCommand(Carrera data, Carrera newData) {
        class EditCarreraCommand extends KairosCommand {

            private final Carrera oldData;//Datos para deshacer
            private final Carrera newData;
            private final Carrera data;

            public EditCarreraCommand(Carrera data, Carrera newData) {
                super(KairosCommand.STD_CMD);
                this.data = data;
                this.newData = newData;
                this.oldData = new Carrera("");
            }

            @Override
            public void execute() {
                oldData.copyBasicValuesFrom(data); //Copio datos para poder deshacer
                data.copyBasicValuesFrom(newData);//Copio datos básicos

            }

            @Override
            public void undo() {
                data.copyBasicValuesFrom(oldData);
            }

            @Override
            public String getDescription() {
                return "Edit carrera";
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
        return new EditCarreraCommand(data, newData);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getEditCursoCommand">
    public KairosCommand getEditCursoCommand(Curso data, Curso newData) {
        class EditCursoCommand extends KairosCommand {

            private final Curso oldData;//Datos para deshacer
            private final Curso newData;
            private final Curso data;
            private Carrera oldCar;

            public EditCursoCommand(Curso data, Curso newData) {
                super(KairosCommand.STD_CMD);
                this.data = data;
                this.newData = newData;
                this.oldData = new Curso("");
            }

            @Override
            public void execute() {
                oldData.copyBasicValuesFrom(data); //Copio datos para poder deshacer

                oldCar = data.getParent();
                Carrera newCar = newData.getParent();

                data.copyBasicValuesFrom(newData);//Copio datos básicos

                oldCar.removeCurso(data); //Cambio la carrera a la que pertenece
                newCar.addCurso(data);
                data.setParent(newCar);

                updateStatusAsignacionDeAula(newCar);
                updateStatusAsignacionDeAula(oldCar);

            }

            @Override
            public void undo() {
                data.copyBasicValuesFrom(oldData);
                final Carrera newCar = data.getParent();
                newCar.removeCurso(data);//Borro el curso de la carrera actual...
                oldCar.addCurso(data);//...y la vuelvo a colocar donde estaba.
                data.setParent(oldCar);
                updateStatusAsignacionDeAula(newCar);
                updateStatusAsignacionDeAula(oldCar);

            }

            @Override
            public String getDescription() {
                return "Edit curso";
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
        return new EditCursoCommand(data, newData);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getEditAsignaturaCommand">
    /**
     * Devuelve un objeto KairosCommand que copia los valores de la asignatura
     * newData en los de la asignatura data. No modifica subelementos
     * (grupos,tramos)
     *
     * @param data
     * @param newData
     * @return KairosCommand que ejecuta el cambio especificado
     */
    public KairosCommand getEditAsignaturaCommand(Asignatura data, Asignatura newData) {
        class EditAsignaturaCommand extends KairosCommand {

            private final Asignatura oldData;//Datos para deshacer
            private final Asignatura newData;
            private final Asignatura data;

            public EditAsignaturaCommand(Asignatura data, Asignatura newData) {
                super(KairosCommand.STD_CMD);
                this.data = data;
                this.newData = newData;
                this.oldData = new Asignatura("");
            }

            @Override
            public void execute() {
                oldData.copyBasicValuesFrom(data);
                oldData.setParent(data.getParent());

                data.copyBasicValuesFrom(newData);
                Curso oldCurso = data.getParent();
                Curso newCurso = newData.getParent();
                data.setParent(newCurso);

                oldCurso.removeAsignatura(data);
                newCurso.addAsignatura(data);
                newCurso.ordenaAsignaturas();

            }

            @Override
            public void undo() {
                data.copyBasicValuesFrom(oldData);

                Curso oldCurso = data.getParent();
                Curso newCurso = oldData.getParent();
                data.setParent(newCurso);

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

    //<editor-fold defaultstate="collapsed" desc="getEditGrupoCommand">
    public KairosCommand getEditGrupoCommand(Grupo data, Grupo grNew) {
        class EditGrupoCommand extends KairosCommand {

            private final Grupo data;
            private final Grupo grNew;
            private final Grupo grOld;

            public EditGrupoCommand(Grupo data, Grupo grNew) {
                super(KairosCommand.STD_CMD);
                this.data = data;
                this.grNew = grNew;
                grOld = new Grupo("");
            }

            @Override
            public void execute() {
                grOld.copyBasicValuesFrom(data);
                data.copyBasicValuesFrom(grNew);

            }

            @Override
            public void undo() {
                data.copyBasicValuesFrom(grOld);
            }

            @Override
            public String getDescription() {
                return "Editar grupo";
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
        return new EditGrupoCommand(data, grNew);
    }
//</editor-fold>

    public KairosCommand getMoveHorarioItem(HorariosJPanelModel model, DraggableHorarioItemComponent dh, int rSrc, int cSrc, int rDst, int cDst) {

        class MoveHorarioItem extends KairosCommand {

            private final HorariosJPanelModel model;
            private final DraggableHorarioItemComponent dh;
            private final int rSrc;
            private final int cSrc;
            private final int rDst;
            private final int cDst;

            public MoveHorarioItem(HorariosJPanelModel model, DraggableHorarioItemComponent dh, int rSrc, int cSrc, int rDst, int cDst) {
                super(KairosCommand.STD_CMD);
                this.dh = dh;
                this.model = model;
                this.rSrc = rSrc;
                this.cSrc = cSrc;
                this.rDst = rDst;
                this.cDst = cDst;

            }

            @Override
            public void execute() {
                model.effectivelyDropItem(dh, rDst, cDst);
            }

            @Override
            public void undo() {
                model.effectivelyDropItem(dh, rSrc, cSrc);
            }

            @Override
            public String getDescription() {
                return "Mover item de horario";
            }

            @Override
            public Object getDataType() {
                return dh;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.MODIFY;
            }

        }

        return new MoveHorarioItem(model, dh, rSrc, cSrc, rDst, cDst);

    }
    //**************************************************************************
    //COMANDOS DE CREACION
    //**************************************************************************

    //<editor-fold defaultstate="collapsed" desc="getCreateDepartamentoCommand">
    public KairosCommand getCreateDepartamentoCommand(Departamento newDep) {
        class CreateDepartamentoCommand extends KairosCommand {

            private final Departamento newDep;

            public CreateDepartamentoCommand(Departamento dep) {
                super(KairosCommand.STD_CMD);
                this.newDep = dep;
            }

            @Override
            public void execute() {
                final DataProfesores dp = dk.getDP().getDataProfesores();
                dp.addDepartamento(newDep);
                newDep.setParent(dp);

            }

            @Override
            public void undo() {
                final DataProfesores dp = dk.getDP().getDataProfesores();
                dp.removeDepartamento(newDep);
                newDep.setParent(null);

            }

            @Override
            public String getDescription() {
                return "Añadir departamento";
            }

            @Override
            public String toString() {
                return getDescription();
            }

            @Override
            public Object getDataType() {
                return newDep;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.ADD;
            }
        }
        return new CreateDepartamentoCommand(newDep);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getCreateProfesorCommand">
    public KairosCommand getCreateProfesorCommand(Departamento dep, Profesor newProfesor) {
        class CreateProfesorCommand extends KairosCommand {

            private final Profesor newProfesor;
            private final Departamento dep;

            public CreateProfesorCommand(Departamento dep, Profesor newProfesor) {
                super(KairosCommand.STD_CMD);
                this.newProfesor = newProfesor;
                this.dep = dep;
                dep.ordenaProfesores();
            }

            @Override
            public void execute() {
                dep.addProfesor(newProfesor);
                newProfesor.setDepartamento(dep);

            }

            @Override
            public void undo() {
                dep.removeProfesor(newProfesor);
                newProfesor.setDepartamento(null);
            }

            @Override
            public String getDescription() {
                return "Añadir profesor";
            }

            @Override
            public String toString() {
                return getDescription();
            }

            @Override
            public Object getDataType() {
                return newProfesor;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.ADD;
            }
        }
        return new CreateProfesorCommand(dep, newProfesor);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getCreateAulaCommand">
    public KairosCommand getCreateAulaCommand(Aula newAula) {
        class CreateAulaCommand extends KairosCommand {

            private final Aula newAula;

            public CreateAulaCommand(Aula newAula) {
                super(KairosCommand.STD_CMD);
                this.newAula = newAula;
            }

            @Override
            public void execute() {
                final DataAulas da = dk.getDP().getDataAulas();
                da.addAula(newAula);
                dk.getDP().getAsignacionAulas().addAula(newAula);//Añado aula a las asignaciones ¿es necesaria esta clase??
                newAula.setParent(da);

            }

            @Override
            public void undo() {
                final DataAulas da = dk.getDP().getDataAulas();
                da.removeAula(newAula);
                dk.getDP().getAsignacionAulas().removeAula(newAula);//Añado aula a las asignaciones ¿es necesaria esta clase??
                newAula.setParent(null);
            }

            @Override
            public String getDescription() {
                return "Añadir aula";
            }

            @Override
            public String toString() {
                return getDescription();
            }

            @Override
            public Object getDataType() {
                return newAula;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.ADD;
            }
        }
        return new CreateAulaCommand(newAula);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getCreateCarreraCommand">
    public KairosCommand getCreateCarreraCommand(Carrera car) {
        class CreateCarreraCommand extends KairosCommand {

            private final Carrera car;

            public CreateCarreraCommand(Carrera car) {
                super(KairosCommand.STD_CMD);
                this.car = car;
            }

            @Override
            public void execute() {
                final DataAsignaturas da = dk.getDP().getDataAsignaturas();
                da.addCarrera(car);
                da.ordenaCarreras();
                car.setParent(da);
                updateStatusAsignacionDeAula(car);

            }

            @Override
            public void undo() {
                dk.getDP().getDataAsignaturas().removeCarrera(car);
                car.setParent(null);
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
                return car;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.ADD;
            }
        }
        return new CreateCarreraCommand(car);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getCreateCursoCommand">
    public KairosCommand getCreateCursoCommand(Carrera car, Curso cur) {
        class CreateCursoCommand extends KairosCommand {

            private final Curso cur;
            private final Carrera car;

            public CreateCursoCommand(Carrera car, Curso cur) {
                super(KairosCommand.STD_CMD);
                this.cur = cur;
                this.car = car;
            }

            @Override
            public void execute() {
                car.addCurso(cur);
                car.ordenaCursos();
                cur.setParent(car);
                updateStatusAsignacionDeAula(car);

            }

            @Override
            public void undo() {
                car.removeCurso(cur);
                cur.setParent(null);
                updateStatusAsignacionDeAula(car);
            }

            @Override
            public String getDescription() {
                return "Añadir curso";
            }

            @Override
            public String toString() {
                return getDescription();
            }

            @Override
            public Object getDataType() {
                return car;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.ADD;
            }

            private void updateListaGrupoCursos(Teachable t, int eventType) {
                dk.getDP().getDataAsignaturas().getListaGrupoCursos().dataEvent(t, eventType);
            }
        }
        return new CreateCursoCommand(car, cur);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getCreateAsignaturaCommand">
    public KairosCommand getCreateAsignaturaCommand(Curso cur, Asignatura asig) {
        class CreateAsignaturaCommand extends KairosCommand {

            private final Asignatura asig;
            private final Curso cur;

            public CreateAsignaturaCommand(Curso cur, Asignatura asig) {
                super(KairosCommand.STD_CMD);
                this.asig = asig;
                this.cur = cur;
            }

            @Override
            public void execute() {
                cur.addAsignatura(asig);
                asig.setParent(cur);
                cur.ordenaAsignaturas();
                updateStatusAsignacionDeAula(asig);

            }

            @Override
            public void undo() {
                cur.removeAsignatura(asig);
                asig.setParent(null);
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
        return new CreateAsignaturaCommand(cur, asig);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getCreateGrupoCommand">
    public KairosCommand getCreateGrupoCommand(Asignatura asig, Grupo gr) {
        class CreateGrupoCommand extends KairosCommand {

            private final Grupo gr;
            private final Asignatura asig;

            public CreateGrupoCommand(Asignatura asig, Grupo gr) {
                super(KairosCommand.STD_CMD);
                this.gr = gr;
                this.asig = asig;
            }

            @Override
            public void execute() {
                asig.addGrupo(gr);
                gr.setParent(asig);
                asig.ordenaGrupos();

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
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getCreateTramoCommand">
    public KairosCommand getCreateTramoCommand(Grupo gr, Tramo tr) {
        class CreateTramoCommand extends KairosCommand {

            private final Grupo gr;
            private final Tramo tr;

            public CreateTramoCommand(Grupo gr, Tramo tr) {
                super(KairosCommand.STD_CMD);
                this.tr = tr;
                this.gr = gr;
            }

            @Override
            public void execute() {
                gr.getTramosGrupoCompleto().getTramos().add(tr);
                tr.setParent(gr.getTramosGrupoCompleto());
                updateStatusAsignacionDeAula(gr);

            }

            @Override
            public void undo() {
                gr.getTramosGrupoCompleto().getTramos().remove(tr);
                tr.setParent(null);
                updateStatusAsignacionDeAula(gr);
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
//</editor-fold>
    //**************************************************************************
    //COMANDOS DE BORRADO
    //**************************************************************************

    //<editor-fold defaultstate="collapsed" desc="getDeleteProfesorCommand">
    public KairosCommand getDeleteProfesorCommand(Profesor p) {
        class DeleteProfesorCommand extends KairosCommand {

            private final Profesor p;
            private final Departamento dep;
            private KairosCommand cmdBorrarDocencia;

            public DeleteProfesorCommand(Profesor p) {
                super(KairosCommand.STD_CMD);
                this.p = p;
                this.dep = p.getDepartamento();
            }

            @Override
            public void execute() {
                //Primero borro la docencia del profesor
                cmdBorrarDocencia = getClearDocenciaCommand(p);
                cmdBorrarDocencia.execute();
                //Ahora puedo borrar el profesor
                dep.removeProfesor(p);
                p.setDepartamento(null);

            }

            @Override
            public void undo() {
                dep.addProfesor(p);
                p.setDepartamento(dep);
                dep.ordenaProfesores();
                //Restauro la docencia
                cmdBorrarDocencia.undo();
            }

            @Override
            public String getDescription() {
                return "Borrar profesor";
            }

            @Override
            public String toString() {
                return getDescription();
            }

            @Override
            public Object getDataType() {
                return p;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.REMOVE;
            }
        }
        return new DeleteProfesorCommand(p);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getDeleteCarreraCommand">
    public KairosCommand getDeleteCarreraCommand(Carrera car) {
        class DeleteCarreraCommand extends KairosCommand {

            private final Carrera car;

            public DeleteCarreraCommand(Carrera car) {
                super(KairosCommand.STD_CMD);
                this.car = car;
            }

            @Override
            public void execute() {
                final DataAsignaturas da = dk.getDP().getDataAsignaturas();
                da.removeCarrera(car);
                car.setParent(da);
                updateStatusAsignacionDeAula(car);
            }

            @Override
            public void undo() {
                if (car != null) {
                    final DataAsignaturas da = dk.getDP().getDataAsignaturas();
                    da.addCarrera(car);
                    car.setParent(da);
                    da.ordenaCarreras();
                    updateStatusAsignacionDeAula(car);
                }
            }

            @Override
            public String getDescription() {
                return "Borrar carrera";
            }

            @Override
            public String toString() {
                return getDescription();
            }

            @Override
            public Object getDataType() {
                return car;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.REMOVE;
            }
        }
        return new DeleteCarreraCommand(car);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getDeleteCursoCommand">
    public KairosCommand getDeleteCursoCommand(Curso cur) {
        class DeleteCursoCommand extends KairosCommand {

            private final Curso cur;
            private final Carrera car;

            public DeleteCursoCommand(Curso cur) {
                super(KairosCommand.STD_CMD);
                this.cur = cur;
                this.car = cur.getParent();
            }

            @Override
            public void execute() {
                if (car != null) {
                    car.removeCurso(cur);
                    cur.setParent(null);
                    updateStatusAsignacionDeAula(car);
                }
            }

            @Override
            public void undo() {
                if (car != null) {
                    car.addCurso(cur);
                    cur.setParent(car);
                    car.ordenaCursos();
                    updateStatusAsignacionDeAula(car);
                }
            }

            @Override
            public String getDescription() {
                return "Borrar curso";
            }

            @Override
            public String toString() {
                return getDescription();
            }

            @Override
            public Object getDataType() {
                return cur;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.REMOVE;
            }
        }
        return new DeleteCursoCommand(cur);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getDeleteAsignaturaCommand">
    public KairosCommand getDeleteAsignaturaCommand(Asignatura asig) {
        class DeleteAsignaturaCommand extends KairosCommand {

            private final Curso cur;
            private final Asignatura asig;

            public DeleteAsignaturaCommand(Asignatura asig) {
                super(KairosCommand.STD_CMD);
                this.cur = asig.getParent();
                this.asig = asig;
            }

            @Override
            public void execute() {
                if (cur != null) {
                    cur.removeAsignatura(asig);
                    asig.setParent(null);
                    updateStatusAsignacionDeAula(cur);
                }
            }

            @Override
            public void undo() {
                if ((cur != null) && (asig != null)) {
                    cur.addAsignatura(asig);
                    asig.setParent(cur);
                    cur.ordenaAsignaturas();
                    updateStatusAsignacionDeAula(cur);
                }
            }

            @Override
            public String getDescription() {
                return "Borrar asignatura";
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
                return DataProyectoListener.REMOVE;
            }
        }
        return new DeleteAsignaturaCommand(asig);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getDeleteGrupoCommand">
    public KairosCommand getDeleteGrupoCommand(Grupo gr) {
        class DeleteGrupoCommand extends KairosCommand {

            private final Grupo gr;
            private final Asignatura asig;

            public DeleteGrupoCommand(Grupo gr) {
                super(KairosCommand.STD_CMD);
                this.gr = gr;
                this.asig = gr.getParent();
            }

            @Override
            public void execute() {
                if (asig != null) {
                    asig.getGrupos().getGrupos().remove(gr);
                    gr.setParent(null);
                    updateStatusAsignacionDeAula(asig);
                }
            }

            @Override
            public void undo() {
                if ((asig != null) && (gr != null)) {
                    asig.getGrupos().getGrupos().add(gr);
                    gr.setParent(asig);
                    updateStatusAsignacionDeAula(asig);
                }
            }

            @Override
            public String getDescription() {
                return "Borrar grupo";
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
                return DataProyectoListener.REMOVE;
            }
        }
        return new DeleteGrupoCommand(gr);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getDeleteTramoCommand">
    public KairosCommand getDeleteTramoCommand(Tramo tr) {
        class DeleteTramoCommand extends KairosCommand {

            private final Grupo gr;
            private final Tramo tr;

            public DeleteTramoCommand(Tramo tr) {
                super(KairosCommand.STD_CMD);
                this.tr = tr;
                this.gr = tr.getParent().getParent();
            }

            @Override
            public void execute() {
                if (gr != null) {
                    gr.getTramosGrupoCompleto().getTramos().remove(tr);
                    tr.setParent(null);
                    updateStatusAsignacionDeAula(gr);
                }
            }

            @Override
            public void undo() {
                if ((gr != null) && (tr != null)) {
                    gr.getTramosGrupoCompleto().getTramos().add(tr);
                    tr.setParent(gr.getTramosGrupoCompleto());
                    updateStatusAsignacionDeAula(gr);
                }
            }

            @Override
            public String getDescription() {
                return "Borrar tramo";
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
                return DataProyectoListener.REMOVE;
            }
        }
        return new DeleteTramoCommand(tr);
    }
//</editor-fold>
    //**************************************************************************
    //COMANDOS DE ASIGNACION
    //**************************************************************************
    //<editor-fold defaultstate="collapsed" desc="getAsignarDocenciaCommand">

    public KairosCommand getAsignarDocenciaCommand(Profesor p, Teachable t) {
        class AsignarDocenciaCommand extends KairosCommand {

            private final Profesor p;
            private final Teachable t;
            //Tramo->antiguo profesor, necesario para deshacer
            private final HashMap<Tramo, Profesor> undoInfo;
            private ArrayList<Tramo> tramos;

            public AsignarDocenciaCommand(Profesor p, Teachable t) {
                super(KairosCommand.STD_CMD);
                this.p = p;
                this.t = t;
                undoInfo = new HashMap<Tramo, Profesor>();
                tramos = new ArrayList<Tramo>();
            }

            @Override
            public void execute() {
                tramos = getTramosFromTeachable(t);
                for (Tramo tr : tramos) {
                    Profesor oldProfesor = tr.getDocente();
                    if (oldProfesor != null) {
                        oldProfesor.removeDocencia(tr);//Borro la docencia de la lista del antiguo profesor
                    }
                    undoInfo.put(tr, oldProfesor);//Guardo en map para poder deshacer después

                    tr.setDocente(p);//Asigno nuevo docente
                    if (p != null) {
                        p.addDocencia(tr);
                    }
                }
            }

            @Override
            public void undo() {
                for (Tramo tr : tramos) {
                    Profesor newProfesor = undoInfo.get(tr);
                    if (p != null) {
                        p.removeDocencia(tr);//Borro la docencia de la lista del antiguo profesor (si existía)
                    }
                    tr.setDocente(newProfesor);//Asigno nuevo docente
                    if (newProfesor != null) {
                        newProfesor.addDocencia(tr);
                    }
                }
            }

            @Override
            public String getDescription() {
                return "Asignar docencia";
            }

            @Override
            public Object getDataType() {
                return p;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.ADD;
            }
        }
        return new AsignarDocenciaCommand(p, t);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getAsignarAulaCommand">
    public KairosCommand getAsignarAulaCommand(AulaMT aulaMT, Teachable t) {
        class AsignarAulaCommand extends KairosCommand {

            private final AulaMT aulaMT;
            private final Teachable t;
            //Tramo->antiguo profesor, necesario para deshacer
            private final HashMap<Tramo, AulaMT> undoInfo;
            private ArrayList<Tramo> tramos;

            public AsignarAulaCommand(AulaMT aulaMT, Teachable t) {
                super(KairosCommand.STD_CMD);
                this.aulaMT = aulaMT;
                this.t = t;
                undoInfo = new HashMap<Tramo, AulaMT>();
                tramos = new ArrayList<Tramo>();
            }

            @Override
            public void execute() {
                tramos = getTramosFromTeachable(t);
                for (Tramo tr : tramos) {
                    AulaMT oldAulaMT = tr.getAulaMT();
                    undoInfo.put(tr, oldAulaMT);//Guardo en map para poder deshacer después
                    tr.setAulaMT(aulaMT);//Asigno nueva aula
                    updateStatusAsignacionDeAula(tr);
                }

            }

            @Override
            public void undo() {
                for (Tramo tr : tramos) {
                    AulaMT newAulaMT = undoInfo.get(tr);
                    tr.setAulaMT(newAulaMT);//Asigna aula que tenía antes
                    updateStatusAsignacionDeAula(tr);
                }
            }

            @Override
            public String getDescription() {
                return "Asignar aula";
            }

            @Override
            public Object getDataType() {
                return aulaMT;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.ADD;
            }
        }
        return new AsignarAulaCommand(aulaMT, t);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getClearDocenciaCommand">
    public KairosCommand getClearDocenciaCommand(Profesor p) {
        class ClearDocenciaCommand extends KairosCommand {

            private final Profesor p;
            private final HashSet<Tramo> undoInfo;//Información para deshacer

            public ClearDocenciaCommand(Profesor p) {
                super(KairosCommand.STD_CMD);
                this.p = p;
                undoInfo = new HashSet<Tramo>();
            }

            @Override
            public void execute() {
                undoInfo.clear();
                for (Tramo tr : p.getDocencia()) {
                    tr.setDocente(null);
                    undoInfo.add(tr);
                }
                p.getDocencia().clear();

            }

            @Override
            public void undo() {
                for (Tramo tr : undoInfo) {
                    tr.setDocente(p);
                    p.addDocencia(tr);
                }
            }

            @Override
            public String getDescription() {
                return "Eliminar docencia";
            }

            @Override
            public Object getDataType() {
                return p;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.MODIFY;
            }
        }

        return new ClearDocenciaCommand(p);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getVaciarAulaCommand">
    public KairosCommand getVaciarAulaCommand(AulaMT aulaMT) {
        class VaciarAulaCommand extends KairosCommand {

            private final HashSet<Tramo> undoInfo;//Información para deshacer
            private final AulaMT aulaMT;

            public VaciarAulaCommand(AulaMT aulaMT) {
                super(KairosCommand.STD_CMD);
                this.aulaMT = aulaMT;
                undoInfo = new HashSet<Tramo>();
            }

            @Override
            public void execute() {
                undoInfo.clear();
                ArrayList<Tramo> todosTramos = getTodosLosTramos();
                //Ahora la recorro y me quedo con los tramos cuya aula sea esta
                for (Tramo tr : todosTramos) {
                    if (aulaMT.equals(tr.getAulaMT())) {
                        undoInfo.add(tr);//guardo para poder deshacer
                        tr.setAulaMT(null);//Quito el aula
                    }
                    aulaMT.getAsignaciones().clear();

                }
            }

            @Override
            public void undo() {
                for (Tramo tr : undoInfo) {
                    tr.setAulaMT(aulaMT);
                    aulaMT.asignaTramo(tr);
                }
            }

            @Override
            public String getDescription() {
                return "Vaciar aula";
            }

            @Override
            public Object getDataType() {
                return new Tramo(0);
            }

            @Override
            int getEventType() {
                return DataProyectoListener.MODIFY;
            }
        }

        return new VaciarAulaCommand(aulaMT);
    }
//</editor-fold>
//**************************************************************************
    //OTROS COMANDOS
    //**************************************************************************
    //<editor-fold defaultstate="collapsed" desc="getBeginBlockCommand">

    public KairosCommand getBeginBlockCommand() {
        final KairosController controller = this;
        class BeginBlockCommand extends KairosCommand {

            public BeginBlockCommand() {
                super(KairosCommand.BEGIN_BLOCK);
                this.setUndoable(true);
            }

            @Override
            public void execute() {
                controller.setBatchMode(true);
            }

            @Override
            public void undo() {
                controller.setBatchMode(false);
            }

            @Override
            public String getDescription() {
                return "Begin Atomic Command Block";
            }

            @Override
            public Object getDataType() {
                return null;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.MODIFY;
            }
        }
        return new BeginBlockCommand();
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="getEndBlockCommand">
    public KairosCommand getEndBlockCommand() {
        final KairosController controller = this;
        class EndBlockCommand extends KairosCommand {

            public EndBlockCommand() {
                super(KairosCommand.END_BLOCK);
                this.setUndoable(true);
            }

            @Override
            public void execute() {
                controller.setBatchMode(false);
            }

            @Override
            public void undo() {
                controller.setBatchMode(true);
            }

            @Override
            public String getDescription() {
                return "Begin Atomic Command Block";
            }

            @Override
            public Object getDataType() {
                return null;
            }

            @Override
            int getEventType() {
                return DataProyectoListener.MODIFY;
            }
        }
        return new EndBlockCommand();
    }
//</editor-fold>
}

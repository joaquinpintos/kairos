/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.a
 */
package testers;

import data.ArrayRangoHoras;
import data.CalendarioAcademico;
import data.DataProyecto;
import data.asignaturas.Carrera;
import data.asignaturas.Asignatura;
import data.asignaturas.Curso;
import data.asignaturas.DataAsignaturas;
import data.asignaturas.Grupo;
import data.asignaturas.Tramo;
import data.aulas.Aula;
import data.aulas.DataAulas;
import data.RangoHoras;
import data.profesores.DataProfesores;
import data.profesores.Departamento;
import data.profesores.Profesor;
import data.genetic.Asignacion;
import data.genetic.DataGenerator;
import data.genetic.PosibleSolucion;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import restricciones.profesorCiertosDias.RProfesorCiertosDias;

/**
 *
 * @author David Gutierrez
 */
public class AsigTester {
    //Clase para comprobar si funcionan las asignaturas

    Asignatura asig;
    private DataAsignaturas dataAsignaturas;
    private DataProfesores dataProfesores;
    private DataAulas dataAulas;

    /**
     *
     */
    public void pruebaCambioSegmentos()
    {
        ArrayList<Integer> oldAsig = new ArrayList<Integer>();
        oldAsig.add(0);
        oldAsig.add(2);
        oldAsig.add(9);
        oldAsig.add(8);
        oldAsig.add(7);
        oldAsig.add(3);
        oldAsig.add(5);
        oldAsig.add(4);
        oldAsig.add(6);
        int indexSrc=7;
        int numSegmentoSrc = oldAsig.get(indexSrc);
        int numSegmentoDst=2;
        List<Integer> segmentosDestino = oldAsig.subList(numSegmentoDst, numSegmentoDst+3);
        System.out.println("numsegme ="+numSegmentoSrc);
        System.out.println("sgDst    ="+segmentosDestino);
        System.out.println("oldasig  ="+oldAsig);
        ArrayList<Integer> nuevaAsig = changeSegmentos(indexSrc, numSegmentoDst, oldAsig, segmentosDestino, numSegmentoSrc);
        System.out.println("nuevaAsig="+nuevaAsig);
        
    }
      private ArrayList<Integer> changeSegmentos(int index1, int index2, ArrayList<Integer> oldAsig, List<Integer> segmentosDestino, int numSegmentoSrc) {
        ArrayList<Integer>nuevaAsig=new ArrayList<Integer>();
        if (index1<index2)
        {
            nuevaAsig.addAll(oldAsig.subList(0, index1));
            nuevaAsig.addAll(segmentosDestino);
            nuevaAsig.addAll(oldAsig.subList(index1+1, index2));
            nuevaAsig.add(numSegmentoSrc);
            nuevaAsig.addAll(oldAsig.subList(index2+segmentosDestino.size(), oldAsig.size()));
        }
         if (index1>index2)
        {
            nuevaAsig.addAll(oldAsig.subList(0, index2));
            nuevaAsig.add(numSegmentoSrc);
            nuevaAsig.addAll(oldAsig.subList(index2+segmentosDestino.size(),index1));
            nuevaAsig.addAll(segmentosDestino);
            nuevaAsig.addAll(oldAsig.subList(index1+1, oldAsig.size()));
            
            
        }
        return nuevaAsig;
    }
    /**
     *
     */
    public void pruebaRangoHoras() {
        ArrayRangoHoras a = new ArrayRangoHoras("9:00-10:00");
        RangoHoras r1=new RangoHoras("9:15-10:15");
        RangoHoras r2=new RangoHoras("11:15-12:15");
        
        System.out.println("a contiene r1: "+a.contiene(r1));
        System.out.println("a solapa con r1: "+a.solapaCon(r1));
        
        System.out.println("a contiene r2: "+a.contiene(r2));
        System.out.println("a solapa con r2: "+a.solapaCon(r2));



    }

    /**
     *
     */
    public void pruebaCalendarios() {

        GregorianCalendar c1, c2;

        c1 = new GregorianCalendar(2013, Calendar.FEBRUARY, 3);
        Date d = c1.getTime();
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        dbg(df.format(d));
        dbg(d);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dbg(sdf.format(d));

        Date d2 = null;
        try {
            d2 = sdf.parse("3/9/2013");
        } catch (ParseException ex) {
            Logger.getLogger(AsigTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        dbg(sdf.format(d2));
        dbg(d2);
        c2 = new GregorianCalendar();
        c2.setTime(d2);
        GregorianCalendar c3 = new GregorianCalendar();
        try {
            c3.setTime(sdf.parse("3/12/2013"));
        } catch (ParseException ex) {
            Logger.getLogger(AsigTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        c2.add(GregorianCalendar.MONTH, 3);
        d2 = c2.getTime();
        dbg(sdf.format(d2));

        dbg(c2.equals(c3));
        d2 = c2.getTime();
        dbg(sdf.format(d2));
        c2.add(GregorianCalendar.DAY_OF_MONTH, 29);
        dbg(sdf.format(c2.getTime()));

        CalendarioAcademico calendarioAcadémico = new CalendarioAcademico();


        try {
            calendarioAcadémico.setInicio("04/09/2013");
            calendarioAcadémico.setFin("15/09/2013");
        } catch (ParseException ex) {
            Logger.getLogger(AsigTester.class.getName()).log(Level.SEVERE, null, ex);
        }
        calendarioAcadémico.marcaDiasSemanaLectivos(true, false, false, true, true);

        dbg("Total " + calendarioAcadémico.getArrayDiasLectivos() + " días lectivos");
    }

    /**
     *
     * @param texto
     * @return
     */
    public ArrayList<String> creaArrayDiasFestivos(String texto) {
        ArrayList<String> resul = new ArrayList<String>();
        String[] lineas = texto.split("\n");


        return resul;
    }

    /**
     *
     * @throws Exception
     */
    public void pruebaRangos() throws Exception {
        RangoHoras r1 = new RangoHoras(8, 0, 9, 10);
        RangoHoras r2 = new RangoHoras(9, 0, 13, 0);

        boolean resul = r1.solapaCon(r2);
        System.out.println("¿" + r1 + " contiene " + r2.getInicio() + "? " + r1.contieneEstrictamente(r2.getInicio()));
        System.out.println("¿" + r1 + " se solapa con " + r2 + "? " + resul);
    }

    /**
     *
     * @throws Exception
     */
    public void run() throws Exception {
    }

    /**
     *
     * @param dp
     * @throws Exception
     */
    public void datosRelleno(DataProyecto dp) throws Exception {
        DataProyecto dataProyecto = dp;
        Departamento dep = new Departamento("Ciencias experimentales");
        Departamento dep2 = new Departamento("Lengua");
        dp.getDataProfesores().addDepartamento(dep2);
        dp.getDataProfesores().addDepartamento(dep);
        Profesor david = new Profesor("David", "Gutiérrez Rubio","David");
        Profesor alberto = new Profesor("Alberto", "Alvarez","Alberto");
        dep.addProfesor(david);
        dep.addProfesor(alberto);
        dep2.addProfesor(new Profesor("Paquito", "Palotes","Paco"));
        dep2.addProfesor(new Profesor("Juan", "Amedio","JuanAm"));

        Carrera car1 = new Carrera("Primaria");
        Carrera car2 = new Carrera("Infantil");
        Curso cur1 = new Curso("1º");
        car1.addCurso(cur1);
        car1.addCurso(new Curso("2º"));
        car1.addCurso(new Curso("3º"));
        car1.addCurso(new Curso("4º"));
        dp.getDataAsignaturas().addCarrera(car1);
        //dp.getDataAsignaturas().addCarrera(car2);

        Asignatura asignatura = new Asignatura("Mates");
        cur1.addAsignatura(asignatura);
        Tramo t1 = new Tramo( 60);
        Tramo t2 = new Tramo( 90);
        Grupo gr = new Grupo("1");
        gr.addTramoGrupoCompleto(t1);
        asignatura.addGrupo(gr);
        gr = new Grupo("2");
        asignatura.addGrupo(gr);

        gr.addTramoGrupoCompleto(new Tramo( 90));

        asignatura = new Asignatura("Lengua");
        cur1.addAsignatura(asignatura);
        gr = new Grupo("1");
        t1 = new Tramo(60);
        t2 = new Tramo(90);
        gr.addTramoGrupoCompleto(t1);
        asignatura.addGrupo(gr);
        gr = new Grupo("3");
        t1 = new Tramo(60);
        gr.addTramoGrupoCompleto(t1);
        asignatura.addGrupo(gr);
        Aula aula11 = new Aula("11");
        Aula aula12 = new Aula("21");
        dp.getDataAulas().addAula(aula11);
        dp.getDataAulas().addAula(aula12);
        dp.setMañana1(new RangoHoras(8, 15, 11, 15));
        dp.setMañana2(new RangoHoras(11, 45, 14, 45));
        dp.setTarde1(new RangoHoras(16, 00, 18, 00));
        dp.setTarde2(new RangoHoras(18, 30, 21, 30));

        dataProyecto.addGrupoCompletoToAula("1º@1", "11@M");

        dataProyecto.addGrupoCompletoToAula("1º@2", "21@M");

        dataProyecto.addGrupoCompletoToAula("1º@3", "11@T");


    }

    /**
     *
     * @throws Exception
     */
    public void pruebaNuevoTipoDatos() throws Exception {
        DataProyecto dp = new DataProyecto();
        datosRelleno(dp);
        DataGenerator dg = new DataGenerator(30, dp);
        dg.generaDatos();
        PosibleSolucion sol = PosibleSolucion.generador(dp);
        sol.setDataProyecto(dp);
        String myhash = "1@M";
        Asignacion myAsig = sol.getAsignacion(myhash);


        dbg("Asigs   1: " + myAsig.getAsignaciones());
        dbg("QueCasi 1: " + myAsig.getQueCasilla());
        sol.update();
        dbg("Asigs   1: " + myAsig.getAsignaciones());
        dbg("QueCasi 1: " + myAsig.getQueCasilla());
        sol.update();
        dbg("Asigs   1: " + myAsig.getAsignaciones());
        dbg("QueCasi 1: " + myAsig.getQueCasilla());
    }

    /**
     *
     * @param dataProyecto
     * @throws Exception
     */
    public void datosBucleRelleno(DataProyecto dataProyecto) throws Exception {
        //Rellena datos usando bucles
        int numProfe = 1;
        Departamento dep;
        for (int numDep = 1; numDep <= 10; numDep++) {
            dep = new Departamento("Departamento " + numDep);
            dataProyecto.getDataProfesores().addDepartamento(dep);
            for (int k = 0; k < 5; k++) {
                dep.addProfesor(new Profesor("Profe" + String.format("%02d", numProfe), "Apellidos" + String.format("%02d", numProfe),"pro"+String.format("%02d", numProfe)));
                numProfe++;
            }
        }
        Asignatura asign;
        Carrera car;
        Curso cur;
        Grupo gr;
        int numCarreras = 1;
        int numAsignaturas = 5;
        int numAulas = 10;
        int numeroCursos = 1;


        for (int numCar = 1; numCar <= numCarreras; numCar++) {
            car = new Carrera("Carrera " + numCar);
            dataProyecto.getDataAsignaturas().addCarrera(car);

            for (int numCurso = 1; numCurso <= numeroCursos; numCurso++) {
                cur = new Curso("" + numCurso);
                car.addCurso(cur);

                for (int numAsig = 1; numAsig <= numAsignaturas; numAsig++) {
                    asign = new Asignatura("Asignatura " + numAsig);
                    asign.addGrupo(grupoEstandar("1"));
                    asign.addGrupo(grupoEstandar("2"));
                    asign.addGrupo(addGrupoSinSubgrupos("3"));
                    cur.addAsignatura(asign);
                }
            }
        }

        Aula aula;
        for (int numAula = 1; numAula <= numAulas; numAula++) {
            aula = new Aula("" + numAula);
            dataProyecto.getDataAulas().addAula(aula);
        }
//Set mañana rangos and tarde rangos 
        dataProyecto.setMañana1(new RangoHoras(8, 15, 11, 15));
        dataProyecto.setMañana2(new RangoHoras(11, 45, 14, 45));
        dataProyecto.setTarde1(new RangoHoras(16, 00, 18, 00));
        dataProyecto.setTarde2(new RangoHoras(18, 30, 21, 30));
        int k = 1;
        for (Carrera carr : dataProyecto.getDataAsignaturas().getCarreras()) {
            for (int n = 1; n <= numeroCursos; n++) {
                dataProyecto.addGrupoCompletoToAula(carr.getNombre() + "@" + n + "@3", k + "@T");
                dataProyecto.addGrupoCompletoToAula(carr.getNombre() + "@" + n + "@1", k + "@M");
                dataProyecto.addGrupoCompletoToAula(carr.getNombre() + "@" + n + "@2", (k + 1) + "@M");
                k += 2;
            }
        }
    }

    /**
     *
     * @param nombre
     * @return
     */
    public Grupo grupoEstandar(String nombre) {
        Grupo gr = new Grupo(nombre);

        gr.addTramoGrupoCompleto(new Tramo( 60));
        gr.setTarde(false);
        return gr;
    }

    /**
     *
     * @param nombre
     * @return
     */
    public Grupo addGrupoSinSubgrupos(String nombre) {
        Grupo gr = new Grupo(nombre);

        gr.addTramoGrupoCompleto(new Tramo( 60));
        gr.addTramoGrupoCompleto(new Tramo( 90));
        gr.setTarde(true);
        return gr;
    }

    /**
     *
     * @param str
     */
    public void dbg(Object str) {
        System.out.println(str);
    }

    /**
     *
     * @param str
     */
    public void dbg2(Object str) {
        System.out.print(str);
    }

    /**
     *
     */
    public void pruebaClasesDinamicas() {
        RProfesorCiertosDias r = new RProfesorCiertosDias();
        String nombre = r.getClass().getName();
        Class c;
        RProfesorCiertosDias r2 = null;
        try {
            c = Class.forName(nombre);

             r2 = (RProfesorCiertosDias) c.newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(AsigTester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AsigTester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AsigTester.class.getName()).log(Level.SEVERE, null, ex);
        }

        dbg(r.getClass().getName());
        dbg(r2.getClass().getName());


    }
}


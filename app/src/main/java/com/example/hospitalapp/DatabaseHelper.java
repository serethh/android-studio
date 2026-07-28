package com.example.hospitalapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hospital.db";
    private static final int DATABASE_VERSION = 4;

    public static final String TABLE_USUARIOS = "Usuarios";
    public static final String TABLE_MEDICOS = "Medicos";
    public static final String TABLE_PACIENTES = "Pacientes";
    public static final String TABLE_CONSULTAS = "Consultas";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USUARIOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuario TEXT NOT NULL, " +
                "password TEXT NOT NULL, " +
                "rol TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + TABLE_MEDICOS + " (" +
                "id_medico INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "especialidad TEXT, " +
                "cedula TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_PACIENTES + " (" +
                "id_paciente INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "edad INTEGER, " +
                "diagnostico TEXT)");

        db.execSQL("CREATE TABLE " + TABLE_CONSULTAS + " (" +
                "id_consulta INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "id_paciente INTEGER NOT NULL, " +
                "id_medico INTEGER NOT NULL, " +
                "fecha_hora_atencion TEXT, " +
                "hora_salida TEXT, " +
                "observaciones TEXT, " +
                "diagnostico_consulta TEXT, " +
                "FOREIGN KEY(id_paciente) REFERENCES " + TABLE_PACIENTES + "(id_paciente), " +
                "FOREIGN KEY(id_medico) REFERENCES " + TABLE_MEDICOS + "(id_medico))");

        cargarDatosDePrueba(db);
    }

    private void cargarDatosDePrueba(SQLiteDatabase db) {
        db.execSQL("INSERT INTO Usuarios (usuario, password, rol) VALUES ('admin','admin123','Administrador')");
        db.execSQL("INSERT INTO Usuarios (usuario, password, rol) VALUES ('doctor1','doc123','Medico')");
        db.execSQL("INSERT INTO Usuarios (usuario, password, rol) VALUES ('recepcion','rec123','Recepcionista')");
        db.execSQL("INSERT INTO Usuarios (usuario, password, rol) VALUES ('enfermero','enf','Enfermera')");

        db.execSQL("INSERT INTO Medicos (nombre, especialidad, cedula) VALUES ('Dr. Juan Perez','Pediatria','MED-001')");
        db.execSQL("INSERT INTO Medicos (nombre, especialidad, cedula) VALUES ('Dra. Maria Lopez','Cardiologia','MED-002')");
        db.execSQL("INSERT INTO Medicos (nombre, especialidad, cedula) VALUES ('Dr. Carlos Ruiz','Medicina General','MED-003')");
        db.execSQL("INSERT INTO Medicos (nombre, especialidad, cedula) VALUES ('Dra. Sereth Jimenez','Cirujana','MED-004')");

        db.execSQL("INSERT INTO Pacientes (nombre, edad, diagnostico) VALUES ('Ana Torres',34,'Control rutinario')");
        db.execSQL("INSERT INTO Pacientes (nombre, edad, diagnostico) VALUES ('Luis Gomez',45,'Hipertension')");
        db.execSQL("INSERT INTO Pacientes (nombre, edad, diagnostico) VALUES ('Sofia Martinez',8,'Fiebre')");
        db.execSQL("INSERT INTO Pacientes (nombre, edad, diagnostico) VALUES ('Diego Santiago',20,'Muerte')");


        db.execSQL("INSERT INTO Consultas (id_paciente, id_medico, fecha_hora_atencion, hora_salida, diagnostico_consulta) " +
                "VALUES (1,1,'2026-07-20 09:00','2026-07-20 09:30','Paciente estable, control en 6 meses')");
        db.execSQL("INSERT INTO Consultas (id_paciente, id_medico, fecha_hora_atencion, hora_salida, diagnostico_consulta) " +
                "VALUES (2,2,'2026-07-21 10:15','2026-07-21 10:45','Se ajusta tratamiento de presion arterial')");
        db.execSQL("INSERT INTO Consultas (id_paciente, id_medico, fecha_hora_atencion, hora_salida, diagnostico_consulta) " +
                "VALUES (3,3,'2026-07-22 11:00','2026-07-22 11:20','Fiebre viral, reposo e hidratacion')");
        db.execSQL("INSERT INTO Consultas (id_paciente, id_medico, fecha_hora_atencion, hora_salida, diagnostico_consulta) " +
                "VALUES (4,4,'2026-07-23 8:30','2026-07-26 11:20','muerte, fallecimiento')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONSULTAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PACIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEDICOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS);
        onCreate(db);
    }

    public String validarUsuario(String usuario, String password) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(
                "SELECT rol FROM " + TABLE_USUARIOS + " WHERE usuario=? AND password=?",
                new String[]{usuario, password});
        String rol = null;
        if (c.moveToFirst()) {
            rol = c.getString(0);
        }
        c.close();
        return rol;
    }
    public boolean existeUsuario(String usuario) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(
                "SELECT id FROM " + TABLE_USUARIOS + " WHERE usuario=?",
                new String[]{usuario});

        boolean existe = c.moveToFirst();
        c.close();

        return existe;
    }

    public long registrarUsuario(String usuario, String password) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("usuario", usuario);
        cv.put("password", password);
        cv.put("rol", "Recepcionista");

        return db.insert(TABLE_USUARIOS, null, cv);
    }

    public static class Medico {
        public int id;
        public String nombre;

        public Medico(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        @Override
        public String toString() {
            return nombre;
        }
    }

    public List<Medico> obtenerMedicos() {
        List<Medico> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id_medico, nombre FROM " + TABLE_MEDICOS, null);
        while (c.moveToNext()) {
            lista.add(new Medico(c.getInt(0), c.getString(1)));
        }
        c.close();
        return lista;
    }
    public long guardarMedico(String nombre,
                              String especialidad,
                              String cedula) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("especialidad", especialidad);
        cv.put("cedula", cedula);

        return db.insert(TABLE_MEDICOS, null, cv);
    }

    public long guardarPaciente(String nombre, int edad, String diagnostico) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("nombre", nombre);
        cv.put("edad", edad);
        cv.put("diagnostico", diagnostico);
        return db.insert(TABLE_PACIENTES, null, cv);
    }

    public long guardarConsulta(long idPaciente, long idMedico, String fechaHoraAtencion,
                                 String horaSalida, String diagnosticoConsulta) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("id_paciente", idPaciente);
        cv.put("id_medico", idMedico);
        cv.put("fecha_hora_atencion", fechaHoraAtencion);
        cv.put("hora_salida", horaSalida);
        cv.put("diagnostico_consulta", diagnosticoConsulta);
        return db.insert(TABLE_CONSULTAS, null, cv);
    }
    public static class ConsultaPendiente {
        public int idConsulta;
        public String nombrePaciente;
        public String horaEntrada;

        @Override
        public String toString() {
            return nombrePaciente;
        }
    }
    public List<ConsultaPendiente> obtenerConsultasPendientes() {

        List<ConsultaPendiente> lista = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();

        String sql =
                "SELECT c.id_consulta, p.nombre, c.fecha_hora_atencion " +
                        "FROM Consultas c " +
                        "INNER JOIN Pacientes p ON c.id_paciente = p.id_paciente " +
                        "WHERE c.hora_salida = '' OR c.hora_salida IS NULL";

        Cursor c = db.rawQuery(sql, null);

        while (c.moveToNext()) {

            ConsultaPendiente cp = new ConsultaPendiente();

            cp.idConsulta = c.getInt(0);
            cp.nombrePaciente = c.getString(1);
            cp.horaEntrada = c.getString(2);

            lista.add(cp);
        }

        c.close();

        return lista;
    }
    public boolean registrarSalida(int idConsulta,
                                   String horaSalida,
                                   String observaciones) {

        SQLiteDatabase db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("hora_salida", horaSalida);
        cv.put("observaciones", observaciones);

        int filas = db.update(
                TABLE_CONSULTAS,
                cv,
                "id_consulta=?",
                new String[]{String.valueOf(idConsulta)}
        );

        return filas > 0;
    }

    public static class PacienteConDoctor {
        public String nombrePaciente;
        public int edad;
        public String nombreDoctor;
        public String diagnosticoConsulta;
        public String fechaAtencion;
    }

    public List<PacienteConDoctor> obtenerPacientesConMedico() {
        List<PacienteConDoctor> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "SELECT p.nombre, p.edad, m.nombre, c.diagnostico_consulta, c.fecha_hora_atencion " +
                "FROM " + TABLE_PACIENTES + " p " +
                "INNER JOIN " + TABLE_CONSULTAS + " c ON p.id_paciente = c.id_paciente " +
                "INNER JOIN " + TABLE_MEDICOS + " m ON c.id_medico = m.id_medico " +
                "ORDER BY c.fecha_hora_atencion DESC";
        Cursor c = db.rawQuery(query, null);
        while (c.moveToNext()) {
            PacienteConDoctor p = new PacienteConDoctor();
            p.nombrePaciente = c.getString(0);
            p.edad = c.getInt(1);
            p.nombreDoctor = c.getString(2);
            p.diagnosticoConsulta = c.getString(3);
            p.fechaAtencion = c.getString(4);
            lista.add(p);
        }
        c.close();
        return lista;
    }

    public String[] obtenerNombresTablas() {
        return new String[]{TABLE_USUARIOS, TABLE_MEDICOS, TABLE_PACIENTES, TABLE_CONSULTAS};
    }

    public Cursor obtenerTabla(String nombreTabla) {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + nombreTabla, null);
    }

}

package gestionar_almacenamiento;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import Modelos.Accidente_Transito;
import Modelos.Articulos_Coip;
import Modelos.Conductor;
import Modelos.Evidencia;
import Modelos.Infraccion_Transito;
import Modelos.Vehiculo;
import Utilidades.Constantes;
import Utilidades.Utilidades;


public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {

    public AdminSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL("create table infraccionTransito(numeroInfraccion int, descripcion text, ubicacion text," +
                "latitud text, longitud text, estado text," +
                "fechaInfraccion text, fechaRegistro text, horaRegistro text, horaInfraccion text, codigoAgente int," +        //Hora detencion es igual a la hora de registro
                "cedulaConductor int, numeroPlaca text, idArticulo int)");
        BaseDeDatos.execSQL("create table accidenteTransito(numeroAccidente int, tipoAccidente text," +
                " descripcion text, ubicacion text, latitud text, longitud text, fechaRegistro datetime, horaAccidente datetime," +
                "horaRegistro datetime, codigoAgente text, estado text)");
        BaseDeDatos.execSQL("create table agenteTransito(codigo int, cedula int, nombres text, apellidos text, clave text)");
        BaseDeDatos.execSQL("create table conductor(cedula text, nombres text, apellidos text, tipoLicencia text, " +
                "categoriaLicencia text, fechaEmisionLicencia datetime, fechaCaducidadLicencia datetime, Puntos)");
        BaseDeDatos.execSQL("create table vehiculo(placa text, marca text, tipo text, color text, " +
                "fechaMatricula text, fechaCaducidadMatricula text)");
        BaseDeDatos.execSQL("create table articulosCoip(idArticulo text, articulo text, inciso text, numeral text)");
        BaseDeDatos.execSQL("create table evidencia(idEvidencia int, audio text, foto text, video blob, codigoInfraccion int)");
        BaseDeDatos.execSQL("create table contadorInfracciones(cedula int, contador)"); //Contador infracciones
        BaseDeDatos.execSQL("create table contadorAccidentes(cedula int, contador)");//contador accidentes
        BaseDeDatos.execSQL("create table numeroIntentos(cedulaAgente int, fechIntento text, horaIntento Intento, latitud text, longitud text, codigoAgentes text)");//
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL ("DROP TABLE IF EXISTS infraccionTransito");
        db.execSQL ("DROP TABLE IF EXISTS accidenteTransito");
        db.execSQL ("DROP TABLE IF EXISTS agenteTransito");
        db.execSQL ("DROP TABLE IF EXISTS conductor");
        db.execSQL ("DROP TABLE IF EXISTS vehiculo");
        db.execSQL ("DROP TABLE IF EXISTS articulosCoip");
        db.execSQL ("DROP TABLE IF EXISTS evidencia");
        onCreate (db);
    }

    public void crearInfraccionTransito(Infraccion_Transito infraccion) {
        SQLiteDatabase db = Constantes.helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("numeroInfraccion", infraccion.getNumrero_infraccion());
        values.put("descripcion", infraccion.getDescripcion());
        values.put("ubicacion", infraccion.getUbicacion());
        values.put("latitud", infraccion.getLatitud());
        values.put("longitud", infraccion.getLongitud());
        values.put("estado", infraccion.getEstado());
        values.put("fechaInfraccion", Utilidades.obtenerFechaActual());
        values.put("fechaRegistro", Utilidades.obtenerFechaActual());
        values.put("horaRegistro", String.valueOf (infraccion.getHora_registro()));
        values.put("horaInfraccion", String.valueOf (infraccion.getHora_infraccion()));
        values.put("codigoAgente", infraccion.getAgente_transito());
        values.put("cedulaConductor", infraccion.getConductor());
        values.put("numeroPlaca", infraccion.getVehiculo());
        values.put("idArticulo", infraccion.getArticulos());
        db.insert("infraccionTransito", null, values);
        db.close();
    }

    public void crearAccidenteTransito(Accidente_Transito accidente) {
        SQLiteDatabase db = Constantes.helper.getWritableDatabase ();
        ContentValues values = new ContentValues ();
        values.put ("numeroAccidente", accidente.getNumero ());
        values.put ("tipoAccidente", accidente.getTipo ());
        values.put ("descripcion", accidente.getDescripcion ());
        values.put ("ubicacion", accidente.getUbicacion ());
        values.put("latitud", String.valueOf(accidente.getLatitud()));
        values.put("longitud", String.valueOf(accidente.getLongitud()));
        values.put ("fechaRegistro", String.valueOf(accidente.getFecha_registro ()));
        values.put ("horaAccidente", String.valueOf(accidente.getHora_accidente ()));
        values.put ("horaRegistro", String.valueOf(accidente.getHora_accidente ()));
        values.put ("codigoAgente", accidente.getAgente_transito ());
        values.put ("estado", accidente.getEstado());
        db.insert ("accidenteTransito", null, values);
        db.close ();
    }

    public void crearConductor(Conductor conductor) {
        SQLiteDatabase db = Constantes.helper.getWritableDatabase ();
        ContentValues values = new ContentValues ();
        values.put("cedula", conductor.getCedula ());
        values.put("nombres", conductor.getNombres ());
        values.put("apellidos", conductor.getApellidos ());
        values.put("tipoLicencia", conductor.getApellidos ());
        values.put("categoriaLicencia", conductor.getApellidos ());
        values.put("fechaEmisionLicencia", conductor.getApellidos ());
        values.put("fechaCaducidadLicencia", conductor.getApellidos ());
        db.insert ("conductor", null, values);
        db.close ();
    }

    public void crearVehiculo(Vehiculo vehiculo) {
        SQLiteDatabase db = Constantes.helper.getWritableDatabase ();
        ContentValues values = new ContentValues ();
        values.put ("placa", vehiculo.getPlaca ());
        values.put ("marca", vehiculo.getMarca ());
        values.put ("tipo", vehiculo.getTipo ());
        values.put ("color", vehiculo.getColor ());
        values.put ("fechaMatricula", vehiculo.getFecha_matricula ());
        values.put ("fechaCaducidadMatricula", vehiculo.getFecha_caducidad_matricula ());

        db.insert ("vehiculo", null, values);
        db.close ();
    }

    public void crearAritculo(Articulos_Coip articulos) {
        SQLiteDatabase db = Constantes.helper.getWritableDatabase ();
        ContentValues values = new ContentValues ();
        values.put ("articulo", articulos.getArticulo ());
        values.put ("inciso", articulos.getInciso ());
        values.put ("numeral", articulos.getNumeral ());

        db.insert ("articulosCoip", null, values);
        db.close ();
    }

    public void crearEvidencia(Evidencia evidencia) {
        SQLiteDatabase db = Constantes.helper.getWritableDatabase ();
        ContentValues values = new ContentValues ();
        values.put ("idEvidencia", evidencia.getId_evidencia ());
        values.put ("audio", evidencia.getAudio ());
        values.put ("foto", evidencia.getFoto ());
        values.put ("video", evidencia.getVideo ());
        values.put ("codigoInfraccion", evidencia.getCodigoInfraccion ());
        db.insert ("evidencia", null, values);
        db.close ();
    }

}

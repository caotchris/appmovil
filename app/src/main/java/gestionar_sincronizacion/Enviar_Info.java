package gestionar_sincronizacion;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Modelos.Accidente_Transito;
import Modelos.Conductor;
import Modelos.Evidencia;
import Modelos.Infraccion_Transito;
import Modelos.Vehiculo;
import Utilidades.Constantes;
import gestionar_almacenamiento.AdminSQLiteOpenHelper;
import timber.log.Timber;


public class Enviar_Info {

    @SuppressLint("LogNotTimber")
    public static ArrayList<Evidencia> obtenerEvidencias(AdminSQLiteOpenHelper admin) {
        ArrayList<Evidencia> evidencias = new ArrayList<> ();
        SQLiteDatabase db = admin.getWritableDatabase();
        Evidencia evidencia;
        @SuppressLint("Recycle") Cursor fila = db.rawQuery("select * from agenteTransito",null);
        if (fila.moveToFirst ()) {
            do {
                evidencia = new Evidencia ();
                evidencia.setId_evidencia (fila.getString (0));
                evidencia.setAudio (fila.getString (1));
                evidencia.setVideo (fila.getString (2));
                evidencia.setFoto (fila.getString (3));
                evidencia.setCodigoInfraccion (fila.getString (4));
                evidencias.add (evidencia);
            } while (fila.moveToNext ());
            fila.close ();
            db.close ();
            Log.e ("EvidenciaDB", evidencias.toString());
            return evidencias;
        } else {
            fila.close ();
            db.close ();
            return null;
        }
    }

    public static ArrayList<Accidente_Transito> obtenerAccidentes(AdminSQLiteOpenHelper admin) {
        ArrayList<Accidente_Transito> accidentes = new ArrayList<> ();
        SQLiteDatabase db = admin.getWritableDatabase();
        Accidente_Transito accidente;
        @SuppressLint("Recycle") Cursor fila = db.rawQuery("select * from agenteTransito",null);
        if (fila.moveToFirst ()) {
            do {
                accidente = new Accidente_Transito ();
                accidente.setNumero (fila.getInt (0));
                accidente.setTipo (fila.getString (1));
                accidente.setDescripcion (fila.getString (2));
                accidente.setUbicacion (fila.getString (3));
                accidente.setFecha_registro (fila.getString (4));
                accidente.setHora_accidente (fila.getString (5));
                accidente.setHora_registro (fila.getString (6));
                accidente.setAgente_transito (Integer.parseInt (fila.getString (7)));
                accidentes.add (accidente);
            } while (fila.moveToNext ());
            fila.close ();
            db.close ();
            Timber.tag ("AccidentesDB").e (accidentes.toString ());
            return accidentes;
        } else {
            fila.close ();
            db.close ();
            return null;
        }
    }

    public static ArrayList<Conductor> obtenerConductor(AdminSQLiteOpenHelper admin) {
        ArrayList<Conductor> conductores = new ArrayList<> ();
        SQLiteDatabase db = admin.getWritableDatabase();
        Conductor conductor;
        @SuppressLint("Recycle") Cursor fila = db.rawQuery("select * from agenteTransito",null);
        if (fila.moveToFirst ()) {
            do {
                conductor = new Conductor ();
                conductor.setCedula (fila.getString (0));
                conductor.setNombres (fila.getString (1));
                conductor.setApellidos (fila.getString (2));
                conductor.setTipo_licencia (fila.getString (3));
                conductor.setCategoria_licencia (fila.getString (4));
                conductor.setFecha_emision_licencia (fila.getString (5));
                conductor.setFecha_caducidad_licencia (fila.getString (6));
                conductores.add (conductor);
            } while (fila.moveToNext ());
            fila.close ();
            db.close();
            Timber.tag ("ConductorDB").e (conductores.toString ());
            return conductores;
        } else {
            fila.close ();
            db.close ();
            return null;
        }
    }

    @SuppressLint("LogNotTimber")
    public static ArrayList<Vehiculo> obtenerrVehiculo(AdminSQLiteOpenHelper admin) {
        ArrayList<Vehiculo> vehiculos = new ArrayList<> ();
        SQLiteDatabase db = admin.getWritableDatabase();
        Vehiculo vehiculo;
        @SuppressLint("Recycle") Cursor fila = db.rawQuery("select * from agenteTransito",null);
        if (fila.moveToFirst ()) {
            do {
                vehiculo = new Vehiculo ();
                vehiculo.setPlaca (fila.getString (0));
                vehiculo.setMarca (fila.getString (1));
                vehiculo.setTipo (fila.getString (2));
                vehiculo.setColor (fila.getString (3));
                vehiculo.setFecha_matricula (fila.getString (4));
                vehiculo.setFecha_caducidad_matricula (fila.getString (5));
                vehiculos.add (vehiculo);
            } while (fila.moveToNext ());
            fila.close ();
            db.close();
            Log.e ("VehiculoDB", vehiculos.toString ());
            return vehiculos;
        } else {
            return null;
        }
    }

    public static ArrayList<Infraccion_Transito> obtenerInfracciones(AdminSQLiteOpenHelper admin) {
        ArrayList<Infraccion_Transito> infracciones = new ArrayList<> ();
        SQLiteDatabase db = admin.getWritableDatabase();
        Infraccion_Transito infraccion;
        @SuppressLint("Recycle") Cursor fila = db.rawQuery("select * from infraccionTransito",null);
        if (fila.moveToFirst ()) {
            do {
                Log.e ("FilaInfraccion", fila.getString (3));
                Log.e ("FilaInfraccion", fila.getString (4));
                infraccion = new Infraccion_Transito();
                infraccion.setNumrero_infraccion(fila.getInt (0));
                infraccion.setDescripcion(fila.getString (1));
                infraccion.setUbicacion (fila.getString (2));
                infraccion.setLatitud (Double.parseDouble (fila.getString (3)));
                infraccion.setLongitud (Double.parseDouble (fila.getString (4)));
                infraccion.setEstado (fila.getString (5));
                infraccion.setFecha_infraccion(fila.getString (6));
                infraccion.setHora_registro(fila.getString (7));
                infraccion.setHora_infraccion(fila.getString (8));
                infraccion.setAgente_transito(Integer.parseInt (fila.getString (9)));
                infraccion.setConductor(fila.getString (10));
                infraccion.setVehiculo(fila.getString (11));
                infraccion.setArticulos(fila.getString (12));
                infraccion.setIntento(Integer.parseInt (fila.getString (13)));
                infracciones.add(infraccion);
            } while (fila.moveToNext ());
            fila.close();
            db.close();
            Timber.tag("InfraccionDB").e(infracciones.toString ());
            return  infracciones;
        } else {
            fila.close();
            db.close();
            return null;
        }
    }

    @SuppressLint("LogNotTimber")
    public static boolean crearAccidente(RequestQueue request, Accidente_Transito accidente) {
        final boolean[] band = {false};
        JSONObject object = new JSONObject ();
        try {
            object.put ("NumeroAccidente",accidente.getNumero ());
            object.put ("TipoAccidente",accidente.getTipo () );
            object.put ("Descripcion", accidente.getDescripcion ());
            object.put ("Ubicacion",accidente.getUbicacion ());
            object.put ("Latitud", accidente.getLatitud ());
            object.put ("Longitud",accidente.getLongitud() );
            object.put ("Estado",accidente.getEstado () );
            object.put ("Fecha",accidente.getFecha_registro () );
            object.put ("Hora_Registro",accidente.getHora_registro () );
            object.put ("Hora_Accidente",  accidente.getHora_accidente ());
            object.put ("CodigoInfraccion",  accidente.getNumero ());
            Log.e ("CrearAccidente", object.toString());
            JsonObjectRequest objectRequest = new JsonObjectRequest (Request.Method.POST,
                    Constantes.URL_CREAR_ACCIDENTE, object, response -> {
                Log.e ("CrearAccidenteResp", response.toString());
                band[0] = true;
            }, error -> {
                Log.e ("CrearAccidenteResp", error.toString());
                band[0] = false;
            });
            request.add (objectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return band[0];
    }

    public static boolean crearInfraccion(RequestQueue request, Infraccion_Transito infraccion) {
        final boolean[] band = {false};
        JSONObject object = new JSONObject ();
        try {
            object.put ("NumeroInfraccion", infraccion.getNumrero_infraccion ());
            object.put ("Descripcion", "emitida");
            object.put ("Ubicacion", infraccion.getUbicacion ());
            object.put ("Latitud", String.valueOf (infraccion.getLatitud ()));
            object.put ("Longitud", String.valueOf (infraccion.getLongitud ()));
            object.put ("Estado", String.valueOf (infraccion.getEstado ()));
            object.put ("Fecha_Infraccion", infraccion.getFecha_infraccion ());
            object.put ("Hora_Infraccion", infraccion.getHora_registro ());
            Log.e ("ObjectInfraccion", object.toString ());
            JsonObjectRequest objectRequest = new JsonObjectRequest (Request.Method.POST,
                    Constantes.URL_CREAR_INFRACCION, object, response -> {
                Log.e ("CrearInfraccionResp", response.toString ());
                try {
                    String status = response.getString ("status");
                    if (status.equals ("ok"))
                        band[0] = true;
                } catch (JSONException e) {
                    e.printStackTrace ();
                }
            }, error -> {
                Log.e ("CrearInfraccinResp", error.toString ());
                band[0] = false;
            });
            request.add (objectRequest);
        } catch (JSONException e) {
            e.printStackTrace ();
        }
        return band[0];
    }

    public static boolean crearEvidencia(RequestQueue request, Evidencia evidencia) {
        final boolean[] band = {false};
        JSONObject object = new JSONObject ();
        try {
            object.put ("idEvidencia",evidencia.getId_evidencia ());
            object.put ("video",evidencia.getVideo ());
            object.put ("foto",evidencia.getFoto ());
            object.put ("audio",evidencia.getAudio ());
            object.put ("codigoInfraccion",evidencia.getCodigoInfraccion ());
            @SuppressLint("LogNotTimber") JsonObjectRequest objectRequest = new JsonObjectRequest (Request.Method.POST, Constantes.URL_CREAR_EVIDENCIA, object, response -> {
                Log.e ("Evidencia.Response", response.toString ());
                try {
                    String status = response.getString ("status");
                    if (status.equals ("ok")){
                        band[0] = true;
                    }
                } catch (JSONException e) {
                    e.printStackTrace ();
                }
            }, error -> {
                Log.e ("EvidenciaError", error.toString ());
            });
            request.add (objectRequest);
        } catch (JSONException e) {
            e.printStackTrace ();
        }
        return band[0];
    }

}

package Utilidades;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;

import Modelos.Agente_Transito;
import Modelos.Conductor;
import Modelos.Vehiculo;

public class Utilidades {

    public static Vehiculo obtenerVehiculo(JSONObject object) {
        try {
            String placaV = object.getString ("Placa");
            String marcaV = object.getString ("Marca");
            String tipoV = object.getString ("Tipo");
            String colorV = object.getString ("Color");
            String emision = object.getString ("FechaMatricula");
            String caducidad = object.getString ("FechaCaducidadMatricula");
            return new Vehiculo (placaV, tipoV, marcaV, colorV, emision, caducidad);
        } catch (JSONException e) {
            e.printStackTrace ();
            return null;
        }
    }

    public static String obtenerFechaActual() {
        Calendar calendar = Calendar.getInstance ();
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd");
        return sdf.format (calendar.getTime());
    }

    /**
     * Permite convertir un String en fecha (Date).
     * @param fecha Cadena de fecha dd/MM/yyyy
     * @return Objeto Date
     */
    public static Date convertirFecha(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex) {
            System.out.println(ex);
        }
        return fechaDate;
    }

    public static String obtenerHoraActual() {
        Date date = new Date ();
        SimpleDateFormat sdf = new SimpleDateFormat ("hh:mm:ss");
        return sdf.format (date.getTime ());
    }

    /**
     * Permite convertir un String en fecha (Date).
     * @param fecha Cadena de fecha dd/MM/yyyy
     * @return Objeto Date111111111111111111
     */
    public static Date convertirHora(String fecha) {
        SimpleDateFormat formato = new SimpleDateFormat("hh:mm:ss");
        Date fechaDate = null;
        try {
            fechaDate = formato.parse(fecha);
        }
        catch (ParseException ex) {
            System.out.println(ex);
        }
        return fechaDate;
    }

    //Cuando se sincronice informacion del agente
    public static Agente_Transito obtenerAgente(JSONObject obj) throws JSONException {
        String cedula = obj.getString ("Cedula");
        int codigo = obj.getInt ("Codigo_Agente");
        String nombres = obj.getString ("Nombres");
        String apellidos = obj.getString ("Apellidos");
        String clave = obj.getString ("Clave");
        return new Agente_Transito (codigo, cedula, clave, nombres, apellidos);
    }

    public static Conductor obtenerConductor(JSONObject obj) throws JSONException {
        String cedula = obj.getString ("Cedula");
        String nombres = obj.getString ("Nombres");
        String apellidos = obj.getString ("Apellidos");
        String tipoLicencia = obj.getString ("TipoLicencia");
        String categoriaLicencia = obj.getString ("CategoriaLicencia");
        String fechaEmisionLicencia = obj.getString ("FechaEmisionLicencia");
        String fechaCaducidadLicencia = obj.getString ("FechaCaducidadLicencia");
        String puntos = obj.getString ("Puntos");
        return new Conductor (cedula, nombres, apellidos, tipoLicencia,
                categoriaLicencia, fechaEmisionLicencia, fechaCaducidadLicencia, puntos);
    }

}

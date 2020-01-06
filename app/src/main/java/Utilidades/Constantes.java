package Utilidades;

import Modelos.Accidente_Transito;
import Modelos.Agente_Transito;
import Modelos.Articulos_Coip;
import Modelos.Conductor;
import Modelos.Vehiculo;
import gestionar_almacenamiento.AdminSQLiteOpenHelper;

public class Constantes {

    //SERVER
    private static final String IP_SERVER = "http://18.228.40.250/";
    public static final String URL_CREAR_INFRACCION = IP_SERVER + "servicio_web/crearinfraccion/";
    public static final String URL_CREAR_ACCIDENTE = IP_SERVER + "servicio_web/crearAccidente/";
    public static final String URL_CREAR_EVIDENCIA = IP_SERVER + "Gestionar_Evidencia/";
    public static final String URL_GET_CONDUCTOR = IP_SERVER + "servicio_web/detalleConductor/";
    public static final String URL_GET_VEHICULO = IP_SERVER + "servicio_web/detalleVehiculo/";
    public static final String URL_GET_AGENTE = IP_SERVER + "servicio_web/detalleAgente/";
    public static final String URL_GET_NUM_INFRA = IP_SERVER + "servicio_web/detalleNumeroInfraccion/";
    public static final String DB = "administracion";

    //CONSTANTES
    public static Conductor conductor;
    public static Vehiculo vehiculo;
    public static Articulos_Coip articulo;
    public static String audio;
    public static String foto;
    public static String video;
    public static String ubicacion;
    public static Double lat;
    public static Double lng;
    public static Agente_Transito agente;
    public static int intento;
    public static Accidente_Transito accidente;
    public static int numaccidente=40;
    public static int numinfraccion=99;

    public static final String MSG_ERROR = "Campo obligatorio";
    public static AdminSQLiteOpenHelper helper;

}

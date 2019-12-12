package Modelos;

import java.util.Date;

public class Accidente_Transito {

    private int numero;
    private String tipo;
    private String descripcion;
    private String ubicacion;
    private int intento;
    private double latitud;
    private double longitud;
    private String estado;
    private String fecha_accidente;
    private String fecha_registro;
    private String hora_accidente;
    private String hora_registro;
    private int agente_transito;
    private Conductor conductor;
    private Vehiculo vehiculo;
    private Articulos_Coip articulos;

    public Accidente_Transito(int numero, String tipo, String descripcion, String ubicacion, int intento,
                              double latitud, double longitud, String estado, String fecha_accidente,
                              String fecha_registro, String hora_accidente, String hora_registro) {
        this.numero = numero;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.intento = intento;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
        this.fecha_accidente = fecha_accidente;
        this.fecha_registro = fecha_registro;
        this.hora_accidente = hora_accidente;
        this.hora_registro = hora_registro;
    }

    public Accidente_Transito() {

    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public int getIntento() {
        return intento;
    }

    public void setIntento(int intento) {
        this.intento = intento;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha_accidente() {
        return fecha_accidente;
    }

    public void setFecha_accidente(String fecha_accidente) {
        this.fecha_accidente = fecha_accidente;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public String getHora_accidente() {
        return hora_accidente;
    }

    public void setHora_accidente(String hora_accidente) {
        this.hora_accidente = hora_accidente;
    }

    public String getHora_registro() {
        return hora_registro;
    }

    public void setHora_registro(String hora_registro) {
        this.hora_registro = hora_registro;
    }

    public int getAgente_transito() {
        return agente_transito;
    }

    public void setAgente_transito(int agente_transito) {
        this.agente_transito = agente_transito;
    }

    public Conductor getConductor() {
        return conductor;
    }

    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Articulos_Coip getArticulos() {
        return articulos;
    }

    public void setArticulos(Articulos_Coip articulos) {
        this.articulos = articulos;
    }
    @Override
    public String toString() {
        return "Accidente_Transito{" +
                "numero=" + numero +
                ", tipo='" + tipo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", intento=" + intento +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", estado='" + estado + '\'' +
                ", fecha_accidente=" + fecha_accidente +
                ", fecha_registro=" + fecha_registro +
                ", hora_accidente=" + hora_accidente +
                ", hora_registro=" + hora_registro +
                ", agente_transito=" + agente_transito +
                ", conductor=" + conductor +
                ", vehiculo=" + vehiculo +
                ", articulos=" + articulos +
                '}';
    }
}

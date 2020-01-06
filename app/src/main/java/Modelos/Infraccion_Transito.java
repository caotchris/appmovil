package Modelos;

public class Infraccion_Transito {

    private int numrero_infraccion;
    private String descripcion;
    private String ubicacion;
    private int intento;
    private double latitud;
    private double longitud;
    private String estado;
    private String fecha_infraccion;
    private String fecha_registro;
    private String hora_infraccion;
    private String hora_registro;
    private int agente_transito;
    private int conductor;
    private String vehiculo;
    private int articulos;

    public Infraccion_Transito(int numrero_infraccion, String descripcion, String ubicacion, int intento,
                               double latitud, double longitud, String estado, String fecha_infraccion,
                               String hora_infraccion, String hora_registro) {
        this.numrero_infraccion = numrero_infraccion;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.intento = intento;
        this.latitud = latitud;
        this.longitud = longitud;
        this.estado = estado;
        this.fecha_infraccion = fecha_infraccion;
        this.fecha_registro = fecha_registro;
        this.hora_infraccion = hora_infraccion;
        this.hora_registro = hora_registro;    // Es igual a la hora de detencion
    }

    public Infraccion_Transito() {
    }

    public void setIntento(int intento) {
        this.intento = intento;
    }

    public String getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(String fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public int getNumrero_infraccion() {
        return numrero_infraccion;
    }

    public void setNumrero_infraccion(int numrero_infraccion) {
        this.numrero_infraccion = numrero_infraccion;
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

    public String getFecha_infraccion() {
        return fecha_infraccion;
    }

    public void setFecha_infraccion(String fecha_infraccion) {
        this.fecha_infraccion = fecha_infraccion;
    }

    public String getHora_infraccion() {
        return hora_infraccion;
    }

    public void setHora_infraccion(String hora_infraccion) {
        this.hora_infraccion = hora_infraccion;
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

    public int getConductor() {
        return conductor;
    }

    public void setConductor(int conductor) {
        this.conductor = conductor;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public int getArticulos() {
        return articulos;
    }

    public void setArticulos(int articulos) {
        this.articulos = articulos;
    }

    @Override
    public String toString() {
        return "Infraccion_Transito{" +
                "numrero_infraccion='" + numrero_infraccion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", latitud=" + latitud +
                ", longitud=" + longitud +
                ", fecha_infraccion='" + fecha_infraccion + '\'' +
                ", fecha_registro='" + fecha_registro + '\'' +
                ", hora_infraccion='" + hora_infraccion + '\'' +
                ", hora_registro='" + hora_registro + '\'' +
                ", estado=" + estado +
                ", agente_transito=" + agente_transito +
                ", conductor=" + conductor +
                ", vehiculo=" + vehiculo +
                ", artuculos=" + articulos +
                '}';
    }
}

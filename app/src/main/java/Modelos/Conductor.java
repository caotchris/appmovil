package Modelos;

public class Conductor {

    private String cedula;
    private String nombres;
    private String apellidos;
    private String tipo_licencia;
    private String categoria_licencia;
    private String fecha_emision_licencia;
    private String fecha_caducidad_licencia;

    public Conductor(String cedula, String nombres, String apellidos, String tipo_licencia,
                     String categoria_licencia, String fecha_emision_licencia, String fecha_caducidad_licencia) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipo_licencia = tipo_licencia;
        this.categoria_licencia = categoria_licencia;
        this.fecha_emision_licencia = fecha_emision_licencia;
        this.fecha_caducidad_licencia = fecha_caducidad_licencia;
    }

    public Conductor() {

    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTipo_licencia() {
        return tipo_licencia;
    }

    public void setTipo_licencia(String tipo_licencia) {
        this.tipo_licencia = tipo_licencia;
    }

    public String getCategoria_licencia() {
        return categoria_licencia;
    }

    public void setCategoria_licencia(String categoria_licencia) {
        this.categoria_licencia = categoria_licencia;
    }

    public String getFecha_emision_licencia() {
        return fecha_emision_licencia;
    }

    public void setFecha_emision_licencia(String fecha_emision_licencia) {
        this.fecha_emision_licencia = fecha_emision_licencia;
    }

    public String getFecha_caducidad_licencia() {
        return fecha_caducidad_licencia;
    }

    public void setFecha_caducidad_licencia(String fecha_caducidad_licencia) {
        this.fecha_caducidad_licencia = fecha_caducidad_licencia;
    }

    @Override
    public String toString() {
        return "Conductor{" +
                "cedula='" + cedula + '\'' +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", tipo_licencia='" + tipo_licencia + '\'' +
                ", categoria_licencia='" + categoria_licencia + '\'' +
                ", fecha_emision_licencia=" + fecha_emision_licencia +
                ", fecha_caducidad_licencia=" + fecha_caducidad_licencia +
                '}';
    }

}

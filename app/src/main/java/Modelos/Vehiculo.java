package Modelos;

import java.util.Date;

public class Vehiculo {

    private String placa;
    private String tipo;
    private String marca;
    private String color;
    private String fecha_matricula;
    private String fecha_caducidad_matricula;
    private int infraccion;

    public Vehiculo() {}

    public Vehiculo(String placa, String tipo, String marca, String color, String fecha_matricula, String fecha_caducidad_matricula) {
        this.placa = placa;
        this.tipo = tipo;
        this.marca = marca;
        this.color = color;
        this.fecha_matricula = fecha_matricula;
        this.fecha_caducidad_matricula = fecha_caducidad_matricula;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFecha_matricula() {
        return fecha_matricula;
    }

    public void setFecha_matricula(String fecha_matricula) {
        this.fecha_matricula = fecha_matricula;
    }

    public String getFecha_caducidad_matricula() {
        return fecha_caducidad_matricula;
    }

    public void setFecha_caducidad_matricula(String fecha_caducidad_matricula) {
        this.fecha_caducidad_matricula = fecha_caducidad_matricula;
    }

    public int getInfraccion() {
        return infraccion;
    }

    public void setInfraccion(int infraccion) {
        this.infraccion = infraccion;
    }

    @Override
    public String toString() {
        return "Vehiculo{" +
                "numero_placa='" + placa + '\'' +
                ", tipo='" + tipo + '\'' +
                ", marca='" + marca + '\'' +
                ", color='" + color + '\'' +
                ", fecha_matricula='" + fecha_matricula + '\'' +
                ", fecha_caducidad_matricula='" + fecha_caducidad_matricula + '\'' +
                '}';
    }
}

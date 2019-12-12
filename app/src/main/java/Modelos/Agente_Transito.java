package Modelos;

public class Agente_Transito {

    private int codigo_agente;
    private String cedula;
    private String clave;
    private String nombre;
    private String apellidos;
    private String firma;

    public Agente_Transito(int codigo_agente, String cedula, String clave, String nombre, String apellidos) {
        this.codigo_agente = codigo_agente;
        this.cedula = cedula;
        this.clave = clave;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public int getCodigo_agente() {
        return codigo_agente;
    }

    public void setCodigo_agente(int codigo_agente) {
        this.codigo_agente = codigo_agente;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    @Override
    public String toString() {
        return "Agente_Transito{" +
                "codigo_agente='" + codigo_agente + '\'' +
                ", cedula='" + cedula + '\'' +
                ", clave='" + clave + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", firma='" + firma + '\'' +
                '}';
    }
}

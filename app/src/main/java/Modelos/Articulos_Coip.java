package Modelos;

public class Articulos_Coip {

    private String articulo;
    private String numeral;
    private String inciso;
    private String descripcion;

    public Articulos_Coip(String articulo, String numeral, String inciso, String descripcion) {
        this.articulo = articulo;
        this.numeral = numeral;
        this.inciso = inciso;
        this.descripcion = descripcion;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public String getNumeral() {
        return numeral;
    }

    public void setNumeral(String numeral) {
        this.numeral = numeral;
    }

    public String getInciso() {
        return inciso;
    }

    public void setInciso(String inciso) {
        this.inciso = inciso;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Articulos_Coip{" +
                "articulo='" + articulo + '\'' +
                ", numeral='" + numeral + '\'' +
                ", inciso='" + inciso + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}

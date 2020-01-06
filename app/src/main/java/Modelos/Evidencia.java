package Modelos;

public class Evidencia {

    private int id_evidencia;
    private String audio;
    private String foto;
    private String video;
    private int codigoInfraccion;

    public Evidencia (int id_evidencia, String audio, String foto, String video, int codigoInfraccion) {
        this.id_evidencia = id_evidencia;
        this.audio = audio;
        this.foto = foto;
        this.video = video;
        this.codigoInfraccion = codigoInfraccion;
    }

    public Evidencia() {

    }

    public int getId_evidencia() {
        return id_evidencia;
    }

    public void setId_evidencia(int id_evidencia) {
        this.id_evidencia = id_evidencia;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public int getCodigoInfraccion() {
        return codigoInfraccion;
    }

    public void setCodigoInfraccion(int codigoInfraccion) {
        this.codigoInfraccion = codigoInfraccion;
    }

    @Override
    public String toString() {
        return "Evidencia{" +
                "id_evidencia='" + id_evidencia + '\'' +
                ", audio='" + audio + '\'' +
                ", foto='" + foto + '\'' +
                ", video='" + video + '\'' +
                ", codigoInfraccion='" + codigoInfraccion + '\'' +
                '}';
    }
}

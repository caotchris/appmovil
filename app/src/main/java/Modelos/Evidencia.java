package Modelos;

public class Evidencia {

    private String id_evidencia;
    private String audio;
    private String foto;
    private String video;
    private String codigoInfraccion;

    public Evidencia() {
    }

    public Evidencia(String id_evidencia, String audio, String foto, String video, String codigoInfraccion) {
        this.id_evidencia = id_evidencia;
        this.audio = audio;
        this.foto = foto;
        this.video = video;
        this.codigoInfraccion = codigoInfraccion;
    }

    public String getId_evidencia() {
        return id_evidencia;
    }

    public void setId_evidencia(String id_evidencia) {
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

    public String getCodigoInfraccion() {
        return codigoInfraccion;
    }

    public void setCodigoInfraccion(String codigoInfraccion) {
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

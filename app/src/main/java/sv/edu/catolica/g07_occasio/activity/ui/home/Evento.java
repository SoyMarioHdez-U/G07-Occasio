package sv.edu.catolica.g07_occasio.activity.ui.home;

public class Evento {
    public String idEvento;
    public String nombreEvento;
    public String organizador;
    public String fechaEvento;
    public String horaEvento;
    public String categoria;
    public String urlImagen;

    public Evento(String idEvento, String nombreEvento, String organizador,
                  String fechaEvento, String horaEvento, String categoria, String urlImagen) {
        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
        this.organizador = organizador;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.categoria = categoria;
        this.urlImagen = urlImagen;
    }
}

package sv.edu.catolica.g07_occasio.activity.ui.home;

import java.util.ArrayList;

public class Evento {
    public String idEvento;
    public String nombreEvento;
    public String organizador;
    public String fechaEvento;
    public String horaEvento;
    public String lugar;
    public String precios;
    public int aforoMinimo;
    public int aforoMaximo;
    public String categoria;
    public String urlImagen;
    public ArrayList<String> fotografias;

    public Evento(String idEvento, String nombreEvento, String organizador,
                  String fechaEvento, String horaEvento, String lugar,
                  String precios, int aforoMinimo, int aforoMaximo,
                  String categoria, String urlImagen, ArrayList<String> fotografias) {

        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
        this.organizador = organizador;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.lugar = lugar;
        this.precios = precios;
        this.aforoMinimo = aforoMinimo;
        this.aforoMaximo = aforoMaximo;
        this.categoria = categoria;
        this.urlImagen = urlImagen;
        this.fotografias = fotografias;
    }
}

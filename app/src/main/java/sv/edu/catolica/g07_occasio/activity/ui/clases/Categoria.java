package sv.edu.catolica.g07_occasio.activity.ui.clases;

public class Categoria {
    private final String idCategoria;
    private final String nombreCategoria;
    private final String urlCategoria;

    public Categoria(String idCategoria, String nombreCategoria, String urlCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.urlCategoria = urlCategoria;
    }

    public String getIdCategoria() {
        return idCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public String getUrlCategoria() {
        return urlCategoria;
    }
}
package Clases;

public class eventos {
    private int idEvento;
    private int idUsuario;
    private String nombreEvento;
    private String portadaUrl;
    private String lugar;
    private String direccion;
    private String fechaEvento;  // Puedes usar Date si deseas manejar fechas con más precisión
    private String horaEvento;
    private int idCategoria;
    private String descripcion;
    private int edadMax;
    private int edadMin;
    private boolean aceptarManualmente;
    private int aforoMax;
    private int aforoMin;
    private int tiempoCancelacion;
    private boolean galeriaEvento;
    private String precios;

    public eventos(int idEvento, int idUsuario, String nombreEvento, String portadaUrl, String lugar, String direccion,
                   String fechaEvento, String horaEvento, int idCategoria, String descripcion, int edadMax, int edadMin,
                   boolean aceptarManualmente, int aforoMax, int aforoMin, int tiempoCancelacion, boolean galeriaEvento, String precios) {
        this.idEvento = idEvento;
        this.idUsuario = idUsuario;
        this.nombreEvento = nombreEvento;
        this.portadaUrl = portadaUrl;
        this.lugar = lugar;
        this.direccion = direccion;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
        this.edadMax = edadMax;
        this.edadMin = edadMin;
        this.aceptarManualmente = aceptarManualmente;
        this.aforoMax = aforoMax;
        this.aforoMin = aforoMin;
        this.tiempoCancelacion = tiempoCancelacion;
        this.galeriaEvento = galeriaEvento;
        this.precios = precios;
    }

    public eventos(int idUsuario, String nombreEvento, String portadaUrl, String lugar, String direccion, String fechaEvento,
                   String horaEvento, int idCategoria, String descripcion, int edadMax, int edadMin, boolean aceptarManualmente,
                   int aforoMax, int aforoMin, int tiempoCancelacion, boolean galeriaEvento, String precios) {
        this.idUsuario = idUsuario;
        this.nombreEvento = nombreEvento;
        this.portadaUrl = portadaUrl;
        this.lugar = lugar;
        this.direccion = direccion;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.idCategoria = idCategoria;
        this.descripcion = descripcion;
        this.edadMax = edadMax;
        this.edadMin = edadMin;
        this.aceptarManualmente = aceptarManualmente;
        this.aforoMax = aforoMax;
        this.aforoMin = aforoMin;
        this.tiempoCancelacion = tiempoCancelacion;
        this.galeriaEvento = galeriaEvento;
        this.precios = precios;
    }

    public eventos() {
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
    }

    public String getPortadaUrl() {
        return portadaUrl;
    }

    public void setPortadaUrl(String portadaUrl) {
        this.portadaUrl = portadaUrl;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(String fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEdadMax() {
        return edadMax;
    }

    public void setEdadMax(int edadMax) {
        this.edadMax = edadMax;
    }

    public int getEdadMin() {
        return edadMin;
    }

    public void setEdadMin(int edadMin) {
        this.edadMin = edadMin;
    }

    public boolean isAceptarManualmente() {
        return aceptarManualmente;
    }

    public void setAceptarManualmente(boolean aceptarManualmente) {
        this.aceptarManualmente = aceptarManualmente;
    }

    public int getAforoMax() {
        return aforoMax;
    }

    public void setAforoMax(int aforoMax) {
        this.aforoMax = aforoMax;
    }

    public int getAforoMin() {
        return aforoMin;
    }

    public void setAforoMin(int aforoMin) {
        this.aforoMin = aforoMin;
    }

    public int getTiempoCancelacion() {
        return tiempoCancelacion;
    }

    public void setTiempoCancelacion(int tiempoCancelacion) {
        this.tiempoCancelacion = tiempoCancelacion;
    }

    public boolean isGaleriaEvento() {
        return galeriaEvento;
    }

    public void setGaleriaEvento(boolean galeriaEvento) {
        this.galeriaEvento = galeriaEvento;
    }

    public String getPrecios() {
        return precios;
    }

    public void setPrecios(String precios) {
        this.precios = precios;
    }
}
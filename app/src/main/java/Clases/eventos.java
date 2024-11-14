package Clases;

public class eventos
{
    private int idEvento;
    private String nombreEvento;
    private String lugar;
    private String direccion;
    private String fechaEvento;  // Puedes usar Date si deseas manejar fechas con más precisión
    private String horaEvento;
    private int idCategoria;
    private int aforoMax;
    private int aforoMin;
    private int tiempoCancelacion;
    private boolean reservaDerechoAdmision;
    private int edadMax;
    private int edadMin;
    private String galeriaEvento;
    private String galeriaEventoUsers;
    private boolean eventoGratuito;
    private int idUsuario;
    private String portadaUrl;

    public eventos(int idEvento, String nombreEvento, String lugar, String direccion, String fechaEvento, String horaEvento,
                   int idCategoria, int aforoMax, int aforoMin, boolean reservaDerechoAdmision, int tiempoCancelacion,
                   int edadMax, int edadMin, String galeriaEvento, String galeriaEventoUsers, boolean eventoGratuito, int idUsuario, String portadaUrl)
    {
        this.idEvento = idEvento;
        this.nombreEvento = nombreEvento;
        this.lugar = lugar;
        this.direccion = direccion;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.idCategoria = idCategoria;
        this.aforoMax = aforoMax;
        this.aforoMin = aforoMin;
        this.tiempoCancelacion = tiempoCancelacion;
        this.reservaDerechoAdmision = reservaDerechoAdmision;
        this.edadMax = edadMax;
        this.edadMin = edadMin;
        this.galeriaEvento = galeriaEvento;
        this.galeriaEventoUsers = galeriaEventoUsers;
        this.eventoGratuito = eventoGratuito;
        this.idUsuario = idUsuario;
        this.portadaUrl = portadaUrl;
    }

    public eventos(String nombreEvento, String lugar, String direccion, String fechaEvento, String horaEvento,
                   int idCategoria, int aforoMax, int aforoMin, boolean reservaDerechoAdmision, int tiempoCancelacion,
                   int edadMax, int edadMin, String galeriaEvento, String galeriaEventoUsers, boolean eventoGratuito, int idUsuario, String portadaUrl) {
        this.nombreEvento = nombreEvento;
        this.lugar = lugar;
        this.direccion = direccion;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.idCategoria = idCategoria;
        this.aforoMax = aforoMax;
        this.aforoMin = aforoMin;
        this.reservaDerechoAdmision = reservaDerechoAdmision;
        this.tiempoCancelacion = tiempoCancelacion;
        this.edadMax = edadMax;
        this.edadMin = edadMin;
        this.galeriaEvento = galeriaEvento;
        this.galeriaEventoUsers = galeriaEventoUsers;
        this.eventoGratuito = eventoGratuito;
        this.idUsuario = idUsuario;
        this.portadaUrl = portadaUrl;
    }

    public eventos() { }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
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

    public boolean isReservaDerechoAdmision() {
        return reservaDerechoAdmision;
    }

    public void setReservaDerechoAdmision(boolean reservaDerechoAdmision) {
        this.reservaDerechoAdmision = reservaDerechoAdmision;
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

    public String getGaleriaEvento() {
        return galeriaEvento;
    }

    public void setGaleriaEvento(String galeriaEvento) {
        this.galeriaEvento = galeriaEvento;
    }

    public String getGaleriaEventoUsers() {
        return galeriaEventoUsers;
    }

    public void setGaleriaEventoUsers(String galeriaEventoUsers) {
        this.galeriaEventoUsers = galeriaEventoUsers;
    }

    public boolean isEventoGratuito() {
        return eventoGratuito;
    }

    public void setEventoGratuito(boolean eventoGratuito) {
        this.eventoGratuito = eventoGratuito;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPortadaUrl() {
        return portadaUrl;
    }

    public void setPortadaUrl(String portadaUrl) {
        this.portadaUrl = portadaUrl;
    }
}

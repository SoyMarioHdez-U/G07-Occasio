package Clases;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import sv.edu.catolica.g07_occasio.CrearEvento;
import sv.edu.catolica.g07_occasio.MainActivity;

public class eventos {

    String resultado;


    private int idEvento;
    private int idUsuario;
    private String nombre_evento;
    private String descripcion;
    private String fecha_evento;  // Puedes usar Date si deseas manejar fechas con más precisión
    private String hora_evento;
    private String lugar;
    private String direccion;
    private String precios;
    private int id_categoria;
    private String asistencia_aprobada;
    private int aforo_minimo;
    private int aforo_maximo;
    private String restriccion_edad;
    private String url_imagen;
    private String fecha_creacion;

    public eventos(int idEvento, int idUsuario, String nombre_evento, String descripcion, String hora_evento, String fecha_evento,
                   String lugar, String direccion, String precios, int id_categoria, String asistencia_aprobada, int aforo_minimo, int aforo_maximo,
                   String restriccion_edad, String url_imagen, String fecha_creacion)
    {
        this.idEvento = idEvento;
        this.idUsuario = idUsuario;
        this.nombre_evento = nombre_evento;
        this.descripcion = descripcion;
        this.hora_evento = hora_evento;
        this.fecha_evento = fecha_evento;
        this.lugar = lugar;
        this.direccion = direccion;
        this.precios = precios;
        this.id_categoria = id_categoria;
        this.asistencia_aprobada = asistencia_aprobada;
        this.aforo_minimo = aforo_minimo;
        this.aforo_maximo = aforo_maximo;
        this.restriccion_edad = restriccion_edad;
        this.url_imagen = url_imagen;
        this.fecha_creacion = fecha_creacion;
    }

    public eventos(int idUsuario, String nombre_evento, String descripcion, String fecha_evento, String hora_evento, String lugar,
                   String direccion, String precios, int id_categoria, String asistencia_aprobada, int aforo_minimo, int aforo_maximo,
                   String restriccion_edad, String url_imagen, String fecha_creacion)
    {
        this.idUsuario = idUsuario;
        this.nombre_evento = nombre_evento;
        this.descripcion = descripcion;
        this.fecha_evento = fecha_evento;
        this.hora_evento = hora_evento;
        this.lugar = lugar;
        this.direccion = direccion;
        this.precios = precios;
        this.id_categoria = id_categoria;
        this.asistencia_aprobada = asistencia_aprobada;
        this.aforo_minimo = aforo_minimo;
        this.aforo_maximo = aforo_maximo;
        this.restriccion_edad = restriccion_edad;
        this.url_imagen = url_imagen;
        this.fecha_creacion = fecha_creacion;
    }

    public eventos() { }

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

    public String getNombre_evento() {
        return nombre_evento;
    }

    public void setNombre_evento(String nombre_evento) {
        this.nombre_evento = nombre_evento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha_evento() {
        return fecha_evento;
    }

    public void setFecha_evento(String fecha_evento) {
        this.fecha_evento = fecha_evento;
    }

    public String getHora_evento() {
        return hora_evento;
    }

    public void setHora_evento(String hora_evento) {
        this.hora_evento = hora_evento;
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

    public String getPrecios() {
        return precios;
    }

    public void setPrecios(String precios) {
        this.precios = precios;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getAsistencia_aprobada() {
        return asistencia_aprobada;
    }

    public void setAsistencia_aprobada(String asistencia_aprobada) {
        this.asistencia_aprobada = asistencia_aprobada;
    }

    public int getAforo_minimo() {
        return aforo_minimo;
    }

    public void setAforo_minimo(int aforo_minimo) {
        this.aforo_minimo = aforo_minimo;
    }

    public int getAforo_maximo() {
        return aforo_maximo;
    }

    public void setAforo_maximo(int aforo_maximo) {
        this.aforo_maximo = aforo_maximo;
    }

    public String getRestriccion_edad() {
        return restriccion_edad;
    }

    public void setRestriccion_edad(String restriccion_edad) {
        this.restriccion_edad = restriccion_edad;
    }

    public String getUrl_imagen() {
        return url_imagen;
    }

    public void setUrl_imagen(String url_imagen) {
        this.url_imagen = url_imagen;
    }

    public String getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(String fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

}
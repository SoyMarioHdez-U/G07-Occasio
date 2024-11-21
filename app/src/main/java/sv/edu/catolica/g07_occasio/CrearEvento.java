package sv.edu.catolica.g07_occasio;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import sv.edu.catolica.g07_occasio.clases.categorias;
import sv.edu.catolica.g07_occasio.clases.eventos;
import cz.msebera.android.httpclient.Header;

public class CrearEvento extends AppCompatActivity {

    ImageView portada;

    EditText nombreEvento, lugarEvento, direccionEvento, fechaEvento, horaEvento, descripcionEvento, aforoMin, aforoMax, precios;

    Spinner categoriaEvento;

    TextView texto;

    CheckBox aforominimo, aforomaximo;

    Switch asistenciaAprobada, restriccionEdad, eventogratis;

    private static int TAKE_PICTURE = 500, SELECT_PICTURE = 600;

    String url, portadaurl, resultado;
    ;

    eventos evento;

    ArrayList<categorias> categorias;

    private Uri selectedImageUri; // Para almacenar el Uri de la imagen seleccionada
    private Bitmap capturedImageBitmap; // Para almacenar el Bitmap si tomaste una foto

    String IP ; //se cambia por la ip de la máquina en la que está el servidor(hecho en casa)



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_crear_evento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        evento = new eventos();

        IP = "192.168.1.8";

        categorias  = new ArrayList<>();

        portada = findViewById(R.id.bannerEvento);

        nombreEvento = findViewById(R.id.etNombreEvento);
        lugarEvento = findViewById(R.id.etLugarEvento);
        direccionEvento = findViewById(R.id.etDireccionEvento);
        fechaEvento = findViewById(R.id.etFecha);
        horaEvento = findViewById(R.id.etHora);
        aforoMin = findViewById(R.id.etAforoMinimo);
        aforoMax = findViewById(R.id.etAforoMaximo);
        descripcionEvento = findViewById(R.id.etDescripcion);
        precios = findViewById(R.id.etPrecio);

        categoriaEvento = findViewById(R.id.spCategoria);

        texto = findViewById(R.id.tvSubirPortada);

        aforominimo = findViewById(R.id.ckbAforoMinimo);
        aforomaximo = findViewById(R.id.ckbAforoMaximo);

        asistenciaAprobada = findViewById(R.id.swAceptarSolicitudManualmente);
        restriccionEdad = findViewById(R.id.swpregunta);
        eventogratis = findViewById(R.id.swEventoGratis);

        cargarcat();
    }


    public void seleccionaImagen(View view)
    {
        ImageView foto, imagen;

        AlertDialog.Builder Seleccion = new AlertDialog.Builder(this);

        LayoutInflater inflater =this.getLayoutInflater();
        View vista = inflater.inflate(R.layout.dialogo_seleccionarimagen, null);

        Seleccion.setView(vista);
        final AlertDialog dialog = Seleccion.create();

        foto = vista.findViewById(R.id.tvFoto);
        imagen = vista.findViewById(R.id.tvGaleria);

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                Intent capturarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (capturarFoto.resolveActivity(getPackageManager()) != null)
                {
                    startActivityForResult(capturarFoto, TAKE_PICTURE);
                }

            }
        });

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                Intent seleccionaImagen = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(seleccionaImagen, SELECT_PICTURE);

            }
        });

        dialog.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == TAKE_PICTURE && resultCode == RESULT_OK)
        {
            Bitmap imgCapturada = (Bitmap) data.getExtras().get("data");
            capturedImageBitmap = imgCapturada;
            portada.setImageBitmap(imgCapturada);
            texto.setText("");
        }
        else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK)
        {
            selectedImageUri = data.getData(); // Almacena el URI
            portada.setImageURI(selectedImageUri);
            texto.setText("");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void crearEvento(View view) {
        int idUsu = 1;

        // Obtener la fecha de creación del evento
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String fechaCreacion = sdf.format(Calendar.getInstance().getTime());

        // Validar campos obligatorios
        if (nombreEvento.getText().length() == 0 || fechaEvento.getText().length() == 0 || horaEvento.getText().length() == 0) {
            AlertDialog.Builder advertencia = new AlertDialog.Builder(this);
            advertencia.setTitle("Empty fields");
            advertencia.setMessage("Make sure you have filled out all the important fields like the name, date, and hour");
            advertencia.show();
            return; // Salir si los campos obligatorios están vacíos
        }

        // Inicializar el objeto Evento si no está inicializado


        // Asignar valores al evento
        try {
            evento.setIdUsuario(idUsu);
            evento.setNombre_evento(nombreEvento.getText().toString());
            evento.setDescripcion(descripcionEvento.getText().toString());
            evento.setFecha_evento(fechaEvento.getText().toString());
            evento.setHora_evento(horaEvento.getText().toString());
            evento.setLugar(lugarEvento.getText().toString());
            evento.setDireccion(direccionEvento.getText().toString());
            evento.setPrecios(precios.getText().toString());
            evento.setFecha_creacion(fechaCreacion);

            // Validar y asignar categoría
            for (categorias categoria : categorias) {
                if (categoria.getNombre_categoria().equals(categoriaEvento.getSelectedItem().toString())) {
                    evento.setId_categoria(categoria.getId_categoria());
                    break;
                }
            }

            // Validar asistencia aprobada
            evento.setAsistencia_aprobada(asistenciaAprobada.isChecked() ? "aprobada" : "libre");

            // Validar aforo mínimo
            if (aforominimo.isChecked()) {
                String aforoMinText = aforoMin.getText().toString();
                if (aforoMinText.isEmpty() || !TextUtils.isDigitsOnly(aforoMinText)) {
                    AlertDialog.Builder advertencia = new AlertDialog.Builder(this);
                    advertencia.setTitle("Invalid input");
                    advertencia.setMessage("Minimum capacity must be a valid number.");
                    advertencia.show();
                    return;
                }
                evento.setAforo_minimo(Integer.parseInt(aforoMinText));
            } else {
                evento.setAforo_minimo(0);
            }

            // Validar aforo máximo
            if (aforomaximo.isChecked()) {
                String aforoMaxText = aforoMax.getText().toString();
                if (aforoMaxText.isEmpty() || !TextUtils.isDigitsOnly(aforoMaxText)) {
                    AlertDialog.Builder advertencia = new AlertDialog.Builder(this);
                    advertencia.setTitle("Invalid input");
                    advertencia.setMessage("Maximum capacity must be a valid number.");
                    advertencia.show();
                    return;
                }
                evento.setAforo_maximo(Integer.parseInt(aforoMaxText));
            } else {
                evento.setAforo_maximo(0);
            }

            // Validar restricción de edad
            evento.setRestriccion_edad(restriccionEdad.isChecked() ? "mayores" : "todo_publico");

            // Subir imagen a Firebase y manejar la URL
            subirImagenAFirebase();
            if (portadaurl == null || portadaurl.isEmpty()) {
                evento.setUrl_imagen("default_image_url"); // URL predeterminada si no se obtiene la imagen
            } else {
                evento.setUrl_imagen(portadaurl);
            }

            // Enviar datos al servidor
            String url = "http://" + IP + "/WebServicePHP/crearevento.php";
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams parametros = new RequestParams();

            parametros.put("id_usuario", evento.getIdUsuario());
            parametros.put("nombre_evento", evento.getNombre_evento());
            parametros.put("descripcion", evento.getDescripcion());
            parametros.put("fecha_evento", evento.getFecha_evento());
            parametros.put("hora_evento", evento.getHora_evento());
            parametros.put("lugar", evento.getLugar());
            parametros.put("direccion", evento.getDireccion());
            parametros.put("precios", evento.getPrecios());
            parametros.put("id_categoria", evento.getId_categoria());
            parametros.put("asistencia_aprobada", evento.getAsistencia_aprobada());
            parametros.put("aforo_minimo", evento.getAforo_minimo());
            parametros.put("aforo_maximo", evento.getAforo_maximo());
            parametros.put("restriccion_edad", evento.getRestriccion_edad());
            parametros.put("url_imagen", evento.getUrl_imagen());
            parametros.put("fecha_creacion", evento.getFecha_creacion());

            client.post(url, parametros, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        String respuesta = new String(responseBody);
                        JSONObject json = new JSONObject(respuesta);
                        if (json.names().get(0).equals("exito")) {
                            Toast.makeText(CrearEvento.this, "The event was created successfully.", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(CrearEvento.this, "Error creating the event.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Log.e("CrearEvento", "Error parsing response: ", e);
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(CrearEvento.this, "Failed to connect to server.", Toast.LENGTH_SHORT).show();
                    Log.e("CrearEvento", "Request failed: ", error);
                }
            });
        } catch (Exception e) {
            Log.e("CrearEvento", "Error creating the event: ", e);
        }
    }


    public void cargarcat()
    {
        url = "http://"+IP+"/WebServicePHP/leercategorias.php";



        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    String respuesta = new String(responseBody);
                    JSONArray jsonArray = new JSONArray(respuesta);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        int id = jsonObject.getInt("id_categoria");
                        String nombre = jsonObject.getString("nombre_categoria");

                        // Crear objeto y agregarlo a la lista
                        categorias.add(new categorias(id, nombre));
                    }

                    // Configurar el adaptador del Spinner
                    ArrayAdapter<categorias> adaptador = new ArrayAdapter<>(
                            CrearEvento.this,
                            android.R.layout.simple_spinner_item,
                            categorias
                    );
                    adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categoriaEvento.setAdapter(adaptador);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    private void subirImagenAFirebase() {
        Uri imageUri = selectedImageUri; // Si seleccionaste una imagen de la galería
        if (imageUri == null && capturedImageBitmap != null) {
            // Si no tienes un Uri, pero tienes un Bitmap (si usaste la cámara)
            imageUri = saveBitmapToTempFile(capturedImageBitmap); // Guardar el Bitmap y obtener su Uri
        }

        if (imageUri != null) {
            // Obtén la referencia de Firebase Storage
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();

            // Genera un nombre único para la imagen
            String fileName = "images/" + System.currentTimeMillis() + ".jpg";
            StorageReference fileReference = storageReference.child(fileName);

            UploadTask uploadtask = fileReference.putFile(imageUri);

            // Sube la imagen
            uploadtask.addOnSuccessListener(taskSnapshot -> {
                        // Obtén la URL de descarga
                        fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                            // La URL está aquí
                            String downloadUrl = uri.toString();

                            portadaurl = downloadUrl;
                            Log.d("Firebase", "Download URL: " + downloadUrl);
                        }).addOnFailureListener(e -> {
                            // Manejar errores al obtener la URL
                            Log.e("Firebase", "Error al obtener la URL", e);
                        });
                    })
                    .addOnFailureListener(e -> {
                        // Manejar errores en la subida
                        Log.e("Firebase", "Error al subir la imagen", e);
                    });
        }
        else
        {
            portadaurl = "No hay imagen";
        }
    }

    private Uri saveBitmapToTempFile(Bitmap bitmap) {
        try {
            // Crea un archivo temporal en el almacenamiento interno
            File tempFile = File.createTempFile("captura_", ".jpg", getCacheDir());

            // Guarda el Bitmap en el archivo
            FileOutputStream out = new FileOutputStream(tempFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

            // Devuelve el Uri del archivo
            return Uri.fromFile(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
package sv.edu.catolica.g07_occasio.activity.ui.crear_evento;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import sv.edu.catolica.g07_occasio.R;
import sv.edu.catolica.g07_occasio.activity.ui.clases.crear_categoria;
import sv.edu.catolica.g07_occasio.activity.ui.clases.crear_evento;
import sv.edu.catolica.g07_occasio.sesion_actual.SessionManager;

public class CrearEventoFragment extends Fragment {

    private ImageView portada;
    private EditText nombreEvento, lugarEvento, direccionEvento, fechaEvento, horaEvento, descripcionEvento, aforoMin, aforoMax, precios;
    private Spinner categoriaEvento;
    private TextView texto;
    private CheckBox aforoMinimoCheck, aforoMaximoCheck;
    private Switch asistenciaAprobada, restriccionEdad, eventoGratis;
    private Uri selectedImageUri;
    private Bitmap capturedImageBitmap;

    private ArrayList<crear_categoria> categorias;
    private String portadaUrl = "";
    private static final String IP = "192.168.5.179";

    private static final int TAKE_PICTURE = 500;
    private static final int SELECT_PICTURE = 600;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_crear_evento, container, false);

        // Inicialización de vistas
        portada = root.findViewById(R.id.bannerEvento);
        nombreEvento = root.findViewById(R.id.etNombreEvento);
        lugarEvento = root.findViewById(R.id.etLugarEvento);
        direccionEvento = root.findViewById(R.id.etDireccionEvento);
        fechaEvento = root.findViewById(R.id.etFecha);
        horaEvento = root.findViewById(R.id.etHora);
        descripcionEvento = root.findViewById(R.id.etDescripcion);
        aforoMin = root.findViewById(R.id.etAforoMinimo);
        aforoMax = root.findViewById(R.id.etAforoMaximo);
        precios = root.findViewById(R.id.etPrecio);
        categoriaEvento = root.findViewById(R.id.spCategoria);
        texto = root.findViewById(R.id.tvSubirPortada);
        aforoMinimoCheck = root.findViewById(R.id.ckbAforoMinimo);
        aforoMaximoCheck = root.findViewById(R.id.ckbAforoMaximo);
        asistenciaAprobada = root.findViewById(R.id.swAceptarSolicitudManualmente);
        restriccionEdad = root.findViewById(R.id.swpregunta);
        eventoGratis = root.findViewById(R.id.swEventoGratis);

        categorias = new ArrayList<>();
        cargarCategorias();

        // Botón para seleccionar imagen
        portada.setOnClickListener(v -> seleccionarImagen());

        // Botón para crear evento
        root.findViewById(R.id.btnCrear).setOnClickListener(v -> crearEvento());

        return root;
    }

    private void seleccionarImagen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialogo_seleccionarimagen, null);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        dialogView.findViewById(R.id.tvFoto).setOnClickListener(v -> {
            dialog.dismiss();
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, TAKE_PICTURE);
        });

        dialogView.findViewById(R.id.tvGaleria).setOnClickListener(v -> {
            dialog.dismiss();
            Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
            startActivityForResult(pickPhotoIntent, SELECT_PICTURE);
        });

        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PICTURE && resultCode == getActivity().RESULT_OK && data != null) {
            capturedImageBitmap = (Bitmap) data.getExtras().get("data");
            portada.setImageBitmap(capturedImageBitmap);
            texto.setText("");
        } else if (requestCode == SELECT_PICTURE && resultCode == getActivity().RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            portada.setImageURI(selectedImageUri);
            texto.setText("");
        }
    }

    private void cargarCategorias() {
        String url = "http://" + IP + "/WebServicePHP/leercategorias.php";
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray jsonArray = new JSONArray(new String(responseBody));
                    categorias.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        categorias.add(new crear_categoria(
                                obj.getInt("id_categoria"),
                                obj.getString("nombre_categoria")
                        ));
                    }
                    ArrayAdapter<crear_categoria> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, categorias);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categoriaEvento.setAdapter(adapter);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(requireContext(), "Error al cargar categorías", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crearEvento() {
        if (TextUtils.isEmpty(nombreEvento.getText()) || TextUtils.isEmpty(fechaEvento.getText()) || TextUtils.isEmpty(horaEvento.getText())) {
            Toast.makeText(requireContext(), "Llena los campos obligatorios: nombre, fecha y hora.", Toast.LENGTH_SHORT).show();
            return;
        }

        SessionManager sessionManager = new SessionManager(requireContext());
        String idUsuario = sessionManager.getIdUsuario();

        if (idUsuario == null) {
            Toast.makeText(requireContext(), "No se encontró usuario logueado.", Toast.LENGTH_SHORT).show();
            return;
        }

        crear_evento evento = new crear_evento();
        evento.setIdUsuario(Integer.parseInt(idUsuario));
        evento.setNombre_evento(nombreEvento.getText().toString());
        evento.setDescripcion(descripcionEvento.getText().toString());
        evento.setFecha_evento(fechaEvento.getText().toString());
        evento.setHora_evento(horaEvento.getText().toString());
        evento.setLugar(lugarEvento.getText().toString());
        evento.setDireccion(direccionEvento.getText().toString());
        evento.setPrecios(precios.getText().toString());
        evento.setFecha_creacion(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        evento.setAsistencia_aprobada(asistenciaAprobada.isChecked() ? "aprobada" : "libre");
        evento.setRestriccion_edad(restriccionEdad.isChecked() ? "mayores" : "todo_publico");

        if (aforoMinimoCheck.isChecked()) evento.setAforo_minimo(Integer.parseInt(aforoMin.getText().toString()));
        if (aforoMaximoCheck.isChecked()) evento.setAforo_maximo(Integer.parseInt(aforoMax.getText().toString()));

        crear_categoria categoriaSeleccionada = (crear_categoria) categoriaEvento.getSelectedItem();
        if (categoriaSeleccionada != null) {
            evento.setId_categoria(categoriaSeleccionada.getId_categoria());
        } else {
            Toast.makeText(requireContext(), "Selecciona una categoría.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Subir la imagen y manejar el proceso de creación
        subirImagenAFirebase(evento);
    }

    private void subirImagenAFirebase(crear_evento evento) {
        Uri imageUri = selectedImageUri;
        if (imageUri == null && capturedImageBitmap != null) {
            imageUri = saveBitmapToTempFile(capturedImageBitmap);
        }

        if (imageUri != null) {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            String fileName = "images/" + System.currentTimeMillis() + ".jpg";
            StorageReference fileReference = storageReference.child(fileName);

            fileReference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {
                fileReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    evento.setUrl_imagen(uri.toString());
                    enviarEventoAlServidor(evento);
                });
            }).addOnFailureListener(e -> Toast.makeText(requireContext(), "Error al subir la imagen.", Toast.LENGTH_SHORT).show());
        } else {
            evento.setUrl_imagen("default_image_url");
            enviarEventoAlServidor(evento);
        }
    }

    private Uri saveBitmapToTempFile(Bitmap bitmap) {
        try {
            // Crea un archivo temporal en el almacenamiento interno
            File tempFile = File.createTempFile("captura_", ".jpg", requireContext().getCacheDir());

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

    private void enviarEventoAlServidor(crear_evento evento) {
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
                        Toast.makeText(requireContext(), "Evento creado exitosamente.", Toast.LENGTH_SHORT).show();

                        // Limpiar campos
                        limpiarCampos();

                        // Redirigir al HomeFragment
                        NavController navController = Navigation.findNavController(requireView());
                        navController.navigate(R.id.nav_home);
                    } else {
                        Toast.makeText(requireContext(), "Error al crear el evento.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e("CrearEvento", "Error procesando respuesta del servidor", e);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(requireContext(), "Error al conectar con el servidor.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limpiarCampos() {
        nombreEvento.setText("");
        lugarEvento.setText("");
        direccionEvento.setText("");
        fechaEvento.setText("");
        horaEvento.setText("");
        descripcionEvento.setText("");
        aforoMin.setText("");
        aforoMax.setText("");
        precios.setText("");
        categoriaEvento.setSelection(0);
        portada.setImageResource(R.drawable.icono_imagen);
        texto.setText("Subir Portada");
        asistenciaAprobada.setChecked(false);
        restriccionEdad.setChecked(false);
        selectedImageUri = null;
        capturedImageBitmap = null;
    }
}
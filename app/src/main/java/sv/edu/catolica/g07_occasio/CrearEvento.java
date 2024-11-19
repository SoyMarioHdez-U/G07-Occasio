package sv.edu.catolica.g07_occasio;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import Clases.categorias;
import Clases.eventos;
import cz.msebera.android.httpclient.Header;

public class CrearEvento extends AppCompatActivity {

    ImageView portada;

    EditText nombreEvento, lugarEvento, direccionEvento, fechaEvento, horaEvento, descripcionEvento, aforoMin, aforoMax;

    Spinner categoriaEvento;

    TextView texto;

    CheckBox aforominimo, aforomaximo;

    Switch asistenciaAprobada, restriccionEdad, eventogratis;

    private static int TAKE_PICTURE = 500, SELECT_PICTURE = 600;

    String url;

    ArrayList<categorias> categorias;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.crear_evento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

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
            portada.setImageBitmap(imgCapturada);
            texto.setText("");
        }
        else if (requestCode == SELECT_PICTURE && resultCode == RESULT_OK)
        {
            Uri imgSeleccionada = data.getData();
            portada.setImageURI(imgSeleccionada);
            texto.setText("");
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public void crearEvento(View view)
    {
        eventos evento = new eventos();


    }

    public void cargarcat()
    {
        String IP = "192.168.1.85"; //se cambia por la ip de la máquina en la que está el servidor(hecho en casa)
        url = "http://"+IP+"/leercategorias.php";



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
}
package sv.edu.catolica.g07_occasio;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
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

import Clases.eventos;

public class CrearEvento extends AppCompatActivity {

    ImageView portada;

    EditText nombreEvento, lugarEvento, direccionEvento, fechaEvento, horaEvento, descripcionEvento, aforoMin, aforoMax, edadMin,edadMax, meses, dias, horas, minutos;

    Spinner categoriaEvento;

    TextView texto;

    RadioButton reservarAdmisionSi, reservarAdmisionNo;

    CheckBox edadminima, edadmaxima, aforominimo, aforomaximo;

    Switch Cancelarevento, galeriaevento, eventogratis;

    private static int TAKE_PICTURE = 500, SELECT_PICTURE = 600;


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

        portada = findViewById(R.id.bannerEvento);

        nombreEvento = findViewById(R.id.etNombreEvento);
        lugarEvento = findViewById(R.id.etLugarEvento);
        direccionEvento = findViewById(R.id.etDireccionEvento);
        fechaEvento = findViewById(R.id.etFecha);
        horaEvento = findViewById(R.id.etHora);
        aforoMin = findViewById(R.id.etAforoMinimo);
        aforoMax = findViewById(R.id.etAforoMaximo);
        edadMin = findViewById(R.id.etMinima);
        edadMax = findViewById(R.id.etMaxima);
        meses = findViewById(R.id.etMeses);
        dias = findViewById(R.id.etDias);
        horas = findViewById(R.id.etHoras);
        minutos = findViewById(R.id.etMinutos);
        descripcionEvento = findViewById(R.id.etDescripcion);

        reservarAdmisionSi = findViewById(R.id.rbSi);
        reservarAdmisionNo = findViewById(R.id.rbNo);

        categoriaEvento = findViewById(R.id.spCategoria);

        texto = findViewById(R.id.tvSubirPortada);

        edadminima = findViewById(R.id.ckbMinima);
        edadmaxima = findViewById(R.id.ckbMaxima);
        aforominimo = findViewById(R.id.ckbAforoMinimo);
        aforomaximo = findViewById(R.id.ckbAforoMaximo);

        Cancelarevento = findViewById(R.id.swCancelarSiNoCumpleAforo);
        galeriaevento = findViewById(R.id.swGaleria);
        eventogratis = findViewById(R.id.swEventoGratis);
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

        evento.setNombreEvento(nombreEvento.getText().toString());
        evento.setLugar(lugarEvento.getText().toString());
        evento.setDireccion(direccionEvento.getText().toString());
        evento.setFechaEvento(fechaEvento.getText().toString());
        evento.setHoraEvento(horaEvento.getText().toString());
        //evento.set
    }
}
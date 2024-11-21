package sv.edu.catolica.g07_occasio.activity.ui.crear_evento;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import sv.edu.catolica.g07_occasio.databinding.FragmentCrearEventoBinding;
import sv.edu.catolica.g07_occasio.activity.ui.clases.Evento;
import sv.edu.catolica.g07_occasio.activity.ui.clases.Categoria;
import sv.edu.catolica.g07_occasio.activity.ui.crear_evento.CrearEventoViewModel;

public class CrearEventoFragment extends Fragment {

    private FragmentCrearEventoBinding binding;

    ImageView portada;

    EditText nombreEvento, lugarEvento, direccionEvento, fechaEvento, horaEvento, descripcionEvento, aforoMin, aforoMax, precios;

    Spinner categoriaEvento;

    TextView texto;

    CheckBox aforominimo, aforomaximo;

    Switch asistenciaAprobada, restriccionEdad, eventogratis;

    private static int TAKE_PICTURE = 500, SELECT_PICTURE = 600;

    String url, portadaurl, resultado;
    ;

    Evento evento;

    ArrayList<Categoria> categorias;

    private Uri selectedImageUri; // Para almacenar el Uri de la imagen seleccionada
    private Bitmap capturedImageBitmap; // Para almacenar el Bitmap si tomaste una foto

    String IP ; //se cambia por la ip de la máquina en la que está el servidor(hecho en casa)


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CrearEventoViewModel CrearEventoViewModel =
                new ViewModelProvider(this).get(CrearEventoViewModel.class);

        binding = FragmentCrearEventoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textSlideshow;
//        CrearEventoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
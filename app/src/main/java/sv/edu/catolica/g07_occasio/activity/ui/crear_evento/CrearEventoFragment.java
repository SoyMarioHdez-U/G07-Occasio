package sv.edu.catolica.g07_occasio.activity.ui.crear_evento;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import sv.edu.catolica.g07_occasio.databinding.FragmentCrearEventoBinding;
import sv.edu.catolica.g07_occasio.activity.ui.crear_evento.CrearEventoViewModel;

public class CrearEventoFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        CrearEventoViewModel CrearEventoViewModel =
                new ViewModelProvider(this).get(CrearEventoViewModel.class);


        return null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
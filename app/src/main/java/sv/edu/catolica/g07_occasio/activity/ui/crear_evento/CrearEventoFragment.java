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

    private FragmentCrearEventoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CrearEventoViewModel CrearEventoViewModel =
                new ViewModelProvider(this).get(CrearEventoViewModel.class);

        binding = FragmentCrearEventoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        CrearEventoViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
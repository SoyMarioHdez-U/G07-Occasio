package sv.edu.catolica.g07_occasio.activity.ui_admin.eventos_pasados;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import sv.edu.catolica.g07_occasio.databinding.FragmentEventosPasadosBinding;

public class EventosPasadosFragment extends Fragment {

    private FragmentEventosPasadosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventosPasadosViewModel eventos_pasadosViewModel =
                new ViewModelProvider(this).get(EventosPasadosViewModel.class);

        binding = FragmentEventosPasadosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textEventosPasados;
        eventos_pasadosViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package sv.edu.catolica.g07_occasio.activity.ui_admin.mis_eventos;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import sv.edu.catolica.g07_occasio.databinding.FragmentMisEventosBinding;


public class MisEventosFragment extends Fragment {

    private FragmentMisEventosBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MisEventosViewModel mis_eventosViewModel =
                new ViewModelProvider(this).get(MisEventosViewModel.class);

        binding = FragmentMisEventosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMisEventos;
        mis_eventosViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
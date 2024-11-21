package sv.edu.catolica.g07_occasio.activity.ui_admin.eventos_asistir;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import sv.edu.catolica.g07_occasio.databinding.FragmentEventosAsistirBinding;

public class EventosAsistirFragment extends Fragment {

    private FragmentEventosAsistirBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EventosAsistirViewModel eventos_asistirViewModel =
                new ViewModelProvider(this).get(EventosAsistirViewModel.class);

        binding = FragmentEventosAsistirBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textEventosAsistir;
        eventos_asistirViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
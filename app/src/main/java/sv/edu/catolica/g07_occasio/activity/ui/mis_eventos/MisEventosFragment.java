package sv.edu.catolica.g07_occasio.activity.ui.mis_eventos;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import sv.edu.catolica.g07_occasio.R;
import sv.edu.catolica.g07_occasio.activity.ui.clases.Evento;
import sv.edu.catolica.g07_occasio.databinding.FragmentMisEventosBinding;
import sv.edu.catolica.g07_occasio.sesion_actual.SessionManager;


public class MisEventosFragment extends Fragment {

    private FragmentMisEventosBinding binding;
    private RecyclerView recyclerView;
    private MisEventosViewModel.MisEventosAdapter adapter;
    private List<Evento> eventoList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MisEventosViewModel misEventosViewModel =
                new ViewModelProvider(this).get(MisEventosViewModel.class);

        binding = FragmentMisEventosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerMisEventos;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        eventoList = new ArrayList<>();
        cargarMisEventos();

        return root;
    }

    private void cargarMisEventos() {
        SessionManager sessionManager = new SessionManager(requireContext());
        String idUsuario = sessionManager.getIdUsuario();

        if (idUsuario == null || idUsuario.isEmpty()) {
            Toast.makeText(getContext(), "Error: No se pudo obtener el usuario logueado", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://192.168.1.8/WebServicePHP/obtenerMisEventos.php";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id_usuario", idUsuario);

        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String respuesta = new String(responseBody);
                    JSONArray jsonArray = new JSONArray(respuesta);

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        ArrayList<String> fotos = new ArrayList<>();
                        try {
                            JSONArray fotosArray = obj.getJSONArray("fotografias");
                            for (int o = 0; o < fotosArray.length(); o++) {
                                fotos.add(fotosArray.getString(o));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Evento evento = new Evento(
                                obj.getString("id_evento"),
                                obj.getString("nombre_evento"),
                                obj.getString("organizador"),
                                obj.getString("fecha_evento"),
                                obj.getString("hora_evento"),
                                obj.getString("lugar"),
                                obj.getString("precios"),
                                obj.getInt("aforo_minimo"),
                                obj.getInt("aforo_maximo"),
                                obj.getString("categoria"),
                                obj.getString("url_imagen"),
                                fotos
                        );
                        eventoList.add(evento);
                    }

                    adapter = new MisEventosViewModel.MisEventosAdapter(getContext(), eventoList);
                    recyclerView.setAdapter(adapter);

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), "Error al conectar con el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
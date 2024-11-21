package sv.edu.catolica.g07_occasio.activity.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import sv.edu.catolica.g07_occasio.activity.ui.clases.Evento;
import sv.edu.catolica.g07_occasio.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView recyclerView;
    private HomeViewModel.EventoAdapter adapter;
    private List<Evento> eventoList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = binding.recyclerEventos;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        eventoList = new ArrayList<>();
        cargarEventos();

        return root;
    }

    private void cargarEventos() {
        String url = "http://192.168.107.179/WebServicePHP/obtenerEventos.php";
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new AsyncHttpResponseHandler() {
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

                    adapter = new HomeViewModel.EventoAdapter(getContext(), eventoList);
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
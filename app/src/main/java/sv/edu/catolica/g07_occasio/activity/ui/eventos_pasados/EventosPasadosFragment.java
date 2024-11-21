package sv.edu.catolica.g07_occasio.activity.ui.eventos_pasados;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import sv.edu.catolica.g07_occasio.R;
import sv.edu.catolica.g07_occasio.activity.ui.clases.Evento;
import sv.edu.catolica.g07_occasio.activity.ui.eventos_pasados.EventosPasadosViewModel;
import sv.edu.catolica.g07_occasio.activity.ui.home.HomeViewModel;
import sv.edu.catolica.g07_occasio.databinding.FragmentEventosPasadosBinding;
import sv.edu.catolica.g07_occasio.sesion_actual.SessionManager;

public class EventosPasadosFragment extends Fragment {

    private RecyclerView recyclerView;
    private HomeViewModel.EventoAdapter adapter;
    private List<Evento> listaEventos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_eventos_pasados, container, false);

        recyclerView = root.findViewById(R.id.recyclerEventosPasados);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaEventos = new ArrayList<>();
        adapter = new HomeViewModel.EventoAdapter(getContext(), listaEventos);
        recyclerView.setAdapter(adapter);

        cargarEventosPasados();

        return root;
    }

    private void cargarEventosPasados() {
        String url = "http://192.168.1.8/WebServicePHP/obtenerEventosPasados.php"; // Cambia por tu IP

        SessionManager sessionManager = new SessionManager(requireContext());
        String idUsuario = sessionManager.getIdUsuario();

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("id_usuario", idUsuario);

        client.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String respuesta = new String(responseBody);
                    JSONArray jsonArray = new JSONArray(respuesta);

                    listaEventos.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        ArrayList<String> fotos = new ArrayList<>();
                        try {
                            JSONArray fotosArray = obj.getJSONArray("fotografias");
                            for (int o = 0; o < fotosArray.length(); o++) {
                                fotos.add(fotosArray.getString(o));
                            }
                        } catch (Exception e) {
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
                        listaEventos.add(evento);
                    }

                    adapter.notifyDataSetChanged();
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
}
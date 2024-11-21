package sv.edu.catolica.g07_occasio.activity.ui.categorias;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import sv.edu.catolica.g07_occasio.R;
import sv.edu.catolica.g07_occasio.activity.ui.clases.Evento;
import sv.edu.catolica.g07_occasio.activity.ui.home.HomeViewModel;

public class EventosPorCategoria extends Fragment {

    private RecyclerView recyclerView;
    private HomeViewModel.EventoAdapter adapter;
    private List<Evento> listaEventos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_eventos_por_categoria, container, false);


        recyclerView = root.findViewById(R.id.recyclerEventosPorCategoria);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        TextView tvNombreCategoria = root.findViewById(R.id.tv_nombre_categoria);


        Bundle args = getArguments();
        if (args != null) {
            String nombreCategoria = args.getString("nombre_categoria", "Sin Categoría");
            tvNombreCategoria.setText(nombreCategoria);


            String idCategoria = args.getString("id_categoria");
            cargarEventosPorCategoria(idCategoria);
        }


        listaEventos = new ArrayList<>();
        adapter = new HomeViewModel.EventoAdapter(getContext(), listaEventos);
        recyclerView.setAdapter(adapter);

        return root;
    }

    private void cargarEventosPorCategoria(String idCategoria) {
        String url = "http://192.168.1.8/WebServicePHP/obtenerEventosPorCategoria.php"; // Cambia por tu IP
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("Content-Type", "application/json");
        client.get(url + "?id_categoria=" + idCategoria, new AsyncHttpResponseHandler() {
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
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
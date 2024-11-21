package sv.edu.catolica.g07_occasio.activity.ui.categorias;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import sv.edu.catolica.g07_occasio.R;
import sv.edu.catolica.g07_occasio.activity.ui.clases.Categoria;
import sv.edu.catolica.g07_occasio.databinding.FragmentCategoriasBinding;

public class CategoriasFragment extends Fragment {

    private RecyclerView recyclerView;
    private CategoriasViewModel.CategoriasAdapter adapter;
    private List<Categoria> listaCategorias;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CategoriasViewModel categoriasViewModel = new ViewModelProvider(this).get(CategoriasViewModel.class);

        View root = inflater.inflate(R.layout.fragment_categorias, container, false);

        recyclerView = root.findViewById(R.id.recyclerCategorias);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        listaCategorias = new ArrayList<>();
        adapter = new CategoriasViewModel.CategoriasAdapter(getContext(), listaCategorias, this::irAEventosPorCategoria);
        recyclerView.setAdapter(adapter);

        cargarCategorias();

        return root;
    }

    private void cargarCategorias() {
        String url = "http://192.168.1.8/WebServicePHP/obtenerCategorias.php"; // Cambia por tu IP
        AsyncHttpClient client = new AsyncHttpClient();

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String respuesta = new String(responseBody);

                    JSONArray jsonArray = new JSONArray(respuesta);
                    listaCategorias.clear();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        String idCategoria = obj.getString("id_categoria");
                        String nombreCategoria = obj.getString("nombre_categoria");
                        String urlCategoria = obj.getString("url_categoria");

                        listaCategorias.add(new Categoria(
                                idCategoria,
                                nombreCategoria,
                                urlCategoria
                        ));
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

    private void irAEventosPorCategoria(Categoria categoria) {
        Bundle args = new Bundle();
        args.putString("id_categoria", categoria.getIdCategoria());
        args.putString("nombre_categoria", categoria.getNombreCategoria());

        NavController navController = Navigation.findNavController(requireView());
        navController.navigate(R.id.eventosPorCategoriaFragment, args);
    }
}
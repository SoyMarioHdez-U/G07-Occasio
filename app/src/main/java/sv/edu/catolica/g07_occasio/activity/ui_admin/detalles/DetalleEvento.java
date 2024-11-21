package sv.edu.catolica.g07_occasio.activity.ui_admin.detalles;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;
import sv.edu.catolica.g07_occasio.R;
import sv.edu.catolica.g07_occasio.sesion_actual.SessionManager;

public class DetalleEvento extends Fragment {

    private ViewPager carruselImagenes;
    private TextView aforo, categoriaEvento, tituloEvento, organizadorEvento, lugarEvento, fechaHoraEvento, preciosEvento;
    private Button btnAccion;
    private String idEvento;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_detalle_evento, container, false);

        // Referencias a las vistas
        carruselImagenes = root.findViewById(R.id.carruselImagenes);
        aforo = root.findViewById(R.id.aforo);
        categoriaEvento = root.findViewById(R.id.categoriaEvento);
        tituloEvento = root.findViewById(R.id.tituloEvento);
        organizadorEvento = root.findViewById(R.id.organizadorEvento);
        lugarEvento = root.findViewById(R.id.lugarEvento);
        fechaHoraEvento = root.findViewById(R.id.fechaHoraEvento);
        preciosEvento = root.findViewById(R.id.preciosEvento);
        btnAccion = root.findViewById(R.id.btnAccion);

        // Obtener datos del Bundle
        Bundle args = getArguments();
        if (args != null) {
            tituloEvento.setText(args.getString("nombre_evento"));
            categoriaEvento.setText(args.getString("categoria"));
            organizadorEvento.setText("Organiza: " + args.getString("organizador"));
            lugarEvento.setText("Lugar: " + args.getString("lugar"));
            fechaHoraEvento.setText("Fecha y Hora: " + args.getString("fecha_evento") + ", " + args.getString("hora_evento"));
            preciosEvento.setText("Precios: " + args.getString("precios"));
            aforo.setText("Cupos disponibles: " + args.getInt("aforo_minimo") + " / " + args.getInt("aforo_maximo"));

            // Configurar el carrusel de imágenes
            ArrayList<String> fotos = args.getStringArrayList("fotografias");
            CarruselAdapter adapter = new CarruselAdapter(requireContext(), fotos);
            carruselImagenes.setAdapter(adapter);
        }
        if (getArguments() != null) {
            idEvento = getArguments().getString("id_evento");
        }

        validarAsistencia();


        return root;
    }

    private void validarAsistencia() {

        SessionManager sessionManager = new SessionManager(requireContext());
        String idUsuario = sessionManager.getIdUsuario();

        if (idUsuario == null || idEvento == null) {
            Toast.makeText(getContext(), "Error: Información faltante", Toast.LENGTH_SHORT).show();
            return;
        }


        String url = "http://192.168.5.179/WebServicePHP/validarAsistencia.php";
        RequestParams params = new RequestParams();
        params.put("id_usuario", idUsuario);
        params.put("id_evento", idEvento);


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String respuesta = new String(responseBody);
                    JSONObject json = new JSONObject(respuesta);

                    if (json.getString("asistencia").equals("existe")) {

                        btnAccion.setVisibility(View.GONE);
                        Toast.makeText(getContext(), "Usted ya mandó una solicitud", Toast.LENGTH_SHORT).show();
                    } else {

                        btnAccion.setVisibility(View.VISIBLE);
                        btnAccion.setOnClickListener(v -> registrarAsistencia());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), "Error al validar la asistencia", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registrarAsistencia() {

        SessionManager sessionManager = new SessionManager(requireContext());
        String idUsuario = sessionManager.getIdUsuario();

        if (idUsuario == null || idEvento == null) {
            Toast.makeText(getContext(), "Error: Hola", Toast.LENGTH_SHORT).show();
            return;
        }


        String url = "http://192.168.5.179/WebServicePHP/registrarAsistencia.php"; // Cambia por tu IP y ruta
        RequestParams params = new RequestParams();
        params.put("id_usuario", idUsuario);
        params.put("id_evento", idEvento);
        params.put("estado", "pendiente");


        AsyncHttpClient client = new AsyncHttpClient();
        client.post(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getContext(), "¡Asistencia registrada exitosamente!", Toast.LENGTH_SHORT).show();

                NavController navController = Navigation.findNavController(requireView());
                navController.navigate(R.id.nav_home);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(getContext(), "Error al registrar la asistencia", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
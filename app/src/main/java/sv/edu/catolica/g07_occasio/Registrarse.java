package sv.edu.catolica.g07_occasio;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class Registrarse extends AppCompatActivity {

    private EditText etNombre, etApellido, etEmail, etPassword, etFechaNac;
    public String nombre, apellido, fecha_nac, email, pasw, url, resultado;
    public int rol = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registrarse);

        etNombre = findViewById(R.id.et_nombre);
        etApellido = findViewById(R.id.et_apellido);
        etFechaNac = findViewById(R.id.et_fecha_nac);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        etFechaNac.setOnClickListener(v -> mostrarDatePickerDialog());



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void registrarUsuario(View view){
        String IP = "192.168.1.8";  // Cambia esta IP a la de tu servidor

        nombre = etNombre.getText().toString();
        apellido = etApellido.getText().toString();
        fecha_nac = etFechaNac.getText().toString();
        email = etEmail.getText().toString();
        pasw = etPassword.getText().toString();


        AsyncHttpClient client = new AsyncHttpClient();
        url = "http://" + IP + "/WebServicePHP/validacionOccasio.php";

        RequestParams parametros = new RequestParams();
        parametros.put("nombre", nombre);
        parametros.put("apellido", apellido);
        parametros.put("email", email);
        parametros.put("password", pasw);
        parametros.put("fecha_nac", fecha_nac);
        parametros.put("accion", "registro");


        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        String respuesta = new String(responseBody);
                        Log.d("Respuesta del servidor", respuesta);  // Esto imprimirá la respuesta completa en Logcat

                        JSONObject json = new JSONObject(respuesta);
                        if (json.names().get(0).equals("exito")) {
                            resultado = json.getString("usuario");
                            Intent categorias = new Intent(Registrarse.this, Categorias.class);
                            startActivity(categorias);
                            finish();

                        } else {
                            resultado = json.getString("error");
                        }
                        Toast.makeText(Registrarse.this, resultado, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(Registrarse.this, "Algo falló", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(Registrarse.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void mostrarDatePickerDialog() {
        Calendar calendario = Calendar.getInstance();
        int year = calendario.get(Calendar.YEAR);
        int month = calendario.get(Calendar.MONTH);
        int day = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    String fechaSeleccionada = year1 + "-" + (monthOfYear + 1) + "-" + dayOfMonth ;
                    etFechaNac.setText(fechaSeleccionada);
                },
                year, month, day);

        datePickerDialog.show();
    }

    public void AbrirIngresar(View view) {
        Intent login = new Intent(Registrarse.this, MainActivity.class);
        startActivity(login);
        finish();

    }
}


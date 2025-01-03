package sv.edu.catolica.g07_occasio.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
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

import cz.msebera.android.httpclient.Header;
import sv.edu.catolica.g07_occasio.Categorias;
import sv.edu.catolica.g07_occasio.R;
import sv.edu.catolica.g07_occasio.Registrarse;

public class MainActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText clave;
    public String user, pasw, url, resultado;
    boolean DobleToqueParaSalir = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.et_email);
        clave = findViewById(R.id.et_password);

//        //Shared Preferences
//        SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
//        String savedUser = preferences.getString("user", null);
//        String savedPasw = preferences.getString("pasw", null);

//        if (savedUser != null && savedPasw != null) {
//            user = savedUser;
//            pasw = savedPasw;
//            ingresar(null);
//        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void ingresar(View view) {
        String IP = "192.168.1.8";
        user = usuario.getText().toString();
        pasw = clave.getText().toString();

        AsyncHttpClient client = new AsyncHttpClient();
        url = "http://" + IP + "/WebServicePHP/validacionOccasio.php";
        RequestParams parametros = new RequestParams();
        parametros.put("usu", user);
        parametros.put("pas", pasw);
        parametros.put("accion", "login"); // Acción fija para login

        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode == 200) {
                    try {
                        String respuesta = new String(responseBody);
                        JSONObject json = new JSONObject(respuesta);
                        if (json.names().get(0).equals("exito")) {
                            resultado = json.getString("usuario");
//                            //Intentando guardar la sesión

                                SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                                SharedPreferences.Editor editor =  preferences.edit();
                                editor.putString("user", user);
                                editor.putString("pasw", pasw);



                            Intent categorias = new Intent(MainActivity.this, InicioActivity.class);
                            startActivity(categorias);
                            finish();

                        } else {
                            resultado = json.getString("error");
                        }
                        Toast.makeText(MainActivity.this, "Bienvenido, " + resultado, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, "Algo falló", Toast.LENGTH_SHORT).show();
                    }


                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(MainActivity.this, "Valió verdura. No conecta", Toast.LENGTH_SHORT).show();
                Log.e("ConexionError", "Código de error: " + statusCode);
                Log.e("ConexionError", "Mensaje: " + error.getMessage());

            }
        });
    }



    public void AbrirRegistrarse(View view) {
        Intent login = new Intent(MainActivity.this, Registrarse.class);
        startActivity(login);
        finish();

    }

    @Override
    public void onBackPressed() {
        if(DobleToqueParaSalir){
            super.onBackPressed();
            return;
        }

        this.DobleToqueParaSalir = true;
        Toast.makeText(this, "Presione de nuevo para salir", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                DobleToqueParaSalir = false;
            }
        }, 2000);

    }




}
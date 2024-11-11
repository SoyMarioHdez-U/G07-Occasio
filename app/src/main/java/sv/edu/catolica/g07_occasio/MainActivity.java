package sv.edu.catolica.g07_occasio;

import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
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

public class MainActivity extends AppCompatActivity {

    private EditText usuario;
    private EditText clave;
    public String user, pasw, url, resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        usuario = findViewById(R.id.et_usuario);
        clave = findViewById(R.id.et_password);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void ingresar(View view) {
        String IP="192.168.1.8";
        user = usuario.getText().toString();
        pasw = clave.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        url = "http://" + IP + "/WebServicePHP/validacionOccasio.php";
        RequestParams parametros = new RequestParams();
        parametros.put("usu", user);
        parametros.put("pas", pasw);
        client.post(url, parametros, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (statusCode==200){
                    try {
                        String respuesta = new String(responseBody);
                        JSONObject json = new JSONObject(respuesta);
                        if(json.names().get(0).equals("exito")){
                            resultado = json.getString("usuario");
                        }
                        else{
                            resultado = json.getString("error");
                        }
                        Toast.makeText(MainActivity.this, resultado, Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    public void AbrirRegistrarse(View view) {
        Intent login = new Intent(MainActivity.this, Registrarse.class);
        startActivity(login);

    }


}
package sv.edu.catolica.g07_occasio.Notificacion;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import cz.msebera.android.httpclient.Header;
import sv.edu.catolica.g07_occasio.Notificacion.NotificationReceiver;
import sv.edu.catolica.g07_occasio.R;
import sv.edu.catolica.g07_occasio.sesion_actual.SessionManager;

public class NotificacionesWorker extends Worker {

    public NotificacionesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        consultarRecordatorios(); // Llama al método que consulta y procesa los recordatorios
        return Result.success();
    }

    private void consultarRecordatorios() {
        SessionManager sessionManager = new SessionManager(getApplicationContext());
        String idUsuario = sessionManager.getIdUsuario();

        if (idUsuario == null) {
            return; // Salir si no hay un usuario logueado
        }

        String urlString = "http://192.168.5.179/WebServicePHP/obtenerRecordatorios.php?id_usuario=" + idUsuario;

        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10000); // 10 segundos
            connection.setReadTimeout(10000);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();
                procesarRespuesta(response.toString());
            }

            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void procesarRespuesta(String respuesta) {
        try {
            JSONArray jsonArray = new JSONArray(respuesta);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject recordatorio = jsonArray.getJSONObject(i);

                String titulo = recordatorio.getString("nombre_evento");
                String mensaje = recordatorio.getString("mensaje");

                mostrarNotificacion(titulo, mensaje);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mostrarNotificacion(String titulo, String mensaje) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "recordatorios_channel")
                .setSmallIcon(R.drawable.ic_event) // Cambia esto por un ícono válido
                .setContentTitle(titulo)
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify((int) System.currentTimeMillis(), builder.build());
        }
    }
}

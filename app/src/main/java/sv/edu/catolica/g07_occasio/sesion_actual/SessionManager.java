package sv.edu.catolica.g07_occasio.sesion_actual;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private static final String PREF_NAME = "user_session";
    private static final String KEY_ID_USUARIO = "id_usuario";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_EMAIL = "email";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public SessionManager(Context context) {
        preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public void createSession(String idUsuario, String nombre, String email) {
        editor.putString(KEY_ID_USUARIO, idUsuario);
        editor.putString(KEY_NOMBRE, nombre);
        editor.putString(KEY_EMAIL, email);
        editor.apply();
    }

    public String getIdUsuario() {
        return preferences.getString(KEY_ID_USUARIO, null);
    }

    public String getNombre() {
        return preferences.getString(KEY_NOMBRE, null);
    }

    public String getEmail() {
        return preferences.getString(KEY_EMAIL, null);
    }

    public void clearSession() {
        editor.clear();
        editor.apply();
    }
}

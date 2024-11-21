package sv.edu.catolica.g07_occasio.activity.ui_admin.notificaciones;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificacionesViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotificacionesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Notificaciones irán aquí");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
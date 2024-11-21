package sv.edu.catolica.g07_occasio.activity.ui_admin.crear_evento;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CrearEventoViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CrearEventoViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
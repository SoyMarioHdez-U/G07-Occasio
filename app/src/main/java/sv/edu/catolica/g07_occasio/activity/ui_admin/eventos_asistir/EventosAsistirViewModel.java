package sv.edu.catolica.g07_occasio.activity.ui_admin.eventos_asistir;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventosAsistirViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EventosAsistirViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Eventos asistir");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
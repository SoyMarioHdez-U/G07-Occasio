package sv.edu.catolica.g07_occasio.activity.ui.eventos_pasados;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EventosPasadosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public EventosPasadosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aquí van los eventos que ya pasaron");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
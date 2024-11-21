package sv.edu.catolica.g07_occasio.activity.ui_admin.mis_eventos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MisEventosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MisEventosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Mis eventos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
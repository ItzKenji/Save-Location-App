package br.usjt.ucsist.savelocationusjtql.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class LocalViewModel extends AndroidViewModel {
    public LocalRepository localRepository;
    public LiveData<Local> local;
    public LocalViewModel (Application application) {
        super(application);
        localRepository = new LocalRepository(application);
        local = localRepository.getLocal();
    }
    public LiveData<Local> getLocal() { return local; }
    public void insert(Local local) { localRepository.insert(local);
    }
}
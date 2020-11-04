package br.usjt.ucsist.savelocationusjtql.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

public class LocalRepository {
    private LocalDAO localDao;
    private LiveData<Local> local;
    LocalRepository(Application application) {
        LocalRoomDatabase db = LocalRoomDatabase.getDatabase(application);
        localDao = db.localDao();
        local = localDao.getLocal();
    }
    LiveData<Local> getLocal() {
        return local;
    }
    void insert(Local local) {
        LocalRoomDatabase.databaseWriteExecutor.execute(() -> {
            localDao.insert(local);
        });
    }
}

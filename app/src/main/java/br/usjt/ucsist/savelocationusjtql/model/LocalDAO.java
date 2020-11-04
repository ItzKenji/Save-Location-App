package br.usjt.ucsist.savelocationusjtql.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LocalDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Local local);

    @Query("DELETE FROM local")
    void deleteAll();

    @Query("SELECT * from local LIMIT 1")
    LiveData<Local> getLocal();
}

package kg.geektech.appnote.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import kg.geektech.appnote.models.Note;



@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Query("SELECT * FROM note")
    LiveData<Note> getAllLive();


    @Insert
    void insert(Note note);


    @Update
    void update(Note note);



    @Query("SELECT * FROM 'note'ORDER BY title ASC")
    List<Note> sortAll();

    @Query("SELECT * FROM 'note'ORDER BY date ASC")
    List<Note> sortAllByDate();
}

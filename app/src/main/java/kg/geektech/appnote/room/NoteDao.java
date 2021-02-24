package kg.geektech.appnote.room;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import kg.geektech.appnote.models.Note;



@Dao
public interface NoteDao {

    @Query("SELECT * FROM note")
    List<Note> getAll();

    @Insert
    void insert(Note note);


    @Query("SELECT * FROM 'note'ORDER BY title ASC")
    List<Note> sortAll();

    @Query("SELECT * FROM 'note'ORDER BY date ASC")
    List<Note> sortAllByDate();
}

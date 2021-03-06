package kg.geektech.appnote.ui.home;

import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kg.geektech.appnote.App;
import kg.geektech.appnote.R;
import kg.geektech.appnote.models.Note;

public class NoteFragment extends Fragment {
    private ActivityResultLauncher<String> content;
    private Note note;
    private EditText editText;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null)
            note = (Note) getArguments().getSerializable("note");
        editText = view.findViewById(R.id.editText);
        if (note != null) editText.setText(note.getTitle());
        Button btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText != null) {
                    save();
                    close();
                }

            }
        });
    }

    private void save() {
        String text = editText.getText().toString();
        if (note == null) {
            note = new Note(text);
            note.setCreatedAt(System.currentTimeMillis());
            App.getDatabase().noteDao().insert(note);
        } else {
            note.setTitle(text);
            App.getDatabase().noteDao().update(note);

        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("text", note);
        getParentFragmentManager().setFragmentResult("rk_note", bundle);

    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}

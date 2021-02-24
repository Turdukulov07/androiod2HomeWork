package kg.geektech.appnote.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import kg.geektech.appnote.App;
import kg.geektech.appnote.MainActivity;
import kg.geektech.appnote.R;
import kg.geektech.appnote.models.Note;

public class NoteFragment extends Fragment {
    private NoteAdapter adapter;
    CircleImageView circleImageView;
    private ActivityResultLauncher<String> content;

    private Button button;
    private EditText editText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<String> list = new ArrayList<>();

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_note, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        button = view.findViewById(R.id.btnSave);
        editText = view.findViewById(R.id.editText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText != null) {


                }
            }
        });
        circleImageView = view.findViewById(R.id.circleImage);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteFragment.this.openGallery();
                save();
                close();

            }
        });

    }

    private void close() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }

    private void save() {
        String text = editText.getText().toString();
        Note note = new Note(text);
        note.setCreatedAt(System.currentTimeMillis());
        App.getDatabase().noteDao().insert(note);
        Bundle bundle = new Bundle();
        bundle.putSerializable("text", note);
        getParentFragmentManager().setFragmentResult("rk_note", bundle);

        Toast.makeText(getContext(), "операция успешно добавлена", Toast.LENGTH_LONG).show();

    }

    private void openGallery() {
        content.launch("image/*");
    }
}


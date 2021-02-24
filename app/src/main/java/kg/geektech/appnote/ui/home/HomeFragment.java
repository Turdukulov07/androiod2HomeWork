package kg.geektech.appnote.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import kg.geektech.appnote.App;
import kg.geektech.appnote.ItemClickListener;
import kg.geektech.appnote.Prefs;
import kg.geektech.appnote.R;
import kg.geektech.appnote.models.Note;

public class HomeFragment extends Fragment implements ItemClickListener {

    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private int position;
    private Prefs prefs;
    private boolean update = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NoteAdapter();
        adapter.setClickListener(this);
        ArrayList<Note> list = new ArrayList<>();
        loadData();


    }

    private void loadData() {
        List<Note> list = App.getAppDatabase().noteDao().getAll();
        adapter.setList(list);
    }


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclerView);
        initList();
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openNote();
            }
        });

        setFragmentListener();
    }

    private void openNote() {
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.noteFragment);


    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener("rk_note",
                getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                   Note note = (Note) result.getSerializable("note");
                        adapter.addItem(note);
                        if (update) {
                            adapter.updateItem(position,note);
                        } else {
                            adapter.addItem(note);
                        }
                    }
                });
        }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_options, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        prefs = new Prefs(requireContext());
        switch (item.getItemId()) {
            case R.id.clear_settings:
                alertDialogClearSettings();
                return true;


            case R.id.sort_database:
                sort_database();
                return true;

            case  R.id.sortByDate:
                SortDyDate();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void SortDyDate() {
        adapter.sortListByDate(App.getAppDatabase().noteDao().sortAllByDate());

    }

    private void sort_database() {
        adapter.sortList(App.getAppDatabase().noteDao().sortAll());


    }

    private void alertDialogClearSettings() {
        new AlertDialog.Builder(getContext())
                .setIcon(android.R.drawable.ic_delete)
                .setTitle("Are you sure?")
                .setMessage("Do you want to clear settings?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        prefs.clearSettings();
                        requireActivity().finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }


    private void initList() {
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter.setClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position, Note txt) {
                position = position;
                update = true;
                Note note = adapter.getItem(position);
                openForm(note);
            }
        });
    }

    private void openForm(Note note) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("note", note);
        NavController navController = Navigation.findNavController(requireActivity(),
                R.id.nav_host_fragment);
        navController.navigate(R.id.noteFragment, bundle);
    }


    @Override
    public void onItemClick(int position, Note txt) {
        Bundle bundle = new Bundle();
        getParentFragmentManager().setFragmentResult("text", bundle);
        this.position = position;
        Navigation.findNavController(requireActivity(), R.id.nav_host_fragment).navigate(R.id.noteFragment);
    }
}
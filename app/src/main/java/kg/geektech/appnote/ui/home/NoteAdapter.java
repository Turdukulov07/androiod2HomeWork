package kg.geektech.appnote.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import kg.geektech.appnote.App;
import kg.geektech.appnote.OnItemClickListener;
import kg.geektech.appnote.R;
import kg.geektech.appnote.models.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    public ArrayList<Note> list;
    private OnItemClickListener onItemClickListener;



    public NoteAdapter() {
     list = new ArrayList<>();
     list.addAll(App.getAppDatabase().noteDao().getAll());

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_note, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void addItem(Note note) {
        list.add(note);
        notifyItemInserted(list.indexOf(note));
    }

    public void setList(List<Note> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public Note getItem(int position) {
        return list.get(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle, textDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(v ->
                    onItemClickListener.onClick(getAdapterPosition()));
            textTitle = itemView.findViewById(R.id.text_title);
            textDate = itemView.findViewById(R.id.text_date);
        }

        public void bind(Note note) {
            textDate.setText(note.getDate());
            textTitle.setText(note.getTitle());
        }


    }
}

package kg.geektech.appnote.ui.home;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.List;

import kg.geektech.appnote.App;
import kg.geektech.appnote.ItemClickListener;
import kg.geektech.appnote.R;
import kg.geektech.appnote.models.Note;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private ItemClickListener listener;
    private static ArrayList<Note> list;

    public NoteAdapter(){
        list = new ArrayList<>();
        list.addAll(App.getDatabase().noteDao().getAll());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note, parent, false), listener);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
        if (position % 2 == 0)
          holder.itemView.setBackgroundColor(Color.GRAY);
        else{
            holder.itemView.setBackgroundColor(Color.WHITE);
    }
        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();

    }
    public void addItem(Note note) {
        this.list.add(0,note);
        notifyItemInserted(list.size()-1);

    }

    public void setList(List<Note> list) {

    }

    public void setClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    public Note getItem(int position) {
        return list.get(position);

    }

    public void updateItem(int position, Note note) {
        list.set(position,note);
        notifyItemChanged(position);
    }
    public void sortListByDate(List<Note> sortAllByDate) {
        list.clear();
        list.addAll(sortAllByDate);
        notifyDataSetChanged();
    }

    public void sortList(List<Note> sort) {
        list.clear();
        list.addAll(sort);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;

        public ViewHolder(@NonNull View itemView, ItemClickListener listener) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onItemClick(ViewHolder.this.getAdapterPosition(), list.get(ViewHolder.this.getAdapterPosition()));
                    return true;
                }
            });
            itemView.setOnClickListener(this::onLongClick);
        }

        public void bind(Note note ) {
            textTitle.setText(note.getTitle());

        }

        private boolean onLongClick(View view) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(view.getRootView().getContext());
            dialog.setTitle("Are you sure about that!?").setPositiveButton("No...", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }

            }).setPositiveButton("Yes of course!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    list.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), list.size());
                }
            }).show();
            return true;
        }
    }

}

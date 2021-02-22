package kg.geektech.appnote.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kg.geektech.appnote.R;
import kg.geektech.appnote.ui.profile.OnclickPosition;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private ArrayList<String> list = new ArrayList<>();
    private static OnclickPosition onclickPosition;



    public NoteAdapter(OnclickPosition onclickPosition) {
        this.onclickPosition = onclickPosition;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_note, parent, false);
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
    public void addItem(String text) {
        this.list.add(0,text);
        notifyItemInserted(list.size()-1);

    }

    public void addList(ArrayList<String> list) {
            this.list.addAll(0,list);
            notifyDataSetChanged();
        }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onclickPosition.clickListener(getAdapterPosition());

                }
            });


        }

        public void bind(String s) {
            textTitle.setText(s);
            if (getAdapterPosition() % 2 == 0)
                itemView.setBackgroundColor(itemView.getResources().getColor(R.color.gray));
            else
                itemView.setBackgroundColor(itemView.getResources().getColor(R.color.grey));
        }
    }
}

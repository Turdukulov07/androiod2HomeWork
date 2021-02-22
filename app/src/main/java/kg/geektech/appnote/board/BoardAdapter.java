package kg.geektech.appnote.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import kg.geektech.appnote.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private String[] titles = new String[]{"Fast", "Free", "Powerful"};
    private String[] desc = new String[]{"Запомни одну фразу", "ВСЕ БУДЕТ", "но не СРАЗУ"};
    private int[] images = new int[]{R.drawable.zap, R.drawable.milll, R.drawable.programmer};


    //с начала обьвит интерфейс как класс
    public interface OnStartClickListener {
        void onClick();

    }

    //потом его обьивит так
    private OnStartClickListener onStartClickListener;


    public BoardAdapter() {


    }

    @NonNull
    @Override
    public BoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.pager_bord, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardAdapter.ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setOnStartClickListener(OnStartClickListener onStartClickListener) {
        this.onStartClickListener = onStartClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTitle, textDesc;
        ;
        private LinearLayout linearLayoutTabIncurs;
        private Button btnStart;
        private ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            imageView = itemView.findViewById(R.id.imageView);
            btnStart = itemView.findViewById(R.id.btnStart);
            textDesc = itemView.findViewById(R.id.textDesc);
            btnStart.setOnClickListener(v -> {
                //и потом его вызвать
                onStartClickListener.onClick();

            });
        }

        public void bind(int position) {
            btnStart.setVisibility(View.GONE);
            textTitle.setText(titles[position]);
            textDesc.setText(desc[position]);
            imageView.setImageResource(images[position]);
        }
    }
}

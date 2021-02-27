package kg.geektech.appnote.board;

import android.content.Context;
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

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

import kg.geektech.appnote.OnItemClickListener;
import kg.geektech.appnote.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private String[] titles = new String[]{"Fast", "Free", "Powerful"};
    private String[] desc = new String[]{"Запомни одну фразу", "ВСЕ БУДЕТ", "но не СРАЗУ"};
    private int[] images = new int[]{R.drawable.zap, R.drawable.milll, R.drawable.programmer};


    private List<BoardModel> listBoardModels;
    private LayoutInflater layoutInflater;

    private Context context;
    private OnItemClickListener onItemClickListener;

    public BoardAdapter(Context context, List<BoardModel> listBoardModels) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.listBoardModels = listBoardModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pager_bord, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(listBoardModels.get(position));
    }

    @Override
    public int getItemCount() {
        return listBoardModels.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textTitle, textDesc;
        private LottieAnimationView lottieAnimationView;
        private Button buttonGetStarted;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lottieAnimationView = itemView.findViewById(R.id.lottie_animation_view);
            textDesc = itemView.findViewById(R.id.textDesc);
            textTitle = itemView.findViewById(R.id.textTitle);
            buttonGetStarted = itemView.findViewById(R.id.button_skip_onboard);
            buttonGetStarted.setOnClickListener(v ->
                    onItemClickListener.onClick(getAdapterPosition()));
        }

        public void bind(BoardModel boardModel) {
            //TODO:   3rd Home Work - init. views & showing button on the 3rd page
            buttonGetStarted.setVisibility(View.GONE);
            textTitle.setText(boardModel.getTitle());
            textDesc.setText(boardModel.getTitleBelow());
            lottieAnimationView.setAnimation(boardModel.getImages());
            if (getAdapterPosition() == listBoardModels.size() - 1) {
                buttonGetStarted.setVisibility(View.VISIBLE);
            }
        }
    }
}

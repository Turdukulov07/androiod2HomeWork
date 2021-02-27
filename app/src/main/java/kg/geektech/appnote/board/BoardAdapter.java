package kg.geektech.appnote.board;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

import kg.geektech.appnote.OnItemClickListener;
import kg.geektech.appnote.R;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder> {
    private String[] titles = new String[]{"The seed is the most important", "Love each other", "You don’t choose your family"};
    private String[] desc = new String[]{"Take care of your parents", "When you look at your life, the greatest happiness's are family happiness's", "The family is one of nature’s masterpieces"};
    private int[] images = new int[]{R.raw.familylove, R.raw.selfie, R.raw.children};



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
                    onItemClickListener.onItemClick(getAdapterPosition()));
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

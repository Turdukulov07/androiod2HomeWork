package kg.geektech.appnote.board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import kg.geektech.appnote.OnItemClickListener;
import kg.geektech.appnote.Prefs;
import kg.geektech.appnote.R;


public class BoardFragment extends Fragment {

    private Button buttonSkip, buttonNext;
    private int position = 0;
    private LinearLayout linearLayoutTabIncurs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutTabIncurs = view.findViewById(R.id.tab_indicator);
        buttonNext = view.findViewById(R.id.button_next_onboard);
        buttonSkip = view.findViewById(R.id.button_skip_onboard);
        buttonSkip.setOnClickListener(v -> close());

        List<BoardModel> listBoard = new ArrayList<>();

        ViewPager2 viewPager2 = view.findViewById(R.id.view_pager);
        BoardAdapter adapter = new BoardAdapter(this.getContext(), listBoard);
        viewPager2.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                close();
            }
        });

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        requireActivity().finish();
                    }
                });

        ImageView[] indicators = new ImageView[adapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(15, 0, 15, 0);
        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_indicators));
            indicators[i].setLayoutParams(layoutParams);
            linearLayoutTabIncurs.addView(indicators[i]);
        }
        setCurrentIndicators(0);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicators(position);
            }
        });

        buttonNext.setOnClickListener(v -> {
            position = viewPager2.getCurrentItem();
            if (position < listBoard.size()) {
                position++;
                viewPager2.setCurrentItem(position);
            }

            if (position == listBoard.size() - 1) {
                buttonNext.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void setCurrentIndicators(int index) {
        int childCount = linearLayoutTabIncurs.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) linearLayoutTabIncurs.getChildAt(i);
            if (i == index) {
                imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.indicator));

            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.ic_indicators));

            }
        }
    }

    private void close() {
        Prefs prefs = new Prefs(requireContext());
        prefs.saveBoardStatus();
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigateUp();
    }
}

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

import kg.geektech.appnote.R;


public class BoardFragment extends Fragment {
    private Button buttonNext, buttonSkip;

    private LinearLayout linearLayoutTabIncurs;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        linearLayoutTabIncurs = view.findViewById(R.id.tab_indicator);
        buttonNext = view.findViewById(R.id.button_next);
        buttonSkip = view.findViewById(R.id.button_skip);
        buttonSkip.setOnClickListener(view1 -> close());
        ViewPager2 viewPager = view.findViewById(R.id.viewPager);
        BoardAdapter adapter = new BoardAdapter();

        adapter.setOnStartClickListener(new BoardAdapter.OnStartClickListener() {
            @Override
            public void onClick() {
                close();

            }
        });
        viewPager.setAdapter(adapter);
        //если кнопку назад нажимает тогда финиш будет через этот код
        requireActivity()
                .getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(),new OnBackPressedCallback(true) {//getViewLifecycleOwner() когда мы открываем борд этот код работает только на этом проекте
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
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicators(position);
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





    private void close(){
        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.navigation_home);
    }

}
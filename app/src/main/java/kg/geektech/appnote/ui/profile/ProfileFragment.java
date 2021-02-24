package kg.geektech.appnote.ui.profile;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;
import kg.geektech.appnote.R;

public class ProfileFragment extends Fragment {
    CircleImageView circleImageView;
    private ImageView imageEdit;
    private TextView textName;
    private ActivityResultLauncher<String> content;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        circleImageView = view.findViewById(R.id.circleImage);
        imageEdit = view.findViewById(R.id.imageEdit);
        textName = view.findViewById(R.id.textName);
        setListener();
    }

    private void setListener() {
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.navigation_profile);
                ProfileFragment.this.content.launch("image/*");
            }
        });
        content = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri result) {
                circleImageView.setImageURI(result);
            }
        });
        imageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController controller = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                controller.navigate(R.id.action_navigation_profile_to_editFragment);
            }
        });
        getParentFragmentManager().setFragmentResultListener("edit", getViewLifecycleOwner(), new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                Log.e("RESULT", "request_key: " + requestKey);
                String name = result.getString("name");
                Toast.makeText(getContext(), "операция успешно добавлена", Toast.LENGTH_LONG).show();

            }
        });

    }
}

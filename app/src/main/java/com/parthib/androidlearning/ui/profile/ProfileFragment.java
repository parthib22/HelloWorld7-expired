package com.parthib.androidlearning.ui.profile;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.parthib.androidlearning.StoreInfo;
import com.parthib.androidlearning.databinding.FragmentProfileBinding;
import com.parthib.androidlearning.databinding.ProfileOptionsBinding;
import com.parthib.androidlearning.models.UserDetails;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private CircleImageView dp;
    private FloatingActionButton fab;
    private TextView txtName;
    private Button btn;
    String userId;
    UserDetails userInfo;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dp = binding.profileImage;
        fab = binding.fab;
        txtName = binding.forName;
        btn = binding.btn;
        // fetching details from shared preference

        StoreInfo info = new StoreInfo(getActivity());
        userId = info.getId();

        fetchDetails();

        fab.setOnClickListener(v -> openOptions());

        return root;
    }

    private void openOptions() {
        BottomSheetDialog modal = new BottomSheetDialog(getContext());
        ProfileOptionsBinding optionsBinding = ProfileOptionsBinding.inflate(getLayoutInflater());
        modal.setContentView(optionsBinding.getRoot()); // specify the ui for modal
        modal.show(); // display modal

        optionsBinding.optionCamera.setOnClickListener(v -> {
            // code to open camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraLauncher.launch(intent);
            modal.dismiss();
        });

        optionsBinding.optionGallery.setOnClickListener(v -> {
            // code to open library based on pictures
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            Intent chooser = Intent.createChooser(intent, "Select Image");
            galleryLauncher.launch(chooser);
            modal.dismiss();
        });
    }

    private void fetchDetails() {

        // fetching details from database
        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        fs.collection("User_details")
                .document(userId)
                .get()
                .addOnCompleteListener(task -> {
                    DocumentSnapshot ds = task.getResult(); // fetching particular document details
                    userInfo = ds.toObject(UserDetails.class); // converting to the provided model class type
                    if(userInfo != null)
                        txtName.setText(userInfo.getName());
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show()
                );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // for camera image access
    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Bitmap imageBitmap = (Bitmap) result.getData().getExtras().get("data");
                    dp.setImageBitmap(imageBitmap);
                }
            }
    );

    // for gallery image access
    ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getData() != null){
                    Uri imgUri = result.getData().getData();
                    dp.setImageURI(imgUri);
                }
            }
    );
}
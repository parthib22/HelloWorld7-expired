package com.parthib.androidlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.parthib.androidlearning.databinding.ActivityPlaceDetailBinding;
import com.parthib.androidlearning.models.Place;
import com.squareup.picasso.Picasso;

public class PlaceDetailActivity extends AppCompatActivity {

    ActivityPlaceDetailBinding binding;
    TextView showName, showType, showLocation;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPlaceDetailBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        showName = binding.placeName;
        showType = binding.placeType;
        showLocation = binding.placeLocation;
        iv = binding.placeImage;

        Place placeObj = (Place) getIntent().getSerializableExtra("place_details");

        // displaying the details in respective ui views

        showLocation.setText(placeObj.getLocation());
        showType.setText(placeObj.getType());
        showName.setText(placeObj.getP_name());

        Picasso.get().load(placeObj.getPic()).into(iv);
    }
}
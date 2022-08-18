package com.parthib.androidlearning.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.parthib.androidlearning.PlaceAdapter;
import com.parthib.androidlearning.databinding.FragmentHomeBinding;
import com.parthib.androidlearning.models.Place;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ArrayList<Place> placeList; // create a heterogeneous list
    private RecyclerView placesRecyclerList, gridRecycler ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        placesRecyclerList=binding.placeListView;
        gridRecycler = binding.placeGridView;

        placeList = new ArrayList<>();
        fetchPlaces();

        return root;
    }

    private void fetchPlaces() {
        FirebaseFirestore fire = FirebaseFirestore.getInstance(); //creating object of Firestore database

        fire.collection("places")
                .get() // to fetch all the documents present in the collection
                .addOnCompleteListener(
                        task -> {
                            // task.getResult() => Querysnapshot i.e. the entire document set present within the collection
                            for(DocumentSnapshot doc : task.getResult()){
                                Place place = doc.toObject(Place.class); //individual place details above
                                placeList.add(place); //populating the array list

                            }
                            if(!placeList.isEmpty()){
                                PlaceAdapter adapter = new PlaceAdapter(placeList,getActivity());
                                placesRecyclerList.setAdapter(adapter);
                                gridRecycler.setAdapter(adapter);
                            }
                        }
                )
                .addOnFailureListener(e -> Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
package com.parthib.androidlearning;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.parthib.androidlearning.databinding.PlaceUiBinding;
import com.parthib.androidlearning.models.Place;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.MyAdapter> {

    private ArrayList<Place> pList;
    private Context con;

    public PlaceAdapter(ArrayList<Place> pList, Context con) {
        this.pList = pList;
        this.con = con;
    }

    @NonNull
    @Override
    public MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewUI = LayoutInflater.from(parent.getContext()).inflate(R.layout.place_ui, parent, false);
        return new MyAdapter(viewUI);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter holder, int position) {
        Place placeInfo = pList.get(position);
        holder.binding.placeName.setText(placeInfo.getP_name());
        Picasso.get().load(placeInfo.getPic()).into(holder.binding.placeImage);

        //apply event on item card
        CardView card = holder.binding.getRoot(); //initialising the card view present in the ui
        card.setOnClickListener(v -> {
            Intent navigation = new Intent(con, PlaceDetailActivity.class);
            // need to navigate with some data & information
            navigation.putExtra("place_details", placeInfo);
            con.startActivity(navigation);
        });
    }

    @Override
    public int getItemCount() {

        return pList.size();
    }

    //inner class
    static class MyAdapter extends RecyclerView.ViewHolder {

        PlaceUiBinding binding;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            binding = PlaceUiBinding.bind(itemView);
        }
    }
}

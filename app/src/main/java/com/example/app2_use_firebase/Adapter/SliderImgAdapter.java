package com.example.app2_use_firebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app2_use_firebase.Domain.SliderItems;
import com.example.app2_use_firebase.databinding.ViewholderSlideItemBinding;
//import com.google.protobuf.BooleanArrayList;

import java.util.List;

public class SliderImgAdapter extends RecyclerView.Adapter<SliderImgAdapter.SlideViewHolder> {

    private List<String> imageUrls;

    Context context ;
    public SliderImgAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public SlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        ViewholderSlideItemBinding binding = ViewholderSlideItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new SlideViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SlideViewHolder holder, int position) {
        String imageUrl = imageUrls.get(position);

        Glide.with(context).load(imageUrl).into(holder.binding.imageViewSlide);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    public static class SlideViewHolder extends RecyclerView.ViewHolder {

        ViewholderSlideItemBinding binding;

        public SlideViewHolder(@NonNull  ViewholderSlideItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

package com.hcmut.assignment.biotech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> {
    private Context context;
    private List<ImageData> dataList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(String imgSrc);
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        Button button;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.item_button);
        }
    }

    public ButtonAdapter(Context context, List<ImageData> dataList, OnItemClickListener listener) {
        this.context = context;
        this.dataList = dataList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ButtonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_button, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ButtonAdapter.ViewHolder holder, int position) {
        ImageData data = dataList.get(position);
        holder.button.setText(data.title);
        holder.button.setId(View.generateViewId()); // Tạo ID duy nhất

        holder.button.setOnClickListener(v -> listener.onItemClick(data.img_src));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}

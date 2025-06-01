package com.hcmut.assignment.biotech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.ar.sceneform.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.ViewHolder> {
	private Context context;
	private OnItemClickListener listener;

	public interface OnItemClickListener {
		void onItemClick(int position, String fileName);
	}
	public static class ViewHolder extends RecyclerView.ViewHolder {
		Button button;

		public ViewHolder(@NonNull View itemView) {
			super(itemView);
			button = itemView.findViewById(R.id.item_button);
		}
	}

	public ButtonAdapter(Context context, OnItemClickListener listener) {
		this.context = context;
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
		ImageData data = Database.dataList.get(position);
		holder.button.setText(data.title);
		holder.button.setId(View.generateViewId()); // Tạo ID duy nhất

		holder.button.setOnClickListener(v -> listener.onItemClick(position, data.img_src));
	}

	@Override
	public int getItemCount() {
		return Database.dataList.size();
	}
}

package com.sugiartha.juniorandroid.adapter;

import android.telecom.Call;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sugiartha.juniorandroid.R;

import java.util.List;

public class NegaraAdapter extends RecyclerView.Adapter<NegaraAdapter.ViewHolder> {

    private List<String> data;
    private ItemClickListener mItemListener;

    public NegaraAdapter(List<String> data, ItemClickListener itemClickListener){
        this.data = data;
        this.mItemListener = itemClickListener;
    }


    @NonNull
    @Override
    public NegaraAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.negara_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NegaraAdapter.ViewHolder holder, int position) {
        String negara = data.get(position);
        holder.bind(negara);
        holder.itemView.setOnClickListener(v -> {
            mItemListener.onItemClick(data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface ItemClickListener {
        void onItemClick(String data);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text1);
        }

        public void bind(String data) {
            textView.setText(data);
        }
    }
}

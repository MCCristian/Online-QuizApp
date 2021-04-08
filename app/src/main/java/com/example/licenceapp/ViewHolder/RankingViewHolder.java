package com.example.licenceapp.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.licenceapp.Interface.ItemClickListener;
import com.example.licenceapp.R;

import org.w3c.dom.Text;

public class RankingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtName, txtScore;

    private ItemClickListener itemClickListener;

    public RankingViewHolder(@NonNull View itemView) {
        super(itemView);
        txtName = (TextView)itemView.findViewById(R.id.text_name);
        txtScore = (TextView)itemView.findViewById(R.id.text_score);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}

package com.app.AlistApp.adapter;//package

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.app.AlistApp.R;
import com.app.AlistApp.databinding.BottomsheetRecyclerItemBinding;
import com.app.AlistApp.model.SpinnerItem;

import java.util.ArrayList;

public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.ItemHolder> {


    ArrayList<SpinnerItem> items;
    ItemClickedListener listener;
    private static final String TAG = "elc adapter";

    public SpinnerAdapter(ArrayList<SpinnerItem> SpinnerItems, ItemClickedListener listener) {
        this.items = SpinnerItems;
        this.listener=listener;
    }
    public void setItems(ArrayList<SpinnerItem> items){
        this.items=items;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bottomsheet_recycler_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        holder.recItem=items.get(position);
        holder.binding.bottomSheetItemBtn.setText(items.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        BottomsheetRecyclerItemBinding binding;
        SpinnerItem recItem;


        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            binding=BottomsheetRecyclerItemBinding.bind(itemView);
            binding.bottomSheetItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickItemListener(recItem);
                }
            });

        }
    }

    public interface ItemClickedListener{

        public void onClickItemListener(SpinnerItem item);

    }
}

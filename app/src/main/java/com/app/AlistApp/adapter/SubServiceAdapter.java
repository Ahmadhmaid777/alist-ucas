package com.app.AlistApp.adapter;//package

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.AlistApp.R;
import com.app.AlistApp.databinding.SubServiceCustomItemBinding;
import com.app.AlistApp.model.SubService;
import com.app.AlistApp.interfaces.ClickSubServiceListener;


import java.util.ArrayList;

public class SubServiceAdapter extends RecyclerView.Adapter<SubServiceAdapter.SubServiceHolder> {


    ArrayList<SubService> subServices;
    ClickSubServiceListener listener;

    public SubServiceAdapter(ArrayList<SubService> subServices, ClickSubServiceListener listener) {
        this.subServices = subServices;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SubServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_service_custom_item, parent, false);
        return new SubServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubServiceHolder holder, int position) {
        holder.subService=subServices.get(position);
        holder.bind();
    }

    @Override
    public int getItemCount() {
        return subServices.size();
    }

    class SubServiceHolder extends RecyclerView.ViewHolder {
        SubServiceCustomItemBinding binding;
        SubService subService;
        public SubServiceHolder(@NonNull View itemView) {
            super(itemView);
            binding=SubServiceCustomItemBinding.bind(itemView);
            binding.subServiceItemBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClickSubService(subService);
                }
            });
        }
        public void bind(){
            binding.subServiceItemBtn.setText(subService.getName());
        }

    }
}

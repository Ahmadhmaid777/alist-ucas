package com.app.AlistApp.adapter;//package

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.AlistApp.R;
import com.app.AlistApp.databinding.ServiceCustomItemBinding;
import com.app.AlistApp.model.Service;


import java.util.ArrayList;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceHolder> {


    ArrayList<Service> services;
    Context context;
    OnClickServiceItemListener itemListener;

    public ServicesAdapter(ArrayList<Service> services, OnClickServiceItemListener itemListener) {
        this.services = services;
        this.itemListener = itemListener;
    }

    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.service_custom_item, parent, false);
        return new ServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {
        holder.service=services.get(position);
        holder.bind();

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    class ServiceHolder extends RecyclerView.ViewHolder {
        ServiceCustomItemBinding binding;
        Service service;

        public ServiceHolder(@NonNull View itemView) {
            super(itemView);
            binding=ServiceCustomItemBinding.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemListener.onClickListener(service);
                }
            });
        }

        public void bind(){
            binding.serviceItemImage.setImageDrawable(context.getDrawable(service.getIconId()));
            binding.serviceItemName.setText(service.getName());
        }
    }

    public interface OnClickServiceItemListener{
        public void onClickListener(Service service);
    }
}

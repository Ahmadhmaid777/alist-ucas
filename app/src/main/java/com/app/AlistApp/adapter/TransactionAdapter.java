package com.app.AlistApp.adapter;//package

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.AlistApp.R;
import com.app.AlistApp.databinding.TransactionCustomeItemBinding;
import com.app.AlistApp.model.Transaction;
import com.app.AlistApp.interfaces.ClickRateListener;


import java.util.Date;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionHolder> {


    List<Transaction> transactions;
    ClickRateListener rateListener;
    Context context;


    public void setTransactions( List<Transaction> transactions){
        this.transactions=transactions;
        notifyItemRangeInserted(this.transactions.size(),transactions.size());
    }
    public void clearTransaction(){
        transactions.clear();
        notifyDataSetChanged();

    }

    public TransactionAdapter(List<Transaction> Transactions, ClickRateListener rateListener) {
        this.transactions = Transactions;
        this.rateListener=rateListener;
    }

    @NonNull
    @Override
    public TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_custome_item, parent, false);
        return new TransactionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionHolder holder, int position) {

        holder.transaction=transactions.get(position);
        holder.bind();

        holder.checkIfTransactionRated();
        holder.checkTransactionState();



    }

    @Override
    public int getItemCount() {
        return transactions.size();
    }

    class TransactionHolder extends RecyclerView.ViewHolder {
        TransactionCustomeItemBinding binding;
        Transaction transaction;

        public TransactionHolder(@NonNull View itemView) {
            super(itemView);
            binding=TransactionCustomeItemBinding.bind(itemView);
            binding.transactionItemRateBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    rateListener.onclickRateListener(transaction.getId());
                }
            });

        }

        public void bind(){
            binding.transactionItemDateTv.setText(transaction.getRequestDate());
            binding.transactionItemIdTv.setText("#"+transaction.getId());
            binding.transactionItemRateTv.setText(String.valueOf(transaction.getRate()));
            binding.transactionItemStateTv.setText(transaction.getState());
            binding.transactionItemTitleTv.setText(transaction.getName());

        }
        public void checkIfTransactionRated(){
            if (transaction.getRate()!=0){
               binding.transactionItemRateBtn.setVisibility(View.GONE);
                binding.transactionItemRateTv.setVisibility(View.VISIBLE);
            }else {
                binding.transactionItemRateBtn.setVisibility(View.VISIBLE);
                binding.transactionItemRateTv.setVisibility(View.GONE);
            }
        }
        public void checkTransactionState(){
            if (transaction.getState().equals("ملغي")){
                binding.transactionCardView.setCardBackgroundColor(context.getResources().getColor(R.color.color_gray_dark));
            }else {
              binding.transactionCardView.setCardBackgroundColor(context.getResources().getColor(R.color.green));

            }
        }

    }

    public void filterDate(Date startDate,Date endDate){

    }




}

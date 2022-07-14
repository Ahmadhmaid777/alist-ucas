package com.app.AlistApp.ui.transactionArchive;

import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.AlistApp.R;
import com.app.AlistApp.adapter.TransactionAdapter;
import com.app.AlistApp.databinding.ActivityTransactionArchiveBinding;
import com.app.AlistApp.model.Transaction;
import com.app.AlistApp.model.TransactionFilters;
import com.app.AlistApp.network.NetworkConnection;
import com.app.AlistApp.fragments.FilterBottomSheet;
import com.app.AlistApp.interfaces.ClickApplyFilterListener;
import com.app.AlistApp.utils.Utils;
import com.app.AlistApp.viewmodel.ServiceViewModel;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;

@AndroidEntryPoint
public class TransactionArchiveActivity extends AppCompatActivity implements ClickApplyFilterListener {
    private ActivityTransactionArchiveBinding binding;
    private TransactionAdapter transactionsAdapter;
    private ServiceViewModel viewModel;
    private Boolean isTransactionListCome = false;
    private NetworkConnection networkConnection;
    private boolean isFilterApply = false;
    private static final String TAG = "elc_tag";
    private TransactionFilters filters;
    private boolean isFilterTransactionCome = false;

    //in this activity you should run getUserTransaction() and getUserId()


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTransactionArchiveBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.transactionActToolbar.mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.transactionActToolbar.filter.setVisibility(View.VISIBLE);
        binding.transactionActToolbar.toolbarTitle.setText(getString(R.string.transaction_act_title));
        networkConnection = new NetworkConnection(this);
        viewModel = new ViewModelProvider(this).get(ServiceViewModel.class);
        checkInternetConnection();


        binding.transactionActRecycler.setItemAnimator(new FadeInDownAnimator());
        binding.transactionActRecycler.getItemAnimator().setAddDuration(500);
        transactionsAdapter = new TransactionAdapter(new ArrayList<>(), transactionId -> {
//            Intent intent=new Intent(getBaseContext(),RATE_ACTIVITY_NAME);
//            startActivity(intent);
        });

        initRecyclerView();


        if (!networkConnection.isNetworkAvailable()) {
            shimmerOnUi(true);
            binding.transactionActLostNetworkTv.setVisibility(View.VISIBLE);
        } else {
            getUserTransactions();
        }

        binding.transactionActToolbar.filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!FilterBottomSheet.isVisible)
                    FilterBottomSheet.newInstance().show(getSupportFragmentManager(), "");
            }
        });
        binding.transactionActToolbar.mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void getUserTransactions() {
        shimmerOnUi(true);
        viewModel.getUserTransactions(getUserId()).observe(this, new Observer() {
            @Override
            public void onChanged(Object response) {
                isTransactionListCome = true;
                if (response != null) {
                    shimmerOnUi(false);
                    List<Transaction> transactions = (List<Transaction>) response;
                    toggleEmptyDataMessage(transactions.size());
                    transactionsAdapter.setTransactions(transactions);
                } else {
                    Log.e(TAG, "get transaction fail");
                }
            }
        });
    }


    public void checkInternetConnection() {
        networkConnection.registerCallBack(this, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(@NonNull Network network) {
                super.onAvailable(network);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (binding.transactionActLostNetworkTv.getVisibility() == View.VISIBLE) {
                            hideLostInternetMessage();
                        }
                        if (!isTransactionListCome && !isFilterApply)
                            getUserTransactions();
                        else if (isFilterApply && isFilterTransactionCome == false) {
                            filterTransactions(getUserId(), filters);
                        }


                    }
                });

            }

            @Override
            public void onLost(@NonNull Network network) {
                super.onLost(network);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Utils.showSnackbar(binding.transactionActLostNetworkTv, getString(R.string.network_connection_lost));
                    }
                }, 1000);


            }
        });
    }


    public void filterTransactions(long userId, TransactionFilters filters) {
        shimmerOnUi(true);
        isFilterTransactionCome = true;
        viewModel.filterTransactions(userId, filters).observe(this, new Observer() {
            @Override
            public void onChanged(Object response) {
                if (response != null) {
                    shimmerOnUi(false);
                    List<Transaction> transactions = (List<Transaction>) response;
                    toggleEmptyDataMessage(transactions.size());
                    transactionsAdapter.setTransactions(transactions);

                } else {

                }
            }
        });
    }


    public void hideLostInternetMessage() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                binding.transactionActLostNetworkTv.setVisibility(View.GONE);
            }
        });
    }


    public void initRecyclerView() {
        binding.transactionActRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.transactionActRecycler.setAdapter(transactionsAdapter);
        binding.transactionActRecycler.setHasFixedSize(false);
    }


    public void shimmerOnUi(boolean visibility) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (visibility) {
                    binding.shimmerLayout.startShimmer();
                    binding.shimmerLayout.setVisibility(View.VISIBLE);

                } else {
                    binding.shimmerLayout.startShimmer();
                    binding.shimmerLayout.setVisibility(View.GONE);
                }
            }
        });

    }


    public long getUserId() {
        return viewModel.getCurrentUser().getId();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        networkConnection.unRegisterCallBack();
        FilterBottomSheet.clearFilter();
    }

    public void toggleEmptyDataMessage(int listSize) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (listSize == 0) {
                    binding.transactionActEmptyListTv.setVisibility(View.VISIBLE);
                    binding.transactionActRecycler.setVisibility(View.GONE);
                } else {
                    binding.transactionActEmptyListTv.setVisibility(View.GONE);
                    binding.transactionActRecycler.setVisibility(View.VISIBLE);
                }

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onClickApplyListener(TransactionFilters filters) {
        transactionsAdapter.clearTransaction();
        if (!filters.isFiltersNull()) {
            isFilterApply = true;
            this.filters = filters;
            if (networkConnection.isNetworkAvailable()) {
                filterTransactions(getUserId(), filters);
            } else
                Utils.showSnackbar(binding.transactionActEmptyListTv, getString(R.string.message_applied_filter_fail_check_internet));

        } else if (isFilterApply) {
            isFilterApply = false;
            getUserTransactions();
        }

    }


}

package com.app.AlistApp.ui.services;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.AlistApp.R;
import com.app.AlistApp.adapter.ServicesAdapter;
import com.app.AlistApp.databinding.ActivityServicesBinding;
import com.app.AlistApp.model.Service;
import com.app.AlistApp.ui.subService.SubServicesActivity;
import com.app.AlistApp.fragments.VideoDialog;
import com.app.AlistApp.ui.transactionArchive.TransactionArchiveActivity;
import com.app.AlistApp.utils.Keys;
import com.app.AlistApp.utils.ServicesData;

public class ServicesActivity extends AppCompatActivity {
    ActivityServicesBinding binding;
    private int WATCH_VIDEO_SERVICE_POSITION = 1;
    ServicesAdapter servicesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityServicesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar((Toolbar) binding.servicesActToolbar.mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.servicesActToolbar.showTransactions.setVisibility(View.VISIBLE);
        binding.servicesActToolbar.showTransactions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), TransactionArchiveActivity.class);
                startActivity(intent);
            }
        });

        //on click back arrow on tool bar
        binding.servicesActToolbar.mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //services adapter
        servicesAdapter = new ServicesAdapter(ServicesData.getServices(getBaseContext()),
                new ServicesAdapter.OnClickServiceItemListener() {
                    @Override
                    public void onClickListener(Service service) {
                        if (service.getId() == WATCH_VIDEO_SERVICE_POSITION) {
                            showVideo();
                            return;
                        }
                        Intent subServiceAct = new Intent(getBaseContext(), SubServicesActivity.class);
                        subServiceAct.putExtra(Keys.SERVICE_KEY, service);
                        startActivity(subServiceAct);


                    }
                });
        initRecyclerview();
        setRecyclerViewAnimation();


    }

    public void initRecyclerview() {
        binding.servicesActServicesRec.setAdapter(servicesAdapter);
        binding.servicesActServicesRec.setLayoutManager(new LinearLayoutManager(this));
        binding.servicesActServicesRec.setHasFixedSize(true);
    }


    //for recyclerView animation
    public void setRecyclerViewAnimation() {
        binding.servicesActServicesRec.getViewTreeObserver().addOnPreDrawListener(
                new ViewTreeObserver.OnPreDrawListener() {

                    @Override
                    public boolean onPreDraw() {
                        binding.servicesActServicesRec.getViewTreeObserver().removeOnPreDrawListener(this);
                        for (int counter = 0; counter < binding.servicesActServicesRec.getChildCount(); counter++) {
                            View recChild = binding.servicesActServicesRec.getChildAt(counter);
                            recChild.setAlpha(0.0f);
                            recChild.animate().alpha(1.0f)
                                    .setDuration(500)
                                    .setStartDelay(counter * 50)
                                    .start();
                        }

                        return true;
                    }
                });
    }


    public void showVideo(){
        if (!VideoDialog.newInstance().isVisible())
            VideoDialog.newInstance().show(getSupportFragmentManager(), "");
    }


}
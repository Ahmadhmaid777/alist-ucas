package com.app.AlistApp.ui.subService;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.app.AlistApp.R;
import com.app.AlistApp.adapter.SubServiceAdapter;
import com.app.AlistApp.databinding.ActivitySubServicesBinding;
import com.app.AlistApp.interfaces.ClickSubServiceListener;
import com.app.AlistApp.model.Service;
import com.app.AlistApp.model.SubService;
import com.app.AlistApp.ui.RequestService.RequestServiceActivity;
import com.app.AlistApp.utils.Keys;

import dagger.hilt.android.AndroidEntryPoint;


@AndroidEntryPoint

public class SubServicesActivity extends AppCompatActivity implements ClickSubServiceListener {
    ActivitySubServicesBinding binding;
    private Service mainService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubServicesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.subServiceActToolbar.mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Intent intent = getIntent();
        if (intent != null) {
            mainService = (Service) getIntent().getSerializableExtra(Keys.SERVICE_KEY);
            binding.subServiceActToolbar.toolbarTitle.setText(mainService.getName());
        }

        SubServiceAdapter serviceRecyclerAdapter = new SubServiceAdapter(mainService.getSubServices(), this);
        initRecyclerView(serviceRecyclerAdapter);

        // on click back arrow
        binding.subServiceActToolbar.mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });


    }


    // on click sub service listener
    @Override
    public void onClickSubService(SubService subService) {
        Intent intent = new Intent(getBaseContext(), RequestServiceActivity.class);
        intent.putExtra(Keys.SUB_SERVICE_KEY, subService);
        startActivity(intent);
        overridePendingTransition(R.anim.enter_animation, R.anim.leave_animation);

    }

    public void initRecyclerView(SubServiceAdapter adapter) {
        binding.subServiceActRec.setAdapter(adapter);
        binding.subServiceActRec.setLayoutManager(new LinearLayoutManager(this));
        binding.subServiceActRec.setHasFixedSize(true);

    }
}
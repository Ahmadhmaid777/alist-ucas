package com.app.AlistApp.ui.RequestService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.app.AlistApp.R;
import com.app.AlistApp.databinding.ActivityRequestServiceBinding;
import com.app.AlistApp.model.ServiceRequest;
import com.app.AlistApp.model.SpinnerItem;
import com.app.AlistApp.fragments.SpinnerBottomSheet;
import com.app.AlistApp.network.NetworkConnection;
import com.app.AlistApp.utils.Utils;
import com.app.AlistApp.viewmodel.ServiceViewModel;
import com.app.AlistApp.model.User;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint

public class RequestServiceActivity extends AppCompatActivity implements SpinnerBottomSheet.BottomSheetListener {
    private ActivityRequestServiceBinding binding;
    private String DATE_PATTERN = "dd-MM-yyyy";
    private ServiceViewModel viewModel;
    private TextInputLayout[] inputLayouts;
    private SimpleDateFormat simpleDateFormat;
    private Date requestDate;
    private NetworkConnection networkConnection;

    private static final String TAG = "ttt";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRequestServiceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.requestServiceToolbar.mainToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        binding.requestServiceToolbar.toolbarTitle.setText(getString(R.string.service_request_act_toolbar_title));
        networkConnection = new NetworkConnection(this);

        simpleDateFormat = new SimpleDateFormat(DATE_PATTERN);
        viewModel = new ViewModelProvider(this).get(ServiceViewModel.class);
        binding.requestServiceRequestDateEt.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));
        getTextInputLayouts();


        displayUserData(getCurrentUser());

        binding.requestServiceBtnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!networkConnection.isNetworkAvailable()) {
                    Utils.showSnackbar(binding.requestServiceBtnNext, getString(R.string.message_sendRequest_fail_checkInternet));
                    Log.d(TAG, "fail");
                    return;

                }

                if (checkFormElements()) {
                    binding.requestServiceBtnNext.setEnabled(false);
                    binding.progressBar.setVisibility(View.VISIBLE);
                    addServiceRequest(getServiceRequest());

//                    //deleted on run addServiceRequest()
//                    onlyForTest();
                } else {
                    binding.requestServiceBtnNext.setEnabled(true);
                }


            }
        });


        binding.requestServiceApplicantSpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpinnerBottomSheet.newInstance(SpinnerBottomSheet.APPLICANT_SPINNER_ID).show(getSupportFragmentManager(), "");
            }
        });
        binding.requestServiceApplicantJobSpn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SpinnerBottomSheet.newInstance(SpinnerBottomSheet.APPLICANT_JOB_SPINNER_ID).show(getSupportFragmentManager(), "");
            }
        });

        binding.requestServiceBtnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetForm();
                removeErrorMessages();
            }
        });

        //finish on click back arrow
        binding.requestServiceToolbar.mainToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    public boolean checkFormElements() {
        boolean isAllElementsNotNull = true;
        for (TextInputLayout inputLayout : inputLayouts) {
            if (TextUtils.isEmpty(inputLayout.getEditText().getText())) {
                inputLayout.setError(getString(R.string.message_this_field_required));
                isAllElementsNotNull = false;
            } else if (inputLayout.isErrorEnabled()) {
                inputLayout.setErrorEnabled(false);
            }
        }

        return isAllElementsNotNull;
    }

    //add service request to api
    public void addServiceRequest(ServiceRequest request) {
        viewModel.addServiceRequest(request).observe(RequestServiceActivity.this, new Observer() {
            @Override
            public void onChanged(Object response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        binding.progressBar.setVisibility(View.GONE);
                        binding.requestServiceBtnNext.setEnabled(true);
                        progressbarVisibility(false);
                        if (response != null) {
                            Utils.showSnackbar(binding.requestServiceBtnNext, getString(R.string.message_sendRequestDone));
                            resetForm();
                        } else {
                            Utils.showSnackbar(binding.requestServiceBtnNext, getString(R.string.message_sendRequestFail));

                        }


                    }
                });

            }
        });
    }


    public User getCurrentUser() {
        return viewModel.getCurrentUser();
    }


    public void displayUserData(User user) {
        binding.requestServicePhoneNumberEt.setText(user.getPhoneNumber());
        binding.requestServiceNearestLandmarkEt.setText(user.getNearestLandmark());
        binding.requestServiceApplicantNameEt.setText(user.getName());
    }

    public void resetForm() {
        for (TextInputLayout layout : inputLayouts) {
            layout.getEditText().setText("");
        }

        //set current date
        binding.requestServiceRequestDateEt.setText(simpleDateFormat.format(Calendar.getInstance().getTime()));

    }

    public void removeErrorMessages() {
        for (TextInputLayout layout : inputLayouts) {
            if (layout.isErrorEnabled()) {
                layout.setErrorEnabled(false);
            }
        }
    }

    //change progress visibility in main thread
    public void progressbarVisibility(boolean visibility) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (visibility)
                    binding.progressBar.setVisibility(View.VISIBLE);
                else
                    binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void getTextInputLayouts() {
        inputLayouts = new TextInputLayout[]{
                binding.requestServiceRequestDateEtLayout,
                binding.requestServiceApplicantSpnLayout,
                binding.requestServiceCardIdNumberEtLayout,
                binding.requestServiceApplicantNameEtLayout,
                binding.requestServiceApplicantJobSpnLayout,
                binding.requestServicePhoneNumberEtLayout,
                binding.requestServiceNearestLandmarkEtLayout,
                binding.requestServiceNotesEtLayout,
        };
    }

    //get service request data from input fields
    public ServiceRequest getServiceRequest() {
        String applicant = binding.requestServiceApplicantSpn.getText().toString();
        String notes = binding.requestServiceNotesEt.getText().toString();
        String nearestLandmark = binding.requestServiceNearestLandmarkEt.getText().toString();
        String applicantJob = binding.requestServiceApplicantJobSpn.getText().toString();
        String applicantName = binding.requestServiceApplicantNameEt.getText().toString();
        String phoneNumber = binding.requestServicePhoneNumberEt.getText().toString();
        long cardId = Long.parseLong(binding.requestServiceCardIdNumberEt.getText().toString());
        return new ServiceRequest(requestDate, applicantName, applicantJob, applicant, cardId, notes, nearestLandmark, phoneNumber);
    }

    @Override
    public void onSpinnerItemClickedListener(SpinnerItem clickedItem, int spinnerFor) {
        if (spinnerFor == SpinnerBottomSheet.APPLICANT_SPINNER_ID) {
            binding.requestServiceApplicantSpn.setText(clickedItem.getName());
        } else {
            binding.requestServiceApplicantJobSpn.setText(clickedItem.getName());
        }
    }


    public void onlyForTest() {
        binding.requestServiceBtnNext.setEnabled(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.progressBar.setVisibility(View.GONE);
                resetForm();
            }
        }, 2000);
    }



}
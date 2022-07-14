package com.app.AlistApp.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.AlistApp.R;
import com.app.AlistApp.databinding.BottomSheetFilterBinding;
import com.app.AlistApp.model.TransactionFilters;
import com.app.AlistApp.interfaces.ClickApplyFilterListener;
import com.app.AlistApp.ui.transactionArchive.TransactionArchiveActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilterBottomSheet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilterBottomSheet extends BottomSheetDialogFragment implements View.OnClickListener {
    private static final String TAG = "elc_filter";
    public static boolean isVisible = false;
    public static Button appliedServiceType;
    public static Button appliedServiceState;
    public static Date appliedStartDate = null;
    public static Date appliedEndDate = null;


    private Button selectedBtnServiceType, selectedBtnServiceState;
    private Date startDate = null, endDate = null;

    private int END_DATE = 1;
    private int START_DATE = 2;
    private int requestPikerViewId;


    private SimpleDateFormat simpleDateFormat;
    private View rootView;
    private MaterialDatePicker datePicker;
    private BottomSheetFilterBinding binding;
    private Button[] serviceTypeButtons;


    private ClickApplyFilterListener filterBtn;


    public static FilterBottomSheet newInstance() {
        FilterBottomSheet fragment = new FilterBottomSheet();
        return fragment;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof TransactionArchiveActivity) {
            filterBtn = (ClickApplyFilterListener) context;
        } else {
            try {
                throw new Exception("your activity should implement OnClickFilterBtn");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatePiker();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = LayoutInflater.from(requireContext()).inflate(R.layout.bottom_sheet_filter, null, false);
        binding = BottomSheetFilterBinding.bind(rootView);
        serviceTypeButtons = getServiceTypeButtons();
        simpleDateFormat = new SimpleDateFormat(getString(R.string.simple_date_pattern));


        //display previous filter if there one applied
        if (appliedServiceType != null || appliedServiceState != null || appliedStartDate != null)
            displayPreviousFilters(rootView);


        //show date piker on click start date
        binding.filterSheetStartDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPikerViewId = START_DATE;
                showDatePikerDialog();
            }
        });

        //show date piker on click end date
        binding.filterSheetEndDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPikerViewId = END_DATE;
                showDatePikerDialog();

            }
        });

        //set on click listener to services buttons
        for (Button button : serviceTypeButtons) {
            button.setOnClickListener(this);
        }


        // on click reset filter btn
        binding.filterSheetFilterResetFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetFilter();

            }
        });

        //on click apply filter button
        binding.filterSheetFilterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String serviceState = "";
                String serviceType = "";

                if (!checkStartAndEndDate(startDate, endDate)) return;


                if (selectedBtnServiceState != null) {
                    serviceState = selectedBtnServiceState.getText().toString();
                    appliedServiceState = selectedBtnServiceState;
                }
                if (selectedBtnServiceType != null) {
                    serviceType = selectedBtnServiceType.getText().toString();
                    appliedServiceType = selectedBtnServiceType;
                }

                filterBtn.onClickApplyListener(new TransactionFilters(serviceType, serviceState, startDate, endDate));
                dismiss();
            }
        });

        // select (done) service state
        binding.filterSheetDoneStateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectStateBtn((Button) view);
            }
        });
        //select (canceled) service state
        binding.filterSheetCancelStateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectStateBtn((Button) view);
            }
        });

        // on click ok on date piker dialog
        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis((Long) selection);
                if (requestPikerViewId == START_DATE) {
                    binding.filterSheetStartDateEt.setText(simpleDateFormat.format(calendar.getTime()));
                    startDate = calendar.getTime();
                } else {
                    binding.filterSheetEndDateEt.setText(simpleDateFormat.format(calendar.getTime()));
                    endDate = calendar.getTime();
                }

            }
        });


        binding.filterBottomSheetCloseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        // Inflate the layout for this fragment
        return binding.getRoot();
    }

    // initialize material date piker
    public void initDatePiker() {
        datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();
    }

    //on click a service type button
    @Override
    public void onClick(View view) {

        if (!view.isSelected()) {
            view.setSelected(true);
            if (selectedBtnServiceType != null)
                rootView.findViewById(selectedBtnServiceType.getId()).setSelected(false);
            selectedBtnServiceType = (Button) view;
        } else {
            view.setSelected(false);
            selectedBtnServiceType = null;

        }



    }

    public Button[] getServiceTypeButtons() {
        Button[] buttons = {binding.filterSheetNewSubBtn
                , binding.filterSheetEditSubBtn
                , binding.filterSheetActiveSubBtn
                , binding.filterSheetEnlargeOrMoveSubBtn
                , binding.filterSheetEditCounterBtn
                , binding.filterSheetInspectionTranBtn
                , binding.filterSheetFinancialTranBtn};
        return buttons;
    }

    public boolean checkStartAndEndDate(Date startDate, Date endDate) {
        if (startDate == null && endDate == null)
            return true;

        if (startDate == null && endDate != null) {
            binding.filterSheetStartDateEtLayout.setError("اختر تاريخ البداية");
            return false;
        } else if (endDate == null && startDate != null) {
            binding.filterSheetEndDateEtLayout.setError("اختر تاريخ النهاية");
            return false;
        }

        if (startDate.getTime() > Calendar.getInstance().getTimeInMillis()) {
            binding.filterSheetStartDateEtLayout.setError("لايمكن ان يكون التاريخ في المستقبل");
            return false;
        }
        if (endDate.getTime() > Calendar.getInstance().getTimeInMillis()) {
            binding.filterSheetEndDateEtLayout.setError("لايمكن ان يكون التاريخ في المستقبل");
            return false;
        }

        if (startDate.getTime() > endDate.getTime()) {
            binding.filterSheetEndDateEtLayout.setError("لا يمكن ان يكون تاريخ البداية بعد تاريخ النهاية");
            return false;
        }

        appliedStartDate = startDate;
        appliedEndDate = endDate;
        Log.d(TAG, "startDate" + appliedStartDate.getTime() + "   end date" + appliedEndDate.getTime());
        binding.filterSheetEndDateEtLayout.setErrorEnabled(false);
        binding.filterSheetStartDateEtLayout.setErrorEnabled(false);

        return true;

    }


    public void showDatePikerDialog() {
        if (!datePicker.isVisible())
            datePicker.show(requireActivity().getSupportFragmentManager(), "");
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        isVisible = false;
    }

    @Override
    public void onStart() {
        super.onStart();
        isVisible = true;
    }

    //display applied filter
    public void displayPreviousFilters(View layout) {
        if (appliedServiceType != null) {
            layout.findViewById(appliedServiceType.getId()).setSelected(true);
            selectedBtnServiceType = appliedServiceType;
        }
        if (appliedServiceState != null) {
            layout.findViewById(appliedServiceState.getId()).setSelected(true);
            selectedBtnServiceState = appliedServiceState;

        }
        if (appliedStartDate != null && appliedEndDate != null) {
            binding.filterSheetStartDateEt.setText(simpleDateFormat.format(appliedStartDate));
            binding.filterSheetEndDateEt.setText(simpleDateFormat.format(appliedEndDate));
        }

    }


    public void onSelectStateBtn(Button clickedState) {

        if (!clickedState.isSelected()) {
            clickedState.setSelected(true);
            if (selectedBtnServiceState != null)
                rootView.findViewById(selectedBtnServiceState.getId()).setSelected(false);
            selectedBtnServiceState = clickedState;
        }else {
            clickedState.setSelected(false);
            selectedBtnServiceState = null;
        }


    }

    public void resetFilter() {

        if (selectedBtnServiceType != null) {
            rootView.findViewById(selectedBtnServiceType.getId()).setSelected(false);
            selectedBtnServiceType = null;
        }
        if (selectedBtnServiceState != null) {
            rootView.findViewById(selectedBtnServiceState.getId()).setSelected(false);
            selectedBtnServiceState = null;
        }
        if (startDate != null || endDate != null) {
            startDate = null;
            endDate = null;
            binding.filterSheetStartDateEt.setText("");
            binding.filterSheetEndDateEt.setText("");
            binding.filterSheetStartDateEtLayout.setErrorEnabled(false);
            binding.filterSheetEndDateEtLayout.setErrorEnabled(false);
        }

    }
    //this method should declared on destroy the activity of fragment
    public static void clearFilter() {
        appliedServiceType = null;
        appliedServiceState = null;
        appliedStartDate = null;
        appliedEndDate = null;
    }


}